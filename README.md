# tictactoe3d
The same as the tictactoe, but a generalized version. This one instead of a 4x4 board, it plays in a 3D hypercube (A.I. course KTH project).
This project is also written in Java instead of C++.

# 3D Tic-Tac-Toe

In this assignment we consider a special case of 3-dimensional generalization of Tic-Tac-Toe game. The rules are simple. There is a 3-dimensional hypercube H, consisting of 43 cells. Two players, X and Y, take turns marking blank cells in H. The first player to mark 4 cells along a row wins. Here by a row we mean any 4 cells, whose centres lie along a straight line in H. Winning rows lie along the 48 orthogonal rows (those which are parallel to one of the edges of the cube), the 24 diagonal rows, or the 4 main diagonals of the cube, making 76 winning rows in total. Player X always starts the game.

Our goal is to find the best possible move for player X given a particular state of the game.

# Input

You will be given a game state which consists of a board, whose turn it is, and what the last move was.

#Output

Your agent program should output the next game state in the same format. This is taken care of for you by the skeleton.

# To play in a single terminal

Assume that “agentX” is your C++ agent, which is in the current directory together with another agent called “agentY”. First, you have to set up a pipe for them to communicate with:

mkfifo pipe

which you do once only.

Now you can make your agents play with

./agentX init verbose < pipe | ./agentY > pipe

The agent that is started with "init" will play as white (exactly one of them has to issue "init").

If you want to test a Java client with one of these you would do

java Main init verbose < pipe | ./agentX > pipe

# Testing in two terminals

To play in two different terminals (so that we can tell who does what in an easier way) you would have to create two pipes with

mkfifo pipe1 pipe2

which you do only once.

In Terminal 1:

./agentX init verbose < pipe1 > pipe2

In Terminal 2:

java Main verbose > pipe1 < pipe2

If you run in two different terminals or directories, make sure that you use the same named pipes for both agents. That is, if agent1 writes to pipe1 (">") then agent2 must read ("<") from the same file. You can specify the full path of the pipe to make sure that you use the same pipes.

Note that if you work on for example computers that use the AFS filesystem, you cannot create named pipes like above. However, you can create your named pipes in the /tmp directory.

mkfifo /tmp/mypipe

and then replace pipe above with /tmp/mypipe

# Windows users

If you want to run your code in Windows with the above technique, you can use Cygwin.

C++

If you want to be able to compile your C++ code in cygwin you should obtain the gcc-g++ package. You can find it easy with the search function on the screen that lets you select additional packages during the installation.

Java

Install the JDK on your Windows machine. In cygwin you need add the path to the java executables with something like

export PATH=\$PATH:/cygdrive/c/Program Files/Java/jdk1.7.0\_80/bin/

# Advanced use

You can run your agent directly in the terminal without any pipe, for example, for debugging/testing purposes. Start the agent

./agent verbose

and then paste in a board state message in the terminal and press ENTER. When you run an agent in verbose mode you see the board state message together with the graphical representation. This what you should pass into your agent. You can also do this by putting such a message into a file (make sure to have an end of line) and do

java Main verbose < file.txt

The start state is represented by the following string:

 x...o.oxx.....ox...oxoo.o..x.xxo......x.....x...o...xx.ooo.x.oo. 0_57_2 o

where the first 64 characters describe the board state, 0 says that the state is not final, 57 says that the previous player marked the cell 57, 2 says that it was the second player, and o means that his mark is "o".
