import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Collections;
import java.util.InputMismatchException;

//this program reads a list of trades from a CSV file
//and provides basic analytics on the trades
public class Main {

    public static void main(String[] args) {

        //scanner to read user input from command line
        Scanner sc = new Scanner(System.in);

        //array of trade objects
        ArrayList<Trade> trades = new ArrayList<Trade>();
        int option=-1;

        while(true){
            System.out.println("Select the command:\n" +
                    "1-Add trade\n2-Delete trade\n3-Totals by instrument\n" +
                    "4-Totals by User\n5-Print\n0-Exit");
            try{
                option=sc.nextInt(); //read input from scanner
            }catch (InputMismatchException ex){
                System.out.println("Error: Please enter a valid option");
                sc.nextLine();
                continue;
            }


            switch (option){
                case 0:
                    System.exit(0); //exit the system
                    break;
                case 1:
                    trades = addTrade(sc); //specify a file and load the trades into memory
                    System.out.println("All trades were successfully loaded into memory");
                    break;
                case 2:
                    deleteTrade(sc,trades); //delete a trade from the in-memory list
                    break;
                case 3:
                    totalsByInstrument(trades); //output the total quantity that has been traded for each instrument
                    break;
                case 4:
                    totalsByUser(trades); //output the total consideration (quantity x by price) for each user
                    break;
                case 5:
                    printTradesInCSV(sc, trades); //output the trades in CSV format
                    break;
                default:
                    System.out.println("Error: Please enter a valid option");
                    break;
            }
        }
    }

    //print the trades in CSV format
    //input: scanner to read input from command line, list of trades
    private static void printTradesInCSV(Scanner sc, ArrayList<Trade> trades){
        if(trades.size() !=0) {

            System.out.println("Please choose sorting order:" +
                    "\n0-default\n1-order by trade id\n2-order by user id" +
                    "\n3-order by quantity\n4-order by price");
            sc.nextLine();
            int sortingType = -1;

            try {
                sortingType = sc.nextInt();

                switch (sortingType) {
                    case 0: //default order
                        System.out.println("Trades in CSV format\nDefault order");
                        for (Trade tr : trades) {
                            System.out.println(tr);
                        }
                        break;
                    case 1: //order by trade id
                        System.out.println("Trades in CSV format\nOrdered by trade id");
                        Collections.sort(trades, Trade.tradeIdComparator);
                        for (Trade tr : trades) {
                            System.out.println(tr);
                        }
                        break;
                    case 2:
                        System.out.println("Trades in CSV format\nOrdered by user id");
                        Collections.sort(trades, Trade.userIdComparator);
                        for (Trade tr : trades) {
                            System.out.println(tr);
                        }
                        break;
                    case 3:
                        System.out.println("Trades in CSV format\nOrdered by quantity");
                        Collections.sort(trades, Trade.qtyComparator);
                        for (Trade tr : trades) {
                            System.out.println(tr);
                        }
                        break;
                    case 4:
                        System.out.println("Trades in CSV format\nOrdered by price");
                        Collections.sort(trades, Trade.priceComparator);
                        for (Trade tr : trades) {
                            System.out.println(tr);
                        }
                        break;
                    default:
                        System.out.println("Error: Please enter a valid option for sorting");
                        break;
                }

            } catch (InputMismatchException ex) {
                System.out.println("Error: Sorting option can only be numeric");
                sc.nextLine();
            }
        }else{
            System.out.println("Error: Nothing to print. Memory contains to trades");
        }
    }

    //output the total quantity that has been traded for each instrument
    //input: list of trades
    private static void totalsByInstrument(ArrayList<Trade> trades){
        Map<String, Integer> ttlByInst=new HashMap<>();
        for(Trade tr: trades){
            if(ttlByInst.containsKey(tr.getInstrument())){
                Integer qty=ttlByInst.get(tr.getInstrument());
                qty+=tr.getQuantity();
                ttlByInst.put(tr.getInstrument(),qty);
            }else{
                Integer qty=tr.getQuantity();
                ttlByInst.put(tr.getInstrument(),qty);
            }
        }

        //output
        System.out.println("Totals by instrument");
        for (Map.Entry<String, Integer> entry : ttlByInst.entrySet()) {
            System.out.println(entry.getKey() + " "+entry.getValue());
        }
    }


    //output the total consideration (quantity x by price) for each user
    //input: list of trades
    private static void totalsByUser(ArrayList<Trade> trades){
        Map<Integer, Double> ttlByUser=new HashMap<>();
        for(Trade tr: trades){
            if(ttlByUser.containsKey(tr.getUserID())){
                Double ttlConsideration=ttlByUser.get(tr.getUserID());
                ttlConsideration+=tr.getQuantity() * tr.getPrice();
                ttlByUser.put(tr.getUserID(),ttlConsideration);
            }else{
                Double ttlConsideration=tr.getQuantity() * tr.getPrice();
                ttlByUser.put(tr.getUserID(),ttlConsideration);
            }
        }

        //output
        System.out.println("Totals by user");
        for (Map.Entry<Integer, Double> entry : ttlByUser.entrySet()) {
            System.out.println(entry.getKey() + " "+entry.getValue());
        }
    }


    //delete a trade from the in-memory list
    //input: scanner to read input from command line, list of trades
    private static void deleteTrade(Scanner sc, ArrayList<Trade> trades){

        if(trades.size()!=0) {

            System.out.println("Please enter the trade id to delete:");
            sc.nextLine();
            int tradeId = 0;
            try {
                tradeId = sc.nextInt();

                Trade trade = null;

                //iterate through trades
                for (Trade tr : trades) {
                    if (tr.getTradeID() == tradeId) {
                        trade = tr;
                        break;
                    }
                }

                if (trade != null) {
                    trades.remove(trade);
                    System.out.println("Trade with id " + tradeId + " was removed from memory");
                } else {
                    System.out.println("Error: Trade with id " + tradeId + " cannot be found in memory");
                }
            } catch (InputMismatchException ex) {
                System.out.println("Error: Trade id can only be numeric");
                sc.nextLine();
            }
        }else{
            System.out.println("Error: Nothing to delete. Memory contains no trades");
        }
    }


    //specify a file and load the trades into memory
    //input: scanner to read input from command line
    //output: list of trades loaded into memory
    private static ArrayList<Trade> addTrade(Scanner sc){

        System.out.println("Please enter full path to the file:");
        sc.nextLine();
        String csvFile=sc.nextLine();
        BufferedReader br=null;
        String line=null;
        ArrayList<Trade> trades=new ArrayList<Trade>();

        try{
            while(br==null){

                try{
                    br=new BufferedReader(new FileReader(csvFile));
                }catch (FileNotFoundException ex){
                    System.out.println("Please enter full path to the file:");
                    csvFile=sc.nextLine();
                    continue;
                }

            }


            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] rawTrades = line.split(",");
                Trade trade=new Trade(
                        Integer.parseInt(rawTrades[0]),
                        Integer.parseInt(rawTrades[1]),
                        rawTrades[2],
                        Integer.parseInt(rawTrades[3]),
                        rawTrades[4],
                        Double.parseDouble(rawTrades[5]));

                trades.add(trade);
            }

        }catch (IOException e){
            e.printStackTrace();
        }

        return trades;
    }
}
