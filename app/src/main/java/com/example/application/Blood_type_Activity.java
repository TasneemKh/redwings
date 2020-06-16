package com.example.application;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

public class Blood_type_Activity extends AppCompatActivity {
    RadioButton radioOpos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_type_);
        radioOpos=findViewById(R.id.radioOpos);

    }
    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        String str="";
        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radioOpos:
                if(checked)
                    radioOpos.setTextColor(getResources().getColor(R.color.colorWhite));
                   // radioOpos.setBac(contextInstance.getResources().getColorStateList(R.color.);
                break;
            case R.id.radioOneg:
                if(checked)
                    str = "AngularJS Selected";
                break;
            case R.id.radioApos:
                if(checked)
                    str = "Java Selected";
                break;
            case R.id.radioAneg:
                if(checked)
                    str = "Python Selected";
                break;
            case R.id.radioBpos:
                if(checked)
                    str = "eee Selected";
                break;
            case R.id.radioBneg:
                if(checked)
                    str = "Pyteewhon Selected";
                break;
            case R.id.radioABpos:
                if(checked)
                    str = "fd Selected";
                break;
            case R.id.radioABneg:
                if(checked)
                    str = "f Selected";
                break;
        }

    }
}
