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

public class JIT_MFC_NetCrawlerManager {

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
