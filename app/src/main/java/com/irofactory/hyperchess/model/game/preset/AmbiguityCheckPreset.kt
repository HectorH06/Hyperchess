package com.irofactory.hyperchess.model.game.preset

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.irofactory.hyperchess.model.board.Board
import com.irofactory.hyperchess.model.board.Position.*
import com.irofactory.hyperchess.model.game.controller.GameController
import com.irofactory.hyperchess.model.game.state.GameSnapshotState
import com.irofactory.hyperchess.model.piece.P45King
import com.irofactory.hyperchess.model.piece.P10Knight
import com.irofactory.hyperchess.model.piece.P00Pawn
import com.irofactory.hyperchess.model.piece.P39Queen
import com.irofactory.hyperchess.model.piece.P10Rook
import com.irofactory.hyperchess.model.piece.Set.BLACK
import com.irofactory.hyperchess.model.piece.Set.WHITE
import com.irofactory.hyperchess.ui.app.Preset
import com.irofactory.hyperchess.ui.base.ChessoTheme

object AmbiguityCheckPreset : Preset {

    override fun apply(gameController: GameController) {
        gameController.apply {
            val board = Board(
                pieces = mapOf(
                    e2 to P45King(BLACK),
                    c3 to P00Pawn(BLACK),
                    h1 to P45King(WHITE),
                    e4 to P10Knight(WHITE),
                    a4 to P10Knight(WHITE),
                    a2 to P10Knight(WHITE),
                    b1 to P10Knight(WHITE),
                    d1 to P10Knight(WHITE),
                    b7 to P10Rook(WHITE),
                    c8 to P10Rook(WHITE),
                    d7 to P10Rook(WHITE),
                    g8 to P39Queen(WHITE),
                    h8 to P39Queen(WHITE),
                    h7 to P39Queen(WHITE),
                )
            )
            reset(
                GameSnapshotState(
                    board = board,
                    toMove = WHITE
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AmbiguityCheckPresetPreview() {
    ChessoTheme {
        Preset(AmbiguityCheckPreset)
    }
}

