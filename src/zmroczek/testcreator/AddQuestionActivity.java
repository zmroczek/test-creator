package zmroczek.testcreator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class AddQuestionActivity extends Activity {
    private EditText txtQuestion;
    private CheckBox checkBoxA;
    private CheckBox checkBoxB;
    private CheckBox checkBoxC;
    private EditText answerA;
    private EditText answerB;
    private EditText answerC;
    private String testName;
    private Button buttonNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_question);
        setViewElements();
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (areAllCheckBoxesEmpty()) {
                    showErrorMessage("You have to choose at least one correct answer!");
                } else if (isQuestionTextEmpty()) {
                    showErrorMessage("Question field cannot be empty!");
                } else {
                    addQuestionFromViewToDatabase();
                    showAddQuestionResult();
                }
            }

        });
    }

    private void showAddQuestionResult() {
        Intent intent = new Intent(AddQuestionActivity.this,
                ResultActivity.class);
        Bundle b = new Bundle();
        b.putString("message", "Question has been added!");
        intent.putExtras(b);
        startActivity(intent);
        finish();
    }

    private boolean isQuestionTextEmpty() {
        return txtQuestion.getText().toString().equals("");
    }

    private void showErrorMessage(String message) {
        Toast.makeText(AddQuestionActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    private boolean areAllCheckBoxesEmpty() {
        return !checkBoxA.isChecked() && !checkBoxB.isChecked() && !checkBoxC.isChecked();
    }

    private void addQuestionFromViewToDatabase() {
        Question q = createQuestionFromView();
        addQuestionToDatabase(q);
    }



    private Question createQuestionFromView() {
        return new Question(txtQuestion.getText().toString(),
                answerA.getText().toString(), answerB.getText().toString(),
                answerC.getText().toString(), checkBoxA.isChecked(),
                checkBoxB.isChecked(), checkBoxC.isChecked(),
                testName);
    }

    private void addQuestionToDatabase(Question q) {
        DatabaseHelper db = new DatabaseHelper(this);
        db.addQuestion(q);
    }

    private void setViewElements() {
        testName = getIntent().getExtras().getString("testName");
        txtQuestion = (EditText) findViewById(R.id.editText0);
        checkBoxA = (CheckBox) findViewById(R.id.checkBox1);
        checkBoxB = (CheckBox) findViewById(R.id.checkBox2);
        checkBoxC = (CheckBox) findViewById(R.id.checkBox3);
        answerA = (EditText) findViewById(R.id.editText1);
        answerB = (EditText) findViewById(R.id.editText2);
        answerC = (EditText) findViewById(R.id.editText3);
        buttonNext = (Button) findViewById(R.id.button1);
    }
}
