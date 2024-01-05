package com.kj.cherryengineering20;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.kj.cherryengineering20.bottomBarFragments.HomeFragment;
import com.kj.cherryengineering20.bottomBarFragments.SharedViewModel;
import com.kj.cherryengineering20.completedCaseActivity.CompletedCaseActivityMenu;
import com.kj.cherryengineering20.employees.EmployeesMainMenu;
import com.kj.cherryengineering20.inventoryFragments.InventoryMainMenu;
import com.kj.cherryengineering20.payroll.PayrollMenu;
import com.kj.cherryengineering20.product.InventoryDatabase;

public class MainActivity extends AppCompatActivity {

    private SharedViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewModel = new ViewModelProvider(this).get(SharedViewModel.class);
        viewModel.setContext(this);

        InventoryDatabase inventoryDatabase = new InventoryDatabase(this);
        viewModel.setInventoryDatabase(inventoryDatabase);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

//        TextView currentActivity = findViewById(R.id.currentActivity);
//        currentActivity.setText(getLocalClassName());

//        Button button1 = (Button) findViewById(R.id.buildButton);
//        button1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                openCompletedCaseActivity();
//            }
//        });
//
//        Button viewInventory = (Button) findViewById(R.id.view_inventory);
//        viewInventory.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                openInventoryViewer();
//            }
//        });
//
//        Button openPayrollButton = (Button) findViewById(R.id.payroll);
//        openPayrollButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                openPayroll();
//            }
//        });
    }

    private NavigationBarView.OnItemSelectedListener navListener =
            new NavigationBarView.OnItemSelectedListener() {

                @SuppressLint("NonConstantResourceId")
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;
                    int thing = item.getItemId();

                    if (thing == R.id.nav_home) {
                        selectedFragment = new HomeFragment();
                    } else if (thing == R.id.nav_inventory) {
                        selectedFragment = new InventoryMainMenu();
                    } else if (thing == R.id.nav_employees) {
                        selectedFragment = new EmployeesMainMenu();
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();

                    return true;
                }
            };

    public void openCompletedCaseActivity() {
        Intent intent = new Intent(this, CompletedCaseActivityMenu.class);
        startActivity(intent);
    }

    public void openInventoryViewer() {
        startActivity(new Intent(this, InventoryViewer.class));
    }

    public void openPayroll() {
        startActivity(new Intent(this, PayrollMenu.class));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}