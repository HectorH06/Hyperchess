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

fun Piece.unlimitedMobilityLegacy( // TODO remove this function, replace all usages with Piece.limitedMobility
    gameSnapshotState: GameSnapshotState,
    directions: List<Pair<Int, Int>>,
) : List<BoardMove> {
    val moves = mutableListOf<BoardMove>()
    val board = gameSnapshotState.board
    val square = board.find(this) ?: return emptyList()

    directions.map {
        moves += unlimitedMobilityLegacy(board, square, it.first, it.second)
    }

    return moves
}

fun unlimitedMobilityLegacy(
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
        // TODO hacer un when con cada uno de los casos de movilidad
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
    mobilityPoints: Int = 2,
    attackPoints: Int = 3,
    specialPoints: Int = 0,
): List<BoardMove> {
    var moves = mutableListOf<BoardMove>()
    val board = gameSnapshotState.board
    val square = board.find(this) ?: return emptyList()

    val maxDistance = when (mobilityPoints) {
        0 -> 1
        1 -> 2
        2 -> 3
        3 -> 4
        4, 5 -> 12
        else -> 12
    }
    directions.forEach { (deltaFile, deltaRank) ->
        for (i in 1..maxDistance) {
            val target = board[square.file + deltaFile * i, square.rank + deltaRank * i] ?: break

            if (target.hasPiece(set) && (mobilityPoints != 4 || attackPoints != 4)) {
                break
            }

            val move = Move(piece = this, intent = MoveIntention(from = square.position, to = target.position))
            var auxMoves = BoardMove(move)
            if (attackPoints == 4) {
                for (j in 1..4) {

                    val target4 = board[square.file + deltaFile * j, square.rank + deltaRank * j] ?: break

                    val move4 = Move(piece = this, intent = MoveIntention(from = square.position, to = target4.position))

                    if (target4.isNotEmpty && j != 1){
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
            if (attackPoints == 5) {
                for (j in 0..1) {

                    val target5 = board[square.file + deltaFile * j, square.rank + deltaRank * j] ?: break

                    val move5 = Move(piece = this, intent = MoveIntention(from = square.position, to = target5.position))

                    if (target5.hasPiece(set.opposite())) {
                        auxMoves = BoardMove(
                            move = move5,
                            preMove = CaptureArea7x7(target5.piece!!, target5.position),
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
                    4, 5 -> auxMoves
                    else -> BoardMove(
                            move = move,
                            preMove = Capture(target.piece!!, target.position)
                    )
                }

                break
            }

            if (target.isEmpty) {
                moves += BoardMove(move)
                continue
            }
        }
    }

    return moves
}

/* TODO esto sirve para atravesar
fun Piece.penetrationMobility(
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