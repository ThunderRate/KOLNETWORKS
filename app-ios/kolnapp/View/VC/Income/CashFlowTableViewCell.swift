//
//  CashFlowTableViewCell.swift
//  kolnapp
//
//  Created by 黃世維 on 2020/4/21.
//  Copyright © 2020 黃世維. All rights reserved.
//

import UIKit

class CashFlowTableViewCell: UITableViewCell {

    @IBOutlet weak var name: UILabel!
    @IBOutlet weak var value: UILabel!
    @IBOutlet weak var date: UILabel!
    @IBOutlet weak var commission: UILabel!

    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
        
        self.layer.cornerRadius = 8
        self.clipsToBounds = true
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }

}
