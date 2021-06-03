package uni.fmi.masters.fireorganizer.model;

public class ImageModel {

    private String imageTimeStamp,  PicassoUrl;

    public ImageModel() {}
    public ImageModel (String date, String url) {
        this.imageTimeStamp = date;
        this.PicassoUrl = url;
    }

    public String getImageTimeStamp() {
        return imageTimeStamp;
    }

    public void setImageTimeStamp(String imageTimeStamp) {
        this.imageTimeStamp = imageTimeStamp;
    }

    public String getPicassoUrl() {
        return PicassoUrl;
    }

    public void setPicassoUrl(String picassoUrl) {
        PicassoUrl = picassoUrl;
    }
}
