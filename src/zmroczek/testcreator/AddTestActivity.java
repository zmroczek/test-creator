package zmroczek.testcreator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddTestActivity extends Activity {
    private EditText txtQuestion;
    private Button buttonNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_test);
        setViewElements();
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (addTestAndGetResult()) {
                    showAddTestResult();
                } else {
                    showErrorMessage("Wrong! Try again");
                }
            }
        });
    }

    private void showAddTestResult() {
        Intent intent = new Intent(AddTestActivity.this,
                TestsActivity.class);
        startActivity(intent);
        finish();
    }

    private void setViewElements() {
        txtQuestion = (EditText) findViewById(R.id.editText1);
        buttonNext = (Button) findViewById(R.id.button1);
    }

    private boolean addTestAndGetResult() {
        DatabaseHelper db = new DatabaseHelper(this);
        return db.addTest(txtQuestion.getText().toString());
    }

    private void showErrorMessage(String message) {
        Toast.makeText(AddTestActivity.this, message, Toast.LENGTH_SHORT).show();
    }
}
