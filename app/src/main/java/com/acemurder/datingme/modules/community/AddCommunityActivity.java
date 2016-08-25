package com.acemurder.datingme.modules.community;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.os.EnvironmentCompat;
import android.support.v4.widget.SwipeRefreshLayout;
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
import com.acemurder.datingme.data.bean.Community;
import com.acemurder.datingme.data.bean.DatingItem;
import com.acemurder.datingme.data.bean.Image;
import com.acemurder.datingme.data.network.subscriber.SimpleSubscriber;
import com.acemurder.datingme.data.network.subscriber.SubscriberListener;
import com.acemurder.datingme.modules.community.event.CommunityInsertEvent;
import com.acemurder.datingme.modules.dating.DatingContract;
import com.acemurder.datingme.modules.dating.EditPresenter;
import com.avos.avoscloud.AVUser;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import rx.Observable;

/**
 * Created by fg on 2016/8/18.
 */
public class AddCommunityActivity extends AppCompatActivity implements CommunityContract.IPostNewCommunityView, View.OnClickListener {


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
    private CommunityContract.IPostNewCommunityPresenter mIPostNewCommunityPresenter;


    @OnClick({R.id.toolbar_cancel, R.id.toolbar_save})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_cancel:
                onBackPressed();
                break;
            case R.id.toolbar_save:
                if (APP.getAVUser() == null){
                    Toast.makeText(APP.getContext(),"还没有登录哦",Toast.LENGTH_SHORT).show();
                    return;
                }
                String theme = mThemeEditText.getText().toString();
                String content = mAddNewsEdit.getText().toString();
                if (theme.length() == 0 || content.length() == 0){
                    Toast.makeText(APP.getContext(),"标题和内容不能为空哦",Toast.LENGTH_SHORT).show();
                    return;
                }

                Community community = new Community();
                community.setAuthorName(APP.getAVUser().getUsername());
                community.setTitle(theme);
                community.setContent(content);
                community.setAuthorId(APP.getAVUser().getObjectId());

                if (mImgList.size() == 1){
                    mIPostNewCommunityPresenter.sendCommunityItem(community);
                }else{
                   // Log.e("xxxxxxxxxxxxx",mImgList.get(1).url);
                    ArrayList<String > list = new ArrayList();
                    list.add(mImgList.get(1).url);
                    mIPostNewCommunityPresenter.sendCommunityItem(community,list);

                    //mIEditPresenter.sendDatingItem(datingItem,mImgList.get(1).url);
                }

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dating);
        ButterKnife.bind(this);
      //  EventBus.getDefault().register(this);
        mUser = APP.getAVUser();
        mIPostNewCommunityPresenter = new PostNewCommunityPresenter(this,this);
        init();



    }

    private void init() {

        mThemeEditText.setHint("嗯,吐槽得有个标题");
        mAddNewsEdit.setHint("没内容怎么吐槽~~");
        mTitleText.setText("吐个槽");

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

            Intent intent = new Intent(AddCommunityActivity.this, MultiImageSelectorActivity.class);
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
        });

        mNineGridlayout.setOnClickDeletecteListener((v, position) -> {
            mImgList.remove(position);
            mNineGridlayout.setImagesData(mImgList);
        });

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
                    Toast.makeText(APP.getContext(),"只能选1张图哦",Toast.LENGTH_SHORT).show();
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
    public void showSendError() {
        Snackbar.make(mNineGridlayout,"网络遇到点小问题~~~",Snackbar.LENGTH_SHORT).show();

    }

    @Override
    public void showSendSuccess() {
        Snackbar.make(mNineGridlayout,"发送成功啦~",Snackbar.LENGTH_SHORT).show();
        EventBus.getDefault().post(new CommunityInsertEvent());
        onBackPressed();
    }

    @Override
    public void setPresenter(CommunityContract.IPostNewCommunityPresenter presenter) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.mIPostNewCommunityPresenter.unBind();
    }
}
