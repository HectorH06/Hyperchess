package com.irofactory.hyperchess.model.game.converter

import com.irofactory.hyperchess.model.game.state.GameState

sealed class ImportResult {
    class ValidationError(val msg: String) : ImportResult()
    class ImportedGame(val gameState: GameState) : ImportResult()
}
