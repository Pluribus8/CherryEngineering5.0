package com.kj.cherryengineering20.inventoryFragments;

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
import android.widget.Toast;

import com.kj.cherryengineering20.CompletedCaseQuantityMenu.PointFiveWaterBottle;
import com.kj.cherryengineering20.R;
import com.kj.cherryengineering20.bottomBarFragments.InventoryFragment;
import com.kj.cherryengineering20.bottomBarFragments.SharedViewModel;
import com.kj.cherryengineering20.fileManagers.FileManager;
import com.kj.cherryengineering20.payroll.EmailManager;
import com.kj.cherryengineering20.product.HydraJet128;
import com.kj.cherryengineering20.product.HydraJet32;
import com.kj.cherryengineering20.product.HydraJet64;
import com.kj.cherryengineering20.product.InventoryDatabase;
import com.kj.cherryengineering20.product.OnePointFiveMwb;
import com.kj.cherryengineering20.product.Product;
import com.kj.cherryengineering20.product.TwoPointTwoMwb;

import java.text.DecimalFormat;
import java.util.HashMap;

public class InventoryMainMenu extends Fragment {

    SharedViewModel viewModel;
    PointFiveWaterBottle pointFive = new PointFiveWaterBottle();
    OnePointFiveMwb onePointFive = new OnePointFiveMwb();
    TwoPointTwoMwb twoPointTwo = new TwoPointTwoMwb();
    HydraJet32 hj32 = new HydraJet32();
    HydraJet64 hj64 = new HydraJet64();
    HydraJet128 hj128 = new HydraJet128();
    HashMap<String, Double> hj32Hashmap = new HydraJet32().get32OzHjComponents();
    HashMap<String, Double> pointFiveHashmap = new PointFiveWaterBottle().getPointFiveComponents();
    DecimalFormat decFor = new DecimalFormat("###,###.##");

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        return inflater.inflate(R.layout.fragment_inventory_main_menu, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        InventoryDatabase inventoryDatabase = new InventoryDatabase(requireContext());
        inventoryDatabase.populateSmallestInventoryPerProductFile();
        FileManager fileManager = new FileManager(requireContext());

        TextView productNames = view.findViewById(R.id.product_name_inventory);
        TextView productQuantities = view.findViewById(R.id.product_quantity_inventory);
        StringBuilder nameBuilder = new StringBuilder();
        StringBuilder quantityBuilder = new StringBuilder();

        HashMap<String, Double> retailProducts = fileManager.getInventoryFileAsHashmap("retailProducts.txt");
        HashMap<String, Double> sortedRetailMap = inventoryDatabase.sortHashMap(retailProducts);

        HashMap<String, Double> inventoryHashmap = inventoryDatabase.getInventoryAsHashmap();

        double pointFiveSmallest = 5000;
        double onePointFiveSmallest = 5000;
        double twoPointTwoSmallest = 5000;
        double hj32Smallest = 5000;
        double hj64Smallest = 5000;
        double hj128Smallest = 5000;

        for (String s : inventoryHashmap.keySet()) {

            double comparedValue = inventoryHashmap.get(s);

            if (pointFiveHashmap.keySet().contains(s)) {

                if (s.equals("MWB tube") || s.equals("MWB slider")) {
                    comparedValue = inventoryHashmap.get(s) / getTubeConsumption(s, pointFive);
                }
                if (comparedValue < pointFiveSmallest)
                    pointFiveSmallest = comparedValue;
            }

            if (onePointFive.getOnePointFiveComponents().keySet().contains(s)) {
                if (s.equals("MWB tube") || s.equals("MWB slider")) {
                    comparedValue = inventoryHashmap.get(s) / getTubeConsumption(s, onePointFive);
                }
                if (comparedValue < onePointFiveSmallest)
                    onePointFiveSmallest = comparedValue;
            }

            if (twoPointTwo.getTwoPointTwoComponents().keySet().contains(s)) {
                if (s.equals("MWB tube") || s.equals("MWB slider")) {
                    comparedValue = inventoryHashmap.get(s) / getTubeConsumption(s, twoPointTwo);
                }
                if (comparedValue < twoPointTwoSmallest)
                    twoPointTwoSmallest = comparedValue;
            }

            if (hj32Hashmap.keySet().contains(s)) {

                if (s.equals("MWB tube") || s.equals("MWB slider") || s.equals("HJ tube")) {
                    comparedValue = 1 / getTubeConsumption(s, hj32);
                }
                if (comparedValue < hj32Smallest)
                    hj32Smallest = inventoryHashmap.get(s);
            }

            if (hj64.get64OzHjComponents().keySet().contains(s)) {
                if (s.equals("MWB tube") || s.equals("MWB slider") || s.equals("HJ tube")) {
                    comparedValue = 1 / getTubeConsumption(s, hj64);
                }
                if (comparedValue < hj64Smallest)
                    hj64Smallest = comparedValue;
            }

            if (hj128.get128OzHjComponents().keySet().contains(s)) {
                if (s.equals("MWB tube") || s.equals("MWB slider") || s.equals("HJ tube")) {
                    comparedValue = 1 / getTubeConsumption(s, hj128);
                }
                if (comparedValue < hj128Smallest)
                    hj128Smallest = comparedValue;
            }
        }

        pointFiveSmallest /= 16;
        onePointFiveSmallest /= 16;
        twoPointTwoSmallest /= 16;
        hj32Smallest /= 16;
        hj64Smallest /= 16;
        hj128Smallest /=16;

        nameBuilder.append("0.5 MWB\n").append("1.5 MWB\n").append("2.2 MWB\n").append("32oz HydraJet\n").append("64oz HydraJet\n").append("128oz HydraJet");
        quantityBuilder.append(decFor.format(pointFiveSmallest)).append("\n")
                .append(decFor.format(onePointFiveSmallest)).append("\n")
                .append(decFor.format(twoPointTwoSmallest)).append("\n")
                .append(decFor.format(hj32Smallest)).append("\n")
                .append(decFor.format(hj64Smallest)).append("\n")
                .append(decFor.format(hj128Smallest)).append("\n");


        productNames.setText(nameBuilder.toString());
        productQuantities.setText(quantityBuilder.toString());

        Button viewInventoryBtn = (Button) view.findViewById(R.id.view_inventory);
        viewInventoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft = getParentFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_container, new InventoryFragment()).addToBackStack(null);
                ft.commit();
            }
        });

        Button addInventoryBtn = (Button) view.findViewById(R.id.add_inventory);
        addInventoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.setInventoryModificiationStatus(true);
                modifyInventory();
            }
        });

        Button removeInventoryBtn = view.findViewById(R.id.remove_inventory);
        removeInventoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.setInventoryModificiationStatus(false);
                modifyInventory();
            }
        });

//        Button resetInvButton = view.findViewById(R.id.reset_inv_button);
//        resetInvButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                inventoryDatabase.resetInventory();
//            }
//        });
    }

    private double getTubeConsumption(String s, Product product) {
        switch (s) {
            case "MWB tube":
                //Toast.makeText(requireContext(), String.valueOf(1 / product.getMwbTube()), Toast.LENGTH_SHORT).show();
                return product.getMwbTube();
            case "MWB slider":
                return product.getMwbSlider();
            case "HJ tube":
                return product.getHjTube();
            default:
                return 5000;
        }
    }

    private void modifyInventory() {
        FragmentTransaction ft = getParentFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, new AddInventoryFragment()).addToBackStack(null);
        ft.commit();
    }
}