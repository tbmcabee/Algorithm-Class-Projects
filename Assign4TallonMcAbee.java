import java.util.*;
import java.io.*;

public class Assign4TallonMcAbee 
{
    public static void main(String [] args) throws IOException
    {
        //tempArray is utilized as a temporary array for storage during the sorting process for each individual temp file
        int[] tempArray;
        //Throughout this program I utilized BufferedReader to read out files and user input, for this case it is utilized for taking in
        //user input which will be used for accessing the specific file that houses the inital integer list. 
        BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));

        //The comments below prompt the user to enter the file name and .txt extension of the file which will be utilized
        System.out.println("Please input an input file name, along with its .txt extension: ");
        String userChoice = userInput.readLine();

        //The static readMasterFile function will take the inital list, read its values and then seperate it into 4 seperate files. Each with
        //the naming scheme of: tempFile(File ID#)
        readMasterFile(userChoice); 

        //This for loop cycles through each of the tempFiles in order to sort out each file. It does so by offloading the array data into the
        //tempArray variable, sorts it utilizing Java's built in sorting algorithm and then writes it back to its tempFile. 
        for (int i = 0; i < 4; i++)
        {
            tempArray = loadTempFile("tempFile"+i+".txt");
            Arrays.sort(tempArray);
            writeArray("tempFile"+i+".txt", tempArray);
        }

        //Each of these statements will offload 2 tempFile's arrays, merge sort them and then write them to a combined tempFile. This will repeat until
        //all 4 files have been mergeSorted into the result.txt file. 
        writeArray("tempFile4.txt", mergeSort(loadTempFile("tempFile0.txt"), loadTempFile("tempFile1.txt")));

        writeArray("tempFile5.txt", mergeSort(loadTempFile("tempFile2.txt"), loadTempFile("tempFile3.txt")));

        writeArray("result.txt", mergeSort(loadTempFile("tempFile4.txt"), loadTempFile("tempFile5.txt")));
    }

    public static void readMasterFile(String fileName) throws IOException
    {
        //This group of statements initalizes all utilized varibles
        //Handles all file reads
        BufferedReader inputReader;
        //Handles all string inputs read from the input file
        String stringInput = "";
        //Utilized for indexing the array when adding new values
        int intcounter = 0;
        //Utilized for indexing each new tempFile
        int tempFileNum = 0;
        //Defines the integer chunk size for each tempFile, in this case it is 250000 integers
        int chunk = 250000;
        //Defines a new array based on the chunk size defined above
        int[] returnArray  = new int[chunk];

        try
        {
            //Sets the inputreader variable to read out of the fileName txt file. 
            inputReader = new BufferedReader(new FileReader(fileName));
            
            //This loop will continously read the file until it reads a null value for the next read line
            while((stringInput = inputReader.readLine()) != null)
            {  
                //The stringInput variable is converted into an integer value and then set to returnArray's intcounter position
                returnArray[intcounter] = Integer.parseInt(stringInput);
                intcounter++; 

                //This conditional checks if the index counter for the array has reached the chunk limit
                if (intcounter >= chunk)
                {
                    //resultWriter is initialized and pointed towards the new tempFile for all future writes
                    BufferedWriter resultWriter = new BufferedWriter(new FileWriter("tempFile"+tempFileNum+".txt"));
                    //This for loop will write each of returnArray's stored integer values into its corresponding tempFile 
                    for (int i = 0; i < returnArray.length; i++)
                    {
                        resultWriter.write(String.valueOf(returnArray[i]));
                        resultWriter.newLine();
                    }
                    //Below here all variables are reset for the file writer object, intcounter index, array values and finally the tempFile index is increased by 1
                    resultWriter.close();
                    intcounter = 0;
                    tempFileNum ++;
                    Arrays.fill(returnArray, 0);
                }
            }

        }
        catch (IOException except)
        {
            except.printStackTrace();
        }
    }

    public static int[] loadTempFile(String fileName) throws IOException
    {
        //This group of statements initalizes all utilized varibles.
        //Each of these variables are utilized in the same fashion as they were within the readMasterfile func.
        //With the exception of arrayCounter and lineCounter, lineCounter is now utilized to count the number of lines present in a file
        //so that its corresponding array can be sized correctly, and arrayCounter is the new array index. 
        BufferedReader inputReader;
        String stringInput = "";
        int lineCounter = 0;
        int arrayCounter = 0;
        int[] newArray;

        try
        {
            //inputReader is pointed towards the fileName file which will be utilized to load data into the returning int array
            inputReader = new BufferedReader(new FileReader(fileName));
            
            //This while loop will read and index each of the file's lines in order to establish how large the file is
            //and decide how large of an array needs to be created for it. 
            while ((stringInput = inputReader.readLine()) != null)
            {
                lineCounter++;
            }

            inputReader.close();

            //The returning array is initialized based on the previously recorded size of the read file
            newArray = new int[lineCounter];

            inputReader = new BufferedReader(new FileReader(fileName));

            //This while loop will read the file line by line, converting each line's string into an int and then
            //placing the value within the returning array based on its index. 
            while ((stringInput = inputReader.readLine()) != null)
            {
                newArray[arrayCounter] = Integer.parseInt(stringInput);
                arrayCounter++; 
            }

            inputReader.close();
    
            return newArray;
        }
        catch (IOException except)
        {
            except.printStackTrace();
            return null; 
        }
    }

    public static void writeArray(String fileName, int[] a1) throws IOException
    {
        //resultWriter is initialized and pointed towards the new tempFile for all future writes.
        BufferedWriter resultWriter = new BufferedWriter(new FileWriter(fileName));

        try
        {
            //The following for loop is utilized to write each of the a1 array's values into
            //its own specific line within the new file named, according to the value stored in fileName. 
            for (int i = 0; i < a1.length; i++)
            {
                resultWriter.write(String.valueOf(a1[i]));
                resultWriter.newLine();
            }
        }
        catch (IOException except)
        {
            except.printStackTrace();
        }

        resultWriter.close();
    }

    public static int[] mergeSort(int[] a1, int[] a2) throws IOException
    {
        //Initializes all variables utilized within the mergeSort func
        //Index1 and Index2 will be utilized to index through arrays a1 and a2.
        //returnArray is created based on the size of both arrays a1 and a2, and its 
        //corresponding index variable is returnIndex. 
        int index1 = 0;
        int index2 = 0;
        int returnIndex = 0;
        int[] returnArray = new int[a1.length + a2.length];

        //This while loop checks if each of the index values is less then its corresponding array's length.
        //If the conditional is meet then  each value is checked based on their corresponding index, then which ever value is the lowest
        //between a1[index1] and a2[index2] is set into the returnArray for storage. Then the added value's array is indexed, whereas the other array
        //will not be indexed until its current value is added into the returnArray. This gurantees values are not skipped. 
        while ((index1 < a1.length) && (index2 < a2.length))
        {
            //check if a1's value is less then a2's
            if (a1[index1] < a2[index2])
            {
                returnArray[returnIndex] = a1[index1];
                returnIndex++;
                index1++;
            }
            else //if a2 is less then or equal to a1's value then it is added into the returnArray
            {
                returnArray[returnIndex] = a2[index2];
                returnIndex++;
                index2++;
            }
        }

        //This if else statement checks which array still has values left over that were not copied into the new returnArray. Since the conitional will cut off for the while loop
        //when a single array meets it requirement, this means one of the arrays will still have values left to copy after its counterpart has copied all of its values into returnArray.
        //This if else statement will run the left over values array and copy its final values into the returnArray until it has copied all of its remainding values. 
        //If the index1 is not the same value as a1's length, then it means it did not break out of the conditional and
        //has values left over to copy. 
        if (index1 != (a1.length))
        {
            for (int i = index1; i < a1.length; i++)
            {
                returnArray[returnIndex] = a1[index1];
                returnIndex++;
                index1++;
            }
        }
        else //If index1 does equal a1's length, then this automatically means that a2 has leftover values and the else statement will begin copying its values. 
        {
            for (int i = index2; i < a2.length; i++)
            {
                returnArray[returnIndex] = a2[index2];
                returnIndex++;
                index2++;
            }
        }

        return returnArray;
        
    }
  
}
