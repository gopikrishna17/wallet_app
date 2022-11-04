package app.dao;

import java.sql.SQLException;

import app.dto.Wallet;
import app.exception.WalletException;

public interface WalletDao {
	// CRUD

	Wallet addWallet(Wallet newWallet) throws WalletException, SQLException;

	Wallet getWalletById(Integer walletId) throws WalletException, SQLException;

	Wallet updateWallet(Integer walletId, Double amount) throws WalletException, SQLException;

	Wallet deleteWalletById(Integer walletID) throws WalletException, SQLException;

}
