package sg.ic.umx.engine.model;

public enum AccountType {
    WHITELIST("Whitelist"), NORMAL("Normal");

    private String type;

    private AccountType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
