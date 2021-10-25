package android.example.merge_split_test.fragment;

import android.example.merge_split_test.R;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class MergeFragment extends Fragment {

    private ArrayList<Bitmap> mChunkedImages;
    private int mChunkNumber;

    public MergeFragment(ArrayList<Bitmap> mChunks, double mChunksNumber) {
        this.mChunkedImages = mChunks;
        this.mChunkNumber = (int) mChunksNumber;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_merge, container, false);

        ImageView mImageView = view.findViewById(R.id.image_View);
        mImageView.setImageBitmap(mergeImage(mChunkedImages));

        Button mDoneButton = view.findViewById(R.id.done_Button);
        mDoneButton.setOnClickListener(v -> {
            getParentFragmentManager()
                    .beginTransaction()
                    .remove(MergeFragment.this)
                    .commit();

            getParentFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container,
                            new MainFragment(), "main")
                    .commit();
        });

        return view;
    }

    private Bitmap mergeImage(ArrayList<Bitmap> mChunkedImages) {
        //Get the width and height of the smaller chunks
        int chunkWidth = mChunkedImages.get(0).getWidth();
        int chunkHeight = mChunkedImages.get(0).getHeight();

        //create a bitmap of a size which can hold the complete image after merging
//        Bitmap bitmap = Bitmap.createBitmap(chunkWidth * 5, chunkHeight * 5, Bitmap.Config.ARGB_4444);
        Bitmap bitmap = Bitmap.createBitmap(chunkWidth * mChunkNumber, chunkHeight * mChunkNumber, Bitmap.Config.ARGB_8888);

        //create a canvas for drawing all those small images
        Canvas canvas = new Canvas(bitmap);
        int count = 0;
        for (int rows = 0; rows < mChunkNumber; rows++) {
            for (int cols = 0; cols < mChunkNumber; cols++) {
                canvas.drawBitmap(mChunkedImages.get(count), chunkWidth * cols, chunkHeight * rows, null);
                count++;
            }
        }
        return bitmap;
    }
}