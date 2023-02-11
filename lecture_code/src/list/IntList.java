package list;
public class IntList {
    public int first;
    public IntList rest;

    public IntList(int f, IntList r){
        first = f;
        rest = r;
    }
    
    /* Return the size of the list using... Recursion! */
    public int size() {
    	if (rest == null) {
    		return 1;
    	}
    	return 1 + this.rest.size();
    }
    
    /* Return the size of the list using no Recursion!*/
    public int iterationSize() {
    	IntList p = this;
    	int totalSize = 0;
    	while(p != null) {
    		totalSize += 1;
    		p = p.rest;
    	}
    	return totalSize;
    }
    
    /* Return the ith in the IntList using Recursion*/
    public int get(int i) {
    	if (i == 0) {
    		return this.first;
    	}
    	return this.rest.get(i-1);
    }
    
    /* Return the ith in the IntList using no Recursion*/
    public int iterationGet(int i) {
    	IntList p = this;
    	for (int j = 0; j < i; j++) {
    		p = p.rest;
    	}
    	return p.first;
    }
    /*Extra IntList Practice*/
    public static IntList incrList(IntList L, int x) {
    	int size = L.iterationSize();
    	IntList Q = new IntList(L.iterationGet(size - 1) + x, null);
    	for (int i = size - 2; i > -1; i-- ) {
    		Q = new IntList(L.iterationGet(i) + x,Q);
    	}
    	return Q;
    }
    
    /*Extra IntList Practice*/
    public static IntList dincrList(IntList L, int x) {
    	IntList Q = L;
    	for (int i = 0; i < L.iterationSize(); i++) {
    		Q.first = L.iterationGet(i) + x;
    		Q = Q.rest;
    	}
    	Q = L;
    	return Q;
    }
    
    public static IntList reverse(IntList L) {
    	IntList Q = new IntList(L.first, null);
    	L = L.rest;
    	while (L != null) {
    		IntList temp = Q;
    		Q = L;
    		L = L.rest;
    		Q.rest = temp;
    	}
    	return Q;
    }
    
    public static void main(String[] args) {
        // IntList L = new IntList();
        // L.first = 5;
        // L.rest = null;

        // L.rest = new IntList();
        // L.rest.first = 10;

        // L.rest.rest = new IntList();
        // L.rest.rest.first = 15;

        IntList L = new IntList(15, null);
        L = new IntList(10, L);
        L = new IntList(5, L);
        
//        System.out.println(L.size());
//        System.out.println(L.iterationSize());
//        System.out.println(L.get(2));
//        System.out.println(L.get(1));
//        System.out.println(L.get(0));
//        System.out.println(L.iterationGet(2));
//        System.out.println(L.iterationGet(1));
//        System.out.println(L.iterationGet(0));
//        System.out.println("==========================");
//        IntList Q = incrList(L, 2);
//        System.out.println("Q:" + Q);
//        System.out.println(Q.iterationGet(2));
//        System.out.println(Q.iterationGet(1));
//        System.out.println(Q.iterationGet(0));
//        
//        System.out.println("L:" + L);
//        System.out.println(L == Q);
//        System.out.println(L.iterationGet(2));
//        System.out.println(L.iterationGet(1));
//        System.out.println(L.iterationGet(0));
//        
//        System.out.println("==========================");
//        IntList R = dincrList(L, 2);
//        System.out.println("R:" + R);
//        System.out.println(R.iterationGet(2));
//        System.out.println(R.iterationGet(1));
//        System.out.println(R.iterationGet(0));
//        
//        System.out.println("L:" + L);
//        System.out.println(L == R);
//        System.out.println(L.iterationGet(2));
//        System.out.println(L.iterationGet(1));
//        System.out.println(L.iterationGet(0));
        
    }
}
