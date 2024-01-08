import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class DeliveryCardTest {
    private String dateGenerate(int i, String s) {
        return LocalDate.now().plusDays(i).format(DateTimeFormatter.ofPattern(s));
    }

    @Test
    void openDeliveryCardTest() {
        open("http://localhost:9999/");
        $(byText("Карта с доставкой!"));
        $("[data-test-id=city] input").setValue("Че");
        SelenideElement el = $$(".popup__container").last();
        el.$$(".menu-item__control").find(exactText("Челябинск")).click();
        String dateNew = dateGenerate(7, "dd.MM.yyyy");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME),
                Keys.DELETE);
        $("[data-test-id='date'] input").setValue(dateNew);
//        String xpathOne = "//*[date()=" + dateNew + "  ]";
//        SelenideElement date = $("[data-test-id='date']");
//        date.$(".icon_name_calendar").click();
//        date.$(By.xpath(xpathOne)).click();
        $("[data-test-id='name'] input").setValue("Ступин Иван");
        $("[data-test-id='phone'] input").setValue("+79998887766");
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $(".notification__title")
                .shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldBe(Condition.text("Успешно!"));
        $(".notification__content")
                .shouldBe(Condition.visible)
                .shouldHave(Condition.text("Встреча успешно забронирована на " + dateNew));
    }


}
