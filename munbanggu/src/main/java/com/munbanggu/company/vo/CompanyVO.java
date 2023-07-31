package com.munbanggu.company.vo;

import java.sql.Date;

import org.springframework.stereotype.Component;

@Component("companyVO")
public class CompanyVO {
	private int company_id;
	private int goods_id;
	private String business_registration_number;
	private String company_name;
	private String representative_name;
	private String zipcode;
	private String roadAddress;
	private String jibunAddress;
	private String namujiAddress;
	private String business_type;
	private String business_item;
	private String corporation_registration_number;
	private String industry_code;
	private String industry_name;
	private String telephone;
	private String fax;
	private String email;
	private String license_number;
	private String del_yn;
	private Date license_date;
	public int getCompany_id() {
		return company_id;
	}
	public void setCompany_id(int company_id) {
		this.company_id = company_id;
	}
	public int getGoods_id() {
		return goods_id;
	}
	public void setGoods_id(int goods_id) {
		this.goods_id = goods_id;
	}
	public String getBusiness_registration_number() {
		return business_registration_number;
	}
	public void setBusiness_registration_number(String business_registration_number) {
		this.business_registration_number = business_registration_number;
	}
	public String getCompany_name() {
		return company_name;
	}
	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}
	public String getRepresentative_name() {
		return representative_name;
	}
	public void setRepresentative_name(String representative_name) {
		this.representative_name = representative_name;
	}
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	public String getRoadAddress() {
		return roadAddress;
	}
	public void setRoadAddress(String roadAddress) {
		this.roadAddress = roadAddress;
	}
	public String getJibunAddress() {
		return jibunAddress;
	}
	public void setJibunAddress(String jibunAddress) {
		this.jibunAddress = jibunAddress;
	}
	public String getNamujiAddress() {
		return namujiAddress;
	}
	public void setNamujiAddress(String namujiAddress) {
		this.namujiAddress = namujiAddress;
	}
	public String getBusiness_type() {
		return business_type;
	}
	public void setBusiness_type(String business_type) {
		this.business_type = business_type;
	}
	public String getBusiness_item() {
		return business_item;
	}
	public void setBusiness_item(String business_item) {
		this.business_item = business_item;
	}
	public String getCorporation_registration_number() {
		return corporation_registration_number;
	}
	public void setCorporation_registration_number(String corporation_registration_number) {
		this.corporation_registration_number = corporation_registration_number;
	}
	public String getIndustry_code() {
		return industry_code;
	}
	public void setIndustry_code(String industry_code) {
		this.industry_code = industry_code;
	}
	public String getIndustry_name() {
		return industry_name;
	}
	public void setIndustry_name(String industry_name) {
		this.industry_name = industry_name;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getLicense_number() {
		return license_number;
	}
	public void setLicense_number(String license_number) {
		this.license_number = license_number;
	}
	public String getDel_yn() {
		return del_yn;
	}
	public void setDel_yn(String del_yn) {
		this.del_yn = del_yn;
	}
	public Date getLicense_date() {
		return license_date;
	}
	public void setLicense_date(Date license_date) {
		this.license_date = license_date;
	}

	
}
