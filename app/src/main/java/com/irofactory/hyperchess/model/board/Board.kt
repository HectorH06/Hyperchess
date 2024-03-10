package com.irofactory.hyperchess.model.board

import android.os.Parcelable
import android.util.Log
import com.irofactory.hyperchess.model.board.Position.*
import com.irofactory.hyperchess.model.move.PieceEffect
import com.irofactory.hyperchess.model.piece.*
import com.irofactory.hyperchess.model.piece.Set
import com.irofactory.hyperchess.model.piece.Set.BLACK
import com.irofactory.hyperchess.model.piece.Set.WHITE
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import java.lang.IllegalArgumentException

@Parcelize
data class Board(
    val pieces: Map<Position, Piece>
) : Parcelable {
    constructor() : this(
        pieces = initialPieces
    )

    @IgnoredOnParcel
    val squares = Position.values().map { position ->
        position to Square(position, pieces[position])
    }.toMap()

    operator fun get(position: Position): Square =
        squares[position]!!

    operator fun get(file: File, rank: Int): Square? =
        get(file.ordinal + 1, rank)

    operator fun get(file: Int, rank: Int): Square? {
        return try {
            val position = Position.from(file, rank)
            squares[position]
        } catch (e: IllegalArgumentException) {
            null
        }
    }

    fun find(piece: Piece): Square? =
        squares.values.firstOrNull { it.piece == piece }

    inline fun <reified T : Piece> find(set: Set): List<Square> =
        squares.values.filter {
            it.piece != null &&
                it.piece::class == T::class &&
                it.piece.set == set
        }

    fun pieces(set: Set): Map<Position, Piece> =
        pieces.filter { (_, piece) -> piece.set == set }

    fun apply(effect: PieceEffect?): Board =
        effect?.applyOn(this) ?: this

    fun minus(positions: List<Position>): Board {
        Log.v("positions", "$positions")
        val newPieces = pieces.filterNot { (position, _) -> position in positions }
        return copy(pieces = newPieces)
    }
}

private val initialPieces = mapOf(
    a12 to P10Rook(BLACK),
    b12 to P10Rook(BLACK),
    c12 to P10Knight(BLACK),
    d12 to P10Knight(BLACK),
    e12 to P10Bishop(BLACK),
    f12 to P39Queen(BLACK),
    g12 to P45King(BLACK),
    h12 to P10Bishop(BLACK),
    i12 to P10Knight(BLACK),
    j12 to P10Knight(BLACK),
    k12 to P10Rook(BLACK),
    l12 to P10Rook(BLACK),

    a10 to P00Pawn(BLACK),
    b10 to P00Pawn(BLACK),
    c10 to P00Pawn(BLACK),
    d10 to P00Pawn(BLACK),
    e10 to P00Pawn(BLACK),
    f10 to P00Pawn(BLACK),
    g10 to P00Pawn(BLACK),
    h10 to P00Pawn(BLACK),
    i10 to P00Pawn(BLACK),
    j10 to P00Pawn(BLACK),
    k10 to P00Pawn(BLACK),
    l10 to P00Pawn(BLACK),

    a3 to P00Pawn(WHITE),
    b3 to P00Pawn(WHITE),
    c3 to P00Pawn(WHITE),
    d3 to P00Pawn(WHITE),
    e3 to P00Pawn(WHITE),
    f3 to P00Pawn(WHITE),
    g3 to P00Pawn(WHITE),
    h3 to P00Pawn(WHITE),
    i3 to P00Pawn(WHITE),
    j3 to P00Pawn(WHITE),
    k3 to P00Pawn(WHITE),
    l3 to P00Pawn(WHITE),

    a1 to P20Spider(WHITE),
    b1 to P10Rook(WHITE),
    c1 to P10Knight(WHITE),
    d1 to P10Knight(WHITE),
    e1 to P10Bishop(WHITE),
    f1 to P39Queen(WHITE),
    g1 to P45King(WHITE),
    h1 to P10Bishop(WHITE),
    i1 to P10Knight(WHITE),
    j1 to P10Knight(WHITE),
    k1 to P10Rook(WHITE),
    g6 to P20Spider(WHITE),
)
