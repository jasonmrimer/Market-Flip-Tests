package com.marketflip.tests.sharedassets;

import static org.junit.Assert.*;
import java.util.Date;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import com.marketflip.shared.products.MF_Price;
import com.marketflip.shared.shopper.MF_PricePoint;
import com.marketflip.shared.products.MF_Product;
import com.marketflip.shared.shopper.MF_Shopper;

/**
 * This tests methods in the MF_Shopper class
 * @author Tiffany Mathers
 * Updated: 12/28/2015
 *
 */

public class JUT_MF_Shopper {

	@Test
	public void TestAddPricePointKey_SendProductDellLaptop_ExpectDellLaptop() {
		MF_Shopper jSmith = new MF_Shopper("JSmith", "jsmith@gmail.com");
		MF_PricePoint pricepoint = new MF_PricePoint ("0008888692522", 100.00);
		MF_Price price = new MF_Price(100.00, new Date());
		MF_Product dellLaptop = new MF_Product("0008888692522", price);
		
		jSmith.addPricePoint(dellLaptop, pricepoint);
		
		assertTrue(jSmith.getPricePointMap().containsKey(dellLaptop));
	}

	@Test
	public void TestAddPricePointValue_SendPricePoint100_ExpectPricePoint100() {
		MF_Shopper jSmith = new MF_Shopper("JSmith", "jsmith@gmail.com");
		MF_PricePoint pricepoint = new MF_PricePoint ("0008888692522", 100.00);
		MF_Price price = new MF_Price(100.00, new Date());
		MF_Product dellLaptop = new MF_Product("0008888692522", price);
		
		jSmith.addPricePoint(dellLaptop, pricepoint);
		
		assertTrue(jSmith.getPricePointMap().containsValue(pricepoint));
	}

	@Test
	public void TestEquals_SendingEqualShopper_ExpectTrue() {
		MF_Shopper jSmith = new MF_Shopper("JSmith", "jsmith@gmail.com");
		MF_Shopper alsoJSmith = new MF_Shopper("JSmith", "jsmith@gmail.com");
		
		assertTrue(jSmith.equals(alsoJSmith));
	}
	
	@Test
	public void TestEquals_SendingUnequalShopper_ExpectFalse() {
		MF_Shopper jSmith = new MF_Shopper("JSmith", "jsmith@gmail.com");
		MF_Shopper notJSmith = new MF_Shopper("JDoe", "jdoe@gmail.com");
		
		assertFalse(jSmith.equals(notJSmith));
	}
	
}
