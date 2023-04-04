import java.io.File;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.Scanner;

public class ArithmeticCoding extends javax.swing.JFrame {
    public ArithmeticCoding() {
        initComponents();
    }
//GUI of the Application
    private void initComponents() {
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        jButton1.setFont(new java.awt.Font("Georgia", 1, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(51, 51, 0));
        jButton1.setText("Compress");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jButton2.setFont(new java.awt.Font("Georgia", 1, 14)); // NOI18N
        jButton2.setForeground(new java.awt.Color(51, 51, 0));
        jButton2.setText("Decompress");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jLabel1.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(102, 0, 102));
        jLabel1.setText("Choose the process");
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(53, 53, 53)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 50, Short.MAX_VALUE)
                                .addComponent(jButton2)
                                .addGap(86, 86, 86))
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(154, 154, 154)
                                                .addComponent(jLabel2))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(94, 94, 94)
                                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(48, 48, 48)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(36, 36, 36)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 58, Short.MAX_VALUE)
                                .addComponent(jLabel2)
                                .addGap(63, 63, 63))
        );
        pack();
    }

    public class Character
    {
        private String letter ;
        private double probability ;
        private double Low ;
        private double High ;

        public Character(String letter, double probability, double Low, double High) {
            this.letter = letter;
            this.probability = probability;
            this.Low = Low;
            this.High = High;
        }

        public Character() {}

        public void setLetter(String letter) {
            this.letter = letter;
        }

        public void setProbability(double probability) {
            this.probability = probability;
        }

        public void setLow(double Low) {
            this.Low = Low;
        }

        public void setHigh(double High) {
            this.High = High;
        }

        public String getLetter() {
            return letter;
        }

        public double getProbability() {
            return probability;
        }

        public double getLow() {
            return Low;
        }

        public double getHigh() {
            return High;
        }
    }

    Scanner sc;
    public void open_file(String FileName) {
        try {
            sc = new Scanner(new File(FileName));
        } catch (Exception e) {
            jLabel2.setText("File is not Found ");
        }
    }

    public void close_file() {
        sc.close();
    }

    Formatter out; // to write the tag as same as string format

    public void openfile(String pass) {
        try {
            out = new Formatter(pass);
        } catch (Exception e) {
            jLabel2.setText("Error...The File is not Found! ");
        }
    }

    public void closefile() {
        out.close();
    }

    public void write(String code) {
        out.format("%s", code);
        out.format("%n");
        out.flush(); // to write the file
    }

    public String read_file(String FileName) // read input data
    {
        open_file(FileName);
        String data = "";

        while (sc.hasNext()) // while it's not the end of file
        {
            data += sc.nextLine(); // read line by line by appending ....bseb al spaces
        }
        close_file();
        return data ;
    }

    ArrayList< Character > Characters_comp  = new ArrayList <Character>();// to store Character + probablity + ranges in Compression
    ArrayList < Character > Characters_decomp  = new ArrayList <Character>();// to store Character + probablity + ranges in Decompression
    
    public void calculate_range (  ArrayList < Character >  arr ) // comulative probability
    {
        double low = 0 ;

        for (int i=0 ; i<arr.size() ; i++)
        {
            double high = arr.get(i).getProbability()+low ; // el high range = prob + low ely 2abli
            arr.get(i).setLow(low);
            arr.get(i).setHigh(high);
            low = high ;
        }
    }

    public void calculate_probability(String data)
    {
        for (int i = 0; i < data.length(); i++)  // count frequency
        {
            String letter = "" ;
            letter += data.charAt(i);
            boolean found = false;
            for (int j = 0; j < Characters_comp.size(); j++) // loop 3ala l array of elements
            {
                if (Characters_comp.get(j).getLetter().equals(letter)) // law l 7arf da mwgod 2abl kda
                {
                    double new_prob = Characters_comp.get(j).getProbability()+1 ; // bnzwd el freq bta3o be wa7d
                    Characters_comp.get(j).setProbability(new_prob);
                    found = true; // 34an maydef4 el 7arf da l2no geh 2abl kda
                    break;
                }
            }

            if (found == false) // law el 7arf da awl mara yegi ha3mlo add wel freq =1
            {
                Characters_comp.add(new Character (letter , 1 , 0 , 0 ));
            }
        }
        
        for (int i=0 ; i<Characters_comp.size() ; i++) //calculate probability
        {
            double new_prob = Characters_comp.get(i).getProbability()/data.length();
            Characters_comp.get(i).setProbability(new_prob);
        }

        calculate_range(Characters_comp);
        for (int i=0 ; i<Characters_comp.size() ; i++)
        {
            System.out.println(Characters_comp.get(i).getLetter() + " " + Characters_comp.get(i).getProbability() + " " + Characters_comp.get(i).getLow() + " " + Characters_comp.get(i).getHigh());
        }
    }
    
    public Character search_item (char x , ArrayList <Character> arr)
    // bdelo el 7arf yrgli l data bt3to  ( probability , low range , high range )
    {
        String item = "";
        item+=x ;
        Character result = new Character () ;

        for (int i=0 ; i<arr.size() ; i++)
        {
            if (arr.get(i).getLetter().equals(item))
            {
                result = arr.get(i);
                break;
            }
        }
        return result ;
    }

    public void save_probabilities() // save for char and prob in file to use it in decomp
    {
        for (int i=0 ; i<Characters_comp.size() ; i++)
        {
            String item = Characters_comp.get(i).getLetter() + Characters_comp.get(i).getProbability();
            write(item);
        }
    }
    
    public void compress ()
    {
        String data = read_file("Data.txt");  // the Original data ale h3mlha comp w decomp
        calculate_probability(data);
        System.out.println("X: Prob - LR - HR ");
        for (int i=0 ; i<Characters_comp.size() ; i++)
        {
            System.out.println(Characters_comp.get(i).getLetter() + ": " + Characters_comp.get(i).getProbability() + " " + Characters_comp.get(i).getLow() + " " + Characters_comp.get(i).getHigh());
        }
        
        double lower = 0 ; // da el cur iteraion
        double upper = 1 ;
        double old_lower = 0 ; // el prev iteration
        double old_upper = 1 ;
        
        for (int i=0 ; i<data.length() ; i++)
        {
            Character cur = search_item (data.charAt(i) , Characters_comp); // brou7 ageb el info bta3t el 7rf
            lower  = old_lower + (old_upper - old_lower )*cur.getLow(); // bzooom el range bta3o ... b7sblo range gded
            upper  = old_lower + (old_upper - old_lower )*cur.getHigh();
            // b3ml old lower 34an law m3mltha4 bya5od el lower eli lsa tal3 mn el equation eli 2abli y7oto fe el upper
            old_lower = lower ; // we a5ly el range eli na feeh da yb2a el adem 34an ast5dmo fel next interation
            old_upper = upper ;
            System.out.println("Range  = " + lower + " , " + upper);
        }

        double rand_num = (lower + upper)/2 ; // pick a num between final range
        String code = Double.toString(rand_num);
        int length = data.length();
        String iterations = Integer.toString(length);
        openfile("Compressed Data.txt");  // save number & length & prob 3ala l file
        write(code);
        write(iterations);
        save_probabilities ();
        closefile();
        System.out.println("code = " + code );
    }
    public void reconstruct_probabilities () // read letters and thier probablity from file and calculate commulative ranges for each one
    {
        while (sc.hasNext())
        {
            String data = sc.nextLine();
            String letter = "" + data.charAt(0) ;
            Characters_decomp.add(new Character ( letter , Double.parseDouble(data.substring(1)) , 0 , 0 )) ;
        }
        calculate_range(Characters_decomp);
    }
    public void decompress ()
    {
        open_file("Compressed Data.txt");
        double code = Double.parseDouble(sc.nextLine()); // read float num
        int iterations = Integer.parseInt(sc.nextLine()); // read length
        reconstruct_probabilities(); // read prob
        close_file();
        System.out.println("------------------------------------------------");
        System.out.println("X: Prob - LR - HR ");
        for (int i=0 ; i<Characters_decomp.size() ; i++)
        {
            System.out.println(Characters_decomp.get(i).getLetter() + ": " + Characters_decomp.get(i).getProbability() + " " + Characters_decomp.get(i).getLow() + " " + Characters_decomp.get(i).getHigh());
        }
        String stream = ""; // el output data
        double lower = 0 , upper = 1  ;   // same idea of comp
        double old_lower = 0 , old_upper = 1  ;
        for (int i=0 ; i<iterations ; i++)
        {
            double cur_code = (code - old_lower) / (old_upper - old_lower) ;  // b7sb el code lel iteration li na feha
            System.out.println("cur code = " + cur_code);
            System.out.println("lower = " + lower);
            System.out.println("upper = " + upper);
            for (int j=0 ; j<Characters_decomp.size() ; j++)
            {
                if ( (cur_code > Characters_decomp.get(j).getLow()) && ( cur_code <Characters_decomp.get(j).getHigh())) // b4ofo howa fe anhy range
                {
                    stream += Characters_decomp.get(j).getLetter(); // bgeeb el 7arf el mokabel lel range dah
                    lower = old_lower + (old_upper - old_lower)*Characters_decomp.get(j).getLow(); // b3ml lel 7arf da new range ... expand
                    upper = old_lower + (old_upper - old_lower)*Characters_decomp.get(j).getHigh();
                    old_lower = lower ;
                    old_upper = upper ;
                    break;
                }
            }
        }
        openfile("Decompressed Data.txt");
        write(stream);
        closefile();
        System.out.println("stream = " + stream);
    }
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        compress ();
    }
    
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        decompress ();
    }
  
    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ArithmeticCoding.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ArithmeticCoding.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ArithmeticCoding.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ArithmeticCoding.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ArithmeticCoding().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    // End of variables declaration//GEN-END:variables
}
