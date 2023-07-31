package com.munbanggu.common.service;

import java.util.List;
import java.util.Map;

import com.munbanggu.common.vo.CommonSelectVO;
import com.munbanggu.order.vo.OrderVO;

public interface CommonService {
	public List<Object>commonSearchList(Map condMap) throws Exception;
	public int commonCount( Map condMap ) throws Exception ;
	
	
}
