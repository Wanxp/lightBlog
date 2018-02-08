package com.wanxp.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wanxp.pageModel.Colum;
import com.wanxp.pageModel.Content;
import com.wanxp.pageModel.DataGrid;
import com.wanxp.pageModel.Json;
import com.wanxp.pageModel.PageHelper;
import com.wanxp.service.ContentServiceI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

/**
 * Content管理控制器
 * 
 * @author John
 * 
 */
@Controller
@RequestMapping("/contentController")
public class ContentController extends BaseController {

	@Autowired
	private ContentServiceI contentService;


	/**
	 * 跳转到Content管理页面
	 * 
	 * @return
	 */
	@RequestMapping("/manager")
	public String manager(HttpServletRequest request) {
		return "/content/content";
	}

	/**
	 * 获取Content数据表格
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	public DataGrid dataGrid(Content content, PageHelper ph) {
		return contentService.dataGrid(content, ph);
	}
	/**
	 * 获取Content数据表格excel
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
	public void download(Content content, PageHelper ph,String downloadFields,HttpServletResponse response) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException{
		DataGrid dg = dataGrid(content,ph);		
		downloadFields = downloadFields.replace("&quot;", "\"");
		downloadFields = downloadFields.substring(1,downloadFields.length()-1);
		List<Colum> colums = JSON.parseArray(downloadFields, Colum.class);
		downloadTable(colums, dg, response);
	}
	/**
	 * 跳转到添加Content页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request) {
		Content content = new Content();
		return "/content/contentAdd";
	}

	/**
	 * 添加Content
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Json add(Content content) {
		Json j = new Json();		
		contentService.add(content);
		j.setSuccess(true);
		j.setMsg("添加成功！");		
		return j;
	}

	/**
	 * 跳转到Content查看页面
	 * 
	 * @return
	 */
	@RequestMapping("/view")
	public String view(HttpServletRequest request, Integer id) {
		Content content = contentService.get(id);
		request.setAttribute("content", content);
		return "/content/contentView";
	}

	/**
	 * 跳转到Content修改页面
	 * 
	 * @return
	 */
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, Integer id) {
		Content content = contentService.get(id);
		request.setAttribute("content", content);
		return "/content/contentEdit";
	}

	/**
	 * 修改Content
	 * 
	 * @param content
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(Content content) {
		Json j = new Json();		
		contentService.edit(content);
		j.setSuccess(true);
		j.setMsg("编辑成功！");		
		return j;
	}

	/**
	 * 删除Content
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(Integer id) {
		Json j = new Json();
		contentService.delete(id);
		j.setMsg("删除成功！");
		j.setSuccess(true);
		return j;
	}

}
