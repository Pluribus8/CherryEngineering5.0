package com.kj.cherryengineering20.inventoryFragments;

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
import android.widget.ListView;

import com.kj.cherryengineering20.R;
import com.kj.cherryengineering20.bottomBarFragments.SharedViewModel;
import com.kj.cherryengineering20.product.InventoryDatabase;

import java.util.List;

public class AddInventoryFragment extends Fragment {

    SharedViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        return inflater.inflate(R.layout.fragment_add_inventory, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        InventoryDatabase id = viewModel.getInventoryDatabase();
        List<String> products = id.getInventoryProductNames();

        ListView productListView = view.findViewById(R.id.product_list_view);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, products);
        productListView.setAdapter(adapter);
        productListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                viewModel.setText(products.get(i));

                EnterAmountDialogFragment myDialog = new EnterAmountDialogFragment();
                myDialog.show(requireActivity().getSupportFragmentManager(), "EnterAmountFragment");
//                FragmentTransaction ft = getParentFragmentManager().beginTransaction();
//                ft.replace(R.id.fragment_container, new EnterAmountFragment()).addToBackStack(null);
//                ft.commit();
            }
        });
    }
}