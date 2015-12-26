package com.mft.crawler;

import static org.junit.Assert.*;

import org.apache.commons.validator.routines.checkdigit.EAN13CheckDigit;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * The purpose of this test class is to inspect fake HTML documents created for use across
 * the rest of Market Flip to ensure each document properly interacts with JSoup to become
 * a consistent source for testing (i.e., control group)
 * 
 * @author Atlas
 *
 */
public class JUT_HTML {

	final ClassLoader	loader				= this.getClass().getClassLoader();
	final String		htmlResourceFolder	= "html/";
	final String		bucketName			= "http://www.lovenirds.com/"; // Google Developer Console bucket that acts as a static website

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
	 * I want a test that ensures the relative paths work.
	 */
	@Test
	public void GetLocalResource_RelativePathWithURL_CorrectPath() {
		String projectPath = getClass().getProtectionDomain().getCodeSource().getLocation()
				.getPath();
		projectPath += "html/";
		String fileName = "TestHTMLDoc.html";
		String expectedPath = projectPath + fileName;
		String actualPath = loader.getResource(htmlResourceFolder + fileName).getPath();
		assertEquals(expectedPath, actualPath);
	}

	/**
	 * This test intends to retrieve a local file then load it as a
	 * JSoup Document. It tests if the JSDoc's title matches the actual title of the file.
	 * 
	 * @throws IOException
	 */
	@Test
	public void CreateHTMLDoc_LocalHTMLDoc_HTMLDocWithCorrectTitle() throws IOException {
		// set traits
		String expectedTitle = "HTML Test for Links"; // <title>
		String testFileName = "HTMLTest_AbsoluteReferences.html"; // name of file
		String charSetName = "UTF-8"; // used to determine how the HTML parses
		// manipulate fields
		// get URL to use for paths later
		URL testFileURL = loader.getResource(htmlResourceFolder + testFileName);
		String testFilePath = testFileURL.getPath(); // use URL to get path
		File testFile = new File(testFilePath); // temp file to pass to JSoup parser
		Document jsDoc = Jsoup.parse(testFile, charSetName);
		String actualTitle = jsDoc.title();
		assertEquals(expectedTitle, actualTitle);
	}

	/**
	 * The purpose of this test is to extract all links from a fake HTML document and test
	 * the array against the known array of links.
	 * 
	 * @throws IOException
	 */
	@Test
	public void ExtractLinksFromHTMLDoc_LocalHTMLDoc_CorrectArrayOfLinks() throws IOException {
		// create expected array
		ArrayList<String> expectedLinkArray = new ArrayList<String>();
		expectedLinkArray.add("http://www.link1.com");
		expectedLinkArray.add("http://www.link2.com");
		expectedLinkArray.add("http://www.link3.com");
		// create actual array
		ArrayList<String> actualLinkArray = new ArrayList<String>();
		String testFileName = "HTMLTest_AbsoluteReferences.html"; // name of file
		String charSetName = "UTF-8"; // used to determine how the HTML parses
		Document jsDoc = Jsoup.parse(
				new File(loader.getResource(htmlResourceFolder + testFileName).getPath()),
				charSetName);
		// add links from doc to array
		Elements links = jsDoc.select("a[href]");
		for (Element link : links) {
			actualLinkArray.add(link.attr("abs:href"));
		}
		assertEquals(expectedLinkArray, actualLinkArray);
	}

	@Test
	public void RetrieveFilePath_LocalHTMLFileInResources_HTMLFilePath() throws Exception {
		// Test Variables
		String expectedPath, actualPath;
		final ClassLoader loader = this.getClass().getClassLoader();
		// Expected
		// change to bin as it builds the file to the bin to retrieve at runtime
		expectedPath = "/Users/Atlas/GCloud/marketflip-tests/MFCJUnitTests/bin/html/LocationTest.html";
		// Actual
		actualPath = loader.getResource("html/LocationTest.html").getPath();
		// Test
		assertEquals(expectedPath, actualPath);
	}

	/*
	 * Walmart Tets
	 * This section tests all of the HTML documents from and specific actions related to Walmart.
	 */

	/**
	 * 
	 * The purose of this test is to create an HTML document from a local file that is actually
	 * source code from a Walmart website. It will test the document's parsed title with the actual
	 * title via string check.
	 *
	 * @throws Exception
	 */
	@Test
	public void CreateHTMLDoc_TestWalmartSuperMario_DocByTitle() throws Exception {
		// set traits
		String expectedTitle, actualTitle;
		expectedTitle = "New Super Mario Bros. (Wii) - Walmart.com"; // <title>
		String testFileName = "TestWalmartSuperMarioBrosWii.html"; // name of file
		// manipulate fields
		// get URL to use for paths later
		URL testFileURL = loader.getResource(htmlResourceFolder + testFileName);
		String testFilePath = testFileURL.getPath(); // use URL to get path
		File testFile = new File(testFilePath); // temp file to pass to JSoup parser
		Document jsDoc = Jsoup.parse(testFile, "UTF-8");
		actualTitle = jsDoc.title();
		assertEquals(expectedTitle, actualTitle);
	}

	@Test
	public void ExtractUPC_TestWalmartSuperMarioBrosWii_MatchingUPC() throws Exception {
		// Test Variables
		String expectedUPC, actualUPC;
		String testFileName;
		// Expected
		expectedUPC = "045496901738";
		// Actual
		testFileName = "TestWalmartSuperMarioBrosWii.html"; // name of file
		URL testFileURL = loader.getResource(htmlResourceFolder + testFileName);
		String testFilePath = testFileURL.getPath(); // use URL to get path
		File testFile = new File(testFilePath); // temp file to pass to JSoup parser
		Document jsDoc = Jsoup.parse(testFile, "UTF-8");
		actualUPC = jsDoc.select("meta[property=og:upc").attr("content");
		// Test
		assertEquals(expectedUPC, actualUPC);
	}

	@Test
	public void ValidateUPC_SuperMarioBrosWii_ValidUPC() throws Exception {
		// Test Variables
		String expectedUPC, actualUPC;
		// Expected
		expectedUPC = "045496901738";
		// Test
		EAN13CheckDigit validator = new EAN13CheckDigit();
		assertTrue(validator.isValid(expectedUPC));
	}

	@Test
	public void RetrieveFileFromBucket_FileName_FileByTitle() throws Exception {
		// Test Variables
		String expectedTitle, actualTitle;
		String fileName;
		Document jsDoc;
		URL url;
		// Expected
		expectedTitle = "HTML Test using Relative References";
		// Actual
		fileName = "HTMLTest_RelativeReferences.html";
		url = new URL(bucketName + fileName);
		jsDoc = Jsoup.parse(url, 5000);
		actualTitle = jsDoc.title();
		// Test
		assertEquals(expectedTitle, actualTitle);
	}
	
	/**
	 * The purpose of this test is to extract all links from a fake HTML document and test
	 * the array against the known array of links.
	 * 
	 * @throws IOException
	 */
	@Test
	public void ExtractLinksFromHTMLDoc_BucketHTMLDoc_CorrectArrayOfLinks() throws IOException {
		// Test Variables
		ArrayList<String> expectedLinkArray = new ArrayList<String>();
		ArrayList<String> actualLinkArray = new ArrayList<String>();
		String fileName;
		Document jsDoc;
		URL url;
		// Expected
		expectedLinkArray.add("http://www.lovenirds.com/LinkedHTMLFile1.html");
		expectedLinkArray.add("http://www.lovenirds.com/LinkedHTMLFile2.html");
		expectedLinkArray.add("http://www.lovenirds.com/LinkedHTMLFile3.html");
		// Actual
		fileName = "HTMLTest_RelativeReferences.html";
		url = new URL(bucketName + fileName);
		jsDoc = Jsoup.parse(url, 5000);
		// add links from doc to array
		Elements links = jsDoc.select("a[href]");
		for (Element link : links) {
			actualLinkArray.add(link.attr("abs:href"));
		}
		assertEquals(expectedLinkArray, actualLinkArray);
	}
}
