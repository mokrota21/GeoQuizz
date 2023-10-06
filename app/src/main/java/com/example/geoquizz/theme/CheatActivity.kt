package com.example.geoquizz.theme

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity
import com.example.geoquizz.R

class CheatActivity : ComponentActivity() {
    private lateinit var mShowAnswer: Button
    private var mAnswerIsTrue: Boolean = false
    private lateinit var mAnswerTextView: TextView

    companion object {

        private val EXTRA_ANSWER_IS_TRUE = "com.example.geoquizz.answer_is_true"
        fun newIntent(packageContext: Context, answerIsTrue: Boolean): Intent {
            val intent = Intent(packageContext, CheatActivity::class.java)
            intent.putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cheat)

        mAnswerIsTrue = intent.getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false)

        mAnswerTextView = findViewById(R.id.answer_text_view)

        mShowAnswer = findViewById(R.id.show_answer_button)
        mShowAnswer.setOnClickListener {
            if (mAnswerIsTrue) {
                mAnswerTextView.setText(R.string.true_button)
            } else {
                mAnswerTextView.setText(R.string.false_button)
            }
        }
    }


}
