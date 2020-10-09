package tpsql.dao;

import java.util.HashMap;
import java.util.Map;

import tpsql.entity.IEntityMap;
import tpsql.sql.SqlDriverException;
import tpsql.sql.SqlExecuteException;
import tpsql.collection.DataRow;
import tpsql.collection.DataTable;
import tpsql.entity.IEntityMap;
import tpsql.reader.Reader;
import tpsql.spring.SpringContext;
import tpsql.sql.SqlDriverException;
import tpsql.sql.SqlExecuteException;
import tpsql.sql.meta.Table;
import tpsql.sql.util.ISqlHelper;
import tpsql.sql.util.SqlHelper;
import tpsql.util.StringUtil;
import tpsql.util.TypeUtil;

/**
 * 产生主键Id的类,支持多数据库版本
 */
public class DefaultIdGenerator implements IdGenerator {
	private Map<String,IdStore> idStores;
	private Object lockObj = new Object();
	private static final String EntityMapKey = "EntityMap";

	protected IEntityMap getEntityMap()
	{
		return (IEntityMap)SpringContext.instance().get(EntityMapKey);
	}

	public DefaultIdGenerator()
	{
		idStores = new HashMap<String,IdStore>();
	}

	public Long getId(Class<?> entityType){
		String tableName = getEntityMap().getTable(entityType);
		return this.getId(tableName);
	}

	public Long getId(String tableName){
		return this.getId(tableName, "");
	}

	@Override
	public Long[] getId(String tableName, int num, String dbConfig) {
		Long[] ids = this.getId(tableName, num,"",dbConfig);
		return ids;
	}

	public Long[] getId(Class<?> entityType,int num){
		String tableName = getEntityMap().getTable(entityType);
		return this.getId(tableName,num);
	}

	public Long[] getId(String tableName,int num){
		return this.getId(tableName,num,"");
	}

	public Long getId(Class<?> entityType,String dbConfig){
		String tableName = getEntityMap().getTable(entityType);
		return this.getId(tableName,dbConfig);
	}

	public Long getId(String tableName,String dbConfig){
		Long[] ids = this.getId(tableName, 1, dbConfig);
		return ids[0];
	}

	public Long[] getId(Class<?> entityType,int num,String dbConfig){
		String tableName = getEntityMap().getTable(entityType);
		if(StringUtil.isEmpty(tableName))
			throw new EntityMappingException(StringUtil.format("找不实体类{0}的SqlMap映射文件",entityType.getName()));
		return this.getId(tableName, num,"",dbConfig);
	}

	public Long getPkId(String tableName,String pk){
		Long[] ids = this.getId(tableName,1,pk,"");
		return ids[0];
	}

	public Long[] getPkId(String tableName,String pk,int num){
		Long[] ids = this.getId(tableName,num,pk,"");
		return ids;
	}

	public Long getPkId(String tableName,String pk,String dbConfig){
		Long[] ids = this.getId(tableName,1,pk,dbConfig);
		return ids[0];
	}

	public Long[] getPkId(String tableName,String pk,int num,String dbConfig){
		Long[] ids = this.getId(tableName,num,pk,dbConfig);
		return ids;
	}

	public Long[] getId(String tableName,int num,String pk,String dbConfig){
		synchronized(lockObj) {
			ISqlHelper ish = ("".equals(dbConfig))?SqlHelper.instance():SqlHelper.instance(dbConfig);
			try{
				String dbType = ish.getDriver().getDBType();
				if(StringUtil.isNotEmpty(tableName)){
					if(!idStores.containsKey(dbType)){
						if(ish.getDialect().supportsSequences()){
							IdStore store = new Sequence(dbType);
							idStores.put(dbType, store);
						}else{
							IdStore store = new SqlStore();
							idStores.put(dbType, store);
						}
					}
					IdStore gen = idStores.get(dbType);
					if(!gen.isExist(tableName,ish)){
						long maxId = getMaxPKValue(tableName,pk,ish);
						gen.create(tableName, maxId, ish);
					}
					if(num>1){
						Long[] ids = gen.getId(tableName,num,ish);
						return ids;
					}else{
						return new Long[]{gen.getId(tableName, ish)};
					}
				}
				else throw new EntityMappingException("获取Id出错 表名不能为空");
			}catch(EntityMappingException e){
				throw e;
			}catch(SqlExecuteException e){
				throw e;
			}catch(SqlDriverException e){
				throw e;
			}
			/*
			catch(Exception e){
				throw new EntityMappingException(StringUtil.format("获取Id出错 找不到该表{0}的实体映射文件 error:"+e.getMessage(),tableName),e);
			}
			*/
			finally{
				ish.dispose();
			}
		}
	}

	private Long getMaxPKValue(String tableName,String pk,ISqlHelper ish){
		String pkName = StringUtil.isNotEmpty(pk)?pk:getEntityMap().getIdColumn(tableName);
		if(StringUtil.isNullOrEmpty(tableName))throw new EntityMappingException(StringUtil.format("获取Id出错 找不到该表{0}的实体映射文件",tableName));
		if(StringUtil.isNullOrEmpty(pkName))throw new EntityMappingException(StringUtil.format("获取Id出错 找不到该表{0}的实体映射文件或未定义主键列",tableName));
		int n = tableName.indexOf(".");
		if(n>-1)tableName = tableName.substring(n+1,tableName.length());
		String sqlString = StringUtil.format("SELECT MAX({0}) AS VAL FROM {1}",pkName,tableName);
		try{
			Object value = ish.executeScalar(sqlString);
			long id = (Long)TypeUtil.changeType(value,Long.class);
			return id+1;
		}catch(Exception e){
			return new Long(1);
		}
	}

	interface IdStore{
		Long getId(String name,ISqlHelper ish);

		Long[] getId(String name,int num,ISqlHelper ish);

		void create(String name,long value,ISqlHelper ish);

		boolean isExist(String name,ISqlHelper ish);
	}

	class Sequence implements IdStore{
		private Map<String,Long> ids;
		private String dbType;
		public Sequence(String dbType){
			ids = new HashMap<String,Long>();
			this.dbType =dbType;
		}

		@Override
		public Long getId(String name,ISqlHelper ish){
			String sequenceName=this.getSequenceName(name);
			long id = ish.getDbMeta().getSequenceNextValue(sequenceName);
			this.ids.put(sequenceName, id);
			return id;
		}

		@Override
		public Long[] getId(String name,int num,ISqlHelper ish){
			String sequenceName=this.getSequenceName(name);
			Long[] ids = ish.getDbMeta().getSequenceNextValue(sequenceName,num);
			this.ids.put(sequenceName, ids[ids.length-1]);
			return ids;
		}

		@Override
		public void create(String name,long value,ISqlHelper ish){
			String seq=this.getSequenceName(name);
			ish.getDbMeta().createSequence(seq, value);
		}

		@Override
		public boolean isExist(String name,ISqlHelper ish){
			String seq=this.getSequenceName(name);
			int n = seq.indexOf(".");
			if(n>-1)seq = seq.substring(n+1,seq.length());
			if(this.ids.containsKey(seq))return true;
			else return ish.getDbMeta().getSequence(seq)!=null;
		}

		private String getSequenceName(String name){
			int n = name.indexOf(".");
			String seq = (n>-1)?name.substring(0,n+1)+"SEQ_"+name.substring(n+1,name.length())
					:"SEQ_"+name;
			if(this.dbType.equals("Oracle"))
			{
				if (seq.length()>30){
					seq=seq.substring(0,30);
				}
			}
			return seq;
		}
	}

	/**
	 * 不支持Sequence的数据据,产生Id的类
	 * 支持集群
	 */
	class SqlStore implements IdStore{
		private Map<String,IdRange> ids;
		private final long INCREMENT = 100;
		private boolean tableFlag = false;
		private boolean funFlag = false;

		public SqlStore(){
			this.ids = new HashMap<String,IdRange>();
		}

		@Override
		public Long getId(String name,ISqlHelper ish) {

			if(this.ids.containsKey(name)){
				IdRange range = this.ids.get(name);
				if(range.next())return range.getId();
			}
			long id = this.getTopId(name,ish);
			long topId = id+INCREMENT-1;
			this.ids.put(name,new IdRange(id,topId));
			return id;
		}

		@Override
		public Long[] getId(String name,int num, ISqlHelper ish) {
			Long[] ids = new Long[num];
			for(int i=0;i<num;i++){
				ids[i] = this.getId(name, ish);
			}
			return ids;
		}

		@Override
		public void create(String name, long value, ISqlHelper ish) {
			String sqlString = StringUtil.format("INSERT INTO ID_SEQUENCE (ID_NAME,ID_VALUE) VALUES ('{0}',{1})", name,value);
			ish.executeNoneQuery(sqlString);
		}

		@Override
		public boolean isExist(String name,ISqlHelper ish){
			if(this.ids.containsKey(name))return true;
			else{
				createIdSequenceTable(ish);
				String sqlString = StringUtil.format("SELECT ID_VALUE FROM ID_SEQUENCE WHERE ID_NAME = '{0}'",name);
				Object obj = ish.executeScalar(sqlString);
				if(obj instanceof Integer){
					return ((Integer)obj).intValue()>0;
				}
				return false;
			}
		}

		private synchronized void createIdSequenceTable(ISqlHelper ish){
			if(!tableFlag) {
				tableFlag= true;
				String schema = getSchema(ish);
				boolean needCreateFlag = true;
				String tableSqlString = ish.getDialect().getMetaSql().getTablesSql("ID_SEQUENCE");
				DataTable table = ish.getDataTable(tableSqlString);
				if(table!=null && table.rowSize()>0){
					for(DataRow row : table.getRows()){
						String tableSchema = Reader.stringReader().getValue(row,"TABLE_SCHEMA");
						if(schema.equals(tableSchema)){
							needCreateFlag = false;
						}
					}
				}

				if(needCreateFlag){
					StringBuffer sql = new StringBuffer();
					sql.append("CREATE TABLE ID_SEQUENCE (\r\n");
					sql.append("\tID_NAME VARCHAR(50) NOT NULL COMMENT '表名',\r\n");
					sql.append("\tID_VALUE INT NOT NULL COMMENT '序列值',\r\n");
					sql.append("\tPRIMARY KEY (ID_NAME)\r\n");
					sql.append(")");
					ish.executeNoneQuery(sql.toString());
				}
			}
		}

		private long getTopId(String name,ISqlHelper ish){
			createNextValFunction(ish);
			Object obj = ish.executeScalar(StringUtil.format("select nextval('{0}',{1})",name,INCREMENT));
			long value = (Long)TypeUtil.changeType(obj,Long.class);
			return value;
		}

		private String getSchema(ISqlHelper ish){
			String schema = ish.getDriver().getConfig().getSchema();
			if(StringUtil.isNullOrEmpty(schema)){
				schema = (String)ish.getMetaDataMap().get("schema");
				ish.getDriver().getConfig().setSchema(schema);
			}
			return schema;
		}

		private synchronized void createNextValFunction(ISqlHelper ish){
			if(!this.funFlag) {
				funFlag = true;
				String schema = getSchema(ish);
				boolean needCreateFlag = true;
				String funcSql = ish.getDialect().getMetaSql().getFunctionsSql("nextval");
				DataTable table = ish.getDataTable(funcSql);
				if(table!=null && table.rowSize()>0){
					for(DataRow row : table.getRows()){
						String funcSchema = Reader.stringReader().getValue(row,"ROUTINE_SCHEMA");
						if(schema.equals(funcSchema)){
							needCreateFlag = false;
						}
					}
				}

				if(needCreateFlag) {
					StringBuffer sql = new StringBuffer();
					sql.append("create function nextval(seq_name VARCHAR(64),increment int)\r\n");
					sql.append("\treturns integer(11)\r\n");
					sql.append("begin\r\n");
					sql.append("\tdeclare value integer;\r\n");
					sql.append("\tset value = 0;\r\n");
					sql.append("\tselect ID_VALUE into value  from ID_SEQUENCE where ID_NAME = seq_name for update;\r\n");
					sql.append("\tupdate ID_SEQUENCE set ID_VALUE = ID_VALUE+increment where ID_NAME = seq_name;\r\n");
					sql.append("\treturn value;\r\n");
					sql.append("end");
					ish.executeNoneQuery(sql.toString());
				}
			}
		}

		class IdRange
		{
			private long id;
			private long topId;

			public IdRange(long id,long topId)
			{
				this.id = id;
				this.topId = topId;
			}

			public long getId()
			{
				return id;
			}

			public boolean next()
			{
				this.id++;
				return (this.id+1)<this.topId;
			}
		}
	}

}
