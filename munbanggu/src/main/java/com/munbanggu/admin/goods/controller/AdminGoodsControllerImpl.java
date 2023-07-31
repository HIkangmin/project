package com.munbanggu.admin.goods.controller ;

import java.io.File ;
import java.io.PrintWriter ;
import java.util.Enumeration ;
import java.util.HashMap ;
import java.util.List ;
import java.util.Map ;

import javax.servlet.http.HttpServletRequest ;
import javax.servlet.http.HttpServletResponse ;
import javax.servlet.http.HttpSession ;

import org.apache.commons.io.FileUtils ;
import org.springframework.beans.factory.annotation.Autowired ;
import org.springframework.http.HttpHeaders ;
import org.springframework.http.HttpStatus ;
import org.springframework.http.ResponseEntity ;
import org.springframework.stereotype.Controller ;
import org.springframework.web.bind.annotation.RequestMapping ;
import org.springframework.web.bind.annotation.RequestMethod ;
import org.springframework.web.bind.annotation.RequestParam ;
import org.springframework.web.multipart.MultipartHttpServletRequest ;
import org.springframework.web.servlet.ModelAndView ;
import org.springframework.web.servlet.view.RedirectView;

import com.munbanggu.admin.goods.service.AdminGoodsService;
import com.munbanggu.common.base.BaseController;
import com.munbanggu.goods.vo.GoodsVO;
import com.munbanggu.goods.vo.ImageFileVO;
import com.munbanggu.member.vo.MemberVO;

@Controller( "adminGoodsController" )
@RequestMapping( value = "/admin/goods" )
public class AdminGoodsControllerImpl extends BaseController implements AdminGoodsController
{

	@Autowired
	private AdminGoodsService adminGoodsService ;  

	@RequestMapping( value = "/adminGoodsMain.do", method = { RequestMethod.POST, RequestMethod.GET } )
	public ModelAndView adminGoodsMain( @RequestParam Map < String, Object > dateMap, HttpServletRequest request,
			HttpServletResponse response ) throws Exception
	{
		
		
		HttpSession session = request.getSession( ) ;
		session = request.getSession( ) ;
		session.setAttribute( "side_menu", "admin_mode" ) ; // 마이페이지 사이드 메뉴로 설정한다.
		
		String viewName;
		ModelAndView mav;

		String fixedSearchPeriod;
		
		int section = 0;
		int pageNum = 0;
		int countGoodsId = 0;
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
		countGoodsId = adminGoodsService.countSelectNewGoodsList(condMap);
		if (countGoodsId == 0) {
			maxPage = 1;
		} else {
			maxPage = (int) Math.ceil(countGoodsId / 10.0);
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
//				rv = new RedirectView(request.getContextPath() + "/admin/Goods/adminGoodsMain.do?section=" + section
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
			rv = new RedirectView(request.getContextPath() + "/admin/Goods/adminGoodsMain.do?section=" + section
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
		List < GoodsVO > newGoodsList = adminGoodsService.listNewGoods( condMap ) ;

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
		mav.addObject( "newGoodsList", newGoodsList ) ;
		mav.addObject("newGoodsList", newGoodsList);
		
		System.out.println("beginYear " + beginDate1[0]);
		System.out.println("beginMonth " + beginDate1[1]);
		System.out.println("beginDay " + beginDate1[2]);
		System.out.println("endYear " + endDate1[0]);
		System.out.println("endMonth " + endDate1[1]);
		System.out.println("endDay " + endDate1[2]);
		System.out.println("section " + section);
		System.out.println("pageNum " + pageNum);
		System.out.println("maxPage " + maxPage);
		return mav ;
		
//		String viewName = ( String ) request.getAttribute( "viewName" ) ;
//		ModelAndView mav = new ModelAndView( viewName ) ;
//		HttpSession session = request.getSession( ) ;
//		session = request.getSession( ) ;
//		session.setAttribute( "side_menu", "admin_mode" ) ; // 마이페이지 사이드 메뉴로 설정한다.
//
//		String fixedSearchPeriod =  ( String ) dateMap.get( "fixedSearchPeriod" ) ;
//		int section = 0 ;
//		if( ( String ) dateMap.get( "section" ) != null ) {
//			section = Integer.parseInt( dateMap.get( "section" ).toString( ) );
//		}
//		int pageNum = 0 ;
//		if( ( String ) dateMap.get( "pageNum" ) != null ) {
//			pageNum = Integer.parseInt( dateMap.get( "pageNum" ).toString( ) );
//		}
//	
//		
//		String beginDate = null, endDate = null ;
//
//		String[] tempDate = calcSearchPeriod( fixedSearchPeriod ).split( "," ) ;
//		beginDate = tempDate[ 0 ] ;
//		endDate = tempDate[ 1 ] ;
//		dateMap.put( "beginDate", beginDate ) ;
//		dateMap.put( "endDate", endDate ) ;
//
//		Map < String, Object > condMap = new HashMap < String, Object >( ) ;
//		condMap.put( "section", section ) ;
//		
//		condMap.put( "pageNum", pageNum ) ;
//		condMap.put( "beginDate", beginDate ) ;
//		condMap.put( "endDate", endDate ) ;
//		List < GoodsVO > newGoodsList = adminGoodsService.listNewGoods( condMap ) ;
//		mav.addObject( "newGoodsList", newGoodsList ) ;
//
//		String beginDate1[] = beginDate.split( "-" ) ;
//		String endDate2[] = endDate.split( "-" ) ;
//		mav.addObject( "beginYear", beginDate1[ 0 ] ) ;
//		mav.addObject( "beginMonth", beginDate1[ 1 ] ) ;
//		mav.addObject( "beginDay", beginDate1[ 2 ] ) ;
//		mav.addObject( "endYear", endDate2[ 0 ] ) ;
//		mav.addObject( "endMonth", endDate2[ 1 ] ) ;
//		mav.addObject( "endDay", endDate2[ 2 ] ) ;
//
//		mav.addObject( "section", section ) ;	
//		mav.addObject( "pageNum", pageNum ) ;
//		return mav ;

	}

	@RequestMapping( value = "/addNewGoods.do", method = { RequestMethod.POST } )
	public ResponseEntity addNewGoods( MultipartHttpServletRequest multipartRequest, HttpServletResponse response )
			throws Exception
	{
		multipartRequest.setCharacterEncoding( "utf-8" ) ;
		response.setContentType( "text/html; charset=UTF-8" ) ;
		String imageFileName = null ;

		Map newGoodsMap = new HashMap( ) ;
		Enumeration enu = multipartRequest.getParameterNames( ) ;
		while ( enu.hasMoreElements( ) )
		{
			String name = ( String ) enu.nextElement( ) ;
			String value = multipartRequest.getParameter( name ) ;
			newGoodsMap.put( name, value ) ;
		}

		HttpSession session = multipartRequest.getSession( ) ;
		MemberVO memberVO = ( MemberVO ) session.getAttribute( "memberInfo" ) ;
		String reg_id = memberVO.getMember_id( ) ;

		List < ImageFileVO > imageFileList = upload( multipartRequest ) ;
		if ( imageFileList != null && imageFileList.size( ) != 0 )
		{
			for ( ImageFileVO imageFileVO : imageFileList )
			{
				imageFileVO.setReg_id( reg_id ) ;
			}
			newGoodsMap.put( "imageFileList", imageFileList ) ;
		}

		String message = null ;
		ResponseEntity resEntity = null ;
		HttpHeaders responseHeaders = new HttpHeaders( ) ;
		responseHeaders.add( "Content-Type", "text/html; charset=utf-8" ) ;
		try
		{
			int goods_id = adminGoodsService.addNewGoods( newGoodsMap ) ;
			if ( imageFileList != null && imageFileList.size( ) != 0 )
			{
				for ( ImageFileVO imageFileVO : imageFileList )
				{
					imageFileName = imageFileVO.getFileName( ) ;
					File srcFile = new File( CURR_IMAGE_REPO_PATH + "/" + "temp" + "/" + imageFileName ) ;
					File destDir = new File( CURR_IMAGE_REPO_PATH + "/" + goods_id ) ;
					FileUtils.moveFileToDirectory( srcFile, destDir, true ) ;
				}
			}
			message = "<script>" ;
			message += " alert('새상품을 추가했습니다.');" ;
			message += " location.href='" + multipartRequest.getContextPath( ) + "/admin/goods/addNewGoodsForm.do';" ;
			message += ( "</script>" ) ;
		} catch ( Exception e )
		{
			if ( imageFileList != null && imageFileList.size( ) != 0 )
			{
				for ( ImageFileVO imageFileVO : imageFileList )
				{
					imageFileName = imageFileVO.getFileName( ) ;
					File srcFile = new File( CURR_IMAGE_REPO_PATH + "/" + "temp" + "/" + imageFileName ) ;
					srcFile.delete( ) ;
				}
			}

			message = "<script>" ;
			message += " alert('오류가 발생했습니다. 다시 시도해 주세요');" ;
			message += " location.href='" + multipartRequest.getContextPath( ) + "/admin/goods/addNewGoodsForm.do';" ;
			message += ( "</script>" ) ;
			e.printStackTrace( ) ;
		}
		resEntity = new ResponseEntity( message, responseHeaders, HttpStatus.OK ) ;
		return resEntity ;
	}

	@RequestMapping( value = "/modifyGoodsForm.do", method = { RequestMethod.GET, RequestMethod.POST } )
	public ModelAndView modifyGoodsForm( @RequestParam( "goods_id" ) int goods_id, HttpServletRequest request,
			HttpServletResponse response ) throws Exception
	{
		String viewName = ( String ) request.getAttribute( "viewName" ) ;
		ModelAndView mav = new ModelAndView( viewName ) ;

		Map goodsMap = adminGoodsService.goodsDetail( goods_id ) ;
		mav.addObject( "goodsMap", goodsMap ) ;

		return mav ;
	}

	@RequestMapping( value = "/modifyGoodsInfo.do", method = { RequestMethod.POST } )
	public ResponseEntity modifyGoodsInfo( @RequestParam( "goods_id" ) String goods_id,
			@RequestParam( "attribute" ) String attribute, @RequestParam( "value" ) String value,
			HttpServletRequest request, HttpServletResponse response ) throws Exception
	{
		// System.out.println("modifyGoodsInfo");

		Map < String, String > goodsMap = new HashMap < String, String >( ) ;
		goodsMap.put( "goods_id", goods_id ) ;
		goodsMap.put( attribute, value ) ;
		adminGoodsService.modifyGoodsInfo( goodsMap ) ;

		String message = null ;
		ResponseEntity resEntity = null ;
		HttpHeaders responseHeaders = new HttpHeaders( ) ;
		message = "mod_success" ;
		resEntity = new ResponseEntity( message, responseHeaders, HttpStatus.OK ) ;
		return resEntity ;
	}

	@RequestMapping( value = "/modifyGoodsImageInfo.do", method = { RequestMethod.POST } )
	public void modifyGoodsImageInfo( MultipartHttpServletRequest multipartRequest, HttpServletResponse response )
			throws Exception
	{
		System.out.println( "modifyGoodsImageInfo" ) ;
		multipartRequest.setCharacterEncoding( "utf-8" ) ;
		response.setContentType( "text/html; charset=utf-8" ) ;
		String imageFileName = null ;

		Map goodsMap = new HashMap( ) ;
		Enumeration enu = multipartRequest.getParameterNames( ) ;
		while ( enu.hasMoreElements( ) )
		{
			String name = ( String ) enu.nextElement( ) ;
			String value = multipartRequest.getParameter( name ) ;
			goodsMap.put( name, value ) ;
		}

		HttpSession session = multipartRequest.getSession( ) ;
		MemberVO memberVO = ( MemberVO ) session.getAttribute( "memberInfo" ) ;
		String reg_id = memberVO.getMember_id( ) ;

		List < ImageFileVO > imageFileList = null ;
		int goods_id = 0 ;
		int image_id = 0 ;
		try
		{
			imageFileList = upload( multipartRequest ) ;
			if ( imageFileList != null && imageFileList.size( ) != 0 )
			{
				for ( ImageFileVO imageFileVO : imageFileList )
				{
					goods_id = Integer.parseInt( ( String ) goodsMap.get( "goods_id" ) ) ;
					image_id = Integer.parseInt( ( String ) goodsMap.get( "image_id" ) ) ;
					imageFileVO.setGoods_id( goods_id ) ;
					imageFileVO.setImage_id( image_id ) ;
					imageFileVO.setReg_id( reg_id ) ;
				}

				adminGoodsService.modifyGoodsImage( imageFileList ) ;
				for ( ImageFileVO imageFileVO : imageFileList )
				{
					imageFileName = imageFileVO.getFileName( ) ;
					File srcFile = new File( CURR_IMAGE_REPO_PATH + "/" + "temp" + "/" + imageFileName ) ;
					File destDir = new File( CURR_IMAGE_REPO_PATH + "/" + goods_id ) ;
					FileUtils.moveFileToDirectory( srcFile, destDir, true ) ;
				}
			}
		} catch ( Exception e )
		{
			if ( imageFileList != null && imageFileList.size( ) != 0 )
			{
				for ( ImageFileVO imageFileVO : imageFileList )
				{
					imageFileName = imageFileVO.getFileName( ) ;
					File srcFile = new File( CURR_IMAGE_REPO_PATH + "/" + "temp" + "/" + imageFileName ) ;
					srcFile.delete( ) ;
				}
			}
			e.printStackTrace( ) ;
		}

	}

	@Override
	@RequestMapping( value = "/addNewGoodsImage.do", method = { RequestMethod.POST } )
	public void addNewGoodsImage( MultipartHttpServletRequest multipartRequest, HttpServletResponse response )
			throws Exception
	{
		System.out.println( "addNewGoodsImage" ) ;
		multipartRequest.setCharacterEncoding( "utf-8" ) ;
		response.setContentType( "text/html; charset=utf-8" ) ;
		String imageFileName = null ;

		Map goodsMap = new HashMap( ) ;
		Enumeration enu = multipartRequest.getParameterNames( ) ;
		while ( enu.hasMoreElements( ) )
		{
			String name = ( String ) enu.nextElement( ) ;
			String value = multipartRequest.getParameter( name ) ;
			goodsMap.put( name, value ) ;
		}

		HttpSession session = multipartRequest.getSession( ) ;
		MemberVO memberVO = ( MemberVO ) session.getAttribute( "memberInfo" ) ;
		String reg_id = memberVO.getMember_id( ) ;

		List < ImageFileVO > imageFileList = null ;
		int goods_id = 0 ;
		try
		{
			imageFileList = upload( multipartRequest ) ;
			if ( imageFileList != null && imageFileList.size( ) != 0 )
			{
				for ( ImageFileVO imageFileVO : imageFileList )
				{
					goods_id = Integer.parseInt( ( String ) goodsMap.get( "goods_id" ) ) ;
					imageFileVO.setGoods_id( goods_id ) ;
					imageFileVO.setReg_id( reg_id ) ;
				}

				adminGoodsService.addNewGoodsImage( imageFileList ) ;
				for ( ImageFileVO imageFileVO : imageFileList )
				{
					imageFileName = imageFileVO.getFileName( ) ;
					File srcFile = new File( CURR_IMAGE_REPO_PATH + "/" + "temp" + "/" + imageFileName ) ;
					File destDir = new File( CURR_IMAGE_REPO_PATH + "/" + goods_id ) ;
					FileUtils.moveFileToDirectory( srcFile, destDir, true ) ;
				}
			}
		} catch ( Exception e )
		{
			if ( imageFileList != null && imageFileList.size( ) != 0 )
			{
				for ( ImageFileVO imageFileVO : imageFileList )
				{
					imageFileName = imageFileVO.getFileName( ) ;
					File srcFile = new File( CURR_IMAGE_REPO_PATH + "/" + "temp" + "/" + imageFileName ) ;
					srcFile.delete( ) ;
				}
			}
			e.printStackTrace( ) ;
		}
	}

	@Override
	@RequestMapping( value = "/removeGoodsImage.do", method = { RequestMethod.POST } )
	public void removeGoodsImage( @RequestParam( "goods_id" ) int goods_id, @RequestParam( "image_id" ) int image_id,
			@RequestParam( "imageFileName" ) String imageFileName, HttpServletRequest request,
			HttpServletResponse response ) throws Exception
	{

		adminGoodsService.removeGoodsImage( image_id ) ;
		try
		{
			File srcFile = new File( CURR_IMAGE_REPO_PATH + "/" + goods_id + "/" + imageFileName ) ;
			srcFile.delete( ) ;
		} catch ( Exception e )
		{
			e.printStackTrace( ) ;
		}
	}

}
