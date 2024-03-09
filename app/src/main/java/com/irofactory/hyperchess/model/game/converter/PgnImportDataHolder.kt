package com.irofactory.hyperchess.model.game.converter

import com.irofactory.hyperchess.model.game.state.GameMetaInfo

data class PgnImportDataHolder(
    val metaInfo: GameMetaInfo,
    val moves: List<String>
)
