package ImdbTest;

import com.codeborne.selenide.Selenide;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.io.IOException;
import java.util.Iterator;

import static com.codeborne.selenide.Selenide.open;

public class ImdTop100Test {
    private static final String IMDB_COM_CHART_TOP = "https://www.imdb.com/chart/top/";

    @Test(dataProvider = "top100Movies")
    public void top100MoviesData(Element movieTop) {
        String yearTop = movieTop.parent().parent().parent()
                .select("span[class*=cli-title-metadata-item]").get(0).text();
        String ratingTop = movieTop.parent().parent().parent()
                .select("span[data-testid='ratingGroup--imdb-rating']").get(0).text().split(" ")[0];
        String titleTop = movieTop.parent().parent().parent().selectFirst("a").text();
        String movieLinkSelector = movieTop.parent().parent().parent().selectFirst("a").cssSelector();

        open(IMDB_COM_CHART_TOP);
        Selenide.$(movieLinkSelector).click();
        String ratingMoviePage = Selenide.$("[data-testid='hero-rating-bar__aggregate-rating__score'] span")
                .text();
        String titleMoviePage = Selenide.$("[data-testid='hero__pageTitle'] span").text();
        String yearMoviePage = Selenide.$("[data-testid='hero__pageTitle']").sibling(1).$("a").text();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(yearMoviePage, yearTop);
        softAssert.assertEquals(ratingMoviePage, ratingTop);
        softAssert.assertTrue(titleTop.contains(titleMoviePage));
        softAssert.assertAll();
    }

    @DataProvider(name = "top100Movies")
    public Iterator<Element> top100Movies() throws IOException {
        Document doc = Jsoup.connect(IMDB_COM_CHART_TOP).get();
        Elements top = doc.select("[data-testid='chart-layout-main-column'] h3");
        return top.subList(0, 100).iterator();
    }
}
