package com.litosh.ilya.ct_sdk.models;

/**
 * Базовый куки-класс для простого создания другого
 * куки-класса с статическими полями и быстрого
 * использования его в дальнейшем
 *
 * Created by ilya_ on 18.06.2018.
 */

public abstract class BaseCookie {

    /**
     * Возвращает куки-логин
     */
    public abstract String getCbtl();

    /**
     * Возвращает куки-пароль (его md5-hash)
     */
    public abstract String getCbtp();

    /**
     * Возвращает id сессии
     */
    public abstract String getPhpSessId();

    /**
     * Возвращает язык
     */
    public abstract String getLang();

    /**
     * Возвращает режим ночной/дневной
     */
    public abstract String getNight();

    /**
     * Возвращает флаг вкл./выкл. превью сайта
     */
    public abstract String getNoprev();

    @Override
    public String toString() {
        return "noprev=" + getNoprev() + "; " +
                "night=" + getNight() + "; " +
                "lang=" + getLang() + "; " +
                "PHPSESSID=" + getPhpSessId() + "; " +
                "cbtl=" + getCbtl() + "; " +
                "cbtp=" + getCbtp();
    }

}
