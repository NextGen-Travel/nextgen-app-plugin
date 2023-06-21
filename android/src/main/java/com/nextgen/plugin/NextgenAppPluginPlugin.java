package com.nextgen.plugin;

import android.content.Intent;
import android.util.Log;

import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;

import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.modelbase.BaseResp;

import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

@CapacitorPlugin(name = "NextgenAppPlugin")
public class NextgenAppPluginPlugin extends Plugin implements IWXAPIEventHandler {
    private IWXAPI api;
    private PluginCall callback;
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
        Intent intent = this.getActivity().getIntent();
        String appId = call.getString("appId");
        api = WXAPIFactory.createWXAPI(this.getContext(), appId, true);
        api.registerApp(appId);
        api.handleIntent(intent, this);
        call.resolve();
    }


    public void onReq(BaseReq baseReq) {}
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
    @PluginMethod
    public void wxLogin(PluginCall call) {
        Log.i("Echo", "Wx Login");
        final SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "wechat";
        callback = call;
        api.sendReq(req);
    }
}
