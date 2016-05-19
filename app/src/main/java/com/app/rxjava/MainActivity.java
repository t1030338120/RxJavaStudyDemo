package com.app.rxjava;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.app.rxjava.mvc.MvcMainActivity;
import com.app.rxjava.mvp.ui.MvpDemoMainActivity;
import com.app.rxjava.mvvm.MvvmMainActivity;
import com.app.rxjava.retrofit.DownloadFileActivity;
import com.app.rxjava.retrofit.RetrofitDemoActivity;
import com.app.rxjava.rxjava.AppInfosActivity;
import com.app.rxjava.rxjava_3_cache.RxJavaCacheMainActivity;
import com.jakewharton.rxbinding.view.RxView;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class MainActivity extends AppCompatActivity {

    String TAG = "RXJAVA";
    private LinearLayout llContent;
    private Integer[] resIds = {
            R.drawable.ic_01,
            R.drawable.ic_02,
            R.drawable.ic_03,
            R.drawable.ic_04,
            R.drawable.ic_05,
            R.drawable.ic_06,
            2};
    private Button mButton;
    private Subscription mSubscription;

    CompositeSubscription mCompositeSubscription = new CompositeSubscription();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (mSubscription != null) {
            mCompositeSubscription.add(mSubscription);
        }

        initView();
    }


    private void initView(){
        llContent = (LinearLayout) findViewById(R.id.ll_content);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCompositeSubscription.clear();
    }

    private void setListeners() {
        RxView.clicks(mButton)
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe(aVoid -> loadBitmapForSubscriber(resIds));
    }


    public void btnClick(View view) {
        switch (view.getId()) {
            case R.id.btn_01:
                testObservableCreate();
                break;
            case R.id.btn_02:
                testObservableJust02();
                break;
            case R.id.btn_03:
                loadBitmapForFlatMap(resIds);
                break;
            case R.id.btn_04:
                startActivity(new Intent(this, AppInfosActivity.class));
                break;
            case R.id.btn_05:
                startActivity(new Intent(this, DownloadFileActivity.class));
                break;
            case R.id.btn_07:
                startActivity(new Intent(this, RetrofitDemoActivity.class));
                break;
            case R.id.btn_08:
                testObservableSchedulerMain();

                break;
            case R.id.btn_09:
                startActivity(new Intent(this, MvpDemoMainActivity.class));
                break;
            case R.id.btn_10:
                startActivity(new Intent(this, MvcMainActivity.class));
                break;
            case R.id.btn_11:
                startActivity(new Intent(this, MvvmMainActivity.class));
                break;
            case R.id.btn_12:
                startActivity(new Intent(this, RxJavaCacheMainActivity.class));
                break;
            case R.id.btn_13:
                testDoOnNext();
                //                testSample();
                break;
        }
    }


    private void testObservableJust() {
        Observable.just("Hello rxjava end !")
                .subscribe((String s) -> Log.i(TAG, "执行了onNext( )" + s));

        //        Observable.just("Hello rxjava end !")
        //                .subscribe(new Action1<String>() {
        //                    @Override
        //                    public void call(String s) {
        //                        Log.i(TAG, "执行了onNext( )" + s);
        //                    }
        //                });
    }


    private void testObservableJust02() {
        Observable.just(R.drawable.ic_01)
                .subscribe(integer2 -> addView(BitmapFactory.decodeResource(getResources(), integer2)));
    }


    private void testObservableCreate() {
        //lambda表达式
        Observable.create(subscriber -> {
            subscriber.onNext("hello");
            subscriber.onNext("rxjava");
            subscriber.onNext("end !");
            subscriber.onCompleted();
        })
                .subscribe(s -> Log.i(TAG, "执行了onNext( )" + s),
                        throwable -> Log.i(TAG, "执行了onError( )"),
                        () -> Log.i(TAG, "执行了onCompleted( )"));
    }


    private void testObservableSchedulerMain() {
        Observable<String> observable = Observable.create(subscriber -> {
            Log.i("### Observer Thread", Thread.currentThread().getName());
            subscriber.onNext("");
            subscriber.onCompleted();
        });

        Subscription subscription = observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(s -> {
                    Log.i("### flatMap Thread", Thread.currentThread().getName());
                    return Observable.just(s);
                })
                .map(s1 -> {
                    Log.i("### map Thread", Thread.currentThread().getName());
                    return s1;
                })
                .subscribe(s2 -> Log.i("### subscribe Thread", Thread.currentThread().getName()));
        mCompositeSubscription.add(subscription);
    }


    private void loadBitmapForObserver(Integer[] resIds) {
        //lambda表达式
        Observable.from(resIds)
                .subscribeOn(Schedulers.io())
                        //                .observeOn(Schedulers.io())
                .map(integer -> BitmapFactory.decodeResource(getResources(), integer))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bitmap -> addView(bitmap));
    }


    private void loadBitmapForSubscriber(Integer[] resIds) {
        //lambda表达式
        Observable.from(resIds)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(integer -> BitmapFactory.decodeResource(getResources(), integer))
                .subscribe(bitmap -> addView(bitmap));
    }


    private void loadBitmapForAction(Integer[] resIds) {
        Observable.from(resIds)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(integer -> BitmapFactory.decodeResource(getResources(), integer))
                .subscribe(bitmap -> addView(bitmap));
    }


    private void loadBitmapForFlatMap(Integer[] resIds) {
        Observable.from(resIds)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(integer -> integer != 2)
                .flatMap(integer1 -> getBitmapSize(integer1))
                .subscribe(imageViewSize -> Log.i(TAG, "执行了 call( ) bitmap size : " + imageViewSize.width + " x " + imageViewSize.height),
                        throwable -> Log.i(TAG, "bitmap 为空 ！"));
    }


    private Observable<ImageViewSize> getBitmapSize(int resId) {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), resId);
        return Observable.just(new ImageViewSize(bitmap.getWidth(), bitmap.getHeight()),
                new ImageViewSize(bitmap.getWidth(), bitmap.getHeight()));
    }


    private void addView(Bitmap bitmap) {
        ImageView img = new ImageView(MainActivity.this);
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        p.topMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15, getResources().getDisplayMetrics());
        img.setLayoutParams(p);
        img.setImageBitmap(bitmap);
        llContent.addView(img);
    }


    public static class ImageViewSize {
        public int width;
        public int height;

        public ImageViewSize(int width, int height) {
            this.width = width;
            this.height = height;
        }
    }


    public void testDoOnNext() {
        Observable<String> observable = Observable.create(subscriber -> {
            System.out.println("### Observer Thread" + Thread.currentThread());
            subscriber.onNext("123456");
            subscriber.onCompleted();
        });

        //        Observable.just("654321")
        observable
                .subscribeOn(Schedulers.newThread())
                        //                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(s -> {
                    System.out.println("执行了doOnNext()方法");
                    System.out.println("Thread " + Thread.currentThread());
                })
                .doOnCompleted(() -> System.out.println("执行了doOnCompleted()方法"))
                .doOnError(throwable -> System.out.println("执行了doOnError()方法"))
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        System.out.println("onCompleted()方法");
                    }

                    @Override
                    public void onError(Throwable e) {
                        //                        System.out.println("doOnError()方法");
                    }

                    @Override
                    public void onNext(String s) {
                        System.out.println("onNext()方法");
                        System.out.println("Thread " + Thread.currentThread());
                    }
                });
    }




}
