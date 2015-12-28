package com.marketflip.tests.application;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.marketflip.application.shopper.MFA_ShopperManager;
import com.marketflip.shared.shopper.MF_PricePoint;

public class JUT_MFA_ShopperManager {

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
	 * The purpose of this test is to start the creation of a MFA_ShopperManager with a first
	 * failing
	 * test. It is a simple constructor test.
	 *
	 * @throws Exception
	 */
	@Test
	public void Construct_NoArgs_ProperToString() throws Exception {
		// Test Variables
		String expectedToString, actualToString;
		MFA_ShopperManager shopperManager;
		// Expected
		expectedToString = "Instance of Market Flip Application Shopping Manager without parameters.";
		// Actual
		shopperManager = new MFA_ShopperManager();
		actualToString = shopperManager.toString();
		// Test
		assertEquals(expectedToString, actualToString);
	}

	/**
	 * The purpose of this test is to construct a manager with a blocking queue (that will
	 * eventually
	 * be some type of ternary with an array of products and offers to send to the shopper notifier)
	 *
	 * @throws Exception
	 */
	@Test
	public void Construct_BlockingQueueSize3_ToStringWithSize3() throws Exception {
		// Test Variables
		String expectedToString, actualToString;
		BlockingQueue bq;
		MFA_ShopperManager shopperManager;
		// Expected
		expectedToString = "Instance of Market Flip Application Shopping Manager instantiated with blocking queue size: 3.";
		// Actual
		bq = new ArrayBlockingQueue<>(3);
		shopperManager = new MFA_ShopperManager(bq);
		actualToString = shopperManager.toString();
		// Test
		assertEquals(expectedToString, actualToString);
	}

	/**
	 * The purpose of this test is to ensure the manager can run a single future then stop after
	 * completing that task.
	 *
	 * @throws Exception
	 */
	@Test
	public void Run_SingleFutureToCompletion_EndAfter1Future() throws Exception {
		// Test Variables
		int expectedFutureCount, actualFutureCount;
		MFA_ShopperManager shopperManager;
		BlockingQueue bq;
		// Expected
		expectedFutureCount = 1;
		// Actual
		bq = new ArrayBlockingQueue<>(3);
		shopperManager = new MFA_ShopperManager(bq, expectedFutureCount, true);
		Thread smThread = new Thread(shopperManager);
		smThread.run();
		while (smThread.isAlive()) {
		}
		actualFutureCount = shopperManager.getCompletedFutureCount();
		// Test
		assertEquals(expectedFutureCount, actualFutureCount);
	}

	/**
	 * The purpose of this method is to ensure it can run 3 futures then stop.
	 *
	 * @throws Exception
	 */
	@Test
	public void Run_3FuturesToCompletion_EndAfter3Futures() throws Exception {
		// Test Variables
		int expectedFutureCount, actualFutureCount;
		MFA_ShopperManager shopperManager;
		BlockingQueue bq;
		// Expected
		expectedFutureCount = 3;
		// Actual
		bq = new ArrayBlockingQueue<>(3);
		shopperManager = new MFA_ShopperManager(bq, expectedFutureCount, true);
		Thread smThread = new Thread(shopperManager);
		smThread.start();
		while (smThread.isAlive()) {
		}
		actualFutureCount = shopperManager.getCompletedFutureCount();
		// Test
		assertEquals(expectedFutureCount, actualFutureCount);
	}

	/**
	 * The purpose of this test is to ensure the setup for a mock object works, esp. regarding the
	 * creation of an arrayList of fake shoppers.
	 *
	 * @throws Exception
	 */
	@Test
	public void Construct_MockTrueAndFutureLimit1_1ShopperInArray() throws Exception { // TODO change to shopper class 
		// Test Variables
		ArrayList<String> expectedArrayList = new ArrayList<String>(),
				actualArrayList = new ArrayList<String>();
		MFA_ShopperManager shopperManager;
		BlockingQueue bq;
		int futureLimit;
		// Expected
		expectedArrayList.add("shopper0");
		// Actual
		futureLimit = 1;
		bq = new ArrayBlockingQueue<>(3);
		shopperManager = new MFA_ShopperManager(bq, futureLimit, true);
		actualArrayList = shopperManager.getArrayListOfShoppers();
		// Test
		assertEquals(expectedArrayList, actualArrayList);
	}

	/**
	 * The purpose of this test is to check whether all the futures make it into the blocking queue
	 * and the clean up loop (after futures finish but before shutdown) successfully handles all
	 * completed futures to the blocking queue.
	 *
	 * @throws Exception
	 */
	@Test
	public void Run_Futures3AndBlockingSize3_CompletedBQAdditionsEquals3() throws Exception {
		// Test Variables
		int expectedBQCount, actualBQCount;
		MFA_ShopperManager shopperManager;
		Thread smThread;
		BlockingQueue bq;
		int shopperLimit;
		// Expected
		expectedBQCount = 3;
		// Actual
		shopperLimit = 3;
		bq = new ArrayBlockingQueue<>(3);
		shopperManager = new MFA_ShopperManager(bq, shopperLimit, true);
		smThread = new Thread(shopperManager);
		smThread.start();
		while (smThread.isAlive()) {
		}
		actualBQCount = shopperManager.getCompletedBlockingQueueAdditions();
		// Test
		assertEquals(expectedBQCount, actualBQCount);
	}
}
