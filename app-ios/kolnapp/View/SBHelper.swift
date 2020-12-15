//
//  SBHelper.swift
//  kolnapp
//
//  Created by Victor_Huang on 2020/2/12.
//  Copyright © 2020 Victor_Huang. All rights reserved.
//

import UIKit

final class SBHelper {

    static func instanceVCfromSB(storyBoardName: String, viewControllerID: String) -> UIViewController? {
        let storyboard = UIStoryboard(name: storyBoardName, bundle: nil)
        let vc = storyboard.instantiateViewController(withIdentifier: viewControllerID)
        return vc
    }
}

/// Storyboard File Name
enum StoryboardName: String {
    case main = "Main"
    case task = "TaskBoard"
    case wanted = "Wanted"
    case income = "Income"

    var id: String { return rawValue }
}

/// VC's Storyboard ID
enum ViewControllerSBID: String {
    // Main.storyboard
    case launch = "LaunchVC"
    case tabBarController  = "TabBarController"

    // TaskBaord.storyboard
    case task = "TaskBoardVC"

    // Wanted.storyboard
    case wanted = "WantedVC"
    case manegerWantedVC = "ManegerWantedVC"

    // Income.storyboard
    case income = "IncomeVC"

    var id: String { return rawValue }
}
