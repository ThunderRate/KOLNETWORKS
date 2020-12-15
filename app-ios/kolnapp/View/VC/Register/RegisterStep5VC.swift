//
//  RegisterStep5VC.swift
//  kolnapp
//
//  Created by 黃世維 on 2020/4/12.
//  Copyright © 2020 黃世維. All rights reserved.
//

import UIKit

class RegisterStep5VC: UIViewController {

    override var prefersStatusBarHidden: Bool {
        return true
    }

    @IBAction func go2Login(_ sender: Any) {
        // 確認使用者token登入狀態
        if RegisterStep5VC.isApiIng { return }
        self.showLottie()
        // 抓取資料.
        let q1 = DispatchQueue(label: "updateUser_qqq1", attributes: .concurrent)
        group.enter()
        q1.async(group: group) {
          KolAPI.putUser(parameters: MemoryCache.shared.register_user_data!) { [weak self] response, _ in
            print("updateUser putUser response: \(response)")
//            MemoryCache.shared.register_user_data = [:]
            self?.group.leave()
          }
        }

        let q2 = DispatchQueue(label: "updateUser_tags_qqq1", attributes: .concurrent)
        group.enter()
        q2.async(group: group) {
          var parameters: [String: Any] = Dictionary()
            parameters.updateValue(MemoryCache.shared.register_user_tag!, forKey: "tag_ids")
          KolAPI.assainTag(parameters: parameters) { [weak self] response, _ in
            print("updateUser_tags_qqq1: \(response)")
            MemoryCache.shared.register_user_tag = []
            self?.group.leave()
          }
        }

        group.notify(queue: DispatchQueue.main) { [weak self] in
          // 完成所有 Call 後端 API 的動作
          print("完成所有 Call 後端 API 的動作...")
           KolAPI.userProfile(completion: { [weak self] response2, error in
//                self?.stopLottie()
                RegisterStep5VC.isApiIng = false
                if let response2 = response2 {
                    print("userProfile ok: \(response2)")
                    MemoryCache.shared.userData = response2["user"] as? [String: Any]
                    Router.showMainVC()
                } else {
                    print("userProfile ok, but no data: \(error)")
                    self?.navigationController?.dismiss(animated: true, completion: nil)
                }
            
                self?.stopLottie()
            })
        }

    }

    private static var isApiIng: Bool = false
    private let group: DispatchGroup = DispatchGroup()

    override func viewDidLoad() {
        super.viewDidLoad()
    }

    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        self.navigationController?.navigationBar.isHidden = true
    }
}
