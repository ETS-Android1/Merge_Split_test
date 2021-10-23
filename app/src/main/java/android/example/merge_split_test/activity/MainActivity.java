package android.example.merge_split_test.activity;

import android.example.merge_split_test.R;
import android.example.merge_split_test.fragment.MainFragment;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container,
                        new MainFragment(), "main")
                .commit();
    }
}