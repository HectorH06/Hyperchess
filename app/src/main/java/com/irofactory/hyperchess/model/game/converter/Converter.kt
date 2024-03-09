package com.irofactory.hyperchess.model.game.converter

import com.irofactory.hyperchess.model.game.state.GameState

interface Converter {

    fun preValidate(text: String): Boolean

    fun import(text: String): ImportResult

    fun export(gameState: GameState): String
}

