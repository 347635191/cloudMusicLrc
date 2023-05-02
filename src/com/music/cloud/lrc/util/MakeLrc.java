package com.music.cloud.lrc.util;

import com.alibaba.fastjson.JSONObject;

import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Pattern;

/**
 * 通过网易云歌词制作lrc
 * lyric?csrf_token= -> 右键Copy -> Copy Response
 */
public class MakeLrc {
    private static final Map<String, String> map = new TreeMap<>();

    public static void main(String[] args) {
        String lyricResponse = ClipBoardUtil.getClipBoardText();
        JSONObject responseObject = JSONObject.parseObject(lyricResponse);

        //副语言歌词
        String subLrc = responseObject.getJSONObject("tlyric").getString("lyric");

        //主语言歌词
        String mainLrc = responseObject.getJSONObject("lrc").getString("lyric");
        if ("".equals(mainLrc)) {
            System.err.println("主字幕为空");
            return;
        }
        if ("".equals(subLrc)) {
            System.out.println(mainLrc);
            return;
        }
        String[] mainLrcArray = mainLrc.split("\n");
        Arrays.stream(mainLrcArray).forEach(lrc -> {
            String time = lrc.substring(0, lrc.indexOf(']') + 1);
            String word = lrc.substring(lrc.indexOf(']') + 1);
            map.put(time, word);
        });

        //添加副语言歌词
        String[] subLrcArray = subLrc.split("\n");
        Arrays.stream(subLrcArray).filter(lrc -> Pattern.matches("^\\[\\d{2}:\\d{2}.\\d{2,3}][\\s\\S]*$", lrc)).forEach(lrc -> {
            String time = lrc.substring(0, lrc.indexOf(']') + 1);
            String word = lrc.substring(lrc.indexOf(']') + 1);
            if ("".equals(word.trim())) {
                return;
            }
            if (!map.containsKey(time)) {
                throw new RuntimeException("副语言歌词与主语言歌词不匹配：" + lrc);
            }
            String wordFromMap = map.get(time);
            map.put(time, wordFromMap + '\n' + word);
        });
        StringBuilder content = new StringBuilder();
        map.forEach((time, word) -> content.append(time).append(word).append('\n'));
        String result = content.substring(0,content.length() - 1);
        FileUtil.writeLrc(result);
        System.out.println(FileUtil.FILE_NAME + "生成成功");
        System.out.println(result);
    }
}
