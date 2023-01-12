package model.services;

import java.time.LocalDate;

import model.entities.Contract;
import model.entities.Installment;

public class ContractService {

  private OnlinePaymentService onlinePaymentService;

  public ContractService(OnlinePaymentService onlinePaymentService) {
    this.onlinePaymentService = onlinePaymentService;
  }
  
  public void processContract(Contract contract, Integer months) {

    double totalValue = contract.getTotalValue();
    double installmentBase = totalValue / months;
    
   for (int i = 1; i <= months; i++) {
      LocalDate dueDate = contract.getDate().plusMonths(i);     

      double interest = onlinePaymentService.interest(installmentBase, i);
      double fee = onlinePaymentService.paymentFee(interest + installmentBase);

      double amount = fee + interest + installmentBase;

      contract.addInstallment(new Installment(dueDate, amount));
    }

  }
}
