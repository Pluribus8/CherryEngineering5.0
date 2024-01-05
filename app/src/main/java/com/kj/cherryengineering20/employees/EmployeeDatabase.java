package com.kj.cherryengineering20.employees;

import android.content.Context;

import com.kj.cherryengineering20.CompletedCaseQuantityMenu.PointFiveWaterBottle;
import com.kj.cherryengineering20.product.HydraJet;
import com.kj.cherryengineering20.product.Product;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class EmployeeDatabase {

    private Context mContext;
    private String FILE_NAME = "employees.txt";
    private double total;
    DecimalFormat decfor = new DecimalFormat("#,###.00");

    public EmployeeDatabase(Context context) {
        mContext = context;
        total = 0;
    }

    public void addEmployee(Employee employee) {
        createEmployeesFileIfAbsent();
        try {
            FileOutputStream fos = mContext.openFileOutput(FILE_NAME, mContext.MODE_APPEND);
            String content = employee.toString() + "\n";
            fos.write(content.getBytes());

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeEmployee(String name) {
        List<Employee> employees = getEmployees();

        for (Employee e: employees) {
            if (e.getName().equals(name)) {
                employees.remove(e);
                break;
            }
        }

        replaceEmployees(employees);
    }

    public Employee getEmployee(String name) {
        List<Employee> employees = getEmployees();
        for (Employee e: employees) {
            if (e.getName().equalsIgnoreCase(name)) {
                return e;
            }
        }
        return null;
    }

    public List<Employee> getEmployees() {
        List<Employee> employees = new ArrayList<>();

        // 66 - 74 can probably be replaced with createFile method call

        File file = new File(mContext.getFilesDir(), FILE_NAME);

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        try {
            FileInputStream fis = mContext.openFileInput("employees.txt");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            String line;

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                employees.add(new Employee(parts[0], Double.parseDouble(parts[1])));
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        return employees;
    }

    public void changeEmployeeCommissionRate(String employeeName, double newRate) {
        List<Employee> employees = getEmployees();

        for (Employee e: employees) {
            if (e.getName().equals(employeeName)) {
                e.setCommissionRate(newRate);
                break;
            }
        }

        replaceEmployees(employees);
    }

    public void replaceEmployees(List<Employee> employees) {

        deleteAllEmployees();
        createEmployeesFileIfAbsent();

        try {

            FileOutputStream fos = mContext.openFileOutput(FILE_NAME, mContext.MODE_APPEND);

            for (Employee e: employees) {
                String content = e.toString() + "\n";
                fos.write(content.getBytes());
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> getEmployeeNames() {
        List<Employee> employees = getEmployees();
        List<String> names = new ArrayList<>();

        for (Employee e: employees) {
            names.add(e.getName());
        }

        return names;
    }

    public void updateEmployeePayroll(String employeeName, Product productSelection, double quantitySelection, Employee employeeSelection) {
        String fileName = employeeName + "Payroll.txt";

        createEmployeePayrollFilesIfAbsent(employeeName);
        createEmployeesFileIfAbsent();

        File file = new File(mContext.getFilesDir(), fileName);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        Product product;
        if (productSelection.getName().equals("0.5 MWB") || productSelection.getName().equals("1.5 MWB") || productSelection.getName().equals("2.2 MWB"))
            product = new PointFiveWaterBottle();
        else
            product = new HydraJet();

        double amount = product.getPayrollPricePerCase() * quantitySelection * employeeSelection.getCommissionRate();
        String content = employeeName + " completed " + quantitySelection + " cases of " + productSelection + " on " + Calendar.getInstance().getTime() + " at " + employeeSelection.getCommissionRate()
                + " commission rate for $" + decfor.format(amount) + "\n";

        try {
            FileOutputStream fos = mContext.openFileOutput(fileName, Context.MODE_APPEND);
            fos.write(content.getBytes());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getEmployeeCurrentPayroll(String name) {
        createEmployeePayrollFilesIfAbsent(name);

        try {
            FileInputStream fis = mContext.openFileInput(name + "Payroll.txt");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder builder = new StringBuilder();
            String line = br.readLine();
            String[] lineArr;
            String amount;

            if (line == null) {
                builder.append("\n\n\tNo payroll activity");
            } else {
                while (line != null) {
                    //retrieving each amount in line

                    lineArr = line.split(" ");
                    amount = lineArr[lineArr.length - 1].replace("$", "");
                    total += Double.parseDouble(amount);

                    builder.append(line + "\n");

                    line = br.readLine();

                }
            }

            builder.append("\n\n\t").append("Total = " + decfor.format(total));

            return builder.toString();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public double getEmployeeTotal(String name) {
        total = 0;
        getEmployeeCurrentPayroll(name);
        return total;
    }

    public String getAllEmployeeTotals() {
        List<String> employees = getEmployeeNames();
        StringBuilder builder = new StringBuilder();
        String amount = "";
        total = 0;

        for (String employee: employees) {
            if (!employee.equals("Christopher")) {
                getEmployeeCurrentPayroll(employee);

                if (total == 0)
                    amount = "0";
                else
                    amount = decfor.format(total);

                builder.append(employee).append(" = ").append(amount).append("\n");
                total = 0;
            }
        }

        return builder.toString();
    }

    public String getEmployeePayrollHistory(String name) {
        createEmployeePayrollFilesIfAbsent(name);

        FileInputStream fis = null;
        try {
            fis = mContext.openFileInput(name + "PayrollHistory.txt");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            String line = br.readLine();
            StringBuilder sb = new StringBuilder();

            if (line != null) {
                while (line != null) {
                    sb.append(line).append("\n");
                    line = br.readLine();
                }
                return sb.toString();
            }
            return "No payroll history";

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void migrateAllEmployeePayrolls() {
        List<String> employeeNames = getEmployeeNames();
        for (String employeeName: employeeNames) {
            moveCurrentToHistory(employeeName);
        }
    }

    public void moveCurrentToHistory(String name) {
        String toMove = getEmployeeCurrentPayroll(name) + "\n\n";
        String fileName = name + "PayrollHistory.txt";
        File file = new File(mContext.getFilesDir(), fileName);

        try {
            FileOutputStream fos = mContext.openFileOutput(fileName, mContext.MODE_APPEND);
            if (!toMove.contains("No payroll activity"))
                fos.write(toMove.getBytes());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        clearCurrentPayroll(name);
    }

    public void clearCurrentPayroll(String name) {
        File file = new File(mContext.getFilesDir(), name + "Payroll.txt");
        file.delete();
    }

    public void createEmployeesFileIfAbsent() {
        File file = new File(mContext.getFilesDir(), FILE_NAME);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void createEmployeePayrollFilesIfAbsent(String name) {
        File file = new File(mContext.getFilesDir(), name + "Payroll.txt");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        File historyFile = new File(mContext.getFilesDir(), name + "PayrollHistory.txt");
        if (!historyFile.exists()) {
            try {
                historyFile.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void deleteAllPayrollHistories() {
        List<String> employeeNames = getEmployeeNames();
        for (String name: employeeNames) {
            File file = new File(mContext.getFilesDir(), name + "PayrollHistory.txt");
            file.delete();
        }
    }

    public void deleteAllEmployees() {
        File file = new File(mContext.getFilesDir(), FILE_NAME);
        file.delete();
    }
}
