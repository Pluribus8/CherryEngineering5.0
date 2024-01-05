package com.kj.cherryengineering20.employees;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.kj.cherryengineering20.R;
import com.kj.cherryengineering20.bottomBarFragments.SharedViewModel;
import com.kj.cherryengineering20.inventoryFragments.EnterAmountDialogFragment;

import java.util.ArrayList;
import java.util.List;

public class ModifyCommissionRateFragment extends Fragment {

    SharedViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_modify_commission_rate, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        EmployeeDatabase edb = new EmployeeDatabase(requireContext());

        AutoCompleteTextView employeeDropdown = view.findViewById(R.id.employee_list_ACTV);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), R.layout.list_item, new EmployeeDatabase(requireContext()).getEmployeeNames());

        employeeDropdown.setAdapter(adapter);

        String[] selected = new String[1];
        employeeDropdown.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selected[0] = adapterView.getItemAtPosition(i).toString();
                viewModel.setText("Enter new commission rate for " + selected[0] + (" (ex: 85 = 85%)"));
                viewModel.setName(selected[0]);
                viewModel.setInventoryModificiationStatus(true);
                viewModel.setCommissionRateChangeStatus(true);
                new EnterAmountDialogFragment().show(requireActivity().getSupportFragmentManager(), "EnterAmountFragment");
            }
        });

        Button enterButton = view.findViewById(R.id.commission_rate_enter_button);
        enterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}