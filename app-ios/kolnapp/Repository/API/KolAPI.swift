//
//  KolAPI.swift
//  kolnapp
//
//  Created by Victor_Huang on 2020/2/12.
//  Copyright © 2020 Victor_Huang. All rights reserved.
//

import Foundation
import UIKit

// TODO: 增加network layer.
// TODO: 封裝成一個request.
final class KolAPI {

    static let shared = KolAPI()
    private init() {}

    static var token: String?

    // basic get
    static private func get(url: String, parameters: [String: Any], completion: @escaping ([String: Any]?, Error?) -> Void) {

        var components = URLComponents(string: url)!
        components.queryItems = parameters.map { (arg) -> URLQueryItem in
            let (key, value) = arg
            return URLQueryItem(name: key, value: value as? String)
        }
        components.percentEncodedQuery = components.percentEncodedQuery?.replacingOccurrences(of: "+", with: "%2B")

        var request = URLRequest(url: components.url!)
        request.httpMethod = "GET"
        request.addValue("application/json", forHTTPHeaderField: "Accept")
        request.addValue("application/json", forHTTPHeaderField: "Content-Type")
        request.httpShouldHandleCookies = false

        if let token = token {
           request.addValue("Bearer \(token)", forHTTPHeaderField: "Authorization")
        }

        print("Current request: \(request) ,body: \(request.httpBody), headers: \(request.allHTTPHeaderFields)")
        
        let configuration = URLSessionConfiguration.default
        configuration.timeoutIntervalForRequest = TimeInterval(30)
        configuration.timeoutIntervalForResource = TimeInterval(30)
        let session = URLSession(configuration: configuration)

        let task = session.dataTask(with: request) { data, response, error in
            guard let data = data,                            // is there data
                let response = response as? HTTPURLResponse,  // is there HTTP response
                (200 ..< 500) ~= response.statusCode,         // is statusCode 2XX
                error == nil else {                           // was there no error, otherwise ...
                    completion(nil, error)
                    return
            }

            let responseObject = (try? JSONSerialization.jsonObject(with: data)) as? [String: Any]
//            print(responseObject)
            if let responseObject = (try? JSONSerialization.jsonObject(with: data)) as? [String: Any] {
//                print("data to jsonObject: \(responseObject)")
                DispatchQueue.main.async {
                    completion(responseObject, error)
                }

            }
        }
        task.resume()
    }

    // basic post
    static private func post(url: String, parameters: [String: Any], completion: @escaping ([String: Any]?, Error?) -> Void) {

        let components = URL(string: url)
        var request = URLRequest(url: components!)
        request.httpMethod = "POST"
        request.addValue("application/json", forHTTPHeaderField: "Accept")
        request.addValue("application/json", forHTTPHeaderField: "Content-Type")
        request.httpShouldHandleCookies = false

        if let token = token {
           request.addValue("Bearer \(token)", forHTTPHeaderField: "Authorization")
        }

        do {
            request.httpBody = try JSONSerialization.data(withJSONObject: parameters, options: .prettyPrinted)

        } catch let error {
            print(error)
        }
        
        let configuration = URLSessionConfiguration.default
        configuration.timeoutIntervalForRequest = TimeInterval(7)
        configuration.timeoutIntervalForResource = TimeInterval(7)
        let session = URLSession(configuration: configuration)

        let task = session.dataTask(with: request) { data, response, error in
            print("response: \(response)")

            guard let data = data,                            // is there data
                let response = response as? HTTPURLResponse,  // is there HTTP response
                (200 ..< 500) ~= response.statusCode,         // is statusCode 2XX
                error == nil else {                           // was there no error, otherwise ...
                    completion(nil, error)
                    return
            }

            if let responseObject = (try? JSONSerialization.jsonObject(with: data)) as? [String: Any] {
                print("data to jsonObject: \(responseObject)")
                DispatchQueue.main.async {
                    completion(responseObject, error)
                }
            }

        }
        task.resume()
    }

    // basic get
    static private func put(url: String, parameters: [String: Any], completion: @escaping ([String: Any]?, Error?) -> Void) {

        let components = URL(string: url)
        var request = URLRequest(url: components!)
        request.httpMethod = "PUT"
        request.addValue("application/json", forHTTPHeaderField: "Accept")
        request.addValue("application/json", forHTTPHeaderField: "Content-Type")
        request.httpShouldHandleCookies = false

        if let token = token {
           request.addValue("Bearer \(token)", forHTTPHeaderField: "Authorization")
        }

        do {
            request.httpBody = try JSONSerialization.data(withJSONObject: parameters, options: .prettyPrinted)

        } catch let error {
            print(error)
        }
        
        let configuration = URLSessionConfiguration.default
        configuration.timeoutIntervalForRequest = TimeInterval(7)
        configuration.timeoutIntervalForResource = TimeInterval(7)
        let session = URLSession(configuration: configuration)

        print("update put somthing")
        let task = session.dataTask(with: request) { data, response, error in
            guard let data = data,                            // is there data
                let response = response as? HTTPURLResponse,  // is there HTTP response
                (200 ..< 500) ~= response.statusCode,         // is statusCode 2XX
                error == nil else {                           // was there no error, otherwise ...
                    completion(nil, error)
                    return
            }

            print("update put error  \(error)")
            print("update put response  \(response)")
            let responseObject = (try? JSONSerialization.jsonObject(with: data)) as? [String: Any]
            print("update put json \(responseObject)")
            if let responseObject = (try? JSONSerialization.jsonObject(with: data)) as? [String: Any] {
                print("data to jsonObject: \(responseObject)")
                DispatchQueue.main.async {
                    completion(responseObject, error)
                }

            }
        }
        task.resume()
    }

    // TODO: 封裝執行事件, http status handler
    private func fetchedDataByDataTask(from request: URLRequest, completion: @escaping (Data) -> Void) {

        let task = URLSession.shared.dataTask(with: request) { (data, _, error) in

            if error != nil {
                print(error as Any)
            } else {
                guard let data = data else {return}
                completion(data)
            }
        }
        task.resume()
    }

    static func serverError502() {
        let alertController = UIAlertController(title: "連線失敗", message: "請稍後再試或著聯絡系統管理員", preferredStyle: .alert)
        let okAction = UIAlertAction(
            title: "確認",
            style: .default,
            handler: {
            (_: UIAlertAction!) -> Void in
              print("按下確認後，閉包裡的動作")
        })
        alertController.addAction(okAction)

        DispatchQueue.main.async {
            alertController.show(alertController, sender: nil)
        }
    }
}

extension KolAPI {
    // TODO: Refresh API Token
    static func refresh(completion: @escaping ([String: Any]?, Error?) -> Void) {
        post(url: "https://api.kolnetworks.com/login", parameters: MemoryCache.shared.p, completion: completion)
    }
}

// 專案經理專用API
extension KolAPI {
    /// 檢查權限.
    static func checkPermissions(completion: @escaping ([String: Any]?, Error?) -> Void) {
        let parameters: [String: Any] = Dictionary()
        get(url: "https://api.kolnetworks.com/layouts", parameters: parameters, completion: completion)
    }
    /// 取得專案經理可管理的專案清單
    static func getProjects(completion: @escaping ([String: Any]?, Error?) -> Void) {
        var parameters: [String: Any] = Dictionary()
        parameters.updateValue("1", forKey: "page")
        parameters.updateValue("1000", forKey: "pageSize")
        get(url: "https://api.kolnetworks.com/projects/management", parameters: parameters, completion: completion)
    }
    static func getProjectsWithoutM(completion: @escaping ([String: Any]?, Error?) -> Void) {
        var parameters: [String: Any] = Dictionary()
        parameters.updateValue("1", forKey: "page")
        parameters.updateValue("1000", forKey: "pageSize")
        get(url: "https://api.kolnetworks.com/projects", parameters: parameters, completion: completion)
    }
    /// 取得專案經理可管理的專案細節 by uuid.
    // 只有申請合作
    static func getApplyProjectByUUID(projectID: String, completion: @escaping ([String: Any]?, Error?) -> Void) {
        var parameters: [String: Any] = Dictionary()
        parameters.updateValue("3,4,5,6,7", forKey: "kolStatus")
        parameters.updateValue("false", forKey: "isPicked")
        get(url: "https://api.kolnetworks.com/projectkols/\(projectID)", parameters: parameters, completion: completion)
    }
    // 逛逛邀約
    static func getInvitationProjectByUUID(projectID: String, completion: @escaping ([String: Any]?, Error?) -> Void) {
        var parameters: [String: Any] = Dictionary()
        parameters.updateValue("1,3,4,5,7", forKey: "kolStatus")
        parameters.updateValue("true", forKey: "isPicked")
        get(url: "https://api.kolnetworks.com/projectkols/\(projectID)", parameters: parameters, completion: completion)
    }
    // 拒絕申請合作
    static func rejectKolApply(parameters: [String: Any], completion: @escaping ([String: Any]?, Error?) -> Void) {
        post(url: "https://api.kolnetworks.com/projects/reject", parameters: parameters, completion: completion)
    }
    // 同意申請合作
    static func comfirmKolApply(parameters: [String: Any], completion: @escaping ([String: Any]?, Error?) -> Void) {
        post(url: "https://api.kolnetworks.com/projects/confirm", parameters: parameters, completion: completion)
    }
    // 取得審核連結
    static func getTargetKolReviewUri(projectUUID: String, kolUUID: String, completion: @escaping ([String: Any]?, Error?) -> Void) {
        var parameters: [String: Any] = Dictionary()
        parameters.updateValue(kolUUID, forKey: "kol_uuid")
        parameters.updateValue("created_at", forKey: "orderby")
        get(url: "https://api.kolnetworks.com/projects/report/\(projectUUID)", parameters: parameters, completion: completion)
    }
    // 同意審核連結
    static func comfirmReviewUri(parameters: [String: Any], completion: @escaping ([String: Any]?, Error?) -> Void) {
        post(url: "https://api.kolnetworks.com/projects/report/confirm", parameters: parameters, completion: completion)
    }
    // 駁回審核連結
    static func rejectReviewUri(parameters: [String: Any], completion: @escaping ([String: Any]?, Error?) -> Void) {
        post(url: "https://api.kolnetworks.com/projects/report/reject", parameters: parameters, completion: completion)
    }
    // 修改專案狀態
    static func startProject(parameters: [String: Any], completion: @escaping ([String: Any]?, Error?) -> Void) {
        put(url: "https://api.kolnetworks.com/projects/status", parameters: parameters, completion: completion)
    }
}

// KOL專用API
extension KolAPI {
    // TODO: 以host組成endpoint
    static func register(parameters: [String: Any], completion: @escaping ([String: Any]?, Error?) -> Void) {
        post(url: "https://api.kolnetworks.com/register", parameters: parameters, completion: completion)
    }
    /// 重設密碼
    static func resetPassword(parameters: [String: Any], completion: @escaping ([String: Any]?, Error?) -> Void) {
        post(url: "https://api.kolnetworks.com/reset/token", parameters: parameters, completion: completion)
    }
    /// link ig
    static func link_ig(parameters: [String: Any], completion: @escaping ([String: Any]?, Error?) -> Void) {
        post(url: "https://api.kolnetworks.com/users/linkig", parameters: parameters, completion: completion)
    }
    /// link fb
    static func link_fb(parameters: [String: Any], completion: @escaping ([String: Any]?, Error?) -> Void) {
        post(url: "https://api.kolnetworks.com/users/linkfb", parameters: parameters, completion: completion)
    }
    /// link yt
    static func link_yt(parameters: [String: Any], completion: @escaping ([String: Any]?, Error?) -> Void) {
        post(url: "https://api.kolnetworks.com/users/linkyt", parameters: parameters, completion: completion)
    }
    /// link linkblog
    static func link_blog(parameters: [String: Any], completion: @escaping ([String: Any]?, Error?) -> Void) {
        post(url: "https://api.kolnetworks.com/users/linkblog", parameters: parameters, completion: completion)
    }


    static func login(parameters: [String: Any], completion: @escaping ([String: Any]?, Error?) -> Void) {
        post(url: "https://api.kolnetworks.com/login", parameters: parameters, completion: completion)
    }
    /// User Profile
    static func userProfile(completion: @escaping ([String: Any]?, Error?) -> Void) {
        let parameters: [String: Any] = Dictionary()
        get(url: "https://api.kolnetworks.com/user", parameters: parameters, completion: completion)
    }
    /// 更新 User Profile
    static func putUser(parameters: [String: Any], completion: @escaping ([String: Any]?, Error?) -> Void) {
        print("updateUser putUser send: \(parameters)")
        put(url: "https://api.kolnetworks.com/users", parameters: parameters, completion: completion)
    }
    /// +Tags
    static func assainTag(parameters: [String: Any], completion: @escaping ([String: Any]?, Error?) -> Void) {
        post(url: "https://api.kolnetworks.com/tags/user/assign", parameters: parameters, completion: completion)
    }
    /// Tags
    static func unassainTag(parameters: [String: Any], completion: @escaping ([String: Any]?, Error?) -> Void) {
        post(url: "https://api.kolnetworks.com/tags/user/unassign", parameters: parameters, completion: completion)
    }
    /// 抓取收益總覽
    static func cashflow(completion: @escaping ([String: Any]?, Error?) -> Void) {
        var parameters: [String: Any] = Dictionary()
        parameters.updateValue("1", forKey: "page")
        parameters.updateValue("100", forKey: "pageSize")
        parameters.updateValue("cashflow_id", forKey: "orderby")
//        parameters.updateValue("1", forKey: "search")
        get(url: "https://api.kolnetworks.com/my/cashflow", parameters: parameters, completion: completion)
    }

    /// 抓取邀請的工作清單
    static func invitaion(completion: @escaping ([String: Any]?, Error?) -> Void) {
        var parameters: [String: Any] = Dictionary()
        parameters.updateValue("1", forKey: "page")
        parameters.updateValue("100", forKey: "pageSize")
        parameters.updateValue("2,3,4,5,6", forKey: "projectStatus")
        parameters.updateValue("1", forKey: "kolStatus")
        get(url: "https://api.kolnetworks.com/my/projects", parameters: parameters, completion: completion)
    }
    /// 抓取配對的清單  => 改 逛逛   
    static func paring(completion: @escaping ([String: Any]?, Error?) -> Void) {
        var parameters: [String: Any] = Dictionary()
        parameters.updateValue("1", forKey: "page")
        parameters.updateValue("100", forKey: "pageSize")
        parameters.updateValue("2,3,4,5,6", forKey: "projectStatus")
        parameters.updateValue("2", forKey: "kolStatus")
        get(url: "https://api.kolnetworks.com/my/projects", parameters: parameters, completion: completion)
    }
    /// 抓取已申請的清單
    static func applied(completion: @escaping ([String: Any]?, Error?) -> Void) {
        var parameters: [String: Any] = Dictionary()
        parameters.updateValue("1", forKey: "page")
        parameters.updateValue("100", forKey: "pageSize")
        parameters.updateValue("2,3,4,5,6", forKey: "projectStatus")
        parameters.updateValue("6", forKey: "kolStatus")
        get(url: "https://api.kolnetworks.com/my/projects", parameters: parameters, completion: completion)
    }
    static func applied2(completion: @escaping ([String: Any]?, Error?) -> Void) {
        var parameters: [String: Any] = Dictionary()
        parameters.updateValue("1", forKey: "page")
        parameters.updateValue("100", forKey: "pageSize")
        parameters.updateValue("2,3,4,5,6", forKey: "projectStatus")
        parameters.updateValue("7", forKey: "kolStatus")
        get(url: "https://api.kolnetworks.com/my/projects", parameters: parameters, completion: completion)
    }
    
    /// 抓取尚未進行的任務清單
    static func unactive(completion: @escaping ([String: Any]?, Error?) -> Void) {
        var parameters: [String: Any] = Dictionary()
        parameters.updateValue("1", forKey: "page")
        parameters.updateValue("100", forKey: "pageSize")
        parameters.updateValue("2,3", forKey: "projectStatus")
        parameters.updateValue("3", forKey: "kolStatus")
        get(url: "https://api.kolnetworks.com/my/projects", parameters: parameters, completion: completion)
    }
    /// 抓取任務進行中的清單
    static func active(completion: @escaping ([String: Any]?, Error?) -> Void) {
        var parameters: [String: Any] = Dictionary()
        parameters.updateValue("1", forKey: "page")
        parameters.updateValue("100", forKey: "pageSize")
        parameters.updateValue("4", forKey: "projectStatus")
        parameters.updateValue("3,4,5", forKey: "kolStatus")
        get(url: "https://api.kolnetworks.com/my/projects", parameters: parameters, completion: completion)
    }
    static func active2(completion: @escaping ([String: Any]?, Error?) -> Void) {
        var parameters: [String: Any] = Dictionary()
        parameters.updateValue("1", forKey: "page")
        parameters.updateValue("100", forKey: "pageSize")
        parameters.updateValue("5", forKey: "projectStatus")
        parameters.updateValue("3,4,5", forKey: "kolStatus")
        get(url: "https://api.kolnetworks.com/my/projects", parameters: parameters, completion: completion)
    }
    /// 抓取已經完成的任務清單, 抓取審核中以及結案中尚未撥款的專案
    static func active3(completion: @escaping ([String: Any]?, Error?) -> Void) {
        var parameters: [String: Any] = Dictionary()
        parameters.updateValue("1", forKey: "page")
        parameters.updateValue("100", forKey: "pageSize")
        parameters.updateValue("6", forKey: "projectStatus")
        parameters.updateValue("3,4,5", forKey: "kolStatus")
        get(url: "https://api.kolnetworks.com/my/projects", parameters: parameters, completion: completion)
    }
    
    /// 抓取已經完成的任務清單, 抓取已結案的專案
    static func complete(completion: @escaping ([String: Any]?, Error?) -> Void) {
        var parameters: [String: Any] = Dictionary()
        parameters.updateValue("1", forKey: "page")
        parameters.updateValue("100", forKey: "pageSize")
        parameters.updateValue("7", forKey: "projectStatus") // 5 or 7
        parameters.updateValue("5", forKey: "kolStatus")
        get(url: "https://api.kolnetworks.com/my/projects", parameters: parameters, completion: completion)
    }
    
    /// 抓起留言板資訊
    static func getBoard(board_uuid: String, completion: @escaping ([String: Any]?, Error?) -> Void) {
        let parameters: [String: Any] = Dictionary()
        let url = String.init(format: "https://api.kolnetworks.com/boards/%@", board_uuid.urlEncoded())
        get(url: url, parameters: parameters, completion: completion)
    }
    /// 抓送出一則留言
    static func sendMessage(body: [String: Any], completion: @escaping ([String: Any]?, Error?) -> Void) {
        post(url: "https://api.kolnetworks.com/comments", parameters: body, completion: completion)
    }

    /// 送出一則任務申請
    static func apply(body: [String: Any], completion: @escaping ([String: Any]?, Error?) -> Void) {
       post(url: "https://api.kolnetworks.com/projects/apply", parameters: body, completion: completion)
    }
    /// 送出一則邀請同意
    static func agree(body: [String: Any], completion: @escaping ([String: Any]?, Error?) -> Void) {
//       post(url: "https://api.kolnetworks.com/projects/submit", parameters: body, completion: completion)
        post(url: "https://api.kolnetworks.com/projects/confirm", parameters: body, completion: completion)
    }
    /// 拒絕任務邀請
    static func reject(body: [String: Any], completion: @escaping ([String: Any]?, Error?) -> Void) {
       post(url: "https://api.kolnetworks.com/projects/reject", parameters: body, completion: completion)
    }
    /// 提交任務
    static func report(body: [String: Any], completion: @escaping ([String: Any]?, Error?) -> Void) {
       post(url: "https://api.kolnetworks.com/projects/report/submit", parameters: body, completion: completion)
    }

    /// 取得Tag
    static func getTags(category: String, completion: @escaping ([String: Any]?, Error?) -> Void) {
        var parameters: [String: Any] = Dictionary()
        parameters.updateValue(category, forKey: "ia")
        parameters.updateValue("100", forKey: "pageSize")
        parameters.updateValue("1", forKey: "page")
        get(url: "https://api.kolnetworks.com/tags", parameters: parameters, completion: completion)
    }
    
    /// 根據ＵＵＩＤ取得專案內容
    static func getProjectByUUID(project_uuid: String, completion: @escaping ([String: Any]?, Error?) -> Void) {
        let parameters: [String: Any] = Dictionary()
        get(url: "https://api.kolnetworks.com/projects/\(project_uuid)", parameters: parameters, completion: completion)
    }
}

extension String {
    func urlEncoded() -> String {
        let encodeUrlString = self.addingPercentEncoding(withAllowedCharacters: .urlQueryAllowed)
        return encodeUrlString ?? ""
    }
}
