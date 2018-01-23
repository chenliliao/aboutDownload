package com.cll.sample.downloadnet;

import android.util.Log;

import com.cll.sample.downloadnet.utils.MainMessages;
import com.cll.sample.downloadnet.utils.Poster;
import com.cll.sample.downloadnet.utils.Request;
import com.cll.sample.downloadnet.utils.ResultListenr;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by cll on 2018/1/22.
 */

public class DownloadBlockTask implements Runnable {

    private Request request;
    private MainMessages message;
    private int threadCount;
    private ResultListenr mResultListenr;
    public DownloadBlockTask(Request request, int threadCount, ResultListenr mResultListenr){
        this.request = request;
        this.threadCount = threadCount;
        this.mResultListenr = mResultListenr;
        message = new MainMessages(mResultListenr);
    }
    @Override
    public void run() {
        HttpURLConnection connection = null;
        try {
            if (threadCount > 5){
                threadCount = 5;
            }
            URL url = new URL(request.getUrl());
            connection = (HttpURLConnection)url.openConnection();
            int fileLength = connection.getContentLength();
            request.getProgressBar().setMax(fileLength);
            File file = new File(request.getSavePath());
            int blockSize = fileLength % threadCount == 0? fileLength / threadCount : fileLength / threadCount + 1;
            Download[] download = new Download[threadCount];
            for (int i = 0; i < download.length; i++){
                download[i] = new Download(request,blockSize,fileLength,file,i+1,mResultListenr);
                download[i].start();
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (connection != null)
                connection.disconnect();
        }
    }

    private class Download extends Thread{

        private Request request;
        private int blockSize;
        private File file;
        private int progress;
        private  int fileLength;
        private int threadCount;
        public  Download(Request request, int blockSize, int fileLength, File file,int threadCount,ResultListenr mResultListenr){
            this.request = request;
            this.blockSize = blockSize;
            this.file = file;
            this.fileLength = fileLength;
            this.threadCount = threadCount;
        }

        @Override
        public void run() {
            super.run();
            URL url = null;
            HttpURLConnection connection = null;
            BufferedInputStream bis = null;
            RandomAccessFile raf = null;
            try {
                url = new URL(request.getUrl());
                connection = (HttpURLConnection)url.openConnection();
                connection.setAllowUserInteraction(true);
//                connection.setRequestMethod("GET");


                int startPos = blockSize * (threadCount - 1);
                int endPos = blockSize * threadCount - 1;
                connection.setRequestProperty("Range", "bytes=" + startPos + "-" + endPos);
                bis = new BufferedInputStream(connection.getInputStream());
                raf = new RandomAccessFile(file,"rwd");
                raf.seek(startPos);

                byte[] buffer = new byte[2048];
                int len= 0;

                while ((len = bis.read(buffer)) != -1){
                    raf.write(buffer,0,len);
                    progress += len;
                    message.setProgress(progress,fileLength);
                    if (progress == fileLength){
                        message.setSuccess(MainMessages.IS_SUCCESS);
                        Poster.getInstance().removeCallbacks(message);
                    }
                    Poster.getInstance().post(message);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if (connection != null)
                    connection.disconnect();
                if (bis != null)
                    bis = null;
                if (raf != null)
                    raf = null;
            }

        }
    }
}
