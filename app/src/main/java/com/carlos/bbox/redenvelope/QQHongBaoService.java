package com.carlos.bbox.redenvelope;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Toast;

import com.carlos.bbox.util.LogUtil;
import com.carlos.bbox.util.PreferencesUtils;
import com.carlos.bbox.util.ToastUtil;
import com.carlos.bbox.util.WakeupTools;

import java.util.List;

/**
 * Created by 小不点 on 2016/2/18.
 */
public class QQHongBaoService {

    private Context context;
    private AccessibilityEvent event;
    private AccessibilityNodeInfo nodeRoot;
    private AccessibilityNodeInfo nodeInfo;
    private AccessibilityNodeInfo parent;
    private AccessibilityNodeInfo child;
    private List<AccessibilityNodeInfo> list;

    private static final String TAG="QQHongBaoService";

    private static final String CLASS_QQ_LIST="com.tencent.mobileqq.activity.SplashActivity";//QQ聊天列表页
//    private static final String CLASS_RED_ENVELOPE="cooperation.qwallet.plugin.QWalletPayProgressDialog";//QQ红包框和红包详情页
    private static final String CLASS_RED_ENVELOPE="cooperation.qwallet.plugin.QWalletPluginProxyActivity";//QQ红包框和红包详情页

    private final static String QQ_NOTIFICATION_TIP = "[QQ红包]";
    private final static String QQ_DEFAULT_CLICK_OPEN = "点击拆开";
    private final static String QQ_HONG_BAO_PASSWORD = "口令红包";
    private final static String QQ_CLICK_TO_PASTE_PASSWORD = "点击输入口令";
    private final static String QQ_HONG_BAO_SEND="发送";

    private String packageName;
    private String className;
    private int eventType;
    private int size;
    private int i;
    private int time;

//    private boolean isHasReceived;//true已经通知或聊天页面收到红包
    private boolean isHasClicked;//true点击了红包
    private boolean isHasInput;//true输入了红包口令
    private boolean isHasOpened;//true发送红包口令
//    private boolean isHasReceivedList;//从聊天页面收到后点击红包
    private static boolean isFinished;//true查看抢红包的结果
    private static boolean isSingerClick=false;//一次红包口令不重复点击

    private int delayedOpenPutong=0;//延迟点击普通红包时间
    private int delayedOpenKouling=1;//延迟点击口令红包时间
    private int delayedClick=2;//延迟输入口令时间
    private int delayedSubmit=3;//延迟点击发送时间
    private int delayedClose=4;//延迟关闭红包页面时间


    public QQHongBaoService(Context context,AccessibilityEvent event, AccessibilityNodeInfo nodeRoot){
        this.context= context;
        this.event=event;
        this.nodeRoot=nodeRoot;

        packageName=event.getPackageName().toString();
        className=event.getClassName().toString();
        eventType=event.getEventType();

        nodeInfo=nodeRoot;

        switch (eventType){
            case AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED:
                LogUtil.d( "检测到QQ通知，文本为------------>" + event.getText());
                openNotification( event, event.getText());
                break;
            case AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED:
                LogUtil.d( "检测到QQ界面改变，页面为："+event.getClassName());
                if (event.getClassName().equals(CLASS_RED_ENVELOPE)){
                    closeResult();
                }
                break;
            case AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED:
                LogUtil.d( "检测到QQ内容改变");

//                WakeupTools.wakeUpAndUnlock(context);//解锁

                //没有发送按钮，不是领红包页面
                list=nodeInfo.findAccessibilityNodeInfosByText("发送");
                if(list.size()<1){
                    LogUtil.d("不是领红包页面");
                    return;
                }

                openCommonEnvelope();

                clickPasswordEnvelope();

                grabHongBao();

                inputEnvelopePassword();

                sendEnvelopePassword();

                clickChatPage();

                break;
        }

    }

    private void clickChatPage() {
        List<AccessibilityNodeInfo> node_hongbao=nodeInfo.findAccessibilityNodeInfosByText(QQ_NOTIFICATION_TIP);
        LogUtil.d( "聊天页面出现的红包" + node_hongbao.size());
//        delayedTime(500);
        //聊天页面出现红包
        node_hongbao=nodeInfo.findAccessibilityNodeInfosByText(QQ_NOTIFICATION_TIP);
        LogUtil.d( "聊天页面出现的红包" + node_hongbao.size());
        size=node_hongbao.size();
        if(size>0){
            AccessibilityNodeInfo parent=node_hongbao.get(size-1);
            LogUtil.d( "子节点：" + node_hongbao.get(size-1));
//            isHasReceivedList=true;
            parent.performAction(AccessibilityNodeInfo.ACTION_CLICK);
            LogUtil.d( "点击聊天页面中的红包");
        }
    }

    private void sendEnvelopePassword() {
        //发送红包口令
        List<AccessibilityNodeInfo> node_send=nodeInfo.findAccessibilityNodeInfosByText(QQ_HONG_BAO_SEND);
        LogUtil.d( "点击发送输入的口令个数" + node_send.size());
        size=node_send.size();
        if(size>0){
            AccessibilityNodeInfo parent=node_send.get(size-1);

            delayedControl(3);

            if(isHasInput){
                isHasInput=false;
                isHasOpened=true;
                parent.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                LogUtil.d( "点击发送输入的红包口令");
                isFinished=true;
            }
        }
    }

    private void inputEnvelopePassword() {
        //输入红包口令
        List<AccessibilityNodeInfo> node_input=nodeInfo.findAccessibilityNodeInfosByText(QQ_CLICK_TO_PASTE_PASSWORD);
        if(node_input!=null){
            LogUtil.d( "点击输入口令个数" + node_input.size());
            size=node_input.size();
            if(size>0){
                AccessibilityNodeInfo parent=node_input.get(size-1).getParent();
                if(isHasClicked){
                    isHasClicked=false;
                    isHasInput=true;
                    delayedControl(2);
                    parent.performAction(AccessibilityNodeInfo.ACTION_CLICK);
//                            LogUtil.d( "点击输入红包口令");
                }
            }
        }
    }

    private void openNotification(AccessibilityEvent event, List<CharSequence> texts) {
        if (texts.isEmpty())
            return;
        String text=texts.toString();
        if(text.contains(QQ_NOTIFICATION_TIP)){
            LogUtil.d( "准备打开通知栏");
            WakeupTools.wakeUpAndUnlock(context);
//            isHasReceived=true;
            //以下是精华，将QQ的通知栏消息打开
            Notification notification= (Notification) event.getParcelableData();
            PendingIntent pendingIntent=notification.contentIntent;
            try {
                LogUtil.d("准备打开通知栏");
                pendingIntent.send();
            } catch (PendingIntent.CanceledException e) {
                e.printStackTrace();
            }
        }
    }

    private void grabHongBao(){
//        WakeupTools.wakeUpAndUnlock(context);//解锁

        //没有发送按钮，不是领红包页面
        list=nodeInfo.findAccessibilityNodeInfosByText("发送");
        if(list.size()<1){
            LogUtil.d("不是领红包页面");
            return;
        }

        openCommonEnvelope();

        clickPasswordEnvelope();


    }

    private void clickPasswordEnvelope() {
        //口令红包
        list=nodeInfo.findAccessibilityNodeInfosByText(QQ_HONG_BAO_PASSWORD);
        LogUtil.i("口令红包的个数为："+list.size());
        size=list.size();
        //领取最新的红包
        if(size>0){
            AccessibilityNodeInfo node_child=list.get(size-1);

            if(node_child!=null&&node_child.getClassName().equals("android.widget.TextView")
                    &&node_child.getText().toString().equals(QQ_HONG_BAO_PASSWORD)){
                if(!isSingerClick){
                    isSingerClick=true;
                    isHasClicked=true;
                    LogUtil.d( "点击领红包");
                    Toast.makeText(context,"点击领红包",Toast.LENGTH_SHORT).show();
//                    WakeupTools.wakeUpAndUnlock(context);//解锁
                    delayedControl(1);
                    node_child.getParent().performAction(AccessibilityNodeInfo.ACTION_CLICK);

                }
            }
        }
    }

    /**
     * 打开普通红包
     */
    private void openCommonEnvelope() {
        //普通红包
        list=nodeInfo.findAccessibilityNodeInfosByText(QQ_DEFAULT_CLICK_OPEN);
        LogUtil.i("普通红包的个数为："+list.size());
        size=list.size();
        //领取最新的红包
        if(size>0){
            AccessibilityNodeInfo parent=list.get(size-1).getParent();
//            LogUtil.i( "----------------->普通红包：" + parent);
            if(parent!=null){
                isFinished=true;
                WakeupTools.wakeUpAndUnlock(context);//解锁
                delayedControl(0);
                parent.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                isHasClicked=true;
                isHasOpened=true;
                LogUtil.d("isfinish-------->"+isFinished);
            }
        }
    }

    private void closeResult(){
        isFinished=true;//测试用，单独关闭红包页面。
        LogUtil.d("isFinished" + ""+isFinished);
        delayedTime(1000);
        if(isFinished){
            list=nodeInfo.findAccessibilityNodeInfosByText("查看领取详情");
            LogUtil.d("需要关闭的页面数量："+list.size());
            isSingerClick=false;

            saveHongbao();

            list=nodeInfo.findAccessibilityNodeInfosByText("关闭");
            for (AccessibilityNodeInfo accessibilityNodeInfo : list) {
                LogUtil.d("guanbi"+accessibilityNodeInfo.getContentDescription()+accessibilityNodeInfo.getText());
            }

            if (PreferencesUtils.getQQLingquDelay() != 11) {
                delayedControl(4);
            }
            if (list.size()>0){
                ToastUtil.showToast(context,"关闭");
                list.get(0).performAction(AccessibilityNodeInfo.ACTION_CLICK);
                LogUtil.d("关闭弹出的红包框");
                isFinished = false;
            }

        }
    }


    public void delayedControl(int state){
        delayedControlOne(state);
//        delayedControlTwo(state);
    }


    public void delayedControlTwo(int state){
        switch (state){
            case 0:
                time=PreferencesUtils.getQQPutongDelay()*1000;
                LogUtil.d("------>putong"+time);
                break;
            case 1:
                time=1*1000;
                break;
            case 2:
                //                delayedTime(1*1000);
                time=1*1000;
                LogUtil.d("------>点击口令"+time);
                break;
            case 3:
//                delayedTime(PreferencesUtils.getQQKoulingDelay()-2);
                time=(PreferencesUtils.getQQKoulingDelay()-2)*1000;
                LogUtil.d( "------>发送口令" + time);
                break;
            case 4:
                time=PreferencesUtils.getQQLingquDelay()*1000;
                LogUtil.d( "------>关闭窗口" + time);
                break;
        }
        delayedTime(time);
        LogUtil.d("延迟时间：-------->"+time);
    }

    /**
     * 自己的时间
     * @param state
     */
    public void delayedControlOne(int state){
        switch (state){
            case 0:
                time=PreferencesUtils.getQQPutongDelay()*1000;
//                LogUtil.d("------>putong"+time);
                //设置0.1秒内随机抢
//                Random random=new Random();
//                time=random.nextInt(100);
//                delayedTime(time);
                break;
            case 1:
                //设置0.1秒内随机抢
//                Random random=new Random();
//                time=random.nextInt(100);
//                delayedTime(time);
                break;
            case 2:
//                //设置0.1秒内随机抢
//                random=new Random();
//                time=random.nextInt(100);
//                delayedTime(time);
                break;
            case 3:
               // 设置0.5-1秒内随机抢
//                random=new Random();
//                time=random.nextInt(500);
//                time=500+time;
//                delayedTime(time);
                break;
            case 4:
//                delayedTime(1000);
                break;
        }
    }

    public void delayedTime(int time){
        LogUtil.d("延迟时间：-------->"+time);
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    private void saveHongbao(){
        LogUtil.d("gfgfgfgf");

        list=nodeInfo.findAccessibilityNodeInfosByViewId("com.tencent.mobileqq:id/hb_error_tv");//红包被领完
        Toast.makeText(context, "保存红包记录", Toast.LENGTH_SHORT).show();
        //如果没有被领完才保存记录
        if(list.size()<1){
            LogUtil.d("kkkkkkkkkkkk");
            list=nodeInfo.findAccessibilityNodeInfosByText("口令红包");
            for (AccessibilityNodeInfo accessibilityNodeInfo : list) {
                ToastUtil.showToast(context,"领取的是口令红包");
            }

            list=nodeInfo.findAccessibilityNodeInfosByText("元");
            for (AccessibilityNodeInfo accessibilityNodeInfo : list) {
                ToastUtil.showToast(context,"元");
                LogUtil.i("元");
            }

//            QQ_Hongbao qq_hongbao=new QQ_Hongbao();
//            SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm");
//            Date date=new Date(System.currentTimeMillis());
//            String time=dateFormat.format(date);
//            qq_hongbao.setTime(time);
//
//            list=nodeInfo.findAccessibilityNodeInfosByViewId("com.tencent.mobileqq:id/sender_info");//谁发送的红包
//            String send_info=list.get(0).getText().toString();
//            qq_hongbao.setSend_info(send_info);
//
//            list=nodeInfo.findAccessibilityNodeInfosByViewId("com.tencent.mobileqq:id/wish_word");//红包种类
//            String wish_word=list.get(0).getText().toString();
//            qq_hongbao.setWish_word(wish_word);
//
//            list=nodeInfo.findAccessibilityNodeInfosByViewId("com.tencent.mobileqq:id/hb_count_tv");//红包金额
//            String hb_count_tv=list.get(0).getText().toString();
//            qq_hongbao.setHb_count_tv(hb_count_tv);
//
//
//            float count= PreferencesUtils.getQQHongbaoRecordCount();
//            if(count==0){
//                PreferencesUtils.setQQHongbaoRecordTime(time);//清空过数据，重新设置开始时间
//            }
//            count+= Float.valueOf(hb_count_tv);
//            PreferencesUtils.setQQHongbaoRecordCount(count);
//
//            DatabaseHelper databaseHelper=new DatabaseHelper(context);
//            databaseHelper.insert(qq_hongbao);

        }

    }
}
