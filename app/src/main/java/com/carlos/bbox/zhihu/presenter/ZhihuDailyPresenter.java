package com.carlos.bbox.zhihu.presenter;

import com.carlos.bbox.ApiManager;
import com.carlos.bbox.util.CommonSubscriber;
import com.carlos.bbox.util.LogUtil;
import com.carlos.bbox.util.RxDisposables;
import com.carlos.bbox.zhihu.bean.ZhihuDailyBeforeVO;
import com.carlos.bbox.zhihu.bean.ZhihuDailyItemVO;
import com.carlos.bbox.zhihu.bean.ZhihuDailyTodayVO;
import com.carlos.bbox.zhihu.contract.ZhihuDailyContract;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by caochang on 2017/7/14.
 */

public class ZhihuDailyPresenter implements ZhihuDailyContract.Presenter{

    private ZhihuDailyContract.View mZhihuDailyView;

    public ZhihuDailyPresenter(ZhihuDailyContract.View zhihuDailyView) {
        mZhihuDailyView = zhihuDailyView;
        mZhihuDailyView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void unsubscribe() {
        RxDisposables.clear();
    }


    @Override
    public void getTodayData() {
        Flowable<ZhihuDailyTodayVO> flowable = ApiManager.getInstence().getZhihuApiService().getTodayDaily();
        Disposable disposable=flowable.subscribeOn(Schedulers.io())
                .map(zhihuDailyVO -> {
                    String date = zhihuDailyVO.getDate();
                    List<ZhihuDailyItemVO> zhihuDailyItemVOs=zhihuDailyVO.getStories();
                    for (ZhihuDailyItemVO zhihuDailyItemVO : zhihuDailyItemVOs) {
//                        zhihuDailyItemVO.setHasFadedIn(true);
                    }
                    return zhihuDailyVO;
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new CommonSubscriber<ZhihuDailyTodayVO>() {

                    @Override
                    public void onNext(ZhihuDailyTodayVO zhihuDailyTodayVO) {
                        if (mZhihuDailyView.isActive()){
                            mZhihuDailyView.setTodayData(zhihuDailyTodayVO);
                            mZhihuDailyView.stateSuccess();
                        }
//
//                        mZhihuDailyAdapter.addItems(zhihuZhihuDailyItems);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        if (mZhihuDailyView.isActive()){
                            mZhihuDailyView.stateFailure();
                        }
                        LogUtil.e("error", e);
                    }
                });
        LogUtil.d("0000000---");
        RxDisposables.add(disposable);
//        ActivityCollector.finishAll();
    }

    @Override
    public void getBefore(String date) {
        Flowable<ZhihuDailyBeforeVO> flowable = ApiManager.getInstence().getZhihuApiService().getBeforeDaily(date);
        Disposable disposable=flowable.subscribeOn(Schedulers.io())
                .map(zhihuDailyVO -> {
//                    String date = zhihuDailyVO.getDate();
                    List<ZhihuDailyItemVO> zhihuDailyItemVOs=zhihuDailyVO.getStories();
                    for (ZhihuDailyItemVO zhihuDailyItemVO : zhihuDailyItemVOs) {
                    }
                    return zhihuDailyVO;
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new CommonSubscriber<ZhihuDailyBeforeVO>() {

                    @Override
                    public void onNext(ZhihuDailyBeforeVO zhihuDailyBeforeVOs) {
                        if (mZhihuDailyView.isActive()){
                        mZhihuDailyView.setBeforeData(zhihuDailyBeforeVOs);
                        mZhihuDailyView.stateSuccess();

                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        LogUtil.e("error", e);
                        if (mZhihuDailyView.isActive()){
                            mZhihuDailyView.stateFailure();
                        }
                    }
                });
        RxDisposables.add(disposable);
    }

}
