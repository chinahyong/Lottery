package com.pkx.lottery.dto;

import com.google.gson.annotations.Expose;

public class NoticeAuth {
	@Expose
	private String act="ajax_list";
	@Expose
	private String commisition;// 佣金
	@Expose
	private String keyword;
	@Expose
	private String page;
	@Expose
	private String page_count;
	@Expose
	private String page_size;
	@Expose
	private String private_type;
	@Expose
	private String record_count;
	@Expose
	private String rest_count;
	@Expose
	private String sort_by;// bet_amount process
	@Expose
	private String sort_order;// DESC ASC
	@Expose
	private String start;
	@Expose
	private String total_amount;
	@Expose
	private String type_id;// 彩种
	public NoticeAuth(){}
	public NoticeAuth(String type,String page,String page_size,String sort_oder,String sort_by){
		this.type_id=type;
		this.page_size=page_size;
		this.page=page;
		this.sort_by=sort_by;
		this.sort_order=sort_oder;
	}
	public String getAct() {
		return act;
	}
	public String getCommisition() {
		return commisition;
	}
	public String getKeyword() {
		return keyword;
	}
	public String getPage() {
		return page;
	}
	public String getPage_count() {
		return page_count;
	}
	public String getPage_size() {
		return page_size;
	}
	public String getPrivate_type() {
		return private_type;
	}
	public String getRecord_count() {
		return record_count;
	}
	public String getRest_count() {
		return rest_count;
	}
	public String getSort_by() {
		return sort_by;
	}
	public String getSort_order() {
		return sort_order;
	}
	public String getStart() {
		return start;
	}
	public String getTotal_amount() {
		return total_amount;
	}
	public String getType_id() {
		return type_id;
	}
}
