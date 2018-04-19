package jingyc.com.zanview;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

public class ZanView extends android.support.v7.widget.AppCompatImageView {
    private OnClickListener mOnClickListener;

    public void setOnClickListener(OnClickListener onClickListener) {
        mOnClickListener = onClickListener;
    }

    public ZanView(Context context) {
        super(context);
    }

    public ZanView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ZanView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

    }

    /**
     * 定义点击事件
     */
    public interface OnClickListener{
        void OnClick();
    }
}
