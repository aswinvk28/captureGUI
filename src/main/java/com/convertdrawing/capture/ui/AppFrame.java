/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.convertdrawing.capture.ui;

import com.convertdrawing.capture.db.DatabaseRequest;
import com.convertdrawing.capture.db.DatabaseRequestFactory;
import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JMenuItem;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 *
 * @author aswin.vijayakumar
 */
public class AppFrame extends javax.swing.JFrame {

    GridBagConstraints gc;
    JPanel fieldPanel;
    private DatabaseRequest request;
    private ArrayList<String> menuList;
    
    private ResultSet choiceResultSet;
    public ArrayList<ArrayList<ComboControl>> comboControlsArray;
    
    /**
     * Creates new form AppFrame
     */
    public AppFrame() throws Exception
    {
        initComponents();
        
        try {
            request = DatabaseRequestFactory.createDatabaseRequest();
        } catch(Exception exception) {
            throw exception;
        }
        
        gc = new GridBagConstraints();
        
        tplMenu1.setVisible(false);
        tplMenu2.setVisible(false);
        tplMenu1Prefix.setVisible(false);
        tplMenu2Prefix.setVisible(false);
        tplMenu1Suffix.setVisible(false);
        tplMenu2Suffix.setVisible(false);
        templateText.setVisible(false);
        
        setWelcomePaneComponents();
        setPreparationPaneComponents();
        setRequirementsPaneComponents();
        
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
    }
    
    public void setWelcomePaneComponents()
    {
        projectCode = new JTextField(10);
        unitCode = new JTextField(10);
        isVerify = new JCheckBox("To Verify", false);
        shortCodeSubmit = new JButton("Create / Verify");
        password = new JPasswordField(10);
        password.setVisible(false);
        
        isVerify.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JCheckBox checkbox = (JCheckBox) e.getComponent();
                if(checkbox.getModel().isSelected()) {
                    checkbox.removeAll();
                    password = new JPasswordField("Password: ", 10);
                    checkbox.add(password);
                } else {
                    checkbox.removeAll();
                }
            }
            @Override
            public void mousePressed(MouseEvent e) {}
            @Override
            public void mouseReleased(MouseEvent e) {}
            @Override
            public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseExited(MouseEvent e) {}
        });
        
        shortCodeSubmit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
            }
            
        });
        
        gc.anchor = GridBagConstraints.FIRST_LINE_START;
        
        welcomePane.add(projectCode, gc);
        gc.anchor = GridBagConstraints.LINE_START;
        welcomePane.add(unitCode, gc);
        gc.anchor = GridBagConstraints.LINE_START;
        welcomePane.add(isVerify, gc);
        gc.anchor = GridBagConstraints.LINE_START;
        welcomePane.add(password, gc);
        gc.anchor = GridBagConstraints.LINE_START;
        welcomePane.add(shortCodeSubmit, gc);
    }
    
    public void setPreparationPaneComponents()
    {
        focalPoints.setName("Click here to list all the focal points...");
        metrics.setName("Click here to list all the metrics...");
        
        intro.setText("As a preparatory guide, whatever you submit here will be sent as a request to "
                + "an external facing web server which processes the request and returns "
                + "needed for generating designs.");
        
        conclude.setText("This is a GUI tool which extracts the requirements from the submissions "
                + "and validates before they are sent to the interface.");
        
        gc.anchor = GridBagConstraints.LINE_START;
        preparationPane.add(intro, gc);
        
        gc.anchor = GridBagConstraints.LINE_START;
        preparationPane.add(focalPoints, gc);
        
        gc.anchor = GridBagConstraints.LINE_START;
        preparationPane.add(metrics, gc);
        
        gc.anchor = GridBagConstraints.LINE_START;
        preparationPane.add(conclude, gc);
    }
    
    public void setRequirementsPaneComponents() throws Exception
    {
        comboControlsArray = new ArrayList<ArrayList<ComboControl>>();
        gc.anchor = GridBagConstraints.LINE_START;
        
        gc.anchor = GridBagConstraints.SOUTHEAST;
        requirementsPane.add(submitRequirements);
        submitRequirements.setVisible(false);
        
        requirementsPane.setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());
        requirementsPane.setAutoscrolls(true);
        
        try {
            DatabaseRequest request = DatabaseRequestFactory.createDatabaseRequest();
            ResultSet menuResult = request.getMenu("menu");
            menuList = new ArrayList<String>();
            while(menuResult.next()) {
                menuList.add(menuResult.getString("value"));
            }
        } catch(SQLException exception) {
            throw exception;
        }
        
        setMenuItems(menuList);
        
        for(short i=0; i<addRequirementDefinition.getMenuComponentCount(); i++) {
            JMenuItem component = (JMenuItem) addRequirementDefinition.getMenuComponent(i);
            component.addActionListener(new ActionListener() {
                public ArrayList<ComboControl> comboControls;
                
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        comboControls = new ArrayList<ComboControl>();
                        boolean proceed = (comboControlsArray.size() == 0);
                        boolean proceed1 = true;
                        boolean proceed2 = true;
                        JLabel label = new JLabel("Requirement Definition");
                        label.setBackground(Color.red);
                        label.setSize(30, 10);
                        ComboControl cmp1 = null, cmp2 = null;
                        if(comboControlsArray.size() > 0) {
                            if(comboControlsArray.get(comboControlsArray.size() - 1).size() > 0) {
                                cmp1 = comboControlsArray.get(comboControlsArray.size() - 1).get(0);
                                if(cmp1.text != null) {
                                    proceed2 = (cmp1.text.getText().length() != 0);
                                } else {
                                    if(comboControlsArray.get(comboControlsArray.size() - 1).size() > 1) {
                                        cmp2 = comboControlsArray.get(comboControlsArray.size() - 1).get(1);
                                    }
                                    if(cmp1 != null && cmp2 != null) {
                                        proceed1 = (cmp1.getText().length() != 0 && cmp2.getText().length() != 0);
                                    }
                                }
                            }
                            
                        }
                        if(proceed || (proceed1 && proceed2)) {
                            try {
                                Choice choice; 
                                JTextField text;
                                JTextField textSuffix;
                                ComboControl comboControl;
                                if(component.getText().toLowerCase().contains("focal")) {
                                    try {
                                        choice = new Choice(); 
                                        text = new JTextField(20);
                                        textSuffix = new JTextField(20);
                                        choiceResultSet = request.getFocalPoints();
                                        choice.setSize(20, 10);
                                        while(choiceResultSet.next()) {
                                            choice.add(choiceResultSet.getString("value"));
                                        }
                                    } catch(Exception exception) {
                                        throw new UnsupportedOperationException(exception.getClass() + ": " + exception.getMessage());
                                    }
                                    comboControl = new ComboControl(text, choice, textSuffix);
                                    ComboControl.addComponents(comboControl);
                                    comboControls.add(comboControl);
                                } else if(component.getText().toLowerCase().contains("metric")) {
                                    try {
                                        choice = new Choice();
                                        text = new JTextField(20); 
                                        textSuffix = new JTextField(20);
                                        choiceResultSet = request.getMetrics();
                                        choice.setSize(20, 10);
                                        while(choiceResultSet.next()) {
                                            choice.add(choiceResultSet.getString("value"));
                                        }
                                    } catch(Exception exception) {
                                        throw new UnsupportedOperationException(exception.getClass() + ": " + exception.getMessage());
                                    }
                                    comboControl = new ComboControl(text, choice, textSuffix);
                                    ComboControl.addComponents(comboControl);
                                    comboControls.add(comboControl);
                                } else if(component.getText().toLowerCase().contains("plan")) {
                                    try {
                                        choice = new Choice();
                                        text = new JTextField(20); 
                                        textSuffix = new JTextField(20);
                                        choiceResultSet = request.getPlans();
                                        choice.setSize(20, 10);
                                        while(choiceResultSet.next()) {
                                            choice.setName(choiceResultSet.getString("value"));
                                            choice.add(choiceResultSet.getString("name"));
                                        }
                                    } catch(Exception exception) {
                                        throw new UnsupportedOperationException(exception.getClass() + ": " + exception.getMessage());
                                    }
                                    comboControl = new ComboControl(text, choice, textSuffix);
                                    ComboControl.addComponents(comboControl);
                                    comboControls.add(comboControl);
                                } else if(component.getText().toLowerCase().contains("region")) {
                                    try {
                                        choice = new Choice();
                                        text = new JTextField(20); 
                                        textSuffix = new JTextField(20);
                                        int regions = request.getRegions();
                                        for(int i=0; i<regions; i++) {
                                            choice.add(Integer.toString(i));
                                        }
                                    } catch(Exception exception) {
                                        throw new UnsupportedOperationException(exception.getClass() + ": " + exception.getMessage());
                                    }
                                    comboControl = new ComboControl(text, choice, textSuffix);
                                    ComboControl.addComponents(comboControl);
                                    comboControls.add(comboControl);
                                }
                                comboControlsArray.add(comboControls);
                            } catch(Exception exception) {
                                throw new UnsupportedOperationException(exception.getClass() + ": " + exception.getMessage());
                            }
                            // triggers
                            if(comboControlsArray.size() >= 2) {
                                ArrayList<ComboControl> comboControlsPrev = comboControlsArray.get(comboControlsArray.size() - 1);
                                ArrayList<ComboControl> comboControlsPrePrev = comboControlsArray.get(comboControlsArray.size() - 2);
                                if((comboControlsPrePrev.size() == 1 && comboControlsPrePrev.get(0).text == null) && 
                                        comboControlsPrev.size() == 1 && comboControlsPrev.get(0).text == null) {
                                    comboControlsArray.remove(comboControlsArray.size() - 1);
                                    comboControlsPrev.add(comboControls.get(0));
                                    comboControlsArray.add(comboControlsPrev);
                                }
                            }
                            bindComponents(comboControlsArray, label);
                        }
                    } catch(Exception exception) {
                        throw new UnsupportedOperationException(exception.getClass() + ": " + exception.getMessage());
                    }
                }
            });
        }
        
        addRequirement.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    boolean proceed = (comboControlsArray.size() == 0);
                    boolean proceed1 = true;
                    boolean proceed2 = true;
                    ComboControl cmp1 = null, cmp2 = null;
                    if(comboControlsArray.size() > 0) {
                        if(comboControlsArray.get(comboControlsArray.size() - 1).size() > 0) {
                            cmp1 = comboControlsArray.get(comboControlsArray.size() - 1).get(0);
                            if(cmp1.text != null) {
                                proceed2 = (cmp1.text.getText().length() != 0);
                            } else {
                                if(comboControlsArray.get(comboControlsArray.size() - 1).size() > 1) {
                                    cmp2 = comboControlsArray.get(comboControlsArray.size() - 1).get(1);
                                }
                                if(cmp1 != null && cmp2 != null) {
                                    proceed1 = (cmp1.getText().length() != 0 && cmp2.getText().length() != 0);
                                }
                            }
                        }
                    }
                    if(proceed || (proceed1 && proceed2)) {
                        JTextField text = new JTextField(80);
                        JLabel label = new JLabel("The Requirement");
                        label.setSize(30, 10);
                        label.setBackground(Color.red);
                        ArrayList<ComboControl> comboControls = new ArrayList<ComboControl>();
                        comboControls.add(new ComboControl(text));
                        comboControlsArray.add(comboControls);
                        bindComponents(comboControlsArray, label);
                    }
                } catch(Exception exception) {
                    throw new UnsupportedOperationException(exception.getClass() + ": " + exception.getMessage());
                }
            }
        });
    }
    
    private void setMenuItems(ArrayList<String> menuList) {
        for(String menu : menuList) {
            JMenuItem menuItem = new JMenuItem();
            menuItem.setText(menu);
            menuItem.setName(menu);
            menuItem.getAccessibleContext().setAccessibleName(menu);
            addRequirementDefinition.add(menuItem);
        }
    }
    
    public JPanel getFieldPanel(ArrayList<ArrayList<ComboControl>> comboControlsArray)
    {
        JPanel fp;
        FlowLayout flow;
        ArrayList<ComboControl> recentComboControl = comboControlsArray.get(comboControlsArray.size() - 1);
        if((recentComboControl.size() == 2) || recentComboControl == null) {
            if(fieldPanel == null) {
                fieldPanel = new JPanel();
                fieldPanel.setPreferredSize(new Dimension(requirementsPane.getWidth(), 30));
                fieldPanel.setMaximumSize(new Dimension(requirementsPane.getWidth(), 40));
                fieldPanel.setMinimumSize(new Dimension(requirementsPane.getWidth(), 20));
                flow = new FlowLayout();
                flow.setHgap(10);
                flow.setVgap(5);
                fieldPanel.setLayout(flow);
                fieldPanel.setSize(requirementsPane.getWidth(), 40);
                requirementsPane.add(fieldPanel, BorderLayout.CENTER);
            }
            return fieldPanel;
        } else {
            fp = new JPanel();
            fp.setPreferredSize(new Dimension(requirementsPane.getWidth(), 30));
            fp.setMaximumSize(new Dimension(requirementsPane.getWidth(), 40));
            fp.setMinimumSize(new Dimension(requirementsPane.getWidth(), 20));
            flow = new FlowLayout();
            flow.setHgap(10);
            flow.setVgap(5);
            fp.setLayout(flow);
            fp.setSize(requirementsPane.getWidth(), 40);
            requirementsPane.add(fp, BorderLayout.WEST);
            return fp;
        }
    }
    
    public void bindComponents(ArrayList<ArrayList<ComboControl>> comboControlsArray, JLabel label) 
    {
        BoxLayout box = new BoxLayout(requirementsPane, BoxLayout.Y_AXIS);
        requirementsPane.setLayout(box);
        fieldPanel = getFieldPanel(comboControlsArray);
        fieldPanel.setSize(requirementsPane.getWidth(), 40);
        if(comboControlsArray.size() > 0) {
            int idx = 0;
            int height = 40;
            ArrayList<ComboControl> comboControlArray = comboControlsArray.get(comboControlsArray.size() - 1);
            if(comboControlArray.size() > 0) {
                for(ComboControl comboControl : comboControlArray) {
                    if(comboControl.text != null) {
                        bindComponents(fieldPanel, comboControl, label);
                    } else {
                        JTextField text = comboControl.newP;
                        JTextField textSuffix = comboControl.newS;
                        Choice choice = comboControl.sF;
                        bindComponents(fieldPanel, text, choice, textSuffix, label);
                    }
                    idx++;
                }
            }
        }
        if(comboControlsArray.size() == 1) {
            submitRequirements.setVisible(true);
        }
    }
    
    public void bindComponents(JPanel fieldPanel, JTextField text, Choice choice, JTextField textSuffix, JLabel label)
    {
        fieldPanel.add(label);
        fieldPanel.add(text, gc);
        fieldPanel.add(choice, gc);
        fieldPanel.add(textSuffix, gc);
    }
    
    public void bindComponents(JPanel fieldPanel, ComboControl ctrl, JLabel label)
    {
        fieldPanel.add(label);
        fieldPanel.add(ctrl.text);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        appTabbedPane = new javax.swing.JTabbedPane();
        welcomePane = new javax.swing.JPanel();
        projectCode = new javax.swing.JTextField();
        unitCode = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        isVerify = new javax.swing.JCheckBox();
        password = new javax.swing.JPasswordField();
        jLabel3 = new javax.swing.JLabel();
        shortCodeSubmit = new javax.swing.JButton();
        preparationPane = new javax.swing.JPanel();
        intro = new java.awt.Label();
        focalPoints = new java.awt.Panel();
        metrics = new java.awt.Panel();
        conclude = new java.awt.Label();
        requirementsPane = new javax.swing.JPanel();
        submitRequirements = new javax.swing.JButton();
        templateText = new javax.swing.JTextField();
        tplMenu1Prefix = new javax.swing.JTextField();
        tplMenu1Suffix = new javax.swing.JTextField();
        tplMenu2Prefix = new javax.swing.JTextField();
        tplMenu2Suffix = new javax.swing.JTextField();
        tplMenu1 = new java.awt.Choice();
        tplMenu2 = new java.awt.Choice();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu3 = new javax.swing.JMenu();
        addRequirement = new javax.swing.JMenuItem();
        addRequirementDefinition = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        welcomePane.setAlignmentX(0.1F);
        welcomePane.setAlignmentY(0.1F);
        welcomePane.setMaximumSize(new java.awt.Dimension(200, 200));

        projectCode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                projectCodeActionPerformed(evt);
            }
        });

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("Project Code");

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("Unit Code");

        isVerify.setText("To Verify");
        isVerify.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                isVerifyActionPerformed(evt);
            }
        });

        jLabel3.setText("Password");

        shortCodeSubmit.setText("Check / Verify");

        javax.swing.GroupLayout welcomePaneLayout = new javax.swing.GroupLayout(welcomePane);
        welcomePane.setLayout(welcomePaneLayout);
        welcomePaneLayout.setHorizontalGroup(
            welcomePaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, welcomePaneLayout.createSequentialGroup()
                .addGap(87, 87, 87)
                .addGroup(welcomePaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(welcomePaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(welcomePaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(isVerify)
                        .addComponent(projectCode, javax.swing.GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE)
                        .addComponent(unitCode)
                        .addComponent(password))
                    .addComponent(shortCodeSubmit))
                .addGap(294, 294, 294))
        );
        welcomePaneLayout.setVerticalGroup(
            welcomePaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(welcomePaneLayout.createSequentialGroup()
                .addGap(51, 51, 51)
                .addGroup(welcomePaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(projectCode, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(18, 18, 18)
                .addGroup(welcomePaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(unitCode, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(isVerify)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(welcomePaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(password, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addComponent(shortCodeSubmit, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(222, Short.MAX_VALUE))
        );

        isVerify.getAccessibleContext().setAccessibleName("isVerify");
        shortCodeSubmit.getAccessibleContext().setAccessibleName("shortCodeSubmit");

        appTabbedPane.addTab("Welcome", welcomePane);
        welcomePane.getAccessibleContext().setAccessibleName("welcomePane");
        welcomePane.getAccessibleContext().setAccessibleParent(appTabbedPane);

        javax.swing.GroupLayout focalPointsLayout = new javax.swing.GroupLayout(focalPoints);
        focalPoints.setLayout(focalPointsLayout);
        focalPointsLayout.setHorizontalGroup(
            focalPointsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        focalPointsLayout.setVerticalGroup(
            focalPointsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout metricsLayout = new javax.swing.GroupLayout(metrics);
        metrics.setLayout(metricsLayout);
        metricsLayout.setHorizontalGroup(
            metricsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        metricsLayout.setVerticalGroup(
            metricsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 23, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout preparationPaneLayout = new javax.swing.GroupLayout(preparationPane);
        preparationPane.setLayout(preparationPaneLayout);
        preparationPaneLayout.setHorizontalGroup(
            preparationPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(preparationPaneLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(preparationPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(intro, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, preparationPaneLayout.createSequentialGroup()
                        .addGroup(preparationPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(conclude, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 614, Short.MAX_VALUE)
                            .addComponent(metrics, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(focalPoints, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())))
        );
        preparationPaneLayout.setVerticalGroup(
            preparationPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(preparationPaneLayout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(intro, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(focalPoints, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(metrics, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(conclude, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(95, Short.MAX_VALUE))
        );

        intro.getAccessibleContext().setAccessibleName("intro");
        focalPoints.getAccessibleContext().setAccessibleName("focalPoints");
        conclude.getAccessibleContext().setAccessibleName("conclude");

        appTabbedPane.addTab("Preparation", preparationPane);
        preparationPane.getAccessibleContext().setAccessibleName("preparationPane");
        preparationPane.getAccessibleContext().setAccessibleParent(appTabbedPane);

        submitRequirements.setText("Submit to Server");
        submitRequirements.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                submitRequirementsActionPerformed(evt);
            }
        });

        tplMenu1Prefix.setText("jTextField1");

        tplMenu1Suffix.setText("jTextField2");

        tplMenu2Prefix.setText("jTextField3");

        tplMenu2Suffix.setText("jTextField4");

        javax.swing.GroupLayout requirementsPaneLayout = new javax.swing.GroupLayout(requirementsPane);
        requirementsPane.setLayout(requirementsPaneLayout);
        requirementsPaneLayout.setHorizontalGroup(
            requirementsPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(requirementsPaneLayout.createSequentialGroup()
                .addGroup(requirementsPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(requirementsPaneLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(tplMenu1Prefix, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(tplMenu1, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tplMenu1Suffix, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(requirementsPaneLayout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(requirementsPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(requirementsPaneLayout.createSequentialGroup()
                                .addComponent(tplMenu2Prefix, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(3, 3, 3)
                                .addComponent(tplMenu2, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(7, 7, 7)
                                .addComponent(tplMenu2Suffix, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(requirementsPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(submitRequirements)
                                .addComponent(templateText, javax.swing.GroupLayout.PREFERRED_SIZE, 543, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        requirementsPaneLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {tplMenu2Prefix, tplMenu2Suffix});

        requirementsPaneLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {tplMenu1Prefix, tplMenu1Suffix});

        requirementsPaneLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {tplMenu1, tplMenu2});

        requirementsPaneLayout.setVerticalGroup(
            requirementsPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(requirementsPaneLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(requirementsPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(requirementsPaneLayout.createSequentialGroup()
                        .addComponent(templateText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(requirementsPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(requirementsPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(tplMenu1Prefix, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(tplMenu1Suffix, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(tplMenu1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(requirementsPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(tplMenu2Prefix, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(tplMenu2Suffix, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(tplMenu2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 332, Short.MAX_VALUE)
                .addComponent(submitRequirements)
                .addGap(44, 44, 44))
        );

        templateText.getAccessibleContext().setAccessibleName("templateText");
        tplMenu1Prefix.getAccessibleContext().setAccessibleName("tplMenu1Prefix");
        tplMenu1Suffix.getAccessibleContext().setAccessibleName("tplMenu1Suffix");
        tplMenu2Prefix.getAccessibleContext().setAccessibleName("tplMenu2Prefix");
        tplMenu2Suffix.getAccessibleContext().setAccessibleName("tplMenu2Suffix");
        tplMenu1.getAccessibleContext().setAccessibleName("tplMenu1");
        tplMenu2.getAccessibleContext().setAccessibleName("tplMenu2");
        tplMenu2.getAccessibleContext().setAccessibleDescription("");

        appTabbedPane.addTab("Requirements", requirementsPane);
        requirementsPane.getAccessibleContext().setAccessibleName("requirementsPane");
        requirementsPane.getAccessibleContext().setAccessibleParent(appTabbedPane);

        jMenu1.setText("File");

        jMenu3.setText("New");

        addRequirement.setText("+ REQ");
        jMenu3.add(addRequirement);
        addRequirement.getAccessibleContext().setAccessibleName("addRequirement");

        addRequirementDefinition.setText("+ REQ DEF");
        jMenu3.add(addRequirementDefinition);
        addRequirementDefinition.getAccessibleContext().setAccessibleName("addRequirementDefinition");

        jMenu1.add(jMenu3);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(appTabbedPane)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(appTabbedPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        appTabbedPane.getAccessibleContext().setAccessibleName("appTabbedPane");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void projectCodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_projectCodeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_projectCodeActionPerformed

    private void isVerifyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_isVerifyActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_isVerifyActionPerformed

    private void submitRequirementsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_submitRequirementsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_submitRequirementsActionPerformed

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
            java.util.logging.Logger.getLogger(AppFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AppFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AppFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AppFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new AppFrame().setVisible(true);
                } catch(Exception exception) {
                    throw new UnsupportedOperationException(exception.getClass() + ": " + exception.getMessage());
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem addRequirement;
    private javax.swing.JMenu addRequirementDefinition;
    private javax.swing.JTabbedPane appTabbedPane;
    private java.awt.Label conclude;
    private java.awt.Panel focalPoints;
    private java.awt.Label intro;
    private javax.swing.JCheckBox isVerify;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private java.awt.Panel metrics;
    private javax.swing.JPasswordField password;
    private javax.swing.JPanel preparationPane;
    private javax.swing.JTextField projectCode;
    private javax.swing.JPanel requirementsPane;
    private javax.swing.JButton shortCodeSubmit;
    private javax.swing.JButton submitRequirements;
    private javax.swing.JTextField templateText;
    private java.awt.Choice tplMenu1;
    private javax.swing.JTextField tplMenu1Prefix;
    private javax.swing.JTextField tplMenu1Suffix;
    private java.awt.Choice tplMenu2;
    private javax.swing.JTextField tplMenu2Prefix;
    private javax.swing.JTextField tplMenu2Suffix;
    private javax.swing.JTextField unitCode;
    private javax.swing.JPanel welcomePane;
    // End of variables declaration//GEN-END:variables

}
