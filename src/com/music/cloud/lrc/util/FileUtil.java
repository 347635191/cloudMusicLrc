package com.music.cloud.lrc.util;

import java.io.FileWriter;
import java.io.IOException;

public class FileUtil {
    public static final String FILE_NAME = "歌词.lrc";

    private static final String FILE_PATH = "C:\\Users\\lufei\\Desktop\\" + FILE_NAME;

    public static void writeLrc(String content) {
        try (FileWriter fileWriter = new FileWriter(FILE_PATH)) {
            fileWriter.append(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
