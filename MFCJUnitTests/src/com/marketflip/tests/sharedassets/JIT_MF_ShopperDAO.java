package com.marketflip.tests.sharedassets;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Properties;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.marketflip.shared.products.MF_Product;
import com.marketflip.shared.shopper.MF_PricePoint;
import com.marketflip.shared.shopper.MF_Shopper;
import com.marketflip.shared.shopper.MF_ShopperDAO;

/**
 * The purpose of this test case is to run integration testing for the Shopper Data Access Object by
 * running several methods such as insertions, deletions, and connections to determine whether the
 * class proper connects and manipulates the database.
 *
 * @author highball
 *
 */
public class JIT_MF_ShopperDAO {

	static MF_ShopperDAO shopperDAO;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		shopperDAO = new MF_ShopperDAO(false);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		shopperDAO.close();
	}

	/**
	 * The purpose of this test is to ensure a rollback function, ClearAllTables, successful delets
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
		//		shopperDAO.populateTableNameArrayList();
		tableNames = shopperDAO.getTableNameArrayList();
		actualSize = tableNames.size();
		// Test
		assertEquals(expectedSize, actualSize);
		// Rollabck
		shopperDAO.deleteAllTables();
	}

	/**
	 * The purpose of this test is to create the standard Shopper table then see if it exists byt
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
		shopperDAO.createShoppersTable();
		shopperDAO.populateTableNameArrayList();
		actualTableNameArrayList = shopperDAO.getTableNameArrayList();
		// Test
		assertEquals(expectedTableNameArrayList, actualTableNameArrayList);
		// Rollback
		shopperDAO.deleteAllTables();
	}

	/**
	 * The purpose of this test is specifically to test whether the Shopper's price point initiates
	 * a
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
		shopperDAO.createShoppersTable();
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
	 * The purpose of this test is to send a single price point for a shopper and test whether it
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
		shopperDAO.createShoppersTable();
		shopperDAO.addShopper(emailBeforeHash);
		shopperDAO.addPricePoint(emailBeforeHash, productUPC, price);
		actualPricePoint = shopperDAO.getArrayListOfPricePoints(emailBeforeHash);
		// Test
		assertEquals(expectedPricePoint, actualPricePoint);
		// Rollback
		shopperDAO.deleteAllTables();
	}

	/**
	 * The purpose of this test is to send 3 price points for a shopper and test whether all
	 * successfully inserted to the records. It tests the arraylists of pricepoints
	 *
	 * @throws Exception
	 */
	@Test
	public void AddPricePoint_KarlSilkroad3PricePoints_MatchingRecordsInPricePointTable()
			throws Exception {
		// Test Variables
		ArrayList<MF_PricePoint> expectedPricePoint, actualPricePoint;
		String shopperEmail, product1UPC, product2UPC, product3UPC;
		Double price1, price2, price3;
		MF_PricePoint pricePoint1, pricePoint2, pricePoint3;
		// Expected
		shopperEmail = "karlsilkroad@dnd.com";
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
		shopperDAO.createShoppersTable();
		shopperDAO.addShopper(shopperEmail);
		shopperDAO.addPricePoint(shopperEmail, product1UPC, price1);
		shopperDAO.addPricePoint(shopperEmail, product2UPC, price2);
		shopperDAO.addPricePoint(shopperEmail, product3UPC, price3);
		actualPricePoint = shopperDAO.getArrayListOfPricePoints(shopperEmail);
		// Test
		Collections.sort(expectedPricePoint);
		Collections.sort(actualPricePoint);
		assertEquals(expectedPricePoint, actualPricePoint);
		// Rollback
		shopperDAO.deleteAllTables();
	}

	/**
	 * The purpose of this test is to insert a new shopper into the database then ensure that
	 * shopper is indeed in the databse by fetching the shopper from the database.
	 *
	 * @throws Exception
	 */
	@Test
	public void AddShopper_AcceptableShopper_ReturnShopperFromDB() throws Exception {
		// Test Variables
		MF_Shopper expectedShopper, actualShopper;
		String shopperUserName, shopperEmail;
		// Expected
		shopperUserName = "silkroad";
		shopperEmail = "karlsilkroad@gmail.com";
		expectedShopper = new MF_Shopper(shopperUserName, shopperEmail);
		// Actual
		shopperDAO.deleteAllTables();
		shopperDAO.createShoppersTable();
		shopperDAO.addShopper(expectedShopper);
		actualShopper = shopperDAO.getShopper(expectedShopper);
		// Test
		assertTrue(expectedShopper.equals(actualShopper));
		// Rollback
		shopperDAO.deleteAllTables();
	}

	/**
	 * The purpose of this test is to ensure addShopperPricePoint, a method that adds a single price
	 * point to the shopper's price points table, works without flaws by returning the retrieved
	 * price points from the database and comparing the values.
	 *
	 * @throws Exception
	 */
	@Test
	public void AddShopperPricePoint_ShopperWithPricePoint_ReturnPricePointFromDBUsingShopper()
			throws Exception {
		// Test Variables
		MF_PricePoint expectedPricePoint, actualPricePoint;
		ArrayList<MF_PricePoint> retrievedArrayListOfPricePoints;
		MF_Shopper shopper;
		String shopperUserName, shopperEmail;
		// Expected
		expectedPricePoint = new MF_PricePoint("045496901738", 10.00); // actual super mario bros wii UPC
		// TODO determine how/whether to incorporate MF_Product & Price points most effectively
		// Actual
		shopperUserName = "silkroad";
		shopperEmail = "karlsilkroad@gmail.com";
		shopper = new MF_Shopper(shopperUserName, shopperEmail);
		//		give the shopper a price point; when it adds the shopper to the DB it will add the price point, too
		shopper.addPricePoint(expectedPricePoint);
		shopperDAO.deleteAllTables();
		shopperDAO.createShoppersTable();
		//		adding shopper to DB also adds all attached price points
		shopperDAO.addShopper(shopper);
		retrievedArrayListOfPricePoints = shopperDAO.getArrayListOfPricePoints(shopper);
		actualPricePoint = (retrievedArrayListOfPricePoints.size() > 0)
				? retrievedArrayListOfPricePoints.get(0) : null;
		// Test
		assertEquals(expectedPricePoint, actualPricePoint);
		// Rollback
		shopperDAO.deleteAllTables();
	}

}
