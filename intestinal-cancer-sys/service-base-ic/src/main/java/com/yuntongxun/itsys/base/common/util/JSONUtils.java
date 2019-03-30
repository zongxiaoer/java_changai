package com.yuntongxun.itsys.base.common.util;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.TypeAdapter;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.yuntongxun.itsys.base.po.Menu;

/**
 * Json Util
 * 
 * @author Lake.zhang
 *
 */
public class JSONUtils {

	private static Gson gson = null;

	static {
		gson = new Gson();// todo yyyy-MM-dd HH:mm:ss
	}

	public static synchronized Gson newInstance() {
		if (gson == null) {
			gson = new Gson();
		}
		return gson;
	}

	public static String toJson(Object obj) {
		 Gson gson = new GsonBuilder().serializeNulls().create();
		return gson.toJson(obj);
	}

	public static <T> T toBean(String json, Class<T> clz) {

		return gson.fromJson(json, clz);
	}

	public static <T> Map<String, T> toMap(String json, Class<T> clz) {
		Map<String, JsonObject> map = gson.fromJson(json, new TypeToken<Map<String, JsonObject>>() {
		}.getType());
		Map<String, T> result = new HashMap<>();
		for (String key : map.keySet()) {
			result.put(key, gson.fromJson(map.get(key), clz));
		}
		return result;
	}

	public static Map<String, Object> toMap(String json) {
		Map<String, Object> map = gson.fromJson(json, new TypeToken<Map<String, Object>>() {
		}.getType());
		return map;
	}

	public static <T> List<T> toList(String json, Class<T> clz) {
		JsonArray array = new JsonParser().parse(json).getAsJsonArray();
		List<T> list = new ArrayList<>();
		for (final JsonElement elem : array) {
			list.add(gson.fromJson(elem, clz));
		}
		return list;
	}

	/**
	 * 将对象转换成json格式(并自定义日期格式)
	 * 
	 * @param ts
	 * @return
	 */
	public static String objectToJsonDateSerializer(Object ts, String dateformat) {
		if (StringUtil.isEmpty(dateformat)) {
			dateformat = "yyyy-MM-dd HH:mm:ss";
		}
		final String formatMath = dateformat;
		String jsonStr = null;
		Gson gson = new GsonBuilder().registerTypeHierarchyAdapter(Date.class, new JsonSerializer<Date>() {
			public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
				SimpleDateFormat format = new SimpleDateFormat(formatMath);
				return new JsonPrimitive(format.format(src));
			}
		}).setDateFormat(dateformat).serializeNulls().create();
		if (gson != null) {
			jsonStr = gson.toJson(ts);
		}
		return jsonStr;
	}

	/**
	 * 将json转换为objeect，可制定json字符串中的日期转换格式
	 * 
	 * @param jsonStr
	 * @param cl
	 * @param pattern
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T jsonToBeanDateSerializer(String jsonStr, Class<T> cl, String pattern) {
		Object obj = null;
		if (StringUtil.isEmpty(pattern)) {
			pattern = "yyyy-MM-dd HH:mm:ss";
		}
		final String formats = pattern;
		Gson gson = new GsonBuilder().registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
			public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
					throws JsonParseException {
				SimpleDateFormat format = new SimpleDateFormat(formats);
				String dateStr = json.getAsString();
				try {
					return format.parse(dateStr);
				} catch (ParseException e) {
					e.printStackTrace();

				}
				return null;
			}
		}).setDateFormat(pattern).serializeNulls().create();
		if (gson != null) {
			obj = gson.fromJson(jsonStr, cl);
		}
		return (T) obj;
	}

	/**
	 * 将json转换为object 
	 * @param <T>
	 * @param json
	 * @param c
	 * @return
	 */
	public static <T> Object JsonToObject(String json, Class<T> type) {

		Gson gson = new Gson();
		return gson.fromJson(json, type);
	}

	/**
	 * 格式化json为自定义对象（可避免转换时int被转换为double类型的问题） 调用格式：list = (List)
	 * JSONUtils.fromJson(local, new TypeToken<List>(){});
	 * 
	 * @param json
	 * @param typeToken
	 * @return
	 */
	public static <T> T fromJson(String json, TypeToken<T> typeToken) {

		Gson gson = new GsonBuilder()
				/**
				 * 重写map的反序列化
				 */
				.registerTypeAdapter(new TypeToken<List>() {
				}.getType(), new MapTypeAdapter()).create();

		return gson.fromJson(json, typeToken.getType());
	}

	/**
	 * 
	 * 重写TypeAdapter
	 * 
	 * @author zhangzl
	 *
	 */
	public static class MapTypeAdapter extends TypeAdapter<Object> {

		@Override
		public Object read(JsonReader in) throws IOException {
			JsonToken token = in.peek();
			switch (token) {
			case BEGIN_ARRAY:
				List<Object> list = new ArrayList<Object>();
				in.beginArray();
				while (in.hasNext()) {
					list.add(read(in));
				}
				in.endArray();
				return list;

			case BEGIN_OBJECT:
				Map<String, Object> map = new LinkedTreeMap<String, Object>();
				in.beginObject();
				while (in.hasNext()) {
					map.put(in.nextName(), read(in));
				}
				in.endObject();
				return map;

			case STRING:
				return in.nextString();

			case NUMBER:
				/**
				 * 改写数字的处理逻辑，将数字值分为整型与浮点型。
				 */
				double dbNum = in.nextDouble();

				// 数字超过long的最大值，返回浮点类型
				if (dbNum > Long.MAX_VALUE) {
					return dbNum;
				}

				// 判断数字是否为整数值
				long lngNum = (long) dbNum;
				if (dbNum == lngNum) {
					return lngNum;
				} else {
					return dbNum;
				}

			case BOOLEAN:
				return in.nextBoolean();

			case NULL:
				in.nextNull();
				return null;

			default:
				throw new IllegalStateException();
			}
		}

		@Override
		public void write(JsonWriter out, Object value) throws IOException {
			// 序列化无需实现
		}
	}
	/**
	 * 将一个 Map 对象转化为一个 JavaBean
	 *
	 * @param type
	 *            要转化的类型
	 * @param map
	 *            包含属性值的 map
	 * @return 转化出来的 JavaBean 对象
	 * @throws IntrospectionException
	 *             如果分析类属性失败
	 * @throws IllegalAccessException
	 *             如果实例化 JavaBean 失败
	 * @throws InstantiationException
	 *             如果实例化 JavaBean 失败
	 * @throws InvocationTargetException
	 *             如果调用属性的 setter 方法失败
	 */
	public static Object convertMap(Class type, Map map)
			throws IntrospectionException, IllegalAccessException,
			InstantiationException, InvocationTargetException {
		BeanInfo beanInfo = Introspector.getBeanInfo(type); // 获取类属性
		Object obj = type.newInstance(); // 创建 JavaBean 对象

		// 给 JavaBean 对象的属性赋值
		PropertyDescriptor[] propertyDescriptors = beanInfo
				.getPropertyDescriptors();
		for (int i = 0; i < propertyDescriptors.length; i++) {
			PropertyDescriptor descriptor = propertyDescriptors[i];
			String propertyName = descriptor.getName();

			if (map.containsKey(propertyName)) {
				// 下面一句可以 try 起来，这样当一个属性赋值失败的时候就不会影响其他属性赋值。
				Object value = map.get(propertyName);

				Object[] args = new Object[1];
				args[0] = value;

				descriptor.getWriteMethod().invoke(obj, args);
			}
		}
		return obj;
	}



}
