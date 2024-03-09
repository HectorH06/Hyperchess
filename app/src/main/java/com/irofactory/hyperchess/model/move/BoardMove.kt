package com.irofactory.hyperchess.model.move

import android.os.Parcelable
import com.irofactory.hyperchess.model.board.Position
import com.irofactory.hyperchess.model.move.BoardMove.Ambiguity.*
import com.irofactory.hyperchess.model.piece.P00Pawn
import com.irofactory.hyperchess.model.piece.Piece
import kotlinx.parcelize.Parcelize
import java.util.EnumSet

@Parcelize
data class BoardMove(
    val move: PrimaryMove,
    val preMove: PreMove? = null,
    val consequence: Consequence? = null,
    val ambiguity: EnumSet<Ambiguity> = EnumSet.noneOf(Ambiguity::class.java)
) : Parcelable {

    enum class Ambiguity {
        AMBIGUOUS_FILE, AMBIGUOUS_RANK,
    }

    val from: Position = move.from

    val to: Position = move.to

    val piece: Piece = move.piece

    override fun toString(): String =
        toString(useFigurineNotation = true)

    fun toString(useFigurineNotation: Boolean): String {
        if (move is KingSideCastle) return "O-O"
        if (move is QueenSideCastle) return "O-O-O"
        val isCapture = preMove is Capture
        val symbol = when {
            piece !is P00Pawn -> if (useFigurineNotation) piece.symbol else piece.textSymbol
            isCapture -> from.fileAsLetter
            else -> ""
        }
        val file = if (ambiguity.contains(AMBIGUOUS_FILE)) from.fileAsLetter.toString() else ""
        val rank = if (ambiguity.contains(AMBIGUOUS_RANK)) from.rank.toString() else ""
        val capture = if (isCapture) "x" else ""
        val promotion = if (consequence is Promotion) "=${consequence.piece.textSymbol}" else ""

        return "$symbol$file$rank$capture$to$promotion"
    }
}
