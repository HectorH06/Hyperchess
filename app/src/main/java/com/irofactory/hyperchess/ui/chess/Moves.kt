package com.irofactory.hyperchess.ui.chess

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.irofactory.hyperchess.model.move.AppliedMove
import com.irofactory.hyperchess.model.game.controller.GameController
import com.irofactory.hyperchess.model.board.Position.*
import com.irofactory.hyperchess.model.game.state.GamePlayState
import com.irofactory.hyperchess.ui.base.hyperTheme
import com.irofactory.hyperchess.ui.base.r03
import com.irofactory.hyperchess.ui.base.x03
import com.irofactory.hyperchess.ui.base.b04

@Composable
fun Moves(
    moves: List<AppliedMove>,
    selectedItemIndex: Int,
    onMoveSelected: (Int) -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(36.dp)
            .background(x03),
        contentAlignment = Alignment.CenterStart
    ) {
        val listState = rememberLazyListState()
        val coroutineScope = rememberCoroutineScope()

        // Where did you come from, where did you go?
        // Where did you come from ScrollableRow?
        LazyRow(
            state = listState,
            modifier = Modifier.padding(start = 16.dp, end = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            moves.forEachIndexed { index, move ->
                val isSelected = index == selectedItemIndex

                if (index % 2 == 0) {
                    item {
                        StepNumber(index / 2 + 1)
                        Spacer(modifier = Modifier.width(2.dp))
                    }
                }

                item("$index$isSelected") {
                    Move(move, isSelected) { onMoveSelected(index) }
                }

                if (index % 2 == 1) {
                    item {
                        Spacer(modifier = Modifier.width(10.dp))
                    }
                }
            }
        }

        LaunchedEffect(moves, selectedItemIndex) {
            if (moves.isNotEmpty()) {
                listState.animateScrollToItem(selectedItemIndex * 2)
            }
        }
    }
}

@Composable
private fun StepNumber(stepNumber: Int) {
    Text(
        text = "$stepNumber.",
        color = MaterialTheme.colors.onSecondary,
        fontWeight = FontWeight.Bold
    )
}

@Composable
private fun Move(
    move: AppliedMove,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    Text(
        text = move.toString(),
        color = MaterialTheme.colors.onSecondary,
        modifier = (if (isSelected) modifier.pill(move.effect != null) else modifier)
            .padding(start = 3.dp, end = 3.dp)
            .clickable(onClick = onClick)
    )
}

private fun Modifier.pill(isHighlighted: Boolean) =
    background(
        color = if (isHighlighted) r03 else b04,
        shape = RoundedCornerShape(6.dp)
    )

@Preview
@Composable
fun MovesPreview() {
    hyperTheme {
        var gamePlayState = GamePlayState()
        GameController({ gamePlayState }, { gamePlayState = it}).apply {
            applyMove(e2, e4)
            applyMove(e7, e5)
            applyMove(b1, c3)
            applyMove(b8, c6)
            applyMove(f1, b5)
            applyMove(d7, d5)
            applyMove(e4, d5)
            applyMove(d8, d5)
            applyMove(c3, d5)
            stepBackward()
            stepBackward()
            stepBackward()
            stepBackward()
        }

        Moves(
            moves = gamePlayState.gameState.moves(),
            selectedItemIndex = gamePlayState.gameState.currentIndex - 1
        ) {}
    }
}
