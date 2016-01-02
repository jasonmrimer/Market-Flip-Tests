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


public class JIT_MFC_DatabaseCrawlerManager {

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
		while(dbmThread.isAlive()){
			//wait
		}
		actualProductInsertionCount = dbm.getProductsInsertedToDBCount();
		// Test
		assertEquals(expectedProductInsertionCount, actualProductInsertionCount);
	}

}
