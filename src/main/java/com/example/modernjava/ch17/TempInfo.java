package com.example.modernjava.ch17;

import java.util.Random;
// 544p 현재 보고된 온도를 전달하는 자바 빈
public class TempInfo {

    public static final Random random = new Random();

    private final String town;
    private final int temp;

    public TempInfo(String town, int temp) {
        this.town = town;
        this.temp = temp;
    }
    // 정적 팩토리 메서드를 이용해 해당 도시의 TempInfo 인스턴스를 생성한다.
    public static TempInfo fetch(String town) {
        // 10% 확률로 에러발생
//        if(random.nextInt(10) == 0)
//            throw new RuntimeException("Error!!!!!");
        return new TempInfo(town, random.nextInt(100));
    }

    public String getTown() {
        return town;
    }

    public int getTemp() {
        return temp;
    }

    @Override
    public String toString() {
        return "TempInfo{" +
                "town='" + town + '\'' +
                ", temp=" + temp +
                '}';
    }
}
