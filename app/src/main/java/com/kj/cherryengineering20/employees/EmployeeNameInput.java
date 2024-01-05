package com.kj.cherryengineering20.employees;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.kj.cherryengineering20.R;
import com.kj.cherryengineering20.bottomBarFragments.SharedViewModel;
import com.kj.cherryengineering20.dialogFragments.ErrorDialogFragment;

public class EmployeeNameInput extends Fragment {

    SharedViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        return inflater.inflate(R.layout.fragment_employee_name_input, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        EmployeeDatabase edb = new EmployeeDatabase(viewModel.getViewModelContext());

        getView().requestFocus();

        EditText name = view.findViewById(R.id.name_input_editText);

        Button addInput = view.findViewById(R.id.add_input_button);
        addInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Employee employee = new Employee(name.getText().toString());
                if (!edb.getEmployeeNames().contains(name.getText().toString())) {
                    edb.addEmployee(employee);
                    Toast.makeText(view.getContext(), employee.getName() + " added", Toast.LENGTH_SHORT).show();
                } else {
                    ErrorDialogFragment errorDialog = new ErrorDialogFragment();
                    viewModel.setErrorDialogString("Employee name already exists");
                    errorDialog.show(requireActivity().getSupportFragmentManager(), "duplicate employee tag");
                }
                name.setText("");
            }
        });
    }
}