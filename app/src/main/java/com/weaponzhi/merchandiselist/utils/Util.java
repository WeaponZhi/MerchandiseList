/**
 *
 */
package com.weaponzhi.merchandiselist.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.os.Build;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.support.compat.BuildConfig;
import android.text.Editable;
import android.text.Selection;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.ReplacementTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Description: 工具类 Copyright (c) CSII. All Rights Reserved.
 */
public class Util {

    public static double lat = 0.0;// 当前定位经度
    public static double lon = 0.0;// 当前定位纬度
    public static String district = "";
    public static String city = "";

    private Context context;
    protected SharedPreferences preferences;// 保存开关

    public Util(Context context) {
        this.context = context;
    }

    /**
     * 获取布局文件在工程中的id
     */
    public int getLayoutId(String xmlName) {
        return getResourceId("layout", xmlName);
    }

    /**
     * 获取图片资源在工程中的id
     */
    public int getDrawableId(String xmlName) {
        return getResourceId("drawable", xmlName);
    }

    /**
     * 获取控件在工程中的id
     */
    public int getViewId(String xmlName) {
        return getResourceId("id", xmlName);
    }

    // 利用正则表达式判断用户输入的格式
    public static boolean checkString(String str, String strMatches) {
        return str.matches(strMatches);
    }

    /**
     * 根据资源文件名获取相应实例
     */
    private int getResourceId(String xmlFile, String xmlName) {
        Class localClass = null;
        try {
            localClass = Class.forName(context.getPackageName() + ".R$" + xmlFile);
        } catch (ClassNotFoundException exception) {
            exception.printStackTrace();
            return 0;
        }
        Field localField = null;
        try {
            localField = localClass.getField(xmlName);
        } catch (SecurityException exception) {
            exception.printStackTrace();
            return 0;
        } catch (NoSuchFieldException exception) {
            exception.printStackTrace();
            return 0;
        }
        try {
            return Integer.parseInt(localField.get(localField.getName()).toString());
        } catch (NumberFormatException exception) {
            exception.printStackTrace();
            return 0;
        } catch (IllegalArgumentException exception) {
            exception.printStackTrace();
            return 0;
        } catch (IllegalAccessException exception) {
            exception.printStackTrace();
            return 0;
        }
    }

    /**
     * 把px转化dip
     */
    public static int px2dip(Context context, float px) {
        float density = context.getResources().getDisplayMetrics().density;// 密度
        int dip = (int) (px * (density / 1));
        return dip;
    }

    /**
     * 判断字符串是否为空
     *
     * @param str
     * @return
     */
    public static boolean isStringNull(String str) {
        return 0 == str.length() || null == str || "".equals(str);
    }

    /**
     * 判断APP是否是在后台运行
     *
     * @param ctx
     * @return
     * @auther 刘淏卿
     */
    public static boolean isAppOnForeground(Context ctx) {
        android.app.ActivityManager activityManager = (android.app.ActivityManager) ctx.getApplicationContext().getSystemService(
                Context.ACTIVITY_SERVICE);
        String packageName = ctx.getApplicationContext().getPackageName();

        List<RunningAppProcessInfo> appProcesses = ((android.app.ActivityManager) activityManager).getRunningAppProcesses();
        if (appProcesses == null) {
            return false;
        }
        for (RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(packageName) && appProcess.importance == RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断activity是否运行栈顶
     */
    public static boolean isTopActivity(Context context, Class<? extends Activity> activity) {
        android.app.ActivityManager am = (android.app.ActivityManager) context.getApplicationContext().getSystemService(
                Context.ACTIVITY_SERVICE);
        List<RunningTaskInfo> list = am.getRunningTasks(1);// 获取的是APP
        if (null != list) {
            if (list.size() > 0 && list.get(0).topActivity.getShortClassName().equals(activity.getName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 替换字符串 \n
     *
     * @return String
     */
    public static String replaceBlank(String str) {
        String dest = "";
        if (str != null) {
            Pattern p = Pattern.compile("\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");

        }
        return dest;
    }

    /**
     * 替换字符串 \r
     *
     * @return String
     */
    public static String replaceBlank1(String str) {
        String dest = "";
        if (str != null) {
            Pattern p = Pattern.compile("\r");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");

        }
        return dest;
    }

    /**
     * 内容替换
     *
     * @param str            原文，需要被操作的内容
     * @param replaceContent 需要被替换的内容
     * @param targetContent  替换内容
     * @return
     */
    public static String mReplaceBlank(String str, String replaceContent, String targetContent) {
        String dest = "";
        if (str != null) {
            Pattern p = Pattern.compile(replaceContent);
            Matcher m = p.matcher(str);
            dest = m.replaceAll(targetContent);

        }
        return dest;
    }

    /*
     * 字符串半角转为全角
     */
    public static String trimStringTDC(String input) {
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 12288) {
                c[i] = (char) 32;
                continue;
            }
            if (c[i] > 65280 && c[i] < 65375)
                c[i] = (char) (c[i] - 65248);
        }
        return new String(c);
    }

    // 替换、过滤特殊字符
    public static String StringFilter(String str) {
        str = str.replaceAll("【", "[").replaceAll("】", "]").replaceAll("！", "!");// 替换中文标号
        String regEx = "[『』]"; // 清除掉特殊字符
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }

    public static String[] chineseDigits = new String[]{"零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖"};

    public static String amountToChinese(double amount) {
        if (amount > 999999999999999999.99 || amount < -999999999999999999.99)
            throw new IllegalArgumentException("参数值超出允许范围 (-999999999999999999.99 ～ 999999999999999999.99)！");
        boolean negative = false;
        if (amount < 0) {
            negative = true;
            amount = amount * (-1);
        }
        long temp = Math.round(amount * 100);
        int numFen = (int) (temp % 10); // 分
        temp = temp / 10;
        int numJiao = (int) (temp % 10); // 角
        temp = temp / 10; // temp 目前是金额的整数部分
        int[] parts = new int[20]; // 其中的元素是把原来金额整数部分分割为值在 0~9999 之间的数的各个部分
        int numParts = 0; // 记录把原来金额整数部分分割为了几个部分（每部分都在 0~9999 之间）
        for (int i = 0; ; i++) {
            if (temp == 0)
                break;
            int part = (int) (temp % 10000);
            parts[i] = part;
            numParts++;
            temp = temp / 10000;
        }
        boolean beforeWanIsZero = true; // 标志“万”下面一级是不是 0
        boolean beforeYiIsZero = true;//标志“亿”下面一级是不是 0
        String chineseStr = "";
        for (int i = 0; i < numParts; i++) {
            String partChinese = partTranslate(parts[i]);
            if (i % 2 == 0) {
                if ("".equals(partChinese))
                    beforeWanIsZero = true;
                else
                    beforeWanIsZero = false;
            }
            if (i == 1) {
                if ("".equals(partChinese))
                    beforeYiIsZero = true;
                else
                    beforeYiIsZero = false;
            }
            if (i != 0) {
                if (i % 2 == 0) {
                    if ("".equals(partChinese) && !beforeYiIsZero) {//万之前为0，万之后不为0则加零
                        chineseStr = "零" + chineseStr;
                    } else if (!"".equals(partChinese) && parts[i - 1] < 1000 && parts[i - 1] > 0) {//万之前不为0，万之后不到1000，则
                        //加万和零
                        chineseStr = "零" + chineseStr;
                        chineseStr = "亿" + chineseStr;
                    } else if ("".equals(partChinese) && beforeYiIsZero) {//如果都为0,说明是整数亿，则什么都不加
                    } else if (!"".equals(partChinese) && beforeYiIsZero) {
                        chineseStr = "亿" + chineseStr;
                    } else {
                        chineseStr = "亿" + chineseStr;
                    }
                } else {
                    if ("".equals(partChinese) && !beforeWanIsZero) {//万之前为0，万之后不为0则加零
                        chineseStr = "零" + chineseStr;
                    } else if (!"".equals(partChinese) && parts[i - 1] < 1000 && parts[i - 1] > 0) {//万之前不为0，万之后不到1000，则
                        //加万和零
                        chineseStr = "零" + chineseStr;
                        chineseStr = "万" + chineseStr;
                    } else if ("".equals(partChinese) && beforeWanIsZero) {//如果都为0,说明是整数亿，则什么都不加
                    } else if (!"".equals(partChinese) && beforeWanIsZero) {
                        chineseStr = "万" + chineseStr;
                    } else {
                        chineseStr = "万" + chineseStr;
                    }
                }
            }
            chineseStr = partChinese + chineseStr;
        }
        if ("".equals(chineseStr)) // 整数部分为 0, 则表达为"零元"
            chineseStr = chineseDigits[0];
        else if (negative) // 整数部分不为 0, 并且原金额为负数
            chineseStr = "负" + chineseStr;
        chineseStr = chineseStr + "元";
        if (numFen == 0 && numJiao == 0) {
            chineseStr = chineseStr + "整";
        } else if (numFen == 0) { // 0 分，角数不为 0
            chineseStr = chineseStr + chineseDigits[numJiao] + "角";
        } else { // “分”数不为 0
            if (numJiao == 0)
                chineseStr = chineseStr + "零" + chineseDigits[numFen] + "分";
            else
                chineseStr = chineseStr + chineseDigits[numJiao] + "角" + chineseDigits[numFen] + "分";
        }
        return chineseStr;
    }

    /**
     * 把一个 0~9999 之间的整数转换为汉字的字符串，如果是 0 则返回 ""
     *
     * @param amountPart
     * @return
     */
    private static String partTranslate(int amountPart) {
        if (amountPart < 0 || amountPart > 10000) {
            throw new IllegalArgumentException("参数必须是大于等于 0，小于 10000 的整数！");
        }
        String[] units = new String[]{"", "拾", "佰", "仟"};
        int temp = amountPart;
        String amountStr = new Integer(amountPart).toString();
        int amountStrLength = amountStr.length();
        boolean lastIsZero = true; // 在从低位往高位循环时，记录上一位数字是不是 0
        String chineseStr = "";
        for (int i = 0; i < amountStrLength; i++) {
            if (temp == 0) // 高位已无数据
                break;
            int digit = temp % 10;
            if (digit == 0) { // 取到的数字为 0
                if (!lastIsZero) // 前一个数字不是 0，则在当前汉字串前加“零”字;
                    chineseStr = "零" + chineseStr;
                lastIsZero = true;
            } else { // 取到的数字不是 0
                chineseStr = chineseDigits[digit] + units[i] + chineseStr;
                lastIsZero = false;
            }
            temp = temp / 10;
        }
        return chineseStr;
    }

    public static String getTo4Division(String str) {
        StringBuilder sb = new StringBuilder(str);
        int i = str.length() / 4;
        if (str.length() % 4 == 0) {
            i--;
        }
        for (int j = 1; j <= i; j++) {
            sb.insert(j * 4 + j - 1, " ");
        }
        System.out.println(sb.toString());
        return sb.toString();
    }

    /**
     * 格式化金额
     *
     * @param money
     * @return
     */
    @SuppressLint("NewApi")
    public static String fmtMoney(String money) {
        if (money == null || "".equals(money)) {
            return "";
        }

        double dobleValue = Double.parseDouble(money);
        DecimalFormat fmt = new DecimalFormat("#,##0.00");
        fmt.setRoundingMode(RoundingMode.DOWN);
        String fmtMoney = fmt.format(dobleValue);
        return fmtMoney;
    }


    /**
     * Description: 格式化银行卡号
     *
     * @param mEditText
     * @param carSub_tv
     * @Version1.0 2015-6-6 下午3:56:53 by xupeng (xupeng@csii.com.cn)
     */
    public static void bankCardNumAddSpace(final EditText mEditText, final TextView carSub_tv) {
        mEditText.addTextChangedListener(new TextWatcher() {

            int beforeTextLength = 0;
            int onTextLength = 0;
            boolean isChanged = false;

            int location = 0;// 记录光标的位置
            private char[] tempChar;
            private StringBuffer buffer = new StringBuffer();
            int konggeNumberB = 0;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                beforeTextLength = s.length();
                if (buffer.length() > 0) {
                    buffer.delete(0, buffer.length());
                }
                konggeNumberB = 0;
                for (int i = 0; i < s.length(); i++) {
                    if (s.charAt(i) == ' ') {
                        konggeNumberB++;
                    }
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                onTextLength = s.length();
                buffer.append(s.toString());
                if (onTextLength == beforeTextLength || onTextLength <= 3 || isChanged) {
                    isChanged = false;
                    return;
                }
                isChanged = true;
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (carSub_tv != null) {
                    carSub_tv.setText(mEditText.getText());
                }
                if (isChanged) {
                    location = mEditText.getSelectionEnd();
                    int index = 0;
                    while (index < buffer.length()) {
                        if (buffer.charAt(index) == ' ') {
                            buffer.deleteCharAt(index);
                        } else {
                            index++;
                        }
                    }

                    index = 0;
                    int konggeNumberC = 0;
                    while (index < buffer.length()) {
                        if ((index == 4 || index == 9 || index == 14 || index == 19 || index == 24 || index == 29)) {
                            buffer.insert(index, ' ');
                            konggeNumberC++;
                        }
                        index++;
                    }

                    if (konggeNumberC > konggeNumberB) {
                        location += (konggeNumberC - konggeNumberB);
                    }

                    tempChar = new char[buffer.length()];
                    buffer.getChars(0, buffer.length(), tempChar, 0);
                    String str = buffer.toString();
                    if (location > str.length()) {
                        location = str.length();
                    } else if (location < 0) {
                        location = 0;
                    }

                    if (str.length() >= 23) {
                        str = str.substring(0, 23);
                        location = str.length();
                    }

                    mEditText.setText(str);
                    Editable etable = mEditText.getText();
                    Selection.setSelection(etable, location);
                    isChanged = false;
                }
            }
        });
    }

    /**
     * Description: 解析银行卡
     *
     * @param cardNo
     * @return
     * @Version1.0 2015-6-6 下午4:00:24 by xupeng (xupeng@csii.com.cn)
     */
    public static String parseCardNo(String cardNo) {
        return cardNo.replaceAll(" ", "");
    }

    /**
     * 格式化卡号，每隔4位加空格
     *
     * @param cardNo 银行卡号
     * @return 格式化后的银行卡号
     */
    public static String fmtCardNo(String cardNo) {
        if (TextUtils.isEmpty(cardNo))
            return null;
        StringBuffer fmtCardNo = new StringBuffer();
        char[] charArray = cardNo.toCharArray();
        for (int i = 0; i < cardNo.length(); i++) {
            if (0 != i && (i % 4 == 0)) {
                fmtCardNo.append(" ");
            }
            fmtCardNo.append(charArray[i]);
        }
        return fmtCardNo.toString();
    }

    /**
     * 格式化卡号，只有前四位和最后4位显示数字，其他位显示空格和**
     *
     * @param cardNo 银行卡号
     * @return 格式化后的银行卡号
     */
    public static String fmtCardNoByStar(String cardNo) {
        StringBuffer fmtCardNo = new StringBuffer();
        if (TextUtils.isEmpty(cardNo)) {
            return "";
        } else {
            char[] charArray = cardNo.toCharArray();
            for (int i = 0; i < cardNo.length() - 4; i++) {
                if (0 != i && (i % 4 == 0)) {
                    fmtCardNo.append(" ");
                }
                if (i > 3) {
                    fmtCardNo.append("*");
                } else {
                    fmtCardNo.append(charArray[i]);
                }
            }
            fmtCardNo.append(" ");
            for (int j = cardNo.length() - 4; j < cardNo.length(); j++) {
                fmtCardNo.append(charArray[j]);
            }
            return fmtCardNo.toString();
        }
    }

    /**
     * 获取当前时间戳
     *
     * @return 当前时间戳
     */
    @SuppressLint("SimpleDateFormat")
    public static String getCurtDateStr() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        return sdf.format(new Date());
    }

    /**
     * @param str      需要格式化的值
     * @param decimals 保留到小数点后几位
     * @param is       扩展
     * @return
     */
    @SuppressLint("UseValueOf")
    public static String formatAmount(String str, int decimals, boolean is) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        String patt = "";
        for (int i = 0; i < decimals; i++) {
            patt = patt + "0";
        }
        if (!TextUtils.isEmpty(patt)) {
            patt = "." + patt;
        }
        Double doubl = new Double(str);
        if (doubl == 0) {
            return "0" + patt;
        }
        DecimalFormat myformat = new DecimalFormat();
        // myformat.applyPattern("##,###"+patt);
        myformat.applyPattern("##,##0" + patt);
        return myformat.format(doubl);
    }

    /**
     * 动态格式化金额（录入金额时将金额格式化为#,##0.00格式）
     *
     * @param etMoney 金额输入框对象
     */
    public static void dynamicFmtMoney(final EditText etMoney) {
        etMoney.addTextChangedListener(new TextWatcher() {

            // 是否是后台自动改变值（代码自动格式化后重新设置的值非用户输入，避免死循环）
            boolean isAutoChange = false;
            // 后台自动改变值之前的值
            String beforeAutoChangeValue;

            // 用户光标位置
            int userCursorIndex;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 若是后台自动改变值则重置是否后台自动改变值标识为false、设置最新光标位置
                if (isAutoChange) {
                    isAutoChange = false;
                    // 设置改变值后的光标位置
                    Editable etable = etMoney.getText();
                    // 设置值改变后对应用户光标位置，改变之前值长度为1，格式后只会往后面加”.00“无需改变用户光标位置，否则设置最新光标位置为【用户原始光标位置+(改变值后的长度-改变之前的长度）】
                    int cursorIndex = userCursorIndex;
                    if (1 < beforeAutoChangeValue.length()) {
                        // 后台自动改变值后的长度
                        int afterAutoChangeLength = s.toString().length();
                        cursorIndex = userCursorIndex + (afterAutoChangeLength - beforeAutoChangeValue.length());
                    }
                    Selection.setSelection(etable, cursorIndex);
                }
                // 否则格式化用户录入金额
                else {
                    try {
                        // 记录用户输入值
                        beforeAutoChangeValue = s.toString();
                        // 记录用户光标位置
                        userCursorIndex = etMoney.getSelectionEnd();
                        // 获取用户输入金额并解析金额（将金额中的逗号“,”剔除）
                        String money = parseMoney(beforeAutoChangeValue);
                        // 若当前输入的是点则不做格式化
                        if (beforeAutoChangeValue.length() == 1
                                && beforeAutoChangeValue.substring(0, 1).equals(".")) {
                            etMoney.setText("0.");
                            Selection.setSelection(etMoney.getText(), 2);
                            return;
                        }
                        if (beforeAutoChangeValue.substring(start, beforeAutoChangeValue.length()).equals(".")) {
                            return;
                        }
                        // 若当前输入的是点则不做格式化
                        if (beforeAutoChangeValue.substring(start, beforeAutoChangeValue.length()).equals("0") && start >= 1
                                && beforeAutoChangeValue.substring(start - 1, start).equals(".")) {
                            return;
                        }
                        if (beforeAutoChangeValue.length() >= 3
                                && beforeAutoChangeValue.indexOf(".") != -1
                                && beforeAutoChangeValue.substring(beforeAutoChangeValue.indexOf(".") + 2
                                , beforeAutoChangeValue.indexOf(".") + 3).equals("0")
                                ) {
                            if (money.contains(".")) {
                                money = money.substring(0, money.indexOf(".") + 3);
                            }
                            double dobleValue = Double.parseDouble(money);
                            DecimalFormat fmt = new DecimalFormat("#,##0.00");
                            String fmtMoney = fmt.format(dobleValue);
                            // 设置自动改变值标识为true
                            //                        String fmtMoney = fmtMoney(money);
                            isAutoChange = true;
                            // 自动改变金额为格式化后的金额
                            etMoney.setText(fmtMoney);
                            return;
                        }
                        // 格式化金额
                        if (money.contains(".")) {
                            money = money.substring(0, money.indexOf(".") + 3);
                        }
                        final double doubleValue;
                        if (TextUtils.isEmpty(money)) {
                            return;
                        } else {
                            doubleValue = Double.parseDouble(money);
                        }
                        DecimalFormat fmt = new DecimalFormat("#,###.##");
                        String fmtMoney = fmt.format(doubleValue);
                        // 设置自动改变值标识为true
//                        String fmtMoney = fmtMoney(money);
                        isAutoChange = true;
                        // 自动改变金额为格式化后的金额
                        etMoney.setText(fmtMoney);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    /**
     * 解析金额（将金额中的逗号“,”剔除）
     *
     * @param money 要格式化的金额
     * @return 格式化后的金额
     */
    public static String parseMoney(String money) {
        return money.replaceAll(",", "");
    }

    /**
     * 格式化手机号码（隐藏4~7位）
     *
     * @param s 需要格式化的号码
     * @return 格式化后的号码
     */
    public static String formatPhoneNum(String s) {
        if (!TextUtils.isEmpty(s)) {
            char[] data = s.toCharArray();
            for (int i = 3; i < 7; i++) {
                data[i] = '*';
            }
            return new String(data);
        } else {
            return "";
        }
    }

    /**
     * 格式化身份证号码（隐藏4~14位）
     *
     * @param s 需要格式化的号码
     * @return 格式化后的号码
     */
    public static String parseIDNo(String s) {
        char[] data = s.toCharArray();
        for (int i = 3; i < 14; i++) {
            data[i] = '*';
        }
        return new String(data);
    }

    /**
     * 去除手机号里面的空格
     *
     * @param s 需要格式化的号码
     * @return 格式化后的号码
     */
    public static String parsePhoneNumNoBlank(String s) {
        if (s.startsWith("+86")) {
            s = s.substring(3);
        } else {

        }
        s = s.replaceAll(" ", "");
        return s;
    }

    /**
     * 日期格式化工具(****年**月**日 )
     *
     * @param date
     * @return
     */
    public static String string2date(String date) {
        if (date == null) {
            return "";
        }
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d = null;
        try {
            d = format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        DateFormat df = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        String rs = df.format(d);
        return rs;
    }

    /**
     * 图片压缩工具，通过改变图片质量
     *
     * @param bm   需要压缩的图片
     * @param size 压缩大小(kb)
     * @return
     */
    public static Bitmap compressBitmap(Bitmap bm, int size) {
        Bitmap bitmap = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        int quality = 100;
        // 当大小大于size,质量压缩10
        while (baos.toByteArray().length / 1024 > size) {
            baos.reset();
            bm.compress(Bitmap.CompressFormat.JPEG, quality, baos);
            quality -= 5;

        }
        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        if(BuildConfig.DEBUG)
        Log.d("-------baos------", baos.toByteArray().length + "");
        bitmap = BitmapFactory.decodeStream(bais, null, null);
        return bitmap;
    }

    /**
     * 把dip转化px
     */
    public static int dip2px(Context context, float dip) {
        float density = context.getResources().getDisplayMetrics().density;// 密度
        int px = (int) (dip / (density / 1));
        return px;
    }

    /*
     * 压缩图片 避免内存溢出
     */
    public static Bitmap readBitmap(Context context, int id, int sampleSize) {
        Options opt = new Options();
        opt.inPreferredConfig = Bitmap.Config.ARGB_8888;
        opt.inPurgeable = true;
        opt.inSampleSize = sampleSize;
        opt.inInputShareable = true;
        InputStream is = context.getResources().openRawResource(id);
        return BitmapFactory.decodeStream(is, null, opt);

    }

    /**
     * 按照实际内容重新计算listview高度
     *
     * @param listView
     * @param context
     */
    public static void setListViewHeightBasedOnChilden(ListView listView, Context context) {
        ListAdapter adapter = listView.getAdapter();
        if (adapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listView.getCount(); i++) {
            View listItem = adapter.getView(i, null, listView);
            listItem.measure(View.MeasureSpec.makeMeasureSpec(context.getResources().getDisplayMetrics().widthPixels, View.MeasureSpec.EXACTLY), 0);
            totalHeight += listItem.getMeasuredHeight();

        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (adapter.getCount() - 1)) + listView.getPaddingTop()
                + listView.getPaddingBottom();
        listView.setLayoutParams(params);
    }

    /**
     * Description: 根据网络url获取视频缩略图
     *
     * @param url
     * @param width
     * @param height
     * @return
     * @Version1.0 2016-1-19 上午10:02:44 by xupeng (xupeng@csii.com.cn)
     */
    public static Bitmap createVideoThumbnail(Context context, String url, int width, int height) {
        Bitmap bitmap = null;
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        int kind = MediaStore.Video.Thumbnails.FULL_SCREEN_KIND;
        try {
            if (Build.VERSION.SDK_INT >= 14) {
                retriever.setDataSource(url, new HashMap<String, String>());
            } else {
                retriever.setDataSource(url);
            }
            bitmap = retriever.getFrameAtTime();
        } catch (IllegalArgumentException ex) {
            // Assume this is a corrupt video file
        } catch (RuntimeException ex) {
            // Assume this is a corrupt video file.
        } finally {
            try {
                retriever.release();
            } catch (RuntimeException ex) {
                // Ignore failures while cleaning up.
            }
        }
        if (kind == Images.Thumbnails.MICRO_KIND && bitmap != null) {
            bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height, ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
        }
        return bitmap;
    }

    /**
     * Description: 获取本地视频缩略图
     *
     * @param videoPath
     * @param width
     * @param height
     * @param kind
     * @return
     * @Version1.0 2016-1-19 上午10:03:41 by xupeng (xupeng@csii.com.cn)
     */
    public static Bitmap getVideoThumbnail(Context context, String videoPath, int width, int height, int kind) {
        Bitmap bitmap = null;
        // 获取视频的缩略图
        bitmap = ThumbnailUtils.createVideoThumbnail(videoPath, kind);
        bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height, ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
        return bitmap;
    }

    /**
     * 格式化手机号
     *
     * @param phone
     * @return
     */
    public static String formatPhone(String phone) {
        int length = phone.length();
        if (length < 11) {
            return phone;
        } else {
            return phone.substring(0, 3) + " **** " + phone.substring(length - 4, length);
        }
    }

    /**
     * 输入过程中格式化身份证号
     *
     * @param mEditText
     * @param idSub_tv
     */
    public static void idAddSpace(final EditText mEditText, final TextView idSub_tv) {
        mEditText.addTextChangedListener(new TextWatcher() {

            int beforeTextLength = 0;
            int onTextLength = 0;
            boolean isChanged = false;

            int location = 0;// 记录光标的位置
            private char[] tempChar;
            private StringBuffer buffer = new StringBuffer();
            int konggeNumberB = 0;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                beforeTextLength = s.length();
                if (buffer.length() > 0) {
                    buffer.delete(0, buffer.length());
                }
                konggeNumberB = 0;
                for (int i = 0; i < s.length(); i++) {
                    if (s.charAt(i) == ' ') {
                        konggeNumberB++;
                    }
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                onTextLength = s.length();
                buffer.append(s.toString());
                if (onTextLength == beforeTextLength || onTextLength <= 3 || isChanged) {
                    isChanged = false;
                    return;
                }
                isChanged = true;
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (idSub_tv != null) {
                    idSub_tv.setText(mEditText.getText());
                }
                if (isChanged) {
                    location = mEditText.getSelectionEnd();
                    int index = 0;
                    while (index < buffer.length()) {
                        if (buffer.charAt(index) == ' ') {
                            buffer.deleteCharAt(index);
                        } else {
                            index++;
                        }
                    }

                    index = 0;
                    int konggeNumberC = 0;
                    while (index < buffer.length()) {
                        if ((index == 6 || index == 15)) {
                            buffer.insert(index, ' ');
                            konggeNumberC++;
                        }
                        index++;
                    }

                    if (konggeNumberC > konggeNumberB) {
                        location += (konggeNumberC - konggeNumberB);
                    }

                    tempChar = new char[buffer.length()];
                    buffer.getChars(0, buffer.length(), tempChar, 0);
                    String str = buffer.toString();
                    if (location > str.length()) {
                        location = str.length();
                    } else if (location < 0) {
                        location = 0;
                    }

                    if (str.length() >= 20) {
                        str = str.substring(0, 20);
                        location = str.length();
                    }

                    mEditText.setText(str);
                    Editable etable = mEditText.getText();
                    Selection.setSelection(etable, location);
                    isChanged = false;
                }
            }
        });
    }

    /**
     * 解析身份证
     *
     * @param id
     * @return
     */
    public static String parseidNo(String id) {
        return id.replaceAll(" ", "");
    }


    /**
     * 格式化身份证号
     *
     * @param id 身份证号
     * @return
     */
    public static String formatId(String id) {
        if (TextUtils.isEmpty(id))
            return null;
        StringBuffer fmtCardNo = new StringBuffer();
        char[] charArray = id.toCharArray();
        for (int i = 0; i < id.length(); i++) {
            if (((i == 6) || (i == 14))) {
                fmtCardNo.append(" ");
            }
            fmtCardNo.append(charArray[i]);
        }
        return fmtCardNo.toString();
    }

    /**
     * 获取状态栏高度
     *
     * @param context context
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resoureId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resoureId > 0) {
            result = context.getResources().getDimensionPixelSize(resoureId);
        }
        return result;
    }

    /**
     * 自动转换大小写
     */
    public static class AutoCaseTransformationMethod extends ReplacementTransformationMethod {
        /**
         * 获取要改变的字符。
         *
         * @return 将你希望被改变的字符数组返回。
         */
        @Override
        protected char[] getOriginal() {
            return new char[]{'a', 'b', 'c', 'd', 'e',
                    'f', 'g', 'h', 'i', 'j', 'k', 'l',
                    'm', 'n', 'o', 'p', 'q', 'r', 's',
                    't', 'u', 'v', 'w', 'x', 'y', 'z'};
        }

        /**
         * 获取要替换的字符。
         *
         * @return 将你希望用来替换的字符数组返回。
         */
        @Override
        protected char[] getReplacement() {
            return new char[]{'A', 'B', 'C', 'D', 'E',
                    'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
                    'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
        }
    }

    /**
     * 将jsonStr格式化
     *
     * @param jsonStr
     * @return
     */
    public static String jsonFormat(String jsonStr) {
        int level = 0;
        StringBuffer jsonForMatStr = new StringBuffer();
        for (int i = 0; i < jsonStr.length(); i++) {
            char c = jsonStr.charAt(i);
            if (level > 0 && '\n' == jsonForMatStr.charAt(jsonForMatStr.length() - 1)) {
                jsonForMatStr.append(getLevelStr(level));
            }
            switch (c) {
                case '{':
                case '[':
                    jsonForMatStr.append(c + "\n");
                    level++;
                    break;
                case ',':
                    jsonForMatStr.append(c + "\n");
                    break;
                case '}':
                case ']':
                    jsonForMatStr.append("\n");
                    level--;
                    jsonForMatStr.append(getLevelStr(level));
                    jsonForMatStr.append(c);
                    break;
                default:
                    jsonForMatStr.append(c);
                    break;
            }
        }

        return jsonForMatStr.toString();

    }

    private static String getLevelStr(int level) {
        StringBuffer levelStr = new StringBuffer();
        for (int levelI = 0; levelI < level; levelI++) {
            levelStr.append("\t");
        }
        return levelStr.toString();
    }

}
