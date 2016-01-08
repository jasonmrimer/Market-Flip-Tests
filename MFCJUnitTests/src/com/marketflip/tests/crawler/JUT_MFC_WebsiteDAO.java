package com.marketflip.tests.crawler;

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

import com.marketflip.crawler.netcrawler.MFC_NetCrawler;
import com.marketflip.crawler.netcrawler.MFC_NetCrawlerManager;
import com.marketflip.crawler.netcrawler.MFC_WebsiteDAO;
import com.marketflip.crawler.scanalyzer.MFC_SourceCodeAnalyzerManager;
import com.sun.org.apache.bcel.internal.generic.NEW;

/**
 * The purpose of this test case is to run unit testing for the creation of the WebsiteDAO without
 * connecting to any external resources.
 *
 * @author highball
 *
 */
public class JUT_MFC_WebsiteDAO {

	static MFC_WebsiteDAO	db;
	private String			testURL	= "www.here.com";

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		// Create & test database connection
		db = new MFC_WebsiteDAO();
		assertFalse("DB not connected", db.con.isClosed()); //ensure connection is built and opened
	}

	@Test
	public void testNumberOfSites() throws SQLException {
		int count = 0;
		ResultSet rs = db.getResultSet("SELECT * FROM Websites;");
		while (rs.next()) {
			count++;
		}
		rs.close();
		System.out.println("Total websites: " + count);
		//		assertTrue("ResultSet not retrieved for ", db.isRecorded("http://www.jsoup.org"));
	}
}
