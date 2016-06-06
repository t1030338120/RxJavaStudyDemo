/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 Piasy
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.app.rxjava.http;

import retrofit2.adapter.rxjava.HttpException;
import rx.functions.Action1;

/**
 * Created by Piasy{github.com/Piasy} on 15/7/24.
 *
 * used for Rx network error handling
 * Usage: Observable.subscribe(onNext, RxNetErrorProcessor.NetErrorProcessor)
 * run in the observeOn() thread
 * onErrorReturn run in subscribeOn thread (retrofit run in background thread, not good for
 * error handling)
 *
 * Note: if you handle onError for the net request, than you should call it manually:
 * RxNetErrorProcessor.NetErrorProcessor.call(throwable);
 * Otherwise this method won't be invoked
 */

public class RxNetErrorProcessor implements Action1<Throwable> {


    /**
     * Final path of network error processing, submit it to server at production build.
     */
    @Override
    public void call(final Throwable throwable) {
//        Timber.e(throwable, "RxNetErrorProcessor");
        System.out.println("网络出错了 Exception : "+throwable.getClass().getSimpleName());
    }

    public static boolean tryWithApiError(final Throwable throwable, final Action1<Throwable> handler) {
        if (throwable instanceof HttpException) { //网络异常处理
//            handler.call(throwable);
            System.out.println("网络出错了 Exception : "+throwable.getClass().getSimpleName());
            return true;
        } else { //错误码异常处理
            handler.call(throwable);

        }
        return false;
    }
}
