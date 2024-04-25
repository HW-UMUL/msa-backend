package com.ssg.kms.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.ssg.kms.FileManager.FileManager;
import com.ssg.kms.FileManager.FileManagerService;
import com.ssg.kms.role.ERole;
import com.ssg.kms.role.Role;
import com.ssg.kms.role.RoleRepository;
import com.ssg.kms.security.SecurityUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;    
    
    private final PasswordEncoder passwordEncoder;
    private final FileManagerService fileManagerService;
    
    @Transactional
    public Boolean delete(Optional<User> user) {



    	userRoleRepository.deleteAllByUserId(user.get().getId());
    	
        userRepository.delete(user.get());
        
		return true;
    }
    
    @Transactional(readOnly = true)
    public List<String> getInfo(Optional<User> user) {
    	List<String> info = new ArrayList<>();
    	info.add(user.get().getUsername());
    	info.add(user.get().getEmail());
    	
    	if(user.get().getProfile() != null) {    		
        	info.add(user.get().getProfile().getStoreFileName());
    	}

    	return info;
    }
    
    @Transactional(readOnly = true)
    public List<String> searchEmails(String searchKeyword, Optional<User> user) {
    	List<String> userEmails = userRepository.findAllByEmailContaining(searchKeyword, user.get().getId());

    	return userEmails;
    }

	// USER INFO
    //////////////////////////////////////////////////////////////////////    
    @Transactional
    public String updateUsername(UsernameDTO usernameDto, Optional<User> user) {
    	user.get().setUsername(usernameDto.getUsername());
    	userRepository.save(user.get());
    	return usernameDto.getUsername();
    }
    
    @Transactional
    public String updateEmail(EmailDTO emailDto, Optional<User> user) {
    	user.get().setEmail(emailDto.getEmail());
    	userRepository.save(user.get());
    	return emailDto.getEmail();
    }
    
    @Transactional
    public Boolean updatePassword(PasswordDTO passwordDto, Optional<User> user) {
    	// passwordEncoder.encode(userDto.getPassword())
    	user.get().setPassword(passwordEncoder.encode(passwordDto.getPassword()));
    	userRepository.save(user.get());
    	return true;
    }
    
    @Transactional
    public Boolean updateImage(MultipartFile file, Optional<User> user) {
    	MultipartFile[] multipartFiles = {file};
    	FileManager fileManager;
    	try {
			fileManager = fileManagerService.createFile(multipartFiles, user).get(0);
			user.get().setProfile(fileManager);
			userRepository.save(user.get());
		} catch (Exception e) {
			return false;
		}
    	return true;
    }
    
    //////////////////////////////////////////////////////////////////////
    @Transactional
    public User signup(UserDto userDto) {
        if (userRepository.findOneWithAuthoritiesByUsername(userDto.getUsername()).orElse(null) != null) {
            throw new RuntimeException("이미 가입되어 있는 유저입니다.");
        }
        
        if (userRepository.findOneWithAuthoritiesByEmail(userDto.getEmail()).orElse(null) != null) {
            throw new RuntimeException("이미 가입되어 있는 이메일입니다.");
        }

        // 가입되어 있지 않은 회원이면,
        // 권한 정보 만들고
        Role authority = Role.builder()
                .role(ERole.ROLE_USER)
                .id(roleRepository.findByRole(ERole.ROLE_USER).getId())
                .build();

//        Role authority2 = Role.builder()
//                .role(ERole.ROLE_ADMIN)
//                .id(roleRepository.findByRole(ERole.ROLE_ADMIN).getId())
//                .build();

        // 유저 정보를 만들어서 save
        User user = User.builder()
                .username(userDto.getUsername())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .email(userDto.getEmail())
                .build();
        
        UserRole userRole = UserRole.builder()
        		.user(user)
        		.role(authority)
        		.build();

//        UserRole userRole2 = UserRole.builder()
//        		.user(user)
//        		.role(authority2)
//        		.build();

//        user.getUserRoles().add(userRole);        
//        userRole.setUser(user);
        userRepository.save(user);
        userRoleRepository.save(userRole);
//        userRoleRepository.save(userRole2);
        
        return user;
    }

    // 유저,권한 정보를 가져오는 메소드
    @Transactional(readOnly = true)
    public Optional<User> getUserWithAuthorities(String username) {
        return userRepository.findOneWithAuthoritiesByUsername(username);
    }

    // 현재 securityContext에 저장된 username의 정보만 가져오는 메소드
    @Transactional(readOnly = true)
    public Optional<User> getMyUserWithAuthorities() {
        return SecurityUtil.getCurrentUsername()
                .flatMap(userRepository::findOneWithAuthoritiesByUsername);
    }
    
    @Transactional
    public User updateUser(UserDto userDto, User user) {
    	user.setEmail(userDto.getEmail());
    	user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        return userRepository.save(user);
    }
    
    @Transactional
    public void deleteUser(User user) {
    	userRepository.delete(user);
    }
    
    @Transactional(readOnly = true)
    public User getOtherUserInfo(Long userId, Optional<User> user) {
    	return userRepository.findById(userId).get();
    }

	public Long getUserId(Optional<User> myUserWithAuthorities) {
		
		return myUserWithAuthorities.get().getId();
	}

	public Map<String, Object> whoAreYou(Long userId, Optional<User> myUserWithAuthorities) {
		Map<String, Object> map = new HashMap<>();
		Optional<User> user = userRepository.findById(userId);
		
		if(user.isEmpty()) {
			map.put("username", "[탈퇴]");
			map.put("email", "[탈퇴]");			
		} else {
			map.put("username", user.get().getUsername());
			map.put("email", user.get().getEmail());						
		}
		if(user.isPresent() && user.get().getProfile() != null) {
			map.put("profile", user.get().getProfile().getStoreFileName());
		}
		
		// TODO Auto-generated method stub
		return map;
	}
	
	public Map<String, Object> whoAreYouEmail(EmailDTO emailDto, Optional<User> myUserWithAuthorities) {
		Map<String, Object> map = new HashMap<>();
		User user = userRepository.findByEmail(emailDto.getEmail());
		
		map.put("userId", user.getId());
		map.put("username", user.getUsername());
		map.put("email", user.getEmail());
		if(user.getProfile() != null) {
			map.put("profile", user.getProfile().getStoreFileName());
		}
		
		// TODO Auto-generated method stub
		return map;
	}
    
	
	
	
    // admin 怨꾩젙 �깮�꽦
    @Transactional
    public void createAdminUser() {
        Optional<User> existingAdmin = userRepository.findOneWithAuthoritiesByUsername("admin");
        if (existingAdmin.isPresent()) {
            throw new RuntimeException("�씠誘� 媛��엯�릺�뼱 �엳�뒗 �쑀���엯�땲�떎.");
        }

        Role adminRole = roleRepository.findByRole(ERole.ROLE_ADMIN);
        System.out.println(roleRepository.findById(1));
        if (adminRole == null) {
            throw new RuntimeException("ROLE_ADMIN 沅뚰븳�씠 議댁옱�븯吏� �븡�뒿�땲�떎.");
        }

        User adminUser = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("admin"))
                .email("admin@example.com")
                .build();

        UserRole userRole = UserRole.builder()
                .user(adminUser)
                .role(adminRole)
                .build();

        userRepository.save(adminUser);
        userRoleRepository.save(userRole);
    }
    
    // admin 沅뚰븳 �솗�씤
    @Transactional
    public boolean isAdmin(Optional<User> user) {
        if (user.isPresent()) {
            List<UserRole> userRoles = userRoleRepository.findAllByUser(user.get());
            return userRoles.stream()
                    .anyMatch(userRole -> userRole.getRole().getRole() == ERole.ROLE_ADMIN);
        }
        return false;
    }

    // �빐�떦 �쑀�� �궘�젣
    @Transactional
    public Boolean deleteUser(Long userid) {

    	userRoleRepository.deleteAllByUserId(userid);
    	
    	Optional<User> user = userRepository.findById(userid);
        userRepository.delete(user.get());
        
		return true;
    }

    // 紐⑤뱺 User �젙蹂� 媛��졇�삤�뒗 硫붿냼�뱶(admin �슜)
    @Transactional(readOnly = true)
    public List<User> getAllUserinfo() {
        return userRepository.findAll();
    }

    // user 紐⑤뱺 �젙蹂� 媛��졇�삤�뒗 硫붿냼�뱶(admin �슜)
    @Transactional(readOnly = true)
    public List<UserRole> getUserRoleByUserId(Long userId) {
    	List<UserRole> userrole = userRoleRepository.findAllByUserId(userId);
    	return userrole;
    }

    // user + role �젙蹂� 媛��졇�삤�뒗 硫붿냼�뱶
    @Transactional(readOnly = true)
    public List<UserRoleDTO> getRoleByUserId(Long userId) {
    	List<UserRole> userRoleList = userRoleRepository.findAllByUserId(userId);
    	List<UserRoleDTO> userRoleDTOList = new ArrayList<>();
    	for (UserRole userRole : userRoleList) {
            UserRoleDTO userRoleDTO = new UserRoleDTO();
            userRoleDTO.setUsername(userRole.getUser().getUsername());
            userRoleDTO.setEmail(userRole.getUser().getEmail());
            userRoleDTO.setProfile(userRole.getUser().getProfile());
            userRoleDTO.setRole(userRole.getRole());
            userRoleDTOList.add(userRoleDTO);
        }
    	return userRoleDTOList;
    }

}
