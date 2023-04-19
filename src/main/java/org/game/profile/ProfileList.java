package org.game.profile;

import jakarta.xml.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "profiles")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProfileList {

    @XmlElement(name = "profile")
    private List<Profile> profiles;

    public ProfileList() {
        profiles = new ArrayList<>();
    }

    public List<Profile> getProfiles() {
        return profiles;
    }

    public void setProfiles(List<Profile> profiles) {
        this.profiles = profiles;
    }

    public void addProfile(Profile profile) {
        profiles.add(profile);
    }
}
