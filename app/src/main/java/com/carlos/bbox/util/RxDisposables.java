package com.carlos.bbox.util;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by caochang on 2017/8/15.
 */

public class RxDisposables {
    private static CompositeDisposable sCompositeDisposable = new CompositeDisposable();

    public static boolean isDisposed() {
        return sCompositeDisposable.isDisposed();
    }

    public static void add(Disposable disposable) {
        if (disposable != null) {
            sCompositeDisposable.add(disposable);
        }
    }

    public static void remove(Disposable disposable) {
        if (disposable != null) {
            sCompositeDisposable.remove(disposable);
        }
    }

    public static void clear() {
        sCompositeDisposable.clear();
    }

    public static void unsubscribe() {
        sCompositeDisposable.dispose();
    }

//    public static boolean hasSubscriptions() {
//        return mSubscriptions.hasSubscriptions();
//    }
}
