package tpsql.sql.mapping;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import tpsql.core.spring.ISpringContext;
import tpsql.core.spring.SpringContext;
import org.springframework.core.io.Resource;
import tpsql.sql.SqlMapException;
import tpsql.sql.builder.SqlString;
import tpsql.sql.cache.CacheModel;
import tpsql.core.util.StringUtil;
import tpsql.core.xml.XmlAttributeCollection;
import tpsql.core.xml.XmlDocument;
import tpsql.core.xml.XmlNode;
import tpsql.core.xml.XmlNodeType;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;

/**
 * Sql构造配置对像
 */
public class SqlMap implements ISqlMap,InitializingBean {
	/** Sql配置项集合 */
	private Map<String,SqlItem> items;
	/** Sql配置项子句集合 */
	private Map<String,SqlItemClause> clauses;
	/** Sql配置项自定义函数集合 */
	private Map<String,ISqlMethod> methods;
	/** 自定义断言函数 */
	private Map<String,ISqlClauseAssert> asserts;
	/** 结果类型信息集合 */
	private Map<String,SqlResultMap> resultMap;
	/** 缓存集合 */
	private Map<String,CacheModel> cacheMap;
	/** 聚合集合 */
	private Map<String,SqlPoly> polyMap;
	/** 连接集合 */
	private Map<String,SqlLink> linkMap;
	/** 配置资源集合 */
	private Set<SqlContext> contexts;
	/** 是否加载初始化配置对像 */
	private boolean isInit;

	private List<XmlNode> nodes;
	
	private static Log log = LogFactory.getLog(SqlMap.class);
	
	public SqlMap()
	{
		this.items = new HashMap<String,SqlItem>();
		this.clauses = new HashMap<String,SqlItemClause>();
		this.methods = new HashMap<String,ISqlMethod>();
		this.asserts = new HashMap<String,ISqlClauseAssert>();
		this.resultMap = new HashMap<String,SqlResultMap>();
		this.cacheMap = new HashMap<String,CacheModel>();
		this.polyMap = new HashMap<String, SqlPoly>();
		this.linkMap = new HashMap<String, SqlLink>();
		this.nodes = new ArrayList<XmlNode>();
		this.isInit = false;
	}
	
	/** 得到配置资源集合 */
	public Set<SqlContext> getContexts()
	{
		return this.contexts;
	}
	
	/** 设置配置资源集合 */
	public void setContexts(Set<SqlContext> contexts)
	{
		this.contexts = contexts;
	}
	
	/** 得到Sql配置项 */
	public SqlItem get(String sqlId)
	{
		if(this.items.containsKey(sqlId))
		{
			return this.items.get(sqlId);
		}
		else {
			throw new SqlMapException(StringUtil.format("找不到SqlId {0}", sqlId));
		}
	}
	
	/** 得到缓存项 */
	public CacheModel getCache(String cacheId){
		if(this.cacheMap.containsKey(cacheId))
		{
			return this.cacheMap.get(cacheId);
		}
		else {
			throw new SqlMapException(StringUtil.format("找不到cacheId {0}", cacheId));
		}
	}

	/**
	 * 得到聚合
	 * @param polyId
	 * @return
	 */
	public SqlPoly getPoly(String polyId){
		if(this.polyMap.containsKey(polyId))
		{
			return this.polyMap.get(polyId);
		}
		else {
			throw new SqlMapException(StringUtil.format("找不到polyId {0}", polyId));
		}
	}

	/**
	 * 推到连接
	 * @param linkId
	 * @return
	 */
	public SqlLink getLink(String linkId){
		if(this.linkMap.containsKey(linkId))
		{
			return this.linkMap.get(linkId);
		}
		else {
			throw new SqlMapException(StringUtil.format("找不到linkId {0}", linkId));
		}
	}
	
	/**
	 * 得到自定义函数
	 * @param name
	 * @return
	 */
	public ISqlMethod getMethod(String name)
	{
		if(this.methods.containsKey(name))
		{
			return this.methods.get(name);
		}
		return null;
	}
	
	/**
	 * 得到自定义断言
	 * @param name
	 * @return
	 */
	public ISqlClauseAssert getAssert(String name)
	{
		if(this.asserts.containsKey(name))
		{
			return this.asserts.get(name);
		}
		return null;
	}
	
	/** 加载初始化配置对像 */
	public void onit()
	{
		if(!this.isInit)
		{
			this.onitContexts(contexts);
			this.loadResultMap(nodes);
			this.loadSqlItemClause(nodes);
			this.loadSqlCache(nodes);
			this.loadSqlItem(nodes);
			this.loadSqlPoly(nodes);
			this.nodes.clear();
		}
	}
	
	/** 通过配置资源对像，获取配置节点集合 */
	private void onitContexts(Set<SqlContext> contexts)
	{
		for(SqlContext context : contexts)
		{
			ISpringContext springContext = SpringContext.instance();
			this.methods = springContext.getBeans(ISqlMethod.class);
			this.asserts = springContext.getBeans(ISqlClauseAssert.class);
			for(Resource resource : context.getResources())
			{
				InputStream stream = null;
				try {
					stream = resource.getInputStream();
				}catch (IOException e){
					throw new SqlMapException(StringUtil.format("找不到该资源文件 {0}", resource));
				}
				if(stream==null) {
					throw new SqlMapException(StringUtil.format("找不到该资源文件 {0}", resource));
				}
				XmlDocument xdoc = null;
				try{xdoc=new XmlDocument(stream);}
				catch(Exception e){throw new SqlMapException(StringUtil.format("读取资源文件 {0} 出错,格式不正解",resource),e);}
				if(log.isInfoEnabled()){
					log.info(StringUtil.format("load sql file :"+resource));
				}
				for(XmlNode root : xdoc.getChildNodes()){
	        		for(XmlNode node : root.getChildNodes())
	        		{
	        			if(node.getNodeType() == XmlNodeType.Element)
	        			{
	        				nodes.add(node);
	        			}
	        		}
		        }
			}
		}
	}
	
	/** 加载Sql配置项 */
	private void loadSqlItem(List<XmlNode> nodes){
		for(XmlNode node : nodes){
			if(node.getName().equalsIgnoreCase("sql")){
				SqlItem item = this.buildSqlItem(node);
				if(!this.items.containsKey(item.getId())) {
					this.items.put(item.getId(), item);
				} else {
					throw new SqlMapException(StringUtil.format("Sql配置Id[{0}]重复", item.getId()));
				}
			}
		}
	}
	
	private void loadSqlCache(List<XmlNode> nodes){
		for(XmlNode node : nodes){
			if(node.getName().equalsIgnoreCase("cache")){
				CacheModel cache = this.buildCacheModel(node);
				if(!this.cacheMap.containsKey(cache.getId())) {
					this.cacheMap.put(cache.getId(), cache);
				} else {
					throw new SqlMapException(StringUtil.format("Cache配置Id[{0}]重复", cache.getId()));
				}
			}
		}
	}

	private void loadSqlPoly(List<XmlNode> nodes){
		for(XmlNode node : nodes){
			if(node.getName().equalsIgnoreCase("poly")){
				SqlPoly poly = this.buildPoly(node);
				if(!this.polyMap.containsKey(poly.getId())) {
					this.polyMap.put(poly.getId(), poly);
				} else {
					throw new SqlMapException(StringUtil.format("Poly配置Id[{0}]重复", poly.getId()));
				}
			}
		}
	}
	
	/** 加载Sql配置项子句 */
	private void loadSqlItemClause(List<XmlNode> nodes)
	{
		for(XmlNode node : nodes)
		{
			if(node.getName().equalsIgnoreCase("clause"))
			{
				SqlItemClause clause = this.buildSqlItemClause(node);
				clause.setMap(this);
				this.buildChildContent(node, clause);
				if(!this.clauses.containsKey(clause.getId())) {
					this.clauses.put(clause.getId(), clause);
				} else {
					throw new SqlMapException(StringUtil.format("Clause配置Id[{0}]重复", clause.getId()));
				}
			}
		}
	}
	
	/** 构造Sql配置项 */
	private SqlItem buildSqlItem(XmlNode node)
	{
		SqlItem item = new SqlItem();
		if(!node.getAttributes().containsKey("id")) {
			throw new SqlMapException("sql节点 id属性未定义");
		}
		String id = node.getAttributes().get("id").getValue();
		item.setId(id);
		
		XmlAttributeCollection attrs = node.getAttributes();
		if(attrs.containsKey("result")){
			String result = attrs.get("result").getValue();
			if(resultMap.containsKey(result)){
				item.setResult(resultMap.get(result));
			}
		}
		
		if(attrs.containsKey("resultClass")){
			item.setResultClass(attrs.get("resultClass").getValue());
		}

		if(attrs.containsKey("autoIncrement")){
			item.setAutoIncrement("true".equalsIgnoreCase(attrs.get("autoIncrement").getValue()));
		}
		
		if(attrs.containsKey("cache")){
			String cacheId = attrs.get("cache").getValue();
			if(cacheMap.containsKey(cacheId)) {
				item.setCacheId(cacheId);
				cacheMap.get(cacheId).setUsed(true);
			} else {
				throw new SqlMapException(StringUtil.format("不存在缓存Id {0} 项", cacheId));
			}
		}
		
		this.buildChildContent(node, item.getContent());
		
		this.findIncludeJoins(item, item.getContent());
		
		this.buildPoly(node, item);

		this.buildLink(node, item);
		
		if(log.isInfoEnabled()){
			log.info(StringUtil.format("load sqlId:"+item.getId()));
		}
		
		return item;
	}

	/**
	 * 构造子连接
	 * @param node
	 */
	private SqlPoly buildPoly(XmlNode node){
		SqlPoly poly = new SqlPoly();
		XmlAttributeCollection attrs = node.getAttributes();
		if(attrs.containsKey("id")) {
			poly.setId(attrs.get("id").getValue());
		} else {
			throw new SqlMapException("poly节点 id 不能为空");
		}
		if(attrs.containsKey("sqlId")) {
			poly.setSqlId(attrs.get("sqlId").getValue());
		} else {
			throw new SqlMapException("poly节点 sqlId 不能为空");
		}
		if(attrs.containsKey("pkey")) {
			poly.setPkey(attrs.get("pkey").getValue());
		} else {
			throw new SqlMapException("poly节点 pkey 不能为空");
		}
		if(attrs.containsKey("fkey")) {
			poly.setFkey(attrs.get("fkey").getValue());
		} else {
			throw new SqlMapException("poly节点 fkey 不能为空");
		}
		if(attrs.containsKey("split")) {
			poly.setSplit(attrs.get("split").getValue());
		}
		this.buildPolyColumn(node, poly);
		return poly;
	}
	
	/**
	 * 构造子连接
	 * @param node
	 * @param item
	 */
	private void buildPoly(XmlNode node,SqlItem item){
		for(XmlNode n : node.getChildNodes()){
			if(n.getNodeType()==XmlNodeType.Element){
				if(n.getName().equalsIgnoreCase("POLY")){
					SqlPoly poly = new SqlPoly();
					XmlAttributeCollection attrs = n.getAttributes();
					if(attrs.containsKey("sqlId")) {
						poly.setSqlId(attrs.get("sqlId").getValue());
					} else {
						throw new SqlMapException("poly节点 sqlId 不能为空");
					}
					if(attrs.containsKey("pkey")) {
						poly.setPkey(attrs.get("pkey").getValue());
					} else {
						throw new SqlMapException("poly节点 pkey 不能为空");
					}
					if(attrs.containsKey("fkey")) {
						poly.setFkey(attrs.get("fkey").getValue());
					} else {
						throw new SqlMapException("poly节点 fkey 不能为空");
					}
					if(attrs.containsKey("split")) {
						poly.setSplit(attrs.get("split").getValue());
					}
					this.buildPolyColumn(n, poly);
					item.addSqlJoin(poly);
				}
			}
		}
	}
	
	/**
	 * 查找引用sql里的子连接
	 * @param item
	 */
	private void findIncludeJoins(SqlItem item,SqlItemContent content){
		for(ISqlItemPart part : content.getParts()){
			if(part instanceof SqlItemInclude){
				SqlItemInclude include = (SqlItemInclude)part;
				for(SqlJoin join : include.getJoins()){
					item.addSqlJoin(join);
				}
				if(part instanceof SqlItemContent){
					this.findIncludeJoins(item,(SqlItemContent)part);
				}
			}
		}
	}

	/**
	 * 构造子连接
	 * @param node
	 */
	private SqlLink buildLink(XmlNode node){
		SqlLink link = new SqlLink();
		XmlAttributeCollection attrs = node.getAttributes();
		if(attrs.containsKey("id")) {
			link.setId(attrs.get("id").getValue());
		} else {
			throw new SqlMapException("link节点 id 不能为空");
		}
		if(attrs.containsKey("sqlId")) {
			link.setSqlId(attrs.get("sqlId").getValue());
		} else {
			throw new SqlMapException("link节点 sqlId 不能为空");
		}
		if(attrs.containsKey("pkey")) {
			link.setPkey(attrs.get("pkey").getValue());
		} else {
			throw new SqlMapException("link节点 pkey 不能为空");
		}
		if(attrs.containsKey("fkey")) {
			link.setFkey(attrs.get("fkey").getValue());
		} else {
			throw new SqlMapException("link节点 fkey 不能为空");
		}
		if(attrs.containsKey("column")) {
			link.setColumn(attrs.get("column").getValue());
		} else {
			throw new SqlMapException("link节点 column 不能为空");
		}
		if(attrs.containsKey("split")) {
			link.setSplit(attrs.get("split").getValue());
		}
		return link;
	}

	/**
	 * 构造子连接
	 * @param node
	 * @param item
	 */
	private void buildLink(XmlNode node,SqlItem item){
		for(XmlNode n : node.getChildNodes()){
			if(n.getNodeType()==XmlNodeType.Element){
				if(n.getName().equalsIgnoreCase("LINK")){
					SqlLink link = new SqlLink();
					XmlAttributeCollection attrs = n.getAttributes();
					if(attrs.containsKey("sqlId")) {
						link.setSqlId(attrs.get("sqlId").getValue());
					} else {
						throw new SqlMapException("poly节点 sqlId 不能为空");
					}
					if(attrs.containsKey("pkey")) {
						link.setPkey(attrs.get("pkey").getValue());
					} else {
						throw new SqlMapException("poly节点 pkey 不能为空");
					}
					if(attrs.containsKey("fkey")) {
						link.setFkey(attrs.get("fkey").getValue());
					} else {
						throw new SqlMapException("poly节点 fkey 不能为空");
					}
					if(attrs.containsKey("column")) {
						link.setColumn(attrs.get("column").getValue());
					} else {
						throw new SqlMapException("link节点 column 不能为空");
					}
					item.addSqlJoin(link);
				}
			}
		}
	}
	
	/**
	 * 构造子连接列
	 * @param node
	 * @param poly
	 */
	private void buildPolyColumn(XmlNode node,SqlPoly poly){
		for(XmlNode n : node.getChildNodes()){
			if(n.getNodeType()==XmlNodeType.Element){
				if(n.getName().equalsIgnoreCase("COLUMN")){
					XmlAttributeCollection attrs = n.getAttributes();
					if(!attrs.containsKey("name")) {
						throw new SqlMapException("poly.Column节点 name 不能为空");
					}
					String op = attrs.containsKey("op")?attrs.get("op").getValue():"";
					SqlPoly.PolyColumn column = poly.addColumn(attrs.get("name").getValue(),op);
					if(attrs.containsKey("split")) {
						column.setSplit(attrs.get("split").getValue());
					}
					if(attrs.containsKey("alias")) {
						column.setAlias(attrs.get("alias").getValue());
					}
				}
			}
		}
	}
	
	/**
	 * 构造子结点内容
	 * @param node
	 * @param content
	 */
	private void buildChildContent(XmlNode node,SqlItemContent content){
		for(XmlNode n : node.getChildNodes()){
			if(n.getNodeType()==XmlNodeType.Element){
				if(n.getName().equalsIgnoreCase("clause")){
					SqlItemClause clause = this.buildSqlItemClause(n);
					clause.setMap(this);
					this.buildChildContent(n,clause);
					content.addPart(clause);
				}else if(n.getName().equalsIgnoreCase("for")){
					SqlItemForeach foreach = this.buildSqlItemForeach(n);
					foreach.setMap(this);
					this.buildChildContent(n,foreach);
					content.addPart(foreach);
				}else if(n.getName().equalsIgnoreCase("where")){
					SqlItemWhere where = new SqlItemWhere();
					where.setMap(this);
					this.buildChildContent(n,where);
					content.addPart(where);
				}else if(n.getName().equalsIgnoreCase("values")){
					SqlItemValues values = new SqlItemValues();
					values.setMap(this);
					this.buildChildContent(n,values);
					content.addPart(values);
				}else if(n.getName().equalsIgnoreCase("set")){
					SqlItemSet set = new SqlItemSet();
					set.setMap(this);
					this.buildChildContent(n,set);
					content.addPart(set);
				}else if(n.getName().equalsIgnoreCase("method")){
					SqlItemMethod method = this.buildSqlItemMethod(n);
					method.setMap(this);
					content.addPart(method);
				}else if(n.getName().equalsIgnoreCase("include")){
					SqlItemInclude include = this.buildSqlItemInclude(n);
					include.setMap(this);
					content.addPart(include);
				}else if(n.getName().equalsIgnoreCase("dynamic")){
					SqlItemDynamic dynamic = this.buildSqlItemDynamic(n);
					dynamic.setMap(this);
					this.buildChildContent(n, dynamic);
					content.addPart(dynamic);
				}else if(n.getName().equalsIgnoreCase("dialect")){
					SqlItemDialect dialect = new SqlItemDialect();
					dialect.setMap(this);
					this.buildChildContent(n,dialect);
					content.addPart(dialect);
				}
			}
			else if(n.getNodeType()==XmlNodeType.Text){
				String text = n.getValue();
				if(StringUtil.isNotEmpty(text)) {
					if(content.getItem()!=null && hasOperateSqlWord(text))
						content.getItem().setSelect(false);
					content.addPart(text);
				}
			}else if(n.getNodeType()==XmlNodeType.CDATA){
				String text = n.getValue();
				if(StringUtil.isNotEmpty(text)) {
					content.addPart(text);
				}
			}
		}
	}

	/**
	 * 是否包含 INSERT UPDATE DELETE DROP 等操作类关键字
	 * @return
	 */
	public boolean hasOperateSqlWord(String text){
		String upperCase = text.toUpperCase();
		return upperCase.contains("INSERT ")
				|| upperCase.contains("UPDATE ")
				|| upperCase.contains("DELETE ")
				|| upperCase.contains("DROP ");
	}
	
	/** 加载结果信息配置项 */
	private void loadResultMap(List<XmlNode> nodes)
	{
		for(XmlNode node : nodes)
		{
			if(node.getName().equalsIgnoreCase("Class"))
			{
				SqlResultMap result = this.buildResultMap(node);
				if(!this.resultMap.containsKey(result.getId()))
				{
					this.resultMap.put(result.getId(),result);
				}else{
					throw new SqlMapException(StringUtil.format("Class配置Id[{0}]重复",result.getId()));
				}
			}
		}
	}
	
	private SqlResultMap buildResultMap(XmlNode node)
	{
		SqlResultMap result = new SqlResultMap();
		XmlAttributeCollection attrs = node.getAttributes();
		if(attrs.containsKey("id")) {
			result.setId(attrs.get("id").getValue());
		} else {
			throw new SqlMapException("class节点 id 不能为空");
		}
		if(attrs.containsKey("name")) {
			result.setClassName(attrs.get("name").getValue());
		}
		//else throw new SqlMapException("class节点 name 不能为空");
		
		for(XmlNode n : node.getChildNodes())
		{
			if(n.getNodeType()==XmlNodeType.Element)
			{
				if(n.getName().equalsIgnoreCase("PROPERTY"))
				{
					XmlAttributeCollection nAttrs = n.getAttributes();
					if(nAttrs.containsKey("name") && nAttrs.containsKey("column"))
					{
						if(result.getProperties()==null) {
							result.setProperties(new HashMap<String, String>());
						}
						result.getProperties().put(nAttrs.get("name").getValue(),nAttrs.get("column").getValue());
					}
					else {
						throw new SqlMapException(StringUtil.format("class {0} 的property节点中 name和column属性不能为空", attrs.get("id").getValue()));
					}
				}
			}
		}
		return result;
	}
	
	/** 构造Sql配置项子句 */
	private SqlItemClause buildSqlItemClause(XmlNode node)
	{
		SqlItemClause clause = new SqlItemClause();
		XmlAttributeCollection attrs = node.getAttributes();
		if(attrs.containsKey("id")) {
			clause.setId(attrs.get("id").getValue());
		}
		if(attrs.containsKey("prepend")) {
			clause.setPrepend(attrs.get("prepend").getValue());
		}
		if(attrs.containsKey("assert")) {
			clause.setAssert(attrs.get("assert").getValue());
		}
		if(attrs.containsKey("nvl")) {
			clause.setNvl(attrs.get("nvl").getValue());
		}
		if(attrs.containsKey("parent"))
		{
			String parent = attrs.get("parent").getValue();
			if(clauses.containsKey(parent))
			{
				if(attrs.containsKey("params"))
				{
					String[] paramNames = attrs.get("params").getValue().replaceAll("\\'","").split("\\,");
					clause.setParams(paramNames);
				}
				else {
					clause.setParams(new String[0]);
				}
				clause.setParent(clauses.get(parent));
			}
			else {
				throw new SqlMapException(StringUtil.format("找不到继承的节点 {0}", parent));
			}
		}
		return clause;
	}
	
	private SqlItemForeach buildSqlItemForeach(XmlNode node)
	{
		SqlItemForeach foreach = new SqlItemForeach();
		XmlAttributeCollection attrs = node.getAttributes();
		if(attrs.containsKey("in")) {
			foreach.setInParamName(attrs.get("in").getValue());
		}
		if(attrs.containsKey("vars")){
			String[] vars = attrs.get("vars").getValue().split("\\,");
			foreach.setVariableNames(vars);
		}
		if(attrs.containsKey("split")) {
			foreach.setSplit(attrs.get("split").getValue());
		}
		return foreach;
	}
	
	/** 构造Sql函数配置 */
	private SqlItemMethod buildSqlItemMethod(XmlNode node)
	{
		XmlAttributeCollection attrs = node.getAttributes();
		if(attrs.containsKey("name"))
		{
			String methodName = attrs.get("name").getValue();
			if(StringUtil.isNotEmpty(methodName))
			{
				String params = this.getChildText(node);
				SqlItemMethod method = new SqlItemMethod(methodName,params);
				if(attrs.containsKey("output")) {
					method.setOutput(attrs.get("output").getValue());
				}
				return method;
			}
			else {
				throw new SqlMapException(StringUtil.format("函数名不能为空 {0}", ""));
			}
		}
		else {
			throw new SqlMapException(StringUtil.format("函数名不能为空 {0}", ""));
		}
	}
	
	private SqlItemInclude buildSqlItemInclude(XmlNode node){
		SqlItemInclude include = new SqlItemInclude();
		XmlAttributeCollection attrs = node.getAttributes();
		if(attrs.containsKey("refId")) {
			include.setRefId(attrs.get("refId").getValue());
		} else {
			throw new SqlMapException("include节点 refId 不能为空");
		}
		
		SqlItem sqlItem = this.get(include.getRefId());
		if(sqlItem!=null && sqlItem.getContent()!=null){
			for(ISqlItemPart part : sqlItem.getContent().getParts()){
				include.addPart(part);
			}
			if(sqlItem.getPolyGroups()!=null) {
				for (SqlJoinGroup polyGroup : sqlItem.getPolyGroups()) {
					if(polyGroup.getJoins()!=null) {
						for (SqlJoin join : polyGroup.getJoins()) {
							include.getJoins().add(join);
						}
					}
				}
			}
		}
		
		return include;
	}

	public SqlItem addCountSql(String sqlId, SqlString sqlString){
		if(!this.items.containsKey(sqlId)){
			SqlItemCount item = new SqlItemCount(sqlId,sqlString);
			item.setDynamic(false);
			this.items.put(sqlId, item);
		}
		return this.items.get(sqlId);
	}
	
	private SqlItemDynamic buildSqlItemDynamic(XmlNode node){
		SqlItemDynamic dynamic = new SqlItemDynamic();
		return dynamic;
	}
	
	private CacheModel buildCacheModel(XmlNode node){
		XmlAttributeCollection attrs = node.getAttributes();
		CacheModel cache = new CacheModel();
		if(attrs.containsKey("id")) {
			cache.setId(attrs.get("id").getValue());
		} else {
			throw new SqlMapException("缓存Id不能为空");
		}
		if(attrs.containsKey("type")) {
			cache.setCacheType(attrs.get("type").getValue());
		} else {
			throw new SqlMapException("缓存类型不能为空");
		}
		
		for(XmlNode n : node.getChildNodes()){
			if(n.getNodeType()==XmlNodeType.Element){
				if(n.getName().equalsIgnoreCase("flushInterval")){
					if(n.getAttributes().containsKey("days")){
						long days = Long.parseLong(n.getAttributes().get("days").getValue());
						cache.setFlushInterval(cache.getFlushInterval()+days*24L*3600L*1000L);
					}else if(n.getAttributes().containsKey("hours")){
						long hours = Long.parseLong(n.getAttributes().get("hours").getValue());
						cache.setFlushInterval(cache.getFlushInterval()+hours*3600L*1000L);
					}else if(n.getAttributes().containsKey("minutes")){
						long minutes = Long.parseLong(n.getAttributes().get("minutes").getValue());
						cache.setFlushInterval(cache.getFlushInterval()+minutes*60L*1000L);
					}else if(n.getAttributes().containsKey("seconds")){
						long seconds = Long.parseLong(n.getAttributes().get("seconds").getValue());
						cache.setFlushInterval(cache.getFlushInterval()+seconds*1000L);
					}
				}else if(n.getName().equalsIgnoreCase("flushOnExecute")){
					if(n.getAttributes().containsKey("statement")){
						cache.addFlushStatement(n.getAttributes().get("statement").getValue());
					}
				}
			}
		}
		
		return cache;
	}
	
	private String getChildText(XmlNode node)
	{
		String text = "";
		for(XmlNode n : node.getChildNodes())
		{
			if(n.getNodeType() == XmlNodeType.Text) {
				text += n.getValue();
			}
		}
		return text;
	}
	
	/**
	 * 解析外部动态Xml内容
	 * @param xml
	 * @param content
	 */
	public void parserChildContent(String xml,SqlItemContent content){
		XmlDocument xdoc = null;
		StringBuilder dynamicXml = new StringBuilder();
		dynamicXml.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>");
		dynamicXml.append("<dynamic>");
		dynamicXml.append(xml);
		dynamicXml.append("</dynamic>");
		try{xdoc=new XmlDocument(dynamicXml.toString());}
		catch(Exception e){throw new SqlMapException(StringUtil.format("解析外部动态xml{0} 出错,格式不正确",xml),e);}
		
		for(XmlNode root : xdoc.getChildNodes()){
        	if(root.getName().equalsIgnoreCase("dynamic")){
        		this.buildChildContent(root,content);
        	}
        }
		
	}

	/**
	 * 添加动态Sql
	 * @param sqlId
	 * @param sqlString
	 */
	public SqlItem addDynamicSql(String sqlId, String sqlString){
		if(this.items.containsKey(sqlId)){
			SqlItem item = this.items.get(sqlId);
			if(item.isDynamic() && !sqlString.equals(item.getDynamicXml())){
				item = this.buildDynamicSqlItem(sqlString);
				item.setDynamic(true);
				item.setDynamicXml(sqlString);
				this.items.put(sqlId, item);
			}
		}else{
			SqlItem item = this.buildDynamicSqlItem(sqlString);
			item.setDynamic(true);
			item.setDynamicXml(sqlString);
			this.items.put(sqlId, item);
		}
		return this.items.get(sqlId);
	}
	
	private SqlItem buildDynamicSqlItem(String sqlString){
		XmlDocument xdoc = null;
		String xml = sqlString.replaceAll("\\\\t","").replaceAll("\\\\r","").replaceAll("\\\\n","");
		try{xdoc=new XmlDocument("<?xml version=\"1.0\" encoding=\"utf-8\" ?><tpsql>"+xml+"</tpsql>");}
		catch(Exception e){throw new SqlMapException(StringUtil.format("动态添加Sql {0} 出错,格式不正确",sqlString),e);}
		XmlNode node = xdoc.getChildNodes().get(0);
		return this.buildSqlItem(node.getChildNodes().get(0));
	}

	/**
	 *
	 * @return
	 */
	public Collection<SqlItem> getSqlItems(){
		return this.items.values();
	}

	public void afterPropertiesSet() throws Exception {
		this.onit();
	}
}
