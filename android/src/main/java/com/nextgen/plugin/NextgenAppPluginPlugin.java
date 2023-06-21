package com.nextgen.plugin;

import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.getcapacitor.BridgeActivity;

import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;


@CapacitorPlugin(name = "NextgenAppPlugin")
public class NextgenAppPluginPlugin extends Plugin {

    private IWXAPI api;
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
        String appId = call.getString("appId");
        api = WXAPIFactory.createWXAPI(this, appId, true);
        api.registerApp(appId);
        call.resolve();
    }

    @PluginMethod
    public void wxLogin(PluginCall call) {
        final SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "wechat";
        api.sendReq(req);
        saveCall(call);
    }

    @Override
    protected void handleOnActivityResult(int requestCode, int resultCode, Intent data) {
        super.handleOnActivityResult(requestCode, resultCode, data);
        PluginCall savedCall = getSavedCall();
        if (savedCall != null && requestCode == ConstantsAPI.COMMAND_SENDAUTH) {
            String code = data.getStringExtra("_wxapi_sendauth_resp_token");
            if (code != null) {
                // 在這裡處理登錄成功後的邏輯，例如獲取用戶資訊等
                savedCall.resolve(code);
            } else {
                // 登錄失敗
                savedCall.reject("LOGIN_FAILED");
            }
            releaseCall();
        }
    }
}
