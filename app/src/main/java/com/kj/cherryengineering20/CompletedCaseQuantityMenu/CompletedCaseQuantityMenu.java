package com.kj.cherryengineering20.CompletedCaseQuantityMenu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.kj.cherryengineering20.MainActivity;
import com.kj.cherryengineering20.R;
import com.kj.cherryengineering20.employees.Employee;
import com.kj.cherryengineering20.employees.EmployeeDatabase;
import com.kj.cherryengineering20.product.InventoryDatabase;
import com.kj.cherryengineering20.product.Product;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class CompletedCaseQuantityMenu extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final String FILE_NAME = "inventory.txt";
    int quantitySelection;
    Product productSelection;
    Employee employeeSelection;
    String employeeName;
    EditText mEditText;
    private EmployeeDatabase employeeDb = new EmployeeDatabase(this);
    private InventoryDatabase inventoryDb = new InventoryDatabase(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completed_case_quantity_menu);

        employeeName = getIntent().getStringExtra("name");
        employeeSelection = employeeDb.getEmployee(employeeName);

        TextView nameView = findViewById(R.id.name_header);
        nameView.setText(employeeName);

        //commented out for diagnostics - app crashing on device but not in android studio 1/5/24
        //instantiates type spinner
//        Spinner typeSpinner = findViewById(R.id.type_spinner);
//        List<String> quantityTypes = new ArrayList<>();
//        quantityTypes.add("Cases");
//        quantityTypes.add("Individual products");
//        ArrayAdapter<String> typeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, quantityTypes);
//        typeSpinner.setAdapter(typeAdapter);

        //instantiates quantity spinner
        Spinner spinner = findViewById(R.id.caseSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.numbers, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        //instantiates product spinner
        Spinner productSpinner = findViewById(R.id.product_spinner);
        List<Product> products = new ArrayList<>();
        products.add(new Product("0.5 MWB", 41));
        products.add(new Product("1.5 MWB", 41));
        products.add(new Product("32oz HJ", 71));
        products.add(new Product("64oz HJ", 71));
        products.add(new Product("128oz HJ", 71));
        ArrayAdapter<Product> productAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, products);
        productAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        productSpinner.setAdapter(productAdapter);

//        Spinner employeeSpinner = findViewById(R.id.employee_spinner);
//        List<String> employees = getEmployees();
//        ArrayAdapter<Employee> employeeAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, employees);
//        employeeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        employeeSpinner.setAdapter(employeeAdapter);


        Button submitButton = (Button) findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quantitySelection = Integer.parseInt((String) spinner.getSelectedItem());
                productSelection = (Product) productSpinner.getSelectedItem();
//                try {
//                    load();
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
                try {
                    save();
                    updateEmployeePayroll();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                returnToHomeScreen();
            }
        });
    }

    public void load() throws IOException {
        FileInputStream fis = null;

        fis = openFileInput("inventory.txt");
        InputStreamReader isr = new InputStreamReader(fis);
        BufferedReader br = new BufferedReader(isr);
        StringBuilder sb = new StringBuilder();
        String text;

        while ((text = br.readLine()) != null) {
            sb.append(text).append("\n");
        }

        mEditText.setText(sb.toString());

        if (fis != null) {
            fis.close();
        }

    }

    public void save() throws IOException {
        //resetFile();
        //inventoryDb.updateInventory(productSelection, quantitySelection);
    }

    //for testing purposes only
    public void resetInventory() {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("MWB tube", 500);
        map.put("MWB Slider", 2000);
        map.put("MWB screws", 200);
        map.put("Bottle caps", 400);
        map.put("0.5 crosses", 1000);
        map.put("MWB o-ring", 600);
        map.put("MWB end cap", 300);

        File file = new File(this.getFilesDir(), FILE_NAME);
        String content = map.toString();
        try (FileOutputStream fos = openFileOutput(FILE_NAME, MODE_PRIVATE)) {
            fos.write(content.getBytes());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Toast.makeText(this, "Saved to " + getFilesDir() + "/inventory.txt", Toast.LENGTH_LONG).show();

    }

    public void updateEmployeePayroll() {
        String fileName = employeeName + "Payroll.txt";
        File file = new File(this.getFilesDir(), fileName);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        //TODO: find a way to replace content to be specific to the employee and product
        PointFiveWaterBottle product = new PointFiveWaterBottle();
        String content = employeeName + " completed " + quantitySelection + " cases of " + productSelection + " on " + Calendar.getInstance().getTime() + " at " + employeeSelection.getCommissionRate()
                + " commission rate for $" + product.getPayrollPricePerCase() * quantitySelection * employeeSelection.getCommissionRate() + "\n";
        try {
            FileOutputStream fos = openFileOutput(fileName, MODE_APPEND);
            fos.write(content.getBytes());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> getEmployees() {
        List<String> employees = new ArrayList<>();

        try {
            FileInputStream fis = openFileInput("employees.txt");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            String line;

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                employeeSelection = new Employee(parts[0], Double.parseDouble(parts[1]));
                employees.add(parts[0]);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return employees;
    }

    public void returnToHomeScreen() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String text = adapterView.getItemAtPosition(i).toString();
        Toast.makeText(adapterView.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void goBack(View view) {
        finish();
    }
}