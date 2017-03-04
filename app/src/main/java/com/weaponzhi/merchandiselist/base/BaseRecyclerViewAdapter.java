package com.weaponzhi.merchandiselist.base;

import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * BaseRecyclerViewAdapter
 * <p>
 * author: 顾博君 <br>
 * time:   2016/10/24 21:25 <br>
 * e-mail: gubojun@csii.com.cn <br>
 * </p>
 */

public abstract class BaseRecyclerViewAdapter<DA extends Object, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {
    private List<DA> mList;

    public BaseRecyclerViewAdapter() {
        mList = new ArrayList<>();
    }

    public List<DA> getDataList() {
        return mList;
    }

    /**
     * 刷新Apapter
     * @param list
     */
    public void flush(List<DA> list) {
        if (list != null && list.size() > 0) {
            mList.clear();
            mList.addAll(list);
        } else {
            mList.clear();
        }
        notifyDataSetChanged();
    }
}
