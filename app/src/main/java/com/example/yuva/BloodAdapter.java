package com.example.yuva;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class BloodAdapter extends FirebaseRecyclerAdapter<BloodDonerModel, BloodAdapter.BloodViewHolder> {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    String TAG = "BloodAdapter";

    public BloodAdapter(@NonNull FirebaseRecyclerOptions<BloodDonerModel> options) {
        super(options);
        Log.d(TAG,"constructor");
    }

    @Override
    protected void onBindViewHolder(@NonNull BloodViewHolder holder, int position, @NonNull BloodDonerModel model) {
        holder.name.setText(model.getName());
        holder.mob_no.setText(model.getMobileNo());
        holder.bgroup.setText(model.getGroup());
    }

    @NonNull
    @Override
    public BloodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.blood_doner_single_item,parent,false);

        return new BloodViewHolder(view);
    }

    class BloodViewHolder extends RecyclerView.ViewHolder{

        TextView name,mob_no,bgroup;

        public BloodViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.blood_doner_name);
            mob_no = itemView.findViewById(R.id.blood_doner_mob_no);
            bgroup = itemView.findViewById(R.id.blood_doner_blood_group);
        }
    }
}
