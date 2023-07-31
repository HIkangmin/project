package com.munbanggu.admin.customer.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.munbanggu.admin.customer.service.AdminCustomerService;
import com.munbanggu.common.base.BaseController;
import com.munbanggu.common.service.CommonService;
import com.munbanggu.customer.vo.BoardVO;

@Controller("adminCustomerController")
@RequestMapping(value="/admin/customer")
public class AdminCustomerControllerImpl extends BaseController implements AdminCustomerController {
		
		@Autowired
		private AdminCustomerService adminCustomerService;
		
		// 공용 SQL 사용.
		@Autowired
		private CommonService commonService ;
		
		//공지사항 메인페이지
		@RequestMapping(value = "/adminNoticeMain.do", method = RequestMethod.GET)
		public ModelAndView adminNoticeMain(@RequestParam Map<String, Object> dateMap,
				HttpServletRequest request)  throws Exception {
			
			/*
			 * 리팩토링시 기간을 beginDate, endDate로 줄이는 과정 필요( jsp에서만 쪼개서 처리하도록 )
			 * 리팩토링시 페이징에 필요한 클래스를 util.web. Pagination, Serach, Period 같은 형식으로 정리 필요
			 * 리팩토링시 페이징에 사용되는 countOrderId를 기존 쿼리문에 추가 또는 util.web 쪽으로 빼는 작업 필요 
			 */
			System.out.println(" admin dateMap = " + dateMap.toString());

			HttpSession session = request.getSession( ) ;
			session = request.getSession( ) ;
			session.setAttribute( "side_menu", "admin_mode" ) ; // 마이페이지 사이드 메뉴로 설정한다.
			
			String viewName;
			ModelAndView mav;

			String fixedSearchPeriod;

			int section = 0;
			int pageNum = 0;
			int countSelect = 0;
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

			
			/// commonService의 sql을 사용하기 위한 부분, service DAO등을 수정하지 안항도 되지만, 
			/// 사용 할 VO에 String result_type 추가  
			String[] searchTypeList  = "notice_id,notice_title,notice_content,notice_date,member_id".split(",");
			condMap.put("search_type_list", searchTypeList) ; 
			condMap.put("target_table", "munbanggu_notice") ; 
			condMap.put("date_column", "notice_date") ;
			condMap.put("result_type", "NoticeVO") ;
			
			
			condMap.put("search_type", searchType) ; 
			condMap.put("search_word", searchWord) ;
		
			
			// DB에서 페이징에 필요한 count 구하기 ( maxPage )
//			
			countSelect = commonService.commonCount(condMap);
			if (countSelect == 0) {
				maxPage = 1;
			} else {
				maxPage = (int) Math.ceil(countSelect / 10.0);
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

			} catch (NumberFormatException e) {
				section = 0;
				pageNum = 1;
				System.err.println(e.toString());
				RedirectView rv;
				rv = new RedirectView(request.getContextPath() + "/admin/customer/adminNoticeMain.do?section=" + section
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
			List<Object> selectList = commonService.commonSearchList(condMap);

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
			mav.addObject("selectList", selectList);
			
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

		//공지사항 단일조회
		@Override
		@RequestMapping(value="/adminNoticeDetail.do" ,method = RequestMethod.GET)
		public ModelAndView selectNotice(@RequestParam("notice_id") String notice_id,
				HttpServletRequest request, HttpServletResponse response)  throws Exception {
			String viewName = (String)request.getAttribute("viewName");
			ModelAndView mav = new ModelAndView(viewName);
			List selectNotice = adminCustomerService.selectNoticeDetail(notice_id);
			mav.addObject("selectNotice", selectNotice);
			return mav;
		}
		
		//공지사항 수정
		@RequestMapping(value="/modifyNotice.do" ,method={RequestMethod.POST})
		public ResponseEntity modifyNotice( @RequestParam("notice_id") String notice_id,
				                     @RequestParam("notice_title") String notice_title,
				                     @RequestParam("notice_content") String notice_content,
				HttpServletRequest request, HttpServletResponse response)  throws Exception {
			response.setContentType("text/html; charset=UTF-8");
			request.setCharacterEncoding("utf-8");
			
			Map<String,String> noticeMap=new HashMap<String,String>();
			noticeMap.put("notice_id", notice_id);
			noticeMap.put("notice_title", notice_title);
			noticeMap.put("notice_content", notice_content);
			adminCustomerService.modifyNotice(noticeMap);
			
			String script = "<script>alert('공지사항이 수정되었습니다.'); "
					+ "location.href='"+request.getContextPath()+"/admin/customer/adminNoticeMain.do'; </script>";
			ResponseEntity resEntity = null;
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.add("Content-Type", "text/html; charset=utf-8");
			
			resEntity =new ResponseEntity(script, responseHeaders, HttpStatus.OK);
			return resEntity;
		}
		
		//공지사항 삭제하기
		@RequestMapping(value="/deleteNotice.do" ,method = RequestMethod.POST)
		public ResponseEntity deleteNotice(@RequestParam("notice_id") String notice_id,
				                          HttpServletRequest request, HttpServletResponse response)  throws Exception{
			response.setContentType("text/html; charset=UTF-8");
			request.setCharacterEncoding("utf-8");
			
			adminCustomerService.deleteNotice(notice_id);
			
			String script = "<script>alert('공지사항이 삭제되었습니다.'); "
					+ "location.href='"+request.getContextPath()+"/admin/customer/adminNoticeMain.do'; </script>";
			ResponseEntity resEntity = null;
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.add("Content-Type", "text/html; charset=utf-8");
			
			resEntity =new ResponseEntity(script, responseHeaders, HttpStatus.OK);
			return resEntity;
		}
		
		//공지사항 입력페이지
		@RequestMapping(value = "/adminNoticeWrite.do")
		public ModelAndView adminNoticeWrite(HttpServletRequest request, HttpServletResponse response)  throws Exception {
		    String viewName = (String)request.getAttribute("viewName");
			ModelAndView mav = new ModelAndView(viewName);
			return mav;
		}
		
		//공지사항 등록
		@Override
		@RequestMapping(value="/addNotice.do" ,method = RequestMethod.GET)
		public ResponseEntity addNotice(@ModelAttribute("BoardVO") BoardVO BoardVO,
				                HttpServletRequest request, HttpServletResponse response) throws Exception {
			response.setContentType("text/html; charset=UTF-8");
			request.setCharacterEncoding("utf-8");
			String message = null;
			ResponseEntity resEntity = null;
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.add("Content-Type", "text/html; charset=utf-8");
			try {
			    adminCustomerService.addNotice( BoardVO );
			    message = "<script>alert('공지사항이 등록되었습니다.'); "
						+ "location.href='"+request.getContextPath()+"/admin/customer/adminNoticeMain.do'; </script>";
			    
			}catch(Exception e) {
				message = "<script>alert('작업 중 오류가 발생했습니다. 다시 시도해 주세요.'); "
						+ "location.href='"+request.getContextPath()+"/admin/customer/adminNoticeWrite.do'; </script>";
				e.printStackTrace();
			}
			resEntity =new ResponseEntity(message, responseHeaders, HttpStatus.OK);
			return resEntity;
		}
		
		//공지사항 검색
		@RequestMapping(value = "/NoticekeywordSearch.do", method = RequestMethod.POST)
		public ModelAndView NoticekeywordSearch(@RequestParam("keyword") String keyword) {
		    ModelAndView mav = new ModelAndView("redirect:/admin/customer/adminNoticeMain.do");
		    
		    if (keyword == null || keyword.equals("")) {
		        return mav;
		    }
		    
			List<String> keywordList =adminCustomerService.NoticekeywordSearch(keyword);
		    mav.addObject("notice_id", keywordList);
		    
		    return mav;
		}

		
		
		
		
		
		//FAQ
		
		//FAQ 메인페이지
		@RequestMapping(value = "/adminFaqMain.do", method = RequestMethod.GET)
		public ModelAndView adminFaqMain(@RequestParam Map<String, Object> dateMap,
				HttpServletRequest request)  throws Exception {
			
			HttpSession session = request.getSession( ) ;
			session = request.getSession( ) ;
			session.setAttribute( "side_menu", "admin_mode" ) ; // 마이페이지 사이드 메뉴로 설정한다.
			
			String viewName;
			ModelAndView mav;

			String fixedSearchPeriod;

			int section = 0;
			int pageNum = 0;
			int countSelect = 0;
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

			
			/// commonService의 sql을 사용하기 위한 부분, service DAO등을 수정하지 안항도 되지만, 
			/// 사용 할 VO에 String result_type 추가  
			String[] searchTypeList  = "faq_id,faq_title,faq_content,faq_date,member_id".split(",");
			condMap.put("search_type_list", searchTypeList) ; 
			condMap.put("target_table", "munbanggu_faq") ; 
			condMap.put("date_column", "faq_date") ;
			condMap.put("result_type", "FaqVO") ;
			
			
			condMap.put("search_type", searchType) ; 
			condMap.put("search_word", searchWord) ;
		
			
			// DB에서 페이징에 필요한 count 구하기 ( maxPage )
//			
			countSelect = commonService.commonCount(condMap);
			if (countSelect == 0) {
				maxPage = 1;
			} else {
				maxPage = (int) Math.ceil(countSelect / 10.0);
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

			} catch (NumberFormatException e) {
				section = 0;
				pageNum = 1;
				System.err.println(e.toString());
				RedirectView rv;
				rv = new RedirectView(request.getContextPath() + "/admin/customer/adminNoticeMain.do?section=" + section
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
			List<Object> selectList = commonService.commonSearchList(condMap);

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
			mav.addObject("selectList", selectList);
			
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

		//FAQ 단일조회
		@Override
		@RequestMapping(value="/adminFaqDetail.do" ,method = RequestMethod.GET)
		public ModelAndView selectFaq(@RequestParam("faq_id") String faq_id,
				HttpServletRequest request, HttpServletResponse response)  throws Exception {
			String viewName = (String)request.getAttribute("viewName");
			ModelAndView mav = new ModelAndView(viewName);
			List selectFaq = adminCustomerService.selectFaqDetail(faq_id);
			mav.addObject("selectFaq", selectFaq);
			return mav;
		}
		
		//FAQ 수정
		@RequestMapping(value="/modifyFaq.do" ,method={RequestMethod.POST })
		public ResponseEntity modifyFaq( @RequestParam("faq_id") String faq_id,
				                     @RequestParam("faq_title") String faq_title,
				                     @RequestParam("faq_content") String faq_content,
				HttpServletRequest request, HttpServletResponse response)  throws Exception {
			response.setContentType("text/html; charset=UTF-8");
			request.setCharacterEncoding("utf-8");
			
			Map<String,String> faqMap=new HashMap<String,String>();
			faqMap.put("faq_id", faq_id);
			faqMap.put("faq_title", faq_title);
			faqMap.put("faq_content", faq_content);
			adminCustomerService.modifyFaq(faqMap);
			
			String script = "<script>alert('FAQ가 수정되었습니다.'); "
					+ "location.href='"+request.getContextPath()+"/admin/customer/adminFaqMain.do'; </script>";
			ResponseEntity resEntity = null;
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.add("Content-Type", "text/html; charset=utf-8");
			
			resEntity =new ResponseEntity(script, responseHeaders, HttpStatus.OK);
			return resEntity;
		}
		
		
		 // FAQ 삭제하기
		  
		
		@RequestMapping(value="/deleteFaq.do", method = RequestMethod.POST) 
		public ResponseEntity deleteFaq(@RequestParam("faq_id") List<String> faq_id, HttpServletRequest request, HttpServletResponse response) throws Exception{
		response.setContentType("text/html; charset=UTF-8");
		request.setCharacterEncoding("utf-8");
		  
		for (String faqId : faq_id) {
	        adminCustomerService.deleteFaq(faqId);
	    }
		
			  
		String script = "<script>alert('FAQ가 삭제되었습니다.'); " +
		"location.href='"+request.getContextPath()
		+"/admin/customer/adminFaqMain.do'; </script>"; ResponseEntity resEntity =
		null; HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "text/html; charset=utf-8");
		
		resEntity =new ResponseEntity(script, responseHeaders, HttpStatus.OK);
		return resEntity; }
			 

		
	
		
		//FAQ 입력페이지
		@RequestMapping(value = "/adminFaqWrite.do")
		public ModelAndView adminFaqWrite(HttpServletRequest request, HttpServletResponse response)  throws Exception {
		    String viewName = (String)request.getAttribute("viewName");
			ModelAndView mav = new ModelAndView(viewName);
			return mav;
		}
		
		
		
		//FAQ 등록
		@Override
		@RequestMapping(value="/addFaq.do" ,method = RequestMethod.GET)
		public ResponseEntity addFaq(@ModelAttribute("boardVO") BoardVO boardVO,
				                HttpServletRequest request, HttpServletResponse response) throws Exception {
			response.setContentType("text/html; charset=UTF-8");
			request.setCharacterEncoding("utf-8");
			String message = null;
			ResponseEntity resEntity = null;
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.add("Content-Type", "text/html; charset=utf-8");
			try {
			    adminCustomerService.addFaq( boardVO );
			    message = "<script>alert('FAQ가 등록되었습니다.'); "
						+ "location.href='"+request.getContextPath()+"/admin/customer/adminFaqMain.do'; </script>";
			    
			}catch(Exception e) {
				message = "<script>alert('작업 중 오류가 발생했습니다. 다시 시도해 주세요.'); "
						+ "location.href='"+request.getContextPath()+"/admin/customer/adminFaqWrite.do'; </script>";
				e.printStackTrace();
			}
			resEntity =new ResponseEntity(message, responseHeaders, HttpStatus.OK);
			return resEntity;
		}
		
		//FAQ 검색
		@RequestMapping(value = "/FaqkeywordSearch.do", method = RequestMethod.POST)
		public ModelAndView FaqkeywordSearch(@RequestParam("keyword") String keyword) {
		    ModelAndView mav = new ModelAndView("redirect:/admin/customer/adminFaqMain.do");
		    
		    if (keyword == null || keyword.equals("")) {
		        return mav;
		    }
		    
			List<String> keywordList =adminCustomerService.FaqkeywordSearch(keyword);
		    mav.addObject("faq_id", keywordList);
		    
		    return mav;
		}

		
		//Q&A
		
		//Q&A 메인페이지
		@RequestMapping(value = "/adminQnaMain.do", method = RequestMethod.GET)
		public ModelAndView adminQnaMain(@RequestParam Map<String, Object> dateMap,
				HttpServletRequest request)  throws Exception {
			
			System.out.println(" admin dateMap = " + dateMap.toString());

			HttpSession session = request.getSession( ) ;
			session = request.getSession( ) ;
			session.setAttribute( "side_menu", "admin_mode" ) ; // 마이페이지 사이드 메뉴로 설정한다.
			
			String viewName;
			ModelAndView mav;

			String fixedSearchPeriod;

			int section = 0;
			int pageNum = 0;
			int countSelect = 0;
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

			
			/// commonService의 sql을 사용하기 위한 부분, service DAO등을 수정하지 안항도 되지만, 
			/// 사용 할 VO에 String result_type 추가  
			String[] searchTypeList  = "qna_id,goods_id,member_id,qna_title,qna_content,qna_comment,qna_date,fileName,member_name".split(",");
			condMap.put("search_type_list", searchTypeList) ; 
			condMap.put("target_table", "munbanggu_qna") ; 
			condMap.put("date_column", "qna_date") ;
			condMap.put("result_type", "QnaVO") ;
			
			
			condMap.put("search_type", searchType) ; 
			condMap.put("search_word", searchWord) ;
		
			
			// DB에서 페이징에 필요한 count 구하기 ( maxPage )
//			
			countSelect = commonService.commonCount(condMap);
			if (countSelect == 0) {
				maxPage = 1;
			} else {
				maxPage = (int) Math.ceil(countSelect / 10.0);
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

			} catch (NumberFormatException e) {
				section = 0;
				pageNum = 1;
				System.err.println(e.toString());
				RedirectView rv;
				rv = new RedirectView(request.getContextPath() + "/admin/customer/adminNoticeMain.do?section=" + section
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
			List<Object> selectList = commonService.commonSearchList(condMap);

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
			mav.addObject("selectList", selectList);
			
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

		//Q&A 단일조회
		@Override
		@RequestMapping(value="/adminQnaDetail.do" ,method = RequestMethod.GET)
		public ModelAndView selectQna(@RequestParam("qna_id") String qna_id,
				HttpServletRequest request, HttpServletResponse response)  throws Exception {
			String viewName = (String)request.getAttribute("viewName");
			ModelAndView mav = new ModelAndView(viewName);
			List selectQna = adminCustomerService.selectQnaDetail(qna_id);
			mav.addObject("selectQna", selectQna);
			return mav;
		}
		
		//Q&A 수정
		@RequestMapping(value="/modifyQna.do" ,method={RequestMethod.GET})
		public ResponseEntity modifyQna( @RequestParam("qna_id") String qna_id,
				                     @RequestParam("qna_comment") String qna_comment,
				HttpServletRequest request, HttpServletResponse response)  throws Exception {
			response.setContentType("text/html; charset=UTF-8");
			request.setCharacterEncoding("utf-8");
			
			Map<String,String> qnaMap=new HashMap<String,String>();
			qnaMap.put("qna_id", qna_id);
			qnaMap.put("qna_comment", qna_comment);
			System.out.println("qna_id : " + qna_id);
			System.out.println("qna_comment : " + qna_comment);
			adminCustomerService.modifyQna(qnaMap);
			
			String script = "<script>alert('Q&A답글을 수정하였습니다.'); "
					+ "location.href='"+request.getContextPath()+"/admin/customer/adminQnaMain.do'; </script>";
			ResponseEntity resEntity = null;
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.add("Content-Type", "text/html; charset=utf-8");
			
			resEntity =new ResponseEntity(script, responseHeaders, HttpStatus.OK);
			return resEntity;
		}
		
		//Q&A 검색
		@RequestMapping(value = "/QnakeywordSearch.do", method = RequestMethod.POST)
		public ModelAndView QnakeywordSearch(@RequestParam("keyword") String keyword) {
		    ModelAndView mav = new ModelAndView("redirect:/admin/customer/adminQnaMain.do");
		    
		    if (keyword == null || keyword.equals("")) {
		        return mav;
		    }
		    
			List<String> keywordList =adminCustomerService.QnakeywordSearch(keyword);
		    mav.addObject("qna_id", keywordList);
		    
		    return mav;
		}
		
		//Q&A 사진 페이지
		@RequestMapping(value = "//adminQnaDetailImage.do")
		public ModelAndView adminQnaDetailImage(@RequestParam("qna_id") String qna_id,
				@RequestParam("fileName") String fileName, 
				HttpServletRequest request, HttpServletResponse response)  throws Exception {
			String viewName = (String)request.getAttribute("viewName");
			ModelAndView mav = new ModelAndView(viewName);
			List selectQna = adminCustomerService.selectQnaDetail(qna_id);
			mav.addObject("selectQna", selectQna);
			return mav;
		}
		
		
		//REVIEW
		//Review 메인페이지
		@RequestMapping(value = "/adminReviewMain.do", method = RequestMethod.GET)
		public ModelAndView adminReviewMain(@RequestParam Map<String, Object> dateMap,
				HttpServletRequest request)  throws Exception {
		    String viewName = (String)request.getAttribute("viewName");
			ModelAndView mav = new ModelAndView(viewName);
			
//			if( page == null ) {
//				page = "1";
//			}
//			mav.addObject("page", page);
//			
//			int num_page_no = Integer.parseInt( page ); //page번호 1,2,3,4
//			int num_page_size = 5; //한페이지당 Row갯수
//			int startRowNum = (num_page_no - 1) * num_page_size + 1; // 1, 6, 11 페이지 시작 줄번호
//			int endRowNum = (num_page_no * num_page_size);           // 5, 10, 15 페이지 끝 줄번호
//			
//			Map<String ,List> reviewMap = adminCustomerService.ReviewList(String.valueOf(startRowNum), String.valueOf(endRowNum));
//			
//			List<BoardVO> selectReviewList = new ArrayList<BoardVO>();
//			
//			if (review_id != null && !review_id.isEmpty()) {
//				String[] parts = review_id.split(",");
//				for (String part : parts) {
//					int number = Integer.parseInt(part);
//					String numberStr = String.valueOf(number);
//					List<BoardVO> selectReview = adminCustomerService.selectReviewDetail(numberStr);
//					selectReviewList.addAll(selectReview);
//				}
//				mav.addObject("reviewList", selectReviewList);
//			} else {
//				List<BoardVO> reviewList = reviewMap.get("reviewList");
//				mav.addObject("reviewList", reviewList);
//			}

			return mav;
		}

		//Review 단일조회
		@Override
		@RequestMapping(value="/adminReviewDetail.do" ,method = RequestMethod.GET)
		public ModelAndView selectReview(@RequestParam("review_id") String review_id,
				HttpServletRequest request, HttpServletResponse response)  throws Exception {
			String viewName = (String)request.getAttribute("viewName");
			ModelAndView mav = new ModelAndView(viewName);
			List selectReview = adminCustomerService.selectReviewDetail(review_id);
			mav.addObject("selectReview", selectReview);
			return mav;
		}
		
		//Review 수정
		@RequestMapping(value="/modifyReview.do" ,method={RequestMethod.GET})
		public ResponseEntity modifyReview( @RequestParam("review_id") String review_id,
				                     @RequestParam("review_comment") String review_comment,
				HttpServletRequest request, HttpServletResponse response)  throws Exception {
			response.setContentType("text/html; charset=UTF-8");
			request.setCharacterEncoding("utf-8");
			
			Map<String,String> reviewMap=new HashMap<String,String>();
			reviewMap.put("review_id", review_id);
			reviewMap.put("review_comment", review_comment);
			adminCustomerService.modifyReview(reviewMap);
			
			String script = "<script>alert('리뷰 답글을 수정하였습니다.'); "
					+ "location.href='"+request.getContextPath()+"/admin/customer/adminReviewMain.do'; </script>";
			ResponseEntity resEntity = null;
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.add("Content-Type", "text/html; charset=utf-8");
			
			resEntity =new ResponseEntity(script, responseHeaders, HttpStatus.OK);
			return resEntity;
		}
		
		//Review 검색
		@RequestMapping(value = "/ReviewkeywordSearch.do", method = RequestMethod.POST)
		public ModelAndView ReviewkeywordSearch(@RequestParam("keyword") String keyword) {
		    ModelAndView mav = new ModelAndView("redirect:/admin/customer/adminReviewMain.do");
		    
		    if (keyword == null || keyword.equals("")) {
		        return mav;
		    }
		    
			List<String> keywordList =adminCustomerService.ReviewkeywordSearch(keyword);
		    mav.addObject("review_id", keywordList);
		    
		    return mav;
		}
		
		//Review 사진 페이지
		@RequestMapping(value = "//adminReviewDetailImage.do")
		public ModelAndView adminReviewDetailImage(@RequestParam("review_id") String review_id,
				@RequestParam("fileName") String fileName, 
				HttpServletRequest request, HttpServletResponse response)  throws Exception {
			String viewName = (String)request.getAttribute("viewName");
			ModelAndView mav = new ModelAndView(viewName);
			List selectReview = adminCustomerService.selectReviewDetail(review_id);
			mav.addObject("selectReview", selectReview);
			return mav;
		}

}
