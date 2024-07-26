package id.tsi.mmw.console;

import id.tsi.mmw.manager.EncryptionManager;

public class PasswordHash {

    public static void main(String[] args) {
        String salt = EncryptionManager.getInstance().generateRandomString(6);
        System.out.println(salt);

        String plainPassword = "P@ssw0rd";
        System.out.println(EncryptionManager.getInstance().hash(plainPassword, salt));

/*        String plainText = "Pa$$w0rd";
        System.out.println(EncryptionManager.getInstance().encrypt(plainText));*/

        String encText = "9L2AewPCUthgua2hQNOOhJ108O10N0d5MrXa7EmJidk=";
        String decText = EncryptionManager.getInstance().decrypt(encText);
        System.out.println(decText);
    }
}
