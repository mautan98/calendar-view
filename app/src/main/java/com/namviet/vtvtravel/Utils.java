package com.namviet.vtvtravel;

import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.text.Html;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class Utils {
    private static final int WIDTH_INDEX = 0;
    private static final int HEIGHT_INDEX = 1;

    public static int[] getScreenSize(Context context) {
        int[] widthHeight = new int[2];
        widthHeight[WIDTH_INDEX] = 0;
        widthHeight[HEIGHT_INDEX] = 0;

        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();

        Point size = new Point();
        display.getSize(size);
        widthHeight[WIDTH_INDEX] = size.x;
        widthHeight[HEIGHT_INDEX] = size.y;

        if (!isScreenSizeRetrieved(widthHeight)) {
            DisplayMetrics metrics = new DisplayMetrics();
            display.getMetrics(metrics);
            widthHeight[0] = metrics.widthPixels;
            widthHeight[1] = metrics.heightPixels;
        }

        // Last defense. Use deprecated API that was introduced in lower than API 13
        if (!isScreenSizeRetrieved(widthHeight)) {
            widthHeight[0] = display.getWidth(); // deprecated
            widthHeight[1] = display.getHeight(); // deprecated
        }

        return widthHeight;
    }

    private static boolean isScreenSizeRetrieved(int[] widthHeight) {
        return widthHeight[WIDTH_INDEX] != 0 && widthHeight[HEIGHT_INDEX] != 0;
    }

    public static String convertDateToStringVN(Date date, String format) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        Calendar now = Calendar.getInstance();
        int x = calendar.get(Calendar.DAY_OF_YEAR);
        if (calendar.get(Calendar.DAY_OF_YEAR) == now.get(Calendar.DAY_OF_YEAR)
                && calendar.get(Calendar.YEAR) == now.get(Calendar.YEAR)) {
            return "Hôm nay";
        }
        if (calendar.get(Calendar.DAY_OF_YEAR) + 1 == now.get(Calendar.DAY_OF_YEAR)
                && calendar.get(Calendar.YEAR) == now.get(Calendar.YEAR)) {
            return "Hôm qua";
        }
        if ((calendar.get(Calendar.DAY_OF_YEAR) == 365 || calendar.get(Calendar.DAY_OF_YEAR) == 366)
                && now.get(Calendar.DAY_OF_YEAR) == 1
                && calendar.get(Calendar.YEAR) + 1 == now.get(Calendar.YEAR)) {
            return "Hôm qua";
        }
        return convertDateToString(date, format);
    }

    public static String convertDateToString(Date date, String format) {
        SimpleDateFormat SDFoutput = new SimpleDateFormat(format, Locale.getDefault());
        return SDFoutput.format(date);
    }

    public static String convertDateStringToString(String input, String format) {
        return convertDateStringToString(input, format, "MMM dd");
    }

    public static String convertDateStringToString(String input, String inFormat, String outFormat) {
        return convertDateStringToString(input, inFormat, outFormat, TimeZone.getDefault(), TimeZone.getDefault());
    }

    public static String convertDateStringToString(String input, String inFormat, String outFormat,
                                                   TimeZone timeZoneIn, TimeZone timeZoneOut) {
        if (TextUtils.isEmpty(input) || TextUtils.isEmpty(inFormat) || TextUtils.isEmpty(outFormat)) {
            return "";
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(inFormat);
        simpleDateFormat.setTimeZone(timeZoneIn);
        try {
            Date date = simpleDateFormat.parse(input);
            SimpleDateFormat sdfoutput = new SimpleDateFormat(outFormat);
            sdfoutput.setTimeZone(timeZoneOut);
            return sdfoutput.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static void fadeOut(View view) {
        view.setAlpha(1.0f);
        view.animate().alpha(0f).setDuration(500);
    }

    public static void fadeIn(View view) {
        view.setAlpha(0f);
        view.animate().alpha(1.0f).setDuration(500);
    }

    public static String getSpannedText(String text) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(text, Html.FROM_HTML_MODE_COMPACT).toString();
        } else {
            return Html.fromHtml(text).toString();
        }
    }

    public static String convertPriceTrips(BigDecimal price) {
        try {
            DecimalFormat df = new DecimalFormat("###,###,###");
            return df.format(price);
        } catch (Exception e) {
            return "0";
        }
    }

    public static String formatTimestampTrips(Long timeStamp) {
        Date date = new Date(timeStamp);
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_MONTH) + " thg " + (calendar.get(Calendar.MONTH) + 1);
    }

    public static String formatTimeStamp(Long timestamp,String pattern){
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            return simpleDateFormat.format(new Date(timestamp));
        } catch (Exception e){
            return "";
        }
    }

}
