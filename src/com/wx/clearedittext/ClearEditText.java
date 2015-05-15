package com.wx.clearedittext;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.EditText;
/**
 * 重写onTouchEvent实现删除一行
 * @author WangXiang
 */
public class ClearEditText extends EditText implements OnFocusChangeListener, TextWatcher {   
    private Drawable mClear,mEnter,mDef;   
    private boolean hasFoucs;  
    public ClearEditText(Context context) {   
        this(context, null);   
    }   
    public ClearEditText(Context context, AttributeSet attrs) {   
        this(context, attrs, android.R.attr.  editTextStyle);   
    }   
      
    public ClearEditText(Context context, AttributeSet attrs, int defStyle) {  
        super(context, attrs, defStyle);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ClearEditText);  
        mClear = a.getDrawable(R.styleable.ClearEditText_clear);
        mDef = a.getDrawable(R.styleable.ClearEditText_def);
        mEnter = a.getDrawable(R.styleable.ClearEditText_enter);
        a.recycle();
        init();
    }  
    private void init() {   
        //获取EditText的DrawableRight,假如没有设置我们就使用默认的图片  
        if (mClear == null) {
        	mClear = getResources().getDrawable(R.drawable.edittext_clear);   
        }
        mClear.setBounds(0, 0, mClear.getIntrinsicWidth(), mClear.getIntrinsicHeight());
        mDef.setBounds(0, 0, mDef.getIntrinsicWidth(), mDef.getIntrinsicHeight());
        mEnter.setBounds(0, 0, mEnter.getIntrinsicWidth(), mEnter.getIntrinsicHeight());
        setCompoundDrawables(mDef,getCompoundDrawables()[1], mClear, getCompoundDrawables()[3]);
        setClearIconVisible(false);
        setOnFocusChangeListener(this);
        addTextChangedListener(this);
    }
	public boolean performClick() {
		return super.performClick();
	}
    @Override
	public boolean onTouchEvent(MotionEvent event) {
    	super.onTouchEvent(event);
    	if (event.getAction() == MotionEvent.ACTION_DOWN) {  
            if (getCompoundDrawables()[2] != null) {
            	//只是计算了X轴对于暂时没想到好办法计算出Y轴坐标
                boolean x = event.getX() > getWidth() - getTotalPaddingRight() && (event.getX() < ((getWidth() - getPaddingRight())));
                if (x) {  
                    this.setText("");  
                }else{
                   	performClick();
                }
            }  
        }
        return true;  
	}
	/**
     * 当ClearEditText焦点发生变化的时候，判断里面字符串长度设置清除图标的显示与隐藏 
     */
    @Override   
    public void onFocusChange(View v, boolean hasFocus) {   
        this.hasFoucs = hasFocus;  
        if (hasFocus) {   
            setClearIconVisible(getText().length() > 0);   
        } else {   
            setClearIconVisible(false);   
        }
    }   
    /**
     * 设置清除图标的显示与隐藏，调用setCompoundDrawables为EditText绘制上去 
     * @param visible 
     */
    protected void setClearIconVisible(boolean visible) {
    	if(visible){
    		setCompoundDrawables(mEnter,getCompoundDrawables()[1], mClear, getCompoundDrawables()[3]);
    	}else{
    		setCompoundDrawables(mDef,getCompoundDrawables()[1], null, getCompoundDrawables()[3]);
    	}
    }   
    /**
     * 当输入框里面内容发生变化的时候回调的方法 
     */
    public void onTextChanged(CharSequence s, int start, int count,int after) {
    	if(hasFoucs){  
            setClearIconVisible(s.length() > 0);  
        }
    }   
    public void beforeTextChanged(CharSequence s, int start, int count,int after) {   
    }   
    public void afterTextChanged(Editable s) {   
    }
}