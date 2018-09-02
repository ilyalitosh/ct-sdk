package com.litosh.ilya.ct_sdk.models.profile;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

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
            note.setUserName(getUserName(mNotes.get(i)));
            note.setUserId(getUserId(mNotes.get(i)));
            note.setDate(getDate(mNotes.get(i)));
            note.setLikesNumber(getLikesNumber(mNotes.get(i)));
            note.setText(getText(mNotes.get(i)));
            note.setUrlUserAvatar(getUrlUserAvatar(mNotes.get(i)));
            note.setUserOnline(isUserOnline(mNotes.get(i)));
            mWall.add(note);
        }
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

}
