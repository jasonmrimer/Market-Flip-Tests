import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.appengine.repackaged.org.apache.commons.codec.digest.DigestUtils;

public class JUT_MFC_Hashing {

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

	@Test
	public void test() {
		String URL1 = "www.here.com";
		String URL2 = "www.here.com";
		String hashedURL1 = DigestUtils.sha256Hex(URL1);
		String hashedURL2 = DigestUtils.sha256Hex(URL2);
		System.out.print(hashedURL1 + "\n" + hashedURL2);
		assertEquals(hashedURL1, hashedURL2);
	}

}
