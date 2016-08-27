package com.acemurder.datingme.modules.me;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ForwardingListener;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.acemurder.datingme.APP;
import com.acemurder.datingme.R;
import com.acemurder.datingme.component.widget.CircleImageView;
import com.acemurder.datingme.config.Const;
import com.acemurder.datingme.data.bean.Response;
import com.acemurder.datingme.data.network.RequestManager;
import com.acemurder.datingme.data.network.subscriber.SimpleSubscriber;
import com.acemurder.datingme.data.network.subscriber.SubscriberListener;
import com.acemurder.datingme.modules.community.adapter.DetailsAdapter;
import com.acemurder.datingme.modules.me.event.UserInfoChangeEvent;
import com.acemurder.datingme.util.DialogUtil;
import com.acemurder.datingme.util.Utils;
import com.acemurder.datingme.util.permission.AfterPermissionGranted;
import com.acemurder.datingme.util.permission.EasyPermissions;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.jude.library.imageprovider.ImageProvider;
import com.jude.library.imageprovider.OnImageSelectListener;
import com.yalantis.ucrop.UCrop;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.net.PortUnreachableException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.http.Body;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import static com.acemurder.datingme.R.id.edit_introduce_et;
import static com.acemurder.datingme.R.id.image_view_crop;
import static com.avos.avoscloud.AVExceptionHolder.exists;
import static com.jude.library.imageprovider.ImageProvider.readImageWithSize;

public class EditInfoActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks ,OnImageSelectListener{

    private static final String PATH_CROP_PICTURES = Environment.getExternalStorageDirectory() + "/datingMe/" + "Pictures";


    @BindView(R.id.edit_info_avatar_layout)
    RelativeLayout editInfoAvatarLayout;
    @BindView(R.id.edit_introduce_et)
    EditText mIntroductionEdit;
    @BindView(R.id.edit_info_avatar)
    CircleImageView mCircleImageView;

    private CharSequence[] dialogItems = {"拍照", "从相册中选择"};


    private String imageParh = "";
    private boolean isChoosePic = false;
    private ImageProvider provider;
    private Subscription mSubscription;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_info);
        ButterKnife.bind(this);
        init();
        provider = new ImageProvider(this);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        provider.onActivityResult(requestCode, resultCode, data);
    }



    @OnClick(R.id.edit_info_avatar_layout)
    public void OnEditClick() {
        showSelectDialog();

    }

    @OnClick({R.id.toolbar_cancel,R.id.toolbar_save})
    public void OnClickTitleClick(View v){
        switch (v.getId()){
            case R.id.toolbar_cancel:
                onBackPressed();
                break;
            case R.id.toolbar_save:
                saveInfo();
        }
    }

    private void saveInfo() {
         showProgress();

        if (isChoosePic){
            mSubscription = RequestManager.INSTANCE.updateUser(new SimpleSubscriber<Response>(this, new SubscriberListener<Response>() {
                @Override
                public void onStart() {
                    super.onStart();
                }

                @Override
                public void onError(Throwable e) {
                    super.onError(e);
                    dismissProgress();
                    Utils.hideSoftInput(mIntroductionEdit);
                    Utils.showSnackbar(mIntroductionEdit,"网络遇到点小问题,稍后再试~");
                }

                @Override
                public void onNext(Response response) {
                    super.onNext(response);
                    Utils.hideSoftInput(mIntroductionEdit);
                    dismissProgress();
                    Utils.showSnackbar(mIntroductionEdit,"更新成功");
                    EventBus.getDefault().post(new UserInfoChangeEvent());
                    onBackPressed();

                }

                @Override
                public void onCompleted() {
                    super.onCompleted();
                }
            }),APP.getAVUser().getObjectId(),mIntroductionEdit.getText().toString(),imageParh);
        }else{
            mSubscription = RequestManager.INSTANCE.updateUser(new SimpleSubscriber<Response>(this, new SubscriberListener<Response>() {
                @Override
                public void onError(Throwable e) {
                    super.onError(e);
                    dismissProgress();
                    Utils.hideSoftInput(mIntroductionEdit);
                    Utils.showSnackbar(mIntroductionEdit,"网络遇到点小问题,稍后再试~");
                }

                @Override
                public void onNext(Response response) {
                    super.onNext(response);
                    dismissProgress();
                    Utils.hideSoftInput(mIntroductionEdit);
                    Utils.showSnackbar(mIntroductionEdit,"更新成功");
                    EventBus.getDefault().post(new UserInfoChangeEvent());
                    onBackPressed();
                }
            }),APP.getAVUser().getObjectId(),mIntroductionEdit.getText().toString());
        }
    }




    private void init() {
        Glide.with(this).load(APP.getUser().getPhotoSrc()).asBitmap().fitCenter().into(mCircleImageView);
        mIntroductionEdit.setText(APP.getUser().getDescription());
    }


    private void showSelectDialog() {
        new MaterialDialog.Builder(this)
                .titleColor(Color.BLACK)
                .contentColor(Color.BLACK)
                .items(dialogItems)
                .itemsCallback(
                        (dialog, itemView, which, text) -> {
                            if (which == 0) {
                                getImageFromCamera();
                            } else {
                                getImageFromAlbum();
                            }
                        })
                .show();
    }





    @AfterPermissionGranted(1)
    private void getImageFromAlbum() {

        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE};

        if (EasyPermissions.hasPermissions(this, permissions)) {
           /* Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setDataAndType(MediaStore.Images.Media.INTERNAL_CONTENT_URI,
                    "image*//*");
            startActivityForResult(intent, Const.Requests.SELECT_PICTURE);*/
            provider.getImageFromAlbum(this);
        } else {
            EasyPermissions.requestPermissions(this, "读取图片需要访问您的存储空间哦~",
                    1, permissions);
        }

    }


    @AfterPermissionGranted(2)
    private void getImageFromCamera() {
        String[] permissions = {Manifest.permission.CAMERA};

        if (EasyPermissions.hasPermissions(this, permissions)) {
           provider.getImageFromCamera(this);
        } else {
            EasyPermissions.requestPermissions(this, "拍照需要访问你的相机哦~",
                    2, permissions);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mSubscription != null)
            mSubscription.unsubscribe();
    }

    private void showProgress() {
        DialogUtil.showLoadingDiaolog(this, "上传中");
    }


    private void dismissProgress() {
        DialogUtil.dismissDialog();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // EasyPermissions handles the request result.
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
    }

    @Override
    public void onImageSelect() {

    }

    @Override
    public void onImageLoaded(Uri uri) {
        provider.corpImage(uri, 40, 40, new OnImageSelectListener() {
            @Override
            public void onImageSelect() {

            }

            @Override
            public void onImageLoaded(Uri uri) {
                Log.e("onImageLoaded",uri.getPath());
                imageParh = uri.getPath();
                isChoosePic = true;
                Glide.with(EditInfoActivity.this).load(new File(uri.getPath())).asBitmap().centerCrop().into(mCircleImageView);
            }

            @Override
            public void onError() {

            }
        });

    }

    @Override
    public void onError() {

    }
}
