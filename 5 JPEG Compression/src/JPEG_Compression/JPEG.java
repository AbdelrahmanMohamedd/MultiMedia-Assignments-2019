package JPEG_Compression;
import java.awt.Dialog.ModalExclusionType;
import java.beans.IntrospectionException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;


class BIN {
    public int num;
    public String code;
    public BIN() {}
}

public class JPEG {

    public static void Compress(String number) throws IOException {
        // split number with comas
        String[] splitted = number.split(",");
        Vector<Integer> numbers = new Vector<>();
        for (int i = 0; i < splitted.length - 1; i++) {
            numbers.add(Math.abs(Integer.parseInt(splitted[i])));
        }
        // create Table and write on file
        FileWriter HuffmanTable = new FileWriter("HuffmanTable.txt");
        BufferedWriter mywrite = new BufferedWriter(HuffmanTable);
        ArrayList<String> Table = new ArrayList();
        Integer count = 0;
        for (int i = 0; i < numbers.size(); i++) {
            if (numbers.get(i).equals(0)) {
                count++;
                // System.out.println(count + " -- "+ numbers.get(i));
            } else {
                // System.out.println(count + " ** " + numbers.get(i));
                mywrite.write(count + "/" + numbers.get(i));
                Table.add(count + "/" + numbers.get(i));
                count = 0;
                mywrite.write(" ");
            }
        }
        Table.add("EOB");
        mywrite.close();

        for (int i = 0; i < Table.size(); i++) {
            System.out.println(Table.get(i));
        }
        Huffman.mycompress(Table);

        FileReader file = new FileReader("Decompression.txt");
        BufferedReader br = new BufferedReader(file);
        String code = "";
        code += br.readLine();
        br.close();
        String[] codess = code.split(" ");

        String mys = "";
        String finalcode = "";
        Vector<String> mynum = new Vector<String>();
        for (int i = 0; i < splitted.length; i++) {
            if (!(splitted[i].equals("0"))) {
                mynum.add(splitted[i]);
            }

        }

        Vector<String> coded = new Vector<String>();
        Vector<String> nums = new Vector<String>();
        for (int i = 1; i < codess.length; i = i + 2) {
            coded.add(codess[i]);
        }
        for (int i = 0; i < coded.size(); i++) {
            System.out.println("Code: " + coded.get(i));
        }
        String rub = "";
        String temp = "";
        String[] rub1;

        for (int i = 0; i < mynum.size() - 1; i++) {
            BIN obj = new BIN();
          //////  System.out.println("Number: " + mynum.get(i));
            if (Integer.parseInt(mynum.get(i)) > 0) {
                obj.num = Integer.parseInt(mynum.get(i));
                // myList.add(Integer.parseInt(mynum.get(i)));
                rub = intToBinary(Integer.parseInt(mynum.get(i)));
                obj.code = rub;
                myList.add(obj);
               /////// System.out.println("Rub: " + rub);
                nums.add(rub);
                System.out.println("Number: " + mynum.get(i) + "   Code: " + rub);
            } else {
                obj.num = Integer.parseInt(mynum.get(i));
                rub = intToBinary(Math.abs(Integer.parseInt(mynum.get(i))));
                // System.out.println("before conv" + rub);
                temp = convert(rub);
                obj.code = temp;
                myList.add(obj);
                // System.out.println("afterconv" + temp);
                nums.add(temp);
                System.out.println("Number: " + mynum.get(i) + "   Code: " + temp);
            }
        }

        // System.out.println(coded.size()+"----" + nums.size());

        for (int i = 0; i < nums.size(); i++) {
            finalcode += coded.get(i);

            finalcode += nums.get(i);

        }
        finalcode += coded.get(coded.size() - 1); // EOB
        FileWriter myff1 = new FileWriter("CompressionStream.txt");
        BufferedWriter mywrite1 = new BufferedWriter(myff1);
        mywrite1.write(finalcode);
        System.out.println(finalcode);
        mywrite1.close();

    }

    public static String intToBinary(Integer n)

    {
        String s = "";
        while (n > 0) {
            s = ((n % 2) == 0 ? "0" : "1") + s;
            n = n / 2;
        }
        return s;
    }

    public static String convert(String num) {
        String n = "";
        for (int i = 0; i < num.length(); i++) {
            if (num.toCharArray()[i] == '0') {
                n += "1";
            } else {
                n += "0";
            }
        }
        return n;
    }

    public static void Decompress() throws IOException {
        FileReader file = new FileReader("CompressionStream.txt");
        BufferedReader br = new BufferedReader(file);
        String compressioncode = "";
        compressioncode += br.readLine();
        br.close();
        FileReader file1 = new FileReader("Decompression.txt");
        BufferedReader br1 = new BufferedReader(file1);
        String codes = "";
        String finaldecompress = "";
        codes += br1.readLine();
        String[] myc1 = codes.split(" ");
        ArrayList<String> myc = new ArrayList<>();

        for (int i = 0; i < myc1.length; i++) {
            myc.add(myc1[i]);
            //System.out.println(myc.get(i));
        }
        String temp = "";
        int num = 0;

        for (int i = 0; i < compressioncode.length(); i++) {
            //	System.out.println("************"+myc.get(i));
            temp += compressioncode.charAt(i);
            if (myc.contains(temp)) {
                num = myc.indexOf(temp) - 1;
                if (myc.get(num).equals("EOB")) {
                    continue;
                } else {
                    String mytag[] = myc.get(num).split("/");
                    int N_zero = Integer.parseInt(mytag[0]);
                    int Bin = Integer.parseInt(mytag[1]);
                    for (int j = 0; j < N_zero; j++) {
                        finaldecompress += "0";
                        finaldecompress += ",";

                    }
                    temp = "";
                    for (int k = 1; k <= Bin; k++) {
                        temp += compressioncode.charAt(i + k);

                    }
                    int ind = 0;
                    for (int z = 0; z < myList.size(); z++) {
                        if (temp.equals(myList.get(z).code)) {
                            ind = myList.get(z).num;
                            finaldecompress += ind;
                            finaldecompress += ',';
                            break;
                        }
                    }
                    i += temp.length();
                    temp = "";
                }
            }


        }

        for (int i = 0; i < myList.size(); i++) {
            System.out.println(myList.get(i).num + ">>>>" + myList.get(i).code);

        }
        finaldecompress+="EOB";
        System.out.println(finaldecompress);

    }

    public static void main(String[] args) throws IOException {
//		Compress("-2,0,0,2,0,0,3,2,0,1,0,0,-2,0,-1,0,0,1,0,0,-1,EOB");
//		Decompress();
    }

    public static ArrayList<BIN> myList = new ArrayList<>();
}
