//
//  ProfileType2Cell.swift
//  kolnapp
//
//  Created by 黃世維 on 2020/4/11.
//  Copyright © 2020 黃世維. All rights reserved.
//

import UIKit

class ProfileType2Cell: UITableViewCell {

    @IBOutlet weak var profileKey: UILabel!
    @IBOutlet weak var profileVlaue: UILabel!
    @IBOutlet weak var refreshButton: UIButton!

    @IBAction func upadteUser(_ sender: Any) {
        print("upadte user")
    }

    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
//        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }

}
