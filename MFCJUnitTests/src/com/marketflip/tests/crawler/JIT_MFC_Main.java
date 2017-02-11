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

/**
 * The purpose of this test case is to run integration tests for the Main class of the Market Flip
 * Crawler; essentially, it runs the entire application and tests whether it ran successfully.
 *
 * @author highball
 *
 */
public class JIT_MFC_Main {

	/**
	 * The purpose of this test is to run the main method of Market Flip Crawler application. It
	 * should take the two arguments and run to completion as expected by the arguments (i.e., to
	 * the shopper/website limit defined by the parameters).
	 *
	 * @throws Exception
	 */
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
