package com.cll.sample.downloadnet.utils;

import android.os.Handler;
import android.os.Looper;

/**
 * Created by cll on 2018/1/20.
 */

public class Poster extends Handler{

    private static Poster poster;

    public static Poster getInstance(){
        if (poster == null){
            poster = new Poster();
        }
        return poster;
    }

    public Poster(){
        super(Looper.getMainLooper());
    }


}
