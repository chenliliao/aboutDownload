package com.cll.sample.downloadnet.utils;

import android.util.Log;

/**
 * Created by cll on 2018/1/20.
 */

public class MainMessages implements Runnable{


    public static int IS_SUCCESS = 1;
    public static int IS_FAILED = 2;
    private int isSuccess = 0;
    private int progress;
    private int max;
    private ResultListenr mResultListenr;

    public MainMessages(ResultListenr mResultListenr){
        this.mResultListenr = mResultListenr;
    }

    public void setSuccess(int success) {
        isSuccess = success;
    }

    public void setProgress(int progress,int max) {
        this.progress = progress;
        this.max = max;
    }

    @Override
    public void run() {
        if (mResultListenr != null){
            mResultListenr.setProgressBar(progress,max);
                if (isSuccess == IS_SUCCESS){
                    mResultListenr.isSuccess();
                }else if (isSuccess == IS_FAILED){
                    mResultListenr.isFailed();
                }
        }
    }
}
