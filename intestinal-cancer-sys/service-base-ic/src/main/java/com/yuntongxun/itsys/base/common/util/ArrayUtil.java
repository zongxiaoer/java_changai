package com.yuntongxun.itsys.base.common.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 
 * @author Arrays工具类
 *
 */
public class ArrayUtil {
	
	/**
	 * 比较两个数组的差集。
	 * @param t1
	 * @param t2
	 * @return
	 */
	public static <T> List<T> compare(T[] t1, T[] t2) {    
	      List<T> list1 = Arrays.asList(t1);    
	      List<T> list2 = new ArrayList<T>();    
	      for (T t : t2) {    
	          if (!list1.contains(t)) {    
	              list2.add(t);    
	          }    
	      }    
	      return list2;    
	  }   
}
