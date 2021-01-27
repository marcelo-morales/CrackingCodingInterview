import java.util.HashSet;

public class LinkedList {

    public class LinkedListNode {
        public LinkedListNode next, prev, last;
        public int data;
        public LinkedListNode(int d, LinkedListNode n, LinkedListNode p) {
            data = d;
            setNext(n);
            setPrevious(n);
        }

        public LinkedListNode(int d) {
            this.data = d;
        }

        public LinkedListNode() {}

        public void setNext(LinkedListNode n) {
            next = n;
            if (this == last) {
                last = n;
            }
            if (n != null && n.prev != this) {
                n.setPrevious(this);
            }
        }

        private void setPrevious(LinkedListNode previous) {
            prev = previous;
            if (previous != null && previous.next != this) {
                previous.setNext(this);
            }
        }

        public LinkedListNode clone() {
            LinkedListNode next2 = null;
            if (next != null) {
                next2 = next.clone();
            }
            LinkedListNode head2 = new LinkedListNode(data, next2, null);
            return head2;
        }


    }


    /*
    Write code to remove duplicates from an unsroted linked list
     */

    //in order to remove deuplicaions from a linked list, we need to be able to track duplicates
    // a simple hash table will work here

    void delteSups(LinkedListNode n) {
        HashSet<Integer> set = new HashSet<Integer>();
        LinkedListNode previous = null;
        while (n != null) {
            if (set.contains(n.data)) {
                previous.next = n.next;
            } else {
                set.add(n.data);
                previous = n;
            }
            n = n.next;
        }
    }

    //if we are not allowed to have a buffer, we have two pointers
    //current which iterates through the linked list
    //and runner which checks all subsequent nodes for duplicates

    void delteSups(LinkedListNode head) {
        LinkedListNode current = head;
        while (current != null) {
            //remove all future nodes that have the same value
            LinkedListNode runner = current;
            while (runner.next != null) {
                if (runner.next.data == current.data) {
                    runner.next = runner.next.next;
                } else {
                    runner = runner.next;
                }
            }
            current = current.next;
        }

    }

    //runs in O(1) space but in O(N^2) time!!!


}
