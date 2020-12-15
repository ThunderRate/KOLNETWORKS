//
//  MoneyVC.swift
//  kolnapp
//
//  Created by 黃世維 on 2020/5/1.
//  Copyright © 2020 黃世維. All rights reserved.
//

import UIKit

class ReviewVC: UIViewController {
    override var prefersStatusBarHidden: Bool {
        return true
    }

    @IBOutlet weak var alertTitle: UILabel!
    @IBOutlet weak var reviewHint: UILabel!

    @IBOutlet weak var commentButton: UIButton!

    @IBAction func cancel(_ sender: Any) {
        self.dismiss(animated: true, completion: nil)
    }

    @IBAction func sendComment(_ sender: Any) {
        self.dismiss(animated: true, completion: nil)
    }

    override func viewDidLoad() {
        super.viewDidLoad()
    }

}
