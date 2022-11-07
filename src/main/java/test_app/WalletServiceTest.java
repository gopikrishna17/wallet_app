package test_app;

import java.lang.ModuleLayer.Controller;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.junit.jupiter.api.Assertions.fail;
//import static org.junit.Assert.assertNotNull;
//import static org.junit.Assert.assertThrows;
//
//
import java.sql.SQLException;
//
import org.junit.Test;
//import org.junit.Rule;
//import org.junit.jupiter.api.Disabled;
//import org.junit.jupiter.api.function.Executable;
//import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import app.dto.Wallet;
import app.exception.WalletException;
import app.service.WalletService;
import app.service.WalletServiceImpl;

public class WalletServiceTest {

	WalletService walletService = new WalletServiceImpl();
	

	@Test
	public void registerWalletTest() throws WalletException, SQLException {
		
		Wallet wallet = walletService.registerWallet(new Wallet(100,"name100",100.0,"test100"));
		
		assertNotNull(wallet);
		assertEquals(100,wallet.getId());
		assertEquals("name100",wallet.getName());
		assertEquals(100.0,wallet.getBalance());
		assertEquals("test100",wallet.getPassword());
			
	}
	
	@Test
	public void loginTest() throws WalletException, SQLException {
		walletService.registerWallet(new Wallet(200,"name200",200.0,"test200"));
		
		assertEquals(true,walletService.login(200, "test200"));
	}
	
	
	@Test
	public void showWalletBalanceTest() throws WalletException, SQLException {
		walletService.registerWallet(new Wallet(300,"name300",300.0,"test300"));
		
		assertEquals(300.0,walletService.showWalletBalance(300));
	}
	
	@Test
	public void fundTransferTest() throws WalletException, SQLException{
		Wallet fromWallet = walletService.registerWallet(new Wallet(400,"name400",300.0,"test400"));
		Wallet toWallet = walletService.registerWallet(new Wallet(500,"name500",300.0,"test500"));
		
		assertNotNull(fromWallet);
		assertNotNull(toWallet);
		assertEquals(400,fromWallet.getId());
		assertEquals(500,toWallet.getId());
		
		assertEquals(true,walletService.fundTransfer(fromWallet.getId(),toWallet.getId(),150.0));
	}
	
	
	
	
	@Test
	public void loginExceptionTest() throws WalletException, SQLException {
		walletService.registerWallet(new Wallet(600,"name600",600.0,"test600"));
		
		assertThrows(WalletException.class, () -> walletService.login(600, "test900"));
	}
	
	@Test
	public void loginExceptionTestWalletNotFound() throws WalletException, SQLException {
		walletService.registerWallet(new Wallet(8000,"name8000",8000.0,"test8000"));
		
		assertThrows(WalletException.class, () -> walletService.login(null, "test8000"));
	}
	
	
	
	
	@Test
	public void addFundsTest() throws WalletException, SQLException {
		walletService.registerWallet(new Wallet(700,"name700",700.0,"test700"));
		
		Double updatedAmount = walletService.addFundsToWallet(700, 800.0);
		
		assertEquals(1500.0,updatedAmount);
			
	}
	
	
//	@Rule
//	public ExpectedException exception = ExpectedException.none();
//	@Test 
//	public void registerExceptionTest() throws WalletException{
//		
//		try {
//			walletService.registerWallet(new Wallet(700,"name700",700.0,"test700"));
//			fail();
//		}catch(WalletException ex){
//			assertEquals("Wallet not registered",ex.getMessage());
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		
////		assertThrows(WalletException.class,() -> walletService.registerWallet(new Wallet(700,"name700",700.0,"test700")));
//		
////		Wallet wallet = walletService.registerWallet(new Wallet(800,"name800",800.0,"test800"));
////		exception.expect(WalletException.class);
////		exception.expectMessage("Wallet not registered");
//		
//	}
	
	@Test
	public void addFundsExceptionTest() throws SQLException {
		
		
		try {
			Wallet wallet = walletService.registerWallet(new Wallet(900,"name900",900.0,"test900"));
			walletService.addFundsToWallet(wallet.getId(), 0.0);
		}catch(WalletException ex){
			assertEquals("Amount should be greater than Rs.0",ex.getMessage());
		} 
		
		
		
//		walletService.addFundsToWallet(wallet.getId(), 0.0);
//		exception.expect(WalletException.class);
//		exception.expectMessage("Amount should be greater than Rs.0");
	}
	
	@Test
	public void fundTransferReceivernotExistException() throws SQLException {
		try {
			walletService.registerWallet(new Wallet(1000,"name1000",1000.0,"test1000"));
			walletService.registerWallet(new Wallet(2000,"name2000",2000.0,"test2000"));
			walletService.fundTransfer(1000, null, 5000.0);
		}catch(WalletException ex){
			assertEquals("Receiver WalletId not found",ex.getMessage());
		} 
	}
	
	@Test
	public void fundTransferInsufficientBalanceException() throws SQLException {
		try {
			walletService.registerWallet(new Wallet(3000,"name3000",3000.0,"test3000"));
			walletService.registerWallet(new Wallet(4000,"name4000",4000.0,"test4000"));
			walletService.fundTransfer(3000, 4000,5000.0);
		}catch(WalletException ex){
			assertEquals("Insufficient Balance",ex.getMessage());
		} 
	}
	@Test
	public void fundTransferAmountException() throws SQLException {
		try {
			walletService.registerWallet(new Wallet(5000,"name5000",5000.0,"test5000"));
			walletService.registerWallet(new Wallet(6000,"name6000",6000.0,"test6000"));
			walletService.fundTransfer(5000, 6000,0.0);
		}catch(WalletException ex){
			assertEquals("Amount to be transferred should be greater than Rs.0",ex.getMessage());
		} 
	}
	
	@Test
	public void unregisterTest() throws WalletException, SQLException {
		
		Wallet wallet = walletService.registerWallet(new Wallet(7000,"name7000",7000.0,"test7000"));
		
		Wallet deletedWallet = walletService.unRegisterWallet(7000, "test7000");
		
		assertEquals(7000,deletedWallet.getId());
		
			
	}
	
	@Test
	public void unregisterExceptionTest() throws SQLException {
				
		try {
			walletService.registerWallet(new Wallet(9000,"name9000",9000.0,"test9000"));
			walletService.unRegisterWallet(9000, "abc");
		}catch(WalletException ex){
			assertEquals("Password mismatch",ex.getMessage());
		} 
	}
		
	
	@Test
	public void validateTest() throws WalletException, SQLException {
		walletService.registerWallet(new Wallet(10,"name10",10.0,"test10"));
		
		assertEquals(true,walletService.validate(10));
		assertEquals(false,walletService.validate(111));
	}
	

	
	
	
	
	
}
