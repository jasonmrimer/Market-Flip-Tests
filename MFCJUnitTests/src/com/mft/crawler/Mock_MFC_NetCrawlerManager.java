/**
 * 
 */
package com.mft.crawler;

import java.util.concurrent.BlockingQueue;

import org.jsoup.nodes.Document;

import com.mfc.netcrawler.MFC_NetCrawlerManager;


/**
 * @author Atlas
 *
 */
public class Mock_MFC_NetCrawlerManager extends MFC_NetCrawlerManager {

	/**
	 * The purpose of this constructor is to
	 *
	 * @param bqJSoupDoc
	 */
	public Mock_MFC_NetCrawlerManager(BlockingQueue<Document> bqJSoupDoc) {
		super(bqJSoupDoc);
		// TODO Auto-generated constructor stub
	}

	/**
	 * The purpose of this constructor is to
	 *
	 * @param bqMFJSoupDoc
	 * @param startPath
	 */
	public Mock_MFC_NetCrawlerManager(BlockingQueue<Document> bqMFJSoupDoc, String startPath) {
		super(bqMFJSoupDoc, startPath);
		// TODO Auto-generated constructor stub
	}
	
}
