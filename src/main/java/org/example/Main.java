package org.example;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    static String[] products = {"Бумага", "Сок", "Суши"};
    static int[] prices = {218, 25, 179};
    static int[] amount = new int[prices.length];
    static long[] sum = new long[prices.length];
    static File saveFile = new File("basket.json");
    static File clientLog = new File("client.csv");

    public static void main(String[] args) throws Exception{
        XML config = new XML(new File("shop.xml"));
        File loadFile = new File(config.loadFile);
        File saveFile = new File(config.loadFile);
        File logFile = new File(config.loadFile);

        Basket basket = newBasket (loadFile, config.loadFormat);

        Scanner scanner = new Scanner(System.in);

        ClientLog log = new ClientLog();
        while (true) {

            basket.range();
            System.out.println("Введите номер товара и количество через пробел или stop: ");
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("stop")) {
                log.exportAsCSV(clientLog);
                break;
            }

            String[] parts = input.split(" ");
            int productNumber = Integer.parseInt(parts[0]) - 1;
            int productCont = Integer.parseInt(parts[1]);
            basket.addToBasket(productNumber, productCont);
            if (config.log) {
                log.log(productNumber, productCont);
            }

            if (config.save) {
                switch (config.saveFormat) {
                    case "json" -> basket.saveJson(saveFile);
                    case "txt" -> Basket.saveTXT(saveFile);
                }
            }
        }
        basket.basketInfo();
    }

    private static Basket newBasket (File loadFile, String loadFormat) throws IOException {
        Basket basket;
        if (saveFile.exists()) {
            basket = switch (loadFormat) {
                case "json" -> Basket.loadJSON(loadFile);
                case "txt" -> Basket.loadTXT(loadFile);
                default -> new Basket(products, prices, amount, sum);
            };
        } else {
            basket = new Basket(products, prices, amount, sum);
        }
        return basket;
    }
}