package com.irofactory.hyperchess.model.piece

enum class Set {
    WHITE, BLACK;

    fun opposite() =
        when (this) {
            WHITE -> BLACK
            BLACK -> WHITE
        }
}
