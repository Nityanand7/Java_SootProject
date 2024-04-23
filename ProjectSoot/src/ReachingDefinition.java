import soot.*;
import soot.jimple.internal.JAssignStmt;

import java.util.Iterator;
import java.util.Map;

public class ReachingDefinition {
    public static void main(String[] args) {
        System.out.println("Soot started on " + new java.util.Date());

        // Set up Soot
        PackManager.v().getPack("jtp").add(new Transform("jtp.myTransform", new BodyTransformer() {
            @Override
            protected void internalTransform(Body body, String phaseName, Map<String, String> options) {
                Output output = new Output();
                Iterator<Unit> units = body.getUnits().snapshotIterator();
                while (units.hasNext()) {
                    Unit unit = units.next();
                    if (unit instanceof JAssignStmt) {
                        JAssignStmt assignStmt = (JAssignStmt) unit;
                        String entry = "[Entry] " + output.toString();
                        output.put(assignStmt.getLeftOp().toString(), assignStmt.getRightOp().toString());
                        String exit = "[Exit] " + output.toString();
                        System.out.println(unit + " " + entry + " " + exit);
                    }
                }
                System.out.println("Method analyzed: " + body.getMethod());
            }
        }));

        String classPath = "/Users/nityanandmadpathi/Documents/Spring2024/SAM/Java_SootProject/ProjectSoot/bin/";
        String[] sootArgs = {
                "-cp", classPath, // Classpath
                "-pp", // Prepend classpath
                "-process-dir", classPath, // Process directory
                "-allow-phantom-refs", // Allow phantom refs
                "SelectionSort" // Main class to analyze
        };

        soot.Main.main(sootArgs);

        System.out.println("Soot ended on " + new java.util.Date());
    }
}
