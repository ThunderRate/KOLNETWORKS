//
//  ParingPriceVC.swift
//  kolnapp
//
//  Created by Victor_Huang on 2020/5/5.
//  Copyright © 2020 黃世維. All rights reserved.
//

import UIKit
import GrowingTextView

class ParingPriceVC: UIViewController {
    override var prefersStatusBarHidden: Bool {
        return true
    }

    @IBOutlet weak var alertTitle: UILabel!
    @IBOutlet weak var reviewHint: UILabel!
    @IBOutlet weak var wantedValue: GrowingTextView!

    @IBOutlet weak var commentButton: UIButton!

    @IBAction func cancel(_ sender: Any) {
        self.dismiss(animated: true, completion: nil)
    }

    @IBAction func sendComment(_ sender: Any) {
        guard let value: Int = Int(wantedValue.text ?? "") else { return }
        if value <= 0 {
           return
        }
        
        let valueDic: [String: Int] = ["wantedValue": value]
        NotificationCenter.default.post(name: NSNotification.Name(rawValue: "kKOLwantedValue"), object: nil, userInfo: valueDic)
        self.dismiss(animated: true, completion: nil)
    }
    
    var commentstr: String?

    override func viewDidLoad() {
        super.viewDidLoad()

        wantedValue.delegate = self
        wantedValue.keyboardType = .numberPad

        alertTitle.text = "我要參加"
        
        guard let comments = commentstr else {
            reviewHint.text = "請確認合作內容與報價是否包含購買產品費用後，再進行報價喔！\n＊在你與廠商都同意合作後，需自行負擔匯款手續費"
            return
        }
        reviewHint.text = comments
    }

}

extension ParingPriceVC: GrowingTextViewDelegate {
    func textViewDidChangeHeight(_ textView: GrowingTextView, height: CGFloat) {
       UIView.animate(withDuration: 0.2) {
           self.view.layoutIfNeeded()
       }
    }

    private func textFieldDidEndEditing(_ textField: UITextField) {

    }
}
