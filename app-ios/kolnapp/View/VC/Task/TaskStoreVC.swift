//
//  TaskStoreVC.swift
//  kolnapp
//
//  Created by Victor_Huang on 2020/2/12.
//  Copyright © 2020 Victor_Huang. All rights reserved.
//

import UIKit
import SDWebImage

/**
 platform = {
   1: 'fb',
   2: 'ig',
   3: 'yt',
   4: 'blog',
 };
 */
enum platform: Int {
    case fb = 1
    case ig = 2
    case yt = 3
    case blog = 4

    var toString: String {
        switch self {
        case .fb: return "FB"
        case .ig: return "IG"
        case .yt: return "YT"
        case .blog: return "BL"
        }
    }
}

class TaskStoreVC: UIViewController {

    override var prefersStatusBarHidden: Bool {
        return true
    }

    #warning("需要使用自定義rawValue定義default")
    enum Store: Int {
        case invited = 0, paring = 1, applied = 2
    }

    @IBOutlet weak var avatar: UIImageView!
    @IBOutlet weak var tabName: UILabel!
    @IBOutlet weak var doingBoard: UILabel!
    @IBOutlet weak var paringBoard: UILabel!
    @IBOutlet weak var acceptBoard: UILabel!
    @IBOutlet weak var container: UIScrollView!

    @IBOutlet var doingTable: UITableView!
    @IBOutlet var taskTable: UITableView!
//    @IBOutlet var acceptTable: UITableView!
    @IBOutlet weak var indicator: UIView!
    @IBOutlet weak var indicator2: UIView!
    @IBOutlet weak var indicator3: UIView!

    // TODO: 接資料在實作
    private var currentIndex: Store = .invited
    private var canScroll: Bool = true
    private let group: DispatchGroup = DispatchGroup()

    private var tmpInviteData: [[String: Any]] = .init()
    private var tmpParingData: [[String: Any]] = .init()
    
    private var canNotificationPush: Bool = true
    
    override func viewDidLoad() {
        super.viewDidLoad()

        switchIndicator()

        container.delegate = self

        doingTable.delegate = self
        doingTable.dataSource = self
        doingTable.register(UINib(nibName: "TaskCellTableViewCell", bundle: nil), forCellReuseIdentifier: "TaskCellTableViewCell")

        taskTable.delegate = self
        taskTable.dataSource = self
        taskTable.register(UINib(nibName: "TaskCellTableViewCell", bundle: nil), forCellReuseIdentifier: "TaskCellTableViewCell")

        self.avatar.layer.cornerRadius = self.avatar.frame.height/2
        let tap0 = UITapGestureRecognizer(target: self, action: #selector(go2Profile))
        avatar.isUserInteractionEnabled = true
        avatar.addGestureRecognizer(tap0)

        let tap1 = UITapGestureRecognizer(target: self, action: #selector(scrollToDoingBoard))
        doingBoard.isUserInteractionEnabled = true
        doingBoard.addGestureRecognizer(tap1)

        let tap2 = UITapGestureRecognizer(target: self, action: #selector(scrollToParing))
        paringBoard.isUserInteractionEnabled = true
        paringBoard.addGestureRecognizer(tap2)

        let tap3 = UITapGestureRecognizer(target: self, action: #selector(scrollToAcceptBoard))
        acceptBoard.isUserInteractionEnabled = true
        acceptBoard.addGestureRecognizer(tap3)

        container.contentInsetAdjustmentBehavior = .never
        
        NotificationCenter.default.addObserver(self, selector: #selector(self.go2DetailProject(notification:)), name: Notification.Name("ClickNotification"), object: nil)

        if MemoryCache.shared.kolNotifyProjectUUID != "" { // 其他類型的推送通知不轉頁.
            KolAPI.getProjectByUUID(project_uuid: MemoryCache.shared.kolNotifyProjectUUID) { [weak self] response, _ in
                  if let response = response, let project = response["project"] as? [String: Any] {
                      print("project is : \(project)")
                    
                    if let taskDetailVC = UIStoryboard(name: "TaskBoard", bundle: nil).instantiateViewController(withIdentifier: "TaskDetailVC") as? TaskDetailVC {
                        taskDetailVC.viewModel = project
                        
                        // 不需要這邊，重構後由內部自己判斷
                        if MemoryCache.shared.kolNotify == 1 {
                            taskDetailVC.currentSubTab = .paring
                        }
                        
                        if MemoryCache.shared.kolNotify == 2 {
                            taskDetailVC.currentSubTab = .invited
                        }
                        
                        self?.tabBarController?.setTabBarVisible(visible: false, animated: true)
                        guard let navigator = self?.navigationController  else { return }
                        // TODO: 等Peter修好再繼續串
                        navigator.pushViewController(taskDetailVC, animated: false)

                  } else {
                      print("no data")
                  }
                }
            }
        }
    }
    
    @objc private func go2DetailProject(notification: NSNotification){
        print("kolNotify is \(MemoryCache.shared.kolNotify) and UUID is \(MemoryCache.shared.kolNotifyProjectUUID)")
        // TODO: 等Peter修好再繼續串
        
        if MemoryCache.shared.kolNotifyProjectUUID != "" { // 其他類型的推送通知不轉頁.
            self.showLottie()
            KolAPI.getProjectByUUID(project_uuid: MemoryCache.shared.kolNotifyProjectUUID) { [weak self] response, _ in
                  if let response = response, let project = response["project"] as? [String: Any] {
                      print("project is : \(project)")
                    
                    if let taskDetailVC = UIStoryboard(name: "TaskBoard", bundle: nil).instantiateViewController(withIdentifier: "TaskDetailVC") as? TaskDetailVC {
                        taskDetailVC.viewModel = project
                        
                        // 不需要這邊，重構後由內部自己判斷
                        if MemoryCache.shared.kolNotify == 1 {
                            taskDetailVC.currentSubTab = .paring
                        }
                        
                        if MemoryCache.shared.kolNotify == 2 {
                            taskDetailVC.currentSubTab = .invited
                        }
                        
                        self?.tabBarController?.setTabBarVisible(visible: false, animated: true)
                        guard let navigator = self?.navigationController  else { return }
                        // TODO: 等Peter修好再繼續串
                        navigator.pushViewController(taskDetailVC, animated: false)

                  } else {
                      print("no data")
                  }
                    self?.stopLottie()
                }
            }
        }
        
    }
        

    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        self.tabBarController?.setTabBarVisible(visible: true, animated: true)
        
        MemoryCache.shared.inviteData.removeAll()
        MemoryCache.shared.paringData.removeAll()
        self.loading()
    }
    
    override func viewDidAppear(_ animated: Bool) {
        super.viewDidAppear(animated)
        self.navigationController?.setNavigationBarHidden(true, animated: animated)
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

    @objc func scrollToAcceptBoard() {
        scrollTo(container, index: 2)
    }

    // TODO: 要設定timeout.
    
    
    @objc
    func loading() {

        // 暫存舊資料
//        tmpInviteData.removeAll()
//        tmpParingData.removeAll()
//        tmpInviteData.append(contentsOf: MemoryCache.shared.inviteData)
//        tmpParingData.append(contentsOf: MemoryCache.shared.paringData)
        
        if let tData = UserDefaults.standard.data(forKey: "tmpInviteData") {
            let i1 = try? JSONSerialization.jsonObject(with: tData, options: []) as? [[String: Any]]
            self.tmpInviteData = i1 ?? []
        }
        
        if let tData = UserDefaults.standard.data(forKey: "tmpParingData") {
            let i2 = try? JSONSerialization.jsonObject(with: tData, options: []) as? [[String: Any]]
            self.tmpParingData = i2 ?? []
        }

        // 移除舊資料.
        MemoryCache.shared.clearTempData()

        self.showLottie()

        // 抓取資料.
        let q1 = DispatchQueue(label: "invitaion_queue", attributes: .concurrent)
        group.enter()
        q1.async(group: group) {
          KolAPI.invitaion(completion: { [weak self] response, _ in
            if let response = response, let iData = response["projects"] as? [[String: Any]] {
                print("invitaion ok")
                MemoryCache.shared.inviteData.append(contentsOf: iData)
            } else {
                print("invitaion ok, but no data")
            }
            self?.group.leave()
          })
        }

        let q2 = DispatchQueue(label: "paring_queue", attributes: .concurrent)
        group.enter()
        q2.async(group: group) {
          KolAPI.paring(completion: { [weak self] response, _ in
            if let response = response, let pData = response["projects"] as? [[String: Any]] {
                print("paring ok")
                MemoryCache.shared.paringData.append(contentsOf: pData)
            } else {
                print("paring ok, but no data")
            }
            self?.group.leave()
          })
        }

        let q100 = DispatchQueue(label: "user_queue", attributes: .concurrent)
           group.enter()
           q100.async(group: group) {
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

        group.notify(queue: DispatchQueue.main) { [weak self] in
            MemoryCache.shared.inviteData = MemoryCache.shared.inviteData.sorted(by: { ($0["created_at"] as! Int) > ($1["created_at"] as! Int) })
            MemoryCache.shared.paringData = MemoryCache.shared.paringData.sorted(by: { ($0["created_at"] as! Int) > ($1["created_at"] as! Int) })

            
            // TODO: 紅點尚未驗證
            // 先暫存資料, 方便每次重啟時比對是否要顯示紅點.
            let iData = try? JSONSerialization.data(withJSONObject: MemoryCache.shared.inviteData, options: [])
            let iData2 = try? JSONSerialization.data(withJSONObject: MemoryCache.shared.paringData, options: [])
            UserDefaults.standard.setValue(iData, forKeyPath: "tmpInviteData")
            UserDefaults.standard.setValue(iData2 , forKey: "tmpParingData")
            UserDefaults.standard.synchronize()

            // FIXME: 抽象成一個演算法.
            var showBadge: Bool = false
            if let tmpInviteData = self?.tmpInviteData {
                if tmpInviteData.count != MemoryCache.shared.inviteData.count && MemoryCache.shared.inviteData.count > 0 {
                     print(" tmpInviteData顯示紅點點1")
                    showBadge = true
                 } else if tmpInviteData.count == MemoryCache.shared.inviteData.count && tmpInviteData.count  > 0 {
                    var showRedDot: Bool = true
                    for element in tmpInviteData {
                        if MemoryCache.shared.inviteData.contains(where: { $0["uuid"] as! String == element["uuid"] as! String }) {
                            showRedDot = false
                        }
                    }
                    if showRedDot {
                        print(" tmpInviteData顯示紅點點2")
                        showBadge = true
                    }
                 }
            }
            
            
            if let tmpParingData = self?.tmpParingData {
                if tmpParingData.count != MemoryCache.shared.paringData.count && MemoryCache.shared.paringData.count > 0 {
                     print("tmpParingData 顯示紅點點1")
                    showBadge = true
                 } else if tmpParingData.count == MemoryCache.shared.paringData.count && tmpParingData.count  > 0 {
                    var showRedDot: Bool = true
                    for element in tmpParingData {
                        if MemoryCache.shared.paringData.contains(where: { $0["uuid"] as! String == element["uuid"] as! String }) {
                            showRedDot = false
                        }
                    }
                    
                    if showRedDot {
                        print(" tmpParingData顯示紅點點2")
                        showBadge = true
                    }
                 }
            }
            
            self?.badageDot(show: showBadge)

            // 完成所有 Call 後端 API 的動作
            self?.view.layoutIfNeeded()
            self?.doingTable.reloadData {
                self?.taskTable.reloadData {
                    self?.stopLottie()
                    
                    guard let data = MemoryCache.shared.userData else { return }
                    if let photo = data["photo"] as? String {
                        self?.avatar.sd_setImage(with: URL(string: photo), placeholderImage: UIImage())
                    }
                }
            }
            
        }

    }
    
    // FIXME: 移動到tabVC中
    func badageDot(show: Bool) {
        if show {
            tabBarItem.badgeValue = "●"
            tabBarItem.badgeColor = .clear
            tabBarItem.setBadgeTextAttributes([NSAttributedString.Key.foregroundColor: UIColor.red], for: .normal)
        }else {
            tabBarItem.badgeValue = nil
        }
    }
}

extension TaskStoreVC: UITableViewDelegate, UITableViewDataSource {
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        print(self.currentIndex)
        if tableView == taskTable {
            if MemoryCache.shared.inviteData.count == 0 {
                return 1
            }else {
                print("inviteData: \(MemoryCache.shared.inviteData.count)")
                return MemoryCache.shared.inviteData.count
            }
        }

        if tableView == doingTable {
            if MemoryCache.shared.paringData.count == 0 {
                return 1
            }else {
                print("inviteData: \(MemoryCache.shared.paringData.count)")
                return MemoryCache.shared.paringData.count
            }
        }
        return 0
    }

    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = taskTable.dequeueReusableCell(withIdentifier: "TaskCellTableViewCell", for: indexPath) as? TaskCellTableViewCell
        if tableView == taskTable {
            if MemoryCache.shared.inviteData.count == 0 {
                // 空白卡片
                cell?.isEmptyCard(idEmptyCard: true, info1: "很快就會有新的合作囉！" , info2: "未來如果有適合你的合作專案，我們會馬上通知你。")
                return cell!
            }
            cell?.isEmptyCard(idEmptyCard: false)
            let data = MemoryCache.shared.inviteData[indexPath.row]
            print("QQQQ \(data["platform"] as! Int)")
            if let photo = data["photo"] as? String {
                cell?.cellImage.sd_setImage(with: URL(string: photo), placeholderImage: UIImage())
            }
            cell?.intro.text = data["name"] as? String
            cell?.platform.text = platform(rawValue: data["platform"] as! Int)?.toString
            let ss = Date.getDateFromTimeStamp(timeStamp: data["invite_deadline"] as! Double, format: "yyyy-MM-dd")
            cell?.deadline.text = "截止日期 \(ss)"

            if let pp = data["project_kols"] as? [[String: Any]] {
                if let nd: Int = pp[0]["kol_price"] as? Int {
                    cell?.ticket.text = "NTD \(nd)"
                }
            }

        }

        if tableView == doingTable {
            if MemoryCache.shared.paringData.count == 0 {
                // 空白卡片
                cell?.isEmptyCard(idEmptyCard: true, info1: "新專案準備中。敬請期待" , info2: "很快就會有新專案上架囉！歡迎定期查看此頁面。")
                return cell!
            }
            cell?.isEmptyCard(idEmptyCard: false)
            let data = MemoryCache.shared.paringData[indexPath.row]
            print("QQQQ \(data["platform"] as! Int)")
            if let photo = data["photo"] as? String {
                cell?.cellImage.sd_setImage(with: URL(string: photo), placeholderImage: UIImage())

            }
            cell?.intro.text = data["name"] as? String
            cell?.platform.text = platform(rawValue: data["platform"] as! Int)?.toString
            let ss = Date.getDateFromTimeStamp(timeStamp: data["invite_deadline"] as! Double, format: "yyyy-MM-dd")
            cell?.deadline.text = "截止日期 \(ss)"

            // 不顯示價格了, 讓USer報價ㄡ.
            cell?.ticket.text = ""

        }
        
        return cell!
    }

    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        if let taskDetailVC = UIStoryboard(name: "TaskBoard", bundle: nil).instantiateViewController(withIdentifier: "TaskDetailVC") as? TaskDetailVC {

            taskDetailVC.currentIndex = indexPath
            if tableView == taskTable {
                if MemoryCache.shared.inviteData.count > 0 {
                    taskDetailVC.viewModel = MemoryCache.shared.inviteData[indexPath.row]
                    
                    // 不需要這邊，重構後由內部自己判斷
                    taskDetailVC.currentSubTab = self.currentIndex

                    self.tabBarController?.setTabBarVisible(visible: false, animated: true)
                    if let navigator = self.navigationController {
                        navigator.pushViewController(taskDetailVC, animated: true)
                    }
                }
            }

            if tableView ==  doingTable {
                if MemoryCache.shared.paringData.count > 0 {
                    taskDetailVC.viewModel = MemoryCache.shared.paringData[indexPath.row]
                    
                    // 不需要這邊，重構後由內部自己判斷
                    taskDetailVC.currentSubTab = self.currentIndex

                    self.tabBarController?.setTabBarVisible(visible: false, animated: true)
                    if let navigator = self.navigationController {
                        navigator.pushViewController(taskDetailVC, animated: true)
                    }
                }
            }
            

        }
    }

}

extension TaskStoreVC: UIScrollViewDelegate {
    func scrollViewDidEndDecelerating(_ scrollView: UIScrollView) {
        if scrollView == container {
            let index = lrint((Double(scrollView.contentOffset.x / scrollView.bounds.size.width)))
            print("CCC: \(index)")
            self.currentIndex = Store(rawValue: index) ?? Store.invited
            print("C: \(self.currentIndex)")

            switchIndicator()
        }

        if scrollView == doingTable {
            print("scrollViewDidEndDecelerating doingTable")

            if scrollView.isBouncing {
                print("scrollViewDidEndDecelerating doingTable isBouncing")
                self.loading()
            }
        }

        if scrollView == taskTable {
            print("scrollViewDidEndDecelerating taskTable")

            if scrollView.isBouncing {
                print("scrollViewDidEndDecelerating taskTable isBouncing")
                self.loading()
            }
        }
    }

    func scrollTo(_ scrollView: UIScrollView, index: Int) {
        if canScroll {
            canScroll = false
            var frame = scrollView.frame
            frame.origin.x = frame.size.width * CGFloat(index)
            frame.origin.y = 0
            scrollView.scrollRectToVisible(frame, animated: true)

            self.currentIndex = Store(rawValue: index) ?? Store.invited
            switchIndicator()
            canScroll = true
        }
    }

    func switchIndicator() {
        switch self.currentIndex {
        case .invited:
           self.indicator.isHidden = false
           self.indicator2.isHidden = true
           self.indicator3.isHidden = true
        case .paring:
           self.indicator.isHidden = true
           self.indicator2.isHidden = false
           self.indicator3.isHidden = true
        case .applied:
           self.indicator.isHidden = true
           self.indicator2.isHidden = true
           self.indicator3.isHidden = false
        }
    }
}

extension UIScrollView {
    var isBouncing: Bool {
        var isBouncing = false
//        if contentOffset.y >= (contentSize.height - bounds.size.height) {
//            // Bottom bounce
//            isBouncing = true
//        } else

        print("y: \(contentOffset.y) vs top: \(contentInset.top)")
        if contentOffset.y <= contentInset.top {
            // Top bounce
            isBouncing = true
        }
        return isBouncing
    }
}
