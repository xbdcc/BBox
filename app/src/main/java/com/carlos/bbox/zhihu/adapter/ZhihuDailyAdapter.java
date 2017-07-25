package com.carlos.bbox.zhihu.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.carlos.bbox.R;
import com.carlos.bbox.util.ImageUtil;
import com.carlos.bbox.zhihu.bean.ZhihuDailyItemVO;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by caochang on 2017/7/11.
 */

public class ZhihuDailyAdapter extends RecyclerView.Adapter {

    private static final int TYPE_NORMAL = 1;
    private static final int TYPE_LOADING_MORE = 2;
    private Context mContext;
    private List<ZhihuDailyItemVO> mZhihuDailyItems;
    private OnItemClickListener mOnItemClickListener;

    public ZhihuDailyAdapter(Context context, List<ZhihuDailyItemVO> zhihuDailyItemVOs) {
        mContext = context;
        mZhihuDailyItems = zhihuDailyItemVOs;
    }

    public interface OnItemClickListener {
        void onItemClick(int position, View view);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    @Override
    public int getItemViewType(int position) {
        if (position < getItemCount() && getItemCount() > 0)
            return TYPE_NORMAL;
        else
            return TYPE_LOADING_MORE;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_NORMAL:
                return new DailyHolder(LayoutInflater.from(mContext).inflate(R.layout.item_zhihudaily, parent, false));
//                new DailyHolder(LayoutInflater.from(mContext).inflate(R.layout.item_zhihudaily, parent, false));
//            case TYPE_LOADING_MORE:

        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof DailyHolder) {
            ((DailyHolder) holder).bindTo(mZhihuDailyItems.get(position), position);
        }
//        int type=getItemViewType(position);
//        switch (type){
//            case TYPE_NORMAL:
//                ((DailyHolder)holder).bindTo(mZhihuDailyItems.get(position));
//                break;
//        }
    }

    @Override
    public int getItemCount() {
        return mZhihuDailyItems.size();
    }

    public void addItems(List<ZhihuDailyItemVO> zhihuDailyItemVOs) {
        mZhihuDailyItems.addAll(zhihuDailyItemVOs);
        notifyDataSetChanged();
    }

    public void clearData() {
        mZhihuDailyItems.clear();
//        notifyDataSetChanged();
    }

    class DailyHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_daily_item_image)
        ImageView mIvDailyItemImage;
        @BindView(R.id.tv_daily_item_title)
        TextView mTvDailyItemTitle;

        public DailyHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }

        public void bindTo(ZhihuDailyItemVO zhihuDailyItemVO, int position) {
            mTvDailyItemTitle.setText(zhihuDailyItemVO.getTitle());
            ImageUtil.load(mContext, zhihuDailyItemVO.getImages()[0], mIvDailyItemImage);
            itemView.setOnClickListener(view -> mOnItemClickListener.onItemClick(position, itemView));
        }
    }

}
