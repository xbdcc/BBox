package com.carlos.bbox.redenvelope;

import android.view.accessibility.AccessibilityNodeInfo;

import java.util.List;

/**
 * Created by caochang on 2017/8/26.
 */

public class AccessibilityUtil {

    private void click(AccessibilityNodeInfo accessibilityNodeInfo,String clickId) {
        List<AccessibilityNodeInfo> accessibilityNodeInfos = accessibilityNodeInfo.findAccessibilityNodeInfosByViewId(clickId);
        if (accessibilityNodeInfos.size()>0){

        }
        if (accessibilityNodeInfo != null) {
            List<AccessibilityNodeInfo> list = accessibilityNodeInfo.findAccessibilityNodeInfosByViewId(clickId);
            for (AccessibilityNodeInfo item : list) {
                item.performAction(AccessibilityNodeInfo.ACTION_CLICK);
            }
        }
    }
}
