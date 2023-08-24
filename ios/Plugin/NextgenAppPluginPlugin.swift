import Foundation
import Capacitor
import WechatOpenSDK

/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitorjs.com/docs/plugins/ios
 */
@objc(NextgenAppPluginPlugin)
public class NextgenAppPluginPlugin: CAPPlugin {
    private let implementation = NextgenAppPlugin()
    static var responseCall = CAPPluginCall()
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
        DispatchQueue.main.async {
            WXApi.sendAuthReq(request, viewController: UIViewController(), delegate: nil)
        }
        NextgenAppPluginPlugin.responseCall = call
    }
    
    static public func onResponse(_ resp: BaseResp) {
        print("response \(resp)")
        if let response = resp as? SendAuthResp, let code = response.code {
            responseCall.resolve([
                "code": code
            ])
        } else {
            responseCall.resolve([
                "code": ""
            ])
        }
    }
}
