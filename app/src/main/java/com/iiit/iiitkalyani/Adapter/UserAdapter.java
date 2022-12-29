package com.iiit.iiitkalyani.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.iiit.iiitkalyani.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {
    private Context mContext;
    private List<Users> mUploads;

    public UserAdapter(Context context, List<Users> uploads) {
        mContext = context;
        mUploads = uploads;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.users_item, parent, false);
        return new UserViewHolder(v);
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        Users uploadCurrent = mUploads.get(position);
        holder.textViewName.setText(uploadCurrent.getName());
        holder.textViewEmail.setText(uploadCurrent.getEmail());
        Picasso.get().load(uploadCurrent.getImageUrl()).placeholder(R.drawable.profile).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return mUploads.size();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewName;
        private TextView textViewEmail;
        private ImageView imageView;

        private UserViewHolder(View itemView) {
            super(itemView);

            textViewName = itemView.findViewById(R.id.Name);
            textViewEmail = itemView.findViewById(R.id.Email);
            imageView = itemView.findViewById(R.id.UserImage);
        }
    }
}