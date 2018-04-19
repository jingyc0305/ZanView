package jingyc.com.zanview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private ZanView mZanView ;
    private TextView mTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mZanView = (ZanView) findViewById(R.id.zan_icon_iv);
        mTextView = (TextView) findViewById(R.id.zan_sum_tv);

        mZanView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mZanView.setImageResource(R.mipmap.zan_click);
            }
        });
    }
}
