package com.shaun.commons.dao;

import com.ibatis.sqlmap.client.SqlMapExecutor;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.orm.ibatis.SqlMapClientTemplate;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

/**
 * @Author
 * @Description
 * @Date Created on 2017/9/6.
 */
public class IDao{

    public SqlMapClientTemplate sqlMapClientTemplate;

    public SqlMapClientTemplate getSqlMapClientTemplate() {
        return sqlMapClientTemplate;
    }

    public void setSqlMapClientTemplate(SqlMapClientTemplate sqlMapClientTemplate) {
        this.sqlMapClientTemplate = sqlMapClientTemplate;
    }

    public int delete(String statementName) throws DataAccessException {
        return this.getSqlMapClientTemplate().delete(statementName);
    }

    public int delete(String statementName, Object obj) throws DataAccessException{
        return this.getSqlMapClientTemplate().delete(statementName, obj);
    }

    public Object insert(String statementName) throws DataAccessException{
        return this.getSqlMapClientTemplate().insert(statementName);
    }

    public Object insert(String statementName, Object obj) throws DataAccessException{
        return this.getSqlMapClientTemplate().insert(statementName,obj);
    }

    public int update(String statementName) throws DataAccessException{
        return this.getSqlMapClientTemplate().update(statementName);
    }

    public int update(String statementName, Object obj) throws DataAccessException{
        return this.getSqlMapClientTemplate().update(statementName,obj);
    }

    public Object queryForObject(String statementName) throws DataAccessException{
        return this.getSqlMapClientTemplate().queryForObject(statementName);
    }

    public Object queryForObject(String statementName, Object obj) throws DataAccessException{
        return this.getSqlMapClientTemplate().queryForObject(statementName,obj);
    }

    public List queryForList(String statementName) throws DataAccessException{
        return this.getSqlMapClientTemplate().queryForList(statementName);
    }

    public List queryForList(String statementName, Object obj) throws DataAccessException{
        return this.getSqlMapClientTemplate().queryForList(statementName, obj);
    }

    public List queryForPage(String var1, Object var2, int var3, int var4){
        return this.getSqlMapClientTemplate().queryForList(var1, var2, var3, var4);
    }

    public SqlMapClientTemplate getSqlMapClientTemplateTemplate(){
        return this.getSqlMapClientTemplate();
    }

    public int insertBatch(final String statementName,final List<?> list) throws DataAccessException{
        this.getSqlMapClientTemplate().execute(new SqlMapClientCallback() {
            public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
                executor.startBatch();
                Iterator var2 = list.iterator();
                while(var2.hasNext()) {
                    Object object = var2.next();
                    executor.insert(statementName, object);
                }

                executor.executeBatch();
                return null;
            }
        });
        return list.size();
    }
}
