package com.nextgen.plugin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import com.getcapacitor.JSObject;
import com.getcapacitor.PluginCall;

import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;


public class WXEntryActivity extends Activity implements IWXAPIEventHandler {
    static IWXAPI api;
    static PluginCall callback;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("Echo", "Wx On Create");
        var APP_ID = "1234";
        api = WXAPIFactory.createWXAPI(this, APP_ID, false);
        try {
            Intent intent = getIntent();
            api.handleIntent(intent, this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
        finish();
    }

    @Override
    public void onResp(BaseResp baseResp) {
        Log.i("Echo", "Wx Callback");
        Log.i("Echo", baseResp.toString());
        if (baseResp instanceof SendAuth.Resp) {
            SendAuth.Resp authResp = (SendAuth.Resp) baseResp;
            String code = authResp.code;
            if (callback != null) {
                if (code != null) {
                    JSObject ret = new JSObject();
                    // 在這裡處理登錄成功後的邏輯，例如獲取用戶資訊等
                    ret.put("token", code);
                    callback.resolve(ret);
                } else {
                    // 登錄失敗
                    callback.reject("LOGIN_FAILED");
                }
                callback = null;
            }
        }
        finish();
    }
}
