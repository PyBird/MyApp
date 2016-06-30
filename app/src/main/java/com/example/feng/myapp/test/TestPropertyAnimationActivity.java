package com.example.feng.myapp.test;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.graphics.PointF;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.util.Property;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;

import com.example.feng.myapp.R;
import com.example.feng.myapp.base.BaseActivity;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

public class TestPropertyAnimationActivity extends BaseActivity {

    @ViewInject(R.id.ll_left1) public LinearLayout ll_left1;
    @ViewInject(R.id.ll_right1) public LinearLayout ll_right1;

    @ViewInject(R.id.ll_test) public LinearLayout ll_test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_property_animation);

        x.view().inject(this);

//        propertyValuesHolder(ll_test);

        initEvent();
    }

    private void initEvent(){
        ll_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                propertyValuesHolder(ll_test);
                propertyValuesHolder(ll_left1);
                paowuxian(ll_left1);

                propertyValuesHolder(ll_right1);

                paowuxian(ll_test);
                rotateyAnimRun(ll_test);
                propertyValuesHolder(ll_test);
            }
        });
    }

    public void propertyValuesHolder(View view)
    {
        PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat("alpha", 1f, 0f, 1f);
        PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("scaleX", 1f, 0, 1f);
        PropertyValuesHolder pvhZ = PropertyValuesHolder.ofFloat("scaleY", 1f, 0, 1f);

        ObjectAnimator.ofPropertyValuesHolder(view, pvhX, pvhY,pvhZ).setDuration(2000).start();
    }

    public void rotateyAnimRun(View view)
    {
        ObjectAnimator
                .ofFloat(view, "rotationX", 0.0F, 360.0F)//
                .setDuration(2000)//
                .start();

    }

    public void verticalRun(View view)
    {
        ValueAnimator animator = ValueAnimator.ofFloat(0, 0
                - ll_test.getHeight());
        animator.setTarget(ll_test);
        animator.setDuration(1000).start();
    }

    /**
     * 自由落体
     * @param view
     */
    public void verticalRun2(final View view)
    {
        ValueAnimator animator = ValueAnimator.ofFloat(0, 200);
        animator.setTarget(view);
        animator.setDuration(1000).start();
//      animator.setInterpolator(value)
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
        {
            @Override
            public void onAnimationUpdate(ValueAnimator animation)
            {
                view.setTranslationY((Float) animation.getAnimatedValue());
            }
        });
    }

    /**
     * 抛物线
     * @param view
     */
    public void paowuxian(final View view)
    {

        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setDuration(2000);
        valueAnimator.setObjectValues(new PointF(0, 0));
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setEvaluator(new TypeEvaluator<PointF>()
        {
            // fraction = t / duration
            @Override
            public PointF evaluate(float fraction, PointF startValue,
                                   PointF endValue)
            {
                Log.e("evaluate", fraction * 3 + "");
                // x方向200px/s ，则y方向0.5 * 10 * t
                PointF point = new PointF();
                point.x = 200 * fraction * 3;
                point.y = 0.5f * 200 * (fraction * 3) * (fraction * 3);
                return point;
            }
        });

        valueAnimator.start();
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
        {
            @Override
            public void onAnimationUpdate(ValueAnimator animation)
            {
                PointF point = (PointF) animation.getAnimatedValue();
                view.setX(point.x);
                view.setY(point.y);

            }
        });
    }
}
