package com.app.cabi;

import android.app.Application;

public class CabiApp extends Application {
    //App Instance
    private static CabiApp instance;

    synchronized public static CabiApp getInstance() {
        if (instance == null) {
            instance = new CabiApp();
        }
        return instance;
    }

}
