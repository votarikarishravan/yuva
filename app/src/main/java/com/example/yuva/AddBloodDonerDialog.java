package com.example.yuva;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class AddBloodDonerDialog extends DialogFragment {

    private EditText name,mob_no,blood_group;
    private BloodDonerDialogListener listener;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (BloodDonerDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()+ " must implement BloodDonerDialogListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.add_blood_doner, null);
        builder.setView(view).setTitle("Add New Blood Doner")
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String dName = name.getText().toString().trim();
                        String dMob_no = mob_no.getText().toString().trim();
                        String dGroup = blood_group.getText().toString().trim();
                        listener.addNewBloodDoner(dName,dMob_no,dGroup);
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        name = view.findViewById(R.id.doner_name);
        mob_no = view.findViewById(R.id.doner_mobile_no);
        blood_group = view.findViewById(R.id.doner_blood_group);

        return builder.create();
    }

    public interface BloodDonerDialogListener {
        void addNewBloodDoner(String donerName,String  mobNo,String bloodGroup);
    }
}
