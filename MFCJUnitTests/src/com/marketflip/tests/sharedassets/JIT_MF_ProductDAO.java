package com.marketflip.tests.sharedassets;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.marketflip.shared.data.MF_ProductsDAO;
import com.marketflip.shared.products.MF_Price;
import com.marketflip.shared.products.MF_Product;

/**
 * The purpose of this test case is to test methods for the Production Data Access Object while
 * actually connected to the database. It is an integration test that will assure all insertions,
 * deletions, and connections un as desired without error.
 *
 * @author highball
 *
 */
public class JIT_MF_ProductDAO {

	/**
	 * The purpose of this test is to ensure the insertProduct method works when sending it a
	 * product with a single price in its array list. It should receive the entire array list and
	 * that should contain the exact price.
	 *
	 * @throws Exception
	 */
	@Test
	public void InsertProduct_ProductWith1Price_RetrievedPrice() throws Exception {
		// Test Variables
		double expectedPrice, actualPrice;
		MF_ProductsDAO productDAO;
		MF_Price price;
		MF_Product product, retrievedProduct;
		// fill product db
		productDAO = new MF_ProductsDAO("testing");
		price = new MF_Price(30.00, new java.util.Date());
		product = new MF_Product("045496901738", price);
		productDAO.addProductToCommit(product);
		productDAO.commitProductsToDatabase();
		// Expected
		expectedPrice = 30.00;
		// Actual
		retrievedProduct = productDAO.getProduct("045496901738");
		if (retrievedProduct.getPrices().size() > 0) {
			actualPrice = retrievedProduct.getPrices().get(0).getPrice();
		}
		else {
			actualPrice = 0.00;
		}
		// Test
		assertTrue(expectedPrice == actualPrice);
	}
}
