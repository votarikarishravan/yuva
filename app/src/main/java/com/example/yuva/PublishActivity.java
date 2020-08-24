package com.example.yuva;

import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class PublishActivity extends AppCompatActivity implements PublishPostDialog.PublishPostDialogListener {
    UploadTask uploadTask;
    String saveCurrentDate,saveCurrentTime,postRandaomName;

    String TAG = "publishActivity";
    private StorageReference mStorageRef;
    private FirebaseAuth mAuth;
    private DatabaseReference mPostInfoRef;
    private FirebaseDatabase dbRootNode;

    private RecyclerView mRecyclerView;
    private PostAdapter adapter;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.publish_menu,menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"Oncreate");
        setContentView(R.layout.activity_publish);

        mStorageRef = FirebaseStorage.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        dbRootNode = FirebaseDatabase.getInstance();
        mPostInfoRef = dbRootNode.getReference("posts_info");

        mRecyclerView = findViewById(R.id.all_user_posts_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        mRecyclerView.setLayoutManager(layoutManager);

        showAllPosts();
    }

    private void showAllPosts() {
        FirebaseRecyclerOptions<PostModel> options =
                new FirebaseRecyclerOptions.Builder<PostModel>()
                        .setQuery(mPostInfoRef, PostModel.class)
                        .build();
        adapter = new PostAdapter(options);

        mRecyclerView.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG,"Onstart");
        adapter.startListening();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        createPost();
        return super.onOptionsItemSelected(item);
    }

    private void createPost() {
        PublishPostDialog postDialog = new PublishPostDialog();
        postDialog.show(getSupportFragmentManager(),"create post");
    }


    private void storeImageinFirebase(final String title, final String description, final Uri imageUri) {

        Calendar calForDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("dd-MMMM-YYYY");
        saveCurrentDate = currentDate.format(calForDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm");
        saveCurrentTime = currentTime.format(calForDate.getTime());

        postRandaomName = saveCurrentDate + "_" + saveCurrentTime;

        final StorageReference filePath = mStorageRef.child("Post Images")
                                    .child(imageUri.getLastPathSegment() + postRandaomName + ".jpg");
        uploadTask = filePath.putFile(imageUri);

        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {

                    @Override
                    public void onSuccess(Uri uri) {
                        Toast.makeText(PublishActivity.this, "image uploaded!!!", Toast.LENGTH_SHORT).show();
                             //initializeDownloadUri(uri);
                        savePostInfoToDatabase(description,title,uri);
                    }
                });
            }
        });


    }

    private void savePostInfoToDatabase(final String description, final String title, final Uri imageURI) {
        final String mCurrentUID = mAuth.getCurrentUser().getUid();

        dbRootNode.getReference("user_profile_info").child(mCurrentUID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    String userName = snapshot.child("userName").getValue().toString();

                    HashMap hashMap = new HashMap();
                    hashMap.put("userId",mCurrentUID);
                    hashMap.put("userName",userName);
                    hashMap.put("date",saveCurrentDate);
                    hashMap.put("time",saveCurrentTime);
                    hashMap.put("title",title);
                    hashMap.put("description",description);
                    hashMap.put("postImage",imageURI.toString());

                    mPostInfoRef.child(mCurrentUID + "_"  +postRandaomName).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull Task task) {
                            if (task.isSuccessful()){
                                Toast.makeText(PublishActivity.this, "published your post successfully..", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(PublishActivity.this, "post failed...", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });


                }else {

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        /*HashMap hashMap = new HashMap();
        hashMap.put("userId",mCurrentUID);
        //hashMap.put("userName",currentUserName);
        hashMap.put("date",saveCurrentDate);
        hashMap.put("time",saveCurrentTime);
        hashMap.put("title",title);
        hashMap.put("description",description);
        hashMap.put("postImage",imageURI.toString());

        mPostInfoRef.child(mCurrentUID + "_"  +postRandaomName).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if (task.isSuccessful()){
                    Toast.makeText(PublishActivity.this, "published your post successfully..", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(PublishActivity.this, "post failed...", Toast.LENGTH_SHORT).show();
                }
            }
        });*/
    }

    @Override
    public void validatePost(String title, String description, Uri imageUri) {
        if (TextUtils.isEmpty(description) || TextUtils.isEmpty(title) || imageUri == null){
            Toast.makeText(PublishActivity.this,"Write Title and Description for the Post",Toast.LENGTH_SHORT).show();
        }else {
            storeImageinFirebase(title,description,imageUri);
            Toast.makeText(this, "successfully posted...", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG,"Onstop");
        adapter.stopListening();
    }
}