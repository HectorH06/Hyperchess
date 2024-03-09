package com.irofactory.hyperchess.model.game.state

import com.irofactory.hyperchess.model.move.AppliedMove

data class GameStateTransition(
    val fromSnapshotState: GameSnapshotState,
    val toSnapshotState: GameSnapshotState,
    val move: AppliedMove
)
