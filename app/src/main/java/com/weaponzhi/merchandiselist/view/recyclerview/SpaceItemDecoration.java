package com.weaponzhi.merchandiselist.view.recyclerview;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.weaponzhi.merchandiselist.utils.DeviceUtil;

/**
 * SpaceItemDecoration
 * RecyclerView设置两个itemView之间距离的类
 * <p>
 * author: 张冠之 <br>
 * time:   2017/03/02 18:17 <br>
 * GitHub: https://github.com/WeaponZhi
 * blog:   http://weaponzhi.online
 * CSDN:   http://blog.csdn.net/qq_34795285
 * </p>
 */

public class SpaceItemDecoration extends RecyclerView.ItemDecoration {
    int mSpace;

    /**
     * @param space 传入的值，其单位视为dp
     */
    public SpaceItemDecoration(Context context, int space) {
        this.mSpace = DeviceUtil.dp2px(context, space);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int itemCount = parent.getAdapter().getItemCount();
        int pos = parent.getChildAdapterPosition(view);
//        LogUtils.logd("itemCount>>" + itemCount + ";Position>>" + pos);

        outRect.left = 0;
        outRect.top = mSpace;
        outRect.bottom = 0;

        if (pos == (itemCount - 1)) {
            outRect.bottom = mSpace;
        }
//            outRect.right = mSpace;
//        } else {
//            outRect.right = 0;
//        }
    }
}