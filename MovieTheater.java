import java.io.*;
import java.util.*;

public class MovieTheater {
    private final int[] seats;
    int rows, cols;

    public MovieTheater(){
        this(10, 20);
    }

    public MovieTheater(int rows, int cols){
        this.rows = rows;
        this.cols = cols;
        this.seats = new int[rows*cols];
    }

    public void printTheater(){
        for (int i = 0; i < 10; i++) {
            String row = "";
            for (int j = 0; j < 20; j++) {
                row += (seats[j+i*20] != 0 ? 's' : '#');
            }
            System.out.printf("%c%25s%n", 'A'+i, row);
        }
    }

    private boolean insert(int row, int col, int r){
        if(seats[col+row*20] != 0) return false; // already full
        //cols
        for (int i = -3; i <= 3; i++) {
            if(i == 0) continue;
            if(col+i >= 0 && col+i < cols)
                if(seats[col+i+row*20] != 0 && seats[col+i+row*20] != r) return false;
        }

        // rows
        if(row - 1 >= 0)
            if(seats[col+(row-1)*20] != 0 && seats[col+(row-1)*20] != r) return false;
        if(row + 1 < rows)
            if(seats[col+(row+1)*20] != 0 && seats[col+(row+1)*20] != r) return false;

        seats[col+row*20] = r;
        return true;
    }

    public boolean request(int r, int k){
        for (int i = 0; i < 10*20; i++) {
            if(seats[i] == 0){
                if(insert(i/20, i%20, r)) k--;
            }
            if(k == 0) return true;
        }
        for (int i = 0; i < 10*20; i++) { // if not all seats can be filled
            if(seats[i] == r) seats[i] = 0;
        }
        return false;
    }

    public int getSeat(String seat){
        int row = seat.charAt(0) - 'A';
        int col = Integer.parseInt(seat.substring(1)) - 1;
        return seats[col+row*cols];
    }

    public static void main(String[] args) {
        String inName = "input.txt";
        String outName = "output.txt";
        if(args.length > 0){
            for (int i = 0; i < args.length; i++) {
                if(args[i] == "--i") inName = args[i+1];
                if(args[i] == "--o") outName = args[i+1];
            }
        }
        MovieTheater test = new MovieTheater();
        Map<String, Integer> requests = new TreeMap<>();
        try {
            BufferedReader in = new BufferedReader(new FileReader(inName));
            String line;
            while((line = in.readLine()) != null){
                String[] argv = line.split("\\W+", 2);
                int r = Integer.parseInt(argv[0].substring(1));
                int k = Integer.parseInt(argv[1]);
                requests.put(argv[0], k); // keep track of reservations
                test.request(r, k);
            }
            in.close();
            Formatter out = new Formatter(outName);
            for(Map.Entry<String, Integer> e : requests.entrySet()){
                List<String> seatList = new ArrayList<>();
                int r = Integer.parseInt(e.getKey().substring(1));
                for (char i = 'A'; i < 'A'+test.rows; i++) {
                    for (int j = 1; j <= test.cols; j++) {
                        if(test.getSeat(Character.toString(i) + j) == r) seatList.add(Character.toString(i) + j);
                    }
                }
                out.format("%5s %s%n", e.getKey(), seatList.toString().replaceAll("[\\[\\] ]", ""));
            }
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //test.printTheater();
    }
}
