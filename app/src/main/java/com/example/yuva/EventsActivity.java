package com.example.yuva;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class EventsActivity extends AppCompatActivity implements CreateEventDialog.CreateEventDialogListener {
    FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

    }

    private void createEvent() {
        CreateEventDialog eventDialog = new CreateEventDialog();
        eventDialog.show(getSupportFragmentManager(),"New Event");

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.add_new_event:
                createEvent();
                return true;
            default:
                Toast.makeText(this, "no options selected", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.events_menu,menu);
        return true;
    }

    @Override
    public void storeNewEventIntoFirebase() {

    }
}