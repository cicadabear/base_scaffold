package cn.iutils.common.utils;

import java.io.*;

/**
 * 流处理工具
 * @author iutils.cn
 * @version 1.0
 */
public class JStreamUtils extends BaseUtils{

	final static int BUFFER_SIZE = 4096;

	/**
	 * 将InputStream转换成String
	 * 
	 * @param in
	 *            InputStream
	 * @return String
	 * @throws Exception
	 * 
	 */
	public static String InputStreamTOString(InputStream in) {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] data = new byte[BUFFER_SIZE];
		String string = null;
		int count = 0;
		try {
			while ((count = in.read(data, 0, BUFFER_SIZE)) != -1)
				outStream.write(data, 0, count);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		try {
			string = new String(outStream.toByteArray(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
		return string;
	}

	/**
	 * 将InputStream转换成某种字符编码的String
	 * @param in
	 * @param encoding
	 * @return
	 * @throws Exception
	 */
	public static String InputStreamTOString(InputStream in, String encoding) {
		String string = null;
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] data = new byte[BUFFER_SIZE];
		int count = -1;
		try {
			while ((count = in.read(data, 0, BUFFER_SIZE)) != -1)
				outStream.write(data, 0, count);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		try {
			string = new String(outStream.toByteArray(), encoding);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
		return string;
	}

	/**
	 * 将String转换成InputStream
	 * 
	 * @param in
	 * @return
	 * @throws Exception
	 */
	public static InputStream StringTOInputStream(String in) throws Exception {
		ByteArrayInputStream is = new ByteArrayInputStream(in.getBytes("UTF-8"));
		return is;
	}

	/**
	 * 将String转换成InputStream
	 * @param in
	 * @return
	 * @throws Exception
	 */
	public static byte[] StringTObyte(String in) {
		byte[] bytes = null;
		try {
			bytes = InputStreamTOByte(StringTOInputStream(in));
		} catch (IOException e) {
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return bytes;
	}

	/**
	 * 将InputStream转换成byte数组
	 * @param in
	 *            InputStream
	 * @return byte[]
	 * @throws IOException
	 */
	public static byte[] InputStreamTOByte(InputStream in) throws IOException {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] data = new byte[BUFFER_SIZE];
		int count = -1;
		while ((count = in.read(data, 0, BUFFER_SIZE)) != -1)
			outStream.write(data, 0, count);
		return outStream.toByteArray();
	}

	/**
	 * 将byte数组转换成InputStream
	 * @param in
	 * @return
	 * @throws Exception
	 */
	public static InputStream byteTOInputStream(byte[] in) throws Exception {
		ByteArrayInputStream is = new ByteArrayInputStream(in);
		return is;
	}

	/**
	 * 将byte数组转换成String
	 * @param in
	 * @return
	 * @throws Exception
	 */
	public static String byteTOString(byte[] in) {

		InputStream is = null;
		try {
			is = byteTOInputStream(in);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		if (is != null)
			return InputStreamTOString(is, "UTF-8");
		else
			return null;
	}

	/**
	 * 将byte数组转换成String
	 * @param in
	 * @return
	 * @throws Exception
	 */
	public static String getString(String in) {
		String is = null;
		try {
			is = byteTOString(StringTObyte(in));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return is;
	}

	/**
	 * InputStream 转换成byte[]
	 */
	public byte[] getBytes(InputStream is) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] b = new byte[BUFFER_SIZE];
		int len = 0;
		while ((len = is.read(b, 0, BUFFER_SIZE)) != -1) {
			baos.write(b, 0, len);
		}
		baos.flush();
		byte[] bytes = baos.toByteArray();

		System.out.println(new String(bytes));

		return bytes;
	}

	/**
	 * 根据文件路径创建文件输入流处理 以字节为单位（非 unicode ）
	 * @param filepath
	 * @return
	 */
	public static FileInputStream getFileInputStream(String filepath) {
		FileInputStream fileInputStream = null;
		try {
			fileInputStream = new FileInputStream(filepath);
		} catch (FileNotFoundException e) {
			System.out.print("错误信息:文件不存在");
			throw new RuntimeException(e);
		}
		return fileInputStream;
	}

	/**
	 * 根据文件对象创建文件输入流处理 以字节为单位（非 unicode ）
	 * 
	 * @param file
	 * @return
	 */
	public static FileInputStream getFileInputStream(File file) {
		FileInputStream fileInputStream = null;
		try {
			fileInputStream = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			System.out.print("错误信息:文件不存在");
			throw new RuntimeException(e);
		}
		return fileInputStream;
	}

	/**
	 * 根据文件对象创建文件输出流处理 以字节为单位（非 unicode ）
	 * 
	 * @param file
	 * @param append
	 *            true:文件以追加方式打开,false:则覆盖原文件的内容
	 * @return
	 */
	public static FileOutputStream getFileOutputStream(File file, boolean append) {
		FileOutputStream fileOutputStream = null;
		try {
			fileOutputStream = new FileOutputStream(file, append);
		} catch (FileNotFoundException e) {
			System.out.print("错误信息:文件不存在");
			throw new RuntimeException(e);
		}
        return fileOutputStream;
	}

	/**
	 * 根据文件路径创建文件输出流处理 以字节为单位（非 unicode ）
	 * 
	 * @param filepath
	 * @param append
	 *            true:文件以追加方式打开,false:则覆盖原文件的内容
	 * @return
	 */
	public static FileOutputStream getFileOutputStream(String filepath,
			boolean append) {
		FileOutputStream fileOutputStream = null;
		try {
			fileOutputStream = new FileOutputStream(filepath, append);
		} catch (FileNotFoundException e) {
			System.out.print("错误信息:文件不存在");
			throw new RuntimeException(e);
		}
		return fileOutputStream;
	}

	public static File getFile(String filepath) {
		return new File(filepath);
	}

	public static ByteArrayOutputStream getByteArrayOutputStream() {
		return new ByteArrayOutputStream();
	}

	/**
	 * 将输入流的内容复制到输出流里
	 * @param in  输入流
	 * @param out 输出流
	 * @return 复制的数据字节数
	 * @throws IOException IO异常
	 */
	public static int copy(InputStream in, OutputStream out) throws IOException {
		BeanUtils.requireNonNull(in, "No InputStream specified");
		BeanUtils.requireNonNull(out, "No OutputStream specified");
		int byteCount = 0;
		byte[] buffer = new byte[4096];
		int bytesRead1;
		for (; (bytesRead1 = in.read(buffer)) != -1; byteCount += bytesRead1) {
			out.write(buffer, 0, bytesRead1);
		}
		out.flush();
		return byteCount;
	}

	/**
	 * 关闭需要关闭的对象，如果关闭出错，给出警告
	 * @param closeable 需要关闭的对象
	 */
	public static void closeWithWarn(Closeable closeable) {
		if (BeanUtils.nonNull(closeable)) {
			try {
				closeable.close();
			} catch (IOException e) {
				logger.warn("关闭流出错......", e);
			}
		}
	}

}