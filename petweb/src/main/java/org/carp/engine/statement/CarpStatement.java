package org.carp.engine.statement;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.carp.exception.CarpException;
import org.carp.impl.AbstractCarpQuery;
import org.carp.impl.AbstractCarpSession;

/**
 * PreparedStatement���󹹽�
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
	 * ������ѯPreparedStatement���󣬸������ã�����Ӧ�Ƿ񴴽����������.
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
	 * ����PreparedStatement����
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
