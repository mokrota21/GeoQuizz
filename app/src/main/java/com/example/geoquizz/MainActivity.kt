package com.example.geoquizz

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.geoquizz.theme.CheatActivity


class MainActivity : ComponentActivity() {
    private val KEY_INDEX = "index"
    private val TAG = "SudoMainActivity"

    private lateinit var mCheatActivityResultLauncher: ActivityResultLauncher<Intent>

    private lateinit var mTrueButton: Button
    private lateinit var mFalseButton: Button
    private lateinit var mCheatButton: Button
    private lateinit var mNextButton: Button
    private lateinit var mQuestionTextView: TextView
    private val mQuestionBank = arrayOf(
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true)
    )
    private var mIsCheating: BooleanArray = BooleanArray(mQuestionBank.size) { false }
    private var mCurrentIndex = 0

    private fun updateQuestion() {
        val question = mQuestionBank[mCurrentIndex].getTextResId()
        mQuestionTextView.setText(question)
    }

    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer = mQuestionBank[mCurrentIndex].getAnswerTrue()

        val messageResId: Int = if (mIsCheating[mCurrentIndex]) {
            R.string.judgment_toast
        } else if (userAnswer == correctAnswer) {
            R.string.correct_toast
        } else {
            R.string.incorrect_toast
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

        mCheatActivityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                // Handle the result here
                Log.d(TAG, "calculating result")
                val data = result.data
                val resultValue: Boolean? = data?.getBooleanExtra("EXTRA_ANSWER_SHOWN", false)

                if (resultValue == true) {
                    mIsCheating[mCurrentIndex] = true
                }

                Log.d(TAG, "mIsCheating[$mCurrentIndex] = ${mIsCheating[mCurrentIndex]}, resultValue = $resultValue")
            }
        }

        mCheatButton = findViewById(R.id.cheat_button)
        mCheatButton.setOnClickListener {
            val answerIsTrue = mQuestionBank[mCurrentIndex].getAnswerTrue()
            val i: Intent = CheatActivity.newIntent(this@MainActivity, answerIsTrue)
//            val i: Intent = Intent(this@MainActivity, CheatActivity::class.java)
//            i.putExtra("EXTRA_ANSWER_IS_TRUE", mQuestionBank[mCurrentIndex].getAnswerTrue())
            mCheatActivityResultLauncher.launch(i)
        }


        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0)
            mIsCheating = savedInstanceState.getBooleanArray("IS_CHEATING")!!
        }

        updateQuestion()

    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex)
        savedInstanceState.putBooleanArray("IS_CHEATING", mIsCheating)
    }

}