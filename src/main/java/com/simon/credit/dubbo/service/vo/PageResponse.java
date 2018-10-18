package com.simon.credit.dubbo.service.vo;

import java.io.Serializable;
import java.util.List;

/**
 * 分页查询结果
 * @author XUZIMING 2017-08-10
 */
public class PageResponse<T> implements Serializable {
	private static final long serialVersionUID = -3093138802406050898L;

	/** 分页数据 */
	private List<T> data;

	/** 记录总条数 */
	private Integer count;

	public PageResponse() {
	}

	public PageResponse(List<T> data, Integer count) {
		this.data = data;
		this.count = count;
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

}
