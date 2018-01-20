package com.cll.sample.downloadnet.utils;

/**
 * Created by cll on 2018/1/20.
 */

public interface ResultListenr {
    void isSuccess();
    void isFailed();
    void setProgressBar(int progress,int max);
}
