package cn.iutils.common.utils.net;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import cn.iutils.common.utils.JRegularUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * 采集工具
 * 
 * @author cc
 */
public class JCrawlerUtils {

	private static Logger logger = LoggerFactory.getLogger(JCrawlerUtils.class);

	// 超时时间
	private static int timeout = 5000;

	/**
	 * 主要采集，常用购物网站的数据 1、京东网 2、天猫网 3、淘宝网 4、一号店 5、苏宁易购 6、国美在线
	 */

	/**
	 * 京东采集器
	 */
	public static void crawlerJd(String key) {

	}

	/**
	 * 天猫采集器
	 */
	public static void crawlerTmall(String key) {

	}

	/**
	 * 淘宝采集器
	 */
	public static void crawlerTaobao(String key) {

	}

	/**
	 * 一号店采集器
	 */
	public static void crawlerYhd(String key) {

	}

	/**
	 * 苏宁易购采集器
	 */
	public static void crawlerSuning(String key) {

	}

	/**
	 * 国美在线采集器
	 */
	public static void crawlerGome(String key) {

	}

	/**
	 * 采集城市邮编和区号
	 */
	public static List<Map<String, String>> crawlerCity() {
		List<Map<String, String>> list = Lists.newArrayList();
		// 打开网站
		String url = "http://www.diqudaima.com";
		try {
			Document doc = Jsoup.connect(url).timeout(timeout).get();
			// 获取省份列表
			Elements provinceLinks = doc.getElementsByClass("Count").get(0).getElementsByTag("a");
			for (Element province : provinceLinks) {
				Document cityDoc = Jsoup.connect(url + province.attr("href")).timeout(timeout).get();
				Elements cityLinks = cityDoc.getElementsByClass("all").get(0).getElementsByTag("ul").get(0).getElementsByTag("a");
				for (Element city : cityLinks) {
					Document cityInfoDoc = Jsoup.connect(url + city.attr("href")).timeout(timeout).get();
					Elements table = cityInfoDoc.getElementsByTag("table");
					if (!table.isEmpty()) {
						Map<String, String> map = Maps.newHashMap();
						String content = table.get(0).text();
						String code = JRegularUtils.getStr(content, "行政代码:([0-9]*)");
						String zipCode = JRegularUtils.getStr(content, "邮政编码:([0-9]*)");
						String areaCode = JRegularUtils.getStr(content, "长途区号:([0-9]*)");
						map.put("code", code);
						map.put("zipCode", zipCode);
						map.put("areaCode", areaCode);
						list.add(map);
					}
				}
			}
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		return list;
	}

	/**
	 * 爬取集号吧
	 * 
	 * @return
	 */
	public static List<Map<String, String>> crawlerJiHaoBa() {
		List<Map<String, String>> list = Lists.newArrayList();
		// 打开网站
		String url = "http://www.jihaoba.com/tools/haoduan/";
		String host = "http://www.jihaoba.com";
		String top = null, cityStr = null;
		try {
			Document doc = Jsoup.connect(url).timeout(timeout).get();
			// 获取号码段
			Elements haoduanlinks = doc.getElementsByClass("hd_result").get(0).getElementsByTag("a");
			for (Element haoduan : haoduanlinks) {
				Document haoduanDoc = Jsoup.connect(host + haoduan.attr("href")).timeout(timeout).get();
				top = haoduan.text();
				// 获取号码段的城市
				Elements cityLinks = haoduanDoc.getElementsByClass("hd_result1").get(0).getElementsByTag("a");
				for (Element city : cityLinks) {
					Document cityInfoDoc = Jsoup.connect(host + city.attr("href")).timeout(timeout).get();
					cityStr = JRegularUtils.getStr(city.text(), "([\u4e00-\u9fa5]+)[0-9]*");
					Elements citys = cityInfoDoc.getElementsByClass("hd-city").get(1).getElementsByClass("hd-city01");
					for (Element hao : citys) {
						Map<String, String> map = Maps.newHashMap();
						map.put("top", top);
						map.put("section", hao.text());
						map.put("city", cityStr);
						list.add(map);
					}
				}
			}
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		return list;
	}

	/**
	 * 爬取查号吧
	 * 
	 * @return
	 */
	public static List<Map<String, String>> crawlerChaHaoBa() {
		List<Map<String, String>> list = Lists.newArrayList();
		// 打开网站
		String url = "http://www.chahaoba.com/130";
		String host = "http://www.chahaoba.com";
		String top = null, cityStr = null;
		try {
			Document doc = Jsoup.connect(url).timeout(timeout).get();
			// 获取号码段
			Elements haoduanlinks = doc.getElementsByClass("top").get(1).getElementsByTag("ul").get(0).getElementsByTag("a");
			for (Element haoduan : haoduanlinks) {
				if (haoduan.attr("href").length() < "http://www.chahaoba.com/130".length()) {
					continue;
				}
				Document haoduanDoc = Jsoup.connect(haoduan.attr("href")).timeout(timeout).get();
				top = haoduan.text();
				// 获取号码段的城市
				Elements cityLinks = haoduanDoc.getElementById("mw-content-text").getElementsByTag("p");
				// getElementsByClass("hd_result1").get(0).getElementsByTag("a");
				for (Element city : cityLinks) {
					Document cityInfoDoc = Jsoup.connect(host + city.attr("href")).timeout(timeout).get();
					cityStr = JRegularUtils.getStr(city.text(), "([\u4e00-\u9fa5]+)[0-9]*");
					Elements citys = cityInfoDoc.getElementsByClass("hd-city").get(1).getElementsByClass("hd-city01");
					for (Element hao : citys) {
						Map<String, String> map = Maps.newHashMap();
						map.put("top", top);
						map.put("section", hao.text());
						map.put("city", cityStr);
						list.add(map);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 测试入口
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		crawlerChaHaoBa();
	}
}
