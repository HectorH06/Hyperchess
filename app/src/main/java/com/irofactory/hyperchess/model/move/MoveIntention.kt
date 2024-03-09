package com.irofactory.hyperchess.model.move

import com.irofactory.hyperchess.model.board.Position

data class MoveIntention(
    val from: Position,
    val to: Position
)
