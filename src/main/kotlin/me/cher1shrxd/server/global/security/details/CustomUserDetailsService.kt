package me.cher1shrxd.server.global.security.details

import me.cher1shrxd.server.domain.user.repository.UserRepo
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service


@Service
class CustomUserDetailsService(
    private val userRepo: UserRepo
) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        val userEntity = userRepo.findByUsername(username)
            ?: throw UsernameNotFoundException("User not found with email: $username")

        return CustomUserDetails(userEntity)
    }
}