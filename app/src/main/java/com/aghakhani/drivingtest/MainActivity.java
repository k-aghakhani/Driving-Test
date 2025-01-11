package com.aghakhani.drivingtest;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView questionText;
    private RadioGroup optionsGroup;
    private RadioButton option1, option2, option3, option4;
    private Button nextButton;

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

    private int[] correctAnswers = {0, 0, 2};

    private int currentQuestionIndex = 0;
    private int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        questionText = findViewById(R.id.questionText);
        optionsGroup = findViewById(R.id.optionsGroup);
        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);
        option3 = findViewById(R.id.option3);
        option4 = findViewById(R.id.option4);
        nextButton = findViewById(R.id.nextButton);

        loadQuestion();

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
                    showResultDialog();
                }
            }
        });
    }

    private void loadQuestion() {
        questionText.setText(questions[currentQuestionIndex]);
        option1.setText(options[currentQuestionIndex][0]);
        option2.setText(options[currentQuestionIndex][1]);
        option3.setText(options[currentQuestionIndex][2]);
        option4.setText(options[currentQuestionIndex][3]);
        optionsGroup.clearCheck();
    }

    private void showResultDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_result, null);
        builder.setView(dialogView);

        AlertDialog dialog = builder.create();

        TextView title = dialogView.findViewById(R.id.dialogTitle);
        TextView message = dialogView.findViewById(R.id.dialogMessage);
        Button retryButton = dialogView.findViewById(R.id.retryButton);
        Button exitButton = dialogView.findViewById(R.id.exitButton);

        title.setText("Quiz Result");
        // تنظیم متن با رنگ سبز برای امتیاز
        String text = "You scored " + score + " out of " + questions.length + ".";
        SpannableString spannableString = new SpannableString(text);

        // تغییر رنگ بخشی از متن به سبز
        int start = text.indexOf(String.valueOf(score)); // موقعیت عدد امتیاز
        int end = start + String.valueOf(score).length();
        spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.result_color)), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        message.setText(spannableString);
        retryButton.setOnClickListener(v -> {
            resetQuiz();
            dialog.dismiss();
        });
        exitButton.setOnClickListener(v -> finish());

        dialog.setCancelable(false);
        dialog.show();
    }



    private void resetQuiz() {
        currentQuestionIndex = 0;
        score = 0;
        loadQuestion();
    }
}
