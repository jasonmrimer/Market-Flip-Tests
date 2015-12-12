/**
 * The purpose of this integration test is to serve as the test class for MFC_NetCrawler when it
 * connects to external resources (notably different than HTML docs included in the project as in
 * the unit tests). When the object connects to the Internet or a database, this class will test
 * those interactions. Note that these integrations tests inheritantly take more time than the unit
 * tests and are designed to run separately in order to choose long/short testing times.
 */
package com.mft.crawler;

import static org.junit.Assert.*;

import java.net.URL;
import java.sql.SQLException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.mfc.netcrawler.MFC_NetCrawler;
import com.mfc.netcrawler.MFC_WebsiteDAO;

public class JIT_MFC_NetCralwer {

	private static MFC_WebsiteDAO database;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		database = new MFC_WebsiteDAO(); // create database for test use
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		WebsiteDAO_DefaultConstructor_IsRunning(); // test connection
	}

	@After
	public void tearDown() throws Exception {
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
		String testFileName = "HTMLTest_Links.html"; // name of file
		URL testFileURL = getClass().getResource(testFileName); // get URL to use for paths later
		expectedToString = "MFC_NetCrawler object with database for: " + testFileURL.getPath();
		// Actual
		MFC_NetCrawler netCrawler = new MFC_NetCrawler(database, testFileURL.getPath());
		actualToString = netCrawler.toString();
		assertEquals(expectedToString, actualToString);
	}
}
