package com.codepath.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

// edit item.

public class EditItemActivity extends AppCompatActivity {

    int currentPosition;
    EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        Bundle extras = getIntent().getExtras();
        String editItem = extras.getString("EditItem");
        currentPosition = extras.getInt("position");
        editText = (EditText)findViewById(R.id.editTextMultiline);
        editText.setText(editItem);
        editText.setSelection(editText.getText().length());

    }

    public void onSaveItem(View view){
        // Prepare data intent
        Intent data = new Intent();
        // Pass relevant data back as a result
        String itemText = editText.getText().toString();
        if (itemText.isEmpty()){
            Toast.makeText(this,"Please enter some text",Toast.LENGTH_LONG).show();
        }
        else {
            data.putExtra("name", editText.getText().toString());
            data.putExtra("code", 200); // ints work too
            data.putExtra("currentPosition", currentPosition);
            // Activity finished ok, return the data
            setResult(RESULT_OK, data); // set result code and bundle data for response
            finish(); // closes the activity, pass data to parent

        }

    }
}
