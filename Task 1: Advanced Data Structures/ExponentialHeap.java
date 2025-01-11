import java.util.ArrayList;

public class ExponentialHeap <T extends Comparable<T>> {
    
    // Properties
    private final int children;
    private final ArrayList<T> data;

    // Constructor
    public ExponentialHeap(int children) {
        this.validate(children);
        this.children = children;
        this.data = new ArrayList<T>();
    }

    // Validates the number of children in the heap
    private void validate(int children) {
        if (children <= 0) {
            throw new IllegalArgumentException("Number of children must be >= 0");
        }
        // Check that 'children' is a power of 2
        double logChildren = Math.log(children) / Math.log(2);
        if (Math.ceil(logChildren) != Math.floor(logChildren)) {
            throw new IllegalArgumentException("The number of children must be a power of 2");
        }
    }

    // Insert a node into the heap
    public void insert(T node) {
        data.add(node);
        int index = data.size() - 1;
        while (index > 0) {
            index = this.swapUp(index);
        }
    }

    // Compare a child to its parent, and swap if necessary to maintain heap property
    private int swapUp(int index) {
        T childValue = data.get(index);
        int parentIndex = (int) Math.floor((float)(index - 1) / children);
        if (parentIndex >= 0) {
            T parentValue = data.get(parentIndex);
            // Swap the child and parent if the child is greater than the parent
            if (childValue.compareTo(parentValue) > 0) {
                data.set(parentIndex, childValue);
                data.set(index, parentValue);
                return parentIndex;
            }
        }
        return -1;
    }

    // Pop the max value from the heap, or null if heap is empty
    public T popMax() {
        if (data.size() > 0) {
            T maxValue = data.get(0);
            // If there are more than 2 nodes in the heap, adjust the heap accordingly after popping the max
            if (data.size() > 1) {
                T lastValue = data.remove(data.size() - 1);
                data.set(0, lastValue);
                int index = 0;
                while (index >= 0) {
                    index = this.swapDown(index);
                }
            }
            return maxValue;
        } else {
            return null;
        }
    }

    // Check all children against a parent and swap the parent with the highest child (if necessary) to maintain heap property
    private int swapDown(int parentIndex) {
        T parentValue = data.get(parentIndex);

        // Find the largest child of the given parent
        int largestIndex = 0;
        T largestValue = null;
        for (int i = 0; i < children; i++) {
            int childIndex = children * parentIndex + i + 1;
            if (childIndex < data.size() - 1) {
                T childValue = data.get(childIndex);
                if (largestValue == null || childValue.compareTo(largestValue) > 0) {
                    largestIndex = childIndex;
                    largestValue = childValue;
                }
            }
        }

        // Swap the parent and child if necessary
        if (largestValue != null && parentValue.compareTo(largestValue) < 0) {
            data.set(parentIndex, largestValue);
            data.set(largestIndex, parentValue);
            return largestIndex;
        }

        return -1;

    }

    // Display the heap
    public void printHeap() {
        for (int i = 0; i < data.size(); i++) {
            System.out.print(data.get(i));
            System.out.print(',');
        }
        System.out.println();
    }


    // Main function for testing ExponentialHeap
    public static void main(String[] args) {
        int children = 16; // number of nodes in the heap

        ExponentialHeap testHeap = new ExponentialHeap(children);

        testHeap.insert(8);
        testHeap.insert(20);
        testHeap.insert(5);
        testHeap.insert(2);
        
        testHeap.printHeap();

        System.out.println("Max value in heap: " + testHeap.popMax());

        testHeap.printHeap();

        System.out.println("Max value in heap: " + testHeap.popMax());

        testHeap.printHeap();
        
    }

}
