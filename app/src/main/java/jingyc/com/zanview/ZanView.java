package jingyc.com.zanview;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
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
        mUnZanBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_messages_like_unselected);
        mZanedBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_messages_like_selected);
        mShiningBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_messages_like_selected_shining);

        mZanBitmapWidth = mUnZanBitmap.getWidth();
        mZanBitmapHeight = mUnZanBitmap.getHeight();

        mBitmapPaint = new Paint();
        mBitmapPaint.setAntiAlias(true);

        mCirclePaint = new Paint();
        mCirclePaint.setAntiAlias(true);
        mCirclePaint.setStyle(Paint.Style.STROKE);
        mCirclePaint.setStrokeWidth(UIUtil.dip2px(getContext(), 2));


    }

    public boolean getZanState() {
        return mZanState;
    }

    public void setiZanState(boolean zanstate) {
        mZanState = zanstate;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //Logger.i("widthMeasureSpec:"+getMeasuredWidth());
        //Logger.i("heightMeasureSpec:"+getMeasuredHeight());
        mCirclePointX = getMeasuredWidth() / 2;
        mCirclePointY = getMeasuredHeight() / 2;

        mZanBitmapPointX = mCirclePointX - mZanBitmapWidth / 2;
        mZanBitmapPointY = mCirclePointY - mZanBitmapHeight / 2;

        mShiningBitmapX = mZanBitmapPointX + UIUtil.dip2px(getContext(), 2);
        mShiningBitmapY = mZanBitmapPointY - UIUtil.dip2px(getContext(), 16);

        mRadiusMax = mZanBitmapWidth/2+ UIUtil.dip2px(getContext(), 8);
        mRadiusMin = UIUtil.dip2px(getContext(), 2);

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        //Logger.i("left:"+left+",top:"+top+",right:"+right+",bottom:"+bottom);
    }
    private void setZanScalAnimator(float scale) {
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);
        mUnZanBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_messages_like_selected);
        mUnZanBitmap = Bitmap.createBitmap(mUnZanBitmap, 0, 0, mUnZanBitmap.getWidth(), mUnZanBitmap.getHeight(),
                matrix, true);
        postInvalidate();
    }
    private void setZanShiningScale(float scale) {
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);
        mShiningBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_messages_like_selected_shining);
        mShiningBitmap = Bitmap.createBitmap(mShiningBitmap, 0, 0, mShiningBitmap.getWidth(), mShiningBitmap.getHeight(),
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
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //Logger.i("("+mZanBitmapPointX+","+mZanBitmapPointY+")");
        if(mZanState){
            if (mClipPath != null) {
                canvas.save();
                canvas.clipPath(mClipPath);
                canvas.drawBitmap(mShiningBitmap, mShiningBitmapX, mShiningBitmapY, mBitmapPaint);
                canvas.restore();
                canvas.drawCircle(mCirclePointX, mCirclePointY, mRadius,mCirclePaint);
            }
            canvas.drawBitmap(mZanedBitmap,mZanBitmapPointX,mZanBitmapPointY,mBitmapPaint);
        }else {
            canvas.drawBitmap(mUnZanBitmap,mZanBitmapPointX,mZanBitmapPointY,mBitmapPaint);
        }
    }
    public void startAnimator() {
        //拇指缩放动画
        ObjectAnimator zanScalAnimator = ObjectAnimator.ofFloat(this,"zanScalAnimator",SCALE_MIN,SCALE_MAX);
        zanScalAnimator.setInterpolator(new OvershootInterpolator());
        zanScalAnimator.setDuration(300);
        //zanScalAnimator.start();
        //圆圈动画
        ObjectAnimator zanCircleAnimator = ObjectAnimator.ofFloat(this,"zanCircleAnimator",mRadiusMin,mRadiusMax);
        zanCircleAnimator.setDuration(300);
        zanCircleAnimator.start();
//        AnimatorSet animatorSet = new AnimatorSet();
//        animatorSet.play(zanCircleAnimator);
//        animatorSet.addListener(new AnimatorListenerAdapter() {
//            @Override
//            public void onAnimationCancel(Animator animation) {
//                super.onAnimationCancel(animation);
//                mOnZanClickListener.onZanCancle();
//            }
//
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                super.onAnimationEnd(animation);
//                mZanState = false;
//                mOnZanClickListener.onZanSucess();
//            }
//
//            @Override
//            public void onAnimationRepeat(Animator animation) {
//                super.onAnimationRepeat(animation);
//            }
//
//            @Override
//            public void onAnimationStart(Animator animation) {
//                super.onAnimationStart(animation);
//                mZanState = true;
//            }
//
//            @Override
//            public void onAnimationPause(Animator animation) {
//                super.onAnimationPause(animation);
//            }
//
//            @Override
//            public void onAnimationResume(Animator animation) {
//                super.onAnimationResume(animation);
//            }
//        });
//        animatorSet.start();


    }


    /**
     * 定义点击事件
     */
    public interface OnZanClickListener{
        void onZanSucess();

        void onZanCancle();
    }
}
