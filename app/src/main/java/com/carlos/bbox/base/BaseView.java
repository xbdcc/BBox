package com.carlos.bbox.base;

/**
 * Created by caochang on 2017/7/4.
 */

public interface BaseView<T> {

    void setPresenter(T presenter);

    void showErrorMsg(String msg);

    //=======  State  =======

    void stateLoading();

    void stateSuccess();

    void stateFailure();
}
