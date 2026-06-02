/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package com.askal.ispbillingsystem;

import javax.swing.JOptionPane;

/**
 *
 * @author Lorence
 */
public class CustomerDialog extends javax.swing.JDialog {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(CustomerDialog.class.getName());
    private boolean saved = false;
    private Customer customer = null;
    public boolean isSaved() { return saved; }
    public Customer getCustomer() { return customer; }
    
    public CustomerDialog(java.awt.Frame parent, Customer c) {
        super(parent, true);
        initComponents();
        setLocationRelativeTo(parent);
        
        if (c != null) {
        this.customer = c;
        setTitle("Edit Customer");
        fFirst.setText(c.getFirstName());
        fLast.setText(c.getLastName());
        fAddr.setText(c.getAddress());
        fPlan.setSelectedItem(c.getPlan());
        fBal.setText(String.valueOf(c.getBalance()));
        fStatus.setSelectedItem(c.getStatus());
        fDueDay.setText(String.valueOf(c.getDueDay()));
    } else {
        // Add mode
        this.customer = new Customer();
        setTitle("Add Customer");
    }
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        formPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        fFirst = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        fLast = new javax.swing.JTextField();
        JLabel3 = new javax.swing.JLabel();
        fAddr = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        fPlan = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        fBal = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        fDueDay = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        fStatus = new javax.swing.JComboBox<>();
        btnRowPanel = new javax.swing.JPanel();
        cancelBtn = new javax.swing.JButton();
        saveBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Customer Details");
        setModal(true);
        setSize(new java.awt.Dimension(400, 380));

        formPanel.setBackground(new java.awt.Color(255, 255, 255));
        formPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(16, 16, 8, 16));
        formPanel.setLayout(new java.awt.GridLayout(7, 2, 8, 10));

        jLabel1.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("First Name:");
        formPanel.add(jLabel1);

        fFirst.setColumns(15);
        fFirst.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        formPanel.add(fFirst);

        jLabel2.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("Last Name:");
        formPanel.add(jLabel2);

        fLast.setColumns(15);
        fLast.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        formPanel.add(fLast);

        JLabel3.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        JLabel3.setForeground(new java.awt.Color(0, 0, 0));
        JLabel3.setText("Address: ");
        formPanel.add(JLabel3);

        fAddr.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        formPanel.add(fAddr);

        jLabel4.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("Plan: ");
        formPanel.add(jLabel4);

        fPlan.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        fPlan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Basic", "Standard", "Premium", "VIP" }));
        formPanel.add(fPlan);

        jLabel5.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("Balance:");
        formPanel.add(jLabel5);

        fBal.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        fBal.addActionListener(this::fBalActionPerformed);
        formPanel.add(fBal);

        jLabel7.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
        jLabel7.setText("Month Due Day:");
        formPanel.add(jLabel7);
        formPanel.add(fDueDay);

        jLabel6.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setText("Status: ");
        formPanel.add(jLabel6);

        fStatus.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        fStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Paid", "Unpaid", "Pending" }));
        formPanel.add(fStatus);

        getContentPane().add(formPanel, java.awt.BorderLayout.CENTER);

        btnRowPanel.setBackground(new java.awt.Color(255, 255, 255));
        btnRowPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 16, 16, 16));
        btnRowPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        cancelBtn.setText("Cancel");
        cancelBtn.addActionListener(this::cancelBtnActionPerformed);
        btnRowPanel.add(cancelBtn);

        saveBtn.setText("Save");
        saveBtn.addActionListener(this::saveBtnActionPerformed);
        btnRowPanel.add(saveBtn);

        getContentPane().add(btnRowPanel, java.awt.BorderLayout.SOUTH);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cancelBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelBtnActionPerformed
        saved = false;
        dispose();
    }//GEN-LAST:event_cancelBtnActionPerformed

    private void saveBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveBtnActionPerformed
        int dueDayVal;
    try {
        dueDayVal = Integer.parseInt(fDueDay.getText().trim());
        if (dueDayVal < 1 || dueDayVal > 31) {
            JOptionPane.showMessageDialog(this, "Please enter a valid day of the month (1-31).");
            return;
        }
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Monthly due day must be a valid whole number.");
        return; 
    }
    double balanceVal;
    try {
        balanceVal = Double.parseDouble(fBal.getText().trim());
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Balance must be a valid number.");
        return; 
    }
    customer.setFirstName(fFirst.getText().trim());
    customer.setLastName(fLast.getText().trim());
    customer.setAddress(fAddr.getText().trim());
    customer.setPlan((String) fPlan.getSelectedItem());
    customer.setBalance(balanceVal);
    customer.setStatus((String) fStatus.getSelectedItem());
    customer.setDueDay(dueDayVal);
    
    saved = true;
    dispose();
    }//GEN-LAST:event_saveBtnActionPerformed

    private void fBalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fBalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_fBalActionPerformed

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
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                CustomerDialog dialog = new CustomerDialog(new javax.swing.JFrame(), null);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel JLabel3;
    private javax.swing.JPanel btnRowPanel;
    private javax.swing.JButton cancelBtn;
    private javax.swing.JTextField fAddr;
    private javax.swing.JTextField fBal;
    private javax.swing.JTextField fDueDay;
    private javax.swing.JTextField fFirst;
    private javax.swing.JTextField fLast;
    private javax.swing.JComboBox<String> fPlan;
    private javax.swing.JComboBox<String> fStatus;
    private javax.swing.JPanel formPanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JButton saveBtn;
    // End of variables declaration//GEN-END:variables
}
