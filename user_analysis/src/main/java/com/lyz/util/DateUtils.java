package com.lyz.util;

import javafx.scene.input.DataFormat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @Author: liyuzhan
 * @classDesp：时间工具类
 * @Date: 2019/12/31 10:07
 * @Email: 1031759184@qq.com
 */
public class DateUtils {
    public static String getYearBaseByAge(String age) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.YEAR, -Integer.parseInt(age));
        Date date = calendar.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
        String dateString = dateFormat.format(date);
        int dateInt = Integer.parseInt(dateString);
        String yearDataType = "未知";
        if (dateInt >= FrameContent.FOURTY_YEAR && dateInt < FrameContent.FIFTY_YEAR) {
            yearDataType = FrameContent.FOURTY_YEAR_NAME;
        } else if (dateInt >= FrameContent.FIFTY_YEAR && dateInt < FrameContent.SIXTY_YEAR) {
            yearDataType = FrameContent.FIFTY_YEAR_NAME;
        } else if (dateInt >= FrameContent.SIXTY_YEAR && dateInt < FrameContent.SEVENTY_YEAR) {
            yearDataType = FrameContent.SEVENTY_YEAR_NAME;
        } else if (dateInt >= FrameContent.SEVENTY_YEAR && dateInt < FrameContent.EIGHTY_YEAR) {
            yearDataType = FrameContent.SEVENTY_YEAR_NAME;
        } else if (dateInt >= FrameContent.EIGHTY_YEAR && dateInt < FrameContent.NINETY_YEAR) {
            yearDataType = FrameContent.EIGHTY_YEAR_NAME;
        } else if (dateInt >= FrameContent.NINETY_YEAR && dateInt < FrameContent.ZERO_YEAR) {
            yearDataType = FrameContent.NINETY_YEAR_NAME;
        } else if (dateInt >= FrameContent.ZERO_YEAR && dateInt < FrameContent.TEN_YEAR) {
            yearDataType = FrameContent.ZERO_YEAR_NAME;
        } else if (dateInt >= FrameContent.TEN_YEAR) {
            yearDataType = FrameContent.TEN_YEAR_NAME;
        }

        return yearDataType;
    }
}
