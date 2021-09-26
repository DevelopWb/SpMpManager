package org.easydarwin.easyplayer;

import android.app.Activity;

import com.basenetlib.util.NetWorkUtil;
import com.juntai.wisdom.basecomponent.app.BaseApplication;
import com.orhanobut.hawk.Hawk;

/**
 * Created by afd on 8/13/16.
 */
public class MyApp extends BaseApplication {


    @Override
    public void appBackground(boolean isBackground, Activity activity) {

    }

    @Override
    public String getTinkerId() {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Hawk.init(this).build();
        NetWorkUtil.initContext(this);
    }

}
