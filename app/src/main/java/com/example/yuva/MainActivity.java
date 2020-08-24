package com.example.yuva;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private EditText mUserName;
    private EditText mPassword;
    private Button mRegister;
    private Button mLogin;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mUserName = findViewById(R.id.username);
        mPassword = findViewById(R.id.password);
        mLogin = findViewById(R.id.login);
        mRegister = findViewById(R.id.register);
        auth = FirebaseAuth.getInstance();

        mUserName.addTextChangedListener(loginTextWatcher);
        mPassword.addTextChangedListener(loginTextWatcher);

        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,RegisterActivity.class));
                finish();
            }
        });

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txt_username = mUserName.getText().toString().trim();
                String txt_password = mPassword.getText().toString().trim();
                signInUser(txt_username,txt_password);
            }
        });
    }

    private void signInUser(String username, String password) {
        auth.signInWithEmailAndPassword(username,password).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(MainActivity.this,"Login Successfull",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity.this,HomeActivity.class));
                }else {
                    Toast.makeText(MainActivity.this,"Incorrect Credentials",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private TextWatcher loginTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String userName = mUserName.getText().toString().trim();
            String password = mPassword.getText().toString().trim();

            mLogin.setEnabled(!userName.isEmpty() && !password.isEmpty());

        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };
}