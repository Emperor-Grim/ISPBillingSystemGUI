/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.askal.ispbillingsystem;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 *
 * @author Lorence
 */
public class ISPBillingSystem extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(ISPBillingSystem.class.getName());
    // --colors------------------------------------
    
    static final Color MAIN_BG = new Color(245, 247, 250);
    static final Color CARD_BG = Color.WHITE;
    static final Color BORDER_CLR = new Color(220, 225, 235);
    static final Color TEXT_PRIMARY = new Color(30, 40, 55);
    static final Color TEXT_SECONDARY = new Color(100, 110, 130);
    static final Color BLUE_LIGHT = new Color(230, 241, 251);
    static final Color BLUE_MID = new Color(12, 68, 124);
    static final Color GREEN_LIGHT = new Color(234, 243, 222);
    static final Color GREEN_DARK = new Color(39, 80, 10);
    static final Color AMBER_LIGHT = new Color(250, 238, 218);
    static final Color AMBER_DARK = new Color(99, 56, 6);
    static final Color RED_LIGHT = new Color(252, 235, 235);
    static final Color RED_DARK = new Color(163, 45, 45);
    
    
    //extra declaration needed
    private javax.swing.JPanel activeSideBtn = null;
    
    //database
    CustomerDAO dao = new CustomerDAO();
    
    public ISPBillingSystem() {
        
        initComponents();
        activeSideBtn = btnDashboard;
        btnDashboard.setBackground(new java.awt.Color(40, 90, 137));

        
    //----------------------DASHBOARD MODIFICATION CUSTOM----------------------------------    
        //table Dashboard
        String[] cols = {"ID","Full Name","Address","Plan","Balance","Status"};
        DefaultTableModel model = new DefaultTableModel(cols, 0) {
        public boolean isCellEditable(int r, int c) { return false; }
        };
        
        customerTable = styledTable(model);
        customerScrollPane.setViewportView(customerTable);
        styleStatusColumn(customerTable, 5);
        
        //table Customer Panel
        String[] custCols = {"ID","Full Name","Address","Plan","Balance","Status"};
        DefaultTableModel custModel = new DefaultTableModel(custCols, 0) {
        public boolean isCellEditable(int r, int c) { return false; }
        };
        
        custTable = styledTable(model);
        custScrollPane.setViewportView(custTable);
        styleStatusColumn(custTable, 5);
        
              
        SwingUtilities.invokeLater(() -> {
            for (Customer c2 : dao.getAllCustomers())
            model.addRow(new Object[]{c2.getId(), c2.getFullName(), c2.getAddress(), c2.getPlan(),
            String.format("₱%.2f", c2.getBalance()), c2.getStatus()});
        });
        
        handlerTable.setBorder(BorderFactory.createLineBorder(BORDER_CLR));
        customerScrollPane.setBorder(BorderFactory.createEmptyBorder());
        
        
        //----------------------CUSTOMER MODIFICATION CUSTOM----------------------------------  
        //searchField
        searchField.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(BORDER_CLR), new EmptyBorder(5,8,5,8)));
        searchField.setToolTipText("Search by name...");
        
        refreshDashboard();
        refreshCustomerTable();
    }
    
    //-----------JDialogs----------------------
    void showAddCustomerDialog() {
        CustomerDialog dialog = new CustomerDialog(this, null);
        dialog.setVisible(true);
    if (dialog.isSaved()) {
        dao.addCustomer(dialog.getCustomer());
        refreshCustomerTable();
        refreshDashboard();
    }
}

    void showEditCustomerDialog(Customer c) {
        CustomerDialog dialog = new CustomerDialog(this, c);
        dialog.setVisible(true);
    if (dialog.isSaved()) {
        dao.updateCustomer(dialog.getCustomer());
        refreshCustomerTable();
        refreshDashboard();
    }
}

    //=============other methods===============
    void refreshDashboard() {
        SwingUtilities.invokeLater(() -> {
            statusTotal.setText(String.valueOf(dao.getTotalCustomers()));
            statusPaid.setText(String.valueOf(dao.getPaidCount()));
            statusUnpaid.setText(String.valueOf(dao.getUnpaidCount()));
            statusBalance.setText(String.format("₱%.0f", dao.getTotalBalance()));
        });
    }
    
    void refreshCustomerTable() {
    javax.swing.table.DefaultTableModel model =
        (javax.swing.table.DefaultTableModel) custTable.getModel();
    model.setRowCount(0);
    for (Customer c : dao.getAllCustomers()) {
        model.addRow(new Object[]{
            c.getId(), c.getFullName(), c.getAddress(),
            c.getPlan(), String.format("₱%.2f", c.getBalance()), c.getStatus(), c.getDueDay()
        });
    }
}
    
    void styleStatusColumn(JTable tbl, int col) {
    tbl.getColumnModel().getColumn(col).setCellRenderer(new DefaultTableCellRenderer() {
        @Override
        public Component getTableCellRendererComponent(JTable t, Object v, boolean sel, boolean foc, int r, int c) {
            super.getTableCellRendererComponent(t, v, sel, foc, r, c);
            setHorizontalAlignment(CENTER);
            setFont(new Font("SansSerif", Font.BOLD, 10));
            
            String s = (v != null) ? v.toString() : "";

            if (!sel) {
                setOpaque(true); 
                switch (s) {
                    case "Paid":
                        setBackground(GREEN_LIGHT);
                        setForeground(GREEN_DARK);
                        break; 
                    case "Pending":
                        setBackground(AMBER_LIGHT);
                        setForeground(AMBER_DARK);
                        break; 
                    case "Unpaid":
                        setBackground(RED_LIGHT); 
                        setForeground(RED_DARK);
                        break;
                    case "OVERDUE": 
                        setBackground(new Color(254, 226, 226)); 
                        setForeground(new Color(153, 27, 27));  
                        break;
                    default:
                        setBackground(t.getBackground());
                        setForeground(t.getForeground());
                        break;
                }
            } else {
                setBackground(t.getSelectionBackground());
                setForeground(t.getSelectionForeground());
            }
            return this; 
        }
    });
    }   
    
        JTable styledTable(DefaultTableModel model){
            JTable table = new JTable(model);
            table.setFont(new Font("SansSerif", Font.PLAIN,12));
            table.setRowHeight(28); table.setShowGrid(false); table.setIntercellSpacing(new Dimension(0,0));
            table.setBackground(CARD_BG); table.setSelectionBackground(BLUE_LIGHT); table.setSelectionForeground(TEXT_PRIMARY);
            table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD,11));
            table.getTableHeader().setBackground(MAIN_BG); table.getTableHeader().setForeground(TEXT_SECONDARY);
            table.getTableHeader().setBorder(BorderFactory.createMatteBorder(0,0,1,0, BORDER_CLR));
            return table;
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
        statusTotal = new javax.swing.JLabel();
        lblTotal = new javax.swing.JLabel();
        cardPaid = new javax.swing.JPanel();
        lblPaid = new javax.swing.JLabel();
        statusPaid = new javax.swing.JLabel();
        cardUnpaid = new javax.swing.JPanel();
        lblUnpaid = new javax.swing.JLabel();
        statusUnpaid = new javax.swing.JLabel();
        cardBalance = new javax.swing.JPanel();
        lblBalance = new javax.swing.JLabel();
        statusBalance = new javax.swing.JLabel();
        dSpacer = new javax.swing.JPanel();
        handlerTable = new javax.swing.JPanel();
        tableCard = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        refBtn1 = new javax.swing.JButton();
        customerScrollPane = new javax.swing.JScrollPane();
        customerTable = new javax.swing.JTable();
        customerPanel = new javax.swing.JPanel();
        lblCustomerHeader = new javax.swing.JLabel();
        handlerCustomer = new javax.swing.JPanel();
        custCenterPanel = new javax.swing.JPanel();
        custToolBar = new javax.swing.JPanel();
        searchField = new javax.swing.JTextField();
        addBtn = new javax.swing.JButton();
        editBtn = new javax.swing.JButton();
        delBtn = new javax.swing.JButton();
        payBtn = new javax.swing.JButton();
        refBtn = new javax.swing.JButton();
        custTableHandler = new javax.swing.JPanel();
        custTableCard = new javax.swing.JPanel();
        custScrollPane = new javax.swing.JScrollPane();
        custTable = new javax.swing.JTable();
        billingPanel = new javax.swing.JPanel();
        lblBillingHeader = new javax.swing.JLabel();
        billCenterPanel = new javax.swing.JPanel();
        formCardBorder = new javax.swing.JPanel();
        formCard = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        billFirst = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        billLast = new javax.swing.JTextField();
        JLabel3 = new javax.swing.JLabel();
        billAddr = new javax.swing.JTextField();
        JLabel4 = new javax.swing.JLabel();
        billPlan = new javax.swing.JComboBox<>();
        JLabel5 = new javax.swing.JLabel();
        billPeriod = new javax.swing.JTextField();
        billCenterPanel4 = new javax.swing.JPanel();
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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

        statusTotal.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        statusTotal.setForeground(new java.awt.Color(30, 40, 55));
        statusTotal.setText("...");
        cardTotal.add(statusTotal, java.awt.BorderLayout.LINE_START);

        lblTotal.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblTotal.setForeground(new java.awt.Color(100, 110, 130));
        lblTotal.setText("Total Customers");
        cardTotal.add(lblTotal, java.awt.BorderLayout.PAGE_START);

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

        dSpacer.setBorder(javax.swing.BorderFactory.createEmptyBorder(12, 1, 1, 1));
        dSpacer.setMaximumSize(new java.awt.Dimension(9999, 12));
        dSpacer.setMinimumSize(new java.awt.Dimension(0, 12));
        dSpacer.setOpaque(false);

        javax.swing.GroupLayout dSpacerLayout = new javax.swing.GroupLayout(dSpacer);
        dSpacer.setLayout(dSpacerLayout);
        dSpacerLayout.setHorizontalGroup(
            dSpacerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 940, Short.MAX_VALUE)
        );
        dSpacerLayout.setVerticalGroup(
            dSpacerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        dashCenterPanel.add(dSpacer);

        handlerTable.setBackground(new java.awt.Color(255, 255, 255));
        handlerTable.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(220, 225, 235), 1, true));
        handlerTable.setLayout(new java.awt.BorderLayout(12, 12));

        tableCard.setBackground(new java.awt.Color(255, 255, 255));
        tableCard.setBorder(javax.swing.BorderFactory.createEmptyBorder(12, 12, 12, 12));
        tableCard.setLayout(new java.awt.BorderLayout(12, 12));

        jPanel1.setOpaque(false);
        jPanel1.setLayout(new java.awt.BorderLayout());

        jLabel2.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("All Customers");
        jPanel1.add(jLabel2, java.awt.BorderLayout.WEST);

        refBtn1.setBackground(new java.awt.Color(230, 241, 251));
        refBtn1.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        refBtn1.setForeground(new java.awt.Color(12, 68, 124));
        refBtn1.setText("Refresh");
        refBtn1.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 8, 5, 8));
        refBtn1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        refBtn1.addActionListener(this::refBtn1ActionPerformed);
        jPanel1.add(refBtn1, java.awt.BorderLayout.EAST);

        tableCard.add(jPanel1, java.awt.BorderLayout.NORTH);

        customerScrollPane.setBackground(new java.awt.Color(255, 255, 255));
        customerScrollPane.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        customerTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        customerTable.setShowGrid(false);
        customerScrollPane.setViewportView(customerTable);

        tableCard.add(customerScrollPane, java.awt.BorderLayout.CENTER);

        handlerTable.add(tableCard, java.awt.BorderLayout.CENTER);

        dashCenterPanel.add(handlerTable);

        dashboardPanel.add(dashCenterPanel, java.awt.BorderLayout.CENTER);

        contentPanel.add(dashboardPanel, "dashboard");

        customerPanel.setBackground(new java.awt.Color(245, 247, 250));
        customerPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(14, 14, 14, 14));
        customerPanel.setLayout(new java.awt.BorderLayout());

        lblCustomerHeader.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        lblCustomerHeader.setForeground(new java.awt.Color(30, 40, 55));
        lblCustomerHeader.setText("Customer Management");
        lblCustomerHeader.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 12, 0));
        lblCustomerHeader.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        customerPanel.add(lblCustomerHeader, java.awt.BorderLayout.PAGE_START);

        handlerCustomer.setBackground(new java.awt.Color(255, 255, 255));
        handlerCustomer.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(220, 225, 235), 2, true));
        handlerCustomer.setLayout(new java.awt.BorderLayout());

        custCenterPanel.setBackground(new java.awt.Color(245, 247, 250));
        custCenterPanel.setForeground(new java.awt.Color(30, 40, 55));
        custCenterPanel.setLayout(new java.awt.BorderLayout());

        custToolBar.setBackground(new java.awt.Color(255, 255, 255));
        custToolBar.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 5, 10, 0));
        custToolBar.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 8, 0));

        searchField.setColumns(22);
        searchField.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        searchField.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        searchField.addActionListener(this::searchFieldActionPerformed);
        custToolBar.add(searchField);

        addBtn.setBackground(new java.awt.Color(230, 241, 251));
        addBtn.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        addBtn.setForeground(new java.awt.Color(12, 68, 124));
        addBtn.setText("+ Add");
        addBtn.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 8, 5, 8));
        addBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        addBtn.addActionListener(this::addBtnActionPerformed);
        custToolBar.add(addBtn);

        editBtn.setBackground(new java.awt.Color(255, 255, 255));
        editBtn.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        editBtn.setForeground(new java.awt.Color(30, 40, 55));
        editBtn.setText("Edit");
        editBtn.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 8, 5, 8));
        editBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        editBtn.addActionListener(this::editBtnActionPerformed);
        custToolBar.add(editBtn);

        delBtn.setBackground(new java.awt.Color(252, 235, 235));
        delBtn.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        delBtn.setForeground(new java.awt.Color(163, 45, 45));
        delBtn.setText("Delete");
        delBtn.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 8, 5, 8));
        delBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        delBtn.addActionListener(this::delBtnActionPerformed);
        custToolBar.add(delBtn);

        payBtn.setBackground(new java.awt.Color(234, 243, 222));
        payBtn.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        payBtn.setForeground(new java.awt.Color(39, 80, 10));
        payBtn.setText("Mark Paid");
        payBtn.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 8, 5, 8));
        payBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        payBtn.addActionListener(this::payBtnActionPerformed);
        custToolBar.add(payBtn);

        refBtn.setBackground(new java.awt.Color(255, 255, 255));
        refBtn.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        refBtn.setForeground(new java.awt.Color(30, 40, 55));
        refBtn.setText("Refresh");
        refBtn.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 8, 5, 8));
        refBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        refBtn.addActionListener(this::refBtnActionPerformed);
        custToolBar.add(refBtn);

        custCenterPanel.add(custToolBar, java.awt.BorderLayout.PAGE_START);

        custTableHandler.setBackground(new java.awt.Color(255, 255, 255));
        custTableHandler.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 12, 10, 12));
        custTableHandler.setLayout(new java.awt.BorderLayout());

        custTableCard.setBackground(new java.awt.Color(255, 255, 255));
        custTableCard.setLayout(new java.awt.BorderLayout());

        custScrollPane.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        custTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        custScrollPane.setViewportView(custTable);

        custTableCard.add(custScrollPane, java.awt.BorderLayout.CENTER);

        custTableHandler.add(custTableCard, java.awt.BorderLayout.CENTER);

        custCenterPanel.add(custTableHandler, java.awt.BorderLayout.CENTER);

        handlerCustomer.add(custCenterPanel, java.awt.BorderLayout.CENTER);

        customerPanel.add(handlerCustomer, java.awt.BorderLayout.CENTER);

        contentPanel.add(customerPanel, "customers");

        billingPanel.setBackground(new java.awt.Color(245, 247, 250));
        billingPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(14, 14, 14, 14));
        billingPanel.setForeground(new java.awt.Color(245, 247, 250));
        billingPanel.setLayout(new java.awt.BorderLayout());

        lblBillingHeader.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        lblBillingHeader.setForeground(new java.awt.Color(30, 40, 55));
        lblBillingHeader.setText("Generate Bill");
        lblBillingHeader.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 12, 0));
        lblBillingHeader.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        billingPanel.add(lblBillingHeader, java.awt.BorderLayout.PAGE_START);

        billCenterPanel.setBackground(new java.awt.Color(245, 247, 250));
        billCenterPanel.setLayout(new java.awt.GridLayout(1, 2, 8, 0));

        formCardBorder.setBackground(new java.awt.Color(255, 255, 255));
        formCardBorder.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(220, 225, 235), 1, true));
        formCardBorder.setLayout(new java.awt.BorderLayout());

        formCard.setBackground(new java.awt.Color(255, 255, 255));
        formCard.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEmptyBorder(12, 12, 12, 12), "Bill Details", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("SansSerif", 1, 14), new java.awt.Color(0, 0, 0))); // NOI18N
        formCard.setLayout(new java.awt.GridLayout(7, 2, 8, 0));

        jLabel3.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("First Name:");
        formCard.add(jLabel3);

        billFirst.setColumns(15);
        billFirst.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        formCard.add(billFirst);

        jLabel5.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("Last Name:");
        formCard.add(jLabel5);

        billLast.setColumns(15);
        billLast.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        formCard.add(billLast);

        JLabel3.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        JLabel3.setForeground(new java.awt.Color(0, 0, 0));
        JLabel3.setText("Address: ");
        formCard.add(JLabel3);

        billAddr.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        formCard.add(billAddr);

        JLabel4.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        JLabel4.setForeground(new java.awt.Color(0, 0, 0));
        JLabel4.setText("Plan:");
        formCard.add(JLabel4);

        billPlan.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        billPlan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Basic", "Standard", "Premium", "VIP" }));
        formCard.add(billPlan);

        JLabel5.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        JLabel5.setForeground(new java.awt.Color(0, 0, 0));
        JLabel5.setText("Billing Period");
        formCard.add(JLabel5);

        billPeriod.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        formCard.add(billPeriod);

        formCardBorder.add(formCard, java.awt.BorderLayout.CENTER);

        billCenterPanel.add(formCardBorder);

        billCenterPanel4.setBackground(new java.awt.Color(255, 255, 255));
        billCenterPanel4.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(220, 225, 235), 1, true));
        billCenterPanel4.setLayout(new java.awt.GridLayout());
        billCenterPanel.add(billCenterPanel4);

        billingPanel.add(billCenterPanel, java.awt.BorderLayout.CENTER);

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
        refreshCustomerTable();
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

    private void searchFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchFieldActionPerformed
        String query = searchField.getText().trim().toLowerCase();
        DefaultTableModel m = (DefaultTableModel) custTable.getModel();
        m.setRowCount(0);
        for (Customer c : dao.getAllCustomers()) {
            if (c.getFullName().toLowerCase().contains(query) ||
                c.getAddress().toLowerCase().contains(query)) {
                m.addRow(new Object[]{
                    c.getId(), c.getFullName(), c.getAddress(),
                    c.getPlan(), String.format("₱%.2f", c.getBalance()), c.getStatus()
                });
            }
        }
    }//GEN-LAST:event_searchFieldActionPerformed

    private void editBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editBtnActionPerformed
        int row = custTable.getSelectedRow();
            if (row == -1) { JOptionPane.showMessageDialog(this, "Select a customer first."); return; }
            int id = (int) custTable.getValueAt(row, 0);
        showEditCustomerDialog(dao.getCustomerById(id));
    }//GEN-LAST:event_editBtnActionPerformed

    private void addBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addBtnActionPerformed
        showAddCustomerDialog();
    }//GEN-LAST:event_addBtnActionPerformed

    private void delBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_delBtnActionPerformed
        int row = custTable.getSelectedRow();
            if (row == -1) { JOptionPane.showMessageDialog(this, "Select a customer first."); return; }
            int id = (int) custTable.getValueAt(row, 0);
            int confirm = JOptionPane.showConfirmDialog(this, "Delete this customer?", "Confirm", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
        dao.deleteCustomer(id);
        refreshCustomerTable();
        refreshDashboard();
        }
    }//GEN-LAST:event_delBtnActionPerformed

    private void payBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_payBtnActionPerformed
        int row = custTable.getSelectedRow();
        if (row == -1) { JOptionPane.showMessageDialog(this, "Select a customer first."); return; }
        int id = (int) custTable.getValueAt(row, 0);
        dao.markAsPaid(id);
        refreshCustomerTable();
        refreshDashboard();
    }//GEN-LAST:event_payBtnActionPerformed

    private void refBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refBtnActionPerformed
        searchField.setText("");
        refreshCustomerTable();
        refreshDashboard();
    }//GEN-LAST:event_refBtnActionPerformed

    private void refBtn1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refBtn1ActionPerformed
        searchField.setText("");
        refreshCustomerTable();
        refreshDashboard();
    }//GEN-LAST:event_refBtn1ActionPerformed

    
    
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new ISPBillingSystem().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel JLabel3;
    private javax.swing.JLabel JLabel4;
    private javax.swing.JLabel JLabel5;
    private javax.swing.JButton addBtn;
    private javax.swing.JTextField billAddr;
    private javax.swing.JPanel billCenterPanel;
    private javax.swing.JPanel billCenterPanel4;
    private javax.swing.JTextField billFirst;
    private javax.swing.JTextField billLast;
    private javax.swing.JTextField billPeriod;
    private javax.swing.JComboBox<String> billPlan;
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
    private javax.swing.JPanel custCenterPanel;
    private javax.swing.JScrollPane custScrollPane;
    private javax.swing.JTable custTable;
    private javax.swing.JPanel custTableCard;
    private javax.swing.JPanel custTableHandler;
    private javax.swing.JPanel custToolBar;
    private javax.swing.JPanel customerPanel;
    private javax.swing.JScrollPane customerScrollPane;
    private javax.swing.JTable customerTable;
    private javax.swing.JPanel dSpacer;
    private javax.swing.JPanel dashCenterPanel;
    private javax.swing.JPanel dashboardPanel;
    private javax.swing.JButton delBtn;
    private javax.swing.JLabel dl;
    private javax.swing.JButton editBtn;
    private javax.swing.JPanel formCard;
    private javax.swing.JPanel formCardBorder;
    private javax.swing.JPanel handlerCustomer;
    private javax.swing.JPanel handlerTable;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblBalance;
    private javax.swing.JLabel lblBillingHeader;
    private javax.swing.JLabel lblCustomerHeader;
    private javax.swing.JLabel lblDashboardHeader;
    private javax.swing.JLabel lblPaid;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JLabel lblUnpaid;
    private javax.swing.JPanel logo;
    private javax.swing.JLabel n;
    private javax.swing.JButton payBtn;
    private javax.swing.JPanel receiptsPanel;
    private javax.swing.JButton refBtn;
    private javax.swing.JButton refBtn1;
    private javax.swing.JPanel reportsPanel;
    private javax.swing.JTextField searchField;
    private javax.swing.JLabel statusBalance;
    private javax.swing.JLabel statusPaid;
    private javax.swing.JLabel statusTotal;
    private javax.swing.JLabel statusUnpaid;
    private javax.swing.JPanel summaryRowPanel;
    private javax.swing.JPanel tableCard;
    private javax.swing.JLabel v;
    // End of variables declaration//GEN-END:variables
}
