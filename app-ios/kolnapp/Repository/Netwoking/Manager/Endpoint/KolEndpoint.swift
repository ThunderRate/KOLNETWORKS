//
//  KolEndpoint.swift
//  kolnapp
//
//  Created by 黃世維 on 2020/3/20.
//  Copyright © 2020 黃世維. All rights reserved.
//

import Foundation

enum NetworkEnvironment {
    case qa
    case production
    case staging
}

public enum MovieApi {
    case recommended(id: Int, i2: String)
    case popular(page: Int)
    case newMovies(page: Int)
    case video(id: Int)
}
