package com.munbanggu.customer.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.munbanggu.common.base.BaseController;
import com.munbanggu.common.base.BaseRepoPath;
import com.munbanggu.common.service.CommonService;
import com.munbanggu.customer.service.CustomerService;
import com.munbanggu.customer.vo.BoardVO;
import com.munbanggu.customer.vo.NoticeVO;
import com.munbanggu.goods.service.GoodsService;
import com.munbanggu.member.vo.MemberVO;

@Controller("CustomerController")
@RequestMapping(value="/customer")
public class CustomerControllerImpl extends BaseController implements CustomerController {
//	private static final String CURR_QNA_IMAGE_REPO_PATH = "D:/spring-workspace/shopping/qna_file_repo";
	
	private static final String CURR_IMAGE_REPO_PATH = BaseRepoPath.getImagePath();

	
	@Autowired
	private CustomerService customerService;
	@Autowired
	private GoodsService goodsService;
	
	@Autowired
	private CommonService commonService ;
	
	//공지사항
	//공지사항 메인페이지
	@RequestMapping(value = "/noticeMain.do", method = RequestMethod.GET)
	public ModelAndView noticeMain(@RequestParam Map<String, Object> dateMap,
			HttpServletRequest request)  throws Exception {
		
		HttpSession session = request.getSession( ) ;
		session = request.getSession( ) ;
		session.setAttribute("side_menu", "user");
		
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
		String[] searchTypeList  = "acticle_id,notice_id,title,writer,user_id,editdate,member_name,notice_title,notice_content,notice_date".split(",");
		condMap.put("search_type_list", searchTypeList) ; 
		condMap.put("target_table", "board") ; 
		condMap.put("date_column", "notice_date") ;
		condMap.put("result_type", "BoardVO") ;
		
		
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
			rv = new RedirectView(request.getContextPath() + "/customer/NoticeMain.do?section=" + section
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
	@RequestMapping(value="/noticeDetail.do" ,method = RequestMethod.GET)
	public ModelAndView selectNotice(@RequestParam("notice_id") String notice_id,
			HttpServletRequest request, HttpServletResponse response)  throws Exception {
		String viewName = (String)request.getAttribute("viewName");
		ModelAndView mav = new ModelAndView(viewName);
		List selectNotice = customerService.selectNoticeDetail(notice_id);
		mav.addObject("selectNotice", selectNotice);
		return mav;
	}
	
	
	//공지사항 검색
	@RequestMapping(value = "/NoticekeywordSearch.do", method = RequestMethod.POST)
	public ModelAndView NoticekeywordSearch(@RequestParam("keyword") String keyword) {
	    ModelAndView mav = new ModelAndView("redirect:/customer/noticeMain.do");
	    
	    if (keyword == null || keyword.equals("")) {
	        return mav;
	    }
	    
		List<String> keywordList =customerService.NoticekeywordSearch(keyword);
	    mav.addObject("notice_id", keywordList);
	    
	    return mav;
	}
	
	
	//FAQ
	//FAQ 메인페이지
	@RequestMapping(value = "/faqMain.do", method = RequestMethod.GET)
	public ModelAndView faqMain(@RequestParam Map<String, Object> dateMap,
			HttpServletRequest request)  throws Exception {

		HttpSession session = request.getSession( ) ;
		session = request.getSession( ) ;
		session.setAttribute( "side_menu", "user" ) ; // 마이페이지 사이드 메뉴로 설정한다.
		
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
		String[] searchTypeList  = "acticle_id,faq_id,title,writer,user_id,editdate,member_name,faq_title,faq_content,faq_date".split(",");
		condMap.put("search_type_list", searchTypeList) ; 
		condMap.put("target_table", "board") ; 
		condMap.put("date_column", "faq_date");
		condMap.put("result_type", "BoardVO") ;
		
		
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
	
	
	
		//Q&A 메인페이지
		@RequestMapping(value = "/qnaMain.do", method = RequestMethod.GET)
		public ModelAndView qnaMain(@RequestParam Map<String, Object> dateMap,
				HttpServletRequest request)  throws Exception {
		    
			System.out.println(" admin dateMap = " + dateMap.toString());

			HttpSession session = request.getSession( ) ;
			session = request.getSession( ) ;
			session.setAttribute( "side_menu", "user" ) ; // 마이페이지 사이드 메뉴로 설정한다.
			
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
			String[] searchTypeList  = "acticle_id,qna_id,title,writer,user_id,editdate,member_name,qna_title,qna_content,qna_date".split(",");
			condMap.put("search_type_list", searchTypeList) ; 
			condMap.put("target_table", "board") ; 
			condMap.put("date_column", "qna_date") ;
			condMap.put("result_type", "BoardVO") ;
			
			
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
		@RequestMapping(value="/qnaDetail.do" ,method = RequestMethod.GET)
		public ModelAndView selectQna(@RequestParam("qna_id") String qna_id,
				HttpServletRequest request, HttpServletResponse response)  throws Exception {
			String viewName = (String)request.getAttribute("viewName");
			ModelAndView mav = new ModelAndView(viewName);
			List selectQna = customerService.selectQnaDetail(qna_id);
			mav.addObject("selectQna", selectQna);
			return mav;
		}
		
		//Q&A 입력페이지
		@RequestMapping(value = "/qnaWrite.do")
		public ModelAndView qnaWrite(HttpServletRequest request, HttpServletResponse response)  throws Exception {
		    String viewName = (String)request.getAttribute("viewName");
			ModelAndView mav = new ModelAndView(viewName);
			String goods_id = request.getParameter("goods_id");
			String goods_title = request.getParameter("goods_title");
			System.out.println("굿즈아이디 : " + goods_id);
			System.out.println("굿즈타이틀 : " + goods_title);
			if( goods_id != null ) {
				mav.addObject("goods_id", goods_id);
				mav.addObject("goods_title", goods_title);				
			}
			return mav;
		}
		
		//Q&A 등록하기
		@RequestMapping(value="/addQna.do" ,method={RequestMethod.POST})
		public ResponseEntity addQna(MultipartHttpServletRequest multipartRequest, HttpServletResponse response)  throws Exception {
			multipartRequest.setCharacterEncoding("utf-8");
			response.setContentType("text/html; charset=UTF-8");
			String imageFileName=null;
			
			Map newQnaMap = new HashMap();
			Enumeration enu=multipartRequest.getParameterNames();
			
			while(enu.hasMoreElements()){
				String name=(String)enu.nextElement();
				String value=multipartRequest.getParameter(name);
				newQnaMap.put(name,value);
				System.out.println("name: " + name + ", value: " + value);
			}
			
			MultipartFile file = multipartRequest.getFile("fileName");
			String fileName = file.getOriginalFilename();
			newQnaMap.put("fileName", fileName);
			
			HttpSession session = multipartRequest.getSession();
			MemberVO memberVO = (MemberVO) session.getAttribute("memberInfo");
			String member_id = memberVO.getMember_id();
			newQnaMap.put("member_id", member_id);
			
			String goodsID = (String)newQnaMap.get("goods_id");
			newQnaMap.put("goods_id", goodsID);

			
			List<BoardVO> imageFileList =qnaUpload(multipartRequest);
			if(imageFileList!= null && imageFileList.size()!=0) {
				for(BoardVO boardVO : imageFileList) {
					boardVO.setMember_id(member_id);
				}
				newQnaMap.put("imageFileList", imageFileList);
			}
			
			String message = null;
			ResponseEntity resEntity = null;
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.add("Content-Type", "text/html; charset=utf-8");
			try {
				int qna_id = customerService.addQna(newQnaMap);
				if(newQnaMap!=null && newQnaMap.size()!=0) {
					imageFileName = (String) newQnaMap.get("fileName");
					File srcFile = new File(CURR_IMAGE_REPO_PATH +"/"+"temp"+"/"+imageFileName);
					File destDir = new File(CURR_IMAGE_REPO_PATH +"/"+qna_id);
					if (!destDir.exists()) {
		                destDir.mkdirs(); // 폴더가 존재하지 않으면 생성
		            }
					if (fileName != null && !fileName.isEmpty()) {
						FileUtils.moveFileToDirectory(srcFile, destDir,true);
					}
				}
				message= "<script>";
				message += " alert('Q&A가 작성되었습니다.');";
				message +=" location.href='"+multipartRequest.getContextPath()+"/admin/customer/adminQnaMain.do';";
				message +=("</script>");
			}catch(Exception e) {
				if(newQnaMap!=null && newQnaMap.size()!=0) {
					imageFileName = (String)newQnaMap.get("fileName");
					File srcFile = new File(CURR_IMAGE_REPO_PATH +"/"+"temp"+"/"+imageFileName);
					srcFile.delete();
				}
				message= "<script>";
				message += " alert('오류가 발생했습니다. 다시 시도해 주세요');";
				message +=" location.href='"+multipartRequest.getContextPath()+"/customer/qnaWrite.do';";
				message +=("</script>");
				e.printStackTrace();
			}
			resEntity =new ResponseEntity(message, responseHeaders, HttpStatus.OK);
			System.out.println("newQnaMap: " + newQnaMap);
			return resEntity;
		}

		@Override
		public ModelAndView FaqkeywordSearch(String keyword) throws Exception {
			// TODO Auto-generated method stub
			return null;
		}
}