package com.wanxp.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wanxp.pageModel.Colum;
import com.wanxp.pageModel.Option;
import com.wanxp.pageModel.DataGrid;
import com.wanxp.pageModel.Json;
import com.wanxp.pageModel.PageHelper;
import com.wanxp.service.OptionServiceI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

/**
 * Option管理控制器
 * 
 * @author John
 * 
 */
@Controller
@RequestMapping("/optionController")
public class OptionController extends BaseController {

	@Autowired
	private OptionServiceI optionService;


	/**
	 * 跳转到Option管理页面
	 * 
	 * @return
	 */
	@RequestMapping("/manager")
	public String manager(HttpServletRequest request) {
		return "/option/option";
	}

	/**
	 * 获取Option数据表格
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	public DataGrid dataGrid(Option option, PageHelper ph) {
		return optionService.dataGrid(option, ph);
	}
	/**
	 * 获取Option数据表格excel
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
	public void download(Option option, PageHelper ph,String downloadFields,HttpServletResponse response) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException{
		DataGrid dg = dataGrid(option,ph);		
		downloadFields = downloadFields.replace("&quot;", "\"");
		downloadFields = downloadFields.substring(1,downloadFields.length()-1);
		List<Colum> colums = JSON.parseArray(downloadFields, Colum.class);
		downloadTable(colums, dg, response);
	}
	/**
	 * 跳转到添加Option页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request) {
		Option option = new Option();
		return "/option/optionAdd";
	}

	/**
	 * 添加Option
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Json add(Option option) {
		Json j = new Json();		
		optionService.add(option);
		j.setSuccess(true);
		j.setMsg("添加成功！");		
		return j;
	}

	/**
	 * 跳转到Option查看页面
	 * 
	 * @return
	 */
	@RequestMapping("/view")
	public String view(HttpServletRequest request, Integer id) {
		Option option = optionService.get(id);
		request.setAttribute("option", option);
		return "/option/optionView";
	}

	/**
	 * 跳转到Option修改页面
	 * 
	 * @return
	 */
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, Integer id) {
		Option option = optionService.get(id);
		request.setAttribute("option", option);
		return "/option/optionEdit";
	}

	/**
	 * 修改Option
	 * 
	 * @param option
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(Option option) {
		Json j = new Json();		
		optionService.edit(option);
		j.setSuccess(true);
		j.setMsg("编辑成功！");		
		return j;
	}

	/**
	 * 删除Option
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(Integer id) {
		Json j = new Json();
		optionService.delete(id);
		j.setMsg("删除成功！");
		j.setSuccess(true);
		return j;
	}

}
