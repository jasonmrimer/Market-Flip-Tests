package com.marketflip.tests.sharedassets;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.marketflip.shared.shopper.MF_PricePoint;

public class JUT_MF_PricePoint {

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
	 * The purose of this test is to make sure the equals method of the MF_PRicePoint class properly
	 * classifies two identical objects as equal.
	 *
	 * @throws Exception
	 */
	@Test
	public void PricePointEquals_SameConditions_MatchingObjects() throws Exception {
		// Test Variables
		MF_PricePoint expectedPricePoint, actualPricePoint;
		// Expected
		expectedPricePoint = new MF_PricePoint("123ABC", 10.00);
		// Actual
		actualPricePoint = new MF_PricePoint("123ABC", 10.00);
		// Test
		assertEquals(expectedPricePoint, actualPricePoint);
	}

	/**
	 * The purose of this test is to ensure the equal method works on an ArrayList as that
	 * collection is used often throughout the rest of Market Flip.
	 *
	 * @throws Exception
	 */
	@Test
	public void PricePointArrayEquals_SameConditions_MatchingArrayLists() throws Exception {
		// Test Variables
		MF_PricePoint expectedPricePoint, actualPricePoint;
		ArrayList<MF_PricePoint> expectedPPAL, actualPPAL;
		// Expected
		expectedPricePoint = new MF_PricePoint("123ABC", 10.00);
		expectedPPAL = new ArrayList<MF_PricePoint>();
		expectedPPAL.add(expectedPricePoint);
		// Actual
		actualPricePoint = new MF_PricePoint("123ABC", 10.00);
		actualPPAL = new ArrayList<MF_PricePoint>();
		actualPPAL.add(actualPricePoint);
		// Test
		assertEquals(expectedPPAL, actualPPAL);
	}

	/**
	 * The purose of this test is to ensure the comparator properly works by using the
	 * Collection.sort (that harness the compareTo) to match two unsorted arraylists into one - used
	 * throughout Market Flip to test ArrayLists that may be unsorted.
	 *
	 * @throws Exception
	 */
	@Test
	public void Equals_EqualUnsortedArrayLists_MatchingArrayLists() throws Exception {
		// Test Variables
		MF_PricePoint pricePoint1, pricePoint2, pricePoint3;
		ArrayList<MF_PricePoint> expectedPPAL, actualPPAL;
		// Expected
		pricePoint1 = new MF_PricePoint("123ABC", 10.00);
		pricePoint2 = new MF_PricePoint("456DEF", 11.00);
		pricePoint3 = new MF_PricePoint("456DEF", 12.00);
		expectedPPAL = new ArrayList<MF_PricePoint>();
		expectedPPAL.add(pricePoint1);
		expectedPPAL.add(pricePoint2);
		expectedPPAL.add(pricePoint3);
		// Actual
		actualPPAL = new ArrayList<MF_PricePoint>();
		actualPPAL.add(pricePoint3);
		actualPPAL.add(pricePoint1);
		actualPPAL.add(pricePoint2);
		// Test
		Collections.sort(actualPPAL);
		assertEquals(expectedPPAL, actualPPAL);
	}

}
