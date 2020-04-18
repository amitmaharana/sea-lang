public enum Type {
    INT("Int"),
    STRING ("String"),
    BOOLEAN ("Boolean");

    private final String val;

    Type(String val) {
        this.val = val;
    }

    public String getVal() {
        return val;
    }
}
