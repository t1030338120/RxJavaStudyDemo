package com.app.rxjava.retrofit;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.app.rxjava.BaseActivity;
import com.app.rxjava.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

public class DownloadFileActivity extends BaseActivity {

    static String TAG = "RxJavaDemo";
    private ProgressBar mProgressBar;
    private Button mBtnStartDown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCusContentView(R.layout.activity_main2);
        mProgressBar = (ProgressBar) findViewById(R.id.progressbar);
        mBtnStartDown = (Button) findViewById(R.id.btn_start_down);
    }

    void resetView() {
        mBtnStartDown.setText("RxJava开始下载文件");
        mBtnStartDown.setEnabled(true);
        mProgressBar.setProgress(0);
    }

    public void click(View view) {
        mBtnStartDown.setEnabled(false);
        mBtnStartDown.setText("正在下载中...");
        final PublishSubject<Integer> mDownloadProgress = PublishSubject.create();

        mDownloadProgress.distinct()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(progress ->mProgressBar.setProgress(progress),
                        throwable -> throwable.printStackTrace());

        String destination = "sdcardsoftboy.avi";
        obserbableDownload("http://archive.blender.org/fileadmin/movies/softboy.avi", destination, mDownloadProgress)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aBoolean -> {
                    //打开文件 ...
                    Log.i(TAG, "文件下载成功，请打开文件！");
                    resetView();
                });
    }
    private boolean downloadFile(String source, String destination, final PublishSubject mDownloadProgress) {
        boolean result = false;
        InputStream input = null;
        OutputStream output = null;
        HttpURLConnection connection = null;
        try {
            URL url = new URL(source);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                return false;
            }
            int fileLength = connection.getContentLength();
            input = connection.getInputStream();
            output = new FileOutputStream(Environment.getExternalStorageDirectory().getAbsoluteFile() + File.separator + destination);
            byte data[] = new byte[4096];
            long total = 0;
            int count;
            while ((count = input.read(data)) != -1) {
                total += count;
                if (fileLength > 0 && mDownloadProgress != null) {
                    int percentage = (int) (total * 100 / fileLength);
                    mDownloadProgress.onNext(percentage);
                }
                output.write(data, 0, count);
            }
            mDownloadProgress.onCompleted();
            result = true;
        } catch (Exception e) {
            mDownloadProgress.onError(e);
        } finally {
            try {
                if (output != null) {
                    output.close();
                }
                if (input != null) {
                    input.close();
                }
            } catch (IOException e) {
                mDownloadProgress.onError(e);
            }
            if (connection != null) {
                connection.disconnect();
                //                mDownloadProgress.onCompleted();
            }
        }
        return result;
    }
    private Observable<Boolean> obserbableDownload(final String source, final String destination, final PublishSubject mDownloadProgress) {
        return Observable.create(subscriber -> {
            try {
                boolean result = downloadFile(source, destination, mDownloadProgress);
                if (result) {
                    subscriber.onNext(true);
                    subscriber.onCompleted();
                } else {
                    subscriber.onError(new Throwable("Download failed."));
                }
            } catch (Exception e) {
                subscriber.onError(e);
            }
        });
    }
}
