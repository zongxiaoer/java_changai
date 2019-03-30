package com.yuntongxun.itsys.gateway.common.util;

import java.io.File;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.apache.commons.lang.StringUtils;

import net.sf.json.JSONObject;

public class StringUtil {

	public static final String allChar = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	public static final String numberChar = "0123456789";

	/**
	 * 返回一个定长的随机字符串(只包含大小写字母、数字)
	 * 
	 * @param length
	 *            随机字符串长度
	 * @return 随机字符串
	 */
	public static String generateString(int length) {
		StringBuffer sb = new StringBuffer();
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			sb.append(allChar.charAt(random.nextInt(allChar.length())));
		}
		return sb.toString();
	}

	/**
	 * 返回一个定长的随机字符串(只包含数字)
	 * 
	 * @param length
	 *            随机字符串长度
	 * @return 随机字符串
	 */
	public static String generateNum(int length) {
		StringBuffer sb = new StringBuffer();
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			sb.append(numberChar.charAt(random.nextInt(numberChar.length())));
		}
		return sb.toString();
	}
	

	/**
	 * 替换SQL注入的单引号（'）
	 * 
	 * @param str
	 * @return
	 */
	public static String replaceSingleQuote(String str) {
		if (str != null && !"".equals(str)) {
			StringBuffer result = new StringBuffer();
			for (int i = 0; i < str.length(); i++) {
				char c = str.charAt(i);
				if (c == '\'') {
					result.append("");
				} else {
					result.append(str.charAt(i));
				}
			}
			return result.toString();
		} else {
			return "";
		}
	}

	/**
	 * 根据位数生成随机数
	 * 
	 * @param number
	 *            位数
	 * @return n 随机数
	 */
	public static String generateRandomNum(int number) {

		StringBuffer num = new StringBuffer();
		while (number > 0) {
			num.append(String.valueOf(new Random().nextInt(10)));// 获取大于等于0，小于10的整型随机数
			number--;
		}
		String n = num.toString();
		return n;
	}

	/**
	 * 将空串转为0
	 * 
	 * @param s
	 *            空串(null或者"")
	 * @return
	 */
	public static String NullToZero(String s) {
		if (s == null || s.trim().length() == 0) {
			s = "0";
		}
		return s;
	}

	/**
	 * 根据传参number，生成12位字符串，位数不够在number前补零
	 * 
	 * @param number
	 *            数字
	 * @return 12位字符串
	 */
	public static String generateTwelveString(BigDecimal number) {
		String s = "";
		if (number != null) {
			s = number.toString();
			if (s.length() == 1) {
				s = "00000000000" + s;
			} else if (s.length() == 2) {
				s = "0000000000" + s;
			} else if (s.length() == 3) {
				s = "000000000" + s;
			} else if (s.length() == 4) {
				s = "00000000" + s;
			} else if (s.length() == 5) {
				s = "0000000" + s;
			} else if (s.length() == 6) {
				s = "000000" + s;
			} else if (s.length() == 7) {
				s = "00000" + s;
			} else if (s.length() == 8) {
				s = "0000" + s;
			} else if (s.length() == 9) {
				s = "000" + s;
			} else if (s.length() == 10) {
				s = "00" + s;
			} else if (s.length() == 11) {
				s = "0" + s;
			} else {
				s = "" + s;
			}
		}
		return s;
	}

	/**
	 * 根据传参number，生成8位字符串，位数不够在number前补零
	 * 
	 * @param number
	 *            数字
	 * @return 8位字符串
	 */
	public static String generateEightString(BigDecimal number) {
		String s = "";
		if (number != null) {
			s = number.toString();
			if (s.length() == 1) {
				s = "0000000" + s;
			} else if (s.length() == 2) {
				s = "000000" + s;
			} else if (s.length() == 3) {
				s = "00000" + s;
			} else if (s.length() == 4) {
				s = "0000" + s;
			} else if (s.length() == 5) {
				s = "000" + s;
			} else if (s.length() == 6) {
				s = "00" + s;
			} else if (s.length() == 7) {
				s = "0" + s;
			} else {
				s = "" + s;
			}
		}
		return s;
	}

	/**
	 * 根据传参number，生成4位字符串，位数不够在number前补零
	 * 
	 * @param number
	 *            数字
	 * @return 8位字符串
	 */
	public static String generateFourString(String number) {
		String s = "";
		if (number != null) {
			s = number.toString();
			if (s.length() == 1) {
				s = "000" + s;
			} else if (s.length() == 2) {
				s = "00" + s;
			} else if (s.length() == 3) {
				s = "0" + s;
			} else {
				s = "" + s;
			}
		}
		return s;
	}

	/**
	 * 生成32位随机字符串
	 * 
	 * @return
	 */
	public static String getUUID() {
		String uuid = UUID.randomUUID().toString().trim().replaceAll("-", "");
		return uuid;
	}

	/**
	 * 获取短信消息类型
	 * 
	 * @param msgType
	 *            消息类型
	 * @return
	 */
	public static String getMsgType(String msgType) {
		String type = null;
		if (msgType != null && msgType.trim().length() > 0) {
			if ("0".equals(msgType)) {// 普通短信
				type = "15";
			} else if ("1".equals(msgType)) {// 长短信
				type = "8";
			} else {
				type = "15";
			}
		} else {
			type = "15";
		}
		return type;
	}

	/**
	 * 获取短信类型
	 * 
	 * @param smsType
	 *            短信类型
	 * @return
	 */
	public static String getSmsType(String smsType) {
		String type = null;
		if (smsType != null && smsType.trim().length() > 0) {
			if ("0".equals(smsType)) {// 上行短信
				type = "0";
			} else if ("1".equals(smsType)) {// 手机接收状态报告
				type = "2";
			} else {
				type = "0";
			}
		} else {
			type = "0";
		}
		return type;
	}


	/**
	 * 计算字符长度
	 * 
	 * @param value
	 * @return
	 */
	public static int length(String value) {
		int valueLength = 0;
		String chinese = "[\u0391-\uFFE5]";
		// 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1
		for (int i = 0; i < value.length(); i++) {
			// 获取一个字符
			String temp = value.substring(i, i + 1);
			// 判断是否为中文字符
			if (temp.matches(chinese)) {
				// 中文字符长度为2
				valueLength += 2;
			} else {
				// 其他字符长度为1
				valueLength += 1;
			}
		}
		return valueLength;
	}

	/**
	 * 判断是否包含中文
	 * 
	 * @param value
	 * @return
	 */
	public static boolean isChinese(String value) {
		boolean flag = false;
		String chinese = "[\u0391-\uFFE5]";
		for (int i = 0; i < value.length(); i++) {
			// 获取一个字符
			String temp = value.substring(i, i + 1);
			// 判断是否为中文字符
			if (temp.matches(chinese)) {
				// 中文字符长度为2
				return true;
			}
		}
		return flag;
	}

	/**
	 * 从后往前截取字符串
	 * 
	 * @param src
	 *            被截取的字符串
	 * @param end
	 *            从后往前截取的位数
	 * @return
	 */
	public static String subEndStr(String src, int end) {
		StringBuffer des1 = new StringBuffer("");
		StringBuffer des = new StringBuffer("");
		char c = '0';
		if (src != null && src.trim().length() > 0) {
			int i = src.length() - 1;
			while (i >= 0) {
				c = src.charAt(i);
				des1.append(c);
				i--;
			}
			String s = des1.toString();
			String ss = s.substring(0, end);
			if (ss != null && ss.trim().length() > 0) {
				int j = ss.length() - 1;
				while (j >= 0) {
					c = ss.charAt(j);
					des.append(c);
					j--;
				}
			}
		}
		return des.toString();
	}

	/**
	 * 获取语音文件串
	 * 
	 * @param prefix
	 *            语音文件路径
	 * @param suffix
	 *            语音文件后缀
	 * @param split
	 *            语音文件分隔符
	 * @param voiceCode
	 *            语音验证码
	 * @return 语音验证码文件串
	 */
	public static String getVoiceCode(String prefix, String suffix,
			String split, String voiceCode) {
		StringBuffer vcode = new StringBuffer();
		String tips = "tishiyin";
		vcode.append(prefix).append(tips).append(suffix).append(split);
		if (voiceCode != null && voiceCode.trim().length() > 0) {
			System.out.println("before lower case VoiceCode = " + voiceCode);
			voiceCode = voiceCode.toLowerCase();
			System.out.println("after lower case VoiceCode = " + voiceCode);
			int len = voiceCode.length();
			for (int i = 0; i < len; i++) {
				char c = voiceCode.charAt(i);
				if (i == len - 1) {
					vcode.append(prefix).append(c).append(suffix);
				} else {
					vcode.append(prefix).append(c).append(suffix).append(split);
				}
			}
			System.out.println("VoiceCode = " + vcode.toString());
		}
		return vcode.toString();
	}

	public static String getFSIndex(String index) {
		String str = "";
		int length = index.length();
		switch (length) {
		case 1:
			str = "0000" + index;
			break;
		case 2:
			str = "000" + index;
			break;
		case 3:
			str = "00" + index;
			break;
		case 4:
			str = "0" + index;
			break;
		case 5:
			str = index;
		}
		return str;
	}

	/**
	 * json兼容 判断json是否以[开头 ]结尾
	 */
	public static String checkJson(String json) {
		return json.startsWith("[") && json.endsWith("]") ? json : json
				.startsWith("[") && !json.endsWith("]") ? json + "]" : !json
				.startsWith("[") && json.endsWith("]") ? "[" + json : "["
				+ json + "]";
	}

	/**
	 * 解析from to
	 */
	public static String getFromOrTo(String str) {
		return str.substring(str.indexOf(":") + 1, str.lastIndexOf("@"))
				.toLowerCase();
	}

	/**
	 * 判断数据长度是否超过有效长度
	 */
	public static boolean isTooLong(String[] args, int[] lens) {
		boolean flag = false;
		int argLen = args.length;
		if (args.length > lens.length) {
			argLen = lens.length;
		} else if (args.length < lens.length) {
			argLen = args.length;
		}
		for (int i = 0; i < argLen; i++) {
			if (args[i] != null) {
				if (length(args[i]) > lens[i]) {
					flag = true;
					break;
				}
			}
		}
		return flag;
	}

	/**
	 * 去掉换行符
	 * 
	 * @param str
	 * @return
	 */
	public static String replaceBlank(String str) {
		String dest = "";
		if (str != null) {
			Pattern p = Pattern.compile("\t|\r|\n");
			Matcher m = p.matcher(str);
			dest = m.replaceAll("");
		}
		return dest;
	}

	/**
	 * 去掉换行符
	 * 
	 * @param str
	 * @return
	 */
	public static String replaceBlank2(String str) {
		String dest = "";
		if (str != null) {
			dest = str.replace("\\n", "");
			dest = dest.replace("\\t", "");
			dest = dest.replace("\\r", "");
		}
		return dest;
	}

	/**
	 * 去掉 中文【】字符
	 * 
	 * @param str
	 * @return
	 */
	public static String replaceRackets(String value) {
		if (value != null) {
			value = value.replace("【", "");
			value = value.replace("】", "");
		}
		return value;
	}

	/**
	 * 去掉 空格字符
	 * 
	 * @param str
	 * @return
	 */
	public static String replaceSpace(String value) {
		if (value != null) {
			value = value.replace(" ", "");
			value = value.trim();
		}
		return value;
	}

	/**
	 * 添加JSON换行符
	 * 
	 * @param str
	 * @return
	 */
	public static String addLineFeed(String str) {
		String dest = "";
		if (str != null) {
			Pattern p = Pattern.compile("\t|\r|\n");
			Matcher m = p.matcher(str);
			dest = m.replaceAll("");
		}
		return dest;
	}

	/**
	 * 判断是否为数字
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str) {
		if (str == null || str.equals("")) {
			return false;
		}
		Pattern pattern = Pattern.compile("[0-9]*");
		return pattern.matcher(str).matches();
	}

	/**
	 * 判断是否为浮点数，包括double和float
	 * 
	 * @param str
	 *            传入的字符串
	 * @return 是浮点数返回true,否则返回false
	 */
	public static boolean isDouble(String str) {
		Pattern pattern = Pattern.compile("^[-\\+]?[.\\d]*$");
		return pattern.matcher(str).matches();
	}

	/**
	 * 判断list中字符串是否被指定的字串包含
	 * 
	 * @param str
	 * @param list
	 * @return
	 */
	public static boolean checkInStr(String str, List<String> list) {
		for (String l : list) {
			if (str.indexOf(l) != -1) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 字符串数组转字符串
	 * 
	 * @param sep
	 * @param list
	 * @return
	 */
	public static String StringListToString(List<String> list, String sep) {
		String str = null;
		if (list != null && list.size() > 0) {
			str = list.get(0);
			for (int i = 1; i < list.size(); i++) {
				String tmp = sep + list.get(i);
				str = str + tmp;
			}
		}
		return str;
	}

	/**
	 * 判断是否为数字或字母
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumericAndABC(String str) {
		if (str == null || str.equals("")) {
			return false;
		}
		Pattern pattern = Pattern.compile("[A-Z,a-z,0-9]*");
		return pattern.matcher(str).matches();
	}

	/**
	 * 判断是否为正确的电话号码
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isPhoneNum(String str) {
		if (str == null || str.equals("")) {
			return false;
		}
		Pattern pattern = Pattern.compile("[0-9,#,*,-]*");
		return pattern.matcher(str).matches();
	}

	/**
	 * @param hangup
	 * @return
	 */
	public static String getHangupDesc(String type) {
		String desc = "未知错误";
		int hangup = (type != null && type.length() != 0 ? Integer
				.parseInt(type) : -999);
		switch (hangup) {
		case 1:
			desc = "结束通话";
			break;
		case 2:
			desc = "账户欠费或者设置的通话时间到";
			break;
		case -1:
			desc = "【异常】被叫没有振铃就收到了挂断消息!";
			break;
		case -2:
			desc = "【异常】呼叫超时没有接通被挂断!";
			break;
		case -3:
			desc = "【异常】回拨: 主叫接通了主叫挂断!";
			break;
		case -4:
			desc = "【异常】回拨: 主叫通道创建了被挂断!";
			break;
		case -5:
			desc = "【异常】被叫通道建立了被挂断!";
			break;
		case -6:
			desc = "【异常】系统鉴权失败!";
			break;
		case -7:
			desc = "【异常】第三方鉴权失败!";
			break;
		case -8:
			desc = "【异常】直拨: 被叫振铃了挂断!";
			break;
		case -9:
			desc = "【异常】回拨: 被叫振铃了挂断!";
			break;
		case -10:
			desc = "【异常】回拨: 主叫振铃了挂断!";
			break;
		}

		return desc;
	}


	public static long getFileSize(String path) {
		File f = new File(path);
		if (f.exists()) {
			return f.length();
		}
		return 0;
	}

	/**
	 * 判断strs数组是否包含list集合外的字符串
	 * 
	 * @param list
	 * @param strs
	 * @return
	 */
	public static boolean checkInStr(List<String> list, String[] strs) {
		for (String str : strs) {
			if (!list.contains(str)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * startNum后4位数字累加count次
	 * 
	 * @param startNum
	 *            基础号码
	 * @param count
	 *            累加次数
	 * @return
	 */
	public static String NumberCumu(String startNum, int count) {
		String prefNum = startNum.substring(0, startNum.length() - 4);
		int suffNum = Integer
				.parseInt(startNum.substring(startNum.length() - 4));
		String numbers = startNum;
		for (int i = 0; i < count; i++) {
			String number = StringUtil.generateFourString(suffNum++ + "");
			if (number.length() == 5) {
				return "0";
			}
			numbers = numbers + "," + prefNum + number;
		}
		return numbers;
	}

	/**
	 * 验证数字的取值范围
	 * 
	 * @param num
	 * @param startNum
	 * @param endNum
	 * @return
	 */
	public static boolean NumberRange(String num, String startNum, String endNum) {
		try {
			if (Integer.parseInt(num) > Integer.parseInt(endNum)
					|| Integer.parseInt(num) < Integer.parseInt(startNum)) {
				return false;
			}
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

	/**
	 * 手机号验证
	 * 
	 * @param str
	 * @return 验证通过返回true
	 */
	public static boolean isMobile(String str) {
		Pattern p = null;
		Matcher m = null;
		boolean b = false;
		p = Pattern.compile("^[1][3,4,5,8][0-9]{9}$"); // 验证手机号
		m = p.matcher(str);
		b = m.matches();
		return b;
	}

	/**
	 * 电话号码验证
	 * 
	 * @param str
	 * @return 验证通过返回true
	 */
	public static boolean isPhone(String str) {
		Pattern p1 = null, p2 = null;
		Matcher m = null;
		boolean b = false;
		p1 = Pattern.compile("^[0][1-9]{2,3}-[0-9]{5,10}$"); // 验证带区号的
		p2 = Pattern.compile("^[1-9]{1}[0-9]{5,8}$"); // 验证没有区号的
		if (str.length() > 9) {
			m = p1.matcher(str);
			b = m.matches();
		} else {
			m = p2.matcher(str);
			b = m.matches();
		}
		return b;
	}

	/**
	 * 
	 * @param url
	 * @return
	 */
	public static String getProtocol(String url) {
		int index = url.indexOf(":");
		String subString = url.substring(0, index);
		return subString;
	}

	/**
	 * 对象转xml
	 * 
	 * @param obj
	 * @throws JAXBException
	 */
	public static String Object2Xml(Object obj) throws JAXBException {
		StringWriter writer = new StringWriter();
		JAXBContext context = JAXBContext.newInstance(obj.getClass());
		Marshaller marshaller = context.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		marshaller.marshal(obj, writer);
		return writer.toString();
	}

	/**
	 * 转换list to String
	 * 
	 * @param ids
	 * @return
	 */
	public static String changeListToString(List<String> ids) {
		StringBuilder groupIdStr = new StringBuilder();
		for (String id : ids) {
			groupIdStr.append("'").append(id).append("',");
		}
		groupIdStr.deleteCharAt(groupIdStr.length() - 1);
		return groupIdStr.toString();
	}

	/**
	 * 判断字符串是否为空
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {
		return str == null || str.equals("");
	}

	/**
	 * 判断字符串是否为非空
	 * 
	 * @param str
	 * @return
	 */
	public static boolean notEmpty(String str) {
		return str != null && !str.equals("");
	}

	/**
	 * Json转换为XML
	 * 
	 * @param json
	 * @return
	 */
	public static String ConvertJsonToXml(String json) {
		return ConvertJsonToXml(json, "Request");
	}

	/**
	 * Json转换为XML
	 * 
	 * @param json
	 * @param root
	 * @return
	 */
	public static String ConvertJsonToXml(String json, String root) {
		JSONObject jsonObject = JSONObject.fromObject(json);
		Object[] names = jsonObject.names().toArray();
		StringBuilder xml = new StringBuilder(
				"<?xml version='1.0' encoding='utf-8'?>\n<").append(root)
				.append(">");
		for (Object name : names) {
			xml.append("\n  <").append(name).append(">")
					.append(jsonObject.get(name)).append("</").append(name)
					.append(">");
		}
		xml.append("\n</").append(root).append(">");

		return xml.toString();
	}


	/**
	 * 验证数字的取值范围
	 * 
	 * @param num
	 * @param startNum
	 * @param endNum
	 * @return
	 */
	public static boolean isOverRange(int num, int startNum, int endNum) {
		return num > endNum || num < startNum;
	}

	public static String stringBuilder(String... strs) {
		StringBuilder builder = new StringBuilder();
		for (String str : strs) {
			builder.append(str);
		}
		return builder.toString();
	}

	/**
	 * 如果str为null则返回“”
	 * 
	 * @param str
	 * @return
	 */
	public static String emptyString(String str) {
		return isEmpty(str) ? "" : str;
	}

	// 以下方法为AppServer移植时添加

	/**
	 * @Title: getUserAcc
	 * @Description: 根据appId userName获取useracc
	 * @param @param appId
	 * @param @param userName
	 * @param @return
	 * @return String
	 * @throws
	 */
	public static String getUserAcc(String appId, String userName) {
		StringBuilder builder = new StringBuilder();
		builder.append(appId);
		builder.append("#");
		builder.append(userName);
		return builder.toString();
	}

	/**
	 * @Title: isBlank
	 * @Description: 判断是否为空
	 * @param @param str
	 * @param @return
	 * @return boolean
	 * @throws
	 */
	public static boolean isBlank(String str) {
		return org.apache.commons.lang.StringUtils.isBlank(str);
	}

	/**
	 * @Title: getUserNameFormUserAcc
	 * @Description: 根据userAcc获取userName
	 * @param @param userAcc
	 * @param @return
	 * @return String
	 * @throws
	 */
	public static String getUserNameFormUserAcc(String userAcc) {
		if (StringUtils.isEmpty(userAcc)) {
			return "";
		}
		String[] strs = userAcc.split("#");
		if (strs != null && strs.length > 1) {
			return strs[1];
		} else {
			return userAcc;
		}
	}

	/**
	 * @Title: isNotBlank
	 * @Description: 判断是否为空
	 * @param @param str
	 * @param @return
	 * @return boolean
	 * @throws
	 */
	public static boolean isNotBlank(String str) {
		return org.apache.commons.lang.StringUtils.isNotBlank(str);
	}

	/**
	 * @Title: getAppIdFormUserAcc
	 * @Description: 根据userAcc获取appId
	 * @param @param userAcc
	 * @param @return
	 * @return String
	 * @throws
	 */
	public static String getAppIdFormUserAcc(String userAcc) {
		if (StringUtils.isEmpty(userAcc)) {
			return "";
		}
		String[] strs = userAcc.split("#");
		if (strs != null && strs.length > 1) {
			return strs[0];
		} else {
			return "";
		}
	}
}
