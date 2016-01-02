package com.marketflip.tests.application;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.marketflip.application.notification.MFA_NotificationManager;
import com.marketflip.application.shopper.MFA_ShopperCrawler;
import com.marketflip.application.shopper.MFA_ShopperManager;
import com.marketflip.shared.data.MF_ProductsDAO;
import com.marketflip.shared.products.MF_Price;
import com.marketflip.shared.products.MF_Product;
import com.marketflip.shared.shopper.MF_PricePoint;
import com.marketflip.shared.shopper.MF_Shopper;
import com.marketflip.shared.shopper.MF_ShopperDAO;

public class JIT_MFA_ShopperCrawler {

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
	public void Construct_ShopperWithPricePointsInDB_HashMapOfPricePoints() throws Exception {
		// Test Variables
		HashMap<MF_Product, MF_Price> expectedHashMap, actualHashMap;
		int expectedShopperCount, actualShopperCount;
		int shopperLimit, notificationLimit;
		MF_Shopper shopper;
		String shopperUserName, shopperEmail;
		MF_ShopperDAO shopperDAO;
		MFA_ShopperManager sm;
		MFA_NotificationManager nm;
		MF_PricePoint pricePoint;
		MF_Product product;
		MF_Price price;
		MF_ProductsDAO productDAO;
		Thread crawlerThread, notificationThread;
		// Expected
		expectedShopperCount = 1;
		// fill shopper and product db
		shopperDAO = new MF_ShopperDAO(false);
		shopperUserName = "silkroad";
		shopperEmail = "karlsilkroad@gmail.com";
		shopper = new MF_Shopper(shopperUserName, shopperEmail);
		pricePoint = new MF_PricePoint("045496901738", 40.00); // actual super mario bros wii UPC
		shopper.addPricePoint(pricePoint);
		shopperDAO.deleteAllTables();
		shopperDAO.createShoppersTable();
		shopperDAO.addShopper(shopper);
		expectedHashMap = new HashMap<MF_Product, MF_Price>();
		// fill product db
		productDAO = new MF_ProductsDAO("testing");
		productDAO.deleteAllTables();
		productDAO.addProductsTable();
		price = new MF_Price(30.00, new java.util.Date());
		product = new MF_Product("045496901738", price);
		productDAO.addProductToCommit(product);
		productDAO.commitProductsToDatabase();
		MF_Product retrievedProduct = productDAO.getProduct("045496901738");
		System.out.println("testing size: " + retrievedProduct.getPrices().size());
		expectedHashMap.put(product, price);
		// Actual
		MFA_ShopperCrawler shopperCrawler = new MFA_ShopperCrawler(shopper);
		shopperCrawler.call();
		actualHashMap = shopperCrawler.getHashMapOfPricePointMatches();
		// Test
		// TODO hashmap cannot work because the database always returns a new date rather than the original price date
		assertEquals(expectedHashMap, actualHashMap);
		//rollback
		shopperDAO.deleteAllTables();
		shopperDAO.close();
		productDAO.close();
	}

}
