package com.carlos.bbox.redenvelope;

import android.accessibilityservice.AccessibilityService;
import android.annotation.TargetApi;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.os.Build;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import com.carlos.bbox.MyApplication;
import com.carlos.bbox.redenvelope.entity.WechatRedEnvelope;
import com.carlos.bbox.util.LogUtil;
import com.carlos.bbox.util.WakeupTools;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by caochang on 2017/8/26.
 */

public class WechatHongbaoService {

    private AccessibilityService accessibilityService;
    private AccessibilityNodeInfo nodeRoot;
    private AccessibilityNodeInfo nodeInfo;
    private List<AccessibilityNodeInfo> parents=new ArrayList<>();
    private Context mContext;

    private List<AccessibilityNodeInfo> list;

    private static final String CLASS_WEIXIN_LIST="com.tencent.mm.ui.LauncherUI";//微信聊天列表页
    private static final String CLASS_WEIXIN_ENVELOPE_DIALOG="com.tencent.mm.plugin.luckymoney.ui.En_fba4b94f";//微信红包点击弹出的Dialog页面
    private static final String CLASS_WEIXIN_ENVELOPE_DETAIL="com.tencent.mm.plugin.luckymoney.ui.LuckyMoneyDetailUI";//微信红包详情页

    private final static String WECHAT_NOTIFICATION_TIP = "[微信红包]";
    private final static String WECHAT_PASS_DATE = "过期";


    private String packageName;
    private String className;
    private int eventType;
    private int size;
    private int i;
    private int time;

    private boolean isHasClicked;//true点击了红包
    private boolean isHasOpened;//true发送红包口令
    private static boolean isFinished;//true查看抢红包的结果

    private static boolean isSingerClick=false;//一次红包口令不重复点击

    private final static int delayedOpenPutong=0;//延迟点击普通红包时间
    private final static int delayedOpenKouling=1;//延迟点击口令红包时间
    private final static int delayedClick=2;//延迟输入口令时间
    private final static int delayedSubmit=3;//延迟点击发送时间
    private final static int delayedClose=4;//延迟关闭红包页面时间



    public WechatHongbaoService(AccessibilityService accessibilityService,AccessibilityEvent event, AccessibilityNodeInfo nodeRoot){
        mContext= MyApplication.getInstance();
        this.accessibilityService=accessibilityService;
        this.nodeRoot=nodeRoot;
        int eventType = event.getEventType();
        switch (eventType) {
            //当通知栏发生改变时
            case AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED:
                openNotification(event);
                break;
            //当窗口的状态发生改变时
            case AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED:

//                LogUtil.d("检测到QQ界面改变，页面为："+event.getClassName());
//                if (CLASS_WEIXIN_LIST.equals(event.getClassName())){
//                    clickRedEnvelope();
//                }

                if (event.getClassName().equals(CLASS_WEIXIN_ENVELOPE_DIALOG)){
                    LogUtil.d("准备点击开红包按钮");
                    openRenEnvelope();
                }

                if (event.getClassName().equals(CLASS_WEIXIN_ENVELOPE_DETAIL)){
                    closeResult();
                }
                break;
            case AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED:
                clickRedEnvelope();

                break;
        }
    }

    private void clickRedEnvelope(){
        list=nodeRoot.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/a9q");
        LogUtil.d("list size:"+list.size());
        for(int i=list.size()-1;i>=0;i--){
            nodeInfo=list.get(i);
            LogUtil.d(nodeInfo.toString());
            LogUtil.d(nodeInfo.getText().toString());

            //筛选点击，被领完的已领完的过期的都不领。查看红包领取红包的领。
            if ("查看红包".equals(nodeInfo.getText().toString())){
                LogUtil.d("kkkkkkkkk");
            }
            if ("查看红包".equals(nodeInfo.getText().toString())||"领取红包".equals(nodeInfo.getText().toString())){
                isHasClicked=true;
                nodeInfo.getParent().performAction(AccessibilityNodeInfo.ACTION_CLICK);
                LogUtil.d("kkkkkkkkk"+isHasClicked);
                break;
            }
        }

    }

    private void openRenEnvelope(){
        LogUtil.d("isHasClicked:"+isHasClicked);
        if (isHasClicked){
            //点击拆开红包

            list=nodeRoot.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/bp6");
            LogUtil.d("list size:"+list.size());
            for (AccessibilityNodeInfo accessibilityNodeInfo : list) {
                LogUtil.d(accessibilityNodeInfo.toString());
                isHasOpened=true;
                isHasClicked=false;
                LogUtil.d("isHasClicked:"+isHasClicked);
                accessibilityNodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
            }

            //红包派完了
            list=nodeRoot.findAccessibilityNodeInfosByText("完了");
            LogUtil.d("list size2:"+list.size());
            if (list.size()>0){
                list=nodeRoot.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/bmu");
                for (AccessibilityNodeInfo accessibilityNodeInfo : list) {
                    accessibilityNodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                }
            }

            //红包过期
            list=nodeRoot.findAccessibilityNodeInfosByText(WECHAT_PASS_DATE);
            LogUtil.d("list size2:"+list.size());
            if (list.size()>0){
                list=nodeRoot.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/bmu");
                for (AccessibilityNodeInfo accessibilityNodeInfo : list) {
                    accessibilityNodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                }
            }
        }

    }

    private void closeResult(){
        isHasOpened=true;//测试单独关闭页面
        if (isHasOpened){
            WechatRedEnvelope wechatRedEnvelope=new WechatRedEnvelope();

            SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date date=new Date(System.currentTimeMillis());
            String time=dateFormat.format(date);
            wechatRedEnvelope.setTime(time);

            list=nodeRoot.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/ble");
            if (list.size()>0&&list.get(0).getText()!=null){
                String whoSend=list.get(0).getText().toString();
                LogUtil.d("收到的微信红包发送者为："+whoSend);
                wechatRedEnvelope.setWho_send(whoSend);
            }

            list=nodeRoot.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/bli");
            if (list.size()>0&&list.get(0).getText()!=null){
                String count=list.get(0).getText().toString();
                LogUtil.d("收到的微信红包金额为："+count);
                wechatRedEnvelope.setCount(count);
            }

            isHasOpened=false;
            accessibilityService.performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK);

        }
    }


    private void openNotification(AccessibilityEvent event) {
        List<CharSequence> texts = event.getText();
        if (texts.isEmpty())
            return;
        String text=texts.toString();
        if(text.contains(WECHAT_NOTIFICATION_TIP)){
            LogUtil.d( "准备打开通知栏");
            WakeupTools.wakeUpAndUnlock(mContext);
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
//        LogUtil.d("检测到微信通知，文本为："+texts.toString());
//        if (!texts.isEmpty()) {
//            for (CharSequence text : texts) {
//                String content = text.toString();
//                if (content.contains(WECHAT_NOTIFICATION_TIP)) {
//                    //模拟打开通知栏消息，即打开微信
//                    if (event.getParcelableData() != null &&
//                            event.getParcelableData() instanceof Notification) {
//                        Notification notification = (Notification) event.getParcelableData();
//                        PendingIntent pendingIntent = notification.contentIntent;
//                        try {
////                            LogUtil.d("准备打开通知栏");
//                            pendingIntent.send();
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//            }
//        }
    }

    /**
     * 通过ID获取控件，并进行模拟点击
     * @param clickId
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void inputClick(String clickId) {
        AccessibilityNodeInfo nodeInfo = nodeRoot;
        if (nodeInfo != null) {
            List<AccessibilityNodeInfo> list = nodeInfo.findAccessibilityNodeInfosByViewId(clickId);
            for (AccessibilityNodeInfo item : list) {
                item.performAction(AccessibilityNodeInfo.ACTION_CLICK);
            }
        }
    }

    /**
     * 获取List中最后一个红包，并进行模拟点击
     */
    private void getLastPacket() {
        AccessibilityNodeInfo rootNode = nodeRoot;
        recycle(rootNode);
        if(parents.size()>0){
            parents.get(parents.size() - 1).performAction(AccessibilityNodeInfo.ACTION_CLICK);
        }
    }

    /**
     * 回归函数遍历每一个节点，并将含有"领取红包"存进List中
     *
     * @param info
     */
    public void recycle(AccessibilityNodeInfo info) {
        if (info.getChildCount() == 0) {
            if (info.getText() != null) {
                if ("领取红包".equals(info.getText().toString())) {
                    if (info.isClickable()) {
                        info.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                    }
                    AccessibilityNodeInfo parent = info.getParent();
                    while (parent != null) {
                        if (parent.isClickable()) {
                            parents.add(parent);
                            break;
                        }
                        parent = parent.getParent();
                    }
                }
            }
        } else {
            for (int i = 0; i < info.getChildCount(); i++) {
                if (info.getChild(i) != null) {
                    recycle(info.getChild(i));
                }
            }
        }
    }

}
