package tpsql.test.query;

import tpsql.test.domain.PmsRole;
import tpsql.test.domain.PmsUser;

import java.util.List;

public class PmsUserQuery extends PmsUser {
    private String roleIds;
    private String roleNames;

    private List<PmsRole> roleList;

    private PmsRole role;

    public String getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(String roleIds) {
        this.roleIds = roleIds;
    }

    public String getRoleNames() {
        return roleNames;
    }

    public void setRoleNames(String roleNames) {
        this.roleNames = roleNames;
    }

    public List<PmsRole> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<PmsRole> roleList) {
        this.roleList = roleList;
    }

    public PmsRole getRole() {
        return role;
    }

    public void setRole(PmsRole role) {
        this.role = role;
    }
}
