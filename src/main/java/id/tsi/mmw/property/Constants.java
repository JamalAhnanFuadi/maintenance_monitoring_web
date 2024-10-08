package id.tsi.mmw.property;

import org.bouncycastle.pqc.crypto.newhope.NHSecretKeyProcessor;

public class Constants {

    private Constants() {}

    public static final String SESSION_TRACKING_ID = "MM_tracking_id";
    public static final String SESSION_USER= "MM_User";
    public static final String SESSION_ROLES= "MM_Roles";


    // Message Constant
    public static final String MESSAGE_INVALID_REQUEST = "Invalid Request Payload";
    public static final String MESSAGE_SUCCESS = "Success";
}
