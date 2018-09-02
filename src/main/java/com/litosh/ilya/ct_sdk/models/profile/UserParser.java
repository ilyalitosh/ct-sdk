package com.litosh.ilya.ct_sdk.models.profile;

import org.jsoup.nodes.Document;

/**
 * UserParser для парсинга документа под User
 *
 * @author Ilya Litosh
 */

public class UserParser {

    private Document mDocument;

    public UserParser(Document document) {
        mDocument = document;
    }

    public String getProfileName() {
        return mDocument.body()
                .getElementById("prfinfname")
                .children()
                .get(0)
                .text();
    }

    public String getCountry() {
        return mDocument.body()
                .getElementById("prfstraninf")
                .children()
                .get(0)
                .getElementsByTag("tr")
                .get(0)
                .getElementsByTag("td")
                .get(1)
                .text();
    }

    public String getCity() {
        return mDocument.body()
                .getElementById("prfstraninf")
                .children()
                .get(0)
                .getElementsByTag("tr")
                .get(1)
                .getElementsByTag("td")
                .get(1)
                .text();
    }

    public String getSex() {
        return mDocument.body()
                .getElementById("prfstraninf")
                .children()
                .get(0)
                .getElementsByTag("tr")
                .get(2)
                .getElementsByTag("td")
                .get(1)
                .text();
    }

    public String getWca() {
        return mDocument.body()
                .getElementById("prfplace")
                .children()
                .get(1)
                .children()
                .get(0)
                .getElementsByTag("tr")
                .get(0)
                .getElementsByTag("td")
                .get(1)
                .text();
    }

    public String getActivity() {
        String className = mDocument
                .body()
                .getElementById("prfinfname")
                .children()
                .get(0)
                .attr("class");
        if (className.contains("online_site")) {
            return "online";
        } else {
            return "offline";
        }
    }

    public String getFriendsCount() {
        String friendsTitle = mDocument
                .body()
                .getElementById("prfdrugname")
                .text();
        String[] parsedfriendsTitle = friendsTitle.split("Друзья ");
        if (parsedfriendsTitle.length == 1) {
            parsedfriendsTitle = friendsTitle.split("Friends ");
        }
        return parsedfriendsTitle[1].split(" ")[0];
    }

    public String getUrlAvatar() {
        return "https://cubingtime.com/" + mDocument.body()
                .getElementById("prfimg")
                .children()
                .get(0)
                .attr("src");
    }

}
