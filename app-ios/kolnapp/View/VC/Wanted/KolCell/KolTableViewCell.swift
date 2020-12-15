//
//  KolTableViewCell.swift
//  kolnapp
//
//  Created by 🍙 Dodo 🍙 on 2020/7/7.
//  Copyright © 2020 黃世維. All rights reserved.
//

import UIKit

class KolTableViewCell: UITableViewCell {
    
    @IBOutlet weak var avatar: UIImageView!
    @IBOutlet weak var name: UILabel!
    @IBOutlet weak var pay: UILabel!
    @IBOutlet weak var kolState: UILabel!
    @IBOutlet weak var kolActionControl: UIStackView!
    @IBOutlet weak var rejectBtn: UIButton!
    @IBOutlet weak var agreeBtn: UIButton!
    
    var kolStatus: Int = 0
    var projectStatus: Int = 0
    var kol_uuid: String = ""
    var project_uuid: String = ""
    
    var showSubmitVC: (() -> ())?
    var successCallback: (() -> ())?

    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
        avatar.layer.cornerRadius = avatar.bounds.size.width/2
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
//        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }
    
    func setStatus(kolStatus: Int, projectStatus: Int, isPicked: Bool) {
        self.kolStatus = kolStatus
        self.projectStatus = projectStatus
        
        // 申請合作顯示
        if kolStatus == 6 {
            kolState.isHidden = true
            kolActionControl.isHidden = false
        }
        else if kolStatus == 4 {
            kolState.isHidden = false
            kolActionControl.isHidden = true
            kolState.text = "審核連結"
            kolState.textColor = #colorLiteral(red: 0, green: 0.8552512527, blue: 0.7037933469, alpha: 1)

            let tap1 = UITapGestureRecognizer(target: self, action: #selector(review))
            kolState.isUserInteractionEnabled = true
            kolState.addGestureRecognizer(tap1)
        }
        else if kolStatus == 5 {
            kolState.isHidden = false
            kolActionControl.isHidden = true
            kolState.text = "已審核"
        }
        else if kolStatus == 3 {
            kolState.isHidden = false
            kolActionControl.isHidden = true
            kolState.text = "同意"
        }
        else if kolStatus == 7 && !isPicked {
            kolState.isHidden = false
            kolActionControl.isHidden = true
            kolState.text = "拒絕"
        }
        
        else if kolStatus == 1 {
            // 邀約清單顯示
            kolState.isHidden = false
            kolActionControl.isHidden = true
            kolState.text = "尚未回覆"
        }
        else if kolStatus == 3 {
            kolState.isHidden = false
            kolActionControl.isHidden = true
            kolState.text = "已同意"
        }
        else if kolStatus == 3 && projectStatus >= 4 {
            kolState.isHidden = false
            kolActionControl.isHidden = true
            kolState.text = "尚未回覆"
        }
        else if kolStatus == 4 {
            kolState.isHidden = false
            kolActionControl.isHidden = true
            kolState.text = "審核連結"
            kolState.textColor = #colorLiteral(red: 0, green: 0.8552512527, blue: 0.7037933469, alpha: 1)
            
            let tap1 = UITapGestureRecognizer(target: self, action: #selector(review))
            kolState.isUserInteractionEnabled = true
            kolState.addGestureRecognizer(tap1)
        }
        else if kolStatus == 5 {
            kolState.isHidden = false
            kolActionControl.isHidden = true
            kolState.text = "已審核"
        }
        else if kolStatus == 7 && isPicked {
            kolState.isHidden = false
            kolActionControl.isHidden = true
            kolState.text = "已拒絕"
        }
        
        if kolState.isHidden {
            rejectBtn.addTarget(self, action: #selector(reject), for: .touchUpInside)
            agreeBtn.addTarget(self, action: #selector(comfirm), for: .touchUpInside)
        }
    }
    
    @objc func review() {
        guard let showSubmitVC = showSubmitVC else { return }
        showSubmitVC()
        
        var parameters: [String: Any] = Dictionary()
        parameters.updateValue("1,3,4,5,7", forKey: "kolStatus")
        parameters.updateValue("true", forKey: "isPicked")
    }
    
    @objc func comfirm() {
        var parameters: [String: Any] = Dictionary()
        parameters.updateValue(project_uuid, forKey: "project_uuid")
        parameters.updateValue(kol_uuid, forKey: "kol_uuid")
        
        KolAPI.comfirmKolApply(parameters: parameters, completion: { [weak self] response, error in
            print("comfirmKolApply \(response)")
            guard let callback = self?.successCallback else { return }
            callback()
        })
    }
    
    @objc func reject() {
        var parameters: [String: Any] = Dictionary()
        parameters.updateValue(project_uuid, forKey: "project_uuid")
        parameters.updateValue(kol_uuid, forKey: "kol_uuid")
        
        KolAPI.rejectKolApply(parameters: parameters, completion: { [weak self] response, error in
            print("rejectKolApply \(response)")
            guard let callback = self?.successCallback else { return }
            callback()
        })
    }
    
}
