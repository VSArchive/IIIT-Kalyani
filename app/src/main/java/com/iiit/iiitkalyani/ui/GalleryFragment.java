package com.iiit.iiitkalyani.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.iiit.iiitkalyani.Adapter.ImgAdapter;
import com.iiit.iiitkalyani.Adapter.Upload;
import com.iiit.iiitkalyani.R;
import com.iiit.iiitkalyani.UploadPics;

import java.util.ArrayList;
import java.util.List;

public class GalleryFragment extends Fragment {
    private RecyclerView RecyclerView;
    private ImgAdapter Adapter;
    private ProgressBar ProgressCircle;
    private List<Upload> mUploads;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        RecyclerView = root.findViewById(R.id.view);
        RecyclerView.setHasFixedSize(true);
        RecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        FloatingActionButton btn = root.findViewById(R.id.btnUpload);
        ProgressCircle = root.findViewById(R.id.progress_circle);
        mUploads = new ArrayList<>();
        DatabaseReference mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads");
        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Upload upload = postSnapshot.getValue(Upload.class);
                    mUploads.add(upload);
                }

                Adapter = new ImgAdapter(getContext(), mUploads);

                RecyclerView.setAdapter(Adapter);
                ProgressCircle.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                ProgressCircle.setVisibility(View.INVISIBLE);
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), UploadPics.class);
                startActivity(intent);
            }
        });
        return root;
    }
}