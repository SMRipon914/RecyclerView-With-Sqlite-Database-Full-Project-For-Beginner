package com.creativeapp.rllll;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class EditData extends AppCompatActivity {
    EditText editText;
    Button button;
    DatabaseHelper db;
    String str_position;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final List<Data> List = new ArrayList<>();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_data);
        editText = findViewById(R.id.editText);
        button = findViewById(R.id.button);
        Bundle bundle = getIntent().getExtras();
        str_position = bundle.getString("position");
        position = Integer.parseInt(str_position);
        db = new DatabaseHelper(this);
        List.addAll(db.getAllDatas());
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Data data = List.get(position);
                // updating data text
                data.setData(editText.getText().toString());
                // updating data in db
                db.updateData(data);
                MainActivity.notifyAdapter();
                Intent intent = new Intent(EditData.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}