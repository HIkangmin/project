package com.munbanggu.company.controller;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.munbanggu.cart.service.CartService;
import com.munbanggu.cart.vo.CartVO;
import com.munbanggu.common.base.BaseController;
import com.munbanggu.company.service.CompanyService;
import com.munbanggu.company.vo.CompanyVO;
import com.munbanggu.goods.vo.GoodsVO;
import com.munbanggu.member.vo.MemberVO;

@Controller("companyController")
@RequestMapping(value="/company")
public class CompanyControllerImpl extends BaseController implements CompanyController{
	@Autowired
	private CartService cartService;
	@Autowired
	private CartVO cartVO;
	@Autowired
	private MemberVO memberVO;
	@Autowired
	private CompanyService companyService;
 
	@Autowired
	private CompanyVO companyVO;	
	
	@RequestMapping(value="/companyList.do" ,method = RequestMethod.GET)
	public ModelAndView companyMain(HttpServletRequest request, HttpServletResponse response)  throws Exception {
		String viewName=(String)request.getAttribute("viewName");
		ModelAndView mav = new ModelAndView(viewName);
		HttpSession session=request.getSession();
 
		Map<String ,List> companyMap=companyService.companyList(companyVO);
		//session.setAttribute("companyMap", companyMap);//장바구니 목록 화면에서 상품 주문 시 사용하기 위해서 장바구니 목록을 세션에 저장한다.
		mav.addObject("companyMap", companyMap);
		return mav;
	}
	@RequestMapping(value="/addCompany.do" ,method = RequestMethod.POST,produces = "application/text; charset=utf8")
	public  @ResponseBody String addCompany(@RequestParam("goods_id") int goods_id,
			                    HttpServletRequest request, HttpServletResponse response)  throws Exception{
		HttpSession session=request.getSession();
		memberVO=(MemberVO)session.getAttribute("memberInfo");
		String member_id=memberVO.getMember_id();
		
		cartVO.setMember_id(member_id);
		//카트 등록전에 이미 등록된 제품인지 판별한다.
		cartVO.setGoods_id(goods_id);
		cartVO.setMember_id(member_id);
		boolean isAreadyExisted=cartService.findCartGoods(cartVO);
		System.out.println("isAreadyExisted:"+isAreadyExisted);
		if(isAreadyExisted==true){
			return "already_existed";
		}else{
			cartService.addGoodsInCart(cartVO);
			return "add_success";
		}
	}
	
	@RequestMapping(value="/modifyCompany.do" ,method = RequestMethod.POST)
	public @ResponseBody String  modifyCompany(@RequestParam("company_id") int goods_id,
			                                  
			                                    HttpServletRequest request, HttpServletResponse response)  throws Exception{
		HttpSession session=request.getSession();
		memberVO=(MemberVO)session.getAttribute("memberInfo");
		String member_id=memberVO.getMember_id();
		cartVO.setGoods_id(goods_id);
		cartVO.setMember_id(member_id);
 
		boolean result=companyService.modifyCompany(companyVO);
		
		if(result==true){
		   return "modify_success";
		}else{
			  return "modify_failed";	
		}
		
	}
	
	@RequestMapping(value="/removeCompany.do" ,method = RequestMethod.POST)
	public ModelAndView removeCompany(@RequestParam("company_id") int company_id,
			                          HttpServletRequest request, HttpServletResponse response)  throws Exception{
		ModelAndView mav=new ModelAndView();
		companyService.removeCompany(company_id);
		mav.setViewName("redirect:/company/companyList.do");
		return mav;
	}
}
