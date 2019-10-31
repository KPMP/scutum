package org.kpmp.auth;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Arrays;

@JsonPropertyOrder({ "id", "firstName", "lastName", "displayName", "email" })
public class UserAuth {

    private String _id;
    private Boolean active;
    private String email;
    private String first_name;
    private String[] groups;
    private String last_name;
    private String organization_id;
    private String[] phone_numbers;
    private String displayName;
    private String shib_id;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String[] getGroups() {
        return groups;
    }

    public void setGroups(String[] groups) {
        this.groups = groups;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getOrganization_id() {
        return organization_id;
    }

    public void setOrganization_id(String organization_id) {
        this.organization_id = organization_id;
    }

    public String[] getPhone_numbers() {
        return phone_numbers;
    }

    public void setPhone_numbers(String[] phone_numbers) {
        this.phone_numbers = phone_numbers;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getShib_id() {
        return shib_id;
    }

    public void setShib_id(String shib_id) {
        this.shib_id = shib_id;
    }

    @Override
    public String toString() {
        return "UserAuth{" +
                "_id='" + _id + '\'' +
                ", active=" + active +
                ", email='" + email + '\'' +
                ", first_name='" + first_name + '\'' +
                ", groups=" + Arrays.toString(groups) +
                ", last_name='" + last_name + '\'' +
                ", organization_id='" + organization_id + '\'' +
                ", phone_numbers=" + Arrays.toString(phone_numbers) +
                ", displayName='" + displayName + '\'' +
                ", shib_id='" + shib_id + '\'' +
                '}';
    }

    public String toJson() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(this);
    }
}
