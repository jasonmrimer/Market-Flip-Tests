package com.marketflip.tests.crawler;

import static org.junit.Assert.*;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.jsoup.nodes.Document;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.marketflip.crawler.netcrawler.MFC_NetCrawlerManager;

/**
 * The purpose of this test case is to run integration testing for the Net Crawler Manager by
 * allowing real connections to the Internet as well as the products database, verifying assertions
 * and connections work successfully.
 *
 * @author highball
 *
 */
public class JIT_MFC_NetCrawlerManager {

	/**
	 * The purpose of this test is to run the call method of the Net Crawler using the Google
	 * Developer's Console bucket to act as a published website that we control and actual place an
	 * HTML Doc on the web, access it, then add its contents to the blocking queue - it will test
	 * the queue's counter for completed tasks.
	 *
	 * @throws Exception
	 */
	@Test
	public void Call_BucketWalmartMarioHTML_AddDocToBlockingQueue() throws Exception {
		// Test Variables
		int expectedBQSize, actualBQSize, siteLimit;
		MFC_NetCrawlerManager ncm;
		BlockingQueue bq;
		String startURLString;
		Thread ncmThread;
		// Expected
		expectedBQSize = 1;
		// Actual
		bq = new ArrayBlockingQueue<Document>(3);
		startURLString = "http://www.lovenirds.com/TestWalmartSuperMarioBrosWii.html";
		siteLimit = 1;
		ncm = new MFC_NetCrawlerManager(bq, startURLString, siteLimit);
		ncmThread = new Thread(ncm);
		ncmThread.start();
		while (ncmThread.isAlive()) {
			// wait until complete
		}
		actualBQSize = ncm.getSitesVisited();
		// Test
		assertEquals(expectedBQSize, actualBQSize);
	}

}
