package com.kj.cherryengineering20.dialogFragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.kj.cherryengineering20.R;
import com.kj.cherryengineering20.bottomBarFragments.HomeFragment;
import com.kj.cherryengineering20.bottomBarFragments.SharedViewModel;
import com.kj.cherryengineering20.employees.EmployeeDatabase;
import com.kj.cherryengineering20.payroll.EmailManager;

//THIS CLASS TO ONLY BE USED TO SUBMIT PAYROLL

public class ConfirmationDialogFragment extends DialogFragment {

    SharedViewModel viewModel;
    EmployeeDatabase edb;

    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        edb = new EmployeeDatabase(requireContext());

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater layoutInflater = requireActivity().getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.fragment_confirmation_dialog, null);

        TextView information = view.findViewById(R.id.dialog_confirmation);
        information.setText(viewModel.getText().getValue().toString());

        builder.setView(view)
                .setTitle("Confirm Submission")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ConfirmationDialogFragment.this.getDialog().cancel();
                    }
                })

                .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        EmailManager manager = new EmailManager();
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                manager.emailPayroll(requireContext());
                            }
                        }).start();

                        edb.migrateAllEmployeePayrolls();

                        Toast.makeText(requireContext(), "Payroll sent!", Toast.LENGTH_SHORT).show();
                        FragmentTransaction ft = getParentFragmentManager().beginTransaction();
                        ft.replace(R.id.fragment_container, new HomeFragment());
                        ft.commit();
                    }
                });

        return builder.create();
    }

    //DEPRECATED: used to open email application to send payroll manually

//    public void emailPayroll() {
//        EmployeeDatabase db = new EmployeeDatabase(requireContext());
//        String[] TO = {"emailForTestingPurposes123@yahoo.com"};
//
//        Intent emailIntent = new Intent(Intent.ACTION_SEND);
//        emailIntent.setType("text/plain");
//        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
//        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Payroll");
//        emailIntent.putExtra(Intent.EXTRA_TEXT, db.getAllEmployeeTotals());
//
//        startActivity(Intent.createChooser(emailIntent, "Send mail..."));
//
//        db.migrateAllEmployeePayrolls();
//    }

}