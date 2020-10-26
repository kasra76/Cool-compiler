package coolExternalClasses;

import javafx.scene.control.Tab;

public class Scope {

    private Scope parent;
    private SymbolTable symbolTable;
    private TableObject owner = null;

    public Scope(Scope parent) {
        this.parent = parent;
        SymbolTable symbolTable = new SymbolTable();
    }

    public Scope getParent() {
        return parent;
    }

    public void setParent(Scope parent) {
        this.parent = parent;
    }

    public SymbolTable getSymbolTable() {
        return symbolTable;
    }

    public TableObject getOwner() {
        return owner;
    }

    public void setOwner(TableObject owner) {
        this.owner = owner;
    }

    //    public void setSymbolTable(SymbolTable symbolTable) {
//        this.symbolTable = symbolTable;
//    }



}
