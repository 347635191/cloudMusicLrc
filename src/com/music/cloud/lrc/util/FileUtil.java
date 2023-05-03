package com.music.cloud.lrc.util;

import javax.swing.*;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class FileUtil {
    public static final String BASE_DIR = "D:\\lrc";

    private static final String DEFAULT_FILE_PATH = BASE_DIR + "\\%s.lrc";

    public static void writeLrc(String id, String content, String musicName) {
        if (createDir()) {
            return;
        }
        String filePath = String.format(DEFAULT_FILE_PATH, musicName == null ? id : musicName);
        try (BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath), StandardCharsets.UTF_8))) {
            bufferedWriter.append(content);
            bufferedWriter.flush();
            LogUtil.log(filePath + "生成成功");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean createDir() {
        File file = new File(BASE_DIR);
        if (!file.exists()) {
            boolean mkdir = file.mkdir();
            if (!mkdir) {
                JOptionPane.showMessageDialog(null, BASE_DIR + "目录创建失败,请手动创建");
                return true;
            }
        }
        return false;
    }
}
