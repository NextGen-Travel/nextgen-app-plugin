import Foundation

@objc public class NextgenAppPlugin: NSObject {
    @objc public func echo(_ value: String) -> String {
        print(value)
        return value
    }

    @objc func wxInit() -> String {
        return "objc"
    }
    
    @objc func wxLogin() -> String {
        return "objc"
    }
}
