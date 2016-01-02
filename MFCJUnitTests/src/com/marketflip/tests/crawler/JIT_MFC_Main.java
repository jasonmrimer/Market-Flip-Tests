package com.marketflip.tests.crawler;

import static org.junit.Assert.*;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.jsoup.nodes.Document;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.marketflip.crawler.dbcrawler.MFC_DatabaseCrawlerManager;
import com.marketflip.crawler.netcrawler.MFC_NetCrawlerManager;
import com.marketflip.crawler.scanalyzer.MFC_SourceCodeAnalyzerManager;
import com.marketflip.shared.products.MF_Product;

public class JIT_MFC_Main {

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
	public void Main_TwoArgs_RunToCompletion() throws Exception {
		// Expected

		// Test
		// Test Variables
		String startURLPath;
		int siteLimit, productLimit, docLimit;
		BlockingQueue<MF_Product> bqMFProduct;
		BlockingQueue<Document> bqMFSourceCode;
		ExecutorService executor;
		MFC_SourceCodeAnalyzerManager scaMngr;
		MFC_DatabaseCrawlerManager dbcMngr;
		MFC_NetCrawlerManager netMngr;
		Thread scaThread, dbcThread, netThread;
		// Actual
		executor = Executors.newCachedThreadPool();
		startURLPath = "http://www.lovenirds.com/TestWalmartSuperMarioBrosWii.html";
		siteLimit = 1;
		productLimit = 1;
		docLimit = 1;
		// Create pipelines for inter-thread communication
		bqMFProduct = new ArrayBlockingQueue<MF_Product>(
				MFC_DatabaseCrawlerManager.MFC_MAX_DB_QUEUE_COUNT);
		bqMFSourceCode = new ArrayBlockingQueue<Document>(
				MFC_SourceCodeAnalyzerManager.MFC_MAX_ANALYZER_QUEUE_COUNT);
		// Create threads to run simultaneous sections of the application
		dbcMngr = new MFC_DatabaseCrawlerManager(bqMFProduct, "testing", productLimit);
		netMngr = new MFC_NetCrawlerManager(bqMFSourceCode, startURLPath, siteLimit);
		scaMngr = new MFC_SourceCodeAnalyzerManager(bqMFSourceCode, bqMFProduct, docLimit);
		dbcThread = new Thread(dbcMngr);
		netThread = new Thread(netMngr); //takes product and updates database
		scaThread = new Thread(scaMngr); //takes sourcecode and returns product
		executor.execute(dbcThread);
		executor.execute(netMngr);
		executor.execute(scaMngr);
		executor.shutdown();
		executor.awaitTermination(5, TimeUnit.MINUTES);
		//		while (!executor.isShutdown()) {
		//			//wait
		//		}
		assertEquals(1, netMngr.getSitesVisited());
		assertEquals(1, scaMngr.getDocsAnalyzedCount());
		assertEquals(1, dbcMngr.getProductsInsertedToDBCount());
	}

}
