package com.circularuins.wstest;

import android.os.AsyncTask;

/**
 * Created by wake on 2014/12/24.
 */
public class ChatTask extends AsyncTask<String, Void, String> {

    @Override
    protected String doInBackground(String... params) {
        String s = params[0] + "\n\n";
        return s;
    }
}
