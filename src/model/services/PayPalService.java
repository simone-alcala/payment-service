package model.services;

public class PayPalService implements OnlinePaymentService {

  private final Double fee = 2.00;
  private final Double interest = 1.00;

  @Override
  public Double interest(Double amount, Integer months)  {
    return amount * months * interest/100.0;
  }

  @Override
  public Double paymentFee(Double amount) {
    return amount * fee/100.0;
  }
  
}
