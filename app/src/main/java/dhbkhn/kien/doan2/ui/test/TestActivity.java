package dhbkhn.kien.doan2.ui.test;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import dhbkhn.kien.doan2.R;
import dhbkhn.kien.doan2.ui.home.HomeActivity;

public class TestActivity extends AppCompatActivity {

    public static Intent doTestActivity(Context context) {
        Intent intent = new Intent(context, HomeActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
    }
}
