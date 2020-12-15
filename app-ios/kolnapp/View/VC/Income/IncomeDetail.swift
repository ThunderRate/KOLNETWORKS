//
//  IncomeDetail.swift
//  kolnapp
//
//  Created by Victor_Huang on 2020/4/28.
//  Copyright © 2020 黃世維. All rights reserved.
//

import UIKit

class IncomeDetailVC: UIViewController {
    override var prefersStatusBarHidden: Bool {
        return true
    }

    @IBOutlet weak var isEmptyCard: UIStackView!
    @IBOutlet weak var incomeTable: UITableView!
    @IBOutlet weak var backBtn: UIButton!

    @IBAction func back(_ sender: Any) {
        self.navigationController?.popToRootViewController(animated: true)
    }
    
    var viewModel: [[String: Any]] = []

    override func viewDidLoad() {
        super.viewDidLoad()

        self.incomeTable.delegate = self
        self.incomeTable.dataSource = self
        self.incomeTable.register(UINib(nibName: "CashFlowTableViewCell", bundle: nil), forCellReuseIdentifier: "CashFlowTableViewCell")
        
        self.incomeTable.layer.cornerRadius = 8
        self.incomeTable.layer.masksToBounds = false
        self.incomeTable.layer.makeShadow(color: .lightGray, x: 0, y: 6, blur: 10, spread: 0)
        self.incomeTable.tableFooterView = UIView()
        
        self.isEmptyCard.isHidden = false
    }

    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        self.navigationController?.setNavigationBarHidden(true, animated: animated)
    }

}

extension IncomeDetailVC: UITableViewDelegate, UITableViewDataSource {
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        self.isEmptyCard.isHidden = viewModel.count > 0
        return viewModel.count
    }

    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let data = viewModel[indexPath.row]

        if let cell = incomeTable.dequeueReusableCell(withIdentifier: "CashFlowTableViewCell", for: indexPath) as? CashFlowTableViewCell {
            if let p = data["project"] as? [String: Any], let name = p["name"] as? String {
                cell.name.text = name
            }

            if let basic_income = data["basic_income"] as? Int, let basic_income_commission = data["basic_income_commission"] as? Int {
                let sum = basic_income + basic_income_commission
                cell.value.text = "\(sum)"
            }

            if let unixtimeInterval = data["created_at"] as? Int {
                let date = Date(timeIntervalSince1970: TimeInterval(unixtimeInterval))
                let dateFormatter = DateFormatter()
                dateFormatter.timeZone = TimeZone(abbreviation: "GMT+8") //Set timezone that you want
                dateFormatter.locale = NSLocale.current
                dateFormatter.dateFormat = "yyyy-MM-dd" //Specify your format that you want
                let strDate = dateFormatter.string(from: date)

                cell.date.text = strDate
            }

            return cell
        } else {
            return CashFlowTableViewCell()
        }

    }

}
