package com.music.cloud.lrc.util;

import com.alibaba.fastjson.JSONObject;
import com.music.cloud.lrc.constant.Language;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Pattern;

/**
 * 通过网易云歌词制作lrc
 * lyric?csrf_token= -> 右键Copy -> Copy Response
 */
public class LrcUtil {
    public static boolean makeLrc(String id, Language language) {
        String lyricResponse = SpiderUtil.getLrcJson(id);
        if (lyricResponse == null) {
            return false;
        }
        JSONObject responseObject = JSONObject.parseObject(lyricResponse);

        //主语言歌词
        JSONObject mainLrcObject = responseObject.getJSONObject("lrc");
        if (mainLrcObject == null) {
            LogUtil.log(id + "不是一个有效的音乐编号,已被跳过");
            return true;
        }
        String mainLrc = mainLrcObject.getString("lyric");
        if ("".equals(mainLrc)) {
            LogUtil.log(id + "获取不到主语言,已被跳过");
            return true;
        }
        //副语言歌词
        String subLrc = responseObject.getJSONObject("tlyric").getString("lyric");
        if (language.isSub()) {
            if ("".equals(subLrc.trim())) {
                LogUtil.log(id + "获取不到副语言,已被跳过");
                return true;
            }
            mainLrc = subLrc;
        }
        Map<String, String> timeWordMap = new TreeMap<>();

        //用来处理双语合并时使用
        Map<String, String> repeatTimeMap = new HashMap<>();

        String[] mainLrcArray = mainLrc.split("\n");
        for (String lrc : mainLrcArray) {
            if (!Pattern.matches("^\\[\\d{2}:\\d{2}.\\d{2,3}][\\s\\S]*$", lrc)) {
                continue;
            }
            String time = lrc.substring(0, lrc.indexOf(']') + 1);
            String word = lrc.substring(lrc.indexOf(']') + 1);
            if (timeWordMap.containsKey(time)) {
                LogUtil.log(id + "有重复时间轴,正在处理");
                String noRepeatTime;
                try {
                    noRepeatTime = getNoRepeatTime(timeWordMap, time);
                    repeatTimeMap.put(time, noRepeatTime);
                    time = noRepeatTime;
                } catch (Exception e) {
                    LogUtil.log(id + "时间轴格式处理异常,已被跳过");
                    return true;
                }
                if (noRepeatTime == null) {
                    LogUtil.log(id + "异常的时间轴格式,已被跳过");
                    return true;
                }
            }
            timeWordMap.put(time, word);
        }

        if (!"".equals(subLrc.trim()) && language.isDouble()) {
            //添加副语言歌词
            String[] subLrcArray = subLrc.split("\n");
            for (String lrc : subLrcArray) {
                if (!Pattern.matches("^\\[\\d{2}:\\d{2}.\\d{2,3}][\\s\\S]*$", lrc)) {
                    continue;
                }
                String time = lrc.substring(0, lrc.indexOf(']') + 1);
                String word = lrc.substring(lrc.indexOf(']') + 1);
                if ("".equals(word.trim())) {
                    continue;
                }
                time = repeatTimeMap.getOrDefault(time, time);
                if (!timeWordMap.containsKey(time)) {
                    LogUtil.log(id + "副语言歌词与主语言歌词不匹配,已被跳过");
                    return true;
                }
                String wordFromMap = timeWordMap.get(time);
                timeWordMap.put(time, wordFromMap + '\n' + word);
            }
        }
        StringBuilder content = new StringBuilder();
        timeWordMap.forEach((time, word) -> content.append(time).append(word).append('\n'));
        String result = content.substring(0, content.length() - 1);
        FileUtil.writeLrc(id, result);
        return true;
    }

    /**
     * 处理时间轴重复的歌词,重复时间依次累加一毫秒
     *
     * @param time 原始时间串
     */
    private static String getNoRepeatTime(Map<String, String> map, String time) throws Exception {
        while (map.containsKey(time)) {
            String newTime = DateUtil.addOneMilliSecond(time);
            if (newTime == null) {
                return null;
            }
            time = newTime;
        }
        return time;
    }
}
