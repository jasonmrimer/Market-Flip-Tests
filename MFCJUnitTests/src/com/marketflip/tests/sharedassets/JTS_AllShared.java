package com.marketflip.tests.sharedassets;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;


@RunWith(Suite.class)
@SuiteClasses({JIT_MF_DataAccessObjectTest.class, JIT_MF_InsertAndGetDAOTest.class, JUT_MF_PriceTest.class,
		JUT_MF_ProductTest.class, JUT_MF_ProductValidatorTest.class})
public class JTS_AllShared {

}
