import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class DeliveryCardTest {
    private String dateGenerate(int i, String s) {
        return LocalDate.now().plusDays(i).format(DateTimeFormatter.ofPattern(s));
    }

    @Test
    void openDeliveryCardTest() {
        open("http://localhost:9999/");
        $(byText("Карта с доставкой!"));
        $("[data-test-id=city] input").setValue("Челябинск");
        String date = dateGenerate(5, "dd.MM.yyyy");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME),
                Keys.DELETE);
        $("[data-test-id='date'] input").setValue(date);
        $("[data-test-id='name'] input").setValue("Ступин Иван");
        $("[data-test-id='phone'] input").setValue("+79998887766");
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $(".notification__title")
                .shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldBe(Condition.text("Успешно!"));
        $(".notification__content")
                .shouldBe(Condition.visible)
                .shouldHave(Condition.text("Встреча успешно забронирована на " + date));
    }


}
