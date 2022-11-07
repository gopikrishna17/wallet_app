package app.service;

import java.sql.SQLException;

import app.dao.WalletDao;
import app.dao.WalletDaoImpl;
import app.dto.Wallet;
import app.exception.WalletException;

public class WalletServiceImpl implements WalletService {

	private WalletDao walletRepository = new WalletDaoImpl();

	public Wallet registerWallet(Wallet newWallet) throws WalletException, SQLException {

		return this.walletRepository.addWallet(newWallet);

	}

	public Boolean login(Integer walletId, String password) throws WalletException, SQLException {
		Wallet walletToBeChecked = this.walletRepository.getWalletById(walletId);

		if (walletToBeChecked != null) {
			if (walletToBeChecked.getPassword().equals(password)) {
				return true;
			} else {
				throw new WalletException("Invalid Credentials");
			}
		} else {
			throw new WalletException("WalletId not found");
		}
	}

	public Double addFundsToWallet(Integer walletId, Double amount) throws WalletException, SQLException {

		if (amount > 0) {
			Double amountToBeUpdated = this.walletRepository.getWalletById(walletId).getBalance() + amount;
			this.walletRepository.updateWallet(walletId, amountToBeUpdated);
			return amountToBeUpdated;
		} else {
			throw new WalletException("Amount should be greater than Rs.0");
		}

	}

	public Double showWalletBalance(Integer walletId) throws WalletException, SQLException {
		Wallet walletToBeChecked = this.walletRepository.getWalletById(walletId);
		return walletToBeChecked.getBalance();
	}

	public Boolean fundTransfer(Integer fromId, Integer toId, Double amount) throws WalletException, SQLException {
		Wallet fromWallet = this.walletRepository.getWalletById(fromId);
		Wallet toWallet = this.walletRepository.getWalletById(toId);
		if (amount > 0.0) {
			if (toWallet != null) {
				Double fromBalance = fromWallet.getBalance();
				Double toBalance = toWallet.getBalance();

				if (fromBalance > amount) {
					this.walletRepository.updateWallet(fromWallet.getId(), fromBalance - amount);
					this.walletRepository.updateWallet(toWallet.getId(), toBalance + amount);
					return true;
				} else {
					throw new WalletException("Insufficient Balance");
				}

			} else {
				throw new WalletException("Receiver WalletId not found");
			}
		} else {
			throw new WalletException("Amount to be transferred should be greater than Rs.0");
		}

	}

	public Wallet unRegisterWallet(Integer walletId, String password) throws WalletException, SQLException {
		Wallet walletToBeDeleted = this.walletRepository.getWalletById(walletId);
		if (walletToBeDeleted.getPassword().equals(password)) {
			return this.walletRepository.deleteWalletById(walletId);
		} else {
			throw new WalletException("Password mismatch");
		}
	}

	public boolean validate(Integer id) throws WalletException, SQLException {
		Wallet idToBeMatched = this.walletRepository.getWalletById(id);
		if (idToBeMatched == null) {
			return false;
		} else {
			return true;
		}
	}

}
