package com.yanzhenjie.coolhttp;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.TextView;

import com.yanzhenjie.album.Album;
import com.yanzhenjie.coolhttp.http.CoolHttp;
import com.yanzhenjie.coolhttp.http.FileBinary;
import com.yanzhenjie.coolhttp.http.HttpListener;
import com.yanzhenjie.coolhttp.http.JavaBeanRequest;
import com.yanzhenjie.coolhttp.http.Method;
import com.yanzhenjie.coolhttp.http.Response;
import com.yanzhenjie.coolhttp.http.StringRequest;

import java.io.File;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_ACTIVITY_ALBUM = 100;

    private TextView mTvResult;
    private RecyclerView mRecyclerView;
    private AlbumPreviewAdapter mAdapter;
    private List<String> mPreivewList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        findViewById(R.id.btn_request).setOnClickListener(onClickListener);
        findViewById(R.id.btn_select_image).setOnClickListener(onClickListener);
        mTvResult = (TextView) findViewById(R.id.tv_result);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.set(5, 5, 5, 5);
            }
        });
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));

        int size = measureSize();
        mAdapter = new AlbumPreviewAdapter(this, size);
        mRecyclerView.setAdapter(mAdapter);
    }

    /**
     * 测量View宽度。
     *
     * @return
     */
    private int measureSize() {
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        if (Build.VERSION.SDK_INT >= 17) {
            display.getRealMetrics(metrics);
        } else {
            display.getMetrics(metrics);
        }
        int screenWidth = metrics.widthPixels;
        return (screenWidth - 20) / 3;
    }

    /**
     * 按钮点击事件。
     */
    private View.OnClickListener onClickListener = v -> {
        int id = v.getId();
        switch (id) {
            case R.id.btn_request: {
                request();
                break;
            }
            case R.id.btn_select_image: {
                Album.startAlbum(this, REQUEST_CODE_ACTIVITY_ALBUM
                        , 6                                                         // 指定选择数量。
                        , ContextCompat.getColor(this, R.color.colorPrimary)        // 指定Toolbar的颜色。
                        , ContextCompat.getColor(this, R.color.colorPrimaryDark));  // 指定状态栏的颜色。
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CODE_ACTIVITY_ALBUM: {
                if (resultCode == RESULT_OK) {
                    mPreivewList = Album.parseResult(data);
                    mAdapter.notifyDataSetChanged(mPreivewList);
                }
                break;
            }
        }
    }

    private void request() {
        // 测试接口见：http://api.nohttp.net

        String url = "http://api.nohttp.net/upload";
        StringRequest request = new StringRequest(url, Method.POST);
        request.addParams("name", "yanzhenjie");
        request.addParams("pwd", 123);
        if (mPreivewList != null && mPreivewList.size() > 0) {
            for (int i = 0; i < mPreivewList.size(); i++) {
                request.addParams("file" + i, new FileBinary(new File(mPreivewList.get(i))));
            }
        }

        CoolHttp.asyncRequest(request, new HttpListener<String>() {
            @Override
            public void onSucceed(Response<String> response) {
                String result = response.getResult();
                Log.e("CoolHttp", result);
                mTvResult.setText("结果：" + result);
            }

            @Override
            public void onFailed(Response<String> response) {
                Log.e("CoolHttp", "", response.getException());
                mTvResult.setText("请求失败。");
            }
        });
    }

    /**
     * JavaBean 请求使用。
     */
    private void requestJavaBean() {
        String url = "http://www.yanzhenjie.com";
        JavaBeanRequest<UserInfo> beanRequest = new JavaBeanRequest<>(url, Method.GET, UserInfo.class);
        CoolHttp.asyncRequest(beanRequest, new HttpListener<UserInfo>() {
            @Override
            public void onSucceed(Response<UserInfo> response) {
                UserInfo userInfo = response.getResult();
            }

            @Override
            public void onFailed(Response<UserInfo> response) {

            }
        });
    }

}
