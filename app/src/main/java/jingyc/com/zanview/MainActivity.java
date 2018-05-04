package jingyc.com.zanview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;


public class MainActivity extends AppCompatActivity {

    ZanViewLayout mZanViewLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mZanViewLayout = findViewById(R.id.zanViewLayout);

        mZanViewLayout.setZanOnClickListener(new ZanView.OnZanClickListener() {
            @Override
            public void onZanSucess() {

                Log.i("JingYuchun","点赞+1");
            }

            @Override
            public void onZanCancle() {
                Log.i("JingYuchun","点赞-1");
            }
        });
    }
}
