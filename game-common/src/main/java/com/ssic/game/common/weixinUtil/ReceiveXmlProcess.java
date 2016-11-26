package com.ssic.game.common.weixinUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.ssic.game.common.dto.PayDto;

/**
 * 解析接收到的微信xml，返回消息对象
 * 
 * @author pamchen-1
 *
 */
public class ReceiveXmlProcess {
	/**
	 * 解析微信xml消息
	 * 
	 * @param strXml
	 * @return
	 */
	public PayDto getMsgEntity(String strXml) {
		PayDto msg = null;
		try {
			if (strXml.length() <= 0 || strXml == null)
				return null;

			// 将字符串转化为XML文档对象
			Document document = DocumentHelper.parseText(strXml);
			// 获得文档的根节点
			Element root = document.getRootElement();
			// 遍历根节点下所有子节点
			Iterator<?> iter = root.elementIterator();

			// 遍历所有结点
			msg = new PayDto();
			// 利用反射机制，调用set方法
			// 获取该实体的元类型
			Class<?> c = Class.forName("com.servlet.PayDto");
			msg = (PayDto) c.newInstance();// 创建这个实体的对象

			while (iter.hasNext()) {
				Element ele = (Element) iter.next();
				// 获取set方法中的参数字段（实体类的属性）
				Field field = c.getDeclaredField(ele.getName());
				// 获取set方法，field.getType())获取它的参数数据类型
				String name=ele.getName();
				name = name.substring(0, 1).toUpperCase() + name.substring(1);
				Method method = c.getDeclaredMethod("set" + name,
						field.getType());
				// 调用set方法
				method.invoke(msg, ele.getText());
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("xml 格式异常: " + strXml);
			e.printStackTrace();
		}
		return msg;
	}

	public static void main(String[] args) {
		String path = Thread.currentThread().getContextClassLoader()
				.getResource("").getPath()+"weixin";
		System.out.println(path);
	}
}
