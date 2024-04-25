package com.ssg.kms.chatroomuser;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ChatRoomUserRepository extends JpaRepository<ChatRoomUser, Long> {

	List<ChatRoomUser> findAllByChatRoomId(Long chatRoomId);

	List<ChatRoomUser> findAllByUserId(Long id);

	Optional<ChatRoomUser> findByUserId(Long id);

	void deleteAllByUserId(Long id);

	List<ChatRoomUser> findByChatRoomIdAndUserId(Long chatRoomId, Long userId);

	@Query(value = "SELECT cru.user_id FROM chat_room_user cru WHERE cru.chat_room_id = :chatRoomId", nativeQuery = true)
	List<Long> findUserIdsByChatRoomId(Long chatRoomId);

}
