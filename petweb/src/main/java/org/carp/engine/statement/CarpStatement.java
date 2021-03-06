package org.carp.engine.statement;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.carp.exception.CarpException;
import org.carp.impl.AbstractCarpQuery;
import org.carp.impl.AbstractCarpSession;

/**
 * PreparedStatement对象构建
 * @author zhou
 *
 */
public class CarpStatement {
	private AbstractCarpSession session;
	private AbstractCarpQuery query;
	public CarpStatement(AbstractCarpSession _session){
		this.session = _session;
	}
	public CarpStatement(AbstractCarpQuery _query){
		this.query = _query;
		this.session = this.query.getSession();
	}
	
	
	
	/**
	 * 创建查询PreparedStatement对象，根据配置，自适应是否创建滚动结果集.
	 * @return
	 * @throws SQLException
	 * @throws CarpException
	 */
	public PreparedStatement createQueryStatement() throws SQLException, CarpException{
		PreparedStatement ps = null;
		if(query.getPreparedStatement() == null){
			if(this.query.getCarpSql().enableScrollableResultSet())
				ps = query.getSession().getConnection().prepareStatement(query.getSql(),ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			else
				ps = query.getSession().getConnection().prepareStatement(query.getSql());
			query.setPreparedStatement(ps);
		}else
			ps = query.getPreparedStatement();
		return ps;
	}
	
	/**
	 * 创建PreparedStatement对象
	 * @param sql
	 * @return
	 * @throws SQLException
	 * @throws CarpException
	 */
	public PreparedStatement createSessionStatement(String sql) throws SQLException, CarpException{
		if(!sql.equals(this.session.getSql())){
			this.session.setPs(this.session.getConnection().prepareStatement(sql));
			this.session.setSql(sql);
		}
		return this.session.getPs();
	}
}
