package com.nextgen.plugin;

import android.util.Log;
import android.content.Intent;
import android.content.Context;

import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;

import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

@CapacitorPlugin(name = "NextgenAppPlugin")
public class NextgenAppPluginPlugin extends Plugin {

    private IWXAPI api;
    private NextgenAppPlugin implementation = new NextgenAppPlugin();
    private String CallbackId = "";

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
        Context context = bridge.getContext();
        String appId = call.getString("appId");
        api = WXAPIFactory.createWXAPI(context, appId, true);
        api.registerApp(appId);
        call.resolve();
    }

    @PluginMethod
    public void wxLogin(PluginCall call) {
        Log.i("Echo", "Wx Login");
        final SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "wechat";
        api.sendReq(req);
        CallbackId = call.getCallbackId();
        bridge.saveCall(call);
    }

    @Override
    protected void handleOnActivityResult(int requestCode, int resultCode, Intent data) {
        super.handleOnActivityResult(requestCode, resultCode, data);
        PluginCall savedCall = bridge.getSavedCall(CallbackId);
        Log.i("Echo", String.valueOf(requestCode));
        if (savedCall != null) {
            String code = data.getStringExtra("_wxapi_sendauth_resp_token");
            if (code != null) {
                JSObject ret = new JSObject();
                // 在這裡處理登錄成功後的邏輯，例如獲取用戶資訊等
                ret.put("token", code);
                savedCall.resolve(ret);
            } else {
                // 登錄失敗
                savedCall.reject("LOGIN_FAILED");
            }
            bridge.releaseCall(CallbackId);
        }
    }
}
