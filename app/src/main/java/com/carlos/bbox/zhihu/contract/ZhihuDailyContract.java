package com.carlos.bbox.zhihu.contract;

import com.carlos.bbox.base.BasePresenter;
import com.carlos.bbox.base.BaseView;
import com.carlos.bbox.zhihu.bean.ZhihuDailyBeforeVO;
import com.carlos.bbox.zhihu.bean.ZhihuDailyTodayVO;

/**
 * Created by caochang on 2017/7/13.
 */

public interface ZhihuDailyContract {

    interface View extends BaseView<Presenter> {

        void setTodayData(ZhihuDailyTodayVO zhihuDailyLastVO);

        void setBeforeData(ZhihuDailyBeforeVO zhihuDailyBeforeVO);

        boolean isActive();

    }

    interface Presenter extends BasePresenter {

        void getTodayData();

        void getBefore(String date);

    }
}
