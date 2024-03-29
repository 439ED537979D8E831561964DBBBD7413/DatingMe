package com.acemurder.datingme.component.ninelayout;

/**
 * Created by mathiasluo on 16-4-11.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.acemurder.datingme.R;
import com.acemurder.datingme.data.bean.Image;
import com.acemurder.datingme.util.DensityUtils;
import com.bumptech.glide.Glide;

/**
 * Created by Pan_ on 2015/2/2.
 */
public class CustomImageView extends ImageView {


    public static final String BASE_NORMAL_IMG_URL = "http://hongyan.cqupt.edu.cn/cyxbsMobile/Public/photo/";
    public static final String BASE_THUMBNAIL_IMG_URL = BASE_NORMAL_IMG_URL + "thumbnail/";

    private String url;
    private boolean isAttachedToWindow;
    private OnClickDeleteListener onClickDeleteListener;
    private int type;


    public CustomImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomImageView(Context context) {
        super(context);
    }


    public int getType() {
        return type;
    }

    public ImageView setType(int type) {
        this.type = type;
        return this;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                int x = (int) event.getX();
                int y = (int) event.getY();
                int w = DensityUtils.dp2px(getContext(), 18);
                if (x > getWidth() - 2 * w && y < 2 * w) {
                    if (onClickDeleteListener != null && type == Image.TYPE_NORMAL)
                        onClickDeleteListener.onClickDelete(this);
                }
                break;
        }

        return super.onTouchEvent(event);
    }

    @Override
    public void onAttachedToWindow() {
        isAttachedToWindow = true;
        setImageUrl(url);
        super.onAttachedToWindow();
    }

    @Override
    public void onDetachedFromWindow() {
        isAttachedToWindow = false;
        setImageBitmap(null);
        super.onDetachedFromWindow();
    }

    public void setImageUrl(String url) {
        if (!TextUtils.isEmpty(url)) {
            this.url = url;
            if (isAttachedToWindow) {
                Glide.with(getContext())
                        .load(url.startsWith("http") || url.startsWith("file:") || url.startsWith("/storage") ? url : BASE_THUMBNAIL_IMG_URL + url)
                        .placeholder(R.drawable.place_holder)
                        .into(this);
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (type == Image.TYPE_NORMAL) {
            int w = canvas.getWidth();
            Bitmap bitmapBack = BitmapFactory.decodeResource(getResources(), R.drawable.ic_delete);
            canvas.drawBitmap(bitmapBack, w - DensityUtils.dp2px(getContext(), 22), 0, null);
        }
    }

    public void setOnClickDeleteListener(OnClickDeleteListener onClickDeleteListener) {
        this.onClickDeleteListener = onClickDeleteListener;
    }

    public interface OnClickDeleteListener {
        void onClickDelete(View v);
    }
}
