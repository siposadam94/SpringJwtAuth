package sAdam.RSJwt.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

@Table(name="AUTH_AUTHORITY")
@Entity
public class Authority implements GrantedAuthority {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column (name = "ROLE_CODE")
	private String roleCode;
	@Column (name = "ROLE_DESC")
	private String roleDesc;

	@Override
	public String getAuthority() {
		return roleCode;
	}

	public Authority(String roleCode, String roleDesc) {
		super();
		this.roleCode = roleCode;
		this.roleDesc = roleDesc;
	}
	
	public Authority() {

	}
}
