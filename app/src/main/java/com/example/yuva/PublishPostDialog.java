package com.example.yuva;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class PublishPostDialog extends DialogFragment {

    private static final int GALLERY_PICK = 1;
    public static final int RESULT_OK = -1;
    private ImageView mPostImagePreview;
    private Uri imageUri;
    private EditText mPostTitle,mPostMessageBox;

    PublishPostDialogListener listener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (PublishPostDialogListener) context;
        }
        catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement PublishPostDialogListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.publish_post, null);
        builder.setView(view).setTitle("Create New Post:").setPositiveButton("POST", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                final String description = mPostMessageBox.getText().toString();
                final String title = mPostTitle.getText().toString();
                listener.validatePost(title,description,imageUri);
            }
        }).setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        mPostImagePreview = view.findViewById(R.id.post_image_preview);
        mPostTitle = view.findViewById(R.id.post_title_box);
        mPostMessageBox = view.findViewById(R.id.post_message_box);

        mPostImagePreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickImage();
            }
        });

        return builder.create();
    }

    public void pickImage() {
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent,GALLERY_PICK);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_PICK && resultCode == RESULT_OK && data != null){
            this.imageUri = data.getData();
            mPostImagePreview.setImageURI(imageUri);
        }
    }

    public interface PublishPostDialogListener{
        void validatePost(String title,String description,Uri imageUri);
    }
}
