import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<Trade> trades = new ArrayList<Trade>();
        int option;

        while(true){
            System.out.println("Select the command:");
            System.out.println("1-Add trade\n2-Delete trade\n3-Totals by instrument\n4-Totals by User\n5-Print\n6-Exit");
            try{
                option=sc.nextInt();
            }catch (InputMismatchException ex){
                System.out.println("Error: Please enter a valid option");
                sc.nextLine();
                continue;
            }


            switch (option){
                case 1:
                    trades = addTrade(sc);
                    System.out.println("All trades were successfully loaded into memory");
                    break;
                case 2:
                    deleteTrade(sc,trades);
                    break;
                case 3:
                    totalsByInstrument(trades);
                    break;
                case 4:
                    totalsByUser(trades);
                    break;
                case 5:
                    printTradesInCSV(sc, trades);
                    break;
                case 6:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Error: Please enter a valid option");
                    break;
            }
        }
    }

    private static void printTradesInCSV(Scanner sc, ArrayList<Trade> trades){
        System.out.println("Please choose sorting order:" +
                "\n0-default\n1-order by trade id\n2-order by user id" +
                "\n3-order by quantity\n4-order by price");
        sc.nextLine();
        int sortingType=0;

        try{
            sortingType=sc.nextInt();

            System.out.println("Trades in CSV format");
            switch (sortingType){
                case 0: //default order
                    System.out.println("Default order");
                    for(Trade tr: trades){
                        System.out.println(tr);
                    }
                    break;
                case 1: //order by trade id
                    System.out.println("Ordered by trade id");
                    Collections.sort(trades, Trade.tradeIdComparator);
                    for(Trade tr: trades){
                        System.out.println(tr);
                    }
                    break;
                case 2:
                    System.out.println("Ordered by user id");
                    Collections.sort(trades, Trade.userIdComparator);
                    for(Trade tr: trades){
                        System.out.println(tr);
                    }
                    break;
                case 3:
                    System.out.println("Ordered by quantity");
                    Collections.sort(trades, Trade.qtyComparator);
                    for(Trade tr: trades){
                        System.out.println(tr);
                    }
                    break;
                case 4:
                    System.out.println("Ordered by price");
                    Collections.sort(trades, Trade.priceComparator);
                    for(Trade tr: trades){
                        System.out.println(tr);
                    }
                    break;
                default:
                    System.out.println("Error: Please enter a valid option");
                    break;
            }

        }catch (InputMismatchException ex){
            System.out.println("Sorting option can only be numeric");
            sc.nextLine();
        }



    }

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

    private static void totalsByUser(ArrayList<Trade> trades){
        Map<String, Double> ttlByUser=new HashMap<>();
        for(Trade tr: trades){
            if(ttlByUser.containsKey(tr.getUserID())){
                Double ttlConsideration=ttlByUser.get(tr.getUserID());
                ttlConsideration+=tr.getQuantity() * tr.getPrice();
                ttlByUser.put(tr.getInstrument(),ttlConsideration);
            }else{
                Double ttlConsideration=tr.getQuantity() * tr.getPrice();
                ttlByUser.put(tr.getInstrument(),ttlConsideration);
            }
        }

        //output
        System.out.println("Totals by user");
        for (Map.Entry<String, Double> entry : ttlByUser.entrySet()) {
            System.out.println(entry.getKey() + " "+entry.getValue());
        }
    }


    private static void deleteTrade(Scanner sc, ArrayList<Trade> trades){
        System.out.println("Please enter the trade id to delete:");
        sc.nextLine();
        int tradeId=0;
        try{
            tradeId = sc.nextInt();

            Trade trade=null;

            //iterate through trades
            for(Trade tr: trades){
                if(tr.getTradeID()==tradeId) {
                    trade = tr;
                    break;
                }
            }

            if(trade != null) {
                trades.remove(trade);
                System.out.println("Trade with id " + tradeId + " was removed from memory");
            }else{
                System.out.println("Trade with id " + tradeId + " cannot be found in memory");
            }
        }catch (InputMismatchException ex){
            System.out.println("Trade id can only be numeric");
            sc.nextLine();
        }
    }

    private static ArrayList<Trade> addTrade(Scanner sc){

        System.out.println("Please enter the file name:");
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
                    System.out.println("Please enter the file name:");
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
        }finally {
            try {
                br.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }

        return trades;
    }
}
