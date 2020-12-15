//
//  SceneDelegate.swift
//  kolnapp
//
//  Created by 黃世維 on 2020/2/13.
//  Copyright © 2020 黃世維. All rights reserved.
//

import UIKit

class SceneDelegate: UIResponder, UIWindowSceneDelegate {

    var window: UIWindow?

    func scene(_ scene: UIScene, willConnectTo session: UISceneSession, options connectionOptions: UIScene.ConnectionOptions) {
        
        // Use this method to optionally configure and attach the UIWindow `window` to the provided UIWindowScene `scene`.
        // If using a storyboard, the `window` property will automatically be initialized and attached to the scene.
        // This delegate does not imply the connecting scene or session are new (see `application:configurationForConnectingSceneSession` instead).
        guard let _ = (scene as? UIWindowScene) else { return }

        // 用Xcode 11 build時打開
        if #available(iOS 13.0, *) {
            window?.overrideUserInterfaceStyle = .light
        }

        // Determine who sent the URL.
        if let urlContext = connectionOptions.urlContexts.first {

            let sendingAppID = urlContext.options.sourceApplication
            let url = urlContext.url
            print("source application = \(sendingAppID ?? "Unknown")")
            print("url = \(url)")

            // Process the URL similarly to the UIApplicationDelegate example.
        }
        
        if let userActivity = connectionOptions.userActivities.first(where: { $0.activityType == NSUserActivityTypeBrowsingWeb }), let incomingURL = userActivity.webpageURL, let components = NSURLComponents(url: incomingURL, resolvingAgainstBaseURL: true), let path = components.path {
            self.scene(scene, continue: userActivity)
        }

    }

    func sceneDidDisconnect(_ scene: UIScene) {
        // Called as the scene is being released by the system.
        // This occurs shortly after the scene enters the background, or when its session is discarded.
        // Release any resources associated with this scene that can be re-created the next time the scene connects.
        // The scene may re-connect later, as its session was not neccessarily discarded (see `application:didDiscardSceneSessions` instead).
    }

    func sceneDidBecomeActive(_ scene: UIScene) {
        // Called when the scene has moved from an inactive state to an active state.
        // Use this method to restart any tasks that were paused (or not yet started) when the scene was inactive.
    }

    func sceneWillResignActive(_ scene: UIScene) {
        // Called when the scene will move from an active state to an inactive state.
        // This may occur due to temporary interruptions (ex. an incoming phone call).
    }

    func sceneWillEnterForeground(_ scene: UIScene) {
        // Called as the scene transitions from the background to the foreground.
        // Use this method to undo the changes made on entering the background.
    }

    func sceneDidEnterBackground(_ scene: UIScene) {
        // Called as the scene transitions from the foreground to the background.
        // Use this method to save data, release shared resources, and store enough scene-specific state information
        // to restore the scene back to its current state.
    }

    // MARK:- UNIVERSAL LINK
    func scene(_ scene: UIScene, continue userActivity: NSUserActivity) {
        // Get URL components from the incoming user activity
        guard userActivity.activityType == NSUserActivityTypeBrowsingWeb,
            let incomingURL = userActivity.webpageURL,
            let components = NSURLComponents(url: incomingURL, resolvingAgainstBaseURL: true) else {
                UIPasteboard.general.string = "components is nil"
                print("components is nil")
                return
        }

        // Check for specific URL components that you need
        guard let path = components.path else {
            print("path is nil")
            return
        }
        
        let mainStoryboard = UIStoryboard(name: "Main", bundle: nil)
        let loginVC = mainStoryboard.instantiateViewController(withIdentifier: "LoginVC") as! LoginViewController
        self.window?.rootViewController = loginVC
        self.window?.makeKeyAndVisible()
        
        // 有參數才進行倒轉
        guard path.contains("/app/events/"), let params = components.queryItems else {
            return
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
    }
}
