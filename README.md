Soot is a Java optimization framework used primarily for analyzing and transforming Java and Android applications. It is an open-source project that provides a flexible infrastructure for developing analyses and transformations of Java bytecode and related languages.

Soot features several intermediate representations (IRs) that facilitate various analysis and transformation tasks:

1. Jimple: A simplified version of Java bytecode that is easier to analyze and transform. It serves as the primary IR for many analyses and optimizations in Soot.
2. Shimple: An SSA (Static Single Assignment) based version of Jimple, which is useful for certain types of data flow analyses.
3. Baf: A streamlined representation of bytecode that abstracts away some of the complexities of the JVM's instruction set.
4. Grimp: An aggregated form of Jimple, providing a more concise representation that is closer to the source code.

Project description: 

The project implements an intra-procedural transform that iterates over the set of all Jimple statements and print out those that read and write heap locations.

Each Java method in soot is represented by a SootMethod object. From this object, we retrieve the body of the method by calling getActiveBody(). This will give us a Body object, through which we have access to all information of the Java method, such as local variables and statements. 

We can then use method getUnits() to get a chain of statements the method contains. Write a while loop that iterates over the statements in the chain. In Jimple, each statement is an object of interface Stmt, which has many different implementing classes. 

Among these Stmt classes, we are interested in JAssignStmt, which represents assignment statements (e.g., anything of the form a = b). 

In order to identify statements that access the heap, we need to understand what the left-hand-side (LHS) and RHS operands are for each statement. 

To do this, we can call methods getLeftOp() and getRightOp() on each statement object. These calls return objects of type Value, which is an interface representing all expressions, locals, constants, and references in a Java program. 

We are particularly interested in InstanceFieldRef, StaticFieldRef, and ArrayRef, each of which represents a type of heap access.

If the LHS/RHS of an assignment is an InstanceFieldRef, the assignment is of the form:
 a.f = b(heap write) 
 b = a.f (heap read);

If the LHS/RHS of an assignment is a StaticFieldRef, the assignment is of the form:
 A.f = b (heap write)
 b=A.f (heap read);

If the LHS/RHS of an assignment is an ArrayRef, the assignment is of the form: 
 a[i] = b (heap write)
 b=a[i] (heap read); 

The output of the analysis should have the following format:

Statement a.f = b, heap write;

Statement b = a[i], heap read;...

I have setup Soot on macOS, the process will be similar for Windows too, just download the Windows version instead of the mac.

**Setting Up Soot on macOS**

1. Install Java JDK: Ensure you have Java JDK installed on your macOS. Soot is a Java-based framework, so Java is a prerequisite. You can check your Java version by running java -version in the terminal. If you need to install Java, you can download it from the Oracle website or use a package manager like Homebrew with brew install openjdk.

2.  Setup VSCode for Java
Install VSCode: If you haven't already, download and install Visual Studio Code from the official site.
Install Java Extension Pack: Open VSCode, go to the Extensions view by clicking on the square icon on the sidebar, or pressing Cmd+Shift+X (on macOS) or Ctrl+Shift+X (on Windows/Linux). Search for “Java Extension Pack” by Microsoft and install it. This pack includes essential Java development tools for VSCode.

3. Download Soot: You can download the Soot framework from its official GitHub repository. Clone the repository or download the latest release as a jar file.

**Implementing Intra-procedural Analysis**
1. Create a New Java Project: Start a new Java project in your IDE or a directory of your choice if using a text editor and terminal.

2. Add Soot Library: Make sure the Soot jar file is added to your project's libraries in the IDE or included in the classpath.

3. Analysis Implementation:

Main Class Setup: Create a main class where you will set up Soot and define your analysis.
Soot Setup: In the main method, configure Soot to load and analyze the target classes. Use soot.Main.main(args); or the Soot API to programmatically set options.
Intra-procedural Analysis: Implement the analysis as described in your requirements. This involves creating a new class that extends BodyTransformer and overriding the internalTransform method.
