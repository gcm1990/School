 /*                                                                      Author:Gavin Murray
                                                                             N#:01062011
                                                                            Class:COP3530
                                                                          Professor:Ken Martin
                                                           Description:Assignment 5, Huffman Tree Compression     

*/
//----------------------------------------------------------------------------------------------------------------------------------------------------------------------------//
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class bigone{
public static void main(String[] args){
File file = new File(args[0]);
String option, encoding, sp;
char ch;
boolean exit=false;

Queue fileText = new Queue(20);
PriorityQ PQ = new PriorityQ(7);  
Node A = new Node('A');
Node B = new Node('B');      
Node C = new Node('C');        
Node D = new Node('D');
Node E = new Node('E');
Node F = new Node('F');
Node G = new Node('G');
Node[] Nodes = {A,B,C,D,E,F,G}; 

try{
Scanner input = new Scanner(file); 
while(input.hasNextLine()){                        //Read the file 
fileText.ins(input.nextLine());
for(int i=0;i<fileText.peekRear().toCharArray().length;i++){
ch=fileText.peekRear().charAt(i);              
switch(ch){
case 'A':A.f++;break;  
case 'B':B.f++;break;                              //Get frequency
case 'C':C.f++;break;
case 'D':D.f++;break;
case 'E':E.f++;break;
case 'F':F.f++;break;
case 'G':G.f++;break;
}
}
}
input.close();
}
catch(FileNotFoundException e){
e.printStackTrace();
}

 
Tree TA = new Tree(A);
Tree TB = new Tree(B);
Tree TC = new Tree(C); 
Tree TD = new Tree(D);
Tree TE = new Tree(E);
Tree TF = new Tree(F);
Tree TG = new Tree(G);
PQ.ins(TA);
PQ.ins(TB);
PQ.ins(TC);
PQ.ins(TD);
PQ.ins(TE);
PQ.ins(TF);
PQ.ins(TG);

Tree T1= new Tree();
Tree T2= new Tree();
Tree T3= new Tree();
Tree T4= new Tree(); 
Tree T5 = new Tree();
Tree T6 = new Tree(); 
Tree[] Forest = {T1, T2, T3, T4, T5, T6};

for(int i = 0; i<6; i++){                     
Forest[i].R.setL(PQ.peekMin().R);  //Order the trees
PQ.del();                            
Forest[i].R.setR(PQ.peekMin().R);
PQ.del();
Forest[i].R.setf();
PQ.ins(Forest[i]);
}
for(int i=0; i<7; i++){
Nodes[i].code = T6.path(Nodes[i], "");  //Set the respective binary for each node
}
encoding ="";
for(int i=0;i<=fileText.nItems;i++){
for(int j=0;j<fileText.peekFront().toCharArray().length;j++){ 
char letter=fileText.peekFront().charAt(j);
switch(letter){
case'A':encoding+=A.code;
break;
case'B':encoding+=B.code;
break;
case'C':encoding+=C.code;          
break;
case'D':encoding+=D.code;              //Write the binary version of the file
break;
case'E':encoding+=E.code;
break;
case'F':encoding+=F.code;
break;
case'G':encoding+=G.code;
break;
}
}
fileText.del();
}
int k=0;
Queue Bytes= new Queue(63);
for(int i=8;i<encoding.toCharArray().length;i+=8){     
if ((i+8)>=encoding.toCharArray().length)   
k=(encoding.toCharArray().length);   
else
k=i+8;
if(i%24==0)
Bytes.ins("\n"+encoding.substring(i,k));
else
Bytes.ins(" "+encoding.substring(i,k));
}
sp="";
while(!Bytes.isEmpty()){
sp+=Bytes.del();
}

encoding=(encoding.substring(0,8)+sp);
T6.code=encoding;

do{
System.out.println("\nChoose one of the following options:");
System.out.println("a. Display the huffman tree.");
System.out.println("b. Display the character encoding table.");
System.out.println("c. Display the encoded file.");
System.out.println("d. Display the original file.");
Scanner input = new Scanner(System.in);
option= input.nextLine();
switch(option){
case "a": T6.displayTree(); break;               
case "b":                      
System.out.println("Ch|Code");
System.out.println("_______");
System.out.println("A: "+A.code);
System.out.println("B: "+B.code);
System.out.println("C: "+C.code);
System.out.println("D: "+D.code);
System.out.println("E: "+E.code);
System.out.println("F: "+F.code);
System.out.println("G: "+G.code);
break;
case"c":System.out.print(encoding);                //Show binary
break;
case"d":
T6.translate(T6.R, 0);                             //Translate binary
break;
case"exit": 
exit=true;
break;
default:
System.out.println("Invalid option.");
break;
}
}while(!exit);

}
}









class Tree{
public Node R;
String code;

Tree(){
R = new Node();
}
Tree(Node Root){
R=Root;
}
 public void displayTree() {
      Stack globalStack = new Stack(50);
      globalStack.push(R); 
            int nBlanks = 32;
      boolean isRowEmpty = false;
      System.out.println(
      "......................................................");
      while(isRowEmpty==false)
         {
         Stack localStack = new Stack(50);
         isRowEmpty = true;

         for(int j=0; j<nBlanks; j++)
            System.out.print(' ');

         while(globalStack.isEmpty()==false)
            {
            Node temp = (Node)globalStack.pop();
            if(temp != null)
               {
               if(temp.hasChildren()==true)
               System.out.print(temp.ID+""+temp.f);
               else
               System.out.print(temp.ID+""+temp.f);
               localStack.push(temp.Lchild);
               localStack.push(temp.Rchild);
               if(temp.Lchild != null ||
                                   temp.Rchild != null)
                  isRowEmpty = false;
               
               }
            else
               {
               System.out.print("--");
               localStack.push(null); 
               localStack.push(null); 
               }
            for(int j=0; j<nBlanks*2-2; j++)
               System.out.print(' ');
            }  // end while globalStack not empty
         System.out.println();
         nBlanks /= 2;
         while(localStack.isEmpty()==false)
            globalStack.push( localStack.pop() );
         }  // end while isRowEmpty is false
      System.out.println(
      "......................................................");
      }  // end displayTree()

public void postOrder(Node localRoot){
if(localRoot!=null){
postOrder(localRoot.Lchild);
postOrder(localRoot.Rchild);
if (localRoot.hasChildren()==true)
System.out.print(localRoot.f+" ");
else
System.out.print(localRoot.ID+" ");
}
}

public String path(Node dest, String str){
if(dest.Parent!=null){
if(dest.L==true)
str=("0"+str);        //If this is a left child, it prints a zero. 
else
str=("1"+str);
return path(dest.Parent, str);
}
else return str;
}

public void translate(Node node, int i){

if(node.hasChildren()==false){  //If the node has no children it is a leaf.
System.out.print(node.ID);
node=R;
}
if((i+1)<code.toCharArray().length){
char bit=code.charAt(i);
if(bit=='0'){                        
translate(node.Lchild, i+1); } //0 means go left
else if(bit=='1'){
translate(node.Rchild, i+1);   //1 means go right
}
else
translate(node, i+1);          //nothing means stay, and keep scanning the code.
}
else 
return;
}





}

class Node{  
char ID;  
Node Parent,Sibling, Lchild,Rchild;    
boolean   L; 
int f=0 ;
String code;

Node(){ //Root Constructor 
ID='-';
if((Lchild!=null)&&(Rchild!=null))
f=(Lchild.f+Rchild.f);   
}
Node(char ch){ //Leaf constructor
ID=ch;
}




public void setf(){
if((Lchild!=null)&&(Rchild!=null))
f=(Lchild.f+Rchild.f);
}

public boolean hasChildren(){
return((Lchild!=null)&&(Rchild!=null));
}

public void setR(Node child){
Rchild=child;
child.L=false;
child.Parent=this;
}

public void setL(Node child){
Lchild = child;
child.L=true;
child.Parent=this;
}

public String path(Node dest, String str){
if (dest.Parent!=null){
if(dest.L==true) 
str=("0"+str);
else
str=("1"+str);
return path(dest.Parent, str);
}
else 
return str;
}



}

class PriorityQ
   {
   // array in sorted order, from max at 0 to min at size-1
   private int maxSize;
   private Tree[] queArray;
   public int nItems;
//-------------------------------------------------------------
   public PriorityQ(int s)          // constructor
      {
      maxSize = s;
      queArray = new Tree[maxSize];
      nItems = 0;
      }
//-------------------------------------------------------------
   public void ins(Tree item)    // insert item
      {
      int j;

      if(nItems==0)                         // if no items,
         queArray[nItems++] = item;         // insert at 0
      else                                // if items,
         {
         for(j=nItems-1; j>=0; j--)         // start at end,
            {
            if( item.R.f > queArray[j].R.f )      // if new item larger,
               queArray[j+1] = queArray[j]; // shift upward
            else                          // if smaller,
               break;                     // done shifting
            }  // end for
         queArray[j+1] = item;            // insert it
         nItems++;
         }  // end else (nItems > 0)
      }  // end insert()
//-------------------------------------------------------------
   public Tree del()             // remove minimum item
      { return queArray[--nItems]; }
//-------------------------------------------------------------
   public Tree peekMin(){ 
       return queArray[nItems-1]; 
}   //index -1*******
//-------------------------------------------------------------
   public boolean isEmpty()         // true if queue is empty
      { return (nItems==0); }
//-------------------------------------------------------------
   public boolean isFull()          // true if queue is full
      { return (nItems == maxSize); }
//-------------------------------------------------------------
   }

class Stack
   {
   private int maxSize=0;        // size of stack array
   private Node[] stackArray;
   private int top;            // top of stack
//--------------------------------------------------------------
   public Stack(int s)         // constructor
      {
      maxSize = s;             // set array size
      stackArray = new Node[maxSize];  // create array
      top = -1;                // no items yet
      }

   public Stack(){
   top=-1;
   maxSize=0;
}
//--------------------------------------------------------------
   public void push(Node j)    // put item on top of stack
      {
      stackArray[++top] = j;    
            }
//--------------------------------------------------------------
   public Node pop()           // take item from top of stack
      {
      return stackArray[top--];  // access item, decrement top
      }
//--------------------------------------------------------------
   public Node peek()          // peek at top of stack
      {
      return stackArray[top];
      }
//--------------------------------------------------------------
   public boolean isEmpty()    // true if stack is empty
      {
      return (top == -1);
      }
//--------------------------------------------------------------
   public boolean isFull()     // true if stack is full
      {
      return (top == maxSize-1);
      }

}

class Queue
   {
   private int maxSize;
   private String[] queArray;
   private int front;
   private int rear;
   public int nItems;
//--------------------------------------------------------------
   public Queue(int s)          // constructor
      {
      maxSize = s;
      queArray = new String[maxSize];
      front = 0;
      rear = -1;
      nItems = 0;
      }
//--------------------------------------------------------------
   public void ins(String j)   // put item at rear of queue
      {
      if(rear == maxSize-1)         // deal with wraparound
         rear = -1;
      queArray[++rear] = j;         // increment rear and insert
      nItems++;                     // one more item
      }
//--------------------------------------------------------------
   public String del()         // take item from front of queue
      {
      String temp = queArray[front++]; // get value and incr front
      if(front == maxSize)           // deal with wraparound
         front = 0;
      nItems--;                      // one less item
      return temp;
      }
//--------------------------------------------------------------
   public String peekFront()      // peek at front of queue
      {
      return queArray[front];
      }
//--------------------------------------------------------------
   public boolean isEmpty()    // true if queue is empty
      {
      return (nItems==0);
      }
//--------------------------------------------------------------
   public boolean isFull()     // true if queue is full
      {
      return (nItems==maxSize);
      }
//--------------------------------------------------------------
   public int size()           // number of items in queue
      {
      return nItems;
      }
//--------------------------------------------------------------
   public String peekRear(){
   return queArray[rear];
      }
   }