package com.irofactory.hyperchess.model.dataviz

import android.os.Parcelable
import com.irofactory.hyperchess.model.board.Position
import com.irofactory.hyperchess.model.game.state.GameSnapshotState

interface DatasetVisualisation : Parcelable {

    val name: Int

    val minValue: Int

    val maxValue: Int

    fun dataPointAt(position: Position, state: GameSnapshotState, cache: MutableMap<Any, Any>): Datapoint?
}

