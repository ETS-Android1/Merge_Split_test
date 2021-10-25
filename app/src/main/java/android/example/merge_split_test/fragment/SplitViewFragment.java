package android.example.merge_split_test.fragment;

import android.example.merge_split_test.R;
import android.example.merge_split_test.adapter.ImageAdapter;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SplitViewFragment extends Fragment {

    private ArrayList<Bitmap> mChunks;
    private RecyclerView mRecyclerView;
    private ImageAdapter mImageAdapter;
    private int mChunkSize;

    public SplitViewFragment(ArrayList<Bitmap> mBitmapsChunks, int mChunkSize) {
        this.mChunks = mBitmapsChunks;
        this.mChunkSize = mChunkSize;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_split_view, container, false);

        Button mSuperImageButton = view.findViewById(R.id.superImage_Image_Button);
        mSuperImageButton.setOnClickListener(v -> {
            getParentFragmentManager()
                    .beginTransaction()
                    .remove(SplitViewFragment.this)
                    .commit();

            getParentFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container,
                            new MergeFragment(mChunks, Math.sqrt(mChunks.size())))
                    .commit();
        });

        mRecyclerView = view.findViewById(R.id.image_Recycler_View);
        initRecyclerView();

        return view;
    }

    private void initRecyclerView() {
        mImageAdapter = new ImageAdapter(getContext(), mChunks);
        GridLayoutManager manager = new GridLayoutManager(getContext(), (int) Math.sqrt(mChunkSize));

        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mImageAdapter);
    }
}