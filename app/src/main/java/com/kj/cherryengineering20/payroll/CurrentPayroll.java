package com.kj.cherryengineering20.payroll;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.kj.cherryengineering20.R;
import com.kj.cherryengineering20.employees.EmployeeDatabase;

public class CurrentPayroll extends AppCompatActivity {

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_payroll);

        mTextView = findViewById(R.id.current_payroll_text);
        EmployeeDatabase db = new EmployeeDatabase(this);
        String name = getIntent().getStringExtra("nameSelected");
        String content = db.getEmployeeCurrentPayroll(name);
        mTextView.setText(content);
    }

    public void goBack(View view) {
        finish();
    }
}