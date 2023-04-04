public class Node {


    Character symbol;
    String code;
    Integer  number;
    Integer counter;
    Node right;
    Node left;
    public  Node(){

        symbol='þ';
        code="";
        number=0;
        counter=0;
        right=left=null;

    }
    public Node(char symbol,String code,int number,int count,Node right,Node left)
    {
        this.symbol = symbol;
        this.code = code;
        this.number = number;
        this.counter = count;
        this.right = right;
        this.left = left;
    }

}
