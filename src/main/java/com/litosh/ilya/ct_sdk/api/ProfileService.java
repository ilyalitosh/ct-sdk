package com.litosh.ilya.ct_sdk.api;

import com.litosh.ilya.ct_sdk.callbacks.OnGettingUserProfileCallback;
import com.litosh.ilya.ct_sdk.models.BaseCookie;
import com.litosh.ilya.ct_sdk.models.profile.User;
import com.litosh.ilya.ct_sdk.models.profile.UserBuilder;
import com.litosh.ilya.ct_sdk.models.profile.UserParser;
import com.litosh.ilya.ct_sdk.models.profile.Wall;
import com.litosh.ilya.ct_sdk.models.profile.WallParser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * ProfileService
 *
 * @author Ilya Litosh
 */
public class ProfileService {

    private BaseCookie mCookie;

    public ProfileService(BaseCookie cookie) {
        mCookie = cookie;
    }

    /**
     * Возвращает через колбэк информацию
     * о профиле пользователя
     *
     * @param userId id юзера
     * @param onGettingUserProfileCallback колбэк на получение данных
     */
    public void getUserProfile(String userId,
                               final OnGettingUserProfileCallback onGettingUserProfileCallback) {
        CtApi.getProfileApi()
                .getUser(mCookie, userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            Document document = Jsoup.parse(responseBody.string());
                            onGettingUserProfileCallback.onUser(
                                    getParsedUserData(document), getParsedWallData(document));
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

    private User getParsedUserData(Document document) {
        try {
            UserBuilder userBuilder = new UserBuilder();
            UserParser userParser = new UserParser(document);
            return userBuilder.profileName(userParser.getProfileName())
                    .city(userParser.getCity())
                    .country(userParser.getCountry())
                    .sex(userParser.getSex())
                    .wca(userParser.getWca())
                    .activity(userParser.getActivity())
                    .friendsCount(userParser.getFriendsCount())
                    .urlAvatar(userParser.getUrlAvatar())
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private Wall getParsedWallData(Document document) {
        try {
            WallParser wallParser = new WallParser(document);
            return wallParser.getWall();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
