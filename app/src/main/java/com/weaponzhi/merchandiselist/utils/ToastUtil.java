package com.weaponzhi.merchandiselist.utils;

/**
 * @description 描述:
 * @auther wangsheng on 16/4/5 21:45.
 * @email 公司邮箱:wangsheng@csii.com.cn  个人邮箱:jingbei110@163.com
 */

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.weaponzhi.merchandiselist.R;


/**
 * Toast－工具类 保持全局的唯一性
 */
public class ToastUtil {

    private static String oldMsg;
    protected static Toast toast   = null;
    static TextView tv_msg_text;
    private static long oneTime=0;
    private static long twoTime=0;

    public static void showToast(Context context, String s){
        if(toast==null){
            toast = new Toast(context);
            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.layout_top_toast, null);
            tv_msg_text = (TextView) view.findViewById(R.id.tv_msg_text);
            tv_msg_text.setText(s);
            toast.setView(view);
            toast.setDuration(Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER,0,-100);
            toast.show();
            oneTime= System.currentTimeMillis();
        }else{
            twoTime= System.currentTimeMillis();
            if(s.equals(oldMsg)){
                if(twoTime-oneTime> Toast.LENGTH_SHORT){
                    toast.show();
                }
            }else{
                oldMsg = s;
                tv_msg_text.setText(s);
                toast.show();
            }
        }
        oneTime=twoTime;
    }


    public static void showToast(Context context, int resId){
        showToast(context, context.getString(resId));
    }

}
