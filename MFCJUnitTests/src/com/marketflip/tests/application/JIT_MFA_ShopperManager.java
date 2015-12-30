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

import com.marketflip.application.shopper.MFA_ShopperManager;

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

}
