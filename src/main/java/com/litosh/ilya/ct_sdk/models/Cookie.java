package com.litosh.ilya.ct_sdk.models;


/**
 * Куки с php сессией и данными для авторизации и последующим пользованием сервиса
 *
 * Created by ilya_ on 16.06.2018.
 */

public class Cookie extends BaseCookie {

    private String cbtl;
    private String cbtp;
    private String lang;
    private String noprev;
    private String night;
    private String phpSessId;

    public void setCbtl(String cbtl) {
        this.cbtl = cbtl;
    }

    public void setCbtp(String cbtp) {
        this.cbtp = cbtp;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public void setNoprev(String noprev) {
        this.noprev = noprev;
    }

    public void setNight(String night) {
        this.night = night;
    }

    public void setPhpSessId(String phpSessId) {
        this.phpSessId = phpSessId;
    }

    @Override
    public String getCbtl() {
        return cbtl;
    }

    @Override
    public String getCbtp() {
        return cbtp;
    }

    @Override
    public String getPHPSESSID() {
        return phpSessId;
    }

    @Override
    public String getLang() {
        return lang;
    }

    @Override
    public String getNight() {
        return night;
    }

    @Override
    public String getNoprev() {
        return noprev;
    }

}
