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

class FlashcardActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FlashcardScreen()
        }
    }
}

@Composable
fun FlashcardScreen() {
    val context = androidx.compose.ui.platform.LocalContext.current

    val questions = listOf(
        HackQuestion("Putting your phone in rice fixes water damage", false, "Rice doesn't absorb moisture well. Use silica gel instead."),
        HackQuestion("Sleeping 8 hours helps memory", true, "Sleep helps consolidate memories. 7-9 hours is ideal."),
        HackQuestion("Cracking knuckles causes arthritis", false, "No evidence it causes arthritis. It's just gas bubbles popping.")
    )

    var currentQuestionIndex by remember { mutableStateOf(0) }
    var score by remember { mutableStateOf(0) }
    var showFeedback by remember { mutableStateOf(false) }
    var isCorrect by remember { mutableStateOf(false) }
    var selectedAnswer by remember { mutableStateOf<Boolean?>(null) }

    val currentQuestion = questions[currentQuestionIndex]

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Question ${currentQuestionIndex + 1}/${questions.size}",
            fontSize = 14.sp,
            color = Color.Gray,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Card(
            modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Text(
                text = currentQuestion.question,
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(24.dp)
            )
        }

        if (!showFeedback) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = {
                        selectedAnswer = true
                        isCorrect = currentQuestion.isHack == true
                        score = if (isCorrect) score + 1 else score
                        showFeedback = true
                    },
                    modifier = Modifier.weight(1f).height(56.dp)
                ) {
                    Text("Hack (True)", fontSize = 16.sp)
                }
                Spacer(modifier = Modifier.width(16.dp))
                Button(
                    onClick = {
                        selectedAnswer = false
                        isCorrect = currentQuestion.isHack == false
                        score = if (isCorrect) score + 1 else score
                        showFeedback = true
                    },
                    modifier = Modifier.weight(1f).height(56.dp)
                ) {
                    Text("Myth (False)", fontSize = 16.sp)
                }
            }
        } else {
            Card(
                modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (isCorrect) Color(0xFFD1FAE5) else Color(0xFFFEE2E2)
                )
            ) {
                Text(
                    text = if (isCorrect) "Correct! ${currentQuestion.explanation}" else "Wrong! ${currentQuestion.explanation}",
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(16.dp)
                )
            }

            Button(
                onClick = {
                    if (currentQuestionIndex < questions.size - 1) {
                        currentQuestionIndex++
                        showFeedback = false
                        selectedAnswer = null
                    } else {
                        val intent = Intent(context, ScoreActivity::class.java)
                        intent.putExtra("SCORE", score)
                        intent.putExtra("TOTAL", questions.size)
                        context.startActivity(intent)
                        finish()
                    }
                },
                modifier = Modifier.fillMaxWidth().height(56.dp)
            ) {
                Text("Next", fontSize = 18.sp)
            }
        }
    }
}

private fun ColumnScope.finish() {
    TODO("Not yet implemented")
}
