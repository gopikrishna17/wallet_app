package app.service;

import app.dto.Wallet;
import app.exception.WalletException;

public interface WalletService {

	Wallet registerWallet(Wallet newWallet) throws WalletException;
	
	Boolean login(Integer walletId,String password)throws WalletException;

	Double addFundsToWallet(Integer walletId, Double amount)throws WalletException;

	Double showWalletBalance(Integer walletId)throws WalletException;

	Boolean fundTransfer(Integer fromId, Integer toId, Double amount)throws WalletException;
	
	Wallet unRegisterWallet(Integer walletId,String password)throws WalletException;
}
