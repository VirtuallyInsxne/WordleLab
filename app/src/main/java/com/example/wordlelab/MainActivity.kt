package com.example.wordlelab

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.TextView
import android.widget.EditText
import androidx.activity.ComponentActivity


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.wordle_activity)

        val guessText = findViewById<EditText>(R.id.guessText)
        val guessButton = findViewById<Button>(R.id.guessButton)
        val guess1Text = findViewById<TextView>(R.id.guess1)
        val guess2Text = findViewById<TextView>(R.id.guess2)
        val guess3Text = findViewById<TextView>(R.id.guess3)
        val correctAnswerText = findViewById<TextView>(R.id.correctAnswerText)
        val correctAnswer = findViewById<TextView>(R.id.correctAnswer)

        var guessAmount = 3
        val guessAmountNum = findViewById<TextView>(R.id.guessAmountNum)
        guessAmountNum.text = guessAmount.toString()

        val wordToGuess = FourLetterWordList.getRandomFourLetterWord()
        correctAnswer.text = wordToGuess

        guessButton.setOnClickListener {
            closeKeyBoard()
            val guess = guessText.text.toString()
            val result = checkGuess(guess.uppercase(), wordToGuess.uppercase())

            if (result == "OOOO") {
                correctAnswerText.visibility = TextView.VISIBLE
                correctAnswer.visibility = TextView.VISIBLE
            }

            if (guessAmount == 3) {
                guess1Text.text = "$guess\n$result"
                guess1Text.visibility = TextView.VISIBLE
                guessAmount--
                guessAmountNum.text = guessAmount.toString()
            }
            else if (guessAmount == 2) {
                guess2Text.text = "$guess\n$result"
                guess2Text.visibility = TextView.VISIBLE
                guessAmount--
                guessAmountNum.text = guessAmount.toString()
            }
            else if (guessAmount == 1) {
                guess3Text.text = "$guess\n$result"
                guess3Text.visibility = TextView.VISIBLE
                guessAmount--
                guessAmountNum.text = guessAmount.toString()
                guessButton.text = "Reset"

                correctAnswerText.visibility = TextView.VISIBLE
                correctAnswer.visibility = TextView.VISIBLE

                guessButton.setOnClickListener {
                    guessAmount = 3
                    guessAmountNum.text = guessAmount.toString()
                    guess1Text.visibility = TextView.INVISIBLE
                    guess2Text.visibility = TextView.INVISIBLE
                    guess3Text.visibility = TextView.INVISIBLE

                    val wordToGuess = FourLetterWordList.getRandomFourLetterWord()
                    correctAnswer.text = wordToGuess

                    correctAnswerText.visibility = TextView.GONE
                    correctAnswer.visibility = TextView.GONE

                }A
            }
        }
    }

        private fun checkGuess(guess: String, wordToGuess: String): String {
            var result = ""
            for (i in 0..3) {
                if (guess[i] == wordToGuess[i]) {
                    result += "O"
                } else if (guess[i] in wordToGuess) {
                    result += "+"
                } else {
                    result += "X"
                }
            }
            return result
        }
        private fun closeKeyBoard() {
            val view = this.currentFocus
            if (view != null) {
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view.windowToken, 0)
            }
        }
}
