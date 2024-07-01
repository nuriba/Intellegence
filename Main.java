import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        RelatedMethods relatedMethods = new RelatedMethods(); // calling the class
        // takes the file name and reads it
        String fileName= args[0];
        File file = new File(fileName);
        Scanner inputFile = new Scanner(file);
        FileWriter writer = new FileWriter(args[1]); // to write the outputs to the output file
        String[] lineList = inputFile.nextLine().split(" ");
        // finds the boss at the beginning
        String bossName = lineList[0];
        String bossStringGMS = lineList[1];
        Node boss = new Node(bossName,bossStringGMS); // creates a node for boss.
        // Taking the inputs from the file.
        while (inputFile.hasNextLine()){
            String[] lineList1 = inputFile.nextLine().split(" ");
            String operationName = lineList1[0];
            // all if-else blocks works for a specific operation
            if (operationName.equals("MEMBER_IN")) {
                Node newPerson = new Node(lineList1[1],lineList1[2]);
                boss = relatedMethods.insert(newPerson,boss,writer);
            }else if (operationName.equals("INTEL_TARGET")){
                Node firstChild = new Node(lineList1[1],lineList1[2]);
                Node secondChild = new Node(lineList1[3],lineList1[4]);
                Node targetPerson= relatedMethods.targetNode(firstChild,secondChild,boss);
                writer.write("Target Analysis Result: " + targetPerson.name + " " + targetPerson.stringGMS +"\n");
                writer.flush();
            }else if (operationName.equals("MEMBER_OUT")){
                boss = relatedMethods.deletion(boss, Float.parseFloat(lineList1[2]),0,writer);
            }else if (operationName.equals("INTEL_DIVIDE")){
                HashMap<Node,Integer> elements = new HashMap<>();
                relatedMethods.division(boss,elements);
                writer.write("Division Analysis Result: " + elements.size() + "\n");
                writer.flush();
            }else if (operationName.equals("INTEL_RANK")){
                Node willSearch = new Node(lineList1[1],lineList1[2]);
                int currentRank=0;
                int wantedRank = relatedMethods.search(boss,willSearch,currentRank);
                ArrayList<Node> sameRankPeople = new ArrayList<>();
                int current=0;
                relatedMethods.findSameRank(boss,wantedRank,current,sameRankPeople);
                writer.write("Rank Analysis Result:");
                for (Node person: sameRankPeople){
                    writer.write(" " + person.name + " " + person.stringGMS);
                    writer.flush();
                }
                writer.write("\n");
                writer.flush();
            }

        }

    }
}