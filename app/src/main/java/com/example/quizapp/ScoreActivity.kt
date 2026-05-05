package com.example.quizapp

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class ScoreActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ScoreScreen()
        }
    }
}

@Composable
fun ScoreScreen() {

    val context = LocalContext.current
    val activity = context as Activity

    // ✅ Get data safely from Intent
    val score = activity.intent.getIntExtra("SCORE", 0)
    val totalQuestions = activity.intent.getIntExtra("TOTAL", 0)

    // ✅ Feedback logic
    val feedback = when (score) {
        totalQuestions -> "Master Hacker! You nailed it!"
        totalQuestions - 1 -> "Great job! Almost perfect."
        else -> "Stay Safe Online! Keep learning."
    }

    // ✅ UI
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Your Score",
            fontSize = 26.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "$score / $totalQuestions",
            fontSize = 32.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = feedback,
            fontSize = 18.sp
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                activity.finish() // ✅ go back to previous screen
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Finish")
        }
    }
}