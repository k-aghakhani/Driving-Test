package com.aghakhani.drivingtest;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    // Declare variables to hold references to UI elements
    private TextView questionText;
    private RadioGroup optionsGroup;
    private RadioButton option1, option2, option3, option4;
    private Button nextButton;

    // Arrays to store questions, options, and correct answers
    private String[] questions = {
            "What does a stop sign mean?",
            "What should you do at a yellow traffic light?",
            "Who has the right of way at a 4-way stop?"
    };

    private String[][] options = {
            {"Stop completely", "Slow down", "Speed up", "Yield"},
            {"Stop if safe", "Speed through", "Stop completely", "Ignore it"},
            {"The car on the right", "The car on the left", "The first car to arrive", "Pedestrians only"}
    };

    private int[] correctAnswers = {0, 0, 2}; // Indices of correct answers

    // Variables to track the current question and the user's score
    private int currentQuestionIndex = 0;
    private int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Find views by their IDs
        questionText = findViewById(R.id.questionText);
        optionsGroup = findViewById(R.id.optionsGroup);
        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);
        option3 = findViewById(R.id.option3);
        option4 = findViewById(R.id.option4);
        nextButton = findViewById(R.id.nextButton);

        // Load the first question
        loadQuestion();

        // Set an OnClickListener for the next button
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = optionsGroup.getCheckedRadioButtonId();
                if (selectedId == -1) {
                    Toast.makeText(MainActivity.this, "Please select an answer", Toast.LENGTH_SHORT).show();
                    return;
                }

                int selectedAnswer = optionsGroup.indexOfChild(findViewById(selectedId));
                if (selectedAnswer == correctAnswers[currentQuestionIndex]) {
                    score++;
                }

                currentQuestionIndex++;
                if (currentQuestionIndex < questions.length) {
                    loadQuestion();
                } else {
                    showResult();
                }
            }
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


    }

    // Load the current question
    private void loadQuestion() {
        questionText.setText(questions[currentQuestionIndex]);
        option1.setText(options[currentQuestionIndex][0]);
        option2.setText(options[currentQuestionIndex][1]);
        option3.setText(options[currentQuestionIndex][2]);
        option4.setText(options[currentQuestionIndex][3]);
        optionsGroup.clearCheck();
    }

    // Show the final result
    private void showResult() {
        String resultMessage = "You scored " + score + " out of " + questions.length;
        Toast.makeText(this, resultMessage, Toast.LENGTH_LONG).show();
        // finish();
    }
}