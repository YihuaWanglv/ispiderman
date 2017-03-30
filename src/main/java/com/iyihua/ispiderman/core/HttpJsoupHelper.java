package com.iyihua.ispiderman.core;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.iyihua.ispiderman.entity.Repository;

/**
 * Jsoup网页抓取帮助类
 * 
 * @author Administrator
 *
 */
public class HttpJsoupHelper {

	private static final Logger LOGGER = LoggerFactory.getLogger(HttpJsoupHelper.class);

	public static List<Repository> loadRepositorysGzsgsww(String module) throws SocketTimeoutException {
		String subject = module.split("_")[0];
		List<Repository> list = Lists.newArrayList();
		try {
			String moduleUrl = StaticConfig.urls.get(module);
			String targetUrl = moduleUrl;
			int total = 33;
			for (int i = 0; i < total; i++) {
				String page = i == 0 ? "" : (i + "");
				targetUrl = targetUrl + "index" + page + ".htm";
				Document doc = Jsoup.connect(targetUrl).get();
				Elements elements = doc.select(".newslist > ul > li > a");
				if (elements != null && elements.size() > 0) {
					for (Element element : elements) {
						String title = "";
						String link = "";
						String date = "";
						Date d = new Date();
						link = element.attr("href");
						if (link.startsWith("http")) {
							continue;
						} else if (link.startsWith(".")) {
							link = link.replaceFirst(".", "");
						}
						title = element.attr("title");
						Element lastEle = element.lastElementSibling();
						if (lastEle != null) {
							date = lastEle.text();
							DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
							if (!date.equals("") && date.indexOf("-") > -1) {
								d = format.parse(date);
							}
						}
						list.add(Repository.builder().title(title).date(d).link(moduleUrl + link).subject(subject)
								.build());
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public static void loadRepositoryGzsgsww(Repository repository) {
		String targetUrl = repository.getLink();
		if (targetUrl != null && !targetUrl.equals("")) {
			try {
				Document doc = Jsoup.connect(targetUrl).get();
				Element e = doc.getElementById("contentpp");
				if (e != null) {
					String content = e.toString();
					repository.setContent(content);
					LOGGER.info(content);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static List<Repository> loadRepositorys(String module) throws SocketTimeoutException {
		String subject = module.split("_")[0];
		List<Repository> list = Lists.newArrayList();
		try {
			String moduleUrl = StaticConfig.urls.get(module);
			String targetUrl = moduleUrl;
			int total = 33;
			for (int i = 0; i < total; i++) {
				String page = i == 0 ? "" : ("_" + i);
				targetUrl = moduleUrl + "index" + page + ".htm";
				Document doc = Jsoup.connect(targetUrl).get();
				Elements elements = doc.select(".content > .gkml_box > table");
				if (elements != null && elements.size() > 0) {
					Element e = elements.get(0);
					Elements es = e.select("tr");
					for (Element element : es) {
						String title = "";
						String link = "";
						String date = "";
						Date d = new Date();
						if (element.hasAttr("onclick")) {
							String urlString = element.attr("onclick");
							Pattern p = Pattern.compile("(/\\d+/\\w+.htm)");
							Matcher m = p.matcher(urlString);
							while (m.find()) {
								link = m.group();
								System.out.println(m.group());
							}
							Elements tdElements = element.getElementsByAttribute("title");
							if (tdElements != null && tdElements.size() > 0) {
								Element titleEle = tdElements.get(0);
								title = titleEle.text();
								Element dateEle = titleEle.nextElementSibling();
								if (dateEle != null) {
									date = dateEle.text();
									DateFormat format = new SimpleDateFormat("yyyy/MM/dd");
									if (!date.equals("") && date.indexOf("/") > -1) {
										d = format.parse(date);
									}
								}
							}
						}
						list.add(Repository.builder().title(title).date(d).link(moduleUrl + link).subject(subject)
								.build());
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public static void loadRepository(Repository repository) {
		String targetUrl = repository.getLink();
		String imageBase = targetUrl.substring(0, targetUrl.lastIndexOf("/"));
		if (targetUrl != null && !targetUrl.equals("")) {
			try {
				Document doc = Jsoup.connect(targetUrl).get();
				Element e = doc.getElementById("content");
				StringBuilder imagesBuilder = new StringBuilder("");
				if (e != null) {
					String content = e.toString();
					repository.setContent(content);
					LOGGER.info(content);

					Elements imges = e.getElementsByTag("img");
					for (Element ie : imges) {
						String src = ie.attr("src");
						if (src != null) {
							if (imagesBuilder.length() > 0) {
								imagesBuilder.append(",");
							}
							imagesBuilder.append(imageBase).append(src.replace("./", "/"));
						}
					}
					repository.setImages(imagesBuilder.toString());
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		try {
			// String str = "window.open('./201703/t20170323_672020.htm')";
			// Pattern p = Pattern.compile("(/\\d+/\\w+.htm)");
			// Matcher m = p.matcher(str);
			// while (m.find()) {
			// String tar = m.group();
			// System.err.println(tar);
			// }

			// List<Repository> list =
			// HttpJsoupHelper.loadRepositorys("gzaic_gskx");
			// for (Repository repository : list) {
			// LOGGER.info(repository.toString());
			// HttpJsoupHelper.loadRepository(repository);
			// }

			// List<Repository> list =
			// HttpJsoupHelper.loadRepositorysGzsgsww("gzsgsww_gsyw");
			// Repository r = list.get(0);
			// HttpJsoupHelper.loadRepositoryGzsgsww(r);

			// String str = "./201701/t20170125_1248233.html";
			// if (str.startsWith(".")) {
			// System.out.println("str.startsWith");
			// }

			System.out.println("2017/12/12".indexOf("/"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
