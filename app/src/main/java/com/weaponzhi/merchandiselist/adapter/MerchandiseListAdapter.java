package com.weaponzhi.merchandiselist.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.weaponzhi.merchandiselist.R;
import com.weaponzhi.merchandiselist.base.BaseRecyclerViewAdapter;
import com.weaponzhi.merchandiselist.bean.MerchandiseBean;
import com.weaponzhi.merchandiselist.utils.LogUtils;
import com.weaponzhi.merchandiselist.utils.Util;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * MerchandiseListAdapter
 * <p>
 * author: 张冠之 <br>
 * time:   2017/03/02 18:19 <br>
 * GitHub: https://github.com/WeaponZhi
 * blog:   http://weaponzhi.online
 * CSDN:   http://blog.csdn.net/qq_34795285
 * </p>
 */

public class MerchandiseListAdapter extends BaseRecyclerViewAdapter<MerchandiseBean, MerchandiseListAdapter.NewsListHolder> {
    public Context mContext;

    private onItemClickListener mOnItemClickListener;
    private ButtonClickListener mButtonClickListener;

    public MerchandiseListAdapter(Activity baseActivity) {
        super();
        mContext = baseActivity;
    }


    public List<MerchandiseBean> getData() {
        return getDataList();
    }

    public void setOnItemClickListener(onItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    public void setButtonClickListener(ButtonClickListener listener) {
        mButtonClickListener = listener;
    }

    @Override
    public NewsListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NewsListHolder(LayoutInflater.from(mContext).inflate(R.layout.item_merchandise, parent, false));
    }

    @Override
    public void onBindViewHolder(final NewsListHolder holder, int position) {
        final MerchandiseBean bean = getDataList().get(position);
        holder.tvMerchandiseName.setText(bean.getGoodsName());
        holder.tvTime.setText(String.format(mContext.getResources().getString(R.string.str_merchandise_time), bean.getPayTime()));
        showState(bean.getOrderState(), holder.tvState);
        holder.tvMerchandisePrice.setText(String.format(
                mContext.getResources().getString(R.string.str_merchandise_price),
                bean.getBuyCount(), Util.fmtMoney(bean.getTruePayAmt())));
        showButton(bean.getOrderState(), holder.tvMerchandiseButton);
        //Picasso图片加载
        Picasso.with(mContext)
                .load(R.mipmap.ic_launcher)
                .placeholder(R.mipmap.ic_launcher)
                .resize(70, 50)
                .error(R.mipmap.ic_launcher)
                .into(holder.imgMerchandise);
    }

    @Override
    public int getItemCount() {
        return getDataList().size();
    }

    class NewsListHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        @Bind(R.id.tv_time)
        TextView tvTime;
        @Bind(R.id.tv_state)
        TextView tvState;
        @Bind(R.id.img_merchandise)
        ImageView imgMerchandise;
        @Bind(R.id.tv_merchandise_name)
        TextView tvMerchandiseName;
        @Bind(R.id.tv_merchandise_price)
        TextView tvMerchandisePrice;
        @Bind(R.id.tv_merchandise_button)
        TextView tvMerchandiseButton;


        public NewsListHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            tvMerchandiseButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mButtonClickListener != null)
                        mButtonClickListener.onClick(getAdapterPosition(), getDataList().get(getAdapterPosition()));
                }
            });
        }

        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(v, getAdapterPosition(), getDataList().get(getAdapterPosition()));
            } else
                Toast.makeText(mContext, "item click pos:" + getAdapterPosition(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public boolean onLongClick(View v) {
            LogUtils.logv("onLongClick");
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemLongClick(NewsListHolder.this, v, getAdapterPosition());
            } else
                Toast.makeText(mContext, "item long click pos:" + getAdapterPosition(), Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    /**
     * 自定义的recyclerView点击监听
     */
    public interface onItemClickListener {
        void onItemClick(View v, int position, MerchandiseBean data);

        void onItemLongClick(RecyclerView.ViewHolder viewHolder, View v, int position);
    }

    /**
     * 每个订单按钮的点击监听
     */
    public interface ButtonClickListener {
        void onClick(int position, MerchandiseBean data);
    }

    /**
     * 根据订单状态修改样式
     * @param state
     * @param textView
     */
    private void showState(String state, TextView textView) {
        String text = "";
        int color = Color.BLACK;
       if ("3".equals(state)) {
            text = "已完成";
            color = Color.BLACK;
        } else if ("2".equals(state)) {
            text = "待收货";
            color = ContextCompat.getColor(mContext, R.color.common_orange);
        } else if ("1".equals(state)) {
            text = "待发货";
            color = Color.BLACK;
        }
        textView.setText(text);
        textView.setTextColor(color);
    }

    /**
     * 根据订单状态修改按钮样式
     * @param state
     * @param textView
     */
    private void showButton(String state, TextView textView) {
        String text = "";
        int visible = View.GONE;
        int color = Color.BLACK;
       if ("3".equals(state)) {
            text = "删除订单";
            color = Color.BLACK;
            visible = View.VISIBLE;
        } else if ("2".equals(state)) {
            text = "确认收货";
            color = ContextCompat.getColor(mContext, R.color.common_orange);
            visible = View.VISIBLE;
        } else if ("1".equals(state)) {
            text = "退货";
            color = Color.BLACK;
//            visible = View.VISIBLE;
        }
        textView.setText(text);
        textView.setTextColor(color);
        textView.setVisibility(visible);
    }
}
