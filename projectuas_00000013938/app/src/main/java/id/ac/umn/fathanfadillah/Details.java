package id.ac.umn.fathanfadillah;

public class Details {
    private String id;
    private String name;
    private String type;
    private String desc;
//    private String url;
    private String image_url_small;
//    private String urlImage;
//    private String published;
//    private String content;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() { return name; }
    public void setName(String name) {
        this.name = name;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getDesc() {
        return desc;
    }
    public void setDesc(String desc) {
        this.desc = desc;
    }
    public String getImageSmall() {
        return image_url_small;
    }
    public void setImageSmall(String image_url_small) {
        this.image_url_small = image_url_small;
    }
}

