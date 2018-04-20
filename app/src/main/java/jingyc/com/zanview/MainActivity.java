package jingyc.com.zanview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.orhanobut.logger.Logger;

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

                Logger.i("点赞+1");
            }

            @Override
            public void onZanCancle() {
                Logger.i("点赞-1");
            }
        });
    }
}
