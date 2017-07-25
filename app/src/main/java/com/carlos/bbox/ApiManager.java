package com.carlos.bbox;

import com.carlos.bbox.zhihu.ZhihuApi;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by caochang on 2017/6/27.
 */

public class ApiManager {

    public static final int CONNECT_TIME_OUT = 8;//超时时间

    private static ApiManager apiManager;

    public static ApiManager getInstence() {
        if (apiManager == null) {
            synchronized (ApiManager.class) {
                if (apiManager == null) {
                    apiManager = new ApiManager();
                }
            }
        }
        return apiManager;
    }

    public  ZhihuApi getZhihuApiService() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(CONNECT_TIME_OUT, TimeUnit.SECONDS).build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(ZhihuApi.HOST)
                .addConverterFactory(GsonConverterFactory.create())// 添加Gson转换器
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit.create(ZhihuApi.class);

    }

//    private static final Interceptor REWRITE_CACHE_CONTROL_INTERCEPTOR = new Interceptor() {
//        @Override
//        public Response intercept(Chain chain) throws IOException {
//            Response originalResponse = chain.proceed(chain.request());
//            if (NetWorkUtil.isNetWorkAvailable(MyApplication.getContext())) {
//                int maxAge = 60; // 在线缓存在1分钟内可读取
//                return originalResponse.newBuilder()
//                        .removeHeader("Pragma")
//                        .removeHeader("Cache-Control")
//                        .header("Cache-Control", "public, max-age=" + maxAge)
//                        .build();
//            } else {
//                int maxStale = 60 * 60 * 24 * 28; // 离线时缓存保存4周
//                return originalResponse.newBuilder()
//                        .removeHeader("Pragma")
//                        .removeHeader("Cache-Control")
//                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
//                        .build();
//            }
//        }
//    };
//    private static File httpCacheDirectory = new File(MyApplication.getContext().getCacheDir(), "zhihuCache");
//    private static int cacheSize = 10 * 1024 * 1024; // 10 MiB
//    private static Cache cache = new Cache(httpCacheDirectory, cacheSize);
//    private static OkHttpClient client = new OkHttpClient.Builder()
//            .addNetworkInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR)
//            .addInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR)
//            .cache(cache)
//            .build();
//    private ZhihuApi zhihuApi;
//    private TopNews topNews;
//    private Object zhihuMonitor = new Object();
//
//    public ZhihuApi getZhihuApiService() {
//        if (zhihuApi == null) {
//            synchronized (zhihuMonitor) {
//                if (zhihuApi == null) {
//                    zhihuApi = new Retrofit.Builder()
//                            .baseUrl("http://news-at.zhihu.com")
//                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
//                            .client(client)
//                            .addConverterFactory(GsonConverterFactory.create())
//                            .build().create(ZhihuApi.class);
//                }
//            }
//        }
//
//        return zhihuApi;
//    }


}
