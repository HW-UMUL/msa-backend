package com.ssg.kms.tableuser;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ssg.kms.mapping.GetTableMapping;
import com.ssg.kms.mapping.GetUserIdMapping;

public interface TableUserRepository extends JpaRepository<TableUser, Long> {

	
	List<GetTableMapping> findAllByUserId(Long userId);

	void deleteAllByUserId(Long id);

	TableUser findOneByTableIdAndUserId(Long tableId, Long UserId);
	
	List<TableUser> findByTableIdAndUserId(Long tableId, Long UserId);
	
	List<TableUser> findByUserIdAndAcceptFalse(Long userId);
	
	List<TableUser> findAllByUserIdAndAcceptTrue(Long userId);

	TableUser findByTableId(Long tableId);

//	TableUser findByUserEmail(String email);

	TableUser findTop1ByTableIdOrderByIdAsc(Long tableId);

	TableUser findByUserIdAndTableId(Long id, Long tableId);
	
	@Query(value = "SELECT tu.table_id FROM table_user tu WHERE tu.user_id = :userId", nativeQuery = true)
	List<Long> findTableIdAllByUserId(Long userId);
	
	// 알람
	@Query(value = "SELECT tu.user_id FROM table_user tu WHERE tu.table_id = :tableId", nativeQuery = true)
	List<Long> findAllUserAllByTableId(Long tableId);

	List<GetUserIdMapping> findAllUsersByTableIdAndAcceptTrue(Long tableId);

	TableUser findByUserId(Long otherUserId);

	List<TableUser> findAllByTableIdAndAcceptTrue(Long tableId);
	
}