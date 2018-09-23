package com.litosh.ilya.ct_sdk.models.profile;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * WallParser парсер записей стены
 *
 * @author Ilya Litosh
 */
public class WallParser {

    private Document mDocument;
    private Elements mNotes;
    private int mNotesNumber;
    private Wall mWall;

    public WallParser(Document document) {
        mDocument = document;
        mWall = new Wall();
        mNotesNumber = getNotesNumber();
    }

    private int getNotesNumber() {
        mNotes = mDocument.getElementById("wall").getElementsByAttributeValue("class", "block wallcont");
        if (mDocument.getElementById("wall").children().last().attr("id").equals("morepost")) {
            mWall.setContainsMorePost(true);
        } else {
            mWall.setContainsMorePost(false);
        }
        return mNotes.size();
    }

    /**
     * Возвращает сущность Wall с записями
     *
     */
    public Wall getWall() {
        parse();
        return mWall;
    }

    private void parse() {
        for (int i = 0; i < mNotesNumber; i++) {
            Note note = new Note();
            note.setNoteId(getNoteId(mNotes.get(i)));
            note.setUserName(getUserName(mNotes.get(i)));
            note.setUserId(getUserId(mNotes.get(i)));
            note.setDate(getDate(mNotes.get(i)));
            note.setLikesNumber(getLikesNumber(mNotes.get(i)));
            note.setText(getText(mNotes.get(i)));
            note.setUrlUserAvatar(getUrlUserAvatar(mNotes.get(i)));
            note.setUserOnline(isUserOnline(mNotes.get(i)));
            note.setCommentsNumber(getCommentsNumber(mNotes.get(i)));
            note.setLikedMe(isLikedMe(mNotes.get(i), note));
            note.setComments(getComments(mNotes.get(i)));
            mWall.add(note);
        }
    }

    private long getNoteId(Element element) {
        return Long.valueOf(element.attr("id").split("wallpost")[1]);
    }

    private String getUserName(Element element) {
        return element.children()
                .get(0)
                .children()
                .get(1)
                .getElementsByClass("wallname")
                .get(0)
                .text();
    }

    private String getUserId(Element element) {
        return element.children()
                .get(0)
                .children()
                .get(1)
                .getElementsByClass("wallname")
                .get(0)
                .attr("href")
                .split("/id")[1];
    }

    private String getDate(Element element) {
        return element.children()
                .get(0)
                .children()
                .get(1)
                .getElementsByClass("walldate")
                .get(0)
                .text();
    }

    private int getLikesNumber(Element element) {
        Elements wallLikesNumberElements = element.children()
                .get(0)
                .children()
                .get(1)
                .getElementsByClass("walllikenum");
        if (wallLikesNumberElements.size() == 0) {
            return 0;
        } else {
            return Integer.valueOf(wallLikesNumberElements.get(0).text());
        }
    }

    private String getText(Element element) {
        return element.children()
                .get(0)
                .children()
                .get(1)
                .getElementsByClass("walltext")
                .get(0)
                .text();
    }

    private String getUrlUserAvatar(Element element) {
        return "https://cubingtime.com/" + element.children()
                .get(0)
                .children()
                .get(0)
                .getElementsByTag("img")
                .get(0)
                .attr("src");
    }

    private boolean isUserOnline(Element element) {
        String className = element.children()
                .get(0)
                .children()
                .get(0)
                .children()
                .get(0)
                .attr("class");
        return className.contains("online_site");
    }

    private int getCommentsNumber(Element element) {
        Elements elements = element.children();
        if (elements.size() == 1) {
            return 0;
        } else {
            return elements.get(1)
                    .children()
                    .size() - 1;
        }
    }

    private boolean isLikedMe(Element element, Note note) {
        String likeUrl = element.getElementById("likepost" + note.getNoteId())
                .getElementsByTag("img").get(0)
                .attr("src");
        if (likeUrl.contains("act")) {
            return true;
        } else {
            return false;
        }
    }

    private List<Comment> getComments(Element element) {
        List<Comment> comments = new ArrayList<>();
        Elements elements = element.getElementsByClass("wallcommcont");
        for (Element commentElement: elements) {
            CommentParser commentParser = new CommentParser(commentElement);
            comments.add(commentParser.getComment());
        }

        return comments;
    }

}
