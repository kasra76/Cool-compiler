package coolExternalClasses;

import java.util.ArrayList;
import java.util.Hashtable;

public class SymbolTable {
    private Hashtable<java.lang.String, ArrayList<TableObject>> table = new Hashtable<>();

    /** A void function to insert a TableObject to the symbol table */
    public void insert(java.lang.String symbol, TableObject object) {
        if (table.get(symbol) == null) {
            ArrayList<TableObject> list = new ArrayList<>();
            list.add(object);
            table.put(symbol, list);
        } else {
            table.get(symbol).add(object);
        }
    }

    /** returns a list with all TableObject with name = symbol */
    public ArrayList<TableObject> lookup(java.lang.String symbol) {
        return table.get(symbol);
    }
}
