package com.litosh.ilya.ct_sdk.callbacks;

/**
 * OnLikePostCallback
 *
 * колбэк для выполнения лайка поста
 * или комментария к посту
 *
 * @author Ilya Litosh
 */
public interface OnLikePostCallback {

    /**
     * Вызывается при успешном
     * выполнении лайка
     *
     */
    void onLike();

    /**
     * Вызывается если попытка лайка
     * оказалась неудачной
     *
     * @param t
     */
    void onError(Throwable t);

}
