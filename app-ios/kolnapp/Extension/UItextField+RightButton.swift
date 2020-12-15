//
//  UItextField+RightButton.swift
//  kolnapp
//
//  Created by Victor_Huang on 2020/3/5.
//  Copyright © 2020 黃世維. All rights reserved.
//

import UIKit

// MARK: - 添加自定义清除按钮
extension UITextField {

    // 增加一個文字當作圖像按鈕.
    func addText2RightView(text: String, backgroundColor: UIColor = .gray, isButton: Bool = true) {
        let label: UILabel = .init()
        label.text = text
        label.backgroundColor = backgroundColor
        label.layer.cornerRadius = label.frame.height/2

        let gesture = UITapGestureRecognizer(target: self, action: #selector(checkAction))
        label.addGestureRecognizer(gesture)

        self.rightView = label
        self.rightViewMode = .always
    }

    @objc func checkAction(sender: AnyObject) {
        self.text = ""
    }

}
