import java.util.*;
import java.io.*;


public class Assign3TallonMcAbee
{
    //This main function handles user string input, txt file reading, 2D matrix creation and both DFS/BFS algorithm function calls. 
    public static void main(String [] args) throws IOException 
    {
        Scanner userInput = new Scanner(System.in); //Scanner object is initalized and reference is set to "userInput"
        System.out.print("Input file name (with .txt extension): "); //This system call prompts a console message informing the user to input a txt file name
        String fileLocation = userInput.next(); //The user's response is recorded in the String fileLocation

        int[][] mainMatrix = matrixReaderFunc(fileLocation); //The 2D matrix is created through the matrixReaderFunc which takes in a string as a parameter

        DFS(mainMatrix); //This static call runs the DFS function and is feed a 2D matrix as a parameter
        BFS(mainMatrix); //This static call runs the BFS function and is feed a 2D matrix as a parameter
    }

    //The matrix reader function takes in a String variable and returns a 2D matrix object after completition. 
    //In this case, the string variable will be the text file name and .txt extension for the matrix file that the user wishes to access. 
    public static int[][] matrixReaderFunc(String file) throws IOException
    {
        //The following handles the initalization of the nextLine string varible, rowCount integer variable and the BufferedReader/FileReader object which
        //will handle all file reading capabilities. 
        String nextLine;
        int rowCount = 0;
        BufferedReader matrixReader = new BufferedReader(new FileReader(file)); //This initialization will intake the file variable as a parameter and utilize it to access the specificated file's general location

        //These next 2 statements will parseInt the first read line of the txt file and turn the string into its corresponding integer value. Which in this context
        //will be the number of verticies within the matrix/graph. 
        int vcount = Integer.parseInt(matrixReader.readLine());
        
        //The following rmatrix variable will be utilized to contruct the graph based upon the requested txt file. In this case, the size will be based on the first read line of
        //the txt file which is stored in the vcount variable. 
        int[][] rmatrix = new int[vcount][vcount];

        //This while loop will read the txt file until the file has no lines left to read, which will be seen as a null value. 
        while ((nextLine = matrixReader.readLine()) != null)
        {
            int colCount = 0;
            for (int i = 0; i < nextLine.length(); i++)
            {
                //Checks if the read character matches the ascii value of 1 or 0. If they match then the corresponding value is added to the newly created matrix. 
                if (nextLine.charAt(i) == 49 || nextLine.charAt(i) == 48)
                {
                    rmatrix[rowCount][colCount] = Character.getNumericValue(nextLine.charAt(i));
                    colCount++;
                }
            }
            rowCount++;
        }

        return rmatrix;
    }

    //The DFS function returns no specific values and takes in an int 2D matrix as its parameter. 
    //In this case, the function will utilize the DFS algorithm to traverse the listed matrix/graph and print out
    //its path into the console. 
    public static void DFS(int[][] m)
    {
        //The following variables are initlaized to be utilized within the DFS algorithm, of which includes the vertexCount integer variable, the visited boolean array that is set to
        //the size based on the amount of vertices listed within the matrix. Finally, an integer linkedlist named dfsQueue will be utilized to record nodes that are currently being
        //visited.  
        int vertexCount = 0;
        boolean visited[] = new boolean[m.length];
        LinkedList<Integer> dfsQueue = new LinkedList<Integer>();

        //These system calls begin the DFS print statement
        System.out.println();
        System.out.print("DFS: ");

        //The vertexCount variable (In this case, it will start at 0) is then added to the dfsQueue and set to visited on the boolean array. 
        dfsQueue.add(vertexCount);
        visited[vertexCount] = true;

        //Pops the first object out of the dfsQueue and prints it out into the DFS statement within the console. 
        System.out.print(dfsQueue.removeFirst() + " ");

        //This for loop will then loop through the columns of the specific selected row (based on the specific vertexCount) to check
        //if the vertex is connected (its value would be 1) and if it has already been visited. If it passes the conditional then the vertex is added to the dfsQueue 
        //and marked as visited. Once completed, the vertex is then sent into the visitDFS function. 
        for (int i = 0; i < m.length; i++)
        {
            if (m[vertexCount][i] == 1 && visited[i] != true)
            {
                dfsQueue.add(i);
                visited[i] = true;
                visitDFS(m, dfsQueue, i, visited);
            }
        }
    }

    //The visitDFS function returns no specific values and takes in an int 2D matrix as its parameter. 
    //In this case, the function will utilize the DFS algorithm to traverse the listed matrix/graph and print out
    //its path into the console. In the visit function, it does the same as the DFS function but does not intialize values. 
    public static void visitDFS(int[][] m, LinkedList<Integer> q, int c, boolean[] v)
    {
        System.out.print(q.removeFirst() + " ");

        for (int i = 0; i < m.length; i++)
        {
            if (m[c][i] == 1 && v[i] != true)
            {
                q.add(i);
                v[i] = true;
                visitDFS(m, q, i, v);
            }
        }
    }

    //The BFS function returns no specific values and takes in an int 2D matrix as its parameter. 
    //In this case, the function will utilize the BFS algorithm to traverse the listed matrix/graph and print out
    //its path into the console.
    public static void BFS(int[][] m)
    {
        //The following variables are initlaized to be utilized within the BFS algorithm, of which includes the vertexCount integer variable, the visited boolean array that is set to
        //the size based on the amount of vertices listed within the matrix. Finally, an integer linkedlist named bfsQueue will be utilized to record nodes that are currently being
        //visited.
        int vertexCount = 0;
        boolean visited[] = new boolean[m.length];
        LinkedList<Integer> bfsQueue = new LinkedList<Integer>();

        //This system call begins the BFS print statement
        System.out.println();
        System.out.print("BFS: ");
        
        //The vertexCount variable (In this case, it will start at 0) is then added to the dfsQueue and set to visited on the boolean array. 
        bfsQueue.add(vertexCount);
        visited[vertexCount] = true;

        //It then pops out the first object onto the bfs print statement and sends it to be prompted through the console. 
        System.out.print(bfsQueue.removeFirst() + " ");

        
        //This for loop will then loop through the columns of the specific selected row (based on the specific vertexCount) to check
        //if the vertex is connected (its value would be 1) and if it has already been visited. If it passes the conditional then the vertex is added to the bfsQueue
        //and marked as visited.
        for (int i = 0; i < m.length; i++)
        {
            if (m[vertexCount][i] == 1 && visited[i] != true)
            {
                bfsQueue.add(i);
                visited[i] = true;
            }
        }

        //This while statement runs until the queue is empty, it will then go through the queue and run the visitBFS function for the corresponding
        //vertex. 
        while(bfsQueue.size() != 0)
        {
            visitBFS(m, bfsQueue, visited, bfsQueue.getFirst());
        }
    }

    //The visitBFS function returns no specific values and takes in an int 2D matrix as its parameter. 
    //In this case, the function will utilize the BFS algorithm to traverse the listed matrix/graph and print out
    //its path into the console. In the visit function, it does the same as the BFS function but does not intialize values. 
    public static void visitBFS(int[][] m, LinkedList<Integer> q, boolean[] v, int c)
    {
        System.out.print(q.removeFirst() + " ");

        for (int i = 0; i < m.length; i++)
        {
            if (m[c][i] == 1 && v[i] != true)
            {
                q.add(i);
                v[i] = true;
            }
        }

        while(q.size() != 0)
        {
            visitBFS(m, q, v, q.getFirst());
        }
    }

}