package com.munbanggu.payment.controller ;

import java.util.HashMap ;
import java.util.Map ;

import org.springframework.beans.factory.annotation.Autowired ;
import org.springframework.web.bind.annotation.RequestMapping ;
import org.springframework.web.bind.annotation.RequestMethod ;
import org.springframework.web.bind.annotation.RequestParam ;
import org.springframework.web.bind.annotation.RestController ;

import com.munbanggu.payment.service.PaymentService;

// 결제모듈 20230601
//@controller랑 @RestController는 다릅니다.
// @controller = return 화면
// @RestController = return data
@RestController
public class PaymentController
{

	@Autowired
	private PaymentService paymentService ;

	@RequestMapping( value = "/payment/kakao/order.do", method = RequestMethod.POST )
	public Map < String, String > kakaoOrder( @RequestParam Map < String, String > receiverMap ) throws Exception
	{

		Map < String, String > resultMap = new HashMap < String, String >( ) ;

		// 제대로 들어오는지 확인
		System.out.println( "kakaoOrder 확인" ) ;
 
		// 주문 API를 쓴다음에
		// resultMap 주문 결과를 넣음

		
		// 결과가 제대로 나가는지 테스트 데이터
//		resultMap.put( "test", "test값" ) ;
		
		resultMap = paymentService.kakaoOrder( receiverMap );
		System.out.println(  resultMap ) ;
//		resultMap.putAll( paymentService.kakaoOrder( receiverMap )  ) ;

		//Map < String, String > tempMap = new HashMap < String, String >( ) ;
		//tempMap.putAll( paymentService.kakaoOrder( receiverMap ) )  ;
		//System.out.println(  tempMap ) ;// good_mny=100, site_cd=A8QOB, Ret_URL=test, affiliaterCode=0005, buyr_name=choi, ordr_idxx=20230601115701KK0379, good_name=강의테스트, responseCode=0000, responseMsg=성공}

		
		return resultMap ;
	}
	@RequestMapping( value = "/payment/naver/order.do", method = RequestMethod.POST )
	public Map < String, String > naverOrder( @RequestParam Map < String, String > receiverMap ) throws Exception
	{
		Map < String, String > resultMap = new HashMap < String, String >( ) ;

		// 제대로 들어오는지 확인
		System.out.println( "naverOrder 확인" ) ;

		// 주문 API를 쓴다음에
		// resultMap 주문 결과를 넣음
		String payType = "";
		payType = receiverMap.get("payType");
		System.out.println( "payType : " + payType ) ;
		
		// 결과가 제대로 나가는지 테스트 데이터
//		resultMap.put( "test", "test값" ) ;
		
		resultMap = paymentService.naverOrder( receiverMap );
		System.out.println(  resultMap );
//		resultMap.putAll( paymentService.kakaoOrder( receiverMap )  ) ;

		//Map < String, String > tempMap = new HashMap < String, String >( ) ;
		//tempMap.putAll( paymentService.naverOrder( receiverMap ) )  ;
		//System.out.println(  tempMap ) ;// good_mny=100, site_cd=A8QOB, Ret_URL=test, affiliaterCode=0005, buyr_name=choi, ordr_idxx=20230601115701KK0379, good_name=강의테스트, responseCode=0000, responseMsg=성공}

		
		return resultMap ;
	}
}