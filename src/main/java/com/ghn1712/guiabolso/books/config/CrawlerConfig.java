package com.ghn1712.guiabolso.books.config;

public class CrawlerConfig {

    private String url;
    private boolean cache;
    private boolean startupCache;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setCache(boolean cache) {
        this.cache = cache;
    }

    public boolean isCacheOn() {
        return cache;
    }

    public boolean isStartupCacheOn() {
        return startupCache;
    }

    public void setStartupCache(boolean startupCache) {
        this.startupCache = startupCache;
    }
}
