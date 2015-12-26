package com.marketflip.tests.crawler;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({JIT_MFC_NetCralwer.class, JUT_HTML.class, JUT_MFC_NetCrawler.class,
		JUT_MFC_NetCrawlerManager.class, JUT_MFC_SourceCodeAnalyzer.class, JUT_MFC_TempDB.class})
public class JTS_AllCrawler {

}
