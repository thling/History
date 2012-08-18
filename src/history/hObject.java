/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package history;

import java.util.ArrayList;

/**
 * hObject (history Object) is an object required for use of History class. It
 * itself represents a single command, no matter how many related objects are involved.<br>
 * <br>
 * History class provides a list of performed task and recorded object
 * that can be used for revert back to previous states or un-revert to the more
 * advanced states. This is also known as Undo and Redo, respectively.<br>
 * <br>
 * hObject provides many methods for modification and accessing purposes.
 * It also has an interface "action" which programmer needs to implement in
 * order to make full use of the undo/redo feature. Detail will be discussed
 * with each methods.<br>
 * <br>
 * @param <T>   An datatype passed in for the hObject. Deemed as Object if nothing is passed.
 * @author Sam
 */
public class hObject<T> {
    /**
     * An hObject contains one list of any data type. The data type must be
     * a reference, not primitive. This will be ALL objects involved in one command,
     * and hObject can represent a single command in a history.
     */
    private ArrayList<T> objs;

    /**
     * A description of this object. This is important because the hList object
     * will be displaying the action using the description.
     */
    private String description;

    /**
     * Implementation of the action interface in this object. This must be
     * implemented to perform undo/redo operations.
     */
    private action act;

    /**
     * Constructs a new hObject.
     * @param desc  Description of this object. This is important because the hList
     *              will be displaying using the description
     * @param ac    Action interface implementation
     * @param items The hObject support multiple object containment. Therefore,
     *              if a certain reverting operation (called using implemented action
     *              interface) involves reverting multiple object, everything can be
     *              put into this single object. Remember, a hObject is a single
     *              command, not a single object to be reverted.
     */
    public hObject(String desc, action ac, T ...items){
        objs = new ArrayList<T>(java.util.Arrays.asList(items));
        description = desc;
        act = ac;
    }

    /**
     * Returns the size of the list of objects involved in this command.
     * @return  size of the list of objects involved in this command
     */
    public int size(){
        return objs.size();
    }

    /**
     * Returns the object at the desired index.
     * @param index value representing the object's index
     * @return  the actual desired object
     */
    public T get(int index){
        return objs.get(index);
    }

    /**
     * Returns the last inserted object and remove it from the list.
     * @return  the last inserted object
     */
    public T pop(){
        T tmp = objs.get(objs.size() - 1);
        objs.remove(objs.size() - 1);
        return tmp;
    }

    /**
     * Returns the description.
     * @return  description of this hObject
     */
    public String getDescription(){
        return description;
    }

    /**
     * Sets the description
     * @param desc  string to be set to description
     */
    public hObject setDescription(String desc){
        description = desc;
        return this;
    }

    /**
     * Returns the list of object involved in this command.
     * @return  the list of object involved
     */
    public T[] getItemList(){
        return (T[])objs.toArray();
    }

    /**
     * Perform the desired revert operation. Usually the same as the normal operation
     * performed to create this object.
     * @param obj   Object that will be passed into the implemented method. Usually the
     *              objects contained in the list
     * @return      Object being returned in the developer defined action interface
     */
    public Object execute(Object[] obj){
        return act.act(obj);
    }

    /**
     * An interface needed to be implemented. Implement using: <br>
     * <PRE>
     *     hObject.action action = new hObject.action(){
     *         public Object act(Object[] obj){ 
     *             //some code for operations 
     *             return ...; 
     *         }
     *     };
     * </PRE>
     * This must be implement because the undo/redo operation will be using this
     * to perform tasks.
     */
    public interface action{
        public Object act(Object[] obj);
    }
}
