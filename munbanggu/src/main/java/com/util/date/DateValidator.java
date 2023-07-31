package com.util.date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateValidator {
// 미완성 날짜 예외처리 보류
    public static boolean isValidDate(String date, ParseExceptionCallback callback) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        simpleDateFormat.setLenient(false);
        try {
        	simpleDateFormat.parse(date);
            return true;
        } catch (ParseException e) {
            if (callback != null) {
                callback.onParseException(e);
            }
            return false;
        }
    }

    public interface ParseExceptionCallback {
        void onParseException(ParseException e);
    }

}