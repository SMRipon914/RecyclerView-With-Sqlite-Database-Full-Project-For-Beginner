package com.creativeapp.rllll;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddData extends AppCompatActivity {
    EditText editText;
    Button button;
    String str_data;
    DatabaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_data);
        editText = findViewById(R.id.editText);
        button = findViewById(R.id.button);
        db = new DatabaseHelper(this);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                str_data = editText.getText().toString();
                long id = db.insertData(str_data);
                Intent intent = new Intent(AddData.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}