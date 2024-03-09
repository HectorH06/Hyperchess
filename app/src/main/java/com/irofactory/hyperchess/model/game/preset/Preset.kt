package com.irofactory.hyperchess.model.game.preset

import com.irofactory.hyperchess.model.game.controller.GameController

interface Preset {

    fun apply(gameController: GameController)
}
