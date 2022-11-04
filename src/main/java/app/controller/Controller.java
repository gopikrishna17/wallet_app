package app.controller;

import java.util.Scanner;

import app.dto.Wallet;
import app.exception.WalletException;
import app.service.WalletService;
import app.service.WalletServiceImpl;

public class Controller {

	public static void main(String[] args) {
		
		WalletService walletService = new WalletServiceImpl();

		try {
			
			
			Wallet wallet1 = walletService.registerWallet(new Wallet(1, "Ford", 1000.0, "123"));
			Wallet wallet2 = walletService.registerWallet(new Wallet(2, "FordBlu", 5000.0, "456"));
			Wallet wallet3 = walletService.registerWallet(new Wallet(3, "FordPro", 3000.0, "789"));
			Wallet wallet4 = walletService.registerWallet(new Wallet(4, "FordModelE", 8000.0, "abc"));
			
			
			System.out.println("Welcome to Gopi India Limited");
			System.out.println("Choose any option for given services");
			System.out.println("1  -  Create/Register");
			System.out.println("2  -  Login/Existing");
			
			Scanner scanner = new Scanner(System.in);
			Integer choice = scanner.nextInt();
			
			switch(choice) {
			case 1:
				System.out.println("Enter Wallet id : ");
				scanner = new Scanner(System.in);
				Integer walletId = scanner.nextInt();
				
				System.out.println("Enter Wallet Name : ");
				scanner = new Scanner(System.in);
				String walletName = scanner.nextLine();
				
				System.out.println("Minimum Balance : ");
				scanner = new Scanner(System.in);
				Double walletMinBalance = scanner.nextDouble();
				
				System.out.println("Enter password : ");
				scanner = new Scanner(System.in);
				String walletPassword = scanner.nextLine();
				
				Wallet wallet = walletService.registerWallet(new Wallet(walletId,walletName,walletMinBalance,walletPassword));
				System.out.println("WalletId registered successfully");
				break;
			
				
			case 2:
				System.out.println("Enter Wallet id : ");
				scanner = new Scanner(System.in);
				Integer loginWalletId = scanner.nextInt();
				
				System.out.println("Enter password : ");
				scanner = new Scanner(System.in);
				String loginWalletPassword = scanner.nextLine();
				
				
				if(walletService.login(loginWalletId, loginWalletPassword))
				{
					System.out.println("Logged in successfully");
					System.out.println("Choose any option : ");
					System.out.println("1  -  View Balance");
					System.out.println("2  -  Add funds to wallet");
					System.out.println("3  -  Transfer funds to another wallet");
					System.out.println("4  -  Unregister");
					
					scanner = new Scanner(System.in);
					Integer option = scanner.nextInt();
					
					switch(option) {
					case 1:
						System.out.println("Your Current Balance is : ");
						System.out.println( walletService.showWalletBalance(loginWalletId));
						break;
					case 2:
						System.out.println("Enter amount to be added to your wallet : ");
						scanner = new Scanner(System.in);
						Double amountToBeAdded = scanner.nextDouble();
						System.out.println("Balance after adding funds : ");
						System.out.println(walletService.addFundsToWallet(loginWalletId, amountToBeAdded));
						break;
					case 3:
						System.out.println("Enter walletId of receiving wallet : ");
						scanner = new Scanner(System.in);
						Integer id = scanner.nextInt();
						
						System.out.println("Enter amount to be transferred : ");
						scanner = new Scanner(System.in);
						Double amountToBeTransferred = scanner.nextDouble();
						if (walletService.fundTransfer(loginWalletId, id, amountToBeTransferred)) {
							System.out.println("Funds transferred successfully");
							System.out.println("Your current balance is : ");
							System.out.println(walletService.showWalletBalance(loginWalletId));

						}
						else {
							System.out.println("Transaction Failed");
						}
						
						break;
					case 4:
						System.out.println("Enter password : ");
						scanner = new Scanner(System.in);
						String password = scanner.nextLine();
						
						Wallet walletToBeUnregistered = walletService.unRegisterWallet(loginWalletId, password);
						if(walletToBeUnregistered != null) {
							System.out.println("id unregistered successfully");
						}
						else {
							System.out.println("unable to unregister");
						}
						break;
					default:
						System.out.println("Enter the valid option");
					}
						
				}
//				else {
//					System.out.println("Login failed");
//				}
				
	
			}
			
			
		} catch (WalletException e) {
			System.out.println(e.getMessage());
		}
	}


}
