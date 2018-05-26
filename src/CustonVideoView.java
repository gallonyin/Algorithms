package com.info.aegis.lawpush4android.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.VideoView;

/**
 * 解决VideoView黑边白边
 * @author gallon on 2018.5.26
 */

public class CustonVideoView extends VideoView {

    public CustonVideoView(Context context) {
        super(context, null);
    }

    public CustonVideoView(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
    }

    public CustonVideoView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = getDefaultSize(0, widthMeasureSpec);
        int height = getDefaultSize(0, heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

}