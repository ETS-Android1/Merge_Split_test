package android.example.merge_split_test.adapter;

import android.content.Context;
import android.example.merge_split_test.R;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.RecyclerViewHolder> {

    // creating a variable for our context and array list.
    private final Context context;
    private final ArrayList<Bitmap> mChunks;

    public ImageAdapter(Context context, ArrayList<Bitmap> mChunks) {
        this.context = context;
        this.mChunks = mChunks;
    }

    @NonNull
    @Override
    public ImageAdapter.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout, parent, false);
        return new RecyclerViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageAdapter.RecyclerViewHolder holder, int position) {
        Glide.with(context).load(mChunks.get(position)).into(holder.imageIV);
    }

    @Override
    public int getItemCount() {
        return mChunks.size();
    }

    // View Holder Class to handle Recycler View.
    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {

        // creating variables for our views.
        private final ImageView imageIV;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);

            // initializing our views with their ids.
            imageIV = itemView.findViewById(R.id.idIVImage);
        }
    }
}
