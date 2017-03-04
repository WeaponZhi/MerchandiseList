package com.weaponzhi.merchandiselist.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.compat.BuildConfig;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import java.io.IOException;


/**
 * @brief 提供一些方法 用于获取移动设备信息
 */
public class DeviceUtil {

    private static final String LOG_TAG = "DeviceUtil";

    /**
     * @return String
     */
    public static String getModel() {
        return android.os.Build.MODEL;
    }


    /**
     * @return int
     */
    public static int getSDKVersion() {
        return android.os.Build.VERSION.SDK_INT;
    }

    /**
     * @return String
     */
    public static String getDeviceType() {
        return android.os.Build.MODEL;
    }

    /**
     * @param context 上下文
     * @return String
     */
    public static String getIMEI(Context context) {
        TelephonyManager tm = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getDeviceId();
    }

    /**
     * @param context 上下文
     * @return String(480*800)
     */
    public static String getDisplayMetrics(Context context) {
        // String str = "";
        DisplayMetrics dm = new DisplayMetrics();
        dm = context.getResources().getDisplayMetrics();
        int screenWidth = dm.widthPixels;// 屏幕宽（像素，如：480px）
        int screenHeight = dm.heightPixels;// 屏幕高（像素，如：800px）
        return String.valueOf(screenWidth) + "*" + String.valueOf(screenHeight);
    }

    /**
     * @param context 上下文
     * @return int
     */
    public static int getMetricsHeight(Context context) {
        // String str = "";
        DisplayMetrics dm = new DisplayMetrics();
        dm = context.getResources().getDisplayMetrics();
        return dm.heightPixels;// 屏幕高（像素，如：800px)
    }

    /**
     * @param context 上下文
     * @return int
     */
    public static int getMetricsWidth(Context context) {
        // String str = "";
        DisplayMetrics dm = new DisplayMetrics();
        dm = context.getResources().getDisplayMetrics();
        return dm.widthPixels;// 屏幕高（像素，如：800px）
    }

    /**
     * @param context 上下文
     * @return "" 无网络 wifi 2G/3G/4G
     */
    public static String getNetWork(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (null != networkInfo && networkInfo.isAvailable()) {
            if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                return "wifi";
            }
            if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                return "2G/3G/4G";
            }
        }
        return "";

    }

    /**
     * @param activity activity对象
     * @return Bitmap
     */
    public static Bitmap getScreenShot(Activity activity) {

        // View是你需要截图的View
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap b1 = view.getDrawingCache();
        // 获取状态栏高度
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        // 获取屏幕长和高

        DisplayMetrics dm = new DisplayMetrics();

        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        // 去掉标题栏
        // Bitmap b = Bitmap.createBitmap(b1, 0, 25, 320, 455);
        Bitmap b = Bitmap.createBitmap(b1, 0, 0, width, height);
        view.destroyDrawingCache();
        return b;
    }

    /**
     * @return boolean
     */
    public static boolean isRoot() {
        try {
            if (null != Runtime.getRuntime().exec("su").getOutputStream()) {
                return true;
            }
        } catch (IOException e) {
            if(BuildConfig.DEBUG)
            Log.e("", "获取root信息失败", e);
        }
        return false;
    }

    /**
     * @return boolean
     */
    public static boolean hasSDCard() {
        String SDState = android.os.Environment.getExternalStorageState();
        return SDState.equals(android.os.Environment.MEDIA_MOUNTED);

    }

    /**
     * @param context 上下文
     * @return boolean
     */
    public static boolean isOpenGPS(Context context) {
        LocationManager alm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        return alm.isProviderEnabled(LocationManager.GPS_PROVIDER);

    }

    /**
     * @param context 上下文
     * @return String
     */
    public static int getVersionCode(Context context) {
        int versionCode = 0;
        // 获取软件版本号，对应AndroidManifest.xml下android:versionCode
        try {
            versionCode = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (NameNotFoundException e) {
            if(BuildConfig.DEBUG)
            Log.e(LOG_TAG, "获取版本号失败！", e);
        }
        return versionCode;
    }

    /**
     * @param context 上下文
     * @return String
     */
    public static String getVersionName(Context context) {
        String versionName = "";
        // 获取软件版本号，对应AndroidManifest.xml下android:versionCode
        try {
            versionName = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (NameNotFoundException e) {
            if(BuildConfig.DEBUG)
            Log.e(LOG_TAG, "获取版本名称失败", e);
        }
        return versionName;
    }



    /**
     * @param activity activity对象
     * @return int
     */
    public static int getMetricDpi(Activity activity) {
        DisplayMetrics metric = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
        return metric.densityDpi; // 屏幕密度DPI（120 / 160 / 240）
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 获取系统版本
     */
    public static String getBuildVersion() {
        int vc = android.os.Build.VERSION.SDK_INT;
        if (vc < 11) {
            return "<Android 3.0";
        } else if (vc == 11) {
            return "Android 3.0";
        } else if (vc == 12) {
            return "Android 3.1";
        } else if (vc == 13) {
            return "Android 3.2";
        } else if (vc == 14) {
            return "Android 4.0";
        } else if (vc == 15) {
            return "Android 4.0.3";
        } else if (vc == 16) {
            return "Android 4.1";
        } else if (vc == 17) {
            return "Android 4.2";
        } else if (vc == 18) {
            return "Android 4.3";
        } else if (vc == 19) {
            return "Android 4.4";
        } else if (vc == 20) {
            return "Android 4.4W.2";
        } else if (vc == 21) {
            return "Android 5.0";
        } else if (vc == 22) {
            return "Android 5.1";
        } else if (vc == 23) {
            return "Android 6.0";
        } else {
            return ">Android 6.0";
        }
    }

    /**
     * 获取屏幕分辨率
     *
     * @param context
     * @return
     */
    public static int[] getScreenDisplay(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int width = windowManager.getDefaultDisplay().getWidth();// 手机屏幕的宽度
        int height = windowManager.getDefaultDisplay().getHeight();// 手机屏幕的高度
        int result[] = {width, height};
        return result;
    }

    public static int getScreenWidth(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param pxValue
     * @param context （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue
     * @param context （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    public static int dp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

}
