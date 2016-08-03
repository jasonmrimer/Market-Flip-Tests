package com.marketflip.tests.crawler;

import static org.junit.Assert.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Future;

import org.jsoup.nodes.Document;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.marketflip.crawler.netcrawler.MFC_NetCrawler;
import com.marketflip.crawler.netcrawler.MFC_NetCrawlerManager;
import com.marketflip.crawler.scanalyzer.MFC_SourceCodeAnalyzerManager;

/**
 * The purpose of this test class is to run tests on the NetCrawlerManager and its various methods.
 * The unit tests will rely on local files to increase speed and consistency of tests while actual
 * connections to the Internet shall reside in the integration tests (JIT).
 * 
 * @author Atlas
 *
 */
public class JUT_MFC_NetCrawlerManager {

	private static BlockingQueue<Document>	bqMFSourceCode		= new ArrayBlockingQueue<Document>(
			MFC_SourceCodeAnalyzerManager.MFC_MAX_ANALYZER_QUEUE_COUNT);
	final ClassLoader						loader				= this.getClass().getClassLoader();
	final String							htmlResourceFolder	= "html/";
	final String		bucketName			= "http://www.lovenirds.com/";		// Google Developer Console bucket that acts as a static website

	/**
	 * The purose of this test is to construct an instance of NetCrawlerManager with a specified
	 * blockingQueue size then test the proper creation of that constructor comparing the ToString
	 * from the instance.
	 *
	 * @throws Exception
	 */
	@Test
	public void Construct_BlockingQueueSize3_ClassToStringMatch() throws Exception {
		// Test Variables
		String expectedClassToString, actualClassToString;
		BlockingQueue<Document> bq;
		MFC_NetCrawlerManager netCrawlerManager;
		// Expected
		expectedClassToString = "MFC_NetCrawlerManager object with BlockingQueue size: 3";
		// Actual
		bq = new ArrayBlockingQueue<Document>(3);
		netCrawlerManager = new MFC_NetCrawlerManager(bq, "", true);
		actualClassToString = netCrawlerManager.toString();
		// Test
		assertEquals(expectedClassToString, actualClassToString);
	}

	/**
	 * The purose of this test is to ensure the NetCrawlerManager visits a number of sites equal
	 * to the original HTML Doc and the number of links inside it thereby indicating it processed
	 * those links.
	 *
	 * @throws Exception
	 */
	@Test
	public void Run_HTMLDocWith3Links_Count4Sites() throws Exception {
		// Test Variables
		int expectedFuturesCount, actualFuturesCount;
		String testFileName, testFilePath;
		MFC_NetCrawlerManager netCrawlerManager;
		// Expected
		testFileName = "HTMLTest_RelativeReferences.html"; // name of file
		testFilePath = loader.getResource(htmlResourceFolder + testFileName).getPath();
		expectedFuturesCount = 4;
		// Actual
		netCrawlerManager = new MFC_NetCrawlerManager(bqMFSourceCode, testFilePath, true);
		netCrawlerManager.run();
		actualFuturesCount = netCrawlerManager.getSitesVisited();
		// Test
		assertEquals(expectedFuturesCount, actualFuturesCount);
	}
	
	/**
	 * Protozoa Sprint 01
	 * The purpose of this test is to ensure the crawler visits 1 site in the bucket then follows it to 4 more bucket sites.
	 * 
	 */
	@Test
	public void Run_HTMLDocWith4Links_Visit5Sites() throws Exception {
		// Test Variables
		// create expected array as a Collection
		Collection<String> expectedURLs = new ArrayList<String>(),
				actualURLs = new ArrayList<String>();
		String testFileName, link1FileName, link2FileName, link3FileName, link3_1FileName;
		String testFilePath, link1FilePath, link2FilePath, link3FilePath, link3_1FilePath;
		String testFileBaseURI;
		MFC_NetCrawlerManager netCrawlerManager;
		URL url;
		// Expected
		testFileName = "HTMLTest_RelativeReferences.html"; // name of file
		link1FileName = "LinkedHTMLFile1.html";
		link2FileName = "LinkedHTMLFile2.html";
		link3FileName = "LinkedHTMLFile3.html";
		link3_1FileName = "LinkedHTMLFile3-1.html";
		testFilePath = bucketName + testFileName;
		link1FilePath = bucketName + link1FileName;
		link2FilePath = bucketName + link2FileName;
		link3FilePath = bucketName + link3FileName;
		link3_1FilePath = bucketName + link3_1FileName;
		testFileBaseURI = testFilePath.replace(testFileName, "");
		expectedURLs.addAll(Arrays.asList(testFilePath, link1FilePath, link2FilePath, link3FilePath, link3_1FilePath));
		// Actual
		netCrawlerManager = new MFC_NetCrawlerManager(bqMFSourceCode, testFilePath, 5);
		//TODO set NCM to be constructed with a limit of 5 then shutdown
		netCrawlerManager.run();
		// create actual array
		MFC_NetCrawler netCrawler = new MFC_NetCrawler(testFilePath);
		netCrawler.call();
		actualURLs = netCrawler.getURLs();
		System.out.println("actualURLs:" + actualURLs.toString());
		// Test
		assertEquals(expectedURLs, actualURLs);
	}

}