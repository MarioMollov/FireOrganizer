package uni.fmi.masters.fireorganizer.model;

public class ImageModel {

    private String uploadedAt;
    private String avatarPath;

    public ImageModel() {}
    public ImageModel (String uploadedAt, String avatarPath) {
        this.uploadedAt = uploadedAt;
        this.avatarPath = avatarPath;
    }

    public String getUploadedAt() {
        return uploadedAt;
    }

    public void setUploadedAt(String uploadedAt) {
        this.uploadedAt = uploadedAt;
    }

    public String getAvatarPath() {
        return avatarPath;
    }

    public void setAvatarPath(String avatarPath) {
        this.avatarPath = avatarPath;
    }
}
