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

@CapacitorPlugin(name = "NextgenAppPlugin")
public class NextgenAppPluginPlugin extends Plugin {
    private NextgenAppPlugin implementation = new NextgenAppPlugin();
    static String WX_APP_ID = "";

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
        WX_APP_ID = call.getString("appId");
        WXEntryActivity.api = WXAPIFactory.createWXAPI(getContext(), WX_APP_ID, false);
        WXEntryActivity.api.registerApp(WX_APP_ID);
        call.resolve();
    }

    @PluginMethod
    public void wxLogin(PluginCall call) {
        Log.i("Echo", "Wx Login");
        final SendAuth.Req req = new SendAuth.Req();
        WXEntryActivity.callback = call;
        req.scope = "snsapi_userinfo";
        req.state = "wechat";
        WXEntryActivity.api.sendReq(req);
    }
}
