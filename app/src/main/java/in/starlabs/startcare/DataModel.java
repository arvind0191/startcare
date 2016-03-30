package in.starlabs.startcare;

/**
 * Created by Arvind on 30/03/16.
 */
public class DataModel {

    private String title;
    private String description;

    public DataModel(String s, String s1) {
        this.title = s;
        this.description = s1;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }
}
