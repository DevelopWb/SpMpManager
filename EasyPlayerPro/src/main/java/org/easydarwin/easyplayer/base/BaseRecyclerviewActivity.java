package org.easydarwin.easyplayer.base;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.juntai.wisdom.basecomponent.mvp.BasePresenter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.easydarwin.easyplayer.R;

/**
 * @Author: tobato
 * @Description: 作用描述  只有一个recyclerview的fragment
 * @CreateDate: 2021/4/29 16:16
 * @UpdateUser: 更新者
 * @UpdateDate: 2021/4/29 16:16
 */
public abstract class BaseRecyclerviewActivity<P extends BasePresenter> extends BaseAppActivity<P> {
    protected RecyclerView mRecyclerview;
    protected SmartRefreshLayout mSmartrefreshlayout;
    protected BaseQuickAdapter adapter;

    @Override
    public int getLayoutView() {
        return R.layout.recycleview_layout;
    }

    @Override
    public void initView() {
        mRecyclerview = (RecyclerView) findViewById(R.id.recyclerview);
        mSmartrefreshlayout = (SmartRefreshLayout) findViewById(R.id.smartrefreshlayout);
        adapter = getAdapter();
        adapter.setEmptyView(getAdapterEmptyView("一条数据也没有...",-1));
        initRecyclerview(mRecyclerview, adapter, LinearLayoutManager.VERTICAL);
        mSmartrefreshlayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                freshlayoutOnRefresh();
            }
        });
        mSmartrefreshlayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                freshlayoutOnLoadMore();
            }
        });
    }

    protected abstract void freshlayoutOnLoadMore();

    protected abstract void freshlayoutOnRefresh();

    protected abstract BaseQuickAdapter getAdapter();

    @Override
    public void onSuccess(String tag, Object o) {
        mSmartrefreshlayout.finishLoadMore();
        mSmartrefreshlayout.finishRefresh();
        getViewFocus(mRecyclerview);
    }
}
