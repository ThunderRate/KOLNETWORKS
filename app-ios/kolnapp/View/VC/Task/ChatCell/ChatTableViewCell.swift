//
//  ChatTableViewCell.swift
//  kolnapp
//
//  Created by 黃世維 on 2020/2/16.
//  Copyright © 2020 黃世維. All rights reserved.
//

import UIKit

class ChatTableViewCell: UITableViewCell {

    @IBOutlet weak var bg: UIView!
    @IBOutlet weak var msg: UILabel!
    @IBOutlet weak var date: UILabel!

    override func awakeFromNib() {
        super.awakeFromNib()

        self.bg.layer.cornerRadius = 10
        self.bg.clipsToBounds = true

        self.selectionStyle = .none
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
//        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }

}
