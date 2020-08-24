package com.example.yuva;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.LinkedList;

public class BloodDonersActivity extends AppCompatActivity implements AddBloodDonerDialog.BloodDonerDialogListener {
    private RecyclerView mRecylerView;
    private BloodAdapter mAdapter;

    private FirebaseDatabase rootNode;
    private DatabaseReference doners;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_doners);

        rootNode = FirebaseDatabase.getInstance();
        doners = rootNode.getReference("blood_doners");
        mRecylerView = findViewById(R.id.blood_doners_recyclerview);
        mRecylerView.setLayoutManager(new LinearLayoutManager(this));


        FirebaseRecyclerOptions<BloodDonerModel> options =
                new FirebaseRecyclerOptions.Builder<BloodDonerModel>()
                           .setQuery(rootNode.getReference().child("blood_doners"), BloodDonerModel.class)
                        .build();
        mAdapter = new BloodAdapter(options);
        mRecylerView.setAdapter(mAdapter);

        findViewById(R.id.blood_doner_fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AddBloodDonerDialog bloodDonerDialog = new AddBloodDonerDialog();
                bloodDonerDialog.show(getSupportFragmentManager(),"show dialog");
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAdapter.stopListening();
    }

    @Override
    public void addNewBloodDoner(String donerName, String mobNo, String bloodGroup) {
        doners.child(mobNo).setValue(new BloodDonerModel(donerName,mobNo,bloodGroup));
        Toast.makeText(BloodDonersActivity.this,"data received from dialogbox",Toast.LENGTH_SHORT).show();
    }
}