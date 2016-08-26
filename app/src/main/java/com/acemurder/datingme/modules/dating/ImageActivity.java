package com.acemurder.datingme.modules.dating;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.acemurder.datingme.APP;
import com.acemurder.datingme.R;
import com.acemurder.datingme.component.ninelayout.CustomImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.senab.photoview.PhotoViewAttacher;


public class ImageActivity extends AppCompatActivity implements PhotoViewAttacher.OnPhotoTapListener {

    @BindView(R.id.fragment_progressBar)
    ProgressBar mProgressBar;
    @BindView(R.id.image_shot)
    ImageView mImageView;
    @BindView(R.id.layout)
    RelativeLayout layout;
    PhotoViewAttacher mAttacher;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_image);
        ButterKnife.bind(this);
        showProgress();
        String url = getIntent().getStringExtra("url");
        if (url == null){
            return;
        }
        /*Glide.with(APP.getContext())
                .load(url.startsWith("http") ? url : CustomImageView.BASE_NORMAL_IMG_URL + url)
                .asBitmap()
                .placeholder(R.drawable.place_holder)
                .error(R.drawable.place_holder)
                .into(mImageView);*/
        Glide.with(APP.getContext()).load(url)
                .asBitmap()
                .placeholder(R.drawable.place_holder)
                .error(R.drawable.place_holder)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        if (mImageView == null) return;
                        mImageView.setImageBitmap(resource);
                        closeProgress();
                        mAttacher = new PhotoViewAttacher(mImageView);
                        mAttacher.update();
                        mAttacher.setOnPhotoTapListener(ImageActivity.this);
                    }
                });
    }

    public void showProgress() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    public void closeProgress() {
        mProgressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onPhotoTap(View view, float v, float v1) {
        onBackPressed();
    }

    @Override
    public void onOutsidePhotoTap() {
        onBackPressed();
    }
}
