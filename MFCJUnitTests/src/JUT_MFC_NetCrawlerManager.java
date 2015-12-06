import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import mfc_analyzer.MFC_SourceCodeAnalyzerManager;

import org.jsoup.nodes.Document;

import mfc_netcrawler.MFC_NetCrawler;
import mfc_netcrawler.MFC_NetCrawlerManager;

public class JUT_MFC_NetCrawlerManager {
	private static BlockingQueue<Document>	bqMFSourceCode	=	new ArrayBlockingQueue<Document>(
														MFC_SourceCodeAnalyzerManager.MFC_MAX_ANALYZER_QUEUE_COUNT);
	private static MFC_NetCrawlerManager	netCrawlerManager;
	private static String					startURL		=	"http://jsoup.org";
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		netCrawlerManager = new MFC_NetCrawlerManager(bqMFSourceCode);
		assertTrue("Class not created", netCrawlerManager != null);
		assertTrue("Crawler not created", netCrawlerManager.getNetCrawler() != null);
		assertFalse("DB not connected", netCrawlerManager.getDatabase().con.isClosed());	//ensure connection is built and opened
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		assertFalse("DB not connected", netCrawlerManager.getDatabase().con.isClosed());	//ensure connection is built and opened
	}

	@After
	public void tearDown() throws Exception {
	}

//	@Test
//	public void testManagerCreated() {
//		assertTrue("Class not created", netCrawlerManager != null);
//	}
//	@Test
//	public void testCrawlerCreated() {
//		assertTrue("Crawler not created", netCrawlerManager.getNetCrawler() != null);
//	}
//	@Test
//	public void testDBConnection() throws SQLException {
//		assertFalse("DB not connected", netCrawlerManager.getDatabase().con.isClosed());	//ensure connection is built and opened
//	}
	
	@Test
	public void testURLArrayCreation() {
		ArrayList<String> URLs = new ArrayList<String>();
		URLs.addAll(netCrawlerManager.getNetCrawler().getURLs());
		assertTrue("ArrayList empty", URLs.isEmpty());
		for (String URL : URLs) {
			System.out.println(URL);
		}
	}
	
}
