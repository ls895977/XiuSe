package com.xsvideoLive.www.mvp.ui.fragment.square;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.viewpager.widget.ViewPager;

import com.xsvideoLive.www.R;
import com.xsvideoLive.www.base.BaseMvpFragment;
import com.xsvideoLive.www.mvp.contract.Squarev1Constract;
import com.xsvideoLive.www.mvp.presenter.Squarev1Presenter;
import com.xsvideoLive.www.mvp.ui.activity.square.GraphicReleaseAct;
import com.xsvideoLive.www.utils.ActStartUtils;

import net.lucode.hackware.magicindicator.MagicIndicator;

import butterknife.BindView;
import butterknife.OnClick;

public class SquareV1Fragment extends BaseMvpFragment<Squarev1Presenter> implements Squarev1Constract.View {
    @BindView(R.id.index)
    MagicIndicator index;
    @BindView(R.id.ns_view_pager)
    ViewPager nsViewPager;
    @BindView(R.id.iv_release)
    ImageView ivRelease;

    @Override
    public int setLayoutId() {
        return R.layout.fragment_squarev1;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

        mPresenter.attachView(this);

        mPresenter.initFragment(index, nsViewPager);


    }

    @Override
    protected Squarev1Presenter createPresenter() {
        return new Squarev1Presenter();
    }

    @OnClick(R.id.iv_release)
    public void onViewClicked() {
        ActStartUtils.startAct(getActivity(), GraphicReleaseAct.class);
    }
}
