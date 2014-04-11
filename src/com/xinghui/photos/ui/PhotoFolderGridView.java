package com.xinghui.photos.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.GridView;

public class PhotoFolderGridView extends GridView {

	private boolean expanded = false;

	public PhotoFolderGridView(Context context) {
		super(context);
	}

	public PhotoFolderGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public PhotoFolderGridView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public boolean isExpanded() {
		return expanded;
	}
	
	public void setExpanded(boolean expanded) {
		this.expanded = expanded;
	}

	@Override
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// HACK! TAKE THAT ANDROID!
		if (isExpanded()) {
			// Calculate entire height by providing a very large height hint.
			// View.MEASURED_SIZE_MASK represents the largest height possible.
			int expandSpec = MeasureSpec.makeMeasureSpec(MEASURED_SIZE_MASK,
					MeasureSpec.AT_MOST);
			
			int expandSpecSize = MeasureSpec.getSize(expandSpec);
			int heightMeasureSpecSize = MeasureSpec.getSize(heightMeasureSpec);
			Log.i("hugo","heightMeasureSpec = " + heightMeasureSpecSize + ",expandSpecSize = " + expandSpecSize);
			
			super.onMeasure(widthMeasureSpec, expandSpec);
			
			ViewGroup.LayoutParams params = getLayoutParams();
			params.height = getMeasuredHeight();
		} else {
			super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		}
	}

}
