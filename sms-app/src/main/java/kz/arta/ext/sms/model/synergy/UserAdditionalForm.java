package kz.arta.ext.sms.model.synergy;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by admin on 05.09.14.
 */
public class UserAdditionalForm {
    @JsonProperty("data-uuid")
    private String dataUuid;

    @JsonProperty("form-uuid")
    private String formUuid;
    private String name;
    private String editable;
    private String mandatory;
    private String userId;

    private String fullName;
    private String[] phones;
    private String iin;
    private String message;
    private String type;

    private String SmsId;

    public String getSmsId() {
        return SmsId;
    }

    public void setSmsId(String smsId) {
        SmsId = smsId;
    }

    private String responceMessage;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getResponceMessage() {
        return responceMessage;
    }

    public void setResponceMessage(String responceMessage) {
        this.responceMessage = responceMessage;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getIin() {
        return iin;
    }

    public void setIin(String iin) {
        this.iin = iin;
    }

    public String[] getPhones() {
        return phones;
    }

    public void setPhones(String[] phones) {
        this.phones = phones;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getDataUuid() {
        return dataUuid;
    }

    public void setDataUuid(String dataUuid) {
        this.dataUuid = dataUuid;
    }

    public String getFormUuid() {
        return formUuid;
    }

    public void setFormUuid(String formUuid) {
        this.formUuid = formUuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEditable() {
        return editable;
    }

    public void setEditable(String editable) {
        this.editable = editable;
    }

    public String getMandatory() {
        return mandatory;
    }

    public void setMandatory(String mandatory) {
        this.mandatory = mandatory;
    }
}
