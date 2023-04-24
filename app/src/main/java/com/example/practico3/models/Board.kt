package com.example.practico3.models

class Board {
    val boardSize = 4
    var score = 0
    var board = Array(boardSize) { Array(boardSize) { 0 } }

    init {
        initBoard()
    }

    private fun initBoard() {
        var aux = 0
        while (aux < 2) {
            val i = (0 until boardSize).random()
            val j = (0 until boardSize).random()
            if (board[i][j] == 0) {
                board[i][j] = 2
                aux++
            }
        }
    }

    fun move(direction: Direction) {
        when (direction) {
            Direction.UP -> moveUp()
            Direction.DOWN -> moveDown()
            Direction.LEFT -> moveLeft()
            Direction.RIGHT -> moveRight()
        }
    }

    fun spawnRandom() {
        while (!validateFullBoard()) {
            val i = (0 until boardSize).random()
            val j = (0 until boardSize).random()
            if (board[i][j] == 0) {
                board[i][j] = 2
                break
            }
        }
    }

    fun validateFullBoard(): Boolean {
        for (i in 0 until boardSize) {
            for (j in 0 until boardSize) {
                if (board[i][j] == 0) {
                    return false
                }
            }
        }
        return true
    }

    fun validatePossibleMovements() : Boolean {
        for (i in 0 until boardSize) {
            for (j in 0 until boardSize) {
                if (board[i][j] == 0) {
                    return true
                }
                if (i > 0 && board[i][j] == board[i - 1][j]) {
                    return true
                }
                if (i < boardSize - 1 && board[i][j] == board[i + 1][j]) {
                    return true
                }
                if (j > 0 && board[i][j] == board[i][j - 1]) {
                    return true
                }
                if (j < boardSize - 1 && board[i][j] == board[i][j + 1]) {
                    return true
                }
            }
        }
        return false
    }

    fun check2048() : Boolean{
        for (i in 0 until boardSize) {
            for (j in 0 until boardSize) {
                if (board[i][j] == 2048) {
                    return true
                }
            }
        }
        return false
    }

    private fun moveRight() {
        for (row in 0 until boardSize) {
            val possibleJoins = getPossiblesRowJoins(board[row])
            var numJoins = 0
            for (col in boardSize - 1 downTo 0) {
                if (board[row][col] != 0) {
                    for (actualCol in col until boardSize - 1) {
                        val actualNumber = board[row][actualCol]
                        val nextNumber = board[row][actualCol + 1]
                        if (actualNumber == nextNumber && nextNumber != 0 && numJoins < possibleJoins) {
                            board[row][actualCol] = 0
                            board[row][actualCol + 1] = actualNumber * 2
                            score += actualNumber * 2
                            numJoins++
                            break
                        } else if (nextNumber == 0) {
                            board[row][actualCol + 1] = actualNumber
                            board[row][actualCol] = 0
                        }
                    }
                }
            }
        }
    }

    private fun moveLeft() {
        for (row in 0 until boardSize) {
            val possibleJoins = getPossiblesRowJoins(board[row])
            var numJoins = 0
            for (col in 1 until boardSize) {
                if (board[row][col] != 0) {
                    for (actualCol in col downTo 1) {
                        val actualNumber = board[row][actualCol]
                        val nextNumber = board[row][actualCol - 1]
                        if (actualNumber == nextNumber && nextNumber != 0 && numJoins < possibleJoins) {
                            board[row][actualCol] = 0
                            board[row][actualCol - 1] = actualNumber * 2
                            score += actualNumber * 2
                            numJoins++
                            break
                        } else if (nextNumber == 0) {
                            board[row][actualCol - 1] = actualNumber
                            board[row][actualCol] = 0
                        }
                    }
                }
            }
        }
    }

    private fun moveDown() {
        for (col in 0 until boardSize) {
            val possibleJoins = getPossiblesColJoins(col)
            var numJoins = 0
            for (row in boardSize - 1 downTo 0) {
                if (board[row][col] != 0) {
                    for (actualRow in row until boardSize - 1) {
                        val actualNumber = board[actualRow][col]
                        val nextNumber = board[actualRow + 1][col]
                        if (actualNumber == nextNumber && nextNumber != 0 && numJoins < possibleJoins) {
                            board[actualRow][col] = 0
                            board[actualRow + 1][col] = actualNumber * 2
                            score += actualNumber * 2
                            numJoins++
                            break
                        } else if (nextNumber == 0) {
                            board[actualRow + 1][col] = actualNumber
                            board[actualRow][col] = 0
                        }
                    }
                }
            }
        }
    }

    private fun moveUp() {
        for (col in 0 until boardSize) {
            val possibleJoins = getPossiblesColJoins(col)
            var numJoins = 0
            for (row in 1 until boardSize) {
                if (board[row][col] != 0) {
                    for (actualRow in row downTo 1) {
                        val actualNumber = board[actualRow][col]
                        val nextNumber = board[actualRow - 1][col]
                        if (actualNumber == nextNumber && nextNumber != 0 && numJoins < possibleJoins) {
                            board[actualRow][col] = 0
                            board[actualRow - 1][col] = actualNumber * 2
                            score += actualNumber * 2
                            numJoins++
                            break
                        } else if (nextNumber == 0) {
                            board[actualRow - 1][col] = actualNumber
                            board[actualRow][col] = 0
                        }
                    }
                }
            }
        }
    }

    private fun getPossiblesRowJoins(array: Array<Int>): Int {
        val list = array.groupingBy { it }.eachCount().filter { it.value > 1 }.keys
        return list.size
    }

    private fun getPossiblesColJoins(col: Int): Int {
        val array = arrayListOf<Int>()
        for (row in 0 until boardSize) {
            array.add(board[row][col])
        }
        return getPossiblesRowJoins(array.toTypedArray())
    }

    enum class Direction {
        UP, DOWN, LEFT, RIGHT
    }
}