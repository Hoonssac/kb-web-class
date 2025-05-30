package ch08.practice.p04;

public interface Payment {
    boolean pay(int amount);      // 결제: 성공 여부 반환
    void approve();               // 승인
    void cancel(String reason);   // 취소
}