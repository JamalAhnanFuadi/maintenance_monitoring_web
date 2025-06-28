package id.tsi.mmw.property;

import org.bouncycastle.pqc.crypto.newhope.NHSecretKeyProcessor;

public class Constants {

    private Constants() {}

    public static final String SESSION_TRACKING_ID = "MM_tracking_id";
    public static final String SESSION_USER= "MM_User";
    public static final String SESSION_ROLES= "MM_Roles";

    public static final String SESSION_RESET_PASSWORD= "MM_Reset_Password";
    public static final String SESSION_RESET_PASSWORD_EMAIL= "MM_Reset_Password_Email";

    // Controlled icon uids
    // Icon uid list is referenced from the icon table in the database.
    // This list should be synchronized with the icon table in the database.
    // Do not change this list unless you know what you are doing.
    // If you are updating an icon, please update the icon table in the database without changing this list and the icon uids.
    public static final String ICON_PROJECT_CREATED="fb334975-097c-44da-94d5-3f556cfd988e";
    public static final String ICON_PROJECT_UPDATED_BY_USER="034d91e7-3c05-40d3-b1f5-f63ce7924068";
    public static final String ICON_PROJECT_UPDATED_BY_SYSTEM="80e7fb53-c719-444c-89a4-162cb26ae645";

    public static final String EVENT_CREATED="CREATE";
    public static final String EVENT_UPDATED="UPDATE";
    public static final String EVENT_DELETED="DELETE";

    // Audit Event
    public static final String APPLICATION_NAME= "Maintenance Monitoring Application";
    public static final String EVENT_LOGIN= "Authentication";
    public static final String EVENT_LOGOUT= "Logout";

    // Message Constant
    public static final String MESSAGE_INVALID_LOGIN = "Invalid username or password";
    public static final String MESSAGE_LOGIN_NOT_ALLOWED = "Login access is not allowed. Please contact administrator for assistance";

    public static final String MESSAGE_INVALID_REQUEST = "Invalid Request Payload";
    public static final String MESSAGE_SUCCESS = "Success";

    public static final String SUCCESS = "SUCCESS";
    public static final String OTP_VALID = "Valid";
    public static final String OTP_EXPIRED = "Expired";
}
