package com.marketflip.tests.crawler;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.marketflip.crawler.dbcrawler.MFC_DataPlatform;
import com.marketflip.shared.products.MF_Price;
import com.marketflip.shared.products.MF_Product;


/**
 * Holds all tests for MFC_DataPlatform. To be moved to marketflip-tests project.
 * @author David Walters
 * Last updated 12/27/2015
 *
 */
public class JUT_MFC_DataPlatformTests {
	
	private MFC_DataPlatform platform;
	private ExecutorService executor = Executors.newFixedThreadPool(1);
	Future<Boolean> future;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
		
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		
		platform = new MFC_DataPlatform("testing");
	}

	@After
	public void tearDown() throws Exception {
		platform.setOperation("delete");
		platform.call();
		platform.close();
	}

	@Test
	public void Constructor_SendingTesting_ExpectTestingEnvironment() {
		assertTrue(platform.getEnvironment().equals("testing"));
	}
	
	@Test
	public void Constructor_SendingInvalidEnvironment_ExpectTestingEnvironment() throws Exception {
		platform.close();
		platform = new MFC_DataPlatform("sadfsadfsdw");
		assertTrue(platform.getEnvironment().equals("testing"));
	}
	
	@Test
	public void Insert_SendingInvalidProduct_ExpectFalse () throws Exception {
		MF_Product product = new MF_Product("444444234", new ArrayList<MF_Price>());
		
		platform.setOperation("insert");
		platform.setProduct(product);
		assertFalse(platform.call());
	}
	
	@Test
	public void Insert_SendingValidProduct_ExpectTrue() throws Exception {
		
		ArrayList<MF_Price> priceList = new ArrayList<MF_Price>();
		priceList.add(new MF_Price(56.33, "Walmart"));
		MF_Product product = new MF_Product("0777733360571", priceList);
		
		platform.setOperation("insert");
		platform.setProduct(product);
		future = executor.submit(platform);
		assertTrue(future.get().booleanValue());
	}
	
	@Test
	public void Delete_SendingNotInDatabaseProduct_ExpectFalse() throws Exception {
		ArrayList<MF_Price> priceList = new ArrayList<MF_Price>();
		priceList.add(new MF_Price(45.66, "Toys R Us"));
		MF_Product product = new MF_Product ("0097201013656", priceList);
		platform.setOperation("delete");
		platform.setProduct(product);
		assertFalse(platform.call());
	}
}
