//
//  TaskDetailVC.swift
//  kolnapp
//
//  Created by 黃世維 on 2020/2/14.
//  Copyright © 2020 黃世維. All rights reserved.
//

import UIKit
import GrowingTextView

class TaskDetailVC: UIViewController {

    override var prefersStatusBarHidden: Bool {
        return true
    }

    #warning("需要使用自定義rawValue定義default")
    enum Store: Int {
      case basic = 0, ask = 1
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
    @IBOutlet weak var indicator1: UIView!
    @IBOutlet weak var indicator2: UIView!

    @IBOutlet weak var container: UIScrollView!
    //1
    @IBOutlet weak var basicHeader: UIView!
    @IBOutlet weak var basicHeaderTitle: UILabel!
    @IBOutlet weak var basicHeaderValidate: UILabel!
    @IBOutlet weak var basicHeaderPay: UILabel!
    @IBOutlet weak var wantedInput: GrowingTextView!

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
    @IBOutlet weak var applyBtn: UIButton!
    @IBAction func apply(_ sender: Any) {
        // 改成彈窗顯示.
        if let paringPriceVC = UIStoryboard(name: "TaskBoard", bundle: nil).instantiateViewController(withIdentifier: "ParingPriceVC") as? ParingPriceVC {
            paringPriceVC.commentstr = viewModel?["comments"] as? String
            self.present(paringPriceVC, animated: false)
        }
    }
    @IBOutlet weak var agreeBtn: UIButton!
    @IBAction func agree(_ sender: Any) {
        self.actionLabel.text = "已加入專案，等待專案開始"
        self.agreeBtn.isHidden = true
        self.rejectBtn.isHidden = true
        self.actionLabel.isHidden = false

        guard let projectUUID = viewModel?["uuid"] as? String else { return }
        guard let kolUUID = MemoryCache.shared.userData?["uuid"] as? String else { return }
        var body: [String: Any] = Dictionary()
        body.updateValue(projectUUID, forKey: "project_uuid")
        body.updateValue(kolUUID, forKey: "kol_uuid")
        KolAPI.agree(body: body, completion: { response, _ in print("agree: \(response)") })
    }
    @IBOutlet weak var rejectBtn: UIButton!
    @IBAction func reject(_ sender: Any) {
        self.actionLabel.text = "已拒絕"
        self.agreeBtn.isHidden = true
        self.rejectBtn.isHidden = true
        self.actionLabel.isHidden = false

        guard let projectUUID = viewModel?["uuid"] as? String else { return }
        guard let kolUUID = MemoryCache.shared.userData?["uuid"] as? String else { return }
        var body: [String: Any] = Dictionary()
        body.updateValue(projectUUID, forKey: "project_uuid")
        body.updateValue(kolUUID, forKey: "kol_uuid")
        KolAPI.reject(body: body, completion: { response, _ in
            print("agree: \(response)")
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
//            self.navigationController?.pushViewController(askVC, animated: false)
        }
    }

    @IBAction func back(_ sender: Any) {
        self.navigationController?.popToRootViewController(animated: true)
    }

    private var subTabIndex: Store = .basic
    var currentSubTab: TaskStoreVC.Store = .invited
    var currentIndex: IndexPath?
//    var viewModel: TaskObject?
    var viewModel: [String: Any]?
    private var canScroll: Bool = true
    private var wantedValue: Int = 0

    override func viewDidLoad() {
        super.viewDidLoad()

        self.container.delegate = self

        // shadow
        self.basicHeader.layer.cornerRadius = 10
        self.basicHeader.layer.makeShadow(color: .lightGray, x: 0, y: 6, blur: 10, spread: 0)

        self.basicBody.layer.cornerRadius = 10
        self.basicBody.layer.makeShadow(color: .lightGray, x: 0, y: 6, blur: 10, spread: 0)

        self.chatBg.layer.cornerRadius = 10
        self.chatBg.layer.makeShadow(color: .lightGray, x: 0, y: 6, blur: 10, spread: 0)

        // chatroom
        chatTable.delegate = self
        chatTable.dataSource = self
        chatTable.register(UINib(nibName: "ChatTableViewCell", bundle: nil), forCellReuseIdentifier: "ChatTableViewCell")
        chatTable.register(UINib(nibName: "ReplyTableViewCell", bundle: nil), forCellReuseIdentifier: "ReplyTableViewCell")

        switchIndicator()

        updateBoardOB()

        self.question.isHidden = true // 暫時不顯示

        self.wantedInput.delegate = self
        self.wantedInput.textAlignment = .right
        self.wantedInput.font = .systemFont(ofSize: 16)
        self.wantedInput.keyboardType = .phonePad

        let tap1 = UITapGestureRecognizer(target: self, action: #selector(scrollToDoingBoard))
        subTab1.isUserInteractionEnabled = true
        subTab1.addGestureRecognizer(tap1)

        // 公告相關的ＵＩ都隱藏.
        // layout部分需要改story board stackview以及super view的width 還有相關的hidden.
//        let tap2 = UITapGestureRecognizer(target: self, action: #selector(scrollToParing))
//        subTab2.isUserInteractionEnabled = true
//        subTab2.addGestureRecognizer(tap2)
        
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

    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        self.navigationController?.setNavigationBarHidden(true, animated: animated)
        getBoard()
        initInfomation()

        //判斷狀態是否正確
        //顯示正確的按鈕或label
        guard let vm = viewModel else { return }
        print("p: \(vm["project_kols"])")
        guard let kol_status = (vm["project_kols"] as? [[String: Any]])?.first else { return }
        print("p: \(kol_status)")

        switch currentSubTab {
        case .invited:
            self.agreeBtn.isHidden = true
            self.rejectBtn.isHidden = true
            self.applyBtn.isHidden = false
            self.actionLabel.isHidden = true
            self.wantedInput.isHidden = false
            self.wantedInput.isUserInteractionEnabled = false

            if kol_status["kol_status"] as! Int == 2 {
                print("kol_status ^^")

                NotificationCenter.default.addObserver(self, selector: #selector(self.showSpinningWheel(_:)), name: NSNotification.Name(rawValue: "kKOLwantedValue"), object: nil)
                return
            }

            if kol_status["kol_status"] as! Int == 6 {
                print("kol_status ^^")
                self.actionLabel.text =  "已申請，等待回覆"
                self.actionLabel.isHidden = false
                self.applyBtn.isHidden = true
                return
            }

        case .paring:

            self.applyBtn.isHidden = true
            self.actionLabel.isHidden = true
            self.wantedInput.isHidden = true

            //更換label字樣
            if kol_status["kol_status"] as! Int == 1 {
                // 同意, 拒絕
                print("kol_status ^^")
                return
            }

            if kol_status["kol_status"] as! Int == 3 {
                // 已加入專案，等待專案開始
                print("kol_status ^^")
                self.actionLabel.text =  "已加入專案，等待專案開始"
                self.agreeBtn.isHidden = false
                self.rejectBtn.isHidden = false
                self.actionLabel.isHidden = true
                return
            }

            if kol_status["kol_status"] as! Int == 7 {
                // 已拒絕
                print("kol_status ^^")
                self.actionLabel.text =  "已拒絕"
                self.agreeBtn.isHidden = true
                self.rejectBtn.isHidden = true
                self.actionLabel.isHidden = false
                return
            }

            print("kol_status QQQQ")

        case .applied:
            self.agreeBtn.isHidden = true
            self.rejectBtn.isHidden = true
            self.applyBtn.isHidden = true
            self.actionLabel.isHidden = false
            self.wantedInput.isHidden = true

            if kol_status["kol_status"] as! Int == 6 {
                print("kol_status ^^")
                return
            }

            if kol_status["kol_status"] as! Int == 7 {
                print("kol_status ^^")
                return
            }

            if kol_status["kol_status"] as! Int == 6 {
                // 已加入專案，等待專案開始
                print("kol_status ^^")
                self.actionLabel.text =  "已加入專案，等待專案開始"
                self.agreeBtn.isHidden = false
                self.rejectBtn.isHidden = false
                self.actionLabel.isHidden = true
                return
            }

            if kol_status["kol_status"] as! Int == 7 {
                // 已拒絕
                print("kol_status ^^")
                self.actionLabel.text =  "抱歉，您不符合資格"
                self.agreeBtn.isHidden = true
                self.rejectBtn.isHidden = true
                self.actionLabel.isHidden = false
                return
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

//            let data = Data(introduction.utf8)
//            // then convert data to NSAttributedString with NSAttributedString.DocumentType.htm
//            if let attributedString = try? NSAttributedString(data: data, options: [.documentType: NSAttributedString.DocumentType.html], documentAttributes: nil) {
//                self.mission.attributedText = attributedString
//            }
            
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
                plate.text = "Facebook"
            }
        } else {
            plate.text = ""
        }

    }

    @objc
    func getBoard() {
        guard let board = viewModel?["board"] as? [String: Any] else { return }
        guard let boardUUID = board["uuid"] as? String else { return }

        KolAPI.getBoard(board_uuid: boardUUID, completion: { [weak self] response, _ in
           guard let response = response else {
               print("login fail, response nil")
               return
           }

            print("getBoard ok, \(response)")
            if let getBoard = response["board"] as? [String: Any] {
                MemoryCache.shared.inviteBoardData = getBoard
                if let comments = getBoard["comments"] as? [[String: Any]], comments.count > 0 {
                    self?.boardHint.isHidden = true
                } else {
                    self?.boardHint.isHidden = false
                }
                self?.chatTable.reloadData()
            }
        })
    }

    @objc func scrollToDoingBoard() {
        scrollTo(container, index: 0)
    }

    @objc func scrollToParing() {
        scrollTo(container, index: 1)
    }

    @objc func showSpinningWheel(_ notification: NSNotification) {

     if let wantedValue = notification.userInfo?["wantedValue"] as? Int {
        self.wantedValue = wantedValue

        self.actionLabel.text = "已申請，等待回覆"
        self.applyBtn.isHidden = true
        self.actionLabel.isHidden = false

        guard let projectUUID = viewModel?["uuid"] else { return }
        var body: [String: Any] = Dictionary()
        body.updateValue(projectUUID, forKey: "project_uuid")
        body.updateValue(wantedValue, forKey: "kol_expect_price") // 需要大魚100
        KolAPI.apply(body: body, completion: { response, _ in print("apply: \(response)") })
     }
    }

}

//extension TaskStoreVC {
//    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
//        let askControl = segue.destination
//    }
//}

extension TaskDetailVC: UIScrollViewDelegate {
    func scrollViewDidEndDecelerating(_ scrollView: UIScrollView) {
        if scrollView == self.container {
            let index = lrint((Double(scrollView.contentOffset.x / scrollView.bounds.size.width)))
            print("CCC: \(index)")
            self.subTabIndex = Store(rawValue: index) ?? Store.basic
            print("C: \(self.subTabIndex)")

            switchIndicator()
        }

    }

//    func scrollViewDidEndDragging(_ scrollView: UIScrollView, willDecelerate decelerate: Bool) {
//        let index = lrint((Double(scrollView.contentOffset.x / scrollView.bounds.size.width)))
//        print("CCC: \(index)")
//        self.subTabIndex = Store(rawValue: index) ?? Store.basic
//        print("C: \(self.subTabIndex)")
//
//        switchIndicator()
//    }

    func switchIndicator() {
        switch self.subTabIndex {
               case .basic:
                    self.indicator1.isHidden = false
                    self.indicator2.isHidden = true
               case .ask:
                    self.indicator1.isHidden = true
                    self.indicator2.isHidden = false
        }
    }

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
}

extension TaskDetailVC: UITableViewDelegate, UITableViewDataSource {
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        let data = MemoryCache.shared.inviteBoardData
        guard let comments = data["comments"] as? [[String: Any]] else { return 0 }
        return comments.count
    }

    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
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

}

extension TaskDetailVC: GrowingTextViewDelegate {
    func textViewDidChangeHeight(_ textView: GrowingTextView, height: CGFloat) {
       UIView.animate(withDuration: 0.2) {
           self.view.layoutIfNeeded()
       }
    }
}
