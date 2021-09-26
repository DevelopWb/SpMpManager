package org.easydarwin.easyplayer.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Author:wang_sir
 * Time:2019/12/26 21:47
 * Description:This is VideoAddrBean
 */
public class VideoAddrBean implements Parcelable {

    private  String  Name ;
    private  String  addTime ;
    private  String Ip;
    private  String RegCode;
    private  String url;
    private  String  Protocal ;
    private  boolean  sendPakage ;
    private int  urlType ;//0代表rtmp  1代表rtsp

    public String getAddTime() {
        return addTime == null ? "" : addTime;
    }

    public int getUrlType() {
        return urlType;
    }

    public void setUrlType(int urlType) {
        this.urlType = urlType;
    }

    public String getUrl() {
        return url == null ? "" : url;
    }

    public void setUrl(String url) {
        this.url = url == null ? "" : url;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime == null ? "" : addTime;
    }

    public String getName() {
        return Name == null ? "" : Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getIp() {
        return Ip == null ? "" : Ip;
    }

    public void setIp(String ip) {
        Ip = ip;
    }

    public String getRegCode() {
        return RegCode == null ? "" : RegCode;
    }

    public void setRegCode(String regCode) {
        RegCode = regCode;
    }

    public String getProtocal() {
        return Protocal == null ? "" : Protocal;
    }

    public void setProtocal(String protocal) {
        Protocal = protocal;
    }

    public boolean isSendPakage() {
        return sendPakage;
    }

    public void setSendPakage(boolean sendPakage) {
        this.sendPakage = sendPakage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.Name);
        dest.writeString(this.addTime);
        dest.writeString(this.Ip);
        dest.writeString(this.RegCode);
        dest.writeString(this.url);
        dest.writeString(this.Protocal);
        dest.writeByte(this.sendPakage ? (byte) 1 : (byte) 0);
        dest.writeInt(this.urlType);
    }

    public VideoAddrBean() {
    }

    protected VideoAddrBean(Parcel in) {
        this.Name = in.readString();
        this.addTime = in.readString();
        this.Ip = in.readString();
        this.RegCode = in.readString();
        this.url = in.readString();
        this.Protocal = in.readString();
        this.sendPakage = in.readByte() != 0;
        this.urlType = in.readInt();
    }

    public static final Creator<VideoAddrBean> CREATOR = new Creator<VideoAddrBean>() {
        @Override
        public VideoAddrBean createFromParcel(Parcel source) {
            return new VideoAddrBean(source);
        }

        @Override
        public VideoAddrBean[] newArray(int size) {
            return new VideoAddrBean[size];
        }
    };
}
