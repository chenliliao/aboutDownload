package com.cll.sample.downloadnet.utils;

import android.widget.ProgressBar;

/**
 * Created by cll on 2018/1/20.
 */

public class Request {

    private String url;
    private String savePath;
    private ProgressBar progressBar;

    public Request(String url, String savePath, ProgressBar progressBar) {
        this.url = url;
        this.savePath = savePath;
        this.progressBar = progressBar;
    }

    public String getUrl() {
        return url;
    }
    public String getSavePath() {
        return savePath;
    }

    public ProgressBar getProgressBar() {
        return progressBar;
    }

}
