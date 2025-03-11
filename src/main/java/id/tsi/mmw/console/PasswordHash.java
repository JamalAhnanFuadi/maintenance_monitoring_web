package id.tsi.mmw.console;

import id.tsi.mmw.manager.EncryptionManager;

public class PasswordHash {

    public static void main(String[] args) {
        String salt = EncryptionManager.getInstance().generateRandomString(6);
        System.out.println(salt);

        String plainPassword = "P@ssw0rd";
        System.out.println(EncryptionManager.getInstance().hash(plainPassword, salt));

/*        System.out.println(EncryptionManager.getInstance().encrypt("bbjn qmob nwhk twzj"));
        System.out.println(EncryptionManager.getInstance().decrypt("9L2AewPCUthgua2hQNOOhJ108O10N0d5MrXa7EmJidk="));*/
    }
}
