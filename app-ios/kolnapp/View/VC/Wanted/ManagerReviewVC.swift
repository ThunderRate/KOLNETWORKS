//
//  ReviewVC.swift
//  kolnapp
//
//  Created by 🍙 Dodo 🍙 on 2020/7/8.
//  Copyright © 2020 黃世維. All rights reserved.
//

import UIKit

extension UIViewController {
    @objc func showSimpleManagerReviewVCAlert() {
        if let managerReviewVC = UIStoryboard(name: "Wanted", bundle: nil).instantiateViewController(withIdentifier: "ManagerReviewVC") as? ManagerReviewVC {
            managerReviewVC.view.tag = 61087339
            
            addChild(managerReviewVC)
            managerReviewVC.view.translatesAutoresizingMaskIntoConstraints = false
            view.addSubview(managerReviewVC.view)
            
            NSLayoutConstraint.activate([
                managerReviewVC.view.leadingAnchor.constraint(equalTo: self.view.leadingAnchor),
                managerReviewVC.view.trailingAnchor.constraint(equalTo: self.view.trailingAnchor),
                managerReviewVC.view.topAnchor.constraint(equalTo: self.view.topAnchor),
                managerReviewVC.view.bottomAnchor.constraint(equalTo: self.view.bottomAnchor)
            ])
            
            managerReviewVC.didMove(toParent: self)
        }
    }
    
    @objc func stopSimplManagerReviewVCAlert() {
         if let foundView = view.viewWithTag(61087339) {
            DispatchQueue.main.asyncAfter(wallDeadline: .now(), execute: {
                foundView.removeFromSuperview()
            })
         }
    }
}

class ManagerReviewVC: UIViewController {
    override var prefersStatusBarHidden: Bool {
        return true
    }

    @IBOutlet weak var alertTitle: UILabel!
    @IBOutlet weak var alertContent: UILabel!
    @IBOutlet weak var rejectButton: UIButton!
    @IBOutlet weak var commentButton: UIButton!

    @IBAction func cancel(_ sender: Any) {
        self.dismiss(animated: true, completion: nil)
        self.stopSimplManagerReviewVCAlert()
    }
    
    @IBAction func sendReject(_ sender: Any) {
        var parameters: [String: Any] = Dictionary()
        parameters.updateValue(project_uuid!, forKey: "project_uuid")
        parameters.updateValue(kol_uuid!, forKey: "user_uuid")
        parameters.updateValue(0, forKey: "likes")
        parameters.updateValue(0, forKey: "comments")
        self.showLottie()
        KolAPI.rejectReviewUri(parameters: parameters, completion: { [weak self] reponse, error in
            print("rejectReviewUri \(reponse)")
            self?.stopLottie()
            self?.stopSimplManagerReviewVCAlert()
            
            guard let callback = MemoryCache.shared.sendReviewCallback else { return }
            callback()
        })
    }
    
    @IBAction func sendComment(_ sender: Any) {
        var parameters: [String: Any] = Dictionary()
        parameters.updateValue(project_uuid!, forKey: "project_uuid")
        parameters.updateValue(kol_uuid!, forKey: "user_uuid")
        parameters.updateValue(0, forKey: "likes")
        parameters.updateValue(0, forKey: "comments")
        self.showLottie()
        KolAPI.comfirmReviewUri(parameters: parameters, completion: { [weak self] reponse, error in
            print("comfirmReviewUri \(reponse)")
            self?.stopLottie()
            self?.stopSimplManagerReviewVCAlert()
            
            guard let callback = MemoryCache.shared.sendReviewCallback else { return }
            callback()
        })
    }

    var project_uuid: String?
    var kol_uuid: String?
    var reviewUri: String?

    override func viewDidLoad() {
        super.viewDidLoad()
        project_uuid = MemoryCache.shared.sendReviewTempData["project_uuid"] as? String
        kol_uuid = MemoryCache.shared.sendReviewTempData["kol_uuid"] as? String
        reviewUri = MemoryCache.shared.sendReviewTempData["report"] as? String
        
        guard let reviewUri = reviewUri else {
            return
        }
        alertContent.text = reviewUri
        
        let tab = UITapGestureRecognizer(target: self, action: #selector(openUrl2Browser))
        alertContent.isUserInteractionEnabled = true
        alertContent.addGestureRecognizer(tab)
    }

    @objc func openUrl2Browser(urlString: String) {
        if let url = URL(string: urlString) {
            UIApplication.shared.open(url)
        }
    }
}
