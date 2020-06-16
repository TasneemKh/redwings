package com.example.application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class signUp extends AppCompatActivity implements View.OnClickListener  {
    DatePickerDialog picker;
    EditText birthTxt ,emailTxt,passwordTxt,userName;
    private FirebaseAuth mAuth;
    private Button regBtn;
    private static final String TAG = signUp.class.getSimpleName();
    float x1,y1,x2,y2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        birthTxt=(EditText) findViewById(R.id.eText);
        birthTxt.setInputType(InputType.TYPE_NULL);
        birthTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(signUp.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                birthTxt.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }

                        }, year, month, day);

                picker.show();
            }
        });
        mAuth = FirebaseAuth.getInstance();
        userName= findViewById(R.id.username);
        emailTxt = findViewById(R.id.emailAddress);
        passwordTxt = findViewById(R.id.passTxt);
        findViewById(R.id.back).setOnClickListener(this);
        findViewById(R.id.signIn).setOnClickListener(this);
        regBtn = findViewById(R.id.sign_up);
        regBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                doSignUp(emailTxt.getText().toString().trim(), passwordTxt.getText().toString().trim());
            }
        });
    }
    private void doSignUp(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            String emailF = user.getEmail();
                            String uid = user.getUid();
                            Map<String,Object> data = new HashMap<>();
                            data.put("userName",userName.getText().toString().trim());
                            data.put("birthday",birthTxt.getText().toString().trim());
                            data.put("uid",uid);
                            data.put("email",emailF);
                            data.put("createdAt",new Date().getTime());
                            FirebaseDatabase.getInstance().getReference().child("User").child(uid).setValue(data)
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(getApplicationContext(),"on Failuer", Toast.LENGTH_SHORT).show();
                                            Log.d("error",e.getLocalizedMessage());
                                        }
                                    })
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Intent intent = new Intent(signUp.this , Add_Request_Activity.class);
                                            startActivity(intent);
                                        }
                                    });

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(signUp.this, task.getException().getLocalizedMessage(),
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                    }
                });
    }
    private void updateUI(FirebaseUser currentUser) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String name = user.getDisplayName();
            String email = user.getEmail();
            boolean emailVerified = user.isEmailVerified();

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getIdToken() instead.
            String uid = user.getUid();
        }
    }
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.back:
                startActivity(new Intent(this,MainActivity.class));
                break;
            case R.id.signIn:
                startActivity(new Intent(this,SignIn.class));
                break;
            case R.id.sign_up:
                doSignUp(emailTxt.getText().toString(), passwordTxt.getText().toString());
                break;
            default:
                break;
        }

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
                    Intent i= new Intent(signUp.this,MainActivity.class);
                    startActivity(i);
                }
                break;
        }
        return false;
    }
}
