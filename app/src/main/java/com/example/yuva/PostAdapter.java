package com.example.yuva;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class PostAdapter extends FirebaseRecyclerAdapter<PostModel,PostAdapter.PostViewHolder> {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public PostAdapter(@NonNull FirebaseRecyclerOptions<PostModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull PostViewHolder holder, int position, @NonNull PostModel model) {
        // Bind the Chat object to the ChatHolder
        // ...
        holder.title.setText(model.getTitle());
        holder.description.setText(model.getDescription());
        String datetime = model.getDate() + " " + model.getTime();
        holder.dateTime.setText(datetime);
        Glide.with(holder.postImage.getContext()).load(model.getPostImage()).into(holder.postImage);
        holder.username.setText(model.getUserName());
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Create a new instance of the ViewHolder, in this case we are using a custom
        // layout called R.layout.message for each item
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.post_activity_item, parent, false);

        return new PostViewHolder(view);
    }

    public static class PostViewHolder extends RecyclerView.ViewHolder {
        ImageView postImage;
        TextView title,description,username,dateTime;
        public PostViewHolder(@NonNull View itemView) {
            super(itemView);

            postImage = itemView.findViewById(R.id.post_image);
            title = itemView.findViewById(R.id.post_title);
            description = itemView.findViewById(R.id.post_description);
            username = itemView.findViewById(R.id.post_user_name);
            dateTime = itemView.findViewById(R.id.post_date_time);

        }
    }
}
