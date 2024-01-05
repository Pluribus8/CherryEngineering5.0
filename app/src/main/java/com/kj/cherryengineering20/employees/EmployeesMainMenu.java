package com.kj.cherryengineering20.employees;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.kj.cherryengineering20.R;

public class EmployeesMainMenu extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_employees_main_menu, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button addEmployeeButton = view.findViewById(R.id.add_employee_button);
        addEmployeeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayThisFragment();
                getView().setFocusableInTouchMode(true);
                getView().requestFocus();
                FragmentTransaction ft = getParentFragmentManager().beginTransaction();
                ft.add(R.id.fragment_container, new EmployeeNameInput()).addToBackStack(null);
                ft.commit();
            }
        });

        Button removeEmployeeButton = view.findViewById(R.id.remove_employee_button);
        removeEmployeeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayThisFragment();
                FragmentTransaction ft = getParentFragmentManager().beginTransaction();
                ft.add(R.id.fragment_container, new RemoveEmployeeFragment()).addToBackStack(null);
                ft.commit();
            }
        });

        Button modifyCommissionRateButton = view.findViewById(R.id.modify_commission_rate_button);
        modifyCommissionRateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayThisFragment();
                FragmentTransaction ft = getParentFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_container, new ModifyCommissionRateFragment()).addToBackStack(null);
                ft.commit();
            }
        });
    }

    public void displayThisFragment() {
        FragmentManager fm = getParentFragmentManager();
        fm.popBackStack();
        FragmentTransaction ft = getParentFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, new EmployeesMainMenu());
        ft.commit();
    }
}