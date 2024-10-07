import java.io.*;
import java.util.*;

public class Main {

    static File file;
    static String fileName;
    static String cwd = new String(System.getProperty("user.dir") + '/');
    static HashMap<Character, Integer> freq = new HashMap<Character, Integer>();
    static HashMap<Character, boolean[]> id = new HashMap<>();
    static HashMap<boolean[], Character> reverseId = new HashMap<>();
    static int totalNumberOfWords = 0;


    static boolean isTxtFile() {
        if (fileName.length() < 4) return false;
        String target = ".txt";
        int idx = 0;
        for (int i = fileName.length() - 4; i < fileName.length(); i++) {
            if ((fileName.charAt(i)) != (target.charAt(idx))) return false;
            idx++;
        }
        return true;
    }

    public static HuffTree builtTree() {
        PriorityQueue<HuffTree> minHeap = new PriorityQueue();

        for (Character c : freq.keySet()) {
            minHeap.add(new HuffTree(new HuffLeafNode(c, freq.get(c))));
        }

        HuffTree temp1, temp2, temp3 = null;
        while (minHeap.size() > 1) {

            temp1 = (HuffTree) minHeap.poll();
            temp2 = (HuffTree) minHeap.poll();

            temp3 = new HuffTree(new HuffInternalNode(temp1.root(), temp2.root()));

            minHeap.add(temp3);
        }

        return temp3;
    }

    public static void dfs(HuffBaseNode root, String code) {
        if (root.isLeaf()) {
            HuffLeafNode leaf = (HuffLeafNode) root;
            char c = leaf.value();
            id.put(c, id.getOrDefault(c, new boolean[code.length()]));
            boolean[] temp = new boolean[code.length()];
            for (int i = 0; i < code.length(); i++) {
                id.get(c)[i] = (code.charAt(i) == '1');
                temp[i] = (code.charAt(i) == '1');
            }
            reverseId.put(id.get(c), c);
            return;
        }
        dfs(((HuffInternalNode) root).right(), code + "1");
        dfs(((HuffInternalNode) root).left(), code + "0");

    }

    public static boolean isCompFile() {
        if (fileName.length() < 4) return false;
        String target = "Comp";
        int idx = 0;
        for (int i = 0; i < 4; i++) {
            if ((fileName.charAt(i)) != (target.charAt(idx))) return false;
            idx++;
        }
        return true;
    }

    public static void getValidFileCompName() {
        Scanner input = new Scanner(System.in);
        boolean validInput = false;
        while (!validInput) {
            System.out.println("Please Enter the compressed file name : ");
            fileName = new String(input.nextLine());
            if (isCompFile()) {
                validInput = true;
            } else {
                System.out.println("please Enter ((Compressed)) File! ");
            }
        }
    }

    public static void getValidFileTextName() {
        Scanner input = new Scanner(System.in);
        boolean validInput = false;
        while (!validInput) {
            System.out.println("Please Enter the text file name : ");
            fileName = new String(input.nextLine());
            if (isTxtFile()) {
                validInput = true;
            } else {
                System.out.println("please Enter ((TEXT)) File! ");
            }
        }
    }


    public static void main(String[] args) throws IOException {

        System.out.println("Welcome to my compress tool");

        boolean validInput = false;
        Scanner input = new Scanner(System.in);
        char CD = 0;
        while (!validInput) {
            System.out.println("you want to compress or decompress [C / D] ");
            CD = input.next().charAt(0);


            if (CD != 'C' && CD != 'D') {
                System.out.println("Please Enter a valid input!");
            } else validInput = true;
        }

        input.nextLine();


        if (CD == 'D') {
            // Decompress
            getValidFileCompName();
            File Output = new File(cwd + fileName);
            reverseId = null;
            File newFile = new File(cwd + "Decomp" + fileName);
            if (newFile.createNewFile()) {
                System.out.println("File created: " + newFile.getName());
            } else {
                System.out.println("File already exists.");
            }
            reverseId = null;
            id = null;
            boolean[] result = null;

            try (FileInputStream fis = new FileInputStream(Output);
                 ObjectInputStream ois = new ObjectInputStream(fis)) {

                // Reading objects in the correct order
                reverseId = (HashMap<boolean[], Character>) ois.readObject();  // HashMap<boolean[], Character>
                totalNumberOfWords = (Integer) ois.readObject();  // Integer

                try (FileWriter fileWriter = new FileWriter(newFile);
                     BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {

                    for (int i = 0; i < totalNumberOfWords; i++) {
                        result = (boolean[]) ois.readObject();
                        Character c = reverseId.get(result);
                        bufferedWriter.write(c);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

            System.out.println("The file has been decompressed successfully");
        } else {
            // Compress
            getValidFileTextName();

            try {
                file = new File(cwd + fileName);
                FileInputStream fis = new FileInputStream(file);
                int r = 0;
                while ((r = fis.read()) != -1) {
                    totalNumberOfWords++;
                    freq.put((char) r, freq.getOrDefault((char) r, 0) + 1);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            HuffTree tree = builtTree();
            dfs(tree.root(), "");


            File Output = new File(cwd + "Comp" + fileName);

            try {
                if (Output.createNewFile()) {
                    System.out.println("File created: " + Output.getName());
                } else {
                    System.out.println("File already exists.");
                }
            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }


            char content[] = new char[totalNumberOfWords];

            try {
                file = new File(cwd + fileName);
                FileInputStream fis = new FileInputStream(file);
                int r = 0;
                int idx = 0;
                while ((r = fis.read()) != -1) {
                    content[idx++] = (char) r;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


            try (FileOutputStream fileOut = new FileOutputStream(Output);
                 ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
                out.writeObject(reverseId);
                out.writeObject((Integer)totalNumberOfWords);
                System.out.println(totalNumberOfWords);
                for (int i = 0; i < totalNumberOfWords; i++) {
                    boolean[] code = id.get((char) content[i]);
                    out.writeObject(code);
                }
            } catch (IOException e) {
                e.printStackTrace();

            }


            System.out.println("The file has been compressed successfully");


        }


    }
}
