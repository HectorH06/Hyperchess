package com.irofactory.hyperchess.model.move

import android.os.Parcelable
import com.irofactory.hyperchess.model.board.Board
import com.irofactory.hyperchess.model.board.Position
import com.irofactory.hyperchess.model.dataviz.impl.WhiteKingsEscape.set
import com.irofactory.hyperchess.model.piece.Piece
import kotlinx.parcelize.Parcelize

interface PieceEffect : Parcelable {

    val piece: Piece

    fun applyOn(board: Board): Board
}

interface PreMove : PieceEffect

interface PrimaryMove : PieceEffect {

    val from: Position

    val to: Position
}

interface Consequence : PieceEffect

@Parcelize
data class Move(
    override val piece: Piece,
    override val from: Position,
    override val to: Position
) : PrimaryMove, Consequence {

    constructor(
        piece: Piece,
        intent: MoveIntention,
    ) : this(
        piece = piece,
        from = intent.from,
        to = intent.to
    )

    override fun applyOn(board: Board): Board =
        board.copy(
            pieces = board.pieces
                .minus(from)
                .plus(to to piece)
        )
}

@Parcelize
data class KingSideCastle(
    override val piece: Piece,
    override val from: Position,
    override val to: Position
) : PrimaryMove {

    override fun applyOn(board: Board): Board =
        board.copy(
            pieces = board.pieces
                .minus(from)
                .plus(to to piece)
        )
}

@Parcelize
data class QueenSideCastle(
    override val piece: Piece,
    override val from: Position,
    override val to: Position
) : PrimaryMove {

    override fun applyOn(board: Board): Board =
        board.copy(
            pieces = board.pieces
                .minus(from)
                .plus(to to piece)
        )
}

@Parcelize
data class Capture(
    override val piece: Piece,
    val position: Position,
) : PreMove {

    override fun applyOn(board: Board): Board =
        board.copy(
            pieces = board.pieces.minus(position)
        )

    fun applyEffects(board: Board, effects: List<PieceEffect>): Board {
        var updatedBoard = board
        for (effect in effects) {
            updatedBoard = effect.applyOn(updatedBoard)
        }
        return updatedBoard
    }
}

@Parcelize
data class CaptureArea3x3(
    override val piece: Piece,
    val position: Position,
) : PreMove, PieceEffect {

    override fun applyOn(board: Board): Board {
        val piecesToRemove = mutableListOf<Position>()

        for (i in -1..1) {
            for (j in -1..1) {
                val target = board[position.file + i, position.rank + j] ?: continue

                if (target.hasPiece(set.opposite())) {
                    piecesToRemove.add(target.position)
                }
            }
        }

        return board.minus(piecesToRemove)
    }
}

@Parcelize
data class CaptureArea7x7(
    override val piece: Piece,
    val position: Position,
) : PreMove, PieceEffect {

    override fun applyOn(board: Board): Board {
        val piecesToRemove = mutableListOf<Position>()

        for (i in -3..3) {
            for (j in -3..3) {
                val target = board[position.file + i, position.rank + j] ?: continue

                if (target.hasPiece(set.opposite())) { // still thinking about setting the condition to target.isNotEmpty with balance purposes
                    piecesToRemove.add(target.position)
                }
            }
        }

        return board.minus(piecesToRemove)
    }
}

@Parcelize
data class Promotion(
    val position: Position,
    override val piece: Piece,
) : Consequence {

    override fun applyOn(board: Board): Board =
        board.copy(
            pieces = board.pieces
                .minus(position)
                .plus(position to piece)
        )
}
