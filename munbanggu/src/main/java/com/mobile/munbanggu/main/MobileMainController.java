package com.mobile.munbanggu.main;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.munbanggu.common.base.BaseController;
import com.munbanggu.goods.service.GoodsService;
import com.munbanggu.goods.vo.GoodsVO;

@Controller("mobileMainController")
@RequestMapping(value="/mobile")
public class MobileMainController extends BaseController {
	@Autowired
	private GoodsService goodsService;

	@RequestMapping(value = "/main/main.do", produces ="application/json; charset=utf8", method =  RequestMethod.GET )
	@ResponseBody
	public String main(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session;
//		ModelAndView mav = new ModelAndView();
//		String viewName = (String) request.getAttribute("viewName");
//		mav.setViewName(viewName);

		Map<String, List<GoodsVO>> goodsMap = goodsService.listGoods();		
		ObjectMapper objectMapper = new ObjectMapper();
		String jsonString = objectMapper.writeValueAsString(goodsMap);
		
//		mav.addObject("goodsMap", goodsMap);
		
		
//        String userAgent = request.getHeader("User-Agent");
//        if (userAgent.contains("Android")) {
//            return "Hello, Android!";
//        } else {
//            return "Hello, World!";
//        }
		
		System.out.println( jsonString );
		
//		return mav;
		return jsonString;
	}

	/* #캔디 리스트 */
	@RequestMapping(value = "/main/candylist.do", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView candylist(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session;
		ModelAndView mav = new ModelAndView();
		String viewName = (String) request.getAttribute("viewName");
		mav.setViewName(viewName);

		session = request.getSession();
		session.setAttribute("side_menu", "user");
		Map<String, List<GoodsVO>> goodsMap = goodsService.listGoods();
		mav.addObject("goodsMap", goodsMap);
		return mav;
	}

	/* #스낵 리스트 */
	@RequestMapping(value = "/main/snacklist.do", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView snacklist(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session;
		ModelAndView mav = new ModelAndView();
		String viewName = (String) request.getAttribute("viewName");
		mav.setViewName(viewName);

		session = request.getSession();
		session.setAttribute("side_menu", "user");
		Map<String, List<GoodsVO>> goodsMap = goodsService.listGoods();
		mav.addObject("goodsMap", goodsMap);
		return mav;
	}

	/* #toy 리스트 */
	@RequestMapping(value = "/main/toylist.do", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView toylist(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session;
		ModelAndView mav = new ModelAndView();
		String viewName = (String) request.getAttribute("viewName");
		mav.setViewName(viewName);

		session = request.getSession();
		session.setAttribute("side_menu", "user");
		Map<String, List<GoodsVO>> goodsMap = goodsService.listGoods();
		mav.addObject("goodsMap", goodsMap);
		return mav;
	}

	/* #jon 리스트 */
	@RequestMapping(value = "/main/jonlist.do", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView jonlist(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session;
		ModelAndView mav = new ModelAndView();
		String viewName = (String) request.getAttribute("viewName");
		mav.setViewName(viewName);

		session = request.getSession();
		session.setAttribute("side_menu", "user");
		Map<String, List<GoodsVO>> goodsMap = goodsService.listGoods();
		mav.addObject("goodsMap", goodsMap);
		return mav;
	}

	/* #dry 리스트 */
	@RequestMapping(value = "/main/drylist.do", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView drylist(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session;
		ModelAndView mav = new ModelAndView();
		String viewName = (String) request.getAttribute("viewName");
		mav.setViewName(viewName);

		session = request.getSession();
		session.setAttribute("side_menu", "user");
		Map<String, List<GoodsVO>> goodsMap = goodsService.listGoods();
		mav.addObject("goodsMap", goodsMap);
		return mav;
	}
	@RequestMapping(value = "/shopinfo/*.do", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView shopinfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session;
		ModelAndView mav = new ModelAndView();
		String viewName = (String) request.getAttribute("viewName");
		mav.setViewName(viewName);
 
		return mav;
	}
	/*
	 * #dry 리스트
	 * 
	 * @RequestMapping(value= "/customer/noticeMain.do"
	 * ,method={RequestMethod.POST,RequestMethod.GET}) public ModelAndView
	 * noticeMain(HttpServletRequest request, HttpServletResponse response) throws
	 * Exception{ HttpSession session; ModelAndView mav=new ModelAndView(); String
	 * viewName=(String)request.getAttribute("viewName"); mav.setViewName(viewName);
	 * 
	 * session=request.getSession(); session.setAttribute("side_menu", "user");
	 * Map<String,List<GoodsVO>> goodsMap=goodsService.listGoods();
	 * mav.addObject("goodsMap", goodsMap); return mav; }
	 */
}
