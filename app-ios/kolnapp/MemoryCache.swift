//
//  MemoryCache.swift
//  kolnapp
//
//  Created by 黃世維 on 2020/2/16.
//  Copyright © 2020 黃世維. All rights reserved.
//

import Foundation

enum Platform {
    case fb, ig, blog, others
}

enum TaskStatus {
    ///新邀約 => 改 專屬
    case invited
    ///同意邀約 => 改 專屬
    case accepted
    ///拒絕邀約 => 改 專屬
    case rejected
    ///新配對 => 改 逛逛
    case paring
    ///同意配對 => 改 逛逛
    case agreePiring
    ///配對審核中 => 改 逛逛
    case reviewParing
    ///同意配對申請 => 改 逛逛
    case acceptParing
    ///拒絕配對申請 => 改 逛逛
    case notPass
}

enum Identity {
    ///系統
    case system
    ///案主
    case owner
    ///網紅
    case utuber
}

struct TaskObject {
    var title: String
    var from: Platform
    var paid: Float
    var date: Date
    var type: TaskStatus
    var msg: [Chat]?
}

struct Chat {
    var msg: String
    var date: Date
    var owner: Identity
}

open class MemoryCache {
    private init() {}

    static let shared = MemoryCache()

    var invitation_code: String = ""
    var isManager: Bool = false

    // Temp Data
    /// 記錄推播點擊事件
    var kolNotify: Int = -1
    var kolNotifyProjectUUID: String = ""
    var p: [String: Any] = Dictionary()
    var register_user_data: [String: Any]? = [:]
    var register_user_tag: [Int]? = []
    var register_user_tag_section0: [Int] = []
    var register_user_tag_section1: [Int] = []
    var register_user_tag_section2: [Int] = []
    var register_user_tag_section3: [Int] = []
    var register_user_tag_section4: [Int] = []
    var register_user_tag_section5: [Int] = []

    var userData: [String: Any]?
    var userDataCashFlow: [[String: Any]]?
    var userDataCashFlow_income_none: [[String: Any]] = []
    var userDataCashFlow_income: [[String: Any]] = []
    var sendEmailForgetTempData: [String: Any] = [:]
    var sendSubmitTempData: [String: Any] = [:]
    
    var sendReviewTempData: [String: Any] = [:]
    var sendReviewCallback: (() -> ())?

    var eename: String = ""

    ///Tags
    var tag2: [[String: Any]]?
    var tag3: [[String: Any]]?
    var tag4: [[String: Any]]?
    var tag5: [[String: Any]]?
    var tag6: [[String: Any]]?
    var tag7: [[String: Any]]?
    var tag8: [[String: Any]]?

    func clearTempData() {
        inviteData.removeAll()
        inviteBoardData.removeAll()
        paringData.removeAll()
        paringBoardData.removeAll()
        appliedData.removeAll()
        appliedBoardData.removeAll()
        doingData.removeAll()
        doingBoardData.removeAll()
        doneData.removeAll()
        doneBoardData.removeAll()
        waitingData.removeAll()
        waitingBoardData.removeAll()
    }

    /// 逛逛
    //邀約
    var inviteData: [[String: Any]] = .init()
    /// 同時只會有一個 BoardData
    var inviteBoardData: [String: Any] = .init()
    //配對 => 改 逛逛   
    var paringData: [[String: Any]] = .init()
    var paringBoardData: [String: Any] = .init()
    //已申請
    var appliedData: [[String: Any]] = .init()
    var appliedBoardData: [String: Any] = .init()

    /// 任務
    //進行
    var doingData: [[String: Any]] = .init()
    var doingBoardData: [String: Any] = .init()
    //結束
    var doneData: [[String: Any]] = .init()
    var doneBoardData: [String: Any] = .init()
    //尚未開始
    var waitingData: [[String: Any]] = .init()
    var waitingBoardData: [String: Any] = .init()
    
    //專案經理-我的專案
    var myprojectData: [[String: Any]] = .init()
    var myprojectBoardData: [String: Any] = .init()
    
    var kols: [[String: Any]] = .init()
    var invitation_kols: [[String: Any]] = .init()

    // Test Data
    /// 逛逛
//    var shoppingMall: [TaskObject] = [TaskObject]()
//    //邀約
//    var invitedTask: [TaskObject] = [TaskObject(title: "i1", from: .fb, paid: 2000, date: Date(), type: .invited), TaskObject(title: "i2", from: .fb, paid: 2000, date: Date(), type: .invited)]
//    //配對
//    var paringTask: [TaskObject] = [TaskObject(title: "p1", from: .fb, paid: 2000, date: Date(), type: .invited), TaskObject(title: "p2", from: .fb, paid: 2000, date: Date(), type: .invited)]
//    //已申請
//    var appliedTask: [TaskObject] = [TaskObject(title: "a1", from: .fb, paid: 2000, date: Date(), type: .invited), TaskObject(title: "a2", from: .fb, paid: 2000, date: Date(), type: .invited)]
//
//    /// 任務
//    var allTask: [TaskObject] = [TaskObject]()
//    //進行
//    var doingTask: [TaskObject] = [TaskObject(title: "doing1", from: .fb, paid: 2000, date: Date(), type: .invited), TaskObject(title: "doing2", from: .fb, paid: 2000, date: Date(), type: .invited)]
//    //結束
//    var doneTask: [TaskObject] = [TaskObject(title: "done", from: .fb, paid: 2000, date: Date(), type: .invited)]
//    //尚未開始
//    var waiting: [TaskObject] = [TaskObject(title: "waiting", from: .fb, paid: 2000, date: Date(), type: .invited)]
//    //我的專案
//    var myTask: [TaskObject] = [TaskObject(title: "my", from: .fb, paid: 2000, date: Date(), type: .agreePiring)]

    var income: [Int] = [2000, 3000, 4000]
}
