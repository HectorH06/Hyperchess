package com.irofactory.hyperchess.model.dataviz

import androidx.compose.runtime.compositionLocalOf
import com.irofactory.hyperchess.model.dataviz.impl.ActivePieces
import com.irofactory.hyperchess.model.dataviz.impl.BlackKingsEscape
import com.irofactory.hyperchess.model.dataviz.impl.CheckmateCount
import com.irofactory.hyperchess.model.dataviz.impl.Influence
import com.irofactory.hyperchess.model.dataviz.impl.KnightsMoveCount
import com.irofactory.hyperchess.model.dataviz.impl.BlockedPieces
import com.irofactory.hyperchess.model.dataviz.impl.None
import com.irofactory.hyperchess.model.dataviz.impl.WhiteKingsEscape

val datasetVisualisations = listOf(
    None,
    ActivePieces,
    BlockedPieces,
    Influence,
    BlackKingsEscape,
    WhiteKingsEscape,
    KnightsMoveCount,
    CheckmateCount
)

val ActiveDatasetVisualisation = compositionLocalOf<DatasetVisualisation> { None }
