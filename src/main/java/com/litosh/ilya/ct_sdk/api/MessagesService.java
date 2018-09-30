package com.litosh.ilya.ct_sdk.api;

import android.util.Log;

import com.litosh.ilya.ct_sdk.callbacks.OnNewMessageListener;
import com.litosh.ilya.ct_sdk.callbacks.OnNewMessagesInChatsListListener;
import com.litosh.ilya.ct_sdk.models.BaseCookie;
import com.litosh.ilya.ct_sdk.models.messages.Chat;
import com.litosh.ilya.ct_sdk.models.messages.Message;
import com.litosh.ilya.ct_sdk.models.messages.MessageBuilder;
import com.litosh.ilya.ct_sdk.models.messages.NewMessageParser;

import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * MessagesService
 * Класс с слушателями сообщений из чата
 * и из списка чатов
 *
 * @author Ilya Litosh
 */
public class MessagesService {

    private BaseCookie mCookie;
    private static final String LOG_TAG = "MessagesService";

    public MessagesService(BaseCookie cookie) {
        mCookie = cookie;
    }

    private Disposable listenNewMessagesInChatListDisposable;
    public void listenNewMessagesInChatsList(
            final OnNewMessagesInChatsListListener onNewMessagesInChatsListListener) {
        listenNewMessagesInChatListDisposable = Observable.interval(3, TimeUnit.SECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(Schedulers.io())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        System.out.println("Слушаю новые сообщения в списке чатов");
                        CtApi.getMessagesApi()
                                .listenMessages(mCookie, "")
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

    public void stopListenNewMessagesInChatsList() {
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

    private static Disposable listenNewMessagesInChatDisposable;
    public void listenNewMessagesInChat(final String userId,
                                        final OnNewMessageListener onNewMessageListener) {
        listenNewMessagesInChatDisposable = Observable.interval(3, TimeUnit.SECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(Schedulers.io())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        Log.i(LOG_TAG, "Слушаю новые сообщения в чате");
                        CtApi.getMessagesApi()
                                .listenMessages(mCookie, userId)
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
                                            String messageBody = s.split("\\|")[1];
                                            if (messageBody != null
                                                    && !messageBody.equals("")
                                                    && messageBody.length() > 1) {
                                                onNewMessageListener.onNewMessage(
                                                        getParsedNewMessage(messageBody));
                                            }
                                        }
                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        Log.i(LOG_TAG, e.toString());
                                    }

                                    @Override
                                    public void onComplete() {
                                    }
                                });
                    }
                });
    }

    public void stopListenNewMessagesInChat() {
        if (listenNewMessagesInChatDisposable != null) {
            listenNewMessagesInChatDisposable.dispose();
        }
    }

    private Message getParsedNewMessage(String messageBody) {
        NewMessageParser newMessageParser =
                new NewMessageParser(Jsoup.parse(messageBody));
        MessageBuilder messageBuilder = new MessageBuilder();
        messageBuilder.userName(newMessageParser.getUserName());
        messageBuilder.messageTime(newMessageParser.getMessageTime());
        messageBuilder.messageText(newMessageParser.getMessageText());
        return messageBuilder.build();
    }

}
