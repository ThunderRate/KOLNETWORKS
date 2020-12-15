//
//  ForgetPassVC.swift
//  kolnapp
//
//  Created by Victor_Huang on 2020/5/7.
//  Copyright © 2020 黃世維. All rights reserved.
//

import UIKit

class ForgetPassVC: UIViewController {

    @IBAction func sendEmailForget(_ sender: Any) {
        // Call API
        guard let inputE = inputE.text, !inputE.isEmpty, inputE.isEmail() else { return }
        var p: [String: Any] = Dictionary()
        p.updateValue(inputE, forKey: "email")
        
        MemoryCache.shared.sendEmailForgetTempData = p
        self.showSimpleAlert()
    }
    
    @IBAction func back2Login(_ sender: Any) {
        self.dismiss(animated: true, completion: nil)
    }
    
    @IBOutlet weak var inputE: UITextField!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        let paddingView: UIView = UIView(frame: CGRect(x: 0, y: 0, width: 5, height: 20))
        inputE.leftView = paddingView
        inputE.leftViewMode = .always
    }
    
}


