import java.io.File;
import java.io.FileInputStream;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Main {

    static File file;
    static String fileName;
    static String cwd = new String(System.getProperty("user.dir") + '/');
    static HashMap<Character , Integer> freq = new HashMap<Character, Integer>();
    static HashMap<Character , String> id = new HashMap<Character, String>();


    static boolean isTxtFile()
    {
        if(fileName.length() < 4)return false;
        String target = ".txt";
        int idx = 0;
        for (int i = fileName.length() - 4 ; i < fileName.length(); i++)
        {
            if((fileName.charAt(i)) != (target.charAt(idx)))return false;
            idx++;
        }
        return true;
    }

    public static HuffTree builtTree()
    {
        PriorityQueue minHeap= new PriorityQueue(Comparator.reverseOrder());

        for(Character c : freq.keySet())
        {
            minHeap.add(new HuffLeafNode(c , freq.getOrDefault(c , 0)));
        }
        HuffTree temp1 , temp2 , temp3 = null;
        while(minHeap.size() > 1)
        {

            temp1 = (HuffTree) minHeap.poll();
            temp2 = (HuffTree) minHeap.poll();

            temp3 = new HuffTree(new HuffInternalNode(temp1.root() , temp2.root()));

            minHeap.add(temp3);
        }

        return temp3;
    }

    public static void dfs(HuffBaseNode root, String code)
    {
        if(root.isLeaf())
        {
            id.put(((HuffLeafNode)root).value() , code);
            return;
        }
        dfs(((HuffInternalNode)root).left() , code + "0");
        dfs(((HuffInternalNode)root).right() , code + "1");
    }


    public static void main(String[] args) {

        System.out.println("Welcome to my compress tool");

        boolean validInput = false;
        Scanner input = new Scanner(System.in);
        while(!validInput)
        {
            System.out.println("you want to compress or decompress [C / D] ");
            char CD =  input.next().charAt(0);


            if(CD != 'C' && CD != 'D')
            {
                System.out.println("Please Enter a valid input!");
            }
            else validInput = true;
        }

        input.nextLine();


        validInput = false;
        while(!validInput)
        {
            System.out.println("Please Enter the text file name : ");
            fileName = new String(input.nextLine());

            if(isTxtFile())
            {
                validInput = true;
            }
            else {
                System.out.println("please Enter ((TEXT)) File! ");
            }
        }

        try
        {
            file = new File(cwd + fileName);
            FileInputStream fis = new FileInputStream(file);
            int r=0;
            while((r=fis.read())!=-1)
            {
                freq.put((char)r , freq.getOrDefault((char)r , 0) + 1);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        HuffTree tree = builtTree();
        dfs(tree.root() , "");



    }
}