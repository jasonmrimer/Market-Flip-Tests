package com.marketflip.tests.sharedassets;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.marketflip.application.shopper.MFA_ShopperManager;
import com.marketflip.shared.shopper.MF_NotificationsDAO;
import com.marketflip.shared.shopper.MF_ShopperDAO;

public class JUT_MF_NotificationsDAO {

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

	/**
	 * The purpose of this test is to start the creation of a MF_NotificationsDAO with a first failing
	 * test. It is a simple constructor test.
	 *
	 * @throws Exception
	 */
	@Test
	public void Construct_NoArgs_ProperToString() throws Exception {
		// Test Variables
		String expectedToString, actualToString;
		MF_NotificationsDAO notificationsDAO;
		// Expected
		expectedToString = "Instance of Market Flip Notifications Database Access Object without parameters.";
		// Actual
		notificationsDAO = new MF_NotificationsDAO();
		actualToString = notificationsDAO.toString();
		// Test
		assertEquals(expectedToString, actualToString);
	}

}
