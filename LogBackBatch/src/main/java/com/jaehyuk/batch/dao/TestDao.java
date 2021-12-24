package com.jaehyuk.batch.dao;

import com.jaehyuk.batch.vo.TestVo;

public interface TestDao {
	public TestVo getTestInfo(TestVo vo) throws Exception;
}
