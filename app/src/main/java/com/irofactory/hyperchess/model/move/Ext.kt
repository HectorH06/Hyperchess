package com.irofactory.hyperchess.model.move

import com.irofactory.hyperchess.model.board.Position

fun List<BoardMove>.targetPositions(): List<Position> =
    map { it.to }
