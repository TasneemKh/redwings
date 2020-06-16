package com.example.application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
public class SignIn extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthStateListener;
    private EditText emailTxt,passTxt;
    float x1,y1,x2,y2;
    Button signIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if (mAuth.getCurrentUser()!=null)
        {
            //open main activity
            //it need to  changed
            Intent intent = new Intent(SignIn.this ,Add_Request_Activity.class);
            startActivity(intent);
        }
        initializeUI();

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUserAccount();
            }
        });
        findViewById(R.id.back).setOnClickListener(this);
        findViewById(R.id.forgotPassword).setOnClickListener(this);
        findViewById(R.id.movSignUp1).setOnClickListener(this);
    }
    private void loginUserAccount() {
        if (!validateEmail() | !validatePassword()) {
            return;
        }

        String email, password;
        email = emailTxt.getText().toString();
        password = passTxt.getText().toString();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Login successful!", Toast.LENGTH_LONG).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            //change it into main activity
                            Intent intent = new Intent(SignIn.this, Add_Request_Activity.class);
                            startActivity(intent);
                        }
                        else {
                          //  Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                View coordinatorLayout = null;
                                Snackbar snackbar = Snackbar
                                        .make(coordinatorLayout, "Email or Password is incorrect ", Snackbar.LENGTH_LONG)
                                        .setAction("RETRY", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                emailTxt.getText().clear();
                                                passTxt.getText().clear();
                                            }
                                        });

                                snackbar.show();

                        }
                    }
                });
    }
    private boolean validateEmail() {
        String emailInput = emailTxt.getText().toString().trim();

        if (emailInput.isEmpty()) {
            emailTxt.setError("email can't be empty");
            return false;
        } else {
            emailTxt.setError(null);
            return true;
        }
    }
    private boolean validatePassword() {
        String passwordInput = passTxt.getText().toString().trim();

        if (passwordInput.isEmpty()) {
            passTxt.setError("password can't be empty");
            return false;
        } else {
            passTxt.setError(null);
            return true;
        }
    }
    private void initializeUI() {
        emailTxt =findViewById(R.id.emailEditTxt);
        passTxt = findViewById(R.id.passEditTxt);
        signIn = findViewById(R.id.signIn);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.forgotPassword:
                startActivity(new Intent(this, forgetPassword.class));
                break;
            case R.id.movSignUp1:
                startActivity(new Intent(this, signUp.class));
                break;
            case R.id.signIn:
                loginUserAccount();
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
                    Intent i= new Intent(SignIn.this,MainActivity.class);
                    startActivity(i);
                }
                break;
        }
        return false;
    }
}
