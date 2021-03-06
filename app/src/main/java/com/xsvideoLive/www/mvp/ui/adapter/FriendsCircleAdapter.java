package com.xsvideoLive.www.mvp.ui.adapter;


import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.xsvideoLive.www.R;
import com.xsvideoLive.www.callback.FriendFabulousCallback;
import com.xsvideoLive.www.net.bean.FriendsCircleResult;
import com.xsvideoLive.www.utils.BirthdayToAgeUtil;
import com.xsvideoLive.www.utils.GlideAppUtil;
import com.xsvideoLive.www.view.CircleImageView;
import com.xsvideoLive.www.view.ExpandableTextView;
import com.lzy.ninegrid.ImageInfo;
import com.lzy.ninegrid.NineGridView;
import com.lzy.ninegrid.PhotoCallback;
import com.lzy.ninegrid.preview.NineGridViewClickAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


public class FriendsCircleAdapter extends BaseQuickAdapter<FriendsCircleResult, FriendsCircleAdapter.FriendHolder> {

    private Context mContext;
    private final SparseBooleanArray mCollapsedStatus;
    private PhotoCallback mCallback;
    private FriendFabulousCallback mFablousCallback;

    public FriendsCircleAdapter(Context context, PhotoCallback callback) {
        super(R.layout.adapter_friend_circle);
        this.mContext = context;
        mCollapsedStatus = new SparseBooleanArray();
        this.mCallback = callback;
    }

    public void setFabulousCallback(FriendFabulousCallback fablousCallback) {
        this.mFablousCallback = fablousCallback;
    }


    @Override
    protected void convert(@NotNull FriendHolder holder, FriendsCircleResult result) {

        GlideAppUtil.loadImage(getContext(), result.getUserPictureUrl(), holder.mCivAvatar);

        holder.mTvNickName.setText(result.getUserName());

        showYear(result.getShowYear(), result.getUserSex(), holder.mIvSex, holder.mTvAge, result.getUserBirthday());

        holder.mTvDate.setText(result.getShowDate());

        int position = getItemPosition(result);
        holder.mExtView.setText(result.getTopicContent(), mCollapsedStatus, position);

        List<String> pictureList = result.getPictureList();
        ArrayList<ImageInfo> images = new ArrayList<>();
        for (String s : pictureList) {
            ImageInfo info = new ImageInfo();
            info.setThumbnailUrl(s);
            info.setBigImageUrl(s);
            images.add(info);
        }

        NineGridViewClickAdapter nineGridViewClickAdapter = new NineGridViewClickAdapter(mContext, images);
        nineGridViewClickAdapter.setPhotoCallback(mCallback);
        holder.mNineGrid.setAdapter(nineGridViewClickAdapter);

        setFabulous(holder.mTvFabulous, result.getHasLike(), result.getLikeNumber());

        holder.mTvComment.setText(String.valueOf(result.getCommentNumber()));

        holder.mRlFabulous.setOnClickListener(view -> {
            if (mFablousCallback != null) {
                mFablousCallback.onClickFabulous(result, position);
            }
        });

        holder.mCivAvatar.setOnClickListener(view -> {
            if (mFablousCallback != null) {
                mFablousCallback.onClickAvater(result, position);
            }
        });


        holder.mTvComment.setOnClickListener(view -> {
            if (mFablousCallback != null) {
                mFablousCallback.onClickComment(result, position);
            }
        });

        holder.mTvShare.setOnClickListener(view -> {
            if (mFablousCallback != null) {
                mFablousCallback.onClickShare(result, position);
            }
        });

        holder.mIvMore.setOnClickListener(view -> {
            if (mFablousCallback != null) {
                mFablousCallback.onClickMore(result, position);
            }
        });


    }

    /**
     * 点赞设置
     *
     * @param fabulous   点赞控件
     * @param hasLike    是否点赞 1 是 0 否
     * @param likeNumber 点赞数
     */
    public void setFabulous(TextView fabulous, int hasLike, int likeNumber) {
        fabulous.setEnabled(hasLike == 0 ? false : true);
        fabulous.setText(String.valueOf(likeNumber));
    }

    /**
     * @param isShowYear   是否显示年龄 1 显示  0 显示
     * @param userSex      性别 "1" 男  "0" 女
     * @param sex          性别图标控件
     * @param age          年龄文字控件
     * @param userBirthday 生日
     */
    private void showYear(int isShowYear, String userSex, ImageView sex, TextView age, String userBirthday) {
        if (1 == isShowYear) {      //显示发布者年龄

            if ("0".equals(userSex)) {
                sex.setImageResource(R.mipmap.icon_sex_female);
            } else if ("1".equals(userSex)) {
                sex.setImageResource(R.mipmap.icon_sex_male);
            }

            age.setText(BirthdayToAgeUtil.BirthdayToAge(userBirthday));


        } else if (0 == isShowYear) {        //不显示发布在年龄
            if ("0".equals(userSex)) {
                sex.setImageResource(R.mipmap.icon_gender_female);
            } else if ("1".equals(userSex)) {
                sex.setImageResource(R.mipmap.icon_gender_male);
            }
            age.setVisibility(View.GONE);
        }
    }


    static class FriendHolder extends BaseViewHolder {

        CircleImageView mCivAvatar;     //头像
        TextView mTvNickName;           //昵称
        ImageView mIvSex;               //性别
        TextView mTvAge;                //年龄
        TextView mTvDate;               //朋友圈发布时间
        ImageView mIvMore;               //更多按钮
        ExpandableTextView mExtView;     //内容
        NineGridView mNineGrid;           //九宫格
        RelativeLayout mRlFabulous;           //点赞数点击控件
        TextView mTvFabulous;           //点赞数
        TextView mTvComment;           //留言数
        TextView mTvShare;           //分享按钮

        public FriendHolder(@NotNull View view) {
            super(view);
            mCivAvatar = view.findViewById(R.id.civ_avatar);
            mTvNickName = view.findViewById(R.id.tv_nick_name);
            mIvSex = view.findViewById(R.id.iv_sex);
            mTvAge = view.findViewById(R.id.tv_age);
            mTvDate = view.findViewById(R.id.tv_date);
            mIvMore = view.findViewById(R.id.iv_more);
            mExtView = view.findViewById(R.id.expand_text_view);
            mNineGrid = view.findViewById(R.id.nineGrid);
            mRlFabulous = view.findViewById(R.id.rl_fabulous);
            mTvFabulous = view.findViewById(R.id.tv_fabulous);
            mTvComment = view.findViewById(R.id.tv_comment);
            mTvShare = view.findViewById(R.id.tv_share);
        }
    }
}
