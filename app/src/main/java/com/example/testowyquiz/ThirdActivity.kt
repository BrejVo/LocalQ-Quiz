package com.example.testowyquiz

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.graphics.Color
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.ImageView
import android.widget.LinearLayout
import com.example.testowyquiz.databinding.ActivityThirdBinding

class ThirdActivity : AppCompatActivity() {
    private lateinit var binding: ActivityThirdBinding

    // Struktura pytania
    data class Question(
        val questionText: String,
        val answers: List<String>,
        val correctAnswerIndex: Int,
        val imageResId: Int? = null, // Opcjonalny obrazek
        var selectedAnswerIndex: Int? = null
    )

    //lista pytań
    private val questions = listOf(
        Question("Stolicą województwa wielkopolskiego jest?",
            listOf("Swarzędz", "Poznań", "Leszno", "Piła"), 1),

        Question("Jak nazywa się najdłuższa rzeka przepływająca przez Wielkopolskę?",
            listOf("Warta", "Noteć", "Odra", "Wisła"), 0),

        Question("W jakim mieście znajduje się Ostrów Tumski?",
            listOf("Piła", "Leszno", "Kalisz", "Poznań"), 3),

        Question("Który zamek znajduje się w Kórniku?",
            listOf("Zamek Kórnicki", "Zamek Królewski", "Zamek Wawelski", "Zamek Krzyżacki"), 0),

        Question("Jakiego miasta to herb?",
            listOf("Swarzędz", "Poznań", "Warszawa", "Gdańsk"), 1, R.drawable.poznan),

        Question("Gdzie znajduje się Jezioro Powidzkie?",
            listOf("Powidz", "Kalisz", "Piła", "Poznań"), 0),

        Question("Jakiego miasta to herb?",
            listOf("Lublin", "Poznań", "Swarzędz", "Los Angeles"), 2, R.drawable.swarzedz)
    )

    private var currentQuestionIndex = 0
    private lateinit var questionText: TextView
    private lateinit var answerButtons: List<Button>
    private lateinit var nextQuestionButton: Button
    private lateinit var questionImage: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Pobieranie wyglądu z activity_third.xml
        setContentView(R.layout.activity_third)

        questionText = findViewById(R.id.questionText)
        questionImage = findViewById(R.id.questionImage) // Pobieramy ImageView

        answerButtons = listOf(
            findViewById(R.id.answer1),
            findViewById(R.id.answer2),
            findViewById(R.id.answer3),
            findViewById(R.id.answer4)
        )
        nextQuestionButton = findViewById(R.id.nextQuestion)

        //Ładuje pierwszy quiz i dalej każdy przycisk dostaje onclic
        loadQuestion()

        answerButtons.forEachIndexed { index, button ->
            button.setOnClickListener { checkAnswer(index) }
        }
        //Sprawdza czy są kolejne pytania jak nei ma to koniec quizu a jak są to ładuje kolejne pytania
        nextQuestionButton.setOnClickListener {
            currentQuestionIndex++
            if (currentQuestionIndex < questions.size) {
                loadQuestion()
            } else {
                finishQuiz()
            }
        }
    }
    //Funkcja ładująca pytanie
    private fun loadQuestion() {
        val question = questions[currentQuestionIndex]
        questionText.text = question.questionText

        // Jeśli pytanie ma obrazek, pokazujemy go
        if (question.imageResId != null) {
            questionImage.setImageResource(question.imageResId)
            questionImage.visibility = View.VISIBLE
        } else {
            questionImage.visibility = View.GONE
        }

        //Resetuje kolory przyciskow
        answerButtons.forEachIndexed { index, button ->
            button.text = question.answers[index]
            button.setBackgroundColor(Color.parseColor("#6750A3"))
            button.isEnabled = true
        }
        nextQuestionButton.visibility = View.GONE
    }

    //Sprawdza odpowiedzi i nadane odpowiednie kolory do przycisków
    private fun checkAnswer(selectedIndex: Int) {
        val question = questions[currentQuestionIndex]
        question.selectedAnswerIndex = selectedIndex // Zapisujemy wybraną odpowiedź

        answerButtons.forEachIndexed { index, button ->
            button.isEnabled = false
            when {
                index == question.correctAnswerIndex -> button.setBackgroundColor(Color.YELLOW)
                index == selectedIndex -> button.setBackgroundColor(Color.RED)
            }
        }

        if (selectedIndex == question.correctAnswerIndex) {
            answerButtons[selectedIndex].setBackgroundColor(Color.GREEN)
        }

        nextQuestionButton.visibility = View.VISIBLE
    }
    //Koniec quizu, pokazuje wynik na koniec, opis i guzik z zakończeniem
    private fun finishQuiz() {
        //Oblicza liczbę zdobytych punktów
        val points = questions.count { it.selectedAnswerIndex == it.correctAnswerIndex }

        // Wyświetla komunikat na koniec
        val resultMessage = when {
            points in 0..3 -> "Matko ziemniaczana, chyba jesteś za Legią co?! Zdobyłeś całe: $points punktów :<"
            points in 4..6 -> "No no, mięsko zostawiłeś i ziemniaczki zjadłeś! Zdobyłeś: $points punktów"
            points == questions.size -> "Otrzymujesz order złotej pyry tej! Zdobyłeś: $points punktów"
            else -> "Zdobyłeś: $points punktów"
        }

        questionText.text = resultMessage
        answerButtons.forEach { it.visibility = View.GONE }
        questionImage.visibility = View.GONE
        nextQuestionButton.text = "Zakończ quiz"
        nextQuestionButton.setOnClickListener { finish() }
        val endQuizImage = findViewById<ImageView>(R.id.endQuizImage)
        endQuizImage.visibility = View.VISIBLE
        val mainLayout = findViewById<LinearLayout>(R.id.mainLayout)
        mainLayout.setBackgroundResource(R.drawable.last_background)
    }
}
