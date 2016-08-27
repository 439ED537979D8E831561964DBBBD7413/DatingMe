package com.acemurder.datingme.modules.dating;

import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.acemurder.datingme.APP;
import com.acemurder.datingme.R;
import com.acemurder.datingme.component.image_selector.MultiImageSelectorActivity;
import com.acemurder.datingme.component.ninelayout.NineGridlayout;
import com.acemurder.datingme.data.bean.DatingItem;
import com.acemurder.datingme.data.bean.Image;
import com.acemurder.datingme.data.bean.User;
import com.acemurder.datingme.data.network.subscriber.SimpleSubscriber;
import com.acemurder.datingme.data.network.subscriber.SubscriberListener;
import com.acemurder.datingme.modules.dating.event.InsertDatingEvent;
import com.acemurder.datingme.util.Utils;
import com.acemurder.datingme.util.permission.AfterPermissionGranted;
import com.acemurder.datingme.util.permission.EasyPermissions;
import com.avos.avoscloud.AVUser;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;

/**
 * Created by fg on 2016/8/18.
 */
public class AddDatingActivity extends AppCompatActivity implements DatingContract.IEditView,
        View.OnClickListener, EasyPermissions.PermissionCallbacks {


    @BindView(R.id.toolbar_cancel)
    TextView mCancelText;
    @BindView(R.id.toolbar_title)
    TextView mTitleText;
    @BindView(R.id.toolbar_save)
    TextView mSaveText;
    @BindView(R.id.toolbar)
    Toolbar mToolBar;
    @BindView(R.id.add_news_edit)
    EditText mAddNewsEdit;
    @BindView(R.id.add_news_theme)
    EditText mThemeEditText;
    @BindView(R.id.iv_ngrid_layout)
    NineGridlayout mNineGridlayout;
    private List<Image> mImgList;
    private AVUser mUser;
    private final static String ADD_IMG = "file:///android_asset/add_news.jpg";
    private final static int REQUEST_IMAGE = 0001;
    private DatingContract.IEditPresenter mIEditPresenter;


    @OnClick({R.id.toolbar_cancel, R.id.toolbar_save})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_cancel:
                onBackPressed();
                break;
            case R.id.toolbar_save:
                view.setClickable(false);
                if (APP.getAVUser() == null) {
                    Toast.makeText(APP.getContext(), "还没有登录哦", Toast.LENGTH_SHORT).show();
                    return;
                }
                String theme = mThemeEditText.getText().toString();
                String content = mAddNewsEdit.getText().toString();
                if (theme.length() == 0 || content.length() == 0) {
                    Toast.makeText(APP.getContext(), "主题和内容不能为空哦", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (theme.contains("#")) {
                    Toast.makeText(APP.getContext(), "主题不能还有特殊字符哦", Toast.LENGTH_SHORT).show();
                    return;
                }
                DatingItem datingItem = new DatingItem();
                datingItem.getMaster().setObjectId(APP.getAVUser().getObjectId());

                datingItem.setContent(content);
                datingItem.setPromulgator(APP.getAVUser().getUsername());
                datingItem.setPromulgatorId(APP.getAVUser().getObjectId());
                datingItem.setTheme(theme);
                if (mImgList.size() == 1) {
                    mIEditPresenter.sendDatingItem(datingItem);
                } else {
                    Log.e("xxxxxxxxxxxxx", mImgList.get(1).url);
                    mIEditPresenter.sendDatingItem(datingItem, mImgList.get(1).url);
                }

                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dating);
        ButterKnife.bind(this);
        mUser = APP.getAVUser();
        mIEditPresenter = new EditPresenter(this, this);
        init();


    }

    private void init() {
        mImgList = new ArrayList<>();
        mImgList.add(new Image(ADD_IMG, Image.TYPE_ADD));
        mNineGridlayout.setImagesData(mImgList);
        setSupportActionBar(mToolBar);

        mAddNewsEdit.setOnFocusChangeListener((view, b) -> {
            EditText editText = (EditText) view;
            if (!b) {
                editText.setHint(editText.getTag().toString());
            } else {
                editText.setTag(editText.getHint().toString());
                editText.setHint("");
            }
        });

        mNineGridlayout.setOnAddImagItemClickListener((v, position) -> {
            getPhoto();

        });

        mNineGridlayout.setOnClickDeletecteListener((v, position) -> {
            mImgList.remove(position);
            mNineGridlayout.setImagesData(mImgList);
        });

    }

    @AfterPermissionGranted(123)
    public void getPhoto() {
        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
        if (EasyPermissions.hasPermissions(this, permissions)) {
            Intent intent = new Intent(AddDatingActivity.this, MultiImageSelectorActivity.class);
            // 是否显示调用相机拍照
            intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, true);
            // 最大图片选择数量
            intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, 1);
            // 设置模式 (支持 单选/MultiImageSelectorActivity.MODE_SINGLE 或者 多选/MultiImageSelectorActivity.MODE_MULTI)
            intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, MultiImageSelectorActivity.MODE_MULTI);
            // 默认选择图片,回填选项(支持String ArrayList)
            ArrayList<String> results = new ArrayList<String>();
            for (Image i : mImgList) {
                if (i.getType() != Image.TYPE_ADD)
                    results.add(i.url);
            }

            if (mImgList.size() != 0)
                intent.putStringArrayListExtra(MultiImageSelectorActivity.EXTRA_DEFAULT_SELECTED_LIST, results);
            startActivityForResult(intent, REQUEST_IMAGE);
        } else {
            EasyPermissions.requestPermissions(this, "有约需要访问您的存储设备和相机哦", 123, permissions);
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == RESULT_OK) {
                // 获取返回的图片列表
                List<String> pathList = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                for (Image image : mImgList) {
                    for (int i = 0; i < pathList.size(); i++) {
                        if (image.url.equals(pathList.get(i))) pathList.remove(i);
                    }
                }
                // 处理你自己的逻辑 ....
                if (mImgList.size() + pathList.size() > 2) {
                    Toast.makeText(APP.getContext(), "只能选1张图哦", Toast.LENGTH_SHORT).show();
                    return;
                }
                Observable.from(pathList)
                        .map(s -> new Image(s, Image.TYPE_NORMAL))
                        .map(image -> {
                            mImgList.add(image);
                            return mImgList;
                        })
                        .subscribe(new SimpleSubscriber<>(this, new SubscriberListener<List<Image>>() {
                            @Override
                            public void onNext(List<Image> list) {
                                super.onNext(list);
                                mNineGridlayout.setImagesData(list);
                            }
                        }));
            }
        }
    }

    @Override
    public void setPresenter(DatingContract.IEditPresenter presenter) {

    }


    @Override
    public void showAddSuccess() {
        Utils.hideSoftInput(mAddNewsEdit);
        Utils.hideSoftInput(mThemeEditText);
        mSaveText.setClickable(true);
        Log.e("showAddSuccess", "showAddSuccess");
        EventBus.getDefault().post(new InsertDatingEvent());
        onBackPressed();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void showAddError() {
        Utils.hideSoftInput(mAddNewsEdit);
        Utils.hideSoftInput(mThemeEditText);

        mSaveText.setClickable(true);
        Snackbar sBar = Snackbar.make(mNineGridlayout, "网络有点小问题", Snackbar.LENGTH_SHORT);
        Snackbar.SnackbarLayout ve = (Snackbar.SnackbarLayout)sBar.getView();
        ve.setBackgroundColor(Color.parseColor("#DEAE75"));
        ve.setAlpha(0.5f);
        ((TextView) ve.findViewById(R.id.snackbar_text)).setTextColor(Color.parseColor("#FFFFFF"));
        sBar.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {

    }
}
