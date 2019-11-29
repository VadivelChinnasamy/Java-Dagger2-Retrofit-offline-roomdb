package com.javadagger2;

import android.app.Application;
import android.content.Context;

import com.javadagger2.dagger2.DaggerNetworkComponent;
import com.javadagger2.dagger2.NetworkComponent;
import com.javadagger2.dagger2.NetworkModule;

public class MyApplication extends Application {
    NetworkComponent mComponent;
    Context mContext;
    @Override
    public void onCreate() {
        super.onCreate();
        mContext=MyApplication.this;
        mComponent= DaggerNetworkComponent.builder().networkModule(new NetworkModule(mContext)).build();
    }
    public NetworkComponent getComponent(){
        return mComponent;
    }
}
