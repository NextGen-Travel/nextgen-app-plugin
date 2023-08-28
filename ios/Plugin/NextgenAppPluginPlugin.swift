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

    // WXApi.registerApp 註冊 app id 以及 universalLink, 讓 Wechat 授權完後知道要開啟哪個 App 並回傳 auth code
    @objc func wxInit(_ call: CAPPluginCall) {
        let appId = call.getString("appId") ?? ""
        let universalLink = call.getString("universalLink") ?? ""
        WXApi.registerApp(appId, universalLink)
        call.resolve()
    }

    // Wechat default setting
    @objc func wxLogin(_ call: CAPPluginCall) {
        let request = SendAuthReq()
        request.scope = "snsapi_userinfo"
        request.state = "wechat"
        DispatchQueue.main.async {
            WXApi.sendAuthReq(request, viewController: UIViewController(), delegate: nil)
        }
        NextgenAppPluginPlugin.responseCall = call
    }
    
    // 自定義的 method, 用來接收 AppDelegate 回傳的 auth code
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
