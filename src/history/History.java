/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package history;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * An History object is a list of hObject in LIFO (Last-in, First-out) order.
 * Creating an hObject and then add it to History list is not necessary, yet
 * History object provides an easier way to manage the hObjects. It also provides
 * a few useful API's to
 * @author Sam
 */
public class History {
    // List of undo hObjects
    private ArrayList<hObject> undo;

    // List of redo hObjects
    private ArrayList<hObject> redo;

    // The GUI list filled with undo objects using descriptions
    private hList undoList;

    // The GUI list filled with redo objects using descriptions
    private hList redoList;

    // The currently in on screen/in use obejct (current state)
    private hObject current;

    /**
     * ID for abstraction (ID is passed to a hList when constructed for later verification
     */
    public final int
            UNDO_LIST_ID = 0,
            REDO_LIST_ID = 1;

    /**
     * Initializes two lists.
     */
    public History(){
        undo = new ArrayList<hObject>();
        redo = new ArrayList<hObject>();
        current = null;
    }

    /**
     * Used to initialize display of the hList. Must be called if intend to use
     * History within GUI.
     *
     * @param containerFrame    the window that contains the action for displaying the lists.
     *                          Has to be direct parent. For Example, window A contains
     *                          window B, and something in B will be used to summon the hLists, then
     *                          window B should be passed in, not window A
     * @param undoComponent     the component used to summon the "Undo" lists. For example,
     *                          there are two buttons (labeled "Undo" and "Redo").
     *                          Clicking Undo button will summon the Undo list, then Undo button
     *                          should be passed in
     * @param redoComponent     same as undoComponent, except this time it's for redo action
     */
    public void initializeDisplayList(
            java.awt.Frame containerFrame,
            java.awt.Component undoComponent, 
            java.awt.Component redoComponent){

        // hLists initialization
        undoList = new hList(undo,this,UNDO_LIST_ID);
        redoList = new hList(redo,this,REDO_LIST_ID);

        // Sets association with the provided information.
        undoList.setAssociationComponents(containerFrame, undoComponent);
        redoList.setAssociationComponents(containerFrame, redoComponent);
    }

    /**
     * Call this when you need the hList GUI to update the items
     */
    public void updateLists(){
        undoList.update(undo);
        redoList.update(redo);
    }

    /**
     * Displays the undo list. Similar to setVisible(true) but there are
     * some more initializations in the method.
     */
    public void showUndoList(){
        undoList.display();
    }

    /**
     * Displays the redo list. Similar to setVisible(true) but there are
     * some more initializations in the method.
     */
    public void showRedoList(){
        redoList.display();
    }

    /**
     * Returns the undo list.
     * @return  returns an ArrayList<hObject>
     */
    public ArrayList<hObject> getUndoList(){
        return undo;
    }

    /**
     * Returns the hObject at the specified index in the undo list
     * @param index     an index indicating the position of desired hObject
     * @return          the needed hObject
     */
    public hObject getUndoItemAt(int index){
        return undo.get(index);
    }

    /**
     * Returns the redo list.
     * @return  returns an ArrayList<hObject>
     */
    public ArrayList<hObject> getRedoList(){
        return redo;
    }

    /**
     * Returns the hObject at the specified index in the redo list
     * @param index     an index indicating the position of desired hObject
     * @return          the needed hObject
     */
    public hObject getRedoItemAt(int index){
        return undo.get(index);
    }

    /**
     * Adds hObject to the history (typically undo list, which makes lots of sensee).
     * @param hobj  hObjects to be added. Note this parameter is defined as indefinite,
     *              so multiple hObjects can be added.
     */
    public void add(hObject... hobj){
        if(hobj.length == 0){
            return;
        }
        undo.addAll(Arrays.asList(hobj));
        redo.clear();
        updateLists();
    }

    /**
     * Sets the current state to the provided hObject
     * @param hobj  hObject to be set as current
     */
    public void setCurrent(hObject hobj){
        current = hobj;
    }

    /**
     * Returns current hObject
     * @return  an hObject stored in current state
     */
    public hObject getCurrent(){
        return current;
    }

    /**
     * Undo exactly once using the user defined hObject.action interface implementation.
     * @return  the object being returned in the implementation of the hObject.action interface
     */
    public Object undoOnce(){
        if(undo.isEmpty()){
            return null;
        }
        Object robj = undo.get(undo.size() - 1).execute(
                undo.get(undo.size() - 1).getItemList());
        
        redo.add(current);
        setCurrent(undo.get(undo.size() - 1));
        undo.remove(undo.size() - 1);
        updateLists();
        return robj;
    }

    /**
     * Redo exactly once using the user defined hObject.action interface implementation.
     * @return  the object being returned in the implementation of the hObject.action interface
     */
    public Object redoOnce(){
        if(redo.isEmpty()){
            return null;
        }
        Object robj = redo.get(redo.size() - 1).execute(
                redo.get(redo.size() - 1).getItemList());

        undo.add(current);
        setCurrent(redo.get(redo.size() - 1));
        redo.remove(redo.size() - 1);
        updateLists();
        return robj;
    }

    /**
     * Undo to the index provided
     * @param index     the index to be undone until
     * @return          the object being returned after executing the interface AT the index.
     *                  Only the object implemented to be returned in the interface
     *                  at the provided index is being returned.
     */
    public Object undoTo(int index){
        while(index >= 0){
            undoOnce();
            index--;
        }
        return null;
    }

    /**
     * Redo to the index provided
     * @param index     the index to be redone until
     * @return          the object being returned after executing the interface AT the index.
     *                  Only the object implemented to be returned in the interface
     *                  at the provided index is being returned.
     */
    public Object redoTo(int index){
        while(index >= 0){
            redoOnce();
            index--;
        }
        return null;
    }

    /**
     * Being called by the hList object when an index is being selected.
     * This is only called when called by hList (since otherwise using undoTo/redoTo
     * will be sufficient. This method exists only for abstraction.
     *
     * @param index     index to be redone/undone
     * @param listID    listID of the hList (provided at the time of construction)
     */
    protected void indexedAction(int index, int listID){
        switch(listID){
            case UNDO_LIST_ID:
                undoTo(index); break;
            case REDO_LIST_ID:
                redoTo(index); break;
            default:
                throw new UnsupportedOperationException("List ID canno be identified!");
        }
    }
}
