//
//  IncomeVC.swift
//  kolnapp
//
//  Created by Victor_Huang on 2020/2/12.
//  Copyright © 2020 Victor_Huang. All rights reserved.
//

import UIKit
import GrowingTextView

class IncomeVC: UIViewController {
    override var prefersStatusBarHidden: Bool {
        return true
    }

#warning("需要使用自定義rawValue定義default")
enum Store: Int {
    case pay = 0, setting = 1
}

    @IBOutlet weak var avatar: UIImageView!
    @IBOutlet weak var tabName: UILabel!
    @IBOutlet weak var doingBoard: UILabel!
    @IBOutlet weak var waitingBoard: UILabel!
    @IBOutlet weak var container: UIScrollView!

    @IBOutlet weak var indicator: UIView!
    @IBOutlet weak var indicator3: UIView!

    // 總攬
    @IBOutlet weak var commission_rate: UILabel!
    @IBOutlet weak var totalContainer: UIView!
    @IBOutlet weak var total: UILabel!
    @IBOutlet weak var totalIncome: UILabel!
    @IBOutlet weak var basic_income_commission: UILabel!
    @IBOutlet weak var basic_income: UILabel!
    @IBOutlet weak var status1_income: UIStackView!
    @IBOutlet weak var status2_income: UIStackView!
    
    // 帳務設定
    @IBOutlet weak var settingsContainer: UIView!

    @IBOutlet weak var input1: GrowingTextView!
    @IBOutlet weak var input2: GrowingTextView!
    @IBOutlet weak var input3: GrowingTextView!
    @IBOutlet weak var input4: GrowingTextView!

    @IBOutlet weak var saveBtn: UIButton!
    @IBAction func saveSettings(_ sender: Any) {
        var user_data: [String: Any] = [:]
        if let bank_code = input1.text {
            user_data.updateValue(bank_code, forKey: "bank_code")
        }

        if let branch_code = input4.text {
            user_data.updateValue(branch_code, forKey: "branch_code")
        }

        if let account_name = input2.text {
            user_data.updateValue(account_name, forKey: "account_name")
        }
        if let account_no = input3.text {
            user_data.updateValue(account_no, forKey: "account_no")
        }

        self.showLottie()
        KolAPI.putUser(parameters: user_data) { [weak self] response, _ in
            print("updateUser putUser response: \(response)")
            self?.stopLottie()
        }
    }

    private var canScroll: Bool = true

    // TODO: 接資料在實作
    private var currentIndex: Store = .pay
    private let group: DispatchGroup = DispatchGroup()

    override func viewDidLoad() {
        super.viewDidLoad()

        switchIndicator()

        container.delegate = self

        self.avatar.layer.cornerRadius = self.avatar.frame.height/2
        let tap0 = UITapGestureRecognizer(target: self, action: #selector(go2Profile))
        avatar.isUserInteractionEnabled = true
        avatar.addGestureRecognizer(tap0)

        totalContainer.layer.cornerRadius = 8
        totalContainer.layer.makeShadow(color: .lightGray, x: 0, y: 6, blur: 10, spread: 0)

        settingsContainer.layer.cornerRadius = 8
        settingsContainer.layer.makeShadow(color: .lightGray, x: 0, y: 6, blur: 10, spread: 0)

        saveBtn.layer.cornerRadius = 4

        container.contentInsetAdjustmentBehavior = .never

        let tap1 = UITapGestureRecognizer(target: self, action: #selector(scrollToDoingBoard))
        doingBoard.isUserInteractionEnabled = true
        doingBoard.addGestureRecognizer(tap1)

        let tap3 = UITapGestureRecognizer(target: self, action: #selector(scrollToParing))
        waitingBoard.isUserInteractionEnabled = true
        waitingBoard.addGestureRecognizer(tap3)

        let tap4 = UITapGestureRecognizer(target: self, action: #selector(go2DetailVC))
        status1_income.isUserInteractionEnabled = true
        status1_income.addGestureRecognizer(tap4)
        
        let tap5 = UITapGestureRecognizer(target: self, action: #selector(go2DetailVC2))
        status2_income.isUserInteractionEnabled = true
        status2_income.addGestureRecognizer(tap5)

        self.input1.keyboardType = .phonePad
        self.input3.keyboardType = .phonePad
        self.input4.keyboardType = .phonePad
        
    }

    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)

        self.tabBarController?.setTabBarVisible(visible: true, animated: true)

        let cashflow_queue = DispatchQueue(label: "cashflow_queue", attributes: .concurrent)
        group.enter()
        cashflow_queue.async(group: group) {
            KolAPI.cashflow(completion: { [weak self] response, _ in
            MemoryCache.shared.userDataCashFlow_income.removeAll()
            MemoryCache.shared.userDataCashFlow_income_none.removeAll()
            if let response = response {
                print("cashflow_queue ok")
                MemoryCache.shared.userDataCashFlow = response["cashflow"] as? [[String: Any]]
                guard let flow = MemoryCache.shared.userDataCashFlow else { return }
                if let rate = response["commission_rate"] as? Float {
                    // 原本是給string現在直接給正整數.
//                    let per = rate * 100
                    self?.commission_rate.text = "平台服務費(\(rate)%)"
                }
                
                let sum = flow.reduce(0, { (sum, next) in
                    if let basic_income = next["basic_income"] as? Int, let basic_income_commission = next["basic_income_commission"] as? Int, let order = next["order"] as? [String:Any], let status = order["status"] as? Int, status == 1 {
                        return sum + basic_income + basic_income_commission
                    } else {
                        return sum
                    }
                })
                // 未撥款總金額
                self?.total.text = "\(sum)"

                let sum2 = flow.reduce(0, { (sum, next) in
                    if let basic_income_commission = next["basic_income_commission"] as? Int, let order = next["order"] as? [String:Any], let status = order["status"] as? Int, status == 1 {
                        return sum + basic_income_commission
                    } else {
                        return sum
                    }
                })
                // 平台抽成總金額
                self?.basic_income_commission.text = "\(sum2)"

                let sum3 = flow.reduce(0, { (sum, next) in
                    print("next \(next)")
                    if let basic_income = next["basic_income"] as? Int, let order = next["order"] as? [String:Any], let status = order["status"] as? Int, status == 2 {
                        return sum + basic_income
                    } else {
                        return sum
                    }
                })
                // 已撥款總金額
                self?.totalIncome.text = "\(sum3)"
                // 已撥款總金額
                self?.basic_income.text = "\(sum3 + sum2)"

                for next in flow {
                    if let order = next["order"] as? [String:Any], let status = order["status"] as? Int {
                        // 未撥款
                        if status == 1 {
                            MemoryCache.shared.userDataCashFlow_income_none.append(next)
                        }
                        // 已撥款(同意出帳)
                        if status == 2 {
                            MemoryCache.shared.userDataCashFlow_income.append(next)
                        }
                    }

                }

            } else {
                print("cashflow_queue ok, but no data")
            }
            self?.group.leave()
            })
        }

        group.notify(queue: DispatchQueue.main) {
            // 完成所有 Call 後端 API 的動作
            print("完成所有 Call 後端 API 的動作...")
            
            // 檢查是否是從推波進來
            if MemoryCache.shared.kolNotify != -1 {
                if MemoryCache.shared.kolNotify == 4 || MemoryCache.shared.kolNotify == 5 ||  MemoryCache.shared.kolNotify == 6 {
                    self.tabBarController?.selectedIndex = 1
                } else if MemoryCache.shared.kolNotify == 2 || MemoryCache.shared.kolNotify == 3{
                    self.tabBarController?.selectedIndex = 0
                }
                
                MemoryCache.shared.kolNotify = -1
            }
        }
    }

    override func viewDidAppear(_ animated: Bool) {
        super.viewDidAppear(animated)
        self.navigationController?.setNavigationBarHidden(true, animated: animated)

        guard let data = MemoryCache.shared.userData else { return }
        if let photo = data["photo"] as? String {
            self.avatar.sd_setImage(with: URL(string: photo), placeholderImage: UIImage())
        }
        if let bank_code = data["bank_code"] as? String {
            self.input1.text = bank_code
        }
        if let branch_code = data["branch_code"] as? String {
            self.input4.text = branch_code
        }
        if let account_name = data["account_name"] as? String {
            self.input2.text = account_name
        }
        if let account_no = data["account_no"] as? String {
            self.input3.text = account_no
        }

        guard var register_user_data = MemoryCache.shared.register_user_data else { return }
        register_user_data.removeAll()
    }

    @objc func go2DetailVC() {

        let storyBoard = UIStoryboard.init(name: "Income", bundle: nil)
        guard let incomeDetailVC = storyBoard.instantiateViewController(withIdentifier: "IncomeDetailVC") as? IncomeDetailVC else { return }
        incomeDetailVC.viewModel = MemoryCache.shared.userDataCashFlow_income_none

        self.tabBarController?.setTabBarVisible(visible: false, animated: true)
        self.navigationController?.pushViewController(incomeDetailVC, animated: true)
    }
    
    @objc func go2DetailVC2() {

        let storyBoard = UIStoryboard.init(name: "Income", bundle: nil)
        guard let incomeDetailVC = storyBoard.instantiateViewController(withIdentifier: "IncomeDetailVC") as? IncomeDetailVC else { return }
        incomeDetailVC.viewModel = MemoryCache.shared.userDataCashFlow_income

        self.tabBarController?.setTabBarVisible(visible: false, animated: true)
        self.navigationController?.pushViewController(incomeDetailVC, animated: true)
    }

    @objc func go2Profile() {
        let storyBoard = UIStoryboard.init(name: "UserStoryboard", bundle: nil)
        guard let profileVC = storyBoard.instantiateViewController(withIdentifier: "ProfileVC") as? ProfileVC else { return }

        self.navigationController?.present(profileVC, animated: true, completion: nil)

    }

    @objc func scrollToDoingBoard() {
        scrollTo(container, index: 0)
    }

    @objc func scrollToParing() {
        scrollTo(container, index: 1)
    }

    func scrollTo(_ scrollView: UIScrollView, index: Int) {
        if canScroll {
            canScroll = false
            var frame = scrollView.frame
            frame.origin.x = frame.size.width * CGFloat(index)
            frame.origin.y = 0
            scrollView.scrollRectToVisible(frame, animated: true)

            self.currentIndex = Store(rawValue: index) ?? Store.pay
            switchIndicator()
            canScroll = true
        }
    }

}

extension IncomeVC: UIScrollViewDelegate {
    func scrollViewDidEndDecelerating(_ scrollView: UIScrollView) {
        if scrollView == container {
            let index = lrint((Double(scrollView.contentOffset.x / scrollView.bounds.size.width)))
            print("CCC: \(index)")
            self.currentIndex = Store(rawValue: index) ?? Store.pay
            print("C: \(self.currentIndex)")

            switchIndicator()
        }
    }

    func switchIndicator() {
        switch self.currentIndex {
        case .pay:
            self.indicator.isHidden = false
            self.indicator3.isHidden = true
        case .setting:
            self.indicator.isHidden = true
            self.indicator3.isHidden = false
        }
    }
}

extension IncomeVC: GrowingTextViewDelegate {
    func textViewDidChangeHeight(_ textView: GrowingTextView, height: CGFloat) {
       UIView.animate(withDuration: 0.2) {
           self.view.layoutIfNeeded()
       }
    }

    func textViewDidEndEditing(_ textView: UITextView) {
        if let input1 = input1.placeholder, input1 == "銀行代號" {
            MemoryCache.shared.register_user_data?.updateValue(textView.text!, forKey: "bank_code")
        }

        if let input4 = input4.placeholder, input4 == "分行代碼" {
            MemoryCache.shared.register_user_data?.updateValue(textView.text!, forKey: "branch_code")
        }

        if let input2 = input2.placeholder, input2 == "戶名" {
            MemoryCache.shared.register_user_data?.updateValue(textView.text!, forKey: "account_name")
        }

        if let input3 = input3.placeholder, input3 == "帳號" {
            MemoryCache.shared.register_user_data?.updateValue(textView.text!, forKey: "account_no")
        }

    }
}
