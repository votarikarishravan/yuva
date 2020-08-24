package com.example.yuva;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class EditProfileActivity extends AppCompatActivity {
    private Spinner spinner;
    private EditText mName,mEmail,mPhoneNumber;
    private Button mSave;
    String group;

    FirebaseAuth mAuth;
    DatabaseReference userRef, doners;
    FirebaseDatabase firebasedb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        mAuth = FirebaseAuth.getInstance();
        firebasedb = FirebaseDatabase.getInstance();
        userRef = firebasedb.getReference().child("user_profile_info");
        doners = firebasedb.getReference("blood_doners");

        mName = findViewById(R.id.edit_person_name);
        mPhoneNumber = findViewById(R.id.edit_phone_number);
        mEmail = findViewById(R.id.edit_email);
        mSave = findViewById(R.id.save_profile_button);
        spinner = findViewById(R.id.blood_group_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                                            R.array.blood_group_list, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                group = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(EditProfileActivity.this, group, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate();
            }
        });
    }

    private void validate() {
        String name = mName.getText().toString().trim();
        String phoneNumber = mPhoneNumber.getText().toString().trim();
        String email = mEmail.getText().toString().trim();
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(phoneNumber) || TextUtils.isEmpty(email) || TextUtils.isEmpty(group)){
            Toast.makeText(EditProfileActivity.this, "please fill all details", Toast.LENGTH_SHORT).show();
        }else {
            //Toast.makeText(EditProfileActivity.this, "OK", Toast.LENGTH_SHORT).show();
            saveDataIntoFirebaseDB(name,phoneNumber,email,group);
        }
    }

    private void saveDataIntoFirebaseDB(String name,String phoneNumber, String email, String group) {
        String userId = mAuth.getUid();

        HashMap hashMap = new HashMap();
        hashMap.put("userName",name);
        hashMap.put("phoneNumber",phoneNumber);
        hashMap.put("email",email);
        hashMap.put("bloodGroup",group);

        userRef.child(userId).setValue(hashMap);
        doners.child(phoneNumber).setValue(new BloodDonerModel(name,phoneNumber,group));

    }
}