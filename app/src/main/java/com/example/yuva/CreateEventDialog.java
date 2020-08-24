package com.example.yuva;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class CreateEventDialog extends DialogFragment {
    EditText mTitle,mPlace,mDescription;
    Button mDate,mTime;
    CreateEventDialogListener listener;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (CreateEventDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement BloodDonerDialogListener");
        }

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.event_item, null);
        builder.setView(view).setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                listener.storeNewEventIntoFirebase();
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        mTitle = view.findViewById(R.id.events_title);
        mDate = view.findViewById(R.id.events_date);
        mTime = view.findViewById(R.id.events_time);
        mPlace = view.findViewById(R.id.events_place);
        mDescription = view.findViewById(R.id.events_description);

        mTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager mgr = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = mgr.beginTransaction();
                Fragment old = mgr.findFragmentById(R.id.event_item_fragment);
                if (old != null) {
                    ft.remove(old);
                }
                ft.addToBackStack(null);

                DialogFragment newFragment = new TimePickerFragment();
                newFragment.show(ft, "timePicker");
            }
        });

        return builder.create();
    }

    public interface CreateEventDialogListener{
        void storeNewEventIntoFirebase();
    }
}
