package com.example.sql_example;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.QuickContactBadge;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

public class FirebaseActivity extends AppCompatActivity {


    private StorageReference storageRef;
    private FirebaseStorage storage;
    private Button download_btn,upload_btn;
    private ImageView imageView,imageView_up;
    public Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase);


        //firebase
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();




        download_btn = findViewById(R.id.dw_btn);
        upload_btn = findViewById(R.id.upl_btn);
        imageView = findViewById(R.id.imageView);
        imageView_up = findViewById(R.id.imageView_up);


        imageView_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePicture();
            }
        });

        download_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadWithBytes();
            }
        });





    }

    public void choosePicture() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode ==1 && resultCode ==RESULT_OK && data!=null && data.getData() != null){
            imageUri = data.getData();
            imageView_up.setImageURI(imageUri);
            upload_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    uploadPicture();
                }
            });
        }
    }

    public void uploadPicture() {

        final ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("Uploaded Image...");
        pd.show();

        final String randomKey = UUID.randomUUID().toString();
        StorageReference mountainsRef = storageRef.child("images/"+ randomKey);
        mountainsRef.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                       pd.dismiss();
                        Toast.makeText(FirebaseActivity.this, "Image Uploaded", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(FirebaseActivity.this, "Failed To Upload", Toast.LENGTH_SHORT).show();


            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
            double progressPercent = (100.00 * snapshot.getBytesTransferred()/snapshot.getTotalByteCount());
            pd.setMessage("progress: "+ (int) progressPercent+ "%");
            }
        });

    }

    public void downloadWithBytes() {

        StorageReference imageRef = storageRef.child("images/12.jpg");

        long MAXBYTES = 1024*1024;
        imageRef.getBytes(MAXBYTES).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                imageView.setImageBitmap(bitmap);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }
}