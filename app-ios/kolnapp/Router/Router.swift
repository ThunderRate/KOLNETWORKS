//
//  Router.swift
//  kolnapp
//
//  Created by 黃世維 on 2020/3/16.
//  Copyright © 2020 黃世維. All rights reserved.
//

import Foundation
import UIKit

final class Router {

    enum ShowMode {
        case show
        case push
        case present
    }

//    private static var keyWindow: UIWindow? {
//        if let window = (UIApplication.shared.delegate as? SceneDelegate)?.window {
//            return window
//        } else {
//            let window = UIWindow.init(frame: UIScreen.main.bounds)
//            window.makeKeyAndVisible()
//            (UIApplication.shared.delegate as? SceneDelegate)?.window = window
//            return window
//        }
//    }

//    static func changeRootVC(to vc: UIViewController, animated: Bool = true, afterScreenUpdates: Bool = false) {
//        guard animated else {
//            keyWindow?.rootViewController = vc
//            return
//        }
//        DispatchQueue.main.async {
//            let snap = keyWindow?.rootViewController?.view.snapshotView(afterScreenUpdates: afterScreenUpdates)
//
//            if let currSnap = snap {
//                keyWindow?.addSubview(currSnap)
//            }
//
//            keyWindow?.rootViewController = vc
//            UIView.animate(withDuration: 0.25, animations: {
//                snap?.alpha = 0
//            }, completion: { _ in
//                snap?.removeFromSuperview()
//            })
//        }
//    }

//    static func show(_ vc: UIViewController, fromVC: UIViewController, hideNavBar: Bool = false, mode: ShowMode, animated: Bool = true) {
//        DispatchQueue.main.async {
//            vc.view.endEditing(true)
//
//            guard mode != .present else {
//                fromVC.present(vc, animated: animated, completion: nil)
//                return
//            }
//
//            guard let navController = (fromVC.navigationController ?? fromVC as? UINavigationController) ?? getTopNavController() else {
//                fromVC.present(vc, animated: animated, completion: nil)
//                return
//            }
//            navController.isNavigationBarHidden = hideNavBar
//
//            if mode == .show {
//                navController.show(vc, sender: nil)
//            } else {
//                navController.pushViewController(vc, animated: animated)
//            }
//        }
//    }

    static func showAlert(form vc: UIViewController, title: String, msg: String? = nil, cancelTitle: String = "OK", confirmTitle: String? = nil, completion: ((_ action: UIAlertAction) -> Void)? = nil) {
        DispatchQueue.main.async {
            let alert = UIAlertController(title: title, message: msg, preferredStyle: .alert)

            if let confirmTitle = confirmTitle {
                let cancel = UIAlertAction(title: cancelTitle, style: .default, handler: nil)
                alert.addAction(cancel)
                let confirm = UIAlertAction(title: confirmTitle, style: .default, handler: completion)
                alert.addAction(confirm)
            } else {
                let cancel = UIAlertAction(title: cancelTitle, style: .default, handler: completion)
                alert.addAction(cancel)
            }
            vc.present(alert, animated: true, completion: nil)
        }
    }

    static func dismiss(_ vc: UIViewController, animated: Bool = true, completion: (() -> Void)? = nil) {
        if let navigationController = vc.navigationController {
            navigationController.popViewController(animated: animated)
            completion?()
        } else {
            vc.dismiss(animated: animated, completion: completion)
        }
    }

    private static func getTopNavController() -> UINavigationController? {
        return (UIApplication.shared.keyWindow?.rootViewController as? NavigationController)
    }
}

extension Router {
    static func showMainVC() {
        // go2MainTab, 需要在SB拉VC to VC 的Segue線.
        DispatchQueue.main.async {
            let storyboard = UIStoryboard(name: "Main", bundle: nil)
            var vc: UITabBarController!
            if MemoryCache.shared.isManager {
                vc = storyboard.instantiateViewController(withIdentifier: "TabBarController2") as! UITabBarController
            } else {
                vc = storyboard.instantiateViewController(withIdentifier: "TabBarController") as! UITabBarController
            }
             
//            UIApplication.shared.windows.first?.rootViewController = vc
//            UIApplication.shared.windows.first?.makeKeyAndVisible()
            
            let appDelegate = UIApplication.shared.delegate as! AppDelegate
            appDelegate.window?.rootViewController = vc
            appDelegate.window?.makeKeyAndVisible()
        }
    }

}
