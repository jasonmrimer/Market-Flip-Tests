package com.marketflip.tests.sharedassets;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.marketflip.application.shopper.MFA_ShopperManager;
import com.marketflip.shared.shopper.MF_ShopperDAO;


public class JUT_MF_ShopperDAO {

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
	 * The purose of this test is to start the creation of a MF_ShopperDAO with a first failing
	 * test. It is a simple constructor test.
	 *
	 * @throws Exception
	 */
	@Test
	public void Construct_NoArgs_ProperToString() throws Exception {
		// Test Variables
		String expectedToString, actualToString;
		MF_ShopperDAO shopperDAO;
		// Expected
		expectedToString = "Instance of Market Flip Application Shopping Database Access Object without parameters.";
		// Actual
		shopperDAO = new MF_ShopperDAO();
		actualToString = shopperDAO.toString();
		// Test
		assertEquals(expectedToString, actualToString);
	}

}
