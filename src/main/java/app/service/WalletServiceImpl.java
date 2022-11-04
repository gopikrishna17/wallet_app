package app.service;

import app.dao.WalletDao;
import app.dao.WalletDaoImpl;
import app.dto.Wallet;
import app.exception.WalletException;

public class WalletServiceImpl implements WalletService {

	private WalletDao walletRepository = new WalletDaoImpl();
	
	
	public Wallet registerWallet(Wallet newWallet) throws WalletException {
		
		return this.walletRepository.addWallet(newWallet);
		
	}

	public Boolean login(Integer walletId, String password) throws WalletException {
		Wallet walletToBeChecked = this.walletRepository.getWalletById(walletId);
		
		if(walletToBeChecked != null) {
			if(walletToBeChecked.getPassword().equals(password)) {
				return true;
			}
			else {
				throw new WalletException("Invalid Credentials");
			}	
		}
		else {
			throw new WalletException("WalletId not found");
		}
	}

	public Double addFundsToWallet(Integer walletId, Double amount) throws WalletException {
		Wallet walletToBeChecked = this.walletRepository.getWalletById(walletId);
		
		return (walletToBeChecked.getBalance() + amount);
	}

	public Double showWalletBalance(Integer walletId) throws WalletException {
		Wallet walletToBeChecked = this.walletRepository.getWalletById(walletId);
		return walletToBeChecked.getBalance();
	}

	public Boolean fundTransfer(Integer fromId, Integer toId, Double amount) throws WalletException {
		Wallet fromWallet = this.walletRepository.getWalletById(fromId);
		Wallet toWallet = this.walletRepository.getWalletById(toId);
		
		if(fromWallet != null && toWallet != null) {
			Double fromBalance = fromWallet.getBalance();
			Double toBalance = toWallet.getBalance();
			
			if(fromBalance > amount) {
				fromWallet.setBalance(fromBalance - amount);
				toWallet.setBalance(toBalance + amount);
				this.walletRepository.updateWallet(fromWallet);
				this.walletRepository.updateWallet(toWallet);
				return true;
			}
			else {
				throw new WalletException("Insufficient Balance");
			}
			
		}
		else if(toWallet == null) {
			throw new WalletException("Receiver WalletId not found");
		}
		return false;
	}

	public Wallet unRegisterWallet(Integer walletId, String password) throws WalletException {
		Wallet walletToBeDeleted = this.walletRepository.getWalletById(walletId);
		if(walletToBeDeleted != null && walletToBeDeleted.getPassword().equals(password)) {
			return this.walletRepository.deleteWalletById(walletId);
		}
		else {
			return null;
		}
	}

}
