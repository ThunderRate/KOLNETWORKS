//
//  HorizontalPagesCollectionView.swift
//  kolnapp
//
//  Created by 黃世維 on 2020/2/13.
//  Copyright © 2020 黃世維. All rights reserved.
//

import UIKit

class HorizontalPagesCollectionView: UICollectionView {
    enum Style {
        case normal, center
    }

    /// 決定cell要露出多少寬度
    var sectionInset: CGFloat = 0
    private var indexOfCellBeforeDragging = 0
    private var collectionViewFlowLayout: UICollectionViewFlowLayout {
        return collectionViewLayout as! UICollectionViewFlowLayout
    }

    //should call in viewDidLoad()
    func setMinimumLineSpacing(space: CGFloat = 0) {
        collectionViewFlowLayout.minimumLineSpacing = space
    }

    //should call in viewDidLayoutSubviews()
    func configureCollectionViewLayoutItemSize(sectionInset: CGFloat) {
        self.sectionInset = sectionInset

        let spacing: CGFloat = collectionViewFlowLayout.minimumLineSpacing
        let inset = spacing*2 + sectionInset
        collectionViewFlowLayout.sectionInset = UIEdgeInsets(top: 0, left: inset, bottom: 0, right: inset)

        collectionViewFlowLayout.itemSize = CGSize(width: collectionViewLayout.collectionView!.bounds.size.width - inset * 2, height: collectionViewLayout.collectionView!.frame.size.height)
    }

    private func indexOfMajorCell() -> Int {
        let itemWidth = collectionViewFlowLayout.itemSize.width
        let proportionalOffset = collectionViewLayout.collectionView!.contentOffset.x / itemWidth
        let index = Int(proportionalOffset.rounded(.toNearestOrAwayFromZero))
        let numberOfItems = self.numberOfItems(inSection: 0)
        let safeIndex = max(0, min(numberOfItems - 1, index))
        return safeIndex
    }

    // 如果系統計算位置錯誤的話需要在 scrollview delegate willBeginDragging 主動呼叫此方法.
    func syncMajorCell() {
        indexOfCellBeforeDragging = indexOfMajorCell()
    }

    func scrollToMajorCell(velocity: CGPoint, targetContentOffset: UnsafeMutablePointer<CGPoint>) -> Int {
        // Stop scrollView sliding:
        targetContentOffset.pointee = self.contentOffset

        // calculate where scrollView should snap to:
        let indexOfMajorCell = self.indexOfMajorCell()

        // This is a much better way to scroll to a cell:
        let indexPath = IndexPath(row: indexOfMajorCell, section: 0)
        self.moveTo(indexPath: indexPath)
        return indexOfMajorCell
    }

    func moveTo(indexPath: IndexPath) {
        // This is a much better way to scroll to a cell:
        collectionViewLayout.collectionView!.scrollToItem(at: indexPath, at: .centeredHorizontally, animated: true)
    }

}
