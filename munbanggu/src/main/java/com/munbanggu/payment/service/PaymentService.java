package com.munbanggu.payment.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.munbanggu.api.service.ApiService;

 

// 결제모듈 20230531 	
@Service
public class PaymentService {

	@Autowired
	private ApiService apiService;

	public Map<String, String> keyin(Map<String, String> receiverMap) {
		// 컨트롤 + 쉬프트 + O 자동 임포트
		Map<String, String> resultMap = new HashMap<String, String>();

		// API 연동 소스 작성 예정
		// restAPI 연동을 할 예정
		// 라이브러리를 사용해서 연동하기

		// 가맹점에서 생성하는 주문번호 20
		String orderNumber = "";
		// 카드번호 (-) 제외 11-20
		String cardNo = "";
		// 카드유효기간 MM 2
		String expireMonth = "";
		// 카드유효기간 YY 2 Y
		String expireYear = "";
		// 생년월일 또는 사업자번호 10 Y
		String birthday = "";
		// 카드비밀번호 카드비밀번호 앞 2자리 2 Y
		String cardPw = "";
		// 결제금액 총 결제금액 12 Y
		String amount = "";
		// 할부기간 0~24 1-2 Y
		String quota = "";
		// 상품명 판매 상품명 128 Y
		String itemName = "";
		// 구매자명 구매자의 이름 50 Y
		String userName = "";
		// 결제 위변조 방지를 위한 파라미터 서명 값(참고 2.1.1 승인요청 signature 생성방법)64 Y
		String signature = "";
		// 타임스탬프 YYYYMMDDHHMI24SS 형식 20 Y
		String timestamp = "";
		// apiCertKey
		String certKey = "ac805b30517f4fd08e3e80490e559f8e";
		// 보내야되는 값 셋팅 예정
//		receiverMap.get("cardNo");// >> 화면에서 입력한 카드번호

		// 보내야되는 값 셋팅 예정

		orderNumber = "TEST_00001";
		cardNo = receiverMap.get("cardNo");
		expireMonth = receiverMap.get("expireMonth");
		expireYear = receiverMap.get("expireYear");
		birthday = receiverMap.get("birthday");
		cardPw = receiverMap.get("cardPw");
		amount = "1004";
		quota = "0";
		itemName = "TEST 상품";
		userName = "최용수";

		timestamp = "20180212000000";

		// 암호화해서 생성해야함.
		try {
			signature = encrypt("himedia|" + orderNumber + "|" + amount + "|" + certKey + "|" + timestamp);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		// paramMap은 요청값들이 담길 맵
		Map<String, String> paramMap = new HashMap<String, String>();
		// 요청 주소
		String url = "https://api.testpayup.co.kr/v2/api/payment/himedia/keyin2";
		paramMap.put("orderNumber", orderNumber);
		paramMap.put("cardNo", cardNo);
		paramMap.put("expireMonth", expireMonth);
		paramMap.put("amount", amount);
		paramMap.put("quota", quota);
		paramMap.put("birthday", birthday);
		paramMap.put("cardPw", cardPw);
		paramMap.put("itemName", itemName);
		paramMap.put("userName", userName);
		paramMap.put("signature", signature);
		paramMap.put("timestamp", timestamp);
		paramMap.put("expireYear", expireYear);
		resultMap = apiService.restApi(paramMap, url);

		// 연동 결과

		return resultMap;
	}

	// 결제모듈 20230601
	public Map<String, String> kakaoOrder(Map<String, String> receiverMap) {

		Map<String, String> resultMap = new HashMap<String, String>();

		// API 통신
		// 카카오 3.1 주문요청

		// 요청 데이터 설정
		String orderNumber = "";
		String userAgent = "";
		String amount = "";
		String itemName = "";
		String userName = "";
		String returnUrl = "";
		String signature = "";
		String timestamp = "";
		String certKey = "ac805b30517f4fd08e3e80490e559f8e";

		orderNumber = "TEST_ORDER";
		userAgent = "WP";
		amount = "100"; // 실결제 됩니다... 금액 크게하면 안되요
		itemName = "강의테스트";
		userName = "choi";
		returnUrl = "test"; // 이거는 아무값이나 넣어도됩니다..
		timestamp = "20230601111111";
		// 암호화해서 생성해야함.
		try {
			signature = encrypt("himedia|" + orderNumber + "|" + amount + "|" + certKey + "|" + timestamp);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		// paramMap은 요청값들이 담길 맵
		Map<String, String> paramMap = new HashMap<String, String>();
		// 요청 주소

		String url = "https://api.testpayup.co.kr/ep/api/kakao/himedia/order";

		paramMap.put("orderNumber", orderNumber);
		paramMap.put("userAgent", userAgent);
		paramMap.put("amount", amount);
		paramMap.put("itemName", itemName);
		paramMap.put("userName", userName);
		paramMap.put("returnUrl", returnUrl);
		paramMap.put("amount", amount);
		paramMap.put("timestamp", timestamp);
		paramMap.put("signature", signature);

		resultMap = apiService.restApi(paramMap, url);

		// 연동 결과
		System.out.println("카카오 주문 API 통신 결과 = " + resultMap.toString());

		return resultMap;
	}

//카카오 승인 요청
	public Map<String, String> kakaoPay(Map<String, String> receiverMap) {

		Map<String, String> resultMap = new HashMap<String, String>();

		// 요청 데이터 설정
		String res_cd = "";
		String enc_info = "";
		String enc_data = "";
		String tran_cd = "";
		String card_pay_method = "";
		String ordr_idxx = "";

		res_cd = receiverMap.get("res_cd");
		enc_info = receiverMap.get("enc_info");
		enc_data = receiverMap.get("enc_data");
		tran_cd = receiverMap.get("tran_cd");
		card_pay_method = receiverMap.get("card_pay_method");
		ordr_idxx = receiverMap.get("ordr_idxx");

		Map<String, String> paramMap = new HashMap<String, String>();
		String url = "https://api.testpayup.co.kr/ep/api/kakao/himedia/pay";
		paramMap.put("res_cd", res_cd);
		paramMap.put("enc_info", enc_info);
		paramMap.put("enc_data", enc_data);
		paramMap.put("tran_cd", tran_cd);
		paramMap.put("card_pay_method", card_pay_method);
		paramMap.put("ordr_idxx", ordr_idxx);
//인증 데이터 확인{ordr_idxx=20230601145231KK0633, good_name=�����׽�Ʈ, good_mny=100, buyr_name=choi, site_cd=A8QOB, req_tx=pay, pay_method=100000000000, currency=410, kakaopay_direct=Y, module_type=01, ordr_chk=20230601145231KK0633|100, param_opt_1=, param_opt_2=, param_opt_3=, res_cd=0000, res_msg=����, enc_info=2n4Wlce-xgN4LmwxlFdckQkCwUWCQaefQenvGxV8gK0FpHiV.c.3l9mRkNiAAMRKkYxHyQU4XkAHYD7gepAYlOrL15WlsCz2wSUzFhXhtMl8HW-X1UPmpkaXn8qeMgwGAbnn22p-tbBVLduSyraF30wKWxgrubTaOrpJWSdozfUo0ra.ALGbeBEwiB9JF83f7AppXEE3kxX__, enc_data=4uLJNhsBw30go2PlAOmWwvrV1Hkag3gdpTR3BmLzdD6RX0UQQuxDaHIfAQsJTmIyoNM2lbUrOTdqYqxG5P-xnoOP8w0VKGmkUAVFSoN7VkSu1oHFwy9eAyTfIJTTlfb6JiUrO4UENZ.gmfUI4YgP0mcRIV3XQLk3O9OF0acMsOSsSunLeLpkYZnUO8HFPx9NL0xszSgl6SZB.mqELSThaRuhUkXmOPX5aWTX-6wphraxBzS9WQKCNipZSnmK2X9.PgjD.dakpXZqEm30hIB63f6WwpXcfcrZN.Q-intTQHhwKMbclYPEdT4naNAtkOQXduyGruGJWKVftJm0Chl6wTDz8u9Nz1YL3BtkHP10yqhkOFZV6FHbg33oPs4Iz6It8JR2sGvryOrqPSx047TP2LruVJrw2ezxSd2DEZ8N6DYbwKLvBTv8M1HQfE9FJhh6wsmj3nxTd8pH6pl.L6VMz1M3rIlGBIQkPsRu6qG4r1bTY101zpuYi-gcsmny7.0FNOBkWYQwyqtYnI6-OG9TSnzCxxFHaMr8hyMPqG5j0Xpe6uaNYAui.kwVa5-xVHQGXDhHde.NQ6iiPn68apn5o9rw0vtz.-R2yDD8nwyzAOoJ8ioD8N.3.hF.3G0JoU.5y0VyWwxmJp7W0MLK9y-3XrDH3qKfLvvi-R8k-o.p4avz_, ret_pay_method=CARD, tran_cd=00100000, use_pay_method=100000000000, card_pay_method=KAKAO_MONEY}

		resultMap = apiService.restApi(paramMap, url);
//  paymentService.kakaoPay, tempMap = {amount=100, orderNumber=TEST_ORDER, type=KAKAO_MONEY, authDateTime=20230601152924, cashReceipt=100, transactionId=20230601152858KK0677, responseCode=0000, responseMsg=정상처리}
		System.out.println("kakaoPay 통신 결과 = " + resultMap.toString());
		return resultMap;
	}
	public Map<String, String> naverOrder(Map<String, String> receiverMap) {

		Map<String, String> resultMap = new HashMap<String, String>();

		// API 통신
		// 카카오 3.1 주문요청
		System.out.println("naverOrder = " + receiverMap.toString());
		// 요청 데이터 설정
		String orderNumber = "";
		String userAgent = "";
		
		String amount = "";
		String itemName = "";
		String userName = "";
		String returnUrl = "";
		String signature = "";
		String timestamp = "";
		String certKey = "ac805b30517f4fd08e3e80490e559f8e";

		orderNumber = "TEST_ORDER";
		userAgent = "WP";
		amount = "100"; // 실결제 됩니다... 금액 크게하면 안되요
		itemName = "강의테스트";
		userName = "choi";
		returnUrl = "test"; // 이거는 아무값이나 넣어도됩니다..
		timestamp = "20230601111111";
		// 암호화해서 생성해야함.
		try {
			signature = encrypt("himedia|" + orderNumber + "|" + amount + "|" + certKey + "|" + timestamp);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("naverOrder = " + receiverMap);
		// paramMap은 요청값들이 담길 맵
		Map<String, String> paramMap = new HashMap<String, String>();
		// 요청 주소
		String url = "https://api.testpayup.co.kr/ep/api/naver/himedia/order";
		paramMap.put("orderNumber", orderNumber);
		paramMap.put("userAgent", userAgent);
		String payType = "";
		payType = receiverMap.get("payType");
 
		if("POINT".equals(payType)){
			payType =  "POINT";
		}else{
			payType = "CARD";
		}
 
		paramMap.put("payType", payType);
		paramMap.put("amount", amount);
		paramMap.put("itemName", itemName);
		paramMap.put("userName", userName);
		paramMap.put("returnUrl", returnUrl);
		paramMap.put("amount", amount);
		paramMap.put("timestamp", timestamp);
		paramMap.put("signature", signature);

		resultMap = apiService.restApi(paramMap, url);

		// 연동 결과
		System.out.println("네이버 주문 API 통신 결과 = " + resultMap.toString());

		return resultMap;
	}

//카카오 승인 요청
	public Map<String, String> naverPay(Map<String, String> receiverMap) {

		Map<String, String> resultMap = new HashMap<String, String>();

		// 요청 데이터 설정
		String res_cd = "";
		String enc_info = "";
		String enc_data = "";
		String tran_cd = "";
		String card_pay_method = "";
		String ordr_idxx = "";

		res_cd = receiverMap.get("res_cd");
		enc_info = receiverMap.get("enc_info");
		enc_data = receiverMap.get("enc_data");
		tran_cd = receiverMap.get("tran_cd");
		card_pay_method = receiverMap.get("card_pay_method");
		ordr_idxx = receiverMap.get("ordr_idxx");

		Map<String, String> paramMap = new HashMap<String, String>();
		String url = "https://api.testpayup.co.kr/ep/api/naver/himedia/pay";
		paramMap.put("res_cd", res_cd);
		paramMap.put("enc_info", enc_info);
		paramMap.put("enc_data", enc_data);
		paramMap.put("tran_cd", tran_cd);
		paramMap.put("card_pay_method", card_pay_method);
		paramMap.put("ordr_idxx", ordr_idxx);
//인증 데이터 확인{ordr_idxx=20230601145231KK0633, good_name=�����׽�Ʈ, good_mny=100, buyr_name=choi, site_cd=A8QOB, req_tx=pay, pay_method=100000000000, currency=410, kakaopay_direct=Y, module_type=01, ordr_chk=20230601145231KK0633|100, param_opt_1=, param_opt_2=, param_opt_3=, res_cd=0000, res_msg=����, enc_info=2n4Wlce-xgN4LmwxlFdckQkCwUWCQaefQenvGxV8gK0FpHiV.c.3l9mRkNiAAMRKkYxHyQU4XkAHYD7gepAYlOrL15WlsCz2wSUzFhXhtMl8HW-X1UPmpkaXn8qeMgwGAbnn22p-tbBVLduSyraF30wKWxgrubTaOrpJWSdozfUo0ra.ALGbeBEwiB9JF83f7AppXEE3kxX__, enc_data=4uLJNhsBw30go2PlAOmWwvrV1Hkag3gdpTR3BmLzdD6RX0UQQuxDaHIfAQsJTmIyoNM2lbUrOTdqYqxG5P-xnoOP8w0VKGmkUAVFSoN7VkSu1oHFwy9eAyTfIJTTlfb6JiUrO4UENZ.gmfUI4YgP0mcRIV3XQLk3O9OF0acMsOSsSunLeLpkYZnUO8HFPx9NL0xszSgl6SZB.mqELSThaRuhUkXmOPX5aWTX-6wphraxBzS9WQKCNipZSnmK2X9.PgjD.dakpXZqEm30hIB63f6WwpXcfcrZN.Q-intTQHhwKMbclYPEdT4naNAtkOQXduyGruGJWKVftJm0Chl6wTDz8u9Nz1YL3BtkHP10yqhkOFZV6FHbg33oPs4Iz6It8JR2sGvryOrqPSx047TP2LruVJrw2ezxSd2DEZ8N6DYbwKLvBTv8M1HQfE9FJhh6wsmj3nxTd8pH6pl.L6VMz1M3rIlGBIQkPsRu6qG4r1bTY101zpuYi-gcsmny7.0FNOBkWYQwyqtYnI6-OG9TSnzCxxFHaMr8hyMPqG5j0Xpe6uaNYAui.kwVa5-xVHQGXDhHde.NQ6iiPn68apn5o9rw0vtz.-R2yDD8nwyzAOoJ8ioD8N.3.hF.3G0JoU.5y0VyWwxmJp7W0MLK9y-3XrDH3qKfLvvi-R8k-o.p4avz_, ret_pay_method=CARD, tran_cd=00100000, use_pay_method=100000000000, card_pay_method=KAKAO_MONEY}

		resultMap = apiService.restApi(paramMap, url);
//  paymentService.kakaoPay, tempMap = {amount=100, orderNumber=TEST_ORDER, type=KAKAO_MONEY, authDateTime=20230601152924, cashReceipt=100, transactionId=20230601152858KK0677, responseCode=0000, responseMsg=정상처리}
		System.out.println("naverPay 통신 결과 = " + resultMap.toString());
		return resultMap;
	}
	public String encrypt(String text) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		md.update(text.getBytes());
		return bytesToHex(md.digest());
	}

	private String bytesToHex(byte[] bytes) {
		StringBuilder builder = new StringBuilder();
		for (byte b : bytes) {
			builder.append(String.format("%02x", b));
		}
		return builder.toString();
	}

}
