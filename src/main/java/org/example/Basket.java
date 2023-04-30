package org.example;

import com.google.gson.Gson;

import java.io.*;
import java.util.Arrays;

public class Basket {
    private static String[] name;
    private static int[] prise;
    private static int[] amount;
    private static long[] sum;

    public Basket() {

    }

    public Basket(String[] products, int[] prices, int[] amount, long[] sum) {
        name = products;
        prise = prices;
        Basket.amount = amount;
        Basket.sum = sum;
    }

    public void addToBasket(int x, int productCont) {
        amount[x] += productCont;
        sum[x] += (long) prise[x] * productCont;
    }

    public void range() {
        for (int i = 0; i < name.length; i++) {
            int number = i + 1;
            System.out.println(number + ". " + name[i] + " по цене " + prise[i]);
        }
    }

    public void basketInfo() {
        System.out.println("Ваша корзина");
        for (int i = 0; i < name.length; i++) {
            System.out.println(name[i] + " " + amount[i] + " шт. по цене " + prise[i] + " за штуку на общую стоимость " + sum[i] + " руб.");
        }
    }

    public void saveJson(File textFile) {
        try (PrintWriter out = new PrintWriter(textFile)) {
            Gson json = new Gson();
            String json2 = json.toJson(this);
            out.print(json2);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void saveTXT(File textFile) throws FileNotFoundException {
        try (PrintWriter out = new PrintWriter(textFile)) {
            out.println(String.join( " ", name));

            out.println(String.join(" ", Arrays.stream(prise)
                    .mapToObj(String::valueOf)
                    .toArray(String []::new)));

            out.println(String.join(" ", Arrays.stream(amount)
                    .mapToObj(String::valueOf)
                    .toArray(String []::new)));

            out.println(String.join(" ", Arrays.stream(sum)
                    .mapToObj(String::valueOf)
                    .toArray(String []::new)));
        }
    }

    public static Basket loadJSON(File textFile) throws IOException {
        Basket basket = new Basket();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(textFile))) {
            StringBuilder x = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                x.append(line);
            }
            Gson gson = new Gson();
            basket = gson.fromJson(x.toString(), Basket.class);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return basket;
    }

    public static Basket loadTXT(File textFile) throws IOException {
        Basket basket = new Basket();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(textFile))) {
            String goodsStr = bufferedReader.readLine();
            String pricesStr = bufferedReader.readLine();
            String quantitiesStr = bufferedReader.readLine();

            name = goodsStr.split(" ");

            prise = Arrays.stream(pricesStr.split(" "))
                    .map(Integer::parseInt)
                    .mapToInt(Integer::intValue)
                    .toArray();

            amount = Arrays.stream(quantitiesStr.split(" "))
                    .map(Integer::parseInt)
                    .mapToInt(Integer::intValue)
                    .toArray();

            sum = Arrays.stream(quantitiesStr.split(" "))
                    .map(Integer::parseInt)
                    .mapToLong(Integer::longValue)
                    .toArray();

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return basket;
    }
}