package com.kj.cherryengineering20;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class InventoryViewer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_viewer);

        try {
            load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void load() throws IOException {

        FileInputStream fis = null;

        fis = openFileInput("inventory.txt");
        InputStreamReader isr = new InputStreamReader(fis);
        BufferedReader br = new BufferedReader(isr);
        StringBuilder sb = new StringBuilder();
        String text;

        while ((text = br.readLine()) != null) {
            String[] itemLine = text.split(",");
            for (int i = 0; i < itemLine.length; i++) {
                itemLine[i] = itemLine[i].replaceAll("[\\[\\](){}]","");
                sb.append(itemLine[i].trim()).append("\n");
            }
        }

        TextView tv = findViewById(R.id.display_text);
        tv.setText(sb.toString());
        //mEditText.setText(sb.toString());

        if (fis != null) {
            fis.close();
        }
    }

    public void goBack(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}