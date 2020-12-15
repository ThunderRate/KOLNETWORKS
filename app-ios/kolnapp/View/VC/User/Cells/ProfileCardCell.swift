//
//  ProfileCardCell.swift
//  kolnapp
//
//  Created by 黃世維 on 2020/5/31.
//  Copyright © 2020 黃世維. All rights reserved.
//

import UIKit
import GrowingTextView

class ProfileCardCell: UITableViewCell {
    
    @IBOutlet weak var bg: UIView!
    @IBOutlet weak var plateFrom: UILabel!
    @IBOutlet weak var value1: GrowingTextView!
    
    @IBOutlet weak var sMoney: UILabel!
    @IBOutlet weak var value2: UILabel!
    @IBAction func updatePlateform(_ sender: Any) {
        guard let openPlateformLinkInWebview = openPlateformLinkInWebview else {
            return
        }
        openPlateformLinkInWebview()
    }
    
    
    // 第三方平台綁定
//    @IBOutlet weak var addBtn: UIButton!
//    @IBAction func linkPlateform(_ sender: Any) {
//        guard value1.text.isEmpty || value1.text == "加入" else {
//            return
//        }
//        print("linking....")
//        guard let openPlateformLinkInWebview = openPlateformLinkInWebview else { return }
//        openPlateformLinkInWebview()
//    }
    
    var openPlateformLinkInWebview: (() -> ())?
    
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
        value1.delegate = self
        value1.textAlignment = .left
        value1.textContainer.lineFragmentPadding = 0
        
        // shadow
//        self.bg.layer.cornerRadius = 10
        self.bg.layer.makeShadow(color: .lightGray, x: 0, y: 6, blur: 10, spread: 0)
        self.bg.clipsToBounds = false
        self.contentView.clipsToBounds = false
        self.clipsToBounds = false

        self.selectionStyle = .none
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
//        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }
    
}


extension ProfileCardCell: GrowingTextViewDelegate {
    func textViewDidChangeHeight(_ textView: GrowingTextView, height: CGFloat) {
       UIView.animate(withDuration: 0.2) {
           self.layoutIfNeeded()
       }
    }
    
    func textViewShouldBeginEditing(_ textView: UITextView) -> Bool {
//        if textView.text == "加入" {
//            textView.text = ""
//        }
        
//        if plateFrom.text == "Blog" && (textView.text == "加入" || textView.text.isEmpty) {
//            textView.text = "https://"
//        }
        return true
    }
    
//    func textViewDidEndEditing(_ textView: UITextView) {
//        if textView.text.isEmpty || textView.text == "" {
//            textView.text = "加入"
//        } else {
//            guard let openPlateformLinkInWebview = openPlateformLinkInWebview else {
//                return
//            }
//            openPlateformLinkInWebview()
//        }
//    }
}
