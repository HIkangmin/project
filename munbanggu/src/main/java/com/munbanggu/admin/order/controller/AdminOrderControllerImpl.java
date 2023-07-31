package com.munbanggu.admin.order.controller;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.checkerframework.checker.index.qual.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.munbanggu.admin.order.service.AdminOrderService;
import com.munbanggu.common.base.BaseController;
import com.munbanggu.common.service.CommonService;
import com.munbanggu.order.vo.OrderVO;

@Controller("adminOrderController")
@RequestMapping(value = "/admin/order")
public class AdminOrderControllerImpl extends BaseController implements AdminOrderController {

	@Autowired
	private AdminOrderService adminOrderService;
	
	@Autowired
	private CommonService commonService ;

	@Override
	@RequestMapping(value = "/adminOrderMain.do", method = { RequestMethod.GET }) //, RequestMethod.POST })
	public ModelAndView adminOrderMain(@RequestParam Map<String, Object> dateMap, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		/*
		 * 리팩토링시 기간을 beginDate, endDate로 줄이는 과정 필요( jsp에서만 쪼개서 처리하도록 )
		 * 리팩토링시 페이징에 필요한 클래스를 util.web. Pagination, Serach, Period 같은 형식으로 정리 필요
		 * 리팩토링시 페이징에 사용되는 countOrderId를 기존 쿼리문에 추가 또는 util.web 쪽으로 빼는 작업 필요 
		 * 리팩토링시 commonService 추가하면서 이전에 만들었던 일부코드들이 필요 없어진 코드 정리 필요. 
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
		int countOrderId = 0;
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
		String[] searchTypeList  = "order_id,orderer_name,orderer_hp,receiver_name,goods_title".split(",");
		condMap.put("search_type_list", searchTypeList) ; 
		condMap.put("target_table", "t_shopping_order") ; 
		condMap.put("date_column", "pay_order_time") ;
		condMap.put("result_type", "OrderVO") ;
		
		
		condMap.put("search_type", searchType) ; 
		condMap.put("search_word", searchWord) ;
	
		
		// DB에서 페이징에 필요한 count 구하기 ( maxPage )
//		countOrderId = adminOrderService.countSelectNewOrderList(condMap);
		countOrderId = commonService.commonCount(condMap);
		if (countOrderId == 0) {
			maxPage = 1;
		} else {
			maxPage = (int) Math.ceil(countOrderId / 10.0);
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
//				rv = new RedirectView(request.getContextPath() + "/admin/order/adminOrderMain.do?section=" + section
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
			rv = new RedirectView(request.getContextPath() + "/admin/order/adminOrderMain.do?section=" + section
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
//		List<OrderVO> newOrderList = adminOrderService.listNewOrder(condMap);
		List<Object> newOrderList = commonService.commonSearchList(condMap);

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
		mav.addObject("newOrderList", newOrderList);
		
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

	@Override
	@RequestMapping(value = "/modifyDeliveryState.do", method = { RequestMethod.POST })
	public ResponseEntity modifyDeliveryState(@RequestParam Map<String, String> deliveryMap, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		adminOrderService.modifyDeliveryState(deliveryMap);

		String message = null;
		ResponseEntity resEntity = null;
		HttpHeaders responseHeaders = new HttpHeaders();
		message = "mod_success";
		resEntity = new ResponseEntity(message, responseHeaders, HttpStatus.OK);
		return resEntity;
	}

	@Override
	@RequestMapping(value = "/orderDetail.do", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView orderDetail(@RequestParam("order_id") int order_id, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String viewName = (String) request.getAttribute("viewName");
		ModelAndView mav = new ModelAndView(viewName);
		Map orderMap = adminOrderService.orderDetail(order_id);
		mav.addObject("orderMap", orderMap);
		return mav;
	}

}
