package com.kj.cherryengineering20.completedCaseActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.kj.cherryengineering20.CompletedCaseQuantityMenu.CompletedCaseQuantityMenu;
import com.kj.cherryengineering20.R;
import com.kj.cherryengineering20.employees.EmployeeDatabase;

import java.util.List;

public class CompletedCaseActivityMenu extends AppCompatActivity {

    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completed_case_menu);

        EmployeeDatabase db = new EmployeeDatabase(this);
        List<String> names = db.getEmployeeNames();

        ListView listView = (ListView) findViewById(R.id.employee_list);
        ArrayAdapter<String> listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, names);
        listView.setAdapter(listAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                name = names.get(i);
                openCompletedCaseQuantityMenu();
            }
        });

//        Button button = (Button) findViewById(R.id.waterBottle);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                openCompletedCaseQuantityMenu();
//            }
//        });
    }

    public void openCompletedCaseQuantityMenu() {
        Intent intent = new Intent(this, CompletedCaseQuantityMenu.class);
        intent.putExtra("name", name);
        startActivity(intent);
    }


    public void goBack(View view) {
        finish();
    }
}
