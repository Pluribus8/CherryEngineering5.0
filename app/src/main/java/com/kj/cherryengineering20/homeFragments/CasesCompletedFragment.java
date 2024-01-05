package com.kj.cherryengineering20.homeFragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.kj.cherryengineering20.CompletedCaseQuantityMenu.PointFiveWaterBottle;
import com.kj.cherryengineering20.R;
import com.kj.cherryengineering20.bottomBarFragments.HomeFragment;
import com.kj.cherryengineering20.bottomBarFragments.SharedViewModel;
import com.kj.cherryengineering20.dialogFragments.ErrorDialogFragment;
import com.kj.cherryengineering20.employees.Employee;
import com.kj.cherryengineering20.employees.EmployeeDatabase;
import com.kj.cherryengineering20.inventoryFragments.EnterAmountDialogFragment;
import com.kj.cherryengineering20.product.HydraJet;
import com.kj.cherryengineering20.product.InventoryDatabase;
import com.kj.cherryengineering20.product.Product;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import java.text.DecimalFormat;


public class CasesCompletedFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private static final String FILE_NAME = "inventory.txt";
    String typeSelection;
    double quantitySelection;
    Product productSelection;

    int multiplier;
    Employee employeeSelection;
    String employeeName;
    private EmployeeDatabase employeeDb;
    private InventoryDatabase inventoryDb;

    private SharedViewModel viewModel;
    private TextView textView;
    private View view;
    private Context myContext;
    private final DecimalFormat decfor = new DecimalFormat("0.00");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        myContext = viewModel.getViewModelContext();
        inventoryDb = new InventoryDatabase(myContext);
        employeeDb = new EmployeeDatabase(myContext);
        return inflater.inflate(R.layout.fragment_cases_completed, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //DELETE NEXT LINES OF CODE

        employeeName = viewModel.getText().getValue().toString();

        multiplier = 1;

        this.view = view;
        textView = view.findViewById(R.id.name_header);
        textView.setText(employeeName);

        employeeSelection = employeeDb.getEmployee(employeeName);

        TextView nameView = view.findViewById(R.id.name_header);
        nameView.setText(employeeName);

//        Button backBtn = view.findViewById(R.id.back_button);
//        backBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                requireActivity().onBackPressed();
//            }
//        });

        //instantiates type spinner
        Spinner typeSpinner = view.findViewById(R.id.type_spinner);
        List<String> quantityTypes = new ArrayList<>();
        quantityTypes.add("<Select Type>");
        quantityTypes.add("Case");
        quantityTypes.add("Individual");
        ArrayAdapter<String> typeAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, quantityTypes);
        typeSpinner.setAdapter(typeAdapter);
        typeSpinner.setOnItemSelectedListener(this);

        Spinner spinner = view.findViewById(R.id.caseSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(requireContext(), R.array.numbers, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        Spinner productSpinner = view.findViewById(R.id.product_spinner);
        List<Product> products = new ArrayList<>();
        products.add(new Product("<Select Product>", -1));
        products.add(new Product("0.5 MWB", 41));
        products.add(new Product("1.5 MWB", 41));
        products.add(new Product("2.2 MWB", 41));
        products.add(new Product("32oz HJ", 71));
        products.add(new Product("64oz HJ", 71));
        products.add(new Product("128oz HJ", 71));
        ArrayAdapter<Product> productAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, products);
        productAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        productSpinner.setAdapter(productAdapter);

        String oldText = viewModel.getText().getValue().toString();

        Button submitButton = view.findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                typeSelection = typeSpinner.getSelectedItem().toString();
                productSelection = (Product) productSpinner.getSelectedItem();
                String quantity = (String) spinner.getSelectedItem();

                if (!(productSelection.getName().equals("<Select Product>")) && !(quantity.equals("<Select Quantity>")) && !(typeSelection.equals("<Select Type>"))) {
                    if (quantity.equals("Half-case")) {
                        quantitySelection = 0.5;
                    } else if (quantity.equals("Enter custom amount")) {
                        //DEPRECATED
                    } else
                        quantitySelection = Integer.parseInt((String) spinner.getSelectedItem());

                    try {
                        if (typeSelection.equals("Case")) {
                            multiplier = 16;
                            updateEmployeePayroll();
                        }
                        save();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    returnToHomeScreen();
                } else {
                    viewModel.setErrorDialogString("Select a valid Type, Quantity, and Product");
                    ErrorDialogFragment fragment = new ErrorDialogFragment();
                    fragment.show(requireActivity().getSupportFragmentManager(), "ErrorDialog");
                }

            }
        });

        viewModel.setText(oldText);

        //DEPRECATED: custom amount button
//        Button customAmountButton = view.findViewById(R.id.button_custom_amount);
//        customAmountButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                viewModel.setText("Enter number of completed items:");
//                EnterAmountDialogFragment dialogFragment = new EnterAmountDialogFragment();
//                dialogFragment.show(requireActivity().getSupportFragmentManager(), "individualItemEntry");
//            }
//        });

        Button viewMyPayroll = view.findViewById(R.id.view_my_payroll_button);
        viewMyPayroll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft = getParentFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_container, new ViewMyPayrollFragment());
                ft.commit();
            }
        });
    }

    public void save() throws IOException {
        inventoryDb.updateInventory(multiplier, productSelection, quantitySelection);
    }

    public void updateEmployeePayroll() {
        String fileName = employeeName + "Payroll.txt";

        File file = new File(requireContext().getFilesDir(), fileName);
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
            FileOutputStream fos = requireContext().openFileOutput(fileName, Context.MODE_APPEND);
            fos.write(content.getBytes());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void returnToHomeScreen() {
        FragmentTransaction fr = getParentFragmentManager().beginTransaction();
        fr.replace(R.id.fragment_container, new HomeFragment());
        fr.commit();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}