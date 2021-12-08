package ru.fazziclay.sharefile;

import android.content.Context;

public class App {
    static App instance = null;

    Context androidContext = null;
    Server myServer = null;

    public static App getInstance() {
        return instance;
    }

    public static boolean isInstanceAvailable() {
        return (getInstance() != null);
    }

    public Context getAndroidContext() {
        return androidContext;
    }

    public App(Context context) {
        if (isInstanceAvailable()) return;
        instance = this;
        androidContext = context;

        myServer = new Server(Throwable::printStackTrace);
        myServer.start();

    }
}
