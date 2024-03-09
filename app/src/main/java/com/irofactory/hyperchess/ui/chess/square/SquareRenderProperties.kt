package com.irofactory.hyperchess.ui.chess.square

import androidx.compose.foundation.layout.size
import androidx.compose.ui.Modifier
import com.irofactory.hyperchess.model.board.Coordinate
import com.irofactory.hyperchess.model.board.Position
import com.irofactory.hyperchess.model.board.toCoordinate
import com.irofactory.hyperchess.ui.chess.board.BoardRenderProperties

data class SquareRenderProperties(
    val position: Position,
    val isHighlighted: Boolean,
    val clickable: Boolean,
    val onClick: () -> Unit,
    val isPossibleMoveWithoutCapture: Boolean,
    val isPossibleCapture: Boolean,
    val boardProperties: BoardRenderProperties
) {
    val coordinate: Coordinate =
        position.toCoordinate(boardProperties.isFlipped)

    val sizeModifier: Modifier
        get() = Modifier.size(boardProperties.squareSize)
}
