package com.irofactory.hyperchess.model.board

enum class File {
    a, b, c, d, e, f, g, h, i, j, k, l
}

operator fun File.get(rank: Int): Position =
    Position.values()[this.ordinal * 12 + (rank - 1)]


operator fun File.get(rank: Rank): Position =
    Position.values()[this.ordinal * 12 + rank.ordinal]
