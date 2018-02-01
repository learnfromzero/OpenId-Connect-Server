package com.shaun.commons.service;

import com.shaun.commons.dao.IDao;

public interface IBaseDaoService {
	/**
	 * 获取跨部门应用模块DAO
	 *
	 * @return
	 */
	public IDao getAppDao();
}