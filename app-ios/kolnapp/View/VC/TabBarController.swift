//
//  TabBarController.swift
//  kolnapp
//
//  Created by Victor_Huang on 2020/2/12.
//  Copyright © 2020 Victor_Huang. All rights reserved.
//

import UIKit

extension UITabBarController {

    private struct AssociatedKeys {
        // Declare a global var to produce a unique address as the assoc object handle
        static var orgFrameView: UInt8 = 0
        static var movedFrameView: UInt8 = 1
    }

    var orgFrameView: CGRect? {
        get { return objc_getAssociatedObject(self, &AssociatedKeys.orgFrameView) as? CGRect }
        set { objc_setAssociatedObject(self, &AssociatedKeys.orgFrameView, newValue, .OBJC_ASSOCIATION_COPY) }
    }

    var movedFrameView: CGRect? {
        get { return objc_getAssociatedObject(self, &AssociatedKeys.movedFrameView) as? CGRect }
        set { objc_setAssociatedObject(self, &AssociatedKeys.movedFrameView, newValue, .OBJC_ASSOCIATION_COPY) }
    }

    override open func viewWillLayoutSubviews() {
        super.viewWillLayoutSubviews()
        if let movedFrameView = movedFrameView {
            view.frame = movedFrameView
        }
    }

    func setTabBarVisible(visible: Bool, animated: Bool) {
        //since iOS11 we have to set the background colour to the bar color it seams the navbar seams to get smaller during animation; this visually hides the top empty space...
        view.backgroundColor =  self.tabBar.barTintColor
        // bail if the current state matches the desired state
        if tabBarIsVisible() == visible { return }

        //we should show it
        if visible {
            tabBar.isHidden = false
            UIView.animate(withDuration: animated ? 0.3 : 0.0) {
                //restore form or frames
                self.view.frame = self.orgFrameView!
                //errase the stored locations so that...
                self.orgFrameView = nil
                self.movedFrameView = nil
                //...the layoutIfNeeded() does not move them again!
                self.view.layoutIfNeeded()
            }
        }
            //we should hide it
        else {
            //safe org positions
            orgFrameView   = view.frame
            // get a frame calculation ready
            let offsetY = self.tabBar.frame.size.height
            movedFrameView = CGRect(x: 0, y: 0, width: self.view.frame.width, height: self.view.frame.height + offsetY)
            //animate
            UIView.animate(withDuration: animated ? 0.3 : 0.0, animations: {
                self.view.frame = self.movedFrameView!
                self.view.layoutIfNeeded()
            }) {
                (_) in
                self.tabBar.isHidden = true
            }
        }
    }

    func tabBarIsVisible() -> Bool {
        return orgFrameView == nil
    }
}

class TabBarController: UITabBarController {

    private let taskBoardVC: TaskStoreVC? = VCFactory.getTaskBoardVC()
    private let wantedVC: UIViewController? = VCFactory.getWantedVC()
    private let incomeVC: IncomeVC? = VCFactory.getIncomeVC()

    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
        setupTheme()
    }

    override var prefersStatusBarHidden: Bool {
        return true
    }

    private func setupTheme() {
        let tabBarItems = tabBar.items! as [UITabBarItem]

        tabBarItems[0].title = "逛逛"
        tabBarItems[0].image = UIImage(named: "tab1")
        tabBarItems[1].title = "任務"
        tabBarItems[1].image = UIImage(named: "tab2")
        tabBarItems[2].title = "收益"
        tabBarItems[2].image = UIImage(named: "tab3")
    }

}
