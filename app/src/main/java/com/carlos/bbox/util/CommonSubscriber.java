package com.carlos.bbox.util;

import android.text.TextUtils;

import com.carlos.bbox.base.BaseView;

import io.reactivex.subscribers.ResourceSubscriber;
import retrofit2.HttpException;

/**
 * Created by caochang on 2017/7/4.
 */


public abstract class CommonSubscriber<T> extends ResourceSubscriber<T> {
    private BaseView mBaseView;
    private String mErrorMsg;
    private boolean isShowErrorState = true;

    protected CommonSubscriber(BaseView view){
        mBaseView = view;
    }

    protected CommonSubscriber(){
    }

    protected CommonSubscriber(BaseView view, String errorMsg){
        mBaseView= view;
        this.mErrorMsg = errorMsg;
    }

    protected CommonSubscriber(BaseView view, boolean isShowErrorState){
        mBaseView = view;
        this.isShowErrorState = isShowErrorState;
    }

    protected CommonSubscriber(BaseView view, String errorMsg, boolean isShowErrorState){
        mBaseView= view;
        this.mErrorMsg = errorMsg;
        this.isShowErrorState = isShowErrorState;
    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onError(Throwable e) {
        if (mBaseView == null) {
            return;
        }
        if (mErrorMsg != null && !TextUtils.isEmpty(mErrorMsg)) {
            mBaseView.showErrorMsg(mErrorMsg);
        }
//        else if (e instanceof ApiException) {
//            mBaseView.showErrorMsg(e.toString());
//        }
        else if (e instanceof HttpException) {
            mBaseView.showErrorMsg("数据加载失败ヽ(≧Д≦)ノ");
        } else {
            mBaseView.showErrorMsg("未知错误ヽ(≧Д≦)ノ");
            LogUtil.d(e.toString());
        }
        if (isShowErrorState) {
            mBaseView.stateFailure();
        }
    }
}
