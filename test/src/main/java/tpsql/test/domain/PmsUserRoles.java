package tpsql.test.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户角色表
 */
public class PmsUserRoles implements Serializable {
	private static final long serialVersionUID = 7000764360004445396L;
	/* 主键ID */
	private Integer purId;
	/* 用户ID */
	private Integer purUserId;
	/* 角色ID */
	private Integer purRoleId;
	/* 创建时间 */
	private Date purCreateDate;
	/* 修改时间 */
	private Date purUpdateDate;
	/* 创建用户 */
	private Integer purCreateUser;
	/* 修改用户 */
	private Integer purUpdateUser;

	public Integer getPurId(){
		return this.purId;
	}

	public void setPurId(Integer purId){
		this.purId=purId;
	}

	public Integer getPurUserId(){
		return this.purUserId;
	}

	public void setPurUserId(Integer purUserId){
		this.purUserId=purUserId;
	}

	public Integer getPurRoleId(){
		return this.purRoleId;
	}

	public void setPurRoleId(Integer purRoleId){
		this.purRoleId=purRoleId;
	}

	public Date getPurCreateDate(){
		return this.purCreateDate;
	}

	public void setPurCreateDate(Date purCreateDate){
		this.purCreateDate=purCreateDate;
	}

	public Date getPurUpdateDate(){
		return this.purUpdateDate;
	}

	public void setPurUpdateDate(Date purUpdateDate){
		this.purUpdateDate=purUpdateDate;
	}

	public Integer getPurCreateUser(){
		return this.purCreateUser;
	}

	public void setPurCreateUser(Integer purCreateUser){
		this.purCreateUser=purCreateUser;
	}

	public Integer getPurUpdateUser(){
		return this.purUpdateUser;
	}

	public void setPurUpdateUser(Integer purUpdateUser){
		this.purUpdateUser=purUpdateUser;
	}

}