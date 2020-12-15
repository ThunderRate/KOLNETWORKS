//
//  LoadingVC.swift
//  kolnapp
//
//  Created by Victor_Huang on 2020/4/20.
//  Copyright © 2020 黃世維. All rights reserved.
//

import UIKit

class LoadingVC: UIViewController, CAAnimationDelegate {
    override var prefersStatusBarHidden: Bool {
        return true
    }

    let gradient = CAGradientLayer()

    // list of array holding 2 colors
    var gradientSet = [[CGColor]]()
    // current gradient index
    var currentGradient: Int = 0

    // colors to be added to the set
    let colorZ = #colorLiteral(red: 1, green: 1, blue: 1, alpha: 1).cgColor
    let colorOne = #colorLiteral(red: 0.01583019271, green: 0.01583534665, blue: 0.01582906395, alpha: 1).cgColor
    let colorTwo = #colorLiteral(red: 1, green: 1, blue: 1, alpha: 1).cgColor
    let colorThree = #colorLiteral(red: 0.01583018526, green: 0.01583527215, blue: 0.01266328618, alpha: 1).cgColor
    let backgroundolor = #colorLiteral(red: 0, green: 0, blue: 0, alpha: 0.5)

    var imageMask: UIImageView?
    var cView: UIView?

    @IBOutlet var container: UIView!
    @IBOutlet weak var pbg: UIView!
    @IBOutlet weak var progressView: UIView!

    override func viewDidLoad() {
        super.viewDidLoad()
        self.view.backgroundColor = backgroundolor
        pbg.layer.cornerRadius = 4
        pbg.backgroundColor = #colorLiteral(red: 0.01583019271, green: 0.01583534665, blue: 0.01582906395, alpha: 1)

        imageMask = UIImageView.init(image: UIImage(named: "logo"))
        imageMask?.contentMode = .scaleAspectFit
        imageMask?.frame = progressView.bounds

        progressView.mask = imageMask

        NotificationCenter.default.addObserver(
            self,
            selector: #selector(dismissLogo),
            name: NSNotification.Name(rawValue: "NotifierName_showLoading"),
            object: nil
        )
    }

    @objc
    func dismissLogo() {
        print("did dismissLoading")
        DispatchQueue.main.async {
            self.dismiss(animated: true, completion: nil)
        }
    }

    override func viewDidLayoutSubviews() {
        super.viewDidLayoutSubviews()

            createGradientView()
    }

    /// Creates gradient view

    func createGradientView() {

        // overlap the colors and make it 3 sets of colors
        gradientSet.append([colorOne, colorTwo])
        gradientSet.append([colorTwo, colorThree])
        gradientSet.append([colorThree, colorOne])

        // set the gradient size to be the entire screen
        gradient.frame = self.progressView.bounds
        gradient.colors = gradientSet[currentGradient]
        gradient.startPoint = CGPoint(x: 0.5, y: 0)
        gradient.endPoint = CGPoint(x: 0.5, y: 1)
        gradient.drawsAsynchronously = true

        self.progressView.layer.insertSublayer(gradient, at: 0)

        animateGradient()
    }

    func animateGradient() {
        // cycle through all the colors, feel free to add more to the set
        if currentGradient < gradientSet.count - 1 {
            currentGradient += 1
        } else {
            currentGradient = 0
        }

        // animate over 3 seconds
        let gradientChangeAnimation = CABasicAnimation(keyPath: "colors")
        gradientChangeAnimation.duration = 1.0
        gradientChangeAnimation.toValue = gradientSet[currentGradient]
        gradientChangeAnimation.fillMode = CAMediaTimingFillMode.forwards
        gradientChangeAnimation.fillMode = CAMediaTimingFillMode.forwards

        gradientChangeAnimation.isRemovedOnCompletion = false
        gradientChangeAnimation.delegate = self
        gradient.add(gradientChangeAnimation, forKey: "gradientChangeAnimation")
    }

    func animationDidStop(_ anim: CAAnimation, finished flag: Bool) {

        // if our gradient animation ended animating, restart the animation by changing the color set
        if flag {
            gradient.colors = gradientSet[currentGradient]
            animateGradient()
        }
    }

}
