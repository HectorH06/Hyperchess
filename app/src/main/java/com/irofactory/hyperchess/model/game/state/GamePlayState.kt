package com.irofactory.hyperchess.model.game.state

import android.os.Parcelable
import com.irofactory.hyperchess.model.dataviz.DatasetVisualisation
import com.irofactory.hyperchess.model.dataviz.impl.None
import kotlinx.parcelize.Parcelize

@Parcelize
data class GamePlayState(
    val gameState: GameState = GameState(GameMetaInfo.createWithDefaults()),
    val uiState: UiState = UiState(gameState.currentSnapshotState),
    val promotionState: PromotionState = PromotionState.None,
    val visualisation: DatasetVisualisation = None
) : Parcelable
