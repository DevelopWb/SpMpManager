package org.easydarwin.easyplayer.util;

import com.orhanobut.hawk.Hawk;

/**
 * @Author: tobato
 * @Description: 作用描述
 * @CreateDate: 2021/9/24 22:22
 * @UpdateUser: 更新者
 * @UpdateDate: 2021/9/24 22:22
 */
public class HawkUtils {

    public  static  String  REG_CODE = "regCode";
    public  static  String  ALL_DEVS = "allDev";//所有的设备
    public  static  String  IP = "ip";



    public String getRtspPort() {
        return "554";
    }
    public String getRtmpPort() {
        return "10935";
    }

    public static String getRegCode() {
        return Hawk.get(HawkUtils.REG_CODE,"XbPARmJZ");
    }

    public static String getIP() {
        return Hawk.get(HawkUtils.IP,"106.75.224.48");
    }
}
