package com.yuntongxun.itsys.base.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.yuntongxun.itsys.base.common.AbstractController;
import com.yuntongxun.itsys.base.common.exception.ItSysException;
import com.yuntongxun.itsys.base.service.WorkflowService;
/**
 * 用户管理
 * @author Lake.zhang
 *
 */
@RestController
public class WorkflowController extends AbstractController {

	@Autowired
	private WorkflowService workflowService;
	private final Logger log = LogManager.getLogger(WorkflowController.class);
	/**
	 * 获取当前用户可以发起的流程列表
	 */
	@RequestMapping(value="/workflow/creat/list",method=RequestMethod.POST)
	public String getCreatList(HttpServletRequest req,HttpServletResponse resp,@RequestBody String body)throws ItSysException{
		
		return null;
	}
	
	/**
	 * 创建流程
	 */
	@RequestMapping(value="/workflow/creat",method=RequestMethod.POST)
	public String creatList(HttpServletRequest req,HttpServletResponse resp,@RequestBody String body)throws ItSysException{
		
		return null;
	}
	
	/**
	 * 获取当前用户待办流程列表
	 */
	@RequestMapping(value="/workflow/todo/list",method=RequestMethod.POST)
	public String getTodoList(HttpServletRequest req,HttpServletResponse resp,@RequestBody String body)throws ItSysException{
		
		return null;
	}
	
	/**
	 * 获取当前用户已办流程列表
	 */
	@RequestMapping(value="/workflow/done/list",method=RequestMethod.POST)
	public String getDoneList(HttpServletRequest req,HttpServletResponse resp,@RequestBody String body)throws ItSysException{
		
		return null;
	}
	
	/**
	 * 获取当前用户办结流程列表
	 */
	@RequestMapping(value="/workflow/over/list",method=RequestMethod.POST)
	public String getOverList(HttpServletRequest req,HttpServletResponse resp,@RequestBody String body)throws ItSysException{
		
		return null;
	}
	
	/**
	 * 根据id查询流程数据
	 */
	@RequestMapping(value="/workflow/get/{id}",method=RequestMethod.POST)
	public String get(HttpServletRequest req,HttpServletResponse resp,@RequestBody String body)throws ItSysException{
		
		return null;
	}
	
	/**
	 * 根据id签收一个待办流程
	 */
	@RequestMapping(value="/workflow/sign",method=RequestMethod.POST)
	public String sign(HttpServletRequest req,HttpServletResponse resp,@RequestBody String body)throws ItSysException{
		
		return null;
	}
	
	/**
	 * 审批通过流程
	 */
	@RequestMapping(value="/workflow/approval",method=RequestMethod.POST)
	public String approval(HttpServletRequest req,HttpServletResponse resp,@RequestBody String body)throws ItSysException{
		
		return null;
	}
	
	/**
	 * 驳回流程
	 */
	@RequestMapping(value="/workflow/reject",method=RequestMethod.POST)
	public String reject(HttpServletRequest req,HttpServletResponse resp,@RequestBody String body)throws ItSysException{
		
		return null;
	}
	
	/**
	 * 转发流程
	 */
	@RequestMapping(value="/workflow/transfer",method=RequestMethod.POST)
	public String transfer(HttpServletRequest req,HttpServletResponse resp,@RequestBody String body)throws ItSysException{
		
		return null;
	}
	
	@Override
	protected String getClassName() {
		// TODO Auto-generated method stub
		return WorkflowController.class.getName();
	}

}
