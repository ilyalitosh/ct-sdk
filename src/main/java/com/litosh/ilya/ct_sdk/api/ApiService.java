package com.litosh.ilya.ct_sdk.api;

import com.litosh.ilya.ct_sdk.callbacks.OnGettingChatsCallback;
import com.litosh.ilya.ct_sdk.callbacks.OnGettingMessagesInChatCallback;
import com.litosh.ilya.ct_sdk.callbacks.OnLikePostCallback;
import com.litosh.ilya.ct_sdk.callbacks.OnMessageSendingCallback;
import com.litosh.ilya.ct_sdk.callbacks.OnNewMessageListener;
import com.litosh.ilya.ct_sdk.callbacks.OnNewMessagesInChatsListListener;
import com.litosh.ilya.ct_sdk.callbacks.OnUserAuthorizateCallback;
import com.litosh.ilya.ct_sdk.callbacks.OnGettingUserProfileCallback;
import com.litosh.ilya.ct_sdk.models.BaseCookie;
import com.litosh.ilya.ct_sdk.models.Cookie;
import com.litosh.ilya.ct_sdk.models.profile.User;
import com.litosh.ilya.ct_sdk.models.profile.UserBuilder;
import com.litosh.ilya.ct_sdk.models.profile.UserParser;
import com.litosh.ilya.ct_sdk.models.messages.Chat;
import com.litosh.ilya.ct_sdk.models.messages.ChatBuilder;
import com.litosh.ilya.ct_sdk.models.messages.ChatParser;
import com.litosh.ilya.ct_sdk.models.messages.Message;
import com.litosh.ilya.ct_sdk.models.messages.MessageBuilder;
import com.litosh.ilya.ct_sdk.models.messages.MessageParser;
import com.litosh.ilya.ct_sdk.models.messages.NewMessageParser;
import com.litosh.ilya.ct_sdk.models.profile.Wall;
import com.litosh.ilya.ct_sdk.models.profile.WallParser;

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
 * @author Ilya Litosh
 */
public class ApiService {

    private static final String LOG_TAG = "ApiService";

    public static void init() {
        CtApi.init();
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

    public static void likePost(BaseCookie cookie,
                                String postId,
                                String type,
                                String act,
                                final OnLikePostCallback onLikePostCallback) {
        CtApi.getProfileApi()
                .likePost(cookie, postId, type, act)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<Void>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Response<Void> response) {
                        if (response.code() == 200) {
                            onLikePostCallback.onLike();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        onLikePostCallback.onError(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

}
