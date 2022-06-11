package GUI;

import java.awt.Image;
import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import javax.swing.table.JTableHeader;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class AdminGUI extends javax.swing.JFrame {
    Srevers S;
    User AdminUser;
    String dataStr="";
    ArrayList<User> users;
    ArrayList<User> usersWithId;//serch result
    ArrayList<User> usersWithName;//serch result 
    int pageNumber = 1;
    int totalPage = 0;

    ArrayList<User> Alluser;//=new ArrayList<>();

    public AdminGUI(Srevers T) {
        initComponents();
        S=T;
        JTableHeader theader = jTable1.getTableHeader();
        theader.setFont(jTextField3.getFont());
        try {
            InputStream is;
            dataStr = "";
            String str = S.getGetUsersServer();
            URL url = new URL(str);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setDoOutput(true);
            con.setDoInput(true);
            con.setUseCaches(false);
            BufferedOutputStream out = new BufferedOutputStream(con.getOutputStream());
            out.write(dataStr.getBytes());
            out.flush();
            out.close();
            String SS = "";
            int b = -1;
            is = con.getInputStream();

            while ((b = is.read()) != -1) {
                SS = SS + (char) b;
            }
            String[] n = SS.split("\\^\\^");
            Alluser = new ArrayList<User>();
            
            for (int i = 0; i < n.length; i++) {
                
                String[] arr2 = n[i].split("\\$\\$");
                User user = new User();
                user.setID(Integer.parseInt(arr2[0]));
                user.setName(arr2[1]);
                user.setEmail(arr2[2]);
                user.setAddress(arr2[3]);
                user.setPichPath("C:\\xampp\\htdocs\\HW2\\" + arr2[4]);
                Alluser.add(user);
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        //displayUsers(Alluser);
        initDefaultValue(Alluser);
    }

    public void initDefaultValue(ArrayList<User> U) {
        
        int totalRows = U.size();
        double dblTotPage = Math.ceil(totalRows / 5.0);
        totalPage = (int) dblTotPage;
        if (pageNumber == 1) {
            btnFirst.setEnabled(false);
            btnPrevious.setEnabled(false);
        } else {
            btnFirst.setEnabled(true);
            btnPrevious.setEnabled(true);
        }
        if (pageNumber == totalPage) {
            btnNext.setEnabled(false);
            btnLast.setEnabled(false);
        } else {
            btnNext.setEnabled(true);
            btnLast.setEnabled(true);
        }
        jLabel4.setText(String.valueOf(pageNumber));
        int index =5*(pageNumber-1);
        DefaultTableModel model1 = (DefaultTableModel)jTable1.getModel();
        model1.setRowCount(0);
        int max=index+5;
        if (max>U.size()){
            max=U.size();
        }
        
        //Icon UserIcon = ResizeImage("text.png");
        /*String[] columnNames = {"#", "ID", "Name", "Email", "Address", "Image"};
        Object[][] data =new Object[5][6];*/
       /* Object[][] data = {
                    {index+1, U.get(index+1).getID(),U.get(index+1).getName(),U.get(index+1).getEmail(),U.get(index+1).getAddress(), ResizeImage("text.png")},
                    {index+2, U.get(index+2).getID(),U.get(index+2).getName(),U.get(index+1).getEmail(),U.get(index+1).getAddress(), ResizeImage("text.png")},
                    {index+3, U.get(index+3).getID(),U.get(index+3).getName(),U.get(index+1).getEmail(),U.get(index+1).getAddress(), ResizeImage("text.png")},
                    {index+4, U.get(index+4).getID(),U.get(index+4).getName(),U.get(index+1).getEmail(),U.get(index+1).getAddress(), ResizeImage("text.png")},
                    {index+5, U.get(index+5).getID(),U.get(index+5).getName(),U.get(index+1).getEmail(),U.get(index+1).getAddress(), ResizeImage("text.png")}
                };*/
       /* if ( index < max){
        for (int i = index ; i < index + 5 ; i++ ){

            data[i][0] = i + 1;
            data[i][1] = U.get(i).getID();
            data[i][2] = U.get(i).getName();
            data[i][3] = U.get(i).getEmail();
            data[i][4] = U.get(i).getAddress();
            String[] w = U.get(i).getPichPath().split("\\\\");
            int x = w.length - 1;
            data[i][5] = ResizeImage(w[x]);
        }
        }else {
                data[4][0] = 0 + 1;
                data[4][1] = " - ";
                data[4][2] = " - ";
                data[4][3] = " - ";
                data[4][4] = " - ";
                data[4][5] = " - "; 
        }*/
            for (int i = index ; i < max ; i++ ){
            Vector v = new Vector();
            v.add(i + 1);
            v.add(U.get(i).getID());
            v.add(U.get(i).getName());
            v.add(U.get(i).getEmail());
            v.add(U.get(i).getAddress());
            v.add(U.get(i).getPichPath());
            model1.addRow(v);
            //jTable1.setModel(model1);
        }
            //jTable1.setModel(model1);

        /*DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            public Class getColumnClass(int column) {
                return getValueAt(0, column).getClass();
            }
        };*/
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        
        jTable1.setDefaultRenderer(Object.class, centerRenderer);
        jTable1.setDefaultRenderer(Integer.class, centerRenderer);
        

    }
    public void addParameter(String N, String V) {

        if (N == null || V == null || N.length() == 0 || V.length() == 0) {
            return;
        }
        if (dataStr.length() > 0) {
            dataStr += "&";
        }
        try {
            dataStr += N + "=" + URLEncoder.encode(V, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            JOptionPane.showMessageDialog(rootPane, e);
        }

    }

    public ImageIcon ResizeImage(String ImagePath) {
        ImageIcon NewImage = new ImageIcon(ImagePath);
        Image IMG = NewImage.getImage();
        Image NewIMG = IMG.getScaledInstance(130, 100, Image.SCALE_SMOOTH);
        ImageIcon image = new ImageIcon(NewIMG);
        return image;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        jTextField3 = new javax.swing.JTextField();
        jComboBox3 = new javax.swing.JComboBox<>();
        jSeparator2 = new javax.swing.JSeparator();
        jPanel15 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        btnFirst = new javax.swing.JButton();
        btnLast = new javax.swing.JButton();
        btnPrevious = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel8.setBackground(new java.awt.Color(56, 56, 56));

        jLabel1.setFont(new java.awt.Font("Goudy Old Style", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Admin MODE");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap(737, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel14.setBackground(new java.awt.Color(6, 97, 99));

        jTextField3.setBackground(new java.awt.Color(6, 97, 99));
        jTextField3.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jTextField3.setForeground(new java.awt.Color(255, 255, 255));
        jTextField3.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField3.setText("Search");
        jTextField3.setBorder(null);
        jTextField3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jTextField3MousePressed(evt);
            }
        });

        jComboBox3.setBackground(new java.awt.Color(6, 97, 99));
        jComboBox3.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jComboBox3.setForeground(new java.awt.Color(255, 255, 255));
        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "BY ID", "BY NAME" }));
        jComboBox3.setBorder(null);

        jPanel15.setBackground(new java.awt.Color(6, 97, 99));
        jPanel15.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        jPanel15.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jLabel9.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("GO");
        jLabel9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel9MousePressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
        );

        jPanel10.setBackground(new java.awt.Color(6, 97, 99));

        btnFirst.setText("<<");
        btnFirst.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnFirst.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnFirstMousePressed(evt);
            }
        });

        btnLast.setText(">>");
        btnLast.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnLast.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnLastMousePressed(evt);
            }
        });

        btnPrevious.setText("<");
        btnPrevious.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnPrevious.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnPreviousMousePressed(evt);
            }
        });

        btnNext.setText(">");
        btnNext.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnNext.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnNextMousePressed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Goudy Old Style", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("1");
        jLabel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnFirst, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnPrevious)
                .addGap(3, 3, 3)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnNext)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnLast, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(216, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnFirst, javax.swing.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE)
                        .addComponent(btnPrevious, javax.swing.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE))
                    .addComponent(btnLast, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnNext, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jSeparator2)
                    .addComponent(jTextField3, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel14Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jPanel15, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(449, 449, 449))
        );

        jPanel4.setBackground(new java.awt.Color(6, 97, 99));

        jTable1.setBackground(new java.awt.Color(6, 97, 99));
        jTable1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        jTable1.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jTable1.setForeground(new java.awt.Color(255, 255, 255));
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "#", "ID", "Name", "Email", "Address", "Image"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                true, true, true, true, true, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_NEXT_COLUMN);
        jTable1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jTable1.setGridColor(new java.awt.Color(255, 255, 255));
        jTable1.setRowHeight(100);
        jTable1.setShowGrid(true);
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 529, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel5.setBackground(new java.awt.Color(6, 97, 99));

        jPanel9.setBackground(new java.awt.Color(6, 97, 99));
        jPanel9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        jPanel9.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jLabel7.setFont(new java.awt.Font("Goudy Old Style", 1, 24)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Log out");
        jLabel7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel7MousePressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE)
        );

        jPanel3.setBackground(new java.awt.Color(6, 97, 99));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));

        jLabel2.setFont(new java.awt.Font("Goudy Old Style", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Delete");
        jLabel2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel2MousePressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 137, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE)
        );

        jPanel6.setBackground(new java.awt.Color(6, 97, 99));
        jPanel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        jPanel6.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jLabel3.setFont(new java.awt.Font("Goudy Old Style", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Show All Useres");
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel3MousePressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 153, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 309, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 47, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(20, 20, 20))
        );

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 887, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 725, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel7MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MousePressed
        dispose();
        LogingGUI logOut = new LogingGUI();
        logOut.setVisible(true);
    }//GEN-LAST:event_jLabel7MousePressed

    private void btnFirstMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnFirstMousePressed
        pageNumber = 1;
        initDefaultValue(Alluser);
    }//GEN-LAST:event_btnFirstMousePressed

    private void btnLastMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLastMousePressed
        pageNumber = totalPage;
        initDefaultValue(Alluser);
    }//GEN-LAST:event_btnLastMousePressed

    private void jLabel2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MousePressed
        int r = jTable1.getSelectedRow();
        String Data = String.valueOf( jTable1.getValueAt(r, 1));
        try {
           InputStream is;
            dataStr = "";
            addParameter("ID", Data);
            String str = S.getDeleteServer();
            URL url = new URL(str);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setDoOutput(true);
            con.setDoInput(true);
            con.setUseCaches(false);
            BufferedOutputStream out = new BufferedOutputStream(con.getOutputStream());
            out.write(dataStr.getBytes());
            out.flush();
            out.close();
            String SS = "";
            int b = -1;
            is = con.getInputStream();

            while ((b = is.read()) != -1) {
                SS = SS + (char) b;
            }
            is.close();
            System.out.println(SS);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        try {
            InputStream is;
            String str = S.getGetUsersServer();
            URL url = new URL(str);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setDoOutput(true);
            con.setDoInput(true);
            con.setUseCaches(false);
            BufferedOutputStream out = new BufferedOutputStream(con.getOutputStream());
            out.write(dataStr.getBytes());
            out.flush();
            out.close();
            String SS = "";
            int b = -1;
            is = con.getInputStream();

            while ((b = is.read()) != -1) {
                SS = SS + (char) b;
            }
            is.close();
            String[] n = SS.split("\\^\\^");
            Alluser = new ArrayList<User>();
            for (int i = 0; i < n.length; i++) {
                String[] arr2 = n[i].split("\\$\\$");
                User user = new User();
                user.setID(Integer.parseInt(arr2[0]));
                user.setName(arr2[1]);
                user.setEmail(arr2[2]);
                user.setAddress(arr2[3]);
                user.setPichPath("C:\\xampp\\htdocs\\HW2\\" + arr2[4]);
                Alluser.add(user);
            }
            initDefaultValue(Alluser);
        } catch (Exception e) {
            System.out.println(e.toString());
        }



        //displayUsers(Alluser);

    }//GEN-LAST:event_jLabel2MousePressed

    private void jLabel9MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel9MousePressed
        String s = jComboBox3.getSelectedItem().toString();
        String search = jTextField3.getText().trim();
        if (s.equals("BY ID")) {
            try {
                InputStream is;
                dataStr = "";
                addParameter("msg", search);
                String str = S.getSearchIdServer();
                URL url = new URL(str);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                con.setDoOutput(true);
                con.setDoInput(true);
                con.setUseCaches(false);
                BufferedOutputStream out = new BufferedOutputStream(con.getOutputStream());
                out.write(dataStr.getBytes());
                out.flush();
                out.close();
                String SS = "";
                int b = -1;
                is = con.getInputStream();

                while ((b = is.read()) != -1) {
                    SS = SS + (char) b;
                }
                is.close();
                String[] q = SS.split("\\^\\^");
                usersWithId = new ArrayList<>();

                for (int i = 0; i < q.length ; i++) {
                    String[] arr2 = q[i].split("\\$\\$");

                    User user = new User();
                    user.setID(Integer.parseInt(arr2[0].trim()));
                    user.setName(arr2[1]);
                    user.setEmail(arr2[2]);
                    user.setAddress(arr2[3]);
                    user.setPichPath("C:\\xampp\\htdocs\\HW2\\" + arr2[4]);
                    usersWithId.add(user);
                }
                pageNumber=1;
                    initDefaultValue(usersWithId);
//                displayUsers(usersWithId);
            } catch (Exception e) {
                System.out.println(e.toString());
            }
        } else if (s.equals("BY NAME")) {
            try {
                InputStream is;
                dataStr = "";
                addParameter("msg", search);
                String str = S.getSearchNameServer();
                URL url = new URL(str);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                con.setDoOutput(true);
                con.setDoInput(true);
                con.setUseCaches(false);
                BufferedOutputStream out = new BufferedOutputStream(con.getOutputStream());
                out.write(dataStr.getBytes());
                out.flush();
                out.close();
                String SS = "";
                int b = -1;
                is = con.getInputStream();

                while ((b = is.read()) != -1) {
                    SS = SS + (char) b;
                }
                is.close();
                String[] q = SS.split("\\^\\^");
                usersWithName = new ArrayList<>();
                for (int i = 0; i < q.length ; i++) {
                    String[] arr2 = q[i].split("\\$\\$");
                    User user = new User();
                    user.setID(Integer.parseInt(arr2[0].trim()));
                    user.setName(arr2[1].trim());
                    user.setEmail(arr2[2]);
                    user.setAddress(arr2[3]);
                    user.setPichPath("C:\\xampp\\htdocs\\HW2\\" + arr2[4]);
                    usersWithName.add(user);
                }
                pageNumber=1;
                    initDefaultValue(usersWithName);
                    //displayUsers(usersWithName);
                
            } catch (Exception e) {
               // System.out.println(e.toString());
            }
        }
    }//GEN-LAST:event_jLabel9MousePressed

    private void jTextField3MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField3MousePressed
        // TODO add your handling code here:
        jTextField3.setText("");
    }//GEN-LAST:event_jTextField3MousePressed

    private void jLabel3MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MousePressed
        // TODO add your handling code here:
        try {
            InputStream is;
            String str = S.getGetUsersServer();
            URL url = new URL(str);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setDoOutput(true);
            con.setDoInput(true);
            con.setUseCaches(false);
            BufferedOutputStream out = new BufferedOutputStream(con.getOutputStream());
            out.write(dataStr.getBytes());
            out.flush();
            out.close();
            String SS = "";
            int b = -1;
            is = con.getInputStream();

            while ((b = is.read()) != -1) {
                SS = SS + (char) b;
            }
            is.close();
            String[] n = SS.split("\\^\\^");
            Alluser = new ArrayList<User>();
            for (int i = 0; i < n.length; i++) {
                String[] arr2 = n[i].split("\\$\\$");
                User user = new User();
                user.setID(Integer.parseInt(arr2[0]));
                user.setName(arr2[1]);
                user.setEmail(arr2[2]);
                user.setAddress(arr2[3]);
                user.setPichPath("C:\\xampp\\htdocs\\HW2\\" + arr2[4]);
                Alluser.add(user);
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        pageNumber=1;
        initDefaultValue(Alluser);
        //displayUsers(Alluser);  
    }//GEN-LAST:event_jLabel3MousePressed

    private void btnPreviousMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPreviousMousePressed
        // TODO add your handling code here:
        if (pageNumber > 1) {
            pageNumber -= 1;
            initDefaultValue(Alluser);
        }
    }//GEN-LAST:event_btnPreviousMousePressed

    private void btnNextMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnNextMousePressed
        // TODO add your handling code here:
        if (pageNumber < totalPage) {
            pageNumber += 1;
            initDefaultValue(Alluser);
        }
    }//GEN-LAST:event_btnNextMousePressed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnFirst;
    private javax.swing.JButton btnLast;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnPrevious;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField3;
    // End of variables declaration//GEN-END:variables

}
