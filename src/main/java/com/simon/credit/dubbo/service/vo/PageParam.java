package com.simon.credit.dubbo.service.vo;

import java.io.Serializable;

/**
 * 分页查询参数
 * @author XUZIMING 2017-08-10
 */
public class PageParam implements Serializable {
	private static final long serialVersionUID = 4948431607512426670L;

	/** 默认显示首页(第一页) */
	protected int pageNow = 1;

	/** 默认每页显示10条数据 */
	protected int pageSize = 10;

	/** 偏移量(意为:跳过多少条, 如:【limit 20, 10】表示跳过20条取10条, 即取结果集的第21至第30条数据) */
	protected int offset;

	public PageParam() {
	}

	public PageParam(int pageNow, int pageSize) {
		if (pageNow < 1) {
			this.pageNow = 1;
		} else {
			this.pageNow = pageNow;
		}
		this.pageSize = pageSize;
	}

	public int getPageNow() {
		return pageNow;
	}

	public void setPageNow(int pageNow) {
		if (pageNow < 1) {
			this.pageNow = 1;
		} else {
			this.pageNow = pageNow;
		}
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		if (pageSize <= 0) {
			return;// 取默认值
		} else {
			this.pageSize = pageSize;
		}
	}

	public int getOffset() {
		if (offset == 0) {
			return offset = (pageNow - 1) * pageSize;
		} else {
			return offset;
		}
	}

}
