//
//  ContentSizedTableView.swift
//  kolnapp
//
//  Created by 🍙 Dodo 🍙 on 2020/7/7.
//  Copyright © 2020 黃世維. All rights reserved.
//

import UIKit

final class ContentSizedTableView: UITableView {
    override var contentSize:CGSize {
        didSet {
            invalidateIntrinsicContentSize()
        }
    }

    override var intrinsicContentSize: CGSize {
        layoutIfNeeded()
        return CGSize(width: UIView.noIntrinsicMetric, height: contentSize.height)
    }
}
