package com.example.yuva;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    private EditText mEmail;
    private EditText mPassword;
    private Button mRegister;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mEmail = findViewById(R.id.register_email_id);
        mPassword = findViewById(R.id.register_password);
        mRegister = findViewById(R.id.action_in_register);
        auth = FirebaseAuth.getInstance();

        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txt_email = mEmail.getText().toString();
                String txt_password = mPassword.getText().toString();

                if (TextUtils.isEmpty(txt_email) || TextUtils.isEmpty(txt_password)){
                    Toast.makeText(RegisterActivity.this,"Empty Credentials",Toast.LENGTH_SHORT).show();
                }else if (txt_password.length()<6){
                    Toast.makeText(RegisterActivity.this,"Password is too short",Toast.LENGTH_SHORT).show();
                }else {
                    registerUser(txt_email,txt_password);
                }
            }
        });


    }

    private void registerUser(String email, String password) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(RegisterActivity.this,"Registration Successfull",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(RegisterActivity.this,"Registration Failed",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}