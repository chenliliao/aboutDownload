package com.cll.sample.downloadnet.utils;

import com.cll.sample.downloadnet.DownloadBlockTask;
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

    /**
     *
     * @param request
     * @param threadCount  if threadCount > 5, threadCount = 5.
     * @param mResultListenr
     */
    public void execute(Request request,int threadCount,ResultListenr mResultListenr){
        sExecutorService.execute(new DownloadBlockTask(request,threadCount,mResultListenr));
    }
}
