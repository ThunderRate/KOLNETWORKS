//
//  RegisterStep3VC.swift
//  kolnapp
//
//  Created by 黃世維 on 2020/4/12.
//  Copyright © 2020 黃世維. All rights reserved.
//

import UIKit

class RegisterStep3VC: UIViewController {

    override var prefersStatusBarHidden: Bool {
        return true
    }

    @IBOutlet weak var tag_boy: UIButton!
    @IBOutlet weak var tag_girl: UIButton!
    @IBOutlet weak var tag_other: UIButton! // LGPT跟不回答合併成不回答
    @IBOutlet weak var tag_none: UIButton! // 暫時隱藏第四個選項.

    @IBOutlet weak var r1: UIButton!
    @IBOutlet weak var r2: UIButton!
    @IBOutlet weak var r3: UIButton!

    @IBOutlet weak var input_location: UITextField!
    @IBOutlet weak var input_fans: UISlider!

    @IBAction func boy(_ sender: Any) {
        MemoryCache.shared.register_user_data?.updateValue(1, forKey: "gender")
        tag_boy.backgroundColor = .lightGray
        tag_girl.backgroundColor = .darkGray
        tag_other.backgroundColor = .darkGray
        tag_none.backgroundColor = .darkGray
    }

    @IBAction func girl(_ sender: Any) {
        MemoryCache.shared.register_user_data?.updateValue(2, forKey: "gender")
        tag_boy.backgroundColor = .darkGray
        tag_girl.backgroundColor = .lightGray
        tag_other.backgroundColor = .darkGray
        tag_none.backgroundColor = .darkGray
    }

    @IBAction func other(_ sender: Any) {
        MemoryCache.shared.register_user_data?.updateValue(3, forKey: "gender")
        tag_boy.backgroundColor = .darkGray
        tag_girl.backgroundColor = .darkGray
        tag_other.backgroundColor = .lightGray
        tag_none.backgroundColor = .darkGray
    }

    @IBAction func none(_ sender: Any) {
//        MemoryCache.shared.register_user_data?.removeValue(forKey: "gender")
//        MemoryCache.shared.register_user_data?.updateValue(0, forKey: "gender")
//
//        tag_boy.backgroundColor = .darkGray
//        tag_girl.backgroundColor = .darkGray
//        tag_other.backgroundColor = .darkGray
//        tag_none.backgroundColor = .lightGray
    }

    @IBAction func r1(_ sender: Any) {
        MemoryCache.shared.register_user_data?.updateValue(1, forKey: "gender")
        r1.backgroundColor = .lightGray
        r2.backgroundColor = .darkGray
        r3.backgroundColor = .darkGray
    }

    @IBAction func r2(_ sender: Any) {
        MemoryCache.shared.register_user_data?.updateValue(2, forKey: "gender")
        r1.backgroundColor = .darkGray
        r2.backgroundColor = .lightGray
        r3.backgroundColor = .darkGray
    }

    @IBAction func r3(_ sender: Any) {
        MemoryCache.shared.register_user_data?.updateValue(3, forKey: "gender")
        r1.backgroundColor = .darkGray
        r2.backgroundColor = .darkGray
        r3.backgroundColor = .lightGray
    }

    @IBAction func changeFans(_ sender: Any) {
        let fans = Int(floor(input_fans.value * 100))
        MemoryCache.shared.register_user_data?.updateValue(10, forKey: "audience_gender")
    }

    @IBAction func goBack(_ sender: Any) {
        self.navigationController?.popViewController(animated: true)
    }

    @IBAction func go2Next(_ sender: Any) {
        let storyboard = UIStoryboard(name: "Register", bundle: nil)
        let vc = storyboard.instantiateViewController(withIdentifier: "RegisterStep4VC") as! RegisterStep4VC
        self.navigationController?.pushViewController(vc, animated: true)
    }

    override func viewDidLoad() {
        super.viewDidLoad()

        MemoryCache.shared.register_user_data?.updateValue(1, forKey: "region")
        MemoryCache.shared.register_user_data?.updateValue(10, forKey: "audience_gender")
    }

    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        self.navigationController?.navigationBar.isHidden = true
    }
}
