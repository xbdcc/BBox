package com.carlos.bbox.zhihu.presenter;

import com.carlos.bbox.ApiManager;
import com.carlos.bbox.util.CommonSubscriber;
import com.carlos.bbox.util.LogUtil;
import com.carlos.bbox.zhihu.bean.ZhihuDailyBeforeVO;
import com.carlos.bbox.zhihu.bean.ZhihuDailyItemVO;
import com.carlos.bbox.zhihu.bean.ZhihuDailyTodayVO;
import com.carlos.bbox.zhihu.contract.ZhihuDailyContract;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
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
    }

    @Override
    public void getTodayData() {
        Flowable<ZhihuDailyTodayVO> flowable = ApiManager.getInstence().getZhihuApiService().getTodayDaily();
        flowable.subscribeOn(Schedulers.io())
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
                        mZhihuDailyView.setTodayData(zhihuDailyTodayVO);
                        mZhihuDailyView.stateSuccess();
//                        m
//                        mZhihuDailyAdapter.addItems(zhihuZhihuDailyItems);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mZhihuDailyView.stateFailure();
                        LogUtil.e("error", e);
                    }
                });
    }

    @Override
    public void getBefore(String date) {
        Flowable<ZhihuDailyBeforeVO> flowable = ApiManager.getInstence().getZhihuApiService().getBeforeDaily(date);
        flowable.subscribeOn(Schedulers.io())
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
                        mZhihuDailyView.setBeforeData(zhihuDailyBeforeVOs);
                        mZhihuDailyView.stateSuccess();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        LogUtil.e("error", e);
                        mZhihuDailyView.stateFailure();
                    }
                });
    }

}
