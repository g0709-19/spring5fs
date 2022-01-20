package chap02;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        // 1. 설정 정보를 이용해서 Bean 객체를 생성한다.
        AnnotationConfigApplicationContext ctx =
                new AnnotationConfigApplicationContext(AppContext.class);
        /*
         * 왜 new Greeter() 하지 않고, 이런 식으로 생성할까?
         * => 스프링은 기본적으로 한 개의 @Bean Annotation 에 대해 한 개의
         * Bean 객체를 생성한다. 자원을 많이 사용하는 DB 접근 객체는 싱글톤
         * 사용이 좋다. (그 외 DI[결합도를 낮출 수 있다] 와 라이프 사이클 관리가
         * 용이하다는 장점이 있지만, 책 내용을 좀 더 살펴봐야 할 것 같다.)
         */
        // 2. Bean 객체를 제공한다.
        Greeter g = ctx.getBean("greeter", Greeter.class);
        String msg = g.greet("스프링");
        System.out.println(msg);

        // 이 코드하고 차이가 뭐지? (위에 서술)
        Greeter g2 = new Greeter();
        g2.setFormat("%s, 안녕하세요!");
        g2.greet("스프링");
        System.out.println(msg);
        ctx.close();
    }
}
