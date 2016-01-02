package com.marketflip.tests.application;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.marketflip.application.shopper.MFA_ShopperCrawler;
import com.marketflip.application.shopper.MFA_ShopperManager;
import com.marketflip.shared.shopper.MF_Shopper;
import com.marketflip.shared.shopper.MF_ShopperDAO;

public class JIT_MFA_ShopperManager {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void Construct_MockWithDatabase_ValidConnectionToDB() throws Exception {
		// Test Variables
		MFA_ShopperManager shopperManager;
		// Expected

		// Actual
		shopperManager = new MFA_ShopperManager(null, 0, false);
		// Test
		assertFalse(shopperManager.getShopperDAO().getConn().isClosed());
		// close
		shopperManager.close();
	}

	@Test
	public void Run_1ShopperInDB_ReturnShopper() throws Exception {
		// Test Variables
		int expectedShopperCount, actualShopperCount;
		MF_Shopper shopper;
		String shopperUserName, shopperEmail;
		MF_ShopperDAO shopperDAO = new MF_ShopperDAO(false);
		MFA_ShopperManager shopperManager;
		int shopperLimit;
		ArrayBlockingQueue<MFA_ShopperCrawler> bq;
		Thread smThread;
		// Expected
		expectedShopperCount = 1;
		// Actual
		shopperUserName = "silkroad";
		shopperEmail = "karlsilkroad@gmail.com";
		shopper = new MF_Shopper(shopperUserName, shopperEmail);
		shopperDAO.deleteAllTables();
		shopperDAO.createShoppersTable();
		shopperDAO.addShopper(shopper);
		shopperLimit = 1;
		bq = new ArrayBlockingQueue<>(3);
		shopperManager = new MFA_ShopperManager(bq, shopperLimit, false);
		smThread = new Thread(shopperManager);
		smThread.start();
		while (smThread.isAlive()) {
		}
		actualShopperCount = shopperManager.getCompletedBlockingQueueAdditions();
		// Test
		assertEquals(expectedShopperCount, actualShopperCount);
		// Rollback
		shopperDAO.deleteAllTables();

	}

}
