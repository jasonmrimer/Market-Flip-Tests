package com.mft.crawler;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
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

	/**
	 * The purpose of this test is to test the succesful creation of a NetCrawler instance without a
	 * database and a local HTML file.
	 */
	@Test
	public void Constructor_DatabaseAndLocalFilePath_NetCrawlerObject() throws Exception {
		// Test variables
		String expectedToString, actualToString;
		// Expected
		String testFileName = "HTMLTest_Links.html"; // name of file
		URL testFileURL = getClass().getResource(testFileName); // get URL to use for paths later
		expectedToString = "MFC_NetCrawler object without database for: " + testFileURL.getPath();
		// Actual
		MFC_NetCrawler netCrawler = new MFC_NetCrawler(testFileURL.getPath());
		actualToString = netCrawler.toString();
		assertEquals(expectedToString, actualToString);
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
		MFC_NetCrawler netCrawler = new MFC_NetCrawler(testFilePath);
		netCrawler.call();
		Collection<String> actualLinkArray = netCrawler.getURLs();
		assertEquals(expectedLinkArray, actualLinkArray);
	}

	/**
	 * The purose of this test is to send a fake local HTML file with relative references to the
	 * NetCrawler and ensure it returns absolute references so it could actually travel to those
	 * websites in real practice.
	 *
	 * @throws Exception
	 */
	@Test
	public void getURLs_HTMLDocWithRelativeURLs_ArrayOfAbsoluteURLs() throws Exception {
		// Test Variables
		Collection<String> expectedURLs = new ArrayList<String>(),
				actualURLs = new ArrayList<String>();
		String testFileName, link1FileName, link2FileName, link3FileName;
		String testFilePath, link1FilePath, link2FilePath, link3FilePath;
		String testFileBaseURI;
		MFC_NetCrawler netCrawler;
		// Expected
		testFileName = "HTMLTest_RelativeReferences.html";
		link1FileName = "LinkedHTMLFile1.html";
		link2FileName = "LinkedHTMLFile2.html";
		link3FileName = "LinkedHTMLFile3.html";
		testFilePath = getClass().getResource(testFileName).getPath();
		link1FilePath = getClass().getResource(link1FileName).getPath();
		link2FilePath = getClass().getResource(link2FileName).getPath();
		link3FilePath = getClass().getResource(link3FileName).getPath();
		testFileBaseURI = testFilePath.replace(testFileName, "");
		System.out.println(testFileBaseURI);
		System.out.println(link1FilePath);
		expectedURLs.addAll(Arrays.asList(link1FilePath, link2FilePath,
				link3FilePath));
		// Actual
		netCrawler = new MFC_NetCrawler(testFilePath, testFileBaseURI);
		netCrawler.call();
		actualURLs = netCrawler.getURLs();
		System.out.println(netCrawler.getSiteDoc().baseUri());
		// Test
		assertEquals(expectedURLs, actualURLs);
	}

	/**
	 * The purpose of this test is to feed the NetCrawler a false site (i.e., one that begins with
	 * something other than "http" thus cannot return an HTML Doc) to assure it returns a null JSoup
	 * Doc. The NetCrawler needs to work only on sites that have an actual HTML documents.
	 */
	@Test
	public void Call_NonHTTPSite_NullJSoupDoc() throws Exception {
		// Testing variables
		Document expectedJSoupDoc, actualJSoupDoc;
		expectedJSoupDoc = null;
		MFC_NetCrawler netCrawler = new MFC_NetCrawler("fakewebsite.com");
		netCrawler.call();
		actualJSoupDoc = netCrawler.getSiteDoc();
		assertEquals(expectedJSoupDoc, actualJSoupDoc);
	}

	/**
	 * The purpose of this test is to feed the NetCrawler a false site (i.e. one that is not an HTML
	 * Doc) to assure it returns a null JSoup Doc. The NetCrawler needs to work only on sites that
	 * have an actual HTML documents.
	 */
	@Test
	public void Call_SiteWithoutHTMLDoc_NullJSoupDoc() throws Exception {
		// Testing variables
		Document expectedJSoupDoc, actualJSoupDoc;
		String testFileName, testFilePath;
		URL testFileURL;
		MFC_NetCrawler netCrawler;
		// Expected
		expectedJSoupDoc = null;
		// Actual
		testFileName = "SiteWithoutHTMLDoc"; // name of file
		testFileURL = getClass().getResource(testFileName); // get URL to use for paths later
		testFilePath = testFileURL.getPath(); // use URL to get path
		netCrawler = new MFC_NetCrawler(testFilePath);
		netCrawler.call();
		actualJSoupDoc = netCrawler.getSiteDoc();
		// Test
		assertEquals(expectedJSoupDoc, actualJSoupDoc);
	}
}
