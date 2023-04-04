import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import javax.swing.plaf.PanelUI;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

public class main {
    private static Node Root;
    Node temp = new Node();
    public static ArrayList<pair> table = new ArrayList<pair>();
    public static Node find (Node root,char symbole) {
        if(root == null)
            return null;
        if(root.symbol == symbole)
            return root;
        else {
            Node x = find(root.right,symbole);
            Node y = find(root.left,symbole);
            if(x != null)
                return x;
            else if(y != null)
                return y;
            else
                return null;
        }
    }
    static Node getNYT(Node root) {
        if(root == null)
            return null;
        if(root.counter == 0)
            return root;
        else
        {
            Node x = getNYT(root.right);
            Node y= getNYT(root.left);
            if(x != null)
                return x;
            else
                return y;
        }
    }
    public static String getShortCode(char symbole) {
        for(int i = 0;i < table.size();i++)
            if(symbole == table.get(i).symbol)
                return table.get(i).shortCode;

        return null;
    }
    public static String getNodeCode(Node root,char symbole) {
        if(root == null)
            return null;
        if(root.symbol == symbole)
            return root.code;
        else
        {
            String x = getNodeCode(root.right,symbole);
            String y= getNodeCode(root.left,symbole);
            if(x != null)
                return x;
            else
                return y;
        }
    }
    public static Node getParent(Node root,Node child) {
        if(root == null)
            return null;
        if(root.left == child)
            return root;
        else if(root.right == child)
            return root;
        else {
            Node x = getParent(root.right,child);
            Node y = getParent(root.left,child);
            if(x != null)
                return x;
            else if(y != null)
                return y;
            else
                return null;
        }
    }
    public static void introduceNewSymbole(Node NYT, char symbole) {
        NYT.counter = 1;
        NYT.right = new Node();
        NYT.left = new Node();
        NYT.right.symbol = symbole;
        NYT.right.counter = 1;
        NYT.right.number = NYT.number - 1;
        NYT.right.code = NYT.code + '1';

        NYT.left.counter = 0;
        NYT.left.number = NYT.number - 2;
        NYT.left.code = NYT.code + '0';
    }
    public static void updateTree(Node root,char symbole) {
        Node current = null;
        if(find(root,symbole) == null) {

            current = getNYT(root);
            introduceNewSymbole(getNYT(root),symbole);
            if(current.number == 100)
                return;
            else
                current = getParent(root,current);
        }
        else
            current = find(root,symbole);
        Node x = Swap(root,current);
        current = x;
        current.counter++;
        while (getParent(root,current)!= null) {
            current = getParent(root,current);
            x = Swap(root,current);
            if(x != null)
                current = x;
            current.counter++;
        }
        reconstructCode(root,"");
    }
    public static Node Swap (Node root, Node newNode){
        ArrayList<Node> nodes = new ArrayList<Node>();
        nodes = swap(root,newNode,nodes);
        int max = -1, index = -1;
        for(int i = 0;i < nodes.size();i++)
            if(nodes.get(i).number > max)
            {max = nodes.get(i).number; index = i;}
        if(max == -1) return newNode;
        root = nodes.get(index);
        char temp = root.symbol;
        root.symbol = newNode.symbol;
        newNode.symbol = temp;
        Node temp1 = root.left;
        root.left = newNode.left;
        newNode.left = temp1;
        Node temp2 = root.right;
        root.right = newNode.right;
        newNode.right = temp2;
        return root;
    }
    public static ArrayList<Node> swap(Node root, Node newNode,ArrayList<Node> nodes) {
        if (root == null)
            return nodes;
        if (root.counter == newNode.counter && root.number > newNode.number && root!=getParent(Root,newNode)) {
            nodes.add(root);
        }
        nodes = swap(root.right,newNode,nodes);
        return swap(root.left,newNode,nodes);
    }
    public static void reconstructCode(Node root,String code) {
        if(root.right != null )
        {
            root.right.code = code + '1';
            reconstructCode(root.right,root.right.code);
        }
         if(root.left != null)
        {
            root.left.code = code + '0';
            reconstructCode(root.left,root.left.code);
        }
    }
    public static Character getSymbol(String shortCode) {
        for(int i = 0;i < table.size();i++)
            if(table.get(i).shortCode.equals(shortCode))
                return table.get(i).symbol;
        return null;
    }
    public static Character getSymbolCode(Node root,String code) {
        if(root == null)
            return null;
        if(root.symbol!=null &&root.code.equals(code) && root.symbol != 'þ')
            return root.symbol;
        Character x = getSymbolCode(root.right,code);
        Character y = getSymbolCode(root.left,code);
        if(x != null)
            return x;
        return y;
    }
    public static String deCompress(String result)
    {
        String original = "";
        Character y = getSymbol((result.charAt(0)+""+result.charAt(1)));
        original += y;
//ABCCACBAA
            Node root =new Node('þ',"",100,1,new Node(),new Node());
        Root=root;
        root.right.symbol=y;
        root.right.counter=1;
        root.right.number=99;
        root.right.code="1";
        
        root.left.counter=0;
        root.left.number=98;
        root.left.code="0";
        
        String temp = "";
        boolean flag = false;
        Character symbol;
        for(int i = 2;i < result.length();i++)
        {
            temp += result.charAt(i);
            if(temp.equals(getNYT(root).code))
            {
                Character x = getSymbol(result.charAt(i+1) +""+ result.charAt(i+2));
                i=i+2;
                original += x;
                temp = "";
                
                updateTree(root,x);
            }
            else if(( symbol= getSymbolCode(root,temp)) != null)
            {
                original += symbol;
                temp = "";
                updateTree(root,symbol);
            }
            // System.out.println("=============");
            //  System.out.println(original);
            //dfs(root);
        }
        return original;
        
    }
    public static String compress(String input) {
        
        pair record = new pair('A', "00");
        pair record2 = new pair('B', "01");
        pair record3 = new pair('C', "10");
        table.add(record);
        table.add(record2);
        table.add(record3);
        
        String result = "";
        
        Node root = new Node('þ', "", 100, 1, new Node(), new Node());
        root.right.symbol = input.charAt(0);
        root.right.counter = 1;
        root.right.number = 99;
        root.right.code = "1";
        
        root.left.counter = 0;
        root.left.number = 98;
        root.right.code = "0";
        //System.out.println(">>>"+root.left.symbol);
        result += table.get(0).shortCode;
        Root = root;
        reconstructCode(root, "");
        for (int i = 1; i < input.length(); i++) {
            if (find(root, input.charAt(i)) == null) {
                result += getNYT(root).code;
                result += getShortCode(input.charAt(i));
            } else {
                result += find(root, input.charAt(i)).code;
            }
            updateTree(root, input.charAt(i));
        }
        return (result);
    }
    public static String readFromFile(String path) throws IOException {
        String content = new Scanner(new File(path)).useDelimiter("\\Z").next();
        return content;
    }
    public static void saveTofile(String content,String path) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(path+".txt"));
        writer.write(content);
        writer.close();
    }


    
    
    public static void main(String args[]){
        
        
        JFrame f=new JFrame("Adaptive Huffman Encoder and Decoder");
        JButton btnReadFromFile=new JButton("read from text file");
        JButton btnSaveToFromFile=new JButton("Save to text file");
    
        JButton btnDecompress=new JButton("Decompress");
        JButton btnCompress=new JButton("Compress");
        JTextField compressFiels = new JTextField();
        JLabel finalResutl=new JLabel("The result is:");
        
    
        int x=70;
        int xx=700;
        compressFiels.setBounds(100,80,250,30);
        finalResutl.setBounds(100,120,400,30);
        finalResutl.setFont(finalResutl.getFont().deriveFont(20f));
        btnReadFromFile.setBounds(xx,80,140,30);
        btnSaveToFromFile.setBounds(xx,110,140,30);
    
        btnDecompress.setBounds(xx,x+100,110,30);
        btnCompress.setBounds(xx,200+x,110,30);
        f.add(btnSaveToFromFile);
        f.getContentPane().add(finalResutl);
        f.add(compressFiels);
        f.add(btnReadFromFile);
        f.add(btnDecompress);
        f.add(btnCompress);
        f.setSize(900,400);
        f.setLayout(null);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
        btnDecompress.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                finalResutl.setText("The result is : "+deCompress(compressFiels.getText()));
            
            }
        });
        btnCompress.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                finalResutl.setText("The result is : "+compress(compressFiels.getText()));
            }
        });
        btnReadFromFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
                int returnValue = jfc.showOpenDialog(null);
                FileNameExtensionFilter filter = new FileNameExtensionFilter(".txt", "txt");
                jfc.setFileFilter(filter);
    
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = jfc.getSelectedFile();
                    try {
                        compressFiels.setText(readFromFile(selectedFile.getAbsolutePath()));
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        
        btnSaveToFromFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
    
                JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
                jfc.setApproveButtonText("save");
                // Set the mnemonic
                jfc.setApproveButtonMnemonic('s');
                // Set the tool tip
                jfc.setApproveButtonToolTipText("save");
                int returnValue = jfc.showOpenDialog(null);
                
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = jfc.getSelectedFile();
    
                    try {
                        saveTofile(finalResutl.getText().substring(16,finalResutl.getText().length()),selectedFile.getAbsolutePath());
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            
            }
        });
        
       

    }



}
