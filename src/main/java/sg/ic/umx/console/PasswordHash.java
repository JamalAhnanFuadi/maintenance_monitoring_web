/**
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2019 Identiticoders, and individual contributors as indicated by the @author tags.
 * All Rights Reserved
 *
 * The contents of this file are subject to the terms of the Common Development and Distribution
 * License (the License).
 *
 * Everyone is permitted to copy and distribute verbatim copies of this license document, but
 * changing it is not allowed.
 *
 */
package sg.ic.umx.console;

import sg.ic.umx.manager.EncryptionManager;

public class PasswordHash {

    public static void main(String[] args) {
        EncryptionManager.init();
        System.out.println(EncryptionManager.decrypt("HWDIGFeYSkKMPPnH6VF3mA=="));
    }
}
