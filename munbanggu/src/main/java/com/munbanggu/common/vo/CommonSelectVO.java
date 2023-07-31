package com.munbanggu.common.vo;

import org.springframework.stereotype.Component;
import lombok.Data;

@Component("commonSelectVO")
@Data
public class CommonSelectVO {
	private String result_type;

	public String getResult_type() {
		return result_type;
	}

	public void setResult_type(String result_type) {
		this.result_type = result_type;
	}



}

