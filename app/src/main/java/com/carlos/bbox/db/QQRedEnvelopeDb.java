package com.carlos.bbox.db;

import com.carlos.bbox.redenvelope.entity.QQRedEnvelope;
import com.carlos.bbox.util.LogUtil;

import java.util.List;

/**
 * Created by caochang on 2017/8/30.
 */

public class QQRedEnvelopeDb {

    private static QQRedEnvelopeDao sQQRedEnvelopeDao = GreenDaoManager.getInstance().getSession().getQQRedEnvelopeDao();

    public static synchronized void insertData(QQRedEnvelope qqRedEnvelope) {
        try {
            sQQRedEnvelopeDao.insert(qqRedEnvelope);
        }catch (Exception e){
            LogUtil.e("insert data error:",e);
        }
    }

    public static synchronized List<QQRedEnvelope> getAllData() {
        return sQQRedEnvelopeDao.loadAll();
    }

}
