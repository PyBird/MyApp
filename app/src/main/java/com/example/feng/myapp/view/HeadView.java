package com.example.feng.myapp.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.feng.myapp.R;

/**
 * 自定义顶部导航栏
 */
public class HeadView extends LinearLayout {

	private String title;
	private int bgColor;
	private int leftImageResource;
	private int rightImageResource;
	private String leftText;
	private String rightText;
	private int leftTextColor;
	private int rightTextColor;
	private int titleColor;
//	private float titleTextSize;
	private float rightTextSize;
	private float leftTextSize;
	private boolean showRight;
	private boolean showLeft;
	
	
	private LinearLayout layoutContent;
	private LinearLayout layoutRight;
	private LinearLayout layoutLeft;
	private TextView tvTitle;
	private TextView tvL;
	private TextView tvR;
	private ImageView ivL;
	private ImageView ivR;

	public HeadView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	public HeadView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();

		initAttrs(context, attrs);
	}

	public HeadView(Context context) {
		super(context);
		init();
	}

	/**
	 * 初始化布局
	 */
	private void init() {
		View.inflate(getContext(), R.layout.view_head, this);
		initController();
	}

	private void initAttrs(Context context, AttributeSet attrs) {
		/**
		 * 获取属性值
		 */
		TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.HeadView);
		title = typedArray.getString(R.styleable.HeadView_title);
		bgColor = typedArray.getColor(R.styleable.HeadView_background_color, Color.WHITE);
		leftImageResource = typedArray.getResourceId(R.styleable.HeadView_left_img, 0);
		rightImageResource = typedArray.getResourceId(R.styleable.HeadView_right_img, 0);
		leftText = typedArray.getString(R.styleable.HeadView_left_text);
		rightText = typedArray.getString(R.styleable.HeadView_right_text);
		leftTextColor = typedArray.getColor(R.styleable.HeadView_left_text_color, Color.BLACK);
		rightTextColor = typedArray.getColor(R.styleable.HeadView_right_text_color, Color.BLACK);
		titleColor = typedArray.getColor(R.styleable.HeadView_title_text_color, Color.BLACK);
//		titleColor = typedArray.getColor(R.styleable.TitleView_title_text_color, getResources().getColor(R.color.base_text));
//		titleTextSize = typedArray.getDimension(R.styleable.TitleView_title_text_size, 18.0f);
//		rightTextSize = typedArray.getDimension(R.styleable.TitleView_right_text_size, 16.0f);
//		leftTextSize = typedArray.getDimension(R.styleable.TitleView_left_text_size, 16.0f);
		showRight = typedArray.getBoolean(R.styleable.HeadView_show_right, true);
		showLeft = typedArray.getBoolean(R.styleable.HeadView_show_left, true);
		
		/**
		 * 根据属性值设置界面
		 */
		if(!showRight){
			layoutRight.setVisibility(View.INVISIBLE);
		}else{
			layoutRight.setVisibility(View.VISIBLE);
		}
		
		if(!showLeft){
			layoutLeft.setVisibility(View.INVISIBLE);
		}else{
			layoutLeft.setVisibility(View.VISIBLE);
		}
		
		layoutContent.setBackgroundColor(bgColor);
		
		tvTitle.setText(title);
//		tvTitle.setTextSize(titleTextSize);


		tvTitle.setTextColor(titleColor);
		
		tvL.setText(leftText);
//		tvL.setTextSize(leftTextSize);
		tvL.setTextColor(leftTextColor);
		
		tvR.setText(rightText);
//		tvR.setTextSize(rightTextSize);
		tvR.setTextColor(rightTextColor);
		
		if(leftImageResource != 0){
			ivL.setVisibility(View.VISIBLE);
			ivL.setImageResource(leftImageResource);
		}else{
			ivL.setVisibility(View.GONE);
		}
		
		if(rightImageResource != 0){
			ivR.setVisibility(View.VISIBLE);
			ivR.setImageResource(rightImageResource);
		}else{
			ivR.setVisibility(View.GONE);
		}
		
		
	}
	
	/**
	 * 初始化控件
	 */
	private void initController(){
		layoutContent = (LinearLayout) findViewById(R.id.layout_content);
		layoutLeft = (LinearLayout) findViewById(R.id.layout_left);
		layoutRight = (LinearLayout) findViewById(R.id.layout_right);
		tvTitle = (TextView) findViewById(R.id.tv_title);
		tvL = (TextView) findViewById(R.id.tv_l);
		tvR = (TextView) findViewById(R.id.tv_r);
		ivR = (ImageView) findViewById(R.id.iv_r);
		ivL = (ImageView) findViewById(R.id.iv_l);
	}
	
	/**
	 * 或者主布局
	 * @return
	 */
	public LinearLayout getContentView(){
		return layoutContent;
	}
	
	/**
	 * 获取左布局
	 * @return
	 */
	public LinearLayout getLeftView(){
		return layoutLeft;
	}
	
	/**
	 * 获取右布局
	 * @return
	 */
	public LinearLayout getRightView(){
		return layoutRight;
	}

	/**
	 * 获取标题TextView
	 * @return
	 */
	public TextView getTitleTextView(){
		return tvTitle;
	}

	/**
	 * 获取左边TextView
	 * @return
	 */
	public TextView getLeftTextView(){
		return tvL;
	}

	/**
	 * 获取右边TextView
	 * @return
	 */
	public TextView getRightTextView(){
		return tvR;
	}
	
	/**
	 * 获取左边ImageView
	 * @return
	 */
	public ImageView getLeftImageView(){
		return ivL;
	}
	
	/**
	 * 获取右边ImageView
	 * @return
	 */
	public ImageView getRightImageView(){
		return ivR;
	}
	
	/**
	 * 设置左边点击事件
	 * @param listener
	 */
	public void setLeftLayoutOnClick(OnClickListener listener){
		if(getLeftView() != null && getLeftView().getVisibility() == View.VISIBLE){
			getLeftView().setOnClickListener(listener);
		}
	}
	
	/**
	 * 设置右边点击事件
	 * @param listener
	 */
	public void setRightLayoutOnClick(OnClickListener listener){
		if(getRightView() != null && getRightView().getVisibility() == View.VISIBLE){
			getRightView().setOnClickListener(listener);
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
