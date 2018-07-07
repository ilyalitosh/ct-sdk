package com.litosh.ilya.ct_sdk.models.messages;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * NewMessageParser парсер новых
 * сообщений в приватном чате
 *
 */
public class NewMessageParser {

    private Elements mMessageElements;

    public NewMessageParser(Document document) {
        mMessageElements = document.getElementsByClass("mlchatblock").get(0).children();
    }

    public String getUserName() {
        return mMessageElements.get(0).text();
    }

    public String getMessageTime() {
        return mMessageElements.get(1).text();
    }

    public String getMessageText() {
        return mMessageElements.get(2).text();
    }

}
