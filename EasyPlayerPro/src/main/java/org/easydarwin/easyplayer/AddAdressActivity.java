package org.easydarwin.easyplayer;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.juntai.wisdom.basecomponent.mvp.BasePresenter;

import org.easydarwin.easyplayer.base.BaseAppActivity;
import org.easydarwin.easyplayer.bean.VideoAddrBean;
import org.easydarwin.easyplayer.util.HawkUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * created by 8级大的狂风
 * created date 2019/12/26 20:10.
 * application   添加地址
 */
public class AddAdressActivity extends BaseAppActivity implements View.OnClickListener {
    public static final int REQUEST_ADD_DEVICE = 1004;
    public static final String DEVICE_INFO = "added_device_info";  //添加的设备信息
    /**
     * 输入设备名称
     */
    private EditText mDevNameEt;
    /**
     * 保存
     */
    private TextView mSaveDeviceTv;
    /**
     * 请输入IP地址
     */
    private EditText mNewMediaSourceIpEt;
    /**
     * 请输入注册码
     */
    private EditText mNewMediaRegCodeEt;
    /**
     * 智能取证S
     */
    private RadioButton mRtspRb;
    /**
     * 智能取证M
     */
    private RadioButton mRtmpRb;
    private RadioGroup mUrlTypeRg;
    /**
     * 取消
     */
    private TextView mCancelTv;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.save_device_tv:
                String name = mDevNameEt.getText().toString().trim();
                //                String ip = binding.newMediaSourceIpEt.getText().toString().trim();
                String regCode = mNewMediaRegCodeEt.getText().toString().trim();
                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(getApplicationContext(), "请输入设备名称", Toast.LENGTH_LONG).show();
                    return;
                }
                //                if (TextUtils.isEmpty(ip)) {
                //                    Toast.makeText(getApplicationContext(), "请输入IP地址", Toast.LENGTH_LONG).show();
                //                    return;
                //                }
                if (TextUtils.isEmpty(regCode)) {
                    Toast.makeText(getApplicationContext(), "请输入注册码", Toast.LENGTH_LONG).show();
                    return;
                }
                VideoAddrBean videoAddrBean = new VideoAddrBean();
                videoAddrBean.setName(name);
                //                vedioAddrBean.setIp(ip);
                StringBuilder sb = new StringBuilder();

                if (mRtspRb.isChecked()) {
                    videoAddrBean.setUrlType(1);
                    videoAddrBean.setUrl(sb.append("rtsp://").append(HawkUtils.getIP()).append(":554/").append(regCode).append(".sdp").toString());
                } else {
                    videoAddrBean.setUrlType(0);
                    videoAddrBean.setUrl(sb.append("rtmp://").append(HawkUtils.getIP()).append(":10935/hls/").append(regCode).toString());

                }
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                videoAddrBean.setAddTime(sdf.format(new Date()));
                videoAddrBean.setRegCode(regCode);
                Intent intent = new Intent();
                intent.putExtra(DEVICE_INFO, videoAddrBean);
                setResult(REQUEST_ADD_DEVICE, intent);
                finish();
                break;
            default:
                break;
            case R.id.cancel_tv:
                finish();
                break;
        }
    }

    @Override
    public int getLayoutView() {
        return R.layout.activity_add_dev;
    }

    @Override
    public void initView() {
        setTitleName("添加设备");
        mDevNameEt = (EditText) findViewById(R.id.dev_name_et);
        mSaveDeviceTv = (TextView) findViewById(R.id.save_device_tv);
        mNewMediaSourceIpEt = (EditText) findViewById(R.id.new_media_source_ip_et);
        mNewMediaRegCodeEt = (EditText) findViewById(R.id.new_media_reg_code_et);
        mSaveDeviceTv.setOnClickListener(this);
        mRtspRb = (RadioButton) findViewById(R.id.rtsp_rb);
        mRtmpRb = (RadioButton) findViewById(R.id.rtmp_rb);
        mUrlTypeRg = (RadioGroup) findViewById(R.id.url_type_rg);
        mCancelTv = (TextView) findViewById(R.id.cancel_tv);
        mCancelTv.setOnClickListener(this);
    }


    @Override
    public void initData() {

    }

    @Override
    public void onSuccess(String tag, Object o) {

    }

}