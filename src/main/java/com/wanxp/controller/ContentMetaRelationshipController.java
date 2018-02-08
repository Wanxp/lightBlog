package com.wanxp.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wanxp.pageModel.Colum;
import com.wanxp.pageModel.ContentMetaRelationship;
import com.wanxp.pageModel.DataGrid;
import com.wanxp.pageModel.Json;
import com.wanxp.pageModel.PageHelper;
import com.wanxp.service.ContentMetaRelationshipServiceI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

/**
 * ContentMetaRelationship管理控制器
 * 
 * @author John
 * 
 */
@Controller
@RequestMapping("/contentMetaRelationshipController")
public class ContentMetaRelationshipController extends BaseController {

	@Autowired
	private ContentMetaRelationshipServiceI contentMetaRelationshipService;


	/**
	 * 跳转到ContentMetaRelationship管理页面
	 * 
	 * @return
	 */
	@RequestMapping("/manager")
	public String manager(HttpServletRequest request) {
		return "/contentmetarelationship/contentMetaRelationship";
	}

	/**
	 * 获取ContentMetaRelationship数据表格
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	public DataGrid dataGrid(ContentMetaRelationship contentMetaRelationship, PageHelper ph) {
		return contentMetaRelationshipService.dataGrid(contentMetaRelationship, ph);
	}
	/**
	 * 获取ContentMetaRelationship数据表格excel
	 * 
	 * @param user
	 * @return
	 * @throws NoSuchMethodException 
	 * @throws SecurityException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 * @throws IOException 
	 */
	@RequestMapping("/download")
	public void download(ContentMetaRelationship contentMetaRelationship, PageHelper ph,String downloadFields,HttpServletResponse response) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException{
		DataGrid dg = dataGrid(contentMetaRelationship,ph);		
		downloadFields = downloadFields.replace("&quot;", "\"");
		downloadFields = downloadFields.substring(1,downloadFields.length()-1);
		List<Colum> colums = JSON.parseArray(downloadFields, Colum.class);
		downloadTable(colums, dg, response);
	}
	/**
	 * 跳转到添加ContentMetaRelationship页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request) {
		ContentMetaRelationship contentMetaRelationship = new ContentMetaRelationship();
		return "/contentmetarelationship/contentMetaRelationshipAdd";
	}

	/**
	 * 添加ContentMetaRelationship
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Json add(ContentMetaRelationship contentMetaRelationship) {
		Json j = new Json();		
		contentMetaRelationshipService.add(contentMetaRelationship);
		j.setSuccess(true);
		j.setMsg("添加成功！");		
		return j;
	}

	/**
	 * 跳转到ContentMetaRelationship查看页面
	 * 
	 * @return
	 */
	@RequestMapping("/view")
	public String view(HttpServletRequest request, Integer id) {
		ContentMetaRelationship contentMetaRelationship = contentMetaRelationshipService.get(id);
		request.setAttribute("contentMetaRelationship", contentMetaRelationship);
		return "/contentmetarelationship/contentMetaRelationshipView";
	}

	/**
	 * 跳转到ContentMetaRelationship修改页面
	 * 
	 * @return
	 */
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, Integer id) {
		ContentMetaRelationship contentMetaRelationship = contentMetaRelationshipService.get(id);
		request.setAttribute("contentMetaRelationship", contentMetaRelationship);
		return "/contentmetarelationship/contentMetaRelationshipEdit";
	}

	/**
	 * 修改ContentMetaRelationship
	 * 
	 * @param contentMetaRelationship
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(ContentMetaRelationship contentMetaRelationship) {
		Json j = new Json();		
		contentMetaRelationshipService.edit(contentMetaRelationship);
		j.setSuccess(true);
		j.setMsg("编辑成功！");		
		return j;
	}

	/**
	 * 删除ContentMetaRelationship
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(Integer id) {
		Json j = new Json();
		contentMetaRelationshipService.delete(id);
		j.setMsg("删除成功！");
		j.setSuccess(true);
		return j;
	}

}
