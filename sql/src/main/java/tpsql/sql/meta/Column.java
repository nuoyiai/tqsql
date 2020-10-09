package tpsql.sql.meta;

public class Column {
	private String label;
	private String name;
	private int type;
	private int precision;
	private int scale;
	private int length;
	private boolean isPrimaryKey;
	private boolean isNullable;
	private String typeName;
	private String className;
	private String schemaName;
	private String tableName;
	private String comment;			//注解
	private Class<?> clazz;
	
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public int getPrecision() {
		return precision;
	}
	public void setPrecision(int precision) {
		this.precision = precision;
	}
	public int getScale() {
		return scale;
	}
	public void setScale(int scale) {
		this.scale = scale;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getSchemaName() {
		return schemaName;
	}
	public void setSchemaName(String schemaName) {
		this.schemaName = schemaName;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public boolean isPrimaryKey() {
		return isPrimaryKey;
	}
	public void setPrimaryKey(boolean primaryKey) {
		isPrimaryKey = primaryKey;
	}
	public boolean isNullable() {
		return isNullable;
	}
	public void setNullable(boolean nullable) {
		isNullable = nullable;
	}
	public Class<?> getClassType(){
		if(clazz==null){
			if(className!=null && className.length()>0){
				try {
					clazz = Class.forName(className);
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return clazz;
	}

	@Override
	public String toString() {
		return "Column{" +
				"label='" + label + '\'' +
				", name='" + name + '\'' +
				", type=" + type +
				", precision=" + precision +
				", scale=" + scale +
				", length=" + length +
				", isPrimaryKey=" + isPrimaryKey +
				", isNullable=" + isNullable +
				", typeName='" + typeName + '\'' +
				", className='" + className + '\'' +
				", schemaName='" + schemaName + '\'' +
				", tableName='" + tableName + '\'' +
				", comment='" + comment + '\'' +
				", clazz=" + clazz +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Column column = (Column) o;

		if (type != column.type) return false;
		if (precision != column.precision) return false;
		if (scale != column.scale) return false;
		if (length != column.length) return false;
		if (isPrimaryKey != column.isPrimaryKey) return false;
		if (isNullable != column.isNullable) return false;
		if (label != null ? !label.equals(column.label) : column.label != null) return false;
		if (name != null ? !name.equals(column.name) : column.name != null) return false;
		if (typeName != null ? !typeName.equals(column.typeName) : column.typeName != null) return false;
		if (className != null ? !className.equals(column.className) : column.className != null) return false;
		if (schemaName != null ? !schemaName.equals(column.schemaName) : column.schemaName != null) return false;
		if (tableName != null ? !tableName.equals(column.tableName) : column.tableName != null) return false;
		if (comment != null ? !comment.equals(column.comment) : column.comment != null) return false;
		return clazz != null ? clazz.equals(column.clazz) : column.clazz == null;
	}

	@Override
	public int hashCode() {
		int result = label != null ? label.hashCode() : 0;
		result = 31 * result + (name != null ? name.hashCode() : 0);
		result = 31 * result + type;
		result = 31 * result + precision;
		result = 31 * result + scale;
		result = 31 * result + length;
		result = 31 * result + (isPrimaryKey ? 1 : 0);
		result = 31 * result + (isNullable ? 1 : 0);
		result = 31 * result + (typeName != null ? typeName.hashCode() : 0);
		result = 31 * result + (className != null ? className.hashCode() : 0);
		result = 31 * result + (schemaName != null ? schemaName.hashCode() : 0);
		result = 31 * result + (tableName != null ? tableName.hashCode() : 0);
		result = 31 * result + (comment != null ? comment.hashCode() : 0);
		result = 31 * result + (clazz != null ? clazz.hashCode() : 0);
		return result;
	}

	public Column clone(){
		Column column = new Column();
		column.setName(name);
		column.setPrimaryKey(isPrimaryKey);
		column.setNullable(isNullable);
		column.setScale(scale);
		column.setPrecision(precision);
		column.setLength(length);
		column.setTypeName(typeName);
		column.setClassName(className);
		column.setSchemaName(schemaName);
		column.setTableName(tableName);
		column.setComment(comment);
		column.setType(type);
		return column;
	}
}
