package com.music.cloud.lrc.util;

import java.io.*;
import java.nio.charset.StandardCharsets;
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
        try (BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(LogPath, true), StandardCharsets.UTF_8))) {
            text = time +  text + '\n';
            bufferedWriter.append(text);
            bufferedWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
