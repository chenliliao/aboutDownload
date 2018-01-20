package com.cll.sample.downloadnet;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ProgressBar;

import com.cll.sample.downloadnet.utils.MainMessages;
import com.cll.sample.downloadnet.utils.Poster;
import com.cll.sample.downloadnet.utils.Request;
import com.cll.sample.downloadnet.utils.ResultListenr;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by cll on 2018/1/20.
 */

public class DownloadTask implements Runnable {

    private Request request;
    private MainMessages message;
    private ProgressBar mProgressBar;
    Activity context;
    public DownloadTask(Request request, ResultListenr mResultListenr) {
        this.request = request;
        message = new MainMessages(mResultListenr);
    }

    @Override
    public void run() {
        HttpURLConnection connection = null;
        BufferedInputStream bis = null;
        FileOutputStream fos = null;
        int fileLength = 0;
        int progress = 0;
        try {
            URL url = new URL(request.getUrl());
            connection = (HttpURLConnection)url.openConnection();
            fileLength = connection.getContentLength();
            if (fileLength > 0){
                request.getProgressBar().setMax(fileLength);
                File file = new File(request.getSavePath());
                if (connection.getResponseCode() != HttpURLConnection.HTTP_OK){
                    message.setSuccess(MainMessages.IS_FAILED);
                    return;
                }
                request.getProgressBar().setMax(fileLength);
                byte[] buffer = new byte[1024 * 2];
                bis = new BufferedInputStream(connection.getInputStream());
                fos =new FileOutputStream(file);
                int len;

                while ((len = bis.read(buffer)) != -1){
                    fos.write(buffer,0,len);
                    progress += len;
                    message.setProgress(progress,fileLength);
                    if (progress == fileLength){
                        Log.w("tag","test Poster len  isSuccess= "+"finally");
                        message.setSuccess(MainMessages.IS_SUCCESS);
                        Poster.getInstance().removeCallbacks(message);
                    }
                    Poster.getInstance().post(message);
                }
                Log.w("tag","test Poster len= "+len);
            }
        } catch (MalformedURLException e) {
            message.setSuccess(MainMessages.IS_FAILED);
            e.printStackTrace();
        } catch (IOException e) {
            message.setSuccess(MainMessages.IS_FAILED);
            e.printStackTrace();
        } finally {
            if (connection != null)
                connection.disconnect();
            if (bis != null)
                bis = null;
            if (fos != null)
                fos = null;
            if (progress != fileLength){
                Poster.getInstance().post(message);
                Log.w("tag","test Poster len  isSuccess= "+"error");
            }
            Log.w("tag","test Poster len  isSuccess= "+"finally ss");
        }
    }

//    private static



}
