package com.marketflip.tests.sharedassets;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.marketflip.application.shopper.MFA_ShopperManager;
import com.marketflip.shared.shopper.MF_ShopperDAO;

/**
 * The purpose of this test case is to unit test the Shopper Data Access Object using its creation
 * and storage methods.
 *
 * @author highball
 *
 */
public class JUT_MF_ShopperDAO {

	/**
	 * The purpose of this test is to start the creation of a MF_ShopperDAO with a first failing
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
