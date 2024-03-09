package com.irofactory.hyperchess.ui.app

import androidx.compose.runtime.Composable
import com.irofactory.hyperchess.model.game.preset.Preset
import com.irofactory.hyperchess.model.game.state.GamePlayState

@Composable
fun Preset(preset: Preset) {
    Game(
        state = GamePlayState(),
        preset = preset
    )
}
