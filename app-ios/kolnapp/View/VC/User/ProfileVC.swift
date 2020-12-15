//
//  ProfileVC.swift
//  kolnapp
//
//  Created by 黃世維 on 2020/4/11.
//  Copyright © 2020 黃世維. All rights reserved.
//

import UIKit
import GrowingTextView

class ProfileVC: UIViewController {

    override var prefersStatusBarHidden: Bool {
        return true
    }

    #warning("需要使用自定義rawValue定義default")
    enum Store: Int {
      case basic = 0, platform = 1, iCode = 2
    }
    
    // MARK:- 分享數據
    
    @IBOutlet weak var inviteBG: UIView!
    @IBOutlet weak var inviteCount: UILabel!
    @IBOutlet weak var inviteDoneBG: UIView!
    @IBOutlet weak var invitedDoneCount: UILabel!
    
    // MARK:- QRCODE
    @IBOutlet weak var qrCodeContainer: UIView!
    @IBOutlet weak var qrCodeImage: UIImageView!
    @IBOutlet weak var sharedInformationBG: UIView!
    @IBOutlet weak var sharedInformation: UILabel!
    @IBAction func go2shared(_ sender: Any) {
        guard let data = MemoryCache.shared.userData, let code = data["invitation_code"] as? String else { return }
        let shareDUri = "www.kolnetworks.com/app/events/invitation?code=\(code)&register=register"
        shareImageButton(content: shareDUri)
    }
    
    @IBOutlet weak var toolBarImage: UIImageView!
    @IBOutlet weak var userName: UILabel!
    @IBOutlet weak var backButton: UIButton!
    @IBAction func back(_ sender: Any) {
//        self.navigationController?.popToRootViewController(animated: true)
        self.dismiss(animated: true, completion: nil)
    }

    @IBOutlet weak var settingsButton: UIButton!
    @IBAction func go2Settings(_ sender: Any) {        
        UserDefaults.standard.removeObject(forKey: "kolpassport")
        UserDefaults.standard.removeObject(forKey: "aemail")
        KolAPI.token = nil
        MemoryCache.shared.userData = nil
        UserDefaults.standard.removeObject(forKey: "tmpInviteData")
        UserDefaults.standard.removeObject(forKey: "tmpParingData")
        UserDefaults.standard.removeObject(forKey: "tmpDoingData")
        UserDefaults.standard.removeObject(forKey: "tmpDoneData")
        UserDefaults.standard.removeObject(forKey: "tmpAppliedData")
        UserDefaults.standard.synchronize()
        
        let mainStoryboard = UIStoryboard(name: "Main", bundle: nil)
        let loginVC = mainStoryboard.instantiateViewController(withIdentifier: "LoginVC") as! LoginViewController
        self.view.window?.rootViewController = loginVC
        self.view.window?.makeKeyAndVisible()
    }

    @IBOutlet weak var subTab1: UILabel!
    @IBOutlet weak var subTab2: UILabel!
    @IBOutlet weak var indicator1: UIView!
    @IBOutlet weak var indicator2: UIView!
    @IBOutlet weak var subTab3: UILabel!
    @IBOutlet weak var indicator3: UIView!

    @IBOutlet weak var container: UIScrollView!
    @IBOutlet weak var basicTable: UITableView!
    @IBOutlet weak var plateformTable: UITableView!

    @IBOutlet weak var updateBtn: UIButton!
    @IBAction func updateUserData(_ sender: Any) {
        if let data = MemoryCache.shared.register_user_data {
            self.showLottie()
            KolAPI.putUser(parameters: data) { [weak self] response, _ in
                self?.stopLottie()
                print("updateUser putUser response: \(response)")
            }
        }
    }
    
    

    // TODO: 接資料在實作
    private var currentIndex: Store = .basic
    private var canScroll: Bool = true
    private let group: DispatchGroup = DispatchGroup()
    private let datePicker = UIDatePicker()

    weak var pv: GrowingTextView?

    override func viewDidLoad() {
        super.viewDidLoad()
        
        automaticallyAdjustsScrollViewInsets = false

        self.indicator1.isHidden = false
        self.indicator2.isHidden = true
        self.indicator3.isHidden = true

        let tap1 = UITapGestureRecognizer(target: self, action: #selector(scrollToDoingBoard))
        subTab1.isUserInteractionEnabled = true
        subTab1.addGestureRecognizer(tap1)

        let tap2 = UITapGestureRecognizer(target: self, action: #selector(scrollToParing))
        subTab2.isUserInteractionEnabled = true
        subTab2.addGestureRecognizer(tap2)
        
        let tap3 = UITapGestureRecognizer(target: self, action: #selector(scrollToIcode))
        subTab3.isUserInteractionEnabled = true
        subTab3.addGestureRecognizer(tap3)

        updateBtn.layer.cornerRadius = 4

        self.toolBarImage.layer.cornerRadius = self.toolBarImage.frame.height/2
        guard let data = MemoryCache.shared.userData else { return }
        if let photo = data["photo"] as? String {
            toolBarImage.sd_setImage(with: URL(string: photo), placeholderImage: UIImage())
        }

        if let name = data["display_name"] as? String {
            userName.text = name
        }

        self.container.delegate = self
        basicTable.delegate = self
        basicTable.dataSource = self
        basicTable.register(UINib(nibName: "ProfileType1Cell", bundle: nil), forCellReuseIdentifier: "ProfileType1Cell")
        basicTable.register(UINib(nibName: "ProfileType2Cell", bundle: nil), forCellReuseIdentifier: "ProfileType2Cell")
        basicTable.layer.cornerRadius = 8
//        basicTable.isUserInteractionEnabled = false
        //
        plateformTable.delegate = self
        plateformTable.dataSource = self
        plateformTable.register(UINib(nibName: "ProfileType1Cell", bundle: nil), forCellReuseIdentifier: "ProfileType1Cell")
        plateformTable.register(UINib(nibName: "ProfileCardCell", bundle: nil), forCellReuseIdentifier: "ProfileCardCell")
//        plateformTable.layer.cornerRadius = 8
//        plateformTable.isUserInteractionEnabled = false
        plateformTable.clipsToBounds = false
        
        // qrcode
        self.sharedInformationBG.layer.cornerRadius = 16
        self.inviteBG.layer.cornerRadius = 16
        self.inviteDoneBG.layer.cornerRadius = 16
        self.qrCodeContainer.layer.cornerRadius = 10
        self.qrCodeContainer.layer.makeShadow(color: .lightGray, x: 0, y: 6, blur: 10, spread: 0)
        
        guard let code = data["invitation_code"] as? String else { return }
        let shareDUri = "www.kolnetworks.com/app/events/invitation?code=\(code)&register=register"
        if let imgQRCode = generateQRCode(from: shareDUri), let blurQrCode = imgQRCode.ciImage {
            let transformedImage = displayQRCodeImage(qrCode: blurQrCode)
            qrCodeImage.image = UIImage(ciImage: transformedImage)
        }
        
        if let inviteCount = data["invited"] as? Int {
            self.inviteCount.text = "註冊數：\(inviteCount)"
        }
        
        if let inviteDoneCount = data["invited_done_first_project"] as? Int {
            self.invitedDoneCount.text = "成功接案：\(inviteDoneCount)"
        }
        
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        if let data = MemoryCache.shared.register_user_data {
            MemoryCache.shared.register_user_data?.removeAll()
        }
        
        loading()
    }

    @objc func scrollToDoingBoard() {
        scrollTo(container, index: 0)
    }

    @objc func scrollToParing() {
        scrollTo(container, index: 1)
    }
    
    @objc func scrollToIcode() {
        scrollTo(container, index: 2)
    }

    // TODO: 要設定timeout.
    func loading() {
        // 顯示進度條.
        self.showLottie()
        // 抓取資料.
        let q1 = DispatchQueue(label: "user_queue", attributes: .concurrent)
        group.enter()
        q1.async(group: group) {
          KolAPI.userProfile(completion: { [weak self] response, _ in
            if let response = response {
                print("user_queue ok")
                MemoryCache.shared.userData = response["user"] as? [String: Any]
            } else {
                print("user_queue ok, but no data")
            }
            self?.group.leave()
          })
        }

        group.notify(queue: DispatchQueue.main) {
          // 完成所有 Call 後端 API 的動作
          print("完成所有 Call 後端 API 的動作...")
            self.basicTable.clipsToBounds = false
            self.basicTable.layer.makeShadow(color: .lightGray, x: 0, y: 6, blur: 10, spread: 0)
//            self.plateformTable.clipsToBounds = false
//            self.plateformTable.layer.makeShadow(color: .lightGray, x: 0, y: 6, blur: 10, spread: 0)

            self.basicTable.reloadData()
            self.plateformTable.reloadData()
            self.stopLottie()
        }

    }

}

extension ProfileVC {
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
        let doneButton = UIBarButtonItem(title: "Cancel", style: .plain, target: self, action: #selector(cancelDatePicker))
        let spaceButton = UIBarButtonItem(barButtonSystemItem: UIBarButtonItem.SystemItem.flexibleSpace, target: nil, action: nil)
        let cancelButton = UIBarButtonItem(title: "Done", style: .plain, target: self, action: #selector(donedatePicker))

        toolbar.setItems([doneButton, spaceButton, cancelButton], animated: false)

        pv?.inputAccessoryView = toolbar
        pv?.inputView = datePicker
    }

     @objc func donedatePicker() {
      let formatter = DateFormatter()
      formatter.dateFormat = "yyyy-MM-dd"
      pv?.text = "  \(formatter.string(from: datePicker.date))"
        MemoryCache.shared.register_user_data?.updateValue(Int(datePicker.date.timeIntervalSince1970), forKey: "birthday")

        self.view.endEditing(true)
    }

    @objc func cancelDatePicker() {
       self.view.endEditing(true)
     }
}

extension ProfileVC: UIScrollViewDelegate {
    func scrollViewDidEndDecelerating(_ scrollView: UIScrollView) {
        let index = lrint((Double(scrollView.contentOffset.x / scrollView.bounds.size.width)))
        print("CCC: \(index)")
        self.currentIndex = Store(rawValue: index) ?? Store.basic
        print("C: \(self.currentIndex)")

        switchIndicator()
    }

    func scrollTo(_ scrollView: UIScrollView, index: Int) {
        if canScroll {
            canScroll = false
            var frame = scrollView.frame
            frame.origin.x = frame.size.width * CGFloat(index)
            frame.origin.y = 0
            scrollView.scrollRectToVisible(frame, animated: true)

            self.currentIndex = Store(rawValue: index) ?? Store.basic
            switchIndicator()
            canScroll = true
        }
    }

    func switchIndicator() {
        switch self.currentIndex {
        case .basic:
           self.indicator1.isHidden = false
           self.indicator2.isHidden = true
           self.indicator3.isHidden = true
        case .platform:
           self.indicator1.isHidden = true
           self.indicator2.isHidden = false
           self.indicator3.isHidden = true
        case .iCode:
            self.indicator1.isHidden = true
            self.indicator2.isHidden = true
            self.indicator3.isHidden = false
        }
    }
}

extension ProfileVC: UITableViewDelegate, UITableViewDataSource {
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        print(self.currentIndex)
        if tableView == basicTable {
            return 8-1
        }

        if tableView == plateformTable {
            return 4
        }

        return 0
    }

    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        if tableView == basicTable {
            let cell = basicTable.dequeueReusableCell(withIdentifier: "ProfileType1Cell", for: indexPath) as? ProfileType1Cell
            cell?.profileVlaue.font = .systemFont(ofSize: 17)
            cell?.editBtn.isHidden = true

            guard let data = MemoryCache.shared.userData else { return UITableViewCell() }
            if indexPath.row == 0 {
                cell?.profileKey.text = "真實姓名"
                guard let name = data["display_name"] as? String else {
                     cell?.profileVlaue.text = " "
                     return cell!
                }
                cell?.profileVlaue.text = name
                cell?.isUserInteractionEnabled = false
            }
            if indexPath.row == 1 {
               cell?.profileKey.text = "Email"
                guard let email = data["email"] as? String else {
                    cell?.profileVlaue.text = " "
                    return cell!
               }
               cell?.profileVlaue.text = email
                cell?.isUserInteractionEnabled = false
            }
            if indexPath.row == 2 {
                cell?.profileKey.text = "手機電話"
                 guard let phone = data["phone"] as? String else {
                     cell?.profileVlaue.text = " "
                     return cell!
                }
                cell?.profileVlaue.text = phone
            }
            if indexPath.row == 3 {
                cell?.profileKey.text = "居住及通訊地址"
                 guard let address = data["address"] as? String else {
                     cell?.profileVlaue.text = " "
                     return cell!
                }
                cell?.profileVlaue.text = address
            }
            if indexPath.row == 4 {
                cell?.profileKey.text = "生日"
                 guard let birthday = data["birthday"] as? Int else {
                     cell?.profileVlaue.text = " "
                     return cell!
                }
                self.pv = cell?.profileVlaue
                self.showDatePicker()

                let date = Date(timeIntervalSince1970: TimeInterval(birthday))
                let dateFormatter = DateFormatter()
                dateFormatter.timeZone = TimeZone(abbreviation: "GMT+8") //Set timezone that you want
                dateFormatter.locale = NSLocale.current
                dateFormatter.dateFormat = "yyyy-MM-dd" //Specify your format that you want
                let strDate = dateFormatter.string(from: date)

                cell?.profileVlaue.text = strDate
            }
            if indexPath.row == 5 {
                cell?.profileKey.text = "LINE"
                 guard let line_id = data["line_id"] as? String else {
                     cell?.profileVlaue.text = " "
                     return cell!
                }
                cell?.profileVlaue.text = line_id
            }
            
            if indexPath.row == 6 {
                cell?.go2TagDelegate = { [weak self] in
                    let storyboard = UIStoryboard(name: "Register", bundle: nil)
                    let registerStep4VC = storyboard.instantiateViewController(withIdentifier: "RegisterStep4VC") as! RegisterStep4VC
                    registerStep4VC.modalPresentationStyle = .fullScreen
                    registerStep4VC.done = "完成"
                    self?.present(registerStep4VC, animated: true, completion: nil)
                }
                cell?.profileKey.text = "更新個人標籤"
                cell?.profileVlaue.text = ""
                cell?.editBtn.isHidden = false
            }
            return cell!
        }

        // TODO: 只要帳號
        if tableView == plateformTable {
            let cell = plateformTable.dequeueReusableCell(withIdentifier: "ProfileCardCell", for: indexPath) as? ProfileCardCell
            cell?.value1.font = .systemFont(ofSize: 17)
            guard let data = MemoryCache.shared.userData else { return UITableViewCell() }

            if indexPath.row == 0 {
                cell?.plateFrom.text = "Instagram"
                // Peter說先用display_name當ig name
                if let ig_url = data["ig_url"] as? String {
                    let gg_url = URL(string: ig_url)?.lastPathComponent
                    cell?.value1.text = gg_url
                    
                    cell?.sMoney.text = "建議報價"
                    if let ig_expect_price = data["ig_expect_price"] as? String {
                        cell?.value2.text = ig_expect_price
                    } else {
                        cell?.value2.text = ""
                    }
                    
                } else {
                    cell?.value1.placeholder = "請輸入Instagram ID"
                }
                
                cell?.openPlateformLinkInWebview = { [weak self] in
                    guard let name = cell?.value1.text.trimmingCharacters(in: .whitespacesAndNewlines), !name.isEmpty else {
                        return
                    }

                    self?.showLottie()
                    var p: [String: Any] = Dictionary()
                    p.updateValue(name, forKey: "name")
                    KolAPI.link_ig(parameters: p, completion: { [weak self] response, _ in

                        guard let self = self else { return }

                        guard let response = response else {
                           print("register fail, response nil")
                           return
                        }

                        if let success = response["success"] as? Bool, success == true {
                            cell?.value1.textColor = .black
                        }
                        
                        cell?.value1.placeholder = "請輸入Instagram ID"
                        
                        self.stopLottie()
                    })
                }
                
            }

            if indexPath.row == 1 {
                cell?.plateFrom.text = "Facebook"
                // Peter說先用display_name當ig name
                if let fb_url = data["fb_url"] as? String {
                    cell?.value1.text = fb_url
                    
                    cell?.sMoney.text = "建議報價"
                    if let fb_expect_price = data["fb_expect_price"] as? String {
                        cell?.value2.text = fb_expect_price
                    } else {
                        cell?.value2.text = ""
                    }
                    
                }
                
                cell?.value1.placeholder = "請輸入 Facebook 網址"

                cell?.openPlateformLinkInWebview = { [weak self] in
                    guard let url = cell?.value1.text.trimmingCharacters(in: .whitespacesAndNewlines), !url.isEmpty else {
                        return
                    }
                    
                    var turl = ""
                    if !url.lowercased().contains("https://") && !url.lowercased().contains("http://") {
                        turl = "https://" + url
                    } else {
                        turl = url
                    }
                    
                    self?.showLottie()
                    var p: [String: Any] = Dictionary()
                    p.updateValue(turl, forKey: "url")
                    KolAPI.link_fb(parameters: p, completion: { [weak self] response, _ in

                        guard let self = self else { return }

                        guard let response = response else {
                           print("register fail, response nil")
                           return
                        }

                        if let success = response["success"] as? Bool, success == true {
                            cell?.value1.textColor = .black
                        } else {
                            cell?.value1.placeholder = "請輸入 Facebook 網址"
                        }
                        
                        self.stopLottie()
                    })
                }
                
            }
            
            if indexPath.row == 2 {
                cell?.plateFrom.text = "Youtube"
                // Peter說先用display_name當ig name
                if let yt_url = data["yt_url"] as? String {
                    cell?.value1.text = yt_url
                    
                    cell?.sMoney.text = "建議報價"
                    if let yt_expect_price = data["yt_expect_price"] as? String {
                        cell?.value2.text = yt_expect_price
                    } else {
                        cell?.value2.text = ""
                    }
                    
                }
                
                cell?.value1.placeholder = "請輸入包含 /channel 的網址"
                
                cell?.openPlateformLinkInWebview = { [weak self] in
                    guard let url = cell?.value1.text.trimmingCharacters(in: .whitespacesAndNewlines), !url.isEmpty else {
                        return
                    }
                    
                    var turl = ""
                    if !url.lowercased().contains("https://") && !url.lowercased().contains("http://") {
                        turl = "https://" + url
                    } else {
                        turl = url
                    }
                    
                    self?.showLottie()
                    var p: [String: Any] = Dictionary()
                    p.updateValue(turl, forKey: "url")
                    KolAPI.link_yt(parameters: p, completion: { [weak self] response, _ in

                        guard let self = self else { return }

                        guard let response = response else {
                           print("register fail, response nil")
                           return
                        }

                        if let success = response["success"] as? Bool, success == true {
                            cell?.value1.textColor = .black
                        }
                        
                        cell?.value1.placeholder = "請輸入包含 /channel 的網址"
                        
                        self.stopLottie()
                    })
                }
            }
            
            if indexPath.row == 3 {
                cell?.plateFrom.text = "Blog"
                // Peter說先用display_name當ig name
                if let blog_url = data["blog_url"] as? String {
                    cell?.value1.text = blog_url
                    
                    cell?.sMoney.text = "建議報價"
                    if let blog_expect_price = data["blog_expect_price"] as? String {
                        cell?.value2.text = blog_expect_price
                    } else {
                        cell?.value2.text = ""
                    }
                    
                }
                
                cell?.value1.placeholder = "請輸入部落格網址"
                
                cell?.openPlateformLinkInWebview = { [weak self] in
                    guard let url = cell?.value1.text.trimmingCharacters(in: .whitespacesAndNewlines), !url.isEmpty else {
                        return
                    }
                    
                    var turl = ""
                    if !url.lowercased().contains("https://") && !url.lowercased().contains("http://") {
                        turl = "https://" + url
                    } else {
                        turl = url
                    }
                    
                    self?.showLottie()
                    var p: [String: Any] = Dictionary()
                    p.updateValue(turl, forKey: "url")
                    KolAPI.link_blog(parameters: p, completion: { [weak self] response, _ in

                        guard let self = self else { return }

                        guard let response = response else {
                           print("register fail, response nil")
                           return
                        }

                        if let success = response["success"] as? Bool, success == true {
                            cell?.value1.textColor = .black
                        } else {
                            cell?.value1.placeholder = "請輸入部落格網址"
                        }
                        
                        self.stopLottie()
                    })
                }
                
            }
            return cell!
        }

        return UITableViewCell()
    }

    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {

    }

}

// QRCODE GENERATOR
extension ProfileVC {
    /// 分享到社群
    func shareImageButton(content: String) {
        // set up activity view controller
        let imageToShare = [ content ]
        let activityViewController = UIActivityViewController(activityItems: imageToShare, applicationActivities: nil)
        activityViewController.popoverPresentationController?.sourceView = self.view // so that iPads won't crash

        self.present(activityViewController, animated: true, completion: nil)
    }
    
    /// 產生QRCODE
    func generateQRCode(from string: String) -> UIImage? {
        let data = string.data(using: String.Encoding.ascii)

        if let filter = CIFilter(name: "CIQRCodeGenerator") {
            filter.setValue(data, forKey: "inputMessage")
            filter.setValue("Q", forKey: "inputCorrectionLevel")
            let transform = CGAffineTransform(scaleX: 3, y: 3)

            if let output = filter.outputImage?.transformed(by: transform) {
                return UIImage(ciImage: output)
            }
        }

        return nil
    }
    
    /// 縮放QRcode, 讓圖片清晰
    func displayQRCodeImage(qrCode: CIImage) -> CIImage {
        let scaleX = qrCodeImage.frame.size.width / qrCode.extent.size.width
        let scaleY = qrCodeImage.frame.size.height / qrCode.extent.size.height
        let transformedImage = qrCode.transformed(by: CGAffineTransform(scaleX: scaleX, y: scaleY))
        return transformedImage
    }

}
