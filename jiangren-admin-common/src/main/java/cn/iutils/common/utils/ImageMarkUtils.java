package cn.iutils.common.utils;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

/**
 * 图片水印处理 Created by cc on 16/5/24.
 */
public class ImageMarkUtils {

	// 水印透明度
	private static float alpha = 1f;
	// 水印横向位置
	private static int positionWidth = 150;
	// 水印纵向位置
	private static int positionHeight = 300;
	// 水印文字字体
	private static Font font = new Font("宋体", Font.BOLD, 18);
	// 水印文字颜色
	private static Color color = Color.BLACK;

	/**
	 * 设置属性
	 * 
	 * @param alpha
	 *            水印透明度
	 * @param positionWidth
	 *            水印横向位置
	 * @param positionHeight
	 *            水印纵向位置
	 * @param font
	 *            水印文字字体
	 * @param color
	 *            水印文字颜色
	 */
	public static void setImageMarkOptions(float alpha, int positionWidth,
			int positionHeight, Font font, Color color) {
		if (alpha != 0.0)
			ImageMarkUtils.alpha = alpha;
		if (positionWidth != 0)
			ImageMarkUtils.positionWidth = positionWidth;
		if (positionHeight != 0)
			ImageMarkUtils.positionHeight = positionHeight;
		if (font != null)
			ImageMarkUtils.font = font;
		if (color != null)
			ImageMarkUtils.color = color;
	}

	/**
	 * 给图片添加水印图片
	 * 
	 * @param iconPath
	 *            水印图片路径
	 * @param srcImgPath
	 *            源图片路径
	 * @param targerPath
	 *            目标图片路径
	 */
	public static void markImageByIcon(String iconPath, String srcImgPath,
			String targerPath) {
		markImageByIcon(iconPath, srcImgPath, targerPath, null);
	}

	/**
	 * 给图片添加水印图片、可设置水印图片旋转角度
	 * 
	 * @param iconPath
	 *            水印图片路径
	 * @param srcImgPath
	 *            源图片路径
	 * @param targerPath
	 *            目标图片路径
	 * @param degree
	 *            水印图片旋转角度
	 */
	public static void markImageByIcon(String iconPath, String srcImgPath,
			String targerPath, Integer degree) {
		OutputStream os = null;
		try {

			Image srcImg = ImageIO.read(new File(srcImgPath));

			BufferedImage buffImg = new BufferedImage(srcImg.getWidth(null),
					srcImg.getHeight(null), BufferedImage.TYPE_INT_RGB);

			// 1、得到画笔对象
			Graphics2D g = buffImg.createGraphics();

			// 2、设置对线段的锯齿状边缘处理
			g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
					RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			g.drawImage(
					srcImg.getScaledInstance(srcImg.getWidth(null),
							srcImg.getHeight(null), Image.SCALE_SMOOTH), 0, 0,
					null);
			// 3、设置水印旋转
			if (null != degree) {
				g.rotate(Math.toRadians(degree),
						(double) buffImg.getWidth() / 2,
						(double) buffImg.getHeight() / 2);
			}

			// 4、水印图片的路径 水印图片一般为gif或者png的，这样可设置透明度
			ImageIcon imgIcon = new ImageIcon(iconPath);

			// 5、得到Image对象。
			Image img = imgIcon.getImage();

			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
					alpha));

			// 6、水印图片的位置
			g.drawImage(img, positionWidth, positionHeight, null);
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
			// 7、释放资源
			g.dispose();

			// 8、生成图片
			os = new FileOutputStream(targerPath);
			ImageIO.write(buffImg, "JPG", os);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			try {
				if (os != null)
					os.close();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}

	/**
	 * 给图片添加水印文字
	 * 
	 * @param logoText
	 *            水印文字
	 * @param srcImgPath
	 *            源图片路径
	 * @param targerPath
	 *            目标图片路径
	 */
	public static void markImageByText(List<Map<String, String>> logoText,
			String srcImgPath, String targerPath) {
		markImageByText(logoText, srcImgPath, targerPath, null);
	}

	/**
	 * 给图片添加水印文字、可设置水印文字的旋转角度
	 * 
	 * @param logoText
	 * @param srcImgPath
	 * @param targerPath
	 * @param degree
	 */
	public static void markImageByText(List<Map<String, String>> logoText,
			String srcImgPath, String targerPath, Integer degree) {

		OutputStream os = null;
		try {
			// 1、源图片
			Image srcImg = ImageIO.read(new File(srcImgPath));
			BufferedImage buffImg = new BufferedImage(srcImg.getWidth(null),
					srcImg.getHeight(null), BufferedImage.TYPE_INT_RGB);

			// 2、得到画笔对象
			Graphics2D g = buffImg.createGraphics();
			// 3、设置对线段的锯齿状边缘处理
			g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
					RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			g.drawImage(
					srcImg.getScaledInstance(srcImg.getWidth(null),
							srcImg.getHeight(null), Image.SCALE_SMOOTH), 0, 0,
					null);
			// 4、设置水印旋转
			if (null != degree) {
				g.rotate(Math.toRadians(degree),
						(double) buffImg.getWidth() / 2,
						(double) buffImg.getHeight() / 2);
			}
			// 5、设置水印文字颜色
			g.setColor(color);
			// 6、设置水印文字Font
			g.setFont(font);
			// 7、设置水印文字透明度
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
					alpha));
			// 8、第一参数->设置的内容，后面两个参数->文字在图片上的坐标位置(x,y)
			for (int i = 0; i < logoText.size(); i++) {
				Map<String, String> map = logoText.get(i);
				g.drawString(map.get("logoText").toString(),
						Integer.valueOf(map.get("positionWidth").toString()),
						Integer.valueOf(map.get("positionHeight").toString()));
			}
			// 9、释放资源
			g.dispose();
			// 10、生成图片
			os = new FileOutputStream(targerPath);
			ImageIO.write(buffImg, "JPG", os);

		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			try {
				if(os!=null){
					os.close();
				}
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}

	/**
	 * 测试执行
	 * @param args
	 */
	public static void main(String[] args) {
		// 生成图片
		List<Map<String, String>> list = Lists.newArrayList();
		list.add(ImmutableMap.of("logoText", "2017年5月24日", "positionWidth",
				"220", "positionHeight", "155"));
		list.add(ImmutableMap.of("logoText", "房明霞", "positionWidth", "330",
				"positionHeight", "460"));
		list.add(ImmutableMap.of("logoText", "15299589828", "positionWidth",
				"520", "positionHeight", "460"));
		list.add(ImmutableMap.of("logoText", "新疆阿克苏地区拜城县", "positionWidth",
				"475", "positionHeight", "500"));
		list.add(ImmutableMap.of("logoText", "369705426", "positionWidth",
				"230", "positionHeight", "500"));
		list.add(ImmutableMap.of("logoText", "一级代理", "positionWidth", "130",
				"positionHeight", "580"));
		list.add(ImmutableMap.of("logoText", "2016年5月24日", "positionWidth",
				"220", "positionHeight", "915"));
		list.add(ImmutableMap.of("logoText", "2016052409", "positionWidth",
				"220", "positionHeight", "940"));
		// 给图片添加水印文字
		markImageByText(list, "/Users/mac/Desktop/authorization.jpg",
				"/Users/mac/Desktop/9房明霞.jpg");
	}

}
