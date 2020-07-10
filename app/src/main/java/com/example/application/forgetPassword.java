package com.example.application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class forgetPassword extends AppCompatActivity {
    ImageButton back;
    Button recoverPassword;
    TextView email_rec;
    float x1,y1,x2,y2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        //getting back to log in activity
        back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), SignIn.class);
                startActivity(i);
            }
        });

        //recover pass using email
        recoverPassword=findViewById(R.id.recoverPassword);
        recoverPassword.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                FirebaseAuth auth = FirebaseAuth.getInstance();
                email_rec=findViewById(R.id.e_mail1);
                String emailAddress = email_rec.getText().toString();
                auth.sendPasswordResetEmail(emailAddress)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    // Log.d(TAG, "Email sent.");
                                    Toast.makeText(getApplicationContext(), "Please ,review your email", Toast.LENGTH_LONG).show();
                                    Intent i = new Intent(getApplicationContext(), CheckEmail.class);
                                    startActivity(i);
                                }else{
                                    //  Toast.makeText(getApplicationContext(), "Please ,enter a valid email", Toast.LENGTH_LONG).show();
                                    Log.d("MEDIA_PLAYER", new Exception().getMessage());
                                    Toast.makeText(getApplicationContext(), "oops", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent touchEvent) {
        switch(touchEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                x1=touchEvent.getX();
                y1=touchEvent.getY();
                break;
            case MotionEvent.ACTION_UP:
                x2=touchEvent.getX();
                y2=touchEvent.getY();
                if(x1 < x2){
                    Intent i= new Intent(forgetPassword.this,SignIn.class);
                    startActivity(i);
                }
                break;
        }
        return false;
    }
}
