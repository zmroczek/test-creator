package zmroczek.testcreator;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class TestsActivity extends Activity {
    private ListView testsList;
    private Button addTestButton;
    private Button addQuestionButton;
    private DatabaseHelper db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tests);
        db = new DatabaseHelper(this);
        setViewElements();
        setOnClickListeners();
    }

    private void setOnClickListeners() {
        testsList.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                String item = ((TextView) arg1).getText().toString();
                if (!db.hasQuestions(item)) {
                    showNoQuestionsInTestMessage();
                } else {
                    startChosenTest(item);
                }
            }
        });
        addTestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TestsActivity.this, AddTestActivity.class);
                startActivity(intent);
            }
        });
        addQuestionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TestsActivity.this, ChooseTestActivity.class);
                startActivity(intent);
            }
        });
    }

    private void showNoQuestionsInTestMessage() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(TestsActivity.this);
        alertDialogBuilder.setTitle("No questions");
        alertDialogBuilder
                .setMessage("First add questions to this test.")
                .setCancelable(false)
                .setPositiveButton("Ok", new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void startChosenTest(String item) {
        Intent intent = new Intent(TestsActivity.this, QuestionActivity.class);
        Bundle b = new Bundle();
        b.putString("testName", item);
        intent.putExtras(b);
        startActivity(intent);
    }

    private void setViewElements() {
        testsList = (ListView) findViewById(R.id.list);
        ListAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, db.getAllTests());
        testsList.setAdapter(adapter);

        addTestButton = (Button) findViewById(R.id.Button01);
        addQuestionButton = (Button) findViewById(R.id.Button02);
    }
}
