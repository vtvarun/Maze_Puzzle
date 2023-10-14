import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;

class Pair{
    int row ;
    int col;
    Pair(int row, int col){
        this.row = row;
        this.col = col;
    }
}

public class Main {
    public static void main(String[] args) {
        MyFrame mainFrame = new MyFrame();
    }
}

// creating frame
class MyFrame extends JFrame{

    MyFrame(){

        // this gives title to the frame
        this.setTitle("Maze Solver");

        // this adds panel on the frame
        this.setLayout(new GridBagLayout());
        this.add(new Panel(),new GridBagConstraints());

        // this makes the size of frame equals to the size of panel
        this.pack();

        // this closed the entire operation
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // this makes the frame visible
        this.setVisible(true);

        // this puts frame in the middle of the computer screen
        this.setLocationRelativeTo(null);

    }
}

// creating panel, because we cannot draw something on frame directly and we need a panel to draw something
// on it
class Panel extends  JPanel{


    int maze[][] = new int[][]{{1,1,1,1,1,1,1,1,1,1},
            {1,0,0,0,0,0,0,0,0,1},
            {1,1,0,1,0,1,0,1,0,1},
            {1,0,0,0,1,0,0,0,0,1},
            {1,0,1,0,0,0,1,0,0,1},
            {1,0,1,0,1,0,0,0,1,1},
            {1,0,0,1,0,0,1,0,1,1},
            {1,0,0,1,1,1,1,0,1,1},
            {1,0,0,0,1,0,1,0,0,1},
            {1,1,1,1,1,1,1,1,1,1}};

    ArrayList<Pair> shortestPath = new ArrayList<>();

    Panel(){
        this.setPreferredSize(new Dimension(700,700));
        this.setBackground(Color.black);
    }

    @Override
    public void paint(Graphics g){
        super.paint(g);
        g.translate(100,100);
        paintPaths(g);
    }

    public void paintPaths(Graphics g){
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze.length; j++) {
                Color color;
                if(maze[i][j] == 0) color = Color.white;
                else if(maze[i][j] == 2) color = Color.red;
                else color = Color.black;
                g.setColor(color);
                g.fillRect(50*i,50*j,50,50);

                g.setColor(Color.red);
                g.drawRect(50*i,50*j,50,50);
            }
        }

        Font currentFont = g.getFont();
        g.setFont(currentFont.deriveFont(24.0f));

        // marking the start cell
        int startRow = 1;
        int startCol = 2;
        g.setColor(Color.black);
        g.drawChars(new char[]{' ','S'},0,2,50*startRow,50*startCol);


        // marking the end cell
        int endRow = 8;
        int endCol = 9;
        g.setColor(Color.black);
        g.drawChars(new char[]{' ','E'},0,2,50*endRow,50*endCol);

        // finding the shortestPAth
        shortestPath = shortestPathFunction(maze);
        g.setColor(Color.blue);
        for(int i=0; i<shortestPath.size() ; i++){
            Pair curr = shortestPath.get(i);
            int row = curr.row;
            int col = curr.col;
            g.fillRect(50*row,50*col,30,30);
        }
    }

    public static ArrayList<Pair> shortestPathFunction(int[][] grid){
        //finding the shortest path and printing it
        java.util.Queue<java.util.List<Pair>> que = new LinkedList<>();
        java.util.List<Pair> al = new ArrayList<>();
        al.add(new Pair(1,1));
        que.offer(al);
        grid[1][1] = 1;

        while(!que.isEmpty()){

            int size = que.size();

            for(int i=0; i < size ; i++){

                // pop the first element
                java.util.List<Pair> curr = que.poll();

                Pair top = curr.get(curr.size()-1);

                int row = top.row;
                int col = top.col;

                if(row == grid.length-2  && col == grid.length-2){
                    return new ArrayList<>(curr);
                }

                // UP
                if (row - 1 >= 0 && grid[row - 1][col] == 0) {
                    System.out.println("UP");
                    grid[row - 1][col] = 1;
                    java.util.List<Pair> newPath = new ArrayList<>(curr); // Create a new copy of the path
                    newPath.add(new Pair(row - 1, col));
                    que.offer(newPath);
                }

                // Down
                if (row + 1 < grid.length && grid[row + 1][col] == 0) {
                    grid[row + 1][col] = 1;
                    System.out.println("Down");
                    java.util.List<Pair> newPath = new ArrayList<>(curr); // Create a new copy of the path
                    newPath.add(new Pair(row + 1, col));
                    que.offer(newPath);
                }

                // Left
                if (col - 1 >= 0 && grid[row][col - 1] == 0) {
                    grid[row][col - 1] = 1;
                    System.out.println("Left");
                    java.util.List<Pair> newPath = new ArrayList<>(curr); // Create a new copy of the path
                    newPath.add(new Pair(row, col - 1));
                    que.offer(newPath);
                }

                // Right
                if (col + 1 < grid.length && grid[row][col + 1] == 0) {
                    grid[row][col + 1] = 1;
                    System.out.println("right");
                    java.util.List<Pair> newPath = new ArrayList<>(curr); // Create a new copy of the path
                    newPath.add(new Pair(row, col + 1));
                    que.offer(newPath);
                }


            }
        }

        ArrayList<Pair> temp = new ArrayList<>();
        temp.add(new Pair(-1,-1));
        return temp;

    }
}