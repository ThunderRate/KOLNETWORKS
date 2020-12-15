//
//  TaskCellTableViewCell.swift
//  kolnapp
//
//  Created by Victor_Huang on 2020/2/13.
//  Copyright © 2020 Victor_Huang. All rights reserved.
//

import UIKit

class TaskCellTableViewCell: UITableViewCell {

    @IBOutlet weak var root: UIView!
    @IBOutlet weak var bg: UIView!
    @IBOutlet weak var sbg: UIView!
    @IBOutlet weak var pbg: UIView!
    @IBOutlet weak var cellImage: UIImageView!

    @IBOutlet weak var platform: UILabel!
    @IBOutlet weak var ticket: UILabel!
    @IBOutlet weak var intro: UILabel!
    @IBOutlet weak var deadline: UILabel!

    @IBOutlet weak var emptyInfo: UIStackView!
    @IBOutlet weak var emptyInfo1: UILabel!
    @IBOutlet weak var emptyInfo2: UILabel!
    
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code

        self.bg.layer.cornerRadius = 10
        self.bg.clipsToBounds = true
        // shadow
        self.sbg.layer.cornerRadius = 10
        self.sbg.layer.makeShadow(color: .lightGray, x: 0, y: 6, blur: 10, spread: 0)
        self.sbg.clipsToBounds = false
        self.contentView.clipsToBounds = false

        self.selectionStyle = .none
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
//        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }
    
    func isEmptyCard(idEmptyCard: Bool, info1: String = "", info2: String = "") {
        emptyInfo.isHidden = !idEmptyCard
        emptyInfo1.text = info1
        emptyInfo2.text = info2
        
//        sbg.isHidden = idEmptyCard
        cellImage.isHidden = idEmptyCard
        platform.isHidden = idEmptyCard
        pbg.isHidden = idEmptyCard
        ticket.isHidden = idEmptyCard
        intro.isHidden = idEmptyCard
        deadline.isHidden = idEmptyCard
    }

}

extension CALayer {
    func makeShadow(color: UIColor,
                    x: CGFloat = 0,
                    y: CGFloat = 0,
                    blur: CGFloat = 0,
                    spread: CGFloat = 0) {
        shadowColor = color.cgColor
        shadowOpacity = 1
        shadowOffset = CGSize(width: x, height: y)
        shadowRadius = blur / 2
        if spread == 0 {
            shadowPath = nil
        } else {
            let rect = bounds.insetBy(dx: -spread, dy: -spread)
            shadowPath = UIBezierPath(rect: rect).cgPath
        }
    }
}

extension UIColor {
    func hexStringToUIColor (hex: String) -> UIColor {
        var cString: String = hex.trimmingCharacters(in: .whitespacesAndNewlines).uppercased()

        if cString.hasPrefix("#") {
            cString.remove(at: cString.startIndex)
        }

        if (cString.count) != 6 {
            return UIColor.gray
        }

        var rgbValue: UInt64 = 0
        Scanner(string: cString).scanHexInt64(&rgbValue)

        return UIColor(
            red: CGFloat((rgbValue & 0xFF0000) >> 16) / 255.0,
            green: CGFloat((rgbValue & 0x00FF00) >> 8) / 255.0,
            blue: CGFloat(rgbValue & 0x0000FF) / 255.0,
            alpha: CGFloat(1.0)
        )
    }
}
