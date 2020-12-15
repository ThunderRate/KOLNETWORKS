//
//  SubmitVC.swift
//  kolnapp
//
//  Created by 黃世維 on 2020/4/19.
//  Copyright © 2020 黃世維. All rights reserved.
//

import UIKit

extension UIViewController {
    @objc func showSimpleSubmitVCAlert() {
//        if let submitVC = UIStoryboard(name: "Wanted", bundle: nil).instantiateViewController(identifier: "SubmitVC") as? SubmitVC {

        if let submitVC = UIStoryboard(name: "Wanted", bundle: nil).instantiateViewController(withIdentifier: "SubmitVC") as? SubmitVC {
            submitVC.view.tag = 61087331
            
            addChild(submitVC)
            submitVC.view.translatesAutoresizingMaskIntoConstraints = false
            view.addSubview(submitVC.view)
            
            NSLayoutConstraint.activate([
                submitVC.view.leadingAnchor.constraint(equalTo: self.view.leadingAnchor),
                submitVC.view.trailingAnchor.constraint(equalTo: self.view.trailingAnchor),
                submitVC.view.topAnchor.constraint(equalTo: self.view.topAnchor),
                submitVC.view.bottomAnchor.constraint(equalTo: self.view.bottomAnchor)
            ])
            
            submitVC.didMove(toParent: self)
        }
    }
    
    @objc func stopSimpleSubmitVCAlert() {
         if let foundView = view.viewWithTag(61087331) {
            DispatchQueue.main.asyncAfter(wallDeadline: .now() + 3, execute: {
                foundView.removeFromSuperview()
            })
         }
    }
}

class SubmitVC: UIViewController {
    override var prefersStatusBarHidden: Bool {
        return true
    }

    @IBOutlet weak var alertTitle: UILabel!
    @IBOutlet weak var alertContent: UILabel!
    @IBOutlet weak var input: UITextField!
    @IBOutlet weak var commentButton: UIButton!

    @IBAction func cancel(_ sender: Any) {
        self.dismiss(animated: true, completion: nil)
        self.stopSimpleSubmitVCAlert()
    }
    @IBAction func sendComment(_ sender: Any) {
        guard let comment = input.text?.trimmingCharacters(in: .whitespacesAndNewlines), !comment.isEmpty, let uuid = project_uuid else { return }

        self.showLottie()

        var m: [String: Any] = Dictionary()
        m.updateValue(uuid, forKey: "project_uuid")
        m.updateValue(comment, forKey: "report")

        if !comment.contains("https") {
            let mm = "https://\(comment)"
            m.updateValue(mm, forKey: "report")
        }

        KolAPI.report(body: m, completion: { [weak self] response, _ in
            
            guard let response = response else {
                print("report fail, response nil")
                return
            }
            print("SubmitVC ok, \(response)")
            NotificationCenter.default.post(name: Notification.Name(rawValue: "NotifierName_sendSubmit"), object: nil)
            
            DispatchQueue.main.async {
                
                self?.dismiss(animated: true, completion: nil)
                self?.stopSimpleSubmitVCAlert()
                
                if let parentVC = self?.parent {
                    if let parentVC = parentVC as? WantedDetailVC {
                        // parentVC is someViewController
                        parentVC.disableButton()
                    }
                }
                
                self?.stopLottie()
            }
        })

    }

    var project_uuid: String?

    override func viewDidLoad() {
        super.viewDidLoad()
        project_uuid = MemoryCache.shared.sendSubmitTempData["project_uuid"] as? String
    }

}
