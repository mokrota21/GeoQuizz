package com.example.geoquizz

import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import android.widget.Button
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.geoquizz.ui.theme.GeoQuizzTheme

class MainActivity : ComponentActivity() {
    private lateinit var mTrueButton: Button
    private lateinit var mFalseButton: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        mTrueButton = findViewById(R.id.true_button)
        mTrueButton.setOnClickListener {
            Toast.makeText(this@MainActivity, R.string.correct_toast, Toast.LENGTH_SHORT).show()
        }
        mFalseButton = findViewById(R.id.false_button)
        mFalseButton.setOnClickListener {
            Toast.makeText(this@MainActivity, R.string.incorrect_toast, Toast.LENGTH_SHORT).show()
        }
    }

}