package pl.ziemniakoss.studentsresourcesmanager;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;

public class CustomUserDetails implements org.springframework.security.core.userdetails.UserDetails {
	private int id;
	private String email;
	private String password;
	private String name;
	private Collection<GrantedAuthority> authorities;

	public CustomUserDetails(){}

	public CustomUserDetails(int id, String email, String password, String name, String role) {
		this.id = id;
		this.email = email;
		this.password = password;
		this.name = name;
		authorities = new ArrayList<>(1);
		authorities.add(new SimpleGrantedAuthority(role));
	}

	public CustomUserDetails(String email, String password, String name) {
		this(0, email,password,name, "");
	}

	public CustomUserDetails(int id, String email, String password, String name, Collection<GrantedAuthority> authorities) {
		this.id = id;
		this.email = email;
		this.password = password;
		this.name = name;
		this.authorities = authorities;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setAuthorities(Collection<GrantedAuthority> authorities) {
		this.authorities = authorities;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
