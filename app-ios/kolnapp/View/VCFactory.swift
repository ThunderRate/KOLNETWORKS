//
//  VCFactory.swift
//  kolnapp
//
//  Created by Victor_Huang on 2020/2/12.
//  Copyright © 2020 Victor_Huang. All rights reserved.
//

import UIKit

final class VCFactory {

    static func get<T: UIViewController>(_ type: T.Type, sbID: ViewControllerSBID, from sb: StoryboardName) -> T? {
        let vc = SBHelper.instanceVCfromSB(storyBoardName: sb.id, viewControllerID: sbID.id) as? T
        return vc
    }
}

// MARK: - Func. for Fano
extension VCFactory {

//    static func getLaunchVC() -> LaunchVC? {
//        return get(LaunchVC.self, sbID: .launch, from: .main)
//    }

    // MARK: - Tabs VC
    static func getTabBarController() -> TabBarController? {
        let tabVC = VCFactory.get(TabBarController.self, sbID: .tabBarController, from: .main)
        return tabVC
    }

    static func getTaskBoardVC() -> TaskStoreVC? {
        let tabVC = VCFactory.get(TaskStoreVC.self, sbID: .task, from: .task)
        return tabVC
    }

    static func getWantedVC() -> UIViewController? {
        if MemoryCache.shared.isManager {
            let tabVC = VCFactory.get(ManegerWantedVC.self, sbID: .manegerWantedVC, from: .wanted)
            return tabVC
        } else {
            let tabVC = VCFactory.get(WantedVC.self, sbID: .wanted, from: .wanted)
            return tabVC
        }
    }

    static func getIncomeVC() -> IncomeVC? {
        let tabVC = VCFactory.get(IncomeVC.self, sbID: .income, from: .income)
        return tabVC
    }

}
