package com.kj.cherryengineering20.bottomBarFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.kj.cherryengineering20.R;
import com.kj.cherryengineering20.product.InventoryDatabase;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class InventoryFragment extends Fragment {

    DecimalFormat decfor = new DecimalFormat("##,###.##");

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_inventory, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        try {
            displayValues(view);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void displayValues(View view) throws IOException {

        List<Double> values = new ArrayList<>();

        StringBuilder sb = new StringBuilder();
        StringBuilder amounts = new StringBuilder();

        InventoryDatabase id = new InventoryDatabase(getContext());
        HashMap<String, Double> map = id.getInventoryAsHashmap();

        LinkedHashMap<String, Double> sortedHashMap = id.sortHashMap(map);

        for (String s: sortedHashMap.keySet()) {
            s = s.trim();
            sb.append(s).append("\n");
            amounts.append(decfor.format(sortedHashMap.get(s))).append("\n");
        }

        TextView label = view.findViewById(R.id.inventory_label);
        label.setText(sb.toString());
        TextView amount = view.findViewById(R.id.inventory_amount);
        amount.setText(amounts.toString());
    }
}
