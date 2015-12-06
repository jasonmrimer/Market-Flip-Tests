import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.marketflip.shared.data.MF_DatabaseAccessObject;
import com.marketflip.shared.products.MF_Product;

public class JUT_MF_DAO {
	static MF_DatabaseAccessObject database;
	static MF_Product product;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		database = MF_DatabaseAccessObject.newInstance();
		product = new MF_Product("walmart", "just dance", "a video game", "008888178224");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		database.close();
	}

	@Before
	public void setUp() throws Exception {
		assertTrue("Connection closed.", !database.getConnection().isClosed());
	}

	@After
	public void tearDown() throws Exception {
	}

//	@Test
//	public void testInsert() throws SQLException {
//		assertTrue("Product not inserted.", database.insertProduct(product));
//	}
	
	@Test
	public void testSelection() throws SQLException {
//		database.insertProduct(product); 045496901738
		MF_Product tempProduct = database.getProductByUPC("045496901738");
		assertTrue("Product not found.", tempProduct.compareTo(product) == 1);
		System.out.println("Found in DB: \nName: " + tempProduct.getName() + "\nDescription: " + tempProduct.getDescription());
	}

}
