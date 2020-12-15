//
//  RegisterVC.swift
//  kolnapp
//
//  Created by 黃世維 on 2020/4/12.
//  Copyright © 2020 黃世維. All rights reserved.
//

import UIKit

class RegisterVC: UIViewController {

    override var prefersStatusBarHidden: Bool {
        return true
    }

    @IBOutlet weak var input_pass: UITextField!
    @IBOutlet weak var input_pass_again: UITextField!
    @IBOutlet weak var input_email: UITextField!

    @IBAction func rester(_ sender: Any) {
        if let email = input_email.text, let password = input_pass.text, let password_again = input_pass_again.text {
            if password.isEmpty || password_again.isEmpty || email.isEmpty || !input_email.isEmail() {
                return
            }

            if password.count < 6 {
                let alertController = UIAlertController(title: "密碼長度應該大於6", message: nil, preferredStyle: .alert)
                let okAction = UIAlertAction(
                    title: "確認",
                    style: .default,
                    handler: {
                    (_: UIAlertAction!) -> Void in
                      print("按下確認後，閉包裡的動作")
                })
                alertController.addAction(okAction)
                self.present(alertController, animated: true, completion: nil)

                return
            }

            if password != password_again {
                input_pass.text = ""
                input_pass_again.text = ""

                input_pass.attributedPlaceholder = NSAttributedString(string: "輸入密碼",
                attributes: [NSAttributedString.Key.foregroundColor: UIColor.red])
                input_pass_again.attributedPlaceholder = NSAttributedString(string: "驗證密碼",
                attributes: [NSAttributedString.Key.foregroundColor: UIColor.red])
                return
            }

            var p: [String: Any] = Dictionary()
            p.updateValue(email.trimmingCharacters(in: .whitespacesAndNewlines), forKey: "email")
            p.updateValue(password, forKey: "password")
            p.updateValue(password_again, forKey: "password_again")
            if let iCode = UserDefaults.standard.value(forKey: "kKolinvitationCode") as? String {
                p.updateValue(iCode, forKey: "invitation_code")
            } else {
                p.updateValue(MemoryCache.shared.invitation_code, forKey: "invitation_code")
            }

            UserDefaults.standard.setValue(email.trimmingCharacters(in: .whitespacesAndNewlines), forKeyPath: "aemail")
            UserDefaults.standard.synchronize()

            KolAPI.register(parameters: p, completion: { [weak self] response, _ in
                guard let response = response else {
                    print("register fail, response nil")
                    self?.input_email.attributedPlaceholder = NSAttributedString(string: "Email",
                    attributes: [NSAttributedString.Key.foregroundColor: UIColor.red])
                    self?.input_pass.attributedPlaceholder = NSAttributedString(string: "輸入密碼",
                    attributes: [NSAttributedString.Key.foregroundColor: UIColor.red])
                    self?.input_pass_again.attributedPlaceholder = NSAttributedString(string: "驗證密碼",
                    attributes: [NSAttributedString.Key.foregroundColor: UIColor.red])

                    let alertController = UIAlertController(title: "驗證失敗", message: "伺服器連線失敗, 請稍後再試", preferredStyle: .alert)
                    let okAction = UIAlertAction(
                      title: "確認",
                      style: .default,
                      handler: {
                      (_: UIAlertAction!) -> Void in
                        print("按下確認後，閉包裡的動作")
                    })
                    alertController.addAction(okAction)
                    self?.present(alertController, animated: true, completion: nil)
                    return
                }

                guard let user = response["user"] else {
                    print("register fail")
                    self?.input_email.attributedPlaceholder = NSAttributedString(string: "Email",
                    attributes: [NSAttributedString.Key.foregroundColor: UIColor.red])
                    self?.input_pass.attributedPlaceholder = NSAttributedString(string: "輸入密碼",
                    attributes: [NSAttributedString.Key.foregroundColor: UIColor.red])
                    self?.input_pass_again.attributedPlaceholder = NSAttributedString(string: "驗證密碼",
                    attributes: [NSAttributedString.Key.foregroundColor: UIColor.red])

                    self?.input_pass.text = ""
                    self?.input_pass_again.text = ""

                    let alertController = UIAlertController(title: "註冊失敗", message: nil, preferredStyle: .alert)
                    let okAction = UIAlertAction(
                      title: "確認",
                      style: .default,
                      handler: {
                      (_: UIAlertAction!) -> Void in
                        print("按下確認後，閉包裡的動作")
                    })
                    alertController.addAction(okAction)
                    self?.present(alertController, animated: true, completion: nil)

                    return
                }

                let alertController2 = UIAlertController(title: "註冊成功", message: "請到註冊的信箱完成驗證才能啟用APP喔", preferredStyle: .alert)
                let okAction = UIAlertAction(
                    title: "確認",
                    style: .default,
                    handler: {
                    (_: UIAlertAction!) -> Void in
                        print("dismiss, go2 login")
                        self?.dismiss(animated: true, completion: nil)
                })
                alertController2.addAction(okAction)
                self?.present(alertController2, animated: true, completion: {
                    print("go2 login")
                })

            })

        }
    }

    private var migrationY: CGFloat = 0

    override func viewDidLoad() {
        super.viewDidLoad()

        input_pass.delegate = self
        input_pass.delegate = self
        input_email.delegate = self

        let paddingView: UIView = UIView(frame: CGRect(x: 0, y: 0, width: 5, height: 20))
        input_pass.leftView = paddingView
        input_pass.leftViewMode = .always

        let paddingView2: UIView = UIView(frame: CGRect(x: 0, y: 0, width: 5, height: 20))
        input_email.leftView = paddingView2
        input_email.leftViewMode = .always

        let paddingView3: UIView = UIView(frame: CGRect(x: 0, y: 0, width: 5, height: 20))
        input_pass_again.leftView = paddingView3
        input_pass_again.leftViewMode = .always

    }

}

extension RegisterVC: UITextFieldDelegate {
  func textFieldShouldReturn(_ textField: UITextField) -> Bool {
    self.view.endEditing(true)
    return false
  }
}
