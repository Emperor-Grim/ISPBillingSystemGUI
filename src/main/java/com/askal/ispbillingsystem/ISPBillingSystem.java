/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.askal.ispbillingsystem;

import javax.swing.SwingUtilities;
import javax.swing.border.*;

/**
 *
 * @author Lorence
 */
public class ISPBillingSystem extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(ISPBillingSystem.class.getName());
    private javax.swing.JPanel activeSideBtn = null;
    
    //database
    CustomerDAO dao = new CustomerDAO();
    
    public ISPBillingSystem() {
        
        initComponents();
        activeSideBtn = btnDashboard;
        btnDashboard.setBackground(new java.awt.Color(40, 90, 137));
        
        
        
    }
    
    void refreshDashboard() {
        SwingUtilities.invokeLater(() -> {
            statusTotal.setText(String.valueOf(dao.getTotalCustomers()));
            statusPaid.setText(String.valueOf(dao.getPaidCount()));
            statusUnpaid.setText(String.valueOf(dao.getUnpaidCount()));
            statusBalance.setText(String.format("₱%.0f", dao.getTotalBalance()));
        });
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buildTopBar = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        bodyPanel = new javax.swing.JPanel();
        buildSideBar = new javax.swing.JPanel();
        logo = new javax.swing.JPanel();
        n = new javax.swing.JLabel();
        v = new javax.swing.JLabel();
        btnDashboard = new javax.swing.JPanel();
        dl = new javax.swing.JLabel();
        btnCustomers = new javax.swing.JPanel();
        cl = new javax.swing.JLabel();
        btnBilling = new javax.swing.JPanel();
        bl = new javax.swing.JLabel();
        btnReceipts = new javax.swing.JPanel();
        brec = new javax.swing.JLabel();
        btnReports = new javax.swing.JPanel();
        brep = new javax.swing.JLabel();
        contentPanel = new javax.swing.JPanel();
        dashboardPanel = new javax.swing.JPanel();
        lblDashboardHeader = new javax.swing.JLabel();
        dashCenterPanel = new javax.swing.JPanel();
        summaryRowPanel = new javax.swing.JPanel();
        cardTotal = new javax.swing.JPanel();
        lblTotal = new javax.swing.JLabel();
        statusTotal = new javax.swing.JLabel();
        cardPaid = new javax.swing.JPanel();
        lblPaid = new javax.swing.JLabel();
        statusPaid = new javax.swing.JLabel();
        cardUnpaid = new javax.swing.JPanel();
        lblUnpaid = new javax.swing.JLabel();
        statusUnpaid = new javax.swing.JLabel();
        cardBalance = new javax.swing.JPanel();
        lblBalance = new javax.swing.JLabel();
        statusBalance = new javax.swing.JLabel();
        customerPanel = new javax.swing.JPanel();
        billingPanel = new javax.swing.JPanel();
        receiptsPanel = new javax.swing.JPanel();
        reportsPanel = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(null);
        setMinimumSize(new java.awt.Dimension(950, 600));
        setSize(new java.awt.Dimension(1150, 700));

        buildTopBar.setBackground(new java.awt.Color(26, 58, 92));
        buildTopBar.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 16, 0, 16));
        buildTopBar.setPreferredSize(new java.awt.Dimension(100, 40));
        buildTopBar.setVerifyInputWhenFocusTarget(false);

        jLabel1.setFont(new java.awt.Font("SansSerif", 1, 13)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(181, 212, 244));
        jLabel1.setText("ISP Billing & Receipt Management System");

        javax.swing.GroupLayout buildTopBarLayout = new javax.swing.GroupLayout(buildTopBar);
        buildTopBar.setLayout(buildTopBarLayout);
        buildTopBarLayout.setHorizontalGroup(
            buildTopBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buildTopBarLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap(807, Short.MAX_VALUE))
        );
        buildTopBarLayout.setVerticalGroup(
            buildTopBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, buildTopBarLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                .addContainerGap())
        );

        getContentPane().add(buildTopBar, java.awt.BorderLayout.PAGE_START);

        bodyPanel.setLayout(new java.awt.BorderLayout());

        buildSideBar.setBackground(new java.awt.Color(12, 68, 124));
        buildSideBar.setPreferredSize(new java.awt.Dimension(162, 0));
        buildSideBar.setLayout(new javax.swing.BoxLayout(buildSideBar, javax.swing.BoxLayout.Y_AXIS));

        logo.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 5, 5));
        logo.setAlignmentX(1.0F);
        logo.setAlignmentY(0.0F);
        logo.setOpaque(false);
        logo.setLayout(new javax.swing.BoxLayout(logo, javax.swing.BoxLayout.Y_AXIS));

        n.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        n.setForeground(new java.awt.Color(230, 241, 251));
        n.setText("Askal ISP");
        logo.add(n);

        v.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        v.setForeground(new java.awt.Color(133, 183, 235));
        v.setText("Billing System");
        logo.add(v);

        buildSideBar.add(logo);

        btnDashboard.setBackground(new java.awt.Color(12, 68, 124));
        btnDashboard.setMaximumSize(new java.awt.Dimension(225, 36));
        btnDashboard.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnDashboardMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnDashboardMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnDashboardMouseExited(evt);
            }
        });
        btnDashboard.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 12, 8));

        dl.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        dl.setForeground(new java.awt.Color(230, 241, 251));
        dl.setText("Dashboard");
        btnDashboard.add(dl);

        buildSideBar.add(btnDashboard);

        btnCustomers.setBackground(new java.awt.Color(12, 68, 124));
        btnCustomers.setMaximumSize(new java.awt.Dimension(225, 36));
        btnCustomers.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnCustomersMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnCustomersMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnCustomersMouseExited(evt);
            }
        });
        btnCustomers.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 12, 8));

        cl.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        cl.setForeground(new java.awt.Color(230, 241, 251));
        cl.setText("Customers");
        btnCustomers.add(cl);

        buildSideBar.add(btnCustomers);

        btnBilling.setBackground(new java.awt.Color(12, 68, 124));
        btnBilling.setMaximumSize(new java.awt.Dimension(225, 36));
        btnBilling.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnBillingMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnBillingMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnBillingMouseExited(evt);
            }
        });
        btnBilling.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 12, 8));

        bl.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        bl.setForeground(new java.awt.Color(230, 241, 251));
        bl.setText("Billing");
        btnBilling.add(bl);

        buildSideBar.add(btnBilling);

        btnReceipts.setBackground(new java.awt.Color(12, 68, 124));
        btnReceipts.setMaximumSize(new java.awt.Dimension(225, 36));
        btnReceipts.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnReceiptsMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnReceiptsMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnReceiptsMouseExited(evt);
            }
        });
        btnReceipts.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 12, 8));

        brec.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        brec.setForeground(new java.awt.Color(230, 241, 251));
        brec.setText("Receipts");
        btnReceipts.add(brec);

        buildSideBar.add(btnReceipts);

        btnReports.setBackground(new java.awt.Color(12, 68, 124));
        btnReports.setMaximumSize(new java.awt.Dimension(225, 36));
        btnReports.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnReportsMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnReportsMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnReportsMouseExited(evt);
            }
        });
        btnReports.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 12, 8));

        brep.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        brep.setForeground(new java.awt.Color(230, 241, 251));
        brep.setText("Reports");
        btnReports.add(brep);

        buildSideBar.add(btnReports);

        bodyPanel.add(buildSideBar, java.awt.BorderLayout.LINE_START);

        contentPanel.setBackground(new java.awt.Color(245, 247, 250));
        contentPanel.setLayout(new java.awt.CardLayout());

        dashboardPanel.setBackground(new java.awt.Color(245, 247, 250));
        dashboardPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(14, 14, 14, 14));
        dashboardPanel.setLayout(new java.awt.BorderLayout());

        lblDashboardHeader.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        lblDashboardHeader.setForeground(new java.awt.Color(30, 40, 55));
        lblDashboardHeader.setText("Dashboard");
        lblDashboardHeader.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 12, 0));
        lblDashboardHeader.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        dashboardPanel.add(lblDashboardHeader, java.awt.BorderLayout.PAGE_START);

        dashCenterPanel.setBackground(new java.awt.Color(245, 247, 250));
        dashCenterPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 14, 14, 14));
        dashCenterPanel.setLayout(new javax.swing.BoxLayout(dashCenterPanel, javax.swing.BoxLayout.Y_AXIS));

        summaryRowPanel.setBackground(new java.awt.Color(245, 247, 250));
        summaryRowPanel.setForeground(new java.awt.Color(245, 247, 250));
        summaryRowPanel.setMaximumSize(new java.awt.Dimension(32767, 90));
        summaryRowPanel.setLayout(new java.awt.GridLayout(1, 4, 10, 0));

        cardTotal.setBackground(new java.awt.Color(230, 241, 251));
        cardTotal.setBorder(javax.swing.BorderFactory.createEmptyBorder(12, 14, 12, 14));
        cardTotal.setLayout(new java.awt.BorderLayout());

        lblTotal.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        lblTotal.setForeground(new java.awt.Color(30, 40, 55));
        lblTotal.setText("...");
        cardTotal.add(lblTotal, java.awt.BorderLayout.CENTER);

        statusTotal.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        statusTotal.setForeground(new java.awt.Color(100, 110, 130));
        statusTotal.setText("Total Customers");
        cardTotal.add(statusTotal, java.awt.BorderLayout.PAGE_START);

        summaryRowPanel.add(cardTotal);

        cardPaid.setBackground(new java.awt.Color(234, 243, 222));
        cardPaid.setBorder(javax.swing.BorderFactory.createEmptyBorder(12, 14, 12, 14));
        cardPaid.setLayout(new java.awt.BorderLayout());

        lblPaid.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblPaid.setForeground(new java.awt.Color(100, 110, 130));
        lblPaid.setText("Paid Accounts");
        cardPaid.add(lblPaid, java.awt.BorderLayout.PAGE_START);

        statusPaid.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        statusPaid.setForeground(new java.awt.Color(30, 40, 55));
        statusPaid.setText("...");
        cardPaid.add(statusPaid, java.awt.BorderLayout.CENTER);

        summaryRowPanel.add(cardPaid);

        cardUnpaid.setBackground(new java.awt.Color(252, 235, 235));
        cardUnpaid.setBorder(javax.swing.BorderFactory.createEmptyBorder(12, 14, 12, 14));
        cardUnpaid.setLayout(new java.awt.BorderLayout());

        lblUnpaid.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblUnpaid.setForeground(new java.awt.Color(100, 110, 130));
        lblUnpaid.setText("Unpaid Accounts");
        cardUnpaid.add(lblUnpaid, java.awt.BorderLayout.PAGE_START);

        statusUnpaid.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        statusUnpaid.setForeground(new java.awt.Color(30, 40, 55));
        statusUnpaid.setText("...");
        cardUnpaid.add(statusUnpaid, java.awt.BorderLayout.CENTER);

        summaryRowPanel.add(cardUnpaid);

        cardBalance.setBackground(new java.awt.Color(250, 238, 218));
        cardBalance.setBorder(javax.swing.BorderFactory.createEmptyBorder(12, 14, 12, 14));
        cardBalance.setLayout(new java.awt.BorderLayout());

        lblBalance.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblBalance.setForeground(new java.awt.Color(100, 110, 130));
        lblBalance.setText("Total Balance Due");
        cardBalance.add(lblBalance, java.awt.BorderLayout.PAGE_START);

        statusBalance.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        statusBalance.setForeground(new java.awt.Color(30, 40, 55));
        statusBalance.setText("...");
        cardBalance.add(statusBalance, java.awt.BorderLayout.CENTER);

        summaryRowPanel.add(cardBalance);

        dashCenterPanel.add(summaryRowPanel);

        dashboardPanel.add(dashCenterPanel, java.awt.BorderLayout.CENTER);

        contentPanel.add(dashboardPanel, "dashboard");

        customerPanel.setBackground(new java.awt.Color(245, 247, 250));
        customerPanel.setLayout(new java.awt.GridBagLayout());
        contentPanel.add(customerPanel, "customers");

        billingPanel.setBackground(new java.awt.Color(245, 247, 250));
        billingPanel.setLayout(new java.awt.GridBagLayout());
        contentPanel.add(billingPanel, "billing");

        receiptsPanel.setBackground(new java.awt.Color(245, 247, 250));
        receiptsPanel.setLayout(new java.awt.GridBagLayout());
        contentPanel.add(receiptsPanel, "receipts");

        reportsPanel.setBackground(new java.awt.Color(245, 247, 250));
        reportsPanel.setLayout(new java.awt.GridBagLayout());
        contentPanel.add(reportsPanel, "reports");

        bodyPanel.add(contentPanel, java.awt.BorderLayout.CENTER);

        getContentPane().add(bodyPanel, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnDashboardMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDashboardMouseClicked
    if (activeSideBtn != null) activeSideBtn.setBackground(new java.awt.Color(12, 68, 124));
        activeSideBtn = btnDashboard;
        btnDashboard.setBackground(new java.awt.Color(40, 90, 137));
        
        java.awt.CardLayout cl = (java.awt.CardLayout) contentPanel.getLayout();
        cl.show(contentPanel, "dashboard");
    }//GEN-LAST:event_btnDashboardMouseClicked

    private void btnDashboardMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDashboardMouseEntered
        if (btnDashboard != activeSideBtn) btnDashboard.setBackground(new java.awt.Color(40, 100, 170));
    }//GEN-LAST:event_btnDashboardMouseEntered

    private void btnCustomersMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCustomersMouseExited
        if (btnCustomers != activeSideBtn) {
        btnCustomers.setBackground(new java.awt.Color(12, 68, 124));
    }
    }//GEN-LAST:event_btnCustomersMouseExited

    private void btnCustomersMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCustomersMouseClicked
        if (activeSideBtn != null) activeSideBtn.setBackground(new java.awt.Color(12, 68, 124));
        activeSideBtn = btnCustomers;
        btnCustomers.setBackground(new java.awt.Color(40, 90, 137));
        
        java.awt.CardLayout cl = (java.awt.CardLayout) contentPanel.getLayout();
        cl.show(contentPanel, "customers");
    }//GEN-LAST:event_btnCustomersMouseClicked

    private void btnDashboardMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDashboardMouseExited
        if (btnDashboard != activeSideBtn) btnDashboard.setBackground(new java.awt.Color(12, 68, 124));
    }//GEN-LAST:event_btnDashboardMouseExited

    private void btnCustomersMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCustomersMouseEntered
        if (btnCustomers != activeSideBtn) btnCustomers.setBackground(new java.awt.Color(40, 100, 170));
    }//GEN-LAST:event_btnCustomersMouseEntered

    private void btnBillingMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBillingMouseClicked
        if (activeSideBtn != null) activeSideBtn.setBackground(new java.awt.Color(12, 68, 124));
        activeSideBtn = btnBilling;
        btnBilling.setBackground(new java.awt.Color(40, 90, 137));
        
        java.awt.CardLayout cl = (java.awt.CardLayout) contentPanel.getLayout();
        cl.show(contentPanel, "billing");
    }//GEN-LAST:event_btnBillingMouseClicked

    private void btnBillingMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBillingMouseEntered
        if (btnBilling != activeSideBtn) btnBilling.setBackground(new java.awt.Color(40, 100, 170));
    }//GEN-LAST:event_btnBillingMouseEntered

    private void btnBillingMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBillingMouseExited
        if (btnBilling != activeSideBtn) btnBilling.setBackground(new java.awt.Color(12, 68, 124));
    }//GEN-LAST:event_btnBillingMouseExited

    private void btnReceiptsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnReceiptsMouseClicked
        if (activeSideBtn != null) activeSideBtn.setBackground(new java.awt.Color(12, 68, 124));
        activeSideBtn = btnReceipts;
        btnReceipts.setBackground(new java.awt.Color(40, 90, 137));
        
        java.awt.CardLayout cl = (java.awt.CardLayout) contentPanel.getLayout();
        cl.show(contentPanel, "receipts");
    }//GEN-LAST:event_btnReceiptsMouseClicked

    private void btnReceiptsMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnReceiptsMouseEntered
       if (btnReceipts != activeSideBtn) btnReceipts.setBackground(new java.awt.Color(40, 100, 170));
    }//GEN-LAST:event_btnReceiptsMouseEntered

    private void btnReceiptsMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnReceiptsMouseExited
        if (btnReceipts != activeSideBtn) btnReceipts.setBackground(new java.awt.Color(12, 68, 124));
    }//GEN-LAST:event_btnReceiptsMouseExited

    private void btnReportsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnReportsMouseClicked
        if (activeSideBtn != null) activeSideBtn.setBackground(new java.awt.Color(12, 68, 124));
        activeSideBtn = btnReports;
        btnReports.setBackground(new java.awt.Color(40, 90, 137));
        
        java.awt.CardLayout cl = (java.awt.CardLayout) contentPanel.getLayout();
        cl.show(contentPanel, "reports");
    }//GEN-LAST:event_btnReportsMouseClicked

    private void btnReportsMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnReportsMouseEntered
        if (btnReports != activeSideBtn) btnReports.setBackground(new java.awt.Color(40, 100, 170));
    }//GEN-LAST:event_btnReportsMouseEntered

    private void btnReportsMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnReportsMouseExited
        if (btnReports != activeSideBtn) btnReports.setBackground(new java.awt.Color(12, 68, 124));
    }//GEN-LAST:event_btnReportsMouseExited

    
    
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new ISPBillingSystem().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel billingPanel;
    private javax.swing.JLabel bl;
    private javax.swing.JPanel bodyPanel;
    private javax.swing.JLabel brec;
    private javax.swing.JLabel brep;
    private javax.swing.JPanel btnBilling;
    private javax.swing.JPanel btnCustomers;
    private javax.swing.JPanel btnDashboard;
    private javax.swing.JPanel btnReceipts;
    private javax.swing.JPanel btnReports;
    private javax.swing.JPanel buildSideBar;
    private javax.swing.JPanel buildTopBar;
    private javax.swing.JPanel cardBalance;
    private javax.swing.JPanel cardPaid;
    private javax.swing.JPanel cardTotal;
    private javax.swing.JPanel cardUnpaid;
    private javax.swing.JLabel cl;
    private javax.swing.JPanel contentPanel;
    private javax.swing.JPanel customerPanel;
    private javax.swing.JPanel dashCenterPanel;
    private javax.swing.JPanel dashboardPanel;
    private javax.swing.JLabel dl;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel lblBalance;
    private javax.swing.JLabel lblDashboardHeader;
    private javax.swing.JLabel lblPaid;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JLabel lblUnpaid;
    private javax.swing.JPanel logo;
    private javax.swing.JLabel n;
    private javax.swing.JPanel receiptsPanel;
    private javax.swing.JPanel reportsPanel;
    private javax.swing.JLabel statusBalance;
    private javax.swing.JLabel statusPaid;
    private javax.swing.JLabel statusTotal;
    private javax.swing.JLabel statusUnpaid;
    private javax.swing.JPanel summaryRowPanel;
    private javax.swing.JLabel v;
    // End of variables declaration//GEN-END:variables
}
