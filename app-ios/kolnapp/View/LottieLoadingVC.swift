//
//  LottieLoadingVC.swift
//  kolnapp
//
//  Created by Victor_Huang on 2020/5/7.
//  Copyright © 2020 黃世維. All rights reserved.
//


import UIKit
import Lottie

extension UIViewController {
    @objc func showLottie() {
        let tabBarControllerItems = self.tabBarController?.tabBar.items

        if let tabArray = tabBarControllerItems {
            for item in tabArray {
                item.isEnabled = false
            }
        }
        
        let lottieVC: LottieLoadingVC = .init()
        lottieVC.view.bounds = UIScreen.main.bounds
        lottieVC.view.tag = 987654321
        self.view.addSubview(lottieVC.view)
        lottieVC.play()
    }
    
    @objc func stopLottie() {
         if let foundView = view.viewWithTag(987654321) {
            DispatchQueue.main.asyncAfter(wallDeadline: .now() + 0.5, execute: {
                let tabBarControllerItems = self.tabBarController?.tabBar.items
                if let tabArray = tabBarControllerItems {
                    for item in tabArray {
                        item.isEnabled = true
                    }
                }
                foundView.removeFromSuperview()
            })
         }
    }
    
    func isLoading() -> Bool {
        if let foundView = view.viewWithTag(987654321) as? LottieLoadingVC {
            return foundView.lottie.isAnimationPlaying
        }
        return false
    }
}

class LottieLoadingVC: UIViewController {
    
    static let LottieDidStoptify: NSNotification.Name = NSNotification.Name(rawValue: "kLottieDidStoptify")

    let bgColor: UIColor = UIColor(displayP3Red: 0.025, green: 0.025, blue: 0.025, alpha: 0.75)
    var animateContainer: UIView!
    var lottie: AnimationView!

    override func viewDidLoad() {
        super.viewDidLoad()
        
        setup()
    }
    
    override func viewDidDisappear(_ animated: Bool) {
        super.viewDidDisappear(animated)
        NotificationCenter.default.post(name: LottieLoadingVC.LottieDidStoptify, object: nil, userInfo: nil)
    }
    
    // MARK: Object lifecycle
    // SB / nib
    override init(nibName nibNameOrNil: String?, bundle nibBundleOrNil: Bundle?) {
        super.init(nibName: nibNameOrNil, bundle: nibBundleOrNil)
    }
    // Code
    init() {
        super.init(nibName: nil, bundle: nil)
    }
    // coder
    required init?(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)
    }
    
    private func setup() {
        self.view.layoutMargins = UIEdgeInsets(top: -10, left: 0, bottom: 0, right: 0)
        self.view.backgroundColor = bgColor
        
        self.animateContainer = .init(frame: CGRect(x: 0, y: 0, width: 240, height: 240))
        self.animateContainer.center = self.view.center
        self.animateContainer.contentMode = .scaleAspectFit
        
        self.lottie = initLottie(frame: CGRect(x: 0, y: 0, width: 120, height: 120))
        self.lottie.contentMode = .scaleAspectFill
        self.lottie.center = self.view.center
        
        self.view.addSubview(self.animateContainer)
        self.view.addSubview(self.lottie)
    }
    
    private func initLottie(frame: CGRect) -> AnimationView {
        // Init AnimationView
        let checkMarkAnimation = AnimationView(name: "lf30_editor_Jx3Vuy")
        checkMarkAnimation.frame = frame
        checkMarkAnimation.loopMode = .loop
        checkMarkAnimation.animationSpeed = 2
        return checkMarkAnimation
    }
    
    public func play() {
        self.lottie.play()
    }

}
