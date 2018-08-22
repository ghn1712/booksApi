package com.ghn1712.guiabolso.books.crawler;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.pmw.tinylog.Logger;

public class IsbnStrategyProvider {

    private IsbnStrategyProvider() {
    }

    private static final Map<String, IsbnRetrieverStrategy> strategyMap = loadStrategyMap();

    private static Map<String, IsbnRetrieverStrategy> loadStrategyMap() {
        HashMap<String, IsbnRetrieverStrategy> map = new HashMap<>();
        map.put("amazon", new AmazonStrategy());
        map.put("manning", new ManningStrategy());
        map.put("packtpub", new PacktpubStrategy());
        map.put("fundamental-kotlin", new FundamentalKotlinStrategy());
        map.put("kuramkitap", new KuramkitapStrategy());
        return map;
    }

    public static IsbnRetrieverStrategy getStrategy(String url) {
        try {
            String key = getKey(url);
            if (strategyMap.containsKey(key)) {
                return strategyMap.get(key);
            }
        }
        catch (MalformedURLException e) {
            Logger.warn(e.getMessage());
        }
        return new UnavailableStrategy();
    }

    private static String getKey(String stringUrl) throws MalformedURLException {
        String[] hostUrl = new URL(stringUrl).getHost().split("\\.");
        String key = hostUrl[0];
        if (key.equals("www")) {
            key = hostUrl[1];
        }
        return key;
    }
}
