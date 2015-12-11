package com.mft.crawler;

import static org.junit.Assert.*;

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
		projectPath += "com/mft/crawler/";
		String fileName = "TestHTMLDoc.html";
		String expectedPath = projectPath + fileName;
		String actualPath = getClass().getResource(fileName).getPath();
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
		String testFileName = "HTMLTest_Links.html"; // name of file
		String charSetName = "UTF-8"; // used to determine how the HTML parses
		Document jsDoc = Jsoup.parse(new File(getClass().getResource(testFileName).getPath()),
				charSetName);
		// add links from doc to array
		Elements links = jsDoc.select("a[href]");
		for (Element link : links) {
			actualLinkArray.add(link.attr("abs:href"));
		}
		assertEquals(expectedLinkArray, actualLinkArray);
	}

	@Test
	public void CreateHTMLDoc_TestWalmartSuperMario_DocByTitle() throws Exception {
		// set traits
		String expectedTitle = "New Super Mario Bros. (Wii) - Walmart.com"; // <title>
		String testFileName = "TestWalmartSuperMarioBrosWii.html"; // name of file
		// manipulate fields
		URL testFileURL = getClass().getResource(testFileName); // get URL to use for paths later
		String testFilePath = testFileURL.getPath(); // use URL to get path
		File testFile = new File(testFilePath); // temp file to pass to JSoup parser
		Document jsDoc = Jsoup.parse(testFile, "UTF-8");
		String actualTitle = jsDoc.title();
		assertEquals(expectedTitle, actualTitle);
	}

}
