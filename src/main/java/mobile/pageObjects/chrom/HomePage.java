package mobile.pageObjects.chrom;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import mobile.pageObjects.BasePage;
import org.openqa.selenium.WebElement;

public class HomePage extends BasePage {

    @AndroidFindBy(id ="com.google.android.deskclock:id/tab_menu_timer")
    private WebElement timer;
    @AndroidFindBy(id ="com.google.android.deskclock:id/action_bar_title")
    private WebElement clockTitle;



    public HomePage(AndroidDriver d) {
        super(d);
    }
    public Boolean IsExist(){
        try{
            visibilityOf(clockTitle).isDisplayed();

        }catch (Exception e){
            return false;
        }
        return true;
    }
    public void clickTimer()
    {
        timer.click();

    }
}
