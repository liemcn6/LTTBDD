package com.example.baeminfake.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.baeminfake.R;
import com.example.baeminfake.fragment.ProfileFragment;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import static com.example.baeminfake.fragment.ProfileFragment.avatar;

public class ProfileActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseUser currentUser;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.profile_activity);

        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();
        storageReference = FirebaseStorage.getInstance().getReference();

        Fragment current = new ProfileFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.profile_holder, current, "TAG")
                .addToBackStack(null)
                .commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @androidx.annotation.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000) {
            if (resultCode == Activity.RESULT_OK) {
                Uri imageUri = data.getData();
                uploadAvatar(imageUri);
            }
        }
    }

    private void uploadAvatar(Uri uri) {
        StorageReference fileRef = storageReference.child(currentUser.getUid() + ".jpg");
        fileRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).into(avatar);
                    }
                });
            }
        });
    }
}