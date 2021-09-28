package org.easydarwin.easyplayer.home.fragments.filerecord;


import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.SparseArray;
import android.view.View;

import com.juntai.wisdom.basecomponent.base.BaseMvpFragment;
import com.juntai.wisdom.basecomponent.utils.ToastUtils;

import org.easydarwin.easyplayer.R;
import org.easydarwin.easyplayer.base.customview.CustomViewPager;
import org.easydarwin.easyplayer.base.customview.MainPagerAdapter;
import org.easydarwin.easyplayer.home.MainPageContract;
import org.easydarwin.easyplayer.home.MainPagePresent;


/**
 * @aouther tobato
 * @description 描述  homepage
 * @date 2021/4/18 14:59
 */
public class FileRecordFragment extends BaseMvpFragment<MainPagePresent> implements MainPageContract.IMainPageView,
        View.OnClickListener, ViewPager.OnPageChangeListener {

    private String[] title = new String[]{"抓拍", "录像"};
    private int[] tabDrawables = new int[]{R.drawable.home_dev_list_index, R.drawable.home_dev_list_index};
    private SparseArray<Fragment> mFragments = new SparseArray<>();
    private TabLayout mFileTablayout;
    private CustomViewPager mFileViewpager;
    private MainPagerAdapter adapter;
    private LocalFileFragment picFragment;

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
        picFragment.lazyLoad();
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
    public void onClick(View v) {
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.file_record_fg;
    }

    @Override
    protected void initView() {

        mFileTablayout = (TabLayout) getView(R.id.file_tablayout);
        mFileViewpager = (CustomViewPager) getView(R.id.file_viewpager);
        initTab();
    }
    public void initTab() {
        picFragment = LocalFileFragment.newInstance(false);
        mFragments.append(0, picFragment);//
        mFragments.append(1, LocalFileFragment.newInstance(true));//
        //
        adapter = new MainPagerAdapter(getChildFragmentManager(),mContext, title, tabDrawables,
                mFragments);
        mFileViewpager.setAdapter(adapter);
        mFileViewpager.setOffscreenPageLimit(title.length);
        /*viewpager切换监听，包含滑动点击两种*/
        mFileViewpager.addOnPageChangeListener(this);
        for (int i = 0; i < title.length; i++) {
            TabLayout.Tab tab = mFileTablayout.newTab();
            if (tab != null) {
                tab.setCustomView(adapter.getTabView(i));
                mFileTablayout.addTab(tab);
            }
        }

        mFileTablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mFileViewpager.setCurrentItem(tab.getPosition(), false);

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        //        mainTablayout.newTab();
        /*viewpager切换默认第一个*/
        mFileViewpager.setCurrentItem(0);
    }
    @Override
    protected void initData() {

    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {

    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }
}
