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
        Log.i("Echo", "Wx Init");
        String appId = call.getString("appId");
        api = WXAPIFactory.createWXAPI(getContext(), appId, false);
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
        call.resolve();
    }
}
