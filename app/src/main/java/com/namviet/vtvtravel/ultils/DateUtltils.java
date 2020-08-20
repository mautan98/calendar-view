package com.namviet.vtvtravel.ultils;

import android.annotation.SuppressLint;
import android.util.Log;

import com.namviet.vtvtravel.config.Constants;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;


public class DateUtltils {
    public static String timeToString(Long dateTime) {
        if (dateTime != null) {
            Date date = new Date(dateTime * 1000);
            DateFormat formatter = new SimpleDateFormat(Constants.DateFormat.DATE_FORMAT_5);
            String dateFormatted = formatter.format(date);
            return dateFormatted;
        } else {
            return null;
        }

    }

    public static String timeToString2(Long dateTime) {
        if (dateTime != null) {
            Date date = new Date(dateTime * 1000);
            DateFormat formatter = new SimpleDateFormat(Constants.DateFormat.DATE_FORMAT_12);
            String dateFormatted = formatter.format(date);
            return dateFormatted;
        } else {
            return null;
        }
    }

    public static String timeToString6(Long dateTime) {
        if (dateTime != null) {
            Date date = new Date(dateTime);
            DateFormat formatter = new SimpleDateFormat(Constants.DateFormat.DATE_FORMAT_12, Locale.getDefault());
            formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
            String dateFormatted = formatter.format(date);
            return dateFormatted;
        } else {
            return null;
        }
    }

    public static String timeToString5(Long dateTime) {
        if (dateTime != null) {
            Date date = new Date(dateTime);
            DateFormat formatter = new SimpleDateFormat(Constants.DateFormat.DATE_FORMAT_5);
            formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
            String dateFormatted = formatter.format(date);
            return dateFormatted;
        } else {
            return null;
        }

    }

    public static String timeToString3(Long dateTime) {
        if (dateTime != null) {
            Date date = new Date(dateTime * 1000);
            DateFormat formatter = new SimpleDateFormat(Constants.DateFormat.DATE_FORMAT_14);
            String dateFormatted = formatter.format(date);
            return dateFormatted;
        } else {
            return null;
        }
    }

    public static String timeToString4(Long dateTime) {
        if (dateTime != null) {
            Date date = new Date(dateTime);
            DateFormat formatter = new SimpleDateFormat(Constants.DateFormat.DATE_FORMAT_14);
            String dateFormatted = formatter.format(date);
            return dateFormatted;
        } else {
            return null;
        }
    }

    public static String timeToString16(Long dateTime) {
        if (dateTime != null) {
            Date date = new Date(dateTime);
            DateFormat formatter = new SimpleDateFormat(Constants.DateFormat.DATE_FORMAT_16);
            String dateFormatted = formatter.format(date);
            return dateFormatted;
        } else {
            return null;
        }
    }


    public static long currentTime() {
        return Calendar.getInstance().getTimeInMillis() / 1000;
    }

    public static String timeToStringWeather(Long dateTime) {
        Locale vn = new Locale("vi", "VN");
        SimpleDateFormat formatThu = new SimpleDateFormat(Constants.DateFormat.DATE_FORMAT_3, vn);
        SimpleDateFormat formatDay = new SimpleDateFormat(Constants.DateFormat.DATE_FORMAT_4);
        Date date = new Date(dateTime * 1000);
        String time = formatThu.format(date) + ", " + formatDay.format(date.getTime());
        return time;

    }

    public static String timeEndTour(String startTour, int duration) {
        SimpleDateFormat format = new SimpleDateFormat(Constants.DateFormat.DATE_FORMAT_5);
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(format.parse(startTour));
            calendar.add(Calendar.DATE, duration);
            return format.format(calendar.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean isToday(long time) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(time);
        String date = android.text.format.DateFormat.format("dd-MM-yyyy", cal).toString();
        String timeStamp = new SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().getTime());

        return date.equals(timeStamp);
    }

    public static String convertTime(String date) {
        SimpleDateFormat spf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        Date newDate = null;
        try {
            newDate = spf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        spf = new SimpleDateFormat("dd MMMM yyyy");
        return spf.format(newDate);
    }
}
