//
//  RegisterStep2VC.swift
//  kolnapp
//
//  Created by 黃世維 on 2020/4/12.
//  Copyright © 2020 黃世維. All rights reserved.
//

import UIKit

extension UIDatePicker {
    func set18YearValidation() {
        let currentDate: Date = Date()
        var calendar: Calendar = Calendar(identifier: Calendar.Identifier.gregorian)
        calendar.timeZone = TimeZone(identifier: "UTC")!
        var components: DateComponents = DateComponents()
        components.calendar = calendar
        components.year = -18
        let maxDate: Date = calendar.date(byAdding: components, to: currentDate)!
        components.year = -150
        let minDate: Date = calendar.date(byAdding: components, to: currentDate)!
        self.minimumDate = minDate
        self.maximumDate = maxDate
    }
}

class RegisterStep2VC: UIViewController {

    override var prefersStatusBarHidden: Bool {
        return true
    }

    @IBOutlet weak var input_displayName: UITextField!
    @IBOutlet weak var input_phone: UITextField!
    @IBOutlet weak var input_birth: UITextField!
//    @IBOutlet weak var input_address: UITextField!
//    @IBOutlet weak var input_language: UITextField!

    @IBAction func goBack(_ sender: Any) {
        self.navigationController?.popViewController(animated: true)
    }

    @IBAction func go2Next(_ sender: Any) {
        guard let input_displayName = input_displayName.text else { return }
        guard let input_phone = input_phone.text else { return }
        guard let input_birth = input_birth.text else { return }

        MemoryCache.shared.register_user_data?.updateValue(input_displayName, forKey: "display_name")
        MemoryCache.shared.register_user_data?.updateValue(input_phone, forKey: "phone")
        MemoryCache.shared.register_user_data?.updateValue("Taiwan", forKey: "address")

        if input_displayName.isEmpty || input_phone.isEmpty || input_birth.isEmpty {
            return
        }

        let storyboard = UIStoryboard(name: "Register", bundle: nil)
        let vc = storyboard.instantiateViewController(withIdentifier: "RegisterStep3VC") as! RegisterStep3VC
        self.navigationController?.pushViewController(vc, animated: true)
    }

    let datePicker = UIDatePicker()

    override func viewDidLoad() {
        super.viewDidLoad()

        input_displayName.delegate = self
        input_phone.delegate = self
        input_birth.delegate = self
        showDatePicker()

        let paddingView: UIView = UIView(frame: CGRect(x: 0, y: 0, width: 5, height: 20))
           input_displayName.leftView = paddingView
           input_displayName.leftViewMode = .always

        let paddingView2: UIView = UIView(frame: CGRect(x: 0, y: 0, width: 5, height: 20))
           input_phone.leftView = paddingView2
           input_phone.leftViewMode = .always

        let paddingView3: UIView = UIView(frame: CGRect(x: 0, y: 0, width: 5, height: 20))
           input_birth.leftView = paddingView3
           input_birth.leftViewMode = .always
    }

    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        self.navigationController?.navigationBar.isHidden = true
    }
}

extension RegisterStep2VC {
     func showDatePicker() {
       //Formate Date
        datePicker.datePickerMode = .date
        datePicker.date = Date()

        let formatter = DateFormatter()
        formatter.dateFormat = "yyyy-MM-dd"

        let fromDateTime = formatter.date(from: "1911-01-01")
        datePicker.minimumDate = fromDateTime
        datePicker.maximumDate = Date()
        datePicker.locale = Locale(identifier: "zh_TW")

        //ToolBar
        let toolbar = UIToolbar()
        toolbar.sizeToFit()
        let doneButton = UIBarButtonItem(title: "Done", style: .plain, target: self, action: #selector(donedatePicker))
        let spaceButton = UIBarButtonItem(barButtonSystemItem: UIBarButtonItem.SystemItem.flexibleSpace, target: nil, action: nil)
        let cancelButton = UIBarButtonItem(title: "Cancel", style: .plain, target: self, action: #selector(cancelDatePicker))

        toolbar.setItems([cancelButton, spaceButton, doneButton], animated: false)

        input_birth.inputAccessoryView = toolbar
        input_birth.inputView = datePicker

    }

     @objc func donedatePicker() {
      let formatter = DateFormatter()
      formatter.dateFormat = "yyyy-MM-dd"
      input_birth.text = "\(formatter.string(from: datePicker.date))"
        MemoryCache.shared.register_user_data?.updateValue(Int(datePicker.date.timeIntervalSince1970), forKey: "birthday")

        self.view.endEditing(true)
    }

    @objc func cancelDatePicker() {
       self.view.endEditing(true)
     }
}

extension RegisterStep2VC: UITextFieldDelegate {
    func textField(_ textField: UITextField, shouldChangeCharactersIn range: NSRange, replacementString string: String) -> Bool {
        guard let textFieldText = textField.text else { return false }

        if textField == input_displayName {
            MemoryCache.shared.register_user_data?.updateValue(textFieldText, forKey: "display_name")
        }

        if textField == input_phone {
            MemoryCache.shared.register_user_data?.updateValue(textFieldText, forKey: "phone")
        }

        return true
    }

    func textFieldShouldReturn(_ textField: UITextField) -> Bool {
        self.view.endEditing(true)
        return false
    }
}
