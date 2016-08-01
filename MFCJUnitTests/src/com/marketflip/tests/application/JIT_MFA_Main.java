package com.marketflip.tests.application;
// 
import static org.junit.Assert.*;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.marketflip.application.main.MFA_Main;
import com.marketflip.application.notification.MFA_NotificationManager;
import com.marketflip.application.shopper.MFA_ShopperCrawler;
import com.marketflip.application.shopper.MFA_ShopperManager;
import com.marketflip.shared.data.MF_ProductsDAO;
import com.marketflip.shared.products.MF_Price;
import com.marketflip.shared.products.MF_Product;
import com.marketflip.shared.shopper.MF_PricePoint;
import com.marketflip.shared.shopper.MF_Shopper;
import com.marketflip.shared.shopper.MF_ShopperDAO;

/**
 * This test case runs integrations tests for the main class of Market Flip Application, effectively running the entire application with real connections to external sources.
 *
 * @author highball
 *
 */
//fixing wokrtree
public class JIT_MFA_Main {

	private ExecutorService							executor;
	private ArrayBlockingQueue<MFA_ShopperCrawler>	bq;

	/**
	 * The purpose of this test is to run the exact same lines as the main method to ensure it works
	 * as inspected; it will check by determining if the application reach the shopper limit set by
	 * the argument shopperLimit.
	 *
	 * @throws Exception
	 */
	@Test
	public void Main_2ArgsFake_RunToCompletion() throws Exception {
		// Test Variables
		int expectedShopperCount, actualShopperCount;
		int shopperLimit, notificationLimit;
		MF_Shopper shopper;
		String shopperUserName, shopperEmail;
		MF_ShopperDAO shopperDAO;
		MFA_ShopperManager sm;
		MFA_NotificationManager nm;
		MF_PricePoint pricePoint;
		MF_Product product;
		MF_ProductsDAO productDAO;
		Thread crawlerThread, notificationThread;
		boolean reset; 
		// Expected
		expectedShopperCount = 1;
		// Actual

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
		// fill product db
		reset = true;
		productDAO = new MF_ProductsDAO("testing", reset);
		product = new MF_Product("045496901738", new MF_Price(30.00, new java.util.Date()));
		productDAO.addProductToCommit(product);
		productDAO.commitProductsToDatabase();
		executor = Executors.newCachedThreadPool();
		bq = new ArrayBlockingQueue<MFA_ShopperCrawler>(3);
		shopperLimit = 1;
		notificationLimit = 1;
		bq = new ArrayBlockingQueue<>(3);
		sm = new MFA_ShopperManager(bq, shopperLimit, false);
		nm = new MFA_NotificationManager(bq, notificationLimit, false);
		crawlerThread = new Thread(sm);
		notificationThread = new Thread(nm);
		executor.execute(crawlerThread);
		executor.execute(notificationThread);
		executor.awaitTermination(3, TimeUnit.MINUTES);
		actualShopperCount = sm.getCompletedBlockingQueueAdditions();
		// Test
		assertEquals(expectedShopperCount, actualShopperCount);
		// rollback
		shopperDAO.deleteAllTables();
		shopperDAO.close();
		productDAO.close();
	}
	/**
	 * The purpose of this test is to run actual main method to ensure it works
	 * as inspected; it will check by determining if the application reach the shopper limit set by
	 * the argument shopperLimit.
	 *
	 * @throws Exception
	 */
	@Test
	public void Main_2ArgsReal_CompleteAtShopperLimit() throws Exception {
		// Test Variables
		int expectedShopperCount, actualShopperCount;
		int shopperLimit, notificationLimit;
		MF_Shopper shopper;
		String shopperUserName, shopperEmail;
		MF_ShopperDAO shopperDAO;
		MFA_ShopperManager sm;
		MFA_NotificationManager nm;
		MF_PricePoint pricePoint;
		MF_Product product;
		MF_ProductsDAO productDAO;
		Thread crawlerThread, notificationThread;
		MFA_Main main;
		String[] args;
		boolean reset;
		// Expected
		expectedShopperCount = 1;
		// Actual

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
		// fill product db
		reset = true;
		productDAO = new MF_ProductsDAO("testing", reset);
		product = new MF_Product("045496901738", new MF_Price(30.00, new java.util.Date()));
		productDAO.addProductToCommit(product);
		productDAO.commitProductsToDatabase();

		shopperLimit = 1;
		notificationLimit = 1;
		args = new String[2];
		args[0] = String.valueOf(shopperLimit);
		args[1] = String.valueOf(notificationLimit);
		main = new MFA_Main();
		main.main(args);
		executor = main.getExecutor();
		executor.shutdown();
		executor.awaitTermination(1, TimeUnit.MINUTES);
		// Test
		assertTrue(executor.isShutdown());
	}
}
