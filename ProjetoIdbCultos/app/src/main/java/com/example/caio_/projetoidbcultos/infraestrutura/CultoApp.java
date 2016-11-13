package com.example.caio_.projetoidbcultos.infraestrutura;

import android.app.Application;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by caio- on 16/07/2016.
 */
public class CultoApp extends Application {

    EventBus eventBus;

    @Override
    public void onCreate() {
        super.onCreate();

        eventBus = new EventBus();
    }

    public EventBus getEventBus() {
        return eventBus;
    }
}
