/*******************************************************************************
 * Copyright 2011, 2012 Chris Banes.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.handmark.pulltorefresh.library.internal;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Matrix;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView.ScaleType;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Orientation;
import com.handmark.pulltorefresh.library.R;

public class RotateLoadingLayout extends LoadingLayout {

	static final int ROTATION_ANIMATION_DURATION = 1200;

	private final Animation mRotateAnimation;
	private final Matrix mHeaderImageMatrix;

	private float mRotationPivotX, mRotationPivotY;

	private final boolean mRotateDrawableWhilePulling;

	private AnimationDrawable mAnimationDrawable;

	public RotateLoadingLayout(Context context, Mode mode, Orientation scrollDirection, TypedArray attrs) {
		super(context, mode, scrollDirection, attrs);

		mRotateDrawableWhilePulling = attrs.getBoolean(R.styleable.PullToRefresh_ptrRotateDrawableWhilePulling, true);

		mHeaderImage.setScaleType(ScaleType.MATRIX);
		mHeaderImageMatrix = new Matrix();
		mHeaderImage.setImageMatrix(mHeaderImageMatrix);

		mRotateAnimation = new RotateAnimation(0, 720, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		mRotateAnimation.setInterpolator(ANIMATION_INTERPOLATOR);
		mRotateAnimation.setDuration(ROTATION_ANIMATION_DURATION);
		mRotateAnimation.setRepeatCount(Animation.INFINITE);
		mRotateAnimation.setRepeatMode(Animation.RESTART);

        mHeaderImage.setImageResource(R.drawable.animation_loading);
        mAnimationDrawable = (AnimationDrawable) mHeaderImage.getDrawable();
//		my_head_img.setImageResource(R.drawable.animation_loading);
//		mAnimationDrawable = (AnimationDrawable) my_head_img.getDrawable();
	}

	public void onLoadingDrawableSet(Drawable imageDrawable) {
		if (null != imageDrawable) {
			mRotationPivotX = Math.round(imageDrawable.getIntrinsicWidth() / 2f);
			mRotationPivotY = Math.round(imageDrawable.getIntrinsicHeight() / 2f);
		}
	}

	protected void onPullImpl(float scaleOfLayout) {
		float angle;

		if(mMode== PullToRefreshBase.Mode.PULL_FROM_START){
			mHeaderImage.setVisibility(VISIBLE);
			mHeaderProgress.setVisibility(GONE);
			mHeaderText.setVisibility(GONE);
			mSubHeaderText.setVisibility(GONE);
		}else{

			if (mRotateDrawableWhilePulling) {
				angle = scaleOfLayout * 90f;
			} else {
				angle = Math.max(0f, Math.min(180f, scaleOfLayout * 360f - 180f));
			}
			mHeaderImageMatrix.setRotate(angle, mRotationPivotX, mRotationPivotY);
			mHeaderImage.setImageMatrix(mHeaderImageMatrix);

			mHeaderProgress.setVisibility(VISIBLE);
			mHeaderText.setVisibility(VISIBLE);
			mSubHeaderText.setVisibility(VISIBLE);
			mHeaderImage.setVisibility(GONE);
		}

//		if(mMode== PullToRefreshBase.Mode.PULL_FROM_START){
//			my_head_img.setVisibility(VISIBLE);
//			ll_my_inner.setVisibility(INVISIBLE);
//		}else{
//			my_head_img.setVisibility(INVISIBLE);
//			ll_my_inner.setVisibility(VISIBLE);
//		}
	}

	@Override
	protected void refreshingImpl() {
//		mHeaderImage.startAnimation(mRotateAnimation);

		mAnimationDrawable.start();
	}

	@Override
	protected void resetImpl() {
		mHeaderImage.clearAnimation();
		resetImageRotation();

		if(mMode== PullToRefreshBase.Mode.PULL_FROM_START){
			mHeaderImage.setVisibility(VISIBLE);
			mHeaderProgress.setVisibility(GONE);
			mHeaderText.setVisibility(GONE);
			mSubHeaderText.setVisibility(GONE);
		}else{
			mHeaderProgress.setVisibility(VISIBLE);
			mHeaderText.setVisibility(VISIBLE);
			mSubHeaderText.setVisibility(VISIBLE);
			mHeaderImage.setVisibility(GONE);
		}
	}

	private void resetImageRotation() {
		if (null != mHeaderImageMatrix) {
			mHeaderImageMatrix.reset();
			mHeaderImage.setImageMatrix(mHeaderImageMatrix);
		}
	}

	@Override
	protected void pullToRefreshImpl() {
		// NO-OP
	}

	@Override
	protected void releaseToRefreshImpl() {
		// NO-OP
	}

	@Override
	protected int getDefaultDrawableResId() {
		return R.drawable.default_ptr_rotate;
	}

}
