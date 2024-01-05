package com.kj.cherryengineering20.completedCaseLogic;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

public class CaseManager extends AppCompatActivity {

    public CaseManager(Context context) {
    }

    public void subtractCase() {
        try {
            HashMap<String, Double> map = getFileAsHashmap();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
//        FileManager manager = new FileManager("inventory.txt");
//        try {
//            HashMap<String, Double> map = manager.getFileAsHashmap();
//
//            for (String key : map.keySet()) {
//                map.replace(key, map.get(key), 16.0);
//            }
//
//            manager.saveNewValues(map);
//        } catch (Exception e) {
//            throw new RuntimeException(e.getMessage());
//        }
    }

    public void addCase() {

    }

    public HashMap<String, Double> getFileAsHashmap() throws IOException {
        FileInputStream fis = null;
        HashMap<String, Double> map = new HashMap<>();

        fis = openFileInput("inventory.txt");
        InputStreamReader isr = new InputStreamReader(fis);
        BufferedReader br = new BufferedReader(isr);
        StringBuilder sb = new StringBuilder();
        String text;


//        try {
//            fis = openFileInput("inventory.txt");
//            InputStreamReader isr = new InputStreamReader(fis);
//            BufferedReader br = new BufferedReader(isr);
//            String text;
//
//            while ((text = br.readLine()) != null) {
//                String[] itemLine = text.split(",");
//                for (int i = 0; i < itemLine.length; i++) {
//                    String[] item = itemLine[i].split("=");
//                    item[0] = item[0].replaceAll("[\\[\\](){}]", "");
//                    item[1] = item[1].replaceAll("[\\[\\](){}]", "");
//                    String itemName = item[0];
//                    double quantity = Double.parseDouble(item[1]);
//                    map.put(itemName, quantity);
//                }
//            }
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }

        return map;
    }
}
