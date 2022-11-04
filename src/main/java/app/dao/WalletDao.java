package app.dao;

import app.dto.Wallet;
import app.exception.WalletException;

public interface WalletDao {
	//CRUD
	Wallet addWallet(Wallet newWallet)throws WalletException;
	Wallet getWalletById(Integer walletId)throws WalletException;
	Wallet updateWallet(Wallet updateWallet)throws WalletException;
	Wallet deleteWalletById(Integer walletID)throws WalletException;
}
