//
//  MyNavigationController.swift
//  kolnapp
//
//  Created by 🍙 Dodo 🍙 on 2020/8/7.
//  Copyright © 2020 黃世維. All rights reserved.
//

import UIKit

class MyNavigationController: UINavigationController {
    var isPushing = false

    override func pushViewController(_ viewController: UIViewController, animated: Bool) {
        if !isPushing && !self.isLoading() {
            isPushing = true
            CATransaction.begin()
            CATransaction.setCompletionBlock {
                self.isPushing = false
            }
            super.pushViewController(viewController, animated: animated)
            CATransaction.commit()
        }
    }
}
