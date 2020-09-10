/*
Program Title: The Dollar Game
File Name: Main.java
External Files: N/A
Names: Grayson Herbert, Jacob Esswein
Email: gdhxnw@umsystem.edu, essweinjacob@gmail.com
Course: 4250-001
Date: TBD
Description: A game to play including a graph and stacks of dollars. The goal is to get every stack with positive dollars.
Resources: N/A
*/
package JavaVersion;
import java.util.Scanner;

// This class functions as a vertex in the dollar game
class Node {
    private String name;    // Name/Label of node
    private int dollars;    // Amount of money node has
    private String connectedNodes = "";     // The nodes this node is connected to

    // Getters
    public String getName(){
        return name;
    }
    public int getDollars(){
        return dollars;
    }
    public String getConnectedNodes(){
        return connectedNodes;
    }

    // Setters
    public void setName(String newName){
        this.name = newName;
    }
    public void setDollars(int newDollars){
        this.dollars = newDollars;
    }

    public boolean setConnectedNodes(String newNode){
        // Check if this node is already connected so we dont connect it twice. If not already connect it add it to the string, if not return false so main can send error message
        if(!this.connectedNodes.contains(newNode)){
            this.connectedNodes = this.connectedNodes + newNode;
            return true;
        }else{
            return false;
        }
    }
    
    // Add to dollars
    public void addDollar(){
        this.dollars = this.dollars + 1;
    }

    // Take away a dollar
    public void loseDollar(){
        this.dollars = this.dollars - 1;
    }
}

public class Main{
    // This function will find a given node, and give money to node connected to it
    public static Node [] give(String giveNode, Node [] graph, int numOfNodes){
        // Search through array of node objects to find given node
        for(int i = 0; i < numOfNodes; i++){
            // When node is successfully found
            if(graph[i].getName().contains(giveNode)){
                // Find nodes that are connected to this one and give them 1 dollar and take one dollar from the give
                for(int j = 0; j < numOfNodes; j++){
                    // This if statement verifies if a node is connected to another
                    if(graph[i].getConnectedNodes().contains(graph[j].getName())){
                        graph[j].addDollar();
                        graph[i].loseDollar();
                        System.out.println("Node "  + graph[i].getName() + " is giving a dollar to " + graph[j].getName());
                    }
                }
                break;
            }
        }
        return graph;
    }

    // This function will find a given node and take money from nodes around it
    public static Node [] take(String takeNode, Node [] graph, int numOfNodes){
        // Search through array of node objects to find given node
        for(int i = 0; i < numOfNodes; i++){
            // When node is successfully found
            if(graph[i].getName().contains(takeNode)){
                // Find nodes that are connected to this one and take one dollar from them
                for(int j = 0; j < numOfNodes; j++){
                    // This if statement verifies if a node is connected to another
                    if(graph[i].getConnectedNodes().contains(graph[j].getName())){
                        graph[i].addDollar();
                        graph[j].loseDollar();
                        System.out.println("Node "  + graph[i].getName() + " is taking a dollar from " + graph[j].getName());
                    }
                }
                break;
            }
        }
        return graph;
    }

    // This function checks to see if a winner
    public static void checkWin(Node [] graph, int numOfNodes){
        // Flag that says if a user has won a game
        boolean winFlag = true;
        // Go through each node and see if any of them are a negative amount of dollars
        for(int i = 0; i < numOfNodes; i++){
            // If a node has a negative amount of dollars then the user has ye to win the game
            if(graph[i].getDollars() < 0){
                winFlag = false; 
            }
        }

        // If win flag is still true, display win message
        if(winFlag){
            System.out.println("CONGRATULATIONS YOU HAVE WON. YOU MAY NOW QUIT OR CONTINUE PLAYING");
        }
    }
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);    // Scanner that will be used to get user inputs
        int numOfNodes = 0;

        // While the user has not input a number of nodes between 2 and 7 inclusive
        while(numOfNodes < 2 || numOfNodes > 7){
            System.out.println("Enter how many nodes there will be in this game (2-7): ");
            
            numOfNodes = sc.nextInt();
            // If given an invalid number give descriptive error message, others wise print how many nodes the user inputed
            if(numOfNodes < 2 || numOfNodes > 7){
                System.out.println("Invalid input for number of nodes.");
            }
            else{
                System.out.println("Number of nodes inputed: " + numOfNodes);
            }
        }
        
        // Create node objects using a for loop based on number of nodes wanted
        Node graph[] = new Node[numOfNodes];
        for(int i = 0; i < numOfNodes; i++){
            graph[i] = new Node();
        }
        
        // Label each node
        String graphNodes = "";
        // Go through each node so the user can assign them a label
        for(int i = 0; i < numOfNodes; i++){
            String inputNode = "";  // Label
            while(inputNode.length() != 1 || graphNodes.contains(inputNode)){
                System.out.print("Label the " + i + " node with a single character: ");
                inputNode = sc.next();
                inputNode = inputNode.toLowerCase();

                if(inputNode.length() != 1){
                    System.out.println("You entered more or less then one character. Please try again." + inputNode);
                }else if(graphNodes.contains(inputNode)){
                    System.out.println("The node character has already been used. Please try again");
                }else{
                    //System.out.println("Node " + i + " has been labeled " + inputNode);
                    graph[i].setName(inputNode);
                    graphNodes += inputNode;
                    break;
                }
            }
            //System.out.println("Length of node: " + inputNode.length() + " Label given: " + inputNode + "Is it in graph nodes? " + graphNodes.contains(inputNode));
        }

        // Get amount of edges
        int numOfEdges = 0;
        while(numOfEdges < numOfNodes - 1){
            System.out.println("Enter the number of edges for this game, must more then or equal to the number of nodes - 1: ");
            numOfEdges = sc.nextInt();
            if(numOfEdges < numOfNodes - 1){
                System.out.println("The number of entered edges is too few");
            }            
        }

        // Assign dollar values to each node
        for(int i = 0; i < numOfNodes; i++){
            System.out.println("Enter the dollar amout for node " + graph[i].getName() + ": ");
            int inputDollars = sc.nextInt();
            graph[i].setDollars(inputDollars);
        }

        // Connect nodes
        for(int i = 0; i < numOfEdges; i++){
            String givenConnection = "";
            boolean nodeConnected = false;
            while(!nodeConnected){
                System.out.println("Please enter two nodes you would like to connect: ");
                givenConnection = sc.next();
                givenConnection = givenConnection.toLowerCase();

                // Validate only two nodes are given
                if(givenConnection.length() == 2){
                    String firstLetter = givenConnection.substring(0, 1);
                    String secondLetter = givenConnection.substring(1, 2);
                    System.out.println("First letter: " + firstLetter + " second letter: " + secondLetter);
                    if(firstLetter.equals(secondLetter)){
                        System.out.println("You cannot connect a node to itself. Please try again");
                    }
                    else if(graphNodes.contains(firstLetter) && graphNodes.contains(secondLetter)){
                        System.out.println("here");
                        // If both nodes given are valid find the nodes that are releavent
                        for(int j = 0; j < numOfNodes; j++){
                            // Find first node given and try to connect nodes
                            if(graph[j].getName().contains(firstLetter)){
                                // Once we found the node of the first node given, try to connect them
                                if(graph[j].setConnectedNodes(secondLetter)){
                                    // Nodes are successfully connect mark flag saying an edge has been created
                                    nodeConnected = true;
                                }else{
                                    System.out.println("Nodes " + givenConnection + " have already been connected");
                                }
                            }
                            // Try to connect second node
                            else if(graph[j].getName().contains(secondLetter)){
                                // Once we found the node of the second node given, try to connect them
                                if(graph[j].setConnectedNodes(firstLetter)){
                                    // Nodes are successfully connect mark flag saying an edge has been created
                                    nodeConnected = true;
                                }else{
                                    System.out.println("Nodes " + givenConnection + " have already been connected");
                                }
                            }
                        }
                    }
                    else{
                        System.out.println("You entered nodes that do not exist on the graph. Please try again.");
                    }
                }else{
                    System.out.println("You entered too few or too many nodes to connect. Please only input two nodes.");
                }
            }
        }

        // Game starts here
        String gameCommand = "";
        while(gameCommand != "q"){
            for(int i = 0; i < numOfNodes; i++){
                System.out.println("Node " + graph[i].getName() + " has " + graph[i].getDollars() + " dollars" + " and is connected to: " + graph[i].getConnectedNodes());
            }
            System.out.println("Please select a command from the following:\n Q/q: Quit the game\n Any letter of a node");
            gameCommand = sc.next();
            gameCommand = gameCommand.toLowerCase();
            // Make sure only one letter command is given
            if(gameCommand.length() == 1){
                if(gameCommand.contains("q")){
                    break;
                }else if(graphNodes.contains(gameCommand)){
                    System.out.println("Please select wether you would like to give or take from node" + gameCommand);
                    System.out.println("(G/g):Give\n(T/t):Take");
                    String giveTake = "a";
                    while(!giveTake.contains("g") || !giveTake.contains("t")){
                        giveTake = sc.next();
                        giveTake = giveTake.toLowerCase();
                        if(giveTake.contains("g") || giveTake.contains("t")){
                            if(giveTake.contains("g")){
                                graph = give(gameCommand, graph, numOfNodes);
                                break;
                            }else{
                                graph = take(gameCommand, graph, numOfNodes);
                                break;
                            }
                        }else{
                            System.out.println("Invalid letter given " + giveTake+ "Please try again.");
                        }
                    }
                }else{
                    System.out.println("Please enter a valid game command, q or a valid node");
                }
            }
            else{
                System.out.println("Invalid command given. Please only type one letter");
            }

            // Check win
            checkWin(graph, numOfNodes);
        }

        sc.close();
    }
}