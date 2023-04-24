package com.example.practico3.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.practico3.R
import com.example.practico3.ui.components.BoardComponent
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var boardComponent: BoardComponent
    private lateinit var fabUp: FloatingActionButton
    private lateinit var fabDown: FloatingActionButton
    private lateinit var fabLeft: FloatingActionButton
    private lateinit var fabRight: FloatingActionButton
    private lateinit var lbScore: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        boardComponent = findViewById(R.id.boardComponent)
        fabUp = findViewById(R.id.fabUp)
        fabDown = findViewById(R.id.fabDown)
        fabLeft = findViewById(R.id.fabLeft)
        fabRight = findViewById(R.id.fabRight)
        lbScore = findViewById(R.id.lbScore)

        setupListeners()
        updateScore()
    }

    private fun setupListeners() {
        fabUp.setOnClickListener {
            boardComponent.moveUp()
            updateScore()
        }
        fabDown.setOnClickListener {
            boardComponent.moveDown()
            updateScore()
        }
        fabLeft.setOnClickListener {
            boardComponent.moveLeft()
            updateScore()
        }
        fabRight.setOnClickListener {
            boardComponent.moveRight()
            updateScore()
        }
    }

    private fun updateScore() {
        lbScore.text = "Score: " + boardComponent.board.score.toString()
    }
}