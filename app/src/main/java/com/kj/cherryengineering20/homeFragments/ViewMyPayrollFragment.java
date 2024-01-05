package com.kj.cherryengineering20.homeFragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.kj.cherryengineering20.R;
import com.kj.cherryengineering20.bottomBarFragments.HomeFragment;
import com.kj.cherryengineering20.bottomBarFragments.SharedViewModel;
import com.kj.cherryengineering20.dialogFragments.ConfirmationDialogFragment;
import com.kj.cherryengineering20.employees.EmployeeDatabase;
import com.kj.cherryengineering20.payroll.ViewPayrollHistoryFragment;

public class ViewMyPayrollFragment extends Fragment {

    private SharedViewModel viewModel;
    private EmployeeDatabase employeeDb;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        employeeDb = new EmployeeDatabase(requireContext());
        return inflater.inflate(R.layout.fragment_view_my_payroll, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView payrollTextView = view.findViewById(R.id.current_payroll_display);
        String employeeName = viewModel.getText().getValue().toString();
        String payrollDisplay = employeeDb.getEmployeeCurrentPayroll(employeeName);

        Button flexButton = view.findViewById(R.id.button_flex);

        if (employeeName.equals("Christopher")) {
            payrollTextView.setText(employeeDb.getAllEmployeeTotals());
            flexButton.setText("Submit payroll");
            flexButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    viewModel.setText("\nSubmit all payroll? This will reset all employee payrolls");
                    ConfirmationDialogFragment fragment = new ConfirmationDialogFragment();
                    fragment.show(requireActivity().getSupportFragmentManager(), "confirm_payroll_submission");
                }
            });
        } else {
            payrollTextView.setText(payrollDisplay);
            flexButton.setText("Return home");
            flexButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FragmentTransaction ft = getParentFragmentManager().beginTransaction();
                    ft.replace(R.id.fragment_container, new HomeFragment());
                    ft.commit();
                }
            });
        }

        Button buttonPayrollHistory = view.findViewById(R.id.button_payroll_history);
        buttonPayrollHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft = getParentFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_container, new ViewPayrollHistoryFragment());
                ft.commit();
            }
        });
    }
}