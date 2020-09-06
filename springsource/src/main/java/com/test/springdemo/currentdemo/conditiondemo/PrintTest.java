package com.test.springdemo.currentdemo.conditiondemo;

public class PrintTest {

    public static void main(String[] args) {
        CurrentPrint currentPrint=new CurrentPrint();
        new Thread(new Product(currentPrint)).start();
        new Thread(new Consumer(currentPrint)).start();
    }

    static class Product implements Runnable{
        private CurrentPrint currentPrint;
        public Product(CurrentPrint currentPrint){
            this.currentPrint=currentPrint;
        }
        @Override
        public void run() {
            try {
//                this.currentPrint.product();
                this.currentPrint.singleConditionProduce();
//                this.currentPrint.synPrpducer();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static class Consumer implements Runnable{
        private CurrentPrint currentPrint;
        public Consumer(CurrentPrint currentPrint){
            this.currentPrint=currentPrint;
        }
        @Override
        public void run() {
            try {
//                this.currentPrint.consumer();
                this.currentPrint.singleConConsumer();
//                this.currentPrint.synConsumer();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
