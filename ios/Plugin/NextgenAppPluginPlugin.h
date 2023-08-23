#import <UIKit/UIKit.h>
#import <WXApi.h>
//! Project version number for Plugin.
FOUNDATION_EXPORT double PluginVersionNumber;

//! Project version string for Plugin.
FOUNDATION_EXPORT const unsigned char PluginVersionString[];

// In this header, you should import all the public headers of your framework using statements like #import <Plugin/PublicHeader.h>

@interface AppDelegate : UIResponder<UIApplicationDelegate, WXApiDelegate>

@property (strong, nonatomic) UIWindow *window;

@end
