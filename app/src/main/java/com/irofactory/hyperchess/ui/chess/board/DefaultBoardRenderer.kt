package com.irofactory.hyperchess.ui.chess.board

import com.irofactory.hyperchess.ui.chess.board.decoration.DecoratedSquares
import com.irofactory.hyperchess.ui.chess.pieces.Pieces
import com.irofactory.hyperchess.ui.chess.square.DefaultSquareRenderer

object DefaultBoardRenderer : BoardRenderer {

    override val decorations: List<BoardDecoration> =
        listOf(
            DecoratedSquares(DefaultSquareRenderer.decorations),
            Pieces
        )
}
