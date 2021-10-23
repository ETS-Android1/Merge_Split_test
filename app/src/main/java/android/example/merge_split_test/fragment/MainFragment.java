package android.example.merge_split_test.fragment;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.example.merge_split_test.R;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.IOException;
import java.util.ArrayList;

public class MainFragment extends Fragment {

    private final static int GALLERY_REQUEST_CODE = 1;
    private ImageView mImageView;
    private Bitmap mBitmap;

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        mImageView = view.findViewById(R.id.image_View);

        Button mLoadImageButton = view.findViewById(R.id.load_Image_Button);
        mLoadImageButton.setOnClickListener(v -> {
            Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            try {
                startActivityForResult(galleryIntent, GALLERY_REQUEST_CODE);
            } catch (Exception e) {
                Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        });

        Button mSplitButton = view.findViewById(R.id.split_Image_Button);
        mSplitButton.setOnClickListener(v -> {
            getParentFragmentManager()
                    .beginTransaction()
                    .remove(MainFragment.this)
                    .commit();

            getParentFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container,
                            new SplitViewFragment(splitImage(mBitmap, 25)), "split")
                    .commit();
        });

        return view;
    }

    private ArrayList<Bitmap> splitImage(Bitmap mBitmap, int chunkNumbers) {

        //For the number of rows and columns of the grid to be displayed
        int rows, cols;

        //For height and width of the small image chunks
        int chunkHeight, chunkWidth;

        //To store all the small image chunks in bitmap format in this list
        ArrayList<Bitmap> mChunkedImages = new ArrayList<>(chunkNumbers);

        //Getting the scaled bitmap of the source image
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(mBitmap, mBitmap.getWidth(), mBitmap.getHeight(), true);

        rows = cols = (int) Math.sqrt(chunkNumbers);
        chunkHeight = mBitmap.getHeight() / rows;
        chunkWidth = mBitmap.getWidth() / cols;

        //xCoord and yCoord are the pixel positions of the image chunks
        int yCoord = 0;
        for (int x = 0; x < rows; x++) {
            int xCoord = 0;
            for (int y = 0; y < cols; y++) {
                mChunkedImages.add(Bitmap.createBitmap(scaledBitmap, xCoord, yCoord, chunkWidth, chunkHeight));
                xCoord += chunkWidth;
            }
            yCoord += chunkHeight;
        }
        return mChunkedImages;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_REQUEST_CODE) {
            try {
                if (resultCode == RESULT_OK && data != null) {
                    mBitmap = uriToBitmap(data.getData());
                    mImageView.setImageBitmap(mBitmap);
                }
            } catch (Exception e) {
                Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
        }
    }

    private Bitmap uriToBitmap(Uri uri) {
        Bitmap bm = null;
        try {
            bm = MediaStore.Images.Media
                    .getBitmap(requireActivity().getApplicationContext().getContentResolver(), uri);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bm;
    }

}