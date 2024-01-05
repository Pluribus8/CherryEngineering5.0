package com.kj.cherryengineering20.completedCaseActivity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.kj.cherryengineering20.R;

public class CompletedCaseActivityMain extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Button button = (Button) findViewById(R.id.buildButton);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                openWaterBottleCaseMenu();
//            }
//        });
    }

    public void openWaterBottleCaseMenu() {
        System.out.println("opened");
    }
}
