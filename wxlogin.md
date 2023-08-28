# Ios 微信登入相關文件

[官方文件1](https://developers.weixin.qq.com/doc/oplatform/Mobile_App/WeChat_Login/Development_Guide.html)
[官方文件2](https://developers.weixin.qq.com/doc/oplatform/Mobile_App/Access_Guide/iOS.html)

## Capacitor Plugin


1. 要在 NextgenAppPlugin.podspec 加入 'WechatOpenSDK-Swift'

```ruby
require 'json'

package = JSON.parse(File.read(File.join(__dir__, 'package.json')))

Pod::Spec.new do |s|
  s.name = 'NextgenAppPlugin'
  s.version = package['version']
  s.summary = package['description']
  s.license = package['license']
  s.homepage = package['repository']['url']
  s.author = package['author']
  s.source = { :git => package['repository']['url'], :tag => s.version.to_s }
  s.source_files = 'ios/Plugin/**/*.{swift,h,m,c,cc,mm,cpp}'
  s.ios.deployment_target  = '13.0'
  s.dependency 'Capacitor'
  # 這裡
  s.dependency 'WechatOpenSDK-Swift'
  s.static_framework = true
  s.swift_version = '5.1'
end
```

2. 在 NextgenAppPluginPlugin.swift 中加入 WechatOpenSDK，可直接看程式碼架構

```swift
// ...
import WechatOpenSDK

public class NextgenAppPluginPlugin: CAPPlugin {
    // WXApi.registerApp 註冊 app id 以及 universalLink, 讓 Wechat 授權完後知道要開啟哪個 App 並回傳 auth code
    @objc func wxInit(_ call: CAPPluginCall) {
        let appId = call.getString("appId") ?? ""
        let universalLink = call.getString("universalLink") ?? ""
        WXApi.registerApp(appId, universalLink: universalLink)
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
        // 這裡要看如何回傳目標值
        // call.resolve()
    }
}
```

## 主專案

1. 需要在 AppDelegate.swift 中加入以下部分

```swift
import WechatOpenSDK
import NextgenAppPlugin

// 加入 WXApiDelegate
class AppDelegate: UIResponder, UIApplicationDelegate, WXApiDelegate {
    // 註冊微信登入啟用方案
    func application(_ app: UIApplication, open url: URL, options: [UIApplication.OpenURLOptionsKey: Any] = [:]) -> Bool {
        // ...
        let handleUrl = url.absoluteString
        if let handel = URL(string: handleUrl) {
            return WXApi.handleOpen(handel, delegate: self)
        }
        // ...
    }
    // 註冊微信登入後觸發位置
    func onResp(_ resp: BaseResp) {
        NextgenAppPluginPlugin.onResponse(resp)
    }
}

```

2. plist.info 中加入以下部分

```xml
<!-- ... -->
<key>LSApplicationQueriesSchemes</key>
<array>
    <string>weixin</string>
    <string>weixinULAPI</string>
    <string>weixinURLParamsAPI</string>
</array>
<!-- ... -->
<key>CFBundleURLTypes</key>
<array>
    <dict>
        <key>CFBundleTypeRole</key>
        <string>Editor</string>
        <key>CFBundleURLName</key>
        <string>weixin</string>
        <key>CFBundleURLSchemes</key>
        <array>
            <string>[wx app id]</string>
        </array>
    </dict>
</array>
```

## 如何使用？

```js
import { NextgenAppPlugin } from 'nextgen-app-plugin'
async function main() {
    await NextgenAppPlugin.wxInit({
        appId: '1234',
        universalLink: '4567'
    })
    const response = await NextgenAppPlugin.wxLogin()
    console.log(response.code)
}
main()
```
