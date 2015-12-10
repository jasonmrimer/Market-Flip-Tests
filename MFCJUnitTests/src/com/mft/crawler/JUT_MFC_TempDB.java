package com.mft.crawler;
import static org.junit.Assert.*;

import java.io.IOException;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.mfc.netcrawler.MFC_NetCrawler;
import com.mfc.netcrawler.MFC_NetCrawlerManager;
import com.mfc.netcrawler.MFC_WebsiteDAO;
import com.mfc.scanalyzer.MFC_SourceCodeAnalyzerManager;
import com.sun.org.apache.bcel.internal.generic.NEW;


public class JUT_MFC_TempDB {
	static MFC_WebsiteDAO db;
	private String testURL = "www.here.com";
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		// Create & test database connection
		db = new MFC_WebsiteDAO();
		assertFalse("DB not connected", db.con.isClosed());	//ensure connection is built and opened
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		db.con.close();
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
//	@Test
//	public void testClearDatabase() throws SQLException {
//		db.clearAllTables();
//		DatabaseMetaData dbMetaData = db.con.getMetaData();
//		String[] tables = {"TABLE"};
//		ResultSet rsTables = dbMetaData.getTables(null, null, "%", tables);
//		assertTrue("Tables not cleared.", !rsTables.first());
//	}
	
//	@Test
//	public void testInsertMethod() {
//		db.createWebsitesTable();
//		assertTrue("Insertion failed", db.insertURLToWebsiteTable(testURL));
//	}
	
//	@Test
//	public void testSelection() throws SQLException {
//		db.createWebsitesTable();
//		db.insertURLToWebsiteTable(testURL);
//		assertTrue("Selection failed", db.getURLResultSet(testURL).first());
//	}
	@Test
	public void testNumberOfSites() throws SQLException {
//		db.createWebsitesTable();
//		String URL = "www.marketflip.com"; 
//		db.insertURLToWebsiteTable(testURL);
		int count = 0;
		ResultSet rs = db.getResultSet("SELECT * FROM Websites;");
		while (rs.next()) {
			count++;
		}
		rs.close();
		System.out.println("Total websites: " + count);
//		assertTrue("ResultSet not retrieved for ", db.isRecorded("http://www.jsoup.org"));
	}
	
//	@Test
//	public void testGetURLResultSet() throws SQLException {
//		db.createWebsitesTable();
//		String URL = "www.marketflip.com"; 
//		db.insertURLToWebsiteTable(testURL);
//		assertTrue("ResultSet not retrieved for " + URL, db.isRecorded(testURL));
//	}
}
