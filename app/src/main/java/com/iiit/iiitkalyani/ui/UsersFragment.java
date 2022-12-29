package com.iiit.iiitkalyani.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.iiit.iiitkalyani.Adapter.UserAdapter;
import com.iiit.iiitkalyani.Adapter.Users;
import com.iiit.iiitkalyani.R;

import java.util.ArrayList;
import java.util.List;

public class UsersFragment extends Fragment {
    private RecyclerView RecyclerView;
    private UserAdapter Adapter;
    private ProgressBar ProgressCircle;
    private List<Users> mUploads;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_users, container, false);
        RecyclerView = root.findViewById(R.id.UsersRecyclerView);
        RecyclerView.setHasFixedSize(true);
        RecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ProgressCircle = root.findViewById(R.id.progress_circle);
        mUploads = new ArrayList<>();
        DatabaseReference mDatabaseRef = FirebaseDatabase.getInstance().getReference("Users");
        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Users upload = postSnapshot.getValue(Users.class);
                    mUploads.add(upload);
                }

                Adapter = new UserAdapter(getContext(), mUploads);

                RecyclerView.setAdapter(Adapter);
                ProgressCircle.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                ProgressCircle.setVisibility(View.INVISIBLE);
            }
        });
        return root;
    }
}