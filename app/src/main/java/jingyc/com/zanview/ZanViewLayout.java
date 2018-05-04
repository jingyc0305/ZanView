package jingyc.com.zanview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;


public class ZanViewLayout extends LinearLayout implements View.OnClickListener {

    private ZanView mZanView;
    private CountView mCountView;
    private Paint mRectFPaint;
    private RectF mRectF;
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
       addView(mZanView,getZanLayoutParams());
       mCountView = new CountView(getContext());
       addView(mCountView,getZanLayoutParams());

       mRectFPaint = new Paint();
       mRectFPaint.setAntiAlias(true);
       mRectFPaint.setColor(Color.BLUE);
       mRectFPaint.setStyle(Paint.Style.STROKE);
       mRectFPaint.setStrokeWidth(3);
       setOnClickListener(this);

       setWillNotDraw(false);
    }

    private LayoutParams getZanLayoutParams() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.leftMargin = getPaddingLeft();
        params.topMargin += getPaddingTop();
        params.bottomMargin = getPaddingBottom();
        return params;
    }


    public ZanViewLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        Log.d("JingYuchun","l="+l+"/t="+t+"/r="+r+"/b="+b);
        mRectF = new RectF(l,t,r,b);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //Log.i("JingYuchun","===========layout onDraw=========");
        canvas.drawRect(0,0,446,232,mRectFPaint);
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
