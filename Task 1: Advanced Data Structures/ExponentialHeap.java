class ExponentialHeap {
    constructor(exponent) {
        this.heap = [];
        this.exponent = exponent;
        this.currentHeapSize = 0;
    }

    heapifyUp(index) {
        let temp = this.heap[index];
        while (index > 0 && temp > this.heap[this.getParent(index)]) {
            this.heap[index] = this.heap[this.getParent(index)];
            index = this.getParent(index);
        }
        this.heap[index] = temp;
    }

    insert(item) {
        this.heap[this.currentHeapSize] = item;
        this.currentHeapSize++;
        this.heapifyUp(this.currentHeapSize - 1);
    }

    popMax() {
        if (this.heap.isEmpty()) {
            return null;
        }
        let max = this.heap[0];
        this.heap[0] = this.heap[this.currentHeapSize - 1];
        this.heap[this.currentHeapSize - 1] = -1;
        currentHeapSize--;
        for (let i = 0; i < this.currentHeapSize; i++) {
            this.heapifyUp(i);
        }
        return max;
    }

    isEmpty() {
        return !this.heap.length;
    }

    getParent(index) {
        return ((index - 1) / Math.pow(2, this.exponent));
    }

    printHeap() {
        for (let i = 0; i < this.currentHeapSize; i++) {
            console.log(this.heap[i] + ", ");
        }
    }
}
