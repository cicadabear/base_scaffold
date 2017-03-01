package cn.iutils.common.utils.qiniu;

import java.io.File;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;

/**
 * 七牛API
 */
public class QiniuApi {
	private static final Logger logger = Logger.getLogger(QiniuApi.class);

	private String BUCKET_PUB;
	private String BUCKET_PRI;

	private Auth auth;
	private BucketManager bucketManager;
	private UploadManager uploadManager;
	private String bucketDomain;

	public QiniuApi(String ak, String sk, String domain, String pub, String pri) {
		auth = Auth.create(ak, sk);
		bucketManager = new BucketManager(auth);
		uploadManager = new UploadManager();
		bucketDomain = domain;
		BUCKET_PUB = pub;
		BUCKET_PRI = pri;
	}

	/**
	 * 上传文件到公共空间
	 * 
	 * @param data
	 * @param key
	 * @return
	 */
	public String uploadToPublic(final byte[] data, final String key) {
		return upload(data, key, getPublicUptoken());
	}

	/**
	 * 上传文件到公共空间
	 * 
	 * @param file
	 * @param key
	 * @return
	 */
	public String uploadToPublic(final File file, final String key) {
		return upload(file, key, getPublicUptoken());
	}

	/**
	 * 上传文件到公共空间
	 * 
	 * @param filePath
	 * @param key
	 * @return
	 */
	public String uploadToPublic(final String filePath, final String key) {
		return upload(filePath, key, getPublicUptoken());
	}

	/**
	 * 上传文件到私有空间
	 * 
	 * @param data
	 * @param key
	 * @return
	 */
	public String uploadToPrivate(final byte[] data, final String key) {
		return upload(data, key, getPrivateUptoken());
	}

	/**
	 * 上传文件到私有空间
	 * 
	 * @param file
	 * @param key
	 * @return
	 */
	public String uploadToPrivate(final File file, final String key) {
		return upload(file, key, getPrivateUptoken());
	}

	/**
	 * 上传文件到私有空间
	 * 
	 * @param filePath
	 * @param key
	 * @return
	 */
	public String uploadToPrivate(final String filePath, final String key) {
		return upload(filePath, key, getPrivateUptoken());
	}

	/**
	 * 抓取资源到公共空间
	 * 
	 * @param url
	 *            要求url可公网正常访问
	 * @param key
	 * @return
	 */
	public String fetchToPublic(final String url, final String key) {
		return fetch(url, key, BUCKET_PUB);
	}

	/**
	 * 抓取资源到私有空间
	 * 
	 * @param url
	 *            要求url可公网正常访问
	 * @param key
	 * @return
	 */
	public String fetchToPrivate(final String url, final String key) {
		return fetch(url, key, BUCKET_PRI);
	}

	/**
	 * 下载签名
	 * 
	 * @param baseUrl
	 *            待签名文件url，如 http://img.domain.com/u/3.jpg 、
	 *            http://img.domain.com/u/3.jpg?imageView2/1/w/120
	 * @return
	 */
	public String getPrivateDownloadUrl(final String baseUrl) {
		return auth.privateDownloadUrl(baseUrl);
	}

	/**
	 * 下载签名
	 * 
	 * @param baseUrl
	 *            待签名文件url，如 http://img.domain.com/u/3.jpg 、
	 *            http://img.domain.com/u/3.jpg?imageView2/1/w/120
	 * @param expires
	 *            有效时长，单位秒。默认3600s
	 * @return
	 */
	public String getPrivateDownloadUrl(final String baseUrl, final long expires) {
		return auth.privateDownloadUrl(baseUrl, expires);
	}

	/**
	 * 删除公共bucket七牛空间文件
	 * 
	 * @param key
	 * @throws QiniuException
	 */
	public void deletePublic(final String key) {
		try {
			bucketManager.delete(BUCKET_PUB, key);
		} catch (final QiniuException e) {
			final Response r = e.response;
			// 请求失败时简单状态信息
			logger.error(r.toString());
			if (r.statusCode != 612) {// 指定资源不存在或已被删除。
				logger.error("删除公共bucket七牛空间文件出错。" + e.getMessage(), e);
			}
		}
	}

	/**
	 * 删除私有bucket七牛空间文件
	 * 
	 * @param key
	 * @throws QiniuException
	 */
	public void deletePrivate(final String key) {
		try {
			bucketManager.delete(BUCKET_PRI, key);
		} catch (final QiniuException e) {
			final Response r = e.response;
			// 请求失败时简单状态信息
			logger.error(r.toString());
			if (r.statusCode != 612) {// 指定资源不存在或已被删除。
				logger.error("删除私有bucket七牛空间文件出错。" + e.getMessage(), e);
			}
		}
	}

	/**
	 * 获取Bucket域名
	 * 
	 * @return
	 */
	public String getBucketDomain() {
		return bucketDomain;
	}

	/**
	 * 获取私有bucket上传授权uptoken<br>
	 * 简单上传，使用默认策略
	 * 
	 * @return
	 */
	public String getPrivateUptoken() {
		return auth.uploadToken(BUCKET_PRI);// 请确保该bucket已经存在
	}

	/**
	 * 获取公共bucket上传授权uptoken<br>
	 * 简单上传，使用默认策略
	 * 
	 * @return
	 */
	public String getPublicUptoken() {
		return auth.uploadToken(BUCKET_PUB);// 请确保该bucket已经存在
	}

	// 上传内存中数据
	private String upload(final File file, final String key, final String uptoken) {
		try {
			final Response res = uploadManager.put(file, key, uptoken);
			final QiniuRet ret = res.jsonToObject(QiniuRet.class);
			if (res.isOK()) {
				return bucketDomain + ret.key;
			}
			if (logger.isInfoEnabled()) {
				logger.info(res);
				logger.info(ret);
			}
		} catch (final QiniuException e) {
			final Response r = e.response;
			// 请求失败时简单状态信息
			logger.error(r.toString());
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	// 上传内存中数据
	private String upload(final byte[] data, final String key, final String uptoken) {
		try {
			final Response res = uploadManager.put(data, key, uptoken);
			final QiniuRet ret = res.jsonToObject(QiniuRet.class);
			if (res.isOK()) {
				return bucketDomain + "/" + ret.key;
			}
			if (logger.isInfoEnabled()) {
				logger.info(res);
				logger.info(ret);
			}
		} catch (final QiniuException e) {
			final Response r = e.response;
			// 请求失败时简单状态信息
			logger.error(r.toString());
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	// 上传内存中数据
	private String upload(final String filePath, final String key, final String uptoken) {
		try {
			final Response res = uploadManager.put(filePath, key, uptoken);
			final QiniuRet ret = res.jsonToObject(QiniuRet.class);
			if (res.isOK()) {
				return bucketDomain + "/" + ret.key;
			}
			if (logger.isInfoEnabled()) {
				logger.info(res);
				logger.info(ret);
			}
		} catch (final QiniuException e) {
			final Response r = e.response;
			// 请求失败时简单状态信息
			logger.error(r.toString());
			// 响应的文本信息
			try {
				logger.error(r.bodyString());
			} catch (final QiniuException e1) {
				logger.error(e1.getMessage(), e1);
			}
		}
		return null;
	}

	// 上传内存中数据
	private String fetch(final String url, final String key, final String bucket) {
		try {
			DefaultPutRet ret = bucketManager.fetch(url, bucket, key);
			if (StringUtils.isNotBlank(ret.key)) {
				return bucketDomain + "/" + ret.key;
			}
			if (logger.isInfoEnabled()) {
				logger.info(ret);
			}
		} catch (final QiniuException e) {
			final Response r = e.response;
			// 请求失败时简单状态信息
			logger.error(r.toString());
			// 响应的文本信息
			try {
				logger.error(r.bodyString());
			} catch (final QiniuException e1) {
				logger.error(e1.getMessage(), e1);
			}
		}
		return null;
	}
}
