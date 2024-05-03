package com.irofactory.hyperchess.model.piece

import com.irofactory.hyperchess.R
import com.irofactory.hyperchess.model.game.state.GameSnapshotState
import com.irofactory.hyperchess.model.move.BoardMove
import com.irofactory.hyperchess.model.piece.Set.BLACK
import com.irofactory.hyperchess.model.piece.Set.WHITE
import kotlinx.parcelize.Parcelize

@Parcelize
class P10Bishop(override val set: Set) : Piece {

    override val value: Int = 3

    override val asset: Int =
        when (set) {
            WHITE -> R.drawable.__10_bishop
            BLACK -> R.drawable._10_bishop
        }

    override val symbol: String = when (set) {
        WHITE -> "♗"
        BLACK -> "♝"
    }

    override val textSymbol: String = "B"

    override fun pseudoLegalMoves(gameSnapshotState: GameSnapshotState, checkCheck: Boolean): List<BoardMove> =
        unlimitedMobilityLegacy(gameSnapshotState, directions)

    companion object {
        val directions = listOf(
            -1 to -1,
            -1 to 1,
            1 to -1,
            1 to 1,
        )
    }
}
