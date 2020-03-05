package com.iiit.iiitkalyani.ui;

import android.content.Intent;
import android.net.Uri;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.iiit.iiitkalyani.Blog;
import com.iiit.iiitkalyani.Blog_Post;
import com.iiit.iiitkalyani.Adapter.BlogAdapter;
import com.iiit.iiitkalyani.Adapter.Download;
import com.iiit.iiitkalyani.R;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private RecyclerView recyclerView;
    private BlogAdapter Adapter;
    private ProgressBar ProgressCircle;
    private List<Download> Downloads;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = root.findViewById(R.id.view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        FloatingActionButton btn = root.findViewById(R.id.btnupload);
        ProgressCircle = root.findViewById(R.id.progress_circle);
        Downloads = new ArrayList<>();
        DatabaseReference mDatabaseRef = FirebaseDatabase.getInstance().getReference("blog");
        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Download download = postSnapshot.getValue(Download.class);
                    Downloads.add(download);
                }

                Adapter = new BlogAdapter(getContext(), Downloads);
                LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                layoutManager.setReverseLayout(true);
                layoutManager.setStackFromEnd(true);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(Adapter);
                ProgressCircle.setVisibility(View.INVISIBLE);

                Adapter.setOnItemClickListener(new BlogAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(String ID, int position, Uri img, String title, String des, String name) {
                        Toast.makeText(getContext(),
                                "Post by "+name, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getContext(), Blog.class);
                        intent.putExtra("pos", position);
                        intent.setData(img);
                        intent.putExtra("title", title);
                        intent.putExtra("des", des);
                        startActivity(intent);
                    }

                    @Override
                    public void onDeleteClick(Uri img) {
                        Toast.makeText(getContext(),"Downloading...",Toast.LENGTH_SHORT).show();

                    }

                });
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(),databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                ProgressCircle.setVisibility(View.INVISIBLE);
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Blog_Post.class);
                startActivity(intent);
            }
        });
        return root;
    }


}