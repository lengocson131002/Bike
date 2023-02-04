package com.swd.bike.dto.userPost.response;

import com.swd.bike.core.BaseResponseData;
import com.swd.bike.entity.Account;
import com.swd.bike.entity.Post;
import com.swd.bike.entity.Station;
import com.swd.bike.enums.PostStatus;
import com.swd.bike.enums.TripRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostResponse extends BaseResponseData {
    private Long id;
    private TripRole role;
    private String title;
    private String description;
    private LocalDateTime startTime;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private String authorId;
    private String authorName;
    private PostStatus status;
    private Long startStationId;
    private String startStation;
    private Long endStationId;
    private String endStation;

    public PostResponse(Post post) {
        assert post != null;

        this.id = post.getId();
        this.role = post.getRole();
        this.title = post.getTitle();
        this.status = post.getStatus();
        this.description = post.getDescription();
        this.startTime = post.getStartTime();
        this.createdAt = post.getCreatedAt();
        this.modifiedAt = post.getModifiedAt();

        Account account = post.getAuthor();
        if (account != null) {
            this.authorId = account.getId();
            this.authorName = account.getName();
        }

        Station sStation = post.getStartStation();
        if (sStation != null) {
            startStationId = sStation.getId();
            startStation = sStation.getName();
        }

        Station eStation = post.getEndStation();
        if (eStation != null) {
            endStationId = eStation.getId();
            endStation = eStation.getName();
        }
    }
}
