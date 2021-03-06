/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package imagemanager.gui;

import imagemanager.gui.label.LabelsView;

/**
 *
 * @author Lucas
 */
public class MainFrame extends javax.swing.JFrame {

    /**
     * Creates new form MainFrame
     */
    public MainFrame() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        horizontalSplitPane = new javax.swing.JSplitPane();
        verticalSplitPane = new javax.swing.JSplitPane();
        viewImagesPanel = new javax.swing.JPanel();
        viewAllImagesBox = new javax.swing.JCheckBox();
        viewAllIMagesButton = new javax.swing.JButton();
        labelsScrollPane = new javax.swing.JScrollPane();
        imagesScrollPane = new javax.swing.JScrollPane();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(600, 300));

        horizontalSplitPane.setDividerLocation(275);
        horizontalSplitPane.setToolTipText("");
        horizontalSplitPane.setName(""); // NOI18N

        verticalSplitPane.setDividerLocation(585);
        verticalSplitPane.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        verticalSplitPane.setResizeWeight(1.0);
        
        viewImagesPanel.setMinimumSize(new java.awt.Dimension(275, 50));
        
        imagesScrollPane.setMinimumSize(new java.awt.Dimension(275, 100));
        viewAllImagesBox.setText("Zaznacz wszystkie");

        viewAllIMagesButton.setText("Pokaż zdjęcia");

        javax.swing.GroupLayout viewImagesPanelLayout = new javax.swing.GroupLayout(viewImagesPanel);
        viewImagesPanel.setLayout(viewImagesPanelLayout);
        viewImagesPanelLayout.setHorizontalGroup(
            viewImagesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(viewImagesPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(viewAllImagesBox)
                .addGap(18, 18, 18)
                .addComponent(viewAllIMagesButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        viewImagesPanelLayout.setVerticalGroup(
            viewImagesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(viewImagesPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(viewImagesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(viewAllImagesBox)
                    .addComponent(viewAllIMagesButton))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        verticalSplitPane.setBottomComponent(viewImagesPanel);
        verticalSplitPane.setLeftComponent(labelsScrollPane);

        horizontalSplitPane.setLeftComponent(verticalSplitPane);
        horizontalSplitPane.setRightComponent(imagesScrollPane);

        jMenu1.setText("File");
        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(horizontalSplitPane, javax.swing.GroupLayout.DEFAULT_SIZE, 638, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(horizontalSplitPane, javax.swing.GroupLayout.DEFAULT_SIZE, 640, Short.MAX_VALUE)
                .addContainerGap())
        );
        // init labels view
        {
        	labelsView = new LabelsView();
        	labelsScrollPane.setViewportView(labelsView);
        }
        pack();
    }// </editor-fold>                        

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify                     
    private javax.swing.JSplitPane horizontalSplitPane;
    private javax.swing.JScrollPane imagesScrollPane;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane labelsScrollPane;
    private javax.swing.JSplitPane verticalSplitPane;
    private javax.swing.JButton viewAllIMagesButton;
    private javax.swing.JCheckBox viewAllImagesBox;
    private javax.swing.JPanel viewImagesPanel;
    private LabelsView labelsView;
    // End of variables declaration
    
}