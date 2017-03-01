package cn.iutils.common.utils;

import cn.iutils.common.Page;
import cn.iutils.common.config.JConfig;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * solr服务工具类
 * @author iutils.cn
 */
public class JSolrUtils {

	/**
	 * 当前SolrClient
	 */
	public static ConcurrentMap<String, SolrClient> concurrentMap = new ConcurrentHashMap<String, SolrClient>();

	/**
	 * 服务地址
	 */
	private static String server = JConfig.getConfig("solrServer");

	/**
	 * 获取solr客户端
	 * @param core 核心
	 */
	public static SolrClient getSolrClient(String core){
		SolrClient solrClient = concurrentMap.get(core);
		if(solrClient==null){
			solrClient = new HttpSolrClient(server+ core);
			concurrentMap.putIfAbsent(core,solrClient);
		}
		return solrClient;
	}

	/**
	 * 为多个文档对象的，某些属性建立索引
	 *
	 * @date 2015-8-30 下午5:33:29
	 * @param list
	 * @param properties
	 */
	public static <T> void addDocs(List<T> list, List<String> properties,SolrClient solrClient) {
		if(null == list || list.size() == 0 ) {
			return;
		}
		List<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
		ArrayList<Field> fields = ReflectUtil.getAllFields(list.get(0).getClass());
		for (T obj : list) {
			SolrInputDocument doc = new SolrInputDocument();
			for (Field field : fields) {
				for (String property : properties) {
					if(property.equals(field.getName())) {
						doc.addField(property, ReflectUtil.invokeGetterMethod(obj, property));
					}
				}
			}
			docs.add(doc);
		}
		try {
			solrClient.add(docs);
			solrClient.commit();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 建立单个索引
	 *
	 * @param <T>
	 * @date 2015-8-30 下午5:33:58
	 * @param properties
	 */
	public static <T> void addDoc(T obj, List<String> properties,SolrClient solrClient) {
		List<T> list = new ArrayList<T>();
		list.add(obj);
		addDocs(list, properties,solrClient);
	}

	/**
	 * 将整个对象都添加到索引
	 * @author wuyw
	 * @param <T>
	 * @date 2015-8-30 下午5:35:34
	 */
	public static <T> void addBean(T obj,SolrClient solrClient) {
		try {
			solrClient.addBean(obj);
			solrClient.commit();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 添加多个索引对象
	 *
	 * @param <T>
	 * @date 2015-8-30 下午5:36:37
	 */
	public static <T> void addBeans(List<T> list,SolrClient solrClient) {
		try {
			solrClient.addBeans(list);
			solrClient.commit();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 根据id删除某条索引
	 *
	 * @date 2015-8-30 下午5:37:46
	 * @param id
	 */
	public static void deleteById(String id,SolrClient solrClient) {
		try {
			solrClient.deleteById(id);
			solrClient.commit();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 根据查询语句删除索引
	 *
	 * @date 2015-8-30 下午5:38:46
	 * @param query
	 */
	public static void deleteByQuery(String query,SolrClient solrClient) {
		try {
			solrClient.deleteByQuery(query);
			solrClient.commit();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public static <T> T queryById(String id, Class<?> entityClass,SolrClient solrClient) {
		T obj = null;
		try {
			obj = (T) entityClass.newInstance();
			if(obj==null){
				return obj;
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		SolrQuery query = new SolrQuery();
		query.setQuery("id:" + id);
		QueryResponse response = null;
		try {
			response = solrClient.query(query);
			if(response==null){
				return obj;
			}
			SolrDocumentList docs = response.getResults();
			if(null == docs || docs.size() == 0) {
				return null;
			}
			SolrDocument doc = docs.get(0);
			ArrayList<Field> fields = ReflectUtil.getAllFields(obj.getClass());
			for (Field field : fields) {
				String propertyName = field.getName();
				String propertyValue = (String) doc.getFieldValue(propertyName);
				Class<?> propertyClass = field.getType();
				if(propertyClass.equals(Integer.class)) {
					Integer value = Integer.valueOf(propertyValue);
					ReflectUtil.setFieldValue(obj, propertyClass, propertyName, value);
				} else {
					ReflectUtil.setFieldValue(obj, propertyClass, propertyName, propertyValue);
				}
			}
		} catch (Exception e) {
			 throw new RuntimeException(e);
		}
		return obj;
	}

	/**
	 * solr获取的分页对象
	 *
	 * @param <T>
	 * @date 2015-8-30 下午5:39:36
	 * @param page
	 * @param solrQuery 里面封装了查询对象的条件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> Page<T> getPage(Page<T> page, SolrQuery solrQuery, Class<?> entityClass,SolrClient solrClient) {
		solrQuery.setStart(page.getPageNo());//开始索引
		solrQuery.setRows(page.getPageSize());//分页的数量
		QueryResponse queryResponse = null;
		try {
			queryResponse = solrClient.query(solrQuery);
		} catch (Exception e) {
			 throw new RuntimeException(e);
		}
		if (queryResponse == null) {
			return page;
		}
		SolrDocumentList docs = queryResponse.getResults();
		List<T> list = new ArrayList<T>(0);

		for(SolrDocument doc : docs){
			T obj = null;
			try {
				obj =  (T) entityClass.newInstance();
			} catch (Exception e) {
				 throw new RuntimeException(e);
			}
			if (obj == null) {
				continue;
			}
			ArrayList<Field> fields = ReflectUtil.getAllFields(obj.getClass());
			for (Field field : fields) {
				String propertyName = field.getName();
				Object propertyValue = doc.getFieldValue(propertyName);
				Class<?> propertyClass = field.getType();
				ReflectUtil.setFieldValue(obj, propertyClass, propertyName, propertyValue);
			}
			list.add(obj);
		}
		page.setTotal(docs.size());
		page.setList(list);
		return page;
	}

	/**
	 * 优化solr索引
	 */
	public static void optimize(String collection,SolrClient solrClient) {
		try {
			solrClient.optimize(collection);
		} catch (Exception e) {
			 throw new RuntimeException(e);
		}
	}

}
