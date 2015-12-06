import static org.junit.Assert.*;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.apple.jobjc.Utils.Strings;

import mfc_analyzer.MFC_SourceCodeAnalyzer;
import mfc_analyzer.MFC_SourceCodeAnalyzerManager;
import mfc_netcrawler.MFC_NetCrawler;
import mfc_netcrawler.MFC_NetCrawlerManager;

public class JUT_MFC_SourceCodeAnalyzer {
	static	MFC_SourceCodeAnalyzer	analyzer;
	static	Document				siteDoc;
	static	String					icecatURL			=	"http://icecat.us/us/p/warner-bros/1000486894/video-games-0883929410507-Warner-Bros-Batman-Arkham-Knight-PC-22141896.html";
	static	String					walmartURL			=	"http://www.walmart.com/ip/New-Super-Mario-Bros.-Wii/11991871";
	private static BlockingQueue<Document>	bqMFSourceCode	=	new ArrayBlockingQueue<Document>(
			MFC_SourceCodeAnalyzerManager.MFC_MAX_ANALYZER_QUEUE_COUNT);
	private static MFC_NetCrawlerManager	netCrawlerManager;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		// set up netcrawlemanager
		netCrawlerManager = new MFC_NetCrawlerManager(bqMFSourceCode);
		assertTrue("Class not created", netCrawlerManager != null);
		assertTrue("Crawler not created", netCrawlerManager.getNetCrawler() != null);
		assertFalse("DB not connected", netCrawlerManager.getDatabase().con.isClosed());	//ensure connection is built and opened
		// set up netcrawler
		siteDoc = Jsoup.connect(walmartURL).get();
		analyzer = new MFC_SourceCodeAnalyzer(siteDoc);
		assertTrue("Class not created.", analyzer != null);
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

//	@Test
//	public void testCreateClass() {
//		siteDoc = Jsoup.connect(walmartURL).get();
//		analyzer = new MFC_SourceCodeAnalyzer(siteDoc);
//		assertTrue("Class not created.", analyzer != null);
//	}
	@Test
	public void testSiteDoc() {
		assertTrue("Doc is null.", siteDoc != null);
	}

	@Test
	public void testMeta() {
		Elements meta = siteDoc.select("meta[property=og:upc");
		for (Element element : meta) {
			System.out.println("meta attr: " + element.attr("content"));
		}
		String UPC = siteDoc.select("meta[property=og:upc").attr("content");
		String title = siteDoc.select("meta[property=og:title").attr("content");
		Elements disclaimer = siteDoc.select("div.about-item-preview-text");
		String description = disclaimer.html();
		System.out.println(UPC + "\n" + title + "\n" + description);
	}
	
//	@Test
//	public void testElements() {
//		for (Element element : siteDoc.getAllElements()) {
//			if (!element.id().isEmpty()) {
//				System.out.println(element.id());
//				for (Element child : element.children()) {
//					if (!child.id().isEmpty()) {
//						System.out.println("child:" + child.id());
//					}
//				}
//			}
//		}
//	}

}
