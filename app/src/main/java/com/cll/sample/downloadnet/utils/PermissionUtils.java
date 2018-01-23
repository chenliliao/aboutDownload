package com.cll.sample.downloadnet.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;

/**
 * Created by cll on 2018/1/23.
 */

public class PermissionUtils {

    public static String[]PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private static int REQUEST_CODE = 1;
    public static void applyPermissionUpSix(Activity activity){
//        int permission = ActivityCompat.checkSelfPermission(activity,Manifest.permission.WRITE_EXTERNAL_STORAGE);
        for (String permission : PERMISSIONS_STORAGE){
            int result = ActivityCompat.checkSelfPermission(activity,permission);
            if (result != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(activity,PERMISSIONS_STORAGE,REQUEST_CODE);
                REQUEST_CODE ++;
            }
        }
    }
}
