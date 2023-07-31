package com.munbanggu.customer.vo;

import java.sql.Date;

import lombok.Data;
@Data
public class NoticeVO {
	private int notice_id;
	private String notice_title;
	private String notice_content;
	private Date   notice_date;	
	private String  member_id;	
	private String result_type;
	
	public NoticeVO() {
	}

	public int getNotice_id() {
		return notice_id;
	}

	public void setNotice_id(int notice_id) {
		this.notice_id = notice_id;
	}

	public String getNotice_title() {
		return notice_title;
	}

	public void setNotice_title(String notice_title) {
		this.notice_title = notice_title;
	}

	public String getNotice_content() {
		return notice_content;
	}

	public void setNotice_content(String notice_content) {
		this.notice_content = notice_content;
	}

	public Date getNotice_date() {
		return notice_date;
	}

	public void setNotice_date(Date notice_date) {
		this.notice_date = notice_date;
	}
	
	
	
}
