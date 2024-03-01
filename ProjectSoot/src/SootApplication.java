import java.util.Iterator;
import java.util.Map;
import soot.*;
import soot.jimple.*;

public class SootApplication {
    public static void main(String[] args) {
        PackManager.v().getPack("jtp").add(new Transform("jtp.detailedHeapAnalysis", new BodyTransformer() {
            @Override
            protected void internalTransform(Body body, String phaseName, Map<String, String> options) {
                for (Iterator<Unit> units = body.getUnits().iterator(); units.hasNext(); ) {
                    Unit u = units.next();
                    if (u instanceof AssignStmt) {
                        AssignStmt stmt = (AssignStmt) u;
                        Value lhs = stmt.getLeftOp();
                        Value rhs = stmt.getRightOp();

                        // Instance field write
                        if (lhs instanceof InstanceFieldRef) {
                            System.out.println("Statement " + lhs + " = " + rhs + ", heap write;");
                        }
                        // Instance field read
                        if (rhs instanceof InstanceFieldRef) {
                            System.out.println("Statement " + lhs + " = " + rhs + ", heap read;");
                        }

                        // Static field write
                        if (lhs instanceof StaticFieldRef) {
                            System.out.println("Statement " + lhs + " = " + rhs + ", heap write;");
                        }
                        // Static field read
                        if (rhs instanceof StaticFieldRef) {
                            System.out.println("Statement " + lhs + " = " + rhs + ", heap read;");
                        }

                        // Array write
                        if (lhs instanceof ArrayRef) {
                            System.out.println("Statement " + lhs + " = " + rhs + ", heap write;");
                        }
                        // Array read
                        if (rhs instanceof ArrayRef) {
                            System.out.println("Statement " + lhs + " = " + rhs + ", heap read;");
                        }
                    }
                }
            }
        }));

        String classPath = "/Users/nityanandmadpathi/Documents/Spring2024/SAM/Java_SootProject/ProjectSoot/bin/"; 
        String targetClass = "SelectionSort"; 
        String[] sootArgs = {
            "-cp", classPath, 
            "-pp", 
            "-allow-phantom-refs", 
            targetClass
        };
        soot.Main.main(sootArgs);
    }
}
