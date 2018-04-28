package jingyc.com.zanview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.orhanobut.logger.Logger;

public class ZanViewLayout extends ViewGroup implements View.OnClickListener {

    private ZanView mZanView;
    private CountView mCountView;
    private RectF mViewGroupRectF;
    private Paint mRectFPaint;
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
       addView(mZanView);

       mCountView = new CountView(getContext());
       //addView(mCountView);

       mRectFPaint = new Paint();
       mRectFPaint.setAntiAlias(true);
       mRectFPaint.setColor(Color.BLUE);
       mRectFPaint.setStyle(Paint.Style.STROKE);
       mRectFPaint.setStrokeWidth(3);
       setOnClickListener(this);
    }


    public ZanViewLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setPadding(int left, int top, int right, int bottom) {
        super.setPadding(left, top, right, bottom);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //1.调用每个子 View 的 measure() 来计算子 View 的尺寸
        //2.计算子 View 的位置并保存子 View 的位置和尺寸
        //3.计算自己的尺寸并用 setMeasuredDimension() 保存

        int real_width_group = 0;
        int real_height_group = 0;

        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            measureChild(child,widthMeasureSpec,heightMeasureSpec);
            real_width_group += child.getMeasuredWidth();
            real_height_group = Math.max(real_height_group,child.getMeasuredHeight());
        }
        real_width_group += getPaddingLeft() + getPaddingRight();
        real_height_group += getPaddingTop() + getPaddingBottom();
        Logger.d("real_width_group = "+ real_width_group);
        Logger.d("real_height_group = "+ real_height_group);
        setMeasuredDimension(real_width_group,real_height_group);

    }
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        mViewGroupRectF = new RectF(l,t,r,b);
        Logger.d("ZanViewLayout changed:"+changed + " left:"+l +"top:"+t+"right:"+r+"bottom:"+b);
        int childcount = getChildCount();
        for (int i = 0; i < childcount; i++) {
            View childview = getChildAt(i);
            int childviewwidth = childview.getMeasuredWidth();
            int childviewheight = childview.getMeasuredHeight();
            childview.layout(l , t, l + childviewwidth, t+ childviewheight);
            l += childviewwidth;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Logger.d("===========layout onDraw=========");
        if(null != mViewGroupRectF){
            canvas.drawRect(mViewGroupRectF,mRectFPaint);
        }else {
            Logger.d("layout区域为空 mViewGroupRectF = null");
        }
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
