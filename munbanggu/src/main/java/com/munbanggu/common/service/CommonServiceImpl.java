package com.munbanggu.common.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.munbanggu.common.dao.CommonDAO;
import com.munbanggu.common.vo.CommonSelectVO;

@Service("commonService")
@Transactional(propagation=Propagation.REQUIRED)
public class CommonServiceImpl  implements CommonService{
	@Autowired
	private CommonDAO commonDAO;

	@Override
	public List<Object> commonSearchList(Map condMap) throws Exception {
		List<Object> searchList ;
		searchList = commonDAO.commonSearchSelectList(condMap);
		return searchList;
	}

	@Override
	public int commonCount(Map condMap) throws Exception {
		int count ;
		count = commonDAO.commonCountSelect(condMap);
		return count;
	}


	

}
