package jingyc.com.zanview;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class ZanViewLayout extends LinearLayout implements View.OnClickListener {

    ZanView mZanView;
    CountView mCountView;
    public ZanViewLayout(Context context) {
        super(context);
    }

    public ZanViewLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void setZanOnClickListener(ZanView.OnZanClickListener mOnZanClickListener) {
       mZanView.setZanOnClickListener(mOnZanClickListener);
    }
    private void init() {
        mZanView = new ZanView(getContext());
        addView(mZanView,getThumbParams());

        mCountView = new CountView(getContext());
        addView(mCountView,getThumbParams());

        setOnClickListener(this);
    }
    private LayoutParams getThumbParams() {
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.leftMargin = getPaddingLeft();
        params.topMargin += getPaddingTop();
        params.bottomMargin = getPaddingBottom();
        return params;
    }
    public ZanViewLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

    }

    @Override
    public void onClick(View v) {
        mZanView.startAnimator();
    }
}
