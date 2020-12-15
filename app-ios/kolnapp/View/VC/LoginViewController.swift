//
//  LoginViewController.swift
//  kolnapp
//
//  Created by Victor_Huang on 2020/3/5.
//  Copyright © 2020 黃世維. All rights reserved.
//

import UIKit

class LoginViewController: UIViewController {
    override var prefersStatusBarHidden: Bool {
        return true
    }

    @IBOutlet weak var email: UITextField!
    @IBOutlet weak var password: UITextField!
    
    @IBAction func forgetPass(_ sender: Any) {
        let storyboard = UIStoryboard(name: "Main", bundle: nil)
        let vc = storyboard.instantiateViewController(withIdentifier: "ForgetPassVC") as! ForgetPassVC
        self.present(vc, animated: true, completion: nil)
    }

    @IBAction func check(_ sender: Any) {
        guard let url = URL(string: "https://www.kolnetworks.com/page/privacy-policy") else { return }
        UIApplication.shared.open(url)
    }

    @IBAction func login(_ sender: Any) {

        if LoginViewController.isLogin { return }

        var p: [String: Any] = Dictionary()
        let ttt = UserDefaults.standard.value(forKey: "deviceToken") ?? "test1234567890"

        if let email = email.text, let password = password.text {
            if email.isEmpty || password.isEmpty { return }
            p.updateValue(email.trimmingCharacters(in: .whitespacesAndNewlines), forKey: "email")
            p.updateValue(password.trimmingCharacters(in: .whitespacesAndNewlines), forKey: "password")
            p.updateValue(ttt, forKey: "notification_token")

            MemoryCache.shared.p = p

            LoginViewController.isLogin = true

            KolAPI.login(parameters: p, completion: { [weak self] response, error in
                LoginViewController.isLogin = false

                guard let self = self else { return }

                guard let response = response else {
                    print("login fail, response nil")
                    return
                }

                guard let token = response["token"] else {
                    print("login fail, get token fail")
//                    Router.showAlert(form: self, title: "帳號或密碼錯誤")
                    guard let errorMessage = response["message"] as? String else { return }
                    Router.showAlert(form: self, title: errorMessage)
                    return
                }

                KolAPI.token = token as? String
                print("login ok")
                UserDefaults.standard.set(token, forKey: "kolpassport")
                UserDefaults.standard.setValue(email.trimmingCharacters(in: .whitespacesAndNewlines), forKeyPath: "aemail")
//                UserDefaults.standard.setValue(password.trimmingCharacters(in: .whitespacesAndNewlines), forKeyPath: "pemail")
                UserDefaults.standard.synchronize()
                
                self.checkPermission() { [weak self] response, error in
                    if let isManager = response?["projects"] as? Bool {
                        MemoryCache.shared.isManager = isManager
                    }
                    
                    KolAPI.userProfile(completion: { [weak self] response, error in
                        if let response = response {
                          print("userProfile ok: \(response)")
                          MemoryCache.shared.userData = response["user"] as? [String: Any]
                            
                            let fb_url = MemoryCache.shared.userData?["fb_url"] as? String
                            let ig_url = MemoryCache.shared.userData?["ig_url"] as? String
                            let yt_url = MemoryCache.shared.userData?["yt_url"] as? String
                            let blog_url = MemoryCache.shared.userData?["blog_url"] as? String
                           
                            let isLinkIn: Bool = (fb_url != nil) || (ig_url != nil) || (yt_url != nil) || (blog_url != nil)

                            let audience_age = MemoryCache.shared.userData?["audience_age"] as? [Any]
                            let habbits = MemoryCache.shared.userData?["habbits"] as? [Any]
                            let community = MemoryCache.shared.userData?["community"] as? [Any]
                            let occupation = MemoryCache.shared.userData?["occupation"] as? [Any]
                            let contents = MemoryCache.shared.userData?["contents"] as? [Any]
                            let personality = MemoryCache.shared.userData?["personality"] as? [Any]

                            if isLinkIn && (audience_age?.count ?? 0) > 0 && (habbits?.count ?? 0) > 0 && (community!.count ) > 0 && (occupation?.count ?? 0) > 0 && (contents!.count ) > 0 && (personality!.count ) > 0 {
                                Router.showMainVC()
                            } else {
                                let storyboard = UIStoryboard(name: "Register", bundle: nil)
                                let vc = storyboard.instantiateViewController(withIdentifier: "RStep") as! UINavigationController
                                self?.present(vc, animated: true, completion: nil)
                            }
                            
                            
                        } else {
                          print("userProfile ok, but no data: \(error)")
                        }
                    })
                }

                
             })

        }

    }

    @IBAction func register(_ sender: Any) {
        let storyboard = UIStoryboard(name: "Register", bundle: nil)
        let vc = storyboard.instantiateViewController(withIdentifier: "RegisterVC") as! RegisterVC
//        self.navigationController?.pushViewController(vc, animated: true)
        self.present(vc, animated: true, completion: nil)

    }

    @IBOutlet weak var forgetIcon: UIImageView!
    private static var isLogin: Bool = false
    private var migrationY: CGFloat = 0
    
    private func checkPermission(completion: @escaping ([String: Any]?, Error?) -> Void) {
        KolAPI.checkPermissions(completion: completion)
    }

    override func viewDidLoad() {
        super.viewDidLoad()

        email.delegate = self
        password.delegate = self
        
//        KolAPI.token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1OTE1NTcyNTUsInN1YiI6IjM1MDNhNGIwLWEzNmYtMTFlYS04Y2UzLWZiYmFlNGE1NGQ4NiIsImlhdCI6MTU5MDk1MjQ1NX0.MTBlzli7bXHck-E2Xa7VoBKQIQRvxgqCj95MrVaxe9I"
//
//        Router.showMainVC()
        
        if let kolpassport = UserDefaults.standard.value(forKey: "kolpassport") as? String {
            KolAPI.token = kolpassport

            // get user
            self.showLottie()
            
            self.checkPermission() { [weak self] response, error in
                 if let isManager = response?["projects"] as? Bool {
                     MemoryCache.shared.isManager = isManager
                 }
                
                KolAPI.userProfile(completion: { [weak self] response, error in
                    
                    if let response = response, let user = response["user"] as? [String: Any] {
                        print("userProfile ok: \(response)")
                        MemoryCache.shared.userData = user
                        
                        let fb_url = MemoryCache.shared.userData?["fb_url"] as? String
                        let ig_url = MemoryCache.shared.userData?["ig_url"] as? String
                        let yt_url = MemoryCache.shared.userData?["yt_url"] as? String
                        let blog_url = MemoryCache.shared.userData?["blog_url"] as? String
                        
                        let isLinkIn: Bool = (fb_url != nil) || (ig_url != nil) || (yt_url != nil) || (blog_url != nil)
                        
                        let audience_age = MemoryCache.shared.userData?["audience_age"] as? [Any]
                        let habbits = MemoryCache.shared.userData?["habbits"] as? [Any]
                        let community = MemoryCache.shared.userData?["community"] as? [Any]
                        let occupation = MemoryCache.shared.userData?["occupation"] as? [Any]
                        let contents = MemoryCache.shared.userData?["contents"] as? [Any]
                        let personality = MemoryCache.shared.userData?["personality"] as? [Any]

                        if isLinkIn && (audience_age?.count ?? 0) > 0 && (habbits?.count ?? 0) > 0 && (community!.count ) > 0 && (occupation?.count ?? 0) > 0 && (contents!.count ) > 0 && (personality!.count ) > 0 {
                            Router.showMainVC()
                        } else {
                            let storyboard = UIStoryboard(name: "Register", bundle: nil)
                            let vc = storyboard.instantiateViewController(withIdentifier: "RStep") as! UINavigationController
                            self?.present(vc, animated: true, completion: nil)
                        }
                        
                    } else {
                        print("userProfile ok, but no data: \(error)")
                        self?.setupUI()
                    }
                    
                   DispatchQueue.main.async {
                        self?.stopLottie()
                   }
                   
               })
             }
            
            // go to main tab
            print("has kolpassport")
            return
        }

        print("no kolpassport")
        
    }

    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        setupUI()
    }

    func setupUI() {
        let paddingView: UIView = UIView(frame: CGRect(x: 0, y: 0, width: 5, height: 20))
        email.leftView = paddingView
        email.leftViewMode = .always

        let paddingView2: UIView = UIView(frame: CGRect(x: 0, y: 0, width: 5, height: 20))
        password.leftView = paddingView2
        password.leftViewMode = .always

        guard let myEmail = UserDefaults.standard.value(forKey: "aemail") as? String else {             print("no myEmail")
            return }
        print("has myEmail")
        email.text = myEmail
    }

    override func viewDidAppear(_ animated: Bool) {
        super.viewDidAppear(animated)
        if !MemoryCache.shared.eename.isEmpty {
            email.text = MemoryCache.shared.eename
        }
    }

}

extension LoginViewController: UITextFieldDelegate {
    func textFieldShouldReturn(_ textField: UITextField) -> Bool {
        self.view.endEditing(true)
        return false
    }
}
