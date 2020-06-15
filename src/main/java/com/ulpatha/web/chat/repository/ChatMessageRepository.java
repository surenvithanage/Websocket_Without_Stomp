package com.ulpatha.web.chat.repository;

import com.ulpatha.web.chat.model.ChatMessage;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
public interface ChatMessageRepository extends CrudRepository<ChatMessage, String> {
    @Query(" FROM"
            + "    ChatMessage m"
            + "  WHERE"
            + "    m.authorUser.id IN (:userIdOne, :userIdTwo)"
            + "  AND"
            + "    m.recipientUser.id IN (:userIdOne, :userIdTwo)"
            + "  AND"
            + "    m.postId =:postId"
            + "  ORDER BY"
            + "    m.timeSent"
            + "  DESC")
    List<ChatMessage> getExistingChatMessages(
            @Param("userIdOne") long userIdOne, @Param("userIdTwo") long userIdTwo, Pageable pageable, @Param("postId") long postId);

}
