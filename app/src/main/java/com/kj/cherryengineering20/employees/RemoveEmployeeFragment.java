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
import android.widget.Toast;

import com.kj.cherryengineering20.R;
import com.kj.cherryengineering20.bottomBarFragments.SharedViewModel;
import com.kj.cherryengineering20.dialogFragments.ErrorDialogFragment;

import java.util.List;

public class RemoveEmployeeFragment extends Fragment {

    SharedViewModel viewModel;
    AutoCompleteTextView autoCompleteTextView;
    ArrayAdapter<String> adapterItems;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_remove_employee, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        EmployeeDatabase db = new EmployeeDatabase(view.getContext());
        List<String> names = db.getEmployeeNames();

        autoCompleteTextView = view.findViewById(R.id.auto_complete_text);
        adapterItems = new ArrayAdapter<>(requireContext(), R.layout.list_item, names);

        autoCompleteTextView.setAdapter(adapterItems);

        final String[] toBeRemoved = {""};
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                toBeRemoved[0] = adapterView.getItemAtPosition(i).toString();
            }
        });

        Button removeButton = view.findViewById(R.id.remove_employee_final);
        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (db.getEmployeeTotal(toBeRemoved[0]) == 0) {
                    db.removeEmployee(toBeRemoved[0]);
                    adapterItems.remove(toBeRemoved[0]);
                    autoCompleteTextView.setAdapter(adapterItems);
                    autoCompleteTextView.setText("");
                    Toast.makeText(getContext(), toBeRemoved[0] + " removed", Toast.LENGTH_SHORT).show();
                } else {
                    viewModel.setErrorDialogString("\n" + toBeRemoved[0] + " has unsubmitted payroll");
                    ErrorDialogFragment fragment = new ErrorDialogFragment();
                    fragment.show(requireActivity().getSupportFragmentManager(), "ErrorDialog");
                }
            }
        });
    }
}