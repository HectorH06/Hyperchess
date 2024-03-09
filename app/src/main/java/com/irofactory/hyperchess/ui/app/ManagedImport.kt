package com.irofactory.hyperchess.ui.app

import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.irofactory.hyperchess.R
import com.irofactory.hyperchess.model.game.converter.ImportResult
import com.irofactory.hyperchess.model.game.converter.PgnConverter
import com.irofactory.hyperchess.model.game.state.GamePlayState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun ManagedImport(
    pgnToImport: MutableState<String?>,
    gamePlayState: MutableState<GamePlayState>,
) {
    val pgn = pgnToImport.value
    val context = LocalContext.current
    val genericError = stringResource(id = R.string.import_validation_error_generic)

    if (pgn != null) {
        LoadingSpinner()
        LaunchedEffect(pgn) {
            withContext(Dispatchers.IO) {
                when (val result = PgnConverter.import(pgn)) {
                    is ImportResult.ImportedGame -> gamePlayState.value = GamePlayState(result.gameState)
                    is ImportResult.ValidationError -> {
                        withContext(Dispatchers.Main) {
                            Log.e("Chesso", result.msg)
                            Toast.makeText(
                                context,
                                genericError.replace("%s", result.msg),
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }

                pgnToImport.value = null
            }
        }
    }
}
