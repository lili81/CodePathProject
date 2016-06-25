package com.codepath.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

//main item.

public class MainActivity extends AppCompatActivity {
    ArrayList<String> items;
    ArrayAdapter<String> itemsAdapter;
    ListView lvItems;
    private final int REQUEST_CODE = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        readItems();

        lvItems = (ListView) findViewById(R.id.lvItems);
        items = new ArrayList<>();
        itemsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        lvItems.setAdapter(itemsAdapter);
        items.add("first item");
        items.add("second item");
        setupListViewListener();




    }

    public void onAddItem(View v){
        EditText etNewItem = (EditText) findViewById(R.id.etNewItem);

        String itemText = etNewItem.getText().toString();
        if (itemText.isEmpty()){
            Toast.makeText(this,"Please enter some text",Toast.LENGTH_LONG).show();
        }
        else {
            itemsAdapter.add(itemText);
            etNewItem.setText("");
            writeItems();
        }

    }

    private void setupListViewListener(){
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                items.remove(position);
                itemsAdapter.notifyDataSetChanged();
                writeItems();
                return true;
            }

        });
        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String editItem = items.get(position).toString();
                Intent intent = new Intent(MainActivity.this, EditItemActivity.class);
                intent.putExtra("EditItem", editItem);
                intent.putExtra("position", position);
                startActivityForResult(intent, REQUEST_CODE);

            }
        });
    }



    private void readItems(){
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir,"todo.txt");
        try{
            items = new ArrayList<String>(FileUtils.readLines(todoFile));

        }
        catch(IOException e){
           items = new ArrayList<String>();
        }
    }

    private void writeItems(){

        File filesDir = getFilesDir();
        File todoFile = new File(filesDir,"todo.txt");
        try{
            FileUtils.writeLines(todoFile, items );

        }
        catch(IOException e){
           e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // REQUEST_CODE is defined above
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            // Extract name value from result extras
            String name = data.getExtras().getString("name");
            int code = data.getExtras().getInt("code", 0);
            int position = data.getIntExtra("currentPosition", 0);
            items.set(position, name);
            itemsAdapter.notifyDataSetChanged();
            writeItems();
            // Toast the name to display temporarily on screen
            Toast.makeText(this, name, Toast.LENGTH_SHORT).show();
        }
    }

}
