package com.marketflip.tests.proton;

import static org.junit.Assert.*;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.marketflip.application.main.MFA_Main;
import com.marketflip.application.shopper.MFA_ShopperManager;
import com.marketflip.crawler.main.MFC_Main;
import com.marketflip.crawler.proton.MFC_Proton;
import com.marketflip.shared.shopper.MF_NotificationsDAO;
import com.marketflip.shared.shopper.MF_PricePoint;
import com.marketflip.shared.shopper.MF_Shopper;
import com.marketflip.shared.shopper.MF_ShopperDAO;

public class Proton {

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

//	@Test
//	public void test() throws Exception{
//		String[] args = new String[0];
//		MFC_Main.main(args);
//		System.out.println("ran it");
//	}
	/**
	 * The purpose of this Proton is to test the first iteration of the total system by using one
	 * customer, Karl Silkroad, requesting one product, Super Mario Bros. Wii, from one company,
	 * Walmart. A successful result will be Market Flip collecting data on the product from the
	 * copany and checking the customer's price point - it will be a match and succeed if it
	 * triggers a "notification".
	 *
	 * @throws Exception
	 */
	@Test
	public void Proton_OneShopperOneCompanyOneProduct_PricePointMatchNotification()
			throws Exception {
		// Test Variables
		String expectedNotification, actualNotification;
		String marioUPC, startURLPath;
		String[] crawlerArgs, shopperLimitArgs;
		Double priceDesired;
		int shopperLimit, notificationLimit, siteLimit, productLimit, docLimit;
		MF_ShopperDAO shopperDAO;
		MFC_Proton crawlerApp;
		// Expected
		expectedNotification = "karlsilkroad@gmail.com, your product Super Mario Bros. Wii is available below your desired price point. Buy it now from lovenirds.walmart/supermariobroswii.";
		// Actual
		// create karl the shopper
		MF_Shopper shopperKarl = new MF_Shopper("karl", "karlsilkroad@gmail.com");
		// create karl's price point
		marioUPC = "045496901738";
		priceDesired = 50.00;
		MF_PricePoint pricePoint = new MF_PricePoint(marioUPC, priceDesired);
		// give karl's object the price point
		shopperKarl.addPricePoint(pricePoint);
		// insert karl into shopper database
		shopperDAO = new MF_ShopperDAO(false);
		shopperDAO.deleteAllTables();
		shopperDAO.createShoppersTable();
		shopperDAO.addShopper(shopperKarl);
		// crawl the web for information about product
		startURLPath = "http://www.lovenirds.com/TestWalmartSuperMarioBrosWii.html";
		siteLimit = 1;
		productLimit = 1;
		docLimit = 1; 
		crawlerArgs = new String[4];
		crawlerArgs[0] = startURLPath;
		crawlerArgs[1] = String.valueOf(siteLimit);
		crawlerArgs[2] = String.valueOf(productLimit);
		crawlerArgs[3] = String.valueOf(docLimit);
		MFC_Main mfc = new MFC_Main();
		mfc.main(crawlerArgs);
		mfc.getExecutor().awaitTermination(1, TimeUnit.MINUTES);
		System.out.println("TEST: MFC copmplete.");
		// start application, it should automatically match karl's price point to the availabe game
		shopperLimit = 1;
		notificationLimit = 1;
		shopperLimitArgs = new String[2];
		shopperLimitArgs[0] = String.valueOf(shopperLimit);
		shopperLimitArgs[1] = String.valueOf(notificationLimit);
		MFA_Main mfa = new MFA_Main();
		mfa.main(shopperLimitArgs);
		// wait for programs to complete
		mfa.getExecutor().awaitTermination(1, TimeUnit.MINUTES);
		// Test
//		assertEquals(expectedNotification, actualNotification);
		// Rollback
		shopperDAO.deleteAllTables();
		// shutdown db
		shopperDAO.close();
	}

}
