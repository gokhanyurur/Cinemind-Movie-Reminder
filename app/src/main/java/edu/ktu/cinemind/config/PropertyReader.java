package edu.ktu.cinemind.config;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyReader {

    public static String getProperty(String key, Context context) {
        try {
            Properties properties = new Properties();
            AssetManager assetManager = context.getAssets();
            InputStream inputStream = assetManager.open("application.properties");
            properties.load(inputStream);
            return properties.getProperty(key);
        }catch (IOException e){
            e.fillInStackTrace();
        }
        return null;
    }
}
