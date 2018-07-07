package com.litosh.ilya.ct_sdk.api;

import com.litosh.ilya.ct_sdk.callbacks.OnGettingChatsCallback;
import com.litosh.ilya.ct_sdk.callbacks.OnGettingMessagesInChatCallback;
import com.litosh.ilya.ct_sdk.callbacks.OnMessageSendingCallback;
import com.litosh.ilya.ct_sdk.callbacks.OnNewMessageListener;
import com.litosh.ilya.ct_sdk.callbacks.OnNewMessagesInChatsListListener;
import com.litosh.ilya.ct_sdk.callbacks.OnUserAuthorizateCallback;
import com.litosh.ilya.ct_sdk.callbacks.OnGettingUserCallback;
import com.litosh.ilya.ct_sdk.models.BaseCookie;
import com.litosh.ilya.ct_sdk.models.Cookie;
import com.litosh.ilya.ct_sdk.models.User;
import com.litosh.ilya.ct_sdk.models.UserBuilder;
import com.litosh.ilya.ct_sdk.models.UserParser;
import com.litosh.ilya.ct_sdk.models.messages.Chat;
import com.litosh.ilya.ct_sdk.models.messages.ChatBuilder;
import com.litosh.ilya.ct_sdk.models.messages.ChatParser;
import com.litosh.ilya.ct_sdk.models.messages.Message;
import com.litosh.ilya.ct_sdk.models.messages.MessageBuilder;
import com.litosh.ilya.ct_sdk.models.messages.MessageParser;
import com.litosh.ilya.ct_sdk.models.messages.NewMessageParser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Response;

/**
 * ApiService
 *
 * Created by ilya_ on 17.06.2018.
 */

public class ApiService {

    public static void init() {
        CtApi.init();
    }

    public static void authorizate(final String email,
                                   String pass,
                                   final OnUserAuthorizateCallback onUserAuthorizateCallback) {
        CtApi.getProfileApi()
                .authorizate(email, pass, "on", "on")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<String>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Response<String> stringResponse) {
                        if (stringResponse.body().length() > 4) {
                            final Cookie cookie = new Cookie();
                            cookie.setCbtl(email);
                            cookie.setCbtp(getPassHash(stringResponse));
                            cookie.setLang("ru");
                            cookie.setNight("0");
                            cookie.setNoprev("1");
                            cookie.setPhpSessId(getPhpSessId(stringResponse));
                            final String userId = getUserId(stringResponse);
                            CtApi.getProfileApi()
                                    .activatePhpSession(cookie, userId)
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new Observer<ResponseBody>() {
                                        @Override
                                        public void onSubscribe(Disposable d) {

                                        }

                                        @Override
                                        public void onNext(ResponseBody responseBody) {
                                            onUserAuthorizateCallback.onSuccess(cookie, userId);
                                        }

                                        @Override
                                        public void onError(Throwable e) {
                                            onUserAuthorizateCallback.onError(e, e.toString());
                                        }

                                        @Override
                                        public void onComplete() {

                                        }
                                    });
                        } else {
                            onUserAuthorizateCallback.onError(
                                    new Throwable(),
                                    "Server-error, code: " + stringResponse.body());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        onUserAuthorizateCallback.onError(e, e.toString());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private static Disposable listenNewMessagesInChatListDisposable;

    public static void listenNewMessagesInChatsList(
            final BaseCookie cookie,
            final OnNewMessagesInChatsListListener onNewMessagesInChatsListListener) {
        listenNewMessagesInChatListDisposable = Observable.interval(3, TimeUnit.SECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(Schedulers.io())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        System.out.println("piska piska");
                        CtApi.getMessagesApi()
                                .listenMessages(cookie, "")
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Observer<ResponseBody>() {
                                    @Override
                                    public void onSubscribe(Disposable d) {

                                    }

                                    @Override
                                    public void onNext(ResponseBody responseBody) {
                                        try {
                                            String s = responseBody.string();
                                            if (s.length() > 4) {
                                                onNewMessagesInChatsListListener.onNewMessage(
                                                        getParsedChatAfterNewMessage(s));
                                            }
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }

                                    @Override
                                    public void onError(Throwable e) {

                                    }

                                    @Override
                                    public void onComplete() {

                                    }
                                });
                    }
                });
    }

    public static void stopListenNewMessagesInChatsList() {
        if (listenNewMessagesInChatListDisposable != null) {
            listenNewMessagesInChatListDisposable.dispose();
        }
    }

    private static Chat getParsedChatAfterNewMessage(String s) {

        String[] array = s.split("mailblock\\(")[1].split(",");
        String chatMessage = array[1].replace("'", "");
        String chatId = array[2];
        String chatName = array[3];
        Chat chat = new Chat();
        chat.setChatId(chatId);
        chat.setChatLastMessage(chatMessage);
        chat.setChatName(chatName);
        chat.setContainsNewMessage(true);

        return chat;
    }

    public static void listenNewMessages(final BaseCookie cookie,
                                         final OnNewMessageListener onNewMessageListener) {
        Observable.interval(3, TimeUnit.SECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(Schedulers.io())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Long aLong) {
                        CtApi.getMessagesApi()
                                .listenMessages(cookie, "")
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Observer<ResponseBody>() {
                                    @Override
                                    public void onSubscribe(Disposable d) {

                                    }

                                    @Override
                                    public void onNext(ResponseBody responseBody) {
                                        String s = null;
                                        try {
                                            s = responseBody.string();
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                        if (s.length() > 4) {
                                            onNewMessageListener.onNewMessage(
                                                    getParsedNewMessage(s));
                                        }
                                    }

                                    @Override
                                    public void onError(Throwable e) {

                                    }

                                    @Override
                                    public void onComplete() {

                                    }
                                });
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private static Message getParsedNewMessage(String stringResponse) {
        NewMessageParser newMessageParser =
                new NewMessageParser(Jsoup.parse(stringResponse.split("\\|")[1]));
        MessageBuilder messageBuilder = new MessageBuilder();
        messageBuilder.userName(newMessageParser.getUserName());
        messageBuilder.messageTime(newMessageParser.getMessageTime());
        messageBuilder.messageText(newMessageParser.getMessageText());
        return messageBuilder.build();
    }


    private static String getPhpSessId(Response<String> response) {
        List<String> headers = response.headers().values("Set-Cookie");
        return headers.get(0).split("PHPSESSID=")[1].split(";")[0];
    }

    private static String getPassHash(Response<String> response) {
        return response.body().split("\\|")[0];
    }

    private static String getUserId(Response<String> response) {
        return "id" + response.body().split("\\|")[1];
    }

    public static void getUser(BaseCookie cookie,
                               String userId,
                               final OnGettingUserCallback onGettingUserCallback) {
        CtApi.getProfileApi()
                .getUser(cookie, userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        onGettingUserCallback.onUser(getParsedUserData(responseBody));
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private static User getParsedUserData(ResponseBody responseBody) {
        try {
            UserBuilder userBuilder = new UserBuilder();
            UserParser userParser = new UserParser(Jsoup.parse(responseBody.string()));
            return userBuilder.profileName(userParser.getProfileName())
                    .city(userParser.getCity())
                    .country(userParser.getCountry())
                    .sex(userParser.getSex())
                    .wca(userParser.getWca())
                    .activity(userParser.getActivity())
                    .friendsCount(userParser.getFriendsCount())
                    .urlAvatar(userParser.getUrlAvatar())
                    .build();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void getChats(BaseCookie cookie,
                                final OnGettingChatsCallback onGettingChatsCallback) {
        CtApi.getMessagesApi()
                .getChats(cookie)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        onGettingChatsCallback.onChats(getParsedChatsData(responseBody));
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private static LinkedList<Chat> getParsedChatsData(ResponseBody responseBody) {
        LinkedList<Chat> chatLinkedList = new LinkedList<>();
        Document document = null;
        try {
            document = Jsoup.parse(responseBody.string());
        } catch (IOException e) {
            e.printStackTrace();
        }
        ChatParser chatParser = new ChatParser(document);
        for (int i = 0; i < getChatsCount(document); i++) {
            chatLinkedList.add(new ChatBuilder()
                    .chatId(chatParser.getChatId(i))
                    .chatName(chatParser.getChatName(i))
                    .chatTime(chatParser.getChatTime(i))
                    .urlChatImage(chatParser.getUrlChatImage(i))
                    .chatLastMessage(chatParser.getChatLastMessage(i))
                    .isContainsNewMessage(chatParser.isContainsNewMessage(i))
                    .build());
        }
        return chatLinkedList;
    }

    private static int getChatsCount(Document document) {
        return document.body().getElementById("mlusers").children().size();
    }

    public static void getMessagesInChat(
            String chatId,
            BaseCookie cookie,
            final OnGettingMessagesInChatCallback onGettingMessagesInChatCallback) {
        CtApi.getMessagesApi()
                .getMessagesInChat(cookie, chatId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        onGettingMessagesInChatCallback.onMessages(
                                getParsedMessagesData(responseBody));
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private static LinkedList<Message> getParsedMessagesData(ResponseBody responseBody) {
        LinkedList<Message> messages = new LinkedList<>();
        Document document = null;
        try {
            document = Jsoup.parse(responseBody.string());
        } catch (IOException e) {
            e.printStackTrace();
        }
        MessageParser messageParser = new MessageParser(document);
        for (int i = 0; i < getMessagesCount(document); i++) {
            messages.add(new MessageBuilder()
                    .userName(messageParser.getUserName(i))
                    .messageTime(messageParser.getMessageTime(i))
                    .messageText(messageParser.getMessageText(i))
                    .build());
        }
        return messages;
    }

    private static int getMessagesCount(Document document) {
        return document.body()
                .getElementById("mlchat")
                .getElementsByClass("mlchatblock")
                .size();
    }

    public static void sendMessage(BaseCookie cookie,
                                   String message,
                                   String userId,
                                   final OnMessageSendingCallback onMessageSendingCallback) {

        CtApi.getMessagesApi()
                .sendMessage(cookie, userId, message)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            String responseString = responseBody.string();
                            if (responseString.length() > 5) {
                                onMessageSendingCallback
                                        .onSuccess(getParsedSendingMessage(responseString));
                            } else {
                                onMessageSendingCallback.onError(responseString);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        onMessageSendingCallback.onError(e.toString());
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    private static Message getParsedSendingMessage(String responseString) {
        Message message = new Message();
        Document document = Jsoup.parse(responseString);
        Elements elements = document.getElementsByClass("mlchatblock").get(0).children();
        message.setUserName(elements.get(0).text());
        message.setMessageTime(elements.get(1).text());
        message.setMessageText(elements.get(2).text());

        return message;
    }

}
