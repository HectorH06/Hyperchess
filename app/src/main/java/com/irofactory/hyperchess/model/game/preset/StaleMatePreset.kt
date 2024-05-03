package com.irofactory.hyperchess.model.game.preset

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.irofactory.hyperchess.model.board.Board
import com.irofactory.hyperchess.model.board.Position.*
import com.irofactory.hyperchess.model.game.controller.GameController
import com.irofactory.hyperchess.model.game.state.GameSnapshotState
import com.irofactory.hyperchess.model.piece.P45King
import com.irofactory.hyperchess.model.piece.P39Queen
import com.irofactory.hyperchess.model.piece.P10Rook
import com.irofactory.hyperchess.model.piece.Set.BLACK
import com.irofactory.hyperchess.model.piece.Set.WHITE
import com.irofactory.hyperchess.ui.base.hyperTheme
import com.irofactory.hyperchess.ui.app.Preset

object StaleMatePreset : Preset {

    override fun apply(gameController: GameController) {
        gameController.apply {
            val board = Board(
                pieces = mapOf(
                    a7 to P45King(BLACK),
                    e8 to P10Rook(WHITE),
                    d5 to P39Queen(WHITE),
                    g2 to P45King(WHITE),
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
fun StaleMatePresetPreview() {
    hyperTheme {
        Preset(StaleMatePreset)
    }
}

