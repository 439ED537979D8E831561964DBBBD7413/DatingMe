package com.acemurder.datingme.modules.dating;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.acemurder.datingme.APP;
import com.acemurder.datingme.R;
import com.acemurder.datingme.config.Const;
import com.acemurder.datingme.data.bean.DatingItem;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by fg on 2016/8/18.
 */
public class EditActivity extends AppCompatActivity implements EditContract.IEditView,View.OnClickListener {
    @BindView(R.id.toolbar)Toolbar mToolbar;
    @BindView(R.id.edit_et_theme)EditText themeView;
    @BindView(R.id.edit_et_title)EditText titleView;
    @BindView(R.id.edit_et_content)EditText contentView;
    @BindView(R.id.edit_iv_photo)ImageView photoView;
    @BindView(R.id.edit_menu_photo)FloatingActionMenu mFloatingActionMenu;
    private Uri imageUri;
    private EditPresenter mEditPresenter = new EditPresenter(this,this);
    private DatingItem mDatingItem = new DatingItem();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        ButterKnife.bind(this);
        FloatingActionButton fab1 = (FloatingActionButton) findViewById(R.id.edit_fab_add_photo);
        FloatingActionButton fab2 = (FloatingActionButton) findViewById(R.id.edit_fab_take_photo);
        fab1.setOnClickListener(this);
        fab2.setOnClickListener(this);
        initView();
    }

    @Override
    public void initView() {
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
                Log.e("setOnMenuItem","setOnMenuItemClickListener");
                if (item.getItemId() == R.id.action_finish){
                    mDatingItem.setContent(contentView.getText().toString());
                    mDatingItem.setTheme(themeView.getText().toString());
                    mDatingItem.setPromulgator(APP.getAVUser().getUsername());
                    mDatingItem.setPromulgatorId(APP.getAVUser().getObjectId());
                    //mDatingItem.setTitle(APP.getAVUser().get);
                    mEditPresenter.sendDatingItem(mDatingItem);
                }
                return true;
            }
        });
    }

    @Override
    public void finishActivity() {
      /*finish();*/
        EventBus.getDefault().post(new Event(mDatingItem));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit,menu);
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.edit_fab_take_photo:
                File outputTakeImage = new File(Environment.getExternalStorageDirectory(),"tempImage.jpg");
                try {
                    if (outputTakeImage.exists()){
                        outputTakeImage.delete();
                    }
                    outputTakeImage.createNewFile();
                }catch (IOException e){
                    e.printStackTrace();
                }
                imageUri = Uri.fromFile(outputTakeImage);
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
                startActivityForResult(intent, Const.TAKE_PHOTO);
                break;
            case R.id.edit_fab_add_photo:
                File outputAddImage = new File(Environment.getExternalStorageDirectory(),
                        "output_image.jpg");
                try {
                    if(outputAddImage.exists()){
                        outputAddImage.delete();
                    }
                    outputAddImage.createNewFile();
                }catch (IOException e){
                    e.printStackTrace();
                }
                imageUri = Uri.fromFile(outputAddImage);
                Intent intent1 = new Intent("android.intent.action.GET_CONTENT");
                intent1.setType("image/*");
                intent1.putExtra("crop",true);
                intent1.putExtra("scale",true);
                intent1.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
                startActivityForResult(intent1,Const.CROP_PHOTO);
                break;
            default:
                break;
        }
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
                if (resultCode == RESULT_OK){
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver()
                                .openInputStream(imageUri));
                        photoView.setImageBitmap(bitmap);
                    }catch (FileNotFoundException e){
                        e.printStackTrace();
                    }
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void setPresenter(EditContract.IEditPresenter presenter) {

    }



}
