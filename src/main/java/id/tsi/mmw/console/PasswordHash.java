package id.tsi.mmw.console;

import id.tsi.mmw.manager.EncryptionManager;

public class PasswordHash {

    public static void main(String[] args) {
        EncryptionManager.init();
        System.out.println(EncryptionManager.decrypt("HWDIGFeYSkKMPPnH6VF3mA=="));
    }
}
