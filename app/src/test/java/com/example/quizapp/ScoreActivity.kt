package com.example.quizapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.graphics.Color

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
    val context = androidx.compose.ui.platform.LocalContext.current
    val score = context.intent?.getIntExtra("SCORE", 0)?: 0
    val totalQuestions = context.intent?.getIntExtra("TOTAL", 0)?: 0

    val feedback = when (score) {
        totalQuestions -> "Master Hacker! You nailed it!"
        totalQuestions - 1 -> "Great job! Almost perfect."
        else -> "Stay Safe Online! Keep learning."
    }

    val questions = listOf(
        HackQuestion("Putting your phone in rice fixes water damage", false, "Rice doesn't absorb moisture well. Use silica gel instead."),
        HackQuestion("Sleeping 8 hours helps memory", true, "Sleep helps consolidate memories. 7-9 hours is ideal."),
        HackQuestion("Cracking knuckles causes arthritis", false, "No evidence it causes arthritis. It's just gas bubbles popping.")
    )

    var showReview by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize().padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (!showReview) {
            Text(
                text = "Quiz Complete!",
                fontSize = 28.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Text(
                text = "Score: $score/$totalQuestions",
                fontSize = 32.sp,
                color = Color(0xFF2563EB),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Text(
                text = feedback,
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            Button(
                onClick = {
                    val intent = Intent(context, MainActivity::class.java)
                    context.startActivity(intent)
                    finish()
                },
                modifier = Modifier.fillMaxWidth().height(56.dp).padding(bottom = 12.dp)
            ) {
                Text("Play Again", fontSize = 18.sp)
            }

            Button(
                onClick = { showReview = true },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6B7280))
            ) {
                Text("Review Answers", fontSize = 18.sp)
            }
        } else {
            Text(
                text = "Review Answers",
                fontSize = 24.sp,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            questions.forEachIndexed { index, q ->
                Card(
                    modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                    elevation = CardDefaults.cardElevation(2.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Q${index + 1}: ${q.question}", fontSize = 14.sp)
                        Text(
                            text = "Answer: ${if (q.isHack) "Hack (True)" else "Myth (False)"}",
                            fontSize = 14.sp,
                            color = Color(0xFF059669)
                        )
                        Text(
                            text = q.explanation,
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                    }
                }
            }
            Button(
                onClick = { showReview = false },
                modifier = Modifier.fillMaxWidth().height(56.dp).padding(top = 16.dp)
            ) {
                Text("Back to Score", fontSize = 18.sp)
            }
        }
    }
}


