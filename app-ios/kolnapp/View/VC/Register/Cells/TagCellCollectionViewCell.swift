//
//  TagCellCollectionViewCell.swift
//  kolnapp
//
//  Created by 黃世維 on 2020/4/13.
//  Copyright © 2020 黃世維. All rights reserved.
//

import UIKit

class TagCellCollectionViewCell: UICollectionViewCell {

    var section_id = 0
    var tag_id: Int = -1
    var name: String = ""
    var slug: String = ""
    var category: Int = -1

    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
        print("reused")
    }

    var tagView: UIView!
//    var tagButton: UIButton!
    var tagLabel: UILabel!

    override init(frame: CGRect) {
        super.init(frame: frame)

        // 取得collection view寬度
        let w = Double(UIScreen.main.bounds.size.width) - 20 - 20
        tagView = UIView(frame: CGRect(x: 0, y: 0, width: w/4 - 10.0, height: 50))

        tagLabel = UILabel.init()
        tagLabel.frame = CGRect(x: 0, y: 0, width: w/4 - 15.0, height: 40)
        tagLabel.center = tagView.center
        tagLabel.font =  .systemFont(ofSize: 14)
        tagLabel.layer.borderWidth = 1
        tagLabel.layer.borderColor = UIColor.white.cgColor
        tagLabel.layer.cornerRadius = 2
        tagLabel.lineBreakMode = .byWordWrapping
        tagLabel.textAlignment = .center
        self.tagView.addSubview(tagLabel)

        self.addSubview(tagView)
    }

    required init?(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }

    @objc
    private func selectTag() {
        guard let register_user_tag = MemoryCache.shared.register_user_tag else { return }
        if register_user_tag.contains(where: { $0 == tag_id }) {
            tagLabel.backgroundColor = .clear
            tagLabel.layer.borderWidth = 1
            tagLabel.layer.borderColor = UIColor.white.cgColor
            tagLabel.textColor = .white
            MemoryCache.shared.register_user_tag!.removeAll(where: { $0 == tag_id })
        } else {
            tagLabel.backgroundColor = .white
            tagLabel.layer.borderColor = UIColor.clear.cgColor
            tagLabel.textColor = .black
            MemoryCache.shared.register_user_tag!.append(tag_id)
        }
    }

}
