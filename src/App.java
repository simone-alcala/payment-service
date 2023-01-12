import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Locale;
import java.util.Scanner;

import model.entities.Contract;
import model.entities.Installment;
import model.services.ContractService;
import model.services.PayPalService;

public class App {
    public static void main(String[] args) throws Exception {
        
        Locale.setDefault(Locale.US);
        Scanner sc = new Scanner(System.in);
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        System.out.println("---------------------------");
        System.out.println("Welcome to PAYMENT SERVICE");
        System.out.println("---------------------------");

        System.out.println("Entre com os dados do contrato:");
        
        try {

            System.out.print("Número: ");
            Integer number = sc.nextInt();
    
            System.out.print("Data (dd/MM/yyyy): ");
            sc.nextLine();
            LocalDate date = LocalDate.parse(sc.nextLine(), fmt);
            
            System.out.print("Valor do contrato: ");
            Double totalValue = sc.nextDouble();

            if (totalValue < 0) {
                throw new InputMismatchException();
            }
            
            System.out.print("Número de parcelas: ");
            Integer months = sc.nextInt();

            if (months <= 0) {
                throw new InputMismatchException();
            }
            
            Contract contract = new Contract(number, date, totalValue);
    
            ContractService contractService = new ContractService(new PayPalService());
            contractService.processContract(contract, months);
    
            System.out.println("Parcelas:");
    
            for (Installment installment : contract.getInstallments()) {
                System.out.println(installment);
            }
    
            System.out.println("---------------------------");
        } catch (InputMismatchException e) {
            System.out.println("Dado inválido!");
        } catch (DateTimeParseException e) {
            System.out.println("Data inválida!");
        } catch (Exception e) {
            System.out.println("Deu ruim =/");
        } finally {
            sc.close();
        }

    }

}
