/**
 *
 */
package com.ulpatha.web.chat.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author vimukthi_r
 *
 */
@Entity
@Table(name = "role")
public class Role implements java.io.Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 5943416112489776833L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "role_id", unique = true, nullable = false)
	private Long roleId;
	@NotBlank
	private String name;
	@ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
	private List<User> users;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "roles_privileges", joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "role_id"), inverseJoinColumns = @JoinColumn(name = "privilege_id", referencedColumnName = "privilege_id"))
	private List<Privilege> privileges;

	/**
	 *
	 */
	public Role() {
		super();
	}

	/**
	 * @param name
	 */
	public Role(@NotNull String name) {
		super();
		this.name = name;
	}

	/**
	 * @param roleId
	 * @param name
	 * @param users
	 * @param privileges
	 */
	public Role(Long roleId, @NotNull String name, List<User> users, List<Privilege> privileges) {
		super();
		this.roleId = roleId;
		this.name = name;
		this.users = users;
		this.privileges = privileges;
	}

	/**
	 * @return the id
	 */
	public Long getRoleId() {
		return roleId;
	}

	/**
	 * @param roleId
	 *            the id to set
	 */
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the users
	 */
	public List<User> getUsers() {
		return users;
	}

	/**
	 * @param users
	 *            the users to set
	 */
	public void setUsers(List<User> users) {
		this.users = users;
	}

	/**
	 * @return the privileges
	 */
	public List<Privilege> getPrivileges() {
		return privileges;
	}

	/**
	 * @param privileges
	 *            the privileges to set
	 */
	public void setPrivileges(List<Privilege> privileges) {
		this.privileges = privileges;
	}

}
