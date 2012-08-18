/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * test.java
 *
 * Created on 15-Feb-2011, 2:07:28 PM
 */

package history;

/**
 * This is an example program on using the history package.
 * @author Sam
 */
public class ExampleProgram extends javax.swing.JFrame {
    /**
     * Main object.
     */
    History h = new History();

    /**
     * <PRE>
     * The History object supports different actions on different objects
     * (so you may assign an entirely different action to every undo/redo
     * process). In this case, we only need to print the text to the label
     * so there is only one general action.
     * 
     * Tip: it's very likely that you will be using similar operations
     * over and over, and thus 90% of the time you already have the method
     * declared. Just call the method from within the implementation of the
     * hObject.action interface.
     * </PRE>
     */
    hObject.action gen = new hObject.action() {
        public Object act(Object[] obj) {
            lblText.setText((String)obj[0]);
            return null;
        }
    };

    /** 
     * Creates new form test (Constructor)
     */
    public ExampleProgram() {
        initComponents();
        setLocation(
                java.awt.Toolkit.getDefaultToolkit().getScreenSize().width / 2 - getWidth() / 2,
                java.awt.Toolkit.getDefaultToolkit().getScreenSize().height / 2 - getHeight());

        /**
         * Initializing GUI list. Note that History also works without GUI. Thus,
         * to minimize the use of system resources, hLists are not initialized
         * unless you call the method below.
         *
         * Parameters:
         *   1. Accepts only objects based on java.awt.Frame. Please pass in the
         *      "window" that you will be using to initiate "this" History object.
         *   2. Accepts only objects based on java.awt.Component. Please pass in
         *      the "component" that you will be using to summon the "undo" list.
         *   3. Accepts only objects based on java.awt.Component. Please pass in
         *      the "component" that you will be using to summon the "redo" list.
         *
         * In this sample, there is only one window ("this" window). We will be
         * using btnUndo (a javax.swing.JButton object labeled "Undo" in the main
         * window) to summon the list (so in this case, clicking on btnUndo will
         * display the list). Same goes for btnRedo.
         */
        h.initializeDisplayList(this, btnUndo, btnRedo);
    }



    /**
     * This method manages the keyTypedEvent. It is being called from the system
     * created method only for neatness of this demonstration.
     */
    private void keyTypedAction(java.awt.event.KeyEvent evt){
        // Perform actual action only when Enter is pressed
        if(evt.getKeyChar() == java.awt.event.KeyEvent.VK_ENTER){
            /**
             * Gets the string inside the textfield "txtTestEntry"
             * (the only textfield in the main window).
             */
            String txt = txtTestEntry.getText();

            /**
             * Checks if current state is null. This is usually the case on program
             * startup unless you specify it in this object's constructor.
             */
            if(h.getCurrent() == null){
                /**
                 * Set current state to proper null state for this program
                 *
                 * Creating a new hObject:
                 *  hObjects are objects customized targeting the use with History.
                 *  Please refer to API for the class.
                 *
                 *  Constructor of an hObject requires three parameter:
                 *    1. Description: The description of this state. This is used
                 *       in hList GUI for human readable content. There is one more
                 *       example of this at the bottom of this method.
                 *    2. hObject.action implementation: Include the implementation
                 *       of hObject.action interface. You may put anything here
                 *       as long as it implements hObject.action. This is how
                 *       History manages different operation for different states redo/undo.
                 *    3. Objects: the objects involved in the second parameter. In
                 *       hObject class, there is an "execute" method that calls 
                 *       "act" method in your implemented action interface. Notice that
                 *       "act" method takes in an array of objects. This field is where
                 *       the array of objects comes from. You can supply any amount of
                 *       objects here.
                 */
                h.setCurrent(new hObject("Empty", gen, "empty"));
            }

            /**
             * Adding a state to history list is easy. Simple call History.add(hObject) method
             * to add to the list.
             * In the sample, we add getCurrent() which returns the current state as hObject.
             */
            h.add(h.getCurrent());
            lblText.setText(txt);

            /**
             * Now that you have put today into the history, you need to define a new today
             * (which should be tomorrow but anyhow), and thus you need to use setCurrent
             * method to do so.
             * (make sense, eh? adding current to history and making tomorrow current)
             */
            h.setCurrent(new hObject<String>("Set \"" + txt + "\" to Label",gen,lblText.getText()));

            
            /**
             * Set txtTestEntry to empty for next input.
             */
            txtTestEntry.setText("");




            /**
             * The above is the suggested way of populating a History object. You may
             * still explore into the API and find new way of doing things.
             */
        }
    }

    /**
     * This method manages btnUndo action event. It is called from the system
     * created method only for neatness of this demonstration.
     */
    private void btnUndoAction(){
        /**
         * It is also easy to summon hList object (remember to initialize them
         * using History.initializeDisplayList(String,hObject.action,String) method
         * or you will be getting exception).
         *
         * The showUndoList() method will display the hList containing all
         * possible redo/undo actions NEAR the ASSOCIATED component (described above).
         * Clicking any item in the list will perform redo/undo on the selected item.
         *
         * This is the same for the next method, so nothing will be discussed.
         */
        h.showUndoList();



        /**
         * In addition to do so, you might find methods in History object such as
         * redoOnce()/undoOnce() or redoTo(int)/undoTo(int) extremely useful.
         * Other methods are also provided, please refer to the API.
         */
    }

    /**
     * This method manages btnRedo action event. It is called from the system
     * created method only for neatness of this demonstration.
     */
    private void btnRedoAction(){
        h.showRedoList();
    }



    /**
     * Below are things that are unnecessary to this demo.
     */




    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        lblText = new javax.swing.JLabel();
        btnUndo = new javax.swing.JButton();
        btnRedo = new javax.swing.JButton();
        txtTestEntry = new javax.swing.JTextField();
        btnUndoOnce = new javax.swing.JButton();
        btnRedoOnce = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Example on History");

        jLabel1.setText("Test Text:");

        lblText.setText("empty");

        btnUndo.setText("Undo");
        btnUndo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUndoActionPerformed(evt);
            }
        });

        btnRedo.setText("Redo");
        btnRedo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRedoActionPerformed(evt);
            }
        });

        txtTestEntry.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTestEntryKeyTyped(evt);
            }
        });

        btnUndoOnce.setText("UO");
        btnUndoOnce.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUndoOnceActionPerformed(evt);
            }
        });

        btnRedoOnce.setText("RO");
        btnRedoOnce.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRedoOnceActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtTestEntry, javax.swing.GroupLayout.DEFAULT_SIZE, 257, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(btnUndo))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnUndoOnce)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 37, Short.MAX_VALUE)
                                .addComponent(btnRedoOnce)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnRedo))
                            .addComponent(lblText, javax.swing.GroupLayout.DEFAULT_SIZE, 194, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnUndo)
                    .addComponent(btnRedo)
                    .addComponent(btnUndoOnce)
                    .addComponent(btnRedoOnce))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(lblText, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(txtTestEntry, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnUndoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUndoActionPerformed
        btnUndoAction();
    }//GEN-LAST:event_btnUndoActionPerformed

    private void btnRedoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRedoActionPerformed
        btnRedoAction();
    }//GEN-LAST:event_btnRedoActionPerformed

    private void txtTestEntryKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTestEntryKeyTyped
        keyTypedAction(evt);
    }//GEN-LAST:event_txtTestEntryKeyTyped

    private void btnUndoOnceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUndoOnceActionPerformed
        h.undoOnce();
    }//GEN-LAST:event_btnUndoOnceActionPerformed

    private void btnRedoOnceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRedoOnceActionPerformed
        h.redoOnce();
    }//GEN-LAST:event_btnRedoOnceActionPerformed
    
    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try{
                    javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
                }catch(Exception e){
                    
                }
                new ExampleProgram().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    protected javax.swing.JButton btnRedo;
    protected javax.swing.JButton btnRedoOnce;
    protected javax.swing.JButton btnUndo;
    protected javax.swing.JButton btnUndoOnce;
    protected javax.swing.JLabel jLabel1;
    protected javax.swing.JLabel lblText;
    protected javax.swing.JTextField txtTestEntry;
    // End of variables declaration//GEN-END:variables

}