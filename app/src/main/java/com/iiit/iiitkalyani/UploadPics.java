package com.iiit.iiitkalyani;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.r0adkll.slidr.Slidr;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Objects;

public class UploadPics extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private EditText EditTextFileName;
    private ImageView ImageView;
    private String imageUrl;
    private Uri ImageUri;
    private StorageTask UploadTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_pics);
        Slidr.attach(this);

        Button mButtonChooseImage = findViewById(R.id.button_choose_image);
        Button mButtonUpload = findViewById(R.id.button_upload);
        EditTextFileName = findViewById(R.id.edit_text_file_name);
        ImageView = findViewById(R.id.image_view);
        mButtonChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });
        mButtonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (UploadTask != null && UploadTask.isInProgress()) {
                    Toast.makeText(UploadPics.this, "Upload in progress", Toast.LENGTH_SHORT).show();
                } else {
                    uploadFile();
                }
            }
        });
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            ImageUri = data.getData();

            Picasso.get().load(ImageUri).into(ImageView);
        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void uploadFile() {
        final ProgressDialog pd = new ProgressDialog(this, R.style.MyAlertDialogStyle);
        pd.setMessage("Uploading");
        pd.show();

        if (ImageUri != null) {
            final StorageReference filePath = FirebaseStorage.getInstance().getReference("uploads").child(System.currentTimeMillis() + "." + getFileExtension(ImageUri));

            StorageTask uploadtask = filePath.putFile(ImageUri);
            //noinspection unchecked
            uploadtask.continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull Task task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw Objects.requireNonNull(task.getException());
                    }
                    return filePath.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    Uri downloadUri = task.getResult();
                    if (downloadUri != null) {
                        imageUrl = downloadUri.toString();
                    }
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("uploads");
                    String ID = ref.push().getKey();
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("name", EditTextFileName.getText().toString());
                    map.put("imageUrl", imageUrl);
                    map.put("publisher", Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getDisplayName());
                    if (ID != null) {
                        ref.child(ID).setValue(map);
                    }
                    pd.dismiss();
                    startActivity(new Intent(UploadPics.this, MainActivity.class));
                    finish();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(UploadPics.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(this, "No image was selected!", Toast.LENGTH_SHORT).show();
            pd.dismiss();
        }
    }
}
