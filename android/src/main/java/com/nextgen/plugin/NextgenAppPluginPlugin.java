package com.nextgen.plugin;

import android.os.Bundle;
import android.util.Log;
import android.app.Activity;
import android.app.Application;

import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;

import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;

import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

class WXEntryActivity extends Activity implements IWXAPIEventHandler {
    private String appId;
    private IWXAPI api;
    private PluginCall callback;
    public WXEntryActivity(String aid) {
        appId = aid;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        api = WXAPIFactory.createWXAPI(this, appId, true);
        api.registerApp(appId);
        api.handleIntent(getIntent(), this);
        Log.i("Echo", "Wx Inited");
    }

    @Override
    public void onReq(BaseReq baseReq) {
        finish();
    }

    @Override
    public void onResp(BaseResp baseResp) {
        // 在這裡處理微信回應事件
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
    }

    public void login(PluginCall call) {
        Log.i("Echo", "Wx Login");
        Log.i("Echo", api != null ? "A" : "N");
        if (api != null && api.isWXAppInstalled()) {
            final SendAuth.Req req = new SendAuth.Req();
            req.scope = "snsapi_userinfo";
            req.state = "wechat";
            callback = call;
            api.sendReq(req);
        } else {
            Log.i("Echo", "Wx no Installed");
        }
    }
}

@CapacitorPlugin(name = "NextgenAppPlugin")
public class NextgenAppPluginPlugin extends Plugin {
    private WXEntryActivity activity;
    private NextgenAppPlugin implementation = new NextgenAppPlugin();

    @PluginMethod
    public void echo(PluginCall call) {
        String value = call.getString("value");
        JSObject ret = new JSObject();
        ret.put("value", implementation.echo(value));
        call.resolve(ret);
    }

    @PluginMethod
    public void wxInit(PluginCall call) {
        Log.i("Echo", "Wx Init");
        String appId = call.getString("appId");
        activity = new WXEntryActivity(appId);
        call.resolve();
    }

    @PluginMethod
    public void wxLogin(PluginCall call) {
        Log.i("Echo", "Wx Login");
        activity.login(call);
    }
}
