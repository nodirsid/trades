import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<Trade> trades = new ArrayList<Trade>();
        int option;

        while(true){
            System.out.println("Select the command:");
            System.out.println("1-Add trade\n2-Delete trade\n3-Totals by instrument\n4-Totals by User\n5-Print\n6-Exit");
            option=sc.nextInt();

            switch (option){
                case 1:
                    trades = addTrade(sc);
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

                case 6:
                    System.exit(0);
                    break;
                default:
                    System.out.print("Please enter a valid option");
                    break;
            }
        }



    }

    private static void totalsByInstrument(ArrayList<Trade> trades){
        Map<String, Integer> ttlByInst=new HashMap<>();
        for(Trade tr: trades){
            int qty=ttlByInst.get(tr.getInstrument());
            qty+=tr.getQuantity();
            ttlByInst.put(tr.getInstrument(),qty);
        }

        //output
        for (Map.Entry<String, Integer> entry : ttlByInst.entrySet()) {
            System.out.println(entry.getKey() + " "+entry.getValue());
        }
    }

    private static void totalsByUser(ArrayList<Trade> trades){
        Map<String, Double> ttlByUser=new HashMap<>();
        for(Trade tr: trades){
            Double ttlConsideration=ttlByUser.get(tr.getUserID());
            ttlConsideration+=tr.getQuantity() * tr.getPrice();
            ttlByUser.put(tr.getInstrument(),ttlConsideration);
        }

        //output
        for (Map.Entry<String, Double> entry : ttlByUser.entrySet()) {
            System.out.println(entry.getKey() + " "+entry.getValue());
        }
    }


    private static void deleteTrade(Scanner sc, ArrayList<Trade> trades){
        System.out.println("Please enter the trade id to delete:");
        int tradeId = sc.nextInt();
        Trade trade=null;
        for(Trade tr: trades){
            if(tr.getTradeID()==tradeId)
                trade=tr;
        }

        trades.remove(trade);
    }

    private static ArrayList<Trade> addTrade(Scanner sc){

        System.out.println("Please enter the file name:");
        String csvFile=sc.nextLine();
        BufferedReader br=null;
        String line=null;
        ArrayList<Trade> trades=new ArrayList<Trade>();

        try{
            br=new BufferedReader(new FileReader(csvFile));

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

        }catch (FileNotFoundException e){
            e.printStackTrace();
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
