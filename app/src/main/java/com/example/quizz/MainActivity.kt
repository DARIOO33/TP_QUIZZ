package com.example.quizz

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.app.Activity

data class Question(
    val text: String,
    val imageResId: Int,
    val choices: Array<String>,
    val correctAnswerIndex: Int,
    val imageDescription: String
)

class MainActivity : Activity() {

    // Les cinq questions sont définies localement : aucune base de données nécessaire.
    private val questions = arrayOf(
        Question(
            "Quelle forme possède trois côtés ?",
            R.drawable.triangle,
            arrayOf("Le cercle", "Le triangle", "Le carré"),
            1,
            "Un triangle violet avec trois côtés"
        ),
        Question(
            "Quelle forme a quatre côtés égaux et quatre angles droits ?",
            R.drawable.carre,
            arrayOf("Le rectangle", "Le carré", "L'étoile"),
            1,
            "Un carré violet"
        ),
        Question(
            "Quelle forme est ronde et n'a aucun côté ?",
            R.drawable.cercle,
            arrayOf("Le cercle", "Le triangle", "Le rectangle"),
            0,
            "Un cercle violet"
        ),
        Question(
            "Quelle forme possède cinq branches pointues ?",
            R.drawable.etoile,
            arrayOf("Le carré", "Le cercle", "L'étoile"),
            2,
            "Une étoile à cinq branches violette"
        ),
        Question(
            "Quelle forme a quatre côtés, avec les côtés opposés égaux ?",
            R.drawable.rectangle,
            arrayOf("Le triangle", "Le rectangle", "Le cercle"),
            1,
            "Un rectangle violet"
        )
    )

    private var currentQuestionIndex = 0
    private var score = 0
    private var hasAnswered = false

    private lateinit var txtScore: TextView
    private lateinit var txtProgress: TextView
    private lateinit var txtQuestionNumber: TextView
    private lateinit var txtQuestion: TextView
    private lateinit var txtFeedback: TextView
    private lateinit var imgQuestion: ImageView
    private lateinit var progressQuiz: ProgressBar
    private lateinit var choiceButtons: List<Button>
    private lateinit var btnNext: Button
    private lateinit var layoutResult: View
    private lateinit var txtResult: TextView
    private lateinit var btnReplay: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bindViews()

        choiceButtons.forEachIndexed { index, button ->
            button.setOnClickListener { checkAnswer(index) }
        }

        btnNext.setOnClickListener { goToNextQuestion() }
        btnReplay.setOnClickListener { restartQuiz() }

        showQuestion()
    }

    private fun bindViews() {
        txtScore = findViewById(R.id.txt_score)
        txtProgress = findViewById(R.id.txt_progress)
        txtQuestionNumber = findViewById(R.id.txt_question_number)
        txtQuestion = findViewById(R.id.txt_question)
        txtFeedback = findViewById(R.id.txt_feedback)
        imgQuestion = findViewById(R.id.img_question)
        progressQuiz = findViewById(R.id.progress_quiz)
        choiceButtons = listOf(
            findViewById(R.id.btn_choix1),
            findViewById(R.id.btn_choix2),
            findViewById(R.id.btn_choix3)
        )
        btnNext = findViewById(R.id.btn_suivant)
        layoutResult = findViewById(R.id.layout_result)
        txtResult = findViewById(R.id.txt_result)
        btnReplay = findViewById(R.id.btn_rejouer)
    }

    private fun showQuestion() {
        val question = questions[currentQuestionIndex]
        hasAnswered = false

        txtQuestionNumber.text = "Question ${currentQuestionIndex + 1} sur ${questions.size}"
        txtQuestion.text = question.text
        imgQuestion.setImageResource(question.imageResId)
        imgQuestion.contentDescription = question.imageDescription

        choiceButtons.forEachIndexed { index, button ->
            button.text = question.choices[index]
            button.isEnabled = true
            button.visibility = View.VISIBLE
        }

        txtFeedback.text = ""
        btnNext.isEnabled = false
        btnNext.text = if (currentQuestionIndex == questions.lastIndex) {
            "Voir le résultat"
        } else {
            "Question suivante"
        }

        layoutResult.visibility = View.GONE
        updateScoreAndProgress()
    }

    private fun checkAnswer(selectedIndex: Int) {
        // Cette condition empêche de répondre plusieurs fois à la même question.
        if (hasAnswered) return
        hasAnswered = true

        val question = questions[currentQuestionIndex]
        val isCorrect = selectedIndex == question.correctAnswerIndex

        if (isCorrect) {
            score++
            txtFeedback.text = "Bonne réponse ! Bravo."
            txtFeedback.setTextColor(Color.rgb(35, 140, 75))
        } else {
            val correctAnswer = question.choices[question.correctAnswerIndex]
            txtFeedback.text = "Mauvaise réponse. La bonne réponse était : $correctAnswer"
            txtFeedback.setTextColor(Color.rgb(190, 50, 50))
        }

        // La progression avance après chaque réponse, correcte ou non.
        updateScoreAndProgress()
        choiceButtons.forEach { it.isEnabled = false }
        btnNext.isEnabled = true
    }

    private fun updateScoreAndProgress() {
        txtScore.text = "Score : $score/${questions.size}"
        val answeredCount = if (hasAnswered) currentQuestionIndex + 1 else currentQuestionIndex
        progressQuiz.progress = answeredCount
        txtProgress.text = "Progression : $answeredCount/${questions.size}"
    }

    private fun goToNextQuestion() {
        // Impossible de continuer tant que la question courante n'a pas reçu de réponse.
        if (!hasAnswered) return

        if (currentQuestionIndex < questions.lastIndex) {
            currentQuestionIndex++
            showQuestion()
        } else {
            showFinalResult()
        }
    }

    private fun showFinalResult() {
        txtQuestionNumber.text = "Quiz terminé"
        txtQuestion.text = "Félicitations, vous avez terminé les cinq questions !"
        imgQuestion.visibility = View.GONE
        choiceButtons.forEach { it.visibility = View.GONE }
        txtFeedback.text = ""
        btnNext.visibility = View.GONE
        layoutResult.visibility = View.VISIBLE
        txtResult.text = "Résultat final : $score/${questions.size}"
        progressQuiz.progress = questions.size
        txtProgress.text = "Progression : ${questions.size}/${questions.size}"
    }

    private fun restartQuiz() {
        currentQuestionIndex = 0
        score = 0
        hasAnswered = false

        imgQuestion.visibility = View.VISIBLE
        btnNext.visibility = View.VISIBLE
        layoutResult.visibility = View.GONE

        showQuestion()
    }
}
