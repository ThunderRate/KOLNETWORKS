//
//  LottieVC.swift
//  kolnapp
//
//  Created by 黃世維 on 2020/5/6.
//  Copyright © 2020 黃世維. All rights reserved.
//

import UIKit
import Lottie

class LottieVC: UIViewController {

    @IBOutlet weak var animateView: AnimationView!

    override func viewDidLoad() {
        super.viewDidLoad()

        NotificationCenter.default.addObserver(
            self,
            selector: #selector(dismissLogo),
            name: NSNotification.Name(rawValue: "NotifierName_showLoading"),
            object: nil
        )

        animateView.loopMode = .loop
        animateView.play()
    }

     @objc func dismissLogo() {
        print("did dismissLoading")
        DispatchQueue.main.async {
            self.dismiss(animated: true, completion: nil)
        }
    }

}
