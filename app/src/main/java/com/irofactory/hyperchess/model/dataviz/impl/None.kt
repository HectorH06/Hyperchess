package com.irofactory.hyperchess.model.dataviz.impl

import com.irofactory.hyperchess.R
import com.irofactory.hyperchess.model.board.Position
import com.irofactory.hyperchess.model.dataviz.Datapoint
import com.irofactory.hyperchess.model.dataviz.DatasetVisualisation
import com.irofactory.hyperchess.model.game.state.GameSnapshotState
import kotlinx.parcelize.Parcelize

@Parcelize
object None : DatasetVisualisation {

    override val name = R.string.viz_none

    override val minValue: Int = Int.MIN_VALUE

    override val maxValue: Int = Int.MAX_VALUE

    override fun dataPointAt(
        position: Position,
        state: GameSnapshotState,
        cache: MutableMap<Any, Any>
    ): Datapoint? =
        null
}
