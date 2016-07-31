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

/**
 * The puurpose of this test case is to run integration tests for the Shopper Crawler Manager using
 * real connections to external sources such as the shopper database and ensuring proper
 * relationships with those sources.
 *as
 * @author highball
 *
 */
public class JIT_MFA_ShopperManager {

	/**
	 * The purpose of this test is to construct a shopperManager then ensure it opened a connection.
	 *
	 * @throws Exception
	 */
	@Test
	public void Construct_MockWithDatabase_ValidConnectionToDB() throws Exception {
		// Test Variables
		MFA_ShopperManager shopperManager;
		// Expected: connection is NOT closed
		// Actual
		shopperManager = new MFA_ShopperManager(null, 0, false);
		// Test
		assertFalse(shopperManager.getShopperDAO().getConn().isClosed());
		// close
		shopperManager.close();
	}

	/**
	 * The purpose of this test is to run a thread as the Shopper Manager to completion by limit 1
	 * (only insert one shopper into the shopper database and that is all the database will
	 * retrieve).
	 *
	 * @throws Exception
	 */
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
			// wait
		}
		actualShopperCount = shopperManager.getCompletedBlockingQueueAdditions();
		// Test
		assertEquals(expectedShopperCount, actualShopperCount);
		// Rollback
		shopperDAO.deleteAllTables();
	}
}
