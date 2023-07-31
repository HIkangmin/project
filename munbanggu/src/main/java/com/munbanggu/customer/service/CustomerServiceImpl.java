package com.munbanggu.customer.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.munbanggu.customer.dao.CustomerDAO;
import com.munbanggu.customer.vo.BoardVO;




@Service("CustomerService")
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerDAO customerDAO;

    //공지사항
    @Override
    public Map<String, List> NoticeList(String startRowNum, String endRowNum) throws Exception {
        Map<String, List> noticeMap = new HashMap<String, List>();
        
        List<BoardVO> noticeList = customerDAO.NoticeList(startRowNum, endRowNum);
        noticeMap.put("noticeList", noticeList);
        
        return noticeMap;
    }

    public List selectNoticeDetail(String notice_id) throws Exception{
		return customerDAO.selectNoticeDetail(notice_id);
	}

    
    @Override
    public List<String> NoticekeywordSearch(String keyword) {
        return customerDAO.NoticeKeywordSearch(keyword);
    }

  //FAQ
    @Override
    public Map<String, List> FaqList(String startRowNum, String endRowNum) throws Exception {
        Map<String, List> faqMap = new HashMap<String, List>();
        
        List<BoardVO> selectList = customerDAO.FaqList(startRowNum, endRowNum);
        faqMap.put("selectList", selectList);
        
        return faqMap;
    }

    public List selectFaqDetail(String faq_id) throws Exception{
		return customerDAO.selectFaqDetail(faq_id);
	}

    
    @Override
    public List<String> FaqkeywordSearch(String keyword) {
        return customerDAO.FaqKeywordSearch(keyword);
    }
    
    
    
  //QNA
    @Override
    public Map<String, List> QnaList(String startRowNum, String endRowNum) throws Exception {
        Map<String, List> qnaMap = new HashMap<String, List>();
        
        List<BoardVO> qnaList = customerDAO.QnaList(startRowNum, endRowNum);
        qnaMap.put("qnaList", qnaList);
        
        return qnaMap;
    }

    public List selectQnaDetail(String qna_id) throws Exception{
		return customerDAO.selectQnaDetail(qna_id);
	}

   
    
    @Override
	public int addQna(Map newQnaMap) throws Exception{
		int qna_id = customerDAO.insertQna(newQnaMap);
		return qna_id;
	}


}