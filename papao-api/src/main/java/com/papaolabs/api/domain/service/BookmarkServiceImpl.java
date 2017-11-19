package com.papaolabs.api.domain.service;

import com.papaolabs.api.infrastructure.feign.openapi.PushApiClient;
import com.papaolabs.api.infrastructure.persistence.jpa.entity.Bookmark;
import com.papaolabs.api.infrastructure.persistence.jpa.entity.Post;
import com.papaolabs.api.infrastructure.persistence.jpa.entity.User;
import com.papaolabs.api.infrastructure.persistence.jpa.repository.BookmarkRepository;
import com.papaolabs.api.infrastructure.persistence.jpa.repository.PostRepository;
import com.papaolabs.api.infrastructure.persistence.jpa.repository.UserRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.Objects;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

@Service
public class BookmarkServiceImpl implements BookmarkService {
    @NotNull
    private final BookmarkRepository bookmarkRepository;
    @NotNull
    private final PostRepository postRepository;
    @NotNull
    private final UserRepository userRepository;
    @NotNull
    private final PushApiClient pushApiClient;

    public BookmarkServiceImpl(BookmarkRepository bookmarkRepository,
                               PostRepository postRepository,
                               UserRepository userRepository,
                               PushApiClient pushApiClient) {
        this.bookmarkRepository = bookmarkRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.pushApiClient = pushApiClient;
    }

    @Override
    public Long registerBookmark(String postId, String userId) {
        Bookmark bookmark = bookmarkRepository.findByPostIdAndUserId(Long.valueOf(postId), Long.valueOf(userId));
        if (Objects.isNull(bookmark)) {
            bookmark = new Bookmark();
            bookmark.setPostId(Long.valueOf(postId));
            bookmark.setUserId(Long.valueOf(userId));
            this.bookmarkRepository.save(bookmark);
        }
        Post post = postRepository.findOne(Long.valueOf(postId));
        User user = userRepository.findByUid(String.valueOf(post.getUid()));
        String message = StringUtils.join(user.getNickName(), "님이 내 포스트를 북마크 했습니다\\ud83d\\udc36");
        pushApiClient.sendPush("BOOKMARK", String.valueOf(post.getUid()), message, postId);
        return this.bookmarkRepository.countByPostId(Long.valueOf(postId));
    }

    @Transactional
    @Override
    public Long cancelBookmark(String postId, String userId) {
        Bookmark bookmark = bookmarkRepository.findByPostIdAndUserId(Long.valueOf(postId), Long.valueOf(userId));
        if (!Objects.isNull(bookmark)) {
            this.bookmarkRepository.delete(bookmark.getId());
        }
        return this.bookmarkRepository.countByPostId(Long.valueOf(postId));
    }

    @Override
    public Long countBookmark(String postId) {
        return this.bookmarkRepository.countByPostId(Long.valueOf(postId));
    }

    @Override
    public Boolean checkBookmark(String postId, String userId) {
        Bookmark bookmark = this.bookmarkRepository.findByPostIdAndUserId(Long.valueOf(postId), Long.valueOf(userId));
        return Objects.isNull(bookmark) ? FALSE : TRUE;
    }
}
