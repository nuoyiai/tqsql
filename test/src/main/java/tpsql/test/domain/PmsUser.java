package tpsql.test.domain;

import java.io.Serializable;

/**
 * 用户
 */
public class PmsUser implements Serializable {
	private static final long serialVersionUID = 3699329518701360929L;
	/* 用户ID */
	private Integer userId;
	/* 姓名 */
	private String userName;
	/* 帐号 */
	private String userAccount;
	/* 密码 */
	private String userPassword;
	/* 邮箱 */
	private String userEmail;
	/* 手机号码 */
	private String userPhone;
	/* 机构ID */
	private Integer deptId;
	/* 上次登录IP */
	private String loginIp;
	/* 是否绑定邮箱 (1 已绑定 0 未绑定) */
	private Boolean isBindEmail;
	/* 是否绑定手机 (1 已绑定 0 未绑定) */
	private Boolean isBindPhone;
	/* 删除标记 (1 删除 0 未删除) */
	private Boolean isDel;
	/* 是否禁用 (1 禁用 0 未禁用) */
	private Boolean isDisable;

	public Integer getUserId(){
		return this.userId;
	}

	public void setUserId(Integer userId){
		this.userId=userId;
	}

	public String getUserName(){
		return this.userName;
	}

	public void setUserName(String userName){
		this.userName=userName;
	}

	public String getUserAccount(){
		return this.userAccount;
	}

	public void setUserAccount(String userAccount){
		this.userAccount=userAccount;
	}

	public String getUserPassword(){
		return this.userPassword;
	}

	public void setUserPassword(String userPassword){
		this.userPassword=userPassword;
	}

	public String getUserEmail(){
		return this.userEmail;
	}

	public void setUserEmail(String userEmail){
		this.userEmail=userEmail;
	}

	public String getUserPhone(){
		return this.userPhone;
	}

	public void setUserPhone(String userPhone){
		this.userPhone=userPhone;
	}

	public Integer getDeptId(){
		return this.deptId;
	}

	public void setDeptId(Integer deptId){
		this.deptId=deptId;
	}

	public String getLoginIp(){
		return this.loginIp;
	}

	public void setLoginIp(String loginIp){
		this.loginIp=loginIp;
	}

	public Boolean getIsBindEmail(){
		return this.isBindEmail;
	}

	public void setIsBindEmail(Boolean isBindEmail){
		this.isBindEmail=isBindEmail;
	}

	public Boolean getIsBindPhone(){
		return this.isBindPhone;
	}

	public void setIsBindPhone(Boolean isBindPhone){
		this.isBindPhone=isBindPhone;
	}

	public Boolean getIsDel(){
		return this.isDel;
	}

	public void setIsDel(Boolean isDel){
		this.isDel=isDel;
	}

	public Boolean getIsDisable(){
		return this.isDisable;
	}

	public void setIsDisable(Boolean isDisable){
		this.isDisable=isDisable;
	}

}