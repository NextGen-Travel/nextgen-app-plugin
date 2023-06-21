package com.nextgen.plugin;

import android.util.Log;

public class NextgenAppPlugin {

    public String echo(String value) {
        Log.i("Echo", value);
        return value;
    }
}
