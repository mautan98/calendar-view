package com.namviet.vtvtravel.ultils;

import android.text.TextUtils;


import com.baseapp.utils.StringUtils;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import com.namviet.vtvtravel.config.Constants;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by 1 on 12/8/2017.
 */

public class ValidateUtils {
    //    public static boolean isValidPhoneNumber(String phoneNumber) {
//        String number = StringUtils.isPhoneValidateV2(phoneNumber, 84);
//        return android.util.Patterns.PHONE.matcher(number).matches() && ;
//    }
    public static boolean isValidPhoneNumber(String number) {
        Pattern pattern = Pattern.compile("^[0-9]*$");
        Matcher matcher = pattern.matcher(number);
        if (!matcher.matches()) {
            return false;
        } else if (number.length() == 10 || number.length() == 11) {
            if (number.length() == 10) {
                return number.substring(0, 2).equals("03")
                        || number.substring(0, 2).equals("07")
                        || number.substring(0, 2).equals("08")
                        || number.substring(0, 2).equals("09");
            } else
                return number.substring(0, 2).equals("84") && !number.substring(0, 3).equals("840");
        } else if (number.length() == 12) {
            return number.substring(0, 3).equals("840");
        } else {
            return false;
        }
    }

    public static boolean isValidPhoneNumber09(String number) {
        Pattern pattern = Pattern.compile("^[0-9]*$");
        Matcher matcher = pattern.matcher(number);
        if (!matcher.matches()) {
            return false;
        } else if (number.length() == 10) {
            return number.substring(0, 2).equals("03")
                    || number.substring(0, 2).equals("07")
                    || number.substring(0, 2).equals("08")
                    || number.substring(0, 2).equals("09");
        } else {
            return false;
        }
    }

    public static boolean isValidPhoneNumberInvite(String number) {
        Pattern pattern = Pattern.compile("^[0-9]*$");
        Matcher matcher = pattern.matcher(number);
        if (!matcher.matches()) {
            return false;
        } else if (number.length() == 10) {
            return number.substring(0, 2).equals("03")
                    || number.substring(0, 2).equals("07")
                    || number.substring(0, 2).equals("08")
                    || number.substring(0, 2).equals("09");
        } else if(number.length() == 11){
            return number.substring(0, 3).equals("843")
                    || number.substring(0, 3).equals("847")
                    || number.substring(0, 3).equals("848")
                    || number.substring(0, 3).equals("849");
        }else  {
            return false;
        }
    }

    public static boolean isValidPhoneNumberNew(String number) {
        Pattern pattern = Pattern.compile("^[0-9]*$");
        Matcher matcher = pattern.matcher(number);
        if (!matcher.matches()) {
            return false;
        } else if (number.length() == 10 || number.length() == 9) {
            if (number.length() == 9) {
                return true;
            }
            if (number.length() == 10) {
                return number.substring(0, 2).equals("03")
                        || number.substring(0, 2).equals("07")
                        || number.substring(0, 2).equals("08")
                        || number.substring(0, 2).equals("09");
            } else
                return number.substring(0, 2).equals("84") && !number.substring(0, 3).equals("840");
        } else if (number.length() == 12) {
            return number.substring(0, 3).equals("840");
        } else if(number.length() == 11){
            if(number.startsWith("84")){
                return true;
            }else {
                return false;
            }
        }else {
            return false;
        }
    }

    public static boolean isValidEmail(String email) {
        if (TextUtils.isEmpty(email)) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
        }
    }

    public static boolean isNumeric(String str) {
        try {
            double d = Double.parseDouble(str);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public static boolean isValidPass(String str) {
        String regex = "^(?=.*[a-z])(?=.*[0-9])(?=\\S+$).{6,20}$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(str);
        if (m.matches()) {
            return true;
        }
        return false;
    }

    public static String isValidDate(String date) {
        DateFormat df = new SimpleDateFormat(Constants.DateFormat.DATE_FORMAT_4);
        DateFormat dfNew = new SimpleDateFormat(Constants.DateFormat.DATE_FORMAT_1);
        Date startDate;
        try {
            startDate = df.parse(date);
            String newDateString = dfNew.format(startDate);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String validateTimeFilter(String time) {
        SimpleDateFormat fm = new SimpleDateFormat(Constants.DateFormat.DATE_FORMAT_1);
        SimpleDateFormat fmNew = new SimpleDateFormat(Constants.DateFormat.DATE_FORMAT_1);
        String timeNew = "";
        try {
            timeNew = fmNew.format(fm.parse(time));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return timeNew;
    }

    public static String convertDateTime(String dateTime) {
        SimpleDateFormat formatter = new SimpleDateFormat(Constants.DateFormat.DATE_FORMAT_8);
        SimpleDateFormat formatNew = new SimpleDateFormat(Constants.DateFormat.DATE_FORMAT_11);

        String[] dateTimeArr = new String[0];
        try {
            dateTimeArr = formatNew.format(formatter.parse(dateTime)).split(" ");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return dateTimeArr[1] + " " + dateTimeArr[0];
    }

    public static String convertBirthday(String dateTime) {
        SimpleDateFormat formatter = new SimpleDateFormat(Constants.DateFormat.DATE_FORMAT_1);
        SimpleDateFormat formatNew = new SimpleDateFormat(Constants.DateFormat.DATE_FORMAT_4);

        String birthday = "";
        try {
            birthday = formatNew.format(formatter.parse(dateTime));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return birthday;
    }

    public static long convertStringDate(String dateTime) {
        SimpleDateFormat format = new SimpleDateFormat(Constants.DateFormat.DATE_FORMAT_1);
        SimpleDateFormat formatNew = new SimpleDateFormat(Constants.DateFormat.DATE_FORMAT_2);
        try {
            String time = formatNew.format(format.parse(dateTime));
            Date date = formatNew.parse(time);
            return date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static String convertDateHistoryItem(String dateTime) {
        SimpleDateFormat formatter = new SimpleDateFormat(Constants.DateFormat.DATE_FORMAT_1);
        SimpleDateFormat formatterCustom = new SimpleDateFormat(Constants.DateFormat.DATE_FORMAT_10);
        String time = "";
        try {
            time = formatterCustom.format(formatter.parse(dateTime));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }

    public static String convertDateHistory(String dateTime) {
        Locale vn = new Locale("vi", "VN");
        SimpleDateFormat format = new SimpleDateFormat(Constants.DateFormat.DATE_FORMAT_1);
        SimpleDateFormat formatThu = new SimpleDateFormat(Constants.DateFormat.DATE_FORMAT_3, vn);
        SimpleDateFormat formatDay = new SimpleDateFormat(Constants.DateFormat.DATE_FORMAT_4);
        try {
            Date date = format.parse(dateTime);
            String time = formatThu.format(date) + ", Ngày " + formatDay.format(date.getTime());
            return time;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String coverNumberToCurrency(int number) {
        DecimalFormat formatter = new DecimalFormat("#,###");
        return formatter.format(number);
    }

    public static String currencyVn(double number) {
        Locale vn = new Locale("vi", "VN");
        NumberFormat format = NumberFormat.getCurrencyInstance(vn);

        DecimalFormatSymbols decimalFormatSymbols = ((DecimalFormat) format).getDecimalFormatSymbols();
        decimalFormatSymbols.setCurrencySymbol("");
        decimalFormatSymbols.setDecimalSeparator(',');
        decimalFormatSymbols.setGroupingSeparator('.');
        ((DecimalFormat) format).setDecimalFormatSymbols(decimalFormatSymbols);
        return format.format(number).trim();
    }

    public static String convertPhoneNumber(String phone) {
        String phoneConvert = "";
        if (phone.length() == 10) {
            phoneConvert = phone.substring(0, 4) + "." + phone.substring(4, 7) + "." + phone.substring(7);
        } else {
            phoneConvert = phone.substring(0, 5) + "." + phone.substring(5, 8) + "." + phone.substring(8);
        }
        return phoneConvert;
    }

    public static String convertPhoneNumberWithSpace(String phone) {
        String phoneConvert = "";
        if (phone.length() == 10) {
            phoneConvert = phone.substring(0, 3) + " " + phone.substring(3, 6) + " " + phone.substring(6);
        } else {
            phoneConvert = phone.substring(0, 4) + " " + phone.substring(4, 7) + " " + phone.substring(7);
        }
        return phoneConvert;
    }

    public static String getCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat(Constants.DateFormat.DATE_FORMAT_10);
        String currentDateandTime = sdf.format(new Date());
        return currentDateandTime;
    }

    public static boolean isString(String s) {
        Pattern special = Pattern.compile("[!@#$%&*()_+=|<>?{}\\[\\]~-]");
        Matcher hasSpecial = special.matcher(s);

        return hasSpecial.find();
    }


    public static String isPhoneValidateV2(String phoneNum, int countryCallingCode) {
        if (phoneNum.length() == 9) {
            return "84" + phoneNum;
        } else {
            String phone = "";
            PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
            try {
                String countryCode = phoneUtil.getRegionCodeForCountryCode(countryCallingCode);
                Phonenumber.PhoneNumber numberProto = phoneUtil.parse(phoneNum, countryCode);
                phone = String.valueOf(numberProto.getCountryCode()) + numberProto.getNationalNumber();
                return phone;

            } catch (NumberParseException e) {
                System.err.println("NumberParseException was thrown: " + e.toString());
            }
            return phone;
        }
    }
}
