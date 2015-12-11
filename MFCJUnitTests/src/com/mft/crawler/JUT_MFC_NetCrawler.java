package com.mft.crawler;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.mfc.netcrawler.MFC_NetCrawler;
/**
 * These tests follow the Market Flip naming convention:
 * MethodBeingTested_ParametersOrConditionToTest_ExpectedOutcomeOfMethod()
 * 
 * It seems that nearly everything is an integration test because it requires connecting to an
 * external
 * source such as the website & product database or the Internet.
 */
import com.mfc.netcrawler.MFC_WebsiteDAO;

public class JUT_MFC_NetCrawler {

	private static MFC_WebsiteDAO database;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		// database = new MFC_WebsiteDAO(); // create database for test use
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		// WebsiteDAO_DefaultConstructor_IsRunning(); // test connection
	}

	// @Test
	// public void WebsiteDAO_DefaultConstructor_IsRunning() throws SQLException {
	// assertTrue("DAO disconnected.",!database.con.isClosed());
	// }

	@After
	public void tearDown() throws Exception {
	}

	/**
	 * The NetCrawler intends to take a website URL then return a JSoup Document. It will
	 * assert that the title of a local HTML file is the same as the JSoup Document's title.
	 * 
	 * @throws IOException
	 */
	@Test
	public void netCrawlerCall_localTestHTMLFile_jSoupDocByTitle() throws IOException {
		// set traits
		String expectedTitle = "HTML Test for Links"; // <title>
		String testFileName = "HTMLTest_Links.html"; // name of file
		String charSetName = "UTF-8"; // used to determine how the HTML parses
		// manipulate fields
		URL testFileURL = getClass().getResource(testFileName); // get URL to use for paths later
		String testFilePath = testFileURL.getPath(); // use URL to get path
		File testFile = new File(testFilePath); // temp file to pass to JSoup parser
		Document jsDoc = Jsoup.parse(testFile, charSetName);
		String actualTitle = jsDoc.title();
		assertEquals(expectedTitle, actualTitle);
	}

	/**
	 * The purpose of this test is to test the link extractor method of NetCrawler
	 * to extract all links from a fake HTML document and test
	 * the array against the known array of links.
	 * 
	 * @throws IOException
	 */
	@Test
	public void getURLs_localHTMLDoc_correctArrayOfLinks() throws IOException {
		// create expected array as a Collection
		Collection<String> expectedLinkArray = new ArrayList<String>();
		expectedLinkArray.add("http://www.link1.com");
		expectedLinkArray.add("http://www.link2.com");
		expectedLinkArray.add("http://www.link3.com");
		// create actual array
		String testFileName = "HTMLTest_Links.html"; // name of file
		String testFilePath = getClass().getResource(testFileName).getPath();
		Mock_MFC_NetCrawler netCrawler = new Mock_MFC_NetCrawler(testFilePath, true);
		netCrawler.call();
		Collection<String> actualLinkArray = netCrawler.getURLs();
		assertEquals(expectedLinkArray, actualLinkArray);
	}
	/**
	 * The NetCrawler needs to work only on sites that have an actual HTML documents.
	 * That excludes
	 */

}
