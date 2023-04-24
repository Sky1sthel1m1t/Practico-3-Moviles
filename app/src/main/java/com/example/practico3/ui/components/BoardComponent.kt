package com.example.practico3.ui.components

import android.app.AlertDialog
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.example.practico3.models.Board
import java.util.*
import kotlin.concurrent.schedule

class BoardComponent(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    private var paint = Paint()
    private var paintBorder = Paint()
    var board = Board()

    init {
        setupPaint()
    }

    override fun onDraw(canvas: Canvas?) {
        drawBoard(canvas)
    }

    private fun setupPaint() {
        paint.textSize = 40f
        paint.textAlign = Paint.Align.CENTER
        paint.style = Paint.Style.FILL_AND_STROKE

        paintBorder.style = Paint.Style.STROKE
        paintBorder.color = Color.BLACK
        paintBorder.strokeWidth = 2f
    }

    private fun drawBoard(canvas: Canvas?) {
        var y = 0f
        val width = (width / board.boardSize).toFloat()
        val height = (height / board.boardSize).toFloat()

        for (i in 0 until board.boardSize) {
            var x = 0f
            for (j in 0 until board.boardSize) {
                val value = board.board[i][j]
                val color = getColor(value)
                drawRect(canvas, x, y, width, height, color)
                paint.color = Color.BLACK
                canvas?.drawText(value.toString(), x + width / 2, y + height / 2, paint)
                x += width
            }
            y += height
        }
    }

    fun moveRight() {
        board.move(Board.Direction.RIGHT)
        spawnRandom()
    }

    fun moveLeft() {
        board.move(Board.Direction.LEFT)
        spawnRandom()
    }

    fun moveUp() {
        board.move(Board.Direction.UP)
        spawnRandom()
    }

    fun moveDown() {
        board.move(Board.Direction.DOWN)
        spawnRandom()
    }

    private fun spawnRandom() {
        invalidate()

        if (!board.validatePossibleMovements() && board.validateFullBoard()) {
            loseMessage()
            return
        }

        if (board.check2048()) {
            winMessage()
            return
        }

        Timer().schedule(250) {
            board.spawnRandom()
            invalidate()
        }
    }

    private fun drawRect(
        canvas: Canvas?,
        x: Float,
        y: Float,
        width: Float,
        height: Float,
        color: Int
    ) {
        paint.color = color
        canvas?.drawRect(x, y, x + width, y + height, paint)
        canvas?.drawRect(x, y, x + width, y + height, paintBorder)
    }

    private fun getColor(value: Int): Int {
        return when (value) {
            0 -> Color.WHITE
            2 -> Color.parseColor("#FFC0CB")
            4 -> Color.parseColor("#FFA500")
            8 -> Color.parseColor("#DECBB7")
            16 -> Color.parseColor("#FF00FF")
            32 -> Color.parseColor("#613F75")
            64 -> Color.parseColor("#5E3023")
            128 -> Color.parseColor("#3f88c5")
            256 -> Color.parseColor("#FFF07C")
            512 -> Color.parseColor("#E7D7C1")
            1024 -> Color.parseColor("#034C3C")
            2048 -> Color.parseColor("#FF00FF")
            else -> Color.parseColor("#FF0000")
        }
    }

    private fun loseMessage() {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Perdiste")
        builder.setMessage("No hay mas movimientos posibles")
        builder.setPositiveButton("Aceptar") { _, _ ->
            board = Board()
            invalidate()
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun winMessage() {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Ganaste")
        builder.setMessage("Has ganado el juego")
        builder.setPositiveButton("Aceptar") { _, _ ->
            board = Board()
            invalidate()
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}