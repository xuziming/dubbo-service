package com.simon.credit.dubbo.service.transfer;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.simon.credit.dubbo.service.vo.PageResponse;

public class ObjectTransform {

	public static final <S, T> PageResponse<T> transferDTO(PageResponse<S> pageResponse, Class<T> clazz) {
		PageResponse<T> response = new PageResponse<T>();

		response.setCount(pageResponse.getCount());
		response.setData(transferList(pageResponse.getData(), clazz));

		return response;
	}

	public static final <S, T> T transfer(S obj, Class<T> clazz) {
		return JSON.parseObject(JSON.toJSONString(obj), clazz);
	}

	public static final <S, T> List<T> transferList(List<S> list, Class<T> clazz) {
		return JSON.parseArray(JSON.toJSONString(list), clazz);
	}
}
