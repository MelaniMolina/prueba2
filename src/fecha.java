import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Statement;
public class fecha extends  JFrame {
    PreparedStatement ps;

    Statement st;

    ResultSet rs;
    Connection con;
    private JPanel panel;

    private JComboBox comboBoxdias;
    private JComboBox comboBoxmes;
    private JComboBox comboBoxanio;
    private JButton crear;
    private JButton buttonActualizar;
    private JButton buttonbuscar;
    private JButton buttonEliminar;
    private JButton buttonCrear;
    private JTextField codigo;

    //Combox datos 

    public fecha() {
        conectar();
        try {
            st = con.createStatement();
            rs=st.executeQuery("SELECT*FROM DIA_NUM");
            while (rs.next()){
                comboBoxdias.addItem(rs.getString("DIAS"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        conectar();
        try {
            st = con.createStatement();
            rs = st.executeQuery("SELECT*FROM NOM_MES");
            while (rs.next()){
                comboBoxmes.addItem(rs.getString("MESES"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        conectar();
        try {
            st = con.createStatement();
            rs = st.executeQuery("SELECT*FROM ANIOS");
            while (rs.next()){
                comboBoxanio.addItem(rs.getString("ANIO"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        buttonCrear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                conectar();
                String dia = comboBoxdias.getSelectedItem().toString();
                String mes = comboBoxmes.getSelectedItem().toString();
                String anios = comboBoxanio.getSelectedItem().toString();
                try {
                    ps = con.prepareStatement("INSERT INTO TABLA1 VALUES(?,?,?,?)");
                    ps.setInt(1, Integer.parseInt(codigo.getText()));
                    ps.setInt(2,Integer.parseInt(dia));
                    ps.setString(3,mes);
                    ps.setInt(4,Integer.parseInt(anios));
                    if (ps.executeUpdate()>0){
                        JOptionPane.showMessageDialog(null,"Se ah Creado");
                    }else {
                        JOptionPane.showMessageDialog(null,"No se ah Podido");
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        buttonActualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                conectar();
                String dia = comboBoxdias.getSelectedItem().toString();
                String mes = comboBoxmes.getSelectedItem().toString();
                String anios = comboBoxanio.getSelectedItem().toString();
                String id = codigo.getText();
                int result =0;
                try {
                    ps=con.prepareStatement("UPDATE TABLA1 SET DIA=?, MESES=?,ANIO=? WHERE COD_TAB=?");
                   if (result>0){
                       JOptionPane.showMessageDialog(null,"Se ah actualizado");
                   }else {
                       JOptionPane.showMessageDialog(null,"No se ah Actualizado");
                   }

                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });
        buttonbuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                conectar();
                try {
                    ps = con.prepareStatement("SELECT * FROM TABLA1 WHERE  COD_TAB = ?");
                    ps.setString(1,codigo.getText());
                    rs =ps.executeQuery();
                    if(rs.next()){
                        comboBoxdias.setSelectedItem(rs.getString(2));
                        comboBoxmes.setSelectedItem(rs.getString(3));
                        comboBoxanio.setSelectedItem(rs.getString(4));
                        JOptionPane.showMessageDialog(null,"Se encuentra en la base");
                    }else {
                        JOptionPane.showMessageDialog(null,"No se encuentra en la base");
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });
        buttonEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                conectar();
                String id = codigo.getText();
                int result = 0;
                try {
                    ps=con.prepareStatement("DELETE FROM TABLA1 WHERE COD_TAB = ?");
                    ps.setString(1,id);
                    result = ps.executeUpdate();
                    if (result>0){
                        JOptionPane.showMessageDialog(null,"Se ah Eliminado");
                    }else {
                        JOptionPane.showMessageDialog(null,"No se ah borrado");
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }


        public static void main (String[]args){
            fecha f = new fecha();
            f.setContentPane(new fecha().panel);
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f.setVisible(true);
            f.pack();
        }

        public void conectar () {

            try {
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/FECHAS ", "root", "12345");
                System.out.println("Conectado");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }

}

























