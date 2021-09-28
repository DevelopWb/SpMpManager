package org.easydarwin.easyplayer.home.fragments.filerecord;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.juntai.wisdom.basecomponent.mvp.IPresenter;

import org.easydarwin.easyplayer.ProVideoActivity;
import org.easydarwin.easyplayer.R;
import org.easydarwin.easyplayer.base.BaseAppFragment;
import org.easydarwin.easyplayer.databinding.ImagePickerItemBinding;
import org.easydarwin.easyplayer.util.FileUtil;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.Collections;

public class LocalFileFragment extends BaseAppFragment implements CompoundButton.OnCheckedChangeListener,
        View.OnClickListener {
    public static final String KEY_IS_RECORD = "key_last_selection";

    private boolean mShowMp4File;

    SparseArray<Boolean> mImageChecked;

    private String mSuffix;
    File mRoot = null;
    File[] mSubFiles;
    int mImgHeight;
    private RecyclerView mRecycler;
    private RecyclerView.Adapter adapter;
    //    private FileAdapter adapter;

    public static LocalFileFragment newInstance(boolean isVideo) {
        Bundle args = new Bundle();
        args.putBoolean(KEY_IS_RECORD, isVideo);
        LocalFileFragment fragment = new LocalFileFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_media_file;
    }

    @Override
    protected void initView() {
        setHasOptionsMenu(false);

        mImageChecked = new SparseArray<>();

        mShowMp4File = getArguments().getBoolean(KEY_IS_RECORD);
        mSuffix = mShowMp4File ? ".mp4" : ".jpg";

        if (mShowMp4File) {
            mRoot = new File(FileUtil.getMoviePath());
        } else {
            mRoot = new File(FileUtil.getPicturePath());
        }

        getData();
        mImgHeight = (int) (getResources().getDisplayMetrics().density * 100 + 0.5f);

        mRecycler = (RecyclerView) getView(R.id.recycler);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 3);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        mRecycler.setLayoutManager(layoutManager);
        //        adapter = new FileAdapter(R.layout.image_picker_item);
        //        mRecycler.setAdapter(adapter);
        //
        //        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
        //            @Override
        //            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        //
        //            }
        //        });
        adapter = new RecyclerView.Adapter() {

            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                ImagePickerItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()),
                        R.layout.image_picker_item, parent, false);
                return new ImageItemHolder(binding);
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
                ImageItemHolder holder = (ImageItemHolder) viewHolder;
                holder.mCheckBox.setOnCheckedChangeListener(null);
                holder.mCheckBox.setChecked(mImageChecked.get(position, false));
                holder.mCheckBox.setOnCheckedChangeListener(LocalFileFragment.this);
                holder.mCheckBox.setTag(R.id.click_tag, holder);
                holder.mImage.setTag(R.id.click_tag, holder);

                if (mShowMp4File) {
                    holder.mPlayImage.setVisibility(View.VISIBLE);
                } else {
                    holder.mPlayImage.setVisibility(View.GONE);
                }

                Glide.with(getContext()).load(mSubFiles[position]).into(holder.mImage);
            }

            @Override
            public int getItemCount() {
                return mSubFiles.length;
            }
        };
        mRecycler.setAdapter(adapter);
    }

    @Override
    protected void initData() {

    }


    private void getData() {
        File[] subFiles = mRoot.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String filename) {
                return filename.endsWith(mSuffix);
            }
        });

        if (subFiles == null) {
            subFiles = new File[0];
        }
        Collections.reverse(Arrays.asList(subFiles));
        mSubFiles = subFiles;
    }

    @Override
    protected IPresenter createPresenter() {
        return null;
    }


    @Override
    protected void lazyLoad() {
        getData();
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }


    @Override
    public void onClick(View v) {
        ImageItemHolder holder = (ImageItemHolder) v.getTag(R.id.click_tag);
        if (holder.getAdapterPosition() == RecyclerView.NO_POSITION) {
            return;
        }

        final String path = mSubFiles[holder.getAdapterPosition()].getPath();
        if (TextUtils.isEmpty(path)) {
            Toast.makeText(getContext(), "文件不存在", Toast.LENGTH_SHORT).show();
            return;
        }

        File f = new File(path);
        Uri uri = null;
        if (Build.VERSION.SDK_INT >= 24) {//7.0 Android N
            //com.xxx.xxx.fileprovider为上述manifest中provider所配置相同
            uri = FileProvider.getUriForFile(getContext(), "org.chuangchi.player.fileProvider", f);
            // 读取权限，安装完毕以后，系统会自动收回权限，该过程没有用户交互
        } else {//7.0以下
            uri = Uri.fromFile(f);
        }
        if (path.endsWith(".jpg")) {
            try {
                Intent intent = new Intent();
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.setAction(Intent.ACTION_VIEW);
                intent.setDataAndType(uri, "image/*");
                startActivity(intent);
            } catch (ActivityNotFoundException e) {
                e.printStackTrace();
            }
        } else if (path.endsWith(".mp4")) {
            try {
                Intent i = new Intent(getContext(), ProVideoActivity.class);
                i.putExtra("videoPath", path);
                startActivity(i);

                //                Intent intent = new Intent();
                //                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                //                intent.setAction(Intent.ACTION_VIEW);
                //                intent.setDataAndType(uri, "video/*");
                //                startActivity(intent);
            } catch (ActivityNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onSuccess(String tag, Object o) {

    }

    @Override
    public void onError(String tag, Object o) {

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

    }


    class ImageItemHolder extends RecyclerView.ViewHolder {
        public final CheckBox mCheckBox;
        public final ImageView mImage;
        public final ImageView mPlayImage;

        public ImageItemHolder(ImagePickerItemBinding binding) {
            super(binding.getRoot());

            mCheckBox = binding.imageCheckbox;
            mImage = binding.imageIcon;
            mPlayImage = binding.imagePlay;
            mImage.setOnClickListener(LocalFileFragment.this);
        }
    }
}