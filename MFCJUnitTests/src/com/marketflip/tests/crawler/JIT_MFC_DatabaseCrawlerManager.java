package com.marketflip.tests.crawler;

import static org.junit.Assert.*;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.marketflip.crawler.dbcrawler.MFC_DatabaseCrawlerManager;
import com.marketflip.crawler.netcrawler.MFC_NetCrawlerManager;
import com.marketflip.shared.products.MF_Price;
import com.marketflip.shared.products.MF_Product;

/**
 * The purpose of this test case is to run integration testing for the Database Crawler Manager,
 * allowing it to connect to external resources such as the actual Product Database and make
 * meaningful assertions against it.
 *
 * @author highball
 *
 */
public class JIT_MFC_DatabaseCrawlerManager {

	/**
	 * The purpose of this test is to ensure the database crawler manager can run a complete cycle,
	 * end the thread, and return the expected number of products added to the database.
	 *
	 * @throws Exception
	 */
	@Test
	public void Run_1Product_ProductCountEquals1() throws Exception {
		// Test Variables
		int expectedProductInsertionCount, actualProductInsertionCount, productLimit;
		MFC_DatabaseCrawlerManager dbm;
		MF_Product product;
		BlockingQueue<MF_Product> bqMFProduct;
		Thread dbmThread;
		// Expected
		expectedProductInsertionCount = 1;
		// Actual
		productLimit = 1;
		product = new MF_Product("045496901738", new MF_Price(40.00, new java.util.Date()));
		bqMFProduct = new ArrayBlockingQueue<>(1);
		bqMFProduct.add(product);
		dbm = new MFC_DatabaseCrawlerManager(bqMFProduct, "testing", productLimit);
		dbmThread = new Thread(dbm);
		dbmThread.start();
		while (dbmThread.isAlive()) {
			//wait
		}
		actualProductInsertionCount = dbm.getProductsInsertedToDBCount();
		// Test
		assertEquals(expectedProductInsertionCount, actualProductInsertionCount);
	}

}
