import Foundation
import Capacitor
// TODO:
// import WXApi.h

/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitorjs.com/docs/plugins/ios
 */
@objc(NextgenAppPluginPlugin)
public class NextgenAppPluginPlugin: CAPPlugin {
    private let implementation = NextgenAppPlugin()

    @objc func echo(_ call: CAPPluginCall) {
        let value = call.getString("value") ?? ""
        call.resolve([
            "value": implementation.echo(value)
        ])
    }

    @objc func wxInit(_ call: CAPPluginCall) {
        let value = call.getString("appId") ?? ""
        print(value)
        call.resolve()
    }

    @objc func wxLogin(_ call: CAPPluginCall) {
        call.resolve([
            "code": "123"
        ])
    }
}
