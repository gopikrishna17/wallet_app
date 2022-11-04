package app.dao;

import java.util.HashMap;
import java.util.Map;

import app.dto.Wallet;
import app.exception.WalletException;

public class WalletDaoImpl implements WalletDao {

	// Create collection to store the Wallet information.
	Map<Integer, Wallet> wallets = new HashMap<Integer, Wallet>();

	public Wallet addWallet(Wallet newWallet) throws WalletException {

		 this.wallets.put(newWallet.getId(), newWallet);
		 return this.wallets.get(newWallet.getId());

	}

	public Wallet getWalletById(Integer walletId) throws WalletException {
		
		return wallets.get(walletId);
	}

	public Wallet updateWallet(Wallet updateWallet) throws WalletException {
		return wallets.replace(updateWallet.getId(), updateWallet);
		
	}

	public Wallet deleteWalletById(Integer walletID) throws WalletException {
		Wallet wallet = this.getWalletById(walletID);
		wallets.remove(walletID);
		return wallet;
	}

}
