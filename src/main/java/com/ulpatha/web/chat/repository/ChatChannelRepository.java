package com.ulpatha.web.chat.repository;

import com.ulpatha.web.chat.model.ChatChannel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
public interface ChatChannelRepository extends CrudRepository<ChatChannel, String> {

    @Query(" FROM"
            + " ChatChannel c"
            + " WHERE"
            + " c.userOne.id IN (:userId)"
            + " OR"
            + " c.userTwo.id IN (:userId)")
    List<ChatChannel> findUserChatHistory(@Param("userId") long userId);

    @Query(" FROM"
            + "    ChatChannel c"
            + "  WHERE"
            + "    c.userOne.id IN (:userOneId, :userTwoId) "
            + "  AND"
            + "    c.userTwo.id IN (:userOneId, :userTwoId) "
            + "  AND"
            + "    c.postId =:postId")
    List<ChatChannel> findExistingChannel(
            @Param("userOneId") long userOneId, @Param("userTwoId") long userTwoId, @Param("postId") long postId);

    @Query(" SELECT"
            + "    uuid"
            + "  FROM"
            + "    ChatChannel c"
            + "  WHERE"
            + "    c.userOne.id IN (:userIdOne, :userIdTwo)"
            + "  AND"
            + "    c.userTwo.id IN (:userIdOne, :userIdTwo)")
    String getChannelUuid(
            @Param("userIdOne") long userIdOne, @Param("userIdTwo") long userIdTwo);

    @Query(" FROM"
            + "    ChatChannel c"
            + "  WHERE"
            + "    c.uuid IS :uuid")
    ChatChannel getChannelDetails(@Param("uuid") String uuid);
}
