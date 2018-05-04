package jingyc.com.zanview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.orhanobut.logger.Logger;

import jingyc.com.zanview.utils.UIUtil;

public class CountView extends View {
    TextPaint mTextPaint;
    public CountView(Context context) {
        super(context);

    }

    {
        mTextPaint = new TextPaint();
        mTextPaint.setTextSize(28);
        mTextPaint.setColor(Color.GRAY);
        mTextPaint.setAntiAlias(true);
        setWillNotDraw(false);
    }
    public CountView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CountView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int width = UIUtil.getDefaultSize(widthMeasureSpec, getContentWidth() + getPaddingLeft()
                + getPaddingRight());
        int height = UIUtil.getDefaultSize(heightMeasureSpec,  getContentHeight() + getPaddingTop
                () + getPaddingBottom());
        Log.d("JingYuchun", "width=" + width + "/ height = " + height);
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Logger.i("CountView : left:"+left+",top:"+top+",right:"+right+",bottom:"+bottom);
    }
    private int getContentWidth() {
        return (int) Math.ceil(mTextPaint.measureText(String.valueOf(3)));
    }

    private int getContentHeight() {
        return (int) 28;
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawText("666",0,0,mTextPaint);
    }
}
