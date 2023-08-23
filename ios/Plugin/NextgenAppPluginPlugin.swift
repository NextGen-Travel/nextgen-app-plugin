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
    
    func wxInit(_ call: CAPPluginCall) {
        call.resolve([
            "value": "123"
        ])
    }
    
    func wxLogin(_ call: CAPPluginCall) {
        let value = call.getString("value") ?? ""
        call.resolve([
            "value": "123"
        ])
    }
}
