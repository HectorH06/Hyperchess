package com.irofactory.hyperchess.model.piece

import com.irofactory.hyperchess.R
import com.irofactory.hyperchess.model.game.state.GameSnapshotState
import com.irofactory.hyperchess.model.move.BoardMove
import com.irofactory.hyperchess.model.piece.Set.BLACK
import com.irofactory.hyperchess.model.piece.Set.WHITE
import kotlinx.parcelize.Parcelize

@Parcelize
class P39Queen(override val set: Set) : Piece {

    override val value: Int = 9

    override val asset: Int =
        when (set) {
            WHITE -> R.drawable.__39_queen
            BLACK -> R.drawable._39_queen
        }

    override val symbol: String = when (set) {
        WHITE -> "♕"
        BLACK -> "♛"
    }

    override val textSymbol: String = "Q"

    override fun pseudoLegalMoves(gameSnapshotState: GameSnapshotState, checkCheck: Boolean): List<BoardMove> =
        unlimitedMobilityLegacy(gameSnapshotState, P10Rook.directions + P10Bishop.directions)
}
