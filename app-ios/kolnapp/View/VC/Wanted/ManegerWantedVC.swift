//
//  ManegerWantedVC.swift
//  kolnapp
//
//  Created by 🍙 Dodo 🍙 on 2020/7/7.
//  Copyright © 2020 黃世維. All rights reserved.
//

import UIKit

class ManegerWantedVC: UIViewController {
    override var prefersStatusBarHidden: Bool {
        return true
    }

    #warning("需要使用自定義rawValue定義default")
    enum Store: Int {
        #warning("需要定義 這三個物理狀態的條件")
        case doing = 0, done = 1, waiting = 2, myproject = 3
    }

    @IBOutlet weak var avatar: UIImageView!
    @IBOutlet weak var tabName: UILabel!
    @IBOutlet weak var doingBoard: UILabel!
    @IBOutlet weak var finishBoard: UILabel!
    @IBOutlet weak var waitingBoard: UILabel!
    @IBOutlet weak var managerBoard: UILabel!
    //    @IBOutlet weak var myBoard: UILabel!
    @IBOutlet weak var container: UIScrollView!

    @IBOutlet weak var doingTable: UITableView!
    @IBOutlet weak var taskTable: UITableView!
//    @IBOutlet weak var acceptTable: UITableView!
    @IBOutlet weak var acceptBoard: UITableView!
    @IBOutlet weak var manangerTable: UITableView!
    
    @IBOutlet weak var indicator: UIView!
    @IBOutlet weak var indicator2: UIView!
    @IBOutlet weak var indicator3: UIView!
    @IBOutlet weak var indicator4: UIView!
    //    @IBOutlet weak var indicator4: UIView!

    // TODO: 接資料在實作
    private var currentIndex: ManegerWantedVC.Store = .doing
    private let group: DispatchGroup = DispatchGroup()
    private var isPullRefresh: Bool = false
    private var lastContentOffset: CGPoint = CGPoint(x: 0, y: 0)
    private var canScroll: Bool = true
    
    private var tmpDoingData: [[String: Any]] = .init()
    private var tmpDoneData: [[String: Any]] = .init()
    private var tmpAppliedData: [[String: Any]] = .init()

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

        acceptBoard.delegate = self
        acceptBoard.dataSource = self
        acceptBoard.register(UINib(nibName: "TaskCellTableViewCell", bundle: nil), forCellReuseIdentifier: "TaskCellTableViewCell")
        
        manangerTable.delegate = self
        manangerTable.dataSource = self
        manangerTable.register(UINib(nibName: "TaskCellTableViewCell", bundle: nil), forCellReuseIdentifier: "TaskCellTableViewCell")

        let tap1 = UITapGestureRecognizer(target: self, action: #selector(scrollToDoingBoard))
        doingBoard.isUserInteractionEnabled = true
        doingBoard.addGestureRecognizer(tap1)

        let tap2 = UITapGestureRecognizer(target: self, action: #selector(scrollToParing))
        finishBoard.isUserInteractionEnabled = true
        finishBoard.addGestureRecognizer(tap2)

        let tap3 = UITapGestureRecognizer(target: self, action: #selector(scrollToAcceptBoard))
        waitingBoard.isUserInteractionEnabled = true
        waitingBoard.addGestureRecognizer(tap3)
        
        let tap4 = UITapGestureRecognizer(target: self, action: #selector(scrollToManagerBoard))
        managerBoard.isUserInteractionEnabled = true
        managerBoard.addGestureRecognizer(tap4)

        self.avatar.layer.cornerRadius = self.avatar.frame.height/2
        let tap0 = UITapGestureRecognizer(target: self, action: #selector(go2Profile))
        avatar.isUserInteractionEnabled = true
        avatar.addGestureRecognizer(tap0)

        container.contentInsetAdjustmentBehavior = .never
    }

    override func viewWillAppear(_ animated: Bool) {
        self.tabBarController?.setTabBarVisible(visible: true, animated: true)
        self.loading()
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
    
    @objc func scrollToManagerBoard() {
        scrollTo(container, index: 3)
    }

    override func viewDidAppear(_ animated: Bool) {
        super.viewDidAppear(animated)
        self.navigationController?.setNavigationBarHidden(true, animated: animated)

        self.lastContentOffset = self.container.contentOffset
    }

    @objc func go2Profile() {
        let storyBoard = UIStoryboard.init(name: "UserStoryboard", bundle: nil)
        guard let profileVC = storyBoard.instantiateViewController(withIdentifier: "ProfileVC") as? ProfileVC else { return }
//        self.navigationController?.pushViewController(profileVC, animated: true)
        self.navigationController?.present(profileVC, animated: true, completion: nil)

    }

    // TODO: 要設定timeout.
    @objc
    func loading() {
        // 顯示進度條.
        self.showLottie()
        // 移除舊資料.
        MemoryCache.shared.clearTempData()
        
        if let tData = UserDefaults.standard.data(forKey: "tmpDoingData") {
             let i1 = try? JSONSerialization.jsonObject(with: tData, options: []) as? [[String: Any]]
             self.tmpDoingData = i1 ?? []
         }
         
         if let tData = UserDefaults.standard.data(forKey: "tmpDoneData") {
             let i2 = try? JSONSerialization.jsonObject(with: tData, options: []) as? [[String: Any]]
             self.tmpDoneData = i2 ?? []
         }
        
        if let tData = UserDefaults.standard.data(forKey: "tmpAppliedData") {
            let i3 = try? JSONSerialization.jsonObject(with: tData, options: []) as? [[String: Any]]
            self.tmpAppliedData = i3 ?? []
        }

        // 抓取資料.
        // 已申請-後台尚未確定
        let q4 = DispatchQueue(label: "q4applied_queue", attributes: .concurrent)
        group.enter()
        q4.async(group: group) {
          KolAPI.applied(completion: { [weak self] response, _ in
            if let response = response, let aData = response["projects"] as? [[String: Any]] {
                print("q4 applied ok")
                MemoryCache.shared.appliedData.append(contentsOf: aData)
            } else {
                print("applied ok, but no data")
            }
            self?.group.leave()
          })
        }
        // 已申請-後台拒絕
        let q4_2 = DispatchQueue(label: "q4_2q4applied_queue", attributes: .concurrent)
        group.enter()
        q4_2.async(group: group) {
          KolAPI.applied2(completion: { [weak self] response, _ in
            if let response = response, let aData = response["projects"] as? [[String: Any]] {
                print("q4_2 applied ok")
                MemoryCache.shared.appliedData.append(contentsOf: aData)
            } else {
                print("q4_2 applied ok, but no data")
            }
            self?.group.leave()
          })
        }
        
        // 合作確認-尚未開始
        let q3 = DispatchQueue(label: "unactive_queue", attributes: .concurrent)
        group.enter()
        q3.async(group: group) {
          KolAPI.unactive(completion: { [weak self] response, _ in
            if let response = response, let q3Data = response["projects"] as? [[String: Any]] {
                print("unactive ok")
//                MemoryCache.shared.waitingData.append(contentsOf: q3Data)
                MemoryCache.shared.doingData.append(contentsOf: q3Data)
            } else {
                print("applied ok, but no data")
            }
            self?.group.leave()

          })
        }
        
        // 合作確認-進行中
        let q1 = DispatchQueue(label: "active_queue", attributes: .concurrent)
        group.enter()
        q1.async(group: group) {
          KolAPI.active(completion: { [weak self] response, _ in
            if let response = response, let r1Data = response["projects"] as? [[String: Any]] {
                print("active ok")
                MemoryCache.shared.doingData.append(contentsOf: r1Data)
            } else {
                print("active ok, but no data")
            }
            self?.group.leave()
          })
        }

        let q1_2 = DispatchQueue(label: "active2_queue", attributes: .concurrent)
         group.enter()
         q1_2.async(group: group) {
           KolAPI.active2(completion: { [weak self] response, _ in
             if let response = response, let r1Data = response["projects"] as? [[String: Any]] {
                 print("active2 ok")
                 MemoryCache.shared.doingData.append(contentsOf: r1Data)
             } else {
                 print("active2 ok, but no data")
             }
             self?.group.leave()
           })
         }
        
        let q1_3 = DispatchQueue(label: "active3_queue", attributes: .concurrent)
        group.enter()
        q1_3.async(group: group) {
          KolAPI.active3(completion: { [weak self] response, _ in
            if let response = response, let r1Data = response["projects"] as? [[String: Any]] {
                print("active3_queue ok")
                MemoryCache.shared.doingData.append(contentsOf: r1Data)
            } else {
                print("active3_queue ok, but no data")
            }
            self?.group.leave()
          })
        }

        // 專案結束.
        let q2 = DispatchQueue(label: "complete_queue", attributes: .concurrent)
        group.enter()
        q2.async(group: group) {
          KolAPI.complete(completion: { [weak self] response, _ in
            if let response = response, let q2rData = response["projects"] as? [[String: Any]] {
                print("complete ok")
                MemoryCache.shared.doneData.append(contentsOf: q2rData)
            } else {
                print("applied ok, but no data")
            }
            self?.group.leave()
          })
        }
        
        // 我的專案.
        let q666 = DispatchQueue(label: "myprojects_queue", attributes: .concurrent)
        group.enter()
        q666.async(group: group) {
            KolAPI.getProjects(completion: { [weak self] response, _ in
            if let response = response, let q2rData = response["projects"] as? [[String: Any]] {
                print("myprojects ok: \(response)")
                MemoryCache.shared.myprojectData.removeAll()
                MemoryCache.shared.myprojectData.append(contentsOf: q2rData)
            } else {
                print("myprojects_queue ok, but no data")
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
            MemoryCache.shared.doingData = MemoryCache.shared.doingData.sorted(by: { ($0["created_at"] as! Int) > ($1["created_at"] as! Int) })
            MemoryCache.shared.doneData = MemoryCache.shared.doneData.sorted(by: { ($0["created_at"] as! Int) > ($1["created_at"] as! Int) })
            MemoryCache.shared.appliedData = MemoryCache.shared.appliedData.sorted(by: { ($0["created_at"] as! Int) > ($1["created_at"] as! Int) })
            
            // TODO: 紅點尚未驗證
            // 先暫存資料, 方便每次重啟時比對是否要顯示紅點.
            let iData = try? JSONSerialization.data(withJSONObject: MemoryCache.shared.doingData, options: [])
            let iData2 = try? JSONSerialization.data(withJSONObject: MemoryCache.shared.doneData, options: [])
            let iData3 = try? JSONSerialization.data(withJSONObject: MemoryCache.shared.appliedData, options: [])
            UserDefaults.standard.setValue(iData, forKeyPath: "tmpDoingData")
            UserDefaults.standard.setValue(iData2 , forKey: "tmpDoneData")
            UserDefaults.standard.setValue(iData3 , forKey: "tmpAppliedData")
            UserDefaults.standard.synchronize()

            var showBadge: Bool = false
            if let tmpAppliedData = self?.tmpAppliedData {
                if tmpAppliedData.count != MemoryCache.shared.appliedData.count && MemoryCache.shared.appliedData.count > 0 {
                     print(" appliedData顯示紅點點1")
                    showBadge = true
                 } else if tmpAppliedData.count == MemoryCache.shared.appliedData.count && tmpAppliedData.count  > 0 {
                    var showRedDot: Bool = true
                    for element in tmpAppliedData {
                        if MemoryCache.shared.appliedData.contains(where: { $0["uuid"] as! String == element["uuid"] as! String }) {
                            showRedDot = false
                        }
                    }
                    if showRedDot {
                        print(" appliedData顯示紅點點2")
                        showBadge = true
                    }
                 }
            }
            
            
            if let tmpDoneData = self?.tmpDoneData {
                if tmpDoneData.count != MemoryCache.shared.doneData.count && MemoryCache.shared.doneData.count > 0 {
                     print("tmpParingData 顯示紅點點1")
                    showBadge = true
                 } else if tmpDoneData.count == MemoryCache.shared.doneData.count && tmpDoneData.count  > 0 {
                    var showRedDot: Bool = true
                    for element in tmpDoneData {
                        if MemoryCache.shared.doneData.contains(where: { $0["uuid"] as! String == element["uuid"] as! String }) {
                            showRedDot = false
                        }
                    }
                    
                    if showRedDot {
                        print(" tmpParingData顯示紅點點2")
                        showBadge = true
                    }
                 }
            }
            
            if let tmpDoingData = self?.tmpDoingData {
                       if tmpDoingData.count != MemoryCache.shared.doingData.count && MemoryCache.shared.doingData.count > 0 {
                            print("tmpParingData 顯示紅點點1")
                           showBadge = true
                        } else if tmpDoingData.count == MemoryCache.shared.doingData.count && tmpDoingData.count  > 0 {
                           var showRedDot: Bool = true
                           for element in tmpDoingData {
                               if MemoryCache.shared.doingData.contains(where: { $0["uuid"] as! String == element["uuid"] as! String }) {
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
          print("完成所有 Call 後端 API 的動作...")
//            self?.stopLottie()
            self?.doingTable.reloadData()
            self?.taskTable.reloadData()
            self?.acceptBoard.reloadData()
            self?.manangerTable.reloadData()

            guard let data = MemoryCache.shared.userData else { return }
            if let photo = data["photo"] as? String {
                self?.avatar.sd_setImage(with: URL(string: photo), placeholderImage: UIImage())
            }
            
            // 檢查是否是從推波進來
            if MemoryCache.shared.kolNotify != -1 {
                if MemoryCache.shared.kolNotify == 4 {
                    self?.scrollToDoingBoard()
                } else if MemoryCache.shared.kolNotify == 5 {
                    self?.scrollToParing()
                } else if MemoryCache.shared.kolNotify == 3 {
                    self?.scrollToAcceptBoard()
                } else {
                    // 去我的專案
                    if MemoryCache.shared.kolNotify == 1 || MemoryCache.shared.kolNotify == 2 {
                        self?.tabBarController?.selectedIndex = 0
                    }
                    
                    if MemoryCache.shared.kolNotify == 6 {
                       self?.tabBarController?.selectedIndex = 2
                    }
                }
                
                MemoryCache.shared.kolNotify = -1
            }
            
            self?.stopLottie()
        }

    }
    
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

extension ManegerWantedVC: UITableViewDelegate, UITableViewDataSource {
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        print(self.currentIndex)
        
        if tableView == doingTable {
            if MemoryCache.shared.appliedData.count == 0 {
                return 1
            }else {
                return MemoryCache.shared.appliedData.count
            }
        }
        
        if tableView == taskTable {
            if MemoryCache.shared.doingData.count == 0 {
               return 1
           }else {
               return MemoryCache.shared.doingData.count
           }
        }

        if tableView == acceptBoard {
            if MemoryCache.shared.doneData.count == 0 {
              return 1
          }else {
              return MemoryCache.shared.doneData.count
          }
        }
        
        if tableView == manangerTable {
            if MemoryCache.shared.myprojectData.count == 0 {
              return 1
          }else {
              return MemoryCache.shared.myprojectData.count
          }
        }

        return 0
    }

    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = taskTable.dequeueReusableCell(withIdentifier: "TaskCellTableViewCell", for: indexPath) as? TaskCellTableViewCell
        if tableView == taskTable {
            if MemoryCache.shared.doingData.count == 0 {
                // 空白卡片
                cell?.isEmptyCard(idEmptyCard: true, info1: "目前還沒有已確認的合作喔！" , info2: "歡迎定期查看「找專案」頁面，才不會漏接合作的最新訊息喔～")
                return cell!
            }
            cell?.isEmptyCard(idEmptyCard: false)
            let data = MemoryCache.shared.doingData[indexPath.row]
            print("QQQQ \(data["platform"] as! Int)")
            if let photo = data["photo"] as? String {
                cell?.cellImage.sd_setImage(with: URL(string: photo), placeholderImage: UIImage())
            }
            cell?.intro.text = data["name"] as? String
            cell?.platform.text = platform(rawValue: data["platform"] as! Int)?.toString
            let ss = Date.getDateFromTimeStamp(timeStamp: data["invite_deadline"] as! Double, format: "yyyy-MM-dd")
            cell?.deadline.text = "截止日期 \(ss)"
//            if let pp = data["project_kols"] as? [[String:Any]] {
//                if let nd: Int = pp[0]["kol_price"] as? Int {
//                    cell?.ticket.text = "NTD \(nd)"
//                }
//            }
            if let pp = data["project_kols"] as? [[String: Any]] {
                if let kol_expect_price = pp[0]["kol_expect_price"] as? Int {
                    cell?.ticket.text = "NTD \(kol_expect_price)"
                } else {
                    if let nd: Int = pp[0]["kol_price"] as? Int {
                        cell?.ticket.text = "NTD \(nd)"
                    }
                }
            }

        }

        if tableView == acceptBoard {
            if MemoryCache.shared.doneData.count == 0 {
                // 空白卡片
                cell?.isEmptyCard(idEmptyCard: true, info1: "目前還沒有已結束的合作喔！" , info2: "")
                return cell!
            }
            cell?.isEmptyCard(idEmptyCard: false)
            let data = MemoryCache.shared.doneData[indexPath.row]
            print("QQQQ \(data["platform"] as! Int)")
            if let photo = data["photo"] as? String {
                cell?.cellImage.sd_setImage(with: URL(string: photo), placeholderImage: UIImage())
            }
            cell?.intro.text = data["name"] as? String
            cell?.platform.text = platform(rawValue: data["platform"] as! Int)?.toString
            let ss = Date.getDateFromTimeStamp(timeStamp: data["invite_deadline"] as! Double, format: "yyyy-MM-dd")
            cell?.deadline.text = "截止日期 \(ss)"
//            if let pp = data["project_kols"] as? [[String:Any]] {
//                if let nd: Int = pp[0]["kol_price"] as? Int {
//                    cell?.ticket.text = "NTD \(nd)"
//                }
//            }

            if let pp = data["project_kols"] as? [[String: Any]] {
                if let kol_expect_price = pp[0]["kol_expect_price"] as? Int {
                    cell?.ticket.text = "NTD \(kol_expect_price)"
                } else {
                    if let nd: Int = pp[0]["kol_price"] as? Int {
                        cell?.ticket.text = "NTD \(nd)"
                    }
                }
            }

        }

        if tableView == doingTable {
            if MemoryCache.shared.appliedData.count == 0 {
                // 空白卡片
                cell?.isEmptyCard(idEmptyCard: true, info1: "歡迎到「找專案」瀏覽最新專案，主動申請你有興趣的合作！" , info2: "")
                return cell!
            }
            cell?.isEmptyCard(idEmptyCard: false)
            let data = MemoryCache.shared.appliedData[indexPath.row]
            print("QQQQ \(data["platform"] as! Int)")
            if let photo = data["photo"] as? String {
                cell?.cellImage.sd_setImage(with: URL(string: photo), placeholderImage: UIImage())
            }
            cell?.intro.text = data["name"] as? String
            cell?.platform.text = platform(rawValue: data["platform"] as! Int)?.toString
            let ss = Date.getDateFromTimeStamp(timeStamp: data["invite_deadline"] as! Double, format: "yyyy-MM-dd")
            cell?.deadline.text = "截止日期 \(ss)"

            if let pp = data["project_kols"] as? [[String: Any]] {
                if let kol_expect_price = pp[0]["kol_expect_price"] as? Int, kol_expect_price > 0 {
                    cell?.ticket.text = "NTD \(kol_expect_price)"
                } else {
                    if let nd: Int = pp[0]["kol_price"] as? Int {
                        cell?.ticket.text = "NTD \(nd)"
                    }
                }
            }

        }
        
        if tableView == manangerTable {
            if MemoryCache.shared.myprojectData.count == 0 {
                // 空白卡片
                cell?.isEmptyCard(idEmptyCard: true, info1: "歡迎到「找專案」瀏覽最新專案，主動申請你有興趣的合作！" , info2: "")
                return cell!
            }
            cell?.isEmptyCard(idEmptyCard: false)
            let data = MemoryCache.shared.myprojectData[indexPath.row]
            print("QQQQ \(data["platform"] as! Int)")
            if let photo = data["photo"] as? String {
                cell?.cellImage.sd_setImage(with: URL(string: photo), placeholderImage: UIImage())
            }
            cell?.intro.text = data["name"] as? String
            cell?.platform.text = platform(rawValue: data["platform"] as! Int)?.toString
            let ss = Date.getDateFromTimeStamp(timeStamp: data["invite_deadline"] as! Double, format: "yyyy-MM-dd")
            cell?.deadline.text = "截止日期 \(ss)"

            // 我的專案不顯示金額
//            if let pp = data["project_kols"] as? [[String: Any]] {
//                if let kol_expect_price = pp[0]["kol_expect_price"] as? Int, kol_expect_price > 0 {
//                    cell?.ticket.text = "NTD \(kol_expect_price)"
//                } else {
//                    if let nd: Int = pp[0]["kol_price"] as? Int {
//                        cell?.ticket.text = "NTD \(nd)"
//                    }
//                }
//            }

        }
        return cell!
    }

    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        // 專案經理進去專案管理頁面
        if tableView == manangerTable {
            if let wantedDetailVC = UIStoryboard(name: "Wanted", bundle: nil).instantiateViewController(withIdentifier: "ManegerWantedDetailVC") as? ManegerWantedDetailVC {
                if MemoryCache.shared.myprojectData.count > 0 {
                    wantedDetailVC.viewModel = MemoryCache.shared.myprojectData[indexPath.row]

                    self.tabBarController?.setTabBarVisible(visible: false, animated: true)
                    if let navigator = self.navigationController {
                        navigator.pushViewController(wantedDetailVC, animated: true)
                    }
                }
            }
            return
        }
        
        // 專案經理也可以是KOL
        if let wantedDetailVC = UIStoryboard(name: "Wanted", bundle: nil).instantiateViewController(withIdentifier: "WantedDetailVC") as? WantedDetailVC {

            wantedDetailVC.currentIndex = indexPath
            if tableView == taskTable {
                if MemoryCache.shared.doingData.count > 0 {
                    wantedDetailVC.viewModel = MemoryCache.shared.doingData[indexPath.row]
                    
                    // 不需要這邊，重構後由內部自己判斷
                    guard let index = WantedVC.Store(rawValue: self.currentIndex.rawValue) else { return }
                    wantedDetailVC.currentSubTab = index

                    self.tabBarController?.setTabBarVisible(visible: false, animated: true)
                    if let navigator = self.navigationController {
                        navigator.pushViewController(wantedDetailVC, animated: true)
                    }
                }
            }

            if tableView == acceptBoard {
                if MemoryCache.shared.doneData.count > 0 {
                    wantedDetailVC.viewModel = MemoryCache.shared.doneData[indexPath.row]
                    
                    // 不需要這邊，重構後由內部自己判斷
                    guard let index = WantedVC.Store(rawValue: self.currentIndex.rawValue) else { return }
                    wantedDetailVC.currentSubTab = index

                    self.tabBarController?.setTabBarVisible(visible: false, animated: true)
                    if let navigator = self.navigationController {
                        navigator.pushViewController(wantedDetailVC, animated: true)
                    }
                }
            }

            if tableView == doingTable {
                if MemoryCache.shared.appliedData.count > 0 {
                    wantedDetailVC.viewModel = MemoryCache.shared.appliedData[indexPath.row]
                    
                    // 不需要這邊，重構後由內部自己判斷
                    guard let index = WantedVC.Store(rawValue: self.currentIndex.rawValue) else { return }
                    wantedDetailVC.currentSubTab = index
                    
                    self.tabBarController?.setTabBarVisible(visible: false, animated: true)
                    if let navigator = self.navigationController {
                        navigator.pushViewController(wantedDetailVC, animated: true)
                    }
                }
            }

        }
    }

}

extension ManegerWantedVC: UIScrollViewDelegate {
    func scrollViewDidEndDecelerating(_ scrollView: UIScrollView) {
        if scrollView == container {
            print("scrollViewDidEndDecelerating")
            print("CCC: \(self.lastContentOffset) vs \(scrollView.contentOffset)")
            let index = lrint((Double(scrollView.contentOffset.x / scrollView.bounds.size.width)))
            print("CCC: \(index)")
            self.currentIndex = ManegerWantedVC.Store(rawValue: index) ?? ManegerWantedVC.Store.doing
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

         if scrollView == acceptBoard {
             print("scrollViewDidEndDecelerating acceptBoard")

             if scrollView.isBouncing {
                 print("scrollViewDidEndDecelerating acceptBoard isBouncing")
                self.loading()
             }
         }
        
        if scrollView == manangerTable {
            print("scrollViewDidEndDecelerating acceptBoard")

            if scrollView.isBouncing {
                print("scrollViewDidEndDecelerating acceptBoard isBouncing")
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

            self.currentIndex = Store(rawValue: index) ?? Store.doing
            switchIndicator()
            canScroll = true
        }
    }

    func switchIndicator() {
        if self.isPullRefresh {
            return
        }

        switch self.currentIndex {
        case .doing:
            self.indicator.isHidden = false
            self.indicator2.isHidden = true
            self.indicator3.isHidden = true
            self.indicator4.isHidden = true
        case .done:
            self.indicator.isHidden = true
            self.indicator2.isHidden = false
            self.indicator3.isHidden = true
            self.indicator4.isHidden = true
        case .waiting:
            self.indicator.isHidden = true
            self.indicator2.isHidden = true
            self.indicator3.isHidden = false
            self.indicator4.isHidden = true
        case .myproject:
            self.indicator.isHidden = true
            self.indicator2.isHidden = true
            self.indicator3.isHidden = true
            self.indicator4.isHidden = false
        }
        
    }
}
