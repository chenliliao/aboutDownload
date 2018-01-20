package com.cll.sample.downloadnet.utils;

import com.cll.sample.downloadnet.DownloadTask;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by cll on 2018/1/20.
 */

public enum RequestExecutor {
    SINGLE;

    private ExecutorService sExecutorService ;

    RequestExecutor(){
        sExecutorService = Executors.newSingleThreadExecutor();
    }

    public void execute(Request request,ResultListenr mResultListenr){
        sExecutorService.execute(new DownloadTask(request,mResultListenr));
    }
}
