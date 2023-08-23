import Foundation
import Capacitor
import WechatOpenSDK

// TODO:
// import WXApi.h

/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitorjs.com/docs/plugins/ios
 */
@objc(NextgenAppPluginPlugin)
public class NextgenAppPluginPlugin: CAPPlugin, WXApiDelegate {
    private let implementation = NextgenAppPlugin()
    private var responseCall = CAPPluginCall()
    @objc func echo(_ call: CAPPluginCall) {
        let value = call.getString("value") ?? ""
        call.resolve([
            "value": implementation.echo(value)
        ])
    }

    @objc func wxInit(_ call: CAPPluginCall) {
        let appId = call.getString("appId") ?? ""
        WXApi.registerApp(appId, universalLink: "https://e-drugsearch/")
        call.resolve()
    }

    @objc func wxLogin(_ call: CAPPluginCall) {
        let request = SendAuthReq()
        request.scope = "snsapi_userinfo"
        request.state = "wechat"
        WXApi.sendAuthReq(request, viewController: UIViewController(), delegate: self)
        responseCall = call
    }
    
    public func onResp(_ resp: BaseResp) {
        responseCall.resolve([
            "code":"456"
        ])
        print("response \(resp)")
    }
}
