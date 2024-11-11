package store;

import camp.nextstep.edu.missionutils.test.NsTest;
import org.junit.jupiter.api.Test;

import static camp.nextstep.edu.missionutils.test.Assertions.assertSimpleTest;
import static org.assertj.core.api.Assertions.assertThat;

public class IntegrationTest extends NsTest {
    @Test
    void 프로모션_적용_상품_안내_출력() {
        assertSimpleTest(() -> {
            run("[콜라-2]", "N", "N", "N");
            assertThat(output()).contains("현재 콜라은(는) 1개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)");
        });
    }

    @Test
    void 재고_부족_상품_프로모션_정가_결제_안내_출력() {
        assertSimpleTest(() -> {
            run("[콜라-10]", "N", "N", "N");
            assertThat(output()).contains("현재 콜라 1개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)");
        });
    }

    @Test
    void 멤버십_할인_적용_안내_출력() {
        assertSimpleTest(() -> {
            run("[콜라-10]", "N", "N", "N");
            assertThat(output()).contains("멤버십 할인을 받으시겠습니까? (Y/N)");
        });
    }

    @Test
    void 영수증_출력() {
        assertSimpleTest(() -> {
            run("[콜라-10]", "N", "N", "N");
            assertThat(output()).contains("==============W 편의점================\n" +
                    "상품명\t\t수량\t금액\n" +
                    "콜라\t\t9 \t9,000\n" +
                    "=============증\t정===============\n" +
                    "콜라\t\t3\n" +
                    "====================================\n" +
                    "총구매액\t\t9\t9,000\n" +
                    "행사할인\t\t\t-3,000\n" +
                    "멤버십할인\t\t\t-0\n" +
                    "내실돈\t\t\t 6,000");
        });
    }

    @Test
    void 추가_구매_안내_출력() {
        assertSimpleTest(() -> {
            run("[콜라-10]", "N", "N", "N");
            assertThat(output()).contains("감사합니다. 구매하고 싶은 다른 상품이 있나요? (Y/N)");
        });
    }

    @Override
    protected void runMain() {
        Application.main(new String[]{});
    }
}
