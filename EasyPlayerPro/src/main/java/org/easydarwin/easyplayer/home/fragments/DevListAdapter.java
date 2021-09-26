package org.easydarwin.easyplayer.home.fragments;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import org.easydarwin.easyplayer.R;
import org.easydarwin.easyplayer.bean.VideoAddrBean;

/**
 * @Author: tobato
 * @Description: 作用描述
 * @CreateDate: 2021-09-23 15:28
 * @UpdateUser: 更新者
 * @UpdateDate: 2021-09-23 15:28
 */
public class DevListAdapter extends BaseQuickAdapter<VideoAddrBean, BaseViewHolder> {
    public DevListAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, VideoAddrBean item) {
        helper.setText(R.id.dev_name_tv, item.getName());
        helper.setText(R.id.dev_reg_tv, 0==item.getUrlType()?"智能取证M":"智能取证S");
        helper.setText(R.id.add_time_tv, item.getAddTime());
    }
}
