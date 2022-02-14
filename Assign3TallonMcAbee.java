import java.util.*;
import java.io.*;

public class Assign3TallonMcAbee
{
    public static void main(String [] args) throws IOException
    {
        Scanner userInput = new Scanner(System.in);
        System.out.print("Input file name (with .txt extension): ");
        String fileLocation = userInput.next();

        int[][] mainMatrix = matrixReaderFunc(fileLocation);

        // int[][] testMatrix = {
        //     {0, 1, 1, 0, 0}, // v0
        //     {0, 0, 1, 0, 1}, // v1
        //     {0, 0, 0, 1, 0}, // v2
        //     {0, 0, 0, 0, 1}, // v3
        //     {0, 0, 0, 0, 0} // v4
        // };

        DFS(mainMatrix);
        BFS(mainMatrix);
    }

    public static int[][] matrixReaderFunc(String file) throws IOException
    {
        String nextLine;
        int rowCount = 0;
        BufferedReader matrixReader = new BufferedReader(new FileReader("src/" + file));

        int vcount = Integer.parseInt(matrixReader.readLine());
        int[][] rmatrix = new int[vcount][vcount];

        while ((nextLine = matrixReader.readLine()) != null)
        {
            int colCount = 0;
            for (int i = 0; i < nextLine.length(); i++)
            {
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

    public static void DFS(int[][] m)
    {
        int vertexCount = 0;
        boolean visited[] = new boolean[m.length];
        LinkedList<Integer> dfsQueue = new LinkedList<Integer>();

        System.out.println();
        System.out.print("DFS: ");

        dfsQueue.add(vertexCount);
        visited[vertexCount] = true;

        System.out.print(dfsQueue.removeFirst() + " ");

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

    public static void BFS(int[][] m)
    {
        int vertexCount = 0;
        boolean visited[] = new boolean[m.length];
        LinkedList<Integer> bfsQueue = new LinkedList<Integer>();

        System.out.println();
        System.out.print("BFS: ");

        bfsQueue.add(vertexCount);
        visited[vertexCount] = true;

        System.out.print(bfsQueue.removeFirst() + " ");

        for (int i = 0; i < m.length; i++)
        {
            if (m[vertexCount][i] == 1 && visited[i] != true)
            {
                bfsQueue.add(i);
                visited[i] = true;
            }
        }

        while(bfsQueue.size() != 0)
        {
            visitBFS(m, bfsQueue, visited, bfsQueue.getFirst());
        }
    }

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