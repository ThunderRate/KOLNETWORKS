//
//  NavigationController.swift
//  kolnapp
//
//  Created by Victor_Huang on 2020/2/12.
//  Copyright © 2020 Victor_Huang. All rights reserved.
//

import UIKit

final class NavigationController: UINavigationController {

    private var isPushing: Bool = false

    override func viewDidLoad() {
        super.viewDidLoad()

    }

    override var prefersStatusBarHidden: Bool {
        return true
    }

    override func pushViewController(_ viewController: UIViewController, animated: Bool) {
        guard isPushing == false else { return }
        isPushing = true
        super.pushViewController(viewController, animated: animated)

        DispatchQueue.main.asyncAfter(deadline: .now() + 0.3) {
            self.isPushing = false
        }
    }

}
