package com.marketflip.tests.sharedassets;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.protos.cloud.sql.Client.TupleProto;
import com.marketflip.shared.shopper.MF_PricePoint;
import com.marketflip.shared.shopper.MF_ShopperDAO;

public class JIT_MF_ShopperDAO {

	static MF_ShopperDAO shopperDAO;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		shopperDAO = new MF_ShopperDAO(false);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		shopperDAO.finalize();
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	/**
	 * The purose of this test is to ensure a rollback function, ClearAllTables, successful delets
	 * all tables from the database.
	 *
	 * @throws Exception
	 */
	@Test
	public void DeleteAllTables_VoidMethod_TableArraySize0() throws Exception {
		// Test Variables
		int expectedSize, actualSize;
		ArrayList<String> tableNames;
		// Expected
		expectedSize = 0;
		// Actual
		shopperDAO.deleteAllTables();
		shopperDAO.populateTableNameArrayList();
		tableNames = shopperDAO.getTableNameArrayList();
		actualSize = tableNames.size();
		// Test
		assertEquals(expectedSize, actualSize);
	}

	/**
	 * The purose of this test is to create the standard Shopper table then see if it exists byt
	 * clearing all tables, creating Shopper table, testing, and finally clearing all tables.
	 *
	 * @throws Exception
	 */
	@Test
	public void CreateShopperTable_StandardCreation_TableExistsInDB() throws Exception {
		// Test Variables
		ArrayList<String> expectedTableNameArrayList, actualTableNameArrayList;
		// Expected
		expectedTableNameArrayList = new ArrayList<String>();
		expectedTableNameArrayList.add("Shoppers");
		// Actual
		shopperDAO.deleteAllTables();
		shopperDAO.createShopperTable();
		shopperDAO.populateTableNameArrayList();
		actualTableNameArrayList = shopperDAO.getTableNameArrayList();
		// Test
		assertEquals(expectedTableNameArrayList, actualTableNameArrayList);
		// Rollback
		shopperDAO.deleteAllTables();
	}

	/**
	 * The purose of this test is specifically to test whether the Shopper's price point initiates a
	 * Price Point table creation - specific rows of the tables tested via separate tests.
	 *
	 * @throws Exception
	 */
	@Test
	public void CreatePricePointTable_ShopperKarlSilkroadAndSuperMario_ShopperTableWithPricePointTableAndReference()
			throws Exception {
		// Test Variables
		ArrayList<String> expectedTableNameArrayList, actualTableNameArrayList;
		String emailBeforeHash, emailAfterHash;
		// Expected
		emailBeforeHash = "karlsilkroad@dnd.com";
		emailAfterHash = DigestUtils.md5Hex(emailBeforeHash);
		expectedTableNameArrayList = new ArrayList<String>();
		expectedTableNameArrayList.add("Shoppers"); // ,"PPT_" + emailAfterHash
		expectedTableNameArrayList.add("PPT_" + emailAfterHash);
		// Actual
		shopperDAO.deleteAllTables();
		shopperDAO.createShopperTable();
		shopperDAO.addShopper(emailBeforeHash);
		shopperDAO.populateTableNameArrayList();
		actualTableNameArrayList = shopperDAO.getTableNameArrayList();
		// Test
		Collections.sort(expectedTableNameArrayList);
		Collections.sort(actualTableNameArrayList);
		assertEquals(expectedTableNameArrayList, actualTableNameArrayList);
		// Rollback
		shopperDAO.deleteAllTables();
	}

	/**
	 * The purose of this test is to send a single price point for a shopper and test whether it
	 * successfully inserted to the records.
	 *
	 * @throws Exception
	 */
	@Test
	public void AddPricePoint_KarlSilkroadSuperMario10Dollars_MatchingRecordInPricePointTable()
			throws Exception {
		// Test Variables
		ArrayList<MF_PricePoint> expectedPricePoint, actualPricePoint;
		String emailBeforeHash, productUPC;
		Double price;
		MF_PricePoint pricePoint;
		// Expected
		emailBeforeHash = "karlsilkroad@dnd.com";
		productUPC = "045496901738"; // actual Super Mario Bros Wii UPC
		price = 10.00;
		pricePoint = new MF_PricePoint(productUPC, price);
		expectedPricePoint = new ArrayList<MF_PricePoint>();
		expectedPricePoint.add(pricePoint);
		// Actual
		shopperDAO.deleteAllTables();
		shopperDAO.createShopperTable();
		shopperDAO.addShopper(emailBeforeHash);
		shopperDAO.addPricePoint(emailBeforeHash, productUPC, price);
		actualPricePoint = shopperDAO.getPricePointArrayList(emailBeforeHash);
		// Test
		assertEquals(expectedPricePoint, actualPricePoint);
		// Rollback
		shopperDAO.deleteAllTables();
	}

	/**
	 * The purose of this test is to send 3 price points for a shopper and test whether all
	 * successfully inserted to the records. It tests the arraylists of pricepoints
	 *
	 * @throws Exception
	 */
	@Test
	public void AddPricePoint_KarlSilkroad3PricePoints_MatchingRecordsInPricePointTable()
			throws Exception {
		// Test Variables
		ArrayList<MF_PricePoint> expectedPricePoint, actualPricePoint;
		String emailBeforeHash, product1UPC, product2UPC, product3UPC;
		Double price1, price2, price3;
		MF_PricePoint pricePoint1, pricePoint2, pricePoint3;
		// Expected
		emailBeforeHash = "karlsilkroad@dnd.com";
		product1UPC = "045496901738"; // actual Super Mario Bros Wii UPC
		product2UPC = "045496901739"; // fake
		product3UPC = "045496901740"; // fake
		price1 = 10.00;
		price2 = 11.00;
		price3 = 12.00;
		pricePoint1 = new MF_PricePoint(product1UPC, price1);
		pricePoint2 = new MF_PricePoint(product2UPC, price2);
		pricePoint3 = new MF_PricePoint(product3UPC, price3);
		expectedPricePoint = new ArrayList<MF_PricePoint>();
		expectedPricePoint.add(pricePoint1);
		expectedPricePoint.add(pricePoint2);
		expectedPricePoint.add(pricePoint3);
		// Actual
		shopperDAO.deleteAllTables();
		shopperDAO.createShopperTable();
		shopperDAO.addShopper(emailBeforeHash);
		shopperDAO.addPricePoint(emailBeforeHash, product1UPC, price1);
		shopperDAO.addPricePoint(emailBeforeHash, product2UPC, price2);
		shopperDAO.addPricePoint(emailBeforeHash, product3UPC, price3);
		actualPricePoint = shopperDAO.getPricePointArrayList(emailBeforeHash);
		// Test
		Collections.sort(expectedPricePoint);
		Collections.sort(actualPricePoint);
		assertEquals(expectedPricePoint, actualPricePoint);
		// Rollback
		shopperDAO.deleteAllTables();
	}
}
