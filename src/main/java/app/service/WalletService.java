package app.service;

import java.sql.SQLException;

import app.dto.Wallet;
import app.exception.WalletException;

public interface WalletService {

	Wallet registerWallet(Wallet newWallet) throws WalletException, SQLException;

	Boolean login(Integer walletId, String password) throws WalletException, SQLException;

	Double addFundsToWallet(Integer walletId, Double amount) throws WalletException, SQLException;

	Double showWalletBalance(Integer walletId) throws WalletException, SQLException;

	Boolean fundTransfer(Integer fromId, Integer toId, Double amount) throws WalletException, SQLException;

	Wallet unRegisterWallet(Integer walletId, String password) throws WalletException, SQLException;

	boolean validate(Integer id) throws WalletException, SQLException;
}
