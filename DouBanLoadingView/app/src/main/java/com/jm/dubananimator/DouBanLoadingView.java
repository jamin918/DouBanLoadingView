package com.jm.dubananimator;

import android.animation.Animator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

/**
 * @author jamin
 * @date 2016/9/6
 * @desc
 */
public class DouBanLoadingView extends View {

    private float Width;
    private float Height;
    private Paint mPaint;
    private float mViewWidth;

    private ValueAnimator animator;
    private float animatedValue;
    private long animatorDuration = 3000;
    private TimeInterpolator timeInterpolator = new DecelerateInterpolator();

    private static final String TAG = "DouBanLoadingView";

    public DouBanLoadingView(Context context) {
        super(context);

        init();
    }

    public DouBanLoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    public DouBanLoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }


    private void init() {
//        initAnimator(animatorDuration);
        mPaint=new Paint();
        //设置画笔样式为描边
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeCap(Paint.Cap.ROUND);//圆角笔触
        mPaint.setColor(Color.rgb(97, 195, 109));
        mPaint.setStrokeWidth(10);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        Width=MeasureSpec.getSize(widthMeasureSpec);
        mViewWidth=Width;

        Height=MeasureSpec.getSize(heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.translate(Width/2,Height/2);
        doubanAnimator(canvas, mPaint);
    }


    private void initAnimator(long duration){

        DouBanLoadingView.this.setVisibility(View.VISIBLE);

        if (animator !=null &&animator.isRunning()){
            animator.cancel();
            animator.start();
        }else {
            animator= ValueAnimator.ofFloat(0, 855).setDuration(duration);
            animator.setInterpolator(timeInterpolator);
            animator.setRepeatCount(ValueAnimator.INFINITE);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    animatedValue = (float) animation.getAnimatedValue();
                    invalidate();
                }
            });
            animator.start();
        }
    }


    private void doubanAnimator(Canvas canvas, Paint mPaint){

        float point = Math.min(mViewWidth,mViewWidth)*0.06f/2;
        float r = point*(float) Math.sqrt(2);
        RectF rectF = new RectF(-r,-r,r,r);
        canvas.save();

        // rotate
        if (animatedValue>=135){
            canvas.rotate(animatedValue-135);
        }

        // draw mouth
        float startAngle=0, sweepAngle=0;

        if (animatedValue<10){
            startAngle = 0;
            sweepAngle = 180;

        } else if (animatedValue<135){
            startAngle = animatedValue +5;
            sweepAngle = 170+animatedValue/3;
        }else if (animatedValue<270){
            startAngle = 135+5;
            sweepAngle = 170+animatedValue/3;
        }else if (animatedValue<630){
            startAngle = 135+5;
            sweepAngle = 260-(animatedValue-270)/5;
        }else if (animatedValue<720){
            startAngle = 135-(animatedValue-630)/2+5;
            sweepAngle = 260-(animatedValue-270)/5;
        }else{
            startAngle = 135-(animatedValue-630)/2-(animatedValue-720)/6+5;
            sweepAngle = 170;
        }
        canvas.drawArc(rectF,startAngle,sweepAngle,false,mPaint);

        mPaint.setStrokeWidth(12);
        // draw eye
        canvas.drawPoints(new float[]{
                -point,-point
                ,point,-point
        },mPaint);

        canvas.restore();
    }


    public void showLoading(){
        initAnimator(animatorDuration);
        invalidate();
    }

    public void stopLoading(){

        if (animator != null && animator.isRunning()){
            animator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {
                    animator.cancel();
//                    DouBanLoadingView.this.setVisibility(View.INVISIBLE);
                }
            });
        }
    }
}
