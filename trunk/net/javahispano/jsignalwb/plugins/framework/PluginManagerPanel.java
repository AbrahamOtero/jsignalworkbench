/*
 * PluginManagerPanel.java
 *
 * Created on 23 de julio de 2007, 13:38
 */

package net.javahispano.jsignalwb.plugins.framework;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.jar.Attributes;
import java.util.jar.JarFile;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import net.javahispano.jsignalwb.JSWBManager;
import net.javahispano.jsignalwb.plugins.Plugin;

/**
 *
 * @author  Compaq_Propietario
 */
public class PluginManagerPanel extends javax.swing.JPanel {
    private JSWBManager jswbManager;
    private DefaultListModel dlm;
    private HashMap<String, JarFile> pluginJarAssociation;
    private HashMap<JarFile, File> jarFileFileAssociation;
    private JFileChooser jfc;
    private int mode;
    //private TableModelPluginManager dtm;
    private DefaultTableModel dtm;

    /** Creates new form PluginManagerPanel */
    public PluginManagerPanel(JSWBManager jswbManager) {
        this.jswbManager = jswbManager;
//        dlm=new DefaultListModel();
        pluginJarAssociation = new HashMap<String, JarFile>();
        jarFileFileAssociation = new HashMap<JarFile, File>();
        initComponents();
//        jList1.setModel(dlm);
        jfc = null;
        mode = 0;

//        jList1.addListSelectionListener(new ListSelectionListener() {
//            public void valueChanged(ListSelectionEvent lse) {
//                listSelectionChange();
//            }
//        });

        //dtm=new TableModelPluginManager();
        //jTable1.setModel(dtm);
        jTable1.getColumnModel().getColumn(4).setCellEditor(new LoadedTableCellEditorAndRenderer());
        jTable1.getColumnModel().getColumn(4).setCellRenderer(new LoadedTableCellEditorAndRenderer());
        Image image = Toolkit.getDefaultToolkit().createImage(
                PluginManagerPanel.class.getResource("images/more.png"));
        ImageIcon icon = new ImageIcon(
                image.getScaledInstance(12, 12, Image.SCALE_SMOOTH));
        jTable1.getColumnModel().getColumn(5).setCellEditor(new JButtonTableCellEditorAndRenderer(
                icon, "Click for detailed info"));
        jTable1.getColumnModel().getColumn(5).setCellRenderer(new JButtonTableCellEditorAndRenderer(
                icon, "Click for detailed info"));
        Image image2 = Toolkit.getDefaultToolkit().createImage(
                PluginManagerPanel.class.getResource("images/trash.png"));
        ImageIcon icon2 = new ImageIcon(
                image2.getScaledInstance(12, 12, Image.SCALE_SMOOTH));
        jTable1.getColumnModel().getColumn(6).setCellEditor(new JButtonTableCellEditorAndRenderer(
                icon2, "Click to uninstall plugin"));
        jTable1.getColumnModel().getColumn(6).setCellRenderer(new JButtonTableCellEditorAndRenderer(
                icon2, "Click to uninstall plugin"));
        //jTable1.setDefaultRenderer(JButton.class,new JButtonTableCellEditorAndRenderer("..."));
        //setColumnsWidth();
        jTable1.setRowHeight(20);
        refreshJTable();
//        refreshList();


    }

    void refreshJTable() {
        //TableModelPluginManager dtm=(TableModelPluginManager)jTable1.getModel();
        dtm = (DefaultTableModel) jTable1.getModel();
        dtm.setRowCount(0);
        //dtm.clear();

        File[] plugins = jswbManager.getPluginManager().getInstalledPlugins();
        if (plugins != null) {
            JarFile jarFile;
            String name;
            String type;
            for (int index = 0; index < plugins.length; index++) {
                try {
                    jarFile = new JarFile(plugins[index]);
                    name = jarFile.getManifest().getMainAttributes().getValue("PluginName");
                    type = jarFile.getManifest().getMainAttributes().getValue("PluginType");
                    if (jswbManager.getPluginManager().isPluginRegistered(type, name)) {
                        //dlm.addElement(name);
                        pluginJarAssociation.put(type + ":" + name, jarFile);
                        jarFileFileAssociation.put(jarFile, plugins[index]);
                    }
                    jarFile.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            HashMap<String, ArrayList<String>> registeredPlugins =
                    jswbManager.getPluginManager().getRegisteredPlugins();
            Iterator<String> it = registeredPlugins.keySet().iterator();
            ArrayList<String> temp;
            String kind;
            while (it.hasNext()) {
                kind = it.next();
                if (mode == 0 || mode == 8
                    || (kind.equals("loader") && mode == 1)
                    || (kind.equals("saver") && mode == 2)
                    || (kind.equals("algorithm") && mode == 3)
                    || (kind.equals("generic") && mode == 4)
                    || (kind.equals("grid") && mode == 5)
                    || (kind.equals("mark") && mode == 6)
                    || (kind.equals("annotation") && mode == 7)) {
                    temp = registeredPlugins.get(kind);
                    for (String pluginName : temp) {
                        if (mode != 8 || pluginJarAssociation.containsKey(kind + ":" + pluginName)) {
                            if (jswbManager.getPluginManager().isPluginLoaded(kind + ":" + pluginName)) {
                                //loadButton.setEnabled(false);
                                //detailsButton.setEnabled(true);
                                Plugin plug = jswbManager.getPluginManager().getPlugin(kind + ":" + pluginName);
                                JButton bt = new JButton(new PluginDetailAction(kind + ":" + pluginName, this));
                                JButton uninstall = null;
                                if (pluginJarAssociation.containsKey(kind + ":" + pluginName)) {
                                    JarFile jar = pluginJarAssociation.get(kind + ":" + pluginName);
                                    File file = jarFileFileAssociation.get(jar);
                                    uninstall = new JButton(new PluginUninstallAction(file, kind + ":" + pluginName, this));
                                }
                                //bt.setFocusable(false);
                                dtm.addRow(new Object[] {pluginName, kind,
                                           plug.getPluginVersion(),
                                           plug.getShortDescription(), new JLabel("OK"), bt, uninstall});

//                            if(pluginJarAssociation.containsKey(name))
//                                jButton4.setEnabled(true);
//                            else
//                                jButton4.setEnabled(false);
                            } else {
//                                loadButton.setEnabled(true);
//                                detailsButton.setEnabled(false);
                                if (pluginJarAssociation.containsKey(kind + ":" + pluginName)) {
                                    //jButton4.setEnabled(true);
                                    try {
                                        Attributes att =
                                                pluginJarAssociation.get(kind + ":" + pluginName).getManifest().
                                                getMainAttributes();

                                        String version = att.getValue("PluginVersion");
                                        if (version == null) {
                                            version = "Try to load";
                                        }
                                        String shortDesc =
                                                att.getValue("PluginShortDescription");
                                        if (shortDesc == null) {
                                            shortDesc = "Try to load";
                                        }
                                        JarFile jar = pluginJarAssociation.get(kind + ":" + pluginName);
                                        File file = jarFileFileAssociation.get(jar);
                                        JButton uninstall = new JButton(new PluginUninstallAction(file,
                                                kind + ":" + pluginName, this));
                                        dtm.addRow(new Object[] {pluginName, kind,
                                                version,
                                                shortDesc,
                                                new JButton(new PluginLoadAction(kind + ":" + pluginName, this)),
                                                new JButton(new PluginDetailAction(kind + ":" + pluginName, this)),
                                                uninstall});
                                    } catch (IOException ex) {
                                        ex.printStackTrace();
                                    }
                                } else {
                                    //jButton4.setEnabled(false);
                                    dtm.addRow(new Object[] {pluginName, kind,
                                               "Try to load",
                                               "Try to load",
                                               new JButton(new PluginLoadAction(kind + ":" + pluginName, this)),
                                               new JButton(new PluginDetailAction(kind + ":" + pluginName, this)), null});
                                }
                            }
                        }
                    }
                }
            }
        }
    }


    private void setColumnsWidth() {
        jTable1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        jScrollPane2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        int width = jScrollPane2.getSize().width;
        jTable1.getColumnModel().getColumn(0).setPreferredWidth(150);
        jTable1.getColumnModel().getColumn(1).setPreferredWidth(75);
        jTable1.getColumnModel().getColumn(2).setPreferredWidth(65);

        jTable1.getColumnModel().getColumn(3).setPreferredWidth(width - 381);
        jTable1.getColumnModel().getColumn(4).setPreferredWidth(29);
        jTable1.getColumnModel().getColumn(5).setPreferredWidth(24);
        jTable1.getColumnModel().getColumn(6).setPreferredWidth(20);

    }

    /*private void refreshList(){
        File[] plugins=jswbManager.getPluginManager().getInstalledPlugins();
        if(plugins!=null){
            JarFile jarFile;
            String name;
            String type;
            dlm.clear();
            for(int index=0;index<plugins.length;index++){
                try {
                    jarFile=new JarFile(plugins[index]);
                    name=jarFile.getManifest().getMainAttributes().getValue("PluginName");
                    type=jarFile.getManifest().getMainAttributes().getValue("PluginType");
                    if(jswbManager.getPluginManager().isPluginRegistered(type,name)){
                        //dlm.addElement(name);
                        pluginJarAssociation.put(type+":"+name,jarFile);
                        jarFileFileAssociation.put(jarFile,plugins[index]);
                    }
                    jarFile.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            HashMap<String,ArrayList<String>> registeredPlugins =
                    jswbManager.getPluginManager().getRegisteredPlugins();
            Iterator<String> it=registeredPlugins.keySet().iterator();
            ArrayList<String> temp;
            String kind;
            while(it.hasNext()){
                kind=it.next();
                if(mode==0 || mode == 8
                        ||(kind.equals("loader") && mode ==1)
                        ||(kind.equals("saver") && mode ==2)
                        ||(kind.equals("algorithm") && mode ==3)
                        ||(kind.equals("generic") && mode ==4)
                        ||(kind.equals("grid") && mode ==5)
                        ||(kind.equals("mark") && mode ==6)
                        ||(kind.equals("annotation") && mode ==7)){
                    temp=registeredPlugins.get(kind);
                    for(String pluginName:temp){
                        if(mode!=8 || pluginJarAssociation.containsKey(kind+":"+pluginName))
                            dlm.addElement(kind+":"+pluginName);
                    }
                }
            }
        }
         }
         private void listSelectionChange(){
        Object selected=jList1.getSelectedValue();
        if(selected==null){
            jLabel1.setText("Not Selected");
            jLabel2.setText("Not Selected");
            jLabel3.setText("Not Selected");
            jLabel4.setText("Not Selected");
            jLabel5.setText("Not Selected");
            jLabel6.setText("Not Selected");
            jButton4.setEnabled(false);
            loadButton.setEnabled(false);
            detailsButton.setEnabled(false);
        }else{
            String name=selected.toString();

            if(jswbManager.getPluginManager().isPluginLoaded(name)){
                loadButton.setEnabled(false);
                detailsButton.setEnabled(true);
                Plugin plug=jswbManager.getPluginManager().getPlugin(name);
                jLabel1.setText(plug.getName());
                jLabel2.setText(plug.getPluginVersion());
                jLabel3.setText(plug.getClass().getCanonicalName());
                jLabel4.setText(plug.getShortDescription());
                jLabel5.setText(name.substring(0,name.indexOf(":")));
                jLabel6.setText("Loaded");
                if(pluginJarAssociation.containsKey(name))
                    jButton4.setEnabled(true);
                else
                    jButton4.setEnabled(false);
            }else{
                loadButton.setEnabled(true);
                detailsButton.setEnabled(false);
                if(pluginJarAssociation.containsKey(name)){
                    jButton4.setEnabled(true);
                    try {
                        Attributes att=pluginJarAssociation.get(name).getManifest().getMainAttributes();
                        jLabel1.setText(att.getValue("PluginName"));
                        jLabel2.setText(att.getValue("PluginVersion"));
                        jLabel3.setText(att.getValue("PluginBaseClass"));
                        jLabel4.setText(att.getValue("PluginShortDescription"));
                        jLabel5.setText(att.getValue("PluginType"));
                        jLabel6.setText("Not Loaded");
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }else{
                    jButton4.setEnabled(false);
                    jLabel1.setText(name);
                    jLabel2.setText("Need to load the plugin");
                    jLabel3.setText("Need to load the plugin");
                    jLabel4.setText("Need to load the plugin");
                    jLabel5.setText("Need to load the plugin");
                    jLabel6.setText("Not Loaded");
                }
            }
        }
         }*/
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Codigo Generado  ">//GEN-BEGIN:initComponents
    private void initComponents() {
        buttonGroup1 = new javax.swing.ButtonGroup();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        rbAll = new javax.swing.JRadioButton();
        rbLoaders = new javax.swing.JRadioButton();
        rbSavers = new javax.swing.JRadioButton();
        rbAlgorithms = new javax.swing.JRadioButton();
        rbGrids = new javax.swing.JRadioButton();
        rbGenerics = new javax.swing.JRadioButton();
        rbMarks = new javax.swing.JRadioButton();
        rbAnnotations = new javax.swing.JRadioButton();
        rbFromJarFile = new javax.swing.JRadioButton();

        setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(255,
                153, 0), 5, true), "Plugin Manager"));
        jButton2.setText("Search new plugins");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Close");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][] {

        },
                new String[] {
                "Name", "Kind", "Version", "ShortDescription", "Load", "Info", ""
        }
                ) {
            Class[] types = new Class[] {
                            java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class,
                            java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean[] {
                                false, false, false, false, true, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        jScrollPane2.setViewportView(jTable1);

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        buttonGroup1.add(rbAll);
        rbAll.setText("All");
        rbAll.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        rbAll.setMargin(new java.awt.Insets(0, 0, 0, 0));
        rbAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonsActionPerformed(evt);
            }
        });

        buttonGroup1.add(rbLoaders);
        rbLoaders.setText("Loaders");
        rbLoaders.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        rbLoaders.setMargin(new java.awt.Insets(0, 0, 0, 0));
        rbLoaders.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonsActionPerformed(evt);
            }
        });

        buttonGroup1.add(rbSavers);
        rbSavers.setText("Savers");
        rbSavers.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        rbSavers.setMargin(new java.awt.Insets(0, 0, 0, 0));
        rbSavers.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonsActionPerformed(evt);
            }
        });

        buttonGroup1.add(rbAlgorithms);
        rbAlgorithms.setText("Algorithms");
        rbAlgorithms.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        rbAlgorithms.setMargin(new java.awt.Insets(0, 0, 0, 0));
        rbAlgorithms.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonsActionPerformed(evt);
            }
        });

        buttonGroup1.add(rbGrids);
        rbGrids.setText("Grids");
        rbGrids.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        rbGrids.setMargin(new java.awt.Insets(0, 0, 0, 0));
        rbGrids.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonsActionPerformed(evt);
            }
        });

        buttonGroup1.add(rbGenerics);
        rbGenerics.setText("Generics");
        rbGenerics.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        rbGenerics.setMargin(new java.awt.Insets(0, 0, 0, 0));
        rbGenerics.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonsActionPerformed(evt);
            }
        });

        buttonGroup1.add(rbMarks);
        rbMarks.setText("Marks");
        rbMarks.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        rbMarks.setMargin(new java.awt.Insets(0, 0, 0, 0));
        rbMarks.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonsActionPerformed(evt);
            }
        });

        buttonGroup1.add(rbAnnotations);
        rbAnnotations.setText("Annotations");
        rbAnnotations.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        rbAnnotations.setMargin(new java.awt.Insets(0, 0, 0, 0));
        rbAnnotations.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonsActionPerformed(evt);
            }
        });

        buttonGroup1.add(rbFromJarFile);
        rbFromJarFile.setText("From Jar File");
        rbFromJarFile.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        rbFromJarFile.setMargin(new java.awt.Insets(0, 0, 0, 0));
        rbFromJarFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                          .addContainerGap()
                          .addComponent(rbAll)
                          .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                          .addComponent(rbLoaders)
                          .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                          .addComponent(rbSavers)
                          .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                          .addComponent(rbAlgorithms)
                          .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                          .addComponent(rbGrids)
                          .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                          .addComponent(rbGenerics)
                          .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                          .addComponent(rbMarks)
                          .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                          .addComponent(rbAnnotations)
                          .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                          .addComponent(rbFromJarFile)
                          .addContainerGap(136, Short.MAX_VALUE))
                );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                          .addContainerGap()
                          .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(rbAll)
                                    .addComponent(rbLoaders)
                                    .addComponent(rbSavers)
                                    .addComponent(rbAlgorithms)
                                    .addComponent(rbGrids)
                                    .addComponent(rbGenerics)
                                    .addComponent(rbMarks)
                                    .addComponent(rbAnnotations)
                                    .addComponent(rbFromJarFile))
                          .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                          .addContainerGap()
                          .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING,
                                                  javax.swing.GroupLayout.DEFAULT_SIZE, 699, Short.MAX_VALUE)
                                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                  javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(layout.createSequentialGroup()
                                              .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 183,
                javax.swing.GroupLayout.PREFERRED_SIZE)
                                              .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 425,
                Short.MAX_VALUE)
                                              .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 91,
                javax.swing.GroupLayout.PREFERRED_SIZE)))
                          .addContainerGap())
                );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                          .addContainerGap()
                          .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                          .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                          .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 346,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                          .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                          .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jButton2)
                                    .addComponent(jButton3))
                          .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                );
    } // </editor-fold>//GEN-END:initComponents

    private void jRadioButtonsActionPerformed(java.awt.event.ActionEvent evt) { //GEN-FIRST:event_jRadioButtonsActionPerformed
        if (evt.getSource().equals(rbAll)) {
            mode = 0;
        }
        if (evt.getSource().equals(rbLoaders)) {
            mode = 1;
        }
        if (evt.getSource().equals(rbSavers)) {
            mode = 2;
        }
        if (evt.getSource().equals(rbAlgorithms)) {
            mode = 3;
        }
        if (evt.getSource().equals(rbGenerics)) {
            mode = 4;
        }
        if (evt.getSource().equals(rbGrids)) {
            mode = 5;
        }
        if (evt.getSource().equals(rbMarks)) {
            mode = 6;
        }
        if (evt.getSource().equals(rbAnnotations)) {
            mode = 7;
        }
        if (evt.getSource().equals(rbFromJarFile)) {
            mode = 8;
        }
        refreshJTable();
//        refreshList();
    } //GEN-LAST:event_jRadioButtonsActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) { //GEN-FIRST:event_jButton2ActionPerformed
        if (jfc == null) {
            jfc = new JFileChooser(".");
        }
        jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        jfc.setDialogTitle("Select the directory that contents the plugins");
        if (jfc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = jfc.getSelectedFile();
            if (file.isDirectory()) {
                jswbManager.getPluginManager().searchPlugins(file.getAbsolutePath());
                JOptionPane.showMessageDialog(this, "Valid plugins from " + file.getAbsolutePath() + " installed");
            }
        }
        refreshJTable();
    } //GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) { //GEN-FIRST:event_jButton3ActionPerformed
        hideJWindow();
    } //GEN-LAST:event_jButton3ActionPerformed

    public void showJWindow(Window owner) {
        jw = new JWindow(owner);
        //jw.setLocation(GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint());
        jw.add(this);
        jw.setSize(this.getPreferredSize());
        jw.setLocationRelativeTo(owner);

        jw.setVisible(true);
        setColumnsWidth();
    }

    private void hideJWindow() {
        jw.dispose();
    }

    // Declaracion de varibales -no modificar//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JRadioButton rbAlgorithms;
    private javax.swing.JRadioButton rbAll;
    private javax.swing.JRadioButton rbAnnotations;
    private javax.swing.JRadioButton rbFromJarFile;
    private javax.swing.JRadioButton rbGenerics;
    private javax.swing.JRadioButton rbGrids;
    private javax.swing.JRadioButton rbLoaders;
    private javax.swing.JRadioButton rbMarks;
    private javax.swing.JRadioButton rbSavers;
    // Fin de declaracion de variables//GEN-END:variables
    private JWindow jw;
}
