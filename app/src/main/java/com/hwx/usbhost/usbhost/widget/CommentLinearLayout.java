package com.hwx.usbhost.usbhost.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;


public class CommentLinearLayout extends LinearLayout{


	public CommentLinearLayout(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	public CommentLinearLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		}


	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}


	private static View getLinItemView(int resid,Context context){
		return LayoutInflater.from(context).inflate(resid, null);
	}
	/**
	 * ViewGroup多个子控件的通用复用优化
	 */
	public static void setLinGreat(Context context,ViewGroup rootview,final Object[] datas,ViewReUseFaceListener listener){
		rootview.setVisibility(GONE);
		if (listener==null||datas==null||datas.length==0)
			return;
		rootview.setVisibility(VISIBLE);
		if (rootview.getChildCount()==0){
			for (int y=0; y < datas.length; y++) {
				rootview.addView(listener.backView(context) == null ? getLinItemView(listener.backViewRes(),context) : listener.backView(context));
			}
		}else {
			int oldViewCount = rootview.getChildCount();
			int newViewCount = datas.length;
			if (oldViewCount > newViewCount) {
				rootview.removeViews(newViewCount - 1, oldViewCount - newViewCount);
			} else if (oldViewCount < newViewCount) {
				for (int i = 0; i < newViewCount - oldViewCount; i++) {
					rootview.addView(listener.backView(context) == null ? getLinItemView(listener.backViewRes(),context) : listener.backView(context));
				}
			}
		}
		int linCount = rootview.getChildCount();
		for (int i = 0; i <linCount; i++) {
			try {
				listener.justItemToDo(datas[i], rootview.getChildAt(i), i,context);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
