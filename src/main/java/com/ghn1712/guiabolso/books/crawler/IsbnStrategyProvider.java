package com.ghn1712.guiabolso.books.crawler;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.pmw.tinylog.Logger;

import com.ghn1712.guiabolso.books.injection.modules.InjectorProvider;
import com.google.inject.Key;
import com.google.inject.name.Names;

public class IsbnStrategyProvider {

    private IsbnStrategyProvider() {
    }

    private static final Map<String, IsbnRetrieverStrategy> strategyMap = loadStrategyMap();

    private static Map<String, IsbnRetrieverStrategy> loadStrategyMap() {
        HashMap<String, IsbnRetrieverStrategy> map = new HashMap<>();
        map.put("amazon", InjectorProvider.getInjector()
                .getInstance(Key.get(IsbnRetrieverStrategy.class, Names.named("amazon"))));
        map.put("manning", InjectorProvider.getInjector()
                .getInstance(Key.get(IsbnRetrieverStrategy.class, Names.named("manning"))));
        map.put("packtpub", InjectorProvider.getInjector()
                .getInstance(Key.get(IsbnRetrieverStrategy.class, Names.named("packtpub"))));
        map.put("fundamental-kotlin", InjectorProvider.getInjector()
                .getInstance(Key.get(IsbnRetrieverStrategy.class, Names.named("fundamental-kotlin"))));
        map.put("kuramkitap", InjectorProvider.getInjector()
                .getInstance(Key.get(IsbnRetrieverStrategy.class, Names.named("kuramkitap"))));
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
        return InjectorProvider.getInjector()
                .getInstance(Key.get(IsbnRetrieverStrategy.class, Names.named("unavailable")));
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
