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
import com.acemurder.datingme.modules.me.event.UserInfoChangeEvent;
import com.acemurder.datingme.util.DialogUtil;
import com.acemurder.datingme.util.Utils;
import com.acemurder.datingme.util.permission.AfterPermissionGranted;
import com.acemurder.datingme.util.permission.EasyPermissions;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
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
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import static com.acemurder.datingme.R.id.edit_introduce_et;

public class EditInfoActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {

    private static final String PATH_CROP_PICTURES = Environment.getExternalStorageDirectory() + "/datingMe/" + "Pictures";


    @BindView(R.id.edit_info_avatar_layout)
    RelativeLayout editInfoAvatarLayout;
    @BindView(R.id.edit_introduce_et)
    EditText mIntroductionEdit;
    @BindView(R.id.edit_info_avatar)
    CircleImageView mCircleImageView;

    private CharSequence[] dialogItems = {"拍照", "从相册中选择"};

    private File dir;
    private Uri mDestinationUri, cameraImageUri;
    private String imageParh = "";
    private boolean isChoosePic = false;
    private String token;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_info);
        ButterKnife.bind(this);
        init();
        token = APP.getAVUser().getSessionToken();
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
        if (isChoosePic){
            RequestManager.INSTANCE.updateUser(new SimpleSubscriber<Response>(this, new SubscriberListener<Response>() {
                @Override
                public void onStart() {
                    super.onStart();
                    showProgress();
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
                    Utils.showSnackbar(mIntroductionEdit,"更新成功");
                    EventBus.getDefault().post(new UserInfoChangeEvent());
                    onBackPressed();

                }

                @Override
                public void onCompleted() {
                    super.onCompleted();
                }
            }),APP.getAVUser().getObjectId(),mIntroductionEdit.getText().toString(),imageParh,token);
        }else{
            RequestManager.INSTANCE.updateUser(new SimpleSubscriber<Response>(this, new SubscriberListener<Response>() {
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
                    Utils.showSnackbar(mIntroductionEdit,"更新成功");
                    EventBus.getDefault().post(new UserInfoChangeEvent());
                    onBackPressed();
                }
            }),APP.getAVUser().getObjectId(),mIntroductionEdit.getText().toString(),token);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case Const.Requests.SELECT_PICTURE:
                    Uri selectedUri = data.getData();
                    if (selectedUri != null) {
                        startuCropActivity(selectedUri);
                    } else {
                        Toast.makeText(this, "无法识别该图像", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case UCrop.REQUEST_CROP:
                    handleCropResult(data);
                    break;

                case Const.Requests.SELECT_CAMERA:
                    startuCropActivity(cameraImageUri);
                    break;
                default:
                    break;
            }
        }
        if (resultCode == UCrop.RESULT_ERROR) {
            handleCropError(data);
        }
    }

    private void init() {
        mIntroductionEdit.setText(APP.getUser().getDescription());
        dir = new File(PATH_CROP_PICTURES);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        cameraImageUri = Uri.fromFile(
                new File(dir, System.currentTimeMillis() + ".png"));
        mDestinationUri = Uri.fromFile(new File(dir, APP.getAVUser().getObjectId() + ".png"));
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


    private void startuCropActivity(@NonNull Uri uri) {
        UCrop uCrop = UCrop.of(uri, mDestinationUri);
        UCrop.Options options = new UCrop.Options();
        options.setCompressionFormat(Bitmap.CompressFormat.PNG);
        options.setCompressionQuality(90);
        options.setToolbarColor(
                ContextCompat.getColor(this, R.color.primary));
        options.setStatusBarColor(
                ContextCompat.getColor(this, R.color.primary_dark));
        uCrop.withOptions(options);
        uCrop.start(this);
    }


    private void handleCropResult(Intent result) {
        Uri resultUri = UCrop.getOutput(result);
        if (resultUri != null) {
            //showProgress();
            Log.e("handleCropResult", resultUri.getPath());
            Glide.with(this).load(new File(resultUri.getPath())).asBitmap().centerCrop().into(mCircleImageView);
            imageParh = resultUri.getPath();
            isChoosePic = true;

        }
    }

    private void handleCropError(Intent result) {
        Throwable cropError = UCrop.getError(result);
        if (cropError != null) {
            Toast.makeText(this, cropError.getMessage(), Toast.LENGTH_SHORT)
                    .show();
        } else {
            Toast.makeText(this, "Unexpected error", Toast.LENGTH_SHORT).show();
        }
    }


    @AfterPermissionGranted(1)
    private void getImageFromAlbum() {

        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE};

        if (EasyPermissions.hasPermissions(this, permissions)) {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setDataAndType(MediaStore.Images.Media.INTERNAL_CONTENT_URI,
                    "image/*");
            startActivityForResult(intent, Const.Requests.SELECT_PICTURE);
        } else {
            EasyPermissions.requestPermissions(this, "读取图片需要访问您的存储空间哦~",
                    1, permissions);
        }

    }


    @AfterPermissionGranted(2)
    private void getImageFromCamera() {
        String[] permissions = {Manifest.permission.CAMERA};

        if (EasyPermissions.hasPermissions(this, permissions)) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, cameraImageUri);
            startActivityForResult(intent, Const.Requests.SELECT_CAMERA);
        } else {
            EasyPermissions.requestPermissions(this, "拍照需要访问你的相机哦~",
                    2, permissions);
        }

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
}
