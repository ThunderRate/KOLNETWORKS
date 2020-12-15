//
//  RegisterStep4VC.swift
//  kolnapp
//
//  Created by 黃世維 on 2020/4/12.
//  Copyright © 2020 黃世維. All rights reserved.
//

import UIKit

class RegisterStep4VC: UIViewController {

    override var prefersStatusBarHidden: Bool {
        return true
    }

    @IBOutlet weak var tags: UICollectionView!

    @IBOutlet weak var backBtn: UIButton!
    @IBOutlet weak var doneBtn: UIButton!
    @IBAction func goBack(_ sender: Any) {
        // 歸零
        MemoryCache.shared.register_user_tag?.removeAll()
        MemoryCache.shared.register_user_tag_section0.removeAll()
        MemoryCache.shared.register_user_tag_section1.removeAll()
        MemoryCache.shared.register_user_tag_section2.removeAll()
        MemoryCache.shared.register_user_tag_section3.removeAll()
        MemoryCache.shared.register_user_tag_section4.removeAll()
        MemoryCache.shared.register_user_tag_section5.removeAll()

        if doneBtn.titleLabel?.text == "完成" {
            self.dismiss(animated: true, completion: nil)
        } else {
            self.navigationController?.popViewController(animated: true)
        }
    }

    @IBAction func go2Next(_ sender: Any) {
        if MemoryCache.shared.register_user_tag_section0.count > 0 && MemoryCache.shared.register_user_tag_section1.count > 0 && MemoryCache.shared.register_user_tag_section2.count > 0 && MemoryCache.shared.register_user_tag_section3.count > 0 && MemoryCache.shared.register_user_tag_section4.count > 0 && MemoryCache.shared.register_user_tag_section5.count > 0 {

            // 先加總
            MemoryCache.shared.register_user_tag?.removeAll() // 歸零
            MemoryCache.shared.register_user_tag?.append(contentsOf: MemoryCache.shared.register_user_tag_section0)
            MemoryCache.shared.register_user_tag?.append(contentsOf: MemoryCache.shared.register_user_tag_section1)
            MemoryCache.shared.register_user_tag?.append(contentsOf: MemoryCache.shared.register_user_tag_section2)
            MemoryCache.shared.register_user_tag?.append(contentsOf: MemoryCache.shared.register_user_tag_section3)
            MemoryCache.shared.register_user_tag?.append(contentsOf: MemoryCache.shared.register_user_tag_section4)
            MemoryCache.shared.register_user_tag?.append(contentsOf: MemoryCache.shared.register_user_tag_section5)

            let storyboard = UIStoryboard(name: "Register", bundle: nil)
            let vc = storyboard.instantiateViewController(withIdentifier: "RegisterStep5VC") as! RegisterStep5VC
            
            if doneBtn.titleLabel?.text == "完成" {
                self.updateTags()
            } else {
                self.navigationController?.pushViewController(vc, animated: true)
            }
        } else {
            // 每項必須都選擇一個
            let alertController = UIAlertController(title: "每個標籤都要選取喔", message: nil, preferredStyle: .alert)
            let okAction = UIAlertAction(
                title: "確認",
                style: .default,
                handler: {
                (_: UIAlertAction!) -> Void in
                  print("按下確認後，閉包裡的動作")
            })
            alertController.addAction(okAction)
            self.present(alertController, animated: true, completion: nil)
        }
    }

    let layout = UICollectionViewFlowLayout()
    private let group: DispatchGroup = DispatchGroup()
    public var done: String = "下一步"
    
    var unassigTags: [String: Any] = Dictionary()

    override func viewDidLoad() {
        super.viewDidLoad()

        initColletionViewFlow()
    }

    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        self.navigationController?.navigationBar.isHidden = true
        
        self.doneBtn.setTitle(done, for: .normal)
        
        // 檢查是否需要抓取tags
        if let tag4 = MemoryCache.shared.tag4 {
            return
        }
        
        self.loading()
    }

    func initColletionViewFlow() {
        layout.sectionInset = UIEdgeInsets(top: 5, left: 5, bottom: 5, right: 5)
        layout.minimumLineSpacing = 5
        let w = Double(UIScreen.main.bounds.size.width) - 20 - 20
        layout.itemSize = CGSize(width: CGFloat(w/4 - 10.0), height: 50)

        layout.headerReferenceSize = CGSize(width: tags.bounds.width, height: 40)
        layout.footerReferenceSize = CGSize(width: tags.bounds.width, height: 40)
        layout.scrollDirection = .vertical

        tags.collectionViewLayout = layout
        tags.register(TagCellCollectionViewCell.self, forCellWithReuseIdentifier: "Cell")
        tags.register(UICollectionReusableView.self, forSupplementaryViewOfKind: UICollectionView.elementKindSectionHeader,
          withReuseIdentifier: "Header")
        tags.register(UICollectionReusableView.self, forSupplementaryViewOfKind: UICollectionView.elementKindSectionFooter,
          withReuseIdentifier: "Footer")

        tags.delegate = self
        tags.dataSource = self
    }
    
    func getCurrentTags() -> [Int] {
        let audience_age1 = MemoryCache.shared.userData?["audience_age"] as? [[String:Any]]
        let habbits1 = MemoryCache.shared.userData?["habbits"] as? [[String:Any]]
        let community1 = MemoryCache.shared.userData?["community"] as? [[String:Any]]
        let occupation1 = MemoryCache.shared.userData?["occupation"] as? [[String:Any]]
        let contents1 = MemoryCache.shared.userData?["contents"] as? [[String:Any]]
        let personality1 = MemoryCache.shared.userData?["personality"] as? [[String:Any]]
        
        guard let audience_age = audience_age1, let habbits = habbits1, let community = community1, let occupation = occupation1, let contents = contents1, let personality = personality1 else { return [] }
        
        var unassainTags: [Int] = []
        for t1 in audience_age {
            unassainTags.append(t1["tag_id"]! as! Int)
        }
        for t1 in habbits {
            unassainTags.append(t1["tag_id"]! as! Int)
        }
        for t1 in community {
            unassainTags.append(t1["tag_id"]! as! Int)
        }
        for t1 in occupation {
            unassainTags.append(t1["tag_id"]! as! Int)
        }
        for t1 in contents {
            unassainTags.append(t1["tag_id"]! as! Int)
        }
        for t1 in personality {
            unassainTags.append(t1["tag_id"]! as! Int)
        }

        self.unassigTags.updateValue(unassainTags, forKey: "tag_ids")
        return unassainTags
    }
    
    // TODO: 要設定timeout.
    func loading() {
        // 顯示進度條.
        self.showLottie()
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
            self.tags.reloadData()
            self.stopLottie()
        }

    }
    
    func updateTags() {
        // 確認使用者token登入狀態
        self.showLottie()
        // 抓取資料.
        let q1 = DispatchQueue(label: "updateUser_qqq1", attributes: .concurrent)
        group.enter()
        q1.async(group: group) {
            
            KolAPI.unassainTag(parameters: self.unassigTags) { [weak self] response, _ in
            print("updateUser putUser response: \(response)")
//            MemoryCache.shared.register_user_data = [:]
            self?.group.leave()
          }
        }

        group.notify(queue: DispatchQueue.main) { [weak self] in
            let q2 = DispatchQueue(label: "updateUser_tags_qqq1", attributes: .concurrent)
            self?.group.enter()
            q2.async(group: self?.group) {
              var parameters: [String: Any] = Dictionary()
                parameters.updateValue(MemoryCache.shared.register_user_tag!, forKey: "tag_ids")
              KolAPI.assainTag(parameters: parameters) { [weak self] response, _ in
                print("updateUser_tags_qqq1: \(response)")
                MemoryCache.shared.register_user_tag = []
                self?.group.leave()
                DispatchQueue.main.async {
                    self?.dismiss(animated: true, completion: nil)
                }
              }
            }
        }

    }
}

extension RegisterStep4VC: UICollectionViewDelegate, UICollectionViewDataSource {
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        let session1: Int = MemoryCache.shared.tag2?.count ?? 0
//        let session2: Int = MemoryCache.shared.tag3?.count ?? 0
        let session3: Int = MemoryCache.shared.tag4?.count ?? 0
        let session4: Int = MemoryCache.shared.tag5?.count ?? 0
        let session5: Int = MemoryCache.shared.tag6?.count ?? 0
        let session6: Int = MemoryCache.shared.tag7?.count ?? 0
        let session7: Int = MemoryCache.shared.tag8?.count ?? 0
        switch section {
        case 0:
        return session1
        case 1:
        return session3
        case 2:
        return session4
        case 3:
        return session5
        case 4:
        return session6
        case 5:
        return session7
        default:
            return 0
        }
//        return session1 + session2 + session3 + session4 + session5
    }
    func numberOfSections(in collectionView: UICollectionView) -> Int {
        return 6
    }

    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        // 依據前面註冊設置的識別名稱 "Cell" 取得目前使用的 cell
        let cell = collectionView.dequeueReusableCell(withReuseIdentifier: "Cell", for: indexPath) as? TagCellCollectionViewCell
        
//        guard let cTags = self.unassigTags["tag_ids"] as? [Int] else {
//            cell?.tagLabel.backgroundColor = .clear
//            cell?.tagLabel.layer.borderWidth = 1
//            cell?.tagLabel.layer.borderColor = UIColor.white.cgColor
//            cell?.tagLabel.textColor = .white
//            return cell!
//        }
        
        var cTags: [Int] = []
        
        if self.unassigTags["tag_ids"] != nil {
            cTags.append(self.unassigTags["tag_ids"] as! Int)
        }

        // 先計算是哪一個section
        if indexPath.section == 0 {
            cell?.section_id = 0
            cell?.tag_id = MemoryCache.shared.tag2?[indexPath.row]["tag_id"] as! Int
            cell?.name = MemoryCache.shared.tag2?[indexPath.row]["name"] as! String
            cell?.slug = MemoryCache.shared.tag2?[indexPath.row]["slug"] as! String
            cell?.category = MemoryCache.shared.tag2?[indexPath.row]["category"] as! Int
            cell?.tagLabel.text = "\(MemoryCache.shared.tag2?[indexPath.row]["name"] as! String)"

            if MemoryCache.shared.register_user_tag_section0.contains(where: { $0 == cell?.tag_id }) || (cTags.count > 0 && cTags.contains(where: { $0 == cell?.tag_id })) {
                
                cell?.tagLabel.backgroundColor = .white
                cell?.tagLabel.layer.borderWidth = 1
                cell?.tagLabel.layer.borderColor = UIColor.white.cgColor
                cell?.tagLabel.textColor = .black
            } else {
                cell?.tagLabel.backgroundColor = .clear
                cell?.tagLabel.layer.borderWidth = 1
                cell?.tagLabel.layer.borderColor = UIColor.white.cgColor
                cell?.tagLabel.textColor = .white
            }
        }

        if indexPath.section == 1 {
            cell?.section_id = 1
            cell?.tag_id = MemoryCache.shared.tag4?[indexPath.row]["tag_id"] as! Int
            cell?.name = MemoryCache.shared.tag4?[indexPath.row]["name"] as! String
            cell?.slug = MemoryCache.shared.tag4?[indexPath.row]["slug"] as! String
            cell?.category = MemoryCache.shared.tag4?[indexPath.row]["category"] as! Int
            cell?.tagLabel.text = "\(MemoryCache.shared.tag4?[indexPath.row]["name"] as! String)"

            if MemoryCache.shared.register_user_tag_section1.contains(where: { $0 == cell?.tag_id }) || (cTags.count > 0 && cTags.contains(where: { $0 == cell?.tag_id })) {
                cell?.tagLabel.backgroundColor = .white
                cell?.tagLabel.layer.borderWidth = 1
                cell?.tagLabel.layer.borderColor = UIColor.white.cgColor
                cell?.tagLabel.textColor = .black
            } else {
                cell?.tagLabel.backgroundColor = .clear
                cell?.tagLabel.layer.borderWidth = 1
                cell?.tagLabel.layer.borderColor = UIColor.white.cgColor
                cell?.tagLabel.textColor = .white
            }
        }

        if indexPath.section == 2 {
            cell?.section_id = 2
            cell?.tag_id = MemoryCache.shared.tag5?[indexPath.row]["tag_id"] as! Int
            cell?.name = MemoryCache.shared.tag5?[indexPath.row]["name"] as! String
            cell?.slug = MemoryCache.shared.tag5?[indexPath.row]["slug"] as! String
            cell?.category = MemoryCache.shared.tag5?[indexPath.row]["category"] as! Int
            cell?.tagLabel.text = "\(MemoryCache.shared.tag5?[indexPath.row]["name"] as! String)"

            if MemoryCache.shared.register_user_tag_section2.contains(where: { $0 == cell?.tag_id }) || (cTags.count > 0 && cTags.contains(where: { $0 == cell?.tag_id })) {
                cell?.tagLabel.backgroundColor = .white
                cell?.tagLabel.layer.borderWidth = 1
                cell?.tagLabel.layer.borderColor = UIColor.white.cgColor
                cell?.tagLabel.textColor = .black
            } else {
                cell?.tagLabel.backgroundColor = .clear
                cell?.tagLabel.layer.borderWidth = 1
                cell?.tagLabel.layer.borderColor = UIColor.white.cgColor
                cell?.tagLabel.textColor = .white
            }
        }

        if indexPath.section == 3 {
            cell?.section_id = 3
            cell?.tag_id = MemoryCache.shared.tag6?[indexPath.row]["tag_id"] as! Int
            cell?.name = MemoryCache.shared.tag6?[indexPath.row]["name"] as! String
            cell?.slug = MemoryCache.shared.tag6?[indexPath.row]["slug"] as! String
            cell?.category = MemoryCache.shared.tag6?[indexPath.row]["category"] as! Int
            cell?.tagLabel.text = "\(MemoryCache.shared.tag6?[indexPath.row]["name"] as! String)"

            if MemoryCache.shared.register_user_tag_section3.contains(where: { $0 == cell?.tag_id }) || (cTags.count > 0 && cTags.contains(where: { $0 == cell?.tag_id })) {
                cell?.tagLabel.backgroundColor = .white
                cell?.tagLabel.layer.borderWidth = 1
                cell?.tagLabel.layer.borderColor = UIColor.white.cgColor
                cell?.tagLabel.textColor = .black
            } else {
                cell?.tagLabel.backgroundColor = .clear
                cell?.tagLabel.layer.borderWidth = 1
                cell?.tagLabel.layer.borderColor = UIColor.white.cgColor
                cell?.tagLabel.textColor = .white
            }
        }

        if indexPath.section == 4 {
            cell?.section_id = 4
            cell?.tag_id = MemoryCache.shared.tag7?[indexPath.row]["tag_id"] as! Int
            cell?.name = MemoryCache.shared.tag7?[indexPath.row]["name"] as! String
            cell?.slug = MemoryCache.shared.tag7?[indexPath.row]["slug"] as! String
            cell?.category = MemoryCache.shared.tag7?[indexPath.row]["category"] as! Int
            cell?.tagLabel.text = "\(MemoryCache.shared.tag7?[indexPath.row]["name"] as! String)"

            if MemoryCache.shared.register_user_tag_section4.contains(where: { $0 == cell?.tag_id }) || (cTags.count > 0 && cTags.contains(where: { $0 == cell?.tag_id })) {
                cell?.tagLabel.backgroundColor = .white
                cell?.tagLabel.layer.borderWidth = 1
                cell?.tagLabel.layer.borderColor = UIColor.white.cgColor
                cell?.tagLabel.textColor = .black
            } else {
                cell?.tagLabel.backgroundColor = .clear
                cell?.tagLabel.layer.borderWidth = 1
                cell?.tagLabel.layer.borderColor = UIColor.white.cgColor
                cell?.tagLabel.textColor = .white
            }
        }

        if indexPath.section == 5 {
            cell?.section_id = 5
            cell?.tag_id = MemoryCache.shared.tag8?[indexPath.row]["tag_id"] as! Int
            cell?.name = MemoryCache.shared.tag8?[indexPath.row]["name"] as! String
            cell?.slug = MemoryCache.shared.tag8?[indexPath.row]["slug"] as! String
            cell?.category = MemoryCache.shared.tag8?[indexPath.row]["category"] as! Int
            cell?.tagLabel.text = "\(MemoryCache.shared.tag8?[indexPath.row]["name"] as! String)"

            if MemoryCache.shared.register_user_tag_section5.contains(where: { $0 == cell?.tag_id }) || (cTags.count > 0 && cTags.contains(where: { $0 == cell?.tag_id })) {
                cell?.tagLabel.backgroundColor = .white
                cell?.tagLabel.layer.borderWidth = 1
                cell?.tagLabel.layer.borderColor = UIColor.white.cgColor
                cell?.tagLabel.textColor = .black
            } else {
                cell?.tagLabel.backgroundColor = .clear
                cell?.tagLabel.layer.borderWidth = 1
                cell?.tagLabel.layer.borderColor = UIColor.white.cgColor
                cell?.tagLabel.textColor = .white
            }
        }

//        guard let register_user_tag = MemoryCache.shared.register_user_tag else { return cell! }
//        if register_user_tag.contains(where: { $0 == cell?.tag_id } ) {
//            cell?.tagLabel.backgroundColor = .white
//            cell?.tagLabel.layer.borderWidth = 1
//            cell?.tagLabel.layer.borderColor = UIColor.white.cgColor
//            cell?.tagLabel.textColor = .black
//        } else {
//            cell?.tagLabel.backgroundColor = .clear
//            cell?.tagLabel.layer.borderWidth = 1
//            cell?.tagLabel.layer.borderColor = UIColor.white.cgColor
//            cell?.tagLabel.textColor = .white
//        }

        if cell?.name.count ?? 0 >= 4 {
            cell?.tagLabel.font =  .systemFont(ofSize: 10)
        } else {
            cell?.tagLabel.font =  .systemFont(ofSize: 14)
        }

        return cell!
    }

    func collectionView(_ collectionView: UICollectionView, didSelectItemAt indexPath: IndexPath) {
        print("你選擇了第 \(indexPath.section + 1) 組的")
        print("第 \(indexPath.item + 1) 張圖片")
//        guard let register_user_tag = MemoryCache.shared.register_user_tag else { return }

        if indexPath.section == 0 {
            let tag_id = MemoryCache.shared.tag2?[indexPath.row]["tag_id"] as! Int
            // 選過的取消自己
            if  MemoryCache.shared.register_user_tag_section0.contains(where: { $0 == tag_id }) {
                MemoryCache.shared.register_user_tag_section0.removeAll(where: { $0 == tag_id })
            } else if MemoryCache.shared.register_user_tag_section0.count < 100 {
                // 新增最新的
                MemoryCache.shared.register_user_tag_section0.append(tag_id)
            }
        } else if indexPath.section == 1 {
            let tag_id = MemoryCache.shared.tag4?[indexPath.row]["tag_id"] as! Int
            // 選過的取消自己
            if  MemoryCache.shared.register_user_tag_section1.contains(where: { $0 == tag_id }) {
                MemoryCache.shared.register_user_tag_section1.removeAll(where: { $0 == tag_id })
            } else if MemoryCache.shared.register_user_tag_section1.count < 100 {
                // 新增最新的
                MemoryCache.shared.register_user_tag_section1.append(tag_id)
            }
        } else if indexPath.section == 2 {
            let tag_id = MemoryCache.shared.tag5?[indexPath.row]["tag_id"] as! Int
            // 選過的取消自己
            if  MemoryCache.shared.register_user_tag_section2.contains(where: { $0 == tag_id }) {
                MemoryCache.shared.register_user_tag_section2.removeAll(where: { $0 == tag_id })
            } else if MemoryCache.shared.register_user_tag_section2.count < 100 {
                // 新增最新的
                MemoryCache.shared.register_user_tag_section2.append(tag_id)
            }
        } else if indexPath.section == 3 {
            let tag_id = MemoryCache.shared.tag6?[indexPath.row]["tag_id"] as! Int
            // 選過的取消自己
            if  MemoryCache.shared.register_user_tag_section3.contains(where: { $0 == tag_id }) {
                MemoryCache.shared.register_user_tag_section3.removeAll(where: { $0 == tag_id })
            } else if MemoryCache.shared.register_user_tag_section3.count < 100 {
                // 新增最新的
                MemoryCache.shared.register_user_tag_section3.append(tag_id)
            }
        } else if indexPath.section == 4 {
            let tag_id = MemoryCache.shared.tag7?[indexPath.row]["tag_id"] as! Int
            // 選過的取消自己
            if  MemoryCache.shared.register_user_tag_section4.contains(where: { $0 == tag_id }) {
                MemoryCache.shared.register_user_tag_section4.removeAll(where: { $0 == tag_id })
            } else if MemoryCache.shared.register_user_tag_section4.count < 100 {
                // 新增最新的
                MemoryCache.shared.register_user_tag_section4.append(tag_id)
            }
        } else if indexPath.section == 5 {
            let tag_id = MemoryCache.shared.tag8?[indexPath.row]["tag_id"] as! Int
            // 選過的取消自己
            if  MemoryCache.shared.register_user_tag_section5.contains(where: { $0 == tag_id }) {
                MemoryCache.shared.register_user_tag_section5.removeAll(where: { $0 == tag_id })
            } else if MemoryCache.shared.register_user_tag_section5.count < 100 {
                // 新增最新的
                MemoryCache.shared.register_user_tag_section5.append(tag_id)
            }
        }

        collectionView.reloadData()
    }

    // 設置 reuse 的 section 的 header 或 footer
    func collectionView(_ collectionView: UICollectionView, viewForSupplementaryElementOfKind kind: String, at indexPath: IndexPath) -> UICollectionReusableView {
        // 建立 UICollectionReusableView
        var reusableView = UICollectionReusableView()

        // 顯示文字
        let label = UILabel(frame: CGRect(x: 0, y: 0, width: collectionView.bounds.width, height: 40))
        label.textAlignment = .center

        // header
        if kind == UICollectionView.elementKindSectionHeader {
            // 依據前面註冊設置的識別名稱 "Header" 取得目前使用的 header
            reusableView = collectionView.dequeueReusableSupplementaryView(
                ofKind: UICollectionView.elementKindSectionHeader, withReuseIdentifier: "Header", for: indexPath as IndexPath)
            // 設置 header 的內容
            reusableView.backgroundColor = .clear
            label.text = "Header"
            if indexPath.section == 0 {
                label.text = "受眾年齡"
            }
//            else if indexPath.section == 1 {
//                label.text = "受眾地區"
//            }
            else if indexPath.section == 1 {
                label.text = "興趣專長"
            } else if indexPath.section == 2 {
                label.text = "所屬社群"
            } else if indexPath.section == 3 {
                label.text = "適合內容"
            } else if indexPath.section == 4 {
                label.text = "職業屬性"
            } else if indexPath.section == 5 {
                label.text = "個人風格"
            }
            label.textColor = UIColor.white

        } else if kind == UICollectionView.elementKindSectionFooter {
            // 依據前面註冊設置的識別名稱 "Footer" 取得目前使用的 footer
            reusableView =  collectionView.dequeueReusableSupplementaryView(ofKind: UICollectionView.elementKindSectionFooter, withReuseIdentifier: "Footer", for: indexPath as IndexPath)
            // 設置 footer 的內容
            reusableView.backgroundColor = .clear
            label.text = ""
            label.textColor = .clear
        }
        label.backgroundColor = .black
        reusableView.addSubview(label)
        return reusableView
    }

}
