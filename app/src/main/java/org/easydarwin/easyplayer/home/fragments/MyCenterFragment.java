package org.easydarwin.easyplayer.home.fragments;


import android.text.TextUtils;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.juntai.wisdom.basecomponent.base.BaseMvpFragment;
import com.juntai.wisdom.basecomponent.utils.HawkProperty;
import com.juntai.wisdom.basecomponent.utils.ToastUtils;
import com.orhanobut.hawk.Hawk;

import org.easydarwin.easyplayer.R;
import org.easydarwin.easyplayer.home.MainPageContract;
import org.easydarwin.easyplayer.home.MainPagePresent;
import org.easydarwin.easyplayer.util.HawkUtils;
import org.easydarwin.easyplayer.util.SPUtil;


/**
 * @aouther tobato
 * @description 描述  homepage
 * @date 2021/4/18 14:59
 */
public class MyCenterFragment extends BaseMvpFragment<MainPagePresent> implements MainPageContract.IMainPageView
    {


    private TextView mRegistCodeValue;
    private EditText mPushServerIpEt;
    private Switch mUdpSwitch;
    private Switch mCodecSwitch;
    private View view;
    /**
     * 保存
     */
    private TextView mSaveIpConfigTv;

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    @Override
    protected void lazyLoad() {
        mRegistCodeValue.setText(Hawk.get(HawkProperty.REG_CODE));
        mPushServerIpEt.setText(HawkUtils.getIP());
    }

    @Override
    protected MainPagePresent createPresenter() {
        return new MainPagePresent();
    }


    @Override
    public void onSuccess(String tag, Object o) {
    }

    @Override
    public void onError(String tag, Object o) {
        ToastUtils.error(mContext, String.valueOf(o));
    }



    @Override
    protected int getLayoutRes() {
        return R.layout.my_center;
    }

    @Override
    protected void initView() {

        mRegistCodeValue = (TextView) getView(R.id.regist_code_value);

        mPushServerIpEt = (EditText) getView(R.id.push_server_ip_et);
        mUdpSwitch = (Switch) getView(R.id.udp_switch);
        mCodecSwitch = (Switch) getView(R.id.codec_switch);

        mUdpSwitch.setChecked(SPUtil.getUDPMode(mContext));
        mCodecSwitch.setChecked(SPUtil.getMediaCodec(mContext));

        mUdpSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SPUtil.setUDPMode(mContext, isChecked);
            }
        });

        mCodecSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SPUtil.setMediaCodec(mContext, isChecked);
            }
        });
        mSaveIpConfigTv = (TextView) getView(R.id.save_ip_config_tv);
        mSaveIpConfigTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(getBaseActivity().getTextViewValue(mPushServerIpEt))) {
                    ToastUtils.toast(mContext,"请输入IP地址");
                    return;
                }
                Hawk.put(HawkUtils.IP,getBaseActivity().getTextViewValue(mPushServerIpEt));
                ToastUtils.toast(mContext,"保存成功");
            }
        });
    }

    @Override
    protected void initData() {

    }


}
