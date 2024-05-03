package com.irofactory.hyperchess.ui.chess

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.irofactory.hyperchess.model.game.state.GameMetaInfo
import com.irofactory.hyperchess.model.game.state.GameState
import com.irofactory.hyperchess.model.game.state.GameSnapshotState
import com.irofactory.hyperchess.model.piece.P10Bishop
import com.irofactory.hyperchess.model.piece.P10Knight
import com.irofactory.hyperchess.model.piece.P00Pawn
import com.irofactory.hyperchess.model.piece.Piece
import com.irofactory.hyperchess.model.piece.P39Queen
import com.irofactory.hyperchess.model.piece.P10Rook
import com.irofactory.hyperchess.model.piece.Set
import com.irofactory.hyperchess.model.piece.Set.BLACK
import com.irofactory.hyperchess.model.piece.Set.WHITE
import com.irofactory.hyperchess.ui.base.hyperTheme
import kotlin.math.absoluteValue

@Composable
fun CapturedPieces(
    gameState: GameState,
    capturedBy: Set,
    arrangement: Arrangement.Horizontal,
    scoreAlignment: Alignment.Horizontal,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(36.dp)
            .padding(top = 2.dp, bottom = 2.dp)
            .background(MaterialTheme.colors.background)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp, end = 16.dp),
            horizontalArrangement = arrangement,
            verticalAlignment = Alignment.CenterVertically
        ) {
            val capturedPieces = gameState.currentSnapshotState.capturedPieces
                .filter { it.set == capturedBy.opposite() }
                .sortedWith { t1, t2 ->
                    if (t1.value == t2.value) t1.symbol.hashCode() - t2.symbol.hashCode()
                    else t1.value - t2.value
                }

            val score = gameState.currentSnapshotState.score
            val displayScore = (capturedBy == BLACK && score < 0) || (capturedBy == WHITE && score > 0)
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (scoreAlignment == Alignment.Start && displayScore) {
                    Score(score = score)
                }
                CapturedPieceList(
                    capturedPieces = capturedPieces,
                )
                if (scoreAlignment == Alignment.End && displayScore) {
                    Score(score = score)
                }
            }
        }
    }
}

@Composable
private fun CapturedPieceList(
    capturedPieces: List<Piece>
) {
    val stringBuilder = StringBuilder()
    capturedPieces.forEach { piece ->
        stringBuilder.append(piece.symbol)
    }

    Text(
        text = stringBuilder.toString(),
        color = MaterialTheme.colors.onBackground,
        fontSize = 20.sp
    )
}

@Composable
private fun Score(score: Int) {
    Text(
        text = "+${score.absoluteValue}",
        color = MaterialTheme.colors.onBackground,
        fontSize = 12.sp,
        modifier = Modifier.padding(
            start = 8.dp,
            end = 8.dp
        ),
    )
}

@Preview
@Composable
fun TakenPiecesPreview() {
    hyperTheme {
        CapturedPieces(
            gameState = GameState(
                gameMetaInfo = GameMetaInfo.createWithDefaults(),
                states = listOf(
                    GameSnapshotState(
                        capturedPieces = listOf(
                            P00Pawn(WHITE),
                            P00Pawn(WHITE),
                            P00Pawn(WHITE),
                            P00Pawn(WHITE),
                            P10Knight(WHITE),
                            P10Knight(WHITE),
                            P10Bishop(WHITE),
                            P39Queen(WHITE),

                            P00Pawn(BLACK),
                            P00Pawn(BLACK),
                            P00Pawn(BLACK),
                            P00Pawn(BLACK),
                            P10Knight(BLACK),
                            P10Rook(BLACK),
                        )
                    )
                )
            ),
            capturedBy = BLACK,
            Arrangement.End,
            Alignment.Start
        )
    }
}
