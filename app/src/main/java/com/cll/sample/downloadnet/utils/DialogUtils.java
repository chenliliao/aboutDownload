package com.cll.sample.downloadnet.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.cll.sample.downloadnet.R;

/**
 * Created by cll on 2018/1/23.
 */

public class DialogUtils {

    public static class Builder{

        public Builder(Context context){
            this.context = context;
        }
        private Context context;
        private int title;

        public Builder setTitle(final int title) {
            this.title = title;
            return this;
        }

    }

    public void create(){

    }
}
