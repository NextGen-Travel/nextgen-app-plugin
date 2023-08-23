import Foundation
// TODO:
// import WechatOpenSDK

@objc public class NextgenAppPlugin: NSObject {
    @objc public func echo(_ value: String) -> String {
        print(value)
        return value
    }
}
