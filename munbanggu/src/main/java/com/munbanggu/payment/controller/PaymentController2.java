package com.munbanggu.payment.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.annotations.ResultMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.munbanggu.payment.service.PaymentService;

//@RestController를 사용 할 수 없기 때문에 2를 붙여서 새로 만들기, 단 숫자를 붙이는 건 매우 안 좋은 습관

// 결제모듈 20230601
@Controller
public class PaymentController2 {

	@Autowired
	private PaymentService paymentService;

	@RequestMapping(value = "/payment/kakao/pay.do")
	public ModelAndView kakaoPay(@RequestParam Map<String, String> receiverMap) throws Exception {

		Map<String, String> resultMap = new HashMap<String, String>();
		ModelAndView mav = new ModelAndView();

		System.out.println("인증 데이터 확인" + receiverMap.toString());
		// 인증 데이터 확인{ordr_idxx=20230601145519KK0639, good_name=�����׽�Ʈ, good_mny=100,
		// buyr_name=choi, site_cd=A8QOB, req_tx=pay, pay_method=100000000000,
		// currency=410, kakaopay_direct=Y, module_type=01,
		// ordr_chk=20230601145519KK0639|100, param_opt_1=, param_opt_2=, param_opt_3=,
		// res_cd=0000, res_msg=����,
		// enc_info=1eJlts-UDA6-tySZVpaP8B7-KkfV1PBDTShPI0RDLQlXgdHgggV2YvuufqZgDqH4LA-5ewbRo9Xggvmug-GZPfQ25R0isnbYaEkEZYiVzkOcaBhUr-suYSfYXOVlbqdMdKuLGEe7e.O.ZQTtecVnCaUoaxiwNNhlWKy3UiH9ZJ3.N3wSwmQ.iJeLf3DLNiYyC-GexS8WUC8__,
		// enc_data=3vs6Js0Ax7hupytTaG5d4YtWiADrqUbMqfot7YhY30tJbvgllRAohmNmxtF1yoc3c7ZLHq0Ifcg5g.oLCycvXCVzvoShfaOSY96p70UnghoE.yZe43WBsyCEHA4k2t7-rNue0n8ygCboK.HC6jpaBmM6W7JgFKcTAK4UXoEK82mngwiJ7pL9voog4b6gI3OMKUm-Nnsxn8Qm4E.-RRr2p52OK9SIzz8N0kcamfXmFqd6.2YrJWho0D6kRMQ0wS4v6o4eubo4umf8Bx3d-CTC7NhknGLHcVbZ40GDW12e02If.pXq.o0NyzC0jcPypjOPPPpQ3MgD7NXcadAEQYN7ls3VnIxdfnM.CAR.SxoetOh25QvFZYVs95irGwCN0-1X38lKS-GenKvBefvmbOYP44VYLLUGzZ6jbWDjl4jVzt43cYiTwt695x147LysfDW7PtOqP9CMV5RAJWOR3rPjkzcUhGexabdPWCwnXhpl4UkJjIwvmeXU7rMHY1yCP5fNqnkMZyUvw4mfWbbGl6L6tCGLhqUbJl3xISp75B97zIso1iudeNyfiw4ds.I5l.nGozs8d-ellM2KSmaZnpMMkZ.bypSzTzLvuvwtuMu9lyB4Eml4u1nHdEj3RE9tXBpRaSizU6jyVFT99C3ZpBOd-MGAm8aLRzHpso9abPLkjqA3_,
		// ret_pay_method=CARD, tran_cd=00100000, use_pay_method=100000000000,
		// card_pay_method=KAKAO_MONEY}

		resultMap = paymentService.kakaoPay(receiverMap);

		System.out.println(" paymentService.kakaoPay, resultMap = " + resultMap);
		// paymentService.kakaoPay, tempMap = {amount=100, orderNumber=TEST_ORDER,
		// type=KAKAO_MONEY, authDateTime=20230601151907, cashReceipt=100,
		// transactionId=20230601151831KK0661, responseCode=0000, responseMsg=정상처리}

		String responseCode = resultMap.get("responseCode");
//		String responseCode = "0000" ; // 테스트 

		if ("0000".equals(responseCode)) { // 승인 완료
											// 이동할 페이지 설정
			mav.setViewName("/payment/kakaoS"); // tiles 설정으로 감 /munbanggu/src/main/resources/tiles/tiles_payment.xml

			// 결제 성공관련 데이터 뿌려주기

			// mav.addObject( "amount" , resultMap.get( "amount") );

			mav.addObject("kakaoData", resultMap);

		} else {// 승인 실패
			mav.setViewName("/payment/kakaoF");
		}
		return mav;
	}
	@RequestMapping(value = "/payment/naver/pay.do")
	public ModelAndView naverPay(@RequestParam Map<String, String> receiverMap) throws Exception {

		Map<String, String> resultMap = new HashMap<String, String>();
		ModelAndView mav = new ModelAndView();

		System.out.println("인증 데이터 확인" + receiverMap.toString());
		// 인증 데이터 확인{ordr_idxx=20230601145519KK0639, good_name=�����׽�Ʈ, good_mny=100,
		// buyr_name=choi, site_cd=A8QOB, req_tx=pay, pay_method=100000000000,
		// currency=410, kakaopay_direct=Y, module_type=01,
		// ordr_chk=20230601145519KK0639|100, param_opt_1=, param_opt_2=, param_opt_3=,
		// res_cd=0000, res_msg=����,
		// enc_info=1eJlts-UDA6-tySZVpaP8B7-KkfV1PBDTShPI0RDLQlXgdHgggV2YvuufqZgDqH4LA-5ewbRo9Xggvmug-GZPfQ25R0isnbYaEkEZYiVzkOcaBhUr-suYSfYXOVlbqdMdKuLGEe7e.O.ZQTtecVnCaUoaxiwNNhlWKy3UiH9ZJ3.N3wSwmQ.iJeLf3DLNiYyC-GexS8WUC8__,
		// enc_data=3vs6Js0Ax7hupytTaG5d4YtWiADrqUbMqfot7YhY30tJbvgllRAohmNmxtF1yoc3c7ZLHq0Ifcg5g.oLCycvXCVzvoShfaOSY96p70UnghoE.yZe43WBsyCEHA4k2t7-rNue0n8ygCboK.HC6jpaBmM6W7JgFKcTAK4UXoEK82mngwiJ7pL9voog4b6gI3OMKUm-Nnsxn8Qm4E.-RRr2p52OK9SIzz8N0kcamfXmFqd6.2YrJWho0D6kRMQ0wS4v6o4eubo4umf8Bx3d-CTC7NhknGLHcVbZ40GDW12e02If.pXq.o0NyzC0jcPypjOPPPpQ3MgD7NXcadAEQYN7ls3VnIxdfnM.CAR.SxoetOh25QvFZYVs95irGwCN0-1X38lKS-GenKvBefvmbOYP44VYLLUGzZ6jbWDjl4jVzt43cYiTwt695x147LysfDW7PtOqP9CMV5RAJWOR3rPjkzcUhGexabdPWCwnXhpl4UkJjIwvmeXU7rMHY1yCP5fNqnkMZyUvw4mfWbbGl6L6tCGLhqUbJl3xISp75B97zIso1iudeNyfiw4ds.I5l.nGozs8d-ellM2KSmaZnpMMkZ.bypSzTzLvuvwtuMu9lyB4Eml4u1nHdEj3RE9tXBpRaSizU6jyVFT99C3ZpBOd-MGAm8aLRzHpso9abPLkjqA3_,
		// ret_pay_method=CARD, tran_cd=00100000, use_pay_method=100000000000,
		// card_pay_method=KAKAO_MONEY}

		resultMap = paymentService.naverPay(receiverMap);

		System.out.println(" paymentService.naverPay, resultMap = " + resultMap);
		// paymentService.kakaoPay, tempMap = {amount=100, orderNumber=TEST_ORDER,
		// type=KAKAO_MONEY, authDateTime=20230601151907, cashReceipt=100,
		// transactionId=20230601151831KK0661, responseCode=0000, responseMsg=정상처리}

		String responseCode = resultMap.get("responseCode");
//		String responseCode = "0000" ; // 테스트 

		if ("0000".equals(responseCode)) { // 승인 완료
											// 이동할 페이지 설정
			mav.setViewName("/payment/naverS"); // tiles 설정으로 감 /munbanggu/src/main/resources/tiles/tiles_payment.xml

			// 결제 성공관련 데이터 뿌려주기

			// mav.addObject( "amount" , resultMap.get( "amount") );

			mav.addObject("kakaoData", resultMap);

		} else {// 승인 실패
			mav.setViewName("/payment/naverF");
		}
		return mav;
	}
}