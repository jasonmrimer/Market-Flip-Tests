package com.marketflip.tests.crawler;

import static org.junit.Assert.*;

import java.io.File;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.commons.codec.digest.DigestUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.marketflip.crawler.netcrawler.MFC_NetCrawler;
import com.marketflip.crawler.netcrawler.MFC_WebsiteDAO;

/**
 * The purpose of this integration test is to serve as the test class for MFC_NetCrawler when it
 * connects to external resources (notably different than HTML docs included in the project as in
 * the unit tests). When the object connects to the Internet or a database, this class will test
 * those interactions. Note that these integrations tests inheritantly take more time than the unit
 * tests and are designed to run separately in order to choose long/short testing times.
 *
 * @author highball
 *
 */
public class JIT_MFC_NetCralwer {

	private static MFC_WebsiteDAO	database;
	final ClassLoader				loader				= this.getClass().getClassLoader();
	final String					htmlResourceFolder	= "html/";

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		database = new MFC_WebsiteDAO(); // create database for test use
	}

	@Before
	public void setUp() throws Exception {
		WebsiteDAO_DefaultConstructor_IsRunning(); // test connection
	}

	@Test
	public void WebsiteDAO_DefaultConstructor_IsRunning() throws SQLException {
		assertTrue("DAO disconnected.", !database.con.isClosed());
	}

	/**
	 * The purpose of this test is to test the succesful creation of a NetCrawler instance with a
	 * database and a local HTML file.
	 */
	@Test
	public void Constructor_DatabaseAndLocalFilePath_NetCrawlerObject() throws Exception {
		// Test variables
		String expectedToString, actualToString;
		// Expected
		String testFileName = "HTMLTest_AbsoluteReferences.html"; // name of file
		URL testFileURL = loader.getResource(htmlResourceFolder + testFileName); // get URL to use
																					// for paths
																					// later
		expectedToString = "MFC_NetCrawler object with database for: " + testFileURL.getPath();
		// Actual
		MFC_NetCrawler netCrawler = new MFC_NetCrawler(database, testFileURL.getPath());
		actualToString = netCrawler.toString();
		assertEquals(expectedToString, actualToString);
	}

	/**
	 * The purose of this test is to run a complete call of the NetCrawler. That call should insert
	 * the visited website URL into the Website Database; this test checks whether it added it to
	 * the database.
	 *
	 * @throws Exception
	 */
	@Test
	public void Call_LocalHTMLFile_URLArrayListFromDatabase() throws Exception {
		// Test Variables
		ArrayList<String> expectedResultSetToString = new ArrayList<String>();
		ArrayList<String> actualResultSetToString = new ArrayList<String>();
		String testFileName, testFilePath, testFilePathAfterHash;
		// Expected
		testFileName = "HTMLTest_AbsoluteReferences.html"; // name of file
		testFilePath = loader.getResource(htmlResourceFolder + testFileName).getPath();
		testFilePathAfterHash = DigestUtils.sha256Hex(testFilePath);
		expectedResultSetToString.add(testFilePathAfterHash);
		// Actual
		MFC_NetCrawler netCrawler = new MFC_NetCrawler(database, testFilePath);
		netCrawler.call();
		actualResultSetToString = netCrawler.getDatabase()
				.getURLResultSetAsList(testFilePathAfterHash);
		// Test
		assertEquals(expectedResultSetToString, actualResultSetToString);
	}

	/**
	 * The purose of this test is to feed the NetCrawler a website without an HTML document to test
	 * if it still adds the website to the database by returning a result set as an Array List and
	 * testing whether the site is in the database.
	 *
	 * @throws Exception
	 */
	@Test
	public void Call_FakeWebsiteWithoutHTMLDoc_URLInDatabaseViaArrayList() throws Exception {
		// Test Variables
		ArrayList<String> expectedResultSetToString = new ArrayList<String>();
		ArrayList<String> actualResultSetToString = new ArrayList<String>();
		String fakeWebsite, fakeWebsiteAfterHash;
		// Expected
		fakeWebsite = "http://www.fakewebsite.com"; // name of file
		fakeWebsiteAfterHash = DigestUtils.sha256Hex(fakeWebsite);
		expectedResultSetToString.add(fakeWebsiteAfterHash);
		// Actual
		MFC_NetCrawler netCrawler = new MFC_NetCrawler(database, fakeWebsite);
		netCrawler.call();
		actualResultSetToString = netCrawler.getDatabase()
				.getURLResultSetAsList(fakeWebsiteAfterHash);
		// Test
		assertEquals(expectedResultSetToString, actualResultSetToString);
	}

	/**
	 * The purpose of this test is to run the thread and visit an actual website that is known to
	 * have a JSoup Document for the website then test that document by title.
	 *
	 * @throws Exception
	 */
	@Test
	public void Call_ActualJSoupWebsite_SiteDocument() throws Exception {
		// Test Variables
		String expectedTitle, actualTitle, website;
		// Expected
		website = "http://jsoup.org/"; // name of file
		expectedTitle = "jsoup Java HTML Parser, with best of DOM, CSS, and jquery"; // <title>
		// Actual
		MFC_NetCrawler netCrawler = new MFC_NetCrawler(database, website);
		netCrawler.call();
		Document siteDoc = netCrawler.getSiteDoc();
		actualTitle = siteDoc.title();
		// Test
		assertEquals(expectedTitle, actualTitle);
	}

}