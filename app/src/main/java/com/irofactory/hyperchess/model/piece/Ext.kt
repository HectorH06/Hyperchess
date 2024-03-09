package com.irofactory.hyperchess.model.piece

import com.irofactory.hyperchess.model.board.Board
import com.irofactory.hyperchess.model.board.Square
import com.irofactory.hyperchess.model.game.state.GameSnapshotState
import com.irofactory.hyperchess.model.move.Capture
import com.irofactory.hyperchess.model.move.Move
import com.irofactory.hyperchess.model.move.BoardMove
import com.irofactory.hyperchess.model.move.MoveIntention

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
    maxDistance: Int = 2
): List<BoardMove> {
    val moves = mutableListOf<BoardMove>()
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

            if (target.hasPiece(set.opposite())) {
                moves += BoardMove(
                    move = move,
                    preMove = Capture(target.piece!!, target.position)
                )
                break
            }
        }
    }

    return moves
}

fun Piece.captureArea3x3(
    gameSnapshotState: GameSnapshotState,
    deltaFile: Int,
    deltaRank: Int
): List<BoardMove> {
    val board = gameSnapshotState.board
    val square = board.find(this) ?: return emptyList()
    val moves = mutableListOf<BoardMove>()

    for (i in -1..1) {
        for (j in -1..1) {
            val target = board[square.file + deltaFile + i, square.rank + deltaRank + j]
            if (target != null && target.isNotEmpty && target.piece!!.set != this.set) {
                moves += BoardMove(
                    move = Move(
                        piece = this,
                        intent = MoveIntention(from = square.position, to = target.position)
                    ),
                    preMove = Capture(target.piece!!, target.position)
                )
            }
        }
    }

    return moves
}

fun Piece.captureArea5x5(
    gameSnapshotState: GameSnapshotState,
    deltaFile: Int,
    deltaRank: Int
): List<BoardMove> {
    val board = gameSnapshotState.board
    val square = board.find(this) ?: return emptyList()
    val moves = mutableListOf<BoardMove>()

    for (i in -2..2) {
        for (j in -2..2) {
            val target = board[square.file + deltaFile + i, square.rank + deltaRank + j]
            if (target != null && target.isNotEmpty && target.piece!!.set != this.set) {
                moves += BoardMove(
                    move = Move(
                        piece = this,
                        intent = MoveIntention(from = square.position, to = target.position)
                    ),
                    preMove = Capture(target.piece!!, target.position)
                )
            }
        }
    }

    return moves
}
