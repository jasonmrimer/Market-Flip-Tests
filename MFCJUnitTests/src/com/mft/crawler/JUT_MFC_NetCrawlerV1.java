package com.mft.crawler;
import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.mfc.netcrawler.MFC_NetCrawler;

public class JUT_MFC_NetCrawlerV1 {
	static MFC_NetCrawler	netCrawler;
	static String			startURL	=	"http://jsoup.org";
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		netCrawler = new MFC_NetCrawler(startURL);
		assertTrue("Class not created", netCrawler != null);
		netCrawler.runJSoup();
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

	@Test
	public void testURLCreation() {
		// Test NetCrawler
		ArrayList<String> URLs = new ArrayList<String>();
		URLs.addAll(netCrawler.getURLs());
		assertFalse("ArrayList empty", URLs.isEmpty());
		for (String URL : URLs) {
			System.out.println(URL);
		}
	}
	
//	@Test
//	public void testDBConnection() throws SQLException {
//		assertFalse("DB not connected", netCrawler.getDatabase().con.isClosed());	//ensure connection is built and opened
//	}
}
