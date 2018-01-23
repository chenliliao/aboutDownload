package com.cll.sample.downloadnet;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.cll.sample.downloadnet.layout.ProgressBarLayout;
import com.cll.sample.downloadnet.utils.DialogUtils;
import com.cll.sample.downloadnet.utils.NetStatusUtils;
import com.cll.sample.downloadnet.utils.Request;
import com.cll.sample.downloadnet.utils.RequestExecutor;
import com.cll.sample.downloadnet.utils.ResultListenr;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {


    private static final String TAG = MainActivity.class.getSimpleName();
    private Button singleButton;
    private Button multButton;
    private ImageView imageView;
    private ProgressBarLayout progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        singleButton = (Button) findViewById(R.id.sigle_button);
        multButton = (Button) findViewById(R.id.mult_thread_button);


        progressBar = (ProgressBarLayout)findViewById(R.id.progressbar);
        progressBar.setTest1("0","0");

        initListener();
    }
    private void initListener(){
        singleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isDownloading){
                    return;
                }
                if (NetStatusUtils.isWifiNet(MainActivity.this)){
                    download();
                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this)
                            .setPositiveButton("开", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent();
                                    intent.setAction("android.net.wifi.PICK_WIFI_NETWORK");
                                    startActivity(intent);
                                }
                            });
                    builder.setView(LayoutInflater.from(MainActivity.this).inflate(R.layout.dialog_net_status_hint,null));
                    builder.show();
                }

            }
        });

        multButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                download2();
            }
        });
    }

    int a;
    private void download2(){
        isDownloading = true;
        String path = dir +"阿里星球.apk";
        boolean s = createFile(path);
        Request request = new Request(downloadUrl,path,progressBar);
        mProgress = 0;
        RequestExecutor.SINGLE.execute(request, new ResultListenr() {
            @Override
            public void isSuccess() {
                isDownloading = false;
                Toast.makeText(MainActivity.this, "下载成功!  ", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void isFailed() {
                isDownloading = false;
                Toast.makeText(MainActivity.this, "下载失败!  ", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void setProgressBar(int progress, int max) {
                mProgress = progress;
                mMax = max;
                progressBar.setProgress(mProgress);
                progressBar.setTest1(convertFormat((float)progress / 1024),convertFormat((float)max / 1024));
            }
        });
    }



    private String dir = Environment.getExternalStorageDirectory() + File.separator + "test" + File.separator + "download" + File.separator ;
    private String name = "name";
//    private String downloadUrl = "http://b.hiphotos.baidu.com/image/pic/item/58ee3d6d55fbb2fb4a944f8b444a20a44723dcef.jpg";
    private String downloadUrl = "http://wap.sogou.com/app/redir.jsp?dt=1&appdown=1&u=0Gd8piB6091kOtbAhntD_tgwzTwbQmCtzD0uA8MR4LfZGb7Qywh5rAIvm8kCpFczl1AKy02qcV-Q9HdJulsCEiMYno67jjer_qi9ZoYPeU3c0LLlYVpP8NIoPti-_vcRCoFutw0b2ST9n2EW8SC3pIZ0qnVqJlkXkqb6adUqkRX4gaIhMrqjeUztMxpRm3JmWtFLUzNiIjUt11EdkKO1gMuqxplQrsbk&docid=9190009583366084496&sourceid=7780666504990664623&w=1906&stamp=20180120";
    private int mProgress;
    private int mMax;
    private boolean isDownloading = false;
    private void download(){
        isDownloading = true;
//        String path = dir +System.currentTimeMillis()+".apk";
        String path = dir +"阿里星球.apk";
        createFile(path);
        Request request = new Request(downloadUrl,path,progressBar);
        mProgress = 0;
        RequestExecutor.SINGLE.execute(request, new ResultListenr() {
            @Override
            public void isSuccess() {
                isDownloading = false;
                Toast.makeText(MainActivity.this, "下载成功!  "+mProgress + "/" + mMax, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void isFailed() {
                isDownloading = false;
                Toast.makeText(MainActivity.this, "下载失败!  "+mProgress + "/" + mMax, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void setProgressBar(int progress, int max) {
                mProgress = progress;
                mMax = max;
                progressBar.setProgress(mProgress);
                progressBar.setTest1(convertFormat((float)progress / 1024),convertFormat((float)max / 1024));
            }
        });
    }

    private String convertFormat(Float number){
        String str = new DecimalFormat("0.00").format((float)number / 1024 );
        return str;
    }

    private boolean createFile(final String path){
        File file = new File(path);
        if (!file.getParentFile().exists()){
            file.getParentFile().mkdirs();
        }
        if (!file.exists()){
            try {
                return file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }
}
