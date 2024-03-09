package com.irofactory.hyperchess.model.piece

import com.irofactory.hyperchess.R
import com.irofactory.hyperchess.model.game.state.GameSnapshotState
import com.irofactory.hyperchess.model.move.BoardMove
import com.irofactory.hyperchess.model.piece.Set.BLACK
import com.irofactory.hyperchess.model.piece.Set.WHITE
import kotlinx.parcelize.Parcelize

@Parcelize
class P20Spider(override val set: Set) : Piece {

    override val value: Int = 5

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
        val moves = mutableListOf<BoardMove>()

        for (direction in targets) {
            moves += limitedMobility(gameSnapshotState, listOf(direction), maxDistance = 3)
            moves += captureArea3x3(gameSnapshotState, direction.first, direction.second)
        }

        return moves
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
