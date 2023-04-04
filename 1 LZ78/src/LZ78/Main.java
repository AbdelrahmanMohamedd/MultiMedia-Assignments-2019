package LZ78;
import javafx.util.Pair;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    //////////////////////////////////////////////////////////////////////////////// Compression
    static void Compression(String data) {
        ArrayList<Pair<Integer, String>> tags = new ArrayList<>();
        ArrayList<String> dict = new ArrayList<>();
        String check;
        Integer counter;
        Integer index;
        String input = data;
        dict.add(null);

        for (int i = 0; i < input.length(); i++) {
            counter = i;
            check = String.valueOf(input.charAt(i)); //char
            boolean iscntn = dict.contains(check);
            if (!iscntn) {
                dict.add(check);
                tags.add(new Pair<Integer, String>(0, check));
            } else {
                for (int k = i; k < input.length(); k++) {
                    index = dict.indexOf(check);
                    check = input.substring(i, counter + 1);
                    boolean iscntn1 = dict.contains(check);
                    if (iscntn1) {
                        counter++;
                    } else {
                        String check2 = input.substring(i + 1, counter + 1);
                        dict.add(check);
                        tags.add(new Pair<Integer, String>(index,
                                Character.toString(check2.charAt(check2.length() - 1))));
                        i = counter;
                        break;
                    }
                }
            }
            check = " ";
        }
        for (int i = 0; i < tags.size(); i++) {
            System.out.print(tags.get(i));
            System.out.print("    ");
        }
        dict.clear();
        dict.add(" ");
    }
    //////////////////////////////////////////////////////////////////////////////// Decompression
    static void Decompression(String userTags ) {
        //String[] AllTags = "<0,D> <0,A> <0,B> <3,A <0,C> <1,A> <3,B> <2,C> <6,B> <4,C> <9,B>".split(" ");
        String indx, newchar;
        ArrayList<String> Dictionary = new ArrayList<String>();
        System.out.print("Please adhere to this form or you will get wronge results !!! ");
        Scanner sc = new Scanner(System.in);
        userTags = sc.nextLine();
        String[] AllTags = userTags.split(" ");
        for (String Tag : AllTags) {
            //System.out.println(Tag);
            indx = Tag.substring(1, 2);
            newchar = Tag.substring(3, Tag.length() - 1);
            //System.out.println(indx + " " + newchar);

            if (Integer.parseInt(indx) == 0) {
                Dictionary.add(newchar);
            } else {
                int Index = Integer.parseInt(indx);
                String Last = Dictionary.get(Index-1) + newchar;
                Dictionary.add(Last);
                Last = "";
                // System.out.println(Dictionary);
                // Dictionary.add(Dictionary.get(index-1)+last);
            }
        }
        StringBuilder sb = new StringBuilder();
        for(String d : Dictionary){
            sb.append(d);
    }
    System.out.println(sb.toString());

    /*
        //   ArrayList<Pair <Integer,String>> tags = new ArrayList<>();
        ArrayList<String> dictionary = new ArrayList<>();
        String character;
        String last;
        Integer indx;
        Integer NumberOfTags;
        String MyTags;
            dictionary.add(null);   //b5le awl indx fl dictionary fadya
                    System.out.print("How many Tags you will enter?");
                    Scanner userInput=new Scanner(System.in);
                    NumberOfTags=userInput.nextInt();
                    System.out.print("Enter the "+NumberOfTags+"Tags");
                    Scanner sc=new Scanner(System.in);
                    MyTags=sc.nextLine();
                    String[]Tags=MyTags.split(" ");
                    for(String a:MyTags)
                    // String[] tag = a.split(",");
                    System.out.println(a);
                    for(int getTag=0;getTag<NumberOfTags; getTag++)
            {
            last=dictionary[indx]+character;
            dictionary.add(last);
            last="";
            }

            }
     */
//////////////////////////////////////////////////////////////////////////////// main
    public static void main(String[]args){
        Scanner in=new Scanner(System.in);
        int choice=innextInt();
        while(true){
            System.out.println("Enter your choice:");
            System.out.println("1- compression");
            System.out.println("2- Decompression");
            System.out.println("3- Exit");

            if(choice==1){
                System.out.println("Enter your stream to compress -> ");
                Scanner sc1=new Scanner(System.in);
                String input1=sc1.nextLine();
                Compression(input1);
            }
            if(choice==2){
                System.out.println("Enter your Tags to Decompress like this Form : <number,char>");
                Scanner sc2=new Scanner(System.in);
                String input2=sc2.nextLine();
                Decompression(input2);
            }
            if(choice==3){
                break;
            }
        }
    }
}


