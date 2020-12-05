package tpsql.core.json.parser;

public enum JsonLabelType {
	Undefine(-1),	/* 未定义 */
	Object(0),		/* 对像 */
	String(1),		/* 字符串 */
	Number(2),		/* 数字 */
	Array(3),		/* 数组 */
	Null(4),		/* 空值 */
	Boolean(5),		/* 布尔 */
	Date(6),		/* 日期 */
	Pair(7),		/* 键值对 */
	Value(8);		/* 值" */

	private int value;
	
	public int intValue()
	{
		return value;
	}
	
	JsonLabelType(int value)
	{
		this.value=value;
	}
}
