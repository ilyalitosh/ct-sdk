package com.litosh.ilya.ct_sdk.models.messages;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * MessageParser для парсинга ответа с сервера в Message
 *
 * Created by ilya_ on 25.06.2018.
 */

public class MessageParser {

    private Elements mElementsMessages;

    public MessageParser(Document document) {
        mElementsMessages = document.getElementById("mlchat")
                .getElementsByClass("mlchatblock");
    }

    public String getUserName(int position) {
        return mElementsMessages.get(position)
                .children()
                .get(0)
                .text();
    }

    public String getMessageTime(int position) {
        return mElementsMessages.get(position)
                .children()
                .get(1)
                .text();
    }

    public String getMessageText(int position) {
        return mElementsMessages.get(position)
                .children()
                .get(2)
                .text();
    }

}
