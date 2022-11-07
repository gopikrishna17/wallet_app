package app.controller;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

import app.dao.MySqlUtility;
import app.dao.WalletDao;
import app.dao.WalletDaoImpl;
import app.dto.Wallet;
import app.exception.WalletException;
import app.service.WalletService;
import app.service.WalletServiceImpl;

public class Controller {



	public static void main(String[] args) {

		WalletService walletService = new WalletServiceImpl();
		Integer userOption;
		Integer choice;
		try {
			
			Scanner scanner = new Scanner(System.in);
			choice = displayWelcomePage(scanner);
			

			switch (choice) {
			case 1:
				System.out.println("************ Welcome to Registration Page ************");
				System.out.println("Enter Wallet id : ");
				scanner = new Scanner(System.in);
				Integer walletId = scanner.nextInt();

				if (walletService.validate(walletId)) {
					throw new WalletException("Wallet with id " + walletId + " already exists");
				}
				System.out.println("Enter Wallet Name : ");
				scanner = new Scanner(System.in);
				String walletName = scanner.nextLine();

				System.out.println("Minimum Balance : ");
				scanner = new Scanner(System.in);
				Double walletMinBalance = scanner.nextDouble();

				System.out.println("Enter password : ");
				scanner = new Scanner(System.in);
				String walletPassword = scanner.nextLine();

				Wallet wallet = walletService
						.registerWallet(new Wallet(walletId, walletName, walletMinBalance, walletPassword));
//				
//				
				break;

			case 2:
				System.out.println("************ Welcome to Login Page ************");
				System.out.println("Enter Wallet id : ");
				scanner = new Scanner(System.in);
				Integer loginWalletId = scanner.nextInt();
				if (walletService.validate(loginWalletId)) {
					System.out.println("Enter password : ");
					scanner = new Scanner(System.in);
					String loginWalletPassword = scanner.nextLine();

					if (walletService.login(loginWalletId, loginWalletPassword)) {
						System.out.println("Logged in successfully");

						do {
							userOption = displayMenu(scanner);
							switch (userOption) {
							case 1:
								System.out.println("Your Current Balance is : ");
								System.out.println(walletService.showWalletBalance(loginWalletId));
								break;
							case 2:
								System.out.println("Enter amount to be added to your wallet : ");
								scanner = new Scanner(System.in);
								Double amountToBeAdded = scanner.nextDouble();

								
								System.out.println(walletService.addFundsToWallet(loginWalletId, amountToBeAdded));
								System.out.println("Balance after adding funds : " + walletService.showWalletBalance(loginWalletId));
								
								break;
							case 3:
								System.out.println("Enter walletId of receiving wallet : ");
								scanner = new Scanner(System.in);
								Integer id = scanner.nextInt();

								System.out.println("Enter amount to be transferred : ");
								scanner = new Scanner(System.in);
								Double amountToBeTransferred = scanner.nextDouble();
								if (amountToBeTransferred > 0) {
									if (walletService.fundTransfer(loginWalletId, id, amountToBeTransferred)) {
										System.out.println("Funds transferred successfully");
										System.out.println("Your current balance is : ");
										System.out.println(walletService.showWalletBalance(loginWalletId));

									} else {
										System.out.println("Transaction Failed");
									}
								} else {
									throw new WalletException("Amount to be transferred should be greater than Rs.0");
								}

								break;
							case 4:
								System.out.println("Enter password : ");
								scanner = new Scanner(System.in);
								String password = scanner.nextLine();

								Wallet walletToBeUnregistered = walletService.unRegisterWallet(loginWalletId, password);
							
								System.out.println("id unregistered successfully");
								userOption = 5;
								break;
							case 5:
								System.out.println("Logged out Successully");
								break;
							default:
								System.out.println("Enter the valid option");
							}
						} while (userOption != 5);

					}
//				}

				} else {
					throw new WalletException("Wallet with id " + loginWalletId + " does not exist");
				}
//				else {
//					System.out.println("Login failed");
//				}
				break;
			default:
				System.out.println("Enter the valid option");

			}

		} catch (WalletException e) {
			System.out.println(e.getMessage());
		} catch (NullPointerException e) {
			System.out.println(e.getMessage());
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	private static Integer displayWelcomePage(Scanner scanner) {
		System.out.println("Welcome to Gopi India Limited");
		System.out.println("Choose any option for given services");
		System.out.println("1  -  Create/Register");
		System.out.println("2  -  Login/Existing");
		return scanner.nextInt();
		
	}

	private static Integer displayMenu(Scanner input) {
		System.out.println("Choose any option : ");
		System.out.println("1  -  View Balance");
		System.out.println("2  -  Add funds to wallet");
		System.out.println("3  -  Transfer funds to another wallet");
		System.out.println("4  -  Unregister");
		System.out.println("5  -  Logout");
		return input.nextInt();
	}

}
