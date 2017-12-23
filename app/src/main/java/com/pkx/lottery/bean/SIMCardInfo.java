package com.pkx.lottery.bean;

import android.content.Context;
import android.telephony.TelephonyManager;

public class SIMCardInfo {
	private TelephonyManager telephonyManager;  
    /** 
     * 国际移动用户识别码 
     */  
    private String IMSI;  
  
    public SIMCardInfo(Context context) {  
        telephonyManager = (TelephonyManager) context  
                .getSystemService(Context.TELEPHONY_SERVICE);  
    }  
    public static TelephonyManager getManager(Context context){
    	return (TelephonyManager) context  
                .getSystemService(Context.TELEPHONY_SERVICE);  
    }
  
    /** 
     * Role:获取当前设置的电话号码 
     * <BR>Date:2012-3-12 
     * <BR>@author CODYY)peijiangping 
     */  
    public String getNativePhoneNumber() {  
        String NativePhoneNumber=null;  
        NativePhoneNumber=telephonyManager.getLine1Number();  
        return NativePhoneNumber;  
    }  
  
    /** 
     * Role:Telecom service providers获取手机服务商信息 <BR> 
     * 需要加入权限<uses-permission 
     * android:name="android.permission.READ_PHONE_STATE"/> <BR> 
     * Date:2012-3-12 <BR> 
     *  
     * @author CODYY)peijiangping 
     */  
    public String getProvidersName() {  
        String ProvidersName = null;  
        // 返回唯一的用户ID;就是这张卡的编号神马的  
        IMSI = telephonyManager.getSubscriberId();  
        // IMSI号前面3位460是国家，紧接着后面2位00 02是中国移动，01是中国联通，03是中国电信。  
        System.out.println(IMSI);  
        if (IMSI.startsWith("46000") || IMSI.startsWith("46002")) {  
            ProvidersName = "中国移动";  
        } else if (IMSI.startsWith("46001")) {  
            ProvidersName = "中国联通";  
        } else if (IMSI.startsWith("46003")) {  
            ProvidersName = "中国电信";  
        }  
        return ProvidersName;  
    }  
}
