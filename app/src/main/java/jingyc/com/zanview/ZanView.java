package jingyc.com.zanview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.OvershootInterpolator;

import jingyc.com.zanview.utils.UIUtil;

public class ZanView extends View {
    private static final float SCALE_MIN = 0.9f;
    private static final float SCALE_MAX = 1f;

    //圆圈颜色
    private static final int START_COLOR = Color.parseColor("#00e24d3d");
    private static final int END_COLOR = Color.parseColor("#88e24d3d");
    private OnZanClickListener mOnZanClickListener;

    private Bitmap mUnZanBitmap;
    private Bitmap mShiningBitmap;
    private Bitmap mZanedBitmap;

    private Paint mBitmapPaint;
    private Paint mCirclePaint;

    float mZanBitmapPointX = 0f;
    float mZanBitmapPointY = 0f;

    float mShiningBitmapX = 0f;
    float mShiningBitmapY = 0f;

    float mZanBitmapWidth = 0f;
    float mZanBitmapHeight = 0f;

    float mCirclePointX = 0f;
    float mCirclePointY = 0f;

    public boolean mZanState = false;

    Path mClipPath;
    float mRadius;
    float mRadiusMax;
    float mRadiusMin;

    private RectF mZanViewRectF;
    private Paint mRectFPaint;

    public void setZanOnClickListener(OnZanClickListener mOnZanClickListener) {
        this.mOnZanClickListener = mOnZanClickListener;
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

    {
        mUnZanBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap
                .ic_messages_like_unselected);
        mZanedBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap
                .ic_messages_like_selected);
        mShiningBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap
                .ic_messages_like_selected_shining);

        mZanBitmapWidth = mUnZanBitmap.getWidth();
        mZanBitmapHeight = mUnZanBitmap.getHeight();

        mBitmapPaint = new Paint();
        mBitmapPaint.setAntiAlias(true);

        mCirclePaint = new Paint();
        mCirclePaint.setAntiAlias(true);
        mCirclePaint.setStyle(Paint.Style.STROKE);
        mCirclePaint.setStrokeWidth(UIUtil.dip2px(getContext(), 2));

        mZanBitmapPointX = getPaddingLeft();
        mZanBitmapPointY = getPaddingTop() + UIUtil.dip2px(getContext(), 8);

        mCirclePointX = mZanBitmapPointX + mZanedBitmap.getWidth() / 2;
        mCirclePointY = mZanBitmapPointY + mZanedBitmap.getHeight() / 2;//UIUtil.dip2px
        // (getContext(), 5)

        mShiningBitmapX = getPaddingLeft() + UIUtil.dip2px(getContext(), 4);
        mShiningBitmapY = getPaddingTop() - UIUtil.dip2px(getContext(), 8);

        mRadiusMax = mZanBitmapWidth / 2 + UIUtil.dip2px(getContext(), 8);
        mRadiusMin = mZanBitmapWidth / 2;

        Log.d("JingYuchun", "bitmapX:" + mZanBitmapPointX + "/bitmapY:" + mZanBitmapPointY);

        mZanViewRectF = new RectF();
        mRectFPaint = new Paint();
        mRectFPaint.setColor(Color.RED);
        mRectFPaint.setStyle(Paint.Style.STROKE);
        mRectFPaint.setStrokeWidth(2);
        mRectFPaint.setAntiAlias(true);

    }

    public boolean getZanState() {
        return mZanState;
    }

    public void setiZanState(boolean zanstate) {
        mZanState = zanstate;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = UIUtil.getDefaultSize(widthMeasureSpec, getContentWidth() + getPaddingLeft()
                + getPaddingRight());
        int height = UIUtil.getDefaultSize(heightMeasureSpec, getContentHeight() + getPaddingTop
                () + getPaddingBottom());
        Log.d("JingYuchun", "width=" + width + "/ height = " + height);
        if(width < height){
            width = height;
        }else {
            height = width;
        }
        setMeasuredDimension(width, height);
    }

    private int getContentWidth() {
        float minLeft = Math.min(mShiningBitmapX, mZanBitmapPointX);
        float maxRight = Math.max(mShiningBitmapX + mShiningBitmap.getWidth(), mZanBitmapPointX +
                mZanedBitmap.getWidth());
        return (int) (maxRight - minLeft);
    }

    private int getContentHeight() {
        float minTop = Math.min(mShiningBitmapY, mZanBitmapPointX);
        float maxBottom = Math.max(mShiningBitmapY + mShiningBitmap.getHeight(), mZanBitmapPointY
                + mZanedBitmap.getHeight());
        return (int) (maxBottom - minTop);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.d("JingYuchun", "ZanView onLayout: left:" + left + " top:" + top + " right:" +
                (right) + " bottom:" + bottom);
        mZanViewRectF.left = left;
        mZanViewRectF.top = top;
        mZanViewRectF.right = right;
        mZanViewRectF.bottom = bottom;
    }

    private void setZanScalAnimator(float scale) {
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);
        mZanedBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap
                .ic_messages_like_selected);
        mZanedBitmap = Bitmap.createBitmap(mZanedBitmap, 0, 0, mZanedBitmap.getWidth(),
                mZanedBitmap.getHeight(),
                matrix, true);
        postInvalidate();
    }

    private void setZanShiningScale(float scale) {
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);
        mShiningBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap
                .ic_messages_like_selected_shining);
        mShiningBitmap = Bitmap.createBitmap(mShiningBitmap, 0, 0, mShiningBitmap.getWidth(),
                mShiningBitmap.getHeight(),
                matrix, true);
        postInvalidate();
    }

    public void setZanCircleAnimator(float radius) {
        mRadius = radius;
        mClipPath = new Path();
        mClipPath.addCircle(mCirclePointX, mCirclePointY, mRadius, Path.Direction.CW);
        float fraction = (mRadiusMax - radius) / (mRadiusMax - mRadiusMin);
        mCirclePaint.setColor((int) UIUtil.evaluate(fraction, START_COLOR, END_COLOR));
        postInvalidate();
    }

    private void setZanNormalScalAnimator(float scale) {
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);
        mUnZanBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap
                .ic_messages_like_unselected);
        mUnZanBitmap = Bitmap.createBitmap(mUnZanBitmap, 0, 0, mUnZanBitmap.getWidth(),
                mUnZanBitmap.getHeight(),
                matrix, true);
        postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (null != mZanViewRectF) {
            canvas.drawRect(mZanBitmapPointX,mShiningBitmapY,mZanBitmapPointX+mZanBitmapWidth,mShiningBitmapY + mZanBitmapHeight + mShiningBitmap.getHeight(), mRectFPaint);//(20,20,320,320)
        }
        if (mZanState) {
            if (mClipPath != null) {
                canvas.save();
                canvas.clipPath(mClipPath);
                canvas.drawBitmap(mShiningBitmap, mShiningBitmapX, mShiningBitmapY, mBitmapPaint);
                canvas.restore();
                canvas.drawCircle(mCirclePointX, mCirclePointY, mRadius, mCirclePaint);
            }
            canvas.drawBitmap(mZanedBitmap, mZanBitmapPointX, mZanBitmapPointY, mBitmapPaint);
        } else {
            canvas.drawBitmap(mUnZanBitmap, mZanBitmapPointX, mZanBitmapPointY, mBitmapPaint);
        }
    }

    public void startAnimator() {
        if (mZanState) {
            //拇指缩放动画
            ObjectAnimator zanScalAnimator = ObjectAnimator.ofFloat(this, "zanScalAnimator",
                    SCALE_MIN, SCALE_MAX);
            zanScalAnimator.setInterpolator(new OvershootInterpolator());
            zanScalAnimator.setDuration(150);
            zanScalAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    setZanNormalScalAnimator(SCALE_MAX);
                    mZanState = false;
                    mOnZanClickListener.onZanCancle();
                }
            });
            zanScalAnimator.start();
        } else {
            ObjectAnimator normalScalAnimator = ObjectAnimator.ofFloat(this,
                    "zanNormalScalAnimator", SCALE_MAX, SCALE_MIN);
            normalScalAnimator.setDuration(150);
            //拇指缩放动画
            ObjectAnimator zanScalAnimator = ObjectAnimator.ofFloat(this, "zanScalAnimator",
                    SCALE_MIN, SCALE_MAX);
            zanScalAnimator.setInterpolator(new OvershootInterpolator());
            zanScalAnimator.setDuration(150);
            //zanScalAnimator.start();
            //圆圈动画
            ObjectAnimator zanCircleAnimator = ObjectAnimator.ofFloat(this, "zanCircleAnimator",
                    mRadiusMin, mRadiusMax);
            zanCircleAnimator.setDuration(100);
            //zanCircleAnimator.start();
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.playTogether(zanScalAnimator, zanCircleAnimator);
            //animatorSet.play(zanScalAnimator).with();
            animatorSet.play(zanScalAnimator).after(normalScalAnimator);
            animatorSet.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    mOnZanClickListener.onZanSucess();
                }

                @Override
                public void onAnimationStart(Animator animation) {
                    super.onAnimationStart(animation);
                    mZanState = true;
                }
            });
            animatorSet.start();
        }
    }

    public float getZanBitmapWidth() {
        return mZanBitmapWidth;
    }

    public float getZanBitmapHeight() {
        return mZanBitmapHeight;
    }

    /**
     * 定义点击事件
     */
    public interface OnZanClickListener {
        void onZanSucess();

        void onZanCancle();
    }
}
