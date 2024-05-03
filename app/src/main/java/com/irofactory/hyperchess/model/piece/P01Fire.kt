package com.irofactory.hyperchess.model.piece

import com.irofactory.hyperchess.R
import com.irofactory.hyperchess.model.game.state.GameSnapshotState
import com.irofactory.hyperchess.model.move.BoardMove
import com.irofactory.hyperchess.model.piece.Set.BLACK
import com.irofactory.hyperchess.model.piece.Set.WHITE
import kotlinx.parcelize.Parcelize

@Parcelize
class P01Fire(override val set: Set) : Piece {

    override val value: Int = 5

    override val asset: Int =
        when (set) {
            WHITE -> R.drawable.__01_fire
            BLACK -> R.drawable._01_fire
        }

    override val symbol: String = when (set) {
        WHITE -> "火"
        BLACK -> "火"
    }

    override val textSymbol: String = SYMBOL

    override fun pseudoLegalMoves(
        gameSnapshotState: GameSnapshotState,
        checkCheck: Boolean
    ): List<BoardMove> {
        return targets
            .mapNotNull { limitedMobility(gameSnapshotState, listOf(it), mobilityPoints = 0, attackPoints = 5) }
            .flatten()
            .toMutableList()
    }

    companion object {
        const val SYMBOL = "Fire"
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
