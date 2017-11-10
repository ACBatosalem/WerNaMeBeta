package edu.dlsu.mobapde.wername;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ExtendActivity extends AppCompatActivity {
    Button btnNow, btnLater;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extend);

        btnNow = (Button) findViewById(R.id.btn_ext_now);
        btnLater = (Button) findViewById(R.id.btn_ext_later);

        btnNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getBaseContext(), ArrivedActivity.class);
                startActivity(i);
                finish();
            }
        });

        btnLater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getBaseContext(), ArrivedActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
}
