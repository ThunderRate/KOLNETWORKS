//
//  ProfileType1Cell.swift
//  kolnapp
//
//  Created by 黃世維 on 2020/4/11.
//  Copyright © 2020 黃世維. All rights reserved.
//

import UIKit
import GrowingTextView

class ProfileType1Cell: UITableViewCell {

    @IBAction func go2Tags(_ sender: Any) {
        guard let go2TagDelegate = go2TagDelegate else { return }
        go2TagDelegate()
    }
    @IBOutlet weak var editBtn: UIButton!
    
    @IBOutlet weak var profileKey: UILabel!
    @IBOutlet weak var profileVlaue: GrowingTextView!
    
    var go2TagDelegate: (() -> ())?

    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code

        profileVlaue.delegate = self
        profileVlaue.textAlignment = .right
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
//        super.setSelected(selected, animated: animated)
    }

}

extension ProfileType1Cell: GrowingTextViewDelegate {
    func textViewDidChangeHeight(_ textView: GrowingTextView, height: CGFloat) {
       UIView.animate(withDuration: 0.2) {
           self.layoutIfNeeded()
       }
    }

    func textViewDidEndEditing(_ textView: UITextView) {
        if let key = profileKey.text, key == "真實姓名" {
            guard let text = textView.text else {
                return
            }
            
            MemoryCache.shared.register_user_data?.updateValue(text, forKey: "display_name")
        }

        if let key = profileKey.text, key == "Email" {
            guard let text = textView.text else {
                return
            }
            
            MemoryCache.shared.register_user_data?.updateValue(text.trimmingCharacters(in: .whitespacesAndNewlines), forKey: "email")
        }

        if let key = profileKey.text, key == "手機電話" {
            guard let text = textView.text else {
                return
            }
            
            MemoryCache.shared.register_user_data?.updateValue(text.trimmingCharacters(in: .whitespacesAndNewlines), forKey: "phone")
        }

        if let key = profileKey.text, key == "居住及通訊地址" {
            guard let text = textView.text else {
                return
            }
            
            MemoryCache.shared.register_user_data?.updateValue(text, forKey: "address")
        }

        if let key = profileKey.text, key == "LINE" {
            guard let text = textView.text else {
                return
            }
            
            MemoryCache.shared.register_user_data?.updateValue(text.trimmingCharacters(in: .whitespacesAndNewlines), forKey: "line_id")
        }

    }
}
