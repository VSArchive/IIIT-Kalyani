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

public class ImgAdapter extends RecyclerView.Adapter<ImgAdapter.ImageViewHolder> {
    private Context mContext;
    private List<Upload> mUploads;

    public ImgAdapter(Context context, List<Upload> uploads) {
        mContext = context;
        mUploads = uploads;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.image_item, parent, false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        Upload uploadCurrent = mUploads.get(position);
        holder.textViewName.setText(uploadCurrent.getName());
        Picasso.get().load(uploadCurrent.getImageUrl()).placeholder(R.drawable.logo).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return mUploads.size();
    }

    public static class ImageViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewName;
        private ImageView imageView;

        private ImageViewHolder(View itemView) {
            super(itemView);

            textViewName = itemView.findViewById(R.id.text_view_name);
            imageView = itemView.findViewById(R.id.post_image);
        }
    }
}