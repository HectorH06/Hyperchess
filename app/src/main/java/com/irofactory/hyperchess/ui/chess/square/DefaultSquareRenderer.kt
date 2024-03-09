package com.irofactory.hyperchess.ui.chess.square

import com.irofactory.hyperchess.ui.chess.square.decoration.DatasetVisualiser
import com.irofactory.hyperchess.ui.chess.square.decoration.DefaultHighlightSquare
import com.irofactory.hyperchess.ui.chess.square.decoration.DefaultSquareBackground
import com.irofactory.hyperchess.ui.chess.square.decoration.DefaultSquarePositionLabel
import com.irofactory.hyperchess.ui.chess.square.decoration.TargetMarks

object DefaultSquareRenderer : SquareRenderer {

    override val decorations: List<SquareDecoration> =
        listOf(
            DefaultSquareBackground,
            DefaultHighlightSquare,
            DefaultSquarePositionLabel,
            DatasetVisualiser,
            TargetMarks
        )
}
