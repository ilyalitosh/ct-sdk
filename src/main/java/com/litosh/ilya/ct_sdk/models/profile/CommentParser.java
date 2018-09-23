package com.litosh.ilya.ct_sdk.models.profile;

import android.net.Uri;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * CommentParser
 * парсер комментария из Element
 *
 * @author Ilya Litosh
 */
public class CommentParser {

    private Element mCommentElement;
    private Comment mComment;

    public CommentParser(Element commentElement) {
        mCommentElement = commentElement;
        mComment = new Comment();
    }

    /**
     * Возвращает собранный Comment
     * из Element
     *
     */
    public Comment getComment() {
        mComment.setCommentId(getCommentId());
        mComment.setCommentText(getCommentText());
        mComment.setDate(getDate());
        mComment.setIsUserOnline(isUserOnline());
        mComment.setLikedMe(isLikedMe());
        mComment.setLikesNumber(getLikesNumber());
        mComment.setUrlUserAvatar(getUrlUserAvatar());
        mComment.setUserId(getUserId());
        mComment.setUserName(getUserName());

        return mComment;
    }

    private long getCommentId() {
        return Long.valueOf(mCommentElement.attr("id").split("wallcomm")[1]);
    }

    private String getCommentText() {
        return mCommentElement.getElementById("walltextcomm" + mComment.getCommentId()).text();
    }

    private String getDate() {
        return mCommentElement.getElementsByClass("walldate").get(0).text();
    }

    private boolean isUserOnline() {
        String className = mCommentElement.children().get(0).children().get(0).attr("class");
        return className.contains("online_site");
    }

    private boolean isLikedMe() {
        String likeUrl = mCommentElement.getElementById("likecomm" + mComment.getCommentId())
                .getElementsByTag("img").get(0).attr("src");
        if (likeUrl.contains("act")) {
            return true;
        } else {
            return false;
        }
    }

    private int getLikesNumber() {
        Element likeNumberElement =
                mCommentElement.getElementById("likecommnum" + mComment.getCommentId());
        if (likeNumberElement == null) {
            return 0;
        } else {
            return Integer.valueOf(likeNumberElement.text());
        }
    }

    private String getUrlUserAvatar() {
        return "https://cubingtime.com/" + mCommentElement.children().get(0)
                .children().get(0)
                .getElementsByTag("img").get(0)
                .attr("src");
    }

    private String getUserId() {
        return mCommentElement.children().get(0).attr("href").split("/id")[1];
    }

    private String getUserName() {
        return mCommentElement.children().get(1).getElementsByClass("wallname").get(0).text();
    }

}
