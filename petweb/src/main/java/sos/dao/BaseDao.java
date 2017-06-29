package sos.dao;

import java.io.Serializable;
import java.util.List;

import org.carp.CarpDataSetQuery;
import org.carp.CarpQuery;
import org.carp.CarpSession;
import org.carp.DataSet;
import org.carp.beans.PrimarysMetadata;
import org.carp.exception.CarpException;
import org.carp.factory.BeansFactory;

import com.sunstar.sos.cache.CachedClient;
import com.sunstar.sos.cfg.Sql;
import com.sunstar.sos.util.ObjectUtil;
import com.sunstar.sos.util.SequencesUtil;

/**
 * ����DAO�����࣬������ݵ�CRUD������
 * @author zhou
 *
 */
public class BaseDao {

	/**
	 * ���ݿ�Ự����
	 */
	private CarpSession _session;
	
	public CarpSession getSession() {
		return _session;
	}

	public void setSession(CarpSession session) {
		this._session = session;
	}

	/**
	 * ��ѯ����ѯcls��Ӧ��table��¼
	 * @param cls
	 * @return
	 * @throws CarpException 
	 * @throws Exception
	 */
	public List<?> search(Class<?> cls) throws Exception{
		return _session.creatQuery(cls).list();
	}
	
	/**
	 * ��ѯ���Ѳ�ѯ�ļ�¼��װ��cls�Ķ�����
	 * @param cls
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	public List<?> search(Class<?> cls,String sql) throws Exception {
		return _session.creatQuery(cls,sql).list();
	}
	
	/**
	 * ��ѯ���Ѳ�ѯ�ļ�¼��װ��cls�Ķ�����
	 * @param cls
	 * @param _sql sql����
	 * @return
	 * @throws Exception
	 */
	public List<?> search(Class<?> cls, Sql _sql) throws Exception{
		CarpQuery query = _session.creatQuery(cls, _sql.getSql());
		_sql.processParameters(query);
		return query.list();
	}
	/**
	 * ��ѯ��֧�ַ�ҳ����
	 * @param cls
	 * @param page
	 * @param size
	 * @return
	 * @throws Exception
	 */
	public List<?> searchPage(Class<?> cls,int page,int size) throws Exception {
		CarpQuery query = _session.creatQuery(cls);
		query.setFirstIndex((page-1)*size);
		query.setMaxCount(size);
		return query.list();
	}
	
	/**
	 * ��ѯ��֧�ַ�ҳ����
	 * @param cls
	 * @param sql
	 * @param page
	 * @param size
	 * @return
	 * @throws Exception
	 */
	public List<?> searchPage(Class<?> cls, String sql,int page,int size) throws Exception  {
		CarpQuery query = _session.creatQuery(cls,sql);
		query.setFirstIndex((page-1)*size);
		query.setMaxCount(size);
		return query.list();
	}
	
	
	/**
	 * ��ѯ��֧�ַ�ҳ����
	 * @param cls
	 * @param _sql sql����
	 * @param page
	 * @param size
	 * @return
	 * @throws Exception
	 */
	public List<?> searchPage(Class<?> cls, Sql _sql,int page,int size) throws Exception  {
		CarpQuery query = _session.creatQuery(cls,_sql.getSql());
		query.setFirstIndex((page-1)*size);
		query.setMaxCount(size);
		_sql.processParameters(query);
		return query.list();
	}
	
	/**
	 * ��ѯ
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	public DataSet dataSet(String sql) throws Exception {
		CarpDataSetQuery query = _session.creatDataSetQuery(sql);
		return query.dataSet();
	}
	
	/**
	 * ��ѯ
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	public DataSet dataSet(Sql sql) throws Exception {
		CarpDataSetQuery query = _session.creatDataSetQuery(sql.getSql());
		sql.processParameters(query);
		return query.dataSet();
	}
	
	/**
	 * ��ѯ
	 * @param sql
	 * @param page
	 * @param size
	 * @return
	 * @throws Exception
	 */
	public DataSet dataSetPage(String sql,int page,int size) throws Exception  {
		CarpDataSetQuery query = _session.creatDataSetQuery(sql);
		query.setFirstIndex((page-1)*size);
		query.setMaxCount(size);
		return query.dataSet();
	}
	
	/**
	 * ��ѯ
	 * @param sql
	 * @param page
	 * @param size
	 * @return
	 * @throws Exception
	 */
	public DataSet dataSetPage(Sql sql,int page,int size) throws Exception  {
		CarpDataSetQuery query = _session.creatDataSetQuery(sql.getSql());
		query.setFirstIndex((page-1)*size);
		query.setMaxCount(size);
		sql.processParameters(query);
		return query.dataSet();
	}
	
	/**
	 * �������
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public Serializable add(Object obj)throws Exception {
		this.processId(obj);
		return  _session.save(obj);
	}
	
	/**
	 * �������,�����浽�ڴ���
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public Serializable addCache(Object obj)throws Exception {
		this.processId(obj);
		Serializable id = _session.save(obj);
		this.setCacheValue(id, obj.getClass(), obj);
		return id;
	}
	
	/**
	 * ��������
	 * @param objs
	 * @return
	 * @throws Exception
	 */
	public Serializable[] add(Object[] objs)throws Exception {
		Serializable[] ids = new Serializable[objs.length];
		for(int i = 0, count = objs.length; i < count; ++i){
			this.processId(objs[i]);
			ids[i] = _session.save(objs[i]);
		}
		return ids;
	}
	
	/**
	 * �������棬�����浽�ڴ���
	 * @param objs
	 * @return
	 * @throws Exception
	 */
	public Serializable[] addCache(Object[] objs)throws Exception {
		Serializable[] ids = new Serializable[objs.length];
		for(int i = 0, count = objs.length; i < count; ++i){
			this.processId(objs[i]);
			ids[i] = _session.save(objs[i]);
			this.setCacheValue(ids[i], objs[i].getClass(), objs[i]);
		}
		return ids;
	}
	
	
	
	/**
	 * ����pojo�������
	 * @param obj
	 * @throws CarpException
	 */
	private void processId(Object obj) throws CarpException{
		PrimarysMetadata pmd = BeansFactory.getBean(obj.getClass()).getPrimarys().get(0);
		Object _idValue = pmd.getValue(obj);
		if(_idValue==null)
			pmd.setValue(obj,ObjectUtil.getValue(pmd.getFieldType(),  SequencesUtil.getInstance().getNextPk(obj.getClass())));
		else if(!_idValue.getClass().equals(String.class) && Integer.valueOf(_idValue+"").intValue() == 0){
			pmd.setValue(obj,ObjectUtil.getValue(pmd.getFieldType(),  SequencesUtil.getInstance().getNextPk(obj.getClass())));
		}
	}	
	
	/**
	 * ��������ڴ���
	 * @param id
	 * @param cls
	 * @param o
	 * @throws CarpException 
	 */
	private void setCacheValue(Serializable id, Class<?> cls, Object o) throws CarpException{
		CachedClient.getClient().set(getKey(cls,id), o);
	}
	
	private String getKey(Class<?> cls,Object id) throws CarpException{
		String table = BeansFactory.getBean(cls).getTable().toUpperCase();
		return table+"_@_"+id;
	}
	
	/**
	 * ɾ������
	 * @param obj
	 * @throws Exception
	 */
	public void delete(Object obj)throws Exception {
		_session.delete(obj);
	}
	
	/**
	 * ɾ������,���Ҵ��ڴ���Ҳ�����
	 * @param obj
	 * @throws Exception
	 */
	public void deleteCache(Object obj)throws Exception {
		_session.delete(obj);
		PrimarysMetadata pmd = BeansFactory.getBean(obj.getClass()).getPrimarys().get(0);
		Object id = pmd.getValue(obj);
		String key = getKey(obj.getClass(),id);
		CachedClient.getClient().delete(key);
	}
	
	/**
	 * ɾ����¼����������
	 * @param cls
	 * @param id
	 * @throws Exception
	 */
	public void delete(Class<?> cls, Serializable id) throws Exception {
		_session.delete(cls, id);
	}
	
	/**
	 * �����������ڴ漰���ݿ���ɾ����¼��
	 * @param cls
	 * @param id
	 * @throws Exception
	 */
	public void deleteCache(Class<?> cls, Serializable id) throws Exception {
		_session.delete(cls, id);
		String key = getKey(cls,id);
		CachedClient.getClient().delete(key);
	}
	
	/**
	 * ����ɾ�����󣬸�������
	 * @param cls
	 * @param ids
	 * @throws Exception
	 */
	public void delete(Class<?> cls, Serializable[] ids) throws Exception {
		for(int i = 0, count = ids.length; i < count; ++i){
			_session.delete(cls, ids[i]);
		}
	}
	/**
	 *��������,���ڴ漰���ݿ��� ����ɾ������
	 * @param cls
	 * @param ids
	 * @throws Exception
	 */
	public void deleteCache(Class<?> cls, Serializable[] ids) throws Exception {
		for(int i = 0, count = ids.length; i < count; ++i){
			_session.delete(cls, ids[i]);
			String key = getKey(cls,ids[i]);
			CachedClient.getClient().delete(key);
		}
	}
	
	/**
	 * ���¶���
	 * @param obj
	 * @throws Exception
	 */
	public void update(Object obj) throws Exception {
		_session.update(obj);
	}
	
	/**
	 * ���»��漰DB�еĶ���
	 * @param obj
	 * @throws Exception
	 */
	public void updateCache(Object obj) throws Exception {
		_session.update(obj);
		this.refreshCacheValue(obj);
	}
	
	/**
	 * �������¶���
	 * @param objs
	 * @throws Exception
	 */
	public void update(Object[] objs) throws Exception {
		for(int i = 0, count = objs.length; i < count; ++i){
			_session.update(objs[i]);
		}
	}
	
	/**
	 * �������»��漰���ݿ��еĶ���
	 * @param objs
	 * @throws Exception
	 */
	public void updateCache(Object[] objs) throws Exception {
		for(int i = 0, count = objs.length; i < count; ++i){
			_session.update(objs[i]);
			this.refreshCacheValue(objs[i]);
		}
	}
	
	private void refreshCacheValue(Object obj) throws CarpException{
		PrimarysMetadata pmd = BeansFactory.getBean(obj.getClass()).getPrimarys().get(0);
		Object id = pmd.getValue(obj);
		String key = getKey(obj.getClass(),id);
		if(CachedClient.getClient().get(key) !=  null)
			CachedClient.getClient().replace(key, obj);
		else
			CachedClient.getClient().set(key, obj);
	}
	
	/**
	 * �����������Ҷ���
	 * @param cls
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Object find(Class<?> cls, Serializable id) throws Exception {
		return _session.get(cls, id);
	}
	
	/**
	 * �����������Ҷ���,�ȴӻ����в��ң��Ҳ����ٴ�DB�в���
	 * @param cls
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Object findCache(Class<?> cls, Serializable id) throws Exception {
		String key = getKey(cls,id);
		Object o = CachedClient.getClient().get(key);
		if(o == null){
			o = _session.get(cls, id);
			if(o != null)
				CachedClient.getClient().set(key, o);
		}
		return o;
	}
	
	/**
	 * ����sql����countͳ��
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	public long count(String sql) throws Exception {
		DataSet ds = _session.creatDataSetQuery(sql).dataSet();
		if(ds.count() != 1)
			return 0;
		long count = new Long(ds.getRowData(0).get(0)+"");
		return count;
	}
	
	/**
	 * ����sql����countͳ��
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	public long count(Sql sql) throws Exception {
		CarpDataSetQuery query = _session.creatDataSetQuery(sql.getSelectSql());
		sql.processParameters(query);
		DataSet ds = query.dataSet();
		if(ds.count() != 1)
			return 0;
		long count = new Long(ds.getRowData(0).get(0)+"");
		return count;
	}
	
	/**
	 * ִ��update��delete��insert��sql��� 
	 * @param sql
	 * @throws Exception
	 */
	public void executeUpdate(String sql) throws Exception {
		_session.creatUpdateQuery(sql).executeUpdate();
	}
	
	/**
	 * ִ��update��delete��insert��sql��� 
	 * @param sql
	 * @throws Exception
	 */
	public void executeUpdate(Sql sql) throws Exception {
		CarpQuery query = _session.creatUpdateQuery(sql.getSql());
		sql.processParameters(query);
		query.executeUpdate();
	}
}
