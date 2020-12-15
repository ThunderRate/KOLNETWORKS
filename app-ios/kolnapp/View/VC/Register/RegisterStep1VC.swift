//
//  RegisterStep1VC.swift
//  kolnapp
//
//  Created by 黃世維 on 2020/4/12.
//  Copyright © 2020 黃世維. All rights reserved.
//

import UIKit

class RegisterStep1VC: UIViewController {

    override var prefersStatusBarHidden: Bool {
        return true
    }

    @IBOutlet weak var fb: UIButton!
    @IBOutlet weak var ig: UIButton!
    @IBOutlet weak var yt: UIButton!
    @IBOutlet weak var blog: UIButton!
    @IBOutlet weak var input_blog: UITextField!

    @IBAction func go2FB(_ sender: Any) {
        fb.backgroundColor = .lightGray
        ig.backgroundColor = .darkGray
        yt.backgroundColor = .darkGray
        blog.backgroundColor = .darkGray
        
        input_blog.placeholder = "請輸入 Facebook 網址"
        input_blog.attributedPlaceholder = NSAttributedString(string: "請輸入 Facebook 網址",
                                                              attributes: [NSAttributedString.Key.foregroundColor: UIColor.lightGray])

        input_blog.isHidden = false
    }

    @IBAction func go2IG(_ sender: Any) {
        fb.backgroundColor = .darkGray
        ig.backgroundColor = .lightGray
        yt.backgroundColor = .darkGray
        blog.backgroundColor = .darkGray
        
        input_blog.placeholder = "請輸入Instagram ID"
//        input_blog.attributedPlaceholder = NSAttributedString(string: "請輸入Instagram ID",
//        attributes: [NSAttributedString.Key.foregroundColor: UIColor.lightGray])

        input_blog.isHidden = false
    }

    @IBAction func go2YT(_ sender: Any) {
        fb.backgroundColor = .darkGray
        ig.backgroundColor = .darkGray
        yt.backgroundColor = .lightGray
        blog.backgroundColor = .darkGray
        
        input_blog.placeholder = "請輸入包含 /channel 的網址"
//        input_blog.attributedPlaceholder = NSAttributedString(string: "請輸入 www.youtube.com/channel/開頭連結 ",
//        attributes: [NSAttributedString.Key.foregroundColor: UIColor.lightGray])

        input_blog.isHidden = false
    }

    @IBAction func go2Blog(_ sender: Any) {
        fb.backgroundColor = .darkGray
        ig.backgroundColor = .darkGray
        yt.backgroundColor = .darkGray
        blog.backgroundColor = .lightGray
        
        input_blog.placeholder = "請輸入部落格網址"
//        input_blog.attributedPlaceholder = NSAttributedString(string: "請輸入部落格網址",
//        attributes: [NSAttributedString.Key.foregroundColor: UIColor.lightGray])

        input_blog.isHidden = false
    }

    @IBAction func back(_ sender: Any) {
        self.dismiss(animated: true, completion: nil)
    }

    @IBAction func next(_ sender: Any) {
        guard let input = input_blog.text else { return }
        if input.isEmpty {
            input_blog.text = ""
            if ig.backgroundColor == .lightGray {
                input_blog.attributedPlaceholder = NSAttributedString(string: "請輸入Instagram ID",
                attributes: [NSAttributedString.Key.foregroundColor: UIColor.red])
            }
            if fb.backgroundColor == .lightGray {
                input_blog.attributedPlaceholder = NSAttributedString(string: "請輸入 Facebook 網址",
                attributes: [NSAttributedString.Key.foregroundColor: UIColor.red])
            }
            if yt.backgroundColor == .lightGray {
                input_blog.attributedPlaceholder = NSAttributedString(string: "請輸入包含 /channel 的網址",
                attributes: [NSAttributedString.Key.foregroundColor: UIColor.red])
            }
            if blog.backgroundColor == .lightGray {
                input_blog.attributedPlaceholder = NSAttributedString(string: "請輸入部落格網址",
                attributes: [NSAttributedString.Key.foregroundColor: UIColor.red])
            }
            
            return
        }

        self.showLottie()
        var p: [String: Any] = Dictionary()
//        p.updateValue(input.trimmingCharacters(in: .whitespacesAndNewlines), forKey: "name")
        
        if fb.backgroundColor == .lightGray {
            print("link_fb")
            p.updateValue(input.trimmingCharacters(in: .whitespacesAndNewlines), forKey: "url")
            
            KolAPI.link_fb(parameters: p, completion: { [weak self] response, _ in
                guard let self = self else { return }

                guard let response = response else {
                   print("register fail, response nil")
                   return
                }

                if let success = response["success"] as? Bool, success == true {
                    self.go2Step2()
                } else {
                    // cannot find the instagram user
                    self.input_blog.textColor = .red
                    self.input_blog.text = "綁定失敗"
                }
                
                self.stopLottie()
            })
            return
        }
        
        if ig.backgroundColor == .lightGray {
            print("link_ig")
            p.updateValue(input.trimmingCharacters(in: .whitespacesAndNewlines), forKey: "name")
            
            KolAPI.link_ig(parameters: p, completion: { [weak self] response, _ in
                guard let self = self else { return }

                guard let response = response else {
                   print("register fail, response nil")
                   return
                }

                if let success = response["success"] as? Bool, success == true {
                    self.go2Step2()
                } else {
                    // cannot find the instagram user
                    self.input_blog.textColor = .red
                    self.input_blog.text = "綁定失敗"
                }
                
                self.stopLottie()
            })
            return
        }
        
        if yt.backgroundColor == .lightGray {
            print("link_yt")
            p.updateValue(input.trimmingCharacters(in: .whitespacesAndNewlines), forKey: "url")
            
            KolAPI.link_yt(parameters: p, completion: { [weak self] response, _ in
                guard let self = self else { return }

                guard let response = response else {
                   print("register fail, response nil")
                   return
                }

                if let success = response["success"] as? Bool, success == true {
                    self.go2Step2()
                } else {
                    // cannot find the instagram user
                    self.input_blog.textColor = .red
                    self.input_blog.text = "綁定失敗"
                }
                
                self.stopLottie()
            })
            return
        }
        
        if blog.backgroundColor == .lightGray {
            print("link_blog")
            p.updateValue(input.trimmingCharacters(in: .whitespacesAndNewlines), forKey: "url")
            
            KolAPI.link_blog(parameters: p, completion: { [weak self] response, _ in
                guard let self = self else { return }

                guard let response = response else {
                   print("register fail, response nil")
                   return
                }

                if let success = response["success"] as? Bool, success == true {
                    self.go2Step2()
                } else {
                    // cannot find the instagram user
                    self.input_blog.textColor = .red
                    self.input_blog.text = "綁定失敗"
                }
                
                self.stopLottie()
            })
            return
        }
        
    }
    
    func go2Step2() {
        let storyboard = UIStoryboard(name: "Register", bundle: nil)
        let vc = storyboard.instantiateViewController(withIdentifier: "RegisterStep2VC") as! RegisterStep2VC
        self.navigationController?.pushViewController(vc, animated: true)
    }

    private let group: DispatchGroup = DispatchGroup()

    override func viewDidLoad() {
        super.viewDidLoad()

        input_blog.isHidden = true
        input_blog.delegate = self

        let paddingView: UIView = UIView(frame: CGRect(x: 0, y: 0, width: 5, height: 20))
        input_blog.leftView = paddingView
        input_blog.leftViewMode = .always
        
        loading()
    }

    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        self.navigationController?.navigationBar.isHidden = true
        if let fb_url = MemoryCache.shared.userData?["fb_url"] as? String {
            // 已綁定FB
            go2Step2()
        }
        
        if let ig_url = MemoryCache.shared.userData?["ig_url"] as? String {
            // 已綁定IG
            go2Step2()
        }
        
        if let yt_url = MemoryCache.shared.userData?["yt_url"] as? String {
            // 已綁定YT
            go2Step2()
        }
        
        if let blog_url = MemoryCache.shared.userData?["blog_url"] as? String {
            // 已綁定blog
            go2Step2()
        }
    }

    // TODO: 要設定timeout.
    func loading() {
        // 顯示進度條.

        // 抓取資料.
        let q1 = DispatchQueue(label: "tag4", attributes: .concurrent)
        group.enter()
        q1.async(group: group) {
            KolAPI.getTags(category: "4", completion: { [weak self] response, _ in
            if let response = response {
                print("tag4 ok")
                MemoryCache.shared.tag4 = response["tags"] as? [[String: Any]]
            } else {
                print("tag4 ok, but no data")
            }
            self?.group.leave()
          })
        }

        let q2 = DispatchQueue(label: "tag5", attributes: .concurrent)
        group.enter()
        q2.async(group: group) {
            KolAPI.getTags(category: "5", completion: { [weak self] response, _ in
            if let response = response {
                print("tag5 ok")
                MemoryCache.shared.tag5 = response["tags"] as? [[String: Any]]
            } else {
                print("tag5 ok, but no data")
            }
            self?.group.leave()
          })
        }

        let q3 = DispatchQueue(label: "tag6", attributes: .concurrent)
        group.enter()
        q3.async(group: group) {
            KolAPI.getTags(category: "6", completion: { [weak self] response, _ in
            if let response = response {
                print("tag6 ok")
                MemoryCache.shared.tag6 = response["tags"] as? [[String: Any]]
            } else {
                print("tag6 ok, but no data")
            }
            self?.group.leave()
          })
        }

        let q100 = DispatchQueue(label: "tag7", attributes: .concurrent)
           group.enter()
           q100.async(group: group) {
            KolAPI.getTags(category: "7", completion: { [weak self] response, _ in
               if let response = response {
                   print("tag7 ok")
                   MemoryCache.shared.tag7 = response["tags"] as? [[String: Any]]
               } else {
                   print("tag7 ok, but no data")
               }
               self?.group.leave()
             })
           }

        let q101 = DispatchQueue(label: "tag8", attributes: .concurrent)
        group.enter()
        q101.async(group: group) {
            KolAPI.getTags(category: "8", completion: { [weak self] response, _ in
            if let response = response {
                print("tag8 ok")
                MemoryCache.shared.tag8 = response["tags"] as? [[String: Any]]
            } else {
                print("tag8 ok, but no data")
            }
            self?.group.leave()
          })
        }

        let q102 = DispatchQueue(label: "tag2", attributes: .concurrent)
        group.enter()
        q102.async(group: group) {
            KolAPI.getTags(category: "2", completion: { [weak self] response, _ in
            if let response = response {
                print("tag2 ok")
                MemoryCache.shared.tag2 = response["tags"] as? [[String: Any]]
            } else {
                print("tag2 ok, but no data")
            }
            self?.group.leave()
          })
        }

        group.notify(queue: DispatchQueue.main) {
          // 完成所有 Call 後端 API 的動作
            print("完成所有 Call 後端 API 的動作...")
        }

    }

}

extension RegisterStep1VC: UITextFieldDelegate {
    func textFieldShouldReturn(_ textField: UITextField) -> Bool {
        self.view.endEditing(true)
        return false
    }
    
    func textFieldShouldBeginEditing(_ textField: UITextField) -> Bool {
        if textField.text == "綁定失敗" {
            textField.textColor = .black
            textField.text = ""
        }

        return true
    }
}
