package GoogleTranslateTest;

import org.openqa.selenium.By;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import static com.codeborne.selenide.Selenide.*;
import static org.testng.Assert.assertEquals;

public class GoogleTranslate {

    @Test(dataProvider = "languages")
    public void testTranslation(String langCode, String expectedTranslation) {
        open("https://translate.google.com/?hl=uk&tab=TT&sl=auto&tl=" + langCode + "&op=translate");

        String selector = "textarea[aria-label='Текст оригіналу']";
        $(By.cssSelector(selector)).setValue("Я круто вивчу TestNG ");

        String  translatedXpath = "//span[@class = 'ryNqvb']";
        String translatedText = $$(By.xpath(translatedXpath)).get(0).text();

        assertEquals(translatedText, expectedTranslation);
    }

    @DataProvider(name = "languages")
    public Object[][] persons() {
        return new Object[][] {
                {"de", "Ich werde testng cool studieren"},
                {"ms", "Saya akan belajar testng sejuk"},
                {"qu", "Estudiasaqmi prueba de cool ."},
                {"bm", "N bɛna testng cool kalan ."},
                {"tt", "Мин салкынны салкын өйрәнәчәкмен"},
                {"fi", "Opiskelen testiä viileänä"},
                {"so", "Waxaan baran doonaa imtixaanka qabow"},
                {"la", "Ego studere testng refrigescant"},
                {"is", "Ég mun læra testng flott"},
                {"cs", "Budu studovat testng cool"},
                {"es", "Estudiaré testng genial"},
                {"id", "Saya akan belajar testng keren"},
                {"lt", "Aš studijuosiu „TestNG Cool“"},
                {"fr", "J'étudierai le test."},
                {"sv", "Jag kommer att studera testng cool"},
                {"mt", "Se nistudja testng cool"},
                {"tr", "Testng serin çalışacağım"},
                {"co", "Studiaraghju Testng Cool"},
                {"pt", "Vou estudar testng legal"},
                {"ga", "Déanfaidh mé staidéar ar Testng Cool"}
        };
    }

}