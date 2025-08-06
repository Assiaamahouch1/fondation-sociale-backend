package com.fondationsociale.auth.transformer;

import com.fondationsociale.auth.dto.UserGetDto;
import com.fondationsociale.auth.dto.UserPostDto;
import com.fondationsociale.auth.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserTransformer extends AbstractTransformer<User, UserGetDto, UserPostDto> {
    
    @Override
    public UserGetDto entityToGetDto(User user) {
        if (user == null) {
            return null;
        }
        
        return new UserGetDto(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getRole(),
                user.getIsActive(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }
    
    @Override
    public User postDtoToEntity(UserPostDto userPostDto) {
        if (userPostDto == null) {
            return null;
        }
        
        User user = new User();
        user.setUsername(userPostDto.getUsername());
        user.setEmail(userPostDto.getEmail());
        user.setPassword(userPostDto.getPassword());
        user.setFirstName(userPostDto.getFirstName());
        user.setLastName(userPostDto.getLastName());
        user.setRole(userPostDto.getRole());
        user.setIsActive(true);
        
        return user;
    }
    
    @Override
    public void updateEntityFromPostDto(UserPostDto userPostDto, User user) {
        if (userPostDto == null || user == null) {
            return;
        }
        
        if (userPostDto.getUsername() != null) {
            user.setUsername(userPostDto.getUsername());
        }
        if (userPostDto.getEmail() != null) {
            user.setEmail(userPostDto.getEmail());
        }
        if (userPostDto.getPassword() != null) {
            user.setPassword(userPostDto.getPassword());
        }
        if (userPostDto.getFirstName() != null) {
            user.setFirstName(userPostDto.getFirstName());
        }
        if (userPostDto.getLastName() != null) {
            user.setLastName(userPostDto.getLastName());
        }
        if (userPostDto.getRole() != null) {
            user.setRole(userPostDto.getRole());
        }
    }
}