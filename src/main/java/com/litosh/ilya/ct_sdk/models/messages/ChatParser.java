package com.litosh.ilya.ct_sdk.models.messages;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * ChatParser для парсинга документа под Chat
 *
 * Created by ilya_ on 24.06.2018.
 */

public class ChatParser {

    private Elements mElements;

    public ChatParser(Document document) {
        mElements = document.getElementById("mlusers").children();
    }

    public String getChatId(int position) {
        return mElements
                .get(position)
                .attr("id")
                .split("mlusr")[1];
    }

    public String getUrlChatImage(int position) {
        return "https://cubingtime.com/" + mElements
                .get(position)
                .getElementsByTag("a")
                .get(0)
                .getElementsByTag("img")
                .get(0)
                .attr("src");
    }

    public String getChatName(int position) {
        return mElements
                .get(position)
                .getElementsByTag("a")
                .get(1)
                .children()
                .get(0)
                .children()
                .get(0)
                .text();
    }

    public String getChatTime(int position) {
        return mElements
                .get(position)
                .getElementsByTag("a")
                .get(1)
                .children()
                .get(0)
                .children()
                .get(1)
                .text();
    }

    public String getChatLastMessage(int position) {
        return mElements
                .get(position)
                .getElementsByTag("a")
                .get(1)
                .children()
                .get(0)
                .children()
                .get(2)
                .children()
                .get(0)
                .text();
    }

    public boolean isContainsNewMessage(int position) {
        String attrStyle = mElements
                .get(position)
                .getElementsByTag("a")
                .get(1)
                .children()
                .get(0)
                .children()
                .get(2)
                .children()
                .get(1)
                .attr("style");
        return attrStyle.contains("block");
    }

}
