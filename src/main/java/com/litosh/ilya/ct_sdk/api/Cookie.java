package com.litosh.ilya.ct_sdk.api;


/**
 * Куки с php сессией и данными для авторизации и последующим пользованием сервиса
 *
 * Created by ilya_ on 16.06.2018.
 */

public final class Cookie {

    private static String cbtl;
    private static String cbtp;
    private static String lang;
    private static String noprev;
    private static String night;
    private static String phpSessId;

    public String getCbtl() {
        return cbtl;
    }

    public void setCbtl(String cbtl) {
        Cookie.cbtl = cbtl;
    }

    public String getCbtp() {
        return cbtp;
    }

    public void setCbtp(String cbtp) {
        Cookie.cbtp = cbtp;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        Cookie.lang = lang;
    }

    public String getNoprev() {
        return noprev;
    }

    public void setNoprev(String noprev) {
        Cookie.noprev = noprev;
    }

    public String getNight() {
        return night;
    }

    public void setNight(String night) {
        Cookie.night = night;
    }

    public String getPHPSESSID() {
        return phpSessId;
    }

    public void setPHPSESSID(String phpSessId) {
        Cookie.phpSessId = phpSessId;
    }

    @Override
    public String toString() {
        return "noprev=" + getNoprev() + "; " +
                "night=" + getNight() + "; " +
                "lang=" + getLang() + "; " +
                "PHPSESSID=" + getPHPSESSID() + "; " +
                "cbtl=" + getCbtl() + "; " +
                "cbtp=" + getCbtp();
    }

}
