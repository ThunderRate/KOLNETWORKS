//
//  Html+UILabel.swift
//  kolnapp
//
//  Created by 黃世維 on 2020/5/1.
//  Copyright © 2020 黃世維. All rights reserved.
//

import UIKit

extension UILabel {
  func set(html: String) {
    if let htmlData = html.data(using: .unicode) {
      do {
        self.attributedText = try NSAttributedString(data: htmlData,
                                                     options: [.documentType: NSAttributedString.DocumentType.html],
                                                     documentAttributes: nil)
      } catch let e as NSError {
        print("Couldn't parse \(html): \(e.localizedDescription)")
      }
    }
  }
}
