/**
 * Copyright 2014 Hugo <qxh0305@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.xinghui.photos;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.xinghui.photos.ui.BitmapWorkerTask;
import com.xinghui.photos.ui.PhotoFolderGridView;

public class MainActivity extends Activity {

	public static final String KEY_DISMISS_KEYGUARD = "dismiss-keyguard";

	private static final String TAG = "MainActivity";

	private int size = 0;
	
	Vibrator vibretor;

	ArrayList<String> result = new ArrayList<String>(0);

	private long[] pattern = { 0, 500, 200, 500 };
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_ACTION_BAR);
		requestWindowFeature(Window.FEATURE_ACTION_BAR_OVERLAY);

		if (getIntent().getBooleanExtra(KEY_DISMISS_KEYGUARD, false)) {
			getWindow().addFlags(
					WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
		}

		setContentView(R.layout.activity_main);

		result = getImages(this);
		
		vibretor  = (Vibrator) this.getSystemService(VIBRATOR_SERVICE);

		PhotoFolderGridView grid = (PhotoFolderGridView) findViewById(R.id.image_grid);
		grid.setExpanded(false);
		grid.setAdapter(new ImageAdapter(this));
		grid.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				if(vibretor.hasVibrator()){//Added in API level 11
					vibretor.cancel();
					vibretor.vibrate(pattern,-1);
				}
			}
			
		});

//		DisplayMetrics metrics = new DisplayMetrics();
//		getWindowManager().getDefaultDisplay().getMetrics(metrics);
//		int screenWidth = metrics.widthPixels;
//		int screenHeight = metrics.heightPixels;
		

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public ArrayList getImages(Context context) {

		String[] projection = { MediaStore.Images.Media.DATA };//Media
		final Cursor cursor = context.getContentResolver().query(
				MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI, projection, null,
				null, null);

		ArrayList<String> result = new ArrayList<String>(cursor.getCount());

		size = cursor.getCount();

		Log.i("xinghui", cursor.getCount() + "");

		if (cursor.moveToFirst()) {
			final int dataColumn = cursor
					.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			do {
				final String data = cursor.getString(dataColumn);
				Log.i("xinghui", data);
				result.add(data);
			} while (cursor.moveToNext());
		}
		cursor.close();

		return result;
	}

	private class ImageAdapter extends BaseAdapter {
		public ImageAdapter(Context c) {
			mContext = c;
		}

		public int getCount() {
			return size;
		}

		public Object getItem(int position) {
			return position;
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			SquareImageView imageView;
			if (convertView == null) {
				imageView = new SquareImageView(mContext);
				imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
				imageView.setPadding(8, 8, 8, 8);
			} else {
				imageView = (SquareImageView) convertView;
			}

			String path = result.get(position);
			
//			BitmapWorkerTask task = new BitmapWorkerTask(imageView);
//			task.execute(path);
//			BitmapWorkerTask.loadBitmap(path, imageView,BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher));
			BitmapWorkerTask.loadBitmap(path, imageView,BitmapWorkerTask.decodeSampledBitmapFromFile(path, 1, 1));
			
//			Bitmap bitmap = null;
//			bitmap = decodeSampledBitmapFromFile(path, 100, 100);
//			if (bitmap != null) {
//				imageView.setImageBitmap(bitmap);
//			}

			return imageView;
		}

		private Context mContext;

	}


	private class SquareImageView extends ImageView {
		public SquareImageView(Context context) {
			super(context);
		}

		public SquareImageView(Context context, AttributeSet attrs) {
			super(context, attrs);
		}

		public SquareImageView(Context context, AttributeSet attrs, int defStyle) {
			super(context, attrs, defStyle);
		}

		@Override
		protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
			super.onMeasure(widthMeasureSpec, heightMeasureSpec);
			setMeasuredDimension(getMeasuredWidth(), getMeasuredWidth()); 
		}
	}

	
	private void test(){
//		long mMaxVmHeap     = Runtime.getRuntime().maxMemory()/1024;
//		long mMaxNativeHeap = 16*1024;
//		if (mMaxVmHeap == 16*1024)
//		     mMaxNativeHeap = 16*1024;
//		else if (mMaxVmHeap == 24*1024)
//		     mMaxNativeHeap = 24*1024;
//		else
//		    Log.w(TAG, "Unrecognized VM heap size = " + mMaxVmHeap);
//
//		long sizeReqd        = bitmapWidth * bitmapHeight * targetBpp  / 8;
//		long allocNativeHeap = Debug.getNativeHeapAllocatedSize();
//		if ((sizeReqd + allocNativeHeap + heapPad) >= mMaxNativeHeap)
//		{
//		    // Do not call BitmapFactory��
//		}
	}
	
}
