import java.io.IOException;
import java.util.LinkedList;

public class Storage implements AbstractStorage {
    private final static int MAX_SIZE = 100;
    private LinkedList list = new LinkedList();

    public static class Inner {
        public void print() {
            System.out.println(MAX_SIZE);
        }
    }

    public class Inner1 {
        public void print() {
            class Innnnner{
                public void sys(){
                    System.out.println("");
                }
            }
        }
    }

    @Override
    public void produce(int num) {
        synchronized (list) {

            while (num + list.size() > MAX_SIZE) {
                System.out.println("要生产的产品数量是：" + num + "\t[库存量]：" + list.size() + "\t暂时不能生产产品");
                try {
                    list.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            //疯狂生产
            for (int i = 0; i < num; i++) {
                list.add(new Object());
            }
            System.out.println("已经生产产品数：" + num + "\t[现在存储量是]：" + list.size());
            list.notifyAll();
        }
    }

    @Override
    public void consume(int num) {
        synchronized (list) {
            while (num > list.size()) {
                System.out.println("【要消费的产品数量】:" + num + "\t【库存量】:"
                        + list.size() + "\t暂时不能执行生产任务!");

                try {
                    list.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            //消费条件满足，开始消费
            for (int i = 0; i < num; i++) {
                list.remove();
            }

            System.out.println("【已经消费产品数】:" + num + "\t【现仓储量为】:" + list.size());

            list.notifyAll();
        }

    }

    public static void main(String[] args) {
        Storage.Inner storage = new Storage.Inner();
        storage.print();

    }


}
