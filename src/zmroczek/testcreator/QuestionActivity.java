package zmroczek.testcreator;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

public class QuestionActivity extends Activity {

	private List<Question> questionList;
	private int questionNumber = 0;
	private int score = 0;
	private int questionId = 0;
	private Boolean wrongAnswer = false;
	private Question currentQuestion;
	private TextView txtQuestion;
	private CheckBox checkBoxA, checkBoxB, checkBoxC;
	private Button buttonNext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_question);
        setViewElements();
		buttonNext.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (isCorrectAnswer()) {
					showQuestionResult();
				} else {
					wrongAnswer = true;
					showErrorMessage("Wrong answer, try again!");
				}
			}
		});
		setQuestionView();
	}

    private void showQuestionResult() {
        if (!wrongAnswer) {
            score++;
        }
        if (questionId < questionNumber) {
            currentQuestion = questionList.get(questionId);
            setQuestionView();
        } else {
            showAfterLastQuestionResult();
        }
    }

    private void showErrorMessage(String message) {
        Toast.makeText(QuestionActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    private void showAfterLastQuestionResult() {
        Intent intent = new Intent(QuestionActivity.this,
                ResultActivity.class);
        Bundle b = new Bundle();
        b.putString("message", "Your score (questions with no mistake): " + score + "/" + questionNumber);
        intent.putExtras(b);
        startActivity(intent);
        finish();
    }

    private boolean isCorrectAnswer() {
        return currentQuestion.isCorrectAnswer(checkBoxA.isChecked(), checkBoxB.isChecked(), checkBoxC.isChecked());
    }

    private List<Question> getAllQuestions() {
        DatabaseHelper db = new DatabaseHelper(this);
        Bundle bundle = getIntent().getExtras();
        String testName = bundle.getString("testName");
        return db.getAllQuestions(testName);
    }

    private void setViewElements() {
        questionList = getAllQuestions();
        questionNumber = questionList.size();
        currentQuestion = questionList.get(questionId);
        txtQuestion = (TextView) findViewById(R.id.textView1);
        checkBoxA = (CheckBox) findViewById(R.id.checkBox1);
        checkBoxB = (CheckBox) findViewById(R.id.checkBox2);
        checkBoxC = (CheckBox) findViewById(R.id.checkBox3);
        buttonNext = (Button) findViewById(R.id.button1);
    }

    private void setQuestionView() {
		wrongAnswer = false;
		txtQuestion.setText(currentQuestion.getQuestion());
		checkBoxA.setText(currentQuestion.getAnswerA());
		checkBoxB.setText(currentQuestion.getAnswerB());
		checkBoxC.setText(currentQuestion.getAnswerC());
		checkBoxA.setChecked(false);
		checkBoxB.setChecked(false);
		checkBoxC.setChecked(false);
		questionId++;
	}

}
