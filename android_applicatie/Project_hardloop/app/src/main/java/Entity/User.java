package Entity;

/**
 * Created by niek on 25-12-2015.
 */
public class User {
    private String id;
    private String email;
    private String displayName;
    private String photoUrl;

    public User(String id, String email, String displayName, String photoUrl) {
        this.id = id;
        this.email = email;
        this.displayName = displayName;
        this.photoUrl = photoUrl;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }
}
