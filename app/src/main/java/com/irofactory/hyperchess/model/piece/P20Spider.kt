package com.irofactory.hyperchess.model.piece

import com.irofactory.hyperchess.R
import com.irofactory.hyperchess.model.game.state.GameSnapshotState
import com.irofactory.hyperchess.model.move.BoardMove
import com.irofactory.hyperchess.model.piece.Set.BLACK
import com.irofactory.hyperchess.model.piece.Set.WHITE
import kotlinx.parcelize.Parcelize

@Parcelize
class P20Spider(override val set: Set) : Piece {

    override val value: Int = 3

    override val asset: Int =
        when (set) {
            WHITE -> R.drawable.__20_spider
            BLACK -> R.drawable._20_spider
        }

    override val symbol: String = when (set) {
        WHITE -> "Spi"
        BLACK -> "Spi"
    }

    override val textSymbol: String = SYMBOL

    override fun pseudoLegalMoves(
        gameSnapshotState: GameSnapshotState,
        checkCheck: Boolean
    ): List<BoardMove> {
        return targets
            .mapNotNull { limitedMobility(gameSnapshotState, listOf(it), mobilityPoints = 2, attackPoints = 2, specialPoints = 1) }
            .flatten()
            .toMutableList()
    }

    companion object {
        const val SYMBOL = "Spi"
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
