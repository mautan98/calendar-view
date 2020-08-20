package com.baseapp.utils;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by xuannt on 6/8/2017.
 */

public class StringUtils {
    public static String upperToCaseFirst(String string) {
        if (!string.isEmpty() && string.length() > 1) {
            String upperString = string.substring(0, 1).toUpperCase() + string.substring(1);
            return upperString;
        } else {
            return string;
        }
    }

    public static String lowerToCaseFirst(String string) {
        if (!string.isEmpty() && string.length() > 1) {
            String upperString = string.substring(0, 1).toLowerCase() + string.substring(1);
            return upperString;
        } else {
            return string;
        }
    }

    public static final String md5(final String s) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = MessageDigest
                    .getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String isPhoneValidate(String phoneNum, int countryCallingCode) {
        String phone = "";
        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
        try {
            String countryCode = phoneUtil.getRegionCodeForCountryCode(countryCallingCode);
            Phonenumber.PhoneNumber numberProto = phoneUtil.parse(phoneNum, countryCode);
            if (phoneUtil.isValidNumber(numberProto)) {
                phone = String.valueOf(numberProto.getCountryCode()) + numberProto.getNationalNumber();
                return phone;
            } else {
                return phone;
            }

        } catch (NumberParseException e) {
            System.err.println("NumberParseException was thrown: " + e.toString());
        }
        return phone;
    }

    public static String isPhoneValidateV2(String phoneNum, int countryCallingCode) {
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

    public static boolean isNumeric(String str) {
        try {
            double d = Double.parseDouble(str);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public static boolean isNumericRegex(String s) {
        if (s.matches(".*\\d.*")) {
            return true;
        }
        return false;
    }

    public static boolean isAlphabets(String s) {
        Pattern pattern = Pattern.compile(".*[a-zA-Z]+.*");
        Matcher matcher = pattern.matcher(s);
        if (matcher.matches()) {
            return true;
        }
        return false;
    }

    public static boolean isSpecialCharacter(String s) {
        Pattern p = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(s);
        return m.find();
    }
}
