package com.irofactory.hyperchess.model.game.state

import android.os.Parcelable
import com.irofactory.hyperchess.model.board.Position
import com.irofactory.hyperchess.model.piece.Piece
import kotlinx.parcelize.Parcelize

sealed class PromotionState : Parcelable {
    @Parcelize
    object None : PromotionState()

    @Parcelize
    data class Await(val position: Position) : PromotionState()

    @Parcelize
    data class ContinueWith(val piece: Piece) : PromotionState()
}
