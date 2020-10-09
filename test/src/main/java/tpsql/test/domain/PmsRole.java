package tpsql.test.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 角色
 */
public class PmsRole implements Serializable {
	private static final long serialVersionUID = 2881985801455923712L;
	/* 角色ID */
	private Integer roleId;
	/* 角色类型 */
	private Integer roleType;
	/* 角色名称 */
	private String roleName;
	/* 排序号 */
	private Integer roleSortNo;
	/* 是否默认角色 1:是 0:否 */
	private Boolean roleIsDefault;
	/* 备注 */
	private String roleRemark;
	/* 创建时间 */
	private Date roleCreateDate;
	/* 更新时间 */
	private Date roleUpdateDate;
	/* 创建用户 */
	private Integer roleCreateUser;
	/* 更新用户 */
	private Integer roleUpdateUser;
	/* 是否固定角色 1:是 0:否 */
	private Boolean roleIsFixed;

	public Integer getRoleId(){
		return this.roleId;
	}

	public void setRoleId(Integer roleId){
		this.roleId=roleId;
	}

	public Integer getRoleType(){
		return this.roleType;
	}

	public void setRoleType(Integer roleType){
		this.roleType=roleType;
	}

	public String getRoleName(){
		return this.roleName;
	}

	public void setRoleName(String roleName){
		this.roleName=roleName;
	}

	public Integer getRoleSortNo(){
		return this.roleSortNo;
	}

	public void setRoleSortNo(Integer roleSortNo){
		this.roleSortNo=roleSortNo;
	}

	public Boolean getRoleIsDefault(){
		return this.roleIsDefault;
	}

	public void setRoleIsDefault(Boolean roleIsDefault){
		this.roleIsDefault=roleIsDefault;
	}

	public String getRoleRemark(){
		return this.roleRemark;
	}

	public void setRoleRemark(String roleRemark){
		this.roleRemark=roleRemark;
	}

	public Date getRoleCreateDate(){
		return this.roleCreateDate;
	}

	public void setRoleCreateDate(Date roleCreateDate){
		this.roleCreateDate=roleCreateDate;
	}

	public Date getRoleUpdateDate(){
		return this.roleUpdateDate;
	}

	public void setRoleUpdateDate(Date roleUpdateDate){
		this.roleUpdateDate=roleUpdateDate;
	}

	public Integer getRoleCreateUser(){
		return this.roleCreateUser;
	}

	public void setRoleCreateUser(Integer roleCreateUser){
		this.roleCreateUser=roleCreateUser;
	}

	public Integer getRoleUpdateUser(){
		return this.roleUpdateUser;
	}

	public void setRoleUpdateUser(Integer roleUpdateUser){
		this.roleUpdateUser=roleUpdateUser;
	}

	public Boolean getRoleIsFixed(){
		return this.roleIsFixed;
	}

	public void setRoleIsFixed(Boolean roleIsFixed){
		this.roleIsFixed=roleIsFixed;
	}

}