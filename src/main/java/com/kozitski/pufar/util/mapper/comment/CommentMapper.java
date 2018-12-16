package com.kozitski.pufar.util.mapper.comment;

import com.kozitski.pufar.entity.comment.NotificationComment;

import java.sql.ResultSet;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.util.ArrayList;

public class CommentMapper {
    private static final String COMMENT_ID = "c.comment_id";
    private static final String USER_LOGIN = "u.login";
    private static final String COMMENT_MESSAGE = "c.comment";

    public static ArrayList<NotificationComment> mapComments(ResultSet resultSet) throws SQLDataException {
        ArrayList<NotificationComment> result = new ArrayList<>();

        try {
            while (resultSet.next()){
                NotificationComment comment = new NotificationComment();

                comment.setCommentId(resultSet.getLong(COMMENT_ID));
                comment.setSenderLogin(resultSet.getString(USER_LOGIN));
                comment.setComment(resultSet.getString(COMMENT_MESSAGE));

                result.add(comment);
            }
        }
        catch (SQLException e){
            throw new SQLDataException("Comments were not founded", e);
        }

        return result;
    }

}
