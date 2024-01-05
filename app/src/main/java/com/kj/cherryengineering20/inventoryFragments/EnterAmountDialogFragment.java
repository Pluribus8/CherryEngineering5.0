package com.kj.cherryengineering20.inventoryFragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.kj.cherryengineering20.R;
import com.kj.cherryengineering20.bottomBarFragments.HomeFragment;
import com.kj.cherryengineering20.bottomBarFragments.SharedViewModel;
import com.kj.cherryengineering20.employees.EmployeeDatabase;
import com.kj.cherryengineering20.product.InventoryDatabase;

public class EnterAmountDialogFragment extends DialogFragment {

    SharedViewModel viewModel;
    InventoryDatabase inventoryDb;
    String itemToAdd;
    EmployeeDatabase edb;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        viewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        inventoryDb = new InventoryDatabase(requireContext());
        edb = new EmployeeDatabase(viewModel.getViewModelContext());

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater layoutInflater = requireActivity().getLayoutInflater();

        View view = layoutInflater.inflate(R.layout.fragment_enter_amount, null);

        itemToAdd = viewModel.getText().getValue().toString();

        String positiveButtonText = "Enter";
        if (itemToAdd.equals("Enter number of completed items:"))
            positiveButtonText = "Enter";

        builder.setView(view)
                .setTitle(itemToAdd)
                .setPositiveButton(positiveButtonText, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        EditText inventoryToBeAdded = view.findViewById(R.id.dialog_inventory_quantity_input);
                        //if (TextUtils.isDigitsOnly(inventoryToBeAdded.getText()))
                        double numToBeAdded = Double.parseDouble(inventoryToBeAdded.getText().toString());
                        if (!viewModel.getInventoryModificationStatus())
                            numToBeAdded *= -1;
                        inventoryDb.addInventory(itemToAdd, numToBeAdded);
                        viewModel.setDoubleValue(numToBeAdded);

                        //next block is to change commission rate
                        if (viewModel.getCommissionRateChangeStatus()) {
                            edb.changeEmployeeCommissionRate(viewModel.getName(), numToBeAdded / 100);
                            Toast.makeText(viewModel.getViewModelContext(), viewModel.getName() + "'s commission changed to " + (int) numToBeAdded + "%", Toast.LENGTH_SHORT).show();
                            FragmentTransaction ft = getParentFragmentManager().beginTransaction();
                            ft.replace(R.id.fragment_container, new HomeFragment());
                            ft.commit();
                            viewModel.setCommissionRateChangeStatus(false);
                        }

                        //Toast.makeText(requireContext(), ">adding inventory<", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        EnterAmountDialogFragment.this.getDialog().cancel();
                    }
                });


        return builder.create();
    }
}