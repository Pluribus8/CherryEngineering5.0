package com.kj.cherryengineering20.payroll;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.kj.cherryengineering20.R;
import com.kj.cherryengineering20.bottomBarFragments.SharedViewModel;
import com.kj.cherryengineering20.employees.EmployeeDatabase;


public class ViewPayrollHistoryFragment extends Fragment {

    EmployeeDatabase edb;
    SharedViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        edb = new EmployeeDatabase(requireContext());
        viewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        return inflater.inflate(R.layout.fragment_view_payroll_history, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView textViewPayrollHistory = view.findViewById(R.id.view_payroll_history_textview);
        textViewPayrollHistory.setText(edb.getEmployeePayrollHistory(viewModel.getText().getValue().toString()));
        textViewPayrollHistory.setMovementMethod(new ScrollingMovementMethod());
    }
}