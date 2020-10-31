package com.test.springdemo.currentdemo.threadlocal;

public class MyThreadLocal extends ThreadLocal<MyThreadLocal.Holder>{
    private MyThreadLocal(){}
    private static final MyThreadLocal instance=new MyThreadLocal();
    @Override
    public MyThreadLocal.Holder initialValue(){
        return new Holder();
    }
    public static void assign(String userid,String ip){
        instance.get().assign(ip,userid);
    }
    public static String getUserId(){
        return instance.get().getUserid();
    }
    public static class Holder{
        private String ip;
        private String userid;

        public void assign(String ip,String userid){
            this.ip=ip;
            this.userid=userid;
        }

        public String getIp() {
            return ip;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }
    }

}
