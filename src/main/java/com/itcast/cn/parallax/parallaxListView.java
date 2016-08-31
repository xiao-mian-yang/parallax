package com.itcast.cn.parallax;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.ListView;

/**
 * Created by 吴广东 on 2016/8/31.
 */
public class parallaxListView extends ListView {

    private int originalHeight;
    private ImageView parallaxImage;
    private int screenWidth;
    private int maxHeight;

    public parallaxListView(Context context) {
        super(context);
        init();
    }

    public parallaxListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public parallaxListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    //初始化
    private void init() {
        //获取布局文件里面定义的空间的真实的高度
        originalHeight = (int)getResources().getDimension(R.dimen.height);
        WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        screenWidth = windowManager.getDefaultDisplay().getWidth();

    }

    /**
     * 对传进来的控件计算，获取最大的高度
     * @param imageView
     */

    public  void setParallaxImage(ImageView imageView){
        this.parallaxImage = imageView;

        //计算宽和高
        float ratio = parallaxImage.getDrawable().getIntrinsicWidth()*1f/parallaxImage.getDrawable().getIntrinsicHeight();
        maxHeight = (int) (screenWidth/ratio);
    }


    @Override
    protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX, int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
        //如果是手指拖到头，并且是顶部到头
        if(deltaY<0 && isTouchEvent){
            //增高iamgeView的高度
            int newheight = parallaxImage.getHeight() - deltaY/3;
            if(newheight > maxHeight){
                newheight = maxHeight;
            }
            ViewGroup.LayoutParams params = parallaxImage.getLayoutParams();
            params.height = newheight;
            parallaxImage.setLayoutParams(params);
        }
        return super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX, maxOverScrollY, isTouchEvent);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if(ev.getAction() == MotionEvent.ACTION_UP){
            ValueAnimator animator = ValueAnimator.ofInt(parallaxImage.getHeight(),originalHeight);

            //动画的监听改变
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    //获取动画的值
                    int animatedValue = (int) valueAnimator.getAnimatedValue();

                    //将animatorValue设置给ImageView的高度
                    ViewGroup.LayoutParams params = parallaxImage.getLayoutParams();
                    params.height = animatedValue;
                    parallaxImage.setLayoutParams(params);

                }
            });
            animator.setInterpolator(new OvershootInterpolator());
            animator.setDuration(1000).start();

        }
        return super.onTouchEvent(ev);
    }
}
