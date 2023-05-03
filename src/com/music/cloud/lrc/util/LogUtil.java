package com.music.cloud.lrc.util;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LogUtil {
    private static final String LogPath = FileUtil.BASE_DIR + "\\log.txt";

    private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss\t");

    /**
     * 日志追加输出在log.txt
     *
     * @param text 日志
     */
    public static void log(String text) {
        Date date = new Date();
        String time = SDF.format(date);
        if (FileUtil.createDir()) {
            return;
        }
        try (FileWriter fileWriter = new FileWriter(LogPath, true)) {
            text = time +  text + '\n';
            fileWriter.append(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
