//
//  ForgetAlertVC.swift
//  kolnapp
//
//  Created by 黃世維 on 2020/5/8.
//  Copyright © 2020 黃世維. All rights reserved.
//

import UIKit

extension UIViewController {
    @objc func showSimpleAlert() {
        if let forgetAlertVC = UIStoryboard(name: "TaskBoard", bundle: nil).instantiateViewController(withIdentifier: "ForgetAlertVC") as? ForgetAlertVC {
            forgetAlertVC.view.tag = 667755331
            
            addChild(forgetAlertVC)
            forgetAlertVC.view.translatesAutoresizingMaskIntoConstraints = false
            view.addSubview(forgetAlertVC.view)
            
            NSLayoutConstraint.activate([
                forgetAlertVC.view.leadingAnchor.constraint(equalTo: self.view.leadingAnchor),
                forgetAlertVC.view.trailingAnchor.constraint(equalTo: self.view.trailingAnchor),
                forgetAlertVC.view.topAnchor.constraint(equalTo: self.view.topAnchor),
                forgetAlertVC.view.bottomAnchor.constraint(equalTo: self.view.bottomAnchor)
            ])
            
            forgetAlertVC.didMove(toParent: self)
        }
    }
    
    @objc func stopSimpleAlert() {
         if let foundView = view.viewWithTag(667755331) {
            DispatchQueue.main.asyncAfter(wallDeadline: .now() + 3, execute: {
                foundView.removeFromSuperview()
            })
         }
    }
}

class ForgetAlertVC: UIViewController {
    override var prefersStatusBarHidden: Bool {
        return true
    }

    @IBAction func cancel(_ sender: Any) {
        self.dismiss(animated: true, completion: nil)
    }

    @IBAction func sendComment(_ sender: Any) {
        KolAPI.token = nil
        self.showLottie()
        KolAPI.resetPassword(parameters: MemoryCache.shared.sendEmailForgetTempData, completion: { [weak self] response, _ in
            print(response)
            DispatchQueue.main.async {
//                self?.stopLottie()
                self?.dismiss(animated: true, completion: nil)
                self?.stopLottie()
                
            }
        })
    }

    override func viewDidLoad() {
        super.viewDidLoad()
    }

}
