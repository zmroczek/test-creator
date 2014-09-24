package zmroczek.testcreator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ResultActivity extends Activity {
    private TextView resultMessageView;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        setViewElements();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAfterResultActivity();
            }
        });
    }

    private void showAfterResultActivity() {
        Intent intent = new Intent(ResultActivity.this, TestsActivity.class);
        startActivity(intent);
        finish();
    }

    private void setViewElements() {
        resultMessageView = (TextView) findViewById(R.id.textResult);
        button = (Button) findViewById(R.id.button1);
        setResultMessageView();
    }

    private void setResultMessageView() {
        Bundle b = getIntent().getExtras();
        String message = b.getString("message");
        resultMessageView.setText(message);
    }
}