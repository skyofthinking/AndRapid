package com.joyue.tech.core.ui.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.joyue.tech.core.R;
import com.joyue.tech.core.utils.FileUtils;
import com.joyue.tech.core.utils.ImageLoader;
import com.joyue.tech.core.utils.SystemShareUtils;
import com.joyue.tech.core.utils.TLog;
import com.joyue.tech.core.utils.ToastUtils;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.io.File;
import java.util.ArrayList;

import rx.functions.Action1;
import uk.co.senab.photoview.PhotoView;
import zlc.season.rxdownload.RxDownload;
import zlc.season.rxdownload.entity.DownloadEvent;
import zlc.season.rxdownload.entity.DownloadFlag;
import zlc.season.rxdownload.entity.DownloadStatus;

public class ViewPicActivity extends RapidActivity {

    public final static String IMG_URLS = "ImageUrls";
    public final static String IMG_INDEX = "ImageIndex";
    private ViewPager mViewPager;
    private TextView mTvIndex;
    private ImageView mIvDownload;

    private ArrayList<String> mUrlList;
    private int mCurrentIndex = 0;
    private String mSavePath;
    private RxDownload mRxDownload;

    public static void openActivity(Activity activity, View view, ArrayList<String> urls, int position) {
        Intent intent = new Intent(activity, ViewPicActivity.class);
        intent.putStringArrayListExtra(IMG_URLS, urls);
        intent.putExtra(IMG_INDEX, position);
        ActivityOptionsCompat compat = ActivityOptionsCompat.makeScaleUpAnimation(view, view.getWidth() / 2, view.getHeight() / 2, 0, 0);
        ActivityCompat.startActivity(activity, intent, compat.toBundle());
    }

    public static void openActivity(Activity activity, View view, String url) {
        ArrayList<String> urls = new ArrayList<>();
        urls.add(url);
        openActivity(activity, view, urls, 0);
    }

    public static void openActivity(Context context, View view, ArrayList<String> urls, int position) {
        Intent intent = new Intent(context, ViewPicActivity.class);
        intent.putStringArrayListExtra(IMG_URLS, urls);
        intent.putExtra(IMG_INDEX, position);
        ActivityOptionsCompat compat = ActivityOptionsCompat.makeScaleUpAnimation(view, view.getWidth() / 2, view.getHeight() / 2, 0, 0);
        ActivityCompat.startActivity(context, intent, compat.toBundle());
    }

    public static void openActivity(Context context, View view, String url) {
        ArrayList<String> urls = new ArrayList<>();
        urls.add(url);
        openActivity(context, view, urls, 0);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_view_pic;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mRxDownload = RxDownload.getInstance().context(this);

        mUrlList = getIntent().getExtras().getStringArrayList(IMG_URLS);
        mCurrentIndex = getIntent().getExtras().getInt(IMG_INDEX);

        mViewPager = $(R.id.viewpager);
        mTvIndex = $(R.id.tv_index);
        mIvDownload = $(R.id.iv_download);
        mTvIndex.setText((mCurrentIndex + 1) + "/" + mUrlList.size());
        mIvDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                downloadPicture(0);
            }
        });

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mCurrentIndex = position;
                mTvIndex.setText((mCurrentIndex + 1) + "/" + mUrlList.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mViewPager.setAdapter(new ViewPicViewPager(this));
        mViewPager.setCurrentItem(mCurrentIndex);
    }

    class ViewPicViewPager extends PagerAdapter {
        Context context;

        public ViewPicViewPager(Context context) {
            this.context = context;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            PhotoView photoView = new PhotoView(context);
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            photoView.setLayoutParams(layoutParams);
            ImageLoader.with(photoView, mUrlList.get(position));
            container.addView(photoView);
            return photoView;
        }

        @Override
        public int getCount() {
            return mUrlList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    /**
     * download image
     *
     * @param action 0:save 1:share
     */
    public void downloadPicture(final int action) {
        mSavePath = FileUtils.getSaveImagePath(mContext) + File.separator + FileUtils.getFileName(mUrlList.get(0));
        TLog.i(TAG, mSavePath);

        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE).doOnNext(new Action1<Boolean>() {
            @Override
            public void call(Boolean granted) {
                if (!granted) {
                    throw new RuntimeException("No Permission");
                }
            }
        }).compose(mRxDownload.transformService(mUrlList.get(0), FileUtils.getFileName(mUrlList.get(0)), FileUtils.getSaveImagePath(this))).subscribe(new Action1<Object>() {
            @Override
            public void call(Object o) {
                ToastUtils.show("下载开始");
            }
        });

        mRxDownload.receiveDownloadStatus(mUrlList.get(0)).subscribe(new Action1<DownloadEvent>() {
            @Override
            public void call(DownloadEvent event) {
                if (event.getFlag() == DownloadFlag.FAILED) {
                    Throwable throwable = event.getError();
                    Log.w("Error", throwable);
                    ToastUtils.show("保存失败:" + throwable);
                }
                if (event.getFlag() == DownloadFlag.COMPLETED) {
                    if (action == 0) {
                        ToastUtils.show("已保存至相册");
                        // [Android How to use MediaScannerConnection scanFile - Stack Overflow](http://stackoverflow.com/questions/4646913/android-how-to-use-mediascannerconnection-scanfile/5814533#5814533)
                        MediaScannerConnection.scanFile(mContext, new String[]{mSavePath}, null, new MediaScannerConnection.OnScanCompletedListener() {
                            public void onScanCompleted(String path, Uri uri) {
                                TLog.i(TAG, "Finished Scanning " + path);
                            }
                        });
                    } else {
                        Uri uri = Uri.fromFile(mRxDownload.getRealFiles(FileUtils.getFileName(mUrlList.get(0)), FileUtils.getSaveImagePath(mContext))[0]);
                        SystemShareUtils.shareImage(mContext, uri);
                    }
                }

                DownloadStatus status = event.getDownloadStatus();
                TLog.i(TAG, "totalByte:" + status.getTotalSize() + " downloadedByte:" + status.getDownloadSize() + " progress:" + status.getPercent() + " " + status.getFormatStatusString());
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ActivityCompat.finishAfterTransition(this);
    }
}
