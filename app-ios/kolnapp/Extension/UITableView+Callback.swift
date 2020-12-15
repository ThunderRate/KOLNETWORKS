//
//  UITableView+Callback.swift
//  kolnapp
//
//  Created by 🍙 Dodo 🍙 on 2020/8/7.
//  Copyright © 2020 黃世維. All rights reserved.
//

import UIKit

extension UITableView {
    func reloadData(completion:@escaping ()->()) {
        UIView.animate(withDuration: 0, animations: { self.reloadData() })
            { _ in completion() }
    }
}
