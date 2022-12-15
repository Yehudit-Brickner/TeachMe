package com.example.loginpage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class AddClass extends AppCompatActivity {


    private Button create;
    private ImageButton add;

    public LinearLayout mylayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_class);






        mylayout=(LinearLayout) findViewById(R.id.scrollviewllayout);
        add=(ImageButton)findViewById(R.id.addbtn);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               addview();
            }
        });




    }


    public void addview() {
        View myview = getLayoutInflater().inflate(R.layout.add_class_row,null,false);
        mylayout.addView(myview);
    }


}