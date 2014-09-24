package zmroczek.testcreator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ChooseTestActivity extends Activity {
    private ListView testsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_test);
        setViewElements();
        testsList.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                String item = ((TextView) arg1).getText().toString();
                showResultForChosenTest(item);
            }
        });
    }

    private void showResultForChosenTest(String item) {
        Intent intent = new Intent(ChooseTestActivity.this, AddQuestionActivity.class);
        Bundle b = new Bundle();
        b.putString("testName", item);
        intent.putExtras(b);
        startActivity(intent);
        finish();
    }

    private void setViewElements() {
        DatabaseHelper db = new DatabaseHelper(this);
        ListAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, db.getAllTests());
        testsList = (ListView) findViewById(R.id.list);
        testsList.setAdapter(adapter);
    }


}
