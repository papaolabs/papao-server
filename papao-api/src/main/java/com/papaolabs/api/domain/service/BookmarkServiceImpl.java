package com.papaolabs.api.domain.service;

import com.papaolabs.api.infrastructure.feign.openapi.PushApiClient;
import com.papaolabs.api.infrastructure.persistence.jpa.entity.Bookmark;
import com.papaolabs.api.infrastructure.persistence.jpa.entity.Post;
import com.papaolabs.api.infrastructure.persistence.jpa.entity.User;
import com.papaolabs.api.infrastructure.persistence.jpa.repository.BookmarkRepository;
import com.papaolabs.api.infrastructure.persistence.jpa.repository.PostRepository;
import com.papaolabs.api.infrastructure.persistence.jpa.repository.UserRepository;
import com.papaolabs.api.interfaces.v1.controller.response.BookmarkDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

@Service
public class BookmarkServiceImpl implements BookmarkService {
    private static final String DATE_FORMAT = "yyyy-MM-dd hh:MM:ss";
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
    public BookmarkDTO readBookmarkByUserId(String userId, String index, String size) {
        PageRequest pageRequest = new PageRequest(Integer.valueOf(index), Integer.valueOf(size));
        Page<Bookmark> bookmarks = this.bookmarkRepository.findByUserId(Long.valueOf(userId), pageRequest);
        if(bookmarks == null) {
            BookmarkDTO bookmarkDTO = new BookmarkDTO();
            bookmarkDTO.setTotalPages(0);
            bookmarkDTO.setTotalElements(0L);
            bookmarkDTO.setElements(Arrays.asList());
            return bookmarkDTO;
        }
        return createBookmarkDTO(bookmarks);
    }

    @Override
    public BookmarkDTO readBookmarkByPostId(String postId, String index, String size) {
        PageRequest pageRequest = new PageRequest(Integer.valueOf(index), Integer.valueOf(size));
        Page<Bookmark> bookmarks = this.bookmarkRepository.findByPostId(Long.valueOf(postId), pageRequest);
        if(bookmarks == null) {
            BookmarkDTO bookmarkDTO = new BookmarkDTO();
            bookmarkDTO.setTotalPages(0);
            bookmarkDTO.setTotalElements(0L);
            bookmarkDTO.setElements(Arrays.asList());
            return bookmarkDTO;
        }
        return createBookmarkDTO(bookmarks);
    }

    @Override
    public Boolean checkBookmark(String postId, String userId) {
        Bookmark bookmark = this.bookmarkRepository.findByPostIdAndUserId(Long.valueOf(postId), Long.valueOf(userId));
        return Objects.isNull(bookmark) ? FALSE : TRUE;
    }

    private BookmarkDTO createBookmarkDTO(Page<Bookmark> bookmarks) {
        BookmarkDTO bookmarkDTO = new BookmarkDTO();
        bookmarkDTO.setTotalElements(bookmarks.getTotalElements());
        bookmarkDTO.setTotalPages(bookmarks.getTotalPages());
        bookmarkDTO.setElements(bookmarks.getContent()
                                         .stream()
                                         .map(x -> {
                                             BookmarkDTO.Element element = new BookmarkDTO.Element();
                                             element.setPostId(x.getPostId());
                                             element.setUserId(x.getUserId());
                                             element.setCreatedDate(x.getCreatedDateTime()
                                                                     .format(DateTimeFormatter.ofPattern(DATE_FORMAT)));
                                             element.setUpdatedDate(x.getLastModifiedDateTime()
                                                                     .format(DateTimeFormatter.ofPattern(DATE_FORMAT)));
                                             return element;
                                         })
                                         .collect(Collectors.toList()));
        return bookmarkDTO;
    }
}
