package sos.bpo;

import com.sunstar.sos.action.BaseAction;
import com.sunstar.sos.annotation.AConnection;
import com.sunstar.sos.annotation.ATransaction;
import com.sunstar.sos.cache.CachedClient;
import com.sunstar.sos.cfg.Sql;
import com.sunstar.sos.constants.Constants;
import com.sunstar.sos.dao.BaseDao;
import com.sunstar.sos.permission.LoginUser;
import com.sunstar.sos.util.LogsUtil;
import com.sunstar.sos.util.ObjectUtil;
import com.sunstar.sos.util.page.PageFormData;
import org.apache.log4j.Logger;
import org.carp.DataSet;
import org.carp.beans.CarpBean;
import org.carp.beans.DICMetadata;
import org.carp.exception.CarpException;
import org.carp.factory.BeansFactory;
import org.carp.transaction.Transaction;

import java.io.File;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * ҵ��������࣬�����װ�˴󲿷�(��)��ҵ������߼���һ���ҵ����Ҫʵ���Լ���ҵ�����࣬
 * ������ڸ��ӵ����еĲ�ͨ�õ�ҵ�����������Ҫ�̳б��࣬���Ǹ���ķ������߶����µ�ҵ�񷽷���
 * @author Administrator
 *
 */
public class BaseBpo{
	protected static final Logger logger = Logger.getLogger(BaseBpo.class);
	private String result="";
	private Class<?> pojoClass;
	private BaseDao dao;
	private String table;
	private String action;
	private BaseAction baseAction;
	private Transaction _tx;//�������
	
	
	public void setTransaction(Transaction tx){
		this._tx = tx;
	}
	public Transaction getTransaction(){
		return this._tx;
	}
	/**��������*/
	public String getAction() {
		return action;
	}

	public void setBaseAction(BaseAction _action){
		this.baseAction = _action;
	}
	protected BaseAction getBaseAction(){
		return this.baseAction;
	}
	/**
	 * ����pojo��ȡ����ӳ������ݱ���
	 * @param cls pojo��
	 * @return
	 * @throws CarpException 
	 */
	public String getTable(){
		if(table == null)
			try {
				table = BeansFactory.getBean(this.pojoClass).getTable();
			} catch (CarpException e) {
				table = "";
			}
		return table;
	}
	
	/**
	 * ���ó־û�����pojo��
	 * @param cls
	 */
	public void setPojoClass(Class<?> cls){
		this.pojoClass = cls;
	}
	
	/**
	 * ����dao����
	 * @param dao
	 * @throws Exception 
	 */
	public void setDao(BaseDao dao){
		this.dao = dao;
	}
	
	/**
	 * ��ȡdao����
	 * @param dao
	 * @throws Exception 
	 */
	public BaseDao getDao(){
		return this.dao;
	}
	
	/**
	 * ���ڳ־û����Ӧ�����ݱ���ѯ���ݵ��ܼ�¼��
	 * @return
	 * @throws Exception
	 */
	@AConnection
	protected long count()throws Exception{
		CarpBean bean = BeansFactory.getBean(this.pojoClass);
		return count("select count(*) from "+bean.getTable());
	}
	
	/**
	 * ��ѯĳ�����ƶ��ֶ�ֵ��һ����¼
	 * @param tableName
	 * @param fieldName
	 * @param value
	 * @return
	 * @throws Exception
	 */
	@AConnection
	public Object findUqByField(String tableName,String fieldName,Object value)throws Exception{
		String sql = "select * from " + tableName + " where "+fieldName+" = ";
		if(value instanceof Integer || value instanceof Long){
			sql += value.toString();
		}else if(value instanceof String){
			sql += "'"+value.toString()+"'";
		}
		List list = search(sql);
		Object obj = null;
		if(list != null && list.size() > 0) obj = list.get(0);
		return obj;
	}
	
	/**
	 * ����sql��ѯ���ݵļ�¼��
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	@AConnection
	public long count(String sql) throws Exception{
		long count = 0;
		DataSet ds = dao.dataSet(sql);
		count = new Long(ds.getRowData(0).get(0)+"");
		return count;
	}
	/**
	 * ����sql��ѯ���ݵļ�¼��
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	@AConnection
	public long count(Sql sql) throws Exception{
		return dao.count(sql);
	}
	
	/**
	 * ���ڳ־û����Ӧ�����ݱ���ѯ����
	 * @return
	 * @throws Exception
	 */
	@AConnection
	public List<?> search() throws Exception{
		return dao.search(this.pojoClass);
	}
	
	/**
	 * ���ڳ־û����Ӧ�����ݱ�����sql����ѯ����
	 * @param sql
	 * @return
	 * @throws Exception 
	 */
	@AConnection
	public List<Map<String,Object>> searchMap(String sql) throws Exception {
		DataSet ds = dao.dataSet(sql);
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		List<String> title = ds.getTitle();
		while(ds.next()){
			Map<String,Object> map = new HashMap<String,Object>();
			for(String t:title){
				map.put(t, ds.getData(t));
			}
			list.add(map);
		}
		return list;
	}
	
	/**
	 * ���ڳ־û����Ӧ�����ݱ�����sql����ѯ����
	 * @param sql
	 * @return
	 * @throws Exception 
	 */
	@AConnection
	public List<Map<String,Object>> searchMap(Sql sql) throws Exception {
		DataSet ds = dao.dataSet(sql);
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		List<String> title = ds.getTitle();
		while(ds.next()){
			Map<String,Object> map = new HashMap<String,Object>();
			for(String t:title){
				map.put(t, ds.getData(t));
			}
			list.add(map);
		}
		return list;
	}
	
	/**
	 * ���ڳ־û����Ӧ�����ݱ�����sql����ѯ����
	 * @param sql
	 * @return
	 * @throws Exception 
	 */
	@AConnection
	public List<?> search(String sql) throws Exception {
		return dao.search(this.pojoClass,sql);
	}
	
	/**
	 * ���ڳ־û����Ӧ�����ݱ�����sql����ѯ����
	 * @param sql
	 * @return
	 * @throws Exception 
	 */
	@AConnection
	public List<?> search(Sql sql) throws Exception {
		return dao.search(this.pojoClass,sql);
	}
	
	/**
	 * ����sql����ѯ����,����cls��Ķ��󼯺�
	 * @param sql
	 * @return
	 * @throws Exception 
	 */
	@AConnection
	public List<?> search(Class<?> cls, String sql) throws Exception {
		return dao.search(cls,sql);
	}
	
	/**
	 * ����sql����ѯ����,����cls��Ķ��󼯺�
	 * @param sql
	 * @return
	 * @throws Exception 
	 */
	@AConnection
	public List<?> search(Class<?> cls, Sql sql) throws Exception {
		return dao.search(cls,sql);
	}
	
	/**
	 * ����sql��ѯ����
	 * @param sql
	 * @return
	 * @throws Exception 
	 */
	@AConnection
	public DataSet searchDataSet(String sql) throws Exception{
		return dao.dataSet(sql);
	}
	
	/**
	 * ����sql��ѯ����
	 * @param sql
	 * @return
	 * @throws Exception 
	 */
	@AConnection
	public DataSet searchDataSet(Sql sql) throws Exception{
		return dao.dataSet(sql);
	}
	
	/**
	 * ���ڳ־û����Ӧ�����ݱ���ѯ����,���з�ҳ����
	 * @param page
	 * @param size
	 * @return
	 * @throws Exception 
	 */
	@AConnection
	public PageFormData searchPageMap(String sql,int page,int size) throws Exception{
		String low = sql.toLowerCase();
		String sqlCount = "select count(*) ";
		if(low.indexOf("order by")!= -1)
			sqlCount += sql.substring(low.indexOf("from"), low.indexOf("order by")); 
		else
			sqlCount += sql.substring(low.indexOf("from"));
		long count = this.count(sqlCount);
		if((page-1)*size >=count)
			page = 1;
		DataSet ds = dao.dataSetPage(sql, page, size);
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		List<String> title = ds.getTitle();
		while(ds.next()){
			Map<String,Object> map = new HashMap<String,Object>();
			for(String t:title){
				map.put(t, ds.getData(t));
			}
			list.add(map);
		}
		return new PageFormData(list,count,page,size);
	}
	
	/**
	 * ���ڳ־û����Ӧ�����ݱ���ѯ����,���з�ҳ����
	 * @param page
	 * @param size
	 * @return
	 * @throws Exception 
	 */
	@AConnection
	public PageFormData searchPageMap(Sql _sql,int page,int size) throws Exception{
		Sql cSql = _sql.clone();
		String select = "select count(*) "+cSql.getSqlBlock().substring(cSql.getSqlBlock().indexOf(" from "));
		cSql.setSql(select);
		long count = this.count(cSql);
		if((page-1)*size >=count)
			page = 1;
		DataSet ds = dao.dataSetPage(_sql, page, size);
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		List<String> title = ds.getTitle();
		while(ds.next()){
			Map<String,Object> map = new HashMap<String,Object>();
			for(String t:title){
				map.put(t, ds.getData(t));
			}
			list.add(map);
		}
		return new PageFormData(list,count,page,size);
	}
	
	/**
	 * ���ڳ־û����Ӧ�����ݱ���ѯ����,���з�ҳ����
	 * @param page
	 * @param size
	 * @return
	 * @throws Exception 
	 */
	@AConnection
	public PageFormData searchPage(int page,int size) throws Exception{
		long count = this.count();
		if((page-1)*size >=count)
			page = 1;
		List<?> list = dao.searchPage(this.pojoClass, page, size);
		return new PageFormData(list,count,page,size);
	}
	
	/**
	 * ���ڳ־û����Ӧ�����ݱ�����sql��ѯ���ݣ����з�ҳ����
	 * @param sql
	 * @param page
	 * @param size
	 * @return
	 * @throws Exception 
	 */
	@AConnection
	public PageFormData searchPage(String sql,int page,int size) throws Exception{
		String low = sql.toLowerCase();
		String sqlCount = "select count(*) ";
		if(low.indexOf("order by")!= -1)
			sqlCount += sql.substring(low.indexOf("from"), low.indexOf("order by")); 
		else
			sqlCount += sql.substring(low.indexOf("from"));
		long count = this.count(sqlCount);
		if((page-1)*size >=count)
			page = 1;
		List<?> list = dao.searchPage(this.pojoClass,sql, page, size);
		return new PageFormData(list,count,page,size);
	}
	
	/**
	 * ���ڳ־û����Ӧ�����ݱ�����sql��ѯ���ݣ����з�ҳ����
	 * @param sql
	 * @param page
	 * @param size
	 * @return
	 * @throws Exception 
	 */
	@AConnection
	public PageFormData searchPage(Sql _sql,int page,int size) throws Exception{
		Sql cSql = _sql.clone();
		String select = "select count(*) "+cSql.getSqlBlock().substring(cSql.getSqlBlock().indexOf(" from "));
		cSql.setSql(select);
		long count = this.count(cSql);
		if((page-1)*size >=count)
			page = 1;
		List<?> list = dao.searchPage(this.pojoClass,_sql, page, size);
		return new PageFormData(list,count,page,size);
	}
	
	/**
	 * ���ڳ־û����Ӧ�����ݱ�����sql��ѯ���ݣ����з�ҳ����
	 * @param cls �־û���
	 * @param sql ��ѯ���
	 * @param page ҳ��
	 * @param size ÿҳ��¼��
	 * @return
	 * @throws Exception 
	 */
	@AConnection
	public PageFormData searchPage(Class<?> cls, String sql,int page,int size) throws Exception{
		String low = sql.toLowerCase();
		String sqlCount = "select count(*) ";
		if(low.indexOf("order by")!= -1)
			sqlCount += sql.substring(low.indexOf("from"), low.indexOf("order by")); 
		else
			sqlCount += sql.substring(low.indexOf("from"));
		long count = this.count(sqlCount);
		if((page-1)*size >=count)
			page = 1;
		List<?> list = dao.searchPage(cls,sql, page, size);
		return new PageFormData(list,count,page,size);
	}
	
	/**
	 * ���ڳ־û����Ӧ�����ݱ�����sql��ѯ���ݣ����з�ҳ����
	 * @param cls �־û���
	 * @param sql ��ѯ���
	 * @param page ҳ��
	 * @param size ÿҳ��¼��
	 * @return
	 * @throws Exception 
	 */
	@AConnection
	public PageFormData searchPage(Class<?> cls, Sql _sql,int page,int size) throws Exception{
		Sql cSql = _sql.clone();
		String select = "select count(*) "+cSql.getSqlBlock().substring(cSql.getSqlBlock().indexOf(" from "));
		cSql.setSql(select);
		long count = this.count(cSql);
		if((page-1)*size >=count)
			page = 1;
		List<?> list = dao.searchPage(cls,_sql, page, size);
		return new PageFormData(list,count,page,size);
	}
	
	@AConnection
	public PageFormData searchPageUnion(String _sql,int page,int size) throws Exception{
		String cSql = _sql.toLowerCase();
		String select = "select count(*) from ( "+cSql+" ) a";
		long count = this.count(select);
		if((page-1)*size >=count)
			page = 1;
		List<?> list = dao.searchPage(this.pojoClass,_sql, page, size);
		return new PageFormData(list,count,page,size);
	}
	
	/**
	 * ����sql��ѯ���ݣ����з�ҳ����
	 * @param sql
	 * @param page
	 * @param size
	 * @return
	 * @throws Exception 
	 */
	@AConnection
	public PageFormData searchDataSetPage(String sql,int page,int size) throws Exception{
		String low = sql.toLowerCase();
		String sqlCount = "select count(*) " + sql.substring(low.indexOf("from"),low.indexOf("order by")); 
		long count = this.count(sqlCount);
		if((page-1)*size >=count)
			page = 1;
		DataSet ds = dao.dataSetPage(sql, page, size);
		return new PageFormData(ds,count,page,size);
	}
	
	/**
	 * ����sql��ѯ���ݣ����з�ҳ����
	 * @param sql
	 * @param page
	 * @param size
	 * @return
	 * @throws Exception 
	 */
	@AConnection
	public PageFormData searchDataSetPage(Sql _sql,int page,int size) throws Exception{
		Sql cSql = _sql.clone();
		String select = "select count(*) "+cSql.getSqlBlock().substring(cSql.getSqlBlock().indexOf(" from "));
		cSql.setSql(select);
		long count = this.count(cSql);
		if((page-1)*size >=count)
			page = 1;
		DataSet ds = dao.dataSetPage(_sql, page, size);
		return new PageFormData(ds,count,page,size);
	}
	
	/**
	 * ����־û��������ݿ�
	 * @param obj
	 * @return
	 */
	@ATransaction
	public Serializable save(Object obj)throws Exception{
		if(obj == null)
			return null;
		Serializable[] ids = this.save(new Object[]{obj});
		if(ids != null)
			return ids[0];
		return null;
	}
	
	/**
	 * ����־û��������ݿ⼰������
	 * @param obj
	 * @return
	 */
	@ATransaction
	public Serializable saveCache(Object obj)throws Exception{
		if(obj == null)
			return null;
		Serializable[] ids = this.saveCache(new Object[]{obj});
		if(ids != null)
			return ids[0];
		return null;
	}

	/**
	 * ����־û��������ݿ�
	 * @param objs
	 * @return
	 */
	@ATransaction
	public Serializable[] save(Object[] objs)throws Exception{
		if(objs == null || objs.length == 0)
			return null;
		Serializable[] ids = null;
		if(this.action == null) action = Constants.LOG_WRITE;
		table = BeansFactory.getBean(objs[0].getClass()).getTable();
		for(int i=0,count =objs.length; i < count; ++i)
			processObject(objs[i]);
		ids = dao.add(objs);
		result += LogsUtil.getBatchSaveString(objs);
		return ids;
	}
	
	/**
	 * ����־û��������ݿ⼰������
	 * @param objs
	 * @return
	 */
	@ATransaction
	public Serializable[] saveCache(Object[] objs)throws Exception{
		if(objs == null || objs.length == 0)
			return null;
		Serializable[] ids = null;
		if(this.action == null) action = Constants.LOG_WRITE;
		table = BeansFactory.getBean(objs[0].getClass()).getTable();
		for(int i=0,count =objs.length; i < count; ++i)
			processObject(objs[i]);
		ids = dao.addCache(objs);
		result += LogsUtil.getBatchSaveString(objs);
		return ids;
	}
	
	/**
	 * ɾ����¼����������ֵ
	 * @param id ����ֵ
	 * @return
	 */
	@ATransaction
	public boolean deleteById(Serializable id)throws Exception{
		return this.deleteById(new Serializable[]{id});
	}
	/**
	 * ��������ֵ�����ݿ⼰������ɾ��һ����¼
	 * @param id ����ֵ
	 * @return
	 */
	@ATransaction
	public boolean deleteCacheById(Serializable id)throws Exception{
		return this.deleteCacheById(new Serializable[]{id});
	}
	
	/**
	 * ɾ�����󣬸��ݶ��������ֵ
	 * @param obj �Ѿ����־û��Ķ���
	 * @return
	 */
	@ATransaction
	public boolean delete(Object obj)throws Exception{
		if(obj == null)
			return false;
		if(this.action == null) action = Constants.LOG_DELETE;
		CarpBean bean = BeansFactory.getBean(obj.getClass());
		table = bean.getTable();
		Object o = dao.find(obj.getClass(), bean.getPrimaryValue(obj));
		dao.delete(obj);
		result += LogsUtil.getDeleteString(o);
		return true;
	}
	/**
	 * �����ݿ⼰������ɾ��һ������
	 * @param obj �Ѿ����־û��Ķ���
	 * @return
	 */
	@ATransaction
	public boolean deleteCache(Object obj)throws Exception{
		if(obj == null)
			return false;
		if(this.action == null) action = Constants.LOG_DELETE;
		CarpBean bean = BeansFactory.getBean(obj.getClass());
		table = bean.getTable();
		Object o = dao.find(obj.getClass(), bean.getPrimaryValue(obj));
		dao.deleteCache(obj);
		result += LogsUtil.getDeleteString(o);
		return true;
	}
	
	/**
	 * ɾ�����󣬸��ݶ��������ֵ
	 * @param obj �Ѿ����־û��Ķ���
	 * @return
	 */
	@ATransaction
	public boolean delete(Object[] objs)throws Exception{
		if(objs == null || objs.length==0)
			return false;
		Class<?> cls = this.pojoClass;
		try{
			if(this.action == null) action = Constants.LOG_DELETE;
			CarpBean bean = BeansFactory.getBean(objs[0].getClass());
			table = bean.getTable();
			Serializable[] ids = new java.io.SerializablePermission[objs.length];
			for(int i = 0, count = objs.length; i < count; ++i)
				ids[i] = bean.getPrimaryValue(objs[i]);
			this.pojoClass =  objs[0].getClass();
			return this.deleteById(ids);
		}finally{
			this.pojoClass = cls;
		}
	}
	/**
	 * �����ݿ⼰������ɾ��һ�����
	 * @param obj �Ѿ����־û��Ķ���
	 * @return
	 */
	@ATransaction
	public boolean deleteCache(Object[] objs)throws Exception{
		if(objs == null || objs.length==0)
			return false;
		Class<?> cls = this.pojoClass;
		try{
			if(this.action == null) action = Constants.LOG_DELETE;
			CarpBean bean = BeansFactory.getBean(objs[0].getClass());
			table = bean.getTable();
			Serializable[] ids = new java.io.SerializablePermission[objs.length];
			for(int i = 0, count = objs.length; i < count; ++i)
				ids[i] = bean.getPrimaryValue(objs[i]);
			this.pojoClass =  objs[0].getClass();
			return this.deleteCacheById(ids);
		}finally{
			this.pojoClass = cls;
		}
	}
	
	/**
	 * ɾ����¼����������ֵ
	 * @param ids ����ֵ(����)
	 * @return
	 */
	@ATransaction
	public boolean deleteById(Serializable[] ids)throws Exception{
		if(ids == null || ids.length == 0)
			return true;
		if(this.action == null) action = Constants.LOG_DELETE;
		CarpBean bean = BeansFactory.getBean(this.pojoClass);
		table = bean.getTable();
		StringBuilder sql = new StringBuilder("select * from "+table+" where ");
		String pk = bean.getPrimarys().get(0).getColName();
		for(int i=0; i<ids.length; ++i){
			if(i!=0)
				sql.append(" or ");
			sql.append(pk);
			sql.append(" = ");
			if (bean.getPrimarys().get(0).getFieldType().equals(String.class))
				sql.append("'"+ids[i]+"'");
			else
				sql.append(ids[i]);
		}
		Object[] obj = dao.search(this.pojoClass, sql.toString()).toArray();
		dao.delete(this.pojoClass, ids);
		result += LogsUtil.getBatchDeleteString(obj);
		return true;
	}
	/**
	 * ��������ֵ���ϴ����ݿ⼰������ɾ��һ���¼
	 * @param ids ����ֵ����(����)
	 * @return
	 */
	@ATransaction
	public boolean deleteCacheById(Serializable[] ids)throws Exception{
		if(ids == null || ids.length == 0)
			return true;
		if(this.action == null) action = Constants.LOG_DELETE;
		CarpBean bean = BeansFactory.getBean(this.pojoClass);
		table = bean.getTable();
		StringBuilder sql = new StringBuilder("select * from "+table+" where ");
		String pk = bean.getPrimarys().get(0).getColName();
		for(int i=0; i<ids.length; ++i){
			if(i!=0)
				sql.append(" or ");
			sql.append(pk);
			sql.append(" = ");
			if (bean.getPrimarys().get(0).getFieldType().equals(String.class))
				sql.append("'"+ids[i]+"'");
			else
				sql.append(ids[i]);
		}
		Object[] obj = dao.search(this.pojoClass, sql.toString()).toArray();
		dao.deleteCache(this.pojoClass, ids);
		result += LogsUtil.getBatchDeleteString(obj);
		return true;
	}
	
	/**
	 * ��������������pojoClass���Ӧ�Ķ���
	 * @param id
	 * @return ����
	 */
	@AConnection
	public Object findByIdFromCache(Serializable id)throws Exception{
		if(id == null){
			logger.info(this.pojoClass + " id's value was null,but id's value could not null!");
			return null;
		}
		return dao.findCache(this.pojoClass, id);
	}
	
	/**
	 * ��������������pojoClass���Ӧ�Ķ���
	 * @param id
	 * @return ����
	 */
	@AConnection
	public Object findById(Serializable id)throws Exception{
		if(id == null){
			logger.info(this.pojoClass + " id's value was null,but id's value could not null!");
			return null;
		}
		return dao.find(this.pojoClass, id);
	}
	
	/**
	 * ���ݶ��󣨶�����Ϣ��������һ����Ҫ�ö�����Ҫ��������ֵ�������Ҹö����Ӧ�����ݿ��¼����
	 * @param id
	 * @return
	 */
	@AConnection
	public Object findByObj(Object obj)throws Exception{
		if(obj == null){
			logger.info(" Object was null,but object could not null!");
			return null;
		}
		CarpBean bean = BeansFactory.getBean(obj.getClass());
		return dao.find(obj.getClass(), bean.getPrimaryValue(obj));
	}
	/**
	 * ���ݶ��󣨶�����Ϣ��������һ����Ҫ�ö�����Ҫ��������ֵ�������Ҹö����Ӧ�����ݿ��¼����
	 * @param id
	 * @return
	 */
	@AConnection
	public Object findByObjFromCache(Object obj)throws Exception{
		if(obj == null){
			logger.info(" Object was null,but object could not null!");
			return null;
		}
		CarpBean bean = BeansFactory.getBean(obj.getClass());
		return dao.findCache(obj.getClass(), bean.getPrimaryValue(obj));
	}
	
	/**
	 * ������cls���ֵ�����������
	 * @param cls ��
	 * @return cls����
	 * @throws Exception
	 */
	@AConnection
	public Object initDicData(Object obj)throws Exception{
		if(obj == null)
			return null;
		CarpBean bean = BeansFactory.getBean(obj.getClass());
		List<DICMetadata> dics = bean.getDics();
		if(dics != null && dics.size()>0)
			for(DICMetadata dic : dics){
				dic.setValue(obj, this.search(dic.getDicClass(), dic.getSql()));
			}
		return obj;
	}
	
	/**
	 * ���¶����¼
	 * @param objs
	 * @return
	 * @throws Exception 
	 */
	@ATransaction
	public boolean update(Object[] objs)throws Exception{
		if(objs==null || objs.length==0)
			return true;
		if(this.action == null) action = Constants.LOG_UPDATE;
		CarpBean bean = BeansFactory.getBean(objs[0].getClass());
		String sql = getSelectSqlByUpdateObject(bean, objs);
		Object[] obj = dao.search(objs[0].getClass(), sql).toArray();
		Map<String,Object> map = new HashMap<String,Object>();
		updateMap(bean,map,obj);
		this.processUpdateObject(objs);
		dao.update(objs);
		recordLog(bean,map,objs);//result += LogsUtil.getUpdateString(obj, objs);
		return true;
	}
	
	/**
	 * �������ݿ⼰�����е�һ������¼
	 * @param objs
	 * @return
	 * @throws Exception 
	 */
	@ATransaction
	public boolean updateCache(Object[] objs)throws Exception{
		if(objs==null || objs.length==0)
			return true;
		if(this.action == null) action = Constants.LOG_UPDATE;
		CarpBean bean = BeansFactory.getBean(objs[0].getClass());
		String sql = getSelectSqlByUpdateObject(bean, objs);
		Object[] obj = dao.search(objs[0].getClass(), sql).toArray();
		Map<String,Object> map = new HashMap<String,Object>();
		updateMap(bean,map,obj);
		this.processUpdateObject(objs);
		dao.updateCache(objs);
		recordLog(bean,map,objs);//result += LogsUtil.getUpdateString(obj, objs);
		return true;
	}
	
	/**
	 * ����ǰ�����º����ݣ��ݴ���result�У���������־ʹ�á�
	 * @param bean
	 * @param map
	 * @param objs
	 */
	private void recordLog(CarpBean bean,Map<String,Object> map,Object[] objs){
		for(int i=0; i<objs.length; ++i){
			Object beforeObj = map.get(bean.getPrimaryValue(objs[i])+"");
			result += LogsUtil.getUpdateString(beforeObj, objs[i]);
		}
	}
	
	/**
	 * ��ԭʼ��¼���ݷ���Map��
	 * @param bean
	 * @param map
	 * @param obj
	 */
	private void updateMap(CarpBean bean,Map<String,Object> map,Object[] obj){
		for(int i=0; i<obj.length; ++i){
			map.put(bean.getPrimaryValue(obj[i])+"", obj[i]);
		}
	}
	
	//���ݸ��¶���������Щ�����select sql��䣬���ڲ�ѯԭʼ���ݡ�
	private String getSelectSqlByUpdateObject(CarpBean bean, Object[] objs) throws CarpException{
		table = bean.getTable();
		StringBuilder sql = new StringBuilder("select * from  "+table+"  where  ");
		String pk = bean.getPrimarys().get(0).getColName();
		for(int i=0; i<objs.length; ++i){
			if(i!=0)
				sql.append(" or ");
			sql.append(pk);
			sql.append(" = ");
			if (bean.getPrimarys().get(0).getFieldType().equals(String.class))
				sql.append("'"+bean.getPrimaryValue(objs[i])+"'");
			else
				sql.append(bean.getPrimaryValue(objs[i]));
		}
		return sql.toString();
	}
	/**
	 * ���¶����¼
	 * @param obj
	 * @return
	 * @throws Exception 
	 */
	@ATransaction
	public boolean update(Object obj) throws Exception{
		return this.update(new Object[]{obj});
	}
	/**
	 * �������ݿ⼰�����е�һ�������¼
	 * @param obj
	 * @return
	 * @throws Exception 
	 */
	@ATransaction
	public boolean updateCache(Object obj) throws Exception{
		return this.updateCache(new Object[]{obj});
	}
	
	/**
	 * ����sql����ѯsql����ȷ����ѯ�ļ�¼�Ƿ���ڡ�
	 * @param sql һ��sql���д��Ϊ��select * from table.ע��:����ʹ��count���������򷵻�ֵһ��Ϊtrue.
	 * @return
	 * @throws Exception 
	 */
	@AConnection
	public boolean exist(String sql) throws Exception{
		boolean bool = false;
		DataSet ds = dao.dataSet(sql);
		if(ds.count()!=0)
			bool = true;
		return bool;
	}
	
	/**
	 * ����sql����ѯsql����ȷ����ѯ�ļ�¼�Ƿ���ڡ�
	 * @param sql һ��sql���д��Ϊ��select * from table.ע��:����ʹ��count���������򷵻�ֵһ��Ϊtrue.
	 * @return
	 * @throws Exception 
	 */
	@AConnection
	public boolean exist(Sql sql) throws Exception{
		boolean bool = false;
		DataSet ds = dao.dataSet(sql);
		if(ds.count()!=0)
			bool = true;
		return bool;
	}
	
	/**
	 * ����sql����ѯsql�������ص�һ����¼�ĵ�һ���ֶ�ֵ��
	 * @param sql
	 * @return
	 * @throws Exception 
	 */
	@AConnection
	public Object getColumnData(String sql)throws Exception{
		Object o = null;
		DataSet ds = dao.dataSet(sql);
		if(ds.count()!=0)
			o = ds.getRowData(0).get(0);
		return o;
	}
	/**
	 * ����sql����ѯsql�������ص�һ����¼�ĵ�һ���ֶ�ֵ��
	 * @param sql
	 * @return
	 * @throws Exception 
	 */
	@AConnection
	public Object getColumnData(Sql sql)throws Exception{
		Object o = null;
		DataSet ds = dao.dataSet(sql);
		if(ds.count()!=0)
			o = ds.getRowData(0).get(0);
		return o;
	}
	
	/**
	 * ִ��insert��update��delete������Sql���
	 * @param sql
	 * @return
	 * @throws Exception 
	 */
	@ATransaction
	public boolean executeSql(String sql)throws Exception{
		String _sql = sql.toLowerCase().trim();
		if(_sql.startsWith("delete"))
			action = Constants.LOG_DELETE;
		else if(_sql.startsWith("update"))
			action = Constants.LOG_UPDATE;
		else
			action = Constants.LOG_WRITE;
		sql = sql.trim()
//				.toLowerCase()
				;
		if(sql.startsWith("delete")){
			table = sql.substring(sql.indexOf("from")+5).trim().split(" ")[0];
		}else{
			table = sql.substring(7).trim().split(" ")[0];
		}
		dao.executeUpdate(sql);
		return true;
	}
	
	/**
	 * ִ��insert��update��delete������Sql���
	 * @param sql
	 * @return
	 * @throws Exception 
	 */
	@ATransaction
	public boolean executeSql(Sql sql)throws Exception{
		String _sql = sql.getSql();
		if(_sql.startsWith("delete"))
			action = Constants.LOG_DELETE;
		else if(_sql.startsWith("update"))
			action = Constants.LOG_UPDATE;
		else
			action = Constants.LOG_WRITE;
		_sql = _sql.trim().toLowerCase();
		if(_sql.startsWith("delete")){
			table = _sql.substring(_sql.indexOf("from")+5).trim().split(" ")[0];
		}else{
			table = _sql.substring(7).trim().split(" ")[0];
		}
		dao.executeUpdate(sql);
		return true;
	}
	
	/**
	 * ������Ҫ����Ķ������Ĭ������ֵ
	 * @param obj
	 */
	private void processObject(Object obj){
		ObjectUtil.setValue(obj, "setStsTime", new Class[]{Date.class}, new Object[]{new Date()});
		try {
			Object o = ObjectUtil.getFieldValue(obj, "sts");
			if(o==null||"".equals(o)){
				ObjectUtil.setValue(obj, "setSts", new Class[]{String.class}, new Object[]{"ACTV"});
			}
		} catch (Exception e) {
			//e.printStackTrace();
		}
		LoginUser user = (LoginUser)LoginUser.userThread.get();
		if(user != null ){
			ObjectUtil.setValue(obj, "setOperator", new Class[]{String.class}, new Object[]{user.getUserId()});
		}
	}
	
	/**
	 * ������Ҫ���µĶ�������޸�ʱ�䡢�����˵����ݵĸ�ֵ��
	 * @param objs
	 */
	private void processUpdateObject(Object[] objs){
		LoginUser user = (LoginUser)LoginUser.userThread.get();
		for(int i=0; i<objs.length; ++i){
			ObjectUtil.setValue(objs[i], "setStsTime", new Class[]{Date.class}, new Object[]{new Date()});
			if(user != null ){
				ObjectUtil.setValue(objs[i], "setOperator", new Class[]{String.class}, new Object[]{user.getUserId()});
			}
		}
	}
	
	/**
	 * ������󵽻�����
	 * @param key ��ֵ
	 * @param o   ���������
	 */
	public void saveToCache(String key,Object o){
		CachedClient.getClient().set(key, o);
	}
	/**
	 * ������󵽻�����
	 * @param key ��ֵ
	 * @param o   ���������
	 */
	public void saveToCache(String key,Map<?,?> m){
		CachedClient.getClient().set(key, m);
	}
	
	/**
	 * �ӻ�����ȡ�ñ���Ķ���
	 * @param key ��ֵ
	 * @return
	 */
	public Object getObjectFromCache(String key){
		return CachedClient.getClient().get(key);
	}
	
	/**
	 * ���»����еĶ���
	 * @param key ��ֵ
	 * @param o  �����¶��� 
	 */
	public void updateToCache(String key,Object o){
		CachedClient.getClient().replace(key, o);
	}
	
	/**
	 * ���»����еĶ���
	 * @param key ��ֵ
	 * @param o  �����¶���
	 */
	public void updateToCache(String key,Map<?,?> m){
		CachedClient.getClient().replace(key, m);
	}
	
	/**
	 * ɾ�������еĶ���
	 * @param key
	 */
	public void deleteFromCache(String key){
		CachedClient.getClient().delete(key);
	}
	
	public String getResult() {
		return result;
	}
	
	public String getFileName(String path,Integer id){
		String fileName = "";
		String dateStr = new SimpleDateFormat("yyMMdd").format(new Date());
		int result = 1000 + new Double(Math.random() * 8999).intValue();
		fileName = dateStr+"."+id+"."+result;
		if(new File(path+fileName).exists()){
			fileName = getFileName(path,id);
		}
		return fileName;
	}
}
