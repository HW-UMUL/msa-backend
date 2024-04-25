package com.ssg.kms.user;

import java.util.Set;

import com.ssg.kms.FileManager.FileManager;
import com.ssg.kms.role.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRoleDTO {

	
    @NotNull
    @Size(min = 3, max = 50)
    private String username;

    
    @NotBlank
    @Size(max = 50)
    @Email
    private String email;
    
    private FileManager profile;
    
    private Role role;
    
    
}