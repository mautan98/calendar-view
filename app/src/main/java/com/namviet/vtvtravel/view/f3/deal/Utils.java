package com.namviet.vtvtravel.view.f3.deal;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.namviet.vtvtravel.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;


/**
 * Created by GIGAMOLE on 8/18/16.
 */
public class Utils {

    public static void setupItem(final View view, final LibraryObject libraryObject) {
        final ImageView img = (ImageView) view.findViewById(R.id.img_item);
        img.setImageResource(libraryObject.getRes());
    }

    public static class LibraryObject {

        private String mTitle;
        private int mRes;

        public LibraryObject(final int res, final String title) {
            mRes = res;
            mTitle = title;
        }

        public String getTitle() {
            return mTitle;
        }

        public void setTitle(final String title) {
            mTitle = title;
        }

        public int getRes() {
            return mRes;
        }

        public void setRes(final int res) {
            mRes = res;
        }
    }

    public static class CalendarUtils {

        public static String dateFormat = "dd.MM.yyyy";
        private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);

        public static String ConvertMilliSecondsToFormattedDate(Long milliSeconds) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(milliSeconds);
            return simpleDateFormat.format(calendar.getTime());
        }

        public static String getDayLeft(long timeStamp) {
            long myCurrentTimeMillis = System.currentTimeMillis();
            if (myCurrentTimeMillis > timeStamp) {
                return "0 ngày";
            } else {
                long distance = (timeStamp - myCurrentTimeMillis) / 1000;

                String days = (int) (distance / 86400) + " ngày ";
                String hours = String.valueOf((int) ((distance % 86400) / 3600));
                String minutes = String.valueOf((int) ((distance % 3600) / 60));
                String seconds = String.valueOf((int) ((distance % 3600) % 60));

                return "Còn lại " + days + hours + ":" + minutes + ":" + seconds;

            }
        }
        public static String getDayStart(long timeStamp) {
            long myCurrentTimeMillis = System.currentTimeMillis();
            if (myCurrentTimeMillis > timeStamp) {
                return "0 ngày";
            } else {
                long distance = (timeStamp - myCurrentTimeMillis) / 1000;

                String days = (int) (distance / 86400) + " ngày ";
                String hours = String.valueOf((int) ((distance % 86400) / 3600));
                String minutes = String.valueOf((int) ((distance % 3600) / 60));
                String seconds = String.valueOf((int) ((distance % 3600) % 60));

                return "Bắt đầu sau " + days + hours + ":" + minutes + ":" + seconds;

            }
        }

        public static String getTimeHold(long timeStamp) {
            long distance = timeStamp / 1000;
            String days = (int) (distance / 86400) + " ngày ";
            String hours = String.valueOf((int) ((distance % 86400) / 3600));
            String minutes = String.valueOf((int) ((distance % 3600) / 60));
            String seconds = String.valueOf((int) ((distance % 3600) % 60));
            int hoursC = Integer.parseInt(hours);
            int minutesC = Integer.parseInt(minutes);
            int secondsC = Integer.parseInt(seconds);
            String hoursFinal;
            String minutesFinal;
            String secondsFinal;
            if (hoursC < 10) {
                hoursFinal = "0" + hours;
            } else hoursFinal = hours;
            if (minutesC < 10) {
                minutesFinal = "0" + minutes;
            } else minutesFinal = minutes;
            if (hoursC < 10) {
                secondsFinal = "0" + seconds;
            } else secondsFinal = seconds;

            return days + hoursFinal + ":" + minutesFinal + ":" + secondsFinal;
        }

        public static int getPercentProgress(long startTime, long endTime) {
            try {
                long myCurrentTimeMillis = System.currentTimeMillis();
                if (myCurrentTimeMillis < startTime) {
                    return 0;
                }
                if (myCurrentTimeMillis > endTime) {
                    return 100;
                } else {
//                    return (int)(((double)(endTime - myCurrentTimeMillis)/(double)(endTime - startTime)) * 100);
                    return (int) (((double) (myCurrentTimeMillis - startTime) / (double) (endTime - startTime)) * 100);
                }
            } catch (Exception e) {
                return 50;
            }
        }
    }
}
