package com.novel.service;

import java.io.IOException;
import java.util.HashSet;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.novel.beans.Section;

public class GetDataServiceImpl implements GetDataService {
	// 首页
	public static String rootUrl = "http://www.biquge.com.tw";

	// 已解析url
	public static HashSet<String> downloadedUrlStrings = new HashSet<String>();

	// 待解析url
	public static BlockingQueue<String> waitForParseUrl = new LinkedBlockingQueue<String>();

	// 当前解析地址
	public static String currentUrl = "http://www.biquge.com.tw/4_4642/";
	public static String defaultUrl = "http://www.biquge.com.tw/4_4642/";

	public static void parseUrl(String srcUrl) throws IOException {
		if (null == srcUrl || "".equals(srcUrl)) {
			srcUrl = defaultUrl;
		}
		// 从 URL 直接加载 HTML 文档
		Document doc = Jsoup.connect(srcUrl)
				.userAgent(
						"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.75 Safari/537.36")
				.get();
		String body = doc.body().toString();
		// System.out.println(body);
		parsePages(doc);
	}

	public static void parsePages(Document doc) {
		Elements elements = doc.getElementsByTag("dd");
		for (Element e : elements) {
			String url = e.child(0).attr("href");
			// 待解析页面url
			url = new StringBuilder(rootUrl).append(url).toString();
			try {
				waitForParseUrl.put(url);
			} catch (InterruptedException e1) {
				System.out.println("线程阻塞：" + e1.getMessage());
			}
		}
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				if (waitForParseUrl == null) {
					return;
				}
				while (waitForParseUrl.size() == 0)
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				while (waitForParseUrl.size() > 0) {
					try {
						// 章节内容地址
						String contentPageUrl = waitForParseUrl.take();
						Document doc = Jsoup.connect(contentPageUrl)
								.userAgent(
										"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.75 Safari/537.36")
								.get();

						Section section = new Section();
						section.setSectionurl(contentPageUrl);
						section.setSectionname(doc.getElementsByClass("bookname").first().child(0).html());
						section.setContent(doc.getElementById("content").html());
						System.out.println(section.toString());
					} catch (InterruptedException e) {
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			}
		});
		t1.start();
	}

	public static void main(String[] args) throws IOException {
		parseUrl(null);
	}

}
