//
//  AskVC.swift
//  kolnapp
//
//  Created by 黃世維 on 2020/3/20.
//  Copyright © 2020 黃世維. All rights reserved.
//

import UIKit
import GrowingTextView

class AskVC: UIViewController {
    override var prefersStatusBarHidden: Bool {
        return true
    }

    @IBOutlet var background: UIView!
    @IBOutlet weak var alertTitle: UILabel!
    @IBOutlet weak var alertContent: UILabel!
    @IBOutlet weak var input: GrowingTextView!
//    @IBOutlet weak var input: UITextField!

    @IBOutlet weak var commentButton: UIButton!

    @IBAction func cancel(_ sender: Any) {
        self.dismiss(animated: true, completion: nil)
    }
    @IBAction func sendComment(_ sender: Any) {
        guard let comment = input.text, let uuid = boardUuid else { return }

        self.showLottie()

        var m: [String: Any] = Dictionary()
        m.updateValue(uuid, forKey: "uuid")
        m.updateValue(comment, forKey: "content")
        KolAPI.sendMessage(body: m, completion: { [weak self] response, _ in
//            self?.stopLottie()

            NotificationCenter.default.post(name: NSNotification.Name(rawValue: "NotifierName_loading_board"), object: nil, userInfo: nil)

            guard let response = response else {
                print("login fail, response nil")
                return
            }
            print("AskVC ok, \(response)")
        })

        self.dismiss(animated: true, completion: nil)
        self.stopLottie()
    }

    var boardUuid: String?

    override func viewDidLoad() {
        super.viewDidLoad()

        alertTitle.text = "我要提問"
        alertContent.text = "你的問題只有專案經理人可以看到，相關問題"

        input.delegate = self
        automaticallyAdjustsScrollViewInsets = false

        input.maxLength = 280
        input.trimWhiteSpaceWhenEndEditing = false
//        textView.placeholder = "Say something..."
//        textView.placeholderColor = UIColor(white: 0.8, alpha: 1.0)
        input.minHeight = 25.0
        input.maxHeight = 140.0
        input.backgroundColor = UIColor.lightGray
        input.layer.cornerRadius = 8.0
    }

}

extension AskVC: GrowingTextViewDelegate {
    func textViewDidChangeHeight(_ textView: GrowingTextView, height: CGFloat) {
       UIView.animate(withDuration: 0.2) {
           self.view.layoutIfNeeded()
       }
    }
}
