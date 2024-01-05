package com.kj.cherryengineering20.payroll;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.kj.cherryengineering20.MainActivity;
import com.kj.cherryengineering20.R;
import com.kj.cherryengineering20.employees.Employee;
import com.kj.cherryengineering20.employees.EmployeeDatabase;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class PayrollMenu extends AppCompatActivity {

    private static final String FILE_NAME = "employees.txt";
    private String nameSelection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payroll_menu);

        EmployeeDatabase db = new EmployeeDatabase(this);

        Spinner employeeSpinner = findViewById(R.id.employee_selection_payroll_spinner);
        List<String> employees = db.getEmployeeNames();
        ArrayAdapter<Employee> employeeAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, employees);
        employeeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        employeeSpinner.setAdapter(employeeAdapter);

        Button currentPayrollButton = (Button) findViewById(R.id.payroll_current);
        currentPayrollButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nameSelection = (String) employeeSpinner.getSelectedItem();
                openCurrentPayroll();
            }
        });

        Button addEmployee = (Button) findViewById(R.id.add_employee);
        addEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText text = (EditText) findViewById(R.id.employee_name_text);
                String name = text.getText().toString();
                Employee employee = new Employee(name);
                addEmployeeToFile(employee);
                db.createEmployeePayrollFilesIfAbsent(employee.getName());
                returnToHomeScreen();
            }
        });
    }

    public void openCurrentPayroll() {
        Intent intent = new Intent(this, CurrentPayroll.class);
        intent.putExtra("nameSelected", nameSelection);
        startActivity(intent);
    }

    public void openPayrollHistory(View view) {
        startActivity(new Intent(this, PayrollHistory.class));
    }

    public void addEmployeeToFile(Employee employee) {
        File file = new File(getFilesDir(), FILE_NAME);
        //file.delete();
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        try {
            FileOutputStream fos = openFileOutput(FILE_NAME, MODE_APPEND);
            String content = employee.toString() + "\n";
            fos.write(content.getBytes());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void returnToHomeScreen() {
        startActivity(new Intent(this, MainActivity.class));
    }
}