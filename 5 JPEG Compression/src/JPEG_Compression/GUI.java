package JPEG_Compression;

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;

public class GUI {  ///here is the GUI of the App
    private JFrame frame;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    GUI window = new GUI();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    public GUI() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame();
        frame.setBounds(450, 120, 520, 180);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        frame.setBackground(Color.white);
        frame.setVisible(true);
        frame.setResizable(false);
/////////////////////////////////////////////////////////////////////////////////////// Msg Lable
        JLabel msglable = new JLabel("Choose the process:");
        msglable.setBounds(200, 10, 300, 30);
        msglable.setVisible(true);
        frame.add(msglable);
/////////////////////////////////////////////////////////////////////////////////////// Compress Button
        JButton Compressbtn = new JButton("Compress the data");
        Compressbtn.setBackground(Color.green);
        Compressbtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                try {
                    JPEG.Compress("-2,0,0,2,0,0,3,2,0,1,0,0,-2,0,-1,0,0,1,0,0,-1,EOB");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        Compressbtn.setBounds(12, 60, 200, 50);
        frame.getContentPane().add(Compressbtn);
/////////////////////////////////////////////////////////////////////////////////////// Decompress Button
        JButton Decompressbtn = new JButton("Decompress the data ");
        Decompressbtn.setBackground(Color.blue);
        Decompressbtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                try {
                    JPEG.Decompress();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        Decompressbtn.setBounds(286, 60, 200, 50);
        frame.getContentPane().add(Decompressbtn);
    }
}
