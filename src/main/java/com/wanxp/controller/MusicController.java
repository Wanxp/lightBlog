package com.wanxp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Content管理控制器
 * 
 * @author John
 * 
 */
@Controller
@RequestMapping("/musicController")
public class MusicController extends BaseController {
	/**
	 * 跳转到音乐播放管理页面
	 * 
	 * @return
	 */
	@RequestMapping("/manager")
	public String manager(HttpServletRequest request) {
		return "/examples/blue.monday/demo-01";
	}


}
