package com.munbanggu.admin.company.controller;

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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.munbanggu.admin.company.service.AdminCompanyService;
import com.munbanggu.common.base.BaseController;
import com.munbanggu.company.vo.CompanyVO;

@Controller("adminCompanyController")
@RequestMapping(value="/admin/company")
public class AdminCompanyControllerImpl extends BaseController  implements AdminCompanyController{
	@Autowired
	private AdminCompanyService adminCompanyService;
	
	@RequestMapping(value="/adminCompanyMain.do" ,method={RequestMethod.POST,RequestMethod.GET})
	public ModelAndView adminCompanyMain(@RequestParam Map<String, Object> dateMap,
			                           HttpServletRequest request, HttpServletResponse response)  throws Exception{
		
		HttpSession session = request.getSession( ) ;
		session = request.getSession( ) ;
		session.setAttribute( "side_menu", "admin_mode" ) ; // 마이페이지 사이드 메뉴로 설정한다.
		
		
		String viewName;
		ModelAndView mav;

		String fixedSearchPeriod;

		int section = 0;
		int pageNum = 0;
		int countCompanyId = 0;
		int maxPage = 0;
		boolean isNagativePaging = false;
		boolean isOverPaging = false;

		String beginDate = null;
		String endDate = null;

		String searchType = null ;
		String searchWord = null ;
		
		if( (String) dateMap.get("search_type") != null  ) {
			searchType = dateMap.get("search_type").toString();
		}
		if( (String) dateMap.get("search_word") != null  ) {
			searchWord = dateMap.get("search_word").toString();
		}
		
		System.out.println("searchType : " + searchType);
		System.out.println("searchWord : " + searchWord);
		
		// 전달받은 date 처리
		fixedSearchPeriod = (String) dateMap.get("fixedSearchPeriod");
		if( 
			(dateMap.get("beginDate") == null || dateMap.get("beginDate").equals("")) 
			||
			(dateMap.get("endDate") == null || dateMap.get("endDate").equals("") ) ){
			String[] tempDate = calcSearchPeriod(fixedSearchPeriod).split(",");
			System.out.println("tempDate[0] : " + tempDate[0]);
			System.out.println("tempDate[1] : " + tempDate[1]);
			beginDate = tempDate[0];
			endDate = tempDate[1];
			
		}else {
			beginDate = dateMap.get("beginDate").toString() ;
			endDate = dateMap.get("endDate").toString() ;
			System.out.println("beginDate : " + beginDate);
			System.out.println("endDate : " + endDate);
		}

		// 기존에 페이징 중이던 날짜 또는 calcSearchPeriod에서 받아온 날짜 저장
		dateMap.put("beginDate", beginDate);
		dateMap.put("endDate", endDate);

		// DB에 전달 할 값을 설정
		HashMap<String, Object> condMap = new HashMap<String, Object>();
		condMap.put("beginDate", beginDate);
		condMap.put("endDate", endDate);
		condMap.put("search_type", searchType) ; 
		condMap.put("search_word", searchWord) ;	
		
		// DB에서 페이징에 필요한 count 구하기 ( maxPage )
		countCompanyId = adminCompanyService.countSelectCompanyList (condMap);
		if (countCompanyId == 0) {
			maxPage = 1;
		} else {
			maxPage = (int) Math.ceil(countCompanyId  / 10.0);
		}

		System.out.println("fixedSearchPeriod : " + fixedSearchPeriod);
		System.out.println("beginDate : " + beginDate);
		System.out.println("endDate : " + endDate);

		// 페이징 예외처리 구간
		viewName = (String) request.getAttribute("viewName");
		mav = new ModelAndView(viewName);
		try {
			if ((String) dateMap.get("section") != null) {
				section = Integer.parseInt(dateMap.get("section").toString());
			}
			if ((String) dateMap.get("pageNum") != null) {
				pageNum = Integer.parseInt(dateMap.get("pageNum").toString());
			}
			if (pageNum > maxPage) {
				// section 또는 page 가 기준보다 크게로 들어왔을 경우 최대 페이지로 이동
				isOverPaging = true;
				pageNum = maxPage;
				section = maxPage / 10;
			}
			if (section < 0 || pageNum < 1) {
				// section 또는 page 가 기준보다 작게로 들어왔을 경우 첫 페이지로 이동
				isNagativePaging = true;
				section = 0;
				pageNum = 1;
			}
//			기능추가로 인한 오작동으로 주석처리 
//			if (isNagativePaging || isOverPaging) {
//				RedirectView rv;
//				rv = new RedirectView(request.getContextPath() + "/admin/Company/adminCompanyMain.do?section=" + section
//						+ "&pageNum=" + pageNum );
//
//				System.out.println("isNagativePaging OR isOverPaging" + mav.toString());
//
//				rv.setExposeModelAttributes(false);
//				mav.setView(rv);
//				return mav;
//			}
		} catch (NumberFormatException e) {
			section = 0;
			pageNum = 1;
			System.err.println(e.toString());
			RedirectView rv;
			rv = new RedirectView(request.getContextPath() + "/admin/Company/adminCompanyMain.do?section=" + section
					+ "&pageNum=" + pageNum);
			System.out.println("is NumberFormatException " + mav.toString());
			rv.setExposeModelAttributes(false);
			mav.setView(rv);
			return mav;
		}

		condMap.put("section", section);
		condMap.put("pageNum", (pageNum - 1) * 10); // 페이징을 위해 DB에 전달, 쿼리에서만 씀으로 카멜
		condMap.put("search_type", searchType) ; 
		condMap.put("search_word", searchWord) ;	
		System.out.println("searchWord " + searchWord);
		ArrayList<CompanyVO> companyList = adminCompanyService.listCompany(condMap);

		String beginDate1[] = beginDate.split("-");
		String endDate1[] = endDate.split("-");	
		// jsp 파일일 화면 그리기를 위한 부분
		mav.addObject("beginYear", beginDate1[0]);
		mav.addObject("beginMonth", beginDate1[1]);
		mav.addObject("beginDay", beginDate1[2]);
		mav.addObject("endYear", endDate1[0]);
		mav.addObject("endMonth", endDate1[1]);
		mav.addObject("endDay", endDate1[2]);
		
		mav.addObject("section", section);
		mav.addObject("pageNum", pageNum);
		mav.addObject("maxPage", maxPage);
		mav.addObject("beginDate", beginDate);
		mav.addObject("endDate", endDate);
		mav.addObject("search_type", searchType) ; 
		mav.addObject("search_word", searchWord) ;
		
		mav.addObject("fixedSearchPeriod", fixedSearchPeriod);
		mav.addObject( "Company_list", companyList ) ;
		
		System.out.println("beginYear " + beginDate1[0]);
		System.out.println("beginMonth " + beginDate1[1]);
		System.out.println("beginDay " + beginDate1[2]);
		System.out.println("endYear " + endDate1[0]);
		System.out.println("endMonth " + endDate1[1]);
		System.out.println("endDay " + endDate1[2]);
		System.out.println("section " + section);
		System.out.println("pageNum " + pageNum);
		System.out.println("maxPage " + maxPage);
		

		return mav;
		
	}
	@RequestMapping(value="/companyDetail.do" ,method={RequestMethod.POST,RequestMethod.GET})
	public ModelAndView CompanyDetail(HttpServletRequest request, HttpServletResponse response)  throws Exception{
		String viewName=(String)request.getAttribute("viewName");
		ModelAndView mav = new ModelAndView(viewName);
		String company_id=request.getParameter("company_id");
		CompanyVO company_info=adminCompanyService.companyDetail(company_id);
		mav.addObject("company_info",company_info);
		return mav;
	}
 
	@RequestMapping(value="/modifyCompanyInfo.do" ,method={RequestMethod.POST,RequestMethod.GET})
	public void modifyCompanyInfo(HttpServletRequest request, HttpServletResponse response)  throws Exception{
		HashMap<String,String> companyMap=new HashMap<String,String>();
		String val[]=null;
		PrintWriter pw=response.getWriter();
 
		String company_id=request.getParameter("company_id");
		String mod_type=request.getParameter("mod_type");
		String value =request.getParameter("value");

		if("address".equals(mod_type)){
			val=value.split(",");
			companyMap.put("zipcode",val[0]);
			companyMap.put("roadAddress",val[1]);
			companyMap.put("jibunAddress", val[2]);
			if (val.length == 4)
				companyMap.put("namujiAddress",val[3] );
		}else {
			companyMap.put(mod_type, value);
		}

		companyMap.put("company_id", company_id);
		adminCompanyService.modifyCompanyInfo(companyMap);
		pw.print("mod_success");
		pw.close();		
		
	}
	@RequestMapping(value="/addCompanyInfo.do" ,method={RequestMethod.POST,RequestMethod.GET})
	public ModelAndView addCompanyInfo(@RequestParam HashMap receiveMap,
									HttpServletRequest request, HttpServletResponse response)  throws Exception{
		ModelAndView mav = new ModelAndView();
		HashMap<String,String> companyMap=new HashMap<String,String>();

		
		adminCompanyService.addCompanyInfo(receiveMap);
		mav.setViewName("redirect:/admin/company/adminCompanyMain.do");
		return mav;
		
	}
	@RequestMapping(value="/modifyCompany.do" ,method={RequestMethod.POST,RequestMethod.GET})
	public ModelAndView modifyCompany(@RequestParam HashMap receiveMap,
									HttpServletRequest request, HttpServletResponse response)  throws Exception{
		ModelAndView mav = new ModelAndView();
		HashMap<String,String> companyMap=new HashMap<String,String>();

		
		adminCompanyService.modifyCompany(receiveMap);
		mav.setViewName("redirect:/admin/company/adminCompanyMain.do");
		return mav;
		
	}
	@RequestMapping(value="/deleteCompany.do" ,method={RequestMethod.POST})
	public ModelAndView deleteCompany(HttpServletRequest request, HttpServletResponse response)  throws Exception {
		ModelAndView mav = new ModelAndView();
		HashMap<String,String> companyMap=new HashMap<String,String>();
		String company_id=request.getParameter("company_id");
		String del_yn=request.getParameter("del_yn");
		companyMap.put("del_yn", del_yn);
		companyMap.put("company_id", company_id);
		
		adminCompanyService.modifyCompanyInfo(companyMap);
		mav.setViewName("redirect:/admin/company/adminCompanyMain.do");
		return mav;
		
	}
	@RequestMapping(value="/deleteCompanyInfo.do" ,method={RequestMethod.POST})
	public ModelAndView deleteCompanyInfo(HttpServletRequest request, HttpServletResponse response)  throws Exception {
		ModelAndView mav = new ModelAndView();
		HashMap<String,String> companyMap=new HashMap<String,String>();
		String company_id=request.getParameter("company_id");
 
 
		companyMap.put("company_id", company_id);
		
		adminCompanyService.deleteCompanyInfo(companyMap);
		mav.setViewName("redirect:/admin/company/adminCompanyMain.do");
		return mav;
		
	}		
}
