package com.kj.cherryengineering20.fileManagers;

import android.content.Context;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

public class FileManager extends AppCompatActivity {

    private Context mContext;

    public FileManager(Context context) {
        this.mContext = context;
    }

    public HashMap<String, Double> getInventoryFileAsHashmap(String fileName) {
        File file = new File(mContext.getFilesDir(), fileName);
        createFileIfAbsent(file);

        FileInputStream fis = null;
        HashMap<String, Double> map = new HashMap<>();

        try {
            fis = mContext.openFileInput(fileName);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            String text;

            while ((text = br.readLine()) != null) {
                String[] itemLine = text.split(",");
                for (int i = 0; i < itemLine.length; i++) {
                    String[] item = itemLine[i].split("=");
                    item[0] = item[0].replaceAll("[\\[\\](){}]", "");
                    item[1] = item[1].replaceAll("[\\[\\](){}]", "");
                    String itemName = item[0];
                    double quantity = Double.parseDouble(item[1]);
                    map.put(itemName, quantity);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return map;
    }

    public void saveUpdatedInventoryFileAsHashmap(File file, String productName, int numberOfCases) {
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        FileInputStream fis = null;
        HashMap<String, Double> map = new HashMap<>();

        try {
            fis = mContext.openFileInput(file.getName());
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            String text;

            while ((text = br.readLine()) != null) {
                String[] itemLine = text.split(",");
                for (int i = 0; i < itemLine.length; i++) {
                    String[] item = itemLine[i].split("=");
                    item[0] = item[0].replaceAll("[\\[\\](){}]", "");
                    item[1] = item[1].replaceAll("[\\[\\](){}]", "");
                    String itemName = item[0];
                    double quantity = Double.parseDouble(item[1]);
                    if (itemName.equals(productName)) {
                        quantity = quantity - 16 * numberOfCases;
                    }
                    map.put(itemName, quantity);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        saveNewValues(file, map);
    }

    public void saveNewValues(File file, HashMap<String, Double> map) {
        String content = map.toString();
        try (FileOutputStream fos = mContext.openFileOutput(file.getName(), MODE_PRIVATE)) {
            fos.write(content.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Toast.makeText(mContext, "Inventory updated by FileManager", Toast.LENGTH_SHORT).show();
    }

    public void resetRetailProductsFile() {
        String fileName = "retailProducts.txt";
        File file = new File(mContext.getFilesDir(), fileName);
        createFileIfAbsent(file);

        HashMap<String, Double> retailProducts = new HashMap<>();
        retailProducts.put("0.5 MWB", 1500.0);
        retailProducts.put("1.5 MWB", 1500.0);
        retailProducts.put("32oz Hydra Jet", 1500.0);
        retailProducts.put("64oz Hydra Jet", 1500.0);
        retailProducts.put("128oz Hydra Jet", 1500.0);

        try {
            FileOutputStream fos = mContext.openFileOutput(fileName, MODE_PRIVATE);
            fos.write(retailProducts.toString().getBytes());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void createFileIfAbsent(File file) {
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
