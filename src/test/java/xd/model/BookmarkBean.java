package xd.model;

import java.sql.Timestamp;

/**
 * <p>
 *     Data Access Object (DAO) for storing bookmark object from JDBC driver.
 * </p>
 *
 * @author      jUqItEr (Ki-seok Kang)
 * @version     1.0.0
 * */
public class BookmarkBean {
    private int bookmarkId;
    private int feedId;
    private String userId;
    private Timestamp createdAt;

    public BookmarkBean() {
    }

    public BookmarkBean(int bookmarkId, int feedId, String userId, Timestamp createdAt) {
        this.bookmarkId = bookmarkId;
        this.feedId = feedId;
        this.userId = userId;
        this.createdAt = createdAt;
    }

    public int getBookmarkId() {
        return bookmarkId;
    }

    public void setBookmarkId(int bookmarkId) {
        this.bookmarkId = bookmarkId;
    }

    public int getFeedId() {
        return feedId;
    }

    public void setFeedId(int feedId) {
        this.feedId = feedId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}
