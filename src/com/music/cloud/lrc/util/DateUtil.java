package com.music.cloud.lrc.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    /**
     * 在原时间串的基础上加一毫秒
     *
     * @param oldDateStr 原始时间串 [00.00.00] | [00.00.000]
     */
    public static String addOneMilliSecond(String oldDateStr) throws Exception {
        SimpleDateFormat sdf;
        if (oldDateStr.length() == 10) {
            sdf = new SimpleDateFormat("mm:ss.SS");
        } else if (oldDateStr.length() == 11) {
            sdf = new SimpleDateFormat("mm:ss.SSS");
        } else {
            return null;
        }
        String prefix = oldDateStr.substring(0,1);
        String suffix = oldDateStr.substring(oldDateStr.length() - 1);
        oldDateStr = oldDateStr.substring(1, oldDateStr.length() - 1);
        Date oldDate = sdf.parse(oldDateStr);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(oldDate);
        calendar.add(Calendar.MILLISECOND, 1);
        Date newDate = calendar.getTime();
        return prefix + sdf.format(newDate) + suffix;
    }
}
