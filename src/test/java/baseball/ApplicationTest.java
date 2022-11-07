package baseball;

import camp.nextstep.edu.missionutils.test.NsTest;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import static baseball.Computer.calcResult;
import static baseball.Number.generateRandomNumber;
import static camp.nextstep.edu.missionutils.test.Assertions.assertRandomNumberInRangeTest;
import static camp.nextstep.edu.missionutils.test.Assertions.assertSimpleTest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ApplicationTest extends NsTest {

    @Test
    void getResult_메서드_결과가_2볼() {
        List<Integer> result = calcResult(123, 234);

        int ballCount = result.get(0);
        int strikeCount = result.get(1);

        assertThat(ballCount).isEqualTo(2);
        assertThat(strikeCount).isEqualTo(0);
    }

    @Test
    void getResult_메서드_결과가_1볼_1스트라이크() {
        List<Integer> result = calcResult(293, 234);

        int ballCount = result.get(0);
        int strikeCount = result.get(1);

        assertThat(ballCount).isEqualTo(1);
        assertThat(strikeCount).isEqualTo(1);
    }

    @Test
    void getResult_메서드_결과가_2스트라이크() {
        List<Integer> result = calcResult(423, 523);

        int ballCount = result.get(0);
        int strikeCount = result.get(1);

        assertThat(ballCount).isEqualTo(0);
        assertThat(strikeCount).isEqualTo(2);
    }

    @Test
    void getResult_메서드_결과가_낫싱() {
        List<Integer> result = calcResult(123, 456);

        int ballCount = result.get(0);
        int strikeCount = result.get(1);

        assertThat(ballCount).isEqualTo(0);
        assertThat(strikeCount).isEqualTo(0);
    }


    @Test
    void generateOpponentNumber_메서드의_리턴_값은_세_자리_수() {
        int opponentNumber = generateRandomNumber();

        int length = (int) (Math.log10(opponentNumber) + 1);
        assertThat(length).isEqualTo(3);
    }

    @Test
    void generateOpponentNumber_메서드의_리턴_값의_각_숫자는_1부터_9까지의_숫자로_구성() {
        int opponentNumber = generateRandomNumber();

        List<Integer> numList = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);

        while (opponentNumber != 0) {
            int digit = opponentNumber % 10;
            assertThat(numList).contains(digit);
            opponentNumber /= 10;
        }
    }

    @Test
    void generateOpponentNumber_메서드의_리턴_값의_각_숫자는_중복되지_않음() {
        int opponentNumber = generateRandomNumber();

        List<Integer> numList = new ArrayList<>();

        while (opponentNumber != 0) {
            int digit = opponentNumber % 10;
            assertThat(numList).doesNotContain(digit);
            numList.add(digit);
            opponentNumber /= 10;
        }
    }

    @Test
    void readNum_메서드에서_1_부터_9_이외의_입력_case_1() {
        assertSimpleTest(() ->
                assertThatThrownBy(() -> runException("1 4"))
                        .isInstanceOf(IllegalArgumentException.class)
        );
    }

    @Test
    void readNum_메서드에서_1_부터_9_이외의_입력_case_2() {
        assertSimpleTest(() ->
                assertThatThrownBy(() -> runException("13가"))
                        .isInstanceOf(IllegalArgumentException.class)
        );
    }

    @Test
    void readNum_메서드에서_길이가_잘못된_입력_case_1() {
        assertSimpleTest(() ->
                assertThatThrownBy(() -> runException("1343"))
                        .isInstanceOf(IllegalArgumentException.class)
        );
    }

    @Test
    void readNum_메서드에서_길이가_잘못된_입력_case_2() {
        assertSimpleTest(() ->
                assertThatThrownBy(() -> runException("43"))
                        .isInstanceOf(IllegalArgumentException.class)
        );
    }

    @Test
    void readNum_메서드에서_중복된_입력_case_1() {
        assertSimpleTest(() ->
                assertThatThrownBy(() -> runException("772"))
                        .isInstanceOf(IllegalArgumentException.class)
        );
    }

    @Test
    void readNum_메서드에서_중복된_입력_case_2() {
        assertSimpleTest(() ->
                assertThatThrownBy(() -> runException("212"))
                        .isInstanceOf(IllegalArgumentException.class)
        );
    }

    @Test
    void readNum_메서드에서_중복된_입력_case_3() {
        assertSimpleTest(() ->
                assertThatThrownBy(() -> runException("899"))
                        .isInstanceOf(IllegalArgumentException.class)
        );
    }

    @Test
    void readNum_메서드에서_올바르지_않은_입력_case_1() {
        assertSimpleTest(() ->
                assertThatThrownBy(() -> runException("80 9"))
                        .isInstanceOf(IllegalArgumentException.class)
        );
    }

    @Test
    void readNum_메서드에서_올바르지_않은_입력_case_2() {
        assertSimpleTest(() ->
                assertThatThrownBy(() -> runException("89 9"))
                        .isInstanceOf(IllegalArgumentException.class)
        );
    }

    @Test
    void 게임종료_후_재시작() {
        assertRandomNumberInRangeTest(
                () -> {
                    run("246", "135", "1", "597", "589", "2");
                    assertThat(output()).contains("낫싱", "3스트라이크", "1볼 1스트라이크", "3스트라이크", "게임 종료");
                },
                1, 3, 5, 5, 8, 9
        );
    }

    @Test
    void 예외_테스트() {
        assertSimpleTest(() ->
                assertThatThrownBy(() -> runException("1234"))
                        .isInstanceOf(IllegalArgumentException.class)
        );
    }

    @Override
    public void runMain() {
        Application.main(new String[]{});
    }
}
