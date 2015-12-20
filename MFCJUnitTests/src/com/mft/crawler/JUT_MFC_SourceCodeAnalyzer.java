package com.mft.crawler;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.marketflip.shared.products.MF_Product;
import com.mfc.netcrawler.MFC_NetCrawlerManager;
import com.mfc.scanalyzer.MFC_SourceCodeAnalyzer;

public class JUT_MFC_SourceCodeAnalyzer {

	final ClassLoader	loader				= this.getClass().getClassLoader();
	final String		htmlResourceFolder	= "html/";

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
	 * The purose of this test is to construct an instance of SourceCodeAnalyzer with a specified
	 * HTML Document then test the proper creation of that constructor comparing the ToString
	 * from the instance.
	 *
	 * @throws Exception
	 */
	@Test
	public void Construct_HTMLTest_RelativeReferences_ClassToStringMatch() throws Exception {
		// Test Variables
		String expectedClassToString, actualClassToString;
		MFC_SourceCodeAnalyzer sca;
		Document htmlDocToSCA;
		String localFileName = "HTMLTest_RelativeReferences.html";
		// Expected
		expectedClassToString = "MFC_SourceCodeAnalyzer object with document title: HTML Test using Relative References";
		// Actual
		htmlDocToSCA = getDocFromLocalFileName(localFileName);
		sca = new MFC_SourceCodeAnalyzer(htmlDocToSCA);
		actualClassToString = sca.toString();
		// Test
		assertEquals(expectedClassToString, actualClassToString);
	}

	/**
	 * The purose of this test is to send an HTML doc without any product information to the
	 * analyzer and ensure it returns a null product when it runs its call() method.
	 *
	 * @throws Exception
	 */
	@Test
	public void ReturnProduct_DocWithoutProductDetails_NullProduct() throws Exception {
		// Test Variables
		MF_Product expectedProduct, actualProduct;
		MFC_SourceCodeAnalyzer sca;
		String localFileName = "HTMLTest_RelativeReferences.html";
		// Expected
		expectedProduct = null;
		// Actual
		sca = new MFC_SourceCodeAnalyzer(getDocFromLocalFileName(localFileName));
		actualProduct = sca.call();
		// Test
		assertEquals(expectedProduct, actualProduct);
	}

	/**
	 * The purose of this test is to send a local HTML Doc from a Walmart product site to the
	 * SourceCodeAnalyzer then expect it to return a product that accurately describes the product
	 * inside the HTLML Doc.
	 *
	 * @throws Exception
	 */
	@Test
	public void CallAndReturnProduct_TestWalmartSuperMarioWii_SuperMarioProduct() throws Exception {
		// Test Variables
		MF_Product expectedProduct, actualProduct;
		MFC_SourceCodeAnalyzer sca;
		String localFileName = "TestWalmartSuperMarioBrosWii.html";
		//		String name, description, UPC, UNSPSC, linkToProduct, prices, height, width, length, weight;
		// Expected
		expectedProduct = new MF_Product();
		expectedProduct.setName("New Super Mario Bros. (Wii) - Walmart.com");
		expectedProduct.setDescription(
				"A classic 2D adventure, the game follows the style of New Super Mario Bros. for Nintendo DS. Princess Peach has been kidnapped by Bowser and its up to Mario to save her, but this time hes not alone! Up to four players can join in the fun at the same time, playing as Mario, Luigi or either one of two Toads. Jump, swim and fly through eight classically-themed worlds, with even more challenges awaiting the best players. New power-ups such as the Propeller Suit and Penguin Suit grant unique abilities to traverse levels and combat Koopas, and the return of Yoshi guarentees an enemy-gobbling good time for all. New Super Mario Bros. Wii is also the first Nintendo game to feature a dynamic help system, which allows you to access a mode which allows you to watch how a level can be completed if you are stuck.");
		expectedProduct.setUPC("045496901738");
		// Actual
		sca = new MFC_SourceCodeAnalyzer(getDocFromLocalFileName(localFileName));
		actualProduct = sca.call();
		// Test
		//		assertEquals(expectedProduct, actualProduct);
		assertTrue(expectedProduct.equals(actualProduct));
	}

	// Test by product name
	@Test
	public void CallAndReturnProduct_TestWalmartSuperMarioWii_SuperMarioName() throws Exception {
		// Test Variables
		MF_Product product;
		String expectedName, actualName;
		MFC_SourceCodeAnalyzer sca;
		String localFileName = "TestWalmartSuperMarioBrosWii.html";
		//		String name, description, UPC, UNSPSC, linkToProduct, prices, height, width, length, weight;
		// Expected
		expectedName = "New Super Mario Bros. (Wii) - Walmart.com";
		// Actual
		sca = new MFC_SourceCodeAnalyzer(getDocFromLocalFileName(localFileName));
		product = sca.call();
		actualName = product.getName();
		// Test
		assertEquals(expectedName, actualName);
	}

	// Test by product UPC
	@Test
	public void CallAndReturnProduct_TestWalmartSuperMarioWii_SuperMarioUPC() throws Exception {
		// Test Variables
		String expectedUPC, actualUPC;
		MF_Product product;
		MFC_SourceCodeAnalyzer sca;
		String localFileName = "TestWalmartSuperMarioBrosWii.html";
		//		String name, description, UPC, UNSPSC, linkToProduct, prices, height, width, length, weight;
		// Expected
		expectedUPC = "045496901738";
		// Actual
		sca = new MFC_SourceCodeAnalyzer(getDocFromLocalFileName(localFileName));
		product = sca.call();
		actualUPC = product.getUPC();
		// Test
		assertEquals(expectedUPC, actualUPC);
	}

	// Test by product UPC
	@Test
	public void CallAndReturnProduct_TestWalmartSuperMarioWii_SuperMarioDescription()
			throws Exception {
		// Test Variables
		String expectedDescription, actualDescription;
		MF_Product product;
		MFC_SourceCodeAnalyzer sca;
		String localFileName = "TestWalmartSuperMarioBrosWii.html";
		//		String name, description, UPC, UNSPSC, linkToProduct, prices, height, width, length, weight;
		// Expected
		expectedDescription = "A classic 2D adventure, the game follows the style of New Super Mario Bros. for Nintendo DS. Princess Peach has been kidnapped by Bowser and its up to Mario to save her, but this time hes not alone! Up to four players can join in the fun at the same time, playing as Mario, Luigi or either one of two Toads. Jump, swim and fly through eight classically-themed worlds, with even more challenges awaiting the best players. New power-ups such as the Propeller Suit and Penguin Suit grant unique abilities to traverse levels and combat Koopas, and the return of Yoshi guarentees an enemy-gobbling good time for all. New Super Mario Bros. Wii is also the first Nintendo game to feature a dynamic help system, which allows you to access a mode which allows you to watch how a level can be completed if you are stuck.";
		// Actual
		sca = new MFC_SourceCodeAnalyzer(getDocFromLocalFileName(localFileName));
		product = sca.call();
		actualDescription = product.getDescription();
		// Test
		assertEquals(expectedDescription, actualDescription);
	}

	/*
	 * Simplify the code above with frequent HTML Doc creations via this method
	 */
	private Document getDocFromLocalFileName(String fileName) {
		String localFilePath = loader.getResource(htmlResourceFolder + fileName).getPath();
		File testFile = new File(localFilePath); // temp file to pass to JSoup parser
		Document htmlDoc = null;
		try {
			htmlDoc = Jsoup.parse(testFile, "UTF-8");
		}
		catch (IOException e) {
			System.err.println("getDoc failure in SCA test.");
		}
		return htmlDoc;
	}
}
