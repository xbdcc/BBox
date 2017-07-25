package com.carlos.bbox.zhihu.contract;

import com.carlos.bbox.base.BasePresenter;
import com.carlos.bbox.base.BaseView;
import com.carlos.bbox.zhihu.bean.ZhihuDailyDetailVO;

/**
 * Created by caochang on 2017/7/21.
 */

public interface ZhihuDailyDetailContract {

    interface View extends BaseView<Presenter> {

        void showContent(ZhihuDailyDetailVO zhihuDailyDetailVO);

    }

    interface Presenter extends BasePresenter {

        void getData(String id);

    }
}
