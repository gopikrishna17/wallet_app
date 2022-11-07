package app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import app.dto.Wallet;
import app.exception.WalletException;

public class WalletDaoImpl implements WalletDao {

	Map<Integer, Wallet> wallets = new HashMap<Integer, Wallet>();

	Connection connection = MySqlUtility.getConnectionToMySql();

	public WalletDaoImpl() {

	}

	public Wallet addWallet(Wallet newWallet) throws WalletException, SQLException {

		String sql = "INSERT INTO walletdetails(id,name,amount,password) VALUES(?, ?, ?, ?)";
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setInt(1, newWallet.getId());
		preparedStatement.setString(2, newWallet.getName());
		preparedStatement.setDouble(3, newWallet.getBalance());
		preparedStatement.setString(4, newWallet.getPassword());

		Integer count = preparedStatement.executeUpdate();
		if (count == 1) {
			System.out.println("Wallet registered successfully");
		}

		return getWalletById(newWallet.getId());
	}

	public Wallet getWalletById(Integer walletId) throws WalletException, SQLException {

		String sqlGet = "SELECT * FROM walletdetails WHERE id = " + walletId;
		Statement stat = connection.createStatement();
		ResultSet resSet = stat.executeQuery(sqlGet);

		if (resSet.isBeforeFirst()) {
			resSet.next();
			Wallet wallet = new Wallet();
			wallet.setId(resSet.getInt(1));
			wallet.setName(resSet.getString(2));
			wallet.setBalance(resSet.getDouble(3));
			wallet.setPassword(resSet.getString(4));
			return wallet;
		} else {
			return null;
		}

	}

	public Wallet updateWallet(Integer walletId, Double amount) throws WalletException, SQLException {

		Wallet existId = getWalletById(walletId);
		String sqlUpdate = "UPDATE walletdetails SET amount = " + amount + "WHERE id = " + walletId;
		Statement statement = connection.createStatement();
		statement.executeUpdate(sqlUpdate);
		return getWalletById(walletId);

	}

	public Wallet deleteWalletById(Integer walletID) throws WalletException, SQLException {

		Wallet deletedWallet = getWalletById(walletID);
		String sqlDelete = "DELETE FROM walletdetails WHERE id = " + walletID;
		Statement statement = connection.createStatement();
		int result = statement.executeUpdate(sqlDelete);

		return deletedWallet;

	}

}
