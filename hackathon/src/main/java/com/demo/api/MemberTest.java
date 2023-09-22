package com.demo.api;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import java.io.Serializable;
import java.util.Date;

@DynamoDBTable(tableName = "PA_YOUTH_VOTE_1025")
public class MemberTest implements Serializable {

    @DynamoDBAttribute
    private String firstName;

    @DynamoDBAttribute
    private String lastName;

    @DynamoDBAttribute
    private String Email;

    @DynamoDBHashKey
    public String getHashKey() {
        return String.valueOf(this.Email);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", username=").append(firstName);
        sb.append(", phone=").append(Email);
        sb.append("]");
        return sb.toString();
    }
}