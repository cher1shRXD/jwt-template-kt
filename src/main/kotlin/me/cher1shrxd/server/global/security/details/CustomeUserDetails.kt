package me.cher1shrxd.server.global.security.details

import me.cher1shrxd.server.domain.user.entity.UserEntity
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class CustomUserDetails(
    val userEntity: UserEntity
) : UserDetails {

    override fun getAuthorities(): Collection<out GrantedAuthority> {
        return listOf(SimpleGrantedAuthority("USER"))
    }

    override fun getUsername(): String {
        return userEntity.username
    }

    override fun getPassword(): String {
        return ""
    }

    override fun isEnabled(): Boolean = true

    override fun isCredentialsNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true

    override fun isAccountNonExpired(): Boolean = true
}
