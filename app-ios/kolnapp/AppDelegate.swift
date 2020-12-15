//
//  AppDelegate.swift
//  kolnapp
//
//  Created by 黃世維 on 2020/2/13.
//  Copyright © 2020 黃世維. All rights reserved.
//

import UIKit
import UserNotifications
import IQKeyboardManagerSwift
import AppCenter
import AppCenterAnalytics
import AppCenterCrashes
import Firebase

@UIApplicationMain
class AppDelegate: UIResponder, UIApplicationDelegate {
    
    var window: UIWindow?

    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?) -> Bool {
        
        // 用Xcode 11 build時打開
       if #available(iOS 13.0, *) {
           window?.overrideUserInterfaceStyle = .light
       }

        registerNotifications()
        registerRemoteNotification()

        IQKeyboardManager.shared.enable = true
        
        MSAppCenter.start("be64bd33-1172-488c-8f81-b04ade158acf", withServices:[
          MSAnalytics.self,
          MSCrashes.self
        ])
        
        // Use Firebase library to configure APIs
        FirebaseApp.configure()
        
        //Launched from push notification
//        guard let options = launchOptions,
//            let remoteNotif = options[UIApplication.LaunchOptionsKey.remoteNotification] as? [String: Any]
//            else {
//                return true
//            }
//
//        let aps = remoteNotif["aps"] as? [String: Any]
//        NSLog("\n Custom: \(String(describing: aps))")

        return true
    }
    
    func application(_ application: UIApplication, continue userActivity: NSUserActivity, restorationHandler: @escaping ([UIUserActivityRestoring]?) -> Void) -> Bool {
        
        // Get URL components from the incoming user activity
        guard userActivity.activityType == NSUserActivityTypeBrowsingWeb,
            let incomingURL = userActivity.webpageURL,
            let components = NSURLComponents(url: incomingURL, resolvingAgainstBaseURL: true) else {
                UIPasteboard.general.string = "components is nil"
                print("components is nil")
                return true
        }

        // Check for specific URL components that you need
        guard let path = components.path else {
            print("path is nil")
            return true
        }
        
        let mainStoryboard = UIStoryboard(name: "Main", bundle: nil)
        let loginVC = mainStoryboard.instantiateViewController(withIdentifier: "LoginVC") as! LoginViewController
        self.window?.rootViewController = loginVC
        self.window?.makeKeyAndVisible()
        
        // 有參數才進行倒轉
        guard path.contains("/app/events/"), let params = components.queryItems else {
            return true
        }
        
        if let code = params.first(where: { $0.name == "code" } )?.value,
            let register = params.first(where: { $0.name == "register" })?.value {
            if register == "register" {
                // 複製邀請碼.
                UserDefaults.standard.set(code, forKey: "kKolinvitationCode")
                UserDefaults.standard.synchronize()
                
                DispatchQueue.main.async {
                    // GOTO 註冊畫面.
                    let storyboard = UIStoryboard(name: "Register", bundle: nil)
                    let vc = storyboard.instantiateViewController(withIdentifier: "RegisterVC") as! RegisterVC
                    loginVC.present(vc, animated: true, completion: nil)
                }
            }
            
        } else {
            print("Either invited code or register missing")
        }
        
        return true
    }

    // MARK: - RomoteNotification
    func application(_ application: UIApplication, didFailToRegisterForRemoteNotificationsWithError error: Error) {
        print("[RomoteNotification] error -> \(error)")
    }

    func application(_ application: UIApplication, didRegisterForRemoteNotificationsWithDeviceToken deviceToken: Data) {
        let deviceTokenString = deviceToken.map { String(format: "%02hhx", $0) }.joined()
        print("[RomoteNotification] deviceTokenString -> \(deviceTokenString)")

        UserDefaults.standard.set(deviceTokenString, forKey: "deviceToken")
        UserDefaults.standard.synchronize()
    }

    // url scheme
//    func application(_ application: UIApplication, open url: URL, options: [UIApplication.OpenURLOptionsKey: Any] = [:] ) -> Bool {
//
//        // Determine who sent the URL.
//        let sendingAppID = options[.sourceApplication]
//        print("source application = \(sendingAppID ?? "Unknown")")
//
//        // Process the URL.
//        guard let components = NSURLComponents(url: url, resolvingAgainstBaseURL: true),
//            let albumPath = components.path,
//            let params = components.queryItems else {
//                print("Invalid URL or album path missing")
//                return false
//        }
//
//        if let photoIndex = params.first(where: { $0.name == "index" })?.value {
//            print("albumPath = \(albumPath)")
//            print("photoIndex = \(photoIndex)")
//            return true
//        } else {
//            print("Photo index missing")
//            return false
//        }
//    }
}

// MARK: - UNUserNotificationCenterDelegate
extension AppDelegate: UNUserNotificationCenterDelegate {
    func userNotificationCenter(_ center: UNUserNotificationCenter, willPresent notification: UNNotification, withCompletionHandler completionHandler: @escaping (UNNotificationPresentationOptions) -> Void) {
        completionHandler([.alert, .badge, .sound])
    }

    func userNotificationCenter(_ center: UNUserNotificationCenter, didReceive response: UNNotificationResponse, withCompletionHandler completionHandler: @escaping () -> Void) {
        completionHandler()
        
        let content = response.notification.request.content.userInfo
        print("\(content)")
        
        if let kolNotify = content["kolNotify"] as? Int, let project_uuid = content["project_uuid"] as? String {
            print("kolNotify value is: \(kolNotify)")
            print("project_uuid value is: \(project_uuid)")

            MemoryCache.shared.kolNotify = kolNotify
            MemoryCache.shared.kolNotifyProjectUUID = project_uuid
            
            NotificationCenter.default.post(name: Notification.Name("ClickNotification"), object: nil)
            

//            if let kolpassport = UserDefaults.standard.value(forKey: "kolpassport") as? String {
//                KolAPI.token = kolpassport
//                Router.showMainVC()
//            } else {
//                let mainStoryboard = UIStoryboard(name: "Main", bundle: nil)
//                let loginVC = mainStoryboard.instantiateViewController(withIdentifier: "LoginVC") as! LoginViewController
////                let sceneDelegate = UIApplication.shared.connectedScenes
////                    .first!.delegate as! SceneDelegate
////                sceneDelegate.window!.rootViewController = loginVC
//                let appDelegate = UIApplication.shared.delegate as! AppDelegate
//                appDelegate.window?.rootViewController = loginVC
//                appDelegate.window?.makeKeyAndVisible()
//            }
            
        }
    }

    func registerNotifications() {
        NotificationCenter.default.addObserver(forName: Notification.Name("deviceTokenDidGet"), object: nil, queue: .main) { notification in
            print(notification)
            NotificationCenter.default.removeObserver(self, name: Notification.Name("deviceTokenDidGet"), object: nil)
        }
    }

    func registerRemoteNotification() {
        let center = UNUserNotificationCenter.current()
        center.delegate = self
        center.requestAuthorization(options: [.sound, .alert, .badge]) { (_, error) in
            if error == nil {
                DispatchQueue.main.async {
                    UIApplication.shared.registerForRemoteNotifications()
                }
            }
        }
    }
}
