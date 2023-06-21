package com.nextgen.plugin;

import android.util.Log;

import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;

import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;



import android.app.Activity;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

class WXEntryActivity extends Activity implements IWXAPIEventHandler {

    private PluginCall callback;

    public void regCallback(PluginCall cb) {
        callback = cb;
    }

    @Override
    public void onReq(BaseReq baseReq) {}

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
}

@CapacitorPlugin(name = "NextgenAppPlugin")
public class NextgenAppPluginPlugin extends Plugin {

    private IWXAPI api;
    private WXEntryActivity activity = new WXEntryActivity();
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
        api = WXAPIFactory.createWXAPI(activity, appId, true);
        api.registerApp(appId);
        call.resolve();
    }

    @PluginMethod
    public void wxLogin(PluginCall call) {
        Log.i("Echo", "Wx Login");
        activity.regCallback(call);
        final SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "wechat";
        api.sendReq(req);
    }
}
