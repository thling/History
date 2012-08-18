/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * hList.java
 *
 * Created on 16-Feb-2011, 11:57:14 AM
 */

package history;

import java.awt.Color;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;

import java.awt.Frame;
import java.awt.Component;

/**
 * An hList is (in fact) a JDialog. The reason to implement hList as
 * a dialog is for the ease of the integration into a window. In javax.swing,
 * only "Swing Windows" (JDialog, JFrame, JOptionPane, etc) can be displayed
 * as an independent component in native OS. Other JComponents can only be
 * displayed within a Swing Window. Unfortunately, JList does not belong
 * to one of the Swing Windows. Therefore, implementation extending JDialog
 * would make this list lighter weighted than JFrame and also easier to
 * manipulate since JDialog is just like a "pop-up" to main window. For
 * example, you may lock the parent window with dialog but you can't lock
 * a window itself or another window with a frame (lock is, when a pop up is displayed,
 * the situation that you must finish operations in the pop up before going back
 * to the window).<br>
 * <br>
 * hList is implemented using JDialog with a JList. The JList takes almost
 * the entire dialog except for the very button where a "Hide" label is placed.
 * Refer to the API's below for further information.
 * @author Sam
 */
public class hList extends javax.swing.JDialog {
    /**
     * An ArrayList<hObject> displayed in this list
     */
    private ArrayList<hObject> hobjs;

    /**
     * The frame that contains this list.
     */
    private Frame frame;

    /**
     * The component used to call the display method in this list
     * (thus sets this list to be visible).
     */
    private Component component;

    /**
     * The History object that owns this list.
     */
    private History history;

    /**
     * The ID of this list. Used in association with History.indexedAction(int,int)
     * for actual action upon item selection.
     */
    private int listID;

/**
 * hList constructor. Contains three parameters:
 * @param h         The hObject list that this hList would be responsible displaying
 * @param hist      The History object that owns this hList
 * @param lstID     The ID of this list for identification on which method should be
 *                  called when call back (thus this ID is passed to the method)
 */
    public hList(ArrayList<hObject> h, History hist, int lstID) {
        initComponents();
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        setResizable(false);
        getRootPane().setBorder(
                new javax.swing.border.LineBorder(Color.black));
        setSize(220,260);
        hobjs = h;
        history = hist;
        listID = lstID;
    }

    /**
     * An overloaded method. Accepts an hObject list. Using this method
     * will discard the original hObject list and replace it with the
     * provided one. Use with caution.
     * @param h     a new list of hObjects assigned to this hList
     */
    public void update(ArrayList<hObject> h){
        hobjs = h;
        update();
    }

    /**
     * Fill the list with the hobjs field in this object.
     */
    public void update(){
        ArrayList<Object> objs = new ArrayList<Object>();
        for(hObject hobj : hobjs){
            objs.add(hobj.getDescription());
        }
        lstItems.setListData(upDownReverse(objs.toArray()));
    }

    /**
     * An extremely simple algorithm for reversing a list.<br>
     * Time: lg(n) where n=length of list and lg=log base 2<br>
     * Memory: 1 heap allocation, 2 stack allocation, never changed (in place)<br>
     * <br>
     * Algorithm: Swap the first and last item in the array until the two pointer intersects.
     * @param obj   an array to be reversed
     * @return      the reversed array
     */
    public Object[] upDownReverse(Object[] obj){
        Object holder;
        int rInd = obj.length - 1;
        int fInd = 0;

        while(true){
            if(rInd <= fInd){
                break;
            }

            holder = obj[fInd];
            obj[fInd] = obj[rInd];
            obj[rInd] = holder;
            rInd--;
            fInd++;
        }

        return obj;
    }

    /**
     * Displays the dialog. Call this method instead of the built-in setVisible(boolean).
     * This method contains some more error checking and initialization.
     */
    public void display(){
        if(frame == null){
            throw new UnsupportedOperationException("This object hasn't been associated with any container yet!");
        }else if(component == null){
            throw new UnsupportedOperationException("This object hasn't been associated with any component yet!");
        }else{
            setLocation(component.getLocationOnScreen().x,component.getLocationOnScreen().y + component.getHeight());
            component.setEnabled(false);
            // Locks the parent window
            setModalityType(ModalityType.APPLICATION_MODAL);
            setVisible(true);
        }
    }

    /**
     * Sets the association of this list.
     * @param f     the frame that contains a component that can be used to call this list
     * @param c     the said component that will call this list
     */
    public void setAssociationComponents(Frame f, final Component c){
        frame = f;
        component = c;
        f.addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentMoved(ComponentEvent e) {
                setLocation(c.getLocationOnScreen().x,c.getLocationOnScreen().y + c.getHeight());
            }

            @Override
            public void componentResized(ComponentEvent e) {
                setLocation(c.getLocationOnScreen().x,c.getLocationOnScreen().y + c.getHeight());
            }
        });
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        lstItems = new javax.swing.JList();
        lblHide = new javax.swing.JLabel();

        setUndecorated(true);
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                formComponentResized(evt);
            }
        });
        getContentPane().setLayout(null);

        lstItems.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lstItemsMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(lstItems);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(0, 0, 220, 240);

        lblHide.setText("                               -hide-");
        lblHide.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblHideMouseClicked(evt);
            }
        });
        getContentPane().add(lblHide);
        lblHide.setBounds(0, 240, 220, 14);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lstItemsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lstItemsMouseClicked
        // If mouse clicks on the JList in thsi object, detects the index
        // of it and calls History.indexedAction(int,int) with ID of this list
        // to perform action. If nothing is selected, return.
        component.setEnabled(true);
        setVisible(false);
        
        if(lstItems.getSelectedIndex() == -1){
            return;
        }
        
        history.indexedAction(lstItems.getSelectedIndex(), listID);
    }//GEN-LAST:event_lstItemsMouseClicked

    private void lblHideMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblHideMouseClicked
        // If "Hide" is clicked, hide this hList.
        component.setEnabled(true);
        setVisible(false);
    }//GEN-LAST:event_lblHideMouseClicked

    private void formComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentResized
        // Aciton to the components when being resized.
        lstItems.setSize(getWidth(),getHeight() - lblHide.getHeight());
        lstItems.setLocation(0,0);
        lblHide.setSize(getWidth(),lblHide.getHeight());
        lblHide.setLocation(0, lstItems.getHeight() - 5);
    }//GEN-LAST:event_formComponentResized

    // Variables declaration - do not modify//GEN-BEGIN:variables
    protected javax.swing.JScrollPane jScrollPane1;
    protected javax.swing.JLabel lblHide;
    protected javax.swing.JList lstItems;
    // End of variables declaration//GEN-END:variables

}
