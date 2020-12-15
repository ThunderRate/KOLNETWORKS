//
//  ManegerWantedDetailVC.swift
//  kolnapp
//
//  Created by 🍙 Dodo 🍙 on 2020/7/7.
//  Copyright © 2020 黃世維. All rights reserved.
//

import UIKit

class ManegerWantedDetailVC: UIViewController {
    override var prefersStatusBarHidden: Bool {
        return true
    }

    #warning("需要使用自定義rawValue定義default")
    enum Store: Int {
      case basic = 0,
//        ask = 1,
        list = 1
    }
    
    // floating button
    @IBOutlet weak var fContainer: UIView!
    @IBAction func fContainerAction(_ sender: Any) {
        guard let messenger = viewModel?["messenger"] as? String, let messengerURL = URL(string: messenger) else { return }
        UIApplication.shared.open(messengerURL)
    }

    @IBOutlet weak var toolBarImage: UIImageView!
    @IBOutlet weak var subTab1: UILabel!
    @IBOutlet weak var subTab2: UILabel!
    @IBOutlet weak var subTab3: UILabel!
    @IBOutlet weak var indicator1: UIView!
    @IBOutlet weak var indicator2: UIView!
    @IBOutlet weak var indicator3: UIView!

    @IBOutlet weak var container: UIScrollView!
    //1
    @IBOutlet weak var basicHeader: UIView!
    @IBOutlet weak var basicHeaderTitle: UILabel!
    @IBOutlet weak var basicHeaderValidate: UILabel!
    @IBOutlet weak var basicHeaderPay: UILabel!

    @IBOutlet weak var boardHint: UILabel!
    @IBOutlet weak var basicBody: UIView!
    @IBOutlet weak var basicTaskNo: UILabel!
    @IBOutlet weak var taskNo: UILabel!
    @IBOutlet weak var basicBodyPlate: UILabel!
    @IBOutlet weak var plate: UILabel!
    @IBOutlet weak var basicBodyDate: UILabel!
    @IBOutlet weak var date: UILabel!
    @IBOutlet weak var basicBodyTime: UILabel!
    @IBOutlet weak var time: UILabel!
    @IBOutlet weak var basicBodyMisstion: UILabel!
    @IBOutlet weak var mission: UITextView!
    //action
    @IBOutlet weak var agreeBtn: UIButton!
    @IBAction func agree(_ sender: Any) {

        guard let projectUUID = viewModel?["uuid"] as? String else { return }
        var body: [String: Any] = Dictionary()
        body.updateValue(projectUUID, forKey: "project_uuid")
        body.updateValue(3, forKey: "project_status")
        
        // 專案開始
        KolAPI.startProject(parameters: body, completion: { [weak self] response, error in
           print("startProject \(response)")
            DispatchQueue.main.async {
                self?.navigationController?.popToRootViewController(animated: true)
            }
            
        })
    }

    @IBOutlet weak var actionLabel: UILabel!

    //2
    @IBOutlet weak var chatBg: UIView!
    @IBOutlet weak var chatTable: UITableView!
    @IBOutlet weak var question: UIButton!
    @IBAction func ask(_ sender: Any) {
        if let askVC = UIStoryboard(name: "TaskBoard", bundle: nil).instantiateViewController(withIdentifier: "AskVC") as? AskVC {
            guard let board = viewModel?["board"] as? [String: Any] else { return }
            guard let boardUUID = board["uuid"] as? String else { return }
            askVC.boardUuid = boardUUID
            self.present(askVC, animated: false)
        }
    }

    @IBAction func back(_ sender: Any) {
        self.navigationController?.popToRootViewController(animated: true)
    }
    @IBOutlet weak var listScrollView: UIScrollView!
    // 申請合作
    @IBOutlet weak var applyTitle: UILabel!
    @IBOutlet weak var applyBG: UIView!
    @IBOutlet weak var applyTable: ContentSizedTableView!
    // 邀約名單
    @IBOutlet weak var invitationTitle: UILabel!
    @IBOutlet weak var invitationBG: UIView!
    @IBOutlet weak var InvitationTable: ContentSizedTableView!
    
    @IBOutlet weak var invitationTableHeight: NSLayoutConstraint!
    @IBOutlet weak var applyTableHeight: NSLayoutConstraint!

    private var canScroll: Bool = true
    private var subTabIndex: ManegerWantedDetailVC.Store = .basic
    var currentSubTab: ManegerWantedVC.Store = .doing
    var currentIndex: IndexPath?
    var viewModel: [String: Any]?
    let group: DispatchGroup = DispatchGroup()
    
    // project uuid
    var uuid: String = ""

    // let tableview size to fit.
    override func viewWillLayoutSubviews() {
        super.updateViewConstraints()
        if  MemoryCache.shared.kols.count > 0 {
            DispatchQueue.main.async {
                self.applyTableHeight?.constant = self.applyTable.intrinsicContentSize.height
            }
        }
        
        if  MemoryCache.shared.kols.count > 0 {
            DispatchQueue.main.async {
                self.invitationTableHeight?.constant = self.InvitationTable.intrinsicContentSize.height
            }
        }
    }

    override func viewDidLoad() {
        super.viewDidLoad()
        
        self.showLottie()
        
        uuid = viewModel?["uuid"] as! String
        let q666 = DispatchQueue(label: "getApplyProjectByUUID_queue", attributes: .concurrent)
        group.enter()
        q666.async(group: group) {
            KolAPI.getApplyProjectByUUID(projectID: self.uuid, completion: { [weak self] response, error in
            print("error: \(error)")
            if let response = response, let q2rData = response["kols"] as? [[String: Any]] {
                MemoryCache.shared.kols.removeAll()
                MemoryCache.shared.kols.append(contentsOf: q2rData)
                
                DispatchQueue.main.async {
                    if MemoryCache.shared.kols.count == 0 {
                        self?.applyTitle.isHidden = true
                        self?.applyTable.isHidden = true
                        self?.applyBG.isHidden = true
                    } else {
                        self?.applyTitle.isHidden = false
                        self?.applyTable.isHidden = false
                        self?.applyBG.isHidden = false
                    }
                }
                
            } else {
                print("getApplyProjectByUUID_queue, kols ok, but no data")
                DispatchQueue.main.async {
                    self?.applyTitle.isHidden = true
                    self?.applyTable.isHidden = true
                    self?.applyBG.isHidden = true
                }

            }
                self?.group.leave()
          })
        }
        
        let q666_1 = DispatchQueue(label: "getInvitationProjectByUUID_queue", attributes: .concurrent)
        group.enter()
        q666_1.async(group: group) {
            KolAPI.getInvitationProjectByUUID(projectID: self.uuid, completion: { [weak self] response, error in
                print("error: \(error)")
                if let response = response, let q3rData = response["kols"] as? [[String: Any]] {
                    MemoryCache.shared.invitation_kols.removeAll()
                    MemoryCache.shared.invitation_kols.append(contentsOf: q3rData)
                    
                    DispatchQueue.main.async {
                        if MemoryCache.shared.invitation_kols.count == 0 {
                            self?.invitationTitle.isHidden = true
                            self?.InvitationTable.isHidden = true
                            self?.invitationBG.isHidden = true
                        } else {
                            self?.invitationTitle.isHidden = false
                            self?.InvitationTable.isHidden = false
                            self?.invitationBG.isHidden = false
                        }
                    }
                    
                } else {
                    print("getInvitationProjectByUUID kols ok, but no data")
                    DispatchQueue.main.async {
                        self?.invitationTitle.isHidden = true
                        self?.InvitationTable.isHidden = true
                        self?.invitationBG.isHidden = true
                    }

                }
                self?.group.leave()
            })
        }
        
        group.notify(queue: DispatchQueue.main) { [weak self] in
            self?.applyTable.reloadData()
            self?.InvitationTable.reloadData()
            self?.stopLottie()
        }

        self.container.delegate = self

        // shadow
        self.basicHeader.layer.cornerRadius = 10
        self.basicHeader.layer.makeShadow(color: .lightGray, x: 0, y: 6, blur: 10, spread: 0)

        self.basicBody.layer.cornerRadius = 10
        self.basicBody.layer.makeShadow(color: .lightGray, x: 0, y: 6, blur: 10, spread: 0)

        self.chatBg.layer.cornerRadius = 10
        self.chatBg.layer.makeShadow(color: .lightGray, x: 0, y: 6, blur: 10, spread: 0)

        // chatroom
//        chatTable.delegate = self
//        chatTable.dataSource = self
//        chatTable.register(UINib(nibName: "ChatTableViewCell", bundle: nil), forCellReuseIdentifier: "ChatTableViewCell")
//        chatTable.register(UINib(nibName: "ReplyTableViewCell", bundle: nil), forCellReuseIdentifier: "ReplyTableViewCell")
        
        applyTable.delegate = self
        applyTable.dataSource = self
        applyTable.register(UINib(nibName: "KolTableViewCell", bundle: nil), forCellReuseIdentifier: "KolTableViewCell")
        
        InvitationTable.delegate = self
        InvitationTable.dataSource = self
        InvitationTable.register(UINib(nibName: "KolTableViewCell", bundle: nil), forCellReuseIdentifier: "KolTableViewCell")
        
        applyBG.layer.cornerRadius = 10
        invitationBG.layer.cornerRadius = 10
        self.applyBG.layer.makeShadow(color: .lightGray, x: 0, y: 6, blur: 10, spread: 0)
        self.invitationBG.layer.makeShadow(color: .lightGray, x: 0, y: 6, blur: 10, spread: 0)


        switchIndicator()

        updateBoardOB()

        self.question.isHidden = true // 暫時不顯示

        let tap1 = UITapGestureRecognizer(target: self, action: #selector(scrollToDoingBoard))
           subTab1.isUserInteractionEnabled = true
           subTab1.addGestureRecognizer(tap1)

//        let tap2 = UITapGestureRecognizer(target: self, action: #selector(scrollToParing))
//        subTab2.isUserInteractionEnabled = true
//        subTab2.addGestureRecognizer(tap2)
        
        let tap3 = UITapGestureRecognizer(target: self, action: #selector(scrollToList))
        subTab3.isUserInteractionEnabled = true
        subTab3.addGestureRecognizer(tap3)
        
        // 公告相關的ＵＩ都隱藏.
        // layout部分需要改story board stackview以及super view的width 還有相關的hidden.
        self.fContainer.layer.cornerRadius = self.fContainer.frame.height / 2
        self.fContainer.layer.makeShadow(color: .lightGray, x: 0, y: 6, blur: 10, spread: 0)

        guard let messenger = viewModel?["messenger"] as? String, let messengerURL = URL(string: messenger) else {
            self.fContainer.isHidden = true
            return
        }
        self.fContainer.isHidden = false
//        self.boardHint.isHidden = false
    }

    func updateBoardOB() {
        NotificationCenter.default.addObserver(
            self,
            selector: #selector(getBoard),
            name: NSNotification.Name(rawValue: "NotifierName_loading_board"),
            object: nil
        )
    }

    @objc func scrollToDoingBoard() {
        scrollTo(container, index: 0)
    }

//    @objc func scrollToParing() {
//        scrollTo(container, index: 1)
//    }
    
    @objc func scrollToList() {
        scrollTo(container, index: 1)
    }

    override func viewDidAppear(_ animated: Bool) {
        super.viewDidAppear(animated)
        NotificationCenter.default.removeObserver(self)
    }

    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        self.navigationController?.setNavigationBarHidden(true, animated: animated)

//        getBoard()  公告不顯示
        initInfomation()

        //判斷狀態是否正確
        //顯示正確的按鈕或label
        guard let vm = viewModel else { return }
        guard let project_status = vm["project_status"] as? Int else { return }
        
        if project_status == 2 {
            self.agreeBtn.isHidden = false
            self.actionLabel.isHidden = true
        } else {
            if project_status > 2 {
                self.agreeBtn.isHidden = true
                self.actionLabel.isHidden = false
                self.actionLabel.text = "專案進行中"
            } else {
                self.agreeBtn.isHidden = true
                self.actionLabel.isHidden = true
            }

        }

        switchIndicator()
    }

    func initInfomation() {
        // TITLE
        basicHeaderTitle.text = viewModel?["name"] as? String
        let iii = Date.getDateFromTimeStamp(timeStamp: viewModel?["invite_deadline"] as! Double, format: "yyyy-MM-dd")
        basicHeaderValidate.text = "報名截止日 \(iii)"

        if let pp = viewModel?["project_kols"] as? [[String: Any]] {
            if let kol_expect_price = pp[0]["kol_expect_price"] as? Int, kol_expect_price > 0 {
                basicHeaderPay.text = "NTD \(kol_expect_price)"
           } else {
               if let mmm = pp[0]["kol_price"] as? Int {
                   basicHeaderPay.text = "NTD \(mmm)"
               } else {
                   basicHeaderPay.text = " "
               }
           }
        }

        guard let photo = viewModel?["photo"] as? String else { return }
        toolBarImage.sd_setImage(with: URL(string: photo), placeholderImage: UIImage())
        // add mask
        let gradientLayer = CAGradientLayer()
        gradientLayer.frame = toolBarImage.bounds
        gradientLayer.colors = [#colorLiteral(red: 0.5, green: 0.5, blue: 0.5, alpha: 0.5).cgColor, #colorLiteral(red: 1, green: 1, blue: 1, alpha: 0.5).cgColor]
        gradientLayer.colors = [#colorLiteral(red: 0.5, green: 0.5, blue: 0.5, alpha: 0.5).cgColor, #colorLiteral(red: 1, green: 1, blue: 1, alpha: 0.5).cgColor]

        toolBarImage.layer.addSublayer(gradientLayer)

        // BODY
        taskNo.text = viewModel?["project_no"] as? String

        let ss = Date.getDateFromTimeStamp(timeStamp: viewModel?["start_date"] as! Double, format: "yyyy-MM-dd")
        let dd = Date.getDateFromTimeStamp(timeStamp: viewModel?["end_date"] as! Double, format: "yyyy-MM-dd")
        date.text = "\(ss) - \(dd)"

        if let introduction = viewModel?["introduction"] as? String {
            mission.isUserInteractionEnabled = true
            
            if let result = introduction.htmlAttributedString?.string {
                print(result)   // "Björn is great name"
                self.mission.text = result
                self.mission.textContainerInset = UIEdgeInsets.zero
                self.mission.textContainer.lineFragmentPadding = 0
            }
        }

        if let ppp = viewModel?["platform"] as? Int {
            switch ppp {
            case 1:
                plate.text = "Facebook"
                case 2:
                plate.text = "Instgram"
                case 3:
                plate.text = "Youtube"
                case 4:
                plate.text = "Blog"
            default:
                plate.text = ""
            }
        } else {
            plate.text = "Facebook"
        }

    }

    @objc
    func getBoard() {
        guard let board = viewModel?["board"] as? [String: Any] else { return }
        guard let boardUUID = board["uuid"] as? String else { return }

        KolAPI.getBoard(board_uuid: boardUUID, completion: { [weak self] response, _ in
            guard let response = response, let bData = response["board"] as? [String: Any] else {
               print("login fail, response nil")
               return
            }

            print("getBoard ok, \(response)")
            MemoryCache.shared.inviteBoardData = bData
             if let comments = bData["comments"] as? [[String: Any]], comments.count > 0 {
               self?.boardHint.isHidden = true
           } else {
               self?.boardHint.isHidden = false
           }
            self?.chatTable.reloadData()
        })
    }

}

extension ManegerWantedDetailVC: UIScrollViewDelegate {
    func scrollTo(_ scrollView: UIScrollView, index: Int) {
        if canScroll {
            canScroll = false
            var frame = scrollView.frame
            frame.origin.x = frame.size.width * CGFloat(index)
            frame.origin.y = 0
            scrollView.scrollRectToVisible(frame, animated: true)

            self.subTabIndex = Store(rawValue: index) ?? Store.basic
            switchIndicator()
            canScroll = true
        }
    }

    func scrollViewDidEndDecelerating(_ scrollView: UIScrollView) {
        if scrollView == self.container {
            let index = lrint((Double(scrollView.contentOffset.x / scrollView.bounds.size.width)))
            print("CCC: \(index)")
            self.subTabIndex = Store(rawValue: index) ?? Store.basic
            print("C: \(self.subTabIndex)")

            switchIndicator()
        }
    }

    func switchIndicator() {
        switch self.subTabIndex {
        case .basic:
            self.indicator1.isHidden = false
//            self.indicator2.isHidden = true
            self.indicator3.isHidden = true
            self.fContainer.isHidden = false
//        case .ask:
//            self.indicator1.isHidden = true
//            self.indicator2.isHidden = false
//            self.indicator3.isHidden = true
//            self.fContainer.isHidden = true
        case .list:
            self.indicator1.isHidden = true
//            self.indicator2.isHidden = true
            self.indicator3.isHidden = false
            self.fContainer.isHidden = true
        }
    }
}

extension ManegerWantedDetailVC: UITableViewDelegate, UITableViewDataSource {
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        if tableView == chatTable {
            let data = MemoryCache.shared.inviteBoardData
            guard let comments = data["comments"] as? [[String: Any]] else { return 0 }
            return comments.count
        }

        if tableView == applyTable {
            return MemoryCache.shared.kols.count
        }
        
        if tableView == InvitationTable {
            return MemoryCache.shared.invitation_kols.count
        }
        return 0
    }

    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        if tableView == chatTable {
            let data = MemoryCache.shared.inviteBoardData
            guard let comments = data["comments"] as? [[String: Any]] else { return UITableViewCell() }
            guard let content = comments[indexPath.row]["content"] as? String else { return UITableViewCell() }

            if let is_bg = comments[indexPath.row]["is_bg"] as? Bool, is_bg {
                let cell = chatTable.dequeueReusableCell(withIdentifier: "ChatTableViewCell", for: indexPath) as? ChatTableViewCell
                 cell?.msg.text = comments[indexPath.row]["content"] as? String
                 let ss = Date.getDateFromTimeStamp(timeStamp: comments[indexPath.row]["created_at"] as! Double, format: "yyyy-MM-dd")
                 cell?.date.text = "\(ss)"
                return cell!
            } else {
                let cell = chatTable.dequeueReusableCell(withIdentifier: "ReplyTableViewCell", for: indexPath) as? ReplyTableViewCell
                cell?.msg.text = comments[indexPath.row]["content"] as? String
                let ss = Date.getDateFromTimeStamp(timeStamp: comments[indexPath.row]["created_at"] as! Double, format: "yyyy-MM-dd")
                cell?.date.text = "\(ss)"
                return cell!
            }
        }
        
        guard let vm = viewModel else { return UITableViewCell() }
        guard let project_status = vm["project_status"] as? Int else { return UITableViewCell() }
        
        if tableView == applyTable {
            let cell = applyTable.dequeueReusableCell(withIdentifier: "KolTableViewCell", for: indexPath) as? KolTableViewCell
            
            let data = MemoryCache.shared.kols[indexPath.row]
            guard let user = data["user"] as? [String:Any] else { return UITableViewCell() }
            if let photo = user["photo"] as? String {
                cell?.avatar.sd_setImage(with: URL(string: photo), placeholderImage: UIImage())
            }
            
            if let display_name = user["display_name"] as? String {
                cell?.name.text = display_name
            }
            
            let kol_price = data["kol_price"] as? Int
            let kol_expect_price = data["kol_expect_price"] as? Int
            if kol_price != nil {
                cell?.pay.text = "\(kol_price!)"
            } else {
                cell?.pay.text = "\(kol_expect_price!)"
            }
            
            if let kol_status = data["kol_status"] as? Int {
                cell?.setStatus(kolStatus: kol_status, projectStatus: project_status, isPicked: false)
            }
            
            guard let kol_uuid = user["uuid"] as? String else {
                 return UITableViewCell()
            }
            cell?.kol_uuid = kol_uuid
            cell?.project_uuid = self.uuid
            
            cell?.showSubmitVC = {
                // get review uri first
                KolAPI.getTargetKolReviewUri(projectUUID: self.uuid, kolUUID: kol_uuid, completion: { [weak self] response, error in
                    if let response = response, let reports = response["reports"] as? [[String: Any]], let report = reports.first?["report"] as? String {
                        print("reports \(report)")
                        
                        // show review alert
                        var body: [String: Any] = Dictionary()
                        body.updateValue(self?.uuid, forKey: "project_uuid")
                        if let kol_uuid = user["uuid"] as? String {
                            body.updateValue(kol_uuid, forKey: "kol_uuid")
                        }

                        if let reviewUri = user["uuid"] as? String {
                            body.updateValue(reviewUri, forKey: "kol_uuid")
                        }
                        body.updateValue(report, forKey: "report")

                        MemoryCache.shared.sendReviewTempData = body
                        MemoryCache.shared.sendReviewCallback = {
                            self?.navigationController?.popToRootViewController(animated: true)
                        }
                        self?.showSimpleManagerReviewVCAlert()
                    }
                })
            }
            
            cell?.successCallback = {
                DispatchQueue.main.async {
                    self.navigationController?.popToRootViewController(animated: true)
                }
            }
            
            return cell!
        }
        
        if tableView == InvitationTable {
            let cell = InvitationTable.dequeueReusableCell(withIdentifier: "KolTableViewCell", for: indexPath) as? KolTableViewCell
            
            let data = MemoryCache.shared.invitation_kols[indexPath.row]
            guard let user = data["user"] as? [String:Any] else { return UITableViewCell() }
            if let photo = user["photo"] as? String {
                cell?.avatar.sd_setImage(with: URL(string: photo), placeholderImage: UIImage())
            }
            
            if let display_name = user["display_name"] as? String {
                cell?.name.text = display_name
            }
            
            let kol_price = data["kol_price"] as? Int
            let kol_expect_price = data["kol_expect_price"] as? Int
            if kol_price != nil {
                cell?.pay.text = "\(kol_price!)"
            } else {
                cell?.pay.text = "\(kol_expect_price!)"
            }
            
            if let kol_status = data["kol_status"] as? Int {
                cell?.setStatus(kolStatus: kol_status, projectStatus: project_status, isPicked: true)
            }
            
            guard let kol_uuid = user["uuid"] as? String else {
                 return UITableViewCell()
            }
            cell?.kol_uuid = kol_uuid
            cell?.project_uuid = self.uuid
            
            cell?.showSubmitVC = {
                // get review uri first
                KolAPI.getTargetKolReviewUri(projectUUID: self.uuid, kolUUID: kol_uuid, completion: { [weak self] response, error in
                    if let response = response, let reports = response["reports"] as? [[String: Any]], let report = reports.first?["report"] as? String {
                        print("reports \(report)")
                        
                        // show review alert
                        var body: [String: Any] = Dictionary()
                        body.updateValue(self?.uuid, forKey: "project_uuid")
                        if let kol_uuid = user["uuid"] as? String {
                            body.updateValue(kol_uuid, forKey: "kol_uuid")
                        }

                        if let reviewUri = user["uuid"] as? String {
                            body.updateValue(reviewUri, forKey: "kol_uuid")
                        }
                        body.updateValue(report, forKey: "report")

                        MemoryCache.shared.sendReviewTempData = body
                        MemoryCache.shared.sendReviewCallback = {
                           self?.navigationController?.popToRootViewController(animated: true)
                        }
                        self?.showSimpleManagerReviewVCAlert()
                    }
                })
            }
            
            cell?.successCallback = {
                DispatchQueue.main.async {
                    self.navigationController?.popToRootViewController(animated: true)
                }
            }
            
            return cell!
        }
        
        return UITableViewCell()
    }

}
