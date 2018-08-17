package com.ghn1712.guiabolso.books.config;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import com.google.gson.Gson;

public class ConfigProviderImpl {

    private static final String CONFIG_FILE_NAME = "config/config.json";
    private final Config config;

    public ConfigProviderImpl() throws IOException {
        config = getConfigFile();
    }

    private Config getConfigFile() throws IOException {
        File file = new File(CONFIG_FILE_NAME);
        return new Gson().fromJson(new String(Files.readAllBytes(file.toPath())), Config.class);
    }

    public Config getConfig() {
        return config;
    }
}
