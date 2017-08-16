//package com.carlos.bbox.base;
//
//import java.lang.ref.Reference;
//import java.lang.ref.WeakReference;
//
///**
// * Created by caochang on 2017/8/15.
// */
//
//public abstract class BaseMvp {
//
//    protected Reference mViewRef;
//
//
//
//    public void attachView(T view) {
//
//        mViewRef=new WeakReference(view);
//
//    }
//
//    protected T getView() {
//
//        return mViewRef.get();
//
//    }
//
//    public boolean isViewAttached() {
//
//        return mViewRef!=null&&mViewRef.get() !=null;
//
//    }
//
//    public void detachView() {
//
//        if(mViewRef!=null) {
//
//            mViewRef.clear();
//
//            mViewRef=null;
//
//        }
//
//    }
//}
