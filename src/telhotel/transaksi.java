/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package telhotel;
import java.util.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Alin
 */
public class transaksi extends javax.swing.JFrame {
   Connection Con;
    ResultSet RsCheckIn;
    Statement stm;
    Double biayakamar=0.0;
    Double totalbiaya=0.0;
    Double uangkembalian=0.0;
    Boolean roomno=true;
    Boolean edit=false;
    String room;
    private Object[][] dataTable = null;
    private String[] header = {"ID Customer","Name","Address","Phone Number","ID CheckIn","Room Number","Room Type","Check In Date","Duration","ID Karyawan","Biaya Kamar"};

    /**
     * Creates new form NewJFrame
     */
    public transaksi() {
        initComponents();
        open_db();
        baca_data();
        aktif(false);
        cmdEdit.setEnabled(false);
        txtRoomType.setEnabled(false);
        txtIDChekIn.setEnabled(false);
        txtRoomNo.setEnabled(false);
        room = txtRoomType.getText();
    }

    void autonumber(){
        try{
            stm = Con.createStatement();
            String sql = "Select max(idcheckin) from check_in";
            ResultSet rs = stm.executeQuery(sql);
            while(rs.next()){
                int a = rs.getInt(1);
                txtIDChekIn.setText(("000"+Integer.toString(a+1)));
            }
        }
        catch(SQLException e)
        {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    private void aktif(boolean x) {
        txtCustomer.setEnabled(x);
        txtIDCus.setEnabled(x);
        txtAddress.setEnabled(x);
        txtPhone.setEnabled(x);
        txtIDKar.setEnabled(x);
        txtLamaInap.setEnabled(x);
        txtCheckinDate.setEnabled(x);
        txtBayar.setEnabled(x);
        txtBiayaKamar.setEnabled(x);
        txtTotal.setEnabled(x);
        btnSubmit.setEnabled(x);
        btnItung.setEnabled(x);
    }
    
    private void clear(){
        txtAddress.setText("");
        txtCheckinDate.setText("");
        txtCustomer.setText("");
        txtIDCus.setText("");
        txtIDKar.setText("");
        txtLamaInap.setText("");
        txtPhone.setText("");
        txtBayar.setText("");
        txtBiayaKamar.setText("");
        txtTotal.setText("");
    }
    
    private void roomNumber(boolean act){
        btnkamar1.setEnabled(act);
        btnkamar2.setEnabled(act);
        btnkamar3.setEnabled(act);
        btnkamar4.setEnabled(act);
        btnkamar5.setEnabled(act);
        btnkamar6.setEnabled(act);
        btnkamar7.setEnabled(act);
        btnkamar8.setEnabled(act);
        btnkamar9.setEnabled(act);
        btnkamar10.setEnabled(act);
    }

    
    private void actButton(Boolean act){
        if(act == true){
            cmdKeluar.setText("Exit");
        }
        else{
            cmdKeluar.setText("Cancel");
        }
    }
    
    private void open_db()
    {
        try{
        KoneksiMysql kon = new KoneksiMysql ("localhost","root","","hotel");
        Con = kon.getConnection();
        }catch (Exception e) {
        System.out.println("Error : "+e);
        }
    }
    
    public void RoomType(String type, int x){
        txtRoomType.setText(type);
    try{
                stm = Con.createStatement();
                RsCheckIn = stm.executeQuery("SELECT RoomNumber FROM check_in WHERE RoomType = '"+type+"' and totalbiaya =! 0.0");
                while(RsCheckIn.next()) {
                    //int roomNo = RsCheckIn.getString("RoomNumber");
                    if(RsCheckIn.getInt("RoomNumber") == 1){
                        btnkamar1.setEnabled(true);
                    }
                    else if(RsCheckIn.getInt("RoomNumber") == 2){
                        btnkamar2.setEnabled(true);
                    }
                    else if(RsCheckIn.getInt("RoomNumber") == 3){
                        btnkamar2.setEnabled(true);
                    }
                    else if(RsCheckIn.getInt("RoomNumber") == 4){
                        btnkamar4.setEnabled(true);
                    }
                    else if(RsCheckIn.getInt("RoomNumber") == 5){
                        btnkamar5.setEnabled(true);
                    }
                    else if(RsCheckIn.getInt("RoomNumber") == 6){
                        btnkamar6.setEnabled(true);
                    }
                    else if(RsCheckIn.getInt("RoomNumber") == 7){
                        btnkamar7.setEnabled(true);
                    }
                    else if(RsCheckIn.getInt("RoomNumber") == 8){
                        btnkamar8.setEnabled(true);
                    }
                    else if(RsCheckIn.getInt("RoomNumber") == 9){
                        btnkamar9.setEnabled(true);
                    }
                    else if(RsCheckIn.getInt("RoomNumber") == 10){
                        btnkamar10.setEnabled(true);
                    }
                                        
                }
    }  
    catch(Exception e){
        System.out.println("Error : "+e);
        // TODO add your handling code here:
        }
    }
    
    private void baca_data(){
        try{
                stm = Con.createStatement();
                RsCheckIn = stm.executeQuery("select * from check_in inner join customer on check_in.idcustomer=customer.idcustomer");
                ResultSetMetaData meta = RsCheckIn.getMetaData();
                int col = meta.getColumnCount();
                int baris = 0;
                while(RsCheckIn.next()) {
                    baris = RsCheckIn.getRow();
                }
                dataTable = new Object[baris][col];
                int x = 0;
                RsCheckIn.beforeFirst();
                while(RsCheckIn.next()) {
                    dataTable[x][0] = RsCheckIn.getString("IDCustomer");
                    dataTable[x][1] = RsCheckIn.getString("name");
                    dataTable[x][2] = RsCheckIn.getString("address");
                    dataTable[x][3] = RsCheckIn.getString("phonenumber");
                    dataTable[x][4] = RsCheckIn.getInt("idcheckin");
                    dataTable[x][5] = RsCheckIn.getInt("RoomNumber");
                    dataTable[x][6] = RsCheckIn.getString("Roomtype");
                    dataTable[x][7] = RsCheckIn.getString("CheckInDate");
                    dataTable[x][8] = RsCheckIn.getInt("LamaInap");
                    dataTable[x][9] = RsCheckIn.getString("IDKaryawan");
                    dataTable[x][10] = RsCheckIn.getDouble("biayakamar");
                    x++;
                }
                tblCheckIn.setModel(new DefaultTableModel(dataTable,header));
        }
        catch(SQLException e)
        {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    private void setField(){
        int row=tblCheckIn.getSelectedRow();
        cmdEdit.setEnabled(true);
        txtIDCus.setText((String)tblCheckIn.getValueAt(row,0));
        txtCustomer.setText((String)tblCheckIn.getValueAt(row,1));
        txtAddress.setText((String)tblCheckIn.getValueAt(row,2));
        txtPhone.setText((String)tblCheckIn.getValueAt(row,3));
        String idcheckin = Integer.toString((Integer)tblCheckIn.getValueAt(row, 4));
        txtIDChekIn.setText(idcheckin);
        String roomNo = Integer.toString((Integer)tblCheckIn.getValueAt(row, 5));
        txtRoomNo.setText(roomNo);   
        txtRoomType.setText((String)tblCheckIn.getValueAt(row,6));
        txtCheckinDate.setText((String)tblCheckIn.getValueAt(row, 7));
        String lama = Integer.toString((Integer)tblCheckIn.getValueAt(row, 8));
        txtLamaInap.setText(lama);
        txtIDKar.setText((String)tblCheckIn.getValueAt(row, 9));
        String biayakamar = Double.toString((Double)tblCheckIn.getValueAt(row, 10));
        txtBiayaKamar.setText(biayakamar);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        lblLogo = new javax.swing.JLabel();
        btnKaryawan = new javax.swing.JButton();
        btnCheckIn = new javax.swing.JButton();
        btnCheckOut = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        btnLogOut = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        btnkamar6 = new javax.swing.JButton();
        btnkamar7 = new javax.swing.JButton();
        btnkamar4 = new javax.swing.JButton();
        btnkamar2 = new javax.swing.JButton();
        btnkamar3 = new javax.swing.JButton();
        btnkamar5 = new javax.swing.JButton();
        btnkamar9 = new javax.swing.JButton();
        btnkamar1 = new javax.swing.JButton();
        btnkamar8 = new javax.swing.JButton();
        btnkamar10 = new javax.swing.JButton();
        btnDuluxe = new javax.swing.JButton();
        btnSuite = new javax.swing.JButton();
        btnSuperior = new javax.swing.JButton();
        btnBatal = new javax.swing.JButton();
        pnlCustomer = new javax.swing.JPanel();
        lblCustomer = new javax.swing.JLabel();
        lblIDCus = new javax.swing.JLabel();
        lblAddress = new javax.swing.JLabel();
        lblPhone = new javax.swing.JLabel();
        txtCustomer = new javax.swing.JTextField();
        txtPhone = new javax.swing.JTextField();
        txtAddress = new javax.swing.JTextField();
        txtIDCus = new javax.swing.JTextField();
        btnSubmit = new javax.swing.JButton();
        pnlCheckIn = new javax.swing.JPanel();
        lblIDRoom = new javax.swing.JLabel();
        lblCheckinDate = new javax.swing.JLabel();
        lblLamaInap = new javax.swing.JLabel();
        txtRoomNo = new javax.swing.JTextField();
        txtLamaInap = new javax.swing.JTextField();
        txtCheckinDate = new javax.swing.JTextField();
        txtIDChekIn = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtIDKar = new javax.swing.JTextField();
        lblIDKar = new javax.swing.JLabel();
        txtRoomType = new javax.swing.JTextField();
        cmdTambah = new javax.swing.JButton();
        cmdEdit = new javax.swing.JButton();
        cmdKeluar = new javax.swing.JButton();
        pnlLogin = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblCheckIn = new javax.swing.JTable();
        pnlBayar = new javax.swing.JPanel();
        txtBiayaKamar = new javax.swing.JTextField();
        txtBayar = new javax.swing.JTextField();
        txtTotal = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        btnItung = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 153, 102));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/hotel/Gambar/KINGHOT.png"))); // NOI18N
        jPanel1.add(lblLogo, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 20, -1, 106));

        btnKaryawan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/hotel/icon/EMPLOYEE.png"))); // NOI18N
        btnKaryawan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKaryawanActionPerformed(evt);
            }
        });
        jPanel1.add(btnKaryawan, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 50, 50, 50));

        btnCheckIn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/hotel/icon/CHECK IN.png"))); // NOI18N
        btnCheckIn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCheckInActionPerformed(evt);
            }
        });
        jPanel1.add(btnCheckIn, new org.netbeans.lib.awtextra.AbsoluteConstraints(1000, 50, 50, 50));

        btnCheckOut.setIcon(new javax.swing.ImageIcon(getClass().getResource("/hotel/icon/CHECK OUT.png"))); // NOI18N
        btnCheckOut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCheckOutActionPerformed(evt);
            }
        });
        jPanel1.add(btnCheckOut, new org.netbeans.lib.awtextra.AbsoluteConstraints(1070, 50, 50, 50));

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/hotel/Gambar/ICONBARU.png"))); // NOI18N
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 20, -1, -1));

        btnLogOut.setIcon(new javax.swing.ImageIcon(getClass().getResource("/hotel/icon/LOG OUT.png"))); // NOI18N
        btnLogOut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLogOutActionPerformed(evt);
            }
        });
        jPanel1.add(btnLogOut, new org.netbeans.lib.awtextra.AbsoluteConstraints(1140, 50, 50, 50));

        jPanel2.setBackground(new java.awt.Color(255, 153, 102));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 854, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 461, Short.MAX_VALUE)
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(1421, 583, -1, -1));

        btnkamar6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/hotel/Gambar/06.png"))); // NOI18N
        btnkamar6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnkamar6MouseClicked(evt);
            }
        });
        btnkamar6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnkamar6ActionPerformed(evt);
            }
        });
        jPanel1.add(btnkamar6, new org.netbeans.lib.awtextra.AbsoluteConstraints(736, 168, 62, 84));

        btnkamar7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/hotel/Gambar/07.png"))); // NOI18N
        btnkamar7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnkamar7MouseClicked(evt);
            }
        });
        btnkamar7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnkamar7ActionPerformed(evt);
            }
        });
        jPanel1.add(btnkamar7, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 168, 60, 85));

        btnkamar4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/hotel/Gambar/04.png"))); // NOI18N
        btnkamar4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnkamar4MouseClicked(evt);
            }
        });
        btnkamar4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnkamar4ActionPerformed(evt);
            }
        });
        jPanel1.add(btnkamar4, new org.netbeans.lib.awtextra.AbsoluteConstraints(578, 167, 60, 85));

        btnkamar2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/hotel/Gambar/02.png"))); // NOI18N
        btnkamar2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnkamar2MouseClicked(evt);
            }
        });
        btnkamar2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnkamar2ActionPerformed(evt);
            }
        });
        jPanel1.add(btnkamar2, new org.netbeans.lib.awtextra.AbsoluteConstraints(422, 167, 60, 85));

        btnkamar3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/hotel/Gambar/03.png"))); // NOI18N
        btnkamar3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnkamar3MouseClicked(evt);
            }
        });
        btnkamar3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnkamar3ActionPerformed(evt);
            }
        });
        jPanel1.add(btnkamar3, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 167, 60, 85));

        btnkamar5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/hotel/Gambar/05.png"))); // NOI18N
        btnkamar5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnkamar5MouseClicked(evt);
            }
        });
        btnkamar5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnkamar5ActionPerformed(evt);
            }
        });
        jPanel1.add(btnkamar5, new org.netbeans.lib.awtextra.AbsoluteConstraints(656, 167, 62, 85));

        btnkamar9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/hotel/Gambar/09.png"))); // NOI18N
        btnkamar9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnkamar9MouseClicked(evt);
            }
        });
        btnkamar9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnkamar9ActionPerformed(evt);
            }
        });
        jPanel1.add(btnkamar9, new org.netbeans.lib.awtextra.AbsoluteConstraints(978, 168, 60, 85));

        btnkamar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/hotel/Gambar/01.png"))); // NOI18N
        btnkamar1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnkamar1MouseClicked(evt);
            }
        });
        btnkamar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnkamar1ActionPerformed(evt);
            }
        });
        jPanel1.add(btnkamar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(344, 167, 60, 85));

        btnkamar8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/hotel/Gambar/08.png"))); // NOI18N
        btnkamar8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnkamar8MouseClicked(evt);
            }
        });
        btnkamar8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnkamar8ActionPerformed(evt);
            }
        });
        jPanel1.add(btnkamar8, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 168, 60, 85));

        btnkamar10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/hotel/Gambar/10.png"))); // NOI18N
        btnkamar10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnkamar10MouseClicked(evt);
            }
        });
        btnkamar10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnkamar10ActionPerformed(evt);
            }
        });
        jPanel1.add(btnkamar10, new org.netbeans.lib.awtextra.AbsoluteConstraints(1056, 168, 61, 85));

        btnDuluxe.setIcon(new javax.swing.ImageIcon(getClass().getResource("/hotel/Gambar/DULUXESIP.png"))); // NOI18N
        btnDuluxe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDuluxeActionPerformed(evt);
            }
        });
        jPanel1.add(btnDuluxe, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 130, 99, 76));

        btnSuite.setIcon(new javax.swing.ImageIcon(getClass().getResource("/hotel/Gambar/SUITESIP.png"))); // NOI18N
        btnSuite.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuiteActionPerformed(evt);
            }
        });
        jPanel1.add(btnSuite, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 130, 101, 161));

        btnSuperior.setIcon(new javax.swing.ImageIcon(getClass().getResource("/hotel/Gambar/SUPERIORSIP.png"))); // NOI18N
        btnSuperior.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuperiorActionPerformed(evt);
            }
        });
        jPanel1.add(btnSuperior, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 210, 99, -1));

        btnBatal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/hotel/Gambar/KELUARSIP.png"))); // NOI18N
        btnBatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBatalActionPerformed(evt);
            }
        });
        jPanel1.add(btnBatal, new org.netbeans.lib.awtextra.AbsoluteConstraints(1135, 168, 90, 92));

        pnlCustomer.setBackground(new java.awt.Color(164, 92, 44));
        pnlCustomer.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Customer", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe Script", 0, 10))); // NOI18N

        lblCustomer.setText("Customer name");

        lblIDCus.setText("ID Customer");

        lblAddress.setText("Address");

        lblPhone.setText("Phone number");

        txtCustomer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCustomerActionPerformed(evt);
            }
        });

        txtPhone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPhoneActionPerformed(evt);
            }
        });

        txtIDCus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIDCusActionPerformed(evt);
            }
        });

        btnSubmit.setText("Submit");
        btnSubmit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSubmitActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlCustomerLayout = new javax.swing.GroupLayout(pnlCustomer);
        pnlCustomer.setLayout(pnlCustomerLayout);
        pnlCustomerLayout.setHorizontalGroup(
            pnlCustomerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCustomerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlCustomerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnSubmit)
                    .addGroup(pnlCustomerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlCustomerLayout.createSequentialGroup()
                            .addComponent(lblPhone)
                            .addGap(10, 10, 10)
                            .addComponent(txtPhone, javax.swing.GroupLayout.DEFAULT_SIZE, 112, Short.MAX_VALUE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlCustomerLayout.createSequentialGroup()
                            .addGroup(pnlCustomerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(lblCustomer)
                                .addComponent(lblAddress)
                                .addComponent(lblIDCus))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(pnlCustomerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(txtCustomer, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 112, Short.MAX_VALUE)
                                .addComponent(txtIDCus, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtAddress)))))
                .addContainerGap(47, Short.MAX_VALUE))
        );
        pnlCustomerLayout.setVerticalGroup(
            pnlCustomerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCustomerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlCustomerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblIDCus)
                    .addComponent(txtIDCus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnlCustomerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblCustomer)
                    .addComponent(txtCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnlCustomerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblAddress)
                    .addComponent(txtAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnlCustomerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblPhone)
                    .addComponent(txtPhone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(btnSubmit)
                .addContainerGap(54, Short.MAX_VALUE))
        );

        jPanel1.add(pnlCustomer, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 300, -1, 320));

        pnlCheckIn.setBackground(new java.awt.Color(164, 92, 44));
        pnlCheckIn.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Check In", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe Script", 0, 10))); // NOI18N

        lblIDRoom.setText("Room No");

        lblCheckinDate.setText("CheckIn Date");

        lblLamaInap.setText("Duration");

        txtRoomNo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtRoomNoActionPerformed(evt);
            }
        });

        txtLamaInap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtLamaInapActionPerformed(evt);
            }
        });

        txtCheckinDate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCheckinDateActionPerformed(evt);
            }
        });

        jLabel1.setText("ID Check In");

        jLabel2.setText("Room Type");

        lblIDKar.setText("Karyawan ID");

        javax.swing.GroupLayout pnlCheckInLayout = new javax.swing.GroupLayout(pnlCheckIn);
        pnlCheckIn.setLayout(pnlCheckInLayout);
        pnlCheckInLayout.setHorizontalGroup(
            pnlCheckInLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCheckInLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlCheckInLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblIDKar)
                    .addComponent(lblLamaInap)
                    .addComponent(lblCheckinDate)
                    .addComponent(jLabel2)
                    .addComponent(lblIDRoom)
                    .addComponent(jLabel1))
                .addGap(21, 21, 21)
                .addGroup(pnlCheckInLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtIDKar, javax.swing.GroupLayout.DEFAULT_SIZE, 112, Short.MAX_VALUE)
                    .addComponent(txtRoomNo)
                    .addComponent(txtRoomType)
                    .addComponent(txtCheckinDate)
                    .addComponent(txtLamaInap)
                    .addComponent(txtIDChekIn))
                .addContainerGap(40, Short.MAX_VALUE))
        );
        pnlCheckInLayout.setVerticalGroup(
            pnlCheckInLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCheckInLayout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(pnlCheckInLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtIDChekIn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(18, 18, 18)
                .addGroup(pnlCheckInLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtRoomNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblIDRoom))
                .addGap(18, 18, 18)
                .addGroup(pnlCheckInLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtRoomType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnlCheckInLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCheckinDate)
                    .addComponent(txtCheckinDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnlCheckInLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblLamaInap)
                    .addComponent(txtLamaInap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnlCheckInLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblIDKar)
                    .addComponent(txtIDKar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel1.add(pnlCheckIn, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 300, -1, 320));

        cmdTambah.setText("Save");
        cmdTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdTambahActionPerformed(evt);
            }
        });
        jPanel1.add(cmdTambah, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 300, 70, -1));

        cmdEdit.setText("Edit");
        cmdEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdEditActionPerformed(evt);
            }
        });
        jPanel1.add(cmdEdit, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 340, 70, -1));

        cmdKeluar.setText("Exit");
        cmdKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdKeluarActionPerformed(evt);
            }
        });
        jPanel1.add(cmdKeluar, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 380, 70, -1));

        pnlLogin.setBackground(new java.awt.Color(164, 92, 44));
        pnlLogin.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Daftar", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe Script", 0, 10))); // NOI18N

        tblCheckIn.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5"
            }
        ));
        tblCheckIn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblCheckInMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblCheckIn);

        javax.swing.GroupLayout pnlLoginLayout = new javax.swing.GroupLayout(pnlLogin);
        pnlLogin.setLayout(pnlLoginLayout);
        pnlLoginLayout.setHorizontalGroup(
            pnlLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 438, Short.MAX_VALUE)
        );
        pnlLoginLayout.setVerticalGroup(
            pnlLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 78, Short.MAX_VALUE)
        );

        jPanel1.add(pnlLogin, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 540, 450, -1));

        pnlBayar.setBackground(new java.awt.Color(164, 92, 44));
        pnlBayar.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Transaksi", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe Script", 0, 10))); // NOI18N

        jLabel3.setText("Price Room");

        jLabel5.setText("Payment");

        jLabel6.setText("Residue");

        btnItung.setText("Hitung");
        btnItung.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnItungActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlBayarLayout = new javax.swing.GroupLayout(pnlBayar);
        pnlBayar.setLayout(pnlBayarLayout);
        pnlBayarLayout.setHorizontalGroup(
            pnlBayarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlBayarLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlBayarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnlBayarLayout.createSequentialGroup()
                        .addGap(0, 142, Short.MAX_VALUE)
                        .addComponent(btnItung))
                    .addGroup(pnlBayarLayout.createSequentialGroup()
                        .addGroup(pnlBayarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6))
                        .addGap(18, 18, 18)
                        .addGroup(pnlBayarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtTotal, javax.swing.GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE)
                            .addComponent(txtBiayaKamar, javax.swing.GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE)
                            .addComponent(txtBayar, javax.swing.GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE))))
                .addGap(23, 23, 23))
        );
        pnlBayarLayout.setVerticalGroup(
            pnlBayarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBayarLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlBayarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtBiayaKamar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(pnlBayarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtBayar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(18, 18, 18)
                .addComponent(btnItung)
                .addGap(18, 18, 18)
                .addGroup(pnlBayarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addContainerGap(57, Short.MAX_VALUE))
        );

        jPanel1.add(pnlBayar, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 300, -1, 230));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 1250, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 711, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnKaryawanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKaryawanActionPerformed
        // TODO add your handling code here:
        new Karyawan().setVisible(true);
        dispose();
    }//GEN-LAST:event_btnKaryawanActionPerformed

    private void btnCheckInActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCheckInActionPerformed
        // TODO add your handling code here:
        new transaksi().setVisible(true);
        dispose();
    }//GEN-LAST:event_btnCheckInActionPerformed

    private void btnCheckOutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCheckOutActionPerformed
        // TODO add your handling code here:
        new CheckOut().setVisible(true);
        dispose();
    }//GEN-LAST:event_btnCheckOutActionPerformed

    private void btnLogOutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogOutActionPerformed
        // TODO add your handling code here:
        new login().setVisible(true);
        dispose();
    }//GEN-LAST:event_btnLogOutActionPerformed

    private void btnkamar6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnkamar6MouseClicked
        // TODO add your handling code here:
       btnkamar6.setEnabled(false);
    }//GEN-LAST:event_btnkamar6MouseClicked

    private void btnkamar6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnkamar6ActionPerformed
        // TODO add your handling code here
        txtRoomNo.setText("6");
    }//GEN-LAST:event_btnkamar6ActionPerformed

    private void btnkamar7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnkamar7MouseClicked
        // TODO add your handling code here:
        btnkamar7.setEnabled(false);
    }//GEN-LAST:event_btnkamar7MouseClicked

    private void btnkamar7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnkamar7ActionPerformed
        // TODO add your handling code here:
        txtRoomNo.setText("7");
    }//GEN-LAST:event_btnkamar7ActionPerformed

    private void btnkamar4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnkamar4MouseClicked
        // TODO add your handling code here:
        btnkamar4.setEnabled(false);
    }//GEN-LAST:event_btnkamar4MouseClicked

    private void btnkamar4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnkamar4ActionPerformed
        // TODO add your handling code here:
        txtRoomNo.setText("4");
    }//GEN-LAST:event_btnkamar4ActionPerformed

    private void btnkamar2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnkamar2MouseClicked
        // TODO add your handling code here:
        btnkamar2.setEnabled(false);
    }//GEN-LAST:event_btnkamar2MouseClicked

    private void btnkamar2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnkamar2ActionPerformed
        // TODO add your handling code here:
        txtRoomNo.setText("2");
    }//GEN-LAST:event_btnkamar2ActionPerformed

    private void btnkamar3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnkamar3MouseClicked
        // TODO add your handling code here:
        btnkamar3.setEnabled(false);
    }//GEN-LAST:event_btnkamar3MouseClicked

    private void btnkamar3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnkamar3ActionPerformed
        // TODO add your handling code here:
        txtRoomNo.setText("3");
    }//GEN-LAST:event_btnkamar3ActionPerformed

    private void btnkamar5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnkamar5MouseClicked
        // TODO add your handling code here:
        btnkamar5.setEnabled(false);
    }//GEN-LAST:event_btnkamar5MouseClicked

    private void btnkamar5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnkamar5ActionPerformed
        // TODO add your handling code here:
        txtRoomNo.setText("5");
    }//GEN-LAST:event_btnkamar5ActionPerformed

    private void btnkamar9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnkamar9MouseClicked
        // TODO add your handling code here:
        btnkamar9.setEnabled(false);
    }//GEN-LAST:event_btnkamar9MouseClicked

    private void btnkamar9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnkamar9ActionPerformed
        // TODO add your handling code here:
        txtRoomNo.setText("9");
    }//GEN-LAST:event_btnkamar9ActionPerformed

    private void btnkamar1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnkamar1MouseClicked
        // TODO add your handling code here:
        btnkamar1.setEnabled(false);
    }//GEN-LAST:event_btnkamar1MouseClicked

    private void btnkamar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnkamar1ActionPerformed
        // TODO add your handling code here:
        txtRoomNo.setText("1");
    }//GEN-LAST:event_btnkamar1ActionPerformed

    private void btnkamar8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnkamar8MouseClicked
        // TODO add your handling code here:
        btnkamar8.setEnabled(false);
    }//GEN-LAST:event_btnkamar8MouseClicked

    private void btnkamar8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnkamar8ActionPerformed
        // TODO add your handling code here:
        txtRoomNo.setText("8");
    }//GEN-LAST:event_btnkamar8ActionPerformed

    private void btnkamar10MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnkamar10MouseClicked
        // TODO add your handling code here:
        btnkamar10.setEnabled(false);
    }//GEN-LAST:event_btnkamar10MouseClicked

    private void btnkamar10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnkamar10ActionPerformed
        // TODO add your handling code here:
        txtRoomNo.setText("10");
    }//GEN-LAST:event_btnkamar10ActionPerformed

    private void btnDuluxeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDuluxeActionPerformed
        // TODO add your handling code here:

        btnSuite.setEnabled(false);
        btnSuperior.setEnabled(false);
        txtRoomType.setText("Duluxe");
        autonumber();
        aktif(true);
        tblCheckIn.setEnabled(false);
        clear();
        actButton(false);
        edit=false;
        try{
            stm = Con.createStatement();
            RsCheckIn = stm.executeQuery("SELECT RoomNumber FROM check_in WHERE RoomType = 'Duluxe' and totalbiaya = 0.0");
            while(RsCheckIn.next()) {
                //int roomNo = RsCheckIn.getString("RoomNumber");
                if(RsCheckIn.getInt("RoomNumber") == 1){
                    btnkamar1.setEnabled(false);
                }
                else if(RsCheckIn.getInt("RoomNumber") == 2){
                    btnkamar2.setEnabled(false);
                }
                else if(RsCheckIn.getInt("RoomNumber") == 3){
                    btnkamar3.setEnabled(false);
                }
                else if(RsCheckIn.getInt("RoomNumber") == 4){
                    btnkamar4.setEnabled(false);
                }
                else if(RsCheckIn.getInt("RoomNumber") == 5){
                    btnkamar5.setEnabled(false);
                }
                else if(RsCheckIn.getInt("RoomNumber") == 6){
                    btnkamar6.setEnabled(false);
                }
                else if(RsCheckIn.getInt("RoomNumber") == 7){
                    btnkamar7.setEnabled(false);
                }
                else if(RsCheckIn.getInt("RoomNumber") == 8){
                    btnkamar8.setEnabled(false);
                }
                else if(RsCheckIn.getInt("RoomNumber") == 9){
                    btnkamar9.setEnabled(false);
                }
                else if(RsCheckIn.getInt("RoomNumber") == 10){
                    btnkamar10.setEnabled(false);
                }
            }
        }
        catch(Exception e){
            System.out.println("Error : "+e);
        }
    }//GEN-LAST:event_btnDuluxeActionPerformed

    private void btnSuiteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuiteActionPerformed
        // TODO add your handling code here:
        btnDuluxe.setEnabled(false);
        btnSuperior.setEnabled(false);
        txtRoomType.setText("Suite");
        autonumber();
        aktif(true);
        tblCheckIn.setEnabled(false);
        clear();
        actButton(false);
        edit=false;
        try{
            stm = Con.createStatement();
            RsCheckIn = stm.executeQuery("SELECT RoomNumber FROM check_in WHERE RoomType = 'Suite' and totalbiaya = 0.0");
            while(RsCheckIn.next()) {
                //int roomNo = RsCheckIn.getString("RoomNumber");
                if(RsCheckIn.getInt("RoomNumber") == 1){
                    btnkamar1.setEnabled(false);
                }
                else if(RsCheckIn.getInt("RoomNumber") == 2){
                    btnkamar2.setEnabled(false);
                }
                else if(RsCheckIn.getInt("RoomNumber") == 3){
                    btnkamar2.setEnabled(false);
                }
                else if(RsCheckIn.getInt("RoomNumber") == 4){
                    btnkamar4.setEnabled(false);
                }
                else if(RsCheckIn.getInt("RoomNumber") == 5){
                    btnkamar5.setEnabled(false);
                }
                else if(RsCheckIn.getInt("RoomNumber") == 6){
                    btnkamar6.setEnabled(false);
                }
                else if(RsCheckIn.getInt("RoomNumber") == 7){
                    btnkamar7.setEnabled(false);
                }
                else if(RsCheckIn.getInt("RoomNumber") == 8){
                    btnkamar8.setEnabled(false);
                }
                else if(RsCheckIn.getInt("RoomNumber") == 9){
                    btnkamar9.setEnabled(false);
                }
                else if(RsCheckIn.getInt("RoomNumber") == 10){
                    btnkamar10.setEnabled(false);
                }

            }
        }
        catch(Exception e){
            System.out.println("Error : "+e);
        }
    }//GEN-LAST:event_btnSuiteActionPerformed

    private void btnSuperiorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuperiorActionPerformed
        btnDuluxe.setEnabled(false);
        btnSuite.setEnabled(false);
        txtRoomType.setText("Superior");
        autonumber();
        aktif(true);
        tblCheckIn.setEnabled(false);
        clear();
        actButton(false);
        edit=false;
        try{
            stm = Con.createStatement();
            RsCheckIn = stm.executeQuery("SELECT RoomNumber FROM check_in WHERE RoomType = 'Superior' and totalbiaya = 0.0");
            while(RsCheckIn.next()) {
                //int roomNo = RsCheckIn.getString("RoomNumber");
                if(RsCheckIn.getInt("RoomNumber") == 1){
                    btnkamar1.setEnabled(false);
                }
                else if(RsCheckIn.getInt("RoomNumber") == 2){
                    btnkamar2.setEnabled(false);
                }
                else if(RsCheckIn.getInt("RoomNumber") == 3){
                    btnkamar2.setEnabled(false);
                }
                else if(RsCheckIn.getInt("RoomNumber") == 4){
                    btnkamar4.setEnabled(false);
                }
                else if(RsCheckIn.getInt("RoomNumber") == 5){
                    btnkamar5.setEnabled(false);
                }
                else if(RsCheckIn.getInt("RoomNumber") == 6){
                    btnkamar6.setEnabled(false);
                }
                else if(RsCheckIn.getInt("RoomNumber") == 7){
                    btnkamar7.setEnabled(false);
                }
                else if(RsCheckIn.getInt("RoomNumber") == 8){
                    btnkamar8.setEnabled(false);
                }
                else if(RsCheckIn.getInt("RoomNumber") == 9){
                    btnkamar9.setEnabled(false);
                }
                else if(RsCheckIn.getInt("RoomNumber") == 10){
                    btnkamar10.setEnabled(false);
                }

            }
        }
        catch(Exception e){
            System.out.println("Error : "+e);
        }
            // TODO add your handling code here:
    }//GEN-LAST:event_btnSuperiorActionPerformed

    private void btnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBatalActionPerformed
        // TODO add your handling code here:
        roomNumber(true);
        btnDuluxe.setEnabled(true);
        btnSuite.setEnabled(true);
        btnSuperior.setEnabled(true);
    }//GEN-LAST:event_btnBatalActionPerformed

    private void txtCustomerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCustomerActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCustomerActionPerformed

    private void txtPhoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPhoneActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPhoneActionPerformed

    private void txtIDCusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIDCusActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIDCusActionPerformed

    private void btnSubmitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSubmitActionPerformed
        // TODO add your handling code here:
        if(txtRoomType.getText().equals("Superior")){
            Double lamainap=Double.parseDouble(txtLamaInap.getText());
            biayakamar = lamainap*700000;
        }
        else if(txtRoomType.getText().equals("Duluxe")){
            Double lamainap=Double.parseDouble(txtLamaInap.getText());
            biayakamar = lamainap*800000;
        }
        else if(txtRoomType.getText().equals("Suite")){
            Double lamainap=Double.parseDouble(txtLamaInap.getText());
            biayakamar = lamainap*1000000;
        }
        String total = Double.toString(biayakamar);
        txtBiayaKamar.setText(total);
    }//GEN-LAST:event_btnSubmitActionPerformed

    private void txtRoomNoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtRoomNoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtRoomNoActionPerformed

    private void txtLamaInapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtLamaInapActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtLamaInapActionPerformed

    private void txtCheckinDateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCheckinDateActionPerformed
        // TODO add your handling code here:
        // new java.util.Date();
    }//GEN-LAST:event_txtCheckinDateActionPerformed

    private void cmdEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdEditActionPerformed
        // TODO add your handling code here:
        edit=true;
        aktif(true);
        actButton(false);
        txtIDCus.setEnabled(false);
        txtIDKar.setEnabled(false);
        txtIDChekIn.setEnabled(false);
        txtCheckinDate.setEnabled(false);
        txtLamaInap.setEnabled(false);
        txtBiayaKamar.setEnabled(false);
        txtBayar.setEnabled(false);
        txtTotal.setEnabled(false);
        btnSubmit.setEnabled(false);
        btnItung.setEnabled(false);
    }//GEN-LAST:event_cmdEditActionPerformed

    private void cmdKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdKeluarActionPerformed
        // TODO add your handling code here
        if(cmdKeluar.getText().equals("Exit")){
            int ok=JOptionPane.showConfirmDialog(rootPane,"Are you sure to exit?","Pemberitahuan",JOptionPane.YES_NO_OPTION );
            if(ok==0){
                dispose();
            }
        }
        else{
            aktif(false);
            actButton(true);
            tblCheckIn.setEnabled(true);
            clear();

        }
        //System.exit(0);
    }//GEN-LAST:event_cmdKeluarActionPerformed

    private void tblCheckInMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblCheckInMouseClicked
        // TODO add your handling code here:
        setField();
        cmdEdit.setEnabled(true);
    }//GEN-LAST:event_tblCheckInMouseClicked

    private void btnItungActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnItungActionPerformed
        // TODO add your handling code here:
        Double bayar=Double.parseDouble(txtBayar.getText());
        uangkembalian = bayar-biayakamar;
        String uk = Double.toString(uangkembalian);
        txtTotal.setText(uk);
    }//GEN-LAST:event_btnItungActionPerformed

    private void cmdTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdTambahActionPerformed
        // TODO add your handling code here:

        String cusID=txtIDCus.getText();
        String name=txtCustomer.getText();
        String address=txtAddress.getText();
        String phone=txtPhone.getText();
        int checkinID=Integer.parseInt(txtIDChekIn.getText());
        String karID=txtIDKar.getText();
        int roomNo=Integer.parseInt(txtRoomNo.getText());
        String roomType=txtRoomType.getText();
        String checkIn=txtCheckinDate.getText();
        int lamaInap=Integer.parseInt(txtLamaInap.getText());
        double biayakamar=Double.parseDouble(txtBiayaKamar.getText());

        System.out.println(cusID + name + address + phone + checkinID + karID + roomNo + roomType + checkIn + lamaInap + biayakamar);

        try{
            if (edit==true)
            {
                stm.executeUpdate("update customer set name='"+name+"',address='"+address+"',phonenumber='"+phone+"' where idcustomer='"+cusID+"'");
            }else
            {
                stm.executeUpdate("INSERT into check_in VALUES('"+cusID+"','"+karID+"',"+roomNo+",'"+checkIn+"',"+lamaInap+","+checkinID+",'"+ roomType +"',"+biayakamar+",0.0,0.0,'2013-01-01')");
                stm.executeUpdate("INSERT into customer VALUES('"+cusID+"','"+name+"','"+address+"','"+phone+"')");
            }
            tblCheckIn.setModel(new DefaultTableModel(dataTable,header));
            baca_data();
            aktif(false);
            actButton(true);
            tblCheckIn.setEnabled(true);

        }catch(SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_cmdTambahActionPerformed

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
            java.util.logging.Logger.getLogger(transaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(transaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(transaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(transaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new transaksi().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBatal;
    private javax.swing.JButton btnCheckIn;
    private javax.swing.JButton btnCheckOut;
    private javax.swing.JButton btnDuluxe;
    private javax.swing.JButton btnItung;
    private javax.swing.JButton btnKaryawan;
    private javax.swing.JButton btnLogOut;
    private javax.swing.JButton btnSubmit;
    private javax.swing.JButton btnSuite;
    private javax.swing.JButton btnSuperior;
    private javax.swing.JButton btnkamar1;
    private javax.swing.JButton btnkamar10;
    private javax.swing.JButton btnkamar2;
    private javax.swing.JButton btnkamar3;
    private javax.swing.JButton btnkamar4;
    private javax.swing.JButton btnkamar5;
    private javax.swing.JButton btnkamar6;
    private javax.swing.JButton btnkamar7;
    private javax.swing.JButton btnkamar8;
    private javax.swing.JButton btnkamar9;
    private javax.swing.JButton cmdEdit;
    private javax.swing.JButton cmdKeluar;
    private javax.swing.JButton cmdTambah;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblAddress;
    private javax.swing.JLabel lblCheckinDate;
    private javax.swing.JLabel lblCustomer;
    private javax.swing.JLabel lblIDCus;
    private javax.swing.JLabel lblIDKar;
    private javax.swing.JLabel lblIDRoom;
    private javax.swing.JLabel lblLamaInap;
    private javax.swing.JLabel lblLogo;
    private javax.swing.JLabel lblPhone;
    private javax.swing.JPanel pnlBayar;
    private javax.swing.JPanel pnlCheckIn;
    private javax.swing.JPanel pnlCustomer;
    private javax.swing.JPanel pnlLogin;
    private javax.swing.JTable tblCheckIn;
    private javax.swing.JTextField txtAddress;
    private javax.swing.JTextField txtBayar;
    private javax.swing.JTextField txtBiayaKamar;
    private javax.swing.JTextField txtCheckinDate;
    private javax.swing.JTextField txtCustomer;
    private javax.swing.JTextField txtIDChekIn;
    private javax.swing.JTextField txtIDCus;
    private javax.swing.JTextField txtIDKar;
    private javax.swing.JTextField txtLamaInap;
    private javax.swing.JTextField txtPhone;
    private javax.swing.JTextField txtRoomNo;
    private javax.swing.JTextField txtRoomType;
    private javax.swing.JTextField txtTotal;
    // End of variables declaration//GEN-END:variables

    
}
