package com.irofactory.hyperchess.model.board

enum class Position {
    a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12,
    b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12,
    c1, c2, c3, c4, c5, c6, c7, c8, c9, c10, c11, c12,
    d1, d2, d3, d4, d5, d6, d7, d8, d9, d10, d11, d12,
    e1, e2, e3, e4, e5, e6, e7, e8, e9, e10, e11, e12,
    f1, f2, f3, f4, f5, f6, f7, f8, f9, f10, f11, f12,
    g1, g2, g3, g4, g5, g6, g7, g8, g9, g10, g11, g12,
    h1, h2, h3, h4, h5, h6, h7, h8, h9, h10, h11, h12,
    i1, i2, i3, i4, i5, i6, i7, i8, i9, i10, i11, i12,
    j1, j2, j3, j4, j5, j6, j7, j8, j9, j10, j11, j12,
    k1, k2, k3, k4, k5, k6, k7, k8, k9, k10, k11, k12,
    l1, l2, l3, l4, l5, l6, l7, l8, l9, l10, l11, l12;

    val file: Int = ordinal / 12 + 1

    val fileAsLetter: Char =
        toString()[0]

    val rank: Int = ordinal % 12 + 1

    companion object {

        fun from(file: Int, rank: Int): Position {
            validate(file, rank)

            val idx = idx(file, rank)

            return values()[idx]
        }
    }
}
