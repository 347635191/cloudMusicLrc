package com.music.cloud.lrc.util;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SpiderUtil {
    private final static String iv = "0102030405060708";

    private final static String g = "0CoJUm6Qyw8W8jud";

    private final static String i = "Sx7KnWt4ttr85X0b";

    private final static String encSecKey = "252e2abdc25a5c8e4e4131b88db3df7d01ab4a139249b78e653b97ab52f53b873993b86648e54daa3a99eeb20fd3b2c4d1d551231a152bfa56ed0a13baae9243f978bf1fbcde4e70b25087fd0aeef413a698a0a37567a550876f8cdeedb25cf359f54532eb2681f63641a4fa98b837fb9978c3296b2923bca8f5d1661d3ec5dc";

    private static String encrypt(String str, String g) {
        return AesCBCUtil.encrypt(str, "utf-8", g, iv).replace("\r\n", "");
    }

    public static Map<String, String> buildParamMap(String id) {
        String originParams = String.format("{\"id\":\"%s\",\"lv\":-1,\"tv\":-1,\"csrf_token\":\"\"}", id);
        String encryptParams = encrypt(originParams, g);
        String doubleEncryptParams = encrypt(encryptParams, i);
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("encSecKey", encSecKey);
        paramMap.put("params", doubleEncryptParams);
        return paramMap;
    }

    public static String getLrcJson(String id) {
        Document document = null;
        Map<String, String> headMap = new HashMap<>();
        Map<String, String> paramMap = SpiderUtil.buildParamMap(id);
        Connection con = Jsoup.connect("https://music.163.com/weapi/song/lyric?csrf_token=");
        headMap.put("Content-Type", "application/x-www-form-urlencoded");
        headMap.put("Accept", "*/*");
        con.headers(headMap);
        con.data(paramMap);
        try {
            document = con.post();
        } catch (IOException e) {
            LogUtil.log("请检查网络,音乐编号" + id + "及之后生成失败");
            return null;
        }
        return document.text();
    }
}