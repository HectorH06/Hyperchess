package com.irofactory.hyperchess.model.game.state

import android.os.Parcelable
import com.irofactory.hyperchess.model.board.Board
import com.irofactory.hyperchess.model.piece.Set
import kotlinx.parcelize.Parcelize

@Parcelize
data class RepetitionRelevantState(
    val board: Board,
    val toMove: Set,
    val castlingInfo: CastlingInfo,
) : Parcelable
