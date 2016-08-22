package com.acemurder.datingme.modules.community;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.acemurder.datingme.APP;
import com.acemurder.datingme.R;
import com.acemurder.datingme.config.Const;
import com.acemurder.datingme.data.bean.Community;
import com.avos.avoscloud.AVUser;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by fg on 2016/8/22.
 */
public class WritingActivity extends AppCompatActivity implements CommunityContract.IPostNewCommunityView {
    @BindView(R.id.writing_et_title)EditText titleView;
    @BindView(R.id.writing_et_content)EditText contentView;
    @BindView(R.id.writing_iv_image)ImageView mImageView;
    @BindView(R.id.toolbar)Toolbar mToolbar;
    private Community mCommunity;
    private Uri imageUri;
    private CommunityContract.IPostNewCommunityPresenter mIPostNewCommunityPresenter;
    private final String[]items = new String[]{"拍照","相册"};
    private List<String> path = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_writing);
        ButterKnife.bind(this);
        AVUser avUser = new AVUser();
        avUser.setUsername("admin");
        avUser.setObjectId("123456");
        APP.setUser(avUser);
        mIPostNewCommunityPresenter = new PostNewCommunityPresenter(this,this);
        mCommunity = new Community();
        initView();
    }

    private void initView() {
        mToolbar.setTitle("发布动态");
        mToolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24px);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.action_finish){
                    if (titleView.getText().toString() == null || (titleView.getText().toString()).length() <= 0){
                        Toast.makeText(WritingActivity.this, "标题不为空", Toast.LENGTH_SHORT).show();
                    }else if (contentView.getText().toString() == null || contentView.getText().toString().length() <= 0){
                        Toast.makeText(WritingActivity.this, "内容不为空", Toast.LENGTH_SHORT).show();
                    }else if (mImageView.getDrawable() == null){
                        Toast.makeText(WritingActivity.this, "图片不为空", Toast.LENGTH_SHORT).show();
                    }else {
                        mCommunity.setTitle(titleView.getText().toString());
                        mCommunity.setContent(contentView.getText().toString());
                        mCommunity.setAuthorName(APP.getAVUser().getUsername());
                        mCommunity.setObjectId(APP.getAVUser().getObjectId());
                        path.add(imageUri.toString());
                        mIPostNewCommunityPresenter.sendCommunityItem(mCommunity,path);
                    }

                }
                return true;
            }
        });

        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(WritingActivity.this).setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i){
                            case 0:
                                String root = Environment.getExternalStorageDirectory().toString();
                                File myDir = new File(root + "/path");
                                myDir.mkdirs();
                                String fileName = "photo" + 0 + " tempImage.jpg";
                                File outputTakeImage = new File(myDir,fileName);

                                try {
                                    if (outputTakeImage.exists()) {
                                        outputTakeImage.delete();
                                    }
                                    outputTakeImage.createNewFile();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                imageUri = Uri.fromFile(outputTakeImage);
                                Log.e("WritingActivity", imageUri.toString());
                                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                                startActivityForResult(intent, Const.TAKE_PHOTO);
                                break;
                            case 1:
                                String root1 = Environment.getExternalStorageDirectory().toString();
                                File myDir1 = new File(root1 + "/path");
                                myDir1.mkdirs();
                                String fileName1 = "photo" + 0 + " output_image.jpg";
                                File outputAddImage = new File(myDir1,fileName1);

                                try {
                                    if (outputAddImage.exists()) {
                                        outputAddImage.delete();
                                    }
                                    outputAddImage.createNewFile();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                imageUri = Uri.fromFile(outputAddImage);

                                Intent intent1 = new Intent("android.intent.action.GET_CONTENT");
                                intent1.setType("image/*");
                                intent1.putExtra("crop", true);
                                intent1.putExtra("scale", true);
                                intent1.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                                startActivityForResult(intent1, Const.CROP_PHOTO);
                                break;
                            default:
                                break;
                        }
                    }
                }).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_finish,menu);
        return true;
    }

    @Override
    public void showSendError() {
        Toast.makeText(WritingActivity.this, "抱歉，发送失败，请重试", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showSendSuccess() {
        Toast.makeText(WritingActivity.this, "更新成功", Toast.LENGTH_SHORT).show();
        EventBus.getDefault().post(new CommunityEvent(mCommunity));
        finish();
    }

    @Override
    public void setPresenter(CommunityContract.IPostNewCommunityPresenter presenter) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case Const.TAKE_PHOTO:
                if (resultCode == RESULT_OK){
                    Intent intent = new Intent("com.android.camera.action.CROP");
                    intent.setDataAndType(imageUri,"image/*");
                    intent.putExtra("scale",true);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
                    startActivityForResult(intent,Const.CROP_PHOTO);
                }
                break;
            case Const.CROP_PHOTO:
                Log.e("TAG","这里被执行了吗");
                if (resultCode == RESULT_OK){
                    try {
                        Log.e("TAG","这里被执行了吗");
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver()
                                .openInputStream(imageUri));
                        Log.e("TAG",imageUri.toString());
                        mImageView.setImageBitmap(bitmap);
                    }catch (FileNotFoundException e){
                        e.printStackTrace();
                    }
                }
                break;
            default:
                break;
        }
    }

}
