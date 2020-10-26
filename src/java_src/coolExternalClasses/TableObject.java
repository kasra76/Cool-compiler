package coolExternalClasses;

public class TableObject {
    private java.lang.String name;
    private Type type;
    private Kind kind;
    private String parent;
    private int lineNumber;

    public TableObject(java.lang.String name, Type type, Kind kind, int lineNumber ) {
        this.name = name;
        this.type = type;
        this.kind = kind;
        this.lineNumber = lineNumber;
    }

    public java.lang.String getName() {
        return name;
    }

    public void setName(java.lang.String name) {
        this.name = name;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Kind getKind() {
        return kind;
    }

    public void setKind(Kind kind) {
        this.kind = kind;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }
}

