package com.irofactory.hyperchess.model.piece

import com.irofactory.hyperchess.R
import com.irofactory.hyperchess.model.board.File.*
import com.irofactory.hyperchess.model.game.state.GameSnapshotState
import com.irofactory.hyperchess.model.move.BoardMove
import com.irofactory.hyperchess.model.move.KingSideCastle
import com.irofactory.hyperchess.model.move.Move
import com.irofactory.hyperchess.model.move.QueenSideCastle
import com.irofactory.hyperchess.model.piece.Set.BLACK
import com.irofactory.hyperchess.model.piece.Set.WHITE
import kotlinx.parcelize.Parcelize

@Parcelize
class P45King(override val set: Set) : Piece {

    override val value: Int = Int.MAX_VALUE

    override val asset: Int =
        when (set) {
            WHITE -> R.drawable.__45_king
            BLACK -> R.drawable._45_king
        }

    override val symbol: String = when (set) {
        WHITE -> "♔"
        BLACK -> "♚"
    }

    override val textSymbol: String = SYMBOL

    override fun pseudoLegalMoves(gameSnapshotState: GameSnapshotState, checkCheck: Boolean): List<BoardMove> {
        val moves = targets
            .mapNotNull { singleMobility(gameSnapshotState, it.first, it.second) }
            .toMutableList()

        if (!checkCheck) {
            castleKingSide(gameSnapshotState)?.let { moves += it }
            castleQueenSide(gameSnapshotState)?.let { moves += it }
        }

        return moves
    }

    private fun castleKingSide(
        gameSnapshotState: GameSnapshotState
    ): BoardMove? {
        if (gameSnapshotState.hasCheck()) return null
        if (!gameSnapshotState.castlingInfo[set].canCastleKingSide) return null

        val rank = if (set == WHITE) 1 else 12
        val eSquare = gameSnapshotState.board[e, rank]!!
        val fSquare = gameSnapshotState.board[f, rank]!!
        val gSquare = gameSnapshotState.board[g, rank]!!
        val hSquare = gameSnapshotState.board[h, rank]!!
        if (fSquare.isNotEmpty || gSquare.isNotEmpty) return null
        if (gameSnapshotState.hasCheckFor(fSquare.position) || gameSnapshotState.hasCheckFor(gSquare.position)) return null
        if (hSquare.piece !is P10Rook) return null

        return BoardMove(
            move = KingSideCastle(this, eSquare.position, gSquare.position),
            consequence = Move(hSquare.piece, hSquare.position, fSquare.position)
        )
    }

    private fun castleQueenSide(
        gameSnapshotState: GameSnapshotState
    ): BoardMove? {
        if (gameSnapshotState.hasCheck()) return null
        if (!gameSnapshotState.castlingInfo[set].canCastleQueenSide) return null

        val rank = if (set == WHITE) 1 else 12
        val eSquare = gameSnapshotState.board[e, rank]!!
        val dSquare = gameSnapshotState.board[d, rank]!!
        val cSquare = gameSnapshotState.board[c, rank]!!
        val bSquare = gameSnapshotState.board[b, rank]!!
        val aSquare = gameSnapshotState.board[a, rank]!!
        if (dSquare.isNotEmpty || cSquare.isNotEmpty || bSquare.isNotEmpty) return null
        if (gameSnapshotState.hasCheckFor(dSquare.position) || gameSnapshotState.hasCheckFor(cSquare.position)) return null
        if (aSquare.piece !is P10Rook) return null

        return BoardMove(
            move = QueenSideCastle(this, eSquare.position, cSquare.position),
            consequence = Move(aSquare.piece, aSquare.position, dSquare.position)
        )
    }

    companion object {
        const val SYMBOL = "K"
        val targets = listOf(
            -1 to -1,
            -1 to 0,
            -1 to 1,
            0 to 1,
            0 to -1,
            1 to -1,
            1 to 0,
            1 to 1,
        )
    }
}
