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
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author suren_v
 *
 */
@Entity
@Table(name = "privilege")
public class Privilege implements java.io.Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1661678254106708185L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "privilege_id", unique = true, nullable = false)
	private Long privilegeId;

	@NotBlank
	private String name;

	@ManyToMany(mappedBy = "privileges", fetch = FetchType.LAZY)
	private List<Role> roles;

	/**
	 *
	 */
	public Privilege() {
		super();
	}

	/**
	 * @param name
	 */
	public Privilege(@NotNull String name) {
		super();
		this.name = name;
	}

	/**
	 * @param id
	 * @param name
	 * @param roles
	 */
	public Privilege(Long privilegeId, @NotNull String name, List<Role> roles) {
		super();
		this.privilegeId = privilegeId;
		this.name = name;
		this.roles = roles;
	}

	/**
	 * @return the id
	 */
	public Long getPrivilegeId() {
		return privilegeId;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setPrivilegeId(Long privilegeId) {
		this.privilegeId = privilegeId;
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
	 * @return the roles
	 */
	public List<Role> getRoles() {
		return roles;
	}

	/**
	 * @param roles
	 *            the roles to set
	 */
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

}
