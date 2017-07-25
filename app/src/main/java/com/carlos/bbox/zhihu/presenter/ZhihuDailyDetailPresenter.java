package com.carlos.bbox.zhihu.presenter;

import com.carlos.bbox.ApiManager;
import com.carlos.bbox.util.CommonSubscriber;
import com.carlos.bbox.zhihu.bean.ZhihuDailyDetailVO;
import com.carlos.bbox.zhihu.contract.ZhihuDailyDetailContract;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by caochang on 2017/7/21.
 */

public class ZhihuDailyDetailPresenter implements ZhihuDailyDetailContract.Presenter{

    private ZhihuDailyDetailContract.View mZhihuDialyDetailView;

    public ZhihuDailyDetailPresenter(ZhihuDailyDetailContract.View zhihuDialyDetailView) {
        mZhihuDialyDetailView = zhihuDialyDetailView;
        mZhihuDialyDetailView.setPresenter(this);
    }


    @Override
    public void start() {

    }

    @Override
    public void unsubscribe() {

    }

    @Override
    public void getData(String id) {
        Flowable<ZhihuDailyDetailVO> flowable = ApiManager.getInstence().getZhihuApiService().getDetailDaily(id);
        flowable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new CommonSubscriber<ZhihuDailyDetailVO>() {
                    @Override
                    public void onNext(ZhihuDailyDetailVO zhihuDailyDetailVO) {
                        mZhihuDialyDetailView.showContent(zhihuDailyDetailVO);
                    }
                });
    }
}
