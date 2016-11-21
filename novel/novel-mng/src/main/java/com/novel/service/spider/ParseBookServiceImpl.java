package com.novel.service.spider;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.novel.beans.Book;
import com.novel.dao.BookMapper;
import com.novel.utils.BiqugeConstants;

//解析书名
@Service
public class ParseBookServiceImpl implements ParseBookService{

	//默认解析页面为   ：全本
	private static final String DEFAULT_BOOK_TYPE_PAGE = BiqugeConstants.FINISHED_PAGE;
	
	//待添加列表...
	private static LinkedBlockingQueue<Book> booksReadyForAdd = new LinkedBlockingQueue<Book>();
	
	//已添加书目id
	private static Set<Integer> booksIdAlreadyAdd = new HashSet<Integer>();
	
	@Autowired
	private BookMapper bookDao;

	/**
	 * 解析当前页面的书名
	 * @param url
	 */
	public void parseBooks(String url){
		//当前解析地址为空，默认解析全本
		if(url == null || "".equals(url.trim())){
			url = DEFAULT_BOOK_TYPE_PAGE;
		}
		Document doc = getDoc(url);
		System.out.println(doc);
		
		//所有书
		Elements elements = doc.getElementById("content").getElementsByTag("li");
		for(Element currentBook : elements){
			//作者
			String author = currentBook.getElementsByClass("s5").html();
			//a biaoqian
			Element tmp = currentBook.getElementsByClass("s2").get(0).getElementsByTag("a").get(0);
			//url
			String bookUrl = tmp.attr("href");
			//id
			String bookId = getId(bookUrl);
			//book name
			String bookName = tmp.html().replaceAll("《", "").replaceAll("》", "");
			//book type
			String bookType = "";
			Book book = new Book();
			book.setId(Integer.parseInt(bookId));
			book.setBookurl(bookUrl);
			book.setAuthor(author);
			book.setBooktype(bookType);
			book.setBookname(bookName);
			try {
				booksReadyForAdd.put(book);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		while(booksReadyForAdd.size() != 0 ){
			Book book = null;
			try {
				//插入数据库，已添加则不重复
				book = booksReadyForAdd.take();
				if(!booksIdAlreadyAdd.contains(book.getId())){
					bookDao.insert(book);
					booksIdAlreadyAdd.add(book.getId());
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		

	}
	
	//获取页面文档
	public Document getDoc(String url){
		// 从 URL 直接加载 HTML 文档
		try {
			Document doc = Jsoup.connect(url)
					.userAgent(
							"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.75 Safari/537.36")
					.get();
			return doc;
		} catch (IOException e) {
			System.out.println("连接地址:"+url+"失败！"+e.getMessage());
			e.printStackTrace();
		} 
		return null;
	}
	
	//截取id
	public static String getId(String url) {
		//url切割测试:http://www.biquge.com.tw/15_15652/
		String id = url.substring(url.lastIndexOf("_")+1,url.lastIndexOf("/"));
		return id;
	}
	
	public static void main(String[] args) {
		new ParseBookServiceImpl().parseBooks(null);
	}
	
}
