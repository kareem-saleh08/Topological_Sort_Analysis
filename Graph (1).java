import java.util.LinkedList;
import java.util.Scanner;

public class Graph
{
    private int V; // number of vertices
    private int E; // number of edges
    private Vertex[] vertices; // array of vertices
    private LinkedList<Integer>[] adj; // array of Linked Lists of Integers (adjacency list)

    public Graph(int V, int E)  //param constructor
    {
        // Constraints on vertex and edge count
        if (V >= 1 && V <= 1000)
        {
            if (V >= 0 && E <= 10000)
            {
                this.V = V; // initializing # vertices
                this.E = E; // initializing # edges
                vertices = new Vertex[V]; // empty array with size of # vertices
                adj = new LinkedList[V]; // empty array of linked lists with size of # vertices
                for (int i = 0; i < V; i++)
                {
                    vertices[i] = new Vertex(i); 
                    adj[i] = new LinkedList<>(); 
                }
            }
        }
    }

    
    public void addEdge(int f, int t) //adding edge
    {
        adj[f].add(t); //edge from f to t (adding a node holding t to the linked list at index f)
        vertices[t].indegree++; //incrementing indegree of t as a result of f pointing to it using directed edge
    }

    
    public void topSort() // topological sort method
    {
        BrowserQueue q = new BrowserQueue(); //initializing queue of integers to hold vertices' data
        int counter = 0; // topological number counter

        
        for (Vertex v : vertices) // for each loop over the vertices array 
        {
            if (v.indegree == 0)
            {
                q.enqueue(v.data); // we enquueue the vertex
            }
        }

        
        while (!q.isEmpty()) // while q still has elements
        {
            int v_data = q.dequeue(); // dequeuing the first element and storing it to later decrement the indegree of all the vertices that it points to
            Vertex vert = vertices[v_data];
            vert.topNum = ++counter; //incrementing counter then storing its order in topNum var

            
            for (int w_data : adj[v_data]) // for each loop over the linked list in index of the dequeued vertex
            {
                Vertex w = vertices[w_data];
                w.indegree--; // decrementing indegree

                // If no remaining incoming edges, enqueue it
                if (w.indegree == 0)
                {
                    q.enqueue(w.data); // enqueueing into queue
                }
            }
        }

        
        if (counter != V)
        {
            CycleFoundException();  // a cycle is created
        }
        else
        {
            int[] top = new int[counter];

            
            for (Vertex v : vertices)
            {
                if (v.topNum != -1)
                {
                    top[v.topNum - 1] = v.data;
                }
            }

            // Output sorted by top numbers result
            for (int i : top)
            {
                System.out.print(i + " ");
            }
        }
    }

    // Method to handle cycle detection
    public void CycleFoundException()
    {
        System.out.println("Cycle detected.");
    }

    // Vertex class represents each node in the graph
    public static class Vertex
    {
        int data; // vertex number
        int indegree; // number of incoming edges
        int topNum; // topological order number

        public Vertex(int d)
        {
            this.data = d;
            this.indegree = 0;
            this.topNum = -1;
        }
    }

    public static void main(String[] args)
    {
         Scanner scanner = new Scanner(System.in);

        int V = scanner.nextInt();

        int E = scanner.nextInt();

        Graph g = new Graph(V, E);

    
        for (int i = 0; i < E; i++)
        {
            int from = scanner.nextInt();
            int to = scanner.nextInt();
            g.addEdge(from, to);
        }

    
        g.topSort();

        scanner.close();

    }
}


class BrowserArrayList
{
    int capacity = 10; //capacity variable indicating the max number of element that can be stored
    int[] arr = new int[capacity]; // declaring an array member with capacity
    public int size = 0; // number of elements currently in the array but private to avoid tampering
    public int front = -1; //declaration of the element at front of the queue
    public int back = -1; //declaration of the element at back of the queue

    public BrowserArrayList()
    {
        capacity = 10;
        arr = new int[capacity];
        size = 0;
        front = -1;
        back = -1;
    }

    public int getSize()
    {
        return size;
    }

    boolean isEmpty()
    {
        return front == -1; //if front hasn't been changed, indicating that no element was added
    }

    boolean isFullNorm()
    {
        return front == 0 && back == capacity - 1; //if front of queue at 0 and back at the capacity-1
    }

    boolean isFullLoop()
    {
        return ((back + 1) % capacity) == front; //if back is at the element right before front. Using mod to avoid array out of bounds and allow circular arraylist
    }

    //add is O(n) in the case of a full array since we have multiple single for loops allowing us to resize and copy elements and multiplication constant is ignored
    //add is O(1) in the case of a non-full array since we just put the element at the desired index
    //Therefore, add is of order O(n) since O(n) dominates the O(1)
    
    public void add(int a)
    {
        if (isFullNorm() || isFullLoop()) //checking if arraylist is full (whether wrapped around or just full for beginning to end front at 0 and back at capacity-1)
        {
            this.capacity = capacity * 2 + 1; //resizing the array
            int[] newarr = new int[capacity]; //creating new array with new capacity

            if (front <= back) //checking the front is before back
            {
                for (int i = 0; i < size; i++) //loop around original array
                {
                    newarr[i] = this.arr[i]; //copying array elements to the new array
                }
                front = 0; //resetting front
                back = size - 1; //resetting size based on number of elements
                this.arr = newarr; //making original array become the resized array
                this.arr[++back] = a; //incrementing back and then adding an element at the new back index
                this.size++; //incrementing due to new element being added
            }
            else //case where array has wrapped around and back is before front
            {
                for (int i = 0, j = front; j < this.arr.length; i++, j++) //for loop with 2 indices. i looping over new resized array and j looping around the original array
                {
                    //this for loop copies elements from front to the end into the new resized array
                    newarr[i] = this.arr[j];
                }

                for (int i = size - (this.arr.length - front), j = 0; j <= back; i++, j++) //for loop with 2 indices. i looping over new resized array and j looping around the original array
                {
                    //this for loop copies elements from beginning of the array to the back index into the new resized array
                    newarr[i] = this.arr[j];
                }

                front = 0; //resetting front
                back = size - 1; //resetting size based on number of elements
                this.arr = newarr; //making original array become the resized array
                this.arr[++back] = a; //incrementing back and then adding an element at the new back index
                this.size++; //increment size after incrementing elements
            }
        }
        else
        {
            if (front == -1) //checking if arraylist is empty
            {
                front = 0;
            }

            back = (back + 1) % capacity; //back is incremented then mod capacity aims to avoid going out of bounds and enables wrapping around
            this.arr[back] = a; //adding element at the back index
            this.size++; //incrementing size
        }
    }

    //remove is O(1) since removing at end requires no for loops 
    
    public int delete()
    {
        if (front == -1) //empty arraylist
        {
            return Integer.MIN_VALUE; //error value
        }
        else if (front == back) //if 1 element arraylist
        {
            int del = arr[front]; //storing the element going to be deleted
            front = -1;
            back = -1;
            this.size = 0; //size to 0
            return del; //returning the deleted element
        }
        else
        {
            int del = arr[front]; //storing the element going to be deleted
            front = (front + 1) % capacity; //front is incremented then mod capacity aims to avoid going out of bounds and enables wrapping around
            this.size--; //decrementing size
            return del; //returning the deleted element
        }
    }

    public void Arrayclear()
    {
        ArraydoClear();
    }

    // ArraydoClear() is O(1) since we just make front and back indices be -1 and create(+assign) a new empty array
    private void ArraydoClear()
    {
        arr = new int[capacity]; //creating an empty array and assigning it to our original variable that stores array
        front = -1;
        back = -1;
        size = 0; //size to 0
    }

    // Printing the array elements directly
    public void printArray()
    {
        if (isEmpty())
        {
            System.out.println("Array is empty");
            return;
        }

        for (int i = 0, idx = front; i < size; i++)
        {
            System.out.print(arr[idx] + " ");
            idx = (idx + 1) % capacity;
        }
        System.out.println();
    }
}


class BrowserQueue extends BrowserArrayList
{
    //enqueue uses the Arraylist add which is O(n) in the case of a full array since we have multiple single for loops allowing us to resize and copy elements and multiplication constant is ignored
    //enqueue uses the Arraylist add which is O(1) in the case of a non-full array since we just put the element at the desired index
    //Therefore, enqueue uses the Arraylist add which is of order O(n) since O(n) dominates the O(1)
    
    public int enqueue(int value)
    {
        this.add(value); //enqueue uses the Arraylist add which is of order O(n) since O(n) dominates the O(1) to implement circular queue
        return value;
    }

    public int dequeue()
    {
        if (getSize() != 0) //checking if queue is not empty
        {
            return this.delete(); //dequeue is using ArrayList's remove which is O(1) since we have front index and we are removing from the front of the queue by just incrementing front, removing element after storing it to return
        }
        return Integer.MIN_VALUE; //error value
    }

    // Printing the queue elements directly
    public void printQueue()
    {
        if (isEmpty())
        {
            System.out.println("Queue is empty");
            return;
        }

        for (int i = 0, idx = front; i < size; i++)
        {
            System.out.print(arr[idx] + " ");
            idx = (idx + 1) % capacity;
        }
        System.out.println();
    }
}