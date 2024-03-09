package com.irofactory.hyperchess.model.game.state

import com.irofactory.hyperchess.model.board.Position
import com.irofactory.hyperchess.model.board.isDarkSquare
import com.irofactory.hyperchess.model.board.isLightSquare
import com.irofactory.hyperchess.model.move.BoardMove
import com.irofactory.hyperchess.model.move.BoardMove.Ambiguity.AMBIGUOUS_FILE
import com.irofactory.hyperchess.model.move.BoardMove.Ambiguity.AMBIGUOUS_RANK
import com.irofactory.hyperchess.model.piece.P10Bishop
import com.irofactory.hyperchess.model.piece.P45King
import com.irofactory.hyperchess.model.piece.P10Knight
import com.irofactory.hyperchess.model.piece.Piece
import com.irofactory.hyperchess.model.piece.Set
import java.util.EnumSet

fun List<GameSnapshotState>.hasThreefoldRepetition(): Boolean =
    map { it.toRepetitionRelevantState().hashCode() }
        .groupBy { it }
        .map { it.value.size }
        .any { it > 2 }

fun Map<Position, Piece>.hasInsufficientMaterial(): Boolean =
    when {
        size == 2 && hasWhiteKing() && hasBlackKing() -> true
        size == 3 && hasWhiteKing() && hasBlackKing() && hasBishop() -> true
        size == 3 && hasWhiteKing() && hasBlackKing() && hasKnight() -> true
        size == 4 && hasWhiteKing() && hasBlackKing() && hasBishopsOnSameColor() -> true
        else -> false
    }

private fun Map<Position, Piece>.hasWhiteKing(): Boolean =
    values.find { it.set == Set.WHITE && it is P45King } != null

private fun Map<Position, Piece>.hasBlackKing(): Boolean =
    values.find { it.set == Set.BLACK && it is P45King } != null

private fun Map<Position, Piece>.hasBishop(): Boolean =
    values.find { it is P10Bishop } != null

private fun Map<Position, Piece>.hasKnight(): Boolean =
    values.find { it is P10Knight } != null

private fun Map<Position, Piece>.hasBishopsOnSameColor(): Boolean {
    val bishops = filter { it.value is P10Bishop }

    return bishops.size > 1 && (bishops.all { it.key.isLightSquare() } || bishops.all { it.key.isDarkSquare() })
}

fun BoardMove.applyAmbiguity(gameSnapshotState: GameSnapshotState): BoardMove =
    gameSnapshotState.board
        .pieces(gameSnapshotState.toMove)
        .filter { (_, piece) -> piece.textSymbol == this.piece.textSymbol }
        .flatMap { (_, piece) -> piece.pseudoLegalMoves(gameSnapshotState, false) }
        .filter { it.to == this.to }
        .map { it.from }
        .distinct() // promotion moves have same `from`, but are different per target piece, we don't need all of those
        .let { similarPiecePositions ->
            val ambiguity = EnumSet.noneOf(BoardMove.Ambiguity::class.java)
            when (similarPiecePositions.size) {
                1 -> this
                else -> {
                    val onSameFile = similarPiecePositions.filter { it.file == from.file }
                    if (onSameFile.size == 1) {
                        ambiguity.add(AMBIGUOUS_FILE)
                    } else {
                        val onSameRank = similarPiecePositions.filter { it.rank == from.rank }
                        if (onSameRank.size == 1) {
                            ambiguity.add(AMBIGUOUS_RANK)
                        } else {
                            ambiguity.add(AMBIGUOUS_FILE)
                            ambiguity.add(AMBIGUOUS_RANK)
                        }
                    }

                    copy(ambiguity = ambiguity)
                }
            }
        }
