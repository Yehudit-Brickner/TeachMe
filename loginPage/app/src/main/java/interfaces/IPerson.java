package interfaces;

import java.util.Map;

public interface IPerson {
    public String getUID();

    public String getFirstName();

    public String getLastName();

    public String getEmail();

    public String getPhoneNumber();

    public void setUID(String uID);

    public Map<String, Object> getPersonMap();
}