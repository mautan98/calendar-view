package com.namviet.vtvtravel.ultils;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;

public class StringUtils {
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
}
