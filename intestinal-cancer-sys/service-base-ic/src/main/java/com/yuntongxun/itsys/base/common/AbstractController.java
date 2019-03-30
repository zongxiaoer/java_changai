/**
 * 
 */
package com.yuntongxun.itsys.base.common;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.ModelAndView;


/**
 * All of Controller parents class.
 * 
 * @version 1.0
 */
public abstract class AbstractController {
	protected final Logger logger = LogManager.getLogger();

	private static Map<Integer, AbstractController> controllerMap = new HashMap<Integer, AbstractController>();

	public AbstractController() {
	}

	/**
	 * Sub class must be Override
	 * 
	 * @return
	 */
	protected abstract String getClassName();

	public static enum DATAGRAM_TYPE {
		XML {
			public String getValue() {
				return "XML";
			}
		},
		JSON {
			public String getValue() {
				return "JSON";
			}
		};
		public abstract String getValue();
	}


	/**
	 * print start tag margin 1 with up.
	 * 
	 * @param name
	 * @param space
	 * @return
	 */
	public void printStartTag(String tagName) {
		logger.info("@Controller");
		printStartTag(tagName, MARGIN_0);
	}

	/**
	 * print start tag
	 * 
	 * @param name
	 * @param space
	 * @return
	 */
	public void printStartTag(String tagName, int space) {
		StringBuilder sb = new StringBuilder();
		switch (space) {
		case MARGIN_0:
			break;
		case MARGIN_1:
			sb.append("\r\n");
			break;
		case MARGIN_2:
			sb.append("\r\n\r\n");
			break;
		default:
			sb.append("\r\n");
			break;
		}
		sb.append("----------------------------[").append(tagName).append(" Start").append(
				"]-------------------------------");
		logger.info(sb.toString());
	}

	/**
	 * print end tag
	 * 
	 * @param name
	 * @param space
	 * @return
	 */
	public final void printEndTag(String tagName) {
		printEndTag(tagName, MARGIN_1);
	}

	/**
	 * distance of up or down
	 */
	public static final int MARGIN_0 = 0;
	public static final int MARGIN_1 = 1;
	public static final int MARGIN_2 = 2;

	/**
	 * print end tag
	 * 
	 * @param name
	 * @param space
	 * @return
	 */
	public final void printEndTag(String tagName, int space) {
		StringBuilder sb = new StringBuilder("----------------------------[").append(tagName).append(" End").append(
				"]-------------------------------");
		switch (space) {
		case MARGIN_0:
			break;
		case MARGIN_1:
			sb.append("\r\n");
			break;
		case MARGIN_2:
			sb.append("\r\n\r\n");
			break;
		default:
			sb.append("\r\n");
			break;
		}
		logger.info(sb.toString());
	}

	
	/**
	 * print http packet
	 * 
	 * @param log
	 * @param request
	 * @param body
	 */
	protected final void printHttpPacket(HttpServletRequest request, String body) {
		logger.info(getHttpRequestPacket(request, body));
	}

	 public static String getHttpRequestPacket(HttpServletRequest request, String body)
	  {
	    StringBuffer sb = new StringBuffer("@Received HTTP Packet - \r\n\r\n");
	    sb.append(request.getMethod() + " ");
	    sb.append(request.getRequestURI()).append(
	      "?" + request.getQueryString() + " ");
	    sb.append(request.getProtocol() + "\r\n");
	    
	    Enumeration<String> enumer = request.getHeaderNames();
	    while (enumer.hasMoreElements())
	    {
	      String name = (String)enumer.nextElement();
	      String value = request.getHeader(name);
	      sb.append(name + ": " + value + "\r\n");
	    }
	    if ((body != null) && (body.length() > 0)) {
	      sb.append("\r\n").append(body);
	    }
	    sb.append("\r\n\r\n");
	    return sb.toString();
	  }
	
	/**
	 * request parameter transfer object by parse json or xml.
	 * 
	 * @param type
	 * @param key
	 * @param body
	 * @return
	 * @throws Exception
	 */
	protected Object parser(DATAGRAM_TYPE type, String key, String body) throws Exception {
		return null;
	}



	/**
	 * 
	 * @param protoType
	 * @return
	 */
	public static AbstractController getController(int protoType) {
		return controllerMap.get(protoType);
	}
	


}
