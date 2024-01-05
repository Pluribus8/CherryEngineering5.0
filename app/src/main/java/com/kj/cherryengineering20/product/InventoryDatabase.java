package com.kj.cherryengineering20.product;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.widget.Toast;

import com.kj.cherryengineering20.CompletedCaseQuantityMenu.PointFiveWaterBottle;
import com.kj.cherryengineering20.fileManagers.FileManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class InventoryDatabase {

    private Context mContext;
    private final String FILE_NAME;
    private double smallest;
    
    public InventoryDatabase(Context context) {
        mContext = context;
        FILE_NAME = "inventory.txt";
    }

    public void addInventory(String item, double numberOfCases) {

        HashMap<String, Double> inventoryMap = getInventoryAsHashmap();

        for (String s: inventoryMap.keySet()) {
            if (s.equals(item)) {
                inventoryMap.replace(s, inventoryMap.get(s) + numberOfCases);
                break;
            }
        }

        saveNewInventoryHashmap(inventoryMap);
    }

    //this is where inventory changes based on completed cases
    public void updateInventory(int numberOfProductsPerCase, Product product, double numberOfCases) {
        HashMap<String, Double> productMap = getProductComponents(product);

        HashMap<String, Double> inventoryMap = getInventoryAsHashmap();

        double newValue = 0;
        assert productMap != null;
        smallest = 5000;
        for (String key : productMap.keySet()) {
                key = key.trim();

                //this switch is probably not necessary - default case should suffice

                switch (key) {
                    case "MWB tube":
                        newValue = inventoryMap.get(key) - productMap.get("MWB tube") * numberOfProductsPerCase * numberOfCases;
                        break;
                    case "MWB slider":
                        newValue = inventoryMap.get(key) - productMap.get("MWB slider") * numberOfProductsPerCase * numberOfCases;
                        break;
                    case "HJ tube":
                        newValue = inventoryMap.get(key) - productMap.get("HJ tube") * numberOfProductsPerCase * numberOfCases;
                        break;
                    default:
                        newValue = inventoryMap.get(key) - productMap.get(key) * (numberOfCases * numberOfProductsPerCase);
                        break;
                }

                if (newValue < smallest)
                    smallest = newValue;
                if (newValue < 0) {
                    newValue = 0;
                }
                inventoryMap.replace(key, newValue);
        }

        updateSmallestInventoryPerProduct(product, numberOfCases);

        String content = inventoryMap.toString();
        try (FileOutputStream fos = mContext.openFileOutput(FILE_NAME, MODE_PRIVATE)) {
            fos.write(content.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Toast.makeText(mContext, FILE_NAME + " updated!", Toast.LENGTH_LONG).show();
    }

    public void updateSmallestInventoryPerProduct(Product product, double numberOfCases) {
        HashMap<String, Double> inventoryMap = getInventoryAsHashmap();
        HashMap<String, Double> componentsMap = getProductComponents(product);
        String smallestComponent = "";
        double smallest = 5000;

        for (String s: componentsMap.keySet()) {
            if (inventoryMap.get(s) < smallest) {
                smallestComponent = s;
                smallest = inventoryMap.get(s);
            }
        }


    }

    public HashMap<String, Double> getProductComponents(Product product) {
        switch (product.getName()) {
            case "0.5 MWB":
                PointFiveWaterBottle mwb = new PointFiveWaterBottle();
                return mwb.getPointFiveComponents();
            case "1.5 MWB":
                return new OnePointFiveMwb().getOnePointFiveComponents();
            case "2.2 MWB":
                return new TwoPointTwoMwb().getTwoPointTwoComponents();
            case "32oz HJ":
                HydraJet32 hj32 = new HydraJet32();
                return hj32.get32OzHjComponents();
            case "64oz HJ":
                HydraJet64 hj64 = new HydraJet64();
                return hj64.get64OzHjComponents();
            case "128oz HJ":
                HydraJet128 hj128 = new HydraJet128();
                return hj128.get128OzHjComponents();

            default:
                Toast.makeText(mContext, "Functionality for that product does not exist yet.", Toast.LENGTH_LONG).show();
                return new HashMap<>();
        }
    }

    public HashMap<String, Double> getInventoryAsHashmap() {
        FileInputStream fis = null;
        HashMap<String, Double> map = new HashMap<>();

        File file = new File(mContext.getFilesDir(), FILE_NAME);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        try {
            fis = mContext.openFileInput(FILE_NAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            String text;

            while ((text = br.readLine()) != null) {
                String[] itemLine = text.split(",");
                for (int i = 0; i < itemLine.length; i++) {
                    String[] item = itemLine[i].split("=");
                    item[0] = item[0].replaceAll("[\\[\\](){}]", "");
                    item[1] = item[1].replaceAll("[\\[\\](){}]", "");
                    String itemName = item[0].trim();
                    double quantity = Double.parseDouble(item[1]);
                    map.put(itemName, quantity);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return map;
    }

    public List<String> getInventoryProductNames() {
        List<String> products = new ArrayList<>();

        FileInputStream fis;
        try {
            fis = mContext.openFileInput(FILE_NAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            String text;

            while ((text = br.readLine()) != null) {
                String[] itemLine = text.split(",");
                for (String s: itemLine) {
                    s = s.replaceAll("[\\[\\](){}]", "");
                    String[] item = s.split("=");
                    products.add(item[0].trim());
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return products;
    }

    public List<String> getInventoryProductNames(File file) {
        List<String> products = new ArrayList<>();
        createFileIfAbsent(file.getName());

        FileInputStream fis;
        try {
            fis = mContext.openFileInput(file.getName());
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            String text;

            while ((text = br.readLine()) != null) {
                String[] itemLine = text.split(",");
                for (String s: itemLine) {
                    s = s.replaceAll("[\\[\\](){}]", "");
                    String[] item = s.split("=");
                    products.add(item[0].trim());
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return products;
    }


    public List<String> getRetailProductNames() {
        File file = new File(mContext.getFilesDir(), "smallestInventoryPerProduct.txt");
        return getInventoryProductNames(file);
    }

    public void saveNewInventoryHashmap(HashMap<String, Double> map) {
        FileOutputStream fos = null;
        try {
            fos = mContext.openFileOutput(FILE_NAME, MODE_PRIVATE);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        String content = map.toString();
        try {
            fos.write(content.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void createFileIfAbsent(String fileName) {
        File file = new File(mContext.getFilesDir(), fileName);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public LinkedHashMap<String, Double> sortHashMap(HashMap<String, Double> map) {
        // Step 1: Convert the HashMap to a list of entries
        List<Map.Entry<String, Double>> entryList = new ArrayList<>(map.entrySet());

        // Step 2: Sort the list of entries based on the numeric values of the values
        Collections.sort(entryList, Comparator.comparingDouble(Map.Entry::getValue));

        // Step 3: Create a new LinkedHashMap to maintain the order of sorted entries
        LinkedHashMap<String, Double> sortedHashMap = new LinkedHashMap<>();

        // Step 4: Put the sorted entries into the new LinkedHashMap
        for (Map.Entry<String, Double> entry : entryList) {
            sortedHashMap.put(entry.getKey(), entry.getValue());
        }

        return sortedHashMap;
    }

    public void populateSmallestInventoryPerProductFile() {
        FileOutputStream fos = null;
        try {
            fos = mContext.openFileOutput("smallestInventoryPerProduct.txt", MODE_PRIVATE);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        HashMap<String, Double> map = new HashMap<>();

        map.put("0.5 MWB", 1500.0);
        map.put("1.5 MWB", 1500.0);
        map.put("32oz Hydra Jet", 1500.0);
        map.put("64oz Hydra Jet", 1500.0);
        map.put("128oz Hydra Jet", 1500.0);

        String content = map.toString();
        try {
            fos.write(content.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // resetInventory() FOR TESTING PURPOSES ONLY

    public void resetInventory() {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("MWB tube", 3);
        map.put("MWB slider", 3);
        map.put("MWB screws", 1000);
        map.put("Bottle caps", 1000);
        map.put("0.5 bottles", 1000);
        map.put("0.5 crosses", 1000);
        map.put("MWB o-ring", 1000);
        map.put("MWB end cap", 1000);
        map.put("HJ tube", 3);
        map.put("HJ grommet", 1000);
        map.put("HJ screw", 1000);
        map.put("32oz container", 1000);
        map.put("32oz wedge", 1000);
        map.put("HJ red cap", 1000);
        map.put("32oz lid", 1000);
        map.put("Large HJ lid", 1000);
        map.put("64oz container", 1000);
        map.put("64oz wedge", 1000);
        map.put("128oz container", 1000);
        map.put("128oz wedge", 1000);
        map.put("1.5 bottles", 1000);
        map.put("1.5 crosses", 1000);

        createFileIfAbsent(FILE_NAME);
        String content = map.toString();
        try (FileOutputStream fos = mContext.openFileOutput(FILE_NAME, MODE_PRIVATE)) {
            fos.write(content.getBytes());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Toast.makeText(mContext, "Inventory reset!", Toast.LENGTH_LONG).show();

    }
}
