package com.ssic.catering.base.pojo;

public class AdminRoleMenu {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_ctr_admin_role_menu.id
     *
     * @mbggenerated Tue Aug 04 14:59:20 CST 2015
     */
    private String id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_ctr_admin_role_menu.role_id
     *
     * @mbggenerated Tue Aug 04 14:59:20 CST 2015
     */
    private String roleId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_ctr_admin_role_menu.menu_id
     *
     * @mbggenerated Tue Aug 04 14:59:20 CST 2015
     */
    private String menuId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_ctr_admin_role_menu.stat
     *
     * @mbggenerated Tue Aug 04 14:59:20 CST 2015
     */
    private Integer stat;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_ctr_admin_role_menu.id
     *
     * @return the value of t_ctr_admin_role_menu.id
     *
     * @mbggenerated Tue Aug 04 14:59:20 CST 2015
     */
    public String getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_ctr_admin_role_menu.id
     *
     * @param id the value for t_ctr_admin_role_menu.id
     *
     * @mbggenerated Tue Aug 04 14:59:20 CST 2015
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_ctr_admin_role_menu.role_id
     *
     * @return the value of t_ctr_admin_role_menu.role_id
     *
     * @mbggenerated Tue Aug 04 14:59:20 CST 2015
     */
    public String getRoleId() {
        return roleId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_ctr_admin_role_menu.role_id
     *
     * @param roleId the value for t_ctr_admin_role_menu.role_id
     *
     * @mbggenerated Tue Aug 04 14:59:20 CST 2015
     */
    public void setRoleId(String roleId) {
        this.roleId = roleId == null ? null : roleId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_ctr_admin_role_menu.menu_id
     *
     * @return the value of t_ctr_admin_role_menu.menu_id
     *
     * @mbggenerated Tue Aug 04 14:59:20 CST 2015
     */
    public String getMenuId() {
        return menuId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_ctr_admin_role_menu.menu_id
     *
     * @param menuId the value for t_ctr_admin_role_menu.menu_id
     *
     * @mbggenerated Tue Aug 04 14:59:20 CST 2015
     */
    public void setMenuId(String menuId) {
        this.menuId = menuId == null ? null : menuId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_ctr_admin_role_menu.stat
     *
     * @return the value of t_ctr_admin_role_menu.stat
     *
     * @mbggenerated Tue Aug 04 14:59:20 CST 2015
     */
    public Integer getStat() {
        return stat;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_ctr_admin_role_menu.stat
     *
     * @param stat the value for t_ctr_admin_role_menu.stat
     *
     * @mbggenerated Tue Aug 04 14:59:20 CST 2015
     */
    public void setStat(Integer stat) {
        this.stat = stat;
    }
}