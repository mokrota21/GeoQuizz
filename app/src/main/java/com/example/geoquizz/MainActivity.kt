package com.example.geoquizz

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.geoquizz.theme.CheatActivity


class MainActivity : ComponentActivity() {
    private val KEY_INDEX = "index"
    private val TAG = "SudoMainActivity"

    private lateinit var mTrueButton: Button
    private lateinit var mFalseButton: Button
    private lateinit var mCheatButton: Button
    private lateinit var mNextButton: Button
    private lateinit var mQuestionTextView: TextView
    private var REQUEST_CODE_CHEAT: int = 0
    private val mQuestionBank = arrayOf(
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true)
    )
    private var mCurrentIndex = 0

    private fun updateQuestion() {
        val question = mQuestionBank[mCurrentIndex].getTextResId()
        mQuestionTextView.setText(question)
    }

    private fun checkAnswer(user_answer: Boolean) {
        val correct_answer = mQuestionBank[mCurrentIndex].getAnswerTrue()

        var messageResId: Int

        if (user_answer == correct_answer) {
            messageResId = R.string.correct_toast
        } else {
            messageResId = R.string.incorrect_toast
        }

        Toast.makeText(this@MainActivity, messageResId, Toast.LENGTH_SHORT).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        mTrueButton = findViewById(R.id.true_button)
        mTrueButton.setOnClickListener {
            checkAnswer(true)
        }

        mFalseButton = findViewById(R.id.false_button)
        mFalseButton.setOnClickListener {
            checkAnswer(false)
        }

        mQuestionTextView = findViewById(R.id.question_text_view)

        mNextButton = findViewById(R.id.next_button)
        mNextButton.setOnClickListener {
            mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.size
            updateQuestion()
        }

        mCheatButton = findViewById(R.id.cheat_button)
        mCheatButton.setOnClickListener {
            val answerIsTrue = mQuestionBank[mCurrentIndex].getAnswerTrue()
            val i: Intent = CheatActivity.newIntent(this@MainActivity, answerIsTrue)
//            val i: Intent = Intent(this@MainActivity, CheatActivity::class.java)
//            i.putExtra("EXTRA_ANSWER_IS_TRUE", mQuestionBank[mCurrentIndex].getAnswerTrue())
            startActivityForResult(i, REQUEST_CODE_CHEAT)
        }


        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0)
        }

        updateQuestion()

    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex)
    }

}