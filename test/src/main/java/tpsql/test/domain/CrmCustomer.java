package tpsql.test.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 客户
 */
public class CrmCustomer implements Serializable {
	private static final long serialVersionUID = 6401766494691181705L;
	/* 客户ID */
	private Integer custId;
	/* 客户名称 */
	private String customerName;
	/* 联系人 */
	private String contacterName;
	/* 联系电话 */
	private String contacterPhone;
	/* 微信 */
	private String weixin;
	/* QQ */
	private String qq;
	/* 地区 */
	private String customerAddress;
	/* 旺旺 */
	private String wangwang;
	/* 地区 */
	private String customerArea;
	/* 生日 */
	private Date birthday;

	public Integer getCustId(){
		return this.custId;
	}

	public void setCustId(Integer custId){
		this.custId=custId;
	}

	public String getCustomerName(){
		return this.customerName;
	}

	public void setCustomerName(String customerName){
		this.customerName=customerName;
	}

	public String getContacterName(){
		return this.contacterName;
	}

	public void setContacterName(String contacterName){
		this.contacterName=contacterName;
	}

	public String getContacterPhone(){
		return this.contacterPhone;
	}

	public void setContacterPhone(String contacterPhone){
		this.contacterPhone=contacterPhone;
	}

	public String getWeixin(){
		return this.weixin;
	}

	public void setWeixin(String weixin){
		this.weixin=weixin;
	}

	public String getQq(){
		return this.qq;
	}

	public void setQq(String qq){
		this.qq=qq;
	}

	public String getCustomerAddress(){
		return this.customerAddress;
	}

	public void setCustomerAddress(String customerAddress){
		this.customerAddress=customerAddress;
	}

	public String getWangwang(){
		return this.wangwang;
	}

	public void setWangwang(String wangwang){
		this.wangwang=wangwang;
	}

	public String getCustomerArea(){
		return this.customerArea;
	}

	public void setCustomerArea(String customerArea){
		this.customerArea=customerArea;
	}

	public Date getBirthday(){
		return this.birthday;
	}

	public void setBirthday(Date birthday){
		this.birthday=birthday;
	}

}