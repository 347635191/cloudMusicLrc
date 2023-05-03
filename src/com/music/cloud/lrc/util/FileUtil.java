package com.music.cloud.lrc.util;

import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileUtil {
    public static final String BASE_DIR = "D:\\lrc";

    private static final String DEFAULT_FILE_PATH = BASE_DIR + "\\%s.lrc";

    public static void writeLrc(String id, String content) {
        if (createDir()) {
            return;
        }
        String filePath = String.format(DEFAULT_FILE_PATH, id);
        try (FileWriter fileWriter = new FileWriter(filePath)) {
            fileWriter.append(content);
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
