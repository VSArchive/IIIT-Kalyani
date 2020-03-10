package com.iiit.iiitkalyani.Adapter;

import   android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.iiit.iiitkalyani.R;
import com.squareup.picasso.Picasso;
import java.util.List;
import de.hdodenhof.circleimageview.CircleImageView;

public class BlogAdapter extends RecyclerView.Adapter<BlogAdapter.ViewHolder> {
    private Context Context;
    private static List<Download> downloads;
    private static OnItemClickListener listener;

    public BlogAdapter(Context context, List<Download> Download) {
        Context = context;
        downloads = Download;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(Context).inflate(R.layout.blog_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        Download uploadCurrent = downloads.get(position);
        String temp = uploadCurrent.getdescription();
        int x = temp.length();
        String description;
        if (x <= 35){
            description = temp;
        } else {
            description = temp.substring(0,35).trim() + "...";
        }
        //Toast.makeText(Context,uploadCurrent.getProfileUrl(),Toast.LENGTH_SHORT).show();
        Picasso.get().load(uploadCurrent.getProfileUrl()).placeholder(R.drawable.profile).into(holder.profile_image);
        holder.pro_name.setText(uploadCurrent.getname());
        holder.title.setText(uploadCurrent.gettitle());
        holder.des.setText(description);
        Picasso.get().load(uploadCurrent.getImageUrl()).placeholder(R.drawable.loading).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return downloads.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView des;
        private TextView pro_name;
        private ImageView imageView;
        private CircleImageView profile_image;

        private ViewHolder(View itemView) {
            super(itemView);
            profile_image = itemView.findViewById(R.id.Profile_image);
            pro_name = itemView.findViewById(R.id.profile_name);
            des = itemView.findViewById(R.id.post_description);
            title = itemView.findViewById(R.id.post_title);
            imageView = itemView.findViewById(R.id.post_image);
            final ImageButton btndownload = itemView.findViewById(R.id.btndownload);
            final ImageButton btnlike = itemView.findViewById(R.id.btnlike);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener != null) {
                        Download uploadCurrent = downloads.get(position);
                        String ID;
                        ID = uploadCurrent.toString();
                        Uri img = uploadCurrent.getImageUrl();
                        String title = uploadCurrent.gettitle();
                        String des = uploadCurrent.getdescription();
                        String name = uploadCurrent.getname();
                        listener.onItemClick(ID,position,img,title,des,name);
                    }
                }
            });
            btndownload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION){
                        Download uploadCurrent = downloads.get(position);
                        Uri img = uploadCurrent.getImageUrl();
                        listener.onDeleteClick(img);
                    }
                }
            });
            btnlike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION){
                        String id = FirebaseAuth.getInstance().getUid();
                        listener.onLikeClick(id);
                        DatabaseReference like = FirebaseDatabase.getInstance().getReference().child("likes");
                        like.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                               // if (dataSnapshot.child(po))
                                btnlike.setBackgroundResource(R.drawable.like);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                    }
                }
            });

        }
    }
    public interface OnItemClickListener {
        void onItemClick(String ID, int position, Uri img, String title, String des, String name);
        void onDeleteClick(Uri img);
        void onLikeClick(String id);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        BlogAdapter.listener = listener;
    }
}