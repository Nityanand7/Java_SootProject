import java.util.Iterator; import java.util.Map;
import soot.*;
import soot.jimple.AssignStmt; import soot.jimple.InstanceFieldRef; import soot.jimple.StaticFieldRef; import soot.jimple.Stmt;
import soot.jimple.ArrayRef;
public class MainCode {
public static void main(String[] args) {
 // Set the necessary Soot options and load the desired class
PackManager.v().getPack("jtp").add(new Transform("jtp.myTransform", new BodyTransformer() {
@Override
protected void internalTransform(Body b, String phaseName, Map<String, String> options) {
Iterator<Unit> it = b.getUnits().snapshotIterator(); while(it.hasNext()){
Unit gtSt = it.next();
if(gtSt instanceof AssignStmt){
AssignStmt js = (AssignStmt)gtSt;
Value lhs = js.getLeftOp();
Value rhs = js.getRightOp();
if(lhs instanceof ArrayRef){
System.out.println("Statement " +lhs.toString() + " = " + rhs.toString() + ",\t\t\t heap write");
}
if(rhs instanceof ArrayRef){
System.out.println("Statement " + lhs.toString() + " = " + rhs.toString() + ",\t\t\t heap read");
}
if(lhs instanceof InstanceFieldRef){
System.out.println("Statement " +lhs.toString() + " = " + rhs.toString() + ",\t\t heap write");
}
if(rhs instanceof InstanceFieldRef){
System.out.println("Statement " +lhs.toString() + " = " + rhs.toString() + ",\t\t heap read");
}
if(lhs instanceof StaticFieldRef){
    System.out.println("Statement " +lhs.toString() + " = " + rhs.toString() + ",\t\t\t heap write");
}
if(rhs instanceof StaticFieldRef){
System.out.println("Statement " +lhs.toString() + " = " + rhs.toString() + ",\t heap read");
}
}
}
} }));
// Run Soot analysis on the desired class
String[] sootArgs = {
// "-cp", "/Users/manohar/Downloads/SAM_Project/SAM/bin/", "-pp",
"-cp", "/Users/nityanandmadpathi/Documents/Spring2024/SAM/Java_SootProject/ProjectSoot/bin/", "-pp",
"-allow-phantom-refs",
"BinarySearch"
};
soot.Main.main(sootArgs);
}
}