//
//  EndPointType.swift
//  kolnapp
//
//  Created by 黃世維 on 2020/3/20.
//  Copyright © 2020 黃世維. All rights reserved.
//

import Foundation

protocol EndPointType {
    var baseURL: URL { get }
    var path: String { get }
    var httpMethod: HTTPMethod { get }
    var task: HTTPTask { get }
    var headers: HTTPHeaders? { get }
}
