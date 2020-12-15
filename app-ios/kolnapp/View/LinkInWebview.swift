//
//  LinkInWebview.swift
//  kolnapp
//
//  Created by 黃世維 on 2020/6/1.
//  Copyright © 2020 黃世維. All rights reserved.
//

import UIKit
import WebKit

extension WKWebView {

    func cleanAllCookies() {
        HTTPCookieStorage.shared.removeCookies(since: Date.distantPast)
        print("All cookies deleted")

        WKWebsiteDataStore.default().fetchDataRecords(ofTypes: WKWebsiteDataStore.allWebsiteDataTypes()) { records in
            records.forEach { record in
                WKWebsiteDataStore.default().removeData(ofTypes: record.dataTypes, for: [record], completionHandler: {})
                print("Cookie ::: \(record) deleted")
            }
        }
    }

    func refreshCookies() {
        self.configuration.processPool = WKProcessPool()
    }
}

class LinkInWebview: UIViewController {
    
    @IBOutlet weak var lWKWebView: WKWebView!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        lWKWebView.cleanAllCookies()
        lWKWebView.refreshCookies()

        // Do any additional setup after loading the view.
        lWKWebView.navigationDelegate = self
        // enable JS
        lWKWebView.configuration.preferences.javaScriptEnabled = true
        guard let url = URL(string: "https://api.kolnetworks.com/facebook/link") else {
            return
        }
        // EGG_SESS=tjrnxtZFFwGSONwcuZy0lU634at6rQWHFhiqU2VGnl4YhwRb5xdPQVkkYHWWdDye; _token=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1OTE1NTcyNTUsInN1YiI6IjM1MDNhNGIwLWEzNmYtMTFlYS04Y2UzLWZiYmFlNGE1NGQ4NiIsImlhdCI6MTU5MDk1MjQ1NX0.MTBlzli7bXHck-E2Xa7VoBKQIQRvxgqCj95MrVaxe9I; _token.sig=BAV1aKOKGtyf4emy47858s1ZzcTNqtas59HlmfXqIPs
        
//        let cookie1 = HTTPCookie(properties: [
//            .domain: "api.kolnetworks.com",
//            .path: "/facebook/link",
//            .name: "EGG_SESS",
//            .value: "tjrnxtZFFwGSONwcuZy0lU634at6rQWHFhiqU2VGnl4YhwRb5xdPQVkkYHWWdDye",
//            .secure: "FALSE",
//            .expires: NSDate(timeIntervalSinceNow: 31556926)
//        ])!
        
        let cookie2 = HTTPCookie(properties: [
            .domain: "api.kolnetworks.com",
            .path: "/facebook/link",
            .name: "_token",
            .value: "" + (KolAPI.token ?? ""),
            .secure: "FALSE",
            .expires: NSDate(timeIntervalSinceNow: 31556926)
        ])!
        
        lWKWebView.configuration.websiteDataStore.httpCookieStore.setCookie(cookie2)
//        lWKWebView.configuration.websiteDataStore.httpCookieStore.setCookie(cookie3)

        lWKWebView.load(URLRequest(url: url))
        lWKWebView.allowsBackForwardNavigationGestures = true
    }
    

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destination.
        // Pass the selected object to the new view controller.
    }
    */

}

extension LinkInWebview: WKNavigationDelegate {
    func webView(_ webView: WKWebView, decidePolicyFor navigationAction: WKNavigationAction, decisionHandler: @escaping (WKNavigationActionPolicy) -> Void) {
        print("ccc:  \(navigationAction.request.url)")
        if navigationAction.navigationType == .other {
            guard let uri = navigationAction.request.url else {
                decisionHandler(.allow)
                return
            }
            
            guard let query =  navigationAction.request.url?.query else {
                decisionHandler(.allow)
                return
            }
            
            if query.contains("linkStatus=0") || query.contains("linkStatus=1") {
                print("綁定 \(query)")
                if query.contains("linkStatus=0") {
                    print("綁定失敗, 回前頁")

                }
                
                if query.contains("linkStatus=1") {
                    print("綁定成功, 回前頁")

                }
                
                self.dismiss(animated: true, completion: nil)
            }
            
        }
        decisionHandler(.allow)
    }
}
