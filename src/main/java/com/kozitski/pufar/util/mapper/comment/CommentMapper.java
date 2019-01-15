package com.kozitski.pufar.util.mapper.comment;

import com.kozitski.pufar.entity.comment.NotificationComment;

import java.sql.ResultSet;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * The Class CommentMapper.
 * Map ResultSet into NotificationComment
 */
public class CommentMapper {
    
    /** The Constant COMMENT_ID. */
    private static final String COMMENT_ID = "c.comment_id";
    
    /** The Constant USER_LOGIN. */
    private static final String USER_LOGIN = "u.login";
    
    /** The Constant COMMENT_MESSAGE. */
    private static final String COMMENT_MESSAGE = "c.comment";
    
    /** The Constant COMMENT_DATE. */
    private static final String COMMENT_DATE = "c.date";

    /**
     * Instantiates a new comment mapper.
     */
    private CommentMapper() { }

    /**
     * Map comments.
     *
     * @param resultSet the result set
     * @return the list
     * @throws SQLDataException the SQL data exception
     */
    public static List<NotificationComment> mapComments(ResultSet resultSet) throws SQLDataException {
        ArrayList<NotificationComment> result = new ArrayList<>();

        try {
            while (resultSet.next()) {
                NotificationComment comment = new NotificationComment();

                comment.setCommentId(resultSet.getLong(COMMENT_ID));
                comment.setSenderLogin(resultSet.getString(USER_LOGIN));
                comment.setComment(resultSet.getString(COMMENT_MESSAGE));
                comment.setComment(resultSet.getString(COMMENT_MESSAGE));
                comment.setDate(new Date(resultSet.getLong(COMMENT_DATE)));

                result.add(comment);
            }
        } catch (SQLException e) {
            throw new SQLDataException("Comments were not founded", e);
        }

        return result;
    }

}
