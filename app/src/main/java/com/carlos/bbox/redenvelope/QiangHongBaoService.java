package com.carlos.bbox.redenvelope;

import android.accessibilityservice.AccessibilityService;
import android.app.KeyguardManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.PowerManager;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import com.carlos.bbox.MainActivity;
import com.carlos.bbox.MyApplication;
import com.carlos.bbox.R;
import com.carlos.bbox.util.LogUtil;
import com.carlos.bbox.util.PreferencesUtils;

import java.util.List;

/**
 * Created by 小不点 on 2016/2/5.
 */
public class QiangHongBaoService extends AccessibilityService {

    private static final String TAG="QiangHongBaoService";
    private static final String TAG_SHUA="ShuaYiSHua";
    public  boolean isStopUse;


    private AccessibilityNodeInfo nodeRoot;

    public static final String PACKAGE_QQ = "com.tencent.mobileqq";//QQ包名
    public static final String PACKAGE_WECHAT = "com.tencent.mm";//微信包名

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.d("service oncreate.");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtil.d("service onstartcommand.");
        Notification.Builder builder = new Notification.Builder(MyApplication.getInstance());
        Intent notificationIntent = new Intent(this, MainActivity.class);

        builder.setContentIntent(PendingIntent.getActivity(this, 0, notificationIntent, 0))
                .setLargeIcon(BitmapFactory.decodeResource(this.getResources(), R.mipmap.ic_launcher)) // set the large icon in the drop down list.
                .setContentTitle("BBox") // set the caption in the drop down list.
                .setSmallIcon(R.mipmap.ic_launcher) // set the small icon in state.
                .setContentText("BBox") // set context content.
                .setWhen(System.currentTimeMillis()); // set the time for the notification to occur.

        Notification notification = builder.build();
        notification.defaults = Notification.DEFAULT_SOUND;// set default sound.

        startForeground(110, notification);
        flags = Service.START_FLAG_REDELIVERY;
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.d("service ondestroy.");
    }

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        LogUtil.d("service connect");
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {

        if (isStopUse)
            return;
        nodeRoot=getRootInActiveWindow();
        if(nodeRoot==null){
            LogUtil.d("rootWindow为空");
            return;
        }


//        printEventLog(event);

        String packageName=event.getPackageName().toString();
        if (packageName.equals(PACKAGE_QQ)&& PreferencesUtils.getQQUseStatus()){
            new QQHongBaoService(this,event,nodeRoot);
        }
        if (packageName.equals(PACKAGE_WECHAT)){
            new WechatHongbaoService(this,event,nodeRoot);
        }

//        try {
//            String packageName=event.getPackageName().toString();
//            if (packageName.equals(PACKAGE_QQ)&& PreferencesUtils.getQQUseStatus()){
//                new QQHongBaoService(this,event,nodeRoot);
//            }
//            if (packageName.equals(PACKAGE_WECHAT)){
//                new WechatHongbaoService(this,event,nodeRoot);
//            }
//        }catch (Exception e){
//            LogUtil.e("error:",e);
//            onServiceConnected();
//        }

    }

    private void printEventLog(AccessibilityEvent event) {
        LogUtil.i(  "-------------------------------------------------------------");
        int eventType = event.getEventType(); //事件类型
        LogUtil.i( "PackageName:" + event.getPackageName() + ""); // 响应事件的包名
        LogUtil.i( "Source:" + event.getSource() + ""); // 响应事件
        LogUtil.i( "Source Class:" + event.getClassName().toString() + ""); // 事件源的类名
        LogUtil.i(  "Description:" + event.getContentDescription()+ ""); // 事件源描述
        LogUtil.i(  "Event Type(int):" + eventType + "");
        String eventText = "";

        switch (eventType) {
            case AccessibilityEvent.TYPE_VIEW_CLICKED:
                eventText = "TYPE_VIEW_CLICKED";
                break;
            case AccessibilityEvent.TYPE_VIEW_FOCUSED:
                eventText = "TYPE_VIEW_FOCUSED";
                break;
            case AccessibilityEvent.TYPE_VIEW_LONG_CLICKED:
                eventText = "TYPE_VIEW_LONG_CLICKED";
                break;
            case AccessibilityEvent.TYPE_VIEW_SELECTED:
                eventText = "TYPE_VIEW_SELECTED";
                break;
            case AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED:
                eventText = "TYPE_VIEW_TEXT_CHANGED";
                break;
            case AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED://窗体状态改变
                eventText = "TYPE_WINDOW_STATE_CHANGED";
                break;
            case AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED:// 通知栏事件
                eventText = "TYPE_NOTIFICATION_STATE_CHANGED";
                break;
            case AccessibilityEvent.TYPE_TOUCH_EXPLORATION_GESTURE_END:
                eventText = "TYPE_TOUCH_EXPLORATION_GESTURE_END";
                break;
            case AccessibilityEvent.TYPE_ANNOUNCEMENT:
                eventText = "TYPE_ANNOUNCEMENT";
                break;
            case AccessibilityEvent.TYPE_TOUCH_EXPLORATION_GESTURE_START:
                eventText = "TYPE_TOUCH_EXPLORATION_GESTURE_START";
                break;
            case AccessibilityEvent.TYPE_VIEW_HOVER_ENTER:
                eventText = "TYPE_VIEW_HOVER_ENTER";
                break;
            case AccessibilityEvent.TYPE_VIEW_HOVER_EXIT:
                eventText = "TYPE_VIEW_HOVER_EXIT";
                break;
            case AccessibilityEvent.TYPE_VIEW_SCROLLED:
                eventText = "TYPE_VIEW_SCROLLED";
                break;
            case AccessibilityEvent.TYPE_VIEW_TEXT_SELECTION_CHANGED:
                eventText = "TYPE_VIEW_TEXT_SELECTION_CHANGED";
                break;
            case AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED:
                eventText = "TYPE_WINDOW_CONTENT_CHANGED";
                break;
        }
        eventText = eventText + ":" + eventType;
        LogUtil.i(eventText);
        for (CharSequence txt : event.getText()) {
            Log.i(TAG, "text:" + txt);
        }

        List<AccessibilityNodeInfo>  list=nodeRoot.findAccessibilityNodeInfosByText("查看领取详情");
        LogUtil.d("需要关闭的页面数量："+list
                .size());
        Log.i(TAG, "-------------------------------------------------------------");
    }


    @Override
    public void onInterrupt() {
        Log.e(TAG,"出错");
    }



    //唤醒屏幕解锁
    public static void wakeUpAndUnlock(Context context){
        KeyguardManager km=(KeyguardManager)context.getSystemService(Context.KEYGUARD_SERVICE);
        KeyguardManager.KeyguardLock kl=km.newKeyguardLock("unLock");
        //解锁
        kl.disableKeyguard();
        //获取电源管理器对象
        PowerManager pm=(PowerManager)context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wl=pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP| PowerManager.SCREEN_DIM_WAKE_LOCK,"wakeup");
        //点亮屏幕
        wl.acquire();
        //释放资源
        wl.release();

    }
}
