package com.irofactory.hyperchess.model.piece

import com.irofactory.hyperchess.model.board.Board
import com.irofactory.hyperchess.model.board.Square
import com.irofactory.hyperchess.model.game.state.GameSnapshotState
import com.irofactory.hyperchess.model.move.*

fun Piece.singleMobility(
    gameSnapshotState: GameSnapshotState,
    deltaFile: Int,
    deltaRank: Int
): BoardMove? {
    val board = gameSnapshotState.board
    val square = board.find(this) ?: return null
    val target = board[square.file + deltaFile, square.rank + deltaRank] ?: return null

    return when {
        target.hasPiece(set) -> null
        else -> BoardMove(
            move = Move(
                piece = this,
                intent = MoveIntention(from = square.position, to = target.position)
            ),
            preMove = when {
                target.isNotEmpty -> Capture(target.piece!!, target.position)
                else -> null
            }
        )
    }
}

fun Piece.unlimitedMobility(
    gameSnapshotState: GameSnapshotState,
    directions: List<Pair<Int, Int>>,
) : List<BoardMove> {
    val moves = mutableListOf<BoardMove>()
    val board = gameSnapshotState.board
    val square = board.find(this) ?: return emptyList()

    directions.map {
        moves += unlimitedMobility(board, square, it.first, it.second)
    }

    return moves
}

fun unlimitedMobility(
    board: Board,
    square: Square,
    deltaFile: Int,
    deltaRank: Int
): List<BoardMove> {
    requireNotNull(square.piece)
    val set = square.piece.set
    val moves = mutableListOf<BoardMove>()

    var i = 0
    while (true) {
        i++
        val target = board[square.file + deltaFile * i, square.rank + deltaRank * i] ?: break
        if (target.hasPiece(set)) {
            break
        }

        val move = Move(piece = square.piece, from = square.position, to = target.position)
        if (target.isEmpty) {
            moves += BoardMove(move)
            continue
        }
        if (target.hasPiece(set.opposite())) {
            moves += BoardMove(
                move = move,
                preMove = Capture(target.piece!!, target.position)
            )
            break
        }
    }

    return moves
}

fun Piece.limitedMobility(
    gameSnapshotState: GameSnapshotState,
    directions: List<Pair<Int, Int>>,
    maxDistance: Int = 2,
    attackPoints: Int = 0
): List<BoardMove> {
    var moves = mutableListOf<BoardMove>()
    val board = gameSnapshotState.board
    val square = board.find(this) ?: return emptyList()

    directions.forEach { (deltaFile, deltaRank) ->
        for (i in 1..maxDistance) {
            val target = board[square.file + deltaFile * i, square.rank + deltaRank * i] ?: break

            if (target.hasPiece(set)) {
                break
            }

            val move = Move(piece = this, intent = MoveIntention(from = square.position, to = target.position))

            if (target.isEmpty) {
                moves += BoardMove(move)
                continue
            }

            var auxMoves = BoardMove(move)
            if (attackPoints == 4) {
                for (j in 2..4) {

                    val target4 = board[square.file + deltaFile * j, square.rank + deltaRank * j] ?: break

                    val move4 = Move(piece = this, intent = MoveIntention(from = square.position, to = target4.position))
                    if (target4.isNotEmpty) {
                        moves += BoardMove(move4)
                        continue
                    }
                    if (target4.hasPiece(set.opposite())) {
                        auxMoves = BoardMove(
                            move = move4,
                            preMove = Capture(target4.piece!!, target4.position)
                        )
                    }
                }
            }
            if (target.hasPiece(set.opposite())) {
                moves += when (attackPoints) {
                    0 -> break
                    1 -> {
                        if(i == 1) {
                            BoardMove(
                                move = move,
                                preMove = Capture(target.piece!!, target.position)
                            )
                        } else { break }
                    }
                    2 -> BoardMove(
                        move = move,
                        preMove = Capture(target.piece!!, target.position)
                    )
                    3 -> {
                        BoardMove(
                            move = move,
                            preMove = CaptureArea3x3(target.piece!!, target.position),
                        )
                    }
                    4 -> {
                        auxMoves
                    }
                    5 -> {
                        BoardMove(
                            move = move,
                            preMove = CaptureArea7x7(target.piece!!, target.position),
                        )
                    }
                    else -> BoardMove(
                            move = move,
                            preMove = Capture(target.piece!!, target.position)
                    )
                }

                break
            }
        }
    }

    return moves
}

/* TODO esto sirve para atravesar
fun Piece.limitedMobility(
    gameSnapshotState: GameSnapshotState,
    directions: List<Pair<Int, Int>>,
    maxDistance: Int = 2,
    attackPoints: Int = 0
): List<BoardMove> {
    var moves = mutableListOf<BoardMove>()
    val board = gameSnapshotState.board
    val square = board.find(this) ?: return emptyList()

    directions.forEach { (deltaFile, deltaRank) ->
        for (i in 1..maxDistance) {
            val target = board[square.file + deltaFile * i, square.rank + deltaRank * i] ?: break

            if (target.hasPiece(set)) {
                break
            }

            val move = Move(piece = this, intent = MoveIntention(from = square.position, to = target.position))

            if (target.isEmpty) {
                moves += BoardMove(move)
                continue
            }

            var auxMoves = BoardMove(move)
            if (attackPoints == 4) {
                for (j in 1..4) {
                    val target4 = board[square.file + deltaFile * j, square.rank + deltaRank * j] ?: break

                    val move4 = Move(piece = this, intent = MoveIntention(from = square.position, to = target4.position))
                    if (target4.isNotEmpty) {
                        moves += BoardMove(move4)
                        continue
                    }
                    if (target4.hasPiece(set.opposite())) {
                        auxMoves = BoardMove(
                            move = move4,
                            preMove = Capture(target4.piece!!, target4.position)
                        )
                    }
                }
            }
            if (target.hasPiece(set.opposite())) {
                moves += when (attackPoints) {
                    0 -> break
                    1 -> {
                        if(i == 1) {
                            BoardMove(
                                move = move,
                                preMove = Capture(target.piece!!, target.position)
                            )
                        } else { break }
                    }
                    2 -> BoardMove(
                        move = move,
                        preMove = Capture(target.piece!!, target.position)
                    )
                    3 -> {
                        BoardMove(
                            move = move,
                            preMove = CaptureArea3x3(target.piece!!, target.position),
                        )
                    }
                    4 -> {
                        auxMoves
                    }
                    5 -> {
                        BoardMove(
                            move = move,
                            preMove = CaptureArea7x7(target.piece!!, target.position),
                        )
                    }
                    else -> BoardMove(
                        move = move,
                        preMove = Capture(target.piece!!, target.position)
                    )
                }

                break
            }
        }
    }

    return moves
}
*/